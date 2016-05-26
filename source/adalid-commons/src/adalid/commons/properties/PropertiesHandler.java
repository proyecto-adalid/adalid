/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.properties;

import adalid.commons.util.FilUtils;
import adalid.commons.util.ThrowableUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class PropertiesHandler {

    private static final Logger logger = Logger.getLogger(PropertiesHandler.class);

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEP = System.getProperties().getProperty("file.separator");

    private static final String BOOTSTRAPPING_FILE_NAME = BootstrappingFile.getName();

    private static final String BOOTSTRAPPING_FILE_PATH = USER_DIR + FILE_SEP + BOOTSTRAPPING_FILE_NAME;

    private static final String USER_VELOCITY_RESOURCES_DIR = USER_DIR + FILE_SEP + "resources" + FILE_SEP + "velocity";

    private static final String PRIVATE_PROPERTIES = USER_DIR + FILE_SEP + "nbproject" + FILE_SEP + "private" + FILE_SEP + "private.properties";

    private static final ExtendedProperties bootstrapping;

    private static final String ROOT_FOLDER_KEY = "root.folder";

    private static final String VELOCITY_FOLDER_KEY = "velocity.folder";

    private static final String VELOCITY_FILE_KEY = "velocity.properties.file";

    private static final String VELOCITY_SUP_FILE_KEY = "velocity.supplementary.properties.file";

    private static final File root_folder, velocity_properties_file, velocity_supplementary_properties_file;

    private static final File[] velocity_folders, platforms_folders;

    static {
        bootstrapping = getExtendedProperties(BOOTSTRAPPING_FILE_PATH);
        root_folder = rootFolder();
        velocity_properties_file = velocityPropertiesFile();
        velocity_supplementary_properties_file = velocitySupplementaryPropertiesFile();
        velocity_folders = velocityFolders();
        platforms_folders = platformsFolders();
//      log(bootstrapping, Level.INFO);
    }

    /**
     * @return the bootstrapping properties
     */
    public static ExtendedProperties getBootstrapping() {
        return bootstrapping;
    }

    public static ExtendedProperties getPrivateProperties() {
        File file = new File(PRIVATE_PROPERTIES);
        ExtendedProperties properties = getExtendedProperties(file, Level.TRACE);
        return properties;
    }

    public static Properties getVelocityProperties() {
        File file = getVelocityPropertiesFile();
        Properties properties = loadProperties(file);
        return properties;
    }

    public static Properties getVelocitySupplementaryProperties() {
        File file = getVelocitySupplementaryPropertiesFile();
        Properties properties = loadProperties(file);
        return properties;
    }

    public static Properties loadProperties(String filename) {
        return loadProperties(filename, false);
    }

    public static Properties loadProperties(String filename, boolean sortedKeys) {
        return loadProperties(filename, sortedKeys, Level.ERROR);
    }

    public static Properties loadProperties(String filename, boolean sortedKeys, Level badFileLogLevel) {
        return StringUtils.isBlank(filename) ? newProperties(sortedKeys)
            : loadProperties(new File(filename), sortedKeys, badFileLogLevel);
    }

    public static Properties loadProperties(File file) {
        return loadProperties(file, false);
    }

    public static Properties loadProperties(File file, boolean sortedKeys) {
        return loadProperties(file, sortedKeys, Level.ERROR);
    }

    public static Properties loadProperties(File file, boolean sortedKeys, Level badFileLogLevel) {
        Properties properties = newProperties(sortedKeys);
        String filename = file == null ? "" : file.getPath();
        if (file == null) {
            logger.error("null properties file");
        } else if (file.isFile()) {
            try {
                logger.trace("loading " + filename);
                try (InputStream inStream = new FileInputStream(filename)) {
                    properties.load(inStream);
                }
                printProperties(properties);
            } catch (Exception ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        } else {
            logger.log(badFileLogLevel, filename + " does not exist or is not a normal file");
        }
        return properties;
    }

    public static Properties loadProperties(byte[] buffer) {
        return loadProperties(buffer, false);
    }

    public static Properties loadProperties(byte[] buffer, boolean sortedKeys) {
        Properties properties = newProperties(sortedKeys);
        if (buffer == null) {
            logger.error("null properties buffer");
        } else {
            try {
                logger.trace("loading buffer " + Arrays.toString(buffer));
                try (InputStream inStream = new ByteArrayInputStream(buffer)) {
                    properties.load(inStream);
                }
                printProperties(properties);
            } catch (Exception ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        }
        return properties;
    }

    public static Properties loadProperties(ResourceBundle bundle) {
        return loadProperties(bundle, false);
    }

    public static Properties loadProperties(ResourceBundle bundle, boolean sortedKeys) {
        Properties properties = newProperties(sortedKeys);
        if (bundle == null) {
            logger.error("null properties bundle");
        } else {
            logger.trace("loading bundle " + bundle);
            Set<String> keySet = bundle.keySet();
            String value;
            for (String key : keySet) {
                try {
                    value = bundle.getString(key);
                    if (StringUtils.isBlank(value)) {
                        continue;
                    }
                    properties.setProperty(key, value.trim());
                } catch (MissingResourceException e) {
                }
            }
            printProperties(properties);
        }
        return properties;
    }

    private static Properties newProperties(boolean sortedKeys) {
        return sortedKeys ? new SortedProperties() : new Properties();
    }

    public static void storeProperties(Properties properties, String filename) {
        storeProperties(properties, filename, null);
    }

    public static void storeProperties(Properties properties, String filename, String comments) {
        if (StringUtils.isNotBlank(filename)) {
            storeProperties(properties, new File(filename), comments);
        }
    }

    public static void storeProperties(Properties properties, File file) {
        storeProperties(properties, file, null);
    }

    public static void storeProperties(Properties properties, File file, String comments) {
        SortedProperties sortedProperties;
        String filename = file == null ? "" : file.getPath();
        if (file == null) {
            logger.error("null properties file");
        } else if (file.getParentFile().isDirectory() || file.getParentFile().mkdirs()) {
            try {
                if (properties instanceof SortedProperties) {
                    sortedProperties = (SortedProperties) properties;
                } else {
                    sortedProperties = new SortedProperties();
                    sortedProperties.putAll(properties);
                }
                logger.trace("storing " + filename);
                try (OutputStream outStream = new FileOutputStream(filename)) {
                    sortedProperties.store(outStream, comments);
                }
            } catch (Exception ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        } else {
            logger.error(filename + " is not a valid directory");
        }
    }

    public static void printProperties(Properties properties) {
        log(properties, Level.TRACE);
    }

    private static void log(Properties properties, Level level) {
        String[] names = new String[properties.stringPropertyNames().size()];
        properties.stringPropertyNames().toArray(names);
        Arrays.sort(names);
        String value;
        for (String name : names) {
            value = properties.getProperty(name);
            logger.log(level, name + " = " + (StringUtils.containsIgnoreCase(name, "password") ? "***" : value));
        }
    }

    private static void log(ExtendedProperties properties, Level level) {
        Object next;
        Set<String> names = new TreeSet<>();
        Iterator iterator = properties.getKeys();
        if (iterator != null) {
            while (iterator.hasNext()) {
                next = iterator.next();
                if (next instanceof String) {
                    names.add((String) next);
                }
            }
        }
        String value;
        for (String name : names) {
            value = properties.getString(name);
            logger.log(level, name + " = " + (StringUtils.containsIgnoreCase(name, "password") ? "***" : value));
        }
    }

    public static ExtendedProperties getExtendedProperties(String filename) {
        return getExtendedProperties(filename, Level.ERROR);
    }

    public static ExtendedProperties getExtendedProperties(String filename, Level badFileLogLevel) {
        return StringUtils.isBlank(filename) ? null
            : getExtendedProperties(new File(filename), badFileLogLevel);
    }

    public static ExtendedProperties getExtendedProperties(File file) {
        return getExtendedProperties(file, Level.ERROR);
    }

    public static ExtendedProperties getExtendedProperties(File file, Level badFileLogLevel) {
        ExtendedProperties extendedProperties = new ExtendedProperties();
        String filename = file == null ? "" : file.getPath();
        if (file == null) {
            logger.error("null properties file");
        } else if (file.isFile()) {
            try {
                logger.trace("loading " + filename);
                try (InputStream inStream = new FileInputStream(filename)) {
                    extendedProperties.load(inStream);
                }
                printExtendedProperties(extendedProperties);
            } catch (Exception ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        } else {
            logger.log(badFileLogLevel, filename + " does not exist or is not a normal file");
        }
        return extendedProperties;
    }

    public static ExtendedProperties getExtendedProperties(byte[] buffer) {
        ExtendedProperties extendedProperties = new ExtendedProperties();
        if (buffer == null) {
            logger.error("null properties buffer");
        } else {
            try {
                logger.trace("loading buffer " + Arrays.toString(buffer));
                try (InputStream inStream = new ByteArrayInputStream(buffer)) {
                    extendedProperties.load(inStream);
                }
                printExtendedProperties(extendedProperties);
            } catch (Exception ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        }
        return extendedProperties;
    }

    public static void printExtendedProperties(ExtendedProperties extendedProperties) {
        ArrayList<String> list = new ArrayList<>();
        for (Iterator i = extendedProperties.getKeys(); i.hasNext();) {
            list.add((String) i.next());
        }
        String[] names = new String[list.size()];
        list.toArray(names);
        Arrays.sort(names);
        String[] values;
        for (String name : names) {
            values = extendedProperties.getStringArray(name);
            logger.trace(name + " = " + (StringUtils.containsIgnoreCase(name, "password") ? "***" : getArrayString(values)));
        }
    }

    private static String getArrayString(String[] values) {
        if (values == null) {
            return null;
        }
        String s = "";
        String c = ", ";
        for (String value : values) {
            s += c + "\"" + StringUtils.trimToEmpty(value) + "\"";
        }
        return "{" + StringUtils.removeStart(s, c) + "}";
    }

    private static File rootFolder() {
        String pathname = getPath(ROOT_FOLDER_KEY);
        if (StringUtils.isBlank(pathname)) {
            pathname = FilUtils.getWorkspaceFolderPath();
            if (StringUtils.isBlank(pathname)) {
                logMissingProperty(ROOT_FOLDER_KEY);
                return null;
            }
            logMissingProperty(ROOT_FOLDER_KEY, pathname);
        }
        File file = new File(pathname);
        if (FilUtils.isNotVisibleDirectory(file)) {
            logInvalidDirectory(ROOT_FOLDER_KEY, pathname);
            return null;
        }
        return file;
    }

    private static File velocityPropertiesFile() {
        String pathname = getPath(VELOCITY_FILE_KEY);
        if (StringUtils.isBlank(pathname)) {
            pathname = USER_DIR + FILE_SEP + "velocity.properties";
            logMissingProperty(VELOCITY_FILE_KEY, pathname);
        }
        File file = new File(pathname);
        if (FilUtils.isNotVisibleFile(file)) {
            logInvalidFile(VELOCITY_FILE_KEY, pathname);
            return null;
        }
        return file;
    }

    private static File velocitySupplementaryPropertiesFile() {
        String pathname = getPath(VELOCITY_SUP_FILE_KEY);
        if (StringUtils.isBlank(pathname)) {
            if (velocity_properties_file == null) {
                pathname = USER_DIR + FILE_SEP + "velocity.supplementary.properties";
            } else {
                pathname = velocity_properties_file.getParent() + FILE_SEP + "velocity.supplementary.properties";
            }
            logMissingProperty(VELOCITY_SUP_FILE_KEY, pathname);
        }
        File file = new File(pathname);
        if (FilUtils.isNotVisibleFile(file)) {
            logInvalidFile(VELOCITY_SUP_FILE_KEY, pathname);
            return null;
        }
        return file;
    }

    private static File[] velocityFolders() {
        File file;
        ArrayList<File> files = new ArrayList<>();
        file = new File(USER_VELOCITY_RESOURCES_DIR);
        if (FilUtils.isVisibleDirectory(file)) {
            files.add(file);
        }
        String pathnames[] = getPathArray(VELOCITY_FOLDER_KEY);
        if (pathnames != null && pathnames.length > 0) {
            for (String pathname : pathnames) {
                file = new File(pathname);
                if (FilUtils.isVisibleDirectory(file)) {
                    files.add(file);
                } else {
                    logInvalidDirectory(VELOCITY_FOLDER_KEY, pathname);
                }
            }
        }
        if (files.isEmpty()) {
            logMissingProperty(VELOCITY_FOLDER_KEY);
            return null;
        }
        return files.toArray(new File[0]);
    }

    private static File[] platformsFolders() {
        if (velocity_folders == null || velocity_folders.length == 0) {
            return null;
        }
        String platforms = "platforms";
        File file;
        ArrayList<File> files = new ArrayList<>();
        for (File folder : velocity_folders) {
            file = new File(folder.getPath() + FILE_SEP + platforms);
            if (FilUtils.isVisibleDirectory(file)) {
                files.add(file);
            }
        }
        if (files.isEmpty()) {
            logMissingDirectory(VELOCITY_FOLDER_KEY, platforms);
            return null;
        }
        return files.toArray(new File[0]);
    }

    private static String getPath(String key) {
        String pathname = FilUtils.fixPath(bootstrapping.getString(key));
        return pathname;
    }

    private static String[] getPathArray(String key) {
        String[] strings = bootstrapping.getStringArray(key);
        if (strings == null || strings.length == 0) {
            return null;
        }
        String path;
        Set<String> set = new LinkedHashSet<>();
        for (String string : strings) {
            path = FilUtils.fixPath(string);
            if (StringUtils.isNotBlank(path)) {
                set.add(path);
            }
        }
        return set.toArray(new String[0]);
    }

    private static void logMissingProperty(String key) {
        String pattern = "property \"{0}\" is missing from file \"{1}\"";
        String message = MessageFormat.format(pattern, key, BOOTSTRAPPING_FILE_PATH);
        logger.error(message);
    }

    private static void logMissingProperty(String key, String value) {
        String pattern = "property \"{0}\" is missing from file \"{1}\"; defaults to \"{2}\"";
        String message = MessageFormat.format(pattern, key, BOOTSTRAPPING_FILE_PATH, value);
        logger.warn(message);
    }

    private static void logMissingDirectory(String key, String pathname) {
        String pattern = "unable to find a valid \"{0}\" directory, check property \"{1}\" at file \"{2}\"";
        String message = MessageFormat.format(pattern, pathname, key, BOOTSTRAPPING_FILE_PATH);
        logger.error(message);
    }

    private static void logInvalidDirectory(String key, String pathname) {
        String pattern = "\"{0}\" is not a valid directory, check property \"{1}\" at file \"{2}\"";
        String message = MessageFormat.format(pattern, pathname, key, BOOTSTRAPPING_FILE_PATH);
        logger.error(message);
    }

    private static void logInvalidFile(String key, String pathname) {
        String pattern = "\"{0}\" is not a valid file, check property \"{1}\" at file \"{2}\"";
        String message = MessageFormat.format(pattern, pathname, key, BOOTSTRAPPING_FILE_PATH);
        logger.error(message);
    }

    public static File getRootFolder() {
        return root_folder;
    }

    public static File[] getVelocityFolders() {
        return velocity_folders;
    }

    public static File getVelocityPropertiesFile() {
        return velocity_properties_file;
    }

    public static File getVelocitySupplementaryPropertiesFile() {
        return velocity_supplementary_properties_file;
    }

    public static File[] getPlatformsFolders() {
        return platforms_folders;
    }

}
