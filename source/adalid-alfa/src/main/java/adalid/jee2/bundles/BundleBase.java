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
package adalid.jee2.bundles;

import adalid.commons.bundles.Bundle;
import adalid.commons.util.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class BundleBase {

    private static final Logger logger = Logger.getLogger(BundleBase.class);

    private static final String NBSP = "\u00A0"; // a space in a line that cannot be broken by word wrap

    private static final String BASE_NAME = BundleBase.class.getName();

    private static final Locale[] supported_locales = Bundle.getSupportedLocales();

    private static final Map<Locale, Locale> locales = new LinkedHashMap<>();

    private static final Map<Locale, ResourceBundle> bundles = new LinkedHashMap<>();

    static {
        load();
    }

    @SuppressWarnings("deprecation")
    private static void load() {
        logger.trace(BASE_NAME);
        ResourceBundle bundle, bundlePlus;
        Locale localePlus;
        Locale[] supportedLocales = supported_locales;
        for (Locale locale : supportedLocales) {
            bundle = putBundle(locale);
            if (bundle != null) {
                /**/
                localePlus = new Locale(locale.getLanguage(), locale.getCountry(), "PLUS");
                /* new Locale is deprecated since JDK 19, but for Locale.Builder a valid variant must be a String of 5 to 8 alphanumerics
                localePlus = new Locale.Builder().setLanguage(locale.getLanguage()).setRegion(locale.getCountry()).setVariant("PLUS").build();
                /**/
                bundlePlus = putBundle(localePlus);
                if (bundlePlus != null) {
                    locales.put(locale, localePlus);
                }
            }
        }
    }

    private static ResourceBundle putBundle(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);
            bundles.put(locale, bundle);
            return bundle;
        } catch (Exception ex) {
            error(ex.getClass().getSimpleName() + "@" + BASE_NAME + "." + locale);
            error(ThrowableUtils.getString(ex));
        }
        return null;
    }

    private static void error(String message) {
        logger.error(message);
    }

    private static Locale getLocalePlus(Locale locale) {
        return locale == null ? null : locales.get(locale);
    }

    private static ResourceBundle getBundle(Locale locale) {
        return locale == null ? null : bundles.get(locale);
    }

    public static String getString(String key) {
        return getString(key, Bundle.getLocale());
    }

    public static String getString(String key, Locale locale) {
        if (StringUtils.isNotBlank(key) && Bundle.isSupportedLocale(locale)) {
            String string;
            ResourceBundle bundle;
            Locale[] localities = {getLocalePlus(locale), locale};
            for (Locale locality : localities) {
                if (locality != null) {
                    bundle = getBundle(locality);
                    if (bundle != null) {
                        string = getString(key, bundle);
                        if (string != null) {
                            if (string.endsWith("\b")) {
                                string += NBSP;
                            }
                            return StringUtils.trimToNull(string);
                        }
                    }
                }
            }
        }
        return null;
    }

    private static String getString(String key, ResourceBundle bundle) {
        try {
            return bundle.getString(key);
        } catch (Exception ex) {
            return null;
        }
    }

}
