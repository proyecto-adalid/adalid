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
public enum DataAggregateOp implements Operator {

    COALESCE,
    /**/
    COUNT, MAXIMUM, MINIMUM,
    /**/
    AND, NAND, OR, NOR, NAXOR, NAXNOR, NOR_OR_NAXOR,
    /**/
    CONCAT,
    /**/
    SUM, PRODUCT, AVERAGE

}

/*
 * La conjunción (AND) es un operador lógico que resulta en verdadero si todos los operadores son verdadero.
 *
 * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si al menos uno de los operadores es falso. Es equivalente a la
 * negación (NOT) de la conjunción (AND).
 *
 * La disyunción (OR) es un operador lógico que resulta en verdadero si al menos uno de los operadores es verdadero.
 *
 * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si todos los operadores son falso. Es equivalente a la negación (NOT) de
 * la disyunción (OR).
 *
 * La disyunción exclusiva no-asociativa (NAXOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operadores es verdadero.
 *
 * La equivalencia no-asociativa (NAXNOR) es un operador lógico que resulta en verdadero si todos los operadores son verdadero ó si todos son falso.
 * Es equivalente a la negación (NOT) de la disyunción exclusiva no-asociativa (NAXOR).
 *
 * NOR_OR_NAXOR es un operador lógico compuesto que resulta en verdadero si uno y solo uno de los operadores es verdadero ó si todos son falso. Es
 * equivalente a la disyunción (OR) de la negación conjunta (NOR) y la disyunción exclusiva no-asociativa (NAXOR). Con solo dos operadores también es
 * equivalente a la negación alternativa (NAND).
 */
