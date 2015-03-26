/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.primitives;

import adalid.core.Instance;
import adalid.core.Primitive;
import adalid.core.ProcessOperation;
import adalid.core.Transition;
import adalid.core.Trigger;
import adalid.core.enums.SpecialBooleanValue;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.BooleanConditionalX;
import adalid.core.expressions.BooleanOrderedPairX;
import adalid.core.expressions.BooleanScalarX;
import adalid.core.expressions.CharacterConditionalX;
import adalid.core.expressions.CharacterScalarX;
import adalid.core.expressions.EntityConditionalX;
import adalid.core.expressions.NumericConditionalX;
import adalid.core.expressions.TemporalConditionalX;
import adalid.core.expressions.XB;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.CharacterExpression;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityExpression;
import adalid.core.interfaces.NumericExpression;
import adalid.core.interfaces.State;
import adalid.core.interfaces.TemporalExpression;

/**
 * @author Jorge Campins
 */
public abstract class BooleanPrimitive extends Primitive implements State {

    private Object _initialValue;

    private Object _defaultValue;

    private Object _currentValue;

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
    public void setInitialValue(Boolean initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(SpecialBooleanValue initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(BooleanExpression initialValue) {
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
    public void setDefaultValue(Boolean defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(SpecialBooleanValue defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(BooleanExpression defaultValue) {
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
    public void setCurrentValue(Boolean currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(SpecialBooleanValue currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(BooleanExpression currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
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
