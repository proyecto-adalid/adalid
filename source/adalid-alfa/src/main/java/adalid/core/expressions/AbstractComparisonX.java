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
        this(x, operator, y, null);
    }

    protected AbstractComparisonX(Object x, ComparisonOp operator, Object y, Object z) {
        super();
        _operator = operator;
        _x = x;
        _y = y;
        _z = z;
        initDataType();
    }

    private ComparisonOp _operator;

    private Object _x;

    private Object _y;

    private Object _z;

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
        return _z == null ? _y == null ? _x == null ? new Object[]{} : new Object[]{_x} : new Object[]{_x, _y} : new Object[]{_x, _y, _z};
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

    /**
     * @return the z
     */
    @Override
    public Object getZ() {
        return _z;
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
                if (_z != null) {
                    if (_z instanceof NaryExpression) {
                        e = (Expression) _z;
                        string += e.toString(n + 1, "y", verbose, fields, maps);
                    } else {
                        string += fee + tab + "z" + faa + getValueString(_z) + foo;
                    }
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
