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
package adalid.core.wrappers;

import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.programmers.*;
import adalid.core.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class DataArtifactWrapper extends ArtifactWrapper {

    private final DataArtifact _dataArtifact;

    private final StringData _stringDataArtifact;

    private final IntervalizedArtifact _intervalizedArtifact;

    public DataArtifactWrapper(DataArtifact dataArtifact) {
        super(dataArtifact);
        _dataArtifact = dataArtifact;
        _stringDataArtifact = _dataArtifact instanceof StringData ? (StringData) _dataArtifact : null;
        _intervalizedArtifact = _dataArtifact instanceof IntervalizedArtifact ? (IntervalizedArtifact) _dataArtifact : null;
    }

    @Override
    public DataArtifact getWrapped() {
        return _dataArtifact;
    }

    /**
     * @return the bundle key of properties and properties
     */
    public String getSpecialBundleKey() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        if (sqlProgrammer == null) {
            return null;
        }
        List<String> sqlNames = new ArrayList<>();
        if (_dataArtifact.isProperty()) {
            sqlNames.add(sqlProgrammer.getSqlName(_dataArtifact.getDeclaringEntityRoot()));
            sqlNames.add(sqlProgrammer.getSqlName(_dataArtifact));
        } else if (_dataArtifact.isParameter()) {
            sqlNames.add(sqlProgrammer.getSqlName(_dataArtifact.getDeclaringOperation().getDeclaringEntityRoot()));
            sqlNames.add(sqlProgrammer.getSqlName(_dataArtifact.getDeclaringOperation()));
            sqlNames.add(sqlProgrammer.getSqlName(_dataArtifact));
        }
        return sqlNames.isEmpty() ? null : StringUtils.join(sqlNames, ".");
    }

    /**
     * @return the bundle default regex error message
     */
    public String getBundleDefaultRegexErrorMessage() {
        return _stringDataArtifact == null ? null : getBundleValueString(_stringDataArtifact.getDefaultRegexErrorMessage());
    }

    /**
     * @return the bundle default range error message
     */
    public String getBundleDefaultRangeErrorMessage() {
        return _intervalizedArtifact == null ? null : getBundleValueString(_intervalizedArtifact.getDefaultRangeErrorMessage());
    }

    /**
     * @return the Java getter
     */
    public String getJavaGetter() {
        return "get" + getJavaCapitalizedName();
    }

    /**
     * @return the Java setter
     */
    public String getJavaSetter() {
        return "set" + getJavaCapitalizedName();
    }

    /**
     * @return the Java type
     */
    public String getJavaType() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaType(_dataArtifact);
    }

    /**
     * @return the Java primitive type
     */
    public String getJavaPrimitiveType() {
        Entity entity = _dataArtifact instanceof Entity ? (Entity) _dataArtifact : null;
        DataArtifact dataArtifact = entity != null ? entity.getPrimaryKeyProperty() : _dataArtifact;
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaType(dataArtifact);
    }

    /**
     * @return the Java primitive type
     */
    public String getJavaPrimitiveTypeName() {
        Entity entity = _dataArtifact instanceof Entity ? (Entity) _dataArtifact : null;
        DataArtifact dataArtifact = entity != null ? entity.getPrimaryKeyProperty() : _dataArtifact;
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaTypeName(dataArtifact);
    }

    /**
     * @return the Java type simple name
     */
    public String getJavaTypeSimpleName() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaTypeSimpleName(_dataArtifact);
    }

    /**
     * @return the Java initial value
     */
    public String getJavaInitialValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaInitialValue(_dataArtifact);
    }

    /**
     * @return the Java default value
     */
    public String getJavaDefaultValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaDefaultValue(_dataArtifact);
    }

    /**
     * @return the Java current value
     */
    public String getJavaCurrentValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaCurrentValue(_dataArtifact);
    }

    /**
     * @return the Java maximum value
     */
    public String getJavaMaximumValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaMaximumValue(_dataArtifact);
    }

    /**
     * @return the Java minimum value
     */
    public String getJavaMinimumValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaMinimumValue(_dataArtifact);
    }

    /**
     * @return the Java nullifying value
     */
    public String getJavaNullifyingValue() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaNullifyingValue(_dataArtifact);
    }

    public String getJavaPrimitiveValue(Object object) {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaPrimitiveValue(object, getJavaPrimitiveType());
    }

    /**
     * @return the Java parameterized expression
     */
    public ParameterizedExpression getJavaParameterizedExpression() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaParameterizedExpression(_dataArtifact);
    }

    /**
     * @return the sql default regex error message
     */
    public String getSqlDefaultRegexErrorMessage() {
        return _stringDataArtifact == null ? null : getSqlString(_stringDataArtifact.getDefaultRegexErrorMessage());
    }

    /**
     * @return the sql default range error message
     */
    public String getSqlDefaultRangeErrorMessage() {
        return _intervalizedArtifact == null ? null : getSqlString(_intervalizedArtifact.getDefaultRangeErrorMessage());
    }

    /**
     * @return the column's table name
     */
    public String getSqlColumnTableName() {
        PersistentEntity pent = _dataArtifact.isProperty() ? _dataArtifact.getDeclaringFieldPersistentEntityTableRoot() : null;
        return pent == null ? null : sqlTableName(pent);
    }

    private String sqlTableName(Entity entity) {
        Entity base = entity.getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy ims = pent == null ? null : pent.getInheritanceMappingStrategy();
        return InheritanceMappingStrategy.SINGLE_TABLE.equals(ims) ? sqlTableName(pent) : sqlName(entity.getRoot());
    }

    private String sqlName(Entity entity) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(entity);
    }

    /**
     * @return the SQL type
     */
    public String getSqlType() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlType(_dataArtifact);
    }

    /**
     * @return the SQL CLOB indicator
     */
    public boolean isSqlCharacterLargeObject() {
        return _stringDataArtifact != null && getSqlMaxLength() == null;
    }

    /**
     * @return the SQL max length
     */
    public Integer getSqlMaxLength() {
        Integer maxLength = _stringDataArtifact == null ? null : _stringDataArtifact.getMaxLength();
        if (maxLength == null) {
            return null;
        }
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        if (sqlProgrammer == null) {
            return maxLength;
        }
        int maxVarcharLength = sqlProgrammer.getMaxVarcharLength();
        return maxLength > maxVarcharLength ? null : maxLength;
    }

    /**
     * @return the SQL parameter type
     */
    public String getSqlParameterType() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterType(_dataArtifact);
    }

    /**
     * @return the SQL null clause
     */
    public String getSqlNull() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlNull(_dataArtifact);
    }

    /**
     * @return the SQL initial value
     */
    public String getSqlInitialValue() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlInitialValue(_dataArtifact);
    }

    /**
     * @param queryTable the query table
     * @return the SQL initial value
     */
    public String getSqlInitialValue(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlInitialValue(_dataArtifact, queryTable);
    }

    /**
     * @return the SQL default value
     */
    public String getSqlDefaultValue() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlDefaultValue(_dataArtifact);
    }

    /**
     * @param queryTable the query table
     * @return the SQL default value
     */
    public String getSqlDefaultValue(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlDefaultValue(_dataArtifact, queryTable);
    }

    /**
     * @param queryTable the query table
     * @param unwrapped unwrap unnecessary scalar conversions
     * @return the SQL default value
     */
    public String getSqlDefaultValue(QueryTable queryTable, boolean unwrapped) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlDefaultValue(_dataArtifact, queryTable, unwrapped);
    }

    /**
     * @return the SQL current value
     */
    public String getSqlCurrentValue() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlCurrentValue(_dataArtifact);
    }

    /**
     * @param queryTable the query table
     * @return the SQL current value
     */
    public String getSqlCurrentValue(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlCurrentValue(_dataArtifact, queryTable);
    }

}
