/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.primitives;

import adalid.core.Primitive;
import adalid.core.enums.SpecialNumericValue;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.CharacterScalarX;
import adalid.core.expressions.NumericOrderedPairX;
import adalid.core.expressions.NumericScalarX;
import adalid.core.expressions.XB;
import adalid.core.interfaces.NumericExpression;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class NumericPrimitive extends Primitive implements NumericExpression {

    private Object _initialValue;

    private Object _defaultValue;

    private Object _currentValue;

    private Object _minValue;

    private Object _maxValue;

    private Number _minNumber;

    private Number _maxNumber;

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return _initialValue;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(Number initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(SpecialNumericValue initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(NumericExpression initialValue) {
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
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(Number defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(SpecialNumericValue defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(NumericExpression defaultValue) {
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
     * @param currentValue the current value to set
     */
    public void setCurrentValue(Number currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(SpecialNumericValue currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(NumericExpression currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @return the minimum value
     */
    public Object getMinValue() {
        if (_minValue == null) {
            return _minNumber;
        }
        if (_minNumber == null) {
            return _minValue;
        }
        if (_minValue instanceof Number) {
            return compare((Number) _minValue, _minNumber) > 0 ? _minValue : _minNumber;
        }
        return _minValue;
    }

    /**
     * @param minValue the minimum value to set
     */
    public void setMinValue(Number minValue) {
        _minValue = minValue;
    }

    /**
     * @param minValue the minimum value to set
     */
    public void setMinValue(NumericExpression minValue) {
        _minValue = minValue;
    }

    /**
     * @return the maximum value
     */
    public Object getMaxValue() {
        if (_maxValue == null) {
            return _maxNumber;
        }
        if (_maxNumber == null) {
            return _maxValue;
        }
        if (_maxValue instanceof Number) {
            return compare((Number) _maxValue, _maxNumber) < 0 ? _maxValue : _maxNumber;
        }
        return _maxValue;
    }

    /**
     * @param maxValue the maximum value to set
     */
    public void setMaxValue(Number maxValue) {
        _maxValue = maxValue;
    }

    /**
     * @param maxValue the maximum value to set
     */
    public void setMaxValue(NumericExpression maxValue) {
        _maxValue = maxValue;
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

    private static int compare(Number x, Number y) {
        BigDecimal bx = new BigDecimal(x.toString());
        BigDecimal by = new BigDecimal(y.toString());
        return bx.compareTo(by);
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
    // </editor-fold>

}
