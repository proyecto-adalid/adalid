/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util;

import adalid.commons.properties.BootstrappingFile;
import adalid.commons.properties.PropertiesHandler;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Utility {

    private static final Logger logger = Logger.getLogger(Utility.class);

    private static final String ARGS_FAILED = "method getArguments failed";

    private static final String ARGS_SUFFIX = ".args";

    public static String[] getArguments(Class<?> clazz) {
        ExtendedProperties properties;
        String[] arguments = null;
        if (clazz == null) {
            logger.error(ARGS_FAILED + "; null value for clazz parameter");
        } else {
            properties = PropertiesHandler.getPrivateProperties();
            arguments = getArguments(clazz, properties, Level.TRACE);
            if (arguments == null) {
                properties = PropertiesHandler.getBootstrapping();
                arguments = getArguments(clazz, properties, Level.ERROR);
            }
        }
        return arguments == null ? new String[0] : arguments;
    }

    private static String[] getArguments(Class<?> clazz, ExtendedProperties properties, Level level) {
        if (properties == null) {
            logger.log(level, ARGS_FAILED + "; properties is null");
        } else if (properties.isEmpty()) {
            logger.log(level, ARGS_FAILED + "; properties is empty");
        } else {
            String key = clazz.getName() + ARGS_SUFFIX;
            try {
                String[] strings = properties.getStringArray(key);
                if (strings == null || strings.length == 0) {
                    logger.log(level, ARGS_FAILED + "; property " + key + " not found");
                } else {
                    return strings;
                }
            } catch (Exception e) {
                logger.log(level, ARGS_FAILED + "; " + key + " not properly defined (" + e + ")");
            }
        }
        return null;
    }

    public static void setBootstrappingFileName(String name) {
        BootstrappingFile.setName(name);
    }

}
