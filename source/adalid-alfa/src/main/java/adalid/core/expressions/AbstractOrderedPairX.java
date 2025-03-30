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
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class AbstractOrderedPairX extends AbstractExpression implements OrderedPairX {

    private static final String EOL = "\n";

    protected AbstractOrderedPairX(Object x, OrderedPairOp operator, Object y) {
        super();
        _operator = operator;
//      _x = x instanceof ScalarX ? (ScalarX) x : VariantScalarX.instanceOf(x);
//      _y = y instanceof ScalarX ? (ScalarX) y : VariantScalarX.instanceOf(y);
        _x = x;
        _y = y;
        initDataType();
    }

    private final OrderedPairOp _operator;

    private final Object _x;

    private final Object _y;

    /**
     * @return the operator
     */
    @Override
    public OrderedPairOp getOperator() {
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
        Object operand = _x;
        if (_operator == null) {
            copyDataType(operand);
        } else {
            switch (_operator) {
                case COALESCE, NULLIF, MAXIMUM, MINIMUM ->
                    copyDataType(operand);
                case AND, NAND, OR, NOR, XOR, XNOR, X_IMPLIES_Y ->
                    setDataType(Boolean.class);
                case ASCII, DIACRITICLESS_ASCII, CONCAT, CONCATENATE, FORMAT, LEFT, RIGHT, SUBSTR, TO_ZERO_PADDED_STRING ->
                    setDataType(String.class);
                case X_PLUS_Y, X_MINUS_Y, X_MULTIPLIED_BY_Y, X_DIVIDED_INTO_Y, X_RAISED_TO_THE_Y ->
                    setDataType(BigDecimal.class);
                case ADD_YEARS, ADD_MONTHS, ADD_WEEKS, ADD_DAYS, ADD_HOURS, ADD_MINUTES, ADD_SECONDS -> //                  setDataType(java.util.Date.class); // returning operand's data type until java.util.Date.class is fully tested
                    copyDataType(operand);
                case DIFF_IN_YEARS, DIFF_IN_MONTHS, DIFF_IN_WEEKS, DIFF_IN_DAYS, DIFF_IN_HOURS, DIFF_IN_MINUTES, DIFF_IN_SECONDS ->
                    setDataType(Long.class);
                case TO_TIMESTAMP ->
                    setDataType(java.sql.Timestamp.class);
                default ->
                    copyDataType(operand);
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
