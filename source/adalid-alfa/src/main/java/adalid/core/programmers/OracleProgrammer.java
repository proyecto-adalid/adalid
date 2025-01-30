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
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.primitives.*;
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
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class OracleProgrammer extends AbstractSqlProgrammer {

    private static final Logger logger = Logger.getLogger(OracleProgrammer.class);

    private static final String ORACLE_VERSION = "oracle.version";

    @Override
    public String getDBMS() {
        return "Oracle";
    }

    // <editor-fold defaultstate="collapsed" desc="SQL comparison operators">
    private final String[] NEVER_NULL = {
        "current_user", "current_user_code()", "current_user_id()",
        "current_date", "current_time", "current_timestamp", "localtimestamp"
    };

    @Override
    protected String[] neverNull() {
        return NEVER_NULL;
    }

    @Override
    protected boolean validExpressionOperator(ComparisonOp operator) {
        return switch (operator) {
            case IS_NULL, IS_NOT_NULL, IS_NULL_OR_TRUE, IS_NULL_OR_FALSE, IS_NULL_OR_EQ, IS_NULL_OR_NEQ, IS_NULL_OR_GT, IS_NULL_OR_LTEQ, IS_NULL_OR_GTEQ, IS_NULL_OR_LT, IS_NULL_OR_STARTS_WITH, IS_NULL_OR_NOT_STARTS_WITH, IS_NULL_OR_CONTAINS, IS_NULL_OR_NOT_CONTAINS, IS_NULL_OR_ENDS_WITH, IS_NULL_OR_NOT_ENDS_WITH, IS_NULL_OR_IN, IS_NULL_OR_NOT_IN, IS_NULL_OR_BETWEEN, IS_NULL_OR_NOT_BETWEEN ->
                false;
            default ->
                true;
        };
    }

    @Override
    protected String primitiveIsTruePattern() {
        return ARG0 + " = 'true'";
    }

    @Override
    protected String primitiveIsFalsePattern() {
        return ARG0 + " = 'false'";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="referential integrity options">
    @Override
    protected String getRestricted() {
        return null;
    }

    @Override
    protected String getCascade() {
        return "cascade";
    }

    @Override
    protected String getNullify() {
        return "set null";
    }

    @Override
    protected String getNoAction() {
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="keywords">
    @Override
    protected String getTrue() {
        return "'true'";
    }

    @Override
    protected String getFalse() {
        return "'false'";
    }

    @Override
    protected String getCurrentDate() {
        return "current_date";
    }

    @Override
    protected String getCurrentTime() {
//      return "current_time";
        return "localtimestamp";
    }

    @Override
    protected String getCurrentTimestamp() {
//      return "current_timestamp";
        return "localtimestamp";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="bootstrapping properties">
    protected static final boolean AVOID_LONG_IDENTIFIERS = BitUtils.valueOf(bootstrapping.getString("oracle.database.avoid.long.identifiers", "false"));

    protected static final boolean NATIONAL_CHARACTER_SET = BitUtils.valueOf(bootstrapping.getString("oracle.database.national.character.set", "false"));

    protected static final boolean EXTENDED_MAX_STRING_SIZE = BitUtils.valueOf(bootstrapping.getString("oracle.database.extended.max.string.size", "false"));
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="limits">
    protected static final int MAX_DECIMAL_DIGITS = 38;

    protected static final int MAX_UNICODE_LENGTH = EXTENDED_MAX_STRING_SIZE ? 16383 : 2000;

    protected static final int MAX_NON_UNI_LENGTH = EXTENDED_MAX_STRING_SIZE ? 32767 : 4000;

    protected static final int MAX_VARCHAR_LENGTH = NATIONAL_CHARACTER_SET ? MAX_UNICODE_LENGTH : MAX_NON_UNI_LENGTH;

    private int maxIdentifierLength;

    @Override
    public int getMaxIdentifierLength() {
        if (maxIdentifierLength == 0) {
            if (AVOID_LONG_IDENTIFIERS) {
                maxIdentifierLength = 30;
            } else {
                Project project = TLC.getProject();
                if (project != null) {
                    String version = project.getEnvironmentVariable(ORACLE_VERSION);
                    if (StringUtils.isNotBlank(version)) {
                        version = dottedVersionNumber(version);
                        version += ".0.0";
                        int major = IntUtils.valueOf(StringUtils.substringBefore(version, "."), 0);
                        if (major > 0) {
                            int minor = IntUtils.valueOf(StringUtils.substringBetween(version, "."), 0);
                            maxIdentifierLength = major < 12 || (major == 12 && minor < 2) ? 30 : 128;
                            logger.info(ORACLE_VERSION + ".max.identifier.length=" + maxIdentifierLength);
                            return maxIdentifierLength;
                        }
                    }
                }
                return 30;
            }
        }
        return maxIdentifierLength;
    }

    private String dottedVersionNumber(String version) {
        String v1 = version.trim().toLowerCase();
        logger.info(ORACLE_VERSION + "=" + v1);
        String v2 = StrUtils.getOracleVersionNumber(v1);
        if (v1.equals(v2)) {
            return v1;
        }
        logger.info(ORACLE_VERSION + "=" + v2);
        return v2;
    }

    @Override
    public int getMaxVarcharLength() {
        return MAX_VARCHAR_LENGTH;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Java/SQL types">
    protected static final String BINARY = "blob";

    protected static final String BOOLEANX = "varchar2";

    protected static final String BOOLEAN = BOOLEANX + "(5)";

    protected static final String CHARX = NATIONAL_CHARACTER_SET ? "nchar" : "char";

    protected static final String CHAR = CHARX + "(1)";

    protected static final String VARCHARX = NATIONAL_CHARACTER_SET ? "nvarchar2" : "varchar2";

    protected static final String VARCHAR = VARCHARX + "({0})";

    protected static final String TEXT = VARCHARX + "(" + MAX_VARCHAR_LENGTH + ")";

    protected static final String CLOB = NATIONAL_CHARACTER_SET ? "nclob" : "clob";

    protected static final String BYTE = "number(3)";

    protected static final String SMALLINT = "number(5)";

    protected static final String INTEGER = "number(10)";

    protected static final String LONG = "number(19)";

//  protected static final String FLOAT = "float";
    protected static final String FLOAT = "number";

//  protected static final String DOUBLE = "float";
    protected static final String DOUBLE = "number";

    protected static final String BIGINT = "number({0})";

    protected static final String DECIMAL = "number({0},{1})";

    protected static final String NUMERIC = "number";

    protected static final String DATE = "date";

    protected static final String TIMEX = "timestamp";

    protected static final String TIME = TIMEX + "({0})";

    protected static final String TIMESTAMPX = "timestamp";

    protected static final String TIMESTAMP = TIMESTAMPX + "({0})";

    protected static final String RECORD = "record";

    protected static final String VOID = "void";
    // </editor-fold>

    @Override
    protected String getVariablesPrefix() {
        return "x$";
    }

    @Override
    protected String getVariablesSuffix() {
        return "";
    }

    @Override
    public String getString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Time) {
            return TimeUtils.jdbcTimestampString(obj);
        } else {
            return super.getString(obj);
        }
    }

    @Override
    public String getDelimitedString(Object obj) {
        String string = getString(obj);
        if (string == null) {
            return null;
        } else if (obj instanceof Boolean aBoolean) {
            return aBoolean ? getTrue() : getFalse();
        } else if (obj instanceof String) {
            return SQM$ + string + SQM$;
        } else if (obj instanceof Date) {
            return "date" + SQM$ + string + SQM$;
        } else if (obj instanceof Time) {
//          return "timetz" + SQM$ + string + SQM$;
            return "timestamp" + SQM$ + string + SQM$;
        } else if (obj instanceof java.util.Date) {
//          return "timestamptz" + SQM$ + string + SQM$;
            return "timestamp" + SQM$ + string + SQM$;
        } else {
            return super.getDelimitedString(obj);
        }
    }

    /* para strings TemporalAddend */
    private static final String YM_INTERVAL_ADDEND_PATTERN = "{0} {1} TO_YMINTERVAL(''{2}-{3}'')";

    /* para strings TemporalAddend */
    private static final String DS_INTERVAL_ADDEND_PATTERN = "{0} {1} TO_DSINTERVAL(''{2} {3}:{4}:{5}'')";

    @Override
    public String getDelimitedString(TemporalAddend addend) {
        if (addend == null || addend.isBadValue()) {
            return null;
        }
        String base;
        int anys = 0, months = 0, days = 0, hours = 0, minutes = 0, seconds = 0;
        int remainder;
        int quantity = addend.getQuantity();
        int absolute = Math.abs(quantity);
        char sign = quantity < 0 ? '-' : '+';
        char unit = addend.getUnitCode();
        boolean b = false;
        switch (unit) {
            case 'A', 'Y' -> {
                base = "CURRENT_DATE";
                anys = absolute;
                b = true;
            }
            case 'M' -> {
                base = "CURRENT_DATE";
                anys = absolute / 12;
                months = absolute % 12;
                b = true;
            }
            case 'D' -> {
                base = "CURRENT_DATE";
                days = absolute;
            }
            case 'h' -> {
                base = "LOCALTIMESTAMP";
                days = absolute / 24;
                hours = absolute % 24;
            }
            case 'm' -> {
                base = "LOCALTIMESTAMP";
                days = absolute / (24 * 60);
                remainder = absolute % (24 * 60);
                hours = remainder / 60;
                minutes = remainder % 60;
            }
            case 's' -> {
                base = "LOCALTIMESTAMP";
                days = absolute / (24 * 60 * 60);
                remainder = absolute % (24 * 60 * 60);
                hours = remainder / (60 * 60);
                remainder = remainder % (60 * 60);
                minutes = remainder / 60;
                seconds = remainder % 60;
            }
            default -> {
                return null;
            }
        }
        String string = absolute == 0 ? base
            : b ? MessageFormat.format(YM_INTERVAL_ADDEND_PATTERN, base, sign, anys + "", months)
                : MessageFormat.format(DS_INTERVAL_ADDEND_PATTERN, base, sign, days + "", hours, minutes, seconds);
        /**/
        return absolute == 0 ? string : StrUtils.encloseSqlExpression(string);
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
            return BOOLEANX;
        } else if (artifact instanceof CharacterData) {
            return CHARX;
        } else if (artifact instanceof StringData) {
            return VARCHARX;
        } else if (artifact instanceof ByteData) {
            return NUMERIC;
        } else if (artifact instanceof ShortData) {
            return NUMERIC;
        } else if (artifact instanceof IntegerData) {
            return NUMERIC;
        } else if (artifact instanceof LongData) {
            return NUMERIC;
        } else if (artifact instanceof FloatData) {
            return NUMERIC;
        } else if (artifact instanceof DoubleData) {
            return NUMERIC;
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
        } else if (artifact instanceof Entity) {
            return NUMERIC;
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
            return l < 1 || l > MAX_VARCHAR_LENGTH ? CLOB : format(VARCHAR, l);
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
            return format(BIGINT, p > MAX_DECIMAL_DIGITS ? MAX_DECIMAL_DIGITS : p);
        } else if (artifact instanceof BigDecimalData data) {
            int p = data.getPrecision();
            int s = data.getScale();
            if (p > MAX_DECIMAL_DIGITS) {
                p = MAX_DECIMAL_DIGITS;
                if (s > MAX_DECIMAL_DIGITS) {
                    s = MAX_DECIMAL_DIGITS;
                }
            }
            return format(DECIMAL, p, s);
        } else if (artifact instanceof DateData) {
            return DATE;
        } else if (artifact instanceof TimeData data) {
            int p = IntUtils.valueOf(data.getPrecision(), Constants.DEFAULT_TIME_PRECISION);
            return format(TIME, p);
        } else if (artifact instanceof TimestampData data) {
            int p = IntUtils.valueOf(data.getPrecision(), Constants.DEFAULT_TIME_PRECISION);
            return format(TIMESTAMP, p);
        } else if (artifact instanceof Expression expression) {
            return getExpressionType(expression);
        } else if (artifact instanceof Entity entity) {
            return getEntityReferenceType(entity);
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
            return null;
        }
        return switch (onDeleteAction) {
            case CASCADE ->
                getCascade();
            case NULLIFY ->
                getNullify();
            default ->
                null;
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
            return null;
        }
        return switch (onUpdateAction) {
            case CASCADE ->
                getCascade();
            case NULLIFY ->
                getNullify();
            default ->
                null;
        };
    }

    /**
     * @return the SQL expresion
     */
    @Override
    protected String getSqlDataAggregateExpression(DataAggregateX expression, Object queryObject, SqlQualifierType qualifier, ParameterizedExpression px) {
        DataAggregateOp operator = expression.getOperator();
        Object[] operands = expression.getOperands();
        if (operator == null || operands == null || operands.length < 2) {
            return null;
        }
        Object bcxi;
        String[] arguments = new String[operands.length];
        String[] booleanArguments = new String[operands.length];
        for (int i = 0; i < operands.length; i++) {
            arguments[i] = getSqlExpression(operands[i], queryObject, qualifier, px, false);
            bcxi = operands[i] instanceof BooleanPrimitive ? XB.Boolean.Comparison.isTrue((BooleanPrimitive) operands[i]) : operands[i];
            booleanArguments[i] = getSqlExpression(bcxi, queryObject, qualifier, px, true);
        }
        String string;
        string = switch (operator) {
            case COALESCE ->
                call("coalesce", arguments);
            case MAXIMUM ->
                call("greatest", arguments);
            case MINIMUM ->
                call("least", arguments);
            case AND ->
                and(booleanArguments);
            case NAND ->
                not(and(booleanArguments));
            case OR ->
                or(booleanArguments);
            case NOR ->
                not(or(booleanArguments));
            case NAXOR ->
                xor(booleanArguments);
            case NAXNOR ->
                not(xor(booleanArguments));
            case NOR_OR_NAXOR ->
                or(not(or(booleanArguments)), xor(booleanArguments));
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
        Object bcx1 = x instanceof BooleanPrimitive ? XB.Boolean.Comparison.isTrue((BooleanPrimitive) x) : x;
        Object bcx2 = y instanceof BooleanPrimitive ? XB.Boolean.Comparison.isTrue((BooleanPrimitive) y) : y;
        String boo1 = getSqlExpression(bcx1, queryObject, qualifier, px, true);
        String boo2 = getSqlExpression(bcx2, queryObject, qualifier, px, true);
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
            case AND -> {
                arg1 = boo1;
                arg2 = boo2;
                pattern = "{0} and {1}";
            }
            case NAND -> {
                arg1 = boo1;
                arg2 = boo2;
                pattern = "not({0} and {1})";
            }
            case OR -> {
                arg1 = boo1;
                arg2 = boo2;
                pattern = "{0} or {1}";
            }
            case NOR -> {
                arg1 = boo1;
                arg2 = boo2;
                pattern = "not({0} or {1})";
            }
            case XOR -> {
                arg1 = boo1;
                arg2 = boo2;
                pattern = "not({0} and {1}) and ({0} or {1})";
            }
            case XNOR -> {
                arg1 = boo1;
                arg2 = boo2;
                pattern = "not({0} or {1}) or ({0} and {1})";
            }
            case X_IMPLIES_Y -> {
                arg1 = boo1;
                arg2 = boo2;
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "not({0}) or {1}";
            }
            case ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_ascii_string_2({0}, {1})";
            }
            case DIACRITICLESS_ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_diacriticless_ascii_2({0}, {1})";
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
                pattern = "util_string_format({0}, {1})";
            }
            case LEFT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "substr({0}, 1, {1})";
            }
            case RIGHT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "substr({0}, 0-({1}))";
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
            case X_RAISED_TO_THE_Y -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "power({0}, {1})";
            }
            case ADD_YEARS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_dateadd({0}, {1}, 'years')";
            }
            case ADD_MONTHS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_dateadd({0}, {1}, 'months')";
            }
            case ADD_WEEKS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_dateadd({0}, {1}, 'weeks')";
            }
            case ADD_DAYS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_dateadd({0}, {1}, 'days')";
            }
            case ADD_HOURS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_dateadd({0}, {1}, 'hours')";
            }
            case ADD_MINUTES -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_dateadd({0}, {1}, 'minutes')";
            }
            case ADD_SECONDS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_dateadd({0}, {1}, 'seconds')";
            }
            case DIFF_IN_YEARS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_datediff({0}, {1}, 'years')";
            }
            case DIFF_IN_MONTHS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_datediff({0}, {1}, 'months')";
            }
            case DIFF_IN_WEEKS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_datediff({0}, {1}, 'weeks')";
            }
            case DIFF_IN_DAYS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_datediff({0}, {1}, 'days')";
            }
            case DIFF_IN_HOURS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_datediff({0}, {1}, 'hours')";
            }
            case DIFF_IN_MINUTES -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_datediff({0}, {1}, 'minutes')";
            }
            case DIFF_IN_SECONDS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "util_datediff({0}, {1}, 'seconds')";
            }
            case TO_TIMESTAMP -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                arg2 = StrUtils.discloseSqlExpression(arg2);
                pattern = "case when {0} is null or {1} is null then null else "
                    + "to_timestamp(to_char({0}, 'YYYY-MM-DD')||'-'||to_char({1}, 'HH24:MI:SS'), 'YYYY-MM-DD-HH24:MI:SS') end";
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
        String is_true;
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
                pattern = "cast({0} as " + BOOLEAN + ")";
            case TO_CHARACTER ->
                pattern = "cast({0} as " + CHAR + ")";
            case TO_STRING, TO_LOCALE_STRING -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = toCharStringPattern(operand);
            }
            case TO_BYTE ->
                pattern = "cast({0} as " + SMALLINT + ")";
            case TO_SHORT ->
                pattern = "cast({0} as " + SMALLINT + ")";
            case TO_INTEGER ->
                pattern = "cast({0} as " + INTEGER + ")";
            case TO_LONG ->
                pattern = "cast({0} as " + LONG + ")";
            case TO_FLOAT ->
                pattern = "cast({0} as " + FLOAT + ")";
            case TO_DOUBLE ->
                pattern = "cast({0} as " + DOUBLE + ")";
            case TO_BIG_INTEGER ->
                pattern = "cast({0} as " + NUMERIC + ")";
            case TO_BIG_DECIMAL ->
                pattern = "cast({0} as " + NUMERIC + ")";
            case TO_DATE -> {
                if (varchar) {
                    arg1 = StrUtils.discloseSqlExpression(arg1);
                }
                pattern = getCurrentDate().equals(arg1) ? arg1 : varchar ? "util_cast_varchar_as_date({0})" : "cast({0} as " + DATE + ")";
            }
            case TO_TIME -> {
                if (varchar) {
                    arg1 = StrUtils.discloseSqlExpression(arg1);
                }
                pattern = getCurrentTime().equals(arg1) ? arg1 : varchar ? "util_cast_varchar_as_time({0})" : "cast({0} as " + TIMEX + ")";
            }
            case TO_TIMESTAMP -> {
                if (varchar) {
                    arg1 = StrUtils.discloseSqlExpression(arg1);
                }
                pattern = getCurrentTimestamp().equals(arg1) ? arg1 : varchar ? "util_cast_varchar_as_timestamp({0})" : "cast({0} as " + TIMESTAMPX + ")";
            }
            case NOT -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                is_true = operand instanceof Primitive ? primitiveIsTruePattern() : expressionIsTruePattern();
                pattern = "not(" + is_true + ")";
            }
            case ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_ascii_string_1({0})";
            }
            case DIACRITICLESS -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_diacriticless({0})";
            }
            case DIACRITICLESS_ASCII -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_diacriticless_ascii_1({0})";
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
                pattern = "extract(" + operator.name().toLowerCase() + " from {0})";
            }
            case FIRST_DATE_OF_MONTH -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_first_date_of_month({0})";
            }
            case FIRST_DATE_OF_QUARTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_first_date_of_quarter({0})";
            }
            case FIRST_DATE_OF_SEMESTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_first_date_of_semester({0})";
            }
            case FIRST_DATE_OF_YEAR -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_first_date_of_year({0})";
            }
            case LAST_DATE_OF_MONTH -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_last_date_of_month({0})";
            }
            case LAST_DATE_OF_QUARTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_last_date_of_quarter({0})";
            }
            case LAST_DATE_OF_SEMESTER -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_last_date_of_semester({0})";
            }
            case LAST_DATE_OF_YEAR -> {
                arg1 = StrUtils.discloseSqlExpression(arg1);
                pattern = "util_last_date_of_year({0})";
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
    protected String fixCalculableColumnValueExpression(String expression, Property property) {
        if (expression != null && !expression.equals(getNull())) {
            if (property instanceof CalculableProperty calculableProperty) {
                Object calculableValue = calculableProperty.getCalculableValue();
                if (calculableValue instanceof BooleanExpression) {
                    if (!(calculableValue instanceof BooleanPrimitive)) {
                        return format(getCaseWhenThenElsePattern(), expression, getTrue(), getFalse());
                    }
                }
            }
        }
        return super.fixCalculableColumnValueExpression(expression, property);
    }

    @Override
    protected String defaultCharStringPattern() {
        return "to_char({0})";
    }

    @Override
    protected String defaultZeroPaddedStringPattern(int width) {
        return "lpad({0}, " + width + ", '0')";
    }

    @Override
    protected String randomlyGeneratedUniqueIdentifier() {
        return "rawtohex(sys_guid())"; // 32 characters
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
        String[] arg = arguments.toArray(strings);
        String concatenation = concatXY(arg[0], arg[1]);
        for (int i = 2; i < arg.length; i++) {
            concatenation = concatXY(concatenation, arg[i]);
        }
        return concatenation;
    }

    private String concatXY(String x, String y) {
        return "concat(" + x + ", " + y + ")";
    }

}
