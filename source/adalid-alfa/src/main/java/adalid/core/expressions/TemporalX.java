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
public class TemporalX extends VariantX implements TemporalExpression {

    public TemporalX() {
        super();
        initDataType();
    }

    private void initDataType() {
        setDataType(java.util.Date.class);
    }

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
