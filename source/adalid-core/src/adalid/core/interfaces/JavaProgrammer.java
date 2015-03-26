/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.commons.interfaces.Programmer;
import adalid.core.programmers.ParameterizedExpression;

/**
 * @author Jorge Campins
 */
public interface JavaProgrammer extends Programmer {

    String getJavaClassName(Artifact artifact);

    String getJavaClassName(String name);

    String getJavaConstantName(Artifact artifact);

    String getJavaConstantName(String name);

    String getJavaCurrentValue(Artifact artifact);

    String getJavaDefaultValue(Artifact artifact);

    String getJavaInitialValue(Artifact artifact);

    String getJavaLowerClassName(Artifact artifact);

    String getJavaLowerClassName(String name);

    String getJavaLowerConstantName(Artifact artifact);

    String getJavaLowerConstantName(String name);

    String getJavaLowerVariableName(Artifact artifact);

    String getJavaLowerVariableName(String name);

    String getJavaName(Artifact artifact);

    String getJavaName(String name);

    String getJavaValue(Object object);

    String getJavaPrimitiveValue(Object object, String typeName);

    String getJavaQualifiedName(Artifact artifact);

    String getJavaType(Artifact artifact);

    String getJavaTypeCanonicalName(Artifact artifact);

    String getJavaTypeName(Artifact artifact);

    String getJavaTypeSimpleName(Artifact artifact);

    String getJavaUpperClassName(Artifact artifact);

    String getJavaUpperClassName(String name);

    String getJavaUpperConstantName(Artifact artifact);

    String getJavaUpperConstantName(String name);

    String getJavaUpperVariableName(Artifact artifact);

    String getJavaUpperVariableName(String name);

    String getJavaVariableName(Artifact artifact);

    String getJavaVariableName(String name);

    String getJavaString(String string);

    ParameterizedExpression getJavaParameterizedExpression(Object object);

}
