/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.jaas.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
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

    public static final String RESPONSE_SUCCESSFULLY_VERIFIED = "Google ReCAPTCHA: user's response successfully verified";

    public static final String RESPONSE_COULD_NOT_BE_VERIFIED = "Google ReCAPTCHA: user's response could not be verified";

    private static final Logger logger = Logger.getLogger(GoogleRecaptcha.class.getName());

    private static final Level TRACE = Level.FINE;

    private static final String PARAMETER_KEY = "g-recaptcha-response";

    private static final String SECRET_KEY = "6LdZTgkTAAAAAHjLTTc9tdW75qBThFp_bZG-IIE8";

    private static final boolean VERIFY_NULL_RESPONSE = true;

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
