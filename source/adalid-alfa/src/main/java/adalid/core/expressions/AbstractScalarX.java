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
package adalid.core.expressions;

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class AbstractScalarX extends AbstractExpression implements ScalarX {

    private static final String EOL = "\n";

    protected AbstractScalarX(ScalarOp operator, Object operand) {
        super();
        _operator = operator;
        _operand = operand;
        initDataType();
    }

    private final ScalarOp _operator;

    private final Object _operand;

    /**
     * @return the operator
     */
    @Override
    public ScalarOp getOperator() {
        return _operator;
    }

    /**
     * @return the operands
     */
    @Override
    public Object[] getOperands() {
        return _operand == null ? new Object[]{} : new Object[]{_operand};
    }

    /**
     * @return the operand
     */
    @Override
    public Object getOperand() {
        return _operand;
    }

    //<editor-fold defaultstate="collapsed" desc="unwrap">
    @Override
    public Expression unwrapBooleanExpression() {
        if (_operator != null && _operand instanceof BooleanExpression) {
            switch (_operator) {
                case TO_BOOLEAN -> {
                    return (BooleanExpression) _operand;
                }
            }
        }
        return this;
    }

    @Override
    public Expression unwrapCharacterExpression() {
        if (_operator != null && _operand instanceof CharacterExpression) {
            switch (_operator) {
                case TO_CHARACTER, TO_STRING -> {
                    return (CharacterExpression) _operand;
                }
            }
        }
        return this;
    }

    @Override
    public Expression unwrapNumericExpression() {
        if (_operator != null && _operand instanceof NumericExpression) {
            switch (_operator) {
                case TO_BYTE, TO_SHORT, TO_INTEGER, TO_LONG, TO_FLOAT, TO_DOUBLE, TO_BIG_INTEGER, TO_BIG_DECIMAL -> {
                    return (NumericExpression) _operand;
                }
            }
        }
        return this;
    }

    @Override
    public Expression unwrapTemporalExpression() {
        if (_operator != null && _operand instanceof TemporalExpression) {
            switch (_operator) {
                case TO_DATE, TO_TIME, TO_TIMESTAMP -> {
                    return (TemporalExpression) _operand;
                }
            }
        }
        return this;
    }

    @Override
    public Expression unwrapValueExpression() {
        if (_operator != null) {
            if (_operand instanceof BooleanExpression) {
                return unwrapBooleanExpression();
            }
            if (_operand instanceof CharacterExpression) {
                return unwrapCharacterExpression();
            }
            if (_operand instanceof NumericExpression) {
                return unwrapNumericExpression();
            }
            if (_operand instanceof TemporalExpression) {
                return unwrapTemporalExpression();
            }
        }
        return this;
    }
    //</editor-fold>

    protected static ScalarX coalesceDataType(ScalarX... scalarX) {
        if (scalarX != null) {
            for (ScalarX sx : scalarX) {
                if (sx != null && sx.getDataType() != null) {
                    return sx;
                }
            }
        }
        return null;
    }

    private void initDataType() {
        if (_operator == null) {
            copyDataType(_operand);
        } else {
            switch (_operator) {
                case SELF, DEFAULT_WHEN_NULL, NULL_WHEN_DEFAULT ->
                    copyDataType(_operand);
                case NOT ->
                    setDataType(Boolean.class);
                case ASCII, DIACRITICLESS, DIACRITICLESS_ASCII, LOWER, UPPER, CAPITALIZE, UNCAPITALIZE, TRIM, LTRIM, RTRIM ->
                    setDataType(String.class);
                case MODULUS, OPPOSITE ->
                    copyDataType(_operand);
                case RECIPROCAL ->
                    setDataType(BigDecimal.class);
                case YEAR, MONTH, DAY, HOUR, MINUTE, SECOND ->
                    setDataType(Integer.class);
                case FIRST_DATE_OF_MONTH, FIRST_DATE_OF_QUARTER, FIRST_DATE_OF_SEMESTER, FIRST_DATE_OF_YEAR, LAST_DATE_OF_MONTH, LAST_DATE_OF_QUARTER, LAST_DATE_OF_SEMESTER, LAST_DATE_OF_YEAR ->
                    setDataType(Date.class);
                case TO_BOOLEAN ->
                    setDataType(Boolean.class);
                case TO_CHARACTER ->
                    setDataType(Character.class);
                case TO_STRING, TO_LOCALE_STRING ->
                    setDataType(String.class);
                case TO_BYTE ->
                    setDataType(Byte.class);
                case TO_SHORT ->
                    setDataType(Short.class);
                case TO_INTEGER ->
                    setDataType(Integer.class);
                case TO_LONG ->
                    setDataType(Long.class);
                case TO_FLOAT ->
                    setDataType(Float.class);
                case TO_DOUBLE ->
                    setDataType(Double.class);
                case TO_BIG_INTEGER ->
                    setDataType(BigInteger.class);
                case TO_BIG_DECIMAL ->
                    setDataType(BigDecimal.class);
                case TO_DATE ->
                    setDataType(Date.class);
                case TO_TIME ->
                    setDataType(Time.class);
                case TO_TIMESTAMP ->
                    setDataType(Timestamp.class);
                default ->
                    copyDataType(_operand);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        String nombre = _operand instanceof NamedValue ? ((NamedValue) _operand).name() : null;
        return nombre != null && (_operator == null || _operator.equals(ScalarOp.SELF)) ? nombre : super.toString();
    }

    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                if (_operator != null) {
                    string += fee + tab + "operator" + faa + _operator + foo;
                }
                if (_operand instanceof NaryExpression) {
                    Expression valor = (Expression) _operand;
                    string += valor.toString(n + 1, "operand");
                } else {
                    string += fee + tab + "operand" + faa + getValueString(_operand) + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
