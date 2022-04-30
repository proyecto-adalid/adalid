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
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public abstract class TemporalPrimitive extends Primitive implements IntervalizedArtifact, TemporalExpression {

    private static final Calendar calendar = Calendar.getInstance();

    public static long getEpochInMillis() {
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /*
    private static final java.util.Date minimal = new java.util.Date(-62135755200000L); // January 1, 0001, 04:00:00 GMT.

    private static final java.util.Date maximal = new java.util.Date(253402300799999L); // December 31, 9999, 23:59:59.999999 GMT.

    /**/
    private static final java.util.Date primalValue = new java.util.Date(0); // "the epoch", namely January 1, 1970, 00:00:00 GMT.

    private java.util.Date _primalInitialValue = primalValue;

    private java.util.Date _primalDefaultValue = primalValue;

    private java.util.Date _primalMinValue;

    private java.util.Date _primalMaxValue;

    private Object _calculableValue;

    private Object _initialValue;

    private Object _defaultValue;

    private Object _currentValue;

    private Object _minValue;

    private Object _maxValue;

    private java.util.Date _minDate;

    private java.util.Date _maxDate;

    private final Map<Locale, String> _localizedRangeErrorMessage = new LinkedHashMap<>();

    private String _specialConverterName;

    private String _specialValidatorName;

    private Class<? extends java.util.Date> dataType() {
        return (Class<? extends java.util.Date>) getDataType();
    }

    /**
     * @return the primal initial value
     */
//  @Override
    public java.util.Date getPrimalInitialValue() {
        return _primalInitialValue;
    }

    /**
     * El método setPrimalInitialValue se utiliza para establecer el valor inicial primitivo de propiedades y parámetros. El valor inicial primitivo
     * se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param initialValue valor inicial primitivo de la propiedad o el parámetro
     */
    public void setPrimalInitialValue(java.util.Date initialValue) {
        _primalInitialValue = initialValue == null ? primalValue : initialValue;
    }

    /**
     * @return the primal default value
     */
//  @Override
    public java.util.Date getPrimalDefaultValue() {
        return _primalDefaultValue;
    }

    /**
     * El método setPrimalDefaultValue se utiliza para establecer el valor por omisión primitivo de propiedades y parámetros. El valor por omisión
     * primitivo se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param defaultValue valor por omisión primitivo de la propiedad o el parámetro
     */
    public void setPrimalDefaultValue(java.util.Date defaultValue) {
        _primalDefaultValue = defaultValue == null ? primalValue : defaultValue;
    }

    /**
     * @return the primal minimum value
     */
//  @Override
    public java.util.Date getPrimalMinValue() {
        return _primalMinValue == null ? getPrimitiveMinValue() : _primalMinValue;
    }

    /**
     * El método setPrimalMinValue se utiliza para establecer el valor mínimo primitivo de propiedades y parámetros. El valor mínimo primitivo se
     * utiliza cuando el valor mínimo no es primitivo, y se requiere que lo sea.
     *
     * @param minValue valor mínimo primitivo de la propiedad o el parámetro
     */
    public void setPrimalMinValue(java.util.Date minValue) {
        _primalMinValue = minValue;
    }

    /**
     * @return the primal maximum value
     */
//  @Override
    public java.util.Date getPrimalMaxValue() {
        return _primalMaxValue == null ? getPrimitiveMaxValue() : _primalMaxValue;
    }

    /**
     * El método setPrimalMaxValue se utiliza para establecer el valor máximo primitivo de propiedades y parámetros. El valor máximo primitivo se
     * utiliza cuando el valor máximo no es primitivo, y se requiere que lo sea.
     *
     * @param maxValue valor máximo primitivo de la propiedad o el parámetro
     */
    public void setPrimalMaxValue(java.util.Date maxValue) {
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
    public void setCalculableValueExpression(TemporalExpression expression) {
        _calculableValue = validCalculableValue(expression) ? expression : null;
    }

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return toDataType(_initialValue);
    }

    /**
     * @return true if the initial value is java.util.Date; false otherwise
     */
    public boolean isPrimitiveInitialValue() {
        return _initialValue instanceof java.util.Date;
    }

    /**
     * @return true if the initial value is SpecialTemporalValue; false otherwise
     */
    public boolean isSpecialInitialValue() {
        return _initialValue instanceof SpecialTemporalValue;
    }

    /**
     * @return true if the initial value is TemporalExpression; false otherwise
     */
    public boolean isExpressInitialValue() {
        return _initialValue instanceof TemporalExpression;
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
    public void setInitialValue(String initialValue) {
        setInitialValue(TimeUtils.jdbcObject(initialValue, dataType()));
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(java.util.Date initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(SpecialTemporalValue initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(TemporalExpression initialValue) {
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
     * @return true if the default value is java.util.Date; false otherwise
     */
    public boolean isPrimitiveDefaultValue() {
        return _defaultValue instanceof java.util.Date;
    }

    /**
     * @return true if the default value is SpecialTemporalValue; false otherwise
     */
    public boolean isSpecialDefaultValue() {
        return _defaultValue instanceof SpecialTemporalValue;
    }

    /**
     * @return true if the default value is TemporalExpression; false otherwise
     */
    public boolean isExpressDefaultValue() {
        return _defaultValue instanceof TemporalExpression;
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
    public void setDefaultValue(String defaultValue) {
        setDefaultValue(TimeUtils.jdbcObject(defaultValue, dataType()));
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(java.util.Date defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(SpecialTemporalValue defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(TemporalExpression defaultValue) {
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
     * @return true if the current value is java.util.Date; false otherwise
     */
    public boolean isPrimitiveCurrentValue() {
        return _currentValue instanceof java.util.Date;
    }

    /**
     * @return true if the current value is SpecialTemporalValue; false otherwise
     */
    public boolean isSpecialCurrentValue() {
        return _currentValue instanceof SpecialTemporalValue;
    }

    /**
     * @return true if the current value is TemporalExpression; false otherwise
     */
    public boolean isExpressCurrentValue() {
        return _currentValue instanceof TemporalExpression;
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
    public void setCurrentValue(String currentValue) {
        setCurrentValue(TimeUtils.jdbcObject(currentValue, dataType()));
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(java.util.Date currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(SpecialTemporalValue currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(TemporalExpression currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @return the minimum value
     */
    @Override
    public Object getMinValue() {
        return _minValue == null ? _minDate : _minValue instanceof java.util.Date ? primitiveMinValue() : toDataType(_minValue);
    }

    /**
     * @return the primitive minimum value
     */
    public java.util.Date getPrimitiveMinValue() {
        return _minValue instanceof java.util.Date ? primitiveMinValue() : _minDate;
    }

    /**
     * @return the primitive minimum value
     */
    private java.util.Date primitiveMinValue() {
        java.util.Date value = (java.util.Date) _minValue;
        return _minDate == null ? value : value.compareTo(_minDate) > 0 ? value : _minDate;
    }

    /**
     * @return true if the minimum value is java.util.Date; false otherwise
     */
    public boolean isPrimitiveMinValue() {
        return _minDate != null || _minValue instanceof java.util.Date;
    }

    /**
     * @return true if the minimum value is SpecialTemporalValue; false otherwise
     */
    public boolean isSpecialMinValue() {
        return _minValue instanceof SpecialTemporalValue;
    }

    /**
     * @return true if the minimum value is TemporalExpression; false otherwise
     */
    public boolean isExpressMinValue() {
        return _minValue instanceof TemporalExpression;
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
     * @param minValue valor mínimo de la propiedad o el parámetro en formato ISO (YYYY-MM-DD hh:mm:ss)
     */
    public void setMinValue(String minValue) {
        setMinValue(TimeUtils.jdbcObject(minValue, dataType()));
    }

    /**
     * El método setMinValue se utiliza para establecer el valor mínimo de propiedades y parámetros.
     *
     * @param minValue valor mínimo de la propiedad o el parámetro
     */
    public void setMinValue(java.util.Date minValue) {
        _minValue = minValue;
    }

    /**
     * El método setMinValue se utiliza para establecer el valor mínimo de propiedades y parámetros.
     *
     * @param minValue valor mínimo de la propiedad o el parámetro
     */
    public void setMinValue(SpecialTemporalValue minValue) {
        _minValue = minValue;
    }

    /**
     * El método setMinValue se utiliza para establecer el valor mínimo de propiedades y parámetros.
     *
     * @param minValue valor mínimo de la propiedad o el parámetro
     */
    public void setMinValue(TemporalExpression minValue) {
        _minValue = validMinimumValue(minValue) ? minValue : null;
    }

    /**
     * @return the maximum value
     */
    @Override
    public Object getMaxValue() {
        return _maxValue == null ? _maxDate : _maxValue instanceof java.util.Date ? primitiveMaxValue() : toDataType(_maxValue);
    }

    /**
     * @return the primitive maximum value
     */
    public java.util.Date getPrimitiveMaxValue() {
        return _maxValue instanceof java.util.Date ? primitiveMaxValue() : _maxDate;
    }

    /**
     * @return the primitive maximum value
     */
    private java.util.Date primitiveMaxValue() {
        java.util.Date value = (java.util.Date) _maxValue;
        return _maxDate == null ? value : value.compareTo(_maxDate) < 0 ? value : _maxDate;
    }

    /**
     * @return true if the maximum value is java.util.Date; false otherwise
     */
    public boolean isPrimitiveMaxValue() {
        return _maxDate != null || _maxValue instanceof java.util.Date;
    }

    /**
     * @return true if the maximum value is SpecialTemporalValue; false otherwise
     */
    public boolean isSpecialMaxValue() {
        return _maxValue instanceof SpecialTemporalValue;
    }

    /**
     * @return true if the maximum value is TemporalExpression; false otherwise
     */
    public boolean isExpressMaxValue() {
        return _maxValue instanceof TemporalExpression;
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
     * @param maxValue valor máximo de la propiedad o el parámetro en formato ISO (YYYY-MM-DD hh:mm:ss)
     */
    public void setMaxValue(String maxValue) {
        setMaxValue(TimeUtils.jdbcObject(maxValue, dataType()));
    }

    /**
     * El método setMaxValue se utiliza para establecer el valor máximo de propiedades y parámetros.
     *
     * @param maxValue valor máximo de la propiedad o el parámetro
     */
    public void setMaxValue(java.util.Date maxValue) {
        _maxValue = maxValue;
    }

    /**
     * El método setMaxValue se utiliza para establecer el valor máximo de propiedades y parámetros.
     *
     * @param maxValue valor máximo de la propiedad o el parámetro
     */
    public void setMaxValue(SpecialTemporalValue maxValue) {
        _maxValue = maxValue;
    }

    /**
     * El método setMaxValue se utiliza para establecer el valor máximo de propiedades y parámetros.
     *
     * @param maxValue valor máximo de la propiedad o el parámetro
     */
    public void setMaxValue(TemporalExpression maxValue) {
        _maxValue = validMaximumValue(maxValue) ? maxValue : null;
    }

    /**
     * @return the minimum number
     */
    public java.util.Date getMinDate() {
        return _minDate;
    }

    /**
     * @param minDate the minimum number to set
     */
    protected void setMinDate(java.util.Date minDate) {
        _minDate = minDate;
    }

    /**
     * @return the maximum number
     */
    public java.util.Date getMaxDate() {
        return _maxDate;
    }

    /**
     * @param maxDate the maximum number to set
     */
    protected void setMaxDate(java.util.Date maxDate) {
        _maxDate = maxDate;
    }

    private Object toDataType(Object value) {
        if (value instanceof TemporalExpression) {
            TemporalExpression tx = (TemporalExpression) value;
            Operator txop = tx.getOperator();
            Class<?> txdt = tx instanceof TemporalPrimitive ? tx.getDataType() : null;
            Class<?> mydt = getDataType();
            if (mydt == null || mydt.equals(txdt) || txop == null) {
            } else if (Date.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_DATE.equals(txop)) {
                    return tx.toDate();
                }
            } else if (Time.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_TIME.equals(txop)) {
                    return tx.toTime();
                }
            } else if (Timestamp.class.isAssignableFrom(mydt)) {
                if (!ScalarOp.TO_TIMESTAMP.equals(txop)) {
                    return tx.toTimestamp();
                }
            }
        }
        return value;
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
        Locale l = localeWritingKey(locale);
        if (message == null) {
            _localizedRangeErrorMessage.remove(l);
        } else {
            _localizedRangeErrorMessage.put(l, message);
        }
    }

    /**
     * @return the special converter name
     */
    public String getSpecialConverterName() {
        return _specialConverterName;
    }

    public void setSpecialConverterName(String converter) {
        XS2.checkAccess();
        _specialConverterName = converter;
    }

    /**
     * @return the special validator name
     */
    public String getSpecialValidatorName() {
        return _specialValidatorName;
    }

    public void setSpecialValidatorName(String validator) {
        XS2.checkAccess();
        _specialValidatorName = validator;
    }

    // <editor-fold defaultstate="collapsed" desc="Select Segments">
    public NativeQuerySegment isIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Temporal.Comparison.isIn(this, y));
    }

    public NativeQuerySegment isNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Temporal.Comparison.isNotIn(this, y));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="TemporalExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Temporal.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Temporal.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(java.util.Date y) {
        return XB.Temporal.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(TemporalExpression y) {
        return XB.Temporal.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(java.util.Date y) {
        return XB.Temporal.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(TemporalExpression y) {
        return XB.Temporal.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isIn(TemporalExpression... y) {
        return XB.Temporal.Comparison.isIn(this, y);
    }

    @Override
    public BooleanComparisonX isNotIn(TemporalExpression... y) {
        return XB.Temporal.Comparison.isNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isBetween(TemporalExpression minimo, TemporalExpression maximo) {
        return XB.Temporal.Comparison.isBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNotBetween(TemporalExpression minimo, TemporalExpression maximo) {
        return XB.Temporal.Comparison.isNotBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(java.util.Date y) {
        return XB.Temporal.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(TemporalExpression y) {
        return XB.Temporal.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(java.util.Date y) {
        return XB.Temporal.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(TemporalExpression y) {
        return XB.Temporal.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(java.util.Date y) {
        return XB.Temporal.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(SpecialTemporalValue y) {
        return XB.Temporal.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(TemporalExpression y) {
        return XB.Temporal.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrIn(TemporalExpression... y) {
        return XB.Temporal.Comparison.isNullOrIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotIn(TemporalExpression... y) {
        return XB.Temporal.Comparison.isNullOrNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrBetween(TemporalExpression minimo, TemporalExpression maximo) {
        return XB.Temporal.Comparison.isNullOrBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNullOrNotBetween(TemporalExpression minimo, TemporalExpression maximo) {
        return XB.Temporal.Comparison.isNullOrNotBetween(this, minimo, maximo);
    }

    @Override
    public TemporalOrderedPairX coalesce(java.util.Date y) {
        return XB.Temporal.OrderedPair.coalesce(this, y);
    }

    @Override
    public TemporalOrderedPairX coalesce(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.coalesce(this, y);
    }

    @Override
    public TemporalOrderedPairX coalesce(TemporalExpression y) {
        return XB.Temporal.OrderedPair.coalesce(this, y);
    }

    @Override
    public TemporalOrderedPairX nullIf(java.util.Date y) {
        return XB.Temporal.OrderedPair.nullIf(this, y);
    }

    @Override
    public TemporalOrderedPairX nullIf(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.nullIf(this, y);
    }

    @Override
    public TemporalOrderedPairX nullIf(TemporalExpression y) {
        return XB.Temporal.OrderedPair.nullIf(this, y);
    }

    @Override
    public TemporalOrderedPairX max(java.util.Date y) {
        return XB.Temporal.OrderedPair.max(this, y);
    }

    @Override
    public TemporalOrderedPairX max(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.max(this, y);
    }

    @Override
    public TemporalOrderedPairX max(TemporalExpression y) {
        return XB.Temporal.OrderedPair.max(this, y);
    }

    @Override
    public TemporalOrderedPairX min(java.util.Date y) {
        return XB.Temporal.OrderedPair.min(this, y);
    }

    @Override
    public TemporalOrderedPairX min(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.min(this, y);
    }

    @Override
    public TemporalOrderedPairX min(TemporalExpression y) {
        return XB.Temporal.OrderedPair.min(this, y);
    }

    @Override
    public TemporalOrderedPairX addYears(Number y) {
        return XB.Temporal.OrderedPair.addYears(this, y);
    }

    @Override
    public TemporalOrderedPairX addYears(NumericExpression y) {
        return XB.Temporal.OrderedPair.addYears(this, y);
    }

    @Override
    public TemporalOrderedPairX addMonths(Number y) {
        return XB.Temporal.OrderedPair.addMonths(this, y);
    }

    @Override
    public TemporalOrderedPairX addMonths(NumericExpression y) {
        return XB.Temporal.OrderedPair.addMonths(this, y);
    }

    @Override
    public TemporalOrderedPairX addWeeks(Number y) {
        return XB.Temporal.OrderedPair.addWeeks(this, y);
    }

    @Override
    public TemporalOrderedPairX addWeeks(NumericExpression y) {
        return XB.Temporal.OrderedPair.addWeeks(this, y);
    }

    @Override
    public TemporalOrderedPairX addDays(Number y) {
        return XB.Temporal.OrderedPair.addDays(this, y);
    }

    @Override
    public TemporalOrderedPairX addDays(NumericExpression y) {
        return XB.Temporal.OrderedPair.addDays(this, y);
    }

    @Override
    public TemporalOrderedPairX addHours(Number y) {
        return XB.Temporal.OrderedPair.addHours(this, y);
    }

    @Override
    public TemporalOrderedPairX addHours(NumericExpression y) {
        return XB.Temporal.OrderedPair.addHours(this, y);
    }

    @Override
    public TemporalOrderedPairX addMinutes(Number y) {
        return XB.Temporal.OrderedPair.addMinutes(this, y);
    }

    @Override
    public TemporalOrderedPairX addMinutes(NumericExpression y) {
        return XB.Temporal.OrderedPair.addMinutes(this, y);
    }

    @Override
    public TemporalOrderedPairX addSeconds(Number y) {
        return XB.Temporal.OrderedPair.addSeconds(this, y);
    }

    @Override
    public TemporalOrderedPairX addSeconds(NumericExpression y) {
        return XB.Temporal.OrderedPair.addSeconds(this, y);
    }

    @Override
    public NumericOrderedPairX diffInYears(java.util.Date y) {
        return XB.Temporal.OrderedPair.diffInYears(this, y);
    }

    @Override
    public NumericOrderedPairX diffInYears(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.diffInYears(this, y);
    }

    @Override
    public NumericOrderedPairX diffInYears(TemporalExpression y) {
        return XB.Temporal.OrderedPair.diffInYears(this, y);
    }

    @Override
    public NumericOrderedPairX diffInMonths(java.util.Date y) {
        return XB.Temporal.OrderedPair.diffInMonths(this, y);
    }

    @Override
    public NumericOrderedPairX diffInMonths(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.diffInMonths(this, y);
    }

    @Override
    public NumericOrderedPairX diffInMonths(TemporalExpression y) {
        return XB.Temporal.OrderedPair.diffInMonths(this, y);
    }

    @Override
    public NumericOrderedPairX diffInWeeks(java.util.Date y) {
        return XB.Temporal.OrderedPair.diffInWeeks(this, y);
    }

    @Override
    public NumericOrderedPairX diffInWeeks(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.diffInWeeks(this, y);
    }

    @Override
    public NumericOrderedPairX diffInWeeks(TemporalExpression y) {
        return XB.Temporal.OrderedPair.diffInWeeks(this, y);
    }

    @Override
    public NumericOrderedPairX diffInDays(java.util.Date y) {
        return XB.Temporal.OrderedPair.diffInDays(this, y);
    }

    @Override
    public NumericOrderedPairX diffInDays(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.diffInDays(this, y);
    }

    @Override
    public NumericOrderedPairX diffInDays(TemporalExpression y) {
        return XB.Temporal.OrderedPair.diffInDays(this, y);
    }

    @Override
    public NumericOrderedPairX diffInHours(java.util.Date y) {
        return XB.Temporal.OrderedPair.diffInHours(this, y);
    }

    @Override
    public NumericOrderedPairX diffInHours(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.diffInHours(this, y);
    }

    @Override
    public NumericOrderedPairX diffInHours(TemporalExpression y) {
        return XB.Temporal.OrderedPair.diffInHours(this, y);
    }

    @Override
    public NumericOrderedPairX diffInMinutes(java.util.Date y) {
        return XB.Temporal.OrderedPair.diffInMinutes(this, y);
    }

    @Override
    public NumericOrderedPairX diffInMinutes(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.diffInMinutes(this, y);
    }

    @Override
    public NumericOrderedPairX diffInMinutes(TemporalExpression y) {
        return XB.Temporal.OrderedPair.diffInMinutes(this, y);
    }

    @Override
    public NumericOrderedPairX diffInSeconds(java.util.Date y) {
        return XB.Temporal.OrderedPair.diffInSeconds(this, y);
    }

    @Override
    public NumericOrderedPairX diffInSeconds(SpecialTemporalValue y) {
        return XB.Temporal.OrderedPair.diffInSeconds(this, y);
    }

    @Override
    public NumericOrderedPairX diffInSeconds(TemporalExpression y) {
        return XB.Temporal.OrderedPair.diffInSeconds(this, y);
    }

    @Override
    public TemporalScalarX defaultWhenNull() {
        return XB.Temporal.Scalar.defaultWhenNull(this);
    }

    @Override
    public TemporalScalarX nullWhenDefault() {
        return XB.Temporal.Scalar.nullWhenDefault(this);
    }

    @Override
    public TemporalScalarX toDate() {
        return XB.Temporal.Scalar.toDate(this);
    }

    @Override
    public TemporalScalarX toTime() {
        return XB.Temporal.Scalar.toTime(this);
    }

    @Override
    public TemporalScalarX toTimestamp() {
        return XB.Temporal.Scalar.toTimestamp(this);
    }

    @Override
    public TemporalScalarX firstDateOfMonth() {
        return XB.Temporal.Scalar.firstDateOfMonth(this);
    }

    @Override
    public TemporalScalarX firstDateOfQuarter() {
        return XB.Temporal.Scalar.firstDateOfQuarter(this);
    }

    @Override
    public TemporalScalarX firstDateOfSemester() {
        return XB.Temporal.Scalar.firstDateOfSemester(this);
    }

    @Override
    public TemporalScalarX firstDateOfYear() {
        return XB.Temporal.Scalar.firstDateOfYear(this);
    }

    @Override
    public TemporalScalarX lastDateOfMonth() {
        return XB.Temporal.Scalar.lastDateOfMonth(this);
    }

    @Override
    public TemporalScalarX lastDateOfQuarter() {
        return XB.Temporal.Scalar.lastDateOfQuarter(this);
    }

    @Override
    public TemporalScalarX lastDateOfSemester() {
        return XB.Temporal.Scalar.lastDateOfSemester(this);
    }

    @Override
    public TemporalScalarX lastDateOfYear() {
        return XB.Temporal.Scalar.lastDateOfYear(this);
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
    public NumericScalarX extractYear() {
        return XB.Temporal.Scalar.extractYear(this);
    }

    @Override
    public NumericScalarX extractMonth() {
        return XB.Temporal.Scalar.extractMonth(this);
    }

    @Override
    public NumericScalarX extractDay() {
        return XB.Temporal.Scalar.extractDay(this);
    }

    @Override
    public NumericScalarX extractHour() {
        return XB.Temporal.Scalar.extractHour(this);
    }

    @Override
    public NumericScalarX extractMinute() {
        return XB.Temporal.Scalar.extractMinute(this);
    }

    @Override
    public NumericScalarX extractSecond() {
        return XB.Temporal.Scalar.extractSecond(this);
    }
    // </editor-fold>

}
