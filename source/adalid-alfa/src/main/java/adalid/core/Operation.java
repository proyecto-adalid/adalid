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
package adalid.core;

import adalid.commons.util.*;
import adalid.core.annotations.*;
import adalid.core.comparators.*;
import adalid.core.constants.*;
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.exceptions.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.operations.*;
import adalid.core.wrappers.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
@OperationClass
public abstract class Operation extends AbstractArtifact implements Comparable<Operation>, EntityReferenceContainer {

    private static final Logger logger = Logger.getLogger(Operation.class);

    private static final String EOL = "\n";

    private static final String SELECT_OPERATION_KEY = "select";

    private static final String INSERT_OPERATION_KEY = "insert";

    private static final String UPDATE_OPERATION_KEY = "update";

    private static final String DELETE_OPERATION_KEY = "delete";

    private static final String[] RESERVED = {
        "ADA", "ADR", "ADX", "AIA", "AIR", "AIS", "AIX", "AIY", "AUA", "AUR", "AUS", "AUX", "AUY", "AV1", "AV2",
        "BDR", "BDX", "BIR", "BIX", "BIZ", "BUR", "BUX",
        "CK1", "CV1", "CX1",
        "DV1", "DV2",
        "JOB",
        "LOG", "LOX",
        "SQL",
        "VAL"
    };

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

    private static boolean getBusinessOperationConfirmationRequired(boolean unspecified) {
        Project project = TLC.getProject();
        return project != null && project.getBusinessOperationConfirmationRequired().toBoolean(unspecified);
    }

    // <editor-fold defaultstate="collapsed" desc="expression building protected static methods">
    /**
     * La conjunción (AND) es un operador lógico que resulta en verdadero si ambos operandos son verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si ambos operandos son verdadero; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX and(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.and(operand1, operand2);
    }

    /**
     * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si uno de los operandos es falso. Es equivalente a la negación
     * (NOT) de la conjunción (AND).
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si uno de los operandos es falso; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX nand(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.nand(operand1, operand2);
    }

    /**
     * La disyunción (OR) es un operador lógico que resulta en verdadero si uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX or(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.or(operand1, operand2);
    }

    /**
     * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si ambos operandos son falso. Es equivalente a la negación (NOT) de
     * la disyunción (OR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si ambos operandos son falso; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX nor(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.nor(operand1, operand2);
    }

    /**
     * La disyunción exclusiva (XOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si uno y solo uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX xor(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.xor(operand1, operand2);
    }

    /**
     * La equivalencia (XNOR) es un operador lógico que resulta en verdadero si ambos operandos son verdadero ó si ambos son falso. Es equivalente a
     * la negación (NOT) de la disyunción exclusiva (XOR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si ambos operandos son verdadero ó si ambos son falso; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX xnor(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.xnor(operand1, operand2);
    }

    /**
     * La condicional material (X_IMPLIES_Y) es un operador lógico que resulta en falso solo si X es verdadero y Y es falso. Es equivalente a la
     * disyunción (OR) de la negación (NOT) de X con Y (NOT X OR Y).
     *
     * @param operandX X
     * @param operandY Y
     * @return falso solo si X es verdadero y Y es falso; de lo contrario, verdadero.
     */
    protected static BooleanOrderedPairX xImpliesY(BooleanExpression operandX, BooleanExpression operandY) {
        return XB.Boolean.OrderedPair.xImpliesY(operandX, operandY);
    }

    /**
     * La conjunción (AND) es un operador lógico que resulta en verdadero si todos los operandos son verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si todos los operandos son verdadero; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX and(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.and(operand1, operand2, extraOperands);
    }

    /**
     * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si al menos uno de los operandos es falso. Es equivalente a la
     * negación (NOT) de la conjunción (AND).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si al menos uno de los operandos es falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX nand(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.nand(operand1, operand2, extraOperands);
    }

    /**
     * La disyunción (OR) es un operador lógico que resulta en verdadero si al menos uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si al menos uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX or(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.or(operand1, operand2, extraOperands);
    }

    /**
     * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si todos los operandos son falso. Es equivalente a la negación (NOT)
     * de la disyunción (OR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si todos los operandos son falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX nor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.nor(operand1, operand2, extraOperands);
    }

    /**
     * La disyunción exclusiva no-asociativa (NAXOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si uno y solo uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX naxor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.naxor(operand1, operand2, extraOperands);
    }

    /**
     * La equivalencia no-asociativa (NAXNOR) es un operador lógico que resulta en verdadero si todos los operandos son verdadero ó si todos son
     * falso. Es equivalente a la negación (NOT) de la disyunción exclusiva no-asociativa (NAXOR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si todos los operandos son verdadero ó si todos son falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX naxnor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.naxnor(operand1, operand2, extraOperands);
    }

    /**
     * NOR_OR_NAXOR es un operador lógico compuesto que resulta en verdadero si uno y solo uno de los operandos es verdadero ó si todos son falso. Es
     * equivalente a la disyunción (OR) de la negación conjunta (NOR) y la disyunción exclusiva no-asociativa (NAXOR). Con solo dos operandos también
     * es equivalente a la negación alternativa (NAND).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si uno y solo uno de los operandos es verdadero ó si todos son falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX norOrNaxor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.norOrNaxor(operand1, operand2, extraOperands);
    }

    /**
     * La negación (NOT), también llamada complemento lógico, es un operador lógico que resulta en verdadero si el operando es falso, y viceversa.
     *
     * @param x operando X
     * @return verdadero si el operando es falso, y viceversa.
     */
    protected static BooleanScalarX not(BooleanExpression x) {
        return x.not();
    }
    // </editor-fold>

    private boolean _initialised;
//
//  private boolean _prepared;

    private boolean _settled;

    private char _settler = '?';

    /**
     * annotated with OperationClass
     */
    private boolean _annotatedWithOperationClass;

    /**
     * annotated with OperationDocGen
     */
    private boolean _annotatedWithOperationDocGen;

    private final Map<String, Parameter> _parameters = new LinkedHashMap<>();

    private final Map<String, Expression> _expressions = new LinkedHashMap<>();

    private final Map<String, Transition> _transitions = new LinkedHashMap<>();

    private OperationType _operationType;

    private OperationKind _operationKind = OperationKind.CLASS;

    private OperationAccess _operationAccess = OperationAccess.UNSPECIFIED;

    private Kleenean _asynchronous = Kleenean.UNSPECIFIED;

    private Kleenean _shellEnabled = Kleenean.UNSPECIFIED;

    private boolean _confirmationRequired = getBusinessOperationConfirmationRequired(false);

    private boolean _complex;

    int _dialogSize = -1; // menor que 0 equivale al valor predeterminado

    private OperationLogging _operationLogging = OperationLogging.UNSPECIFIED;

    private boolean _operationActivityDiagramGenEnabled = true;

    private String _confirmationIconClass;

    private final Map<Locale, String> _localizedConfirmationMessage = new LinkedHashMap<>();

    private final Map<Locale, String> _localizedSuccessMessage = new LinkedHashMap<>();

    /**
     * @return the initialised indicator
     */
    public boolean isInitialised() {
        return _initialised;
    }
//
//  /**
//   * @return the prepared indicator
//   */
//  public boolean isPrepared() {
//      return _prepared;
//  }

    /**
     * @return the settled indicator
     */
    public boolean isSettled() {
        return _settled;
    }

    /**
     * @return the settler
     */
    @Override
    protected char settler() {
        return _settler;
    }

    /**
     * @return the OperationClass annotation indicator
     */
    public boolean isAnnotatedWithOperationClass() {
        return _annotatedWithOperationClass;
    }

    /**
     * @return the OperationDocGen annotation indicator
     */
    public boolean isAnnotatedWithOperationDocGen() {
        return _annotatedWithOperationDocGen;
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
            }
        }
        return null;
    }

    public boolean isInstanceOperation() {
        return getInstanceParameter() != null;
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

    public List<Check> getChecksList() {
        Field field;
        Class<?> clazz;
        List<Check> list = new ArrayList<>();
        for (Expression expression : _expressions.values()) {
            if (expression instanceof Check check) {
                field = expression.getDeclaringField();
                clazz = field.getType();
                if (Check.class.isAssignableFrom(clazz)) {
                    list.add(check);
                }
            }
        }
        return list;
    }

    public List<Check> getDatabaseTriggerChecksList() {
        Field field;
        Class<?> clazz;
        List<Check> list = new ArrayList<>();
        for (Expression expression : _expressions.values()) {
            if (expression instanceof Check check) {
                field = expression.getDeclaringField();
                clazz = field.getType();
                if (Check.class.isAssignableFrom(clazz)) {
                    if (Checkpoint.USER_INTERFACE.equals(check.getCheckpoint())) {
                        continue;
                    }
                    list.add(check);
                }
            }
        }
        return list;
    }

    public List<Check> getUserInterfaceChecksList() {
        Field field;
        Class<?> clazz;
        List<Check> list = new ArrayList<>();
        for (Expression expression : _expressions.values()) {
            if (expression instanceof Check check) {
                field = expression.getDeclaringField();
                clazz = field.getType();
                if (Check.class.isAssignableFrom(clazz)) {
                    if (Checkpoint.DATABASE_TRIGGER.equals(check.getCheckpoint())) {
                        continue;
                    }
                    list.add(check);
                }
            }
        }
        return list;
    }

    public List<Check> getInstanceParameterChecksList() {
        Field field;
        Class<?> clazz;
        List<Check> list = new ArrayList<>();
        Parameter instanceParameter = getInstanceParameter();
        if (instanceParameter != null) {
            for (Expression expression : _expressions.values()) {
                if (expression instanceof Check check) {
                    field = expression.getDeclaringField();
                    clazz = field.getType();
                    if (Check.class.isAssignableFrom(clazz)) {
                        if (Checkpoint.DATABASE_TRIGGER.equals(check.getCheckpoint())) {
                            continue;
                        }
                        if (isSingleParameterExpression(instanceParameter, expression)) {
                            list.add(check);
                        }
                    }
                }
            }
        }
        return list;
    }

    private boolean isSingleParameterExpression(Parameter parameter, Expression expression) {
        for (Object operand : expression.getOperands()) {
            if (operand instanceof Property property) {
                if (!isPropertyWithinParameterScope(parameter, property)) {
                    return false;
                }
            } else if (operand instanceof Expression e) {
                if (!isSingleParameterExpression(parameter, e)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPropertyWithinParameterScope(Parameter parameter, Property property) {
        List<Artifact> pathList = property.getPropertyParameterPathList();
        return pathList != null && !pathList.isEmpty() && parameter.equals(pathList.get(0));
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
        List<Property> list = new ArrayList<>();
        for (Expression expression : getChecksList()) {
            list.addAll(expression.getReferencedColumnsList());
        }
        return list;
    }

    public List<Property> getDatabaseTriggerCheckReferencedColumnsList() {
        List<Property> list = new ArrayList<>();
        for (Expression expression : getDatabaseTriggerChecksList()) {
            list.addAll(expression.getReferencedColumnsList());
        }
        return list;
    }

    public List<Property> getUserInterfaceCheckReferencedColumnsList() {
        List<Property> list = new ArrayList<>();
        for (Expression expression : getUserInterfaceChecksList()) {
            list.addAll(expression.getReferencedColumnsList());
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

    protected void setOperationKind(OperationKind operationKind) {
        _operationKind = operationKind;
    }

    /**
     * @return the access type
     */
    public OperationAccess getOperationAccess() {
        return _operationAccess;
    }

    public void setOperationAccess(OperationAccess operationAccess) {
        _operationAccess = operationAccess;
    }

    /**
     * @return the asynchronous execution indicator
     */
    public boolean isAsynchronous() {
        return _asynchronous.toBoolean(!implicitlySynchronous());
    }

    boolean implicitlySynchronous() {
        return OperationKind.INSTANCE.equals(_operationKind);
    }

    /**
     * @return the shell enabled indicator
     */
    public Kleenean getShellEnabled() {
        return isAsynchronous() ? _shellEnabled : Kleenean.FALSE;
    }

    /**
     * @return the confirmation required indicator
     */
    public boolean isConfirmationRequired() {
        return _confirmationRequired;
    }

    /**
     * @return the complex operation indicator
     */
    public boolean isComplex() {
        return !isSimple();
    }

    /**
     * @return the simple operation indicator
     */
    public boolean isSimple() {
        if (_complex) { // || _confirmationRequired until 25/04/2022
            return false;
        }
        for (Parameter parameter : getParametersList()) {
            if (parameter.isInstanceReferenceField()) {
                continue;
            }
            if (parameter.isRequiredParameter()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the constructor operation indicator
     */
    public boolean isConstructor() {
        return false;
    }

    /**
     * @return the self-constructor operation indicator
     */
    public boolean isSelfConstructor() {
        return this instanceof InsertOperation;
    }

    /**
     * @return the operation logging mode
     */
    public OperationLogging getOperationLogging() {
        return _operationLogging;
    }

    public void setOperationLogging(OperationLogging operationLogging) {
        _operationLogging = operationLogging;
    }

    /**
     * @return the operation dialog size
     */
    public int getDialogSize() {
        return _dialogSize < 0 || _dialogSize > 100 ? 100 : _dialogSize;
    }

    public void setDialogSize(int percentage) {
        _dialogSize = percentage; // Math.min(100, Math.max(0, percentage));
    }

    /**
     * @return the operation activity diagram generation indicator
     */
    public boolean isOperationActivityDiagramGenEnabled() {
        return _operationActivityDiagramGenEnabled;
    }

    /**
     * @return the confirmation icon class
     */
    public String getConfirmationIconClass() {
        return _confirmationIconClass;
    }

    /**
     * El método setConfirmationIconClass se utiliza para establecer la clase del icono de confirmación de la operación.
     *
     * @param iconClass clase del icono que se muestra en el diálogo de confirmación
     */
    public void setConfirmationIconClass(String iconClass) {
        _confirmationIconClass = iconClass;
    }

    /**
     * @return the default confirmation message
     */
    public String getDefaultConfirmationMessage() {
        return getLocalizedConfirmationMessage(null);
    }

    /**
     * El método setDefaultConfirmationMessage se utiliza para establecer el mensaje de confirmación de la operación que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el mensaje.
     *
     * @param message mensaje que se muestra en el diálogo de confirmación
     */
    public void setDefaultConfirmationMessage(String message) {
        setLocalizedConfirmationMessage(null, message);
    }

    /**
     * @param locale the locale for the confirmation message
     * @return the localized confirmation message
     */
    public String getLocalizedConfirmationMessage(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedConfirmationMessage.get(l);
    }

    /**
     * El método setLocalizedConfirmationMessage se utiliza para establecer el mensaje de confirmación de la operación que se almacena en el archivo
     * de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el mensaje.
     *
     * @param locale configuración regional
     * @param message mensaje que se muestra en el diálogo de confirmación
     */
    public void setLocalizedConfirmationMessage(Locale locale, String message) {
        Locale l = localeWritingKey(locale);
        if (message == null) {
            _localizedConfirmationMessage.remove(l);
        } else {
            _localizedConfirmationMessage.put(l, message);
        }
    }

    public boolean isLocalizedConfirmationMessageDefined() {
        return !_localizedConfirmationMessage.isEmpty();
    }

    /**
     * @return the default success message
     */
    public String getDefaultSuccessMessage() {
        return getLocalizedSuccessMessage(null);
    }

    /**
     * El método setDefaultSuccessMessage se utiliza para establecer el mensaje de éxito de la operación que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el mensaje.
     *
     * @param message mensaje que se muestra si la operación se ejecuta exitosamente
     */
    public void setDefaultSuccessMessage(String message) {
        setLocalizedSuccessMessage(null, message);
    }

    /**
     * @param locale the locale for the success message
     * @return the localized success message
     */
    public String getLocalizedSuccessMessage(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedSuccessMessage.get(l);
    }

    /**
     * El método setLocalizedSuccessMessage se utiliza para establecer el mensaje de éxito de la operación que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el mensaje.
     *
     * @param locale configuración regional
     * @param message mensaje que se muestra si la operación se ejecuta exitosamente
     */
    public void setLocalizedSuccessMessage(Locale locale, String message) {
        Locale l = localeWritingKey(locale);
        if (message == null) {
            _localizedSuccessMessage.remove(l);
        } else {
            _localizedSuccessMessage.put(l, message);
        }
    }

    public boolean isLocalizedSuccessMessageDefined() {
        return !_localizedSuccessMessage.isEmpty();
    }

    public Operation() {
        super();
        initOperation();
        Artifact declaringArtifact = TLC.getDeclaringArtifact();
        Field declaringField = TLC.getDeclaringField();
        if (declaringArtifact == null || declaringField == null) {
            track("allocate");
            initialise(false);
        } else {
            initOperation(declaringArtifact, declaringField);
        }
    }

    /* commented since 20200203
    public Operation(Artifact declaringArtifact, Field declaringField) {
        super();
        initOperation();
        if (declaringArtifact == null) {
            String msg = "null declaring artifact";
            throw new IllegalArgumentException(msg);
        } else if (declaringField == null) {
            String msg = "null declaring field";
            throw new IllegalArgumentException(msg);
        } else {
            initOperation(declaringArtifact, declaringField);
        }
    }

    /**/
    private void initOperation() {
        _operationType = this instanceof BasicDatabaseOperation ? OperationType.CRUD
            : this instanceof ExportOperation ? OperationType.EXPORT
                : this instanceof ReportOperation ? OperationType.REPORT
                    : this instanceof ProcedureOperation ? OperationType.PROCEDURE
                        : this instanceof ProcessOperation ? OperationType.PROCESS
                            : OperationType.UNSPECIFIED;
    }

    private void initOperation(Artifact declaringArtifact, Field declaringField) {
        setDeclaringArtifact(declaringArtifact);
        setDeclaringField(declaringField);
        track("allocate", declaringArtifact, declaringField);
        initialise(true);
    }

    private void initialise(boolean b) {
        track("initialise", b);
        if (_initialised) {
            logger.warn(getFullName() + " already initialised! ");
            Project.increaseParserWarningCount();
            return;
        }
        _initialised = true;
        addAllocationStrings();
        logAllocationStrings(Project.getDetailLevel());
        initialiseFields(Parameter.class);
        initialiseFields(Expression.class);
    }

    private final Set<String> _allocationStrings = new LinkedHashSet<>();

    @Override
    public Set<String> getAllocationStrings() {
        return _allocationStrings;
    }

    protected void addAllocationStrings() {
        track("addAllocationStrings");
        String name;
        Class<?> type;
        int modifiers;
        boolean restricted;
        for (Field field : XS1.getFields(getClass(), Operation.class, Entity.class)) {
            name = field.getName();
            type = field.getType();
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
            Project project = TLC.getProject();
            if (project != null) {
                Entity entity = project.getEntity(type);
                if (entity != null) {
                    Set<String> strings = entity.getAllocationStrings();
                    for (String string : strings) {
                        addAllocationStrings(name + "." + StringUtils.substringAfter(string, "."));
                    }
                }
            }
        }
    }

    /**
     * El método addAllocationStrings de la meta-operación se utiliza para establecer sus cadenas de asignación (allocation strings). Las cadenas de
     * asignación permiten especificar meta-entidades referenciadas por expresiones de la meta-operación, y que, por lo tanto, deben ser construidas
     * (instanciadas) al generar la aplicación.
     *
     * @param strings una o más cadenas de asignación (allocation strings). Cada cadena de asignación es una lista de referencias (meta-parámetros que
     * hacen referencia a otras meta-entidades) separadas por puntos (siguiendo la notación de puntos de Java), comenzando por uno de los meta
     * parámetros definidos en la meta-operación.
     */
    protected void addAllocationStrings(String... strings) {
        String name = getFullName();
        if (strings != null && strings.length > 0) {
            for (String string : strings) {
                if (StringUtils.isNotBlank(string)) {
                    _allocationStrings.add(name + "." + string);
                }
            }
        }
    }

    private void logAllocationStrings(Level level) {
        if (Level.OFF.equals(level) || Level.ALL.equals(level)) {
        } else if (_allocationStrings != null && !_allocationStrings.isEmpty()) {
            logger.log(level, getFullName() + ".logAllocationStrings(" + depth() + "/" + round() + ")");
            for (String string : _allocationStrings) {
                logger.log(level, string);
            }
        }
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
        verifyParameters();
        _settler = '?';
        localize();
    }

    private void verifyParameters() {
        List<Parameter> list = getParametersList();
        for (Parameter parameter : list) {
            if (!parameter.isAnnotated()) {
                logger.warn("parameter " + getFullName() + " not annotated yet");
                Project.increaseParserWarningCount();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="settle">
    protected void settleAttributes() {
        track("settleAttributes");
        _settler = 'A';
    }

    protected void settleParameters() {
        track("settleParameters");
        _settler = 'P';
    }

    protected void settleExpressions() {
        track("settleExpressions");
        _settler = 'X';
    }

    protected void settleFilters() {
        track("settleFilters");
        _settler = 'F';
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="localize">
    protected void localize() {
        localizeAttributes();
        localizeParameters();
        localizeExpressions();
        localizeFilters();
    }

    protected void localizeAttributes() {
        track("localizeAttributes");
    }

    protected void localizeParameters() {
        track("localizeParameters");
    }

    protected void localizeExpressions() {
        track("localizeExpressions");
    }

    protected void localizeFilters() {
        track("localizeFilters");
    }
    // </editor-fold>

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            finaliseFields();
            finaliseParameters();
            checkExpressions();
            checkTransitions();
            setParametersDisplaySortKey();
        }
        return ok;
    }

    @Override
    public boolean finish() {
        boolean ok = super.finish();
        if (ok) {
            finishParameters();
        }
        return ok;
    }

    // <editor-fold defaultstate="collapsed" desc="initialiseFields">
    void initialiseFields(Class<?> clazz) {
        Class<?> c;
//      int d, r;
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
            for (Field field : XS1.getFields(dac, top, c)) {
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
//              if (!c.isAssignableFrom(type)) {
//                  continue;
//              }
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

    // <editor-fold defaultstate="collapsed" desc="prepareFields">
    /*
    private void prepareFields() {
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
            for (Field field : XS1.getFields(getClass(), Operation.class, c)) { // getClass().getDeclaredFields()
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
//              if (!c.isAssignableFrom(type)) {
//                  continue;
//              }
                modifiers = field.getModifiers();
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                String errmsg = "failed to prepare field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o == null) {
                        logger.debug(message(type, name, o, depth, round));
                    } else if (o instanceof Parameter) {
                        prepareParameter(field, (Parameter) o);
                    } else if (o instanceof Expression) {
                        prepareExpression(field, (Expression) o);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    Project.increaseParserErrorCount();
                }
            }
        }
    }
    /**/
//
    // <editor-fold defaultstate="collapsed" desc="prepareParameter">
    /*
    private void prepareParameter(Field field, Parameter parameter) {
        if (field == null || parameter == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _parameters.containsKey(key)) {
            return;
        }
//      if (field.isAnnotationPresent(CastingField.class)) {
//          XS1.annotateCastingField(field, parameter);
//          return;
//      }
        if (parameter.isNotDeclared()) {
//          parameter.setDeclared(key, this, field);
            XS1.declare(parameter, this, field);
        }
        if (parameter instanceof Entity) {
            Entity entity = (Entity) parameter;
            entity.prepare();
        }
    }
    /**/
    // </editor-fold>
//
    // <editor-fold defaultstate="collapsed" desc="prepareExpression">
    /*
    private void prepareExpression(Field field, Expression expression) {
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
    }
    // </editor-fold>
    /**/
    // </editor-fold>
//
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
            for (Field field : XS1.getFields(getClass(), Operation.class, c)) { // getClass().getDeclaredFields()
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
//              if (!c.isAssignableFrom(type)) {
//                  continue;
//              }
                modifiers = field.getModifiers();
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                String errmsg = "failed to finalize field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o == null) {
                        logger.debug(message(type, name, o, depth, round));
                    } else if (o instanceof Parameter parameter) {
                        finaliseParameter(field, parameter);
                    } else if (o instanceof Expression expression) {
                        finaliseExpression(field, expression);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    Project.increaseParserErrorCount();
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseParameter">
    private void finaliseParameter(Field field, Parameter parameter) {
        if (field == null || parameter == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _parameters.containsKey(key)) {
            return;
        }
//      if (field.isAnnotationPresent(CastingField.class)) {
////        if (!isPrepared()) {
//          XS1.annotateCastingField(field, parameter);
////        }
//          return;
//      }
        if (parameter.isNotDeclared()) {
//          parameter.setDeclared(key, this, field);
            XS1.declare(parameter, this, field);
        }
        if (parameter instanceof Entity entity) {
            if (!entity.isFinalised()) {
                entity.finalise();
            }
            Entity root = entity.getRoot();
            if (root != null) {
                root.getParameterReferencesMap().put(parameter.getPathString(), parameter);
            }
        } else if (parameter instanceof Primitive primitive) {
            if (!primitive.isFinalised()) {
                primitive.finalise();
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

    protected void finaliseParameters() {
        // field annotations can be overriden here
    }

    private void finishParameters() {
        BooleanExpression filter;
        for (Parameter parameter : _parameters.values()) {
            if (parameter instanceof PersistentEntity entity) {
                filter = entity.getSearchQueryFilter();
                if (filter != null) {
                    entity.getSearchQueryPropertiesMap();
                }
            }
            if (parameter != null) {
                parameter.finish();
            }
        }
    }

    /**
     * anchor-linked parameters indicator
     */
    private boolean _containsAnchorLinkedParameters;

    /**
     * @return the contains anchor-linked parameters indicator
     */
    public boolean containsAnchorLinkedParameters() {
        return _containsAnchorLinkedParameters;
    }

    /**
     * @return the parameters list sorted by display sort key
     */
    public List<Parameter> getParametersListByDisplaySortKey() {
        List<Parameter> list = getParametersList();
        Comparator<Parameter> comparator = new ByParameterDisplaySortKey();
        list = (List<Parameter>) ColUtils.sort(list, comparator);
        return list;
    }

    private void setParametersDisplaySortKey() {
        List<Parameter> list = getParametersList();
        Comparator<Parameter> comparator = new ByParameterSequence();
        list = (List<Parameter>) ColUtils.sort(list, comparator);
        int i = 0;
        for (Parameter parameter : list) {
            parameter.setDisplaySortKey(String.format("%04d", i++));
        }
        for (Parameter parameter : list) {
            if (parameter.getAnchorParameter() == null) {
                setChildrenDisplaySortKey(parameter, list);
            } else if (!parameter.isHiddenField()) {
                boolean unlinked = AnchorType.UNLINKED.equals(parameter.getAnchorType());
                if (!unlinked) {
                    _containsAnchorLinkedParameters = true;
                }
            }
        }
        /*
        if (!list.isEmpty()) {
            System.out.println(getFullName());
            for (Parameter parameter : list) {
                System.out.println("\t" + parameter.getDisplaySortKey() + "\t" + parameter.getName());
            }
            System.out.println();
        }
        /**/
    }

    private void setChildrenDisplaySortKey(Parameter parent, List<Parameter> list) {
        int i = 0;
        boolean first = true;
        for (Parameter child : list) {
            if (child.getAnchorParameter() == parent) {
                boolean unlinked = AnchorType.UNLINKED.equals(child.getAnchorType());
                if (!unlinked && !child.isHiddenField()) {
                    parent.setAnchoringLinkedParameters(true);
                    if (first) {
                        first = false;
                        parent.setFirstAnchoredFieldAnchorType(child.getAnchorType());
                    }
                }
                child.setDisplaySortKey(parent.getDisplaySortKey() + (unlinked ? '/' : '-') + String.format("%02d", i++));
                setChildrenDisplaySortKey(child, list); // como '/' es mayor que '-' las UNLINKED quedan después de las enlazadas
            }
        }
    }

    @Override
    protected boolean checkName() {
        if (super.checkName()) {
            String name = getName();
            String NAME = name.toUpperCase();
            if (ArrayUtils.contains(RESERVED, NAME)) {
                logger.error(getFullName() + " must be renamed; " + NAME + " is a reserved operation name");
                Project.increaseParserErrorCount();
            } else {
                return true;
            }
        }
        return false;
    }

    private void checkExpressions() {
        Object o;
        Expression e;
        for (Parameter parameter : _parameters.values()) {
            e = parameter.getRenderingFilter();
            if (e != null) {
                verifyExpression(e, parameter, ExpressionUsage.RENDERING_FILTER, false);
            }
            e = parameter.getRequiringFilter();
            if (e != null) {
                verifyExpression(e, parameter, ExpressionUsage.REQUIRING_FILTER, false);
            }
            e = parameter.getNullifyingFilter();
            if (e != null) {
                verifyExpression(e, parameter, ExpressionUsage.NULLIFYING_FILTER, false);
            }
            if (parameter instanceof Entity entity) {
                e = entity.getSearchQueryFilter();
                if (e != null) {
                    verifyExpression(e, parameter, ExpressionUsage.SEARCH_QUERY_FILTER, false);
                }
            }
            o = parameter.getInitialValue();
            if (o instanceof Expression expression) {
                verifyExpression(expression, parameter, ExpressionUsage.INITIAL_VALUE, false);
            }
            o = parameter.getDefaultValue();
            if (o instanceof Expression expression) {
                verifyExpression(expression, parameter, ExpressionUsage.DEFAULT_VALUE);
            }
            o = parameter.getCurrentValue();
            if (o instanceof Expression expression) {
                verifyExpression(expression, parameter, ExpressionUsage.CURRENT_VALUE);
            }
            if (parameter instanceof IntervalizedArtifact ia) {
                o = ia.getMaxValue();
                if (o instanceof Expression expression) {
                    e = expression;
                    verifyExpression(e, parameter, ExpressionUsage.MAX_VALUE);
                }
                o = ia.getMinValue();
                if (o instanceof Expression expression) {
                    e = expression;
                    verifyExpression(e, parameter, ExpressionUsage.MIN_VALUE);
                }
            }
        }
        for (Expression expression : _expressions.values()) {
            if (expression != null) {
                verifyExpression(expression);
            }
        }
    }

    private void checkTransitions() {
        int equ = 0, neq = 0;
        for (Transition transition : _transitions.values()) {
            State x = transition.getX();
            State y = transition.getY();
            String xn = x == null ? null : x.getName();
            String yn = y == null ? null : y.getName();
            String name = getFullName() + "(" + xn + " -> " + yn + ")";
            if (x == null) {
                if (!isSelfConstructor()) {
                    logger.error("invalid transition: " + name + "; initial state is null");
                    Project.increaseParserErrorCount();
                }
            } else if (!isInstanceOperation()) {
                logger.error("invalid transition: " + name + "; " + getFullName() + " is not an instance operation and initial state is not null");
                Project.increaseParserErrorCount();
            }
            if (y == null) {
                logger.error("invalid transition: " + name + "; final state is null");
                Project.increaseParserErrorCount();
            }
            if (x != null && y != null) {
                if (x == y) {
                    equ++;
                    if (stateHasTrigger(x)) {
                        logger.error("invalid transition: " + name + "; " + getFullName() + " final state is equal to initial state and that state has a trigger");
                        Project.increaseParserErrorCount();
                    }
                } else {
                    neq++;
                }
            }
        }
        if (equ > 0 && neq > 0) {
            logger.warn("some but not all transitions of " + getFullName() + " have its final state equal to its initial state");
            Project.increaseParserWarningCount();
        }
    }

    private boolean stateHasTrigger(State state) {
        for (Trigger trigger : getTriggersMap().values()) {
            if (state.equals(trigger.getState())) {
                return true;
            }
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateOperationClass(type);
            annotateOperationDocGen(type);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(OperationClass.class);
        valid.add(OperationDocGen.class);
        return valid;
    }

    private void annotateOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, OperationClass.class);
        if (annotatedClass != null) {
            OperationClass annotation = annotatedClass.getAnnotation(OperationClass.class);
            if (annotation != null) {
                _operationAccess = specified(annotation.access(), _operationAccess);
                _asynchronous = annotation.asynchronous();
                _shellEnabled = annotation.shell();
                _confirmationRequired = annotation.confirmation().toBoolean(_confirmationRequired);
                _complex = annotation.complex().toBoolean(_complex);
                _dialogSize = annotation.dialogSize(); // Math.min(100, Math.max(0, annotation.dialogSize()));
                _operationLogging = specified(annotation.logging(), _operationLogging);
                _annotatedWithOperationClass = true;
            }
        }
    }

    private void annotateOperationDocGen(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, OperationDocGen.class);
        if (annotatedClass != null) {
            OperationDocGen annotation = annotatedClass.getAnnotation(OperationDocGen.class);
            if (annotation != null) {
                _operationActivityDiagramGenEnabled = annotation.activityDiagram().toBoolean(_operationActivityDiagramGenEnabled);
                _annotatedWithOperationDocGen = true;
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Converters and Validators">
    protected static final String GOOGLE_MAPS_EMBED_CONVERTER = GoogleMapsConstants.GOOGLE_MAPS_EMBED_CONVERTER;

    protected static final String PHONE_NUMBER_VALIDATOR = PhoneConstants.PHONE_NUMBER_VALIDATOR;

    protected static final String LOCAL_PHONE_NUMBER_VALIDATOR = PhoneConstants.LOCAL_PHONE_NUMBER_VALIDATOR;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Fields">
    protected static final int MAX_EMAIL_ADDRESS_LENGTH = Constants.MAX_EMAIL_ADDRESS_LENGTH;

    protected static final String EMAIL_REGEX = Constants.EMAIL_REGEX;

    protected static final String PHONE_REGEX = Constants.PHONE_REGEX;

    protected static final String LOCAL_PHONE_REGEX = Constants.LOCAL_PHONE_REGEX;

    protected static final String URL_REGEX = Constants.URL_REGEX;

    protected static final String WHITESPACELESS_REGEX = Constants.WHITESPACELESS_REGEX;

    protected static final SpecialCharacterValue NO_IMAGE = SpecialCharacterValue.NULL;

    protected static final SpecialEntityValue CURRENT_USER = SpecialEntityValue.CURRENT_USER;

    protected static final BooleanScalarX NULL_BOOLEAN = XB.NULL_BOOLEAN;

    protected static final BooleanScalarX TRUTH = XB.TRUTH;

    protected static final BooleanScalarX UNTRUTH = XB.UNTRUTH;

    protected static final CharacterScalarX NULL_STRING = XB.NULL_STRING;

    protected static final CharacterScalarX EMPTY_STRING = XB.EMPTY_STRING;

    protected static final CharacterScalarX EMPTY = XB.EMPTY;

    protected static final CharacterScalarX RGUID = XB.RGUID;

    protected static final CharacterScalarX SPACE = XB.SPACE;

    protected static final CharacterScalarX COMMA = XB.COMMA;

    protected static final CharacterScalarX HYPHEN = XB.HYPHEN;

    protected static final CharacterScalarX PERIOD = XB.PERIOD;

    protected static final CharacterScalarX SLASH = XB.SLASH;

    protected static final CharacterScalarX COLON = XB.COLON;

    protected static final CharacterScalarX SEMICOLON = XB.SEMICOLON;

    protected static final CharacterScalarX UNDERSCORE = XB.UNDERSCORE;

    protected static final CharacterScalarX VBAR = XB.VBAR;

    protected static final CharacterScalarX CONTENT_ROOT_DIR = XB.CONTENT_ROOT_DIR;

    protected static final CharacterScalarX CURRENT_USER_CODE = XB.CURRENT_USER_CODE;

    protected static final NumericScalarX NULL_NUMBER = XB.NULL_NUMBER;

    protected static final NumericScalarX CURRENT_USER_ID = XB.CURRENT_USER_ID;

    protected static final TemporalScalarX NULL_TEMPORAL = XB.NULL_TEMPORAL;

    protected static final TemporalScalarX CURRENT_DATE = XB.CURRENT_DATE;

    protected static final TemporalScalarX CURRENT_TIME = XB.CURRENT_TIME;

    protected static final TemporalScalarX CURRENT_TIMESTAMP = XB.CURRENT_TIMESTAMP;

    protected static final java.sql.Date EPOCH_DATE = DateData.EPOCH;

    protected static final java.sql.Time EPOCH_TIME = TimeData.EPOCH;

    protected static final java.sql.Timestamp EPOCH_TIMESTAMP = TimestampData.EPOCH;

    protected static final EntityScalarX NULL_ENTITY = XB.NULL_ENTITY;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Fields">
    protected static final String EMAIL_REGEX_ENGLISH_DESCRIPTION = EmailConstants.EMAIL_REGEX_ENGLISH_DESCRIPTION;

    protected static final String EMAIL_REGEX_SPANISH_DESCRIPTION = EmailConstants.EMAIL_REGEX_SPANISH_DESCRIPTION;

    protected static final String EMAIL_REGEX_ENGLISH_ERROR_MESSAGE = EmailConstants.EMAIL_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String EMAIL_REGEX_SPANISH_ERROR_MESSAGE = EmailConstants.EMAIL_REGEX_SPANISH_ERROR_MESSAGE;

    protected static final String PHONE_REGEX_ENGLISH_DESCRIPTION = PhoneConstants.PHONE_REGEX_ENGLISH_DESCRIPTION;

    protected static final String PHONE_REGEX_SPANISH_DESCRIPTION = PhoneConstants.PHONE_REGEX_SPANISH_DESCRIPTION;

    protected static final String PHONE_REGEX_ENGLISH_ERROR_MESSAGE = PhoneConstants.PHONE_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String PHONE_REGEX_SPANISH_ERROR_MESSAGE = PhoneConstants.PHONE_REGEX_SPANISH_ERROR_MESSAGE;

    protected static final String LOCAL_PHONE_REGEX_ENGLISH_DESCRIPTION = PhoneConstants.LOCAL_PHONE_REGEX_ENGLISH_DESCRIPTION;

    protected static final String LOCAL_PHONE_REGEX_SPANISH_DESCRIPTION = PhoneConstants.LOCAL_PHONE_REGEX_SPANISH_DESCRIPTION;

    protected static final String LOCAL_PHONE_REGEX_ENGLISH_ERROR_MESSAGE = PhoneConstants.LOCAL_PHONE_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String LOCAL_PHONE_REGEX_SPANISH_ERROR_MESSAGE = PhoneConstants.LOCAL_PHONE_REGEX_SPANISH_ERROR_MESSAGE;

    protected static final String URL_REGEX_ENGLISH_DESCRIPTION = URLConstants.URL_REGEX_ENGLISH_DESCRIPTION;

    protected static final String URL_REGEX_SPANISH_DESCRIPTION = URLConstants.URL_REGEX_SPANISH_DESCRIPTION;

    protected static final String URL_REGEX_ENGLISH_ERROR_MESSAGE = URLConstants.URL_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String URL_REGEX_SPANISH_ERROR_MESSAGE = URLConstants.URL_REGEX_SPANISH_ERROR_MESSAGE;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Expressions">
    protected static BooleanScalarX nullBoolean() {
        return NULL_BOOLEAN;
    }

    protected static BooleanScalarX truth() {
        return TRUTH;
    }

    protected static BooleanScalarX untruth() {
        return UNTRUTH;
    }

    protected static CharacterScalarX nullString() {
        return NULL_STRING;
    }

    protected static CharacterScalarX emptyString() {
        return EMPTY_STRING;
    }

    protected static CharacterScalarX empty() {
        return EMPTY;
    }

    protected static CharacterScalarX contentRootDir() {
        return CONTENT_ROOT_DIR;
    }

    protected static CharacterScalarX currentUserCode() {
        return CURRENT_USER_CODE;
    }

    protected static NumericScalarX nullNumber() {
        return NULL_NUMBER;
    }

    protected static NumericScalarX currentUserId() {
        return CURRENT_USER_ID;
    }

    protected static TemporalScalarX nullTemporal() {
        return NULL_TEMPORAL;
    }

    protected static TemporalScalarX currentDate() {
        return CURRENT_DATE;
    }

    protected static TemporalScalarX currentTime() {
        return CURRENT_TIME;
    }

    protected static TemporalScalarX currentTimestamp() {
        return CURRENT_TIMESTAMP;
    }

    protected static EntityScalarX nullEntity() {
        return NULL_ENTITY;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Trusted Sites">
    protected static final String EMBED_CALENDAR = TrustedSites.EMBED_CALENDAR;

    protected static final String EMBED_MAPS = TrustedSites.EMBED_MAPS;

    protected static final String EMBED_YOUTUBE = TrustedSites.EMBED_YOUTUBE;

    protected static final String GOOGLE = TrustedSites.GOOGLE;

    protected static final String GOOGLE_CALENDAR = TrustedSites.GOOGLE_CALENDAR;

    protected static final String GOOGLE_MAPS = TrustedSites.GOOGLE_MAPS;

    protected static final String YOUTUBE = TrustedSites.YOUTUBE;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Supplementary Expressions">
    protected static EntityDataAggregateX coalesce(Entity operand1, Entity operand2, Entity... extraOperands) {
        return XB.Entity.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static BooleanDataAggregateX coalesce(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static CharacterDataAggregateX coalesce(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
        return XB.Character.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static NumericDataAggregateX coalesce(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
        return XB.Numeric.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static TemporalDataAggregateX coalesce(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
        return XB.Temporal.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static CharacterDataAggregateX max(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
        return XB.Character.DataAggregate.max(operand1, operand2, extraOperands);
    }

    protected static NumericDataAggregateX max(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
        return XB.Numeric.DataAggregate.max(operand1, operand2, extraOperands);
    }

    protected static TemporalDataAggregateX max(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
        return XB.Temporal.DataAggregate.max(operand1, operand2, extraOperands);
    }

    protected static CharacterDataAggregateX min(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
        return XB.Character.DataAggregate.min(operand1, operand2, extraOperands);
    }

    protected static NumericDataAggregateX min(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
        return XB.Numeric.DataAggregate.min(operand1, operand2, extraOperands);
    }

    protected static TemporalDataAggregateX min(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
        return XB.Temporal.DataAggregate.min(operand1, operand2, extraOperands);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Supplementary Expressions">
    protected static CharacterExpression concat(String x, Expression y) {
        return XB.Character.OrderedPair.concat(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concat(SpecialCharacterValue x, Expression y) {
        return XB.Character.OrderedPair.concat(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concat(Expression x, Expression y) {
        return XB.Character.OrderedPair.concat(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concat(Expression x, Expression y, Expression... extraOperands) {
        CharacterExpression cx = charStringOf(x);
        CharacterExpression cy = charStringOf(y);
        if (extraOperands != null && extraOperands.length > 0) {
            List<CharacterExpression> cz = new ArrayList<>();
            for (Expression extraOperand : extraOperands) {
                if (extraOperand != null) {
                    cz.add(charStringOf(extraOperand));
                }
            }
            if (!cz.isEmpty()) {
                return XB.Character.DataAggregate.concat(cx, cy, cz.toArray(CharacterExpression[]::new));
            }
        }
        return XB.Character.OrderedPair.concat(cx, cy);
    }

    protected static CharacterExpression concatenate(String x, Expression y) {
        return XB.Character.OrderedPair.concatenate(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concatenate(SpecialCharacterValue x, Expression y) {
        return XB.Character.OrderedPair.concatenate(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concatenate(Expression x, Expression y) {
        return XB.Character.OrderedPair.concatenate(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concatenate(Expression x, Expression y, Expression... extraOperands) {
        CharacterExpression cx = charStringOf(x);
        CharacterExpression cy = charStringOf(y);
        if (extraOperands != null && extraOperands.length > 0) {
            List<CharacterExpression> cz = new ArrayList<>();
            for (Expression extraOperand : extraOperands) {
                if (extraOperand != null) {
                    cz.add(charStringOf(extraOperand));
                }
            }
            if (!cz.isEmpty()) {
                return XB.Character.DataAggregate.concatenate(cx, cy, cz.toArray(CharacterExpression[]::new));
            }
        }
        return XB.Character.OrderedPair.concatenate(cx, cy);
    }

    protected static CharacterExpression charStringOf(Object x) {
        return x instanceof CharacterExpression ? (CharacterExpression) x : XB.toCharString(x);
    }

    protected static TemporalExpression dateOf(Object x) {
        return x instanceof TemporalExpression ? (TemporalExpression) x : XB.toDate(x);
    }

    protected static TemporalExpression timeOf(Object x) {
        return x instanceof TemporalExpression ? (TemporalExpression) x : XB.toTime(x);
    }

    protected static TemporalExpression timestampOf(Object x) {
        return x instanceof TemporalExpression ? (TemporalExpression) x : XB.toTimestamp(x);
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

    public String signature() {
        String asynchronous = Kleenean.TRUE.equals(_asynchronous) ? "ASYNCHRONOUS " : "";
        return asynchronous + getOperationAccess() + " " + getOperationKind() + " " + getOperationType() + " " + getFullName();
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

    private void track(String method, Object... parameters) {
        TLC.getProject().getParser().track(depth(), round(), getClassPath(), method, parameters);
    }

}
