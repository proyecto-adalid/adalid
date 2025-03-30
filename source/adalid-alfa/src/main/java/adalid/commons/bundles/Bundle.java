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
package adalid.commons.bundles;

import adalid.commons.i18n.*;
import adalid.commons.util.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Bundle {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    public static final Locale ENGLISH = Locale.ENGLISH;

    public static final Locale SPANISH = Locale.forLanguageTag("es");

    public static final Locale PORTUGUESE = Locale.forLanguageTag("pt");

    private static final Locale[] SUPPORTED_LOCALES = new Locale[]{
        ENGLISH,
        SPANISH
    };

    private static final Linguist[] SUPPORTING_LINGUISTS = new Linguist[]{
        new EnglishLinguist(),
        new SpanishLinguist()
    };

    private static final Logger logger = Logger.getLogger(Bundle.class);

    private static final String BASE_NAME = Bundle.class.getName();

    private static final ResourceBundle[] RESOURCE_BUNDLES = new ResourceBundle[]{
        ResourceBundle.getBundle(BASE_NAME, ENGLISH),
        ResourceBundle.getBundle(BASE_NAME, SPANISH)
    };

    private static final Locale defaultLocale = SUPPORTED_LOCALES[0];

    private static final Linguist defaultLinguist = SUPPORTING_LINGUISTS[0];

    private static final ResourceBundle defaultResourceBundle = RESOURCE_BUNDLES[0];

    private static final ResourceBundle specialResourceBundle = ResourceBundle.getBundle(BASE_NAME, Locale.forLanguageTag("adalid"));

    private static Locale locale;

    private static Linguist linguist;

    private static ResourceBundle resourceBundle;
    // </editor-fold>

    static {
        initialise();
    }

    private static void initialise() {
        locale = defaultLocale;
        linguist = defaultLinguist;
        resourceBundle = defaultResourceBundle;
    }

    public static Locale[] getSupportedLocales() {
        return SUPPORTED_LOCALES;
    }

    public static boolean isSupportedLocale(Locale locale) {
        return locale != null && ArrayUtils.contains(SUPPORTED_LOCALES, locale);
    }

    private static int indexOfLocale(Locale locale) {
        return locale == null ? ArrayUtils.INDEX_NOT_FOUND : ArrayUtils.indexOf(SUPPORTED_LOCALES, locale);
    }

    /**
     * @return the locale
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * @param newLocale the locale to set
     */
    public static void setLocale(Locale newLocale) {
        if (newLocale == null) {
            logger.warn("null value for locale parameter; falling back to " + defaultLocale);
            initialise();
        } else if (newLocale.equals(locale)) {
            logger.trace("Locale " + newLocale + " already set.");
        } else if (isSupportedLocale(newLocale)) {
            try {
                logger.trace("Change of Locale (from \"" + locale + "\" to \"" + newLocale + "\")");
                locale = newLocale;
                linguist = getLinguist(newLocale);
                resourceBundle = getResourceBundle(newLocale);
            } catch (MissingResourceException e) {
                logger.warn("Locale " + newLocale + " bundle missing; falling back to " + defaultLocale);
                initialise();
            }
        } else {
            logger.warn("Locale " + newLocale + " not supported yet; falling back to " + defaultLocale);
            initialise();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="disabled methods">
    /*
    private static Locale getLocale(ResourceBundle rb) {
        try {
            String tag = rb.getString("locale.tag");
            return Locale.forLanguageTag(tag);
        } catch (MissingResourceException e) {
            return Locale.getDefault();
        }
    }

    private static Linguist getLinguist(ResourceBundle rb) {
        try {
            String name = rb.getString(Linguist.class.getName());
            return name == null ? null : getLinguist(name);
        } catch (ClassCastException | MissingResourceException e) {
            return null;
        }
    }

    private static Linguist getLinguist(String name) {
        try {
            Class<?> clazz = Class.forName(name);
            return getLinguist(clazz);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static Linguist getLinguist(Class<?> clazz) {
        try {
            return (Linguist) clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            return null;
        }
    }
    **/
    // </editor-fold>
//
    /**
     * @return the linguist
     */
    public static Linguist getLinguist() {
        return linguist;
    }

    public static Linguist getLinguist(Locale locale) {
        int index = indexOfLocale(locale);
        return index == ArrayUtils.INDEX_NOT_FOUND ? linguist : SUPPORTING_LINGUISTS[index];
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static ResourceBundle getResourceBundle(Locale locale) {
        int index = indexOfLocale(locale);
        return index == ArrayUtils.INDEX_NOT_FOUND ? resourceBundle : RESOURCE_BUNDLES[index];
    }

    public static boolean getBoolean(String key) {
        String trimmed = getTrimmedToNullString(key);
        return BitUtils.valueOf(trimmed);
    }

    public static String getString(String key) {
        String trimmed = getTrimmedToNullString(key);
        return trimmed == null ? key : trimmed;
    }

    public static String getTrimmedToEmptyString(String key) {
        String trimmed = getTrimmedToNullString(key);
        return trimmed == null ? "" : trimmed;
    }

    public static String getTrimmedToNullString(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String string = getTrimmedToNullString(key, resourceBundle);
        if (string == null) {
            string = getTrimmedToNullString(key, specialResourceBundle);
        }
        return string;
    }

    public static String getTrimmedToNullString(String key, Locale locale) {
        // <editor-fold defaultstate="collapsed" desc="until 15/03/2023">
        /*
        if (StringUtils.isBlank(key)) {
            return null;
        }
        int index = indexOfLocale(locale);
        if (index == ArrayUtils.INDEX_NOT_FOUND) {
            return null;
        }
        String string = getTrimmedToNullString(key, RESOURCE_BUNDLES[index]);
        return string;
        /**/
        // </editor-fold>
        return StringUtils.isBlank(key) ? null : getTrimmedToNullString(key, getResourceBundle(locale));
    }

    private static String getTrimmedToNullString(String key, ResourceBundle rb) {
        String string = getUntrimmedString(key, rb);
        return StringUtils.trimToNull(string);
    }

    public static String getUntrimmedString(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String string = getUntrimmedString(key, resourceBundle);
        if (string == null) {
            string = getUntrimmedString(key, specialResourceBundle);
        }
        return string;
    }

    public static String getUntrimmedString(String key, Locale locale) {
        // <editor-fold defaultstate="collapsed" desc="until 15/03/2023">
        /*
        if (StringUtils.isBlank(key)) {
            return null;
        }
        int index = indexOfLocale(locale);
        if (index == ArrayUtils.INDEX_NOT_FOUND) {
            return null;
        }
        String string = getUntrimmedString(key, RESOURCE_BUNDLES[index]);
        return string;
        /**/
        // </editor-fold>
        return StringUtils.isBlank(key) ? null : getUntrimmedString(key, getResourceBundle(locale));
    }

    private static String getUntrimmedString(String key, ResourceBundle rb) {
        try {
            String string = rb.getString(key);
            return string == null || string.isEmpty() ? null : string;
        } catch (MissingResourceException e) {
            return null;
        }
    }

}
