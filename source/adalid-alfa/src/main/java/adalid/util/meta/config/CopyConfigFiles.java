/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package adalid.util.meta.config;

import adalid.commons.util.*;
import adalid.util.*;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class CopyConfigFiles extends Utility {

    static final String SOURCE_DIR = FilUtils.getUserDirPath("src", "main", "resources", "configuration");

    static final String TARGET_DIR = USER_DIR;

    private static final Logger logger = Logger.getLogger(CopyConfigFiles.class);

    private static final String COPY_MESSAGE = "¿Desea copiar el archivo?";

    private static final String COPY_TITLE = "Copiar el archivo de ejemplo ";

    private static final String REPLACE_MESSAGE = " ya existe\n¿Desea reemplazarlo?";

    private static final String REPLACE_TITLE = "Reemplazar el archivo de configuración ";

    private static final String BACKUP_SUBDIR = "backups";

    public static void copyFile(String filename, String fromDir, String toDir, String message) {
        copyFile(filename, filename, fromDir, toDir, message);
    }

    public static void copyFile(String sourcefilename, String targetfilename, String fromDir, String toDir, String message) {
        final String srce = StringUtils.defaultIfBlank(fromDir, SOURCE_DIR);
        final String trgt = StringUtils.defaultIfBlank(toDir, TARGET_DIR);
        final String back = backupFile(targetfilename);
        final File source = new File(srce, sourcefilename);
        final File target = new File(trgt, targetfilename);
        final File backup = new File(trgt, back);
        logger.info("archivo = " + source);
        logger.info("destino = " + target);
        if (source.exists()) {
            boolean copy = showConfirmDialog(message + COPY_MESSAGE, COPY_TITLE + targetfilename);
            if (copy) {
                boolean exists = target.exists();
                if (exists) {
                    copy = showConfirmDialog(target + REPLACE_MESSAGE, REPLACE_TITLE + targetfilename, JOptionPane.WARNING_MESSAGE);
                }
                if (copy) {
                    try {
                        if (exists) {
                            FileUtils.copyFile(target, backup);
                            logger.info("el archivo existente fue copiado como " + backup);
                        }
                        FileUtils.copyFile(source, target);
                        if (exists) {
                            logger.warn("el archivo existente fue reemplazado en el directorio destino");
                        } else {
                            logger.info("el archivo fue copiado al directorio destino");
                        }
                    } catch (IOException ex) {
                        logger.fatal(ThrowableUtils.getString(ex), ex);
                    }
                } else {
                    logger.info("el archivo no fue reemplazado en el directorio destino");
                }
            } else {
                logger.info("el archivo no fue copiado al directorio destino");
            }
        } else {
            logger.warn("el archivo " + source + " no existe");
        }
    }

    public static String backupFile(String filename) {
        int lastDot = filename.lastIndexOf('.');
        String sep = lastDot == 0 ? "" : "-";
        String stss = TimeUtils.simpleTimestampString();
        String back = lastDot < 0 ? filename + sep + stss : filename.substring(0, lastDot) + sep + stss + filename.substring(lastDot);
        return BACKUP_SUBDIR + File.separator + back;
    }

}
