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
public enum ScalarOp implements Operator {

    DEFAULT_WHEN_NULL, NULL_WHEN_DEFAULT,
    /**/
    NOT,
    /**/
    LOWER, UPPER, CAPITALIZE, UNCAPITALIZE, TRIM, LTRIM, RTRIM,
    /**/
    MODULUS, OPPOSITE, RECIPROCAL,
    /**/
    TO_BOOLEAN,
    TO_CHARACTER, TO_STRING,
    TO_BYTE, TO_SHORT, TO_INTEGER, TO_LONG, TO_FLOAT, TO_DOUBLE, TO_BIG_INTEGER, TO_BIG_DECIMAL,
    TO_DATE, TO_TIME, TO_TIMESTAMP

}
