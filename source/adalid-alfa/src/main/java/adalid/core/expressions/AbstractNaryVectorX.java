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
public abstract class AbstractNaryVectorX extends AbstractExpression implements NaryVectorX {

    private static final String EOL = "\n";

    protected AbstractNaryVectorX(NaryVectorOp operator, Object... operands) {
        super();
        _operator = operator;
        _operands = operands;
        initDataType();
    }

    private final NaryVectorOp _operator;

    private final Object[] _operands;

    /**
     * @return the operator
     */
    @Override
    public NaryVectorOp getOperator() {
        return _operator;
    }

    /**
     * @return the operands
     */
    @Override
    public Object[] getOperands() {
        return _operands == null ? new Object[]{} : _operands;
    }

    private void initDataType() {
        if (_operator == null) {
            setDataType(Object.class);
        } else {
            switch (_operator) {
                case SUBSTR:
                    setDataType(String.class);
                    break;
                default:
                    setDataType(Object.class);
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
        Expression e;
        if (fields || verbose) {
            if (verbose) {
                string += fee + tab + "operator" + faa + _operator + foo;
                for (Object operand : _operands) {
                    if (operand != null) {
                        if (operand instanceof NaryExpression) {
                            e = (Expression) operand;
                            string += e.toString(n + 1, "x", verbose, fields, maps);
                        } else {
                            string += fee + tab + "x" + faa + getValueString(operand) + foo;
                        }
                    }
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
