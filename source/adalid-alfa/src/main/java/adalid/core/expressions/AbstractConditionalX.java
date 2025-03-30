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
public abstract class AbstractConditionalX extends AbstractExpression implements ConditionalX {

    private static final String EOL = "\n";

    AbstractConditionalX(BooleanExpression booleanExpression, Object value) {
        super();
        _operator = ConditionalOp.IF_THEN;
        _booleanExpression = booleanExpression;
        _thenValue = value;
        _elseValue = null;
    }

    AbstractConditionalX(ConditionalX conditional, Object value) {
        super();
        _operator = ConditionalOp.IF_THEN_ELSE;
        _booleanExpression = conditional.getBooleanExpression();
        _thenValue = conditional.getThenValue();
        _elseValue = value;
    }

    private final ConditionalOp _operator;

    private final BooleanExpression _booleanExpression;

    private final Object _thenValue;

    private final Object _elseValue;

    /**
     * @return the operator
     */
    @Override
    public ConditionalOp getOperator() {
        return _operator;
    }

    /**
     * @return the operands
     */
    @Override
    public Object[] getOperands() {
        return new Object[]{_booleanExpression, _thenValue, _elseValue};
    }

    /**
     * @return the boolean expression
     */
    @Override
    public BooleanExpression getBooleanExpression() {
        return _booleanExpression;
    }

    /**
     * @return the then value
     */
    @Override
    public Object getThenValue() {
        return _thenValue;
    }

    /**
     * @return the else value
     */
    @Override
    public Object getElseValue() {
        return _elseValue;
    }

    @Override
    public Class<?> getDataType() {
        Class<?> thenDataType = getObjectDataType(_thenValue);
        Class<?> elseDataType = getObjectDataType(_elseValue);
        if (_thenValue == null && _elseValue == null) {
            return null;
        } else if (_thenValue != null && (_elseValue == null || thenDataType.isAssignableFrom(elseDataType))) {
            return thenDataType;
        } else if (_elseValue != null && (_thenValue == null || elseDataType.isAssignableFrom(thenDataType))) {
            return elseDataType;
        } else {
            if (_thenValue != null && _elseValue != null) { // since 20201210
                Class<?>[] types = {Boolean.class, String.class, Number.class, java.util.Date.class};
                for (Class<?> type : types) {
                    if (type.isAssignableFrom(thenDataType) && type.isAssignableFrom(elseDataType)) {
                        return type;
                    }
                }
            }
            return Object.class;
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
                if (_booleanExpression != null) {
                    string += _booleanExpression.toString(n + 1, "booleanExpression");
                }
                if (_thenValue != null) {
                    if (_thenValue instanceof NaryExpression) {
                        e = (Expression) _thenValue;
                        string += e.toString(n + 1, "thenExpression");
                    } else {
                        string += fee + tab + "thenExpression" + faa + getValueString(_thenValue) + foo;
                    }
                }
                if (_elseValue != null) {
                    if (_elseValue instanceof NaryExpression) {
                        e = (Expression) _elseValue;
                        string += e.toString(n + 1, "elseExpression");
                    } else {
                        string += fee + tab + "elseExpression" + faa + getValueString(_elseValue) + foo;
                    }
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
