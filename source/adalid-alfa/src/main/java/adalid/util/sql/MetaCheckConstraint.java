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
package adalid.util.sql;

import adalid.commons.util.StrUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class MetaCheckConstraint extends SqlArtifact {

    private static final Logger logger = Logger.getLogger(SqlUtil.class);

    private final SqlCheckConstraint _check;

    private SqlColumn sqlColumn1, sqlColumn2;

    private final List<String> _expressions = new ArrayList<>();

    private final List<String> _messages = new ArrayList<>();

    private String _type;

    public MetaCheckConstraint(SqlCheckConstraint check) {
        _check = check;
        parse();
    }

    public SqlCheckConstraint getCheck() {
        return _check;
    }

    public SqlColumn getSqlColumn1() {
        return sqlColumn1;
    }

    public SqlColumn getSqlColumn2() {
        return sqlColumn2;
    }

    public List<String> getExpressions() {
        return _expressions;
    }

    public List<String> getMessages() {
        return _messages;
    }

    private void addMetaExpressions(String column1, String comparisonOperator1, String value1, String logicalOperator, String column2, String comparisonOperator2, String value2) {
        sqlColumn1 = _check.getTable().getColumn(column1);
        sqlColumn2 = _check.getTable().getColumn(column2);
        setType(sqlColumn1, comparisonOperator1, logicalOperator, sqlColumn2, comparisonOperator2);
        if (_type.startsWith("COMPLEX")) {
        } else if (_type.equals(b7("MAX", "MIN"))) {
            _expressions.add(camelCase(column1) + ".setMaxValue(" + valueOf(sqlColumn1, value1) + ");");
            _expressions.add(camelCase(column2) + ".setMinValue(" + valueOf(sqlColumn2, value2) + ");");
        } else if (_type.equals(b7("MIN", "MAX"))) {
            _expressions.add(camelCase(column1) + ".setMinValue(" + valueOf(sqlColumn1, value1) + ");");
            _expressions.add(camelCase(column2) + ".setMaxValue(" + valueOf(sqlColumn2, value2) + ");");
        } else if (_type.equals("AND/OR")) {
            String expression1 = expressionOf(sqlColumn1, comparisonOperator1, value1);
            String expression2 = expressionOf(sqlColumn2, comparisonOperator2, value2);
            _expressions.add(getName() + " = " + expression1 + "." + logicalOperator.toLowerCase() + "(" + expression2 + ");");
        }
    }

    private void addMetaExpressions(String column, String comparisonOperator, String value) {
        sqlColumn1 = _check.getTable().getColumn(column);
        sqlColumn2 = sqlColumn1;
        setType(sqlColumn1, comparisonOperator);
        if (_type.startsWith("COMPLEX")) {
        } else if (_type.equals("MAX")) {
            _expressions.add(camelCase(column) + ".setMaxValue(" + valueOf(sqlColumn1, value) + ");");
        } else if (_type.equals("MIN")) {
            _expressions.add(camelCase(column) + ".setMinValue(" + valueOf(sqlColumn1, value) + ");");
        } else if (_type.equals("SIMPLE")) {
            _expressions.add(getName() + " = " + expressionOf(sqlColumn1, comparisonOperator, value) + ";");
        }
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    private void setType(SqlColumn sqlColumn1, String comparisonOperator1, String logicalOperator, SqlColumn sqlColumn2, String comparisonOperator2) {
        if (sqlColumn1 != null && comparisonOperator1 != null && logicalOperator != null && sqlColumn2 != null && comparisonOperator2 != null) {
            if (logicalOperator.equalsIgnoreCase("AND") || logicalOperator.equalsIgnoreCase("OR")) {
                _type = "AND/OR";
                if (sqlColumn1 == sqlColumn2 && logicalOperator.equalsIgnoreCase("AND")) {
                    _type = switch (b7(comparisonOperator1, comparisonOperator2)) {
                        case "<=" + b7 + ">=" ->
                            b7("MAX", "MIN");
                        case ">=" + b7 + "<=" ->
                            b7("MIN", "MAX");
                        default ->
                            "AND/OR";
                    };
                }
            }
        }
    }

    private void setType(SqlColumn sqlColumn, String comparisonOperator) {
        if (sqlColumn != null && comparisonOperator != null) {
            _type = switch (comparisonOperator) {
                case "<=" ->
                    "MAX";
                case ">=" ->
                    "MIN";
                default ->
                    "SIMPLE";
            };
        }
    }

    private boolean containsWord(String input, String word) {
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(input).find();
    }

    private int countMatches(String input, String word) {
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public boolean isCheck() {
        return _type.equals("AND/OR") || _type.equals("SIMPLE");
    }

    public boolean isMaxMin() {
        return _type.startsWith("MAX") || _type.startsWith("MIN");
    }

    public boolean isComplex() {
        return _type.equals("COMPLEX");
    }

    private static final String b7 = "/"; // "\u00B7";
//
//  private static final String co = "(=|<>|>|>=|<|<=|IS NOT|IS)";
//
//  private static final String x2 = "([^)]*)";

    private static final String simpleRegex1 = "CHECK \\(\\(\\((\\w+) (.*) (.*)\\) (AND|OR) \\((\\w+) (.*) (.*)\\)\\)\\)";

    private static final String simpleRegex2 = "CHECK \\(\\((\\w+) (.*) (.*)\\)\\)";

    private static final String simpleComparisonRegex = "(.+?)\\s+(=|<>|>|>=|<|<=|IS NOT|IS)\\s+(.+)";

    private static final String logicalOperatorRegex = "\\s+AND\\s+|\\s+OR\\s+";

    private static final String splitExpressionRegex = "\\s+(AND|OR)\\s+";

    private static final String integerRegex = "^[-]?\\d+$";

    private static final String decimalRegex = "^[-]?\\d+(\\.\\d+)?$";

    private static final String uncastRegex = "(\\(+|')?([^)':]+)(\\)+|')?::\\w+";

    private static final Pattern simplePattern1 = Pattern.compile(simpleRegex1);

    private static final Pattern simplePattern2 = Pattern.compile(simpleRegex2);

    private static final Pattern simpleComparisonPattern = Pattern.compile(simpleComparisonRegex);

    private static final Pattern logicalOperatorPattern = Pattern.compile(logicalOperatorRegex);

    private static final Pattern splitExpressionPattern = Pattern.compile(splitExpressionRegex);

    private static final Pattern integerPattern = Pattern.compile(integerRegex);

    private static final Pattern decimalPattern = Pattern.compile(decimalRegex);

    private static final Pattern uncastPattern = Pattern.compile(uncastRegex);

    private boolean parse() {
        setName(_check.getName());
        setType("COMPLEX");
        String input = _check.getConstraintDefinition();
        logger.trace(input);
        if (containsWord(StringUtils.replace(input.toUpperCase(), "IS NOT", "ISN'T"), "NOT")) {
            // NOTs are not simple!
        } else {
            int lops = countMatches(input, "AND") + countMatches(input, "OR");
            if (lops == 1) {
                Matcher matcher = simplePattern1.matcher(input);
                if (matcher.matches()) {
                    String column1 = matcher.group(1);
                    String comparisonOperator1 = matcher.group(2);
                    String value1 = uncast(matcher.group(3));
                    String logicalOperator = matcher.group(4);
                    String column2 = matcher.group(5);
                    String comparisonOperator2 = matcher.group(6);
                    String value2 = uncast(matcher.group(7));
                    addMetaExpressions(column1, comparisonOperator1, value1, logicalOperator, column2, comparisonOperator2, value2);
                    logger.trace("\tcol1: " + column1);
                    logger.trace("\tcop1: " + comparisonOperator1);
                    logger.trace("\tval1: " + value1);
                    logger.trace("\tlop1: " + logicalOperator);
                    logger.trace("\tcol2: " + column2);
                    logger.trace("\tcop2: " + comparisonOperator2);
                    logger.trace("\tval2: " + value2);
                    return true;
                }
            } else if (lops == 0) {
                Matcher matcher = simplePattern2.matcher(input);
                if (matcher.matches()) {
                    String column = matcher.group(1);
                    String comparisonOperator = matcher.group(2);
                    String value = uncast(matcher.group(3));
                    addMetaExpressions(column, comparisonOperator, value);
                    logger.trace("\tcol1: " + column);
                    logger.trace("\tcop1: " + comparisonOperator);
                    logger.trace("\tval1: " + value);
                    return true;
                }
            }
        }
        String sqlExpression = StringUtils.removeStartIgnoreCase(input, "CHECK");
        String translation = translate(sqlExpression);
        String metaExpression = getName() + " = " + translation + ";";
        _expressions.add(metaExpression);
        return false;
    }

    private String uncast(String value) {
        String group2;
        Matcher matcher = uncastPattern.matcher(value);
        if (matcher.find()) {
            group2 = matcher.group(2);
            return uncast(group2); // Recursively uncast the value
        }
        return value;
    }

    private String nameOf(SqlColumn sqlColumn, String value) {
        String metajavaName = camelCase(sqlColumn.getName());
        SqlTable foreignTable = sqlColumn.getForeignTable();
        if (foreignTable != null) {
            if (integerPattern.matcher(value).matches()) {
                return metajavaName + "." + camelCase(foreignTable.getPrimaryKey().getName());
            }
            SqlColumn column = sqlColumn.getTable().getColumn(value);
            if (column != null && column.getForeignTable() == null) {
                return metajavaName + "." + camelCase(foreignTable.getPrimaryKey().getName());
            }
        }
        return metajavaName;
    }

    private String valueOf(SqlColumn sqlColumn, String value) {
        if (value.startsWith("'") && value.endsWith("'")) {
            return "\"" + value.substring(1, value.length() - 1).replace("''", "'") + "\"";
        }
        if (integerPattern.matcher(value).matches()) {
            String metajavaType = sqlColumn.getMetajavaType();
            if (sqlColumn.isBooleanish() && metajavaType.equals("IntegerProperty")) {
                if (value.equals("1")) {
                    return "true";
                }
                if (value.equals("0")) {
                    return "false";
                }
            }
            Long longValue = Long.valueOf(value);
            return longValue + (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE ? "" : "L");
        }
        if (decimalPattern.matcher(value).matches()) {
            java.math.BigDecimal big = new java.math.BigDecimal(value);
            return "new java.math.BigDecimal(\"" + big + "\")";
        }
        SqlColumn column = sqlColumn.getTable().getColumn(value);
        if (column != null) {
            return camelCase(column.getName());
        }
        return value;
    }

    private String expressionOf(SqlColumn sqlColumn, String comparisonOperator, String value) {
        String metajavaName = nameOf(sqlColumn, value);
        String metajavaType = sqlColumn.getMetajavaType();
        if (comparisonOperator.equalsIgnoreCase("IS")) {
            return metajavaName + ".is" + capitalize(value) + "()";
        }
        if (comparisonOperator.equalsIgnoreCase("IS NOT")) {
            return metajavaName + ".isNot" + capitalize(value) + "()";
        }
        String method = switch (comparisonOperator) {
            case ">" ->
                "isGreaterThan";
            case ">=" ->
                "isGreaterOrEqualTo";
            case "<" ->
                "isLessThan";
            case "<=" ->
                "isLessOrEqualTo";
            case "<>", "!=" ->
                "isNotEqualTo";
            default ->
                "isEqualTo";
        };
        if (sqlColumn.isBooleanish() && metajavaType.equals("IntegerProperty")) {
            if (method.equals("isEqualTo")) {
                if (value.equals("1")) {
                    return metajavaName + ".isTrue()";
                }
                if (value.equals("0")) {
                    return metajavaName + ".isFalse()";
                }
            }
            if (method.equals("isNotEqualTo")) {
                if (value.equals("1")) {
                    return metajavaName + ".isNotTrue()";
                }
                if (value.equals("0")) {
                    return metajavaName + ".isNotFalse()";
                }
            }
        }
        return metajavaName + "." + method + "(" + valueOf(sqlColumn, value) + ")";
    }

    private String translate(String sqlExpression) {
        String expression = sqlExpression.trim();
        return logicalOperatorPattern.matcher(expression).find() ? translateLogicalExpression(expression) : translateSimpleComparison(expression);
    }

    private String translateLogicalExpression(String expression) {
        String[] components = splitExpression(expression);
        if (components.length == 0) {
            return expression; // No logical operators or expressions found
        }
        String result = translateSimpleComparison(components[0]);
        for (int i = 1; i < components.length; i += 2) {
            String operator = components[i].trim();
            String nextSimpleExpression = translateSimpleComparison(components[i + 1].trim());
            result = applyLogicalOperator(result, operator, nextSimpleExpression);
        }
        return result;
    }

    private String[] splitExpression(String expression) {
        List<String> parts = new ArrayList<>();
        int depth = 0; // To track the depth of nested parentheses
        int lastIndex = 0; // Last processed index
        int length = expression.length();
        // Pattern to match logical operators
        Matcher matcher = splitExpressionPattern.matcher(expression);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            // Update depth based on parentheses between last match and current match
            String between = expression.substring(lastIndex, start);
            depth += countParentheses(between);
            // Add the segment if we're at the top level (depth == 0)
            if (depth == 0) {
                if (start > lastIndex) {
                    parts.add(expression.substring(lastIndex, start).trim());
                }
                parts.add(matcher.group(1).trim());
                lastIndex = end;
            }
        }
        // Add the remaining part of the expression
        if (lastIndex < length) {
            parts.add(expression.substring(lastIndex).trim());
        }
        // Convert List to array and print it
        String[] components = parts.toArray(String[]::new);
        logger.trace(components.length + "\t" + ArrayUtils.toString(components));
        return components;
    }

    // Helper method to count the net change in parentheses
    private int countParentheses(String substring) {
        int count = 0;
        for (char ch : substring.toCharArray()) {
            if (ch == '(') {
                count++;
            } else if (ch == ')') {
                count--;
            }
        }
        return count;
    }

    private String applyLogicalOperator(String result, String operator, String nextSimpleExpression) {
        return switch (operator.toUpperCase()) {
            case "AND" ->
                result + ".and(" + nextSimpleExpression + ")";
            case "OR" ->
                result + ".or(" + nextSimpleExpression + ")";
            default ->
                result;
        };
    }

    private String translateSimpleComparison(String expression) {
        int last = expression.length() - 1;
        boolean delimited = last > 1 && expression.charAt(0) == '(' && expression.charAt(last) == ')';
        if (delimited) {
            String substring = expression.substring(1, expression.length() - 1); // removes the leading and trailing parentheses
            if (areParenthesesBalanced(substring)) { // checks if parentheses are still balanced after removing the leading and trailing one
                expression = substring;
            }
        }
        if (logicalOperatorPattern.matcher(expression).find()) {
            return translate(expression);
        }
        Matcher matcher = simpleComparisonPattern.matcher(expression);
        if (matcher.matches()) {
            String operand1 = uncast(matcher.group(1).trim());
            String operator = matcher.group(2).trim();
            String operand2 = uncast(matcher.group(3).trim());
            SqlColumn column1 = _check.getTable().getColumn(operand1);
            SqlColumn column2 = _check.getTable().getColumn(operand2);
            String operando1, operando2;
            if (column1 != null && column2 != null) {
                operando1 = nameOf(column1, operand2);
                operando2 = nameOf(column2, operand1);
            } else if (column1 != null && column2 == null) {
                operando1 = nameOf(column1, operand2);
                operando2 = valueOf(column1, operand2);
            } else if (column1 == null && column2 != null) {
                operando1 = valueOf(column2, operand1);
                operando2 = nameOf(column2, operand1);
            } else {
                operando1 = operand1;
                operando2 = operand2;
            }
            return translateComparison(operando1, operator, operando2);
        }
        return translate(expression);
    }

    private String translateComparison(String operand1, String operator, String operand2) {
        return switch (operator.toUpperCase()) {
            case "=" ->
                operand1 + ".isEqualTo(" + operand2 + ")";
            case "<>" ->
                operand1 + ".isNotEqualTo(" + operand2 + ")";
            case ">" ->
                operand1 + ".isGreaterThan(" + operand2 + ")";
            case ">=" ->
                operand1 + ".isGreaterOrEqualTo(" + operand2 + ")";
            case "<" ->
                operand1 + ".isLessThan(" + operand2 + ")";
            case "<=" ->
                operand1 + ".isLessOrEqualTo(" + operand2 + ")";
            case "IS" ->
                handleIsOperator(operand1, operand2);
            case "IS NOT" ->
                handleIsNotOperator(operand1, operand2);
            default ->
                operand1 + " " + operator + " " + operand2;
        };
    }

    private String handleIsOperator(String operand1, String operand2) {
        return switch (operand2.toUpperCase()) {
            case "TRUE" ->
                operand1 + ".isTrue()";
            case "FALSE" ->
                operand1 + ".isFalse()";
            case "NULL" ->
                operand1 + ".isNull()";
            default ->
                operand1 + ".isNotNull()";
        };
    }

    private String handleIsNotOperator(String operand1, String operand2) {
        return switch (operand2.toUpperCase()) {
            case "TRUE" ->
                operand1 + ".isNotTrue()";
            case "FALSE" ->
                operand1 + ".isNotFalse()";
            case "NULL" ->
                operand1 + ".isNotNull()";
            default ->
                operand1 + ".isNotNull()";
        };
    }

    private boolean areParenthesesBalanced(String expression) {
        int open = 0;
        char quote = 0;
        for (char c : expression.toCharArray()) {
            if (c == '\'' || c == '"') {
                if (quote == 0) {
                    quote = c; // beginning of the literal
                } else if (quote == c) {
                    quote = 0; // end of the literal
                }
            }
            // if not inside a literal, process parentheses
            if (quote == 0) {
                if (c == '(') {
                    open++;
                } else if (c == ')') {
                    if (open == 0) {
                        return false;
                    }
                    open--;
                }
            }
        }
        return open == 0;
    }

    private String b7(String... strings) {
        return StringUtils.join(strings, b7);
    }

    private String camelCase(String snakeCase) {
        return StrUtils.getCamelCase(snakeCase);
    }

    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

}
