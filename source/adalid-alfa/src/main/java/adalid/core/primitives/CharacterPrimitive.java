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
import adalid.core.sql.*;

/**
 * @author Jorge Campins
 */
public abstract class CharacterPrimitive extends Primitive implements CharacterExpression {

    private static final String primalValue = null; // TODO: set a suitable primal value

    private String _primalInitialValue = primalValue;

    private String _primalDefaultValue = primalValue;

    private Object _calculableValue;

    private Object _initialValue;

    private Object _defaultValue;

    private Object _currentValue;

    /**
     * @return the primal initial value
     */
//  @Override
    public String getPrimalInitialValue() {
        return _primalInitialValue;
    }

    /**
     * El método setPrimalInitialValue se utiliza para establecer el valor inicial primitivo de propiedades y parámetros. El valor inicial primitivo
     * se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param initialValue valor inicial primitivo de la propiedad o el parámetro
     */
    public void setPrimalInitialValue(String initialValue) {
        _primalInitialValue = initialValue == null ? primalValue : initialValue;
    }

    /**
     * @return the primal default value
     */
//  @Override
    public String getPrimalDefaultValue() {
        return _primalDefaultValue;
    }

    /**
     * El método setPrimalDefaultValue se utiliza para establecer el valor por omisión primitivo de propiedades y parámetros. El valor por omisión
     * primitivo se utiliza cuando el valor inicial no es primitivo, y se requiere que lo sea.
     *
     * @param defaultValue valor por omisión primitivo de la propiedad o el parámetro
     */
    public void setPrimalDefaultValue(String defaultValue) {
        _primalDefaultValue = defaultValue == null ? primalValue : defaultValue;
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
    public void setCalculableValueExpression(CharacterExpression expression) {
        _calculableValue = validCalculableValue(expression) ? expression : null;
    }

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return _initialValue;
    }

    /**
     * @return true if the initial value is String; false otherwise
     */
    public boolean isPrimitiveInitialValue() {
        return _initialValue instanceof String;
    }

    /**
     * @return true if the initial value is SpecialCharacterValue; false otherwise
     */
    public boolean isSpecialInitialValue() {
        return _initialValue instanceof SpecialCharacterValue;
    }

    /**
     * @return true if the initial value is CharacterExpression; false otherwise
     */
    public boolean isExpressInitialValue() {
        return _initialValue instanceof CharacterExpression;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(String initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(SpecialCharacterValue initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    public void setInitialValue(CharacterExpression initialValue) {
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
     * @return true if the default value is String; false otherwise
     */
    public boolean isPrimitiveDefaultValue() {
        return _defaultValue instanceof String;
    }

    /**
     * @return true if the default value is SpecialCharacterValue; false otherwise
     */
    public boolean isSpecialDefaultValue() {
        return _defaultValue instanceof SpecialCharacterValue;
    }

    /**
     * @return true if the default value is CharacterExpression; false otherwise
     */
    public boolean isExpressDefaultValue() {
        return _defaultValue instanceof CharacterExpression;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(String defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(SpecialCharacterValue defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    public void setDefaultValue(CharacterExpression defaultValue) {
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
     * @return true if the current value is String; false otherwise
     */
    public boolean isPrimitiveCurrentValue() {
        return _currentValue instanceof String;
    }

    /**
     * @return true if the current value is SpecialCharacterValue; false otherwise
     */
    public boolean isSpecialCurrentValue() {
        return _currentValue instanceof SpecialCharacterValue;
    }

    /**
     * @return true if the current value is CharacterExpression; false otherwise
     */
    public boolean isExpressCurrentValue() {
        return _currentValue instanceof CharacterExpression;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(String currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(SpecialCharacterValue currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(CharacterExpression currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    // <editor-fold defaultstate="collapsed" desc="Select Segments">
    public NativeQuerySegment isIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Character.Comparison.isIn(this, y));
    }

    public NativeQuerySegment isNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Character.Comparison.isNotIn(this, y));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CharacterExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Character.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Character.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(String y) {
        return XB.Character.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(Expression y) {
        return XB.Character.Comparison.isEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNotEqualTo(String y) {
        return XB.Character.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(Expression y) {
        return XB.Character.Comparison.isNotEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isGreaterThan(String y) {
        return XB.Character.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterThan(Expression y) {
        return XB.Character.Comparison.isGreaterThan(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(String y) {
        return XB.Character.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isGreaterOrEqualTo(Expression y) {
        return XB.Character.Comparison.isGreaterOrEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isLessThan(String y) {
        return XB.Character.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isLessThan(Expression y) {
        return XB.Character.Comparison.isLessThan(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(String y) {
        return XB.Character.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isLessOrEqualTo(Expression y) {
        return XB.Character.Comparison.isLessOrEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX startsWith(String y) {
        return XB.Character.Comparison.startsWith(this, y);
    }

    @Override
    public BooleanComparisonX startsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.startsWith(this, y);
    }

    @Override
    public BooleanComparisonX startsWith(Expression y) {
        return XB.Character.Comparison.startsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX notStartsWith(String y) {
        return XB.Character.Comparison.notStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX notStartsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.notStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX notStartsWith(Expression y) {
        return XB.Character.Comparison.notStartsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX contains(String y) {
        return XB.Character.Comparison.contains(this, y);
    }

    @Override
    public BooleanComparisonX contains(SpecialCharacterValue y) {
        return XB.Character.Comparison.contains(this, y);
    }

    @Override
    public BooleanComparisonX contains(Expression y) {
        return XB.Character.Comparison.contains(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX notContains(String y) {
        return XB.Character.Comparison.notContains(this, y);
    }

    @Override
    public BooleanComparisonX notContains(SpecialCharacterValue y) {
        return XB.Character.Comparison.notContains(this, y);
    }

    @Override
    public BooleanComparisonX notContains(Expression y) {
        return XB.Character.Comparison.notContains(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX endsWith(String y) {
        return XB.Character.Comparison.endsWith(this, y);
    }

    @Override
    public BooleanComparisonX endsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.endsWith(this, y);
    }

    @Override
    public BooleanComparisonX endsWith(Expression y) {
        return XB.Character.Comparison.endsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX notEndsWith(String y) {
        return XB.Character.Comparison.notEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX notEndsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.notEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX notEndsWith(Expression y) {
        return XB.Character.Comparison.notEndsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isIn(CharacterExpression... y) {
        return XB.Character.Comparison.isIn(this, y);
    }

    @Override
    public BooleanComparisonX isNotIn(CharacterExpression... y) {
        return XB.Character.Comparison.isNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isBetween(CharacterExpression minimo, CharacterExpression maximo) {
        return XB.Character.Comparison.isBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNotBetween(CharacterExpression minimo, CharacterExpression maximo) {
        return XB.Character.Comparison.isNotBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(String y) {
        return XB.Character.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(Expression y) {
        return XB.Character.Comparison.isNullOrEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(String y) {
        return XB.Character.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Expression y) {
        return XB.Character.Comparison.isNullOrNotEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(String y) {
        return XB.Character.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrGreaterThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterThan(Expression y) {
        return XB.Character.Comparison.isNullOrGreaterThan(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(String y) {
        return XB.Character.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrGreaterOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrGreaterOrEqualTo(Expression y) {
        return XB.Character.Comparison.isNullOrGreaterOrEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(String y) {
        return XB.Character.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrLessThan(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessThan(Expression y) {
        return XB.Character.Comparison.isNullOrLessThan(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(String y) {
        return XB.Character.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrLessOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrLessOrEqualTo(Expression y) {
        return XB.Character.Comparison.isNullOrLessOrEqualTo(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrStartsWith(String y) {
        return XB.Character.Comparison.isNullOrStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrStartsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrStartsWith(Expression y) {
        return XB.Character.Comparison.isNullOrStartsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrNotStartsWith(String y) {
        return XB.Character.Comparison.isNullOrNotStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotStartsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotStartsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotStartsWith(Expression y) {
        return XB.Character.Comparison.isNullOrNotStartsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrContains(String y) {
        return XB.Character.Comparison.isNullOrContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrContains(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrContains(Expression y) {
        return XB.Character.Comparison.isNullOrContains(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrNotContains(String y) {
        return XB.Character.Comparison.isNullOrNotContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotContains(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotContains(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotContains(Expression y) {
        return XB.Character.Comparison.isNullOrNotContains(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrEndsWith(String y) {
        return XB.Character.Comparison.isNullOrEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEndsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEndsWith(Expression y) {
        return XB.Character.Comparison.isNullOrEndsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrNotEndsWith(String y) {
        return XB.Character.Comparison.isNullOrNotEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEndsWith(SpecialCharacterValue y) {
        return XB.Character.Comparison.isNullOrNotEndsWith(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEndsWith(Expression y) {
        return XB.Character.Comparison.isNullOrNotEndsWith(this, toCharacterExpression(y));
    }

    @Override
    public BooleanComparisonX isNullOrIn(CharacterExpression... y) {
        return XB.Character.Comparison.isNullOrIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotIn(CharacterExpression... y) {
        return XB.Character.Comparison.isNullOrNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrBetween(CharacterExpression minimo, CharacterExpression maximo) {
        return XB.Character.Comparison.isNullOrBetween(this, minimo, maximo);
    }

    @Override
    public BooleanComparisonX isNullOrNotBetween(CharacterExpression minimo, CharacterExpression maximo) {
        return XB.Character.Comparison.isNullOrNotBetween(this, minimo, maximo);
    }

    @Override
    public CharacterOrderedPairX coalesce(String y) {
        return XB.Character.OrderedPair.coalesce(this, y);
    }

    @Override
    public CharacterOrderedPairX coalesce(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.coalesce(this, y);
    }

    @Override
    public CharacterOrderedPairX coalesce(Expression y) {
        return XB.Character.OrderedPair.coalesce(this, toCharacterExpression(y));
    }

    @Override
    public CharacterOrderedPairX nullIf(String y) {
        return XB.Character.OrderedPair.nullIf(this, y);
    }

    @Override
    public CharacterOrderedPairX nullIf(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.nullIf(this, y);
    }

    @Override
    public CharacterOrderedPairX nullIf(Expression y) {
        return XB.Character.OrderedPair.nullIf(this, toCharacterExpression(y));
    }

    @Override
    public CharacterOrderedPairX max(String y) {
        return XB.Character.OrderedPair.max(this, y);
    }

    @Override
    public CharacterOrderedPairX max(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.max(this, y);
    }

    @Override
    public CharacterOrderedPairX max(Expression y) {
        return XB.Character.OrderedPair.max(this, toCharacterExpression(y));
    }

    @Override
    public CharacterOrderedPairX min(String y) {
        return XB.Character.OrderedPair.min(this, y);
    }

    @Override
    public CharacterOrderedPairX min(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.min(this, y);
    }

    @Override
    public CharacterOrderedPairX min(Expression y) {
        return XB.Character.OrderedPair.min(this, toCharacterExpression(y));
    }

    @Override
    public CharacterOrderedPairX concat(String y) {
        return XB.Character.OrderedPair.concat(this, y);
    }

    @Override
    public CharacterOrderedPairX concat(SpecialCharacterValue y) {
        return XB.Character.OrderedPair.concat(this, y);
    }

    @Override
    public CharacterOrderedPairX concat(Expression y) {
        return XB.Character.OrderedPair.concat(this, toCharacterExpression(y));
    }

    @Override
    public CharacterOrderedPairX format(String template) {
        return XB.Character.OrderedPair.format(this, template);
    }

    @Override
    public CharacterOrderedPairX left(NumericExpression len) {
        return XB.Character.OrderedPair.left(this, len);
    }

    @Override
    public CharacterOrderedPairX left(int len) {
        return XB.Character.OrderedPair.left(this, len);
    }

    @Override
    public CharacterOrderedPairX right(NumericExpression len) {
        return XB.Character.OrderedPair.right(this, len);
    }

    @Override
    public CharacterOrderedPairX right(int len) {
        return XB.Character.OrderedPair.right(this, len);
    }

    @Override
    public CharacterOrderedPairX substr(NumericExpression pos) {
        return XB.Character.OrderedPair.substr(this, pos);
    }

    @Override
    public CharacterOrderedPairX substr(int pos) {
        return XB.Character.OrderedPair.substr(this, pos);
    }

    @Override
    public CharacterNaryVectorX substr(NumericExpression pos, NumericExpression len) {
        return XB.Character.NaryVector.substr(this, pos, len);
    }

    @Override
    public CharacterNaryVectorX substr(NumericExpression pos, int len) {
        return XB.Character.NaryVector.substr(this, pos, len);
    }

    @Override
    public CharacterNaryVectorX substr(int pos, NumericExpression len) {
        return XB.Character.NaryVector.substr(this, pos, len);
    }

    @Override
    public CharacterNaryVectorX substr(int pos, int len) {
        return XB.Character.NaryVector.substr(this, pos, len);
    }

    @Override
    public CharacterOrderedPairX toZeroPaddedString(NumericExpression len) {
        return XB.Character.OrderedPair.toZeroPaddedString(this, len);
    }

    @Override
    public CharacterOrderedPairX toZeroPaddedString(int len) {
        return XB.Character.OrderedPair.toZeroPaddedString(this, len);
    }

    @Override
    public CharacterScalarX defaultWhenNull() {
        return XB.Character.Scalar.defaultWhenNull(this);
    }

    @Override
    public CharacterScalarX nullWhenDefault() {
        return XB.Character.Scalar.nullWhenDefault(this);
    }

    @Override
    public CharacterScalarX lower() {
        return XB.Character.Scalar.lower(this);
    }

    @Override
    public CharacterScalarX upper() {
        return XB.Character.Scalar.upper(this);
    }

    @Override
    public CharacterScalarX capitalize() {
        return XB.Character.Scalar.capitalize(this);
    }

    @Override
    public CharacterScalarX uncapitalize() {
        return XB.Character.Scalar.uncapitalize(this);
    }

    @Override
    public CharacterScalarX trim() {
        return XB.Character.Scalar.trim(this);
    }

    @Override
    public CharacterScalarX ltrim() {
        return XB.Character.Scalar.ltrim(this);
    }

    @Override
    public CharacterScalarX rtrim() {
        return XB.Character.Scalar.rtrim(this);
    }

    @Override
    public CharacterScalarX toChar() {
        return XB.Character.Scalar.toChar(this);
    }

    @Override
    public CharacterScalarX toCharString() {
        return XB.Character.Scalar.toCharString(this);
    }

    private CharacterExpression toCharacterExpression(Object x) {
        return x instanceof CharacterExpression ? (CharacterExpression) x : XB.toCharString(x);
    }
    // </editor-fold>

}
