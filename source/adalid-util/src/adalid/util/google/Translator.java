/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.google;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Translator {

    private static final Logger logger = Logger.getLogger(Translator.class);

    private static final String[] strings = new String[]{
        "Hello everybody!",
        "Afroyim v. Rusk, 387 U.S. 253 (1967), is a major United States Supreme Court case in which the Court ruled that citizens of "
        + "the United States may not be deprived of their citizenship involuntarily.[1][2] The U.S. government had attempted to revoke the "
        + "citizenship of Beys Afroyim, a Polish-born man, because he had cast a vote in an Israeli election after becoming a naturalized U.S. "
        + "citizen. The Supreme Court decided that Afroyim's right to retain his citizenship was guaranteed by the Citizenship Clause of the "
        + "Fourteenth Amendment to the Constitution. In so doing, the Court overruled one of its own precedents, Perez v. Brownell (1958), in "
        + "which it had upheld loss of citizenship under similar circumstances less than a decade earlier.",
        "Goodbye!"};

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            translate(strings);
        } else {
            translate(args);
        }
    }

    public static String[] translate(String[] args) {
        if (args == null || args.length == 0) {
            return args;
        }
        String translation;
        URIBuilder builder = newURIBuilder();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null && args[i].length() > 0) {
                    logger.info(args[i]);
                    translation = translate(args[i], builder, client);
                    logger.info(translation);
                    if (translation != null) {
                        args[i] = translation;
                    }
                }
            }
        } catch (IOException | URISyntaxException ex) {
            logger.fatal(ex);
        }
        return args;
    }

    public static Properties translate(Properties properties) {
        if (properties == null || properties.isEmpty()) {
            return properties;
        }
        String arg, translation;
        URIBuilder builder = newURIBuilder();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            Set<String> names = properties.stringPropertyNames();
            for (String name : names) {
                arg = properties.getProperty(name);
                logger.info(arg);
                translation = translate(arg, builder, client);
                logger.info(translation);
                if (translation != null) {
                    properties.setProperty(name, translation);
                }
            }
        } catch (IOException | URISyntaxException ex) {
            logger.fatal(ex);
        }
        return properties;
    }

    private static URIBuilder newURIBuilder() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("www.google.com");
        builder.setPath("/translate_t");
        return builder;
    }

    private static String translate(String arg, URIBuilder builder, CloseableHttpClient client) throws IOException, URISyntaxException {
        builder.clearParameters();
        builder.setParameter("langpair", "en|es");
        builder.setParameter("text", arg);
        URI uri = builder.build();
        HttpGet get = new HttpGet(uri);
        HttpUriRequest request = (HttpUriRequest) get;
        try (CloseableHttpResponse response = client.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getReasonPhrase().equals("OK")) {
                logger.info(statusLine.toString());
            } else {
                throw new RuntimeException(statusLine.toString());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                logger.info(entity.getContentType());
                return translate(entity);
            }
        } catch (ClientProtocolException ex) {
            logger.fatal(uri, ex);
        }
        return null;
    }

    private static String translate(HttpEntity entity) throws IOException {
        String string = EntityUtils.toString(entity);
        Span resultBox = SpanBuilder.build(string, " id=result_box ");
        if (resultBox == null) {
            return null;
        }
        String result = resultBox.getString();
        String translation = "";
        int index = 0;
        Span span;
        do {
            span = SpanBuilder.build(result, " title=", index);
            if (span == null) {
                break;
            }
            translation += span.getValue();
            index = span.getEndIndex();
        } while (index > 0);
//      logger.info(result);
        return translation;
    }

}
