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
import adalid.core.exceptions.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
class EntityAtlas {

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    private static final Logger logger = Logger.getLogger(EntityAtlas.class);

    private static final Logger parserLogger = Logger.getLogger(Project.Parser.class);

    private static final String EOL = "\n";

    private static final String TAB = "\t";

    private final Entity _declaringArtifact;

    private final Map<String, Property> _properties = new LinkedHashMap<>();

    private final Map<String, EntityCollection> _entityCollections = new LinkedHashMap<>();

    private final Map<String, Property> _references = new LinkedHashMap<>();

    private final Map<String, Parameter> _parameterReferences = new LinkedHashMap<>();

    private final Map<String, Key> _keys = new LinkedHashMap<>();

    private final Map<String, Step> _steps = new LinkedHashMap<>();

    private final Map<String, Tab> _tabs = new LinkedHashMap<>();

    private final Map<String, View> _views = new LinkedHashMap<>();

    private final Map<String, Instance> _instances = new LinkedHashMap<>();

    private final Map<String, NamedValue> _namedValues = new LinkedHashMap<>();

    private final Map<String, Expression> _expressions = new LinkedHashMap<>();

    private final Map<String, Transition> _transitions = new LinkedHashMap<>();

    private final Map<String, Operation> _operations = new LinkedHashMap<>();

    private final Map<String, Trigger> _triggers = new LinkedHashMap<>();

    private final Map<String, Class<?>> _operationClasses = new LinkedHashMap<>();

    private final Map<String, Field> _operationFields = new LinkedHashMap<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return the properties
     */
    List<Property> getPropertiesList() {
        List<Property> list = new ArrayList<>();
        for (Property value : _properties.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the entity collections
     */
    List<EntityCollection> getEntityCollectionsList() {
        List<EntityCollection> list = new ArrayList<>();
        for (EntityCollection value : _entityCollections.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the references
     */
    List<Property> getReferencesList() {
        List<Property> list = new ArrayList<>();
        for (Property value : _references.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the references
     */
    List<Parameter> getParameterReferencesList() {
        List<Parameter> list = new ArrayList<>();
        for (Parameter value : _parameterReferences.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the keys
     */
    List<Key> getKeysList() {
        List<Key> list = new ArrayList<>();
        for (Key value : _keys.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the steps
     */
    List<Step> getStepsList() {
        List<Step> list = new ArrayList<>();
        for (Step value : _steps.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the tabs
     */
    List<Tab> getTabsList() {
        List<Tab> list = new ArrayList<>();
        for (Tab value : _tabs.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the views
     */
    List<View> getViewsList() {
        List<View> list = new ArrayList<>();
        for (View value : _views.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the instances
     */
    List<Instance> getInstancesList() {
        List<Instance> list = new ArrayList<>();
        for (Instance value : _instances.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the named values
     */
    List<NamedValue> getNamedValuesList() {
        List<NamedValue> list = new ArrayList<>();
        for (NamedValue value : _namedValues.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the expressions
     */
    List<Expression> getExpressionsList() {
        List<Expression> list = new ArrayList<>();
        for (Expression value : _expressions.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
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
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the operations
     */
    List<Operation> getOperationsList() {
        List<Operation> list = new ArrayList<>();
        for (Operation value : _operations.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the triggers
     */
    List<Trigger> getTriggersList() {
        List<Trigger> list = new ArrayList<>();
        for (Trigger value : _triggers.values()) {
            if (value != null && !list.contains(value)) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the properties
     */
    Map<String, Property> getPropertiesMap() {
        return _properties;
    }

    /**
     * @return the entity collections
     */
    Map<String, EntityCollection> getEntityCollectionsMap() {
        return _entityCollections;
    }

    /**
     * @return the references
     */
    Map<String, Property> getReferencesMap() {
        return _references;
    }

    /**
     * @return the references
     */
    Map<String, Parameter> getParameterReferencesMap() {
        return _parameterReferences;
    }

    /**
     * @return the keys
     */
    Map<String, Key> getKeysMap() {
        return _keys;
    }

    /**
     * @return the steps
     */
    Map<String, Step> getStepsMap() {
        return _steps;
    }

    /**
     * @return the tabs
     */
    Map<String, Tab> getTabsMap() {
        return _tabs;
    }

    /**
     * @return the views
     */
    Map<String, View> getViewsMap() {
        return _views;
    }

    /**
     * @return the instances
     */
    Map<String, Instance> getInstancesMap() {
        return _instances;
    }

    /**
     * @return the named values
     */
    Map<String, NamedValue> getNamedValuesMap() {
        return _namedValues;
    }

    /**
     * @return the expressions
     */
    Map<String, Expression> getExpressionsMap() {
        return _expressions;
    }

    /**
     * @return the transitions
     */
    public Map<String, Transition> getTransitionsMap() {
        return _transitions;
    }

    /**
     * @return the operations
     */
    Map<String, Operation> getOperationsMap() {
        return _operations;
    }

    /**
     * @return the triggers
     */
    Map<String, Trigger> getTriggersMap() {
        return _triggers;
    }

    /**
     * @return the operation classes Map
     */
    Map<String, Class<?>> getOperationClassesMap() {
        return _operationClasses;
    }
    // </editor-fold>

    EntityAtlas(Entity declaringArtifact) {
        _declaringArtifact = declaringArtifact;
    }
//
//  /**
//   *
//   */
//  private boolean _prepared;
//
//  private boolean isPrepared() {
//      return _prepared;
//  }

    void prepare() {
        /*
        _prepared = true;
        prepareFields(); // TODO: check all annotations using field lists, etc.
        /**/
    }

    void finalise() {
        finaliseFields();
    }

    void checkOperationClasses() {
        track("checkOperationClasses");
        String key;
        String pattern;
        String remarks;
        Class<?> operationClass;
        Class<?> dac = _declaringArtifact.getClass();
        Class<?>[] classArray = dac.getClasses();
        for (Class<?> clazz : classArray) {
            if (Operation.class.isAssignableFrom(clazz)) {
                key = clazz.getSimpleName();
                if (_operationClasses.containsKey(key)) {
                    operationClass = _operationClasses.get(key);
                    if (operationClass.equals(clazz)) {
                    } else if (operationClass.isAssignableFrom(clazz)) {
                        _operationClasses.put(key, clazz);
                    } else if (clazz.isAssignableFrom(operationClass)) {
                    } else {
                        pattern = "{0} is not assignable from {1}";
                        remarks = MessageFormat.format(pattern, operationClass.getName(), clazz.getName());
                        logOperationReferenceOverride(Level.ERROR, clazz, operationClass, dac, remarks);
                        Project.increaseParserErrorCount();
                    }
                } else {
                    _operationClasses.put(key, clazz);
                    _operationFields.put(key, null);
                }
            }
        }
    }

    void checkOperationFields() {
        track("checkOperationFields");
        String pattern = "there is no field for operation {0}";
        String message;
        Class<?> operationClass;
        Field operationField;
        Set<String> keySet = _operationFields.keySet();
        for (String key : keySet) {
            operationClass = _operationClasses.get(key);
            operationField = _operationFields.get(key);
            if (operationField == null) {
                message = MessageFormat.format(pattern, operationClass.getName());
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void logOperationReferenceOverride(Level level, Class<?> riding, Class<?> ridden, Class<?> declaring, String remarks) {
        if (LogUtils.foul(logger, level)) {
            return;
        }
        String pattern = level.isGreaterOrEqual(Level.ERROR) ? "failed to override" : "overriding";
        pattern += " reference to operation {0} at entity {1}";
        String message = MessageFormat.format(pattern, riding.getSimpleName(), declaring.getSimpleName());
        parserLogger.log(level, message);
        //
        Level detailLevel = level.isGreaterOrEqual(Level.WARN) ? level : Project.getDetailLevel();
        if (LogUtils.foul(logger, detailLevel)) {
            return;
        }
        parserLogger.log(detailLevel, TAB + "overriding class: " + riding.getName());
        parserLogger.log(detailLevel, TAB + "overridden class: " + ridden.getName());
        if (StringUtils.isNotBlank(remarks)) {
            parserLogger.log(detailLevel, TAB + remarks);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="initialiseFields">
    @SuppressWarnings("deprecation")
    void initialiseFields(Class<?> clazz) {
        track("initialiseFields", _declaringArtifact, clazz.getSimpleName());
        Class<?> c;
//      int d, r;
        String name;
        String key;
        String pattern = "there are several fields for operation {0}";
        String message;
        Class<?> type;
//      Class<?> decl;
        Class<?> operationClass;
        Field operationField;
        int modifiers;
        boolean restricted;
        Object o;
        int depth = _declaringArtifact.depth();
        int round = _declaringArtifact.round();
        Class<?>[] classes = new Class<?>[]{
            Property.class, EntityCollection.class, Key.class, Step.class, Tab.class, View.class, Instance.class, NamedValue.class, Expression.class, Transition.class, Operation.class, Trigger.class
        };
        Class<?> dac = _declaringArtifact.getClass();
        Class<?> top = Entity.class;
        int i = ArrayUtils.indexOf(classes, clazz);
        if (i != ArrayUtils.INDEX_NOT_FOUND) {
            c = classes[i];
            for (Field field : XS1.getFields(dac, top, c)) {
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
//              decl = field.getDeclaringClass();
//              if (!c.isAssignableFrom(type)) {
//                  continue;
//              }
                if (c.equals(Expression.class) && Property.class.isAssignableFrom(type)) {
                    continue;
                }
                modifiers = type.getModifiers();
                if (NamedValue.class.isAssignableFrom(type) || Expression.class.isAssignableFrom(type)) {
                    restricted = false;
                } else {
                    restricted = type.isInterface() || Modifier.isAbstract(modifiers);
                }
                restricted = restricted || !Modifier.isPublic(modifiers);
                if (restricted) {
                    continue;
                }
                modifiers = field.getModifiers();
                restricted = Modifier.isPrivate(modifiers);
                if (restricted) {
                    continue;
                }
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                if (Operation.class.isAssignableFrom(type)) {
                    key = type.getSimpleName();
                    operationClass = _operationClasses.get(key);
                    if (operationClass != null) {
                        operationField = _operationFields.get(key);
                        if (operationField == null) {
                            _operationFields.put(key, field);
                        } else {
                            message = MessageFormat.format(pattern, operationClass.getName());
                            logger.warn(message);
                            Project.increaseParserWarningCount();
                        }
                    }
                }
                String errmsg = "failed to create a new instance of field \"" + field + "\" at " + _declaringArtifact;
                try {
                    o = field.get(_declaringArtifact);
                    if (o == null) {
                        logger.debug(message(type, name, o, depth, round));
                        o = XS1.initialiseField(_declaringArtifact, field);
                        if (o == null) {
                            logger.debug(message(type, name, o, depth, round));
//                          throw new RuntimeException(message(type, name, o, depth, round));
                        } else {
                            logger.debug(message(type, name, o, depth, round));
                            field.set(_declaringArtifact, o);
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    throw new InstantiationRuntimeException(errmsg, ex);
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepare...">
    // <editor-fold defaultstate="collapsed" desc="prepareFields">
    /*
    private void prepareFields() {
        String name;
        Class<?> type;
        int modifiers;
        boolean restricted;
        Object o;
        int depth = _declaringArtifact.depth();
        int round = _declaringArtifact.round();
        int index = _declaringArtifact.getStartWith();
        Class<?>[] classes = new Class<?>[]{
            Property.class, Key.class, Step.class, Tab.class, View.class, Instance.class, NamedValue.class, Expression.class, Transition.class, Operation.class, Trigger.class
        };
        Class<?> dac = _declaringArtifact.getClass();
        Class<?> top = Entity.class;
        for (Class<?> c : classes) {
            for (Field field : XS1.getFields(dac, top, c)) {
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
//              if (!c.isAssignableFrom(type)) {
//                  continue;
//              }
                if (c.equals(Expression.class) && Property.class.isAssignableFrom(type)) {
                    continue;
                }
                modifiers = field.getModifiers();
                restricted = Modifier.isPrivate(modifiers);
                if (restricted) {
                    continue;
                }
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                String errmsg = "failed to prepare field \"" + field + "\" at " + _declaringArtifact;
                try {
                    o = field.get(_declaringArtifact);
                    if (o == null) {
                        logger.debug(message(type, name, o, depth, round));
                    } else if (o instanceof Property) {
                        prepareProperty(field, (Property) o);
                    } else if (o instanceof Key) {
                        prepareKey(field, (Key) o);
                    } else if (o instanceof Step) {
                        prepareStep(field, (Step) o);
                    } else if (o instanceof Tab) {
                        prepareTab(field, (Tab) o);
                    } else if (o instanceof View) {
                        prepareView(field, (View) o);
                    } else if (o instanceof Instance) {
                        prepareInstance(field, (Instance) o, index++);
                    } else if (o instanceof Expression) {
                        prepareExpression(field, (Expression) o);
                    } else if (o instanceof Transition) {
                        prepareTransition(field, (Transition) o);
                    } else if (o instanceof Operation) {
                        prepareOperation(field, (Operation) o);
                    } else if (o instanceof Trigger) {
                        prepareTrigger(field, (Trigger) o);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    Project.increaseParserErrorCount();
                }
            }
        }
    }
    /**/
    char prepareFields;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareProperty">
    /*
    @SuppressWarnings("deprecation")
    private void prepareProperty(Field field, Property property) {
        if (field == null || property == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _properties.containsKey(key)) {
            return;
        }
        if (field.isAnnotationPresent(CastingField.class)) {
            XS1.annotateCastingField(field, property);
            return;
        }
        if (property.isNotDeclared()) {
//          property.setDeclared(key, _declaringArtifact, field);
            XS1.declare(property, _declaringArtifact, field);
        }
        if (property instanceof Entity) {
            Entity entity = (Entity) property;
            entity.prepare();
        }
    }
    /**/
    char prepareProperty;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareKey">
    /*
    private void prepareKey(Field field, Key entityKey) {
        if (field == null || entityKey == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _keys.containsKey(key)) {
            return;
        }
        if (entityKey.isNotDeclared()) {
            entityKey.setDeclared(key, _declaringArtifact, field);
        }
    }
    /**/
    char prepareKey;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareStep">
    /*
    private void prepareStep(Field field, Step entityStep) {
        if (field == null || entityStep == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _steps.containsKey(key)) {
            return;
        }
        if (entityStep.isNotDeclared()) {
            entityStep.setDeclared(key, _declaringArtifact, field);
        }
    }
    /**/
    char prepareStep;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareTab">
    /*
    private void prepareTab(Field field, Tab entityTab) {
        if (field == null || entityTab == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _tabs.containsKey(key)) {
            return;
        }
        if (entityTab.isNotDeclared()) {
            entityTab.setDeclared(key, _declaringArtifact, field);
        }
    }
    /**/
    char prepareTab;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareView">
    /*
    private void prepareView(Field field, View entityView) {
        if (field == null || entityView == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _views.containsKey(key)) {
            return;
        }
        if (entityView.isNotDeclared()) {
            entityView.setDeclared(key, _declaringArtifact, field);
        }
    }
    /**/
    char prepareView;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareInstance">
    /*
    private void prepareInstance(Field field, Instance entityInstance, int index) {
        if (field == null || entityInstance == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _instances.containsKey(key)) {
            return;
        }
        if (entityInstance.isNotDeclared()) {
            entityInstance.setDeclared(key, _declaringArtifact, field);
            entityInstance.annotate(); // 2020/07/28 - do not annotate while instantiating
        }
    }
    /**/
    char prepareInstance;
    // </editor-fold>

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
//          expression.setDeclared(key, _declaringArtifact, field);
            XS1.declare(expression, _declaringArtifact, field);
        }
    }
    /**/
    char prepareExpression;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareTransition">
    /*
    private void prepareTransition(Field field, Transition transition) {
        if (field == null || transition == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _transitions.containsKey(key)) {
            return;
        }
        if (transition.isNotDeclared()) {
            transition.setDeclared(key, _declaringArtifact, field);
        }
    }
    /**/
    char prepareTransition;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareOperation">
    /*
    private void prepareOperation(Field field, Operation operation) {
        if (field == null || operation == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _operations.containsKey(key)) {
            return;
        }
        if (operation.isNotDeclared()) {
            operation.setDeclared(key, _declaringArtifact, field);
            operation.annotate(); // 2020/07/28 - do not annotate while instantiating
            operation.prepare();
        }
    }
    /**/
    char prepareOperation;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareTrigger">
    /*
    private void prepareTrigger(Field field, Trigger trigger) {
        if (field == null || trigger == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _triggers.containsKey(key)) {
            return;
        }
        if (trigger.isNotDeclared()) {
            trigger.setDeclared(key, _declaringArtifact, field);
        }
    }
    /**/
    char prepareTrigger;
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseFields">
    private void finaliseFields() {
        String name;
        Class<?> type;
        int modifiers;
        boolean restricted;
        Object o;
        int depth = _declaringArtifact.depth();
        int round = _declaringArtifact.round();
        int index = _declaringArtifact.getStartWith();
        Class<?>[] classes = new Class<?>[]{
            Property.class, EntityCollection.class, Key.class, Step.class, Tab.class, View.class, Instance.class, NamedValue.class, Expression.class, Transition.class, Operation.class, Trigger.class
        };
        Class<?> dac = _declaringArtifact.getClass();
        Class<?> top = Entity.class;
        for (Class<?> c : classes) {
            for (Field field : XS1.getFields(dac, top, c)) {
                field.setAccessible(true);
                logger.trace(field);
                name = field.getName();
                type = field.getType();
//              if (!c.isAssignableFrom(type)) {
//                  continue;
//              }
                if (c.equals(Expression.class) && Property.class.isAssignableFrom(type)) {
                    continue;
                }
                modifiers = field.getModifiers();
                restricted = Modifier.isPrivate(modifiers);
                if (restricted) {
                    continue;
                }
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                String errmsg = "failed to finalize field \"" + field + "\" at " + _declaringArtifact;
                try {
                    o = field.get(_declaringArtifact);
                    if (o == null) {
                        logger.debug(message(type, name, o, depth, round));
                    } else if (o instanceof Property) {
                        finaliseProperty(field, (Property) o);
                    } else if (o instanceof EntityCollection) {
                        finaliseEntityCollection(field, (EntityCollection) o);
                    } else if (o instanceof Key) {
                        finaliseKey(field, (Key) o);
                    } else if (o instanceof Step) {
                        finaliseStep(field, (Step) o);
                    } else if (o instanceof Tab) {
                        finaliseTab(field, (Tab) o);
                    } else if (o instanceof View) {
                        finaliseView(field, (View) o);
                    } else if (o instanceof Instance) {
                        finaliseInstance(field, (Instance) o, index++);
                    } else if (o instanceof NamedValue) {
                        finaliseNamedValue(field, (NamedValue) o);
                    } else if (o instanceof Expression) {
                        finaliseExpression(field, (Expression) o);
                    } else if (o instanceof Transition) {
                        finaliseTransition(field, (Transition) o);
                    } else if (o instanceof Operation) {
                        finaliseOperation(field, (Operation) o);
                    } else if (o instanceof Trigger) {
                        finaliseTrigger(field, (Trigger) o);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private int referenceIndex = 0;

    int referencePropertiesCount() {
        return referenceIndex;
    }

    // <editor-fold defaultstate="collapsed" desc="finaliseProperty">
    @SuppressWarnings("deprecation")
    private void finaliseProperty(Field field, Property property) {
        if (field == null || property == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _properties.containsKey(key)) {
            return;
        }
        if (field.isAnnotationPresent(CastingField.class)) {
//          if (!isPrepared()) {
            XS1.annotateCastingField(field, property);
//          }
            return;
        }
        if (property.isNotDeclared()) {
//          property.setDeclared(key, _declaringArtifact, field);
            XS1.declare(property, _declaringArtifact, field);
        }
        if (property instanceof Entity) {
            Entity entity = (Entity) property;
            referenceIndex++;
            XS1.setReferenceIndex(entity, referenceIndex);
            if (!entity.isFinalised()) {
                entity.finalise();
            }
            boolean x = property.depth() > 1 || property.isClassInPath(Operation.class);
            if (!x) {
                Entity root = entity.getRoot();
                if (root != null) {
                    root.getReferencesMap().put(property.getPathString(), property);
                }
            }
        } else if (property instanceof Primitive) {
            Primitive primitive = (Primitive) property;
            if (!primitive.isFinalised()) {
                primitive.finalise();
            }
        }
        _properties.put(key, property);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseEntityCollection">
    @SuppressWarnings("deprecation")
    private void finaliseEntityCollection(Field field, EntityCollection collection) {
        if (field == null || collection == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _entityCollections.containsKey(key)) {
            return;
        }
        if (collection.isNotDeclared()) {
//          collection.setDeclared(key, _declaringArtifact, field);
            XS1.declare(collection, _declaringArtifact, field);
        }
        if (!collection.isFinalised()) {
            collection.finalise();
        }
        _entityCollections.put(key, collection);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseKey">
    private void finaliseKey(Field field, Key entityKey) {
        if (field == null || entityKey == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _keys.containsKey(key)) {
            return;
        }
        if (entityKey.isNotDeclared()) {
            entityKey.setDeclared(key, _declaringArtifact, field);
        }
        if (!entityKey.isFinalised()) {
            entityKey.finalise();
        }
        _keys.put(key, entityKey);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseStep">
    private void finaliseStep(Field field, Step entityStep) {
        if (field == null || entityStep == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _steps.containsKey(key)) {
            return;
        }
        if (entityStep.isNotDeclared()) {
            entityStep.setDeclared(key, _declaringArtifact, field);
        }
        if (!entityStep.isFinalised()) {
            entityStep.finalise();
        }
        _steps.put(key, entityStep);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseTab">
    private void finaliseTab(Field field, Tab entityTab) {
        if (field == null || entityTab == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _tabs.containsKey(key)) {
            return;
        }
        if (entityTab.isNotDeclared()) {
            entityTab.setDeclared(key, _declaringArtifact, field);
        }
        if (!entityTab.isFinalised()) {
            entityTab.finalise();
        }
        _tabs.put(key, entityTab);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseView">
    private void finaliseView(Field field, View entityView) {
        if (field == null || entityView == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _views.containsKey(key)) {
            return;
        }
        if (entityView.isNotDeclared()) {
            entityView.setDeclared(key, _declaringArtifact, field);
        }
        if (!entityView.isFinalised()) {
            entityView.finalise();
        }
        _views.put(key, entityView);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseInstance">
    private void finaliseInstance(Field field, Instance entityInstance, int index) {
        if (field == null || entityInstance == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _instances.containsKey(key)) {
            return;
        }
        if (entityInstance.isNotDeclared()) {
            entityInstance.setDeclared(key, _declaringArtifact, field);
            entityInstance.annotate(); // 2020/07/28 - do not annotate while instantiating
        }
        if (!entityInstance.isFinalised()) {
            entityInstance.finalise(index);
        }
        _instances.put(key, entityInstance);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseNamedValue">
    private void finaliseNamedValue(Field field, NamedValue namedValue) {
        if (field == null || namedValue == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _namedValues.containsKey(key)) {
            return;
        }
        _namedValues.put(key, namedValue);
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
//          expression.setDeclared(key, _declaringArtifact, field);
            XS1.declare(expression, _declaringArtifact, field);
        }
        if (!expression.isFinalised()) {
            expression.finalise();
        }
        _expressions.put(key, expression);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseTransition">
    private void finaliseTransition(Field field, Transition transition) {
        if (field == null || transition == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _transitions.containsKey(key)) {
            return;
        }
        if (transition.isNotDeclared()) {
            transition.setDeclared(key, _declaringArtifact, field);
        }
        _transitions.put(key, transition);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseOperation">
    private void finaliseOperation(Field field, Operation operation) {
        if (field == null || operation == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _operations.containsKey(key)) {
            return;
        }
        if (operation.isNotDeclared()) {
            operation.setDeclared(key, _declaringArtifact, field);
            operation.annotate(); // 2020/07/28 - do not annotate while instantiating
        }
        if (!operation.isFinalised()) {
            operation.finalise();
        }
        _operations.put(key, operation);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseTrigger">
    private void finaliseTrigger(Field field, Trigger trigger) {
        if (field == null || trigger == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _triggers.containsKey(key)) {
            return;
        }
        if (trigger.isNotDeclared()) {
            trigger.setDeclared(key, _declaringArtifact, field);
        }
        _triggers.put(key, trigger);
    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = "";
        if (fields || verbose) {
            string += fee + tab + "properties" + faa + _properties.size() + foo;
            string += fee + tab + "references" + faa + _references.size() + foo;
            string += fee + tab + "keys" + faa + _keys.size() + foo;
            string += fee + tab + "steps" + faa + _steps.size() + foo;
            string += fee + tab + "tabs" + faa + _tabs.size() + foo;
            string += fee + tab + "views" + faa + _views.size() + foo;
            string += fee + tab + "instances" + faa + _instances.size() + foo;
            string += fee + tab + "values" + faa + _namedValues.size() + foo;
            string += fee + tab + "expressions" + faa + _expressions.size() + foo;
            string += fee + tab + "transitions" + faa + _transitions.size() + foo;
            string += fee + tab + "operations" + faa + _operations.size() + foo;
            string += fee + tab + "triggers" + faa + _triggers.size() + foo;
            if (verbose) {
            }
        }
        return string;
    }

    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        return mapsToString(n, key, verbose, fields, maps, true);
    }

    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps, boolean d0) {
        String string = "";
        if (maps || verbose) {
            for (String clave : _properties.keySet()) {
                Property p = _properties.get(clave);
                if (p instanceof Entity) {
                    Entity e = (Entity) p;
                    string += e.toString(n + 1, clave, verbose && d0, fields, maps & e.round() == 0);
                } else if (p != null) {
                    string += p.toString(n + 1, clave, verbose && d0, fields, maps);
                }
            }
            for (String clave : _keys.keySet()) {
                Key valor = _keys.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _steps.keySet()) {
                Step valor = _steps.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _tabs.keySet()) {
                Tab valor = _tabs.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _views.keySet()) {
                View valor = _views.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _instances.keySet()) {
                Instance valor = _instances.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _namedValues.keySet()) {
                NamedValue valor = _namedValues.get(clave);
                string += valor.name();
            }
            for (String clave : _expressions.keySet()) {
                Expression valor = _expressions.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _transitions.keySet()) {
                Transition valor = _transitions.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
            for (String clave : _operations.keySet()) {
                Operation valor = _operations.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _triggers.keySet()) {
                Trigger valor = _triggers.get(clave);
                string += valor.toString(n + 1, clave, verbose && d0, fields, maps);
            }
            for (String clave : _references.keySet()) {
                Property p = _references.get(clave);
                if (p instanceof Entity) {
                    Entity e = (Entity) p;
                    string += e.toString(n + 1, "<" + clave + ">", false, false, false);
                } else if (p != null) {
                    string += p.toString(n + 1, "¿" + clave + "?", false, false, false);
                }
            }
        }
        return string;
    }
    // </editor-fold>

    private String message(Class<?> type, String name, Object value, int depth, int round) {
        String s1 = StringUtils.repeat(" ", 0 + 4 * depth);
        String s2 = _declaringArtifact + "," + depth + "," + round;
        String s3 = type.getSimpleName() + " " + name + "=" + value;
        String s4 = s1 + s2 + " " + s3;
        return s4;
    }

    private void track(String method, Object... parameters) {
        Artifact da = _declaringArtifact;
        TLC.getProject().getParser().track(da.depth(), da.round(), da.getClassPath(), method, parameters);
    }

}
