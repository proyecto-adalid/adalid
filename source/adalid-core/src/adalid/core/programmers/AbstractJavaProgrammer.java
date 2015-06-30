/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.programmers;

import adalid.commons.bundles.Bundle;
import adalid.commons.util.StrUtils;
import adalid.commons.util.TimeUtils;
import adalid.core.Instance;
import adalid.core.enums.ComparisonOp;
import adalid.core.enums.DataAggregateOp;
import adalid.core.enums.OrderedPairOp;
import adalid.core.enums.ScalarOp;
import adalid.core.enums.SpecialBooleanValue;
import adalid.core.enums.SpecialCharacterValue;
import adalid.core.enums.SpecialEntityValue;
import adalid.core.enums.SpecialNumericValue;
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.exceptions.InstantiationRuntimeException;
import adalid.core.expressions.VariantX;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.ComparisonX;
import adalid.core.interfaces.ConditionalX;
import adalid.core.interfaces.DataAggregateX;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.JavaProgrammer;
import adalid.core.interfaces.NamedValue;
import adalid.core.interfaces.Operator;
import adalid.core.interfaces.OrderedPairX;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.RowsAggregateX;
import adalid.core.interfaces.ScalarX;
import adalid.core.interfaces.TypedArtifact;
import adalid.core.interfaces.ValuedArtifact;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractJavaProgrammer extends AbstractProgrammer implements JavaProgrammer {

    protected static final Logger logger = Logger.getLogger(JavaProgrammer.class);

    protected static final String EMPTY = "";

    // <editor-fold defaultstate="collapsed" desc="string constants">
    protected static final boolean RTL = Bundle.getBoolean("java.right_to_left");

    protected static final String UTIL = "ObjUtils";

    protected static final String SEP$ = COM$;

    protected static final String[] KEYWORDS = new String[]{
        "abstract",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "try",
        "void",
        "volatile",
        "while"
    };

    public static Set<String> getJavaKeywords() {
        return new TreeSet<>(Arrays.asList(KEYWORDS));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="name">
    @Override
    public String getJavaName(Artifact artifact) {
        return artifact == null ? null : getJavaName(artifact.getName());
    }

    @Override
    public String getJavaName(String name) {
        return StrUtils.getStringAscii(name);
    }

    @Override
    public String getJavaConstantName(Artifact artifact) {
        return artifact == null ? null : getJavaConstantName(artifact.getName());
    }

    @Override
    public String getJavaConstantName(String name) {
        return getJavaUpperConstantName(name);
    }

    @Override
    public String getJavaUpperConstantName(Artifact artifact) {
        return artifact == null ? null : getJavaUpperConstantName(artifact.getName());
    }

    @Override
    public String getJavaUpperConstantName(String name) {
        return StrUtils.getUpperHumplessCase(name);
    }

    @Override
    public String getJavaLowerConstantName(Artifact artifact) {
        return artifact == null ? null : getJavaLowerConstantName(artifact.getName());
    }

    @Override
    public String getJavaLowerConstantName(String name) {
        return StrUtils.getLowerHumplessCase(name);
    }

    @Override
    public String getJavaClassName(Artifact artifact) {
        return artifact == null ? null : getJavaClassName(artifact.getName());
    }

    @Override
    public String getJavaClassName(String name) {
        return getJavaUpperClassName(name);
    }

    @Override
    public String getJavaUpperClassName(Artifact artifact) {
        return artifact == null ? null : getJavaUpperClassName(artifact.getName());
    }

    @Override
    public String getJavaUpperClassName(String name) {
        return StringUtils.capitalize(StrUtils.getCamelCase(name, true));
    }

    @Override
    public String getJavaLowerClassName(Artifact artifact) {
        return artifact == null ? null : getJavaLowerClassName(artifact.getName());
    }

    @Override
    public String getJavaLowerClassName(String name) {
        return StringUtils.uncapitalize(StrUtils.getCamelCase(name, true));
    }

    @Override
    public String getJavaVariableName(Artifact artifact) {
        return artifact == null ? null : getJavaVariableName(artifact.getName());
    }

    @Override
    public String getJavaVariableName(String name) {
        return getJavaLowerVariableName(name);
    }

    @Override
    public String getJavaUpperVariableName(Artifact artifact) {
        return artifact == null ? null : getJavaUpperVariableName(artifact.getName());
    }

    @Override
    public String getJavaUpperVariableName(String name) {
        return StringUtils.capitalize(StrUtils.getCamelCase(name, true));
    }

    @Override
    public String getJavaLowerVariableName(Artifact artifact) {
        return artifact == null ? null : getJavaLowerVariableName(artifact.getName());
    }

    @Override
    public String getJavaLowerVariableName(String name) {
        return StringUtils.uncapitalize(StrUtils.getCamelCase(name, true));
    }

    @Override
    public String getJavaQualifiedName(Artifact artifact) {
        return artifact == null ? null : artifact.getPathString();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="type">
    @Override
    public String getJavaType(Artifact artifact) {
        Class<?> dataType = getDataType(artifact);
        return dataType == null ? null : javaLangLess(dataType);
    }

    @Override
    public String getJavaTypeName(Artifact artifact) {
        Class<?> dataType = getDataType(artifact);
        return dataType == null ? null : dataType.getName();
    }

    @Override
    public String getJavaTypeCanonicalName(Artifact artifact) {
        Class<?> dataType = getDataType(artifact);
        return dataType == null ? null : dataType.getCanonicalName();
    }

    @Override
    public String getJavaTypeSimpleName(Artifact artifact) {
        Class<?> dataType = getDataType(artifact);
        return dataType == null ? null : dataType.getSimpleName();
    }

    protected Class<?> getDataType(Artifact artifact) {
        TypedArtifact typedArtifact = (artifact instanceof TypedArtifact) ? (TypedArtifact) artifact : null;
        return typedArtifact == null ? null : typedArtifact.getDataType();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="value">
    @Override
    public String getJavaInitialValue(Artifact artifact) {
        Object initialValue = getInitialValue(artifact);
        Class<?> dataType = getDataType(artifact);
        return initialValue == null || dataType == null ? null : getJavaValue(initialValue, dataType);
    }

    protected Object getInitialValue(Artifact artifact) {
        ValuedArtifact valuedArtifact = (artifact instanceof ValuedArtifact) ? (ValuedArtifact) artifact : null;
        return valuedArtifact == null ? null : valuedArtifact.getInitialValue();
    }

    @Override
    public String getJavaDefaultValue(Artifact artifact) {
        Object defaultValue = getDefaultValue(artifact);
        Class<?> dataType = getDataType(artifact);
        return defaultValue == null || dataType == null ? null : getJavaValue(defaultValue, dataType);
    }

    protected Object getDefaultValue(Artifact artifact) {
        ValuedArtifact valuedArtifact = (artifact instanceof ValuedArtifact) ? (ValuedArtifact) artifact : null;
        return valuedArtifact == null ? null : valuedArtifact.getDefaultValue();
    }

    @Override
    public String getJavaCurrentValue(Artifact artifact) {
        Object currentValue = getCurrentValue(artifact);
        Class<?> dataType = getDataType(artifact);
        return currentValue == null || dataType == null ? null : getJavaValue(currentValue, dataType);
    }

    protected Object getCurrentValue(Artifact artifact) {
        ValuedArtifact valuedArtifact = (artifact instanceof ValuedArtifact) ? (ValuedArtifact) artifact : null;
        return valuedArtifact == null ? null : valuedArtifact.getCurrentValue();
    }

    @Override
    public String getJavaValue(Object object) {
        return object == null ? null : getJavaValue(object, object.getClass());
    }

    //protected
    protected String getJavaValue(Object object, Class<?> type) {
        if (object == null || type == null) {
            return null;
        } else if (object instanceof Instance) { // && Entity.class.isAssignableFrom(type)
            Instance instance = (Instance) object;
            return getJavaPrimitiveValue(instance.getInstanceKeyValue(), instance.getInstanceKeyType());
        } else if (object instanceof Artifact) {
            return null;
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
            return getJavaPrimitiveValue(object, type);
        }
    }

    @Override
    public String getJavaPrimitiveValue(Object object, String typeName) {
        if (object == null || typeName == null) {
            return null;
        }
        String name = typeName.contains(".") ? typeName : "java.lang." + typeName;
        Class<?> type = getClassForName(name);
        return getJavaPrimitiveValue(object, type);
    }

    protected Class<?> getClassForName(String className) {
        if (StringUtils.isBlank(className)) {
            return null;
        } else {
            String message = "failed to load " + className;
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ex) {
                throw new InstantiationRuntimeException(message, ex);
            }
        }
    }

    //protected
    protected String getSpecialBooleanValue(SpecialBooleanValue value) {
        switch (value) {
            case NULL:
                return "null";
            case TRUE:
                return "Boolean.TRUE";
            case FALSE:
                return "Boolean.FALSE";
            default:
                return null;
        }
    }

    //protected
    protected String getSpecialCharacterValue(SpecialCharacterValue value) {
        switch (value) {
            case NULL:
                return "null";
            case EMPTY:
                return DQM$ + DQM$;
            case CURRENT_USER_CODE:
                return "TLC.getUsuarioActual().getCodigoUsuario()";
            default:
                return null;
        }
    }

    //protected
    protected String getSpecialEntityValue(SpecialEntityValue value) {
        switch (value) {
            case NULL:
                return "null";
            case CURRENT_USER:
                return "TLC.getUsuarioActual().getIdUsuario()";
            default:
                return null;
        }
    }

    //protected
    protected String getSpecialNumericValue(SpecialNumericValue value) {
        switch (value) {
            case NULL:
                return "null";
            case CURRENT_USER_ID:
                return "TLC.getUsuarioActual().getIdUsuario()";
            default:
                return null;
        }
    }

    //protected
    protected String getSpecialTemporalValue(SpecialTemporalValue value) {
        switch (value) {
            case NULL:
                return "null";
            case CURRENT_DATE:
                return "TimeUtils.currentDate()";
            case CURRENT_TIME:
                return "TimeUtils.currentTime()";
            case CURRENT_TIMESTAMP:
                return "TimeUtils.currentTimestamp()";
            default:
                return null;
        }
    }

    //protected
    protected String getJavaPrimitiveValue(Object object, Class<?> type) {
        if (object == null || type == null) {
            return null;
        } else if (Boolean.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Character.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (String.class.isAssignableFrom(type)) {
            return getDelimitedString(object, type);
        } else if (Byte.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Short.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Integer.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Long.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Float.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Double.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (BigInteger.class.isAssignableFrom(type)) {
            return newFromString(object, type);
        } else if (BigDecimal.class.isAssignableFrom(type)) {
            return newFromString(object, type);
        } else if (Date.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Time.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else if (Timestamp.class.isAssignableFrom(type)) {
            return valueOfString(object, type);
        } else {
            return null;
        }
    }

    protected String newFromString(Object object, Class<?> type) {
        String string = getString(object, type);
        if (string == null) {
            return null;
        } else {
            return "new" + SPC$ + javaLangLess(type) + LRB$ + DQM$ + string + DQM$ + RRB$;
        }
    }

    protected String valueOfString(Object object, Class<?> type) {
        String string = getString(object, type);
        if (string == null) {
            return null;
        } else if (Boolean.class.isAssignableFrom(type)) {
            if (string.equalsIgnoreCase("true")) {
                return "Boolean.TRUE";
            }
            if (string.equalsIgnoreCase("false")) {
                return "Boolean.FALSE";
            }
            return javaLangLess(type) + DOT$ + "valueOf" + LRB$ + DQM$ + string + DQM$ + RRB$;
        } else if (Character.class.isAssignableFrom(type)) {
            return javaLangLess(type) + DOT$ + "valueOf" + LRB$ + SQM$ + string + SQM$ + RRB$;
        } else {
            return javaLangLess(type) + DOT$ + "valueOf" + LRB$ + DQM$ + string + DQM$ + RRB$;
        }
    }

    protected String javaLangLess(Class<?> type) {
        return type == null ? "Object"
            : type.isArray() ? javaLangLess(type.getComponentType()) + "[]"
                : StringUtils.removeStart(type.getName(), "java.lang.");
    }

    @Override
    public String getJavaString(String string) {
        return StrUtils.getStringJava(string);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="string">
    //protected
    protected String getString(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof String) {
            return StrUtils.getStringJava((String) object);
        } else if (object instanceof Date) {
            return TimeUtils.jdbcDateString(object);
        } else if (object instanceof Time) {
            return TimeUtils.jdbcTimeString(object);
        } else if (object instanceof java.util.Date) {
            return TimeUtils.jdbcTimestampString(object);
        } else {
            return String.valueOf(object);
        }
    }

    //protected
    protected String getDelimitedString(Object object) {
        if (object == null) {
            return null;
        }
        String string = getString(object);
        if (string == null) {
            return null;
        } else if (object instanceof Character) {
            return SQM$ + string + SQM$;
        } else if (object instanceof String) {
            return DQM$ + string + DQM$;
        } else if (object instanceof java.util.Date) {
            return DQM$ + string + DQM$;
        } else {
            return string;
        }
    }

    //protected
    protected String getString(Object object, Class<?> type) {
        if (object == null || type == null) {
            return null;
        }
        String errmsg = "cannot get \"" + javaLangLess(type) + "\" from \"" + object + "\"";
        String string = getString(object);
        try {
            if (string == null) {
                return null;
            } else if (Boolean.class.isAssignableFrom(type)) {
                return "" + Boolean.valueOf(string);
            } else if (Character.class.isAssignableFrom(type)) {
                return getCharacterString(string);
            } else if (String.class.isAssignableFrom(type)) {
                return string;
            } else if (Byte.class.isAssignableFrom(type)) {
                return "" + Byte.valueOf(string);
            } else if (Short.class.isAssignableFrom(type)) {
                return "" + Short.valueOf(string);
            } else if (Integer.class.isAssignableFrom(type)) {
                return "" + Integer.valueOf(string);
            } else if (Long.class.isAssignableFrom(type)) {
                return "" + Long.valueOf(string);
            } else if (Float.class.isAssignableFrom(type)) {
                return "" + Float.valueOf(string);
            } else if (Double.class.isAssignableFrom(type)) {
                return "" + Double.valueOf(string);
            } else if (BigInteger.class.isAssignableFrom(type)) {
                return "" + new BigInteger(string);
            } else if (BigDecimal.class.isAssignableFrom(type)) {
                return "" + new BigDecimal(string);
            } else if (object instanceof java.util.Date && Date.class.isAssignableFrom(type)) {
                string = TimeUtils.jdbcDateString(object);
                return getString(Date.valueOf(string));
            } else if (object instanceof java.util.Date && Time.class.isAssignableFrom(type)) {
                string = TimeUtils.jdbcTimeString(object);
                return getString(Time.valueOf(string));
            } else if (object instanceof java.util.Date && Timestamp.class.isAssignableFrom(type)) {
                string = TimeUtils.jdbcTimestampString(object);
                return getString(Timestamp.valueOf(string));
            } else {
                return null;
            }
        } catch (IllegalArgumentException ex) {
//          logger.error(errmsg, ThrowableUtils.getCause(ex));
            logger.error(errmsg);
            return null;
        }
    }

    //protected
    protected String getCharacterString(String string) {
        if (StringUtils.isBlank(string)) {
            return SPC$;
        }
        String s = string.trim();
        int endIndex = s.startsWith(BSL$) ? s.charAt(1) == 'u' ? 6 : 2 : 1;
        return s.substring(0, endIndex);
    }

    //protected
    protected String getDelimitedString(Object object, Class<?> type) {
        if (object == null || type == null) {
            return null;
        }
        String string = getString(object, type);
        if (string == null) {
            return null;
        } else if (Character.class.isAssignableFrom(type)) {
            return SQM$ + string + SQM$;
        } else if (String.class.isAssignableFrom(type)) {
            return DQM$ + string + DQM$;
        } else if (java.util.Date.class.isAssignableFrom(type)) {
            return DQM$ + string + DQM$;
        } else {
            return string;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="expression">
    /**
     * @param object
     * @return the java expression
     */
    @Override
    public ParameterizedExpression getJavaParameterizedExpression(Object object) {
        ParameterizedExpression px = new ParameterizedExpression();
        String expression = getJavaExpression(object, px, false);
        px.setExpression(expression);
        return px;
    }

    /**
     * @param object
     * @param px
     * @param enclose
     * @return the java expresion
     */
    //protected
    protected String getJavaExpression(Object object, ParameterizedExpression px, boolean enclose) {
        if (object == null) {
            return null;
        } else if (object instanceof Entity) {
            Entity entity = (Entity) object;
            return getFullVariableName(entity, px);
        } else if (object instanceof Instance) {
            Instance instance = (Instance) object;
            return getDelimitedString(instance.getInstanceKeyValue());
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
        } else if (object instanceof Expression) {
            return getJavaExpression((Expression) object, px, enclose);
        } else if (object instanceof Artifact) {
            Artifact artifact = (Artifact) object;
            return getJavaQualifiedName(artifact);
        } else {
            return getJavaValue(object);
        }
    }

    /**
     * @param expression
     * @param px
     * @param enclose
     * @return the java expresion
     */
    //protected
    protected String getJavaExpression(Expression expression, ParameterizedExpression px, boolean enclose) {
        String string;
        if (expression == null) {
            return null;
        } else if (expression instanceof Property) {
            Property property = (Property) expression;
            return getFullVariableName(property, px);
        } else if (expression instanceof ComparisonX) {
            string = getJavaComparisonExpression((ComparisonX) expression, px);
        } else if (expression instanceof ConditionalX) {
            string = getJavaConditionalExpression((ConditionalX) expression, px);
        } else if (expression instanceof DataAggregateX) {
            string = getJavaDataAggregateExpression((DataAggregateX) expression, px);
        } else if (expression instanceof RowsAggregateX) {
            string = getJavaRowsAggregateExpression((RowsAggregateX) expression, px);
        } else if (expression instanceof OrderedPairX) {
            string = getJavaOrderedPairExpression((OrderedPairX) expression, px);
        } else if (expression instanceof ScalarX) {
            string = getJavaScalarExpression((ScalarX) expression, px);
        } else if (expression instanceof VariantX) {
            string = getJavaVariantExpression((VariantX) expression, px);
        } else {
            string = getJavaValue(expression);
        }
        return enclose ? StrUtils.encloseSqlExpression(string) : StrUtils.disclose(string);
    }

    /**
     * @param expression
     * @param px
     * @return the java expresion
     */
    //protected
    protected String getJavaComparisonExpression(ComparisonX expression, ParameterizedExpression px) {
        ComparisonOp operator = expression.getOperator();
        Object x = expression.getX();
        Object y = expression.getY();
        if (operator == null || x == null) {
            return null;
        }
        String arg1 = getJavaExpression(x, px, true);
        String arg2 = getJavaExpression(y, px, true);
        String pattern;
        switch (operator) {
            case IS_NULL:
            case IS_NOT_NULL:
            case IS_TRUE:
            case IS_FALSE:
            case IS_NULL_OR_TRUE:
            case IS_NULL_OR_FALSE:
                pattern = call(operator, 1);
                break;
            case EQ:
            case NEQ:
            case GT:
            case GTEQ:
            case LT:
            case LTEQ:
            case STARTS_WITH:
            case NOT_STARTS_WITH:
            case CONTAINS:
            case NOT_CONTAINS:
            case ENDS_WITH:
            case NOT_ENDS_WITH:
            case IS_NULL_OR_EQ:
            case IS_NULL_OR_NEQ:
            case IS_NULL_OR_GT:
            case IS_NULL_OR_GTEQ:
            case IS_NULL_OR_LT:
            case IS_NULL_OR_LTEQ:
            case IS_NULL_OR_STARTS_WITH:
            case IS_NULL_OR_NOT_STARTS_WITH:
            case IS_NULL_OR_CONTAINS:
            case IS_NULL_OR_NOT_CONTAINS:
            case IS_NULL_OR_ENDS_WITH:
            case IS_NULL_OR_NOT_ENDS_WITH:
                pattern = call(operator, 2);
                break;
            default:
                pattern = call(operator, y == null ? 1 : 2);
                break;
        }
        return format(pattern, arg1, arg2);
    }

    /**
     * @param expression
     * @param px
     * @return the java expresion
     */
    //protected
    protected String getJavaConditionalExpression(ConditionalX expression, ParameterizedExpression px) {
        Expression b = expression.getBooleanExpression();
        Object x = expression.getThenValue();
        Object y = expression.getElseValue();
        if (b == null || x == null) {
            return null;
        }
        String arg0 = getJavaExpression(b, px, true);
        String arg1 = getJavaExpression(x, px, true);
        String arg2 = getJavaExpression(y, px, true);
        String pattern = y == null ? "{0} ? {1} : null" : "{0} ? {1} : {2}";
        return format(pattern, arg0, arg1, arg2);
    }

    /**
     * @param expression
     * @param px
     * @return the java expresion
     */
    //protected
    protected String getJavaDataAggregateExpression(DataAggregateX expression, ParameterizedExpression px) {
        DataAggregateOp operator = expression.getOperator();
        Object[] operands = expression.getOperands();
        if (operator == null || operands == null || operands.length < 2) {
            return null;
        }
        String[] arguments = new String[operands.length];
        for (int i = 0; i < operands.length; i++) {
            arguments[i] = StrUtils.disclose(getJavaExpression(operands[i], px, true));
        }
        String string;
        switch (operator) {
            case COALESCE:
            case COUNT:
            case MAXIMUM:
            case MINIMUM:
            case AND:
            case NAND:
            case OR:
            case NOR:
            case NAXOR:
            case NAXNOR:
            case NOR_OR_NAXOR:
            case CONCAT:
            case SUM:
            case PRODUCT:
            case AVERAGE:
            default:
                string = call(operator, arguments);
                break;
        }
        return string;
    }

    /**
     * @param expression
     * @param px
     * @return the java expresion
     */
    //protected
    protected String getJavaRowsAggregateExpression(RowsAggregateX expression, ParameterizedExpression px) {
        String errmsg = EMPTY;
        if (expression == null) {
            return null;
        }
        errmsg += "failed to generate code for expression " + stringOf(expression);
        Entity declaringEntity = expression.getDeclaringEntity();
        if (declaringEntity == null) {
            logger.error(errmsg);
            return null;
        }
        errmsg += " at entity " + stringOf(declaringEntity);
        String function = getJavaExpressionSelectFunctionName(expression);
        if (function == null) {
            logger.error(errmsg);
            return null;
        }
        Entity dimension = expression.getDimension();
        Property argument = dimension instanceof Property ? (Property) dimension : null;
        String argname = argument == null ? EMPTY : getFullVariableName(argument, px);
        return function + LRB$ + argname + RRB$;
    }

    /**
     * @param expression
     * @param px
     * @return the java expresion
     */
    //protected
    protected String getJavaOrderedPairExpression(OrderedPairX expression, ParameterizedExpression px) {
        OrderedPairOp operator = expression.getOperator();
        Object x = expression.getX();
        Object y = expression.getY();
        if (operator == null || x == null || y == null) {
            return null;
        }
        String arg1 = getJavaExpression(x, px, true);
        String arg2 = getJavaExpression(y, px, true);
        String pattern;
        switch (operator) {
            case COALESCE:
            case NULLIF:
            case MAXIMUM:
            case MINIMUM:
            case AND:
            case NAND:
            case OR:
            case NOR:
            case XOR:
            case XNOR:
            case X_IMPLIES_Y:
            case CONCAT:
            case X_PLUS_Y:
            case X_MINUS_Y:
            case X_MULTIPLIED_BY_Y:
            case X_DIVIDED_INTO_Y:
            case X_RAISED_TO_THE_Y:
            default:
                arg1 = StrUtils.disclose(arg1);
                arg2 = StrUtils.disclose(arg2);
                pattern = call(operator, 2);
                break;
        }
        return format(pattern, arg1, arg2);
    }

    /**
     * @param expression
     * @param px
     * @return the java expresion
     */
    //protected
    protected String getJavaScalarExpression(ScalarX expression, ParameterizedExpression px) {
        ScalarOp operator = expression.getOperator();
        Object operand = expression.getOperand();
        if (operand == null) {
            return null;
        }
        String arg1 = getJavaExpression(operand, px, false);
        String arg2 = getJavaExpressionDefaultValue(expression);
        if (operator == null) {
            return arg1;
        }
        String pattern;
        switch (operator) {
            case DEFAULT_WHEN_NULL:
                pattern = call("coalesce", 2);
                break;
            case NULL_WHEN_DEFAULT:
                pattern = call("nullif", 2);
                break;
            case TO_BOOLEAN:
            case TO_CHARACTER:
            case TO_STRING:
            case TO_BYTE:
            case TO_SHORT:
            case TO_INTEGER:
            case TO_LONG:
            case TO_FLOAT:
            case TO_DOUBLE:
            case TO_BIG_INTEGER:
            case TO_BIG_DECIMAL:
            case TO_DATE:
            case TO_TIME:
            case TO_TIMESTAMP:
            case NOT:
            case LOWER:
            case UPPER:
            case CAPITALIZE:
            case UNCAPITALIZE:
            case TRIM:
            case LTRIM:
            case RTRIM:
            case MODULUS:
            case OPPOSITE:
            case RECIPROCAL:
            default:
                pattern = call(operator, 1);
                break;
        }
        return format(pattern, arg1, arg2);
    }

    /**
     * @param expression
     * @param px
     * @return the java expresion
     */
    //protected
    protected String getJavaVariantExpression(VariantX expression, ParameterizedExpression px) {
        String errmsg = EMPTY;
        if (expression == null) {
            return null;
        }
        errmsg += "failed to generate code for expression " + stringOf(expression);
        Entity declaringEntity = expression.getDeclaringEntity();
        if (declaringEntity == null) {
            logger.error(errmsg);
            return null;
        }
        errmsg += " at entity " + stringOf(declaringEntity);
        String function = getJavaExpressionFunctionName(expression);
        Property argument = expression.getExpressionFunctionArgument();
        if (function == null || argument == null) {
            logger.error(errmsg);
            return null;
        }
        boolean doubtful = declaringEntity.isRootInstance();
        if (doubtful) {
            Expression foreignExpression = expression.getForeignExpression();
            if (foreignExpression instanceof RowsAggregateX) {
                RowsAggregateX foreignRowsAggregateX = (RowsAggregateX) foreignExpression;
                String select = getJavaExpressionSelectFunctionName(foreignRowsAggregateX);
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
            return function + LRB$ + getFullVariableName(argument, px) + RRB$;
        }
    }

    /**
     * @param expression
     * @return the java expression function name
     */
    //@Override
    //public
    protected String getJavaExpressionFunctionName(Expression expression) {
        Entity e = expression.getDeclaringEntity();
        return e == null ? getJavaName(expression) : getJavaName(e.getRoot()) + "_" + getJavaName(expression);
    }

    /**
     * @param expression
     * @return the java expression select function name
     */
    //@Override
    //public
    protected String getJavaExpressionSelectFunctionName(Expression expression) {
        Entity declaringEntity = expression instanceof RowsAggregateX ? expression.getDeclaringEntity() : null;
        return declaringEntity == null ? getJavaName(expression)
            : (getJavaName(declaringEntity.getRoot()) + "_select_" + getJavaName(expression));
    }

    protected String stringOf(Expression e) {
        return e == null ? "?"
            : e.getName() != null ? e.getName()
                : e.getParentExpression() != null ? stringOf(e.getParentExpression()) + "[" + e.toString() + "]"
                    : e.toString();
    }

    protected String stringOf(Entity e) {
        return e == null ? "?"
            : e.getName() != null ? e.getName()
                : e.toString();
    }

    //protected
    protected String getJavaExpressionDefaultValue(Expression expression) {
        Class<?> clazz = expression == null ? null : expression.getDataType();
        if (clazz == null) {
            return "null";
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            return "false";
        } else if (Character.class.isAssignableFrom(clazz)) {
            return SQM$ + SPC$ + SQM$;
        } else if (String.class.isAssignableFrom(clazz)) {
            return DQM$ + DQM$;
        } else if (Byte.class.isAssignableFrom(clazz)) {
            return "0";
        } else if (Short.class.isAssignableFrom(clazz)) {
            return "0";
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return "0";
        } else if (Long.class.isAssignableFrom(clazz)) {
            return "0L";
        } else if (Float.class.isAssignableFrom(clazz)) {
            return "0.0F";
        } else if (Double.class.isAssignableFrom(clazz)) {
            return "0.0D";
        } else if (BigInteger.class.isAssignableFrom(clazz)) {
            return "BigInteger.ZERO";
        } else if (BigDecimal.class.isAssignableFrom(clazz)) {
            return "BigDecimal.ZERO";
        } else if (Date.class.isAssignableFrom(clazz)) {
            return "new Date(0)";
        } else if (Time.class.isAssignableFrom(clazz)) {
            return "new Time(0)";
        } else if (Timestamp.class.isAssignableFrom(clazz)) {
            return "new Timestamp(0)";
        } else {
            return "null";
        }
    }

    protected String getFullVariableName(Artifact artifact, ParameterizedExpression px) {
        if (artifact == null) {
            return null;
        }
        String name = RTL ? getPrefixedVariableName(artifact) : getSuffixedVariableName(artifact);
        //name = getJavaLowerVariableName(name);
        if (px != null) {
            px.getParametersMap().put(name, artifact);
        }
        return name;
    }

    protected String getPrefixedVariableName(Artifact artifact) {
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        String prefix = declaringArtifact == null ? "" : getPrefixedVariableName(declaringArtifact) + UND$;
        return prefix + getJavaVariableName(artifact);
    }

    protected String getSuffixedVariableName(Artifact artifact) {
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        String suffix = declaringArtifact == null ? "" : UND$ + getSuffixedVariableName(declaringArtifact);
        return getJavaVariableName(artifact) + suffix;
    }

    protected String getNamedValueName(NamedValue namedValue, ParameterizedExpression px) {
        String name = namedValue.name();
        if (px != null) {
            px.getNamedValuesMap().put(name, namedValue);
        }
        return name;
    }

    //protected
    protected String call(Operator operator, int arguments) {
        String function = operator.name().toLowerCase();
        return call(function, arguments);
    }

    //protected
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

    //protected
    protected String call(Operator operator, String... arguments) {
        String function = operator.name().toLowerCase();
        return call(function, arguments);
    }

    //protected
    protected String call(String function, String... arguments) {
        String method = function.contains(".") ? function : UTIL + DOT$ + StrUtils.getCamelCase(function, true);
        String string = arguments == null || arguments.length == 0 ? LRB$ + RRB$ : StrUtils.enclose(StringUtils.join(arguments, SEP$ + " "));
        return method + string;
    }
    // </editor-fold>

}
