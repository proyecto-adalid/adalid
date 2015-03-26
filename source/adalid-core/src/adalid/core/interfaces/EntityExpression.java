/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.Instance;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.EntityOrderedPairX;

/**
 * @author Jorge Campins
 */
public interface EntityExpression extends Expression {

    BooleanComparisonX isNull();

    BooleanComparisonX isNotNull();

    BooleanComparisonX isEqualTo(Entity y);

    BooleanComparisonX isEqualTo(Instance y);

    BooleanComparisonX isEqualTo(EntityExpression y);

    BooleanComparisonX isNotEqualTo(Entity y);

    BooleanComparisonX isNotEqualTo(Instance y);

    BooleanComparisonX isNotEqualTo(EntityExpression y);

    BooleanComparisonX isNullOrEqualTo(Entity y);

    BooleanComparisonX isNullOrEqualTo(Instance y);

    BooleanComparisonX isNullOrEqualTo(EntityExpression y);

    BooleanComparisonX isNullOrNotEqualTo(Entity y);

    BooleanComparisonX isNullOrNotEqualTo(Instance y);

    BooleanComparisonX isNullOrNotEqualTo(EntityExpression y);

    EntityOrderedPairX coalesce(Entity y);

    EntityOrderedPairX coalesce(Instance y);

    EntityOrderedPairX coalesce(EntityExpression y);

}
