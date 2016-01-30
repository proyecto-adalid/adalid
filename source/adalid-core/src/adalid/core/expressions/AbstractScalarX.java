/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.expressions;

import adalid.core.AbstractExpression;
import adalid.core.enums.ScalarOp;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.NaryExpression;
import adalid.core.interfaces.ScalarX;
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

    static ScalarX coalesceDataType(ScalarX... scalarX) {
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
                case TO_BOOLEAN:
                    setDataType(Boolean.class);
                    break;
                case TO_CHARACTER:
                    setDataType(Character.class);
                    break;
                case TO_STRING:
                    setDataType(String.class);
                    break;
                case TO_BYTE:
                    setDataType(Byte.class);
                    break;
                case TO_SHORT:
                    setDataType(Short.class);
                    break;
                case TO_INTEGER:
                    setDataType(Integer.class);
                    break;
                case TO_LONG:
                    setDataType(Long.class);
                    break;
                case TO_FLOAT:
                    setDataType(Float.class);
                    break;
                case TO_DOUBLE:
                    setDataType(Double.class);
                    break;
                case TO_BIG_INTEGER:
                    setDataType(BigInteger.class);
                    break;
                case TO_BIG_DECIMAL:
                    setDataType(BigDecimal.class);
                    break;
                case TO_DATE:
                    setDataType(Date.class);
                    break;
                case TO_TIME:
                    setDataType(Time.class);
                    break;
                case TO_TIMESTAMP:
                    setDataType(Timestamp.class);
                    break;
                case NOT:
                    setDataType(Boolean.class);
                    break;
                case LOWER:
                case UPPER:
                case CAPITALIZE:
                case UNCAPITALIZE:
                case TRIM:
                case LTRIM:
                case RTRIM:
                    setDataType(String.class);
                    break;
                case DEFAULT_WHEN_NULL:
                case NULL_WHEN_DEFAULT:
                case MODULUS:
                case OPPOSITE:
                    copyDataType(_operand);
                    break;
                case RECIPROCAL:
                    setDataType(BigDecimal.class);
                    break;
                default:
                    copyDataType(_operand);
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
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
