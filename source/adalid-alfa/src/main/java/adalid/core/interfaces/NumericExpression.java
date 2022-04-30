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
package adalid.core.interfaces;

import adalid.core.enums.*;
import adalid.core.expressions.*;

/**
 * @author Jorge Campins
 */
public interface NumericExpression extends Expression {

    /**
     * El método <b>isNull</b> contruye una expresión lógica que genera la comparación de esta expresión con el valor nulo. La comparación resulta en
     * verdadero si el valor de la expresión es nulo.
     *
     * @return expresión lógica que genera la comparación con el valor nulo.
     */
    BooleanComparisonX isNull();

    /**
     * El método <b>isNotNull</b> contruye una expresión lógica que genera la comparación de esta expresión con el valor nulo. La comparación resulta
     * en verdadero si el valor de la expresión no es nulo.
     *
     * @return expresión lógica que genera la comparación con el valor nulo.
     */
    BooleanComparisonX isNotNull();

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que recibe como
     * argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(Number y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(NumericExpression y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(Number y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(NumericExpression y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(Number y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(SpecialNumericValue y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(NumericExpression y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(Number y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(NumericExpression y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(Number y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(SpecialNumericValue y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(NumericExpression y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(Number y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(NumericExpression y);

    /**
     * El método <b>isIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de expresiones que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isIn(NumericExpression... y);

    /**
     * El método <b>isNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de expresiones
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotIn(NumericExpression... y);

    /**
     * El método <b>isBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si X es mayor o igual que minimo y menor o igual que
     * maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isBetween(NumericExpression minimo, NumericExpression maximo);

    /**
     * El método <b>isNotBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si X es menor que minimo o mayor que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotBetween(NumericExpression minimo, NumericExpression maximo);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(Number y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son
     * iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(NumericExpression y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(Number y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no
     * son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(NumericExpression y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(Number y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(SpecialNumericValue y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(NumericExpression y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(Number y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o igual
     * que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la
     * expresión que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o
     * igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(NumericExpression y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(Number y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(SpecialNumericValue y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(NumericExpression y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el número que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(Number y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual
     * que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(SpecialNumericValue y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(NumericExpression y);

    /**
     * El método <b>isNullOrIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * expresiones que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrIn(NumericExpression... y);

    /**
     * El método <b>isNullOrNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * expresiones que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotIn(NumericExpression... y);

    /**
     * El método <b>isNullOrBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o
     * igual que minimo y menor o igual que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrBetween(NumericExpression minimo, NumericExpression maximo);

    /**
     * El método <b>isNullOrNotBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones
     * que recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor
     * que minimo o mayor que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotBetween(NumericExpression minimo, NumericExpression maximo);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y del número que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    NumericOrderedPairX coalesce(Number y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y del valor especial que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    NumericOrderedPairX coalesce(SpecialNumericValue y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y de la expresión que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    NumericOrderedPairX coalesce(NumericExpression y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el número que recibe como argumento
     * (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    NumericOrderedPairX nullIf(Number y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    NumericOrderedPairX nullIf(SpecialNumericValue y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como
     * argumento (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    NumericOrderedPairX nullIf(NumericExpression y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el número que recibe como argumento
     * (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    NumericOrderedPairX max(Number y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    NumericOrderedPairX max(SpecialNumericValue y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como argumento
     * (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    NumericOrderedPairX max(NumericExpression y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el número que recibe como argumento
     * (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    NumericOrderedPairX min(Number y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    NumericOrderedPairX min(SpecialNumericValue y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como argumento
     * (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    NumericOrderedPairX min(NumericExpression y);

    /**
     * El método <b>plus</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el valor del número que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la suma de los dos operandos.
     */
    NumericOrderedPairX plus(Number y);

    /**
     * El método <b>plus</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el valor especial que recibe como argumento
     * (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la suma de los dos operandos.
     */
    NumericOrderedPairX plus(SpecialNumericValue y);

    /**
     * El método <b>plus</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el valor de la expresión que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la suma de los dos operandos.
     */
    NumericOrderedPairX plus(NumericExpression y);

    /**
     * El método <b>minus</b> contruye una expresión que genera el valor de esta expresión (operando X) menos el valor del número que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX minus(Number y);

    /**
     * El método <b>minus</b> contruye una expresión que genera el valor de esta expresión (operando X) menos el valor especial que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX minus(SpecialNumericValue y);

    /**
     * El método <b>minus</b> contruye una expresión que genera el valor de esta expresión (operando X) menos el valor de la expresión que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX minus(NumericExpression y);

    /**
     * El método <b>times</b> contruye una expresión que genera el valor de esta expresión (operando X) multiplicado por el valor del número que
     * recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna el producto de los dos operandos.
     */
    NumericOrderedPairX times(Number y);

    /**
     * El método <b>times</b> contruye una expresión que genera el valor de esta expresión (operando X) multiplicado por el valor especial que recibe
     * como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna el producto de los dos operandos.
     */
    NumericOrderedPairX times(SpecialNumericValue y);

    /**
     * El método <b>times</b> contruye una expresión que genera el valor de esta expresión (operando X) multiplicado por el valor de la expresión que
     * recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna el producto de los dos operandos.
     */
    NumericOrderedPairX times(NumericExpression y);

    /**
     * El método <b>over</b> contruye una expresión que genera el valor de esta expresión (operando X) dividido entre el valor del número que recibe
     * como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna el cociente de los dos operandos.
     */
    NumericOrderedPairX over(Number y);

    /**
     * El método <b>over</b> contruye una expresión que genera el valor de esta expresión (operando X) dividido entre el valor especial que recibe
     * como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna el cociente de los dos operandos.
     */
    NumericOrderedPairX over(SpecialNumericValue y);

    /**
     * El método <b>over</b> contruye una expresión que genera el valor de esta expresión (operando X) dividido entre el valor de la expresión que
     * recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna el cociente de los dos operandos.
     */
    NumericOrderedPairX over(NumericExpression y);

    /**
     * El método <b>toThe</b> contruye una expresión que genera el valor de esta expresión (operando X) elevado al valor del número que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la potencia de los dos operandos.
     */
    NumericOrderedPairX toThe(Number y);

    /**
     * El método <b>toThe</b> contruye una expresión que genera el valor de esta expresión (operando X) elevado al valor especial que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la potencia de los dos operandos.
     */
    NumericOrderedPairX toThe(SpecialNumericValue y);

    /**
     * El método <b>toThe</b> contruye una expresión que genera el valor de esta expresión (operando X) elevado al valor de la expresión que recibe
     * como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la potencia de los dos operandos.
     */
    NumericOrderedPairX toThe(NumericExpression y);

    /**
     * El método <b>defaultWhenNull</b> contruye una expresión que genera el valor de esta expresión o el valor predeterminado según el tipo de dato
     * de esta expresión, si el valor de esta expresión es nulo. El valor predeterminado de una expresión de tipo numérico es 0.
     *
     * @return expresión que retorna el valor de la expresión o el valor por default según el tipo de dato de la expresión.
     */
    NumericScalarX defaultWhenNull();

    /**
     * El método <b>nullWhenDefault</b> contruye una expresión que genera el valor de esta expresión o el valor nulo, si el valor de esta expresión es
     * igual al valor predeterminado según el tipo de dato de esta expresión. El valor predeterminado de una expresión de tipo numérico es 0.
     *
     * @return expresión que retorna el valor de la expresión o el valor nulo.
     */
    NumericScalarX nullWhenDefault();

    /**
     * El método <b>abs</b> contruye una expresión que genera el valor absoluto de esta expresión.
     *
     * @return expresión que retorna el valor absoluto de la expresión.
     */
    NumericScalarX abs();

    /**
     * El método <b>chs</b> contruye una expresión que genera el valor de signo opuesto (-X) de esta expresión.
     *
     * @return expresión que retorna el valor de signo opuesto de la expresión.
     */
    NumericScalarX chs();

    /**
     * El método <b>inv</b> contruye una expresión que genera el valor inverso (1/X) de esta expresión.
     *
     * @return expresión que retorna el valor inverso de la expresión.
     */
    NumericScalarX inv();

    /**
     * El método <b>toBigDecimal</b> contruye una expresión que genera el valor de esta expresión convertido en BigDecimal.
     *
     * @return expresión que retorna el valor de esta expresión convertido en BigDecimal.
     */
    NumericScalarX toBigDecimal();

    /**
     * El método <b>toBigInteger</b> contruye una expresión que genera el valor de esta expresión convertido en BigInteger.
     *
     * @return expresión que retorna el valor de esta expresión convertido en BigInteger.
     */
    NumericScalarX toBigInteger();

    /**
     * El método <b>toByte</b> contruye una expresión que genera el valor de esta expresión convertido en Byte.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Byte.
     */
    NumericScalarX toByte();

    /**
     * El método <b>toShort</b> contruye una expresión que genera el valor de esta expresión convertido en Short.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Short.
     */
    NumericScalarX toShort();

    /**
     * El método <b>toInteger</b> contruye una expresión que genera el valor de esta expresión convertido en Integer.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Integer.
     */
    NumericScalarX toInteger();

    /**
     * El método <b>toLong</b> contruye una expresión que genera el valor de esta expresión convertido en Long.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Long.
     */
    NumericScalarX toLong();

    /**
     * El método <b>toDouble</b> contruye una expresión que genera el valor de esta expresión convertido en Double.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Double.
     */
    NumericScalarX toDouble();

    /**
     * El método <b>toFloat</b> contruye una expresión que genera el valor de esta expresión convertido en Float.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Float.
     */
    NumericScalarX toFloat();

    /**
     * El método <b>toCharString</b> contruye una expresión que genera el valor de esta expresión convertido en String.
     *
     * @return expresión que retorna el valor de esta expresión convertido en String.
     */
    CharacterScalarX toCharString();

    /**
     * El método <b>toLocaleString</b> contruye una expresión que genera el valor de esta expresión convertido en String según el formato establecido
     * por la configuración regional.
     *
     * @return expresión que retorna el valor de esta expresión convertido en String.
     */
    CharacterScalarX toLocaleString();

    /**
     * El método <b>toZeroPaddedString</b> contruye una expresión que genera el valor de esta expresión concatenada con ceros a la izquierda hasta que
     * la expresión resultante alcance la longitud especificada por el parámetro len.
     *
     * @param len longitud de la expresión resultante
     * @return expresión que retorna el valor de esta expresión con ceros a la izquierda.
     */
    CharacterOrderedPairX toZeroPaddedString(NumericExpression len);

    /**
     * El método <b>toZeroPaddedString</b> contruye una expresión que genera el valor de esta expresión concatenada con ceros a la izquierda hasta que
     * la expresión resultante alcance la longitud especificada por el parámetro len.
     *
     * @param len longitud de la expresión resultante
     * @return expresión que retorna el valor de esta expresión con ceros a la izquierda.
     */
    CharacterOrderedPairX toZeroPaddedString(int len);

}
