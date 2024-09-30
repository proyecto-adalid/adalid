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
package adalid.core.expressions;

import adalid.core.enums.*;
import adalid.core.interfaces.*;

/**
 * @author Jorge Campins
 */
public class NumericRowsAggregateX extends AbstractRowsAggregateX implements NumericExpression {

    NumericRowsAggregateX(RowsAggregateOp operator, Object measure) {
        super(operator, measure);
    }

    NumericRowsAggregateX(RowsAggregateOp operator, Object measure, Segment filter) {
        super(operator, measure, filter);
    }

    NumericRowsAggregateX(RowsAggregateOp operator, Object measure, Entity dimension) {
        super(operator, measure, dimension);
    }

    NumericRowsAggregateX(RowsAggregateOp operator, Object measure, Segment filter, Entity dimension) {
        super(operator, measure, filter, dimension);
    }

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
