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
public abstract class AbstractRowsAggregateX extends AbstractExpression implements RowsAggregateX {

    private static final String EOL = "\n";

    protected AbstractRowsAggregateX(RowsAggregateOp operator, Object measure) {
        this(operator, measure, null, null);
    }

    protected AbstractRowsAggregateX(RowsAggregateOp operator, Object measure, Segment filter) {
        this(operator, measure, filter, null);
    }

    protected AbstractRowsAggregateX(RowsAggregateOp operator, Object measure, Entity dimension) {
        this(operator, measure, null, dimension);
    }

    protected AbstractRowsAggregateX(RowsAggregateOp operator, Object measure, Segment filter, Entity dimension) {
        super();
        _operator = operator;
        _measure = measure;
        _filter = filter;
        _dimension = dimension;
        initDataType();
    }

    private RowsAggregateOp _operator;

    private Object _measure;

    private Segment _filter;

    private Entity _dimension;

    /**
     * @return the operator
     */
    @Override
    public RowsAggregateOp getOperator() {
        return _operator;
    }

    /**
     * @return the operands
     */
    @Override
    public Object[] getOperands() {
//      return _operand == null ? new Object[]{} : new Object[]{_operand};
        return _measure == null ? new Object[]{} : new Object[]{_measure, _filter, _dimension};
    }

    /**
     * @return the operand
     */
    @Override
    public Object getMeasure() {
        return _measure;
    }

    /**
     * @return the segment
     */
    @Override
    public Segment getFilter() {
        return _filter;
    }

    /**
     * @return the property
     */
    @Override
    public Entity getDimension() {
        return _dimension;
    }

    private void initDataType() {
        if (_operator == null) {
            copyDataType(_measure);
        } else {
            switch (_operator) {
                case COUNT:
                    setDataType(Long.class);
                    break;
                case AND:
                case OR:
                    setDataType(Boolean.class);
                    break;
                case MAXIMUM:
                case MINIMUM:
                case SUM:
                case AVERAGE:
                default:
                    copyDataType(_measure);
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
                string += fee + tab + "operator" + faa + _operator + foo;
                string += fee + tab + "operand" + faa + getValueString(_measure) + foo;
                if (_filter != null) {
                    string += fee + tab + "segment" + faa + getValueString(_filter) + foo;
                }
                if (_dimension != null) {
                    string += fee + tab + "property" + faa + getValueString(_dimension) + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
