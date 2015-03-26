/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.expressions;

import adalid.core.Instance;
import adalid.core.interfaces.ConditionalX;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityExpression;

/**
 * @author Jorge Campins
 */
public class EntityOtherwiseX extends AbstractConditionalX implements EntityExpression {

    EntityOtherwiseX(ConditionalX conditional, Object value) {
        super(conditional, value);
    }

    // <editor-fold defaultstate="collapsed" desc="EntityExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Entity.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Entity.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(Entity y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(Instance y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(Entity y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(Instance y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(Entity y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(Instance y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Entity y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Instance y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(Entity y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(Instance y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(EntityExpression y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }
    // </editor-fold>

}
