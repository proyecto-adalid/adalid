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
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import adalid.core.sql.*;
import adalid.core.wrappers.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractExpression extends AbstractArtifact implements NaryExpression {

    private static final Logger logger = Logger.getLogger(Expression.class);

    private static final String EOL = "\n";

    private static final String TAB = "\t";

    /**
     *
     */
    private Class<?> _dataType;

    /**
     *
     */
    private Expression _parentExpression;

    /**
     *
     */
    private Field _foreignExpressionField;

    /**
     *
     */
    private final Map<ExpressionUsage, Set<Artifact>> _verifiedUsages = new LinkedHashMap<>();

    public List<Operation> getInitialStateBusinessOperationsList() {
        List<Operation> list = new ArrayList<>();
        if (this instanceof State) {
            Entity declaringEntity = getDeclaringEntity();
            if (declaringEntity != null) {
                State state;
                for (Operation operation : declaringEntity.getBusinessOperationsList()) {
                    if (OperationKind.INSTANCE.equals(operation.getOperationKind())) {
                        for (Transition transition : operation.getTransitionsList()) {
                            if (transition != null) {
                                state = transition.getX();
                                if (this.equals(state) && !list.contains(operation)) {
                                    list.add(operation);
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    public List<Operation> getFinalStateBusinessOperationsList() {
        List<Operation> list = new ArrayList<>();
        if (this instanceof State) {
            Entity declaringEntity = getDeclaringEntity();
            if (declaringEntity != null) {
                State state;
                for (Operation operation : declaringEntity.getBusinessOperationsList()) {
                    if (OperationKind.INSTANCE.equals(operation.getOperationKind())) {
                        for (Transition transition : operation.getTransitionsList()) {
                            if (transition != null) {
                                state = transition.getY();
                                if (this.equals(state) && !list.contains(operation)) {
                                    list.add(operation);
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    // <editor-fold defaultstate="collapsed" desc="commented out">
    /**
     *
     */
//  private Map<String, Expression> _expressions = new LinkedHashMap<>();
//
//  /**
//   * @return the expressions
//   */
//  public List<Expression> getExpressionsList() {
//      List<Expression> list = new ArrayList<>();
//      for (Expression value : _expressions.values()) {
//          if (value != null) {
//              list.add(value);
//          }
//      }
//      return list;
//  }
//
//  /**
//   * @return the expressions
//   */
//  public Map<String, Expression> getExpressionsMap() {
//      return _expressions;
//  }
//  </editor-fold>
//
    /**
     * @return the data type
     */
    @Override
    public Class<?> getDataType() {
        return _dataType;
    }

    /**
     * @param dataType the type to set
     */
    protected void setDataType(Class<?> dataType) {
        _dataType = dataType;
    }

    /**
     * @return the parent expression
     */
    @Override
    public Expression getParentExpression() {
        return _parentExpression;
    }

    /**
     * @param parentExpression the parent expression to set
     */
    private void setParentExpression(Expression parentExpression) {
        if (parentExpression == null || getDeclaringArtifact() == null) {
            _parentExpression = parentExpression;
            Object[] operands = getOperands();
            if (operands != null) {
                for (Object operand : operands) {
                    if (operand instanceof AbstractExpression childExpression) {
                        childExpression.setParentExpression(this);
                    }
                }
            }
        }
    }

    /**
     * @return the foreign expression field
     */
    public Field getForeignExpressionField() {
        return _foreignExpressionField;
    }

    /**
     * @param foreignExpressionField the foreign expression field to set
     */
    protected void setForeignExpressionField(Field foreignExpressionField) {
        _foreignExpressionField = foreignExpressionField;
    }

    /**
     * @return the foreign expression name
     */
    public String getForeignExpressionName() {
        return _foreignExpressionField == null ? null : _foreignExpressionField.getName();
    }

    /**
     * @return the foreign expression type
     */
    public Class<?> getForeignExpressionType() {
        return _foreignExpressionField == null ? null : _foreignExpressionField.getDeclaringClass();
    }

    /**
     * @return the verified usages
     */
    @Override
    public List<ExpressionUsage> getVerifiedUsages() {
        return new ArrayList<>(_verifiedUsages.keySet());
    }

    /**
     * @return the verified usages
     */
    @Override
    public Map<ExpressionUsage, Set<Artifact>> getVerifiedUsageMap() {
        return _verifiedUsages;
    }

    /**
     * @param usage the expression usage
     * @return the verified user list
     */
    @Override
    public Set<Artifact> getVerifiedUsers(ExpressionUsage usage) {
        Set<Artifact> users = _verifiedUsages.get(usage);
        if (users == null) {
            users = new LinkedHashSet<>();
            _verifiedUsages.put(usage, users);
        }
        return users;
    }

    /**
     * @param usage the expression usage
     * @param user the artifact that makes use of this expression
     * @return the verified user list
     */
    @Override
    public Set<Artifact> addVerifiedUsage(ExpressionUsage usage, Artifact user) {
        Set<Artifact> users = getVerifiedUsers(usage);
        if (user != null) {
            users.add(user);
        }
        return users;
    }

    /**
     *
     */
    private boolean _logicalTautology;

//  @Override
    public boolean isLogicalTautology() {
        return _logicalTautology;
    }

//  @Override
    public void setLogicalTautology(boolean tautology) {
        if (this instanceof BooleanExpression) {
            _logicalTautology = tautology;
        }
    }

    private Property _transitionTimestamp;

//  @Override
    public Property getTransitionTimestamp() {
        return _transitionTimestamp;
    }

//  @Override
    public void setTransitionTimestamp(TimestampProperty timestamp) {
        _transitionTimestamp = timestamp;
    }

//  @Override
    public void setTransitionDate(DateProperty date) {
        _transitionTimestamp = date;
    }

    /**
     * @return the strings set
     */
    @Override
    public Set<String> getStringsSet() {
        Set<String> set = new LinkedHashSet<>();
        Object[] operands = getOperands();
        if (operands != null) {
            for (Object operand : operands) {
                if (operand instanceof String) {
                    set.add(operand.toString());
                } else if (operand instanceof Expression expression) {
                    set.addAll(expression.getStringsSet());
                }
            }
        }
        return set;
    }

    /**
     * @return the referenced columns list
     */
    @Override
    public List<Property> getReferencedColumnsList() {
        return new ArrayList<>(getReferencedColumnsMap().values());
    }

    public List<Property> getCorrespondingReferencedColumnsList(QueryTable searchQueryTable1, QueryTable searchQueryTable2) {
        Level level = Level.TRACE;
        String trace = getTrace();
        logger.log(level, getFullName() + "/" + trace);
        List<Property> referencedColumnsList1 = getReferencedColumnsList();
        List<Property> referencedColumnsList2 = new ArrayList<>();
        for (Property property1 : referencedColumnsList1) {
            String alias = searchQueryTable1.getSqlAlias(property1);
            logger.log(level, "\t-" + searchQueryTable1.getName() + "/" + alias + " -> " + property1.getFullName());
            Property property2 = searchQueryTable2.getProperty(alias);
            logger.log(level, "\t+" + searchQueryTable2.getName() + "/" + alias + " -> " + (property2 == null ? null : property2.getFullName()));
            if (property2 != null) {
                referencedColumnsList2.add(property2);
            }
        }
        return referencedColumnsList2;
    }

    /**
     * @return the referenced columns map
     */
    @Override
    public Map<String, Property> getReferencedColumnsMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        Object[] operands = getOperands();
        if (operands != null) {
            for (Object operand : operands) {
                if (operand instanceof Property property) {
                    map.put(property.getPathString(), property);
                } else if (operand instanceof Expression expression) {
                    map.putAll(expression.getReferencedColumnsMap());
                }
            }
        }
        return map;
    }

    /**
     * @return the referenced joins list
     */
    @Override
    public List<QueryJoin> getReferencedJoinsList() {
        return new ArrayList<>(getReferencedJoinsMap().values());
    }

    /**
     * @param queryTable query table
     * @return the referenced joins list
     */
    @Override
    public List<QueryJoin> getReferencedJoinsList(QueryTable queryTable) {
        return new ArrayList<>(getReferencedJoinsMap(queryTable).values());
    }

    /**
     * @return the referenced joins map
     */
    @Override
    public Map<String, QueryJoin> getReferencedJoinsMap() {
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity instanceof PersistentEntity pent) {
            QueryTable queryTable = pent.getQueryTable();
            return getReferencedJoinsMap(queryTable);
        }
        return new LinkedHashMap<>();
    }

    /**
     * @param queryTable query table
     * @return the referenced joins map
     */
    @Override
    public Map<String, QueryJoin> getReferencedJoinsMap(QueryTable queryTable) {
        List<Property> columns = getReferencedColumnsList();
        return queryTable.getReferencedJoinsMap(columns);
    }

    Set<String> crossReferencedExpressionsSet;

    /**
     * @return the referenced expressions map
     */
    @Override
    public Set<String> getCrossReferencedExpressionsSet() {
        return getCrossReferencedExpressionsSet(getDeclaringEntity());
    }

    /**
     * @param declaringEntity declaring entity
     * @return the referenced expressions map
     */
    @Override
    public Set<String> getCrossReferencedExpressionsSet(Entity declaringEntity) {
        if (crossReferencedExpressionsSet == null) {
            crossReferencedExpressionsSet = new LinkedHashSet<>();
            if (declaringEntity != null) {
                Object[] operands = getOperands();
                if (operands != null) {
                    Entity expressionDeclaringEntity;
                    String key;
                    for (Object operand : operands) {
                        if (operand instanceof Primitive) {
                            continue;
                        }
                        if (operand instanceof Expression expression) {
                            expressionDeclaringEntity = expression.getDeclaringEntity();
                            if (expressionDeclaringEntity == null || expressionDeclaringEntity.equals(declaringEntity)) {
                                crossReferencedExpressionsSet.addAll(expression.getCrossReferencedExpressionsSet(declaringEntity));
                            } else {
                                key = expression.getCrossReferencedExpressionsKey();
                                if (key != null) {
                                    crossReferencedExpressionsSet.add(key);
                                }
                            }
                        }
                    }
                }
            }
        }
        return crossReferencedExpressionsSet;
    }

    @Override
    public String getCrossReferencedExpressionsKey() {
        Entity declaringEntity = getDeclaringEntity();
        String key = declaringEntity == null ? null : declaringEntity.getRoot().getName() + "." + getName();
        return key;
    }

    @Override
    public boolean isCrossReferencedExpression() {
        return TLC.getProject().containsCrossReferencedExpression(this);
    }

    /**
     * @return true if is a TemporalExpression; otherwise false
     */
    @Override
    public boolean isSingleEntityExpression() {
        Entity declaringEntity = getDeclaringEntity();
        return declaringEntity != null && isSingleEntityExpression(declaringEntity);
    }

    /**
     * @param declaringEntity declaring entity
     * @return true if is a TemporalExpression; otherwise false
     */
    @Override
    public boolean isSingleEntityExpression(Entity declaringEntity) {
        if (declaringEntity == null) {
            return false;
        } else {
            Object[] operands = getOperands();
            if (operands != null) {
                Entity expressionDeclaringEntity;
                for (Object operand : operands) {
                    if (operand instanceof Expression expression) {
                        if (operand instanceof RowsAggregateX) {
                            return false;
                        }
                        expressionDeclaringEntity = expression.getDeclaringEntity();
                        if (expressionDeclaringEntity == null || expressionDeclaringEntity.equals(declaringEntity)) {
                            if (expression.isSingleEntityExpression(declaringEntity)) {
                                continue;
                            }
                        }
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * @return true if is a BooleanExpression; otherwise false
     */
    public boolean isBooleanExpression() {
        return this instanceof BooleanExpression;
    }

    /**
     * @return true if is a CharacterExpression; otherwise false
     */
    public boolean isCharacterExpression() {
        return this instanceof CharacterExpression;
    }

    /**
     * @return true if is a EntityExpression; otherwise false
     */
    public boolean isEntityExpression() {
        return this instanceof EntityExpression;
    }

    /**
     * @return true if is a NumericExpression; otherwise false
     */
    public boolean isNumericExpression() {
        return this instanceof NumericExpression;
    }

    /**
     * @return true if is a TemporalExpression; otherwise false
     */
    public boolean isTemporalExpression() {
        return this instanceof TemporalExpression;
    }

    /**
     * @return true if is a ComparisonX; otherwise false
     */
    public boolean isComparisonExpression() {
        return this instanceof ComparisonX;
    }

    /**
     * @return true if is a ConditionalX; otherwise false
     */
    public boolean isConditionalExpression() {
        return this instanceof ConditionalX;
    }

    /**
     * @return true if is a DataAggregateX; otherwise false
     */
    public boolean isDataAggregateExpression() {
        return this instanceof DataAggregateX;
    }

    /**
     * @return true if is a OrderedPairX; otherwise false
     */
    public boolean isOrderedPairExpression() {
        return this instanceof OrderedPairX;
    }

    /**
     * @return true if is a RowsAggregateX; otherwise false
     */
    public boolean isRowsAggregateExpression() {
        return this instanceof RowsAggregateX;
    }

    /**
     * @return true if is a ScalarX; otherwise false
     */
    public boolean isScalarExpression() {
        return this instanceof ScalarX;
    }

    /**
     * @return true if is a TemporalExpression; otherwise false
     */
    public boolean isVariantExpression() {
        return this instanceof VariantX;
    }

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
//          finaliseFields();
            checkArguments();
            checkStateAttributes();
            setParentExpression(null);
        }
        return ok;
    }

    protected void checkArguments() {
        String name = getName();
        Artifact da = getDeclaringArtifact();
        boolean b1 = da instanceof Entity && da.depth() == 0;
        boolean b2 = da instanceof Operation;
        Entity declaringEntity = b1 ? (Entity) da : b2 ? ((Operation) da).getDeclaringEntity() : null;
//      boolean special = declaringEntity != null && declaringEntity.isSpecialExpressionsWarningsEnabled();
        boolean unusual = declaringEntity != null && declaringEntity.isUnusualExpressionsWarningsEnabled();
        Level level = unusual ? Project.getUnusualExpressionLevel() : null;
        if (LogUtils.fair(level)) {
            String expression = StringUtils.isBlank(name) ? " of " + da : " " + da + "." + name;
            checkUnusualArguments(expression, level);
        }
    }

    protected void checkUnusualArguments(String expression, Level level) {
        for (Object operand : getOperands()) {
            if (operand instanceof Instance instance) {
                checkUnusualInstance(expression, level, instance);
            } else if (operand instanceof AbstractExpression abstractExpression) {
                abstractExpression.checkUnusualArguments(expression, level);
            }
        }
    }

    protected void checkUnusualInstance(String expression, Level level, Instance argument) {
        boolean ok = argument.getDeclaringEntity() instanceof EnumerationEntity || argument.isUsualArgumentInExpressions();
        if (!ok) {
            String message = "unusual " + argument + " in expression" + expression + "; expressions usually include only instances of enumeration entities";
            logger.log(level, message);
            if (Level.ERROR.equals(level) || Level.FATAL.equals(level)) {
                Project.increaseParserErrorCount();
            } else if (Level.WARN.equals(level)) {
                Project.increaseParserWarningCount();
            }
        }
    }

    protected void checkStateAttributes() {
        if (this instanceof State) {
            if (_transitionTimestamp != null) {
                Artifact myDA = getDeclaringArtifact();
                if (myDA instanceof Entity myEntity && myEntity.depth() == 0) {
                    String myEntityName = myEntity.getName();
                    if (myEntityName != null) {
                        Artifact tsDA = _transitionTimestamp.getDeclaringArtifact();
                        if (!(tsDA instanceof Entity tsEntity && tsEntity.depth() == 0 && tsEntity.isAssignableFrom(myEntity))) {
                            String me = toString("State");
                            String ts = _transitionTimestamp.getDataType().getSimpleName();
                            String message = "transition " + ts + " of " + me + " is not a property of " + myEntityName;
                            logger.error(message);
                            Project.increaseParserErrorCount();
                        }
                    }
                }
            }
        }
    }

    protected void copyDataType(Object object) {
        setDataType(getObjectDataType(object));
    }

    protected Class<?> getObjectDataType(Object object) {
        if (object == null) {
            return Object.class;
        } else if (object instanceof TypedArtifact artifact) {
            return artifact.getDataType();
        } else if (object instanceof Instance instance) { // since 20201209
            Entity entity = instance.getDeclaringEntity();
            return entity != null ? entity.getDataType() : object.getClass();
        } else if (object instanceof SpecialValue special) {
            return special.getDataType();
        } else {
            return object.getClass();
        }
    }

    public Expression getForeignExpression() {
        if (_foreignExpressionField == null) {
            return null;
        }
        String errmsg = "failed to get foreign expression \"" + _foreignExpressionField + "\" at " + this;
        Class<?> declaringClass = _foreignExpressionField.getDeclaringClass();
        Entity declaringEntity = declaringClass == null ? null : getDeclaringEntity();
        Project declaringProject = declaringEntity == null ? null : declaringEntity.getDeclaringProject();
        Entity foreignEntity = declaringProject == null ? null : declaringProject.getEntity(declaringClass);
        if (foreignEntity == null) {
            logger.error(errmsg);
            logger.error(TAB + "foreign expression field declaring class = " + declaringClass);
            logger.error(TAB + "declaring entity = " + declaringEntity);
            logger.error(TAB + "declaring project = " + declaringProject);
            logger.error(TAB + "foreign entity = " + foreignEntity);
            Project.increaseParserErrorCount();
        } else {
            try {
                Object foreignExpression = _foreignExpressionField.get(foreignEntity);
                if (foreignExpression instanceof Expression expression) {
                    return expression;
                }
                logger.error(errmsg);
                logger.error(TAB + _foreignExpressionField + " = " + foreignExpression);
                Project.increaseParserErrorCount();
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                Project.increaseParserErrorCount();
            }
        }
        return null;
    }
//
    // <editor-fold defaultstate="collapsed" desc="finaliseFields">
//  private void finaliseFields() {
//      String name;
//      Class<?> type;
//      int modifiers;
//      boolean expressionArray;
//      boolean restricted;
//      for (Field field : XS.getFields(getClass(), Expression.class)) { // getClass().getDeclaredFields()
//          field.setAccessible(true);
//          logger.trace(field.toString());
//          name = field.getName();
//          type = field.getType();
//          expressionArray = type.equals(Object[].class) && field.isAnnotationPresent(ExpressionArray.class);
//          if (!expressionArray && !Expression.class.isAssignableFrom(type)) {
//              continue;
//          }
//          modifiers = field.getModifiers();
//          restricted = Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
//          if (restricted) {
//              continue;
//          }
//          String errmsg = "failed to initialize field \"" + field + "\" at " + this;
//          try {
//              Object value = field.get(this);
//              if (value == null) {
//                  logger.debug(name + "==null");
//              } else if (value instanceof Expression) {
//                  finaliseExpression(field, (Expression) value);
//              } else if (value instanceof Object[]) {
//                  Object[] operands = (Object[]) value;
//                  for (int i = 0; i < operands.length; i++) {
//                      if (operands[i] instanceof Expression) {
//                          finaliseExpression(field, (Expression) operands[i], i);
//                      }
//                  }
//              }
//          } catch (IllegalArgumentException ex) {
//              logger.error(errmsg, ThrowableUtils.getCause(ex));
//              Project.increaseParserErrorCount();
//          } catch (IllegalAccessException ex) {
//              logger.error(errmsg, ThrowableUtils.getCause(ex));
//              Project.increaseParserErrorCount();
//          }
//      }
//  }
//
    // <editor-fold defaultstate="collapsed" desc="finaliseExpression">
//  private void finaliseExpression(Field field, Expression expression) {
//      String key = field.getName();
//      if (_expressions.containsKey(key)) {
//          return;
//      }
//      finaliseExpression(field, expression, -1);
//      _expressions.put(key, expression);
//  }
//
//  private void finaliseExpression(Field field, Expression expression, int index) {
//      if (field == null || expression == null) {
//          return;
//      }
//      String name = index < 0 ? field.getName() : field.getName() + "_" + index;
//      if (expression.isNotDeclared()) {
//          expression.setDeclared(name, this, field, index);
//          if (expression != this) {
//              expression.finalise();
//          }
//      }
//  }
    // </editor-fold>
    // </editor-fold>

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends ExpressionWrapper> getDefaultWrapperClass() {
        return ExpressionWrapper.class;
    }

    @Override
    public String getLocalizedLabel(Locale locale) {
        String localizedLabel = super.getLocalizedLabel(locale);
        if (localizedLabel == null) {
            Instance instance = getMatchingInstance();
            if (instance != null) {
                localizedLabel = instance.getLocalizedLabel(locale);
                if (localizedLabel == null) {
                    instance = getStateInstance();
                    if (instance != null) {
                        localizedLabel = instance.getInstanceKeyLabel(locale);
                    }
                }
            }
        }
        return localizedLabel;
    }

    @Override
    public String getLocalizedShortLabel(Locale locale) {
        String localizedShortLabel = super.getLocalizedShortLabel(locale);
        if (localizedShortLabel == null) {
            Instance instance = getMatchingInstance();
            if (instance != null) {
                localizedShortLabel = instance.getLocalizedShortLabel(locale);
                if (localizedShortLabel == null) {
                    instance = getStateInstance();
                    if (instance != null) {
                        localizedShortLabel = instance.getInstanceKeyLabel(locale);
                    }
                }
            }
        }
        return localizedShortLabel;
    }

    @Override
    public String getLocalizedCollectionLabel(Locale locale) {
        String localizedCollectionLabel = super.getLocalizedCollectionLabel(locale);
        if (localizedCollectionLabel == null) {
            Instance instance = getMatchingInstance();
            if (instance != null) {
                localizedCollectionLabel = instance.getLocalizedCollectionLabel(locale);
            }
        }
        return localizedCollectionLabel;
    }

    @Override
    public String getLocalizedCollectionShortLabel(Locale locale) {
        String localizedCollectionShortLabel = super.getLocalizedCollectionShortLabel(locale);
        if (localizedCollectionShortLabel == null) {
            Instance instance = getMatchingInstance();
            if (instance != null) {
                localizedCollectionShortLabel = instance.getLocalizedCollectionShortLabel(locale);
            }
        }
        return localizedCollectionShortLabel;
    }

    @Override
    public String getLocalizedDescription(Locale locale) {
        String localizedDescription = super.getLocalizedDescription(locale);
        if (localizedDescription == null) {
            Instance instance = getMatchingInstance();
            if (instance != null) {
                localizedDescription = instance.getLocalizedDescription(locale);
                if (localizedDescription == null) {
                    localizedDescription = instance.getInstanceKeyDescription(locale);
                }
            }
        }
        return localizedDescription;
    }

    @Override
    public String getLocalizedShortDescription(Locale locale) {
        String localizedShortDescription = super.getLocalizedShortDescription(locale);
        if (localizedShortDescription == null) {
            Instance instance = getMatchingInstance();
            if (instance != null) {
                localizedShortDescription = instance.getLocalizedShortDescription(locale);
                if (localizedShortDescription == null) {
                    localizedShortDescription = instance.getInstanceKeyDescription(locale);
                }
            }
        }
        return localizedShortDescription;
    }

    private Instance _matchingInstance;

    private Instance getMatchingInstance() {
        if (_matchingInstance == null) {
            if (this instanceof BooleanComparisonX bcx) {
                if (ComparisonOp.EQ.equals(bcx.getOperator()) && bcx.getX() instanceof Entity entity && bcx.getY() instanceof Instance instance) {
                    if (sameDataTypeSimpleName(entity, instance.getDeclaringEntity())) {
                        _matchingInstance = instance;
                    }
                }
            }
        }
        return _matchingInstance;
    }

    private Instance _stateInstance;

    private Instance getStateInstance() {
        if (_stateInstance == null) {
            if (this instanceof BooleanComparisonX bcx) {
                Entity declaringEntity = getDeclaringEntity();
                if (declaringEntity != null) {
                    Entity stateProperty = declaringEntity.getStateProperty();
                    if (stateProperty != null) {
                        if (ComparisonOp.EQ.equals(bcx.getOperator()) && bcx.getX() == stateProperty && bcx.getY() instanceof Instance instance) {
                            if (sameDataTypeSimpleName(stateProperty, instance.getDeclaringEntity())) {
                                _stateInstance = instance;
                            }
                        }
                    }
                }
            }
        }
        return _stateInstance;
    }

    /**
     * @return the pseudo-expression
     */
    @Override
    public String getExpressionString() {
        Operator operator = getOperator();
        Object[] operands = getOperands();
        String string = operator == null ? "" : operator.name();
        if (operands != null && operands.length != 0) {
            String[] strings = new String[operands.length];
            for (int i = 0; i < strings.length; i++) {
                strings[i] = operandString(operands[i]);
            }
            string += "(" + StringUtils.join(strings, ", ") + ")";
        }
        return string;
    }

    private String operandString(Object operand) {
        if (operand == null) {
            return null;
        } else if (operand instanceof Entity) {
            return ((Artifact) operand).getFullName();
        } else if (operand instanceof Instance) {
            return ((Artifact) operand).getFullName();
        } else if (operand instanceof NamedValue namedValue) {
            return namedValue.name();
        } else if (operand instanceof Expression expression) {
            return expression.getExpressionString();
        } else if (operand instanceof Artifact artifact) {
            return artifact.getFullName();
        } else {
            return "" + operand;
        }
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
            if (verbose) {
                string += fee + tab + "type" + faa + _dataType + foo;
                string += fee + tab + "parentExpression" + faa + _parentExpression + foo;
            }
        }
        return string;
    }
    // </editor-fold>

    /*
    private void track(String method) {
        track(method, this);
    }

    private void track(String method, Object... parameters) {
        TLC.getProject().getParser().track(depth(), round(), getClassPath(), method, parameters);
    }

    /**/
}
