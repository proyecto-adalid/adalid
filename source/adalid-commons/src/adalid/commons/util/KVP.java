/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class KVP { // KeyValuePair

    public static final String EQUALS = "=";

    public static final String SEPARATOR = ", ";

    public static final String OPEN = "(";

    public static final String CLOSE = ")";

    public static KVP join(String key, Object value) {
        return new KVP(key, value);
    }

    public static KVP join(String key, Object... value) {
        return new KVP(key, value);
    }

    private static final String EMPTY = "";

    private final String _key;

    private final Object _value;

    private String _equals, _separator, _open, _close;

    public KVP(String key, Object value) {
        _key = key;
        _value = value;
        _equals = EQUALS;
        _separator = SEPARATOR;
        _open = OPEN;
        _close = CLOSE;
    }

    public KVP delimitedBy(String equals, String separator, String open, String close) {
        _equals = equals == null ? EQUALS : equals;
        _separator = separator == null ? SEPARATOR : separator;
        _open = open == null ? OPEN : open;
        _close = close == null ? CLOSE : close;
        return this;
    }

    public String getKey() {
        return _key;
    }

    public Object getValue() {
        return _value;
    }

    public String getStringKey() {
        return StringUtils.trimToEmpty(_key);
    }

    public String getStringValue() {
        return getStringValue(_equals, _separator, _open, _close);
    }

    public String getStringValue(String equals, String separator, String open, String close) {
        return StrUtils.getString(equals, separator, open, close, _value);
    }

    @Override
    public String toString() {
        return toString(_equals, _separator, _open, _close);
    }

    public String toString(String equals, String separator, String open, String close) {
        if (StringUtils.isBlank(_key) && ObjUtils.isBlank(_value)) {
            return EMPTY;
        }
        if (ObjUtils.isBlank(_value)) {
            return getStringKey();
        }
        if (StringUtils.isBlank(_key)) {
            return getStringValue(equals, separator, open, close);
        }
        return getStringKey() + equals + getStringValue(equals, separator, open, close);
    }

}
