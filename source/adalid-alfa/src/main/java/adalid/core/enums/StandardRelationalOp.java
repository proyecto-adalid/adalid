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
package adalid.core.enums;

import adalid.core.interfaces.*;

/**
 * @author Jorge Campins
 */
public enum StandardRelationalOp implements Operator {

    UNSPECIFIED,
    EQ, NEQ, GT, GTEQ, LT, LTEQ,
    LIKE, NOT_LIKE,
    IS_NULL_OR_EQ, IS_NULL_OR_NEQ, IS_NULL_OR_GT, IS_NULL_OR_GTEQ, IS_NULL_OR_LT, IS_NULL_OR_LTEQ,
    IS_NULL_OR_LIKE, IS_NULL_OR_NOT_LIKE

}
