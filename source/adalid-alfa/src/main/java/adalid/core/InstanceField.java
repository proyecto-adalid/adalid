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
package adalid.core;

import adalid.commons.bundles.Bundle;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class InstanceField extends AbstractArtifact {

    private static final String EOL = "\n";

    private final Property _property;

    private Object _value;

    /**
     * @return the primitive property
     */
    public Property getProperty() {
        return _property;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        if (_property instanceof StringProperty) {
            String localizedValue = getLocalizedValue(null);
            if (localizedValue != null) {
                return localizedValue;
            }
        }
        return _value;
    }

    InstanceField(Instance instance, PersistentEntityReference property, Instance value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, BigDecimal value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, BigInteger value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Boolean value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Byte value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Character value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Date value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Double value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Float value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Integer value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Long value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Short value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, String value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, StringProperty property, String value, Locale locale) {
        super();
        _property = property;
        setLocalizedValue(locale, value);
        init(instance);
    }

    InstanceField(Instance instance, Property property, Time value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    InstanceField(Instance instance, Property property, Timestamp value) {
        super();
        _property = property;
        _value = value;
        init(instance);
    }

    private final Map<Locale, String> _localizedValue = new LinkedHashMap<>();

    /**
     * @param locale the locale for the value
     * @return the localized value
     */
    public String getLocalizedValue(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedValue.get(l);
    }

    /**
     * @param locale the locale for the value
     * @param value the localized value
     */
    private void setLocalizedValue(Locale locale, String value) {
        Locale l = localeWritingKey(locale);
        if (value == null) {
            _localizedValue.remove(l);
        } else {
            _localizedValue.put(l, value);
            if (_value == null && l.equals(Bundle.getLocale())) {
                _value = value;
            }
        }
    }

    void addLocalizedValue(Locale locale, String value) {
        setLocalizedValue(locale, value);
    }

    private void init(Instance instance) {
        initDeclaringArtifact(instance);
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String property = _property.getName();
        String value = _value + "";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                string += fee + tab + "property" + faa + property + foo;
                string += fee + tab + "value" + faa + value + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
