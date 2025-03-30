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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class Dictionary {

    static final Logger logger = Logger.getLogger(Dictionary.class);

    static final String USER_DIR = System.getProperty("user.dir");

    static final String FILE_SEP = System.getProperty("file.separator");

    static final String PROPERTIES_SUFFIX = ".properties";

    static final String DEFAULT_DICTIONARY_DIR = FilUtils.getUserDirPath("src", "main", "resources", "dictionary");

    private static int errorCount, warningCount;

    private static Level _infoLevel = Level.OFF;

    private static boolean _fairInfoLevel;

    public static Level getInfoLevel() {
        return _infoLevel;
    }

    public static void setInfoLevel(Level level) {
        _infoLevel = LogUtils.check(level, Level.OFF, Level.INFO);
        _fairInfoLevel = LogUtils.fair(_infoLevel);
    }

    public static boolean isValidNumericCode(String value) {
        return LongUtils.valueOf(value, 0L) != 0L;
    }

    public static void printSummary() {
        if (warningCount > 0) {
            logger.warn("warnings=" + warningCount);
        }
        if (errorCount > 0) {
            logger.warn("errors=" + errorCount);
        }
    }

    public static int getErrorCount() {
        return errorCount;
    }

    public static int getWarningCount() {
        return warningCount;
    }

    public static void reset() {
        errorCount = 0;
        warningCount = 0;
    }

    private final String simpleName, fileName, filePath, lineSeparator;

    private final boolean scrutinize;

    private final Map<String, String> map = new TreeMap<>();

    private final Properties properties;

    private final Set<String> errors = new TreeSet<>();

    private final Set<String> warnings = new TreeSet<>();

    Dictionary(Class<?> clazz, String folder, String subfolder, String separator, boolean check) {
        simpleName = clazz.getSimpleName();
        fileName = simpleName + PROPERTIES_SUFFIX;
        filePath = folder(folder) + subfolder(subfolder) + fileName;
        lineSeparator = StringUtils.isBlank(separator) ? System.lineSeparator() : separator;
        scrutinize = check;
        properties = loadProperties();
        log(true);
        check();
    }

    private String folder(String folder) {
        return StringUtils.isBlank(folder) ? DEFAULT_DICTIONARY_DIR : folder.trim();
    }

    private String subfolder(String subfolder) {
        return StringUtils.isBlank(subfolder) ? FILE_SEP : FILE_SEP + subfolder.trim() + FILE_SEP;
    }

    private Properties loadProperties() {
        Properties sortedProperties = new SortedProperties();
        File file = new File(filePath);
        if (file.isFile()) {
            try {
                try (InputStream inStream = new FileInputStream(file.getPath())) {
                    sortedProperties.load(inStream);
                }
            } catch (IOException ex) {
                logger.error(ThrowableUtils.getString(ex), ex);
            } catch (Exception ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        }
        return sortedProperties;
    }

    private void check() {
        if (scrutinize) {
            String value;
            for (Object key : properties.keySet()) {
                value = properties.getProperty("" + key);
                if (!isValidNumericCode(value)) {
                    if (StringUtils.isBlank(value)) {
                        warn("property " + simpleName + " [" + key + "] value is missing");
                    } else {
                        warn("\"" + value + "\" is an invalid numeric code; property " + simpleName + " [" + key + "] value will be replaced");
                    }
                }
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Object setProperty(String key, String value) {
        Object previousValue = properties.setProperty(key, value);
        if (scrutinize) {
            if (previousValue != null && !previousValue.equals(value)) {
                if (StringUtils.isBlank("" + previousValue)) {
                    warn("assigning value to property " + simpleName + " [" + key + "]");
                } else {
                    warn("replacing value of property " + simpleName + " [" + key + "]");
                }
            }
        }
        putProperty(key, value);
        return previousValue;
    }

    public String putProperty(String key, String value) {
        String previousKey = map.put(value, key);
        if (scrutinize) {
            if (previousKey != null && !previousKey.equals(key)) {
                int c = key.compareTo(previousKey);
                String k1 = c < 0 ? key : previousKey;
                String k2 = c < 0 ? previousKey : key;
                error("\"" + value + "\" is the same numeric code for properties " + simpleName + " [" + k1 + "] and [" + k2 + "]");
            }
        }
        return previousKey;
    }

    public void store() {
        PropertiesHandler.storeProperties(properties, filePath, null, lineSeparator);
        log(false);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    private void log(boolean initializing) {
        String path = StringUtils.substringAfter(filePath, USER_DIR).replace(FILE_SEP, "/");
        if (properties.isEmpty()) {
            if (initializing) {
                info("initializing " + path);
            } else {
                warn("file " + path + " is empty");
            }
        } else {
            info("file " + path + " has " + properties.size() + " properties");
        }
    }

    private void info(String message) {
        if (_fairInfoLevel) {
            logger.log(_infoLevel, message);
        }
    }

    private void warn(String message) {
        if (warnings.add(message)) {
            warningCount++;
            logger.warn(message);
        }
    }

    private void error(String message) {
        if (errors.add(message)) {
            errorCount++;
            logger.error(message);
        }
    }

}
