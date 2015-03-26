/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.core.enums;

import java.sql.Types;

/**
 * @author Jorge Campins
 */
public enum ProcedureDataType {

    BOOLEAN(Types.BOOLEAN),
    CHAR(Types.CHAR),
    VARCHAR(Types.VARCHAR),
    INTEGER(Types.INTEGER),
    BIGINT(Types.BIGINT),
    DECIMAL(Types.DECIMAL),
    NUMERIC(Types.NUMERIC),
    DATE(Types.DATE),
    TIME(Types.TIME),
    TIMESTAMP(Types.TIMESTAMP),
    OTHER(Types.OTHER);

    public static ProcedureDataType valueOf(int i) {
        switch (i) {
            case Types.BOOLEAN:
                return BOOLEAN;
            case Types.CHAR:
                return CHAR;
            case Types.VARCHAR:
                return VARCHAR;
            case Types.INTEGER:
                return INTEGER;
            case Types.BIGINT:
                return BIGINT;
            case Types.DECIMAL:
                return DECIMAL;
            case Types.NUMERIC:
                return NUMERIC;
            case Types.DATE:
                return DATE;
            case Types.TIME:
                return TIME;
            case Types.TIMESTAMP:
                return TIMESTAMP;
            default:
                return OTHER;
        }
    }

    public static ProcedureDataType valueOf(Class<?> clazz) {
        if (clazz == null) {
            return OTHER;
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            return BOOLEAN;
        } else if (Character.class.isAssignableFrom(clazz)) {
            return CHAR;
        } else if (String.class.isAssignableFrom(clazz)) {
            return VARCHAR;
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return INTEGER;
        } else if (Long.class.isAssignableFrom(clazz)) {
            return BIGINT;
        } else if (java.math.BigInteger.class.isAssignableFrom(clazz)) {
            return BIGINT;
        } else if (java.math.BigDecimal.class.isAssignableFrom(clazz)) {
            return DECIMAL;
        } else if (Number.class.isAssignableFrom(clazz)) {
            return NUMERIC;
        } else if (java.sql.Date.class.isAssignableFrom(clazz)) {
            return DATE;
        } else if (java.sql.Time.class.isAssignableFrom(clazz)) {
            return TIME;
        } else if (java.sql.Timestamp.class.isAssignableFrom(clazz)) {
            return TIMESTAMP;
        } else if (java.util.Date.class.isAssignableFrom(clazz)) {
            return TIMESTAMP;
        } else {
            return OTHER;
        }
    }

    private final int value;

    ProcedureDataType(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }

}
