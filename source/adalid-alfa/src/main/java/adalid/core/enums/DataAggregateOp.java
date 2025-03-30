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
public enum DataAggregateOp implements Operator {

    /**
     * El operador COALESCE devuelve el primero de sus operandos que no es nulo. Solo devuelve nulo si todos los operandos son nulos.
     */
    COALESCE,
    /* operadores para para expresiones numéricas y temporales (fechas, horas y timestamps) */
    COUNT, MAXIMUM, MINIMUM,
    /**
     * La conjunción (AND) es un operador lógico que resulta en verdadero si todos los operandos son verdadero.
     */
    AND,
    /**
     * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si al menos uno de los operandos es falso. Es equivalente a la
     * negación (NOT) de la conjunción (AND).
     */
    NAND,
    /**
     * La disyunción (OR) es un operador lógico que resulta en verdadero si al menos uno de los operandos es verdadero.
     */
    OR,
    /**
     * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si todos los operandos son falso. Es equivalente a la negación (NOT)
     * de la disyunción (OR).
     */
    NOR,
    /**
     * La disyunción exclusiva no-asociativa (NAXOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operandos es verdadero.
     */
    NAXOR,
    /**
     * La equivalencia no-asociativa (NAXNOR) es un operador lógico que resulta en verdadero si todos los operandos son verdadero ó si todos son
     * falso. Es equivalente a la negación (NOT) de la disyunción exclusiva no-asociativa (NAXOR).
     */
    NAXNOR,
    /**
     * NOR_OR_NAXOR es un operador lógico compuesto que resulta en verdadero si uno y solo uno de los operandos es verdadero ó si todos son falso. Es
     * equivalente a la disyunción (OR) de la negación conjunta (NOR) y la disyunción exclusiva no-asociativa (NAXOR). Con solo dos operandos también
     * es equivalente a la negación alternativa (NAND).
     */
    NOR_OR_NAXOR,
    /* operadores para expresiones alfanuméricas */
    CONCAT, CONCATENATE,
    /* operadores para expresiones numéricas */
    SUM, PRODUCT, AVERAGE

}
