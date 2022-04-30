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
public interface CharacterExpression extends Expression {

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
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe como
     * argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(String y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(Expression y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(String y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(Expression y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(String y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(SpecialCharacterValue y);

    /**
     * El método <b>isGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterThan(Expression y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(String y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isGreaterOrEqualTo(Expression y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(String y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(SpecialCharacterValue y);

    /**
     * El método <b>isLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessThan(Expression y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(String y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isLessOrEqualTo(Expression y);

    /**
     * El método <b>startsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX startsWith(String y);

    /**
     * El método <b>startsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX startsWith(SpecialCharacterValue y);

    /**
     * El método <b>startsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX startsWith(Expression y);

    /**
     * El método <b>notStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X no comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notStartsWith(String y);

    /**
     * El método <b>notStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X no comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notStartsWith(SpecialCharacterValue y);

    /**
     * El método <b>notStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X no comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notStartsWith(Expression y);

    /**
     * El método <b>contains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe como
     * argumento (operando Y). La comparación resulta en verdadero si X contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX contains(String y);

    /**
     * El método <b>contains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX contains(SpecialCharacterValue y);

    /**
     * El método <b>contains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX contains(Expression y);

    /**
     * El método <b>notContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X no contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notContains(String y);

    /**
     * El método <b>notContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X no contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notContains(SpecialCharacterValue y);

    /**
     * El método <b>notContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X no contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notContains(Expression y);

    /**
     * El método <b>endsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe como
     * argumento (operando Y). La comparación resulta en verdadero si X termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX endsWith(String y);

    /**
     * El método <b>endsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX endsWith(SpecialCharacterValue y);

    /**
     * El método <b>endsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX endsWith(Expression y);

    /**
     * El método <b>notEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X no termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notEndsWith(String y);

    /**
     * El método <b>notEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X no termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notEndsWith(SpecialCharacterValue y);

    /**
     * El método <b>notEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si X no termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX notEndsWith(Expression y);

    /**
     * El método <b>isIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de expresiones que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si X es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isIn(CharacterExpression... y);

    /**
     * El método <b>isNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de expresiones
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si X no es igual a algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotIn(CharacterExpression... y);

    /**
     * El método <b>isBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si X es mayor o igual que minimo y menor o igual que
     * maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isBetween(CharacterExpression minimo, CharacterExpression maximo);

    /**
     * El método <b>isNotBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si X es menor que minimo o mayor que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNotBetween(CharacterExpression minimo, CharacterExpression maximo);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(String y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son
     * iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(Expression y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(String y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no
     * son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(Expression y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(String y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrGreaterThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterThan(Expression y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(String y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o igual
     * que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrGreaterOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la
     * expresión que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o
     * igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrGreaterOrEqualTo(Expression y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(String y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrLessThan</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessThan(Expression y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(String y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual
     * que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrLessOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor o igual que Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrLessOrEqualTo(Expression y);

    /**
     * El método <b>isNullOrStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrStartsWith(String y);

    /**
     * El método <b>isNullOrStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrStartsWith(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrStartsWith(Expression y);

    /**
     * El método <b>isNullOrNotStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotStartsWith(String y);

    /**
     * El método <b>isNullOrNotStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no comienza por
     * Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotStartsWith(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrNotStartsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no comienza por Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotStartsWith(Expression y);

    /**
     * El método <b>isNullOrContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrContains(String y);

    /**
     * El método <b>isNullOrContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrContains(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrContains(Expression y);

    /**
     * El método <b>isNullOrNotContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotContains(String y);

    /**
     * El método <b>isNullOrNotContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotContains(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrNotContains</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no contiene a Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotContains(Expression y);

    /**
     * El método <b>isNullOrEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEndsWith(String y);

    /**
     * El método <b>isNullOrEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor especial
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEndsWith(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEndsWith(Expression y);

    /**
     * El método <b>isNullOrNotEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el String que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEndsWith(String y);

    /**
     * El método <b>isNullOrNotEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el valor
     * especial que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEndsWith(SpecialCharacterValue y);

    /**
     * El método <b>isNullOrNotEndsWith</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no termina en Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEndsWith(Expression y);

    /**
     * El método <b>isNullOrIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * expresiones que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrIn(CharacterExpression... y);

    /**
     * El método <b>isNullOrNotIn</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con el conjunto de
     * expresiones que recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si X no es igual a
     * algún elemento de Y.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotIn(CharacterExpression... y);

    /**
     * El método <b>isNullOrBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones que
     * recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si el valor del operando X es nulo o si X es mayor o
     * igual que minimo y menor o igual que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrBetween(CharacterExpression minimo, CharacterExpression maximo);

    /**
     * El método <b>isNullOrNotBetween</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con las expresiones
     * que recibe como argumento (operandos minimo y maximo). La comparación resulta en verdadero si el valor del operando X es nulo o si X es menor
     * que minimo o mayor que maximo.
     *
     * @param minimo operando minimo
     * @param maximo operando maximo
     * @return expresión lógica que genera la comparación de los operandos.
     */
    BooleanComparisonX isNullOrNotBetween(CharacterExpression minimo, CharacterExpression maximo);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y del String que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    CharacterOrderedPairX coalesce(String y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y del valor especial que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    CharacterOrderedPairX coalesce(SpecialCharacterValue y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y de la expresión que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    CharacterOrderedPairX coalesce(Expression y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el String que recibe como argumento
     * (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    CharacterOrderedPairX nullIf(String y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    CharacterOrderedPairX nullIf(SpecialCharacterValue y);

    /**
     * El método <b>nullIf</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como
     * argumento (operando Y). La expresión generada retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     *
     * @param y operando Y
     * @return expresión que retorna el valor nulo si ambos operandos son iguales; de lo contrario, el primer operando.
     */
    CharacterOrderedPairX nullIf(Expression y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el String que recibe como argumento
     * (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    CharacterOrderedPairX max(String y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    CharacterOrderedPairX max(SpecialCharacterValue y);

    /**
     * El método <b>max</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como argumento
     * (operando Y). La expresión generada retorna el mayor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el mayor de los dos operandos.
     */
    CharacterOrderedPairX max(Expression y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el String que recibe como argumento
     * (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    CharacterOrderedPairX min(String y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    CharacterOrderedPairX min(SpecialCharacterValue y);

    /**
     * El método <b>min</b> contruye una expresión que genera la comparación de esta expresión (operando X) con la expresión que recibe como argumento
     * (operando Y). La expresión generada retorna el menor de los dos operandos.
     *
     * @param y operando Y
     * @return expresión que retorna el menor de los dos operandos.
     */
    CharacterOrderedPairX min(Expression y);

    /**
     * El método <b>concat</b> contruye una expresión que genera la concatenación de esta expresión (operando X) con el String que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la concatenación de los dos operandos.
     */
    CharacterOrderedPairX concat(String y);

    /**
     * El método <b>concat</b> contruye una expresión que genera la concatenación de esta expresión (operando X) con el valor especial que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la concatenación de los dos operandos.
     */
    CharacterOrderedPairX concat(SpecialCharacterValue y);

    /**
     * El método <b>concat</b> contruye una expresión que genera la concatenación de esta expresión (operando X) con la expresión que recibe como
     * argumento (operando Y).
     *
     * @param y operando Y
     * @return expresión que retorna la concatenación de los dos operandos.
     */
    CharacterOrderedPairX concat(Expression y);

    /**
     * El método <b>format</b> contruye una expresión que genera esta expresión con el formato especificado por la plantilla que recibe como
     * argumento.
     *
     * @param template plantilla de formato. Todas las letras <b>X</b> de la plantilla son sustituidas por caracteres del valor de la expresión; los
     * demás caracteres de la plantilla son copiados al resultado. Para evitar la sustitución de una letra <b>X</b>, ésta debe estar precedida por una
     * barra oblicua inversa (\). Cuando un carácter es precedido por una barra oblicua inversa se omite la barra y se copia el carácter al resultado;
     * por lo tanto, para copiar una barra oblicua inversa debe escribirla dos veces seguidas (\\). Si la plantilla comienza por <b>(LTR)</b> o
     * <b>(RTL)</b>, la sustitución procede de izquierda a derecha, o de derecha a izquierda, respectivamente. Si la plantilla no comienza por
     * <b>(LTR)</b> ni <b>(RTL)</b>, la sustitución procede de izquierda a derecha, si el valor de la expresión contiene al menos un carácter no
     * numérico; y de derecha a izquierda, si solo contiene caracteres numéricos. La sustitución se detiene cuando todos los caracteres de la
     * plantilla o de la expresión han sido utilizados, lo que ocurra primero. Los últimos o los primeros caracteres de la plantilla podrían no ser
     * parte del resultado, dependiendo de la cantidad de caracteres del primer operando y del sentido en el que procede la sustitución; en otras
     * palabras, si la plantilla contiene un prefijo y un sufijo (sin letras <b>X</b>), y la sustitución procede de izquierda a derecha, el sufijo
     * podría no aparecer en el resultado; y si la sustitución procede de derecha a izquierda, el prefijo podría no aparecer en el resultado.
     *
     * @return expresión que retorna la expresión con el formato especificado.
     */
    CharacterOrderedPairX format(String template);

    /**
     * El método <b>left</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de los primeros <b>len</b> caracteres del valor de esta expresión.
     */
    CharacterOrderedPairX left(NumericExpression len);

    /**
     * El método <b>left</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de los primeros <b>len</b> caracteres del valor de esta expresión.
     */
    CharacterOrderedPairX left(int len);

    /**
     * El método <b>right</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de los últimos <b>len</b> caracteres del valor de esta expresión.
     */
    CharacterOrderedPairX right(NumericExpression len);

    /**
     * El método <b>right</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de los últimos <b>len</b> caracteres del valor de esta expresión.
     */
    CharacterOrderedPairX right(int len);

    /**
     * El método <b>substr</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param pos posición a partir de la que se extrae la subcadena de caracteres; la posición del primer caracter es 1
     * @return expresión que retorna la subcadena de caracteres del valor de esta expresión a partir de la posición especificada.
     */
    CharacterOrderedPairX substr(NumericExpression pos);

    /**
     * El método <b>substr</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param pos posición a partir de la que se extrae la subcadena de caracteres; la posición del primer caracter es 1
     * @return expresión que retorna la subcadena de caracteres del valor de esta expresión a partir de la posición especificada.
     */
    CharacterOrderedPairX substr(int pos);

    /**
     * El método <b>substr</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param pos posición a partir de la que se extrae la subcadena de caracteres; la posición del primer caracter es 1
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de caracteres del valor de esta expresión.
     */
    CharacterNaryVectorX substr(NumericExpression pos, NumericExpression len);

    /**
     * El método <b>substr</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param pos posición a partir de la que se extrae la subcadena de caracteres; la posición del primer caracter es 1
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de caracteres del valor de esta expresión.
     */
    CharacterNaryVectorX substr(NumericExpression pos, int len);

    /**
     * El método <b>substr</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param pos posición a partir de la que se extrae la subcadena de caracteres; la posición del primer caracter es 1
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de caracteres del valor de esta expresión.
     */
    CharacterNaryVectorX substr(int pos, NumericExpression len);

    /**
     * El método <b>substr</b> contruye una expresión que genera una subcadena de caracteres del valor de esta expresión.
     *
     * @param pos posición a partir de la que se extrae la subcadena de caracteres; la posición del primer caracter es 1
     * @param len longitud de la subcadena de caracteres resultante
     * @return expresión que retorna la subcadena de caracteres del valor de esta expresión.
     */
    CharacterNaryVectorX substr(int pos, int len);

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

    /**
     * El método <b>defaultWhenNull</b> contruye una expresión que genera el valor de esta expresión o el valor predeterminado según el tipo de dato
     * de esta expresión, si el valor de esta expresión es nulo. El valor predeterminado de una expresión de tipo Char es ' ' (espacio en blanco); y
     * el de una de tipo String es "" (el String de longitud 0).
     *
     * @return expresión que retorna el valor de la expresión o el valor por default según el tipo de dato de la expresión.
     */
    CharacterScalarX defaultWhenNull();

    /**
     * El método <b>nullWhenDefault</b> contruye una expresión que genera el valor de esta expresión o el valor nulo, si el valor de esta expresión es
     * igual al valor predeterminado según el tipo de dato de esta expresión. El valor predeterminado de una expresión de tipo Char es ' ' (espacio en
     * blanco); y el de una de tipo String es "" (el String de longitud 0).
     *
     * @return expresión que retorna el valor de la expresión o el valor nulo.
     */
    CharacterScalarX nullWhenDefault();

    /**
     * El método <b>lower</b> contruye una expresión que genera el valor de esta expresión en minúsculas.
     *
     * @return expresión que retorna el valor de la expresión en minúsculas.
     */
    CharacterScalarX lower();

    /**
     * El método <b>upper</b> contruye una expresión que genera el valor de esta expresión en mayúsculas.
     *
     * @return expresión que retorna el valor de la expresión en mayúsculas.
     */
    CharacterScalarX upper();

    /**
     * El método <b>capitalize</b> contruye una expresión que genera el valor de esta expresión con el primer caracter en mayúscula.
     *
     * @return expresión que retorna el valor de la expresión con el primer caracter en mayúscula.
     */
    CharacterScalarX capitalize();

    /**
     * El método <b>uncapitalize</b> contruye una expresión que genera el valor de esta expresión con el primer caracter en minúscula.
     *
     * @return expresión que retorna el valor de la expresión con el primer caracter en minúscula.
     */
    CharacterScalarX uncapitalize();

    /**
     * El método <b>trim</b> contruye una expresión que genera el valor de esta expresión sin espacios en blanco al comienzo o al final.
     *
     * @return expresión que retorna el valor de la expresión sin espacios en blanco al comienzo o al final.
     */
    CharacterScalarX trim();

    /**
     * El método <b>ltrim</b> contruye una expresión que genera el valor de esta expresión sin espacios en blanco al comienzo.
     *
     * @return expresión que retorna el valor de la expresión sin espacios en blanco al comienzo.
     */
    CharacterScalarX ltrim();

    /**
     * El método <b>rtrim</b> contruye una expresión que genera el valor de esta expresión sin espacios en blanco al final.
     *
     * @return expresión que retorna el valor de la expresión sin espacios en blanco al final.
     */
    CharacterScalarX rtrim();

    /**
     * El método <b>toChar</b> contruye una expresión que genera el primer caracter del valor de esta expresión.
     *
     * @return expresión que retorna el primer caracter del valor de esta expresión.
     */
    CharacterScalarX toChar();

    /**
     * El método <b>toCharString</b> contruye una expresión que genera el valor de esta expresión convertido en String.
     *
     * @return expresión que retorna el valor de esta expresión convertido en String.
     */
    CharacterScalarX toCharString();

}
