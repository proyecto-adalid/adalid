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

    public static KVP[] array(Object o) {
        return o == null ? null
            : o instanceof KVP ? new KVP[]{(KVP) o}
            : o.getClass().isArray() && o.getClass().getComponentType().equals(KVP.class) ? (KVP[]) o
            : null;
    }

    public static boolean isArray(Object o) {
        return o != null && o.getClass().isArray() && o.getClass().getComponentType().equals(KVP.class);
    }

    private static final String EMPTY = "";

    private final String _key;

    private final Object _value;

    private String _equals, _separator, _open, _close;

    private KVP(String key, Object value) {
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

    public KVP[] toArray() {
        return new KVP[]{this};
    }

    public KVP[] toArray(KVP that) {
        return that != null ? new KVP[]{this, that} : toArray();
    }

    public KVP[] toArray(Object that) {
        return that instanceof KVP ? new KVP[]{this, (KVP) that} : toArray();
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
