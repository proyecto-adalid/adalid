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
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ProcessOperation extends Operation {

    private static final Logger logger = Logger.getLogger(Operation.class);

    private boolean _annotatedWithProcessOperationClass;

    private String _processName;

    private String _processingGroup;

    private TemporalAddend _newTaskNotification;

    private boolean _notifySupervisor;

    private boolean _notifyAssignment;

    private boolean _notifyAssumption;

    private boolean _notifyRelief;

    private boolean _notifyAbandonment;

    private boolean _notifyCancellation;

    private boolean _notifyCompletion;

    private TemporalAddend _unassignedTaskNotification;

    private TemporalAddend _unfinishedTaskNotification;

    private TemporalAddend _unassignedTaskEscalation;

    private TemporalAddend _unfinishedTaskEscalation;

    private TemporalAddend _nextUnassignedTaskNotification;

    private TemporalAddend _nextUnfinishedTaskNotification;

    private TemporalAddend _nextUnassignedTaskEscalation;

    private TemporalAddend _nextUnfinishedTaskEscalation;

    private TemporalAddend _deadlineAddend;

    private boolean _automaticAssumption;

    private boolean _builtIn;

    private boolean _treeStructureModifier;

    private int _jobPriority;

    private Boolean _bplCodeGenEnabled;

    private Boolean _sqlCodeGenEnabled;

    private Kleenean _overloading = Kleenean.UNSPECIFIED;

    private Kleenean _serviceable = Kleenean.UNSPECIFIED;

    private boolean _annotatedWithConstructionOperationClass;

    private Class<? extends Entity> _constructionType;

    private OnConstructionOperationSuccess _onConstructionSuccess = OnConstructionOperationSuccess.UNSPECIFIED;

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            checkConstructionType();
        }
        return ok;
    }

    private void checkConstructionType() {
        if (_constructionType != null) {
            String detail = "\"" + _constructionType.getSimpleName() + "\" in ConstructionOperationClass annotation of " + getFullName();
            int modifiers = _constructionType.getModifiers();
            if (Modifier.isAbstract(modifiers)) {
                String message = "abstract class " + detail;
                logger.error(message);
                Project.increaseParserErrorCount();
            } else {
//              Entity entity = TLC.getProject().getEntity(_constructionType);
//              if (entity == null) { // the entity may be null here because it has not yet been referenced
//                  String message = "invalid entity class " + detail;
//                  logger.error(message);
//                  Project.increaseParserErrorCount();
//              } else if (entity.isAbstractClass()) { // a custom business process would work
//                  String message = "abstract entity class " + detail;
//                  logger.error(message);
//                  Project.increaseParserErrorCount();
//              }
                TLC.getProject().addConstructor(_constructionType, this);
            }
        }
    }

    @Override
    boolean implicitlySynchronous() {
        return isConstructor() || OperationKind.INSTANCE.equals(getOperationKind());
    }

    /**
     * @return true if annotated with ProcessOperationClass; false otherwise
     */
    public boolean isAnnotatedWithProcessOperationClass() {
        return _annotatedWithProcessOperationClass;
    }

    /**
     * @return the proper name
     */
    @Override
    public String getProperName() {
        return _processName;
    }

    /**
     * @return the process name
     */
    public String getProcessName() {
        return _processName;
    }

    /**
     * @return the processing group identifier
     */
    public String getProcessingGroup() {
        return _processingGroup;
    }

    /**
     * @return the new task notification addend
     */
    public TemporalAddend getNewTaskNotification() {
        return _newTaskNotification;
    }

    /**
     * @return the notify supervisor indicator
     */
    public boolean isNotifySupervisor() {
        return _notifySupervisor;
    }

    /**
     * @return the notify assignment indicator
     */
    public boolean isNotifyAssignment() {
        return _notifyAssignment;
    }

    /**
     * @return the notify assumption indicator
     */
    public boolean isNotifyAssumption() {
        return _notifyAssumption;
    }

    /**
     * @return the notify relief indicator
     */
    public boolean isNotifyRelief() {
        return _notifyRelief;
    }

    /**
     * @return the notify abandonment indicator
     */
    public boolean isNotifyAbandonment() {
        return _notifyAbandonment;
    }

    /**
     * @return the notify cancellation indicator
     */
    public boolean isNotifyCancellation() {
        return _notifyCancellation;
    }

    /**
     * @return the notify completion indicator
     */
    public boolean isNotifyCompletion() {
        return _notifyCompletion;
    }

    /**
     * @return the unassigned task notification addend
     */
    public TemporalAddend getUnassignedTaskNotification() {
        return _unassignedTaskNotification;
    }

    /**
     * @return the unfinished task notification addend
     */
    public TemporalAddend getUnfinishedTaskNotification() {
        return _unfinishedTaskNotification;
    }

    /**
     * @return the unassigned task escalation addend
     */
    public TemporalAddend getUnassignedTaskEscalation() {
        return _unassignedTaskEscalation;
    }

    /**
     * @return the unfinished task escalation addend
     */
    public TemporalAddend getUnfinishedTaskEscalation() {
        return _unfinishedTaskEscalation;
    }

    /**
     * @return the next unassigned task notification addend
     */
    public TemporalAddend getNextUnassignedTaskNotification() {
        return _nextUnassignedTaskNotification;
    }

    /**
     * @return the next unfinished task notification addend
     */
    public TemporalAddend getNextUnfinishedTaskNotification() {
        return _nextUnfinishedTaskNotification;
    }

    /**
     * @return the next unassigned task escalation addend
     */
    public TemporalAddend getNextUnassignedTaskEscalation() {
        return _nextUnassignedTaskEscalation;
    }

    /**
     * @return the next unfinished task escalation addend
     */
    public TemporalAddend getNextUnfinishedTaskEscalation() {
        return _nextUnfinishedTaskEscalation;
    }

    /**
     * @return the deadline addend
     */
    public TemporalAddend getDeadlineAddend() {
        return _deadlineAddend;
    }

    /**
     * @return the automatic assumption indicator
     */
    public boolean isAutomaticAssumption() {
        return _automaticAssumption;
    }

    /**
     * @return the built-in indicator
     */
    public boolean isBuiltIn() {
        return _builtIn;
    }

    /**
     * @return the tree structure modifier indicator
     */
    public boolean isTreeStructureModifier() {
        return _treeStructureModifier;
    }

    /**
     * @return the job priority
     */
    public int getJobPriority() {
        return _jobPriority;
    }

    /**
     * @return true if this operation business process logic code generation is enabled on the declaring entity or any of its extensions
     */
    public boolean isExtensionBplCodeGenEnabled() {
        return isBplCodeGenEnabled() || isExtensionBplCodeGenEnabled(getDeclaringEntityRoot());
    }

    private boolean isExtensionBplCodeGenEnabled(Entity entity) {
        if (entity != null) {
            for (Entity e : entity.getExtensionsList()) {
                ProcessOperation o = (ProcessOperation) e.getOperation(getClass());
                if (o != null && o.isExtensionBplCodeGenEnabled()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the business process logic code generation enabled indicator
     */
    public boolean isBplCodeGenEnabled() {
        if (_bplCodeGenEnabled == null) {
            Entity operationEntityRoot = getDeclaringEntityRoot();
            return operationEntityRoot == null || operationEntityRoot.isBplCodeGenEnabled();
        }
        return _bplCodeGenEnabled;
    }

    /**
     * El método setBplCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BPL (Business Process Logic) para la operación.
     *
     * El método setBplCodeGenEnabled debe ejecutarse en el método settleAttributes de la operación .
     *
     * @param enabled true o false para generar, o no, código BPL para la operación
     */
    public void setBplCodeGenEnabled(boolean enabled) {
        _bplCodeGenEnabled = enabled;
    }

    /**
     * @return the business web service code generation enabled indicator
     */
    private boolean isBwsCodeGenEnabled() {
        Entity operationEntityRoot = getDeclaringEntityRoot();
        return operationEntityRoot == null || operationEntityRoot.isBwsCodeGenEnabled();
    }

    /**
     * @return the SQL code generation enabled indicator
     */
    public boolean isSqlCodeGenEnabled() {
        if (_sqlCodeGenEnabled == null) {
            Entity operationEntityRoot = getDeclaringEntityRoot();
            return operationEntityRoot == null || operationEntityRoot.isSqlCodeGenEnabled();
        }
        return _sqlCodeGenEnabled;
    }

    /**
     * El método setSqlCodeGenEnabled se utiliza para especificar si se debe, o no, generar código SQL para la operación.
     *
     * El método setSqlCodeGenEnabled debe ejecutarse en el método settleAttributes de la operación.
     *
     * @param enabled true o false para generar, o no, código SQL para la operación
     */
    public void setSqlCodeGenEnabled(boolean enabled) {
        _sqlCodeGenEnabled = enabled;
    }

    /**
     * @return the overloading indicator
     */
    public Kleenean getOverloading() {
        return isSqlCodeGenEnabled() ? _overloading : Kleenean.UNSPECIFIED;
    }

    /**
     * @return the serviceable indicator
     */
    public Kleenean getServiceable() {
        return isBplCodeGenEnabled() && isBwsCodeGenEnabled() ? _serviceable : Kleenean.UNSPECIFIED;
    }

    /**
     * El método setServiceable se utiliza para especificar si se debe, o no, generar código BWS (Business Web Service) para la operación.
     *
     * El método setServiceable debe ejecutarse en el método settleAttributes de la operación.
     *
     * @param serviceable TRUE o FALSE para generar, o no, código BWS para la operación.
     */
    public void setServiceable(Kleenean serviceable) {
        _serviceable = serviceable;
    }

    /**
     * @return true if annotated with ConstructionOperationClass; false otherwise
     */
    public boolean isAnnotatedWithConstructionOperationClass() {
        return _annotatedWithConstructionOperationClass;
    }

    /**
     * @return the constructor operation indicator
     */
    @Override
    public boolean isConstructor() {
        return _constructionType != null;
    }

    /**
     * @return the self-constructor operation indicator
     */
    @Override
    public boolean isSelfConstructor() {
        Entity declaringEntity = isConstructor() ? getDeclaringEntity() : null;
        return declaringEntity != null && declaringEntity.getClass().getSimpleName().equals(_constructionType.getSimpleName());
    }

    /**
     * @return the construction type
     */
    public Class<? extends Entity> getConstructionType() {
        return _constructionType;
    }

    public Map<String, Property> getConstructionOperationLinkedPropertiesMap() {
        if (_constructionType == null) {
            return null;
        }
        Map<String, Parameter> parameters = getParametersMap();
        if (parameters.isEmpty()) {
            return null;
        }
        Entity operationEntityRoot = getDeclaringEntityRoot();
        if (operationEntityRoot == null) {
            return null;
        }
        Entity constructionEntityRoot;
        if (_constructionType.equals(operationEntityRoot.getDataClass())) {
            constructionEntityRoot = operationEntityRoot;
        } else {
            EntityReference constructionEntityReference = operationEntityRoot.getMainEntityReferenceFrom(_constructionType);
            constructionEntityRoot = constructionEntityReference == null ? null : constructionEntityReference.getDeclaringEntityRoot();
        }
        if (constructionEntityRoot == null) {
            return null;
        }
        Map<String, Property> constructionEntityRootProperties = constructionEntityRoot.getPropertiesMap();
        if (constructionEntityRootProperties.isEmpty()) {
            return null;
        }
        Map<String, Property> properties = new LinkedHashMap<>();
        for (String key : parameters.keySet()) {
            Parameter parameter = parameters.get(key);
            Property property = parameter.getLinkedProperty();
            if (property != null && !property.isCalculable()) {
                properties.put(key, property);
            }
        }
        setValidConstructionOperationLinkedPropertiesMap(properties, constructionEntityRootProperties);
        return properties.isEmpty() ? null : properties;
    }

    private boolean _validConstructionOperationLinkedPropertiesMap;

    public boolean isValidConstructionOperationLinkedPropertiesMap() {
        return _validConstructionOperationLinkedPropertiesMap;
    }

    private void setValidConstructionOperationLinkedPropertiesMap(Map<String, Property> linkedProperties, Map<String, Property> entityProperties) {
        _validConstructionOperationLinkedPropertiesMap = false;
        if (linkedProperties.isEmpty()) {
            return;
        }
        for (String key : entityProperties.keySet()) {
            if (linkedProperties.containsKey(key)) {
                continue;
            }
            Property property = entityProperties.get(key);
            if (property.isMandatoryForInsert()) {
                return;
            }
        }
        _validConstructionOperationLinkedPropertiesMap = true;
    }

    public OnConstructionOperationSuccess getOnConstructionSuccess() {
        return _constructionType != null ? _onConstructionSuccess : OnConstructionOperationSuccess.UNSPECIFIED;
    }

    @Override
    public int getDialogSize() {
        /*
        return _dialogSize < 0 // menor que 0 equivale al valor predeterminado
            && _constructionType != null
            && _onConstructionSuccess != null
            && _onConstructionSuccess.equals(OnConstructionOperationSuccess.DISPLAY_NEW_INSTANCE) ? 0 : super.getDialogSize();
        /**/
        return _dialogSize < 0 && _constructionType != null ? 0 : super.getDialogSize();
    }

    /**
     * El método addTransition se utiliza para agregar una transición a la lista de transiciones que lleva a cabo la operación.
     *
     * @param x estado inicial
     * @param y estado final
     * @return la transición agregada
     */
    public Transition addTransition(State x, State y) {
        Transition transition = new Transition(x, y);
        addTransition(transition);
        return transition;
    }

    /**
     * El método addTransition se utiliza para agregar una transición a la lista de transiciones que lleva a cabo la operación.
     *
     * @param transition la transición a agregar
     */
    public void addTransition(Transition transition) {
        if (transition != null) {
            String key = transition.getName();
            if (key != null) {
                Map<String, Transition> map = getTransitionsMap();
                if (map.containsKey(key)) {
                } else {
                    map.put(key, transition);
                    putEntityTransition(transition);
                }
            }
        }
    }

    private void putEntityTransition(Transition transition) {
        Entity root = getDeclaringEntityRoot();
        if (root != null) {
            String key = transition.getName();
            Map<String, Transition> map = root.getTransitionsMap();
            if (map.containsValue(transition)) {
            } else {
                map.put(key, transition);
            }
        }
    }

    public Trigger trigger(State state) {
        return new Trigger(state, this);
    }

    /**
     * El método addTriggerOn se utiliza para agregar uno o más estados a la lista de estados en los cuales se debe disparar la operación.
     *
     * @param states uno o más estados de la entidad
     */
    public void addTriggerOn(State... states) {
        for (State state : states) {
            Trigger trigger = new Trigger(state, this);
            addTriggerOn(trigger);
        }
    }

    private void addTriggerOn(Trigger trigger) {
        String key = trigger.getName();
        if (key != null) {
            Map<String, Trigger> map = getTriggersMap();
            if (map.containsKey(key)) {
            } else {
                map.put(key, trigger);
            }
        }
    }

    @Override
    public String signature() {
        String fullName = getFullName();
        String signature = super.signature();
        String construct = _constructionType == null ? "" : _constructionType.getSimpleName() + " ";
        return signature.replace(fullName, construct + fullName);
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _processName = StringUtils.defaultIfBlank(_processName, getName());
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateProcessOperationClass(type);
            annotateConstructionOperationClass(type);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(ProcessOperationClass.class);
        valid.add(ConstructionOperationClass.class);
        return valid;
    }

    private void annotateProcessOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ProcessOperationClass.class);
        if (annotatedClass != null) {
            ProcessOperationClass annotation = annotatedClass.getAnnotation(ProcessOperationClass.class);
            if (annotation != null) {
                String name = annotation.name();
                if (StringUtils.isNotBlank(name)) {
                    _processName = name;
                }
                String group = annotation.processingGroup();
                if (StringUtils.isNotBlank(group)) {
                    if (StrUtils.esIdentificadorSqlValido(group)) {
                        if (group.length() <= Project.getDefaultCharacterKeyMaxLength()) {
                            _processingGroup = group;
                            TLC.getProject().getProcessingGroups().add(group);
                        } else {
                            String message = "invalid processing group identifier \"" + group + "\" at " + getFullName();
                            message += "; identifier length cannot be greater than " + Project.getDefaultCharacterKeyMaxLength();
                            logger.error(message);
                            Project.increaseParserErrorCount();
                        }
                    } else {
                        String message = "invalid processing group identifier \"" + group + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                /**/
                final char[] validUnits = {TemporalAddend.MINUTES, TemporalAddend.HOURS, TemporalAddend.DAYS};
                final char defaultUnit = TemporalAddend.DAYS;
                final TemporalAddend min = TemporalAddend.temporalAddendValueOf("0m");
                final TemporalAddend max = TemporalAddend.temporalAddendValueOf("365D");
                String previousString = null;
                TemporalAddend previousAddend = null;
                String string = annotation.newTaskNotification();
                if (StringUtils.isNotBlank(string)) {
                    _newTaskNotification = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_newTaskNotification == null) {
                        String message = "invalid new task notification expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_newTaskNotification.isBadValue()) {
                        String message = "invalid new task notification expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else {
                        previousString = "the new task notification expression";
                        previousAddend = _newTaskNotification;
                    }
                }
                _notifySupervisor = annotation.notifySupervisor();
                _notifyAssignment = annotation.notifyAssignment();
                _notifyAssumption = annotation.notifyAssumption();
                _notifyRelief = annotation.notifyRelief();
                _notifyAbandonment = annotation.notifyAbandonment();
                _notifyCancellation = annotation.notifyCancellation();
                _notifyCompletion = annotation.notifyCompletion();
                string = annotation.unassignedTaskNotification();
                if (StringUtils.isNotBlank(string)) {
                    _unassignedTaskNotification = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_unassignedTaskNotification == null) {
                        String message = "invalid unassigned task notification expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_unassignedTaskNotification.isBadValue()) {
                        String message = "invalid unassigned task notification expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (previousAddend != null && previousAddend.compareTo(_unassignedTaskNotification) > 0) {
                        String message = "invalid unassigned task notification expression \"" + string + "\" at " + getFullName();
                        message += "; its value is less than the value of " + previousString + " \"" + previousAddend + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else {
                        previousString = "the unassigned task notification expression";
                        previousAddend = _unassignedTaskNotification;
                    }
                }
                string = annotation.unfinishedTaskNotification();
                if (StringUtils.isNotBlank(string)) {
                    _unfinishedTaskNotification = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_unfinishedTaskNotification == null) {
                        String message = "invalid unfinished task notification expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_unfinishedTaskNotification.isBadValue()) {
                        String message = "invalid unfinished task notification expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (previousAddend != null && previousAddend.compareTo(_unfinishedTaskNotification) > 0) {
                        String message = "invalid unfinished task notification expression \"" + string + "\" at " + getFullName();
                        message += "; its value is less than the value of " + previousString + " \"" + previousAddend + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else {
                        previousString = "the unfinished task notification expression";
                        previousAddend = _unfinishedTaskNotification;
                    }
                }
                string = annotation.unassignedTaskEscalation();
                if (StringUtils.isNotBlank(string)) {
                    _unassignedTaskEscalation = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_unassignedTaskEscalation == null) {
                        String message = "invalid unassigned task escalation expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_unassignedTaskEscalation.isBadValue()) {
                        String message = "invalid unassigned task escalation expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (previousAddend != null && previousAddend.compareTo(_unassignedTaskEscalation) > 0) {
                        String message = "invalid unassigned task escalation expression \"" + string + "\" at " + getFullName();
                        message += "; its value is less than the value of " + previousString + " \"" + previousAddend + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else {
                        previousString = "the unassigned task escalation expression";
                        previousAddend = _unassignedTaskEscalation;
                    }
                }
                string = annotation.unfinishedTaskEscalation();
                if (StringUtils.isNotBlank(string)) {
                    _unfinishedTaskEscalation = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_unfinishedTaskEscalation == null) {
                        String message = "invalid unfinished task escalation expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_unfinishedTaskEscalation.isBadValue()) {
                        String message = "invalid unfinished task escalation expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (previousAddend != null && previousAddend.compareTo(_unfinishedTaskEscalation) > 0) {
                        String message = "invalid unfinished task escalation expression \"" + string + "\" at " + getFullName();
                        message += "; its value is less than the value of " + previousString + " \"" + previousAddend + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                string = annotation.nextUnassignedTaskNotification();
                if (StringUtils.isNotBlank(string)) {
                    _nextUnassignedTaskNotification = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_nextUnassignedTaskNotification == null) {
                        String message = "invalid next unassigned task notification expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_nextUnassignedTaskNotification.isBadValue()) {
                        String message = "invalid next unassigned task notification expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                string = annotation.nextUnfinishedTaskNotification();
                if (StringUtils.isNotBlank(string)) {
                    _nextUnfinishedTaskNotification = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_nextUnfinishedTaskNotification == null) {
                        String message = "invalid next unfinished task notification expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_nextUnfinishedTaskNotification.isBadValue()) {
                        String message = "invalid next unfinished task notification expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                string = annotation.nextUnassignedTaskEscalation();
                if (StringUtils.isNotBlank(string)) {
                    _nextUnassignedTaskEscalation = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_nextUnassignedTaskEscalation == null) {
                        String message = "invalid next unassigned task escalation expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_nextUnassignedTaskEscalation.isBadValue()) {
                        String message = "invalid next unassigned task escalation expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                string = annotation.nextUnfinishedTaskEscalation();
                if (StringUtils.isNotBlank(string)) {
                    _nextUnfinishedTaskEscalation = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_nextUnfinishedTaskEscalation == null) {
                        String message = "invalid next unfinished task escalation expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_nextUnfinishedTaskEscalation.isBadValue()) {
                        String message = "invalid next unfinished task escalation expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                string = annotation.deadline();
                if (StringUtils.isNotBlank(string)) {
                    _deadlineAddend = TemporalAddend.temporalAddendValueOf(string, validUnits, defaultUnit, min, max);
                    if (_deadlineAddend == null) {
                        String message = "invalid deadline expression \"" + string + "\" at " + getFullName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else if (_deadlineAddend.isBadValue()) {
                        String message = "invalid deadline expression \"" + string + "\" at " + getFullName();
                        message += "; allowed range is \"" + min + "\" to \"" + max + "\"";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                /**/
                _automaticAssumption = annotation.automaticAssumption();
                _builtIn = annotation.builtIn();
                _treeStructureModifier = annotation.treeStructureModifier();
                _jobPriority = Math.max(0, annotation.priority());
                _bplCodeGenEnabled = annotation.bpl().toBoolean(_bplCodeGenEnabled);
                _sqlCodeGenEnabled = annotation.sql().toBoolean(_sqlCodeGenEnabled);
                _serviceable = specified(annotation.serviceable(), _serviceable);
                _overloading = specified(annotation.overloading(), _overloading);
                _annotatedWithProcessOperationClass = true;
            }
        }
    }

    private void annotateConstructionOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ConstructionOperationClass.class);
        if (annotatedClass != null) {
            ConstructionOperationClass annotation = annotatedClass.getAnnotation(ConstructionOperationClass.class);
            if (annotation != null) {
                _constructionType = annotation.type();
                _onConstructionSuccess = annotation.onsuccess();
                _annotatedWithConstructionOperationClass = true;
            }
        }
    }
    // </editor-fold>

}
