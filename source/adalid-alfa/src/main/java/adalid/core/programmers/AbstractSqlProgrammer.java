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
package adalid.core.programmers;

import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.sql.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractSqlProgrammer extends AbstractProgrammer implements SqlProgrammer {

    private static final Logger logger = Logger.getLogger(SqlProgrammer.class);

    private static final String EMPTY = "";

    private static final String EXPRESSION_DOLLAR_INFIX = "$";

    private static final String EXPRESSION_SELECT_INFIX = "$select_";

    // <editor-fold defaultstate="collapsed" desc="bootstrapping">
    protected static final ExtendedProperties bootstrapping = PropertiesHandler.getBootstrapping();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="string constants">
    protected static final String SEP$ = COM$;

    protected static final String SEPX = COM$ + " ";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SQL comparison operators">
    protected String getIsNull() {
        return "is null";
    }

    protected String getIsNotNull() {
        return "is not null";
    }

    protected String getIsTrue() {
        return "is true";
    }
//
//  protected String getIsNotTrue() {
//      return "is not true";
//  }

    protected String getIsFalse() {
        return "is false";
    }
//
//  protected String getIsNotFalse() {
//      return "is not false";
//  }

    protected String getEQ() {
        return "=";
    }

    protected String getNEQ() {
        return "<>";
    }

    protected String getGT() {
        return ">";
    }

    protected String getLTEQ() {
        return "<=";
    }

    protected String getGTEQ() {
        return ">=";
    }

    protected String getLT() {
        return "<";
    }

    protected String getLike() {
        return "like";
    }

    protected String getNotLike() {
        return "not like";
    }

    protected String getIn() {
        return "in";
    }

    protected String getNotIn() {
        return "not in";
    }

    protected String getIsNullOr() {
        return "is null or";
    }

    protected String getIsNotNullAnd() {
        return "is not null and";
    }

    protected String getBetween() {
        return "between";
    }

    protected String getNotBetween() {
        return "not between";
    }

    protected String getExists() {
        return "exists";
    }

    protected String getNotExists() {
        return "not exists";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="SQL comparison compound operators">
    private final String[] NEVER_NULL = {
        "current_user", "current_date", "current_time", "current_timestamp", "localtime", "localtimestamp"
    };

    protected String[] neverNull() {
        return NEVER_NULL;
    }

    protected boolean neverNull(String columna) {
        return ArrayUtils.contains(neverNull(), columna.toLowerCase());
    }

    protected String getIsNullOr(String columna) {
        return getIsNullOr(columna, false);
    }

    protected String getIsNullOr(String columna, boolean b) {
        String columnb = b ? "" : columna + SPC$;
        return neverNull(columna) ? columnb : columna + SPC$ + getIsNullOr() + SPC$ + columnb;
    }

    protected String getIsNotNullAnd(String columna) {
        return getIsNotNullAnd(columna, false);
    }

    protected String getIsNotNullAnd(String columna, boolean b) {
        String columnb = b ? "" : columna + SPC$;
//      return neverNull(columna) ? columnb : columna + SPC$ + getIsNotNullAnd() + SPC$ + columnb;
        return columnb; // since 20201221
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="referential integrity options">
    protected abstract String getRestricted();

    protected abstract String getCascade();

    protected abstract String getNullify();

    protected abstract String getNoAction();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="join operators">
    protected String getInnerJoin() {
        return "inner join";
    }

    protected String getLeftJoin() {
        return "left outer join";
    }

    protected String getRightJoin() {
        return "right outer join";
    }

    protected String getFullJoin() {
        return "full join";
    }

    protected String getCrossJoin() {
        return "cross join";
    }

    protected String getDefaultJoin() {
        return "join";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="aggregate functions">
    protected String getCount() {
        return "count";
    }

    protected String getMaximum() {
        return "max";
    }

    protected String getMinimum() {
        return "min";
    }

    protected String getSum() {
        return "sum";
    }

    protected String getAverage() {
        return "avg";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="n-ary operators">
    protected String getConcat() {
        return "||";
    }

    protected String getAnd() {
        return "and";
    }

    protected String getOr() {
        return "or";
    }

    protected String getAdd() {
        return "+";
    }

    protected String getMultiply() {
        return "*";
    }

    protected String getCoalesce() {
        return "coalesce";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="unary operators">
    protected String getNot() {
        return "not";
    }

    protected String getModulus() {
        return "abs";
    }

    protected String getOpposite() {
        return "(-1)*";
    }

    protected String getReciprocal() {
        return "1/";
    }

    protected String getNz() {
        return "coalesce";
    }

    protected String getCast() {
        return "cast";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sort operators">
    protected String getAscending() {
        return "asc";
    }

    protected String getDescending() {
        return "desc";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="keywords">
    protected String getTrue() {
        return "true";
    }

    protected String getFalse() {
        return "false";
    }

    protected String getCurrentDate() {
        return "current_date";
    }

    protected String getCurrentTime() {
        return "current_time";
    }

    protected String getCurrentTimestamp() {
        return "current_timestamp";
    }

    protected String getNull() {
        return "null";
    }

    protected String getNotNull() {
        return "not null";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="zeroes">
    protected String getZeroChar() {
        return SQM$ + SPC$ + SQM$;
    }

    protected String getZeroString() {
        return SQM$ + SQM$;
    }

    protected String getZeroNumber() {
        return "0";
    }

    protected String getZeroDate() {
        return SQM$ + "1970-01-01" + SQM$;
    }

    protected String getZeroTime() {
        return SQM$ + "00:00:00" + SQM$;
    }

    protected String getZeroTimestamp() {
        return SQM$ + "1970-01-01 00:00:00" + SQM$;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="select statement keywords">
    protected String getSelect() {
        return "select";
    }

    protected String getInto() {
        return "into";
    }

    protected String getFrom() {
        return "from";
    }

    protected String getAs() {
        return "as";
    }

    protected String getOn() {
        return "on";
    }

    protected String getWhere() {
        return "where";
    }

    protected String getOrderBy() {
        return "order by";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="case keywords">
    protected String getCase() {
        return "case";
    }

    protected String getWhen() {
        return "when";
    }

    protected String getThen() {
        return "then";
    }

    protected String getElse() {
        return "else";
    }

    protected String getEnd() {
        return "end";
    }

    protected String getCaseWhenThenPattern() {
        return "case when {0} then {1} end";
    }

    protected String getCaseWhenThenElsePattern() {
        return "case when {0} then {1} else {2} end";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="like special characters">
    protected String getLikeSingle() {
        return "_";
    }

    protected String getLikeString() {
        return "%";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="variables prefix and suffix">
    protected String getVariablesPrefix() {
        return "_";
    }

    protected String getVariablesSuffix() {
        return "$";
    }

    protected String getRecordVariableName() {
        return getSqlVariableName("record");
    }

    protected String getRecordVariableName(int index) {
        return getSqlVariableName("record" + index);
    }

    protected String getRowVariableName() {
        return getSqlVariableName("row");
    }

    protected String getRowVariableName(int index) {
        return getSqlVariableName("row" + index);
    }

    protected String getValueVariableName() {
        return getSqlVariableName("value");
    }

    protected String getValueVariableName(int index) {
        return getSqlVariableName("value" + index);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getString...">
    @Override
    public String getString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            return escapeQuotes(obj.toString());
        } else if (obj instanceof Date) {
            return TimeUtils.jdbcDateString(obj);
        } else if (obj instanceof Time) {
            return TimeUtils.jdbcTimeString(obj);
        } else if (obj instanceof java.util.Date) {
            return TimeUtils.jdbcTimestampString(obj);
        } else {
            return obj.toString();
        }
    }

    @Override
    public String getDelimitedString(Object obj) {
        String string = getString(obj);
        if (string == null) {
            return null;
        } else if (obj instanceof String) {
            return SQM$ + string + SQM$;
        } else if (obj instanceof Date) {
            return SQM$ + string + SQM$;
        } else if (obj instanceof Time) {
            return SQM$ + string + SQM$;
        } else if (obj instanceof java.util.Date) {
            return SQM$ + string + SQM$;
        } else {
            return string;
        }
    }
    // </editor-fold>

    public String escapeQuotes(String string) {
        return StringUtils.replace(string, SQM$, SQM$ + SQM$);
    }

    /**
     * @param string string
     * @return the SQL identifier for string
     */
    @Override
    public String getSqlIdentifier(String string) {
        return getSqlIdentifier(null, string, null);
    }

    /**
     * @param prefix prefix
     * @param string string
     * @param suffix suffix
     * @return the prefixed and suffixed SQL identifier for string
     */
    @Override
    public String getSqlIdentifier(String prefix, String string, String suffix) {
        return StrUtils.getIdentificadorSql(prefix, string, suffix, getMaxIdentifierLength());
    }

    /**
     * @param artifact artifact
     * @return the SQL-ish name
     */
    @Override
    public String getSqlishName(Artifact artifact) {
        return artifact == null ? null : getArtifactSqlName(artifact, 0);
    }

    /**
     * @param artifact artifact
     * @return the SQL name
     */
    @Override
    public String getSqlName(Artifact artifact) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getSqlName(null, artifact, null, maxIdentifierLength);
    }

    /**
     * @param artifact artifact
     * @param maxIdentifierLength max identifier length
     * @return the SQL name
     */
    @Override
    public String getSqlName(Artifact artifact, int maxIdentifierLength) {
        return getSqlName(null, artifact, null, maxIdentifierLength);
    }

    /**
     * @param prefix prefix
     * @param artifact artifact
     * @return the SQL name
     */
    @Override
    public String getSqlName(String prefix, Artifact artifact) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getSqlName(prefix, artifact, null, maxIdentifierLength);
    }

    /**
     * @param prefix prefix
     * @param artifact artifact
     * @param maxIdentifierLength max identifier length
     * @return the SQL name
     */
    @Override
    public String getSqlName(String prefix, Artifact artifact, int maxIdentifierLength) {
        return getSqlName(prefix, artifact, null, maxIdentifierLength);
    }

    /**
     * @param artifact artifact
     * @param suffix suffix
     * @return the SQL name
     */
    @Override
    public String getSqlName(Artifact artifact, String suffix) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getSqlName(null, artifact, suffix, maxIdentifierLength);
    }

    /**
     * @param artifact artifact
     * @param suffix suffix
     * @param maxIdentifierLength max identifier length
     * @return the SQL name
     */
    @Override
    public String getSqlName(Artifact artifact, String suffix, int maxIdentifierLength) {
        return getSqlName(null, artifact, suffix, maxIdentifierLength);
    }

    /**
     * @param prefix prefix
     * @param artifact artifact
     * @param suffix suffix
     * @return the SQL name
     */
    @Override
    public String getSqlName(String prefix, Artifact artifact, String suffix) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getSqlName(prefix, artifact, suffix, maxIdentifierLength);
    }

    /**
     * @param prefix prefix
     * @param artifact artifact
     * @param suffix suffix
     * @param maxIdentifierLength max identifier length
     * @return the SQL name
     */
    @Override
    public String getSqlName(String prefix, Artifact artifact, String suffix, int maxIdentifierLength) {
        String fullName = getFullLengthSqlName(artifact);
        if (fullName == null) {
            return null;
        }
        String lowerPrefix = prefix == null ? null : StrUtils.getLowerHumplessCase(prefix);
        String lowerSuffix = suffix == null ? null : StrUtils.getLowerHumplessCase(suffix);
        int max = getMaxIdentifierLength(maxIdentifierLength);
        return StrUtils.getIdentificadorSql(lowerPrefix, fullName, lowerSuffix, max);
    }

    private String getFullLengthSqlName(Artifact artifact) {
        if (artifact == null) {
            return null;
        }
        String sqlName = artifact.getSqlName();
        if (StringUtils.isNotBlank(sqlName)) {
            return StrUtils.getLowerHumplessCase(sqlName);
        }
        return artifact instanceof Property ? getPropertySqlName(artifact, 0) : getArtifactSqlName(artifact, 0);
    }

    private int getMaxIdentifierLength(int maxIdentifierLength) {
        int mil = getMaxIdentifierLength();
        return Math.max(Math.min(maxIdentifierLength, mil), 0);
    }

    /**
     * @param property property
     * @param queryTable query table
     * @return the SQL name of the property if it is found in queryTable; null otherwise
     */
    @Override
    public String getSqlAlias(Property property, QueryTable queryTable) {
        if (property == null || queryTable == null) {
            return null;
        }
        int index = queryTable.getSubqueryIndex();
        String name;
        for (Property p : queryTable.getColumns()) {
            if (p == property) {
                name = index == 0 ? getPropertySqlName(p) : getPropertySqlAlias(p, queryTable);
                return name;
            }
        }
        for (QueryJoin j : queryTable.getJoins()) {
            name = getSqlAlias(property, j.getRightTable());
            if (name == null) {
                continue;
            }
            return name;
        }
        return null;
    }

    @Override
    public Property getProperty(String sqlAlias, QueryTable queryTable) {
        if (sqlAlias == null || queryTable == null) {
            return null;
        }
        for (Property p : queryTable.getColumns()) {
            String alias = getSqlAlias(p, queryTable);
            if (sqlAlias.equals(alias)) {
                return p;
            }
        }
        for (QueryJoin j : queryTable.getJoins()) {
            Property p = getProperty(sqlAlias, j.getRightTable());
            if (p == null) {
                continue;
            }
            return p;
        }
        return null;
    }

    /**
     * @param property property
     * @param queryTable query table
     * @return the SQL name of the property if it is found in queryTable; null otherwise
     */
    @Override
    public String getSqlQualifiedName(Property property, QueryTable queryTable) {
        if (property == null || queryTable == null) {
            return null;
        }
        String name;
        /*
        for (Property p : queryTable.getColumns()) {
            if (p == property) {
                name = queryTable.getAlias() + DOT$ + getPropertySqlName(p);
                return name;
            }
        }
        for (QueryJoin j : queryTable.getJoins()) {
            name = getSqlQualifiedName(property, j.getRightTable());
            if (name == null) {
                continue;
            }
            return name;
        }
        **/
        boolean calculable = property.isCalculable();
        if (calculable) {
            String expression = getCalculableColumnValueExpression(queryTable, property);
            if (expression != null) {
                return expression;
            }
        } else {
            QueryTable qt = queryTable.containingQueryTableOf(property);
            if (qt != null) {
                name = qt.getAlias() + DOT$ + getPropertySqlName(property);
                return name;
            }
        }
        return null;
    }

    private String getPropertySqlAlias(Property property, QueryTable queryTable) {
////    PersistentEntity pent = property.getDeclaringFieldPersistentEntityRoot();
////    String name = pent == null ? getPropertySqlName(property) : getArtifactSqlName(property);
//      String name = getPropertySqlName(property);
//      return queryTable.getPrefix() + name + queryTable.getSuffix();
        String prefix = queryTable.getPrefix();
        String string = getSqlName(property, 0);
        String suffix = queryTable.getSuffix();
        return getSqlIdentifier(prefix, string, suffix);
    }

    private String getPropertySqlName(Artifact artifact) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getPropertySqlName(artifact, maxIdentifierLength);
    }

    private String getPropertySqlName(Artifact artifact, int maxIdentifierLength) {
        String sqlName = artifact.getSqlName();
        if (StringUtils.isNotBlank(sqlName)) {
//          return StrUtils.getLowerHumplessCase(sqlName);
            return StrUtils.getIdentificadorSql(StrUtils.getLowerHumplessCase(sqlName), maxIdentifierLength);
        }
        String name = getArtifactFixedCaseName(artifact);
        PersistentEntity pent = artifact.getDeclaringFieldPersistentEntityRoot();
        if (pent != null) {
//          String propertiesPrefix = pent.getPropertiesPrefix();
//          String propertiesSuffix = pent.getPropertiesSuffix();
            String discriminatorValue = pent.getDiscriminatorValue();
            boolean notTable = pent.isNotTable();
//          boolean prefixed = StringUtils.isNotBlank(propertiesPrefix);
//          boolean suffixed = StringUtils.isNotBlank(propertiesSuffix);
            boolean discriminated = StringUtils.isNotBlank(discriminatorValue);
//          if (prefixed) {
//              name = propertiesPrefix + StringUtils.capitalize(name);
//          }
//          if (suffixed) {
//                name += StringUtils.capitalize(propertiesSuffix);
//          }
//          if (b && notTable && discriminated && !prefixed && !suffixed) {
            if (notTable && discriminated) {
//              name += StringUtils.capitalize("x" + discriminatorValue);
                String prefix = "";
                String string = StrUtils.getLowerHumplessCase(name);
                String suffix = "_x" + discriminatorValue.toLowerCase();
                return StrUtils.getIdentificadorSql(prefix, string, suffix, maxIdentifierLength);
            }
        }
//      return StrUtils.getLowerHumplessCase(name);
        return StrUtils.getIdentificadorSql(StrUtils.getLowerHumplessCase(name), maxIdentifierLength);
    }

    /*
    private String getArtifactSqlName(Artifact artifact) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getArtifactSqlName(artifact, maxIdentifierLength);
    }

    /**/
    private String getArtifactSqlName(Artifact artifact, int maxIdentifierLength) {
        String name = getArtifactFixedCaseName(artifact);
        return StrUtils.getIdentificadorSql(StrUtils.getLowerHumplessCase(name), maxIdentifierLength);
    }

    private String getArtifactFixedCaseName(Artifact artifact) {
        String name = artifact.getName();
        String NAME = StringUtils.upperCase(name);
        return StringUtils.equals(name, NAME) ? StringUtils.lowerCase(name) : name;
    }

    private String getSqlParameterName(Artifact artifact) {
        return "$" + LCB + getSqlQualifiedName(artifact) + RCB;
    }
//
//  private String getSqlParameterName(NamedValue value) {
//      return "$" + LCB + value.name() + RCB;
//  }

    /**
     * @param artifact artifact
     * @return the SQL qualified name
     */
    @Override
    public String getSqlQualifiedName(Artifact artifact) {
        String p;
        if (artifact.getDeclaringField() == null || artifact.getDeclaringArtifact() == null) {
            p = getSqlName(artifact);
        } else {
            p = getSqlQualifiedName(artifact.getDeclaringArtifact()) + DOT$ + getSqlName(artifact);
            if (artifact.getDeclaringField().getType().isArray()) {
                p += UND$ + artifact.getDeclaringFieldIndex();
            }
            p = StringUtils.removeStart(p, DOT$);
        }
        return p;
    }

    /**
     * @param artifact artifact
     * @return the SQL variable name
     */
    @Override
    public String getSqlVariableName(Artifact artifact) {
//      String name = getSqlName(artifact);
//      return getSqlVariableName(name);
        if (artifact == null) {
            return null;
        }
        String prefix = getVariablesPrefix();
        String suffix = getVariablesSuffix();
        return getSqlName(prefix, artifact, suffix);
    }

    /**
     * @param name name
     * @return the SQL variable name
     */
    @Override
    public String getSqlVariableName(String name) {
//      return name == null ? null : getVariablesPrefix() + name.trim() + getVariablesSuffix();
        if (name == null) {
            return null;
        }
        String prefix = getVariablesPrefix();
        String string = StrUtils.getLowerHumplessCase(name);
        String suffix = getVariablesSuffix();
        return getSqlIdentifier(prefix, string, suffix);
    }

    /**
     * @param entity entity
     * @return the discriminator value
     */
    @Override
    public String getSqlDiscriminatorValue(PersistentEntity entity) {
        Property discriminatorProperty = entity.getDiscriminatorProperty();
        if (discriminatorProperty != null) {
            String discriminatorValue = entity.getDiscriminatorValue();
            if (discriminatorValue != null) {
                Class<?> dataClass = discriminatorProperty.getDataClass();
                if (CharacterData.class.isAssignableFrom(dataClass)) {
                    return SQM$ + discriminatorValue + SQM$;
                } else {
                    return discriminatorValue;
                }
            } else if (entity.isAbstractClass()) {
                return null;
            } else {
                return getNull();
            }
        }
        return null;
    }

    /**
     * @param entity entity
     * @return the discriminator values
     */
    @Override
    public List<String> getSqlDiscriminatorValues(PersistentEntity entity) {
        List<String> values = new ArrayList<>();
        String value = getSqlDiscriminatorValue(entity);
        if (value != null) {
            values.add(value);
        }
        List<Entity> extensionsList = entity.getExtensionsList();
        PersistentEntity pent;
        for (Entity ext : extensionsList) {
            if (ext instanceof PersistentEntity) {
                pent = (PersistentEntity) ext;
                value = getSqlDiscriminatorValue(pent);
                if (value != null) {
                    values.add(value);
                }
            }
        }
        Collections.sort(values);
        return values;
    }

    /**
     * @param entity entity
     * @return the schema name
     */
    @Override
    public String getSqlSchemaName(PersistentEntity entity) {
        Entity base = entity.getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy ims = pent == null ? null : pent.getInheritanceMappingStrategy();
        return InheritanceMappingStrategy.SINGLE_TABLE.equals(ims) ? getSqlSchemaName(pent)
            : StringUtils.trimToEmpty(entity.getSchema());
    }

    /**
     * @param entity entity
     * @return the table name
     */
    @Override
    public String getSqlTableName(PersistentEntity entity) {
        Entity base = entity.getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy ims = pent == null ? null : pent.getInheritanceMappingStrategy();
        return InheritanceMappingStrategy.SINGLE_TABLE.equals(ims) ? getSqlTableName(pent) : getSqlName(entity.getRoot());
    }

    /**
     * @param aggregation aggregation
     * @return the aggregation function name
     */
    @Override
    public String getSqlFunctionName(ViewFieldAggregation aggregation) {
        if (aggregation == null) {
            return null;
        }
        switch (aggregation) {
            case COUNT:
                return "COUNT";
            case MINIMUM:
                return "MIN";
            case MAXIMUM:
                return "MAX";
            case SUM:
                return "SUM";
            case AVERAGE:
                return "AVG";
            case DEVIATION:
                return "STDDEV";
            default:
                return "COUNT";
        }
    }

    @Override
    public String getSqlSchemaQualifier(PersistentEntity entity) {
        String schema = getSqlSchemaName(entity);
        return StringUtils.isBlank(schema) ? "" : schema.trim() + ".";
    }

    @Override
    public String getSqlSchemaQualifiedName(PersistentEntity entity) {
        return StrUtils.getQualifiedName(getSqlName(entity), getSqlSchemaName(entity));
    }

    @Override
    public String getSqlSchemaQualifiedShortName(PersistentEntity entity) {
        return StrUtils.getQualifiedShortName(getSqlName(entity), getSqlSchemaName(entity));
    }

    @Override
    public String getSqlSchemaUnqualifiedShortName(PersistentEntity entity) {
        return StrUtils.getUnqualifiedShortName(getSqlName(entity), getSqlSchemaName(entity));
    }

    @Override
    public String getSqlSchemaQualifiedTableName(PersistentEntity entity) {
        return StrUtils.getQualifiedName(getSqlTableName(entity), getSqlSchemaName(entity));
    }

    @Override
    public String getSqlSchemaQualifiedShortTableName(PersistentEntity entity) {
        return StrUtils.getQualifiedShortName(getSqlTableName(entity), getSqlSchemaName(entity));
    }

    @Override
    public String getSqlSchemaUnqualifiedShortTableName(PersistentEntity entity) {
        return StrUtils.getUnqualifiedShortName(getSqlTableName(entity), getSqlSchemaName(entity));
    }

    /**
     * @param artifact artifact
     * @return the SQL nulls clause
     */
    @Override
    public String getSqlNull(Artifact artifact) {
        if (artifact == null) {
            return null;
        } else if (artifact instanceof Property) {
            return getSqlNull((Property) artifact);
        } else {
            return null;
        }
    }

    private String getSqlNull(Property p) {
        if (p.isNullable()) {
            return EMPTY;
        }
        PersistentEntity declaringEntity = p.getDeclaringPersistentEntityRoot();
        return declaringEntity != null && declaringEntity.isTable() ? getNotNull() : EMPTY;
    }

    /**
     * @param artifact artifact
     * @return the SQL initial value
     */
    @Override
    public String getSqlInitialValue(Artifact artifact) {
        QueryTable queryTable = null;
        return getSqlInitialValue(artifact, queryTable);
    }

    /**
     * @param artifact artifact
     * @param queryTable query table
     * @return the SQL initial value
     */
    @Override
    public String getSqlInitialValue(Artifact artifact, QueryTable queryTable) {
        Object initialValue = getInitialValue(artifact);
        Class<?> dataType = getDataType(artifact);
        return initialValue == null || dataType == null ? null : getSqlValue(initialValue, dataType, queryTable);
    }

    /**
     * @param artifact artifact
     * @return the SQL default value
     */
    @Override
    public String getSqlDefaultValue(Artifact artifact) {
        QueryTable queryTable = null;
        return getSqlDefaultValue(artifact, queryTable);
    }

    /**
     * @param artifact artifact
     * @param queryTable query table
     * @return the SQL default value
     */
    @Override
    public String getSqlDefaultValue(Artifact artifact, QueryTable queryTable) {
        return getSqlDefaultValue(artifact, queryTable, false);
    }

    /**
     * @param artifact artifact
     * @param queryTable query table
     * @param unwrapped unwrap unnecessary scalar conversions
     * @return the SQL default value
     */
    @Override
    public String getSqlDefaultValue(Artifact artifact, QueryTable queryTable, boolean unwrapped) {
        Object defaultValue = getDefaultValue(artifact);
        if (unwrapped && defaultValue instanceof ScalarX) {
            defaultValue = ((ScalarX) defaultValue).unwrapValueExpression();
        }
        Class<?> dataType = getDataType(artifact);
        return defaultValue == null || dataType == null ? null : getSqlValue(defaultValue, dataType, queryTable);
    }

    /**
     * @param artifact artifact
     * @return the SQL current value
     */
    @Override
    public String getSqlCurrentValue(Artifact artifact) {
        QueryTable queryTable = null;
        return getSqlCurrentValue(artifact, queryTable);
    }

    /**
     * @param artifact artifact
     * @param queryTable query table
     * @return the SQL current value
     */
    @Override
    public String getSqlCurrentValue(Artifact artifact, QueryTable queryTable) {
        Object currentValue = getCurrentValue(artifact);
        Class<?> dataType = getDataType(artifact);
        return currentValue == null || dataType == null ? null : getSqlValue(currentValue, dataType, queryTable);
    }

    /**
     * Get the SQL value of an object.
     * <p>
     * This method is used by create-convert-function (and perhaps other) velocity templates.
     *
     * @param object object
     * @return the SQL value
     */
//  @Override
    public String getSqlValue(Object object) {
        return getSqlValue(object, null);
    }

    /**
     * Get the SQL value of an object within a QueryTable.
     * <p>
     * This method is used by create-convert-function (and perhaps other) velocity templates.
     *
     * @param object object
     * @param queryTable query table
     * @return the SQL value
     */
//  @Override
    public String getSqlValue(Object object, QueryTable queryTable) {
        return object == null ? null : getSqlValue(object, object.getClass(), queryTable);
    }

    private String getSqlValue(Object object, Class<?> type, QueryTable queryTable) {
        if (object == null || type == null) {
            return null;
        } else if (object instanceof Property) {
            Property property = (Property) object;
            return queryTable == null ? null : getQualifiedName(property, queryTable, SqlQualifierType.RECORD);
        } else if (object instanceof Expression) {
            Expression expression = (Expression) object;
            return queryTable == null ? null : getSqlExpression(expression, queryTable, SqlQualifierType.RECORD);
        } else if (object instanceof Instance) { // && Entity.class.isAssignableFrom(type)
            return getDelimitedString(((Instance) object).getInstanceKeyValue());
        } else if (object instanceof Artifact) {
            Artifact artifact = (Artifact) object;
            return getSqlQualifiedName(artifact);
        } else if (object instanceof SpecialBinaryValue) {
            SpecialBinaryValue value = (SpecialBinaryValue) object;
            return getSpecialBinaryValue(value);
        } else if (object instanceof SpecialBooleanValue) {
            SpecialBooleanValue value = (SpecialBooleanValue) object;
            return getSpecialBooleanValue(value);
        } else if (object instanceof SpecialCharacterValue) {
            SpecialCharacterValue value = (SpecialCharacterValue) object;
            return getSpecialCharacterValue(value);
        } else if (object instanceof SpecialEntityValue) {
            SpecialEntityValue value = (SpecialEntityValue) object;
            return getSpecialEntityValue(value);
        } else if (object instanceof SpecialNumericValue) {
            SpecialNumericValue value = (SpecialNumericValue) object;
            return getSpecialNumericValue(value);
        } else if (object instanceof SpecialTemporalValue) {
            SpecialTemporalValue value = (SpecialTemporalValue) object;
            return getSpecialTemporalValue(value);
        } else if (object instanceof NamedValue) {
            NamedValue value = (NamedValue) object;
            return value.name();
        } else {
            return getDelimitedString(object);
        }
    }

    @Override
    public String getSpecialBinaryValue(SpecialBinaryValue value) {
        switch (value) {
            case NULL:
                return getNull();
            default:
                return null;
        }
    }

    @Override
    public String getSpecialBooleanValue(SpecialBooleanValue value) {
        switch (value) {
            case NULL:
                return getNull();
            case TRUE:
                return getTrue();
            case FALSE:
                return getFalse();
            default:
                return null;
        }
    }

    @Override
    public String getSpecialCharacterValue(SpecialCharacterValue value) {
        switch (value) {
            case NULL:
                return getNull();
            case EMPTY:
                return getZeroString();
            case CURRENT_USER_CODE:
                return "current_user_code()";
            default:
                return null;
        }
    }

    @Override
    public String getSpecialEntityValue(SpecialEntityValue value) {
        switch (value) {
            case NULL:
                return getNull();
            case CURRENT_USER:
                return "current_user_id()";
            default:
                return null;
        }
    }

    @Override
    public String getSpecialNumericValue(SpecialNumericValue value) {
        switch (value) {
            case NULL:
                return getNull();
            case CURRENT_USER_ID:
                return "current_user_id()";
            default:
                return null;
        }
    }

    @Override
    public String getSpecialTemporalValue(SpecialTemporalValue value) {
        switch (value) {
            case NULL:
                return getNull();
            case CURRENT_DATE:
                return getCurrentDate();
            case CURRENT_TIME:
                return getCurrentTime();
            case CURRENT_TIMESTAMP:
                return getCurrentTimestamp();
            default:
                return value.name();
        }
    }

    private Class<?> getDataType(Artifact artifact) {
        TypedArtifact typedArtifact = artifact instanceof TypedArtifact ? (TypedArtifact) artifact : null;
        return typedArtifact == null ? null : typedArtifact.getDataType();
    }

    private Object getInitialValue(Artifact artifact) {
        ValuedArtifact valuedArtifact = (artifact instanceof ValuedArtifact) ? (ValuedArtifact) artifact : null;
        return valuedArtifact == null ? null : valuedArtifact.getInitialValue();
    }

    private Object getDefaultValue(Artifact artifact) {
        ValuedArtifact valuedArtifact = (artifact instanceof ValuedArtifact) ? (ValuedArtifact) artifact : null;
        return valuedArtifact == null ? null : valuedArtifact.getDefaultValue();
    }

    private Object getCurrentValue(Artifact artifact) {
        ValuedArtifact valuedArtifact = (artifact instanceof ValuedArtifact) ? (ValuedArtifact) artifact : null;
        return valuedArtifact == null ? null : valuedArtifact.getCurrentValue();
    }

    /**
     * @param object object
     * @return the SQL expression
     */
    @Override
    public String getSqlExpression(Object object) {
        QueryTable queryTable = null;
        return getSqlExpression(object, queryTable);
    }

    /**
     * @param object object
     * @param queryTable query table
     * @return the SQL expression
     */
    @Override
    public String getSqlExpression(Object object, QueryTable queryTable) {
        return getSqlExpression(object, queryTable, SqlQualifierType.RECORD);
    }

    /**
     * @param object object
     * @param queryTable query table
     * @param qualifier qualifier
     * @return the SQL expresion
     */
    @Override
    public String getSqlExpression(Object object, QueryTable queryTable, SqlQualifierType qualifier) {
        return getSqlExpression(object, queryTable, qualifier, null, false);
    }

    /**
     * @param object object
     * @param queryTablesMap query tables map
     * @return the SQL expression
     */
    @Override
    public String getSqlExpression(Object object, Map<String, QueryTable> queryTablesMap) {
        return getSqlExpression(object, queryTablesMap, SqlQualifierType.RECORD);
    }

    /**
     * @param object object
     * @param queryTablesMap query tables map
     * @param qualifier qualifier
     * @return the SQL expresion
     */
    @Override
    public String getSqlExpression(Object object, Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier) {
        return getSqlExpression(object, queryTablesMap, qualifier, null, false);
    }

    /**
     * @param object object
     * @return the SQL parameterized expresion
     */
    @Override
    public ParameterizedExpression getSqlParameterizedExpression(Object object) {
        QueryTable queryTable = null;
        return getSqlParameterizedExpression(object, queryTable);
    }

    /**
     * @param object object
     * @param queryTable query table
     * @return the SQL parameterized expresion
     */
    @Override
    public ParameterizedExpression getSqlParameterizedExpression(Object object, QueryTable queryTable) {
        return getSqlParameterizedExpression(object, queryTable, SqlQualifierType.RECORD);
    }

    /**
     * @param object object
     * @param queryTable query table
     * @param qualifier qualifier
     * @return the SQL parameterized expresion
     */
    @Override
    public ParameterizedExpression getSqlParameterizedExpression(Object object, QueryTable queryTable, SqlQualifierType qualifier) {
        ParameterizedExpression px = new ParameterizedExpression();
        String expression = getSqlExpression(object, queryTable, qualifier, px, false);
        px.setExpression(expression);
        return px;
    }

    /**
     * @param object object
     * @param queryTablesMap query tables map
     * @return the SQL parameterized expresion
     */
    @Override
    public ParameterizedExpression getSqlParameterizedExpression(Object object, Map<String, QueryTable> queryTablesMap) {
        return getSqlParameterizedExpression(object, queryTablesMap, SqlQualifierType.RECORD);
    }

    /**
     * @param object object
     * @param queryTablesMap query tables map
     * @param qualifier qualifier
     * @return the SQL parameterized expresion
     */
    @Override
    public ParameterizedExpression getSqlParameterizedExpression(Object object, Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier) {
        ParameterizedExpression px = new ParameterizedExpression();
        String expression = getSqlExpression(object, queryTablesMap, qualifier, px, false);
        px.setExpression(expression);
        return px;
    }

    /**
     * @param object object
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @param enclose enclose
     * @return the SQL expresion
     */
    protected String getSqlExpression(Object object, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px, boolean enclose) {
        if (object == null) {
            return null;
        } else if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;
            return getSqlExpression(objects, queryObject, qualifier, px, enclose);
        } else if (object instanceof Entity && queryObject instanceof QueryTable && object.equals(((QueryTable) queryObject).getEntity())) {
            Entity entity = (Entity) object;
            Property property = entity.getPrimaryKeyProperty();
            return getSqlExpression(property, queryObject, qualifier, px, enclose);
        } else if (object instanceof Property) {
            Property property = (Property) object;
            return queryObject == null ? getPropertySqlName(property) : getQualifiedName(property, queryObject, qualifier, px);
        } else if (object instanceof ScalarX) {
            Expression expression = (Expression) object;
            return getSqlExpression(expression, queryObject, qualifier, px, false);
        } else if (object instanceof Expression) {
            Expression expression = (Expression) object;
            return getSqlExpression(expression, queryObject, qualifier, px, enclose);
        } else if (object instanceof Instance) {
            Instance instance = (Instance) object;
            return getDelimitedString(instance.getInstanceKeyValue());
        } else if (object instanceof SpecialBinaryValue) {
            SpecialBinaryValue value = (SpecialBinaryValue) object;
            return getSpecialBinaryValue(value);
        } else if (object instanceof SpecialBooleanValue) {
            SpecialBooleanValue value = (SpecialBooleanValue) object;
            return getSpecialBooleanValue(value);
        } else if (object instanceof SpecialCharacterValue) {
            SpecialCharacterValue value = (SpecialCharacterValue) object;
            return getSpecialCharacterValue(value);
        } else if (object instanceof SpecialEntityValue) {
            SpecialEntityValue value = (SpecialEntityValue) object;
            return getSpecialEntityValue(value);
        } else if (object instanceof SpecialNumericValue) {
            SpecialNumericValue value = (SpecialNumericValue) object;
            return getSpecialNumericValue(value);
        } else if (object instanceof SpecialTemporalValue) {
            SpecialTemporalValue value = (SpecialTemporalValue) object;
            return getSpecialTemporalValue(value);
        } else if (object instanceof NamedValue) {
            NamedValue namedValue = (NamedValue) object;
            return getNamedValueName(namedValue, px);
        } else if (object instanceof NamedQuery) {
            NamedQuery namedQuery = (NamedQuery) object;
            return namedQuery.getString(qualifier);
        } else if (object instanceof NativeQuery) {
            NativeQuery nativeQuery = (NativeQuery) object;
            return nativeQuery.getString(qualifier);
        } else if (object instanceof Artifact) {
            Artifact artifact = (Artifact) object;
            return getSqlQualifiedName(artifact);
        } else {
            return getDelimitedString(object);
        }
    }

    protected String getSqlExpression(Object[] objects, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px, boolean enclose) {
        if (objects == null) {
            return null;
        }
        List<String> expressions = new ArrayList<>();
        for (Object object : objects) {
            String expression = getSqlExpression(object, queryObject, qualifier, px, enclose);
            if (StringUtils.isNotBlank(expression)) {
                expressions.add(expression);
            }
        }
        return expressions.isEmpty() ? null : StrUtils.encloseSqlExpression(StringUtils.join(expressions, SEPX));
    }

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @param enclose enclose
     * @return the SQL expresion
     */
    protected String getSqlExpression(Expression expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px, boolean enclose) {
        String string;
        if (expression == null) {
            return null;
        } else if (expression instanceof Property) {
            Property property = (Property) expression;
            return queryObject == null ? getPropertySqlName(property) : getQualifiedName(property, queryObject, qualifier, px);
        } else if (expression instanceof ComparisonX) {
            string = getSqlComparisonExpression((ComparisonX) expression, queryObject, qualifier, px);
        } else if (expression instanceof ConditionalX) {
            string = getSqlConditionalExpression((ConditionalX) expression, queryObject, qualifier, px);
        } else if (expression instanceof DataAggregateX) {
            string = getSqlDataAggregateExpression((DataAggregateX) expression, queryObject, qualifier, px);
        } else if (expression instanceof RowsAggregateX) {
            string = getSqlRowsAggregateExpression((RowsAggregateX) expression, queryObject, qualifier, px);
        } else if (expression instanceof NaryVectorX) {
            string = getSqlNaryVectorExpression((NaryVectorX) expression, queryObject, qualifier, px);
        } else if (expression instanceof OrderedPairX) {
            string = getSqlOrderedPairExpression((OrderedPairX) expression, queryObject, qualifier, px);
        } else if (expression instanceof ScalarX) {
            string = getSqlScalarExpression((ScalarX) expression, queryObject, qualifier, px);
        } else if (expression instanceof VariantX) {
            string = getSqlVariantExpression((VariantX) expression, queryObject, qualifier, px);
        } else {
            string = getDelimitedString(expression);
        }
        return enclose ? StrUtils.encloseSqlExpression(string) : StrUtils.discloseSqlExpression(string);
    }

    /*
    private boolean isSpecialValueExpression(Object object) {
        if (object instanceof Expression) {
            Expression expression = (Expression) object;
            if (expression instanceof ScalarX) {
                Operator operator = expression.getOperator();
                if (operator == null || operator.equals(ScalarOp.SELF)) {
                    return ((ScalarX) expression).getOperand() instanceof SpecialValue;
                }
            }
        }
        return false;
    }
    /**/
    private final Set<String> sqlComparisonExpressionMessages = new LinkedHashSet<>();

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected String getSqlComparisonExpression(ComparisonX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        ComparisonOp operator = expression.getOperator();
        Object x = expression.getX();
        Object y = expression.getY();
        Object z = expression.getZ();
        if (operator == null || x == null) {
            return null;
        }
        boolean primitive = x instanceof Primitive;
        if (x instanceof Expression && !primitive && !validExpressionOperator(operator)) {
            Expression ex = (Expression) x;
            Artifact ay = y instanceof Artifact ? (Artifact) y : null;
            String sx = ex.isNotDeclared() ? ex.toString() : ex.getFullName();
            String sy = y == null ? "" : " to " + (ay == null || ay.isNotDeclared() ? y.toString() : ay.getFullName());
            String message = operator + " cannot be used to compare expression " + sx + sy;
            boolean added = sqlComparisonExpressionMessages.add(message);
            if (added) {
                logger.error(message);
                Project.increaseWriterErrorCount();
            }
        }
        String arg1 = getSqlExpression(x, queryObject, qualifier, px, true);
        String arg2 = getSqlExpression(y, queryObject, qualifier, px, true);
        String arg3 = getSqlExpression(z, queryObject, qualifier, px, true);
        String pattern;
        switch (operator) {
            case EXISTS:
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = getExists() + SPC$ + LRB$ + ARG0 + RRB$;
                break;
            case NOT_EXISTS:
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = getNotExists() + SPC$ + LRB$ + ARG0 + RRB$;
                break;
            case IS_NULL:
                pattern = ARG0 + SPC$ + getIsNull();
                break;
            case IS_NOT_NULL:
                pattern = ARG0 + SPC$ + getIsNotNull();
                break;
            case IS_TRUE:
                pattern = primitive ? primitiveIsTruePattern() : expressionIsTruePattern();
                pattern = getIsNotNullAnd(arg1, true) + pattern;
                break;
            case IS_FALSE:
                pattern = primitive ? primitiveIsFalsePattern() : expressionIsFalsePattern();
                pattern = getIsNotNullAnd(arg1, true) + pattern;
                break;
            case IS_NULL_OR_TRUE:
                pattern = primitive ? primitiveIsTruePattern() : expressionIsTruePattern();
                pattern = getIsNullOr(arg1, true) + pattern;
                break;
            case IS_NULL_OR_FALSE:
                pattern = primitive ? primitiveIsFalsePattern() : expressionIsFalsePattern();
                pattern = getIsNullOr(arg1, true) + pattern;
                break;
            case EQ:
                pattern = getIsNotNullAnd(arg1) + getEQ() + SPC$ + arg2;
                break;
            case NEQ:
                pattern = getIsNotNullAnd(arg1) + getNEQ() + SPC$ + arg2;
                break;
            case GT:
                pattern = getIsNotNullAnd(arg1) + getGT() + SPC$ + arg2;
                break;
            case GTEQ:
                pattern = getIsNotNullAnd(arg1) + getGTEQ() + SPC$ + arg2;
                break;
            case LT:
                pattern = getIsNotNullAnd(arg1) + getLT() + SPC$ + arg2;
                break;
            case LTEQ:
                pattern = getIsNotNullAnd(arg1) + getLTEQ() + SPC$ + arg2;
                break;
            case STARTS_WITH:
                pattern = getIsNotNullAnd(arg1) + getLike() + SPC$ + startsWithArgument(1);
                break;
            case NOT_STARTS_WITH:
                pattern = getIsNotNullAnd(arg1) + getNotLike() + SPC$ + startsWithArgument(1);
                break;
            case CONTAINS:
                pattern = getIsNotNullAnd(arg1) + getLike() + SPC$ + containsArgument(1);
                break;
            case NOT_CONTAINS:
                pattern = getIsNotNullAnd(arg1) + getNotLike() + SPC$ + containsArgument(1);
                break;
            case ENDS_WITH:
                pattern = getIsNotNullAnd(arg1) + getLike() + SPC$ + endsWithArgument(1);
                break;
            case NOT_ENDS_WITH:
                pattern = getIsNotNullAnd(arg1) + getNotLike() + SPC$ + endsWithArgument(1);
                break;
            case IS_IN:
                pattern = getIsNotNullAnd(arg1) + getIn() + SPC$ + StrUtils.encloseSqlExpression(arg2);
                break;
            case IS_NOT_IN:
                pattern = getIsNotNullAnd(arg1) + getNotIn() + SPC$ + StrUtils.encloseSqlExpression(arg2);
                break;
            case IS_BETWEEN:
                pattern = getIsNotNullAnd(arg1) + getBetween() + SPC$ + arg2 + SPC$ + getAnd() + SPC$ + arg3;
                break;
            case IS_NOT_BETWEEN:
                pattern = getIsNotNullAnd(arg1) + getNotBetween() + SPC$ + arg2 + SPC$ + getAnd() + SPC$ + arg3;
                break;
            case IS_NULL_OR_EQ:
                pattern = getIsNullOr(arg1) + getEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_NEQ:
                pattern = getIsNullOr(arg1) + getNEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_GT:
                pattern = getIsNullOr(arg1) + getGT() + SPC$ + arg2;
                break;
            case IS_NULL_OR_LTEQ:
                pattern = getIsNullOr(arg1) + getLTEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_GTEQ:
                pattern = getIsNullOr(arg1) + getGTEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_LT:
                pattern = getIsNullOr(arg1) + getLT() + SPC$ + arg2;
                break;
            case IS_NULL_OR_STARTS_WITH:
                pattern = getIsNullOr(arg1) + getLike() + SPC$ + startsWithArgument(1);
                break;
            case IS_NULL_OR_NOT_STARTS_WITH:
                pattern = getIsNullOr(arg1) + getNotLike() + SPC$ + startsWithArgument(1);
                break;
            case IS_NULL_OR_CONTAINS:
                pattern = getIsNullOr(arg1) + getLike() + SPC$ + containsArgument(1);
                break;
            case IS_NULL_OR_NOT_CONTAINS:
                pattern = getIsNullOr(arg1) + getNotLike() + SPC$ + containsArgument(1);
                break;
            case IS_NULL_OR_ENDS_WITH:
                pattern = getIsNullOr(arg1) + getLike() + SPC$ + endsWithArgument(1);
                break;
            case IS_NULL_OR_NOT_ENDS_WITH:
                pattern = getIsNullOr(arg1) + getNotLike() + SPC$ + endsWithArgument(1);
                break;
            case IS_NULL_OR_IN:
                pattern = getIsNullOr(arg1) + getIn() + SPC$ + StrUtils.encloseSqlExpression(arg2);
                break;
            case IS_NULL_OR_NOT_IN:
                pattern = getIsNullOr(arg1) + getNotIn() + SPC$ + StrUtils.encloseSqlExpression(arg2);
                break;
            case IS_NULL_OR_BETWEEN:
                pattern = getIsNullOr(arg1) + getBetween() + SPC$ + arg2 + SPC$ + getAnd() + SPC$ + arg3;
                break;
            case IS_NULL_OR_NOT_BETWEEN:
                pattern = getIsNullOr(arg1) + getNotBetween() + SPC$ + arg2 + SPC$ + getAnd() + SPC$ + arg3;
                break;
            default:
                pattern = call(operator, z == null ? y == null ? 1 : 2 : 3);
                break;
        }
        return format(pattern, arg1, arg2, arg3);
    }

    protected boolean validExpressionOperator(ComparisonOp operator) {
        return true;
    }

    protected String primitiveIsTruePattern() {
        return ARG0 + SPC$ + getIsTrue();
    }

    protected String primitiveIsFalsePattern() {
        return ARG0 + SPC$ + getIsFalse();
    }

    protected String expressionIsTruePattern() {
        return "(" + ARG0 + ")";
    }

    protected String expressionIsFalsePattern() {
        return "not(" + ARG0 + ")";
    }

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected String getSqlConditionalExpression(ConditionalX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        Expression b = expression.getBooleanExpression();
        Object x = expression.getThenValue();
        Object y = expression.getElseValue();
        if (b == null || x == null) {
            return null;
        }
        String arg0 = getSqlExpression(b, queryObject, qualifier, px, true);
        String arg1 = getSqlExpression(x, queryObject, qualifier, px, true);
        String arg2 = getSqlExpression(y, queryObject, qualifier, px, true);
        String pattern = y == null ? getCaseWhenThenPattern() : getCaseWhenThenElsePattern();
        return format(pattern, arg0, arg1, arg2);
    }

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected String getSqlDataAggregateExpression(DataAggregateX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        DataAggregateOp operator = expression.getOperator();
        Object[] operands = expression.getOperands();
        if (operator == null || operands == null || operands.length < 2) {
            return null;
        }
        String[] arguments = new String[operands.length];
        for (int i = 0; i < operands.length; i++) {
            arguments[i] = getSqlExpression(operands[i], queryObject, qualifier, px, true);
        }
        String string;
        switch (operator) {
            case COUNT:
                string = count(arguments);
                break;
            case MAXIMUM:
                string = maximum(arguments);
                break;
            case MINIMUM:
                string = minimum(arguments);
                break;
            case AND:
                string = and(arguments);
                break;
            case NAND:
                string = not(and(arguments));
                break;
            case OR:
                string = or(arguments);
                break;
            case NOR:
                string = not(or(arguments));
                break;
            case NAXOR:
                string = xor(arguments);
                break;
            case NAXNOR:
                string = not(xor(arguments));
                break;
            case NOR_OR_NAXOR:
                string = or(not(or(arguments)), xor(arguments));
                break;
            case CONCAT:
                string = connect(getConcat(), arguments);
                break;
            case SUM:
                string = connect(getAdd(), arguments);
                break;
            case PRODUCT:
                string = connect(getMultiply(), arguments);
                break;
            case AVERAGE:
                string = average(arguments);
                break;
            default:
                string = call(operator, arguments);
                break;
        }
        return string;
    }

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected String getSqlRowsAggregateExpression(RowsAggregateX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        String errmsg = EMPTY;
        if (expression == null || queryObject == null) {
            return null;
        }
        errmsg += "failed to generate code for expression " + stringOf(expression);
        Entity declaringEntity = expression.getDeclaringEntity();
        if (declaringEntity == null) {
            logger.error(errmsg);
            return null;
        }
        errmsg += " at entity " + stringOf(declaringEntity);
//      String function = expression.getSqlSelectFunctionName();
//      String function = getSqlExpressionSelectFunctionName(expression);
        String function = getSqlSchemaQualifiedShortExpressionSelectFunctionName(expression);
        if (function == null) {
            logger.error(errmsg);
            return null;
        }
        Entity dimension = expression.getDimension();
        Property argument = dimension instanceof Property ? (Property) dimension : null;
        String argname = argument == null ? EMPTY : getQualifiedName(argument, queryObject, qualifier, px);
        return function + LRB$ + argname + RRB$;
    }

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected abstract String getSqlNaryVectorExpression(NaryVectorX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px);

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected abstract String getSqlOrderedPairExpression(OrderedPairX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px);

    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected abstract String getSqlScalarExpression(ScalarX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px);

    protected String toCharStringPattern(Object operand) {
        Class<?> clazz = operand == null ? null : operand instanceof TypedArtifact ? ((TypedArtifact) operand).getDataType() : operand.getClass();
        if (clazz == null) {
            return null;
        } else if (Date.class.isAssignableFrom(clazz)) {
            return "to_char({0}, 'YYYYMMDD')";
        } else if (Time.class.isAssignableFrom(clazz)) {
            return "to_char({0}, 'HH24MISS')";
        } else if (Timestamp.class.isAssignableFrom(clazz)) {
            return "to_char({0}, 'YYYYMMDDHH24MISS')";
        }
        return defaultCharStringPattern();
    }

    protected String toZeroPaddedStringPattern(Object x, Object y) {
        Integer w = x == null || y == null ? null : ObjUtils.toInteger(y);
        int width = w == null || w < 0 ? 0 : w;
        return defaultZeroPaddedStringPattern(width);
    }

    protected abstract String defaultCharStringPattern();

    protected abstract String defaultZeroPaddedStringPattern(int width);

    /*
    protected int defaultZeroPaddedStringPatternWidth(Object o) {
        return o instanceof ByteData ? 4 : o instanceof ShortData ? 6 : o instanceof IntegerData ? 11 : o instanceof LongData ? 20 : 0;
    }

    /**/
    /**
     * @param expression expression
     * @param queryObject query object
     * @param qualifier qualifier
     * @param px px
     * @return the SQL expresion
     */
    protected String getSqlVariantExpression(VariantX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        String errmsg = EMPTY;
        if (expression == null || queryObject == null) {
            return null;
        }
        errmsg += "failed to generate code for expression " + stringOf(expression);
        Entity declaringEntity = expression.getDeclaringEntity();
        if (declaringEntity == null) {
            logger.error(errmsg);
            return null;
        }
        errmsg += " at entity " + stringOf(declaringEntity);
//      String function = expression.getSqlExpressionFunctionName();
//      String function = getSqlExpressionFunctionName(expression);
        String function = getSqlSchemaQualifiedShortExpressionFunctionName(expression);
//      Property argument = expression.getSqlExpressionFunctionArgument();
        Property argument = expression.getExpressionFunctionArgument();
        if (function == null || argument == null) {
            logger.error(errmsg);
            return null;
        }
        boolean doubtful = declaringEntity.isRootInstance();
        if (doubtful) {
            // <editor-fold defaultstate="collapsed">
//          String name = expression.getName();
//          Class<?> type = expression.getDataType();
//          String foreignName = expression.getForeignName();
//          Class<?> foreignType = expression.getForeignType();
            // </editor-fold>
            Expression foreignExpression = expression.getForeignExpression();
            if (foreignExpression instanceof RowsAggregateX) {
                RowsAggregateX foreignRowsAggregateX = (RowsAggregateX) foreignExpression;
//              String select = foreignRowsAggregateX.getSqlSelectFunctionName();
//              String select = getSqlExpressionSelectFunctionName(foreignRowsAggregateX);
                String select = getSqlSchemaQualifiedShortExpressionSelectFunctionName(foreignRowsAggregateX);
                if (select != null) {
                    Entity dimension = foreignRowsAggregateX.getDimension();
                    if (dimension == null) {
                        function = select;
                        argument = null;
                        doubtful = false;
                    } else if (declaringEntity.getClass().isAssignableFrom(dimension.getClass())) {
                        function = select;
                        doubtful = false;
                    }
                }
                if (doubtful) {
                    errmsg += "; cannot bind it to its foreign expression " + stringOf(foreignExpression);
                    errmsg += " at entity " + stringOf(foreignExpression.getDeclaringEntity());
                }
            }
        }
        if (doubtful) {
            logger.error(errmsg);
            return null;
        } else if (argument == null) {
            return function + LRB$ + RRB$;
        } else {
            return function + LRB$ + getQualifiedName(argument, queryObject, qualifier, px) + RRB$;
        }
    }

    /**
     * @param operation operation
     * @return the sql operation function name
     */
    @Override
    public String getSqlOperationFunctionName(Operation operation) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getSqlOperationFunctionName(operation, maxIdentifierLength);
    }

    /**
     * @param operation operation
     * @param maxIdentifierLength max identifier length
     * @return the sql operation function name
     */
    @Override
    public String getSqlOperationFunctionName(Operation operation, int maxIdentifierLength) {
        if (operation == null) {
            return null;
        }
        Entity e = operation.getDeclaringEntity();
//      return e == null ? getSqlName(operation) : getSqlName(e.getRoot()) + OPERATION_DOLLAR_INFIX + getSqlName(operation);
        String r = e == null ? "" : getSqlName(e.getRoot(), 0) + EXPRESSION_DOLLAR_INFIX;
        String x = getSqlName(operation, 0);
        int max = getMaxIdentifierLength(maxIdentifierLength);
        return StrUtils.getIdentificadorSql(r + x, max);
    }

    /**
     * @param operation operation
     * @return the sql operation schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedOperationFunctionName(Operation operation) {
        if (operation == null) {
            return null;
        }
        String name = getSqlOperationFunctionName(operation);
        PersistentEntity pent = operation.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedName(name, getSqlSchemaName(pent));
    }

    /**
     * @param operation operation
     * @param maxIdentifierLength max identifier length
     * @return the sql operation schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedOperationFunctionName(Operation operation, int maxIdentifierLength) {
        if (operation == null) {
            return null;
        }
        String name = getSqlOperationFunctionName(operation, maxIdentifierLength);
        PersistentEntity pent = operation.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedName(name, getSqlSchemaName(pent));
    }

    /**
     * @param operation operation
     * @return the sql operation schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedShortOperationFunctionName(Operation operation) {
        if (operation == null) {
            return null;
        }
        String name = getSqlOperationFunctionName(operation);
        PersistentEntity pent = operation.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedShortName(name, getSqlSchemaName(pent));
    }

    /**
     * @param operation operation
     * @param maxIdentifierLength max identifier length
     * @return the sql operation schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedShortOperationFunctionName(Operation operation, int maxIdentifierLength) {
        if (operation == null) {
            return null;
        }
        String name = getSqlOperationFunctionName(operation, maxIdentifierLength);
        PersistentEntity pent = operation.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedShortName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @return the sql expression function name
     */
    @Override
    public String getSqlExpressionFunctionName(Expression expression) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getSqlExpressionFunctionName(expression, maxIdentifierLength);
    }

    /**
     * @param expression expression
     * @param maxIdentifierLength max identifier length
     * @return the sql expression function name
     */
    @Override
    public String getSqlExpressionFunctionName(Expression expression, int maxIdentifierLength) {
        if (expression == null) {
            return null;
        }
        Entity e = expression.getDeclaringEntity();
//      return e == null ? getSqlName(expression) : getSqlName(e.getRoot()) + EXPRESSION_DOLLAR_INFIX + getSqlName(expression);
        String r = e == null ? "" : getSqlName(e.getRoot(), 0) + EXPRESSION_DOLLAR_INFIX;
        String x = getSqlName(expression, 0);
        int max = getMaxIdentifierLength(maxIdentifierLength);
        return StrUtils.getIdentificadorSql(r + x, max);
    }

    /**
     * @param expression expression
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedExpressionFunctionName(Expression expression) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionFunctionName(expression);
        PersistentEntity pent = expression.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @param maxIdentifierLength max identifier length
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedExpressionFunctionName(Expression expression, int maxIdentifierLength) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionFunctionName(expression, maxIdentifierLength);
        PersistentEntity pent = expression.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedShortExpressionFunctionName(Expression expression) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionFunctionName(expression);
        PersistentEntity pent = expression.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedShortName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @param maxIdentifierLength max identifier length
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedShortExpressionFunctionName(Expression expression, int maxIdentifierLength) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionFunctionName(expression, maxIdentifierLength);
        PersistentEntity pent = expression.getDeclaringPersistentEntity();
        return pent == null ? name : StrUtils.getQualifiedShortName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @return the sql expression select function name
     */
    @Override
    public String getSqlExpressionSelectFunctionName(Expression expression) {
        int maxIdentifierLength = getMaxIdentifierLength();
        return getSqlExpressionSelectFunctionName(expression, maxIdentifierLength);
    }

    /**
     * @param expression expression
     * @param maxIdentifierLength max identifier length
     * @return the sql expression select function name
     */
    @Override
    public String getSqlExpressionSelectFunctionName(Expression expression, int maxIdentifierLength) {
        if (expression == null) {
            return null;
        }
        Entity e = expression instanceof RowsAggregateX ? expression.getDeclaringEntity() : null;
//      return e == null ? getSqlName(expression) : getSqlName(e.getRoot()) + EXPRESSION_SELECT_INFIX + getSqlName(expression);
        String r = e == null ? "" : getSqlName(e.getRoot(), 0) + EXPRESSION_SELECT_INFIX;
        String x = getSqlName(expression, 0);
        int max = getMaxIdentifierLength(maxIdentifierLength);
        return StrUtils.getIdentificadorSql(r + x, max);
    }

    /**
     * @param expression expression
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedExpressionSelectFunctionName(Expression expression) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionSelectFunctionName(expression);
        PersistentEntity pent = expression instanceof RowsAggregateX ? expression.getDeclaringPersistentEntity() : null;
        return pent == null ? name : StrUtils.getQualifiedName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @param maxIdentifierLength max identifier length
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedExpressionSelectFunctionName(Expression expression, int maxIdentifierLength) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionSelectFunctionName(expression, maxIdentifierLength);
        PersistentEntity pent = expression instanceof RowsAggregateX ? expression.getDeclaringPersistentEntity() : null;
        return pent == null ? name : StrUtils.getQualifiedName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedShortExpressionSelectFunctionName(Expression expression) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionSelectFunctionName(expression);
        PersistentEntity pent = expression instanceof RowsAggregateX ? expression.getDeclaringPersistentEntity() : null;
        return pent == null ? name : StrUtils.getQualifiedShortName(name, getSqlSchemaName(pent));
    }

    /**
     * @param expression expression
     * @param maxIdentifierLength max identifier length
     * @return the sql expression schema qualified function name
     */
    @Override
    public String getSqlSchemaQualifiedShortExpressionSelectFunctionName(Expression expression, int maxIdentifierLength) {
        if (expression == null) {
            return null;
        }
        String name = getSqlExpressionSelectFunctionName(expression, maxIdentifierLength);
        PersistentEntity pent = expression instanceof RowsAggregateX ? expression.getDeclaringPersistentEntity() : null;
        return pent == null ? name : StrUtils.getQualifiedShortName(name, getSqlSchemaName(pent));
    }

    private String stringOf(Expression e) {
        return e == null ? "?"
            : e.getName() != null ? e.getName()
            : e.getParentExpression() != null ? stringOf(e.getParentExpression()) + "[" + e.toString() + "]"
            : e.toString();
    }

    private String stringOf(Entity e) {
        return e == null ? "?"
            : e.getName() != null ? e.getName()
            : e.toString();
    }

    protected String getSqlExpressionDefaultValue(Expression expression) {
        Class<?> clazz = expression == null ? null : expression.getDataType();
        if (clazz == null) {
            return getNull();
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            return getFalse();
        } else if (Character.class.isAssignableFrom(clazz)) {
            return getZeroChar();
        } else if (String.class.isAssignableFrom(clazz)) {
            return getZeroString();
        } else if (Byte.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (Short.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (Long.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (Float.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (Double.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (BigInteger.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (BigDecimal.class.isAssignableFrom(clazz)) {
            return getZeroNumber();
        } else if (Date.class.isAssignableFrom(clazz)) {
            return getZeroDate();
        } else if (Time.class.isAssignableFrom(clazz)) {
            return getZeroTime();
        } else if (Timestamp.class.isAssignableFrom(clazz)) {
            return getZeroTimestamp();
        } else {
            return getNull();
        }
    }

    /**
     * @param sortOption sort option
     * @return the SQL sort option
     */
    @Override
    public String getSqlSortOption(SortOption sortOption) {
        if (sortOption != null) {
            switch (sortOption) {
                case ASC:
                    return getAscending();
                case DESC:
                    return getDescending();
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * @param operator operator
     * @return the SQL join operator
     */
    @Override
    public String getSqlJoinOperator(QueryJoinOp operator) {
        if (operator != null) {
            switch (operator) {
                case INNER:
                    return getInnerJoin();
                case LEFT:
                    return getLeftJoin();
                case RIGHT:
                    return getRightJoin();
                case FULL:
                    return getFullJoin();
                case CROSS:
                    return getCrossJoin();
                default:
                    return getDefaultJoin();
            }
        }
        return null;
    }

    /**
     * @param queryTable query table
     * @return the SQL join qualifier
     */
    @Override
    public String getSqlJoinQualifier(QueryTable queryTable) {
        int index = queryTable == null ? 0 : queryTable.getSubqueryIndex();
        return index == 0 ? getRowVariableName() : getRecordVariableName(index);
    }

    /**
     * @param queryTable query table
     * @return the select columns map
     */
    @Override
    public Map<String, Property> getSelectColumnsMap(QueryTable queryTable) {
        String alias;
        Map<String, Property> map = new LinkedHashMap<>();
        for (Property p : queryTable.getColumns()) {
            alias = getPropertySqlAlias(p, queryTable);
            map.put(alias, p);
        }
        for (QueryJoin j : queryTable.getJoins()) {
            map.putAll(getSelectColumnsMap(j.getRightTable()));
        }
        return map;
    }

    /**
     * @param expression expression
     * @return the SQL statement
     */
    @Override
    public String getSqlSelectStatement(Expression expression) {
        QueryTable queryTable = null;
        return getSqlSelectStatement(expression, queryTable);
    }

    /**
     * @param expression expression
     * @param queryTable query table
     * @return the SQL statement
     */
    @Override
    public String getSqlSelectStatement(Expression expression, QueryTable queryTable) {
        if (expression instanceof RowsAggregateX) {
            return getSqlSelectStatement((RowsAggregateX) expression, queryTable);
        }
        return null;
    }

    protected String getSqlSelectStatement(RowsAggregateX expression, QueryTable queryTable) {
        RowsAggregateOp operator = expression.getOperator();
        Object measure = expression.getMeasure();
        Segment filter = expression.getFilter();
        Entity dimension = expression.getDimension();
        if (operator == null || measure == null) {
            return null;
        }
        String function;
        switch (operator) {
            case COUNT:
                function = getCount();
                break;
            case MAXIMUM:
                function = getMaximum();
                break;
            case MINIMUM:
                function = getMinimum();
                break;
            case SUM:
                function = getSum();
                break;
            case AVERAGE:
                function = getAverage();
                break;
            default:
                function = operator.name().toLowerCase();
                break;
        }
        String argument = getSqlExpression(measure, queryTable, SqlQualifierType.ALIAS, null, false);
        if (argument == null && operator.equals(RowsAggregateOp.COUNT)) {
            argument = AST$;
        }
        List<Property> referencedColumns = new ArrayList<>();
        if (measure instanceof Property) {
            referencedColumns.add((Property) measure);
        } else if (measure instanceof Expression) {
            referencedColumns.addAll(((Expression) measure).getReferencedColumnsList());
        }
        if (filter != null) {
            referencedColumns.addAll(filter.getReferencedColumnsList());
        }
        if (dimension instanceof Property) {
            referencedColumns.add((Property) dimension);
        }
        String string = getSelect();
        string += EOL$ + function + LRB$ + argument + RRB$;
        string += EOL$ + getInto() + SPC$ + getValueVariableName();
        string += EOL$ + getSelectFrom(queryTable, queryTable, referencedColumns, false);
        String where = getWhere();
        if (filter != null) {
            string += EOL$ + where + SPC$ + getSqlExpression(filter, queryTable, SqlQualifierType.ALIAS, null, false);
            where = getAnd();
        }
        if (dimension != null) {
            string += EOL$ + where + SPC$ + getSqlExpression(dimension, queryTable, SqlQualifierType.ALIAS, null, false);
            string += SPC$ + getEQ() + SPC$ + getSqlVariableName(dimension);
        }
        return string;
    }

    /**
     * @param queryTable query table
     * @param referencedColumns referenced columns
     * @param into into
     * @param indent indent
     * @return the SQL statement
     */
    @Override
    public String getSqlSelectStatement(QueryTable queryTable, List<Property> referencedColumns, boolean into, boolean indent) {
        String string = getSelect();
        string += getSelectColumns(queryTable, queryTable, referencedColumns, indent);
        if (into) {
            string += EOL$ + getInto() + SPC$ + getSqlJoinQualifier(queryTable);
        }
        string += EOL$ + getSelectFrom(queryTable, queryTable, referencedColumns, indent);
        return string;
    }

    private String getSelectColumns(QueryTable rootQueryTable, QueryTable queryTable, List<Property> referencedColumns, boolean indent) {
        String string = EMPTY;
        String tab = indent ? StringUtils.repeat(TAB$, queryTable.getDepth()) : EMPTY;
        String name, alias;
        boolean calculable;
        String expression;
        for (Property p : queryTable.getColumns()) {
            if (referencedColumns == null || referencedColumns.isEmpty() || referencedColumns.contains(p)) {
                name = getPropertySqlName(p);
                alias = getPropertySqlAlias(p, queryTable);
                calculable = p.isCalculable();
                if (calculable) {
                    /*
                    expression = getCalculableColumnValueExpression(rootQueryTable, p);
                    expression = fixCalculableColumnValueExpression(expression, p);
                    /**/
                    if (!VirtualEntityType.LINE.equals(rootQueryTable.getVirtualEntityType())) {
                        expression = fixCalculableColumnValueExpression(rootQueryTable, p);
                        if (expression != null) {
                            string += EOL$ + tab + expression;
                            string += SPC$ + getAs() + SPC$ + alias;
                            string += SEP$;
                        }
                    }
                } else {
                    string += EOL$ + tab + queryTable.getAlias() + DOT$ + name;
                    if (name.equals(alias)) {
                    } else {
                        string += SPC$ + getAs() + SPC$ + alias;
                    }
                    string += SEP$;
                }
            }
        }
        String s;
        for (QueryJoin j : queryTable.getJoins()) {
            s = getSelectColumns(rootQueryTable, j.getRightTable(), referencedColumns, indent);
            if (StringUtils.isNotBlank(s)) {
                string += s + SEP$;
            }
        }
        return StringUtils.removeEnd(string, SEP$);
    }

    private String getSelectFrom(QueryTable rootQueryTable, QueryTable queryTable, List<Property> referencedColumns, boolean indent) {
        String string = getFrom() + SPC$ + queryTable.getName();
        if (!queryTable.getName().equals(queryTable.getAlias())) {
            string += SPC$ + queryTable.getAlias();
        }
        // <editor-fold defaultstate="collapsed" desc="log">
        /*
        boolean log = referencedColumns != null && !referencedColumns.isEmpty();
        if (log) {
            logger.info("QueryTable " + rootQueryTable.getName());
            for (Property referencedColumn : referencedColumns) {
                logger.info("\t" + referencedColumn.getFullName());
            }
            logger.info(string);
        }
        /**/
        // </editor-fold>
        string += getSelectJoins(rootQueryTable, queryTable, referencedColumnsForJoins(referencedColumns), indent);
        return string;
    }

    private List<Property> referencedColumnsForJoins(List<Property> referencedColumns) {
        if (referencedColumns == null || referencedColumns.isEmpty()) {
            return referencedColumns;
        }
        Set<Property> set = new LinkedHashSet<>();
        for (Property referencedColumn : referencedColumns) {
            if (referencedColumn.isCalculable()) {
                if (referencedColumn instanceof CalculableProperty) {
                    Object calculableValue = ((CalculableProperty) referencedColumn).getCalculableValue();
                    if (calculableValue instanceof Property) {
                        Property valueProperty = (Property) calculableValue;
                        set.add(valueProperty);
//                      logger.info("\tRC/0\t" + referencedColumn.getFullName() + " -> " + valueProperty.getFullName());
                    } else if (calculableValue instanceof Expression) {
                        Expression valueExpression = (Expression) calculableValue;
                        List<Property> referencedColumnsList = valueExpression.getReferencedColumnsList();
                        for (Property referencedByExpression : referencedColumnsList) {
                            set.add(referencedByExpression);
//                          logger.info("\tRC/1\t" + referencedColumn.getFullName() + " -> " + referencedByExpression.getFullName());
                        }
//                  } else if (calculableValue != null) {
//                      logger.warn("\tRC/2\t" + referencedColumn.getFullName() + " -> " + calculableValue + " :: " + calculableValue.getClass().getName());
//                  } else {
//                      logger.warn("\tRC/3\t" + referencedColumn.getFullName() + " -> " + calculableValue);
                    }
                }
            } else {
                set.add(referencedColumn);
            }
        }
        return new ArrayList<>(set);
    }

    private String getSelectJoins(QueryTable rootQueryTable, QueryTable queryTable, List<Property> referencedColumns, boolean indent) {
        String string = EMPTY;
        boolean norc = referencedColumns == null || referencedColumns.isEmpty();
        boolean calculable;
        for (QueryJoin j : queryTable.getJoins()) {
            calculable = j.getLeftColumn().isCalculable();
            if (!calculable) {
                if (norc || containsAny(j.getRightTable(), referencedColumns, indent)) {
                    string += getSelectJoin(rootQueryTable, j, referencedColumns, indent);
                }
            }
        }
        return string;
    }

    private String getSelectJoin(QueryTable rootQueryTable, QueryJoin queryJoin, List<Property> referencedColumns, boolean indent) {
        String string = EMPTY;
        QueryTable lt = queryJoin.getLeftTable();
        QueryTable rt = queryJoin.getRightTable();
        Property lc = queryJoin.getLeftColumn();
        Property rc = queryJoin.getRightColumn();
        String nested = getSelectJoins(rootQueryTable, rt, referencedColumns, indent);
        String joinop = getSqlJoinOperator(queryJoin.getOperator());
//      boolean calculable = lc.isCalculable();
        boolean b = StringUtils.isNotBlank(nested);
        String tab = indent ? StringUtils.repeat(TAB$, lt.getDepth()) : EMPTY;
        string += EOL$ + tab + joinop + (b ? LRB$ : SPC$) + rt.getName();
        if (!rt.getName().equals(rt.getAlias())) {
            string += SPC$ + rt.getAlias();
        }
        string += b ? nested + RRB$ + EOL$ + tab : SPC$;
        /*
        string += getOn() + SPC$ + rt.getAlias() + DOT$ + getPropertySqlName(queryJoin.getRightColumn());
        string += SPC$ + getEQ() + SPC$ + lt.getAlias() + DOT$ + getPropertySqlName(queryJoin.getLeftColumn());
        **/
        string += getOn() + SPC$ + rt.getAlias() + DOT$ + getPropertySqlName(rc);
        /*  TODO: arrange joins in proper order and enable calculable-column joins (see method getSelectJoins)
        if (calculable) {
            string += SPC$ + getEQ() + SPC$ + getCalculableColumnValueExpression(rootQueryTable, lc);
        } else {
            string += SPC$ + getEQ() + SPC$ + lt.getAlias() + DOT$ + getPropertySqlName(lc);
        }
        **/
        string += SPC$ + getEQ() + SPC$ + lt.getAlias() + DOT$ + getPropertySqlName(lc);
        return string;
    }

    private String getCalculableColumnValueExpression(QueryTable queryTable, Property property) {
        if (property instanceof CalculableProperty) {
            Object calculableValue = ((CalculableProperty) property).getCalculableValue();
            if (calculableValue == null) {
                return getNull();
            } else if (calculableValue instanceof Property) {
                Property p = (Property) calculableValue;
                QueryTable qt = queryTable.containingQueryTableOf(p);
                return qt == null ? null : qt.getAlias() + DOT$ + getPropertySqlName(p);
            } else if (calculableValue instanceof Expression) {
                return getSqlExpression(calculableValue, queryTable, SqlQualifierType.ALIAS);
            }
        }
        return getNull();
    }

    private String fixCalculableColumnValueExpression(QueryTable queryTable, Property property) {
        if (property instanceof CalculableProperty) {
            Object calculableValue = ((CalculableProperty) property).getCalculableValue();
            if (calculableValue == null) {
                QueryTable qt = queryTable.containingQueryTableOf(property);
                if (qt != null && StringUtils.isBlank(qt.getSuffix())) {
                    Entity dent = property.getDeclaringEntity();
                    if (dent != null && !dent.isRootInstance()) {
                        Entity root = dent.getRoot();
                        if (root instanceof PersistentEntity) {
                            QueryTable rootQueryTable = ((PersistentEntity) root).getQueryTable();
                            Property propertyAtRoot = property.getPropertyAtRoot();
                            String expression = getCalculableColumnValueExpression(rootQueryTable, propertyAtRoot);
                            return fixCalculableColumnValueExpression(expression, propertyAtRoot);
                        }
                    }
                }
                return getNull();
            }
        }
        String expression = getCalculableColumnValueExpression(queryTable, property);
        return fixCalculableColumnValueExpression(expression, property);
    }

    protected String fixCalculableColumnValueExpression(String expression, Property property) {
        return expression;
    }

    private boolean containsAny(QueryTable queryTable, List<Property> referencedColumns, boolean indent) {
        for (Property p : queryTable.getColumns()) {
            if (referencedColumns.contains(p)) {
                return true;
            }
        }
        for (QueryJoin j : queryTable.getJoins()) {
            if (containsAny(j.getRightTable(), referencedColumns, indent)) {
                return true;
            }
        }
        return false;
    }

    private String getQualifiedName(Property property, Object queryObject, SqlQualifierType qualifier) {
        return getQualifiedName(property, queryObject, qualifier, null);
    }

    private String getQualifiedName(Property property, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        switch (qualifier) {
            case ALIAS:
                return getAliasQualifiedName(property, queryObject, px);
            case RECORD:
                return getRecordQualifiedName(property, queryObject, px);
            case SUFFIX:
                return getUnqualifiedButSuffixedName(property, queryObject, px);
            default:
                return getQualifiedName(property, px);
        }
    }

    private String getQualifiedName(Property property, ParameterizedExpression px) {
        if (px == null) {
            return getSqlQualifiedName(property);
        }
        String name = getSqlParameterName(property);
        px.getParametersMap().put(name, property);
        return name;
    }

    @SuppressWarnings("unchecked")
    private String getAliasQualifiedName(Property property, Object queryObject, ParameterizedExpression px) {
        QueryTable queryTable = queryObject instanceof QueryTable ? (QueryTable) queryObject : null;
        Map<String, QueryTable> queryTablesMap = queryObject instanceof Map ? (Map<String, QueryTable>) queryObject : null;
        return queryTable != null ? getAliasQualifiedName(property, queryTable, px)
            : queryTablesMap != null ? getAliasQualifiedName(property, queryTablesMap, px)
                : getQualifiedName(property, px);
    }

    private String getAliasQualifiedName(Property property, QueryTable queryTable, ParameterizedExpression px) {
        int index = queryTable.getSubqueryIndex();
        for (Property p : queryTable.getColumns()) {
            if (p == property) {
                return queryTable.getAlias() + DOT$ + getPropertySqlName(p);
            }
        }
        String name;
        for (QueryJoin j : queryTable.getJoins()) {
            name = getAliasQualifiedName(property, j.getRightTable(), px);
            if (name == null) {
                continue;
            }
            return name;
        }
        return index == 0 ? getQualifiedName(property, px) : null;
    }

    private String getAliasQualifiedName(Property property, Map<String, QueryTable> queryTablesMap, ParameterizedExpression px) {
        QueryTable queryTable;
        for (String key : queryTablesMap.keySet()) {
            queryTable = queryTablesMap.get(key);
            if (queryTable.contains(property)) {
                return key + UND$ + getAliasQualifiedName(property, queryTable, px);
            }
        }
//      return getQualifiedName(property, px);
        return getSqlVariableName(property);
    }

    @SuppressWarnings("unchecked")
    private String getRecordQualifiedName(Property property, Object queryObject, ParameterizedExpression px) {
        QueryTable queryTable = queryObject instanceof QueryTable ? (QueryTable) queryObject : null;
        Map<String, QueryTable> queryTablesMap = queryObject instanceof Map ? (Map<String, QueryTable>) queryObject : null;
        return queryTable != null ? getRecordQualifiedName(property, queryTable, px)
            : queryTablesMap != null ? getRecordQualifiedName(property, queryTablesMap, px)
                : getQualifiedName(property, px);
    }

    private String getRecordQualifiedName(Property property, QueryTable queryTable, ParameterizedExpression px) {
        int index = queryTable.getSubqueryIndex();
        String qualifier = getSqlJoinQualifier(queryTable);
        String name;
        for (Property p : queryTable.getColumns()) {
            if (p == property) {
                name = index == 0 ? getPropertySqlName(p) : getPropertySqlAlias(p, queryTable);
                return qualifier + DOT$ + name;
            }
        }
        for (QueryJoin j : queryTable.getJoins()) {
            name = getRecordQualifiedName(property, j.getRightTable(), px);
            if (name == null) {
                continue;
            }
            return name;
        }
//      return index == 0 ? getQualifiedName(property, px) : null;
        if (index == 0) {
            if (property instanceof Entity && px == null) {
                Entity entity = (Entity) property;
                Property primaryKeyProperty = entity.getPrimaryKeyProperty();
                if (primaryKeyProperty != null) {
                    name = getSqlName(primaryKeyProperty);
                    return qualifier + DOT$ + name;
                }
            }
            String qualifiedName = getQualifiedName(property, px);
            return qualifiedName;
        }
        return null;
    }

    private String getRecordQualifiedName(Property property, Map<String, QueryTable> queryTablesMap, ParameterizedExpression px) {
        QueryTable queryTable;
        for (String key : queryTablesMap.keySet()) {
            queryTable = queryTablesMap.get(key);
            if (queryTable.contains(property)) {
                return key + UND$ + getRecordQualifiedName(property, queryTable, px);
            }
        }
//      return getQualifiedName(property, px);
        return getSqlVariableName(property);
    }

    @SuppressWarnings("unchecked")
    private String getUnqualifiedButSuffixedName(Property property, Object queryObject, ParameterizedExpression px) {
        QueryTable queryTable = queryObject instanceof QueryTable ? (QueryTable) queryObject : null;
        Map<String, QueryTable> queryTablesMap = queryObject instanceof Map ? (Map<String, QueryTable>) queryObject : null;
        return queryTable != null ? getUnqualifiedButSuffixedName(property, queryTable, px)
            : queryTablesMap != null ? getUnqualifiedButSuffixedName(property, queryTablesMap, px)
                : getQualifiedName(property, px);
    }

    private String getUnqualifiedButSuffixedName(Property property, QueryTable queryTable, ParameterizedExpression px) {
        int index = queryTable.getSubqueryIndex();
        String name;
        for (Property p : queryTable.getColumns()) {
            if (p == property) {
                name = index == 0 ? getPropertySqlName(p) : getPropertySqlAlias(p, queryTable);
                return name;
            }
        }
        for (QueryJoin j : queryTable.getJoins()) {
            name = getUnqualifiedButSuffixedName(property, j.getRightTable(), px);
            if (name == null) {
                continue;
            }
            return name;
        }
        return index == 0 ? getQualifiedName(property, px) : null;
    }

    private String getUnqualifiedButSuffixedName(Property property, Map<String, QueryTable> queryTablesMap, ParameterizedExpression px) {
        QueryTable queryTable;
        for (String key : queryTablesMap.keySet()) {
            queryTable = queryTablesMap.get(key);
            if (queryTable.contains(property)) {
                return key + DOT$ + getUnqualifiedButSuffixedName(property, queryTable, px);
            }
        }
//      return getQualifiedName(property, px);
        return getSqlVariableName(property);
    }

    private String getNamedValueName(NamedValue namedValue, ParameterizedExpression px) {
        String name = namedValue.name();
        if (px != null) {
            px.getNamedValuesMap().put(name, namedValue);
        }
        return name;
    }

    /**
     * @param queryJoin query join
     * @param referencedColumns referenced columns
     * @param into into
     * @param indent indent
     * @param where where
     * @return the SQL statement
     */
    @Override
    public String getSqlSelectStatement(QueryJoin queryJoin, List<Property> referencedColumns, boolean into, boolean where, boolean indent) {
        String string = getSqlSelectStatement(queryJoin.getRightTable(), referencedColumns, into, indent);
        if (where) {
            string += EOL$ + getSelectWhere(queryJoin);
        }
        return string;
    }

    private String getSelectWhere(QueryJoin queryJoin) {
        String string = getWhere();
        QueryTable lt = queryJoin.getLeftTable();
        QueryTable rt = queryJoin.getRightTable();
        Property lc = queryJoin.getLeftColumn();
        Property rc = queryJoin.getRightColumn();
        /*
        string += SPC$ + rt.getAlias() + DOT$ + getPropertySqlName(queryJoin.getRightColumn());
        string += SPC$ + getEQ() + SPC$ + getSqlJoinQualifier(lt) + DOT$ + getPropertySqlName(queryJoin.getLeftColumn());
        **/
        string += SPC$ + rt.getAlias() + DOT$ + getPropertySqlName(rc);
        string += SPC$ + getEQ() + SPC$ + getSqlJoinQualifier(lt) + DOT$ + getPropertySqlName(lc);
        return string;
    }

    /**
     * @param arg1 X
     * @param operator operator
     * @return the SQL standard relational expression
     */
    @Override
    public String getSqlStandardRelationalExpression(String arg1, StandardRelationalOp operator) {
        return getSqlStandardRelationalExpression(arg1, operator, "?");
    }

    /**
     * @param arg1 X
     * @param op op
     * @param arg2 Y
     * @return the SQL standard relational expression
     */
    @Override
    public String getSqlStandardRelationalExpression(String arg1, StandardRelationalOp op, String arg2) {
        String expression;
        StandardRelationalOp operator = op == null ? StandardRelationalOp.EQ : op;
        switch (operator) {
            case UNSPECIFIED:
            case EQ:
                expression = getIsNotNullAnd(arg1) + getEQ() + SPC$ + arg2;
                break;
            case NEQ:
                expression = getIsNotNullAnd(arg1) + getNEQ() + SPC$ + arg2;
                break;
            case GT:
                expression = getIsNotNullAnd(arg1) + getGT() + SPC$ + arg2;
                break;
            case GTEQ:
                expression = getIsNotNullAnd(arg1) + getGTEQ() + SPC$ + arg2;
                break;
            case LT:
                expression = getIsNotNullAnd(arg1) + getLT() + SPC$ + arg2;
                break;
            case LTEQ:
                expression = getIsNotNullAnd(arg1) + getLTEQ() + SPC$ + arg2;
                break;
            case LIKE:
                expression = getIsNotNullAnd(arg1) + getLike() + SPC$ + startsWithArgument(1);
                break;
            case NOT_LIKE:
                expression = getIsNotNullAnd(arg1) + getNotLike() + SPC$ + startsWithArgument(1);
                break;
            case IS_NULL_OR_EQ:
                expression = getIsNullOr(arg1) + getEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_NEQ:
                expression = getIsNullOr(arg1) + getNEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_GT:
                expression = getIsNullOr(arg1) + getGT() + SPC$ + arg2;
                break;
            case IS_NULL_OR_LTEQ:
                expression = getIsNullOr(arg1) + getLTEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_GTEQ:
                expression = getIsNullOr(arg1) + getGTEQ() + SPC$ + arg2;
                break;
            case IS_NULL_OR_LT:
                expression = getIsNullOr(arg1) + getLT() + SPC$ + arg2;
                break;
            case IS_NULL_OR_LIKE:
                expression = getIsNullOr(arg1) + getLike() + SPC$ + startsWithArgument(1);
                break;
            case IS_NULL_OR_NOT_LIKE:
                expression = getIsNullOr(arg1) + getNotLike() + SPC$ + startsWithArgument(1);
                break;
            default:
                expression = arg1 + SPC$ + getEQ() + SPC$ + arg2;
                break;
        }
        return expression;
    }

    /*
    private String like(int likeness, String unlimited) {
        String like1 = getLikeString();
        String like2 = getDelimitedString(like1);
        switch (likeness) {
            case 1:
                return getDelimitedString(unlimited + like1);
            case 2:
                return unlimited + getConcat() + like2;
            case 3:
                return getDelimitedString(like1 + unlimited + like1);
            case 4:
                return like2 + getConcat() + unlimited + getConcat() + like2;
            case 5:
                return getDelimitedString(like1 + unlimited);
            case 6:
                return like2 + getConcat() + unlimited;
        }
        return unlimited;
    }

    private String startsWithValue(Object valor) {
        return getString(valor) + getLikeString();
    }

    private String containsValue(Object valor) {
        return getLikeString() + getString(valor) + getLikeString();
    }

    private String endsWithValue(Object valor) {
        return getLikeString() + getString(valor);
    }

    /**/
    private String startsWithArgument(int index) {
        String argx = LCB$ + index + RCB$;
        String like = SQM$ + getLikeString() + SQM$;
        return argx + getConcat() + like;
    }

    private String containsArgument(int index) {
        String argx = LCB$ + index + RCB$;
        String like = SQM$ + getLikeString() + SQM$;
        return like + getConcat() + argx + getConcat() + like;
    }

    private String endsWithArgument(int index) {
        String argx = LCB$ + index + RCB$;
        String like = SQM$ + getLikeString() + SQM$;
        return like + getConcat() + argx;
    }

    private String average(String... strings) {
        String case$ = getAverageCasePattern();
        String arg0$ = join(EMPTY, getAdd(), strings);
        String arg1$ = count(strings);
        String[] placeHolders = {ARG0, ARG1};
        String[] argumentList = {arg0$, arg1$};
        return StringUtils.replaceEach(case$, placeHolders, argumentList);
    }

    protected String getAverageCasePattern() {
        return "case when {1} = 0 then null else {0} / {1} end";
    }

    private String count(String... strings) {
        String case$ = getCountCasePattern();
        int i = 0;
        String[] strings1 = new String[strings.length];
        for (String expresion : strings) {
            if (StringUtils.isNotBlank(expresion)) {
                strings1[i++] = case$.replace(ARG0, singleQuotedOrInParentheses(expresion));
            }
        }
        return join(EMPTY, getAdd(), strings1);
    }

    protected String getCountCasePattern() {
        return "case when {0} is null then 0 else 1 end";
    }

    private String maximum(String... strings) {
        return imum(getGTEQ(), strings);
    }

    private String minimum(String... strings) {
        return imum(getLTEQ(), strings);
    }

    private String imum(String comparador, String... strings) {
        String case$ = "case ";
        String when$ = "when {1} then {0} ";
        String else$ = "else null end";
        String arg0$;
        String arg1$;
        String conector = getAnd();
        String[] placeHolders = {ARG0, ARG1};
//      String[] argumentList = {arg0$, arg1$};
        String[] strings1 = new String[strings.length];
        int i1;
        for (int i = 0; i < strings.length; i++) {
            arg0$ = strings[i];
            if (StringUtils.isNotBlank(arg0$)) {
                Arrays.fill(strings1, EMPTY);
                i1 = 0;
                for (int j = 0; j < strings.length; j++) {
                    if (i != j) {
                        arg1$ = strings[j];
                        if (StringUtils.isNotBlank(arg1$)) {
                            strings1[i1++] = join(EMPTY, comparador, arg0$, arg1$);
                        }
                    }
                }
                arg1$ = join(EMPTY, conector, strings1);
                case$ += StringUtils.replaceEach(when$, placeHolders, new String[]{arg0$, arg1$});
            }
        }
        return case$ + else$;
    }

    protected String bind(String op1, String string) {
        String op1$ = StringUtils.trimToEmpty(op1);
        String expresion = StringUtils.trimToEmpty(string);
        if (StringUtils.isNotBlank(op1$) && StringUtils.isNotBlank(expresion)) {
            return op1$ + inParentheses(expresion);
        }
        return expresion;
    }

    protected String call(Operator operator, int arguments) {
        String function = operator.name().toLowerCase();
        return call(function, arguments);
    }

    protected String call(String function, int arguments) {
        String[] placeHolders = null;
        if (arguments > 0) {
            placeHolders = new String[arguments];
            for (int i = 0; i < arguments; i++) {
                placeHolders[i] = LCB$ + i + RCB$;
            }
        }
        return call(function, placeHolders);
    }

    protected String call(Operator operator, String... arguments) {
        String function = operator.name().toLowerCase();
        return call(function, arguments);
    }

    protected String call(String function, String... arguments) {
        String string = arguments == null || arguments.length == 0 ? LRB$ + RRB$ : StrUtils.encloseSqlExpression(StringUtils.join(arguments, SEPX));
        return function + string;
    }

    protected String join(String op1, String op2, String... strings) {
        String op1$ = StringUtils.equals(op1, op2) ? EMPTY : StringUtils.trimToEmpty(op1);
        String op2$ = SPC$ + StringUtils.trimToEmpty(op2) + SPC$;
        String expresion;
        String expresiones = EMPTY;
        boolean b = op1$.equals(getCast()) || op1$.equals(getCoalesce());
        int n = 0;
        for (String string : strings) {
            if (StringUtils.isNotBlank(string)) {
                expresion = b ? StringUtils.trimToEmpty(string) : caseSingleQuotedOrInParentheses(string);
                expresiones += op2$ + expresion;
                n++;
            }
        }
        if (n > 0) {
            expresiones = StringUtils.removeStart(expresiones, op2$);
            if (StringUtils.isNotBlank(op1$)) {
                return op1$ + inParentheses(expresiones);
            }
        }
        return inParentheses(expresiones);
    }

    protected String and(String... arguments) {
        return connect(getAnd(), arguments);
    }

    protected String or(String... arguments) {
        return connect(getOr(), arguments);
    }

    protected String xor(String... arguments) {
        int l = arguments.length;
        int m = l - 1;
        int n = 0;
        for (int i = 1; i < l; i++) {
            n += i;
        }
        String[] ands = new String[n];
        int index = 0;
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < l; j++) {
                ands[index++] = and(arguments[i], arguments[j]);
            }
        }
        return and(or(arguments), not(or(ands)));
    }

    protected String not(String argument) {
        return getNot() + StrUtils.encloseSqlExpression(argument);
    }

    protected String connect(char connective, String... arguments) {
        return StrUtils.encloseSqlExpression(StringUtils.join(arguments, connective + SPC$));
    }

    protected String connect(String connective, String... arguments) {
        return StrUtils.encloseSqlExpression(StringUtils.join(arguments, SPC$ + connective + SPC$));
    }

    private boolean isCase(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        String case$ = getCase() + SPC$;
        String end$ = SPC$ + getEnd();
        String s = StringUtils.trimToEmpty(string);
        return StringUtils.startsWithIgnoreCase(s, case$) && StringUtils.endsWithIgnoreCase(s, end$);
    }

    private boolean isSingle(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        String s = StringUtils.trimToEmpty(string);
        return !s.contains(SPC$);
    }

    private boolean isQuoted(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        String s = StringUtils.trimToEmpty(string);
        return StrUtils.isDelimited(s, SQM);
    }

    private boolean isInParentheses(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        String[] searchStrings = (String[]) ArrayUtils.add(stringsOperadorExpresionUnario(), getCoalesce());
        String s = removeStart(string, searchStrings);
        return StrUtils.isDelimited(s, LRB, RRB);
    }

    private String[] stringsOperadorExpresionUnario() {
        ScalarOp[] enums = ScalarOp.values();
        String[] strings = null;
        String string;
        for (ScalarOp sop : enums) {
            string = getString(sop);
            if (StringUtils.isNotBlank(string)) {
                strings = (String[]) ArrayUtils.add(strings, StringUtils.trimToEmpty(string));
            }
        }
        return strings;
    }

    private String removeStart(String string, String[] searchStrings) {
        String s = StringUtils.trimToEmpty(string);
        String p;
        for (String searchString : searchStrings) {
            if (StringUtils.isNotBlank(searchString)) {
                p = StringUtils.trimToEmpty(searchString);
                if (StringUtils.startsWithIgnoreCase(s, p)) {
                    return StringUtils.trimToEmpty(StringUtils.removeStartIgnoreCase(s, p));
                }
            }
        }
        return s;
    }

    private String caseSingleQuotedOrInParentheses(String string) {
        String s = StringUtils.trimToEmpty(string);
        boolean b = StringUtils.isBlank(s) || isCase(s);
        return b ? s : singleQuotedOrInParentheses(s);
    }

    private String singleQuotedOrInParentheses(String string) {
        String s = StringUtils.trimToEmpty(string);
        boolean b = StringUtils.isBlank(s) || isSingle(s);
        return b ? s : quotedOrInParentheses(s);
    }

    private String quotedOrInParentheses(String string) {
        String s = StringUtils.trimToEmpty(string);
        boolean b = StringUtils.isBlank(s) || isQuoted(s);
        return b ? s : inParentheses(s);
    }

    private String inParentheses(String string) {
        String s = StringUtils.trimToEmpty(string);
        boolean b = StringUtils.isBlank(s) || isInParentheses(s);
        return b ? s : LRB$ + s + RRB$;
    }

}
