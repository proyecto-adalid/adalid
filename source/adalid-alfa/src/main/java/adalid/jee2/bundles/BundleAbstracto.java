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

import adalid.commons.bundles.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class BundleAbstracto {

    private static final Logger logger = Logger.getLogger(BundleAbstracto.class);

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String TEMP_DIR = USER_DIR + FILE_SEP + "resources" + FILE_SEP + "velocity" + FILE_SEP + "templates" + FILE_SEP + "zymurgy";

    abstract Locale[] getSupportedLocales();

    abstract Map<Locale, ResourceBundle> bundles();

    abstract Map<Locale, Set<String>> bundleKeys();

    abstract Map<Locale, List<String>> bundleRows();

    abstract Map<Locale, Locale> locales();

    abstract Set<String> warnings();

    abstract Set<String> errors();

    private final String baseName;

    BundleAbstracto(boolean load) {
        baseName = getClass().getCanonicalName();
        if (load) {
            load();
        }
    }

    @SuppressWarnings("deprecation")
    private void load() {
        logger.trace(baseName);
        ResourceBundle bundle, bundlePlus;
        Locale localePlus;
        Locale[] supportedLocales = getSupportedLocales();
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
                    locales().put(locale, localePlus);
                }
                putBundleKeys(locale);
                putBundleRows(locale);
            }
        }
        int n = supportedLocales.length;
        if (n > 1) {
            Locale loc1, loc2;
            Set<String> set1, set2;
            for (int i = 0; i < n; i++) {
                loc1 = supportedLocales[i];
                set1 = bundleKeys().get(loc1);
                for (int j = i + 1; j < n; j++) {
                    loc2 = supportedLocales[j];
                    set2 = bundleKeys().get(loc2);
                    if (!set2.equals(set1)) {
                        warn("the keySet of " + baseName + "." + loc2 + " is not equal to the keySet of " + baseName + "." + loc1);
                    }
                }
            }
        }
    }

    private ResourceBundle putBundle(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
            bundles().put(locale, bundle);
            return bundle;
        } catch (Exception ex) {
            error(ex.getClass().getSimpleName() + "@" + baseName + "." + locale);
            error(ThrowableUtils.getString(ex));
        }
        return null;
    }

    private void warn(String message) {
//      logger.warn(message);
        warnings().add(message);
    }

    private void error(String message) {
//      logger.error(message);
        errors().add(message);
    }

    private void putBundleKeys(Locale locale) {
        Set<String> keys = new LinkedHashSet<>();
        bundleKeys().put(locale, keys);
        addLocaleKeys(keys, locale);
        Locale localePlus = getLocalePlus(locale);
        if (localePlus != null) {
            addLocaleKeys(keys, localePlus);
        }
//      logger.info("bundle " + baseName + "." + locale + " loaded (" + keys.size() + " keys)");
    }

    private void addLocaleKeys(Set<String> keys, Locale locale) {
        Set<String> resourceBundleKeys = PropertiesHandler.getResourceBundleKeys(baseName, locale);
        if (resourceBundleKeys != null) {
            keys.addAll(resourceBundleKeys);
        }
    }

    private void putBundleRows(Locale locale) {
        List<String> rows = new ArrayList<>();
        bundleRows().put(locale, rows);
        addLocaleRows(rows, locale);
        Locale localePlus = getLocalePlus(locale);
        if (localePlus != null) {
            addLocaleRows(rows, localePlus);
        }
//      logger.info("bundle " + baseName + "." + locale + " loaded (" + rows.size() + " rows)");
    }

    private void addLocaleRows(List<String> rows, Locale locale) {
        List<String> resourceBundleRows = PropertiesHandler.getResourceBundleRows(baseName, locale);
        if (resourceBundleRows != null) {
            rows.addAll(resourceBundleRows);
        }
    }

    public String getBaseName() {
        return baseName;
    }

    public Set<String> getKeys() {
        return getKeys(Bundle.getLocale());
    }

    public Set<String> getKeys(Locale locale) {
        ResourceBundle bundle = getBundle(locale);
        return bundle == null ? null : bundleKeys().get(locale); // bundle.keySet() is unordered!
    }

    public List<String> getRows() {
        return getRows(Bundle.getLocale());
    }

    public List<String> getRows(Locale locale) {
        ResourceBundle bundle = getBundle(locale);
        return bundle == null ? null : bundleRows().get(locale);
    }

    public File getRowsTemplate() {
        return getRowsTemplate(Bundle.getLocale());
    }

    public File getRowsTemplate(Locale locale) {
        File dir = new File(TEMP_DIR);
        File template = new File(dir, StrUtils.getRandomString());
        List<String> rows = getRows(locale);
        try {
            FileUtils.forceMkdir(dir);
            FileUtils.writeLines(template, rows);
            return template;
        } catch (IOException ex) {
            return null;
        }
    }

    public String getTemplatePath(File template) {
        if (template == null) {
            return null;
        }
        String path = template.getPath();
        String relativePath = StringUtils.substringAfter(path, FILE_SEP + "velocity" + FILE_SEP);
        return relativePath.replace(FILE_SEP, "/");
    }

    public boolean deleteQuietly(File template) {
        return template != null && FileUtils.deleteQuietly(template);
    }

    public String getString(String key) {
        return getString(key, Bundle.getLocale());
    }

    public String getString(String key, Locale locale) {
        if (key == null || locale == null) {
            return StringUtils.EMPTY;
        }
        String string = getString(key, getBundle(getLocalePlus(locale)));
        return string.isEmpty() ? getString(key, getBundle(locale)) : string;
    }

    private String getString(String key, ResourceBundle bundle) {
        if (bundle != null) {
            try {
                String string = bundle.getString(key);
                return StringUtils.trimToEmpty(string);
            } catch (Exception ex) {
            }
        }
        return StringUtils.EMPTY;
    }

    private ResourceBundle getBundle(Locale locale) {
        return locale == null ? null : bundles().get(locale);
    }

    private Locale getLocalePlus(Locale locale) {
        return locale == null ? null : locales().get(locale);
    }

    public Set<String> getWarnings() {
        return warnings();
    }

    public Set<String> getErrors() {
        return errors();
    }

}
