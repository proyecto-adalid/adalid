/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.expressions;

import adalid.core.Instance;
import adalid.core.ProcessOperation;
import adalid.core.Transition;
import adalid.core.Trigger;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.CharacterExpression;
import adalid.core.interfaces.Check;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityExpression;
import adalid.core.interfaces.NumericExpression;
import adalid.core.interfaces.State;
import adalid.core.interfaces.TemporalExpression;

/**
 * @author Jorge Campins
 */
public class BooleanX extends VariantX implements Check, State {

    public BooleanX() {
        initDataType();
    }

    private void initDataType() {
        setDataType(Boolean.class);
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
    public EntityConditionalX then(EntityExpression value) {
        return XB.Entity.Conditional.then(this, value);
    }

    @Override
    public BooleanConditionalX then(Boolean value) {
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
    public CharacterConditionalX then(CharacterExpression value) {
        return XB.Character.Conditional.then(this, value);
    }

    @Override
    public NumericConditionalX then(Number value) {
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

    @Override
    public Transition transitionTo(State y) {
        return new Transition(this, y);
    }

    @Override
    public Trigger trigger(ProcessOperation operation) {
        return new Trigger(this, operation);
    }

    private String _defaultErrorMessage;

    @Override
    public String getDefaultErrorMessage() {
        return _defaultErrorMessage;
    }

    @Override
    public void setDefaultErrorMessage(String defaultErrorMessage) {
        _defaultErrorMessage = defaultErrorMessage;
    }
    // </editor-fold>

}
