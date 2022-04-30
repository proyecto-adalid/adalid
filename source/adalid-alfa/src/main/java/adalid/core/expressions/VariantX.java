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
import adalid.core.interfaces.*;

/**
 * @author Jorge Campins
 */
public class VariantX extends AbstractExpression {

    public VariantX() {
        super();
        initDataType();
    }

    private void initDataType() {
        setDataType(Object.class);
    }

    @Override
    public Operator getOperator() {
        return null;
    }

    @Override
    public Object[] getOperands() {
        Object o = getExpressionFunctionArgument();
        return o == null ? new Object[]{} : new Object[]{o};
    }

    public Property getExpressionFunctionArgument() {
        Entity e = getDeclaringEntity();
        return e == null ? null : e.isRootInstance() ? e.getPrimaryKeyProperty() : (Property) e;
    }

    /**
     * @return the declaring entity
     */
    @Override
    public Entity getDeclaringEntity() {
        Entity declaringEntity = super.getDeclaringEntity();
        if (declaringEntity == null) {
            Expression parentExpression = getParentExpression();
            return parentExpression == null ? null : parentExpression.getDeclaringEntity();
        } else {
            return declaringEntity;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        return "(" + getDataType().getSimpleName() + ")" + super.toString();
    }
    // </editor-fold>

}
