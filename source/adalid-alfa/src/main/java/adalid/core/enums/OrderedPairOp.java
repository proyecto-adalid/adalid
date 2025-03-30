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
public enum OrderedPairOp implements Operator {

    /**
     * El operador COALESCE devuelve el primero de sus operandos que no es nulo. Solo devuelve nulo si ambos operandos son nulos.
     */
    COALESCE, NULLIF,
    /* operadores para para expresiones numéricas y temporales (fechas, horas y timestamps) */
    MAXIMUM, MINIMUM,
    /**
     * La conjunción (AND) es un operador lógico que resulta en verdadero si ambos operandos son verdadero.
     */
    AND,
    /**
     * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si uno de los operandos es falso. Es equivalente a la negación
     * (NOT) de la conjunción (AND).
     */
    NAND,
    /**
     * La disyunción (OR) es un operador lógico que resulta en verdadero si uno de los operandos es verdadero.
     */
    OR,
    /**
     * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si ambos operandos son falso. Es equivalente a la negación (NOT) de
     * la disyunción (OR).
     */
    NOR,
    /**
     * La disyunción exclusiva (XOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operandos es verdadero.
     */
    XOR,
    /**
     * La equivalencia (XNOR) es un operador lógico que resulta en verdadero si ambos operandos son verdadero ó si ambos son falso. Es equivalente a
     * la negación (NOT) de la disyunción exclusiva (XOR).
     */
    XNOR,
    /**
     * La condicional material (X_IMPLIES_Y) es un operador lógico que resulta en falso solo si X es verdadero y Y es falso. Es equivalente a la
     * disyunción (OR) de la negación (NOT) de X con Y (NOT X OR Y).
     */
    X_IMPLIES_Y,
    /* operadores para expresiones alfanuméricas */
    ASCII, DIACRITICLESS_ASCII, CONCAT, CONCATENATE, FORMAT, LEFT, RIGHT, SUBSTR,
    /* operadores para expresiones alfanuméricas y numéricas */
    TO_ZERO_PADDED_STRING,
    /* operadores para expresiones numéricas */
    X_PLUS_Y, X_MINUS_Y, X_MULTIPLIED_BY_Y, X_DIVIDED_INTO_Y, X_RAISED_TO_THE_Y,
    /* operadores para expresiones temporales (fechas, horas y timestamps) */
    ADD_YEARS, ADD_MONTHS, ADD_WEEKS, ADD_DAYS, ADD_HOURS, ADD_MINUTES, ADD_SECONDS,
    DIFF_IN_YEARS, DIFF_IN_MONTHS, DIFF_IN_WEEKS, DIFF_IN_DAYS, DIFF_IN_HOURS, DIFF_IN_MINUTES, DIFF_IN_SECONDS, TO_TIMESTAMP

}
