/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.enums;

import adalid.core.interfaces.Operator;

/**
 * @author Jorge Campins
 */
public enum ComparisonOp implements Operator {

    EXISTS, NOT_EXISTS,
    /**/
    IS_NULL, IS_NOT_NULL,
    /**/
    IS_TRUE, IS_FALSE,
    IS_NULL_OR_TRUE, IS_NULL_OR_FALSE,
    /**/
    EQ, NEQ, GT, GTEQ, LT, LTEQ,
    IS_NULL_OR_EQ, IS_NULL_OR_NEQ, IS_NULL_OR_GT, IS_NULL_OR_GTEQ, IS_NULL_OR_LT, IS_NULL_OR_LTEQ,
    /**/
    STARTS_WITH, NOT_STARTS_WITH, CONTAINS, NOT_CONTAINS, ENDS_WITH, NOT_ENDS_WITH,
    IS_NULL_OR_STARTS_WITH, IS_NULL_OR_NOT_STARTS_WITH, IS_NULL_OR_CONTAINS, IS_NULL_OR_NOT_CONTAINS, IS_NULL_OR_ENDS_WITH, IS_NULL_OR_NOT_ENDS_WITH,
    /**/
    IN, NOT_IN, BETWEEN, NOT_BETWEEN,
    IS_NULL_OR_IN, IS_NULL_OR_NOT_IN, IS_NULL_OR_BETWEEN, IS_NULL_OR_NOT_BETWEEN

}
