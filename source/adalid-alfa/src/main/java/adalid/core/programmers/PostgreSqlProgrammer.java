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

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class PostgreSqlProgrammer extends AbstractSqlProgrammer {

    @Override
    public String getDBMS() {
        return "PostgreSQL";
    }

    // <editor-fold defaultstate="collapsed" desc="SQL comparison operators">
    private final String[] NEVER_NULL = {
        "current_user", "current_user_code()", "current_user_id()",
        "current_date", "current_time", "current_timestamp", "localtime", "localtimestamp"
    };

    @Override
    protected String[] neverNull() {
        return NEVER_NULL;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="referential integrity options">
    @Override
    protected String getRestricted() {
        return "no action";
    }

    @Override
    protected String getCascade() {
        return "cascade";
    }

    @Override
    protected String getNullify() {
        return "no action";
    }

    @Override
    protected String getNoAction() {
        return "no action";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="keywords">
    @Override
    protected String getCurrentDate() {
        return "current_date";
    }

    @Override
    protected String getCurrentTime() {
//      return "current_time";
        return "localtime";
    }

    @Override
    protected String getCurrentTimestamp() {
//      return "current_timestamp";
        return "localtimestamp";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="limits">
    protected static final int MAX_VARCHAR_LENGTH = Project.getMaximumStringFieldMaxLength();

    @Override
    public int getMaxIdentifierLength() {
        return 63;
    }

    @Override
    public int getMaxVarcharLength() {
        return MAX_VARCHAR_LENGTH;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Java/SQL types">
    protected static final String BINARY = "bytea";

    protected static final String BOOLEAN = "boolean";

    protected static final String CHAR = "character(1)";

    protected static final String VARCHAR = "character varying({0})";

    protected static final String TEXT = "character varying";

    protected static final String BYTE = "smallint";

    protected static final String SMALLINT = "smallint";

    protected static final String INTEGER = "integer";

    protected static final String LONG = "bigint";

    protected static final String FLOAT = "real";

    protected static final String DOUBLE = "double precision";

    protected static final String BIGINT = "numeric({0})";

    protected static final String DECIMAL = "numeric({0},{1})";

    protected static final String NUMERIC = "numeric";

    protected static final String DATE = "date";

    protected static final String TIME = "time({0}) without time zone";

    protected static final String TIMEX = "time without time zone";

    protected static final String TIMESTAMP = "timestamp({0}) without time zone";

    protected static final String TIMESTAMPX = "timestamp without time zone";

    protected static final String RECORD = "record";

    protected static final String VOID = "void";
    // </editor-fold>

    @Override
    public String getDelimitedString(Object obj) {
        String string = getString(obj);
        if (string == null) {
            return null;
        } else if (obj instanceof String) {
            return SQM$ + string + SQM$;
        } else if (obj instanceof Date) {
            return "date" + SQM$ + string + SQM$;
        } else if (obj instanceof Time) {
//          return "timetz" + SQM$ + string + SQM$;
            return "time" + SQM$ + string + SQM$;
        } else if (obj instanceof java.util.Date) {
//          return "timestamptz" + SQM$ + string + SQM$;
            return "timestamp" + SQM$ + string + SQM$;
        } else {
            return super.getDelimitedString(obj);
        }
    }

    /* para strings TemporalAddend */
    private static final String TEMPORAL_ADDEND_PATTERN = "{0} {1} INTERVAL ''{2} {3}''";

    @Override
    public String getDelimitedString(TemporalAddend addend) {
        if (addend == null || addend.isBadValue()) {
            return null;
        }
        String base, word;
        int quantity = addend.getQuantity();
        int absolute = Math.abs(quantity);
        char sign = quantity < 0 ? '-' : '+';
        char unit = addend.getUnitCode();
        switch (unit) {
            case 'A', 'Y' -> {
                base = "current_date";
                word = "years";
            }
            case 'M' -> {
                base = "current_date";
                word = "months";
            }
            case 'D' -> {
                base = "current_date";
                word = "days";
            }
            case 'h' -> {
//              base = "current_time";
//              base = "localtime";
//              base = "current_timestamp";
                base = "localtimestamp";
                word = "hours";
            }
            case 'm' -> {
//              base = "current_time";
//              base = "localtime";
//              base = "current_timestamp";
                base = "localtimestamp";
                word = "minutes";
            }
            case 's' -> {
//              base = "current_time";
//              base = "localtime";
//              base = "current_timestamp";
                base = "localtimestamp";
                word = "seconds";
            }
            default -> {
                return null;
            }
        }
        return quantity == 0 ? base : StrUtils.encloseSqlExpression(MessageFormat.format(TEMPORAL_ADDEND_PATTERN, base, sign, absolute + "", word));
    }

    /**
     * @param artifact artifact
     * @return the SQL parameter type
     */
    @Override
    public String getSqlParameterType(Artifact artifact) {
        if (artifact == null) {
            return null;
        } else if (artifact instanceof BinaryData) {
            return BINARY;
        } else if (artifact instanceof BooleanData) {
            return BOOLEAN;
        } else if (artifact instanceof CharacterData) {
            return TEXT;
        } else if (artifact instanceof StringData) {
            return TEXT;
        } else if (artifact instanceof ByteData) {
            return INTEGER;
        } else if (artifact instanceof ShortData) {
            return INTEGER;
        } else if (artifact instanceof IntegerData) {
            return INTEGER;
        } else if (artifact instanceof LongData) {
            return LONG;
        } else if (artifact instanceof FloatData) {
            return DOUBLE;
        } else if (artifact instanceof DoubleData) {
            return DOUBLE;
        } else if (artifact instanceof BigIntegerData) {
            return NUMERIC;
        } else if (artifact instanceof BigDecimalData) {
            return NUMERIC;
        } else if (artifact instanceof DateData) {
            return DATE;
        } else if (artifact instanceof TimeData) {
            return TIMEX;
        } else if (artifact instanceof TimestampData) {
            return TIMESTAMPX;
        } else if (artifact instanceof Entity entity) {
            return getEntityReferenceType(entity);
        } else {
            return null;
        }
    }

    /**
     * @param artifact artifact
     * @return the SQL type
     */
    @Override
    public String getSqlType(Artifact artifact) {
        if (artifact == null) {
            return null;
        } else if (artifact instanceof BinaryData) {
            return BINARY;
        } else if (artifact instanceof BooleanData) {
            return BOOLEAN;
        } else if (artifact instanceof CharacterData) {
            return CHAR;
        } else if (artifact instanceof StringData data) {
            int l = (Integer) IntUtils.valueOf(data.getMaxLength());
            return l < 1 || l > MAX_VARCHAR_LENGTH ? TEXT : format(VARCHAR, l);
        } else if (artifact instanceof ByteData) {
            return BYTE;
        } else if (artifact instanceof ShortData) {
            return SMALLINT;
        } else if (artifact instanceof IntegerData) {
            return INTEGER;
        } else if (artifact instanceof LongData) {
            return LONG;
        } else if (artifact instanceof FloatData) {
            return FLOAT;
        } else if (artifact instanceof DoubleData) {
            return DOUBLE;
        } else if (artifact instanceof BigIntegerData data) {
            int p = data.getPrecision();
            return format(BIGINT, p);
        } else if (artifact instanceof BigDecimalData data) {
            int p = data.getPrecision();
            int s = data.getScale();
            return format(DECIMAL, p, s);
        } else if (artifact instanceof DateData) {
            return DATE;
        } else if (artifact instanceof TimeData data) {
            int p = IntUtils.valueOf(data.getPrecision(), Constants.DEFAULT_TIME_PRECISION);
            return format(TIME, p);
        } else if (artifact instanceof TimestampData data) {
            int p = IntUtils.valueOf(data.getPrecision(), Constants.DEFAULT_TIME_PRECISION);
            return format(TIMESTAMP, p);
        } else if (artifact instanceof Entity entity) {
            return getEntityReferenceType(entity);
        } else if (artifact instanceof Expression expression) {
            return getExpressionType(expression);
        } else {
            return null;
        }
    }

    protected String getExpressionType(Expression expression) {
        Class<?> clazz = expression.getDataType();
        if (clazz == null) {
            return VOID;
        } else if (Blob.class.isAssignableFrom(clazz)) {
            return BINARY;
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            return BOOLEAN;
        } else if (Character.class.isAssignableFrom(clazz)) {
            return CHAR;
        } else if (String.class.isAssignableFrom(clazz)) {
            return TEXT;
        } else if (Byte.class.isAssignableFrom(clazz)) {
            return BYTE;
        } else if (Short.class.isAssignableFrom(clazz)) {
            return SMALLINT;
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return INTEGER;
        } else if (Long.class.isAssignableFrom(clazz)) {
            return LONG;
        } else if (Float.class.isAssignableFrom(clazz)) {
            return FLOAT;
        } else if (Double.class.isAssignableFrom(clazz)) {
            return DOUBLE;
        } else if (BigInteger.class.isAssignableFrom(clazz)) {
            return NUMERIC;
        } else if (BigDecimal.class.isAssignableFrom(clazz)) {
            return NUMERIC;
        } else if (Date.class.isAssignableFrom(clazz)) {
            return DATE;
        } else if (Time.class.isAssignableFrom(clazz)) {
            return format(TIME, Constants.DEFAULT_TIME_PRECISION);
        } else if (Timestamp.class.isAssignableFrom(clazz)) {
            return format(TIMESTAMP, Constants.DEFAULT_TIME_PRECISION);
//      } else if (Instance.class.isAssignableFrom(clazz)) {
//          return RECORD;
        } else if (expression instanceof BooleanExpression) {
            return BOOLEAN;
        } else if (expression instanceof CharacterExpression) {
            return TEXT;
        } else if (expression instanceof NumericExpression) {
            return NUMERIC;
        } else if (expression instanceof TemporalExpression) {
            return format(TIMESTAMP, Constants.DEFAULT_TIME_PRECISION);
        } else {
            return TEXT;
        }
    }

    protected String getEntityReferenceType(Entity entity) {
        return entity.getPrimaryKeyProperty() instanceof IntegerProperty ? INTEGER : LONG;
    }

    /**
     * @param entity entity
     * @return the onDeleteAction
     */
    @Override
    public String getSqlOnDeleteAction(PersistentEntityReference entity) {
        if (entity == null) {
            return null;
        }
        OnDeleteAction onDeleteAction = entity.getOnDeleteAction();
        if (onDeleteAction == null) {
            return getNoAction();
        }
        return switch (onDeleteAction) {
            case CASCADE ->
                getCascade();
            default ->
                getNoAction();
        };
    }

    /**
     * @param entity entity
     * @return the onUpdateAction
     */
    @Override
    public String getSqlOnUpdateAction(PersistentEntityReference entity) {
        if (entity == null) {
            return null;
        }
        OnUpdateAction onUpdateAction = entity.getOnUpdateAction();
        if (onUpdateAction == null) {
            return getNoAction();
        }
        return switch (onUpdateAction) {
            case CASCADE ->
                getCascade();
            default ->
                getNoAction();
        };
    }

    /**
     * @return the SQL expresion
     */
    @Override
    protected String getSqlDataAggregateExpression(DataAggregateX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        DataAggregateOp operator = expression.getOperator();
        Object[] operands = expression.getOperands();
        if (operator == null || operands == null || operands.length == 0) {
            return null;
        }
        String[] arguments = new String[operands.length];
        for (int i = 0; i < operands.length; i++) {
            arguments[i] = getSqlExpression(operands[i], queryObject, qualifier, px, false);
        }
        String string;
        string = switch (operator) {
            case COALESCE ->
                call("coalesce", arguments);
            case MAXIMUM ->
                call("greatest", arguments);
            case MINIMUM ->
                call("least", arguments);
            default ->
                super.getSqlDataAggregateExpression(expression, queryObject, qualifier, px);
        };
        return string;
    }

    /**
     * @return the SQL expresion
     */
    @Override
    protected String getSqlNaryVectorExpression(NaryVectorX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        NaryVectorOp operator = expression.getOperator();
        Object[] operands = expression.getOperands();
        int length = operands == null ? 0 : operands.length;
        if (operator == null || length == 0) {
            return null;
        }
        String[] strings = new String[length];
        for (int i = 0; i < length; i++) {
            strings[i] = getSqlExpression(operands[i], queryObject, qualifier, px, true);
        }
        Object[] arguments = new Object[length];
        for (int i = 0; i < length; i++) {
            arguments[i] = StrUtils.discloseSqlExpression(strings[i]);
        }
        String pattern;
        pattern = switch (operator) {
            case SUBSTR ->
                length > 1 ? call(operator, length) : "null";
            default ->
                call(operator, length);
        };
        return format(pattern, arguments);
    }

    /**
     * @return the SQL expresion
     */
    @Override
    protected String getSqlOrderedPairExpression(OrderedPairX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        OrderedPairOp operator = expression.getOperator();
        Object x = expression.getX();
        Object y = expression.getY();
        if (operator == null || x == null || y == null) {
            return null;
        }
        String arg1 = getSqlExpression(x, queryObject, qualifier, px, true);
        String arg2 = getSqlExpression(y, queryObject, qualifier, px, true);
        String pattern;
        switch (operator) {
            case COALESCE -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "coalesce({0}, {1})";
            }
            case NULLIF -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "nullif({0}, {1})";
            }
            case MAXIMUM -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "greatest({0}, {1})";
            }
            case MINIMUM -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "least({0}, {1})";
            }
            case AND ->
                pattern = "{0} and {1}";
            case NAND ->
                pattern = "not({0} and {1})";
            case OR ->
                pattern = "{0} or {1}";
            case NOR ->
                pattern = "not({0} or {1})";
            case XOR ->
                pattern = "not({0} and {1}) and ({0} or {1})";
            case XNOR ->
                pattern = "not({0} or {1}) or ({0} and {1})";
            case X_IMPLIES_Y -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "not({0}) or {1}";
            }
            case ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "ascii_string({0}, {1})";
            }
            case DIACRITICLESS_ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "diacriticless_ascii({0}, {1})";
            }
            case CONCAT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "concat({0}, {1})";
            }
            case CONCATENATE ->
                pattern = "{0} || {1}";
            case FORMAT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "string_format({0}, {1})";
            }
            case LEFT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "left({0}, {1})";
            }
            case RIGHT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "right({0}, {1})";
            }
            case SUBSTR -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "substr({0}, {1})";
            }
            case TO_ZERO_PADDED_STRING ->
                pattern = toZeroPaddedStringPattern(x, y);
            case X_PLUS_Y ->
                pattern = "{0} + {1}";
            case X_MINUS_Y ->
                pattern = "{0} - {1}";
            case X_MULTIPLIED_BY_Y ->
                pattern = "{0} * {1}";
            case X_DIVIDED_INTO_Y ->
                pattern = "{0} / {1}";
            case X_RAISED_TO_THE_Y ->
                pattern = "{0} ^ {1}";
            case ADD_YEARS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "dateadd({0}, {1}, 'years')";
            }
            case ADD_MONTHS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "dateadd({0}, {1}, 'months')";
            }
            case ADD_WEEKS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "dateadd({0}, {1}, 'weeks')";
            }
            case ADD_DAYS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "dateadd({0}, {1}, 'days')";
            }
            case ADD_HOURS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "dateadd({0}, {1}, 'hours')";
            }
            case ADD_MINUTES -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "dateadd({0}, {1}, 'minutes')";
            }
            case ADD_SECONDS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "dateadd({0}, {1}, 'seconds')";
            }
            case DIFF_IN_YEARS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "datediff({0}, {1}, 'years')";
            }
            case DIFF_IN_MONTHS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "datediff({0}, {1}, 'months')";
            }
            case DIFF_IN_WEEKS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "datediff({0}, {1}, 'weeks')";
            }
            case DIFF_IN_DAYS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "datediff({0}, {1}, 'days')";
            }
            case DIFF_IN_HOURS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "datediff({0}, {1}, 'hours')";
            }
            case DIFF_IN_MINUTES -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "datediff({0}, {1}, 'minutes')";
            }
            case DIFF_IN_SECONDS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "datediff({0}, {1}, 'seconds')";
            }
            case TO_TIMESTAMP -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "({0}::date + {1}::time)";
            }
            default -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = call(operator, 2);
            }
        }
        return format(pattern, arg1, arg2);
    }

    /**
     * @return the SQL expresion
     */
    @Override
    protected String getSqlScalarExpression(ScalarX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        ScalarOp operator = expression.getOperator();
        Object operand = expression.getOperand();
        if (operand == null) {
            return null;
        }
        String arg1 = getSqlExpression(operand, queryObject, qualifier, px, false);
        String arg2 = getSqlExpressionDefaultValue(expression);
        if (operator == null || operator.equals(ScalarOp.SELF)) {
            return arg1;
        }
        boolean varchar = operand instanceof String || operand instanceof CharacterExpression;
        String pattern;
        switch (operator) {
            case DEFAULT_WHEN_NULL -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "coalesce({0}, {1})";
            }
            case NULL_WHEN_DEFAULT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "nullif({0}, {1})";
            }
            case TO_BOOLEAN ->
                pattern = "cast({0} as boolean)";
            case TO_CHARACTER ->
                pattern = "cast({0} as character(1))";
            case TO_STRING, TO_LOCALE_STRING -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = toCharStringPattern(operand);
            }
            case TO_BYTE ->
                pattern = "cast({0} as smallint)";
            case TO_SHORT ->
                pattern = "cast({0} as smallint)";
            case TO_INTEGER ->
                pattern = "cast({0} as int)";
            case TO_LONG ->
                pattern = "cast({0} as bigint)";
            case TO_FLOAT ->
                pattern = "cast({0} as real)";
            case TO_DOUBLE ->
                pattern = "cast({0} as double precision)";
            case TO_BIG_INTEGER ->
                pattern = "cast({0} as numeric)";
            case TO_BIG_DECIMAL ->
                pattern = "cast({0} as numeric)";
            case TO_DATE -> {
                if (varchar) {
                    arg1 = StrUtils.discloseSqlExpression(arg1);
                }
                pattern = getCurrentDate().equals(arg1) ? arg1 : varchar ? "cast_varchar_as_date({0})" : "cast({0} as date)";
            }
            case TO_TIME -> {
                if (varchar) {
                    arg1 = StrUtils.discloseSqlExpression(arg1);
                }
                pattern = getCurrentTime().equals(arg1) ? arg1 : varchar ? "cast_varchar_as_time({0})" : "cast({0} as time without time zone)";
            }
            case TO_TIMESTAMP -> {
                if (varchar) {
                    arg1 = StrUtils.discloseSqlExpression(arg1);
                }
                pattern = getCurrentTimestamp().equals(arg1) ? arg1 : varchar ? "cast_varchar_as_timestamp({0})" : "cast({0} as timestamp without time zone)";
            }
            case NOT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "not({0})";
            }
            case ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "ascii_string({0})";
            }
            case DIACRITICLESS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "diacriticless({0})";
            }
            case DIACRITICLESS_ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "diacriticless_ascii({0})";
            }
            case LOWER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "lower({0})";
            }
            case UPPER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "upper({0})";
            }
            case CAPITALIZE -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "initcap({0})";
            }
            case UNCAPITALIZE -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "uncap({0})";
            }
            case TRIM -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "trim({0})";
            }
            case LTRIM -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "ltrim({0})";
            }
            case RTRIM -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "rtrim({0})";
            }
            case MODULUS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "abs({0})";
            }
            case OPPOSITE -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "-({0})";
            }
            case RECIPROCAL -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "1/({0})";
            }
            case YEAR, MONTH, DAY, HOUR, MINUTE, SECOND -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
//              pattern = "cast(date_part('" + operator.name().toLowerCase() + "', {0}) as integer)";
                pattern = "date_part('" + operator.name().toLowerCase() + "', {0})";
            }
            case FIRST_DATE_OF_MONTH -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "first_date_of_month({0})";
            }
            case FIRST_DATE_OF_QUARTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "first_date_of_quarter({0})";
            }
            case FIRST_DATE_OF_SEMESTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "first_date_of_semester({0})";
            }
            case FIRST_DATE_OF_YEAR -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "first_date_of_year({0})";
            }
            case LAST_DATE_OF_MONTH -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "last_date_of_month({0})";
            }
            case LAST_DATE_OF_QUARTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "last_date_of_quarter({0})";
            }
            case LAST_DATE_OF_SEMESTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "last_date_of_semester({0})";
            }
            case LAST_DATE_OF_YEAR -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "last_date_of_year({0})";
            }
            default -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = call(operator, 1);
            }
        }
        return format(pattern, arg1, arg2);
    }

    /**
     * @param expression expression
     * @return the SQL expression default value
     */
    @Override
    protected String getSqlExpressionDefaultValue(Expression expression) {
        Class<?> clazz = expression == null ? null : expression.getDataType();
        if (clazz == null) {
            return getNull();
        } else if (Date.class.isAssignableFrom(clazz)) {
            return "date" + getZeroDate();
        } else if (Time.class.isAssignableFrom(clazz)) {
//          return "timetz" + getZeroTime();
            return "time" + getZeroTime();
        } else if (Timestamp.class.isAssignableFrom(clazz)) {
//          return "timestamptz" + getZeroTimestamp();
            return "timestamp" + getZeroTimestamp();
        } else {
            return super.getSqlExpressionDefaultValue(expression);
        }
    }

//  @Override
//  public String getSqlSchemaName(PersistentEntity entity) {
//      String schema = super.getSqlSchemaName(entity);
//      return StringUtils.isBlank(schema) ? "public" : schema;
//  }
//
    @Override
    protected String defaultCharStringPattern() {
        return "cast({0} as character varying)";
    }

    @Override
    protected String defaultZeroPaddedStringPattern(int width) {
        return "lpad({0}::text, " + width + ", '0')";
    }

    @Override
    protected String randomlyGeneratedUniqueIdentifier() {
        return "gen_random_uuid()::text"; // 36 characters
    }

    @Override
    protected String concat(String... strings) {
        if (strings == null || strings.length < 2) {
            return getNull();
        }
        List<String> arguments = new ArrayList<>();
        for (String string : strings) {
            arguments.add(StrUtils.discloseSqlExpression(string));
        }
        return "concat(" + StringUtils.join(arguments, ", ") + ")";
    }

}
