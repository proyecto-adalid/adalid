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

import adalid.core.*;
import adalid.core.expressions.*;

/**
 * @author Jorge Campins
 */
public interface EntityExpression extends Expression {

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
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la entidad que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(Entity y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la instancia que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(Instance y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(EntityExpression y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la entidad que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(Entity y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la instancia que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(Instance y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(EntityExpression y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la entidad que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(Entity y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la instancia que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(Instance y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(EntityExpression y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la entidad que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(Entity y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la instancia que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(Instance y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(EntityExpression y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y de la entidad que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    EntityOrderedPairX coalesce(Entity y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y de la instancia que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    EntityOrderedPairX coalesce(Instance y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta expresión (operando X) y de la expresión que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que retorna el primero de los operandos que no sea nulo.
     */
    EntityOrderedPairX coalesce(EntityExpression y);

}
