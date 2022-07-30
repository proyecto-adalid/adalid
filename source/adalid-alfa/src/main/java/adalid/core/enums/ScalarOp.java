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
public enum ScalarOp implements Operator {

    SELF,
    /**/
    DEFAULT_WHEN_NULL, NULL_WHEN_DEFAULT,
    /**/
    NOT,
    /**/
    ASCII, DIACRITICLESS, DIACRITICLESS_ASCII, LOWER, UPPER, CAPITALIZE, UNCAPITALIZE, TRIM, LTRIM, RTRIM,
    /**/
    MODULUS, OPPOSITE, RECIPROCAL,
    /**/
    YEAR, MONTH, DAY, HOUR, MINUTE, SECOND,
    FIRST_DATE_OF_MONTH, FIRST_DATE_OF_QUARTER, FIRST_DATE_OF_SEMESTER, FIRST_DATE_OF_YEAR,
    LAST_DATE_OF_MONTH, LAST_DATE_OF_QUARTER, LAST_DATE_OF_SEMESTER, LAST_DATE_OF_YEAR,
    /**/
    TO_BOOLEAN,
    TO_CHARACTER, TO_STRING, TO_LOCALE_STRING,
    TO_BYTE, TO_SHORT, TO_INTEGER, TO_LONG, TO_FLOAT, TO_DOUBLE, TO_BIG_INTEGER, TO_BIG_DECIMAL,
    TO_DATE, TO_TIME, TO_TIMESTAMP

}
