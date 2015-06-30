/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.exceptions.InstantiationRuntimeException;
import adalid.core.expressions.BooleanX;
import adalid.core.expressions.CharacterX;
import adalid.core.expressions.EntityX;
import adalid.core.expressions.NumericX;
import adalid.core.expressions.TemporalX;
import adalid.core.expressions.VariantX;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.CharacterExpression;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityExpression;
import adalid.core.interfaces.NumericExpression;
import adalid.core.interfaces.TemporalExpression;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author Jorge Campins
 */
public class XS2 {

    public static boolean checkAccess() {
        return XS1.checkAccess();
    }

    public static Class<?> getNamedClass(Object object) {
        return XS1.getNamedClass(object);
    }

    public static Class<?> getNamedClass(Class<?> clazz) {
        return XS1.getNamedClass(clazz);
    }

    public static Collection<Field> getFields(Class<?> clazz) throws SecurityException {
        return XS1.getFields(clazz, clazz);
    }

    public static Collection<Field> getFields(Class<?> clazz, Class<?> top) throws SecurityException {
        return XS1.getFields(clazz, top);
    }

    public static void setDataClass(AbstractDataArtifact artifact, Class<?> clazz) {
        XS1.checkAccess();
        artifact.setDataClass(clazz);
    }

    public static void setDataType(AbstractDataArtifact artifact, Class<?> type) {
        XS1.checkAccess();
        artifact.setDataType(type);
    }

    public static VariantX getForeignExpression(Class<? extends VariantX> type, String name, Class<? extends Entity> declaringClass) {
        Field field = getForeignExpressionField(name, declaringClass);
        if (field != null) {
            Class<?> fieldType = field.getType();
            if (fieldType.isInterface() && fieldType.isAssignableFrom(type)) {
                VariantX expression;
                if (EntityExpression.class.isAssignableFrom(fieldType)) {
                    expression = new EntityX();
                } else if (BooleanExpression.class.isAssignableFrom(fieldType)) {
                    expression = new BooleanX();
                } else if (CharacterExpression.class.isAssignableFrom(fieldType)) {
                    expression = new CharacterX();
                } else if (NumericExpression.class.isAssignableFrom(fieldType)) {
                    expression = new NumericX();
                } else if (TemporalExpression.class.isAssignableFrom(fieldType)) {
                    expression = new TemporalX();
                } else {
                    expression = new VariantX();
                }
                expression.setForeignExpressionField(field);
                return expression;
            }
        }
        String errmsg = "failed to get foreign expression " + name + " at " + declaringClass;
        throw new InstantiationRuntimeException(errmsg);
    }

    private static Field getForeignExpressionField(String name, Class<?> type) {
        String role = "foreign expression";
        Class<?> top = Entity.class;
        Class<?>[] validTypes = null;
        return XS1.getField(true, role, name, type, top, validTypes);
    }

}
