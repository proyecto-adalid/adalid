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
import adalid.core.enums.*;
import adalid.core.expressions.*;
import java.util.Locale;

/**
 * @author Jorge Campins
 */
public interface BooleanExpression extends Expression {

    default boolean isLogicalTautology() {
        return false;
    }

    default void setLogicalTautology(boolean tautology) {
    }

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
     * El método <b>isTrue</b> contruye una expresión lógica que genera la evaluación de esta expresión. La evaluación resulta en verdadero si el
     * valor de la expresión es verdadero.
     *
     * @return expresión lógica que genera la evaluación de esta expresión.
     */
    BooleanComparisonX isTrue();

    /**
     * El método <b>isFalse</b> contruye una expresión lógica que genera la evaluación de esta expresión. La evaluación resulta en verdadero si el
     * valor de la expresión es falso.
     *
     * @return expresión lógica que genera la evaluación de esta expresión.
     */
    BooleanComparisonX isFalse();

    /**
     * El método <b>isNotTrue</b> contruye una expresión lógica que genera la evaluación de esta expresión. La evaluación resulta en verdadero si el
     * valor de la expresión es nulo o es falso.
     *
     * @return expresión lógica que genera la evaluación de esta expresión.
     */
    BooleanComparisonX isNotTrue();

    /**
     * El método <b>isNotFalse</b> contruye una expresión lógica que genera la evaluación de esta expresión. La evaluación resulta en verdadero si el
     * valor de la expresión es nulo o es verdadero.
     *
     * @return expresión lógica que genera la evaluación de esta expresión.
     */
    BooleanComparisonX isNotFalse();

    /**
     * El método <b>isNullOrTrue</b> contruye una expresión lógica que genera la evaluación de esta expresión. La evaluación resulta en verdadero si
     * el valor de la expresión es nulo o es verdadero.
     *
     * @return expresión lógica que genera la evaluación de esta expresión.
     */
    BooleanComparisonX isNullOrTrue();

    /**
     * El método <b>isNullOrFalse</b> contruye una expresión lógica que genera la evaluación de esta expresión. La evaluación resulta en verdadero si
     * el valor de la expresión es nulo o es falso.
     *
     * @return expresión lógica que genera la evaluación de esta expresión.
     */
    BooleanComparisonX isNullOrFalse();

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(BooleanExpression y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(BooleanExpression y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(BooleanExpression y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(BooleanExpression y);

    /**
     * El método <b>and</b> contruye una expresión lógica que genera la conjunción (AND) de esta expresión (operando X) con la expresión que recibe
     * como argumento (operando Y). La conjunción es un operador lógico que resulta en verdadero si ambos operandos son verdadero.
     *
     * @param y operando Y
     * @return expresión lógica que genera la conjunción de ambos operandos.
     */
    BooleanOrderedPairX and(BooleanExpression y);

    /**
     * El método <b>nand</b> contruye una expresión lógica que genera la negación alternativa (NAND) de esta expresión (operando X) con la expresión
     * que recibe como argumento (operando Y). La negación alternativa es un operador lógico que resulta en verdadero si uno de los operandos es
     * falso. Es equivalente a la negación (NOT) de la conjunción (AND).
     *
     * @param y operando Y
     * @return expresión lógica que genera la negación alternativa de ambos operandos.
     */
    BooleanOrderedPairX nand(BooleanExpression y);

    /**
     * El método <b>or</b> contruye una expresión lógica que genera la disyunción (OR) de esta expresión (operando X) con la expresión que recibe como
     * argumento (operando Y). La disyunción es un operador lógico que resulta en verdadero si uno de los operandos es verdadero.
     *
     * @param y operando Y
     * @return expresión lógica que genera la disyunción de ambos operandos.
     */
    BooleanOrderedPairX or(BooleanExpression y);

    /**
     * El método <b>nor</b> contruye una expresión lógica que genera la negación conjunta (NOR) de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La negación conjunta es un operador lógico que resulta en verdadero si ambos operandos son falso. Es
     * equivalente a la negación (NOT) de la disyunción (OR).
     *
     * @param y operando Y
     * @return expresión lógica que genera la negación conjunta de ambos operandos.
     */
    BooleanOrderedPairX nor(BooleanExpression y);

    /**
     * El método <b>xor</b> contruye una expresión lógica que genera la disyunción exclusiva (XOR) de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La disyunción exclusiva es un operador lógico que resulta en verdadero si uno y solo uno de los operandos
     * es verdadero.
     *
     * @param y operando Y
     * @return expresión lógica que genera la disyunción exclusiva de ambos operandos.
     */
    BooleanOrderedPairX xor(BooleanExpression y);

    /**
     * El método <b>xnor</b> contruye una expresión lógica que genera la equivalencia (XNOR) de esta expresión (operando X) con la expresión que
     * recibe como argumento (operando Y). La equivalencia es un operador lógico que resulta en verdadero si ambos operandos son verdadero ó si ambos
     * son falso. Es equivalente a la negación (NOT) de la disyunción exclusiva (XOR).
     *
     * @param y operando Y
     * @return expresión lógica que genera la equivalencia de ambos operandos.
     */
    BooleanOrderedPairX xnor(BooleanExpression y);

    /**
     * El método <b>implies</b> contruye una expresión lógica que genera la condicional material (X_IMPLIES_Y) de esta expresión (operando X) con la
     * expresión que recibe como argumento (operando Y). La condicional material es un operador lógico que resulta en falso solo si X es verdadero y Y
     * es falso. Es equivalente a la disyunción (OR) de la negación (NOT) de X con Y (NOT X OR Y).
     *
     * @param y operando Y
     * @return expresión lógica que genera la condicional material de ambos operandos.
     */
    BooleanOrderedPairX implies(BooleanExpression y);

    /**
     * El método <b>not</b> contruye una expresión lógica que genera la negación (NOT) de esta expresión (operando X). La negación es un operador
     * lógico que resulta en verdadero si X es falso y en falso si X es verdadero.
     *
     * @return expresión lógica que genera la negación de esta expresión.
     */
    BooleanScalarX not();

    /**
     * El método <b>toCharString</b> contruye una expresión que genera la conversión de esta expresión a una expresión alfanumérica.
     *
     * @return expresión que genera la conversión de esta expresión.
     */
    CharacterScalarX toCharString();

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    EntityConditionalX then(Entity value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    EntityConditionalX then(Instance value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    EntityConditionalX then(SpecialEntityValue value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    EntityConditionalX then(EntityExpression value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    BooleanConditionalX then(Boolean value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    BooleanConditionalX then(SpecialBooleanValue value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    BooleanConditionalX then(BooleanExpression value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    CharacterConditionalX then(String value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    CharacterConditionalX then(SpecialCharacterValue value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    CharacterConditionalX then(Expression value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    NumericConditionalX then(Number value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    NumericConditionalX then(SpecialNumericValue value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    NumericConditionalX then(NumericExpression value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    TemporalConditionalX then(java.util.Date value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    TemporalConditionalX then(SpecialTemporalValue value);

    /**
     * El método <b>then</b> contruye una expresión condicional que genera la evaluación de esta expresión y da como resultado el valor que recibe
     * como argumento (value), si el valor de esta expresión es verdadero; o el valor nulo, si el valor de esta expresión no es verdadero.
     *
     * @param value value
     * @return value, si el valor de la expresión es verdadero; de lo contrario, el valor nulo.
     */
    TemporalConditionalX then(TemporalExpression value);

    Trigger trigger(ProcessOperation operation);

    String getDefaultErrorMessage();

    /**
     * El método <b>setDefaultErrorMessage</b> se utiliza para establecer el mensaje de error asociado a la expresión que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * @param defaultErrorMessage mensaje de error asociado a la expresión
     */
    void setDefaultErrorMessage(String defaultErrorMessage);

    String getLocalizedErrorMessage(Locale locale);

    /**
     * El método <b>setLocalizedErrorMessage</b> se utiliza para establecer el mensaje de error asociado a la expresión que se almacena en el archivo
     * de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * @param locale configuración regional
     * @param localizedErrorMessage mensaje de error asociado a la expresión
     */
    void setLocalizedErrorMessage(Locale locale, String localizedErrorMessage);

}
