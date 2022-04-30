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
package adalid.core.interfaces;

import adalid.commons.interfaces.*;
import adalid.core.programmers.*;

/**
 * @author Jorge Campins
 */
public interface JavaProgrammer extends Programmer {

    String getJavaClassName(Artifact artifact);

    String getJavaClassName(String name);

    String getJavaConstantName(Artifact artifact);

    String getJavaConstantName(String name);

    String getJavaCurrentValue(DataArtifact artifact);

    String getJavaDefaultValue(DataArtifact artifact);

    String getJavaInitialValue(DataArtifact artifact);

    String getJavaMaximumValue(DataArtifact artifact);

    String getJavaMinimumValue(DataArtifact artifact);

    String getJavaNullifyingValue(DataArtifact artifact);

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

    String getJavaPrimitiveValue(Object object, Class<?> type);

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

    String getJavaExpressionOfSqlExpression(String sqlExpression);

    ParameterizedExpression getJavaParameterizedExpression(Object object);

}
