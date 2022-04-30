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
package adalid.util;

import adalid.commons.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Utility {

    private static final Logger logger = Logger.getLogger(Utility.class);

    private static final String ARGS_FAILED = "method getArguments failed";

    private static final String ARGS_SUFFIX = ".args";

    private static final boolean TESTING = false;

    public static final String USER_DIR = System.getProperty("user.dir");

    public static final String USER_HOME = System.getProperty("user.home");

    public static final String OS_NAME = System.getProperty("os.name");

    public static final boolean WINDOWS = StringUtils.containsIgnoreCase(OS_NAME, "windows");

    private static ProjectObjectModel pom;

    private static ProjectBuilderDictionary pbd;

    private static ProjectObjectModel pom() {
        if (pom == null) {
            pom = new ProjectObjectModel();
        }
        return pom;
    }

    protected static String getAdalidProjectVersion() {
        return pom().getProjectVersionNumber();
    }

    protected static void logAdalidProjectVersion() {
        pom().logProjectVersion();
    }

    protected static void updateProjectBuilderDictionary(Class<?> clazz) {
        if (TESTING) {
            if (clazz != null) {
                pbd = ProjectBuilderDictionary.load();
                pbd.setLastExecutedUtilityClassName(clazz.getCanonicalName());
                pbd.store();
            }
        }
    }

    private static ProjectBuilderDictionary pbd() {
        if (pbd == null) {
            pbd = ProjectBuilderDictionary.load();
        }
        return pbd;
    }

    public static String getLastExecutedProjectAlias() {
        return pbd().getLastExecutedProjectAlias();
    }

    public static String getLastExecutedProjectBaseFolderName() {
        return pbd().getLastExecutedProjectBaseFolderName();
    }

    public static String getLastExecutedProjectClassName() {
        return pbd().getLastExecutedProjectClassName();
    }

    public static Class<? extends ProjectBuilder> getLastExecutedProjectClass() {
        return pbd().getLastExecutedProjectClass();
    }

    public static ProjectBuilder getLastExecutedProject() {
        return pbd().getLastExecutedProject();
    }

    public static void logSystemProperties() {
        Properties properties = System.getProperties();
        Set<String> names = new TreeSet<>(properties.stringPropertyNames());
        for (String name : names) {
            logger.info(name + "=" + System.getProperty(name));
        }
    }

    public static String chooseDirectory(String path) {
        String currentDirectoryPath = FilUtils.isDirectory(path) ? path : USER_DIR;
        JFileChooser chooser = new JFileChooser(currentDirectoryPath);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = chooser.showOpenDialog(null);
        return option == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile().getPath() : null;
    }

    public static String chooseFile(String path) {
        return chooseFile(path, (FileFilter[]) null);
    }

    public static String chooseFile(String path, List<? extends FileFilter> list) {
        if (list == null) {
            return chooseFile(path, (FileFilter[]) null);
        }
        FileFilter[] array1 = new FileFilter[list.size()];
        FileFilter[] array2 = list.toArray(array1);
        return chooseFile(path, true, array2);
    }

    public static String chooseFile(String path, FileFilter... filters) {
        return chooseFile(path, true, filters);
    }

    public static String chooseFile(String path, boolean acceptAllFileFilterUsed, FileFilter... filters) {
        String currentDirectoryPath = FilUtils.isDirectory(path) ? path : USER_DIR;
        JFileChooser chooser = new JFileChooser(currentDirectoryPath);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (filters != null && filters.length > 0) {
            for (FileFilter filter : filters) {
                chooser.addChoosableFileFilter(filter);
            }
            chooser.setFileFilter(filters[0]);
            chooser.setAcceptAllFileFilterUsed(acceptAllFileFilterUsed);
        }
        int option = chooser.showOpenDialog(null);
        return option == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile().getPath() : null;
    }

    /**
     * El método getArguments busca los argumentos en los archivos de propiedades privadas y de arranque (bootstrapping), en ese orden.
     *
     * @param clazz clase cuyo nombre canónico se utiliza en los archivos como prefijo de las propiedades correspondientes a los argumentos.
     * @return la matriz de argumentos.
     */
    public static String[] getArguments(Class<?> clazz) {
        return getArguments(clazz == null ? null : clazz.getName());
    }

    public static String[] getArguments(String clazz) {
        ExtendedProperties properties;
        String[] arguments = null;
        if (clazz == null) {
            logger.error(ARGS_FAILED + "; null value for clazz parameter");
        } else {
            properties = PropertiesHandler.getPrivateProperties();
            arguments = getArguments(clazz, properties, Level.TRACE);
            if (arguments == null) {
                properties = PropertiesHandler.getBootstrapping();
                arguments = getArguments(clazz, properties, Level.INFO);
            }
        }
        return arguments == null ? new String[0] : arguments;
    }

    private static String[] getArguments(String clazz, ExtendedProperties properties, Level level) {
        if (properties == null) {
            logger.log(level, ARGS_FAILED + "; properties is null");
        } else if (properties.isEmpty()) {
            logger.log(level, ARGS_FAILED + "; properties is empty");
        } else {
            String key = clazz + ARGS_SUFFIX;
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

    public static boolean showConfirmDialog(String message, String title) {
        return showConfirmDialog(message, title, JOptionPane.QUESTION_MESSAGE);
    }

    public static boolean showConfirmDialog(String message, String title, int messageType) {
        final int type = JOptionPane.YES_NO_OPTION;
        final int showConfirmDialog = JOptionPane.showConfirmDialog(null, message, title, type, messageType);
        logger.info(title + " = " + (showConfirmDialog == JOptionPane.YES_OPTION ? "Yes" : "No"));
        return showConfirmDialog == JOptionPane.YES_OPTION;
    }

    public static ExtendedProperties getBootstrapping() {
        return PropertiesHandler.getBootstrapping();
    }

    /**
     * El método setBootstrappingFileName se utiliza para establecer el nombre del archivo de configuración inicial del utilitario, en caso de que se
     * deba utilizar un archivo diferente al predeterminado (bootstrapping.properties).
     *
     * @param name nombre del archivo, sin la ruta; el archivo debe estar almacenado en el subdirectorio src/main/resources, donde mismo se encuentra
     * el archivo de configuración inicial predeterminado (bootstrapping.properties).
     */
    public static void setBootstrappingFileName(String name) {
        BootstrappingFile.setName(name);
    }

}
