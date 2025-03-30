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
package adalid.commons.properties;

import adalid.commons.util.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class PropertiesHandler {

    private static final Logger logger = Logger.getLogger(PropertiesHandler.class);

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final String BOOTSTRAPPING_FILE_NAME = BootstrappingFile.getName();

    private static final String BOOTSTRAPPING_FILE_PATH = USER_DIR + FILE_SEP + BOOTSTRAPPING_FILE_NAME;

    private static final String USER_VELOCITY_RESOURCES_DIR = USER_DIR + FILE_SEP + "resources" + FILE_SEP + "velocity";

    private static final String PRIVATE_FILE_NAME = "private.properties";

    private static final String PRIVATE_FILE_PATH = USER_DIR + FILE_SEP + "nbproject" + FILE_SEP + "private" + FILE_SEP + PRIVATE_FILE_NAME;

    private static final String PROPERTIES_SUFFIX = ".properties";

    private static final Pattern startsWithWordCharacter = Pattern.compile("^\\w+");

    private static final ExtendedProperties bootstrapping;

    private static final String PREFERRED_FILE_ENCODING_KEY = "preferred.file.encoding";

    private static final String ROOT_FOLDER_KEY = "root.folder";

    private static final String VELOCITY_FOLDER_KEY = "velocity.folder";

    private static final String VELOCITY_FILE_KEY = "velocity.properties.file";

    private static final String VELOCITY_SUP_FILE_KEY = "velocity.supplementary.properties.file";

    private static final Charset preferred_file_encoding;

    private static final File root_folder, velocity_properties_file, velocity_supplementary_properties_file;

    private static final Properties velocity_supplementary_properties;

    private static final File[] velocity_folders, platforms_folders;

    static {
        bootstrapping = getBootstrappingProperties();
        preferred_file_encoding = preferredFileEncoding();
        root_folder = rootFolder();
        velocity_properties_file = velocityPropertiesFile();
        velocity_supplementary_properties_file = velocitySupplementaryPropertiesFile();
        velocity_supplementary_properties = loadProperties(velocity_supplementary_properties_file);
        velocity_folders = velocityFolders();
        platforms_folders = platformsFolders();
//      log(bootstrapping, Level.INFO);
    }

    public static boolean missingBootstrappingProperties() {
        return bootstrapping == null || bootstrapping.isEmpty()
            || preferred_file_encoding == null
            || root_folder == null
            || velocity_properties_file == null
            || velocity_supplementary_properties_file == null
            || velocity_folders == null || velocity_folders.length == 0
            || platforms_folders == null || platforms_folders.length == 0;
    }

    private static ExtendedProperties getBootstrappingProperties() {
        ExtendedProperties properties = getExtendedProperties(BOOTSTRAPPING_FILE_PATH, Level.DEBUG);
        return properties == null || properties.isEmpty() ? getResourceAsExtendedProperties("/" + BOOTSTRAPPING_FILE_NAME) : properties;
    }

    public static ExtendedProperties getPrivateProperties() {
        ExtendedProperties properties = getExtendedProperties(PRIVATE_FILE_PATH, Level.DEBUG);
        return properties == null || properties.isEmpty() ? getResourceAsExtendedProperties("/" + PRIVATE_FILE_NAME) : properties;
    }

    public static Properties getResourceAsProperties(String resource) {
        return getResourceAsProperties(resource, Level.ERROR);
    }

    public static Properties getResourceAsProperties(String resource, Level badFileLogLevel) {
        return getResourceAsProperties(resource, badFileLogLevel, Level.INFO);
    }

    public static Properties getResourceAsProperties(String resource, Level badFileLogLevel, Level goodFileLogLevel) {
        if (StringUtils.isBlank(resource)) {
            return null;
        }
        Properties properties = new Properties();
        try (final InputStream stream = PropertiesHandler.class.getResourceAsStream(resource)) {
            if (stream == null) {
                logger.log(badFileLogLevel, "resource " + resource + " is missing");
            } else {
                properties.load(stream);
                logger.log(goodFileLogLevel, "resource " + resource + " loaded (" + properties.size() + " properties)");
            }
        } catch (Exception ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return properties;
    }

    public static ExtendedProperties getResourceAsExtendedProperties(String resource) {
        return getResourceAsExtendedProperties(resource, Level.ERROR);
    }

    public static ExtendedProperties getResourceAsExtendedProperties(String resource, Level badFileLogLevel) {
        return getResourceAsExtendedProperties(resource, badFileLogLevel, Level.INFO);
    }

    public static ExtendedProperties getResourceAsExtendedProperties(String resource, Level badFileLogLevel, Level goodFileLogLevel) {
        if (StringUtils.isBlank(resource)) {
            return null;
        }
        ExtendedProperties properties = new ExtendedProperties();
        try (final InputStream stream = PropertiesHandler.class.getResourceAsStream(resource)) {
            if (stream == null) {
                logger.log(badFileLogLevel, "resource " + resource + " is missing");
            } else {
                properties.load(stream);
                logger.log(goodFileLogLevel, "resource " + resource + " loaded (" + properties.size() + " properties)");
            }
        } catch (Exception ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return properties;
    }

    public static Set<String> getResourceBundleKeys(String baseName, Locale locale) {
        Set<String> keySet = new LinkedHashSet<>();
        String resource = resourceBundlePropertiesFileName(baseName, locale);
        try (final InputStream stream = PropertiesHandler.class.getResourceAsStream(resource)) {
            if (stream == null) {
                missingResourceBundle(resource, locale);
            } else {
                try (final BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        if (line == null || line.isEmpty()) {
                        } else {
                            if (line.startsWith("#") || line.startsWith("!")) {
                            } else {
                                addResourceBundleKey(keySet, line);
                            }
                        }
                    }
                    logger.trace("resource " + resource + " loaded (" + keySet.size() + " keys)");
                    return keySet;
                } catch (IOException ex) {
                    logger.error(ex.getClass().getSimpleName() + " " + baseName + "/" + locale);
                    logger.error(ThrowableUtils.getString(ex));
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getClass().getSimpleName() + " " + baseName + "/" + locale);
            logger.error(ThrowableUtils.getString(ex));
        }
        return null;
    }

    public static List<String> getResourceBundleRows(String baseName, Locale locale) {
        List<String> list = new ArrayList<>();
        String resource = resourceBundlePropertiesFileName(baseName, locale);
        try (final InputStream stream = PropertiesHandler.class.getResourceAsStream(resource)) {
            if (stream == null) {
                missingResourceBundle(resource, locale);
            } else {
                try (final BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        list.add(StringUtils.trimToEmpty(line));
                    }
                    logger.trace("resource " + resource + " loaded (" + list.size() + " rows)");
                    return list;
                } catch (IOException ex) {
                    logger.error(ex.getClass().getSimpleName() + " " + baseName + "/" + locale);
                    logger.error(ThrowableUtils.getString(ex));
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getClass().getSimpleName() + " " + baseName + "/" + locale);
            logger.error(ThrowableUtils.getString(ex));
        }
        return null;
    }

    private static String resourceBundlePropertiesFileName(String baseName, Locale locale) {
        return "/" + baseName.replace('.', '/') + "_" + locale.toString() + PROPERTIES_SUFFIX;
    }

    private static void addResourceBundleKey(Set<String> keySet, String line) {
        String separatorChars = "= :";
        String[] split = StringUtils.split(StringUtils.trimToNull(line), separatorChars);
        if (split != null && split.length > 0) {
            String key = split[0];
            if (startsWithWordCharacter.matcher(key).find()) {
                keySet.add(key);
            }
        }
    }

    /**/
    // <editor-fold defaultstate="collapsed" desc="createMissingUserFiles">
    /*
    private static void createMissingUserFiles() {
        String[] dirs = {USER_DIR, "src", "main", "resources", "adalid", "jee2", "bundles"};
        String folder = StringUtils.join(dirs, FILE_SEP);
        File file = new File(folder);
        file.mkdirs();
        String[] bundles = {"BundleMensajes", "BundleWebui"};
        String[] locales = {"_en__PLUS", "_es__PLUS"};
        String name, path;
        for (String bundle : bundles) {
            for (String locale : locales) {
                name = bundle + locale + ".properties";
                path = folder + FILE_SEP + name;
                file = new File(path);
                if (file.exists()) {
                    logger.trace(file + " already exists");
                } else {
                    try {
                        logger.info("createNewFile(" + file + ")");
                        file.createNewFile();
                    } catch (IOException ex) {
                        logger.fatal(ThrowableUtils.getString(ex), ex);
                    }
                }
            }
        }
    }
    /**/
    // </editor-fold>
    /**/
    private static void missingResourceBundle(String resource, Locale locale) {
        if (locale.getVariant().isEmpty()) {
            logger.error("missing resource " + resource);
        } else {
            logger.warn("missing resource " + resource);
            String before = StringUtils.substringBeforeLast(resource, "/").replace("/", FILE_SEP);
            String bundle = StringUtils.substringAfterLast(resource, "/");
            String[] dirs = {USER_DIR, "src", "main", "resources"};
            String folder = StringUtils.join(dirs, FILE_SEP) + before;
            try {
                File file = new File(folder);
                if (!file.exists()) {
                    logger.info("creating directory " + file);
                    file.mkdirs();
                }
                file = new File(file, bundle);
                logger.info("creating resource file " + file);
                file.createNewFile();
            } catch (IOException ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        }
    }

    /**
     * @return the bootstrapping properties
     */
    public static ExtendedProperties getBootstrapping() {
        return bootstrapping;
    }

    public static Properties getVelocityProperties() {
        File file = getVelocityPropertiesFile();
        Properties properties = loadProperties(file);
        return properties;
    }

    public static Properties getVelocitySupplementaryProperties() {
        return velocity_supplementary_properties;
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
            } catch (IOException ex) {
                logger.error(ThrowableUtils.getString(ex), ex);
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
            } catch (IOException ex) {
                logger.error(ThrowableUtils.getString(ex), ex);
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
        storeProperties(properties, filename, comments, null);
    }

    public static void storeProperties(Properties properties, String filename, String comments, String lineSeparator) {
        if (StringUtils.isNotBlank(filename)) {
            storeProperties(properties, new File(filename), comments, lineSeparator);
        }
    }

    public static void storeProperties(Properties properties, File file) {
        storeProperties(properties, file, null);
    }

    public static void storeProperties(Properties properties, File file, String comments) {
        storeProperties(properties, file, comments, null);
    }

    public static void storeProperties(Properties properties, File file, String comments, String lineSeparator) {
        SortedProperties sortedProperties;
        String filename = file == null ? "" : file.getPath();
        if (file == null) {
            logger.error("null properties file");
        } else if (file.getParentFile().isDirectory() || file.getParentFile().mkdirs()) {
            try {
                if (properties instanceof SortedProperties sp) {
                    sortedProperties = sp;
                } else {
                    sortedProperties = new SortedProperties();
                    sortedProperties.putAll(properties);
                }
                logger.trace("storing " + filename);
                try (OutputStream outStream = new FileOutputStream(filename)) {
                    sortedProperties.store(outStream, comments, lineSeparator);
                }
            } catch (IOException ex) {
                logger.error(ThrowableUtils.getString(ex), ex);
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
        String value;
        Set<String> names = new TreeSet<>(properties.stringPropertyNames());
        for (String name : names) {
            value = properties.getProperty(name);
            logger.log(level, name + " = " + (StringUtils.containsIgnoreCase(name, "password") ? "***" : value));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="log">
    /*
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
    /**/
    // </editor-fold>
//
    public static ExtendedProperties getExtendedProperties(String filename) {
        return getExtendedProperties(filename, Level.ERROR);
    }

    public static ExtendedProperties getExtendedProperties(String... filename) {
        if (filename == null || filename.length == 0) {
            return null;
        }
        int last = filename.length - 1;
        ExtendedProperties properties = null;
        for (int i = 0; i < filename.length && (properties == null || properties.isEmpty()); i++) {
            properties = getExtendedProperties(filename[i], i == last ? Level.ERROR : Level.INFO, Level.INFO);
        }
        return properties;
    }

    public static ExtendedProperties getExtendedProperties(String filename, Level badFileLogLevel) {
        return getExtendedProperties(filename, badFileLogLevel, Level.TRACE);
    }

    public static ExtendedProperties getExtendedProperties(String filename, Level badFileLogLevel, Level goodFileLogLevel) {
        return StringUtils.isBlank(filename) ? null : getExtendedProperties(new File(filename), badFileLogLevel, goodFileLogLevel);
    }

    public static ExtendedProperties getExtendedProperties(File file) {
        return getExtendedProperties(file, Level.ERROR);
    }

    public static ExtendedProperties getExtendedProperties(File file, Level badFileLogLevel) {
        return getExtendedProperties(file, badFileLogLevel, Level.TRACE);
    }

    public static ExtendedProperties getExtendedProperties(File file, Level badFileLogLevel, Level goodFileLogLevel) {
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
                logger.log(goodFileLogLevel, "file " + filename + " loaded (" + extendedProperties.size() + " properties)");
                printExtendedProperties(extendedProperties);
            } catch (IOException ex) {
                logger.error(ThrowableUtils.getString(ex), ex);
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
            } catch (IOException ex) {
                logger.error(ThrowableUtils.getString(ex), ex);
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

    private static Charset preferredFileEncoding() {
        return getCharset(PREFERRED_FILE_ENCODING_KEY);
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
        try {
            FileUtils.forceMkdir(file);
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
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
        return files.toArray(ArrUtils.arrayOf(File.class));
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
        return files.toArray(ArrUtils.arrayOf(File.class));
    }

    private static Charset getCharset(String key) {
        return getCharset(key, Charset.defaultCharset());
    }

    private static Charset getCharset(String key, Charset defaultValue) {
        Charset charset = defaultValue;
        Level level = Level.DEBUG;
        String charsetName = bootstrapping.getString(key);
        if (StringUtils.isNotBlank(charsetName)) {
            try {
                charset = Charset.forName(charsetName);
            } catch (Exception ex) {
                logger.error(key + " " + ex);
                level = Level.WARN;
            }
        }
        logger.log(level, key + "=" + charset);
        return charset;
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
        return set.toArray(ArrUtils.arrayOf(String.class));
    }

    private static void logMissingProperty(String key) {
        if (bootstrapping != null && !bootstrapping.isEmpty()) {
            String pattern = "property \"{0}\" is missing from file \"{1}\"";
            String message = MessageFormat.format(pattern, key, BOOTSTRAPPING_FILE_NAME);
            logger.error(message);
        }
    }

    private static void logMissingProperty(String key, String value) {
        if (bootstrapping != null && !bootstrapping.isEmpty()) {
            String pattern = "property \"{0}\" is missing from file \"{1}\"; default is \"{2}\"";
            String message = MessageFormat.format(pattern, key, BOOTSTRAPPING_FILE_NAME, value);
            logger.warn(message);
        }
    }

    private static void logMissingDirectory(String key, String pathname) {
        if (bootstrapping != null && !bootstrapping.isEmpty()) {
            String pattern = "unable to find a valid \"{0}\" directory, check property \"{1}\" at file \"{2}\"";
            String message = MessageFormat.format(pattern, pathname, key, BOOTSTRAPPING_FILE_NAME);
            logger.error(message);
        }
    }

    private static void logInvalidDirectory(String key, String pathname) {
        if (bootstrapping != null && !bootstrapping.isEmpty()) {
            String pattern = "\"{0}\" is not a valid directory, check property \"{1}\" at file \"{2}\"";
            String message = MessageFormat.format(pattern, pathname, key, BOOTSTRAPPING_FILE_NAME);
            logger.error(message);
        }
    }

    private static void logInvalidFile(String key, String pathname) {
        if (bootstrapping != null && !bootstrapping.isEmpty()) {
            String pattern = "\"{0}\" is not a valid file, check property \"{1}\" at file \"{2}\"";
            String message = MessageFormat.format(pattern, pathname, key, BOOTSTRAPPING_FILE_NAME);
            logger.error(message);
        }
    }

    public static Charset getPreferredFileEncoding() {
        return preferred_file_encoding;
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
