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
public interface TemporalExpression extends Expression {

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
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que recibe como
     * argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(java.util.Date y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(TemporalExpression y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(java.util.Date y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(TemporalExpression y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(java.util.Date y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(SpecialTemporalValue y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(TemporalExpression y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(java.util.Date y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(TemporalExpression y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que recibe como
     * argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(java.util.Date y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(SpecialTemporalValue y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(TemporalExpression y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(java.util.Date y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(TemporalExpression y);

    /**
     * El método <b>isIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de expresiones que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isIn(TemporalExpression... y);

    /**
     * El método <b>isIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de instancias que
     * se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isIn(String y);

    /**
     * El método <b>isNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de expresiones
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotIn(TemporalExpression... y);

    /**
     * El método <b>isNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de instancias
     * que se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es
     * igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotIn(String y);

    /**
     * El método <b>isBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si X es mayor o igual que minimo y menor o igual que
     * maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isBetween(TemporalExpression minimo, TemporalExpression maximo);

    /**
     * El método <b>isNotBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si X es menor que minimo o mayor que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotBetween(TemporalExpression minimo, TemporalExpression maximo);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(java.util.Date y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son
     * iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(TemporalExpression y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(java.util.Date y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no
     * son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(TemporalExpression y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(java.util.Date y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(SpecialTemporalValue y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(TemporalExpression y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(java.util.Date y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o igual
     * que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la
     * expresión que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o
     * igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(TemporalExpression y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(java.util.Date y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(SpecialTemporalValue y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(TemporalExpression y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(java.util.Date y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual
     * que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(SpecialTemporalValue y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(TemporalExpression y);

    /**
     * El método <b>isNullOrIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * expresiones que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrIn(TemporalExpression... y);

    /**
     * El método <b>isNullOrIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * instancias que se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero
     * si el valor del operando X es nulo o si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrIn(String y);

    /**
     * El método <b>isNullOrNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * expresiones que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotIn(TemporalExpression... y);

    /**
     * El método <b>isNullOrNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * instancias que se obtiene ejecutando la instrucción SELECT de SQL que recibe como argumento (operando Y). La comparación resulta en verdadero
     * si el valor del operando X es nulo o si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotIn(String y);

    /**
     * El método <b>isNullOrBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o
     * igual que minimo y menor o igual que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrBetween(TemporalExpression minimo, TemporalExpression maximo);

    /**
     * El método <b>isNullOrNotBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones
     * que recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor
     * que minimo o mayor que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotBetween(TemporalExpression minimo, TemporalExpression maximo);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y del número que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    TemporalOrderedPairX coalesce(java.util.Date y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y del valor especial que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    TemporalOrderedPairX coalesce(SpecialTemporalValue y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y de la expresión que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    TemporalOrderedPairX coalesce(TemporalExpression y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor que recibe como argumento
     * (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    TemporalOrderedPairX nullIf(java.util.Date y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    TemporalOrderedPairX nullIf(SpecialTemporalValue y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como
     * argumento (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    TemporalOrderedPairX nullIf(TemporalExpression y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor que recibe como argumento
     * (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    TemporalOrderedPairX max(java.util.Date y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    TemporalOrderedPairX max(SpecialTemporalValue y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como argumento
     * (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    TemporalOrderedPairX max(TemporalExpression y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor que recibe como argumento
     * (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    TemporalOrderedPairX min(java.util.Date y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    TemporalOrderedPairX min(SpecialTemporalValue y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como argumento
     * (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    TemporalOrderedPairX min(TemporalExpression y);

    /**
     * El método <b>addYears</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de años que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de años a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addYears(Number y);

    /**
     * El método <b>addYears</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de años que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de años a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addYears(NumericExpression y);

    /**
     * El método <b>addMonths</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de meses que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de meses a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addMonths(Number y);

    /**
     * El método <b>addMonths</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de meses que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de meses a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addMonths(NumericExpression y);

    /**
     * El método <b>addWeeks</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de semanas que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de semanas a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addWeeks(Number y);

    /**
     * El método <b>addWeeks</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de semanas que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de semanas a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addWeeks(NumericExpression y);

    /**
     * El método <b>addDays</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de dias que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de dias a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addDays(Number y);

    /**
     * El método <b>addDays</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de dias que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de dias a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addDays(NumericExpression y);

    /**
     * El método <b>addHours</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de horas que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de horas a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addHours(Number y);

    /**
     * El método <b>addHours</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de horas que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de horas a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addHours(NumericExpression y);

    /**
     * El método <b>addMinutes</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de minutos que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de minutos a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addMinutes(Number y);

    /**
     * El método <b>addMinutes</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de minutos que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de minutos a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addMinutes(NumericExpression y);

    /**
     * El método <b>addSeconds</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de segundos que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de segundos a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addSeconds(Number y);

    /**
     * El método <b>addSeconds</b> contruye una expresión que genera el valor de esta expresión (operando X) mas el número de segundos que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y: número de segundos a sumar
     * @return expresión que retorna la suma de los dos operandos.
     */
    TemporalOrderedPairX addSeconds(NumericExpression y);

    /**
     * El método <b>diffInYears</b> contruye una expresión que genera el valor de la diferencia en años entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInYears(java.util.Date y);

    /**
     * El método <b>diffInYears</b> contruye una expresión que genera el valor de la diferencia en años entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInYears(SpecialTemporalValue y);

    /**
     * El método <b>diffInYears</b> contruye una expresión que genera el valor de la diferencia en años entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInYears(TemporalExpression y);

    /**
     * El método <b>diffInMonths</b> contruye una expresión que genera el valor de la diferencia en meses entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInMonths(java.util.Date y);

    /**
     * El método <b>diffInMonths</b> contruye una expresión que genera el valor de la diferencia en meses entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInMonths(SpecialTemporalValue y);

    /**
     * El método <b>diffInMonths</b> contruye una expresión que genera el valor de la diferencia en meses entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInMonths(TemporalExpression y);

    /**
     * El método <b>diffInWeeks</b> contruye una expresión que genera el valor de la diferencia en semanas entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInWeeks(java.util.Date y);

    /**
     * El método <b>diffInWeeks</b> contruye una expresión que genera el valor de la diferencia en semanas entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInWeeks(SpecialTemporalValue y);

    /**
     * El método <b>diffInWeeks</b> contruye una expresión que genera el valor de la diferencia en semanas entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInWeeks(TemporalExpression y);

    /**
     * El método <b>diffInDays</b> contruye una expresión que genera el valor de la diferencia en dias entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInDays(java.util.Date y);

    /**
     * El método <b>diffInDays</b> contruye una expresión que genera el valor de la diferencia en dias entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInDays(SpecialTemporalValue y);

    /**
     * El método <b>diffInDays</b> contruye una expresión que genera el valor de la diferencia en dias entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInDays(TemporalExpression y);

    /**
     * El método <b>diffInHours</b> contruye una expresión que genera el valor de la diferencia en horas entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInHours(java.util.Date y);

    /**
     * El método <b>diffInHours</b> contruye una expresión que genera el valor de la diferencia en horas entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInHours(SpecialTemporalValue y);

    /**
     * El método <b>diffInHours</b> contruye una expresión que genera el valor de la diferencia en horas entre esta expresión (operando X) y el valor
     * que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInHours(TemporalExpression y);

    /**
     * El método <b>diffInMinutes</b> contruye una expresión que genera el valor de la diferencia en minutos entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInMinutes(java.util.Date y);

    /**
     * El método <b>diffInMinutes</b> contruye una expresión que genera el valor de la diferencia en minutos entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInMinutes(SpecialTemporalValue y);

    /**
     * El método <b>diffInMinutes</b> contruye una expresión que genera el valor de la diferencia en minutos entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInMinutes(TemporalExpression y);

    /**
     * El método <b>diffInSeconds</b> contruye una expresión que genera el valor de la diferencia en segundos entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInSeconds(java.util.Date y);

    /**
     * El método <b>diffInSeconds</b> contruye una expresión que genera el valor de la diferencia en segundos entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInSeconds(SpecialTemporalValue y);

    /**
     * El método <b>diffInSeconds</b> contruye una expresión que genera el valor de la diferencia en segundos entre esta expresión (operando X) y el
     * valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    NumericOrderedPairX diffInSeconds(TemporalExpression y);

    /**
     * El método <b>toTimestamp</b> contruye una expresión que genera un Timestamp con la fecha de esta expresión (operando X) y la hora, los minutos
     * y los segundos del valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    TemporalOrderedPairX toTimestamp(java.util.Date y);

    /**
     * El método <b>toTimestamp</b> contruye una expresión que genera un Timestamp con la fecha de esta expresión (operando X) y la hora, los minutos
     * y los segundos del valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    TemporalOrderedPairX toTimestamp(SpecialTemporalValue y);

    /**
     * El método <b>toTimestamp</b> contruye una expresión que genera un Timestamp con la fecha de esta expresión (operando X) y la hora, los minutos
     * y los segundos del valor que recibe como argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la diferencia entre los dos operandos.
     */
    TemporalOrderedPairX toTimestamp(TemporalExpression y);

    /**
     * El método <b>defaultWhenNull</b> contruye una expresión que genera el valor de esta expresión o el valor predeterminado según el tipo de dato
     * de esta expresión, si el valor de esta expresión es nulo. El valor predeterminado de una expresión de tipo numérico es 0.
     *
     * @return expresión que retorna el valor de la expresión o el valor por default según el tipo de dato de la expresión.
     */
    TemporalScalarX defaultWhenNull();

    /**
     * El método <b>nullWhenDefault</b> contruye una expresión que genera el valor de esta expresión o el valor nulo, si el valor de esta expresión es
     * igual al valor predeterminado según el tipo de dato de esta expresión. El valor predeterminado de una expresión de tipo numérico es 0.
     *
     * @return expresión que retorna el valor de la expresión o el valor nulo.
     */
    TemporalScalarX nullWhenDefault();

    /**
     * El método <b>toDate</b> contruye una expresión que genera el valor de esta expresión convertido en Date.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Date.
     */
    TemporalScalarX toDate();

    /**
     * El método <b>toTime</b> contruye una expresión que genera el valor de esta expresión convertido en Time.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Time.
     */
    TemporalScalarX toTime();

    /**
     * El método <b>toTimestamp</b> contruye una expresión que genera el valor de esta expresión convertido en Timestamp.
     *
     * @return expresión que retorna el valor de esta expresión convertido en Timestamp.
     */
    TemporalScalarX toTimestamp();

    /**
     * El método <b>firstDateOfMonth</b> contruye una expresión que genera la fecha del primer dia del mes de esta expresión.
     *
     * @return expresión que retorna la fecha del primer dia del mes de esta expresión.
     */
    TemporalScalarX firstDateOfMonth();

    /**
     * El método <b>firstDateOfQuarter</b> contruye una expresión que genera la fecha del primer dia del trimestre de esta expresión.
     *
     * @return expresión que retorna la fecha del primer dia del trimestre de esta expresión.
     */
    TemporalScalarX firstDateOfQuarter();

    /**
     * El método <b>firstDateOfSemester</b> contruye una expresión que genera la fecha del primer dia del semestre de esta expresión.
     *
     * @return expresión que retorna la fecha del primer dia del semestre de esta expresión.
     */
    TemporalScalarX firstDateOfSemester();

    /**
     * El método <b>firstDateOfYear</b> contruye una expresión que genera la fecha del primer dia del año de esta expresión.
     *
     * @return expresión que retorna la fecha del primer dia del año de esta expresión.
     */
    TemporalScalarX firstDateOfYear();

    /**
     * El método <b>lastDateOfMonth</b> contruye una expresión que genera la fecha del último dia del mes de esta expresión.
     *
     * @return expresión que retorna la fecha del último dia del mes de esta expresión.
     */
    TemporalScalarX lastDateOfMonth();

    /**
     * El método <b>lastDateOfQuarter</b> contruye una expresión que genera la fecha del último dia del trimestre de esta expresión.
     *
     * @return expresión que retorna la fecha del último dia del trimestre de esta expresión.
     */
    TemporalScalarX lastDateOfQuarter();

    /**
     * El método <b>lastDateOfSemester</b> contruye una expresión que genera la fecha del último dia del semestre de esta expresión.
     *
     * @return expresión que retorna la fecha del último dia del semestre de esta expresión.
     */
    TemporalScalarX lastDateOfSemester();

    /**
     * El método <b>lastDateOfYear</b> contruye una expresión que genera la fecha del último dia del año de esta expresión.
     *
     * @return expresión que retorna la fecha del último dia del año de esta expresión.
     */
    TemporalScalarX lastDateOfYear();

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
     * El método <b>extractYear</b> contruye una expresión que genera el valor del año de esta expresión.
     *
     * @return expresión que retorna el valor del año de esta expresión.
     */
    NumericScalarX extractYear();

    /**
     * El método <b>extractMonth</b> contruye una expresión que genera el valor del mes de esta expresión.
     *
     * @return expresión que retorna el valor del mes de esta expresión.
     */
    NumericScalarX extractMonth();

    /**
     * El método <b>extractDay</b> contruye una expresión que genera el valor del dia de esta expresión.
     *
     * @return expresión que retorna el valor del dia de esta expresión.
     */
    NumericScalarX extractDay();

    /**
     * El método <b>extractHour</b> contruye una expresión que genera el valor de la hora de esta expresión.
     *
     * @return expresión que retorna el valor de la hora de esta expresión.
     */
    NumericScalarX extractHour();

    /**
     * El método <b>extractMinute</b> contruye una expresión que genera el valor del minuto de esta expresión.
     *
     * @return expresión que retorna el valor del minuto de esta expresión.
     */
    NumericScalarX extractMinute();

    /**
     * El método <b>extractSecond</b> contruye una expresión que genera el valor del segundo de esta expresión.
     *
     * @return expresión que retorna el valor del segundo de esta expresión.
     */
    NumericScalarX extractSecond();

}
