/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.jaas.google;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Jorge Campins
 */
public class GoogleRecaptcha {

    //  <editor-fold defaultstate="collapsed" desc="quick-setup">
    //  Edit web/resources/javascript3.js in the war module of your enterprise project.
    //  Find the return statement of function grecaptchaSiteKey and replace the returned value with your site key.
    //
    //  Add a property named google.recaptcha.secret.key to the application server system properties; the property
    //  value should be your secret key. Alternatively, store your secret key in a file and add a property named
    //  google.recaptcha.secret.key.file to the application server system properties; the property value should be
    //  the absolute path of the file containing your secret key. Alternatively, store your secret key in a file
    //  named google.recaptcha.secret.key in the application server user directory.
    //
    //  </editor-fold>
    //
    public static final String RESPONSE_SUCCESSFULLY_VERIFIED = "Google ReCAPTCHA: user's response successfully verified";

    public static final String RESPONSE_COULD_NOT_BE_VERIFIED = "Google ReCAPTCHA: user's response could not be verified";

    private static final Logger logger = Logger.getLogger(GoogleRecaptcha.class.getName());

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEP = System.getProperties().getProperty("file.separator");

    private static final String SECRET_KEY_PROPERTY_KEY = "google.recaptcha.secret.key";

    private static final String SECRET_KEY_FILE_PROPERTY_KEY = "google.recaptcha.secret.key.file";

    private static final String SECRET_KEY_FILE_DEFAULT_PATH = USER_DIR + FILE_SEP + SECRET_KEY_PROPERTY_KEY;

    private static final String MISSING_SECRET_KEY = "Google ReCAPTCHA secret key is missing; test secret key will be used";

    private static final String DEFAULT_SECRET_KEY = "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";

    private static final Level TRACE = Level.FINE;

    private static final String PARAMETER_KEY = "g-recaptcha-response";

    private static final String VALIDATOR_KEY = "g-recaptcha-response-validator";

    private static final String SECRET_KEY = secretKey();

    private static final boolean VERIFY_NULL_RESPONSE = true;

    private static String secretKey() {
        logger.log(Level.INFO, VALIDATOR_KEY + " = {0}", GoogleRecaptcha.class.getName());
        String secretKey = System.getProperties().getProperty(SECRET_KEY_PROPERTY_KEY);
        if (secretKey == null || secretKey.isEmpty()) {
            String path = secretKeyFilePath();
            File file = new File(path);
            if (file.isFile()) {
                logger.log(TRACE, SECRET_KEY_FILE_PROPERTY_KEY + " = {0}", path);
                try {
                    secretKey = new Scanner(file).useDelimiter("\\Z").next();
                } catch (FileNotFoundException ex) {
                    logger.log(Level.WARNING, SECRET_KEY_FILE_PROPERTY_KEY + " {0} is missing", path);
                }
            } else {
                logger.log(Level.WARNING, SECRET_KEY_FILE_PROPERTY_KEY + " {0} is missing or invalid", path);
            }
        }
        if (secretKey == null || secretKey.isEmpty()) {
            logger.log(Level.WARNING, MISSING_SECRET_KEY);
            return DEFAULT_SECRET_KEY;
        }
        return secretKey;
    }

    private static String secretKeyFilePath() {
        String path = System.getProperties().getProperty(SECRET_KEY_FILE_PROPERTY_KEY);
        return path == null || path.isEmpty() ? SECRET_KEY_FILE_DEFAULT_PATH : path.replace('\\', '/').replace("/", FILE_SEP);
    }

    public static boolean verifyUserResponse() {
        String userResponse = getRequestParameter(PARAMETER_KEY);
        return verify(userResponse);
    }

    private static String getRequestParameter(String key) {
        if (key == null) {
            return null;
        }
        Object request;
        try {
            request = PolicyContext.getContext(HttpServletRequest.class.getName());
        } catch (PolicyContextException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String parameter = httpServletRequest.getParameter(key);
            logger.log(TRACE, "{0}: {1}", new Object[]{key, parameter});
            return parameter;
        }
        logger.log(TRACE, "HTTP Servlet Request: {0}", request);
        return null;
    }

    private static boolean verify(String userResponse) {
        if (userResponse == null) {
            return VERIFY_NULL_RESPONSE;
        }
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost("www.google.com");
        builder.setPath("/recaptcha/api/siteverify");
//      builder.clearParameters();
        builder.setParameter("secret", SECRET_KEY);
        builder.setParameter("response", userResponse);
        URI uri;
        try {
            uri = builder.build();
        } catch (URISyntaxException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex);
            return false;
        }
        HttpUriRequest request = (HttpUriRequest) new HttpGet(uri);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = client.execute(request)) {
                StatusLine statusLine = response.getStatusLine();
                logger.log(TRACE, "Status Line: {0}", statusLine);
                if (statusLine.getReasonPhrase().equals("OK")) {
                    return success(response.getEntity());
                }
            } catch (ClientProtocolException ex) {
                logger.log(Level.SEVERE, ex.toString(), ex);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex);
        }
        return false;
    }

    private static boolean success(HttpEntity entity) throws IOException {
        if (entity == null) {
            return false;
        }
        logger.log(TRACE, "{0}", entity.getContentType());
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
        String line, string = "";
        while ((line = reader.readLine()) != null) {
            string += line;
        }
        logger.log(TRACE, "Content: {0}", string);
        boolean success = success(string);
        if (success) {
            logger.log(TRACE, RESPONSE_SUCCESSFULLY_VERIFIED);
        } else {
            logger.log(TRACE, RESPONSE_COULD_NOT_BE_VERIFIED);
        }
        return success;
    }

    private static boolean success(String jsonString) {
        JsonObject jsonObject;
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            jsonObject = jsonReader.readObject();
            return jsonObject.getBoolean("success");
        }
    }

}
