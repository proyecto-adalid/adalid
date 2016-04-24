/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.properties;

import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class BootstrappingFile {

    private static final Logger logger = Logger.getLogger(BootstrappingFile.class);

    private static final String BOOTSTRAPPING_FILE_NAME = "bootstrapping.properties";

    private static String bootstrapping_file_name;

    public static String getName() {
        if (bootstrapping_file_name == null) {
            bootstrapping_file_name = BOOTSTRAPPING_FILE_NAME;
            logger.info("file name not previously set; defaults to " + bootstrapping_file_name);
        }
        return bootstrapping_file_name;
    }

    public static void setName(String name) {
        if (bootstrapping_file_name == null) {
            if (name == null) {
                bootstrapping_file_name = BOOTSTRAPPING_FILE_NAME;
                logger.warn("null value for name parameter; defaults to " + bootstrapping_file_name);
            } else {
                bootstrapping_file_name = name;
                logger.info("using user-defined file name " + bootstrapping_file_name);
            }
        } else if (bootstrapping_file_name.equals(name)) {
            logger.debug("file name already set to " + bootstrapping_file_name);
        } else {
            logger.error("file name previously set to " + bootstrapping_file_name + " so now it cannot be set to " + name
                + " (once set, it cannot be changed)");
        }
    }

}
