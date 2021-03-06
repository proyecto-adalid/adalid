/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.interfaces.PersistentEntityReference;
import adalid.core.interfaces.Property;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class InstanceField extends AbstractArtifact {

    private static final String EOL = "\n";

    private Property _property;

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
        return _value;
    }

    private InstanceField() {
        super();
    }

    InstanceField(PersistentEntityReference property, Instance value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, BigDecimal value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, BigInteger value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Boolean value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Byte value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Character value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Date value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Double value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Float value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Integer value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Long value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Short value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, String value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Time value) {
        super();
        _property = property;
        _value = value;
    }

    InstanceField(Property property, Timestamp value) {
        super();
        _property = property;
        _value = value;
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
