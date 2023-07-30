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
package adalid.commons.util;

import adalid.commons.bundles.Bundle;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class CSVUtils {

    private static final String BUNDLE_KEY_PREFIX = CSVUtils.class.getName() + ".";

    private static final Map<Locale, String> _separatorMap = new LinkedHashMap<>();

    public static String getSeparator() {
        Locale locale = Bundle.getLocale();
        if (_separatorMap.containsKey(locale)) {
            return _separatorMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "separator.char");
        return StringUtils.trimToEmpty(string);
    }

    public static void setSeparator(Locale locale, char separator) {
        if (locale != null) {
            if (separator == ' ') {
                _separatorMap.remove(locale);
            } else {
                _separatorMap.put(locale, "" + separator);
            }
        }
    }

    private static final Map<Locale, String> _quoteMap = new LinkedHashMap<>();

    public static String getQuote() {
        Locale locale = Bundle.getLocale();
        if (_quoteMap.containsKey(locale)) {
            return _quoteMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "quote.char");
        return StringUtils.trimToEmpty(string);
    }

    public static void setQuote(Locale locale, char quote) {
        if (locale != null) {
            if (quote == ' ') {
                _quoteMap.remove(locale);
            } else {
                _quoteMap.put(locale, "" + quote);
            }
        }
    }

    private static final Map<Locale, String> _escapeMap = new LinkedHashMap<>();

    public static String getEscape() {
        Locale locale = Bundle.getLocale();
        if (_escapeMap.containsKey(locale)) {
            return _escapeMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "escape.char");
        return StringUtils.trimToEmpty(string);
    }

    public static void setEscape(Locale locale, char escape) {
        if (locale != null) {
            if (escape == ' ') {
                _escapeMap.remove(locale);
            } else {
                _escapeMap.put(locale, "" + escape);
            }
        }
    }

    public static String getDecimalSeparator() {
        return NumUtils.getDecimalSeparator();
    }

    private static final Map<Locale, String> _lineEndMap = new LinkedHashMap<>();

    public static String getLineEnd() {
        Locale locale = Bundle.getLocale();
        if (_lineEndMap.containsKey(locale)) {
            return _lineEndMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "line.end.string");
        return string == null || string.length() == 0 ? "" : string; // cannot be trimmed
    }

    public static void setLineEnd(Locale locale, String lineEnd) {
        if (locale != null) {
            if (StringUtils.isBlank(lineEnd)) {
                _lineEndMap.remove(locale);
            } else {
                _lineEndMap.put(locale, lineEnd);
            }
        }
    }

    private static final Map<Locale, String> _nullValueMap = new LinkedHashMap<>();

    public static String getNullValue() {
        Locale locale = Bundle.getLocale();
        if (_nullValueMap.containsKey(locale)) {
            return _nullValueMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "null.value.string");
        return StringUtils.trimToEmpty(string);
    }

    public static void setNullValue(Locale locale, String nullValue) {
        if (locale != null) {
            if (StringUtils.isBlank(nullValue)) {
                _nullValueMap.remove(locale);
            } else {
                _nullValueMap.put(locale, nullValue);
            }
        }
    }

}
