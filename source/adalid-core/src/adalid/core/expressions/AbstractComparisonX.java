/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.expressions;

import adalid.core.AbstractExpression;
import adalid.core.enums.ComparisonOp;
import adalid.core.interfaces.ComparisonX;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.NaryExpression;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class AbstractComparisonX extends AbstractExpression implements ComparisonX {

    private static final String EOL = "\n";

    protected AbstractComparisonX(Object x, ComparisonOp operator) {
        this(x, operator, null);
    }

    protected AbstractComparisonX(Object x, ComparisonOp operator, Object y) {
        super();
        _operator = operator;
//      _x = x instanceof ScalarX ? (ScalarX) x : VariantScalarX.instanceOf(x);
//      _y = y instanceof ScalarX ? (ScalarX) y : VariantScalarX.instanceOf(y);
        _x = x;
        _y = y;
        initDataType();
    }

    // <editor-fold defaultstate="collapsed" desc="instantiators">
//  public static ComparisonX exists(Object x) {
//      return x == null ? null : new ComparisonX(x, ComparisonOp.EXISTS);
//  }
//
//  public static ComparisonX notExists(Object x) {
//      return x == null ? null : new ComparisonX(x, ComparisonOp.NOT_EXISTS);
//  }
//
//  public static ComparisonX in(Object x, Object y) {
//      return x == null || y == null ? null : new ComparisonX(x, ComparisonOp.IN, y);
//  }
//
//  public static ComparisonX between(Object x, Object y) {
//      return x == null || y == null ? null : new ComparisonX(x, ComparisonOp.BETWEEN, y);
//  }
//
//  public static ComparisonX inOrNull(Object x, Object y) {
//      return x == null || y == null ? null : new ComparisonX(x, ComparisonOp.IN_OR_NULL, y);
//  }
//
//  public static ComparisonX betweenOrNull(Object x, Object y) {
//      return x == null || y == null ? null : new ComparisonX(x, ComparisonOp.BETWEEN_OR_NULL, y);
//  }
    // </editor-fold>
//
    private ComparisonOp _operator;

    private Object _x;

    private Object _y;

    /**
     * @return the comparison operator
     */
    @Override
    public ComparisonOp getOperator() {
        return _operator;
    }

    /**
     * @return the operands
     */
    @Override
    public Object[] getOperands() {
        return _y == null ? _x == null ? new Object[]{} : new Object[]{_x} : new Object[]{_x, _y};
    }

    /**
     * @return the x
     */
    @Override
    public Object getX() {
        return _x;
    }

    /**
     * @return the y
     */
    @Override
    public Object getY() {
        return _y;
    }

    private void initDataType() {
        setDataType(Boolean.class);
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        Expression e;
        if (fields || verbose) {
            if (verbose) {
                string += fee + tab + "operator" + faa + _operator + foo;
                if (_x != null) {
                    if (_x instanceof NaryExpression) {
                        e = (Expression) _x;
                        string += e.toString(n + 1, "x", verbose, fields, maps);
                    } else {
                        string += fee + tab + "x" + faa + getValueString(_x) + foo;
                    }
                }
                if (_y != null) {
                    if (_y instanceof NaryExpression) {
                        e = (Expression) _y;
                        string += e.toString(n + 1, "y", verbose, fields, maps);
                    } else {
                        string += fee + tab + "y" + faa + getValueString(_y) + foo;
                    }
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
