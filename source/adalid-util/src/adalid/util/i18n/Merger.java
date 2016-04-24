/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.i18n;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.properties.SortedProperties;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Merger {

    private static final Logger logger = Logger.getLogger(Merger.class);

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");

    private static final String TEST_DIR = USER_DIR + FILE_SEPARATOR + "test" + FILE_SEPARATOR + "i18n" + FILE_SEPARATOR;

    private static final String LOCALE_LANGUAGE_TAG = "locale.language.tag";

    private static final String NEW_VALUE = "[+]";

    private static final String OLD_VALUE = "[-]";

    private static final String TRAN_REQD = "<?>";

    public static void merge(String project) {
        logger.info(project);
        Mapper mapper = Mapper.map(project);
//      mapper.info();
        String name, locale, tag, filename;
        List<File> list;
        File baseBundle;
        Properties baseProperties, localeProperties, mergedProperties;
        Map<String, List<File>> map = mapper.getMap();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            logger.info(key);
            list = map.get(key);
            baseBundle = null;
            for (File bundle : list) {
                name = bundle.getName();
                locale = locale(name);
                if (locale == null) {
                    baseBundle = bundle;
                }
            }
            if (baseBundle == null) {
                continue;
            }
            baseProperties = PropertiesHandler.loadProperties(baseBundle, true);
            tag = baseProperties.getProperty(LOCALE_LANGUAGE_TAG);
            if (tag == null) {
                continue;
            }
            baseProperties.remove(LOCALE_LANGUAGE_TAG);
            logger.debug("\t" + baseBundle.getName() + " " + baseProperties.size());
            for (File bundle : list) {
                name = bundle.getName();
                locale = locale(name);
                if (locale != null) {
                    localeProperties = PropertiesHandler.loadProperties(bundle, true);
                    logger.debug("\t" + bundle.getName() + " " + localeProperties.size());
                    filename = TEST_DIR + locale + FILE_SEPARATOR + key + "_" + locale + ".properties";
                    if (locale.equals(tag)) {
                        mergedProperties = baseProperties;
                    } else {
                        mergedProperties = merge(bundle, baseProperties, localeProperties);
                    }
                    PropertiesHandler.storeProperties(mergedProperties, filename);
                }
            }
        }
    }

    private static Properties merge(File bundle, Properties baseProperties, Properties localeProperties) {
        Properties properties = new SortedProperties();
        Set<String> baseNames = baseProperties.stringPropertyNames();
        Set<String> localeNames = localeProperties.stringPropertyNames();
        String baseValue, localeValue, newValue;
        int newProperties = 0;
        int oldProperties = 0;
        int translationRequired = 0;
        for (String name : baseNames) {
            if (localeNames.contains(name)) {
            } else {
                newProperties++;
            }
            baseValue = baseProperties.getProperty(name);
            localeValue = localeProperties.getProperty(name);
            boolean noTranslationRequired = noTranslationRequired(baseValue);
            if (noTranslationRequired || isNotTranslated(localeValue)) {
                if (noTranslationRequired) {
                    newValue = baseValue;
                } else {
                    newValue = TRAN_REQD + baseValue;
                    translationRequired++;
                }
                properties.setProperty(name, newValue);
            } else {
                properties.setProperty(name, localeValue);
            }
        }
        for (String name : localeNames) {
            if (baseNames.contains(name)) {
            } else {
                oldProperties++;
            }
        }
        if (newProperties > 0) {
            logger.warn(NEW_VALUE + " " + bundle.getName() + " = " + newProperties);
        }
        if (oldProperties > 0) {
            logger.warn(OLD_VALUE + " " + bundle.getName() + " = " + oldProperties);
        }
        if (translationRequired > 0) {
            logger.warn(TRAN_REQD + " " + bundle.getName() + " = " + translationRequired);
        }
        return properties;
    }

    private static boolean noTranslationRequired(String string) {
        return string.isEmpty() || isMessageKey(string);
    }

    private static boolean isNotTranslated(String string) {
        return string == null || string.isEmpty() || isMessageKey(string) || string.startsWith(TRAN_REQD);
    }

    private static boolean isMessageKey(String string) {
        return string.startsWith("<") && string.endsWith(">");
    }

    private static String locale(String name) {
        String substringBeforeLast = StringUtils.substringBeforeLast(name, ".");
        String substringAfter = StringUtils.substringAfter(substringBeforeLast, "_");
        return StringUtils.trimToNull(substringAfter);
    }

}
