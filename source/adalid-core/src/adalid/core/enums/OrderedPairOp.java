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
public enum OrderedPairOp implements Operator {

    COALESCE, NULLIF,
    /**/
    MAXIMUM, MINIMUM,
    /**/
    AND, NAND, OR, NOR, XOR, XNOR, X_IMPLIES_Y,
    /**/
    CONCAT,
    /**/
    X_PLUS_Y, X_MINUS_Y, X_MULTIPLIED_BY_Y, X_DIVIDED_INTO_Y, X_RAISED_TO_THE_Y

}

/*
 * La conjunción (AND) es un operador lógico que resulta en verdadero si ambos operadores son verdadero.
 *
 * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si uno de los operadores es falso. Es equivalente a la negación (NOT)
 * de la conjunción (AND).
 *
 * La disyunción (OR) es un operador lógico que resulta en verdadero si uno de los operadores es verdadero.
 *
 * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si ambos operadores son falso. Es equivalente a la negación (NOT) de la
 * disyunción (OR).
 *
 * La disyunción exclusiva (XOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operadores es verdadero.
 *
 * La equivalencia (XNOR) es un operador lógico que resulta en verdadero si ambos operadores son verdadero ó si ambos son falso. Es equivalente a la
 * negación (NOT) de la disyunción exclusiva (XOR).
 *
 * La condicional material (X_IMPLIES_Y) es un operador lógico que resulta en falso solo si X es verdadero y Y es falso. Es equivalente a la
 * disyunción (OR) de la negación (NOT) de X con Y (NOT X OR Y).
 */
