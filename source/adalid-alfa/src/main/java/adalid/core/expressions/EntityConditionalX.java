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

/**
 * @author Jorge Campins
 */
public class EntityConditionalX extends AbstractConditionalX implements EntityExpression {

    EntityConditionalX(BooleanExpression booleanExpression, Object value) {
        super(booleanExpression, value);
    }

    public EntityOtherwiseX otherwise(Entity value) {
        return XB.Entity.Conditional.otherwise(this, value);
    }

    public EntityOtherwiseX otherwise(Instance value) {
        return XB.Entity.Conditional.otherwise(this, value);
    }

    public EntityOtherwiseX otherwise(SpecialEntityValue value) {
        return XB.Entity.Conditional.otherwise(this, value);
    }

    public EntityOtherwiseX otherwise(EntityExpression value) {
        return XB.Entity.Conditional.otherwise(this, value);
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
