/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.expressions;

import adalid.core.AbstractExpression;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.Operator;
import adalid.core.interfaces.Property;

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
        return o == null ? null : new Object[]{o};
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
