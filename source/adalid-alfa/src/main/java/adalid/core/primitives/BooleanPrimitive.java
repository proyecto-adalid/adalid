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
package adalid.core.primitives;

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public abstract class BooleanPrimitive extends Primitive implements State {

    private boolean _primalInitialValue;

    private boolean _primalDefaultValue;

    private Object _calculableValue;

    private Object _initialValue;

    private Object _defaultValue;

    private Object _currentValue;

    /**
     * @return the primal initial value
     */
//  @Override
    public boolean getPrimalInitialValue() {
        return _primalInitialValue;
    }

    /**
     * El método setPrimalInitialValue se utiliza para establecer el valor inicial primitivo de propiedades y parámetros. El valor inicial primitivo
     * se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param initialValue valor inicial primitivo de la propiedad o el parámetro
     */
    public void setPrimalInitialValue(boolean initialValue) {
        checkScope();
        _primalInitialValue = initialValue;
    }

    /**
     * @return the primal default value
     */
//  @Override
    public boolean getPrimalDefaultValue() {
        return _primalDefaultValue;
    }

    /**
     * El método setPrimalDefaultValue se utiliza para establecer el valor por omisión primitivo de propiedades y parámetros. El valor por omisión
     * primitivo se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param defaultValue valor por omisión primitivo de la propiedad o el parámetro
     */
    public void setPrimalDefaultValue(boolean defaultValue) {
        checkScope();
        _primalDefaultValue = defaultValue;
    }

    /**
     * @return the calculable value
     */
    @Override
    public Object getCalculableValue() {
        return _calculableValue;
    }

    /**
     * El método setCalculableValueExpression se utiliza para establecer la expresión para el cálculo del valor de propiedades definidas como columnas
     * calculables (mediante el elemento calculable de la anotación ColumnField).
     *
     * @param expression expresión para el cálculo del valor
     */
    public void setCalculableValueExpression(BooleanExpression expression) {
        checkScope();
        _calculableValue = validCalculableValue(expression) ? expression : null;
    }

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return getInitialValue(_initialValue);
    }

    /**
     * @return true if the initial value is Boolean; false otherwise
     */
    public boolean isPrimitiveInitialValue() {
        return _initialValue instanceof Boolean;
    }

    /**
     * @return true if the initial value is SpecialBooleanValue; false otherwise
     */
    public boolean isSpecialInitialValue() {
        return _initialValue instanceof SpecialBooleanValue;
    }

    /**
     * @return true if the initial value is BooleanExpression; false otherwise
     */
    public boolean isExpressInitialValue() {
        return _initialValue instanceof BooleanExpression;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(Boolean initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(SpecialBooleanValue initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(BooleanExpression initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @return the default value
     */
    @Override
    public Object getDefaultValue() {
        return _defaultValue;
    }

    /**
     * @return true if the default value is Boolean; false otherwise
     */
    public boolean isPrimitiveDefaultValue() {
        return _defaultValue instanceof Boolean;
    }

    /**
     * @return true if the default value is SpecialBooleanValue; false otherwise
     */
    public boolean isSpecialDefaultValue() {
        return _defaultValue instanceof SpecialBooleanValue;
    }

    /**
     * @return true if the default value is BooleanExpression; false otherwise
     */
    public boolean isExpressDefaultValue() {
        return _defaultValue instanceof BooleanExpression;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(Boolean defaultValue) {
        checkScope();
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(SpecialBooleanValue defaultValue) {
        checkScope();
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(BooleanExpression defaultValue) {
        checkScope();
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @return the current value
     */
    @Override
    public Object getCurrentValue() {
        return _currentValue;
    }

    /**
     * @return true if the current value is Boolean; false otherwise
     */
    public boolean isPrimitiveCurrentValue() {
        return _currentValue instanceof Boolean;
    }

    /**
     * @return true if the current value is SpecialBooleanValue; false otherwise
     */
    public boolean isSpecialCurrentValue() {
        return _currentValue instanceof SpecialBooleanValue;
    }

    /**
     * @return true if the current value is BooleanExpression; false otherwise
     */
    public boolean isExpressCurrentValue() {
        return _currentValue instanceof BooleanExpression;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(Boolean currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(SpecialBooleanValue currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(BooleanExpression currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método keepTallyOn se utiliza para establecer las propiedades donde se lleva la cuenta de los valores verdaderos de esta propiedad.
     *
     * @param properties una o más propiedades donde se lleva la cuenta de los valores verdaderos de esta propiedad; deben ser propiedades numéricas,
     * no calculables, de una entidad directamente referenciada
     */
    public void keepTallyOn(Property... properties) {
        checkScope();
        addAggregate(RowsAggregateOp.TALLY, properties);
    }

    // <editor-fold defaultstate="collapsed" desc="BooleanExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Boolean.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Boolean.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isTrue() {
        return XB.Boolean.Comparison.isTrue(this);
    }

    @Override
    public BooleanComparisonX isFalse() {
        return XB.Boolean.Comparison.isFalse(this);
    }

    @Override
    public BooleanComparisonX isNotTrue() {
        return isNullOrFalse();
    }

    @Override
    public BooleanComparisonX isNotFalse() {
        return isNullOrTrue();
    }

    @Override
    public BooleanComparisonX isNullOrTrue() {
        return XB.Boolean.Comparison.isNullOrTrue(this);
    }

    @Override
    public BooleanComparisonX isNullOrFalse() {
        return XB.Boolean.Comparison.isNullOrFalse(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(BooleanExpression y) {
        return XB.Boolean.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(BooleanExpression y) {
        return XB.Boolean.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(BooleanExpression y) {
        return XB.Boolean.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(BooleanExpression y) {
        return XB.Boolean.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public EntityConditionalX then(Entity value) {
        return XB.Entity.Conditional.then(this, value);
    }

    @Override
    public EntityConditionalX then(Instance value) {
        return XB.Entity.Conditional.then(this, value);
    }

    @Override
    public EntityConditionalX then(SpecialEntityValue value) {
        return XB.Entity.Conditional.then(this, value);
    }

    @Override
    public EntityConditionalX then(EntityExpression value) {
        return XB.Entity.Conditional.then(this, value);
    }

    @Override
    public BooleanConditionalX then(Boolean value) {
        return XB.Boolean.Conditional.then(this, value);
    }

    @Override
    public BooleanConditionalX then(SpecialBooleanValue value) {
        return XB.Boolean.Conditional.then(this, value);
    }

    @Override
    public BooleanConditionalX then(BooleanExpression value) {
        return XB.Boolean.Conditional.then(this, value);
    }

    @Override
    public CharacterConditionalX then(String value) {
        return XB.Character.Conditional.then(this, value);
    }

    @Override
    public CharacterConditionalX then(SpecialCharacterValue value) {
        return XB.Character.Conditional.then(this, value);
    }

    @Override
    public CharacterConditionalX then(Expression value) {
        return XB.Character.Conditional.then(this, toCharacterExpression(value));
    }

    @Override
    public NumericConditionalX then(Number value) {
        return XB.Numeric.Conditional.then(this, value);
    }

    @Override
    public NumericConditionalX then(SpecialNumericValue value) {
        return XB.Numeric.Conditional.then(this, value);
    }

    @Override
    public NumericConditionalX then(NumericExpression value) {
        return XB.Numeric.Conditional.then(this, value);
    }

    @Override
    public TemporalConditionalX then(java.util.Date value) {
        return XB.Temporal.Conditional.then(this, value);
    }

    @Override
    public TemporalConditionalX then(SpecialTemporalValue value) {
        return XB.Temporal.Conditional.then(this, value);
    }

    @Override
    public TemporalConditionalX then(TemporalExpression value) {
        return XB.Temporal.Conditional.then(this, value);
    }

    @Override
    public BooleanOrderedPairX and(BooleanExpression y) {
        return XB.Boolean.OrderedPair.and(this, y);
    }

    @Override
    public BooleanOrderedPairX nand(BooleanExpression y) {
        return XB.Boolean.OrderedPair.nand(this, y);
    }

    @Override
    public BooleanOrderedPairX or(BooleanExpression y) {
        return XB.Boolean.OrderedPair.or(this, y);
    }

    @Override
    public BooleanOrderedPairX nor(BooleanExpression y) {
        return XB.Boolean.OrderedPair.nor(this, y);
    }

    @Override
    public BooleanOrderedPairX xor(BooleanExpression y) {
        return XB.Boolean.OrderedPair.xor(this, y);
    }

    @Override
    public BooleanOrderedPairX xnor(BooleanExpression y) {
        return XB.Boolean.OrderedPair.xnor(this, y);
    }

    @Override
    public BooleanOrderedPairX implies(BooleanExpression y) {
        return XB.Boolean.OrderedPair.xImpliesY(this, y);
    }

    @Override
    public BooleanScalarX not() {
        return XB.Boolean.Scalar.not(this);
    }

    @Override
    public CharacterScalarX toCharString() {
        return XB.Character.Scalar.toCharString(this);
    }

    private CharacterExpression toCharacterExpression(Object x) {
        return x instanceof CharacterExpression ? (CharacterExpression) x : XB.toCharString(x);
    }

    @Override
    public Transition transitionTo(State y) {
        return new Transition(this, y);
    }

    @Override
    public Trigger trigger(ProcessOperation operation) {
        return new Trigger(this, operation);
    }

//  private String _defaultErrorMessage;
    private final Map<Locale, String> _localizedErrorMessage = new LinkedHashMap<>();

    /**
     * @return the default error message
     */
    @Override
    public String getDefaultErrorMessage() {
        return getLocalizedErrorMessage(null);
    }

    /**
     * El método setDefaultErrorMessage se utiliza para establecer el mensaje de error asociado a la expresión que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * @param defaultErrorMessage mensaje de error asociado a la expresión
     */
    @Override
    public void setDefaultErrorMessage(String defaultErrorMessage) {
        setLocalizedErrorMessage(null, defaultErrorMessage);
    }

    /**
     * @param locale the locale for the error message
     * @return the localized error message
     */
    @Override
    public String getLocalizedErrorMessage(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedErrorMessage.get(l);
    }

    /**
     * El método setLocalizedErrorMessage se utiliza para establecer el mensaje de error asociado a la expresión que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * @param locale configuración regional
     * @param localizedErrorMessage mensaje de error asociado a la expresión
     */
    @Override
    public void setLocalizedErrorMessage(Locale locale, String localizedErrorMessage) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedErrorMessage == null) {
            _localizedErrorMessage.remove(l);
        } else {
            _localizedErrorMessage.put(l, localizedErrorMessage);
        }
    }
    // </editor-fold>

}
