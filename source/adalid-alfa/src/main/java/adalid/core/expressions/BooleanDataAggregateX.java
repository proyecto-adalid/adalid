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

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public class BooleanDataAggregateX extends AbstractDataAggregateX implements Check, State {

    BooleanDataAggregateX(DataAggregateOp operator, Object... operands) {
        super(operator, operands);
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

    // <editor-fold defaultstate="collapsed" desc="Check">
    private CheckEvent _checkEvent = CheckEvent.INSERT_AND_UPDATE;

    /**
     * @return the check event
     */
    @Override
    public CheckEvent getCheckEvent() {
        return _checkEvent;
    }

    /**
     * El método setCheckEvent se utiliza para establecer en que evento o eventos se debe evaluar la expresión.
     *
     * @param checkEvent evento o eventos donde se debe evaluar la expresión. Su valor es uno de los elementos de la enumeración CheckEvent.
     * Seleccione INSERT o UPDATE si la expresión se debe evaluar solamente al agregar o al actualizar, respectivamente. Seleccione INSERT_AND_UPDATE
     * para evaluar la expresión en ambos eventos; esta es la opción predeterminada.
     */
    @Override
    public void setCheckEvent(CheckEvent checkEvent) {
        checkScope();
        _checkEvent = checkEvent == null ? CheckEvent.INSERT_AND_UPDATE : checkEvent;
    }

    private Checkpoint _checkpoint = Checkpoint.UNSPECIFIED;

    /**
     * @return the checkpoint
     */
    @Override
    public Checkpoint getCheckpoint() {
        return _checkpoint;
    }

    /**
     * El método setCheckpoint se utiliza para establecer en que componente o componentes se debe evaluar la expresión.
     *
     * @param checkpoint componente o componentes donde se debe evaluar la expresión. Su valor es uno de los elementos de la enumeración Checkpoint.
     * Seleccione DATABASE_TRIGGER o USER_INTERFACE si la expresión se debe evaluar solamente en un disparador (trigger) de la base de datos o en la
     * interfaz de usuario, respectivamente. Seleccione WHEREVER_POSSIBLE para evaluar la expresión en ambos componentes, siempre que sea posible;
     * esta es la opción predeterminada.
     */
    @Override
    public void setCheckpoint(Checkpoint checkpoint) {
        checkScope();
        _checkpoint = checkpoint == null ? Checkpoint.UNSPECIFIED : checkpoint;
    }
    // </editor-fold>

}
