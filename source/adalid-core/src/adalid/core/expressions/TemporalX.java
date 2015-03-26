/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.expressions;

import adalid.core.enums.SpecialTemporalValue;
import adalid.core.interfaces.TemporalExpression;

/**
 * @author Jorge Campins
 */
public class TemporalX extends VariantX implements TemporalExpression {

    public TemporalX() {
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
    public CharacterScalarX toCharString() {
        return XB.Character.Scalar.toCharString(this);
    }
    // </editor-fold>

}
