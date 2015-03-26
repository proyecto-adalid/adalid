/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.RowsAggregateOp;

/**
 * @author Jorge Campins
 */
public interface RowsAggregateX extends Expression {

    /**
     * @return the operator
     */
    @Override
    RowsAggregateOp getOperator();

    /**
     * @return the operand
     */
    Object getMeasure();

    /**
     * @return the segment
     */
    Segment getFilter();

    /**
     * @return the property
     */
    Entity getDimension();
//
//  String getSqlSelectFunctionName();

}
