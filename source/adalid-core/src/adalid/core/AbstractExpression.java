/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.ThrowableUtils;
import adalid.core.enums.OperationKind;
import adalid.core.expressions.VariantX;
import adalid.core.interfaces.*;
import adalid.core.sql.QueryJoin;
import adalid.core.sql.QueryTable;
import adalid.core.wrappers.ExpressionWrapper;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractExpression extends AbstractArtifact implements NaryExpression {

    private static final Logger logger = Logger.getLogger(Expression.class);

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
    private boolean _finalised;

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
            AbstractExpression childExpression;
            Object[] operands = getOperands();
            if (operands != null) {
                for (Object operand : operands) {
                    if (operand instanceof AbstractExpression) {
                        childExpression = (AbstractExpression) operand;
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
     * @return the referenced columns list
     */
    @Override
    public List<Property> getReferencedColumnsList() {
        return new ArrayList<>(getReferencedColumnsMap().values());
    }

    /**
     * @return the referenced columns map
     */
    @Override
    public Map<String, Property> getReferencedColumnsMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        Property property;
        Expression expression;
        Object[] operands = getOperands();
        if (operands != null) {
            for (Object operand : operands) {
                if (operand instanceof Property) {
                    property = (Property) operand;
                    map.put(property.getPathString(), property);
                } else if (operand instanceof Expression) {
                    expression = (Expression) operand;
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
        if (declaringEntity instanceof PersistentEntity) {
            PersistentEntity pent = (PersistentEntity) declaringEntity;
            QueryTable queryTable = pent.getQueryTable();
            return getReferencedJoinsMap(queryTable);
        }
        return new LinkedHashMap<>();
    }

    /**
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
     * @return the referenced expressions map
     */
    @Override
    public Set<String> getCrossReferencedExpressionsSet(Entity declaringEntity) {
        if (crossReferencedExpressionsSet == null) {
            crossReferencedExpressionsSet = new LinkedHashSet<>();
            if (declaringEntity != null) {
                Object[] operands = getOperands();
                if (operands != null) {
                    Expression expression;
                    Entity expressionDeclaringEntity;
                    String key;
                    for (Object operand : operands) {
                        if (operand instanceof Primitive) {
                            continue;
                        }
                        if (operand instanceof Expression) {
                            expression = (Expression) operand;
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
     * @return true if is a TemporalExpression; otherwise false
     */
    @Override
    public boolean isSingleEntityExpression(Entity declaringEntity) {
        if (declaringEntity == null) {
            return false;
        } else {
            Object[] operands = getOperands();
            if (operands != null) {
                Expression expression;
                Entity expressionDeclaringEntity;
                for (Object operand : operands) {
                    if (operand instanceof Expression) {
                        if (operand instanceof RowsAggregateX) {
                            return false;
                        }
                        expression = (Expression) operand;
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

    /**
     * @return the finalised indicator
     */
    @Override
    public boolean isFinalised() {
        return _finalised;
    }

    @Override
    public void finalise() {
        XS1.checkAccess();
        if (_finalised) {
            return;
        }
        _finalised = true;
//      finaliseFields();
        setParentExpression(null);
    }

    protected void copyDataType(Object object) {
        setDataType(getObjectDataType(object));
    }

    protected Class<?> getObjectDataType(Object object) {
        if (object == null) {
            return Object.class;
        } else if (object instanceof DataArtifact) {
            DataArtifact artifact = (DataArtifact) object;
            return artifact.getDataType();
//      } else if (object instanceof Instance) {
//          Instance instance = (Instance) object;
//          Object key = instance.getInstanceKeyValue();
//          return key == null ? Instance.class : key.getClass();
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
            TLC.getProject().getParser().increaseErrorCount();
        } else {
            try {
                Object foreignExpression = _foreignExpressionField.get(foreignEntity);
                if (foreignExpression instanceof Expression) {
                    return (Expression) foreignExpression;
                }
                logger.error(errmsg);
                logger.error(TAB + _foreignExpressionField + " = " + foreignExpression);
                TLC.getProject().getParser().increaseErrorCount();
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                TLC.getProject().getParser().increaseErrorCount();
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
//              TLC.getProject().getParser().increaseErrorCount();
//          } catch (IllegalAccessException ex) {
//              logger.error(errmsg, ThrowableUtils.getCause(ex));
//              TLC.getProject().getParser().increaseErrorCount();
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

}
