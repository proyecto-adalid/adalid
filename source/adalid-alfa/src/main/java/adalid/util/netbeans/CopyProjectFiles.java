/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.netbeans;

import adalid.commons.util.*;
import adalid.util.*;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * CopyProjectFiles sirve para copiar archivos de configuración de NetBeans del directorio de ejemplos al directorio raíz del proyecto.
 *
 * Los archivos del directorio src/main/resources/configuration/NetBeans son ejemplos de archivos de configuración de NetBeans. Puede copiarlos al
 * directorio raíz del proyecto y modificar las copias si fuese necesario.
 *
 * Este proyecto requiere JDK 11. Aun cuando la plataforma Java predeterminada de NetBeans sea JDK 11, se recomienda definir la plataforma "JDK 11" y
 * configurar el proyecto para que utilice esa plataforma en lugar de la plataforma "JDK 11 (Default)". La plataforma se define con el "Java Platform
 * Manager" de NetBeans. La configuración se suele hacer en la ventana de propiedades del proyecto, con el elemento "Java Platform" de la categoría
 * "Build/Compile". Al configurar el proyecto, se agrega el elemento netbeans.hint.jdkPlatform al archivo nb-configuration.xml; el valor de
 * netbeans.hint.jdkPlatform es el nombre de la plataforma, con un guion bajo en lugar del espacio en blanco; es decir, JDK_11. En el archivo de
 * ejemplo nb-configuration.xml se define el valor del elemento netbeans.hint.jdkPlatform. Copiar el archivo equivale solo a la configuración; no
 * tendrá efecto si la plataforma no está definida.
 *
 * En el archivo de ejemplo nbactions.xml se define la acción "Clean and Build with all the fixings"; además se definen parámetros de las acciones
 * run, run.single.main, debug y debug.single.main. Revise y modifique el valor de los parámetros si fuese necesario; en particular, revise los
 * valores de -Xms y -Xmx en las propiedades exec.args; éstos se deben ajustar según la disponibilidad de memoria de su computador.
 *
 * @author Jorge Campins
 */
public class CopyProjectFiles extends Utility {

    private static final Logger logger = Logger.getLogger(CopyProjectFiles.class);

    private static final String SOURCE = FilUtils.getUserDirPath("src", "main", "resources", "configuration", "NetBeans");

    private static final String TARGET = USER_DIR;

    private static final String COPY_MESSAGE = "¿Desea copiar el archivo?";

    private static final String COPY_TITLE = "Copiar el archivo de ejemplo ";

    private static final String REPLACE_MESSAGE = " ya existe\n¿Desea reemplazarlo?";

    private static final String REPLACE_TITLE = "Reemplazar el archivo de configuración ";

    public static void copy() {
        /**/
        final String file1 = "nb-configuration.xml";
        final String text1 = "Este proyecto requiere JDK 11. Aun cuando la plataforma Java predeterminada de NetBeans sea JDK 11,\n"
            + "se recomienda definir la plataforma \"JDK 11\" y configurar el proyecto para que utilice esa plataforma en\n"
            + "lugar de la plataforma \"JDK 11 (Default)\".\n\n"
            + "La plataforma se define con el \"Java Platform Manager\" de NetBeans. La configuración se suele hacer en\n"
            + "la ventana de propiedades del proyecto, con el elemento \"Java Platform\" de la categoría \"Build/Compile\".\n\n"
            + "Al configurar el proyecto, se agrega el elemento netbeans.hint.jdkPlatform al archivo nb-configuration.xml;\n"
            + "el valor de netbeans.hint.jdkPlatform es el nombre de la plataforma, con un guion bajo en lugar del espacio\n"
            + "en blanco; es decir, JDK_11.\n\n"
            + "En el archivo de ejemplo nb-configuration.xml se define el valor del elemento netbeans.hint.jdkPlatform.\n"
            + "Copiar el archivo equivale solo a la configuración; no tendrá efecto si la plataforma no está definida.\n\n";
        /**/
        final String file2 = "nbactions.xml";
        final String text2 = "En el archivo de ejemplo nbactions.xml se define la acción \"Clean and Build with all the fixings\";\n"
            + "además se definen parámetros de las acciones run, run.single.main, debug y debug.single.main.\n\n"
            + "Revise y modifique el valor de los parámetros si fuese necesario; en particular, revise los valores\n"
            + "de -Xms y -Xmx en las propiedades exec.args;  éstos se deben ajustar según la disponibilidad de\n"
            + "memoria de su computador.\n\n";
        /**/
        copyFile(file1, text1);
        copyFile(file2, text2);
        updateProjectBuilderDictionary(CopyProjectFiles.class);
    }

    private static void copyFile(String filename, String message) {
        final String back = back(filename);
        final File source = new File(SOURCE, filename);
        final File target = new File(TARGET, filename);
        final File backup = new File(TARGET, back);
        logger.info("archivo = " + source);
        logger.info("destino = " + TARGET);
        if (source.exists()) {
            boolean copy = showConfirmDialog(message + COPY_MESSAGE, COPY_TITLE + filename);
            if (copy) {
                boolean exists = target.exists();
                if (exists) {
                    copy = showConfirmDialog(target + REPLACE_MESSAGE, REPLACE_TITLE + filename, JOptionPane.WARNING_MESSAGE);
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

    private static String back(String filename) {
        int lastDot = filename.lastIndexOf('.');
        String stmp = TimeUtils.simpleTimestampString();
        String back = lastDot < 0 ? filename + "-" + stmp : filename.substring(0, lastDot) + "-" + stmp + "." + filename.substring(lastDot + 1);
        return "backups" + File.separator + back;
    }

}
