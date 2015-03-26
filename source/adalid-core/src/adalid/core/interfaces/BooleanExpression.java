/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.Instance;
import adalid.core.ProcessOperation;
import adalid.core.Trigger;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.BooleanConditionalX;
import adalid.core.expressions.BooleanOrderedPairX;
import adalid.core.expressions.BooleanScalarX;
import adalid.core.expressions.CharacterConditionalX;
import adalid.core.expressions.CharacterScalarX;
import adalid.core.expressions.EntityConditionalX;
import adalid.core.expressions.NumericConditionalX;
import adalid.core.expressions.TemporalConditionalX;

/**
 * @author Jorge Campins
 */
public interface BooleanExpression extends Expression {

    BooleanComparisonX isNull();

    BooleanComparisonX isNotNull();

    BooleanComparisonX isTrue();

    BooleanComparisonX isFalse();

    BooleanComparisonX isNullOrTrue();

    BooleanComparisonX isNullOrFalse();

    BooleanComparisonX isEqualTo(BooleanExpression y);

    BooleanComparisonX isNotEqualTo(BooleanExpression y);

    BooleanComparisonX isNullOrEqualTo(BooleanExpression y);

    BooleanComparisonX isNullOrNotEqualTo(BooleanExpression y);

    BooleanOrderedPairX and(BooleanExpression y);

    BooleanOrderedPairX nand(BooleanExpression y);

    BooleanOrderedPairX or(BooleanExpression y);

    BooleanOrderedPairX nor(BooleanExpression y);

    BooleanOrderedPairX xor(BooleanExpression y);

    BooleanOrderedPairX xnor(BooleanExpression y);

    BooleanOrderedPairX implies(BooleanExpression y);

    BooleanScalarX not();

    CharacterScalarX toCharString();

    EntityConditionalX then(Entity value);

    EntityConditionalX then(Instance value);

    EntityConditionalX then(EntityExpression value);

    BooleanConditionalX then(Boolean value);

    BooleanConditionalX then(BooleanExpression value);

    CharacterConditionalX then(String value);

    CharacterConditionalX then(CharacterExpression value);

    NumericConditionalX then(Number value);

    NumericConditionalX then(NumericExpression value);

    TemporalConditionalX then(java.util.Date value);

    TemporalConditionalX then(TemporalExpression value);

    Trigger trigger(ProcessOperation operation);

    String getDefaultErrorMessage();

    void setDefaultErrorMessage(String defaultErrorMessage);

}
