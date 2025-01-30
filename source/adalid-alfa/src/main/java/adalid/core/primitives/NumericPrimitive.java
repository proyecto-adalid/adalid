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

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.sql.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class NumericPrimitive extends Primitive implements IntervalizedArtifact, NumericExpression {

    private static final String EOL = "\n";

    private Number _primalInitialValue = 0;

    private Number _primalDefaultValue = 0;

    private Number _primalMinValue;

    private Number _primalMaxValue;

    private Object _calculableValue;

    private Object _initialValue;

    private Object _defaultValue;

    private Object _currentValue;

    private Object _minValue;

    private Object _maxValue;

    private Number _minNumber;

    private Number _maxNumber;

    private final Map<Locale, String> _localizedRangeErrorMessage = new LinkedHashMap<>();

    private NumericFieldType _converterType = NumericFieldType.UNSPECIFIED;

    private int _knobStep = 1;

    private int _divisor = -1; // Constants.DEFAULT_NUMERIC_DIVISOR;

    private DivisorRule _divisorRule = DivisorRule.UNSPECIFIED;

    private String _symbol = "";

    private SymbolPosition _symbolPosition = SymbolPosition.UNSPECIFIED;

    private Boolean _symbolSeparator;

    private String _specialConverterName = "";

    private String _specialValidatorName = "";

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            if (isPrimaryKeyProperty() || isSequenceProperty()) {
                _initialValue = null;
                _defaultValue = null;
                _minValue = null;
                _maxValue = null;
//              _minNumber = null; // 20200104
//              _maxNumber = null; // 20200104
            }
            if (isPrimaryKeyProperty() || isSequenceProperty() || isVersionProperty()) {
                _calculableValue = null;
                _currentValue = null;
                _divisor = 0;
                _divisorRule = DivisorRule.UNSPECIFIED;
                _symbol = null;
                _symbolSeparator = false;
                _symbolPosition = SymbolPosition.UNSPECIFIED;
                _converterType = NumericFieldType.UNSPECIFIED;
                _specialConverterName = null;
                _specialValidatorName = null;
            }
        }
        return ok;
    }

    /**
     * @return the primal initial value
     */
//  @Override
    public Number getPrimalInitialValue() {
        return _primalInitialValue;
    }

    /**
     * El método setPrimalInitialValue se utiliza para establecer el valor inicial primitivo de propiedades y parámetros. El valor inicial primitivo
     * se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param initialValue valor inicial primitivo de la propiedad o el parámetro
     */
    public void setPrimalInitialValue(Number initialValue) {
        checkScope();
        _primalInitialValue = initialValue == null ? 0 : initialValue;
    }

    /**
     * @return the primal default value
     */
//  @Override
    public Number getPrimalDefaultValue() {
        return _primalDefaultValue;
    }

    /**
     * El método setPrimalDefaultValue se utiliza para establecer el valor por omisión primitivo de propiedades y parámetros. El valor por omisión
     * primitivo se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param defaultValue valor por omisión primitivo de la propiedad o el parámetro
     */
    public void setPrimalDefaultValue(Number defaultValue) {
        checkScope();
        _primalDefaultValue = defaultValue == null ? 0 : defaultValue;
    }

    /**
     * @return the primal minimum value
     */
//  @Override
    public Number getPrimalMinValue() {
        return _primalMinValue == null ? getPrimitiveMinValue() : _primalMinValue;
    }

    /**
     * El método setPrimalMinValue se utiliza para establecer el valor mínimo primitivo de propiedades y parámetros. El valor mínimo primitivo se
     * utiliza cuando el valor mínimo no es primitivo, y se requiere que lo sea.
     *
     * @param minValue valor mínimo primitivo de la propiedad o el parámetro
     */
    public void setPrimalMinValue(Number minValue) {
        checkScope();
        _primalMinValue = minValue;
    }

    /**
     * @return the primal maximum value
     */
//  @Override
    public Number getPrimalMaxValue() {
        return _primalMaxValue == null ? getPrimitiveMaxValue() : _primalMaxValue;
    }

    /**
     * El método setPrimalMaxValue se utiliza para establecer el valor máximo primitivo de propiedades y parámetros. El valor máximo primitivo se
     * utiliza cuando el valor máximo no es primitivo, y se requiere que lo sea.
     *
     * @param maxValue valor máximo primitivo de la propiedad o el parámetro
     */
    public void setPrimalMaxValue(Number maxValue) {
        checkScope();
        _primalMaxValue = maxValue;
    }

    /**
     * @return the calculable value
     */
    @Override
    public Object getCalculableValue() {
        return toDataType(_calculableValue);
    }

    /**
     * El método setCalculableValueExpression se utiliza para establecer la expresión para el cálculo del valor de propiedades definidas como columnas
     * calculables (mediante el elemento calculable de la anotación ColumnField).
     *
     * @param expression expresión para el cálculo del valor
     */
    public void setCalculableValueExpression(NumericExpression expression) {
        checkScope();
        _calculableValue = validCalculableValue(expression) ? expression : null;
    }

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return toDataType(getInitialValue(_initialValue));
    }

    /**
     * @return true if the initial value is Number; false otherwise
     */
    public boolean isPrimitiveInitialValue() {
        return _initialValue instanceof Number;
    }

    /**
     * @return true if the initial value is SpecialNumericValue; false otherwise
     */
    public boolean isSpecialInitialValue() {
        return _initialValue instanceof SpecialNumericValue;
    }

    /**
     * @return true if the initial value is NumericExpression; false otherwise
     */
    public boolean isExpressInitialValue() {
        return _initialValue instanceof NumericExpression;
    }

    /**
     * @return true if the initial value is not null; false otherwise
     */
    public boolean isDefinedInitialValue() {
        return _initialValue != null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(Number initialValue) {
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
    public void setInitialValue(SpecialNumericValue initialValue) {
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
    public void setInitialValue(NumericExpression initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @return the default value
     */
    @Override
    public Object getDefaultValue() {
        return toDataType(_defaultValue);
    }

    /**
     * @return true if the default value is Number; false otherwise
     */
    public boolean isPrimitiveDefaultValue() {
        return _defaultValue instanceof Number;
    }

    /**
     * @return true if the default value is SpecialNumericValue; false otherwise
     */
    public boolean isSpecialDefaultValue() {
        return _defaultValue instanceof SpecialNumericValue;
    }

    /**
     * @return true if the default value is NumericExpression; false otherwise
     */
    public boolean isExpressDefaultValue() {
        return _defaultValue instanceof NumericExpression;
    }

    /**
     * @return true if the default value is not null; false otherwise
     */
    public boolean isDefinedDefaultValue() {
        return _defaultValue != null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(Number defaultValue) {
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
    public void setDefaultValue(SpecialNumericValue defaultValue) {
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
    public void setDefaultValue(NumericExpression defaultValue) {
        checkScope();
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @return the current value
     */
    @Override
    public Object getCurrentValue() {
        return toDataType(_currentValue);
    }

    /**
     * @return true if the current value is Number; false otherwise
     */
    public boolean isPrimitiveCurrentValue() {
        return _currentValue instanceof Number;
    }

    /**
     * @return true if the current value is SpecialNumericValue; false otherwise
     */
    public boolean isSpecialCurrentValue() {
        return _currentValue instanceof SpecialNumericValue;
    }

    /**
     * @return true if the current value is NumericExpression; false otherwise
     */
    public boolean isExpressCurrentValue() {
        return _currentValue instanceof NumericExpression;
    }

    /**
     * @return true if the current value is not null; false otherwise
     */
    public boolean isDefinedCurrentValue() {
        return _currentValue != null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(Number currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(SpecialNumericValue currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(NumericExpression currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @return the minimum value
     */
    @Override
    public Object getMinValue() {
        return _minValue == null ? knob() ? minKnob() : _minNumber : _minValue instanceof Number ? primitiveMinValue() : toDataType(_minValue);
    }

    /**
     * @return the primitive minimum value
     */
    public Number getPrimitiveMinValue() {
        return _minValue instanceof Number ? primitiveMinValue() : _minNumber;
    }

    /**
     * @return the primitive minimum value
     */
    private Number primitiveMinValue() {
        Number value = (Number) _minValue;
        return _minNumber == null ? value : compare(value, _minNumber) > 0 ? value : _minNumber;
    }

    /**
     * @return true if the minimum value is Number; false otherwise
     */
    public boolean isPrimitiveMinValue() {
        return _minNumber != null || _minValue instanceof Number;
    }

    /**
     * @return true if the minimum value is SpecialNumericValue; false otherwise
     */
    public boolean isSpecialMinValue() {
        return _minValue instanceof SpecialNumericValue;
    }

    /**
     * @return true if the minimum value is NumericExpression; false otherwise
     */
    public boolean isExpressMinValue() {
        return _minValue instanceof NumericExpression;
    }

    /**
     * @return true if the minimum value is not null; false otherwise
     */
    public boolean isDefinedMinValue() {
        return _minValue != null;
    }

    /**
     * El método setMinValue se utiliza para establecer el valor mínimo de propiedades y parámetros.
     *
     * @param minValue valor mínimo de la propiedad o el parámetro
     */
    public void setMinValue(Number minValue) {
        checkScope();
        _minValue = minValue;
    }

    /**
     * El método setMinValue se utiliza para establecer el valor mínimo de propiedades y parámetros.
     *
     * @param minValue valor mínimo de la propiedad o el parámetro
     */
    public void setMinValue(SpecialNumericValue minValue) {
        checkScope();
        _minValue = minValue;
    }

    /**
     * El método setMinValue se utiliza para establecer el valor mínimo de propiedades y parámetros.
     *
     * @param minValue valor mínimo de la propiedad o el parámetro
     */
    public void setMinValue(NumericExpression minValue) {
        checkScope();
        _minValue = validMinimumValue(minValue) ? minValue : null;
    }

    /**
     * @return the maximum value
     */
    @Override
    public Object getMaxValue() {
        return _maxValue == null ? knob() ? maxKnob() : _maxNumber : _maxValue instanceof Number ? primitiveMaxValue() : toDataType(_maxValue);
    }

    /**
     * @return the primitive maximum value
     */
    public Number getPrimitiveMaxValue() {
        return _maxValue instanceof Number ? primitiveMaxValue() : _maxNumber;
    }

    /**
     * @return the primitive maximum value
     */
    private Number primitiveMaxValue() {
        Number value = (Number) _maxValue;
        return _maxNumber == null ? value : compare(value, _maxNumber) < 0 ? value : _maxNumber;
    }

    /**
     * @return true if the maximum value is Number; false otherwise
     */
    public boolean isPrimitiveMaxValue() {
        return _maxNumber != null || _maxValue instanceof Number;
    }

    /**
     * @return true if the maximum value is SpecialNumericValue; false otherwise
     */
    public boolean isSpecialMaxValue() {
        return _maxValue instanceof SpecialNumericValue;
    }

    /**
     * @return true if the maximum value is NumericExpression; false otherwise
     */
    public boolean isExpressMaxValue() {
        return _maxValue instanceof NumericExpression;
    }

    /**
     * @return true if the maximum value is not null; false otherwise
     */
    public boolean isDefinedMaxValue() {
        return _maxValue != null;
    }

    /**
     * El método setMaxValue se utiliza para establecer el valor máximo de propiedades y parámetros.
     *
     * @param maxValue valor máximo de la propiedad o el parámetro
     */
    public void setMaxValue(Number maxValue) {
        checkScope();
        _maxValue = maxValue;
    }

    /**
     * El método setMaxValue se utiliza para establecer el valor máximo de propiedades y parámetros.
     *
     * @param maxValue valor máximo de la propiedad o el parámetro
     */
    public void setMaxValue(SpecialNumericValue maxValue) {
        checkScope();
        _maxValue = maxValue;
    }

    /**
     * El método setMaxValue se utiliza para establecer el valor máximo de propiedades y parámetros.
     *
     * @param maxValue valor máximo de la propiedad o el parámetro
     */
    public void setMaxValue(NumericExpression maxValue) {
        checkScope();
        _maxValue = validMaximumValue(maxValue) ? maxValue : null;
    }

    /**
     * @return the minimum number
     */
    public Number getMinNumber() {
        return _minNumber;
    }

    /**
     * @param minNumber the minimum number to set
     */
    protected void setMinNumber(Number minNumber) {
        _minNumber = minNumber;
    }

    /**
     * @return the maximum number
     */
    public Number getMaxNumber() {
        return _maxNumber;
    }

    /**
     * @param maxNumber the maximum number to set
     */
    protected void setMaxNumber(Number maxNumber) {
        _maxNumber = maxNumber;
    }

    private int compare(Number x, Number y) {
        BigDecimal bx = new BigDecimal(x.toString());
        BigDecimal by = new BigDecimal(y.toString());
        return bx.compareTo(by);
    }

    private Object toDataType(Object value) {
        if (value instanceof NumericExpression nx) {
            Operator nxop = nx.getOperator();
            Class<?> nxdt = nx instanceof NumericPrimitive ? nx.getDataType() : null;
            Class<?> mydt = getDataType();
            if (mydt == null || mydt.equals(nxdt) || nxop == null) {
            } else if (Byte.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_BYTE.equals(nxop)) {
                    return nx.toByte();
                }
            } else if (Short.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_SHORT.equals(nxop)) {
                    return nx.toShort();
                }
            } else if (Integer.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_INTEGER.equals(nxop)) {
                    return nx.toInteger();
                }
            } else if (Long.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_LONG.equals(nxop)) {
                    return nx.toLong();
                }
            } else if (Float.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_FLOAT.equals(nxop)) {
                    return nx.toFloat();
                }
            } else if (Double.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_DOUBLE.equals(nxop)) {
                    return nx.toDouble();
                }
            } else if (BigInteger.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_BIG_INTEGER.equals(nxop)) {
                    return nx.toBigInteger();
                }
            } else if (BigDecimal.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_BIG_DECIMAL.equals(nxop)) {
                    return nx.toBigDecimal();
                }
            }
        }
        return value;
    }

    public int getIntegerDigits() {
        int maxDigits = digits(_maxValue, _primalMaxValue);
        int minDigits = digits(_minValue, _primalMinValue);
        return maxDigits > 0 && minDigits > 0 ? maxDigits > minDigits ? maxDigits : minDigits : 0;
    }

    private int digits(Object object, Number number) {
        Object obj = integral(object) ? object : integral(number) ? number : null;
        return obj == null ? 0 : StringUtils.removeStart(StringUtils.substringBefore(obj.toString(), "."), "-").length();
    }

    private boolean integral(Object obj) {
        return obj instanceof Byte
            || obj instanceof Short
            || obj instanceof Integer
            || obj instanceof Long
            || obj instanceof BigInteger
            || obj instanceof BigDecimal;
    }

    /**
     * @return the default range error message
     */
    @Override
    public String getDefaultRangeErrorMessage() {
        return getLocalizedRangeErrorMessage(null);
    }

    /**
     * El método setDefaultRangeErrorMessage se utiliza para establecer el mensaje de error asociado al rango de valores que se almacena en el archivo
     * de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * El rango de valores es definido mediante los métodos setMinValue y setMaxValue de propiedades y parámetros numéricos y temporales.
     *
     * @param message mensaje de error. El mensaje puede contener los marcadores de posición {0}, {1} y {2} para mostrar al usuario el valor
     * suministrado, el valor mínimo permitido y el valor máximo permitido, respectivamente.
     */
    @Override
    public void setDefaultRangeErrorMessage(String message) {
        setLocalizedRangeErrorMessage(null, message);
    }

    /**
     * @param locale the locale for the range error message
     * @return the localized range error message
     */
    @Override
    public String getLocalizedRangeErrorMessage(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedRangeErrorMessage.get(l);
    }

    /**
     * El método setLocalizedRangeErrorMessage se utiliza para establecer el mensaje de error asociado al rango de valores que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * El rango de valores es definido mediante los métodos setMinValue y setMaxValue de propiedades y parámetros numéricos y temporales.
     *
     * @param locale configuración regional
     * @param message mensaje de error. El mensaje puede contener los marcadores de posición {0}, {1} y {2} para mostrar al usuario el valor
     * suministrado, el valor mínimo permitido y el valor máximo permitido, respectivamente.
     */
    @Override
    public void setLocalizedRangeErrorMessage(Locale locale, String message) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (message == null) {
            _localizedRangeErrorMessage.remove(l);
        } else {
            _localizedRangeErrorMessage.put(l, message);
        }
    }

    /**
     * @return the data min
     */
    @Override
    public Object getDataGenMinValue() {
        Object dataGenMin = super.getDataGenMinValue();
        if (dataGenMin != null) {
            return dataGenMin;
        }
        if (_minValue == null) {
            return 0;
        }
        Integer min = objectToInteger(_minValue);
        return (min != null) ? min : javaGenMin();
    }

    /**
     * @return the data max
     */
    @Override
    public Object getDataGenMaxValue() {
        Object dataGenMax = super.getDataGenMaxValue();
        if (dataGenMax != null) {
            return dataGenMax;
        }
        if (_maxValue == null) {
            return javaGenMax();
        }
        Integer max = objectToInteger(_maxValue);
        return (max != null) ? max : javaGenMax();
    }

    private Integer objectToInteger(Object obj) {
        if (obj instanceof Number) {
            try {
                return NumUtils.newInteger(obj);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private Number javaGenMin() {
        return isByteData() ? Byte.MIN_VALUE : isShortData() ? Short.MIN_VALUE : Integer.MIN_VALUE;
    }

    private Number javaGenMax() {
        return isByteData() ? Byte.MAX_VALUE : isShortData() ? Short.MAX_VALUE : 2147483640; // Integer.MAX_VALUE; // 2100000000; // 1000000000;
    }

    /**
     * @return the converter type
     */
    public NumericFieldType getConverterType() {
        return _converterType;
    }

    public void setConverterType(NumericFieldType type) {
        checkScope();
        _converterType = type == null ? NumericFieldType.UNSPECIFIED : type;
    }

    private boolean knob() {
        return NumericFieldType.KNOB.equals(_converterType);
    }

    private int minKnob() {
        int max = _maxValue instanceof Number number ? IntUtils.valueOf(NumUtils.toInteger(number), 100) : 100;
        return max > 0 ? 0 : max - 100;
    }

    private int maxKnob() {
        int min = _minValue instanceof Number number ? IntUtils.valueOf(NumUtils.toInteger(number), 0) : 0;
        return min < 100 ? 100 : min + 100;
    }

    /**
     * @return the knob component step value
     */
    public int getKnobStep() {
        Object minValue = getMinValue();
        Object maxValue = getMaxValue();
        int min = minValue instanceof Number number ? NumUtils.toInteger(number) : 0;
        int max = maxValue instanceof Number number ? NumUtils.toInteger(number) : 100;
        int diff = max - min;
        return diff < 1 || _knobStep < 1 || _knobStep > diff ? 1 : _knobStep;
    }

    public void setKnobStep(int step) {
        checkScope();
        _knobStep = step;
    }

    /**
     * @return the divisor
     */
    public int getDivisor() {
        return _divisor;
    }

    public void setDivisor(int divisor) {
        checkScope();
        _divisor = divisor;
    }

    /**
     * @return the divisor rule
     */
    public DivisorRule getDivisorRule() {
        return _divisorRule;
    }

    /**
     * @param rule the divisor rule to set
     */
    public void setDivisorRule(DivisorRule rule) {
        checkScope();
        _divisorRule = rule == null ? DivisorRule.UNSPECIFIED : rule;
    }

    /**
     * @return the symbol or unit
     */
    public String getSymbol() {
        return _symbol;
    }

    public void setSymbol(String symbol) {
        checkScope();
        _symbol = symbol;
    }

    /**
     * @return the symbol or unit position
     */
    public SymbolPosition getSymbolPosition() {
        return _symbolPosition;
    }

    public void setSymbolPosition(SymbolPosition position) {
        checkScope();
        _symbolPosition = position == null ? SymbolPosition.UNSPECIFIED : position;
    }

    /**
     * @return the symbol separator indicator
     */
    public Boolean isSymbolSeparator() {
        return _symbolSeparator;
    }

    public void setSymbolSeparator(boolean separator) {
        checkScope();
        _symbolSeparator = separator;
    }

    /**
     * @return the special converter name
     */
    public String getSpecialConverterName() {
        return _specialConverterName;
    }

    public void setSpecialConverterName(String converter) {
        checkScope();
        _specialConverterName = converter;
    }

    /**
     * @return the special validator name
     */
    public String getSpecialValidatorName() {
        return _specialValidatorName;
    }

    public void setSpecialValidatorName(String validator) {
        checkScope();
        _specialValidatorName = validator;
    }
//
    // <editor-fold defaultstate="collapsed" desc="keepMaximumOn, keepMinimumOn">
//  /**
//   * El método keepMaximumOn se utiliza para establecer la propiedad donde se mantiene el mayor valor no nulo de esta propiedad.
//   *
//   * @param properties una o mas propiedades donde se mantiene el mayor valor no nulo de esta propiedad; deben ser propiedades numéricas, no
//   * calculables, de una entidad directamente referenciada
//   */
//  public void keepMaximumOn(Property... properties) {
//      addAggregate(RowsAggregateOp.MAXIMUM, properties);
//  }
//
//  /**
//   * El método keepMinimumOn se utiliza para establecer la propiedad donde se mantiene el menor valor no nulo de esta propiedad.
//   *
//   * @param properties una o mas propiedades donde se mantiene el menor valor no nulo de esta propiedad; deben ser propiedades numéricas, no
//   * calculables, de una entidad directamente referenciada
//   */
//  public void keepMinimumOn(Property... properties) {
//      addAggregate(RowsAggregateOp.MINIMUM, properties);
//  }
    // </editor-fold>

    /**
     * El método keepSumOn se utiliza para establecer las propiedades donde se lleva la suma de los valores no nulos de esta propiedad.
     *
     * @param properties una o más propiedades donde se lleva la suma de los valores no nulos de esta propiedad; deben ser propiedades numéricas, no
     * calculables, de una entidad directamente referenciada
     */
    public void keepSumOn(Property... properties) {
        addAggregate(RowsAggregateOp.SUM, properties);
    }

    /**
     * El método keepTallyOn se utiliza para establecer las propiedades donde se lleva la cuenta de los valores no nulos y diferentes de cero de esta
     * propiedad.
     *
     * @param properties una o más propiedades donde se lleva la cuenta de los valores no nulos y diferentes de cero de esta propiedad; deben ser
     * propiedades numéricas, no calculables, de una entidad directamente referenciada
     */
    public void keepTallyOn(Property... properties) {
        addAggregate(RowsAggregateOp.TALLY, properties);
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                if (_minValue != null) {
                    string += fee + tab + "minValue" + faa + getValueString(_minValue) + foo;
                }
                if (_maxValue != null) {
                    string += fee + tab + "maxValue" + faa + getValueString(_maxValue) + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Select Segments">
    public NativeQuerySegment isIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Numeric.Comparison.isIn(this, y));
    }

    public NativeQuerySegment isNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Numeric.Comparison.isNotIn(this, y));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NumericExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Numeric.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Numeric.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(Number y) {
        return XB.Numeric.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(Number y) {
        return XB.Numeric.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(Number y) {
        return XB.Numeric.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(NumericExpression y) {
        return XB.Numeric.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(Number y) {
        return XB.Numeric.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(Number y) {
        return XB.Numeric.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(NumericExpression y) {
        return XB.Numeric.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(Number y) {
        return XB.Numeric.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isIn(NumericExpression... y) {
        return XB.Numeric.Comparison.isIn(this, y);
    }

    @Override
    public BooleanComparisonX isIn(String y) {
        return XB.Numeric.Comparison.isIn(this, y);
    }

    @Override
    public BooleanComparisonX isNotIn(NumericExpression... y) {
        return XB.Numeric.Comparison.isNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNotIn(String y) {
        return XB.Numeric.Comparison.isNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isBetween(NumericExpression minimo, NumericExpression maximo) {
        return XB.Numeric.Comparison.isBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNotBetween(NumericExpression minimo, NumericExpression maximo) {
        return XB.Numeric.Comparison.isNotBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(Number y) {
        return XB.Numeric.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Number y) {
        return XB.Numeric.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(Number y) {
        return XB.Numeric.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(NumericExpression y) {
        return XB.Numeric.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(Number y) {
        return XB.Numeric.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(Number y) {
        return XB.Numeric.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(NumericExpression y) {
        return XB.Numeric.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(Number y) {
        return XB.Numeric.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(SpecialNumericValue y) {
        return XB.Numeric.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(NumericExpression y) {
        return XB.Numeric.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrIn(NumericExpression... y) {
        return XB.Numeric.Comparison.isNullOrIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrIn(String y) {
        return XB.Numeric.Comparison.isNullOrIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotIn(NumericExpression... y) {
        return XB.Numeric.Comparison.isNullOrNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotIn(String y) {
        return XB.Numeric.Comparison.isNullOrNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrBetween(NumericExpression minimo, NumericExpression maximo) {
        return XB.Numeric.Comparison.isNullOrBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNullOrNotBetween(NumericExpression minimo, NumericExpression maximo) {
        return XB.Numeric.Comparison.isNullOrNotBetween(this, minimo, maximo);
    }

    @Override
    public NumericOrderedPairX coalesce(Number y) {
        return XB.Numeric.OrderedPair.coalesce(this, y);
    }

    @Override
    public NumericOrderedPairX coalesce(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.coalesce(this, y);
    }

    @Override
    public NumericOrderedPairX coalesce(NumericExpression y) {
        return XB.Numeric.OrderedPair.coalesce(this, y);
    }

    @Override
    public NumericOrderedPairX nullIf(Number y) {
        return XB.Numeric.OrderedPair.nullIf(this, y);
    }

    @Override
    public NumericOrderedPairX nullIf(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.nullIf(this, y);
    }

    @Override
    public NumericOrderedPairX nullIf(NumericExpression y) {
        return XB.Numeric.OrderedPair.nullIf(this, y);
    }

    @Override
    public NumericOrderedPairX max(Number y) {
        return XB.Numeric.OrderedPair.max(this, y);
    }

    @Override
    public NumericOrderedPairX max(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.max(this, y);
    }

    @Override
    public NumericOrderedPairX max(NumericExpression y) {
        return XB.Numeric.OrderedPair.max(this, y);
    }

    @Override
    public NumericOrderedPairX min(Number y) {
        return XB.Numeric.OrderedPair.min(this, y);
    }

    @Override
    public NumericOrderedPairX min(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.min(this, y);
    }

    @Override
    public NumericOrderedPairX min(NumericExpression y) {
        return XB.Numeric.OrderedPair.min(this, y);
    }

    @Override
    public NumericOrderedPairX plus(Number y) {
        return XB.Numeric.OrderedPair.xPlusY(this, y);
    }

    @Override
    public NumericOrderedPairX plus(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.xPlusY(this, y);
    }

    @Override
    public NumericOrderedPairX plus(NumericExpression y) {
        return XB.Numeric.OrderedPair.xPlusY(this, y);
    }

    @Override
    public NumericOrderedPairX minus(Number y) {
        return XB.Numeric.OrderedPair.xMinusY(this, y);
    }

    @Override
    public NumericOrderedPairX minus(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.xMinusY(this, y);
    }

    @Override
    public NumericOrderedPairX minus(NumericExpression y) {
        return XB.Numeric.OrderedPair.xMinusY(this, y);
    }

    @Override
    public NumericOrderedPairX times(Number y) {
        return XB.Numeric.OrderedPair.xTimesY(this, y);
    }

    @Override
    public NumericOrderedPairX times(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.xTimesY(this, y);
    }

    @Override
    public NumericOrderedPairX times(NumericExpression y) {
        return XB.Numeric.OrderedPair.xTimesY(this, y);
    }

    @Override
    public NumericOrderedPairX over(Number y) {
        return XB.Numeric.OrderedPair.xOverY(this, y);
    }

    @Override
    public NumericOrderedPairX over(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.xOverY(this, y);
    }

    @Override
    public NumericOrderedPairX over(NumericExpression y) {
        return XB.Numeric.OrderedPair.xOverY(this, y);
    }

    @Override
    public NumericOrderedPairX toThe(Number y) {
        return XB.Numeric.OrderedPair.xToTheY(this, y);
    }

    @Override
    public NumericOrderedPairX toThe(SpecialNumericValue y) {
        return XB.Numeric.OrderedPair.xToTheY(this, y);
    }

    @Override
    public NumericOrderedPairX toThe(NumericExpression y) {
        return XB.Numeric.OrderedPair.xToTheY(this, y);
    }

    @Override
    public NumericScalarX defaultWhenNull() {
        return XB.Numeric.Scalar.defaultWhenNull(this);
    }

    @Override
    public NumericScalarX nullWhenDefault() {
        return XB.Numeric.Scalar.nullWhenDefault(this);
    }

    @Override
    public NumericScalarX abs() {
        return XB.Numeric.Scalar.abs(this);
    }

    @Override
    public NumericScalarX chs() {
        return XB.Numeric.Scalar.chs(this);
    }

    @Override
    public NumericScalarX inv() {
        return XB.Numeric.Scalar.inv(this);
    }

    @Override
    public NumericScalarX toBigDecimal() {
        return XB.Numeric.Scalar.toBigDecimal(this);
    }

    @Override
    public NumericScalarX toBigInteger() {
        return XB.Numeric.Scalar.toBigInteger(this);
    }

    @Override
    public NumericScalarX toByte() {
        return XB.Numeric.Scalar.toByte(this);
    }

    @Override
    public NumericScalarX toShort() {
        return XB.Numeric.Scalar.toShort(this);
    }

    @Override
    public NumericScalarX toInteger() {
        return XB.Numeric.Scalar.toInteger(this);
    }

    @Override
    public NumericScalarX toLong() {
        return XB.Numeric.Scalar.toLong(this);
    }

    @Override
    public NumericScalarX toDouble() {
        return XB.Numeric.Scalar.toDouble(this);
    }

    @Override
    public NumericScalarX toFloat() {
        return XB.Numeric.Scalar.toFloat(this);
    }

    @Override
    public CharacterScalarX toCharString() {
        return XB.Character.Scalar.toCharString(this);
    }

    @Override
    public CharacterScalarX toLocaleString() {
        return XB.Character.Scalar.toLocaleString(this);
    }

    @Override
    public CharacterOrderedPairX toZeroPaddedString(NumericExpression len) {
        return XB.Character.OrderedPair.toZeroPaddedString(this, len);
    }

    @Override
    public CharacterOrderedPairX toZeroPaddedString(int len) {
        return XB.Character.OrderedPair.toZeroPaddedString(this, len);
    }
    // </editor-fold>

}
