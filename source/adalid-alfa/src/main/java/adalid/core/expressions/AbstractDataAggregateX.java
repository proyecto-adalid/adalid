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
public abstract class AbstractDataAggregateX extends AbstractExpression implements DataAggregateX {

    private static final String EOL = "\n";

    protected AbstractDataAggregateX(DataAggregateOp operator, Object... operands) {
        super();
        _operator = operator;
        _operands = operands;
        initDataType();
    }

    private final DataAggregateOp _operator;

//  @ExpressionArray()
    private Object[] _operands = null; // OJO: sin el = null no obtiene la anotación al ejecutar

    /**
     * @return the operator
     */
    @Override
    public DataAggregateOp getOperator() {
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
        Object operand = _operands == null || _operands.length == 0 ? null : _operands[0];
        if (_operator == null) {
            copyDataType(operand);
        } else {
            switch (_operator) {
                case AND, NAND, OR, NOR, NAXOR, NAXNOR, NOR_OR_NAXOR ->
                    setDataType(Boolean.class);
                case CONCAT, CONCATENATE ->
                    setDataType(String.class);
                case COUNT ->
                    setDataType(Long.class);
                case COALESCE, MAXIMUM, MINIMUM ->
                    copyDataType(operand);
                case SUM, PRODUCT, AVERAGE ->
                    setDataType(BigDecimal.class);
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
        if (fields || verbose) {
            string += fee + tab + "operands" + faa + _operands.length + foo;
            if (verbose) {
                string += fee + tab + "operator" + faa + _operator + foo;
            }
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            if (_operands != null) {
                for (int i = 0; i < _operands.length; i++) {
                    String clave = "operands" + "_" + i;
                    if (_operands[i] instanceof NaryExpression) {
                        Expression valor = (Expression) _operands[i];
                        string += valor.toString(n + 1, clave, verbose, fields, maps);
                    } else {
                        string += fee + tab + clave + faa + getValueString(_operands[i]) + foo;
                    }
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
