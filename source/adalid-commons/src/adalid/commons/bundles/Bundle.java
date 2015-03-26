/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.bundles;

import adalid.commons.i18n.Linguist;
import adalid.commons.util.BitUtils;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class Bundle {

    private static final String BASE_NAME = Bundle.class.getName();

    private static final ResourceBundle defaultResourceBundle = ResourceBundle.getBundle(BASE_NAME);

    private static final ResourceBundle specialResourceBundle = ResourceBundle.getBundle(BASE_NAME, Locale.forLanguageTag("adalid"));

    private static ResourceBundle resourceBundle = defaultResourceBundle;

    private static Locale locale = getLocale(defaultResourceBundle);

    private static Linguist linguist = getLinguist(defaultResourceBundle);

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    private static Locale getLocale(ResourceBundle rb) {
        try {
            String tag = rb.getString("locale.tag");
            return Locale.forLanguageTag(tag);
        } catch (MissingResourceException e) {
            return Locale.getDefault();
        }
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
        ResourceBundle prevResourceBundle = resourceBundle;
        if (newLocale == null) {
            locale = Locale.getDefault();
            resourceBundle = defaultResourceBundle;
        } else if (newLocale.equals(locale)) {
        } else {
            locale = newLocale;
            try {
                resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
            } catch (MissingResourceException e) {
                resourceBundle = defaultResourceBundle;
            }
        }
        if (resourceBundle.equals(prevResourceBundle)) {
        } else {
            linguist = getLinguist(resourceBundle);
        }
    }

    private static Linguist getLinguist(ResourceBundle rb) {
        try {
            String name = rb.getString(Linguist.class.getName());
            Class<?> clazz = Class.forName(name);
            return (Linguist) clazz.newInstance();
        } catch (MissingResourceException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * @return the linguist
     */
    public static Linguist getLinguist() {
        return linguist;
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

    private static String getTrimmedToNullString(String key, ResourceBundle rb) {
        try {
            String string = rb.getString(key);
            return StringUtils.trimToNull(string);
        } catch (MissingResourceException e) {
            return null;
        }
    }

}
