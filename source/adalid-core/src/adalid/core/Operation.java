/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.ThrowableUtils;
import adalid.core.annotations.CastingField;
import adalid.core.annotations.OperationClass;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.OperationKind;
import adalid.core.enums.OperationLogging;
import adalid.core.enums.OperationType;
import adalid.core.exceptions.InstantiationRuntimeException;
import adalid.core.expressions.BooleanDataAggregateX;
import adalid.core.expressions.BooleanOrderedPairX;
import adalid.core.expressions.BooleanScalarX;
import adalid.core.expressions.XB;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.Check;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.State;
import adalid.core.operations.BasicDatabaseOperation;
import adalid.core.wrappers.OperationWrapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
@OperationClass
public abstract class Operation extends AbstractArtifact implements Comparable<Operation> {

    private static final Logger logger = Logger.getLogger(Operation.class);

    private static final String SELECT_OPERATION_KEY = "select";

    private static final String INSERT_OPERATION_KEY = "insert";

    private static final String UPDATE_OPERATION_KEY = "update";

    private static final String DELETE_OPERATION_KEY = "delete";

    public static String getSelectOperationKey() {
        return SELECT_OPERATION_KEY;
    }

    public static String getInsertOperationKey() {
        return INSERT_OPERATION_KEY;
    }

    public static String getUpdateOperationKey() {
        return UPDATE_OPERATION_KEY;
    }

    public static String getDeleteOperationKey() {
        return DELETE_OPERATION_KEY;
    }

    public static String[] getCrudOperationKeys() {
        return new String[]{SELECT_OPERATION_KEY, INSERT_OPERATION_KEY, UPDATE_OPERATION_KEY, DELETE_OPERATION_KEY};
    }

    // <editor-fold defaultstate="collapsed" desc="expression building public static methods">
    public static BooleanOrderedPairX and(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.and(operand1, operand2);
    }

    public static BooleanOrderedPairX or(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.or(operand1, operand2);
    }

    public static BooleanDataAggregateX and(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.and(operand1, operand2, extraOperands);
    }

    public static BooleanDataAggregateX or(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.or(operand1, operand2, extraOperands);
    }

    public static BooleanScalarX not(BooleanExpression x) {
        return x.not();
    }
    // </editor-fold>

    private boolean _initialised;

    private boolean _settled;

    private boolean _finalised;

    /**
     * annotated with OperationClass
     */
    private boolean _annotatedWithOperationClass;

    private final Map<String, Parameter> _parameters = new LinkedHashMap<>();

    private final Map<String, Expression> _expressions = new LinkedHashMap<>();

    private final Map<String, Transition> _transitions = new LinkedHashMap<>();

    private OperationType _operationType;

    private OperationKind _operationKind;

    private OperationAccess _operationAccess;

    private boolean _asynchronous;

    private OperationLogging _operationLogging;

    /**
     * @return the initialised indicator
     */
    public boolean isInitialised() {
        return _initialised;
    }

    /**
     * @return the settled indicator
     */
    public boolean isSettled() {
        return _settled;
    }

    /**
     * @return the finalised indicator
     */
    public boolean isFinalised() {
        return _finalised;
    }

    /**
     * @return the OperationClass annotation indicator
     */
    public boolean isAnnotatedWithOperationClass() {
        return _annotatedWithOperationClass;
    }

    /**
     * @return the proper name
     */
    public String getProperName() {
        return getName();
    }

    /**
     * @return the parameters list
     */
    public List<Parameter> getParametersList() {
        List<Parameter> list = new ArrayList<>();
        for (Parameter value : _parameters.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the instance parameter (if any)
     */
    public Parameter getInstanceParameter() {
        if (OperationKind.INSTANCE.equals(_operationKind)) {
            List<Parameter> parameters = getParametersList();
            if (parameters != null && !parameters.isEmpty()) {
                for (Parameter parameter : parameters) {
                    if (parameter.isInstanceReferenceField()) {
                        return parameter;
                    }
                }
//              Entity declaringEntity = getDeclaringEntity();
//              Class<?> declaringClass = declaringEntity == null ? null : declaringEntity.getDataType();
//              if (declaringClass != null) {
//                  for (Parameter parameter : parameters) {
//                      if (declaringClass.isAssignableFrom(parameter.getDataType())) {
//                          return parameter;
//                      }
//                  }
//              }
            }
        }
        return null;
    }

    /**
     * @return the expressions
     */
    public List<Expression> getExpressionsList() {
        List<Expression> list = new ArrayList<>();
        for (Expression value : _expressions.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    public List<State> getInitialStatesList() {
        List<State> list = new ArrayList<>();
        State state;
        for (Transition transition : _transitions.values()) {
            if (transition != null) {
                state = transition.getX();
                if (state != null && !list.contains(state)) {
                    list.add(state);
                }
            }
        }
        return list;
    }

    public List<State> getFinalStatesList() {
        List<State> list = new ArrayList<>();
        State state;
        for (Transition transition : _transitions.values()) {
            if (transition != null) {
                state = transition.getY();
                if (state != null && !list.contains(state)) {
                    list.add(state);
                }
            }
        }
        return list;
    }

    public List<Expression> getChecksList() {
        Field field;
        Class<?> clazz;
        List<Expression> list = new ArrayList<>();
        for (Expression expression : _expressions.values()) {
            if (expression instanceof Check) {
                field = expression.getDeclaringField();
                clazz = field.getType();
                if (Check.class.isAssignableFrom(clazz)) {
                    list.add(expression);
                }
            }
        }
        return list;
    }

    /**
     * @return the transitions
     */
    public List<Transition> getTransitionsList() {
        List<Transition> list = new ArrayList<>();
        for (Transition value : _transitions.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    public List<Property> getCheckReferencedColumnsList() {
        Field field;
        Class<?> clazz;
        List<Property> list = new ArrayList<>();
        Check check;
        for (Expression expression : _expressions.values()) {
            if (expression instanceof Check) {
                field = expression.getDeclaringField();
                clazz = field.getType();
                if (Check.class.isAssignableFrom(clazz)) {
                    check = (Check) expression;
                    list.addAll(check.getReferencedColumnsList());
                }
            }
        }
        return list;
    }

    /**
     * @return the parameters map
     */
    public Map<String, Parameter> getParametersMap() {
        return _parameters;
    }

    /**
     * @return the expressions
     */
    public Map<String, Expression> getExpressionsMap() {
        return _expressions;
    }

    /**
     * @return the transitions
     */
    public Map<String, Transition> getTransitionsMap() {
        return _transitions;
    }

    /**
     * @return the triggers
     */
    public Map<String, Trigger> getTriggersMap() {
        Entity entity = getDeclaringEntity();
        return entity.getTriggersMap();
    }

    /**
     * @return the operation type
     */
    public OperationType getOperationType() {
        return _operationType;
    }

    /**
     * @return the kind of operation
     */
    public OperationKind getOperationKind() {
        return _operationKind;
    }

    void setOperationKind(OperationKind operationKind) {
        _operationKind = operationKind;
    }

    /**
     * @return the access type
     */
    public OperationAccess getOperationAccess() {
        return _operationAccess;
    }

    void setOperationAccess(OperationAccess operationAccess) {
        _operationAccess = operationAccess;
    }

    /**
     * @return the asynchronous execution indicator
     */
    public boolean isAsynchronous() {
        return _asynchronous;
    }

    /**
     * @return the operation logging mode
     */
    public OperationLogging getOperationLogging() {
        return _operationLogging;
    }

    void setOperationLogging(OperationLogging operationLogging) {
        _operationLogging = operationLogging;
    }

    public Operation() {
        super();
        initOperation();
        Artifact declaringArtifact = TLC.getDeclaringArtifact();
        if (declaringArtifact == null) {
            track("allocate");
            initialise(false);
        } else {
            setDeclaringArtifact(declaringArtifact);
            track("allocate", declaringArtifact);
            initialise(true);
        }
    }

    public Operation(Artifact declaringArtifact) {
        super();
        initOperation();
        if (declaringArtifact == null) {
            String msg = "null declaring artifact";
            throw new IllegalArgumentException(msg);
        } else {
            setDeclaringArtifact(declaringArtifact);
            track("allocate", declaringArtifact);
            initialise(true);
        }
    }

    private void initOperation() {
        _operationType = this instanceof BasicDatabaseOperation ? OperationType.CRUD
            : this instanceof ExportOperation ? OperationType.EXPORT
                : this instanceof ReportOperation ? OperationType.REPORT
                    : this instanceof ProcedureOperation ? OperationType.PROCEDURE
                        : this instanceof ProcessOperation ? OperationType.PROCESS
                            : OperationType.UNSPECIFIED;
    }

    void initialise() {
        initialise(false);
    }

    private void initialise(boolean b) {
        if (_initialised) {
            return;
        }
        _initialised = true;
        initialiseFields(Parameter.class);
        initialiseFields(Expression.class);
    }

    void settle() {
        if (_settled) {
            return;
        }
        _settled = true;
        settleAttributes();
        settleParameters();
        settleExpressions();
        verifyNames(Operation.class, Expression.class);
        settleFilters();
//      verifyNames(Operation.class);
    }

    // <editor-fold defaultstate="collapsed" desc="settle">
    protected void settleAttributes() {
        track("settleAttributes");
    }

    protected void settleParameters() {
        track("settleParameters");
    }

    protected void settleExpressions() {
        track("settleExpressions");
    }

    protected void settleFilters() {
        track("settleFilters");
    }
    // </editor-fold>

    void finalise() {
        if (_finalised) {
            return;
        }
        _finalised = true;
        track("finalise");
        finaliseFields();
        checkExpressions();
    }

    // <editor-fold defaultstate="collapsed" desc="initialiseFields">
    void initialiseFields(Class<?> clazz) {
        Class<?> c;
        int d, r;
        String name;
        Class<?> type;
        int modifiers;
        boolean restricted;
        Object o;
        int depth = depth();
        int round = round();
        Class<?>[] classes = new Class<?>[]{
            Parameter.class, Expression.class
        };
        Class<?> dac = getClass();
        Class<?> top = Operation.class;
        int i = ArrayUtils.indexOf(classes, clazz);
        if (i != ArrayUtils.INDEX_NOT_FOUND) {
            c = classes[i];
            for (Field field : XS1.getFields(dac, top)) {
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
                if (!c.isAssignableFrom(type)) {
                    continue;
                }
                modifiers = type.getModifiers();
                if (type.isInterface() && Expression.class.isAssignableFrom(type)) {
                    restricted = false;
                } else {
                    restricted = Modifier.isAbstract(modifiers);
                }
                restricted = restricted || !Modifier.isPublic(modifiers);
                if (restricted) {
                    continue;
                }
                modifiers = field.getModifiers();
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                String errmsg = "failed to create a new instance of field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o == null) {
                        logger.debug(message(type, name, o, depth, round));
                        o = XS1.initialiseField(this, field);
                        if (o == null) {
                            logger.debug(message(type, name, o, depth, round));
//                          throw new RuntimeException(message(type, name, o, depth, round));
                        } else {
                            logger.debug(message(type, name, o, depth, round));
                            field.set(this, o);
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    throw new InstantiationRuntimeException(errmsg, ex);
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseFields">
    private void finaliseFields() {
        String name;
        Class<?> type;
        int modifiers;
        boolean restricted;
        Class<?>[] classes = new Class<?>[]{
            Parameter.class, Expression.class
        };
        Object o;
        int depth = depth();
        int round = round();
        for (Class<?> c : classes) {
            for (Field field : XS1.getFields(getClass(), Operation.class)) { // getClass().getDeclaredFields()
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
                if (!c.isAssignableFrom(type)) {
                    continue;
                }
                modifiers = field.getModifiers();
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                String errmsg = "failed to initialize field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o == null) {
                        logger.debug(message(type, name, o, depth, round));
                    } else if (o instanceof Parameter) {
                        finaliseParameter(field, (Parameter) o);
                    } else if (o instanceof Expression) {
                        finaliseExpression(field, (Expression) o);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    TLC.getProject().getParser().increaseErrorCount();
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="finaliseParameter">
    private void finaliseParameter(Field field, Parameter parameter) {
        if (field == null || parameter == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _parameters.containsKey(key)) {
            return;
        }
        if (field.isAnnotationPresent(CastingField.class)) {
            if (parameter instanceof AbstractDataArtifact) {
                AbstractDataArtifact artifact = (AbstractDataArtifact) parameter;
                artifact.annotate(field);
            }
            return;
        }
        if (parameter.isNotDeclared()) {
//          parameter.setDeclared(key, this, field);
            XS1.declare(parameter, this, field);
        }
        if (parameter instanceof Entity) {
            Entity entity = (Entity) parameter;
            if (!entity.isFinalised()) {
                entity.finalise();
            }
            Entity root = entity.getRoot();
            if (root != null) {
                root.getParameterReferencesMap().put(parameter.getPathString(), parameter);
            }
        }
        _parameters.put(key, parameter);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseExpression">
    private void finaliseExpression(Field field, Expression expression) {
        if (field == null || expression == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _expressions.containsKey(key)) {
            return;
        }
        if (expression.isNotDeclared()) {
//          expression.setDeclared(key, this, field);
            XS1.declare(expression, this, field);
        }
//      if (!expression.isFinalised()) {
//          expression.finalise();
//      }
        _expressions.put(key, expression);
    }
    // </editor-fold>
    // </editor-fold>

    private void checkExpressions() {
        Object o;
        Expression e;
        for (Parameter parameter : _parameters.values()) {
            e = parameter.getRenderingFilter();
            if (e != null) {
                verifyExpression(e);
            }
            e = parameter.getRequiringFilter();
            if (e != null) {
                verifyExpression(e);
            }
            if (parameter instanceof Entity) {
                Entity entity = (Entity) parameter;
                e = entity.getSearchQueryFilter();
                if (e != null) {
                    verifyExpression(e);
                }
            }
            o = parameter.getInitialValue();
            if (o instanceof Expression) {
                e = (Expression) o;
                verifyExpression(e);
            }
            o = parameter.getDefaultValue();
            if (o instanceof Expression) {
                e = (Expression) o;
                verifyExpression(e);
            }
            o = parameter.getCurrentValue();
            if (o instanceof Expression) {
                e = (Expression) o;
                verifyExpression(e);
            }
        }
        for (Expression expression : _expressions.values()) {
            if (expression != null) {
                verifyExpression(expression);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithOperationClass = false;
        _operationKind = OperationKind.CLASS;
        _operationAccess = OperationAccess.UNSPECIFIED;
        _asynchronous = false;
        _operationLogging = OperationLogging.UNSPECIFIED;
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateOperationClass(type);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(OperationClass.class);
        return valid;
    }

    private void annotateOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, OperationClass.class);
        if (annotatedClass != null) {
            OperationClass annotation = annotatedClass.getAnnotation(OperationClass.class);
            if (annotation != null) {
//              _operationKind = annotation.kind();
                _operationAccess = annotation.access();
                _asynchronous = annotation.asynchronous().toBoolean(_asynchronous);
                _operationLogging = annotation.logging();
                _annotatedWithOperationClass = true;
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(Operation o) {
        Operation that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
    }
    // </editor-fold>

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends OperationWrapper> getDefaultWrapperClass() {
        return OperationWrapper.class;
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            string += fee + tab + "parameters" + faa + _parameters.size() + foo;
            string += fee + tab + "expressions" + faa + _expressions.size() + foo;
            string += fee + tab + "transitions" + faa + _transitions.size() + foo;
            if (verbose) {
            }
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            for (String clave : _parameters.keySet()) {
                Parameter valor = _parameters.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
            for (String clave : _expressions.keySet()) {
                Expression valor = _expressions.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
            for (String clave : _transitions.keySet()) {
                Transition valor = _transitions.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
        }
        return string;
    }
    // </editor-fold>

    private String message(Class<?> type, String name, Object value, int depth, int round) {
        String s1 = StringUtils.repeat(" ", 0 + 4 * depth);
        String s2 = this + "," + depth + "," + round;
        String s3 = type.getSimpleName() + " " + name + "=" + value;
        String s4 = s1 + s2 + " " + s3;
        return s4;
    }

    private void track(String method) {
        track(method, new Object[]{this});
    }

    private void track(String method, Artifact artifact) {
        track(method, this, artifact);
    }

    private void track(String method, Object... parameters) {
        TLC.getProject().getParser().track(depth(), round(), getClassPath(), method, parameters);
    }

}
