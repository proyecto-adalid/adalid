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

import adalid.commons.bundles.*;
import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.programmers.*;
import adalid.core.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ExpressionWrapper extends ArtifactWrapper {

    private final Expression _expression;

    private final BooleanExpression _booleanExpression;

    public ExpressionWrapper(Expression expression) {
        super(expression);
        _expression = expression;
        _booleanExpression = expression instanceof BooleanExpression ? (BooleanExpression) expression : null;
    }

    /**
     * @return the wrapped expression
     */
    @Override
    public Expression getWrapped() {
        return _expression;
    }

    /**
     * @return the default error message
     */
    public String getDefaultErrorMessage() {
        if (_booleanExpression == null) {
            return null;
        }
        return _booleanExpression.getDefaultErrorMessage();
    }

    /**
     * @return the default error message join
     */
    public String getDefaultErrorMessageJoin() {
        if (_booleanExpression == null) {
            return null;
        }
        return getDefaultErrorMessageJoin(null);
    }

    /**
     * @param separator separator
     * @return the default error message join
     */
    public String getDefaultErrorMessageJoin(String separator) {
        if (_booleanExpression == null) {
            return null;
        }
        List<String> messages = getDefaultErrorMessagesList();
        String sep = StringUtils.isBlank(separator) ? Bundle.getTrimmedToEmptyString("error_message_join_separator.string") + " " : separator;
        return StringUtils.join(messages, sep);
    }

    /**
     * @return the default error message choice
     */
    public String getDefaultErrorMessageChoice() {
        if (_booleanExpression == null) {
            return null;
        }
        String message = getDefaultErrorMessage();
        return StringUtils.isNotBlank(message) ? message : getDefaultErrorMessageJoin();
    }

    /**
     * @param separator separator
     * @return the default error message choice
     */
    public String getDefaultErrorMessageChoice(String separator) {
        if (_booleanExpression == null) {
            return null;
        }
        String message = getDefaultErrorMessage();
        return StringUtils.isNotBlank(message) ? message : getDefaultErrorMessageJoin(separator);
    }

    /**
     * @return the default error messages list
     */
    public List<String> getDefaultErrorMessagesList() {
        List<String> defaultErrorMessagesList = new ArrayList<>();
        if (_booleanExpression == null) {
            return defaultErrorMessagesList;
        }
        defaultErrorMessagesList.addAll(getDefaultErrorMessagesList(_booleanExpression));
        return defaultErrorMessagesList;
    }

    /**
     * @return the default error messages list
     */
    private List<String> getDefaultErrorMessagesList(BooleanExpression booleanExpression) {
        List<String> defaultErrorMessagesList = new ArrayList<>();
        BooleanExpression bex;
        String defaultErrorMessage;
        List<Expression> expressions = getExpressionsList(booleanExpression);
        for (Expression expression : expressions) {
            if (expression instanceof BooleanExpression booleanExpression1) {
                bex = booleanExpression1;
                defaultErrorMessage = bex.getDefaultErrorMessage();
                if (defaultErrorMessage == null) {
                    defaultErrorMessagesList.addAll(getDefaultErrorMessagesList(bex));
                } else {
                    defaultErrorMessagesList.add(defaultErrorMessage);
                }
            }
        }
        return defaultErrorMessagesList;
    }

    /**
     * @return the default filter description
     */
    public String getDefaultFilterDescription() {
        if (_booleanExpression == null) {
            return null;
        }
        return _booleanExpression.getDefaultDescription();
    }

    /**
     * @return the default filter description join
     */
    public String getDefaultFilterDescriptionJoin() {
        if (_booleanExpression == null) {
            return null;
        }
        return getDefaultFilterDescriptionJoin(null);
    }

    /**
     * @param separator separator
     * @return the default filter description join
     */
    public String getDefaultFilterDescriptionJoin(String separator) {
        if (_booleanExpression == null) {
            return null;
        }
        List<String> descriptions = getDefaultFilterDescriptionsList();
        String sep = StringUtils.isBlank(separator) ? Bundle.getTrimmedToEmptyString("filter_description_join_separator.string") + " " : separator;
        return StringUtils.join(descriptions, sep);
    }

    /**
     * @return the default filter description choice
     */
    public String getDefaultFilterDescriptionChoice() {
        if (_booleanExpression == null) {
            return null;
        }
        String description = getDefaultFilterDescription();
        return StringUtils.isNotBlank(description) ? description : getDefaultFilterDescriptionJoin();
    }

    /**
     * @param separator separator
     * @return the default filter description choice
     */
    public String getDefaultFilterDescriptionChoice(String separator) {
        if (_booleanExpression == null) {
            return null;
        }
        String description = getDefaultFilterDescription();
        return StringUtils.isNotBlank(description) ? description : getDefaultFilterDescriptionJoin(separator);
    }

    /**
     * @return the default filter descriptions list
     */
    public List<String> getDefaultFilterDescriptionsList() {
        List<String> defaultFilterDescriptionsList = new ArrayList<>();
        if (_booleanExpression == null) {
            return defaultFilterDescriptionsList;
        }
        defaultFilterDescriptionsList.addAll(getDefaultFilterDescriptionsList(_booleanExpression));
        return defaultFilterDescriptionsList;
    }

    /**
     * @return the default filter descriptions list
     */
    private List<String> getDefaultFilterDescriptionsList(BooleanExpression booleanExpression) {
        List<String> defaultFilterDescriptionsList = new ArrayList<>();
        BooleanExpression bex;
        String defaultDescription;
        List<Expression> expressions = getExpressionsList(booleanExpression);
        for (Expression expression : expressions) {
            if (expression instanceof BooleanExpression booleanExpression1) {
                bex = booleanExpression1;
                defaultDescription = bex.getDefaultDescription();
                if (defaultDescription == null) {
                    defaultFilterDescriptionsList.addAll(getDefaultFilterDescriptionsList(bex));
                } else {
                    defaultFilterDescriptionsList.add(defaultDescription);
                }
            }
        }
        return defaultFilterDescriptionsList;
    }

    /**
     * @param expression expression
     * @return the expressions list
     */
    public List<Expression> getExpressionsList(Expression expression) {
        List<Expression> expressionsList = new ArrayList<>();
        Object[] operands = expression.getOperands();
        if (operands != null && operands.length > 0) {
            Expression ex;
            for (Object operand : operands) {
                if (operand instanceof Primitive) {
                    continue;
                }
                if (operand instanceof Expression expression1) {
                    ex = expression1;
                    expressionsList.add(ex);
                }
            }
        }
        return expressionsList;
    }

    /**
     * @return the bundle default error message
     */
    public String getBundleDefaultErrorMessage() {
        return getBundleValueString(getDefaultErrorMessage());
    }

    /**
     * @return the bundle default error message join
     */
    public String getBundleDefaultErrorMessageJoin() {
        return getBundleValueString(getDefaultErrorMessageJoin());
    }

    /**
     * @param separator separator
     * @return the bundle default error message join
     */
    public String getBundleDefaultErrorMessageJoin(String separator) {
        return getBundleValueString(getDefaultErrorMessageJoin(separator));
    }

    /**
     * @return the bundle default error message choice
     */
    public String getBundleDefaultErrorMessageChoice() {
        return getBundleValueString(getDefaultErrorMessageChoice());
    }

    /**
     * @param separator separator
     * @return the bundle default error message choice
     */
    public String getBundleDefaultErrorMessageChoice(String separator) {
        return getBundleValueString(getDefaultErrorMessageChoice(separator));
    }

    /**
     * @return the XML default error message
     */
    public String getXmlDefaultErrorMessage() {
        return getXmlString(getDefaultErrorMessage());
    }

    /**
     * @return the XML default error message join
     */
    public String getXmlDefaultErrorMessageJoin() {
        return getXmlString(getDefaultErrorMessageJoin());
    }

    /**
     * @param separator separator
     * @return the XML default error message join
     */
    public String getXmlDefaultErrorMessageJoin(String separator) {
        return getXmlString(getDefaultErrorMessageJoin(separator));
    }

    /**
     * @return the XML default error message choice
     */
    public String getXmlDefaultErrorMessageChoice() {
        return getXmlString(getDefaultErrorMessageChoice());
    }

    /**
     * @param separator separator
     * @return the XML default error message choice
     */
    public String getXmlDefaultErrorMessageChoice(String separator) {
        return getXmlString(getDefaultErrorMessageChoice(separator));
    }

    /**
     * @return the Java type
     */
    public String getJavaType() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaType(_expression);
    }

    /**
     * @return the Java parameterized expression
     */
    public ParameterizedExpression getJavaParameterizedExpression() {
        JavaProgrammer javaProgrammer = ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaParameterizedExpression(_expression);
    }

    /**
     * @return the sql default error message choice
     */
    public String getSqlDefaultErrorMessageChoice() {
        return getSqlString(getDefaultErrorMessageChoice());
    }

    /**
     * @param separator separator
     * @return the sql default error message choice
     */
    public String getSqlDefaultErrorMessageChoice(String separator) {
        return getSqlString(getDefaultErrorMessageChoice(separator));
    }

    /**
     * @return the SQL type
     */
    public String getSqlType() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlType(_expression);
    }

    /**
     * @return the SQL parameter type
     */
    public String getSqlParameterType() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterType(_expression);
    }

    /**
     * @return the SQL expression function name
     */
    public String getSqlExpressionFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpressionFunctionName(_expression);
    }

    /**
     * @param maxIdentifierLength max identifier length
     * @return the SQL expression function name
     */
    public String getSqlExpressionFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpressionFunctionName(_expression, maxIdentifierLength);
    }

    /**
     * @return the SQL schema quailified expression function name
     */
    public String getSqlSchemaQualifiedExpressionFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedExpressionFunctionName(_expression);
    }

    /**
     * @param maxIdentifierLength max identifier length
     * @return the SQL schema quailified expression function name
     */
    public String getSqlSchemaQualifiedExpressionFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedExpressionFunctionName(_expression, maxIdentifierLength);
    }

    /**
     * @return the SQL schema quailified short expression function name
     */
    public String getSqlSchemaQualifiedShortExpressionFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortExpressionFunctionName(_expression);
    }

    /**
     * @param maxIdentifierLength max identifier length
     * @return the SQL schema quailified short expression function name
     */
    public String getSqlSchemaQualifiedShortExpressionFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortExpressionFunctionName(_expression, maxIdentifierLength);
    }

    /**
     * @return the SQL expression select function name.
     * @see adalid.core.expressions.AbstractRowsAggregateX AbstractRowsAggregateX
     */
    public String getSqlExpressionSelectFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpressionSelectFunctionName(_expression);
    }

    /**
     * @param maxIdentifierLength max identifier length
     * @return the SQL expression select function name.
     * @see adalid.core.expressions.AbstractRowsAggregateX AbstractRowsAggregateX
     */
    public String getSqlExpressionSelectFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpressionSelectFunctionName(_expression, maxIdentifierLength);
    }

    /**
     * @return the SQL schema qualified expression select function name.
     * @see adalid.core.expressions.AbstractRowsAggregateX AbstractRowsAggregateX
     */
    public String getSqlSchemaQualifiedExpressionSelectFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedExpressionSelectFunctionName(_expression);
    }

    /**
     * @param maxIdentifierLength max identifier length
     * @return the SQL schema qualified expression select function name.
     * @see adalid.core.expressions.AbstractRowsAggregateX AbstractRowsAggregateX
     */
    public String getSqlSchemaQualifiedExpressionSelectFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedExpressionSelectFunctionName(_expression, maxIdentifierLength);
    }

    /**
     * @return the SQL schema qualified short expression select function name.
     * @see adalid.core.expressions.AbstractRowsAggregateX AbstractRowsAggregateX
     */
    public String getSqlSchemaQualifiedShortExpressionSelectFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortExpressionSelectFunctionName(_expression);
    }

    /**
     * @param maxIdentifierLength max identifier length
     * @return the SQL schema qualified short expression select function name.
     * @see adalid.core.expressions.AbstractRowsAggregateX AbstractRowsAggregateX
     */
    public String getSqlSchemaQualifiedShortExpressionSelectFunctionName(int maxIdentifierLength) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortExpressionSelectFunctionName(_expression, maxIdentifierLength);
    }

    /**
     * @return the SQL expression
     */
    @Override
    public String getSqlExpression() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression);
    }

    /**
     * @param queryTable query table
     * @return the SQL expression
     */
    public String getSqlExpression(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTable);
    }

    /**
     * @param queryTable query table
     * @param qualifier qualifier
     * @return the SQL expression
     */
    public String getSqlExpression(QueryTable queryTable, SqlQualifierType qualifier) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTable, qualifier);
    }

    /**
     * @param queryTablesMap query tables map
     * @return the SQL expression
     */
    public String getSqlExpression(Map<String, QueryTable> queryTablesMap) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTablesMap);
    }

    /**
     * @param queryTablesMap query tables map
     * @param qualifier qualifier
     * @return the SQL expression
     */
    public String getSqlExpression(Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTablesMap, qualifier);
    }

    /**
     * @return the SQL expression for Java
     */
    public String getSqlJavaExpression() {
        return replaceSpecialValues(getSqlExpression());
    }

    /**
     * @param queryTable query table
     * @return the SQL expression for Java
     */
    public String getSqlJavaExpression(QueryTable queryTable) {
        return replaceSpecialValues(getSqlExpression(queryTable));
    }

    /**
     * @param queryTable query table
     * @param qualifier qualifier
     * @return the SQL expression for Java
     */
    public String getSqlJavaExpression(QueryTable queryTable, SqlQualifierType qualifier) {
        return replaceSpecialValues(getSqlExpression(queryTable, qualifier));
    }

    /**
     * @param queryTablesMap query tables map
     * @return the SQL expression for Java
     */
    public String getSqlJavaExpression(Map<String, QueryTable> queryTablesMap) {
        return replaceSpecialValues(getSqlExpression(queryTablesMap));
    }

    /**
     * @param queryTablesMap query tables map
     * @param qualifier qualifier
     * @return the SQL expression for Java
     */
    public String getSqlJavaExpression(Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier) {
        return replaceSpecialValues(getSqlExpression(queryTablesMap, qualifier));
    }

    private String replaceSpecialValues(String sqlExpression) {
        JavaProgrammer javaProgrammer = StringUtils.isBlank(sqlExpression) ? null : ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? null : javaProgrammer.getJavaExpressionOfSqlExpression(sqlExpression);
    }

    /**
     * @return the SQL parameterized expression
     */
    @Override
    public ParameterizedExpression getSqlParameterizedExpression() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression);
    }

    /**
     * @param queryTable query table
     * @return the SQL parameterized expression
     */
    public ParameterizedExpression getSqlParameterizedExpression(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression, queryTable);
    }

    /**
     * @param queryTable query table
     * @param qualifier qualifier
     * @return the SQL parameterized expression
     */
    public ParameterizedExpression getSqlParameterizedExpression(QueryTable queryTable, SqlQualifierType qualifier) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression, queryTable, qualifier);
    }

    /**
     * @param queryTablesMap query tables map
     * @return the SQL parameterized expression
     */
    public ParameterizedExpression getSqlParameterizedExpression(Map<String, QueryTable> queryTablesMap) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression, queryTablesMap);
    }

    /**
     * @param queryTablesMap query tables map
     * @param qualifier qualifier
     * @return the SQL parameterized expression
     */
    public ParameterizedExpression getSqlParameterizedExpression(Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression, queryTablesMap, qualifier);
    }

    /**
     * @return the SQL select statement
     */
    public String getSqlSelectStatement() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSelectStatement(_expression);
    }

    /**
     * @param queryTable query table
     * @return the SQL select statement
     */
    public String getSqlSelectStatement(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSelectStatement(_expression, queryTable);
    }

}
