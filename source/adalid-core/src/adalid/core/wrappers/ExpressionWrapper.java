/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.wrappers;

import adalid.commons.bundles.Bundle;
import adalid.core.Primitive;
import adalid.core.enums.SqlQualifierType;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.JavaProgrammer;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;
import adalid.core.programmers.ParameterizedExpression;
import adalid.core.sql.QueryTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ExpressionWrapper extends ArtifactWrapper {

    private static final String ERROR_MESSAGE_JOIN_SEPARATOR = Bundle.getTrimmedToEmptyString("error_message_join_separator.string") + " ";

    private Expression _expression;

    private BooleanExpression _booleanExpression;

    private ExpressionWrapper() {
        this(null);
    }

    public ExpressionWrapper(Expression expression) {
        super(expression);
        _expression = expression;
        if (expression instanceof BooleanExpression) {
            _booleanExpression = (BooleanExpression) expression;
        }
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
        return getDefaultErrorMessageJoin(ERROR_MESSAGE_JOIN_SEPARATOR);
    }

    /**
     * @return the default error message join
     */
    public String getDefaultErrorMessageJoin(String separator) {
        if (_booleanExpression == null) {
            return null;
        }
        List<String> messages = getDefaultErrorMessagesList();
        String sep = StringUtils.defaultString(separator, ERROR_MESSAGE_JOIN_SEPARATOR);
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
            if (expression instanceof BooleanExpression) {
                bex = (BooleanExpression) expression;
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
                if (operand instanceof Expression) {
                    ex = (Expression) operand;
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
     * @return the bundle default error message choice
     */
    public String getBundleDefaultErrorMessageChoice(String separator) {
        return getBundleValueString(getDefaultErrorMessageChoice(separator));
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
     * @return the SQL type
     */
    public String getSqlType() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlType(_expression);
    }

    /**
     * @return the SQL expression function name
     */
    public String getSqlExpressionFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpressionFunctionName(_expression);
    }

    /**
     * @return the SQL schema quailified expression function name
     */
    public String getSqlSchemaQualifiedExpressionFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedExpressionFunctionName(_expression);
    }

    /**
     * @return the SQL schema quailified short expression function name
     */
    public String getSqlSchemaQualifiedShortExpressionFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortExpressionFunctionName(_expression);
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
     * @return the SQL schema qualified expression select function name.
     * @see adalid.core.expressions.AbstractRowsAggregateX AbstractRowsAggregateX
     */
    public String getSqlSchemaQualifiedExpressionSelectFunctionName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedExpressionSelectFunctionName(_expression);
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
     * @return the SQL expression
     */
    @Override
    public String getSqlExpression() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression);
    }

    /**
     * @return the SQL expression
     */
    public String getSqlExpression(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTable);
    }

    /**
     * @return the SQL expression
     */
    public String getSqlExpression(QueryTable queryTable, SqlQualifierType qualifier) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTable, qualifier);
    }

    /**
     * @return the SQL expression
     */
    public String getSqlExpression(Map<String, QueryTable> queryTablesMap) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTablesMap);
    }

    /**
     * @return the SQL expression
     */
    public String getSqlExpression(Map<String, QueryTable> queryTablesMap, SqlQualifierType qualifier) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlExpression(_expression, queryTablesMap, qualifier);
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
     * @return the SQL parameterized expression
     */
    public ParameterizedExpression getSqlParameterizedExpression(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression, queryTable);
    }

    /**
     * @return the SQL parameterized expression
     */
    public ParameterizedExpression getSqlParameterizedExpression(QueryTable queryTable, SqlQualifierType qualifier) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression, queryTable, qualifier);
    }

    /**
     * @return the SQL parameterized expression
     */
    public ParameterizedExpression getSqlParameterizedExpression(Map<String, QueryTable> queryTablesMap) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlParameterizedExpression(_expression, queryTablesMap);
    }

    /**
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
     * @return the SQL select statement
     */
    public String getSqlSelectStatement(QueryTable queryTable) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSelectStatement(_expression, queryTable);
    }

}
