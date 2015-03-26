/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.properties;

import java.io.File;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class PropertiesGetter {

    private static final Logger logger = Logger.getLogger(PropertiesGetter.class);

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");

    public static ExtendedProperties getPrivateProperties() {
        String filename = USER_DIR + FILE_SEPARATOR + "nbproject" + FILE_SEPARATOR + "private" + FILE_SEPARATOR + "private.properties";
        logger.debug("properties = " + filename);
        File file = new File(filename);
        ExtendedProperties properties = PropertiesHandler.getExtendedProperties(file);
        return properties;
    }

}
