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
public enum StandardRelationalOp implements Operator {

    EQ, NEQ, GT, GTEQ, LT, LTEQ,
    LIKE, NOT_LIKE,
    IS_NULL_OR_EQ, IS_NULL_OR_NEQ, IS_NULL_OR_GT, IS_NULL_OR_GTEQ, IS_NULL_OR_LT, IS_NULL_OR_LTEQ,
    IS_NULL_OR_LIKE, IS_NULL_OR_NOT_LIKE

}
