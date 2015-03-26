/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.annotations.*;
import adalid.core.enums.DatabaseEntityType;
import adalid.core.enums.DisplayMode;
import adalid.core.enums.InheritanceMappingStrategy;
import adalid.core.enums.KeyProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SearchType;
import adalid.core.enums.VirtualEntityType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Check;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityReference;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.PersistentEntityReference;
import adalid.core.interfaces.PersistentEnumerationEntityReference;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.StringProperty;
import adalid.core.sql.QueryJoin;
import adalid.core.sql.QueryTable;
import adalid.core.wrappers.PersistentEntityReferenceWrapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
@EntityClass(resourceType = ResourceType.OPERATION)
@PersistentEntityClass()
@EntitySelectOperation()
@EntityInsertOperation()
@EntityUpdateOperation()
@EntityDeleteOperation()
@EntityReportOperation()
@EntityExportOperation()
@EntityTableView()
@EntityDetailView()
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView()
@EntityReferenceSearch(searchType = SearchType.DISPLAY, displayMode = DisplayMode.READING)
public abstract class AbstractPersistentEntity extends AbstractDatabaseEntity implements PersistentEntityReference {

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(Entity.class);

    {
        setDatabaseEntityType(DatabaseEntityType.PERSISTENT);
    }

    /**
     * _baseTableClass: concrete entity superclass that is also a table, if any (null otherwise)
     */
    private Class<?> _baseTableClass;

    /**
     * annotated with InheritanceMapping
     */
    private InheritanceMappingStrategy _inheritanceMappingStrategy;

    /**
     * annotated with InheritanceMapping
     */
    private String _discriminatorFieldName;

    /**
     * annotated with InheritanceMapping
     */
    private Field _discriminatorField;

    /**
     * discriminatorProperty: reference to the entity discriminator property, if any (null otherwise)
     */
    private Property _discriminatorProperty;

    /**
     * annotated with DiscriminatorValue
     */
    private String _discriminatorValue;

    /**
     *
     */
    private boolean _triggerBeforeValueEnabled;

    /**
     *
     */
    private boolean _triggerAfterValueEnabled;

    /**
     *
     */
    private boolean _triggerBeforeCheckEnabled;

    /**
     *
     */
    private boolean _triggerAfterCheckEnabled;

    /**
     *
     */
    private int _dataProviderNumber;

    /**
     *
     */
    private boolean _pseudoTargetTable;

    /**
     *
     */
    private OnDeleteAction _onDeleteAction;

    /**
     *
     */
    private OnUpdateAction _onUpdateAction;

    /**
     * annotated with InheritanceMapping
     */
    private boolean _annotatedWithInheritanceMapping;

    /**
     * annotated with DiscriminatorValue
     */
    private boolean _annotatedWithDiscriminatorValue;

    /**
     * annotated with EntityTriggers
     */
    private boolean _annotatedWithEntityTriggers;

    /**
     * annotated with PersistentEntityClass
     */
    private boolean _annotatedWithPersistentEntityClass;

    /**
     * annotated with ForeignKey
     */
    private boolean _annotatedWithForeignKey;

    @Override
    public void finalise() {
        super.finalise();
        check();
    }

    private void check() {
        if (isRootInstance()) {
            if (getPrimaryKeyProperty() == null) {
                String message = getName() + " does not have a primary key property";
                logger.error(message);
                TLC.getProject().getParser().increaseErrorCount();
            }
        }
    }

    /**
     * @return the base table class
     */
    @Override
    public Class<?> getBaseTableClass() {
        return _baseTableClass;
    }

    /**
     * @return the root entity instance of the base table class
     */
    @Override
    public PersistentEntity getBaseTableRoot() {
        Entity entity = _baseTableClass == null ? null : getDeclaringProject().getEntity(_baseTableClass);
        return entity instanceof PersistentEntity ? (PersistentEntity) entity : null;
    }

    /**
     * @return the inheritanceMappingStrategy
     */
    @Override
    public InheritanceMappingStrategy getInheritanceMappingStrategy() {
        return _inheritanceMappingStrategy;
    }

    /**
     * @return the discriminator property name
     */
    public String getDiscriminatorFieldName() {
        return _discriminatorFieldName;
    }

    /**
     * @return the discriminator property field
     */
    public Field getDiscriminatorField() {
        return _discriminatorField;
    }

    /**
     * @return the discriminator property
     */
    @Override
    public Property getDiscriminatorProperty() {
        return _discriminatorProperty;
    }

    /**
     * @return the discriminatorValue
     */
    @Override
    public String getDiscriminatorValue() {
        return _discriminatorValue;
    }

    /**
     * @return the triggerBeforeValueEnabled
     */
    @Override
    public boolean isTriggerBeforeValueEnabled() {
        return _triggerBeforeValueEnabled;
    }

    /**
     * @return the triggerAfterValueEnabled
     */
    @Override
    public boolean isTriggerAfterValueEnabled() {
        return _triggerAfterValueEnabled;
    }

    /**
     * @return the triggerBeforeCheckEnabled
     */
    @Override
    public boolean isTriggerBeforeCheckEnabled() {
        return _triggerBeforeCheckEnabled;
    }

    /**
     * @return the triggerAfterCheckEnabled
     */
    @Override
    public boolean isTriggerAfterCheckEnabled() {
        return _triggerAfterCheckEnabled;
    }

    /**
     * @return the data provider number
     */
    @Override
    public int getDataProviderNumber() {
        return _dataProviderNumber;
    }

    /**
     * @return the pseudo target table indicator
     */
    @Override
    public boolean isPseudoTargetTable() {
        return _pseudoTargetTable;
    }

    /**
     * @return true is the entity defines a foreign key
     */
    @Override
    public boolean isForeignKey() {
        return _annotatedWithForeignKey;
    }

    /**
     * @return the onDeleteAction
     */
    @Override
    public OnDeleteAction getOnDeleteAction() {
        return _onDeleteAction;
    }

    /**
     * @return the onUpdateAction
     */
    @Override
    public OnUpdateAction getOnUpdateAction() {
        return _onUpdateAction;
    }

    /**
     * @return the InheritanceMapping annotation indicator
     */
    public boolean isAnnotatedWithInheritanceMapping() {
        return _annotatedWithInheritanceMapping;
    }

    /**
     * @return the DiscriminatorValue annotation indicator
     */
    public boolean isAnnotatedWithDiscriminatorValue() {
        return _annotatedWithDiscriminatorValue;
    }

    /**
     * @return the EntityTriggers annotation indicator
     */
    public boolean isAnnotatedWithEntityTriggers() {
        return _annotatedWithEntityTriggers;
    }

    /**
     * @return the PersistentEntityClass annotation indicator
     */
    public boolean isAnnotatedWithPersistentEntityClass() {
        return _annotatedWithPersistentEntityClass;
    }

    /**
     * @return the ForeignKey annotation indicator
     */
    public boolean isAnnotatedWithForeignKey() {
        return _annotatedWithForeignKey;
    }

    public AbstractPersistentEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

    @Override
    void setKeyFields() {
        super.setKeyFields();
        setDiscriminatorField();
    }

    void setDiscriminatorField() {
        Field field = getAnnotations().get(DiscriminatorColumn.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getDiscriminatorField(fieldName, type))) {
                _discriminatorFieldName = fieldName;
                _discriminatorField = field;
            }
        }
    }

    @Override
    void setKeyProperties() {
        super.setKeyProperties();
        Object keyProperty = getKeyProperty(_discriminatorField);
        if (keyProperty instanceof Property) {
            _discriminatorProperty = (Property) keyProperty;
        }
    }

    private Field getDiscriminatorField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            IntegerProperty.class,
            StringProperty.class,
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.DISCRIMINATOR, name, type, validTypes);
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithInheritanceMapping = false;
        _annotatedWithDiscriminatorValue = false;
        _annotatedWithEntityTriggers = false;
        _annotatedWithForeignKey = false;
        _annotatedWithPersistentEntityClass = false;
        _inheritanceMappingStrategy = InheritanceMappingStrategy.UNSPECIFIED;
        _discriminatorFieldName = null;
        _discriminatorField = null;
        _discriminatorValue = null;
        _triggerBeforeValueEnabled = false;
        _triggerAfterValueEnabled = false;
        _triggerBeforeCheckEnabled = false;
        _triggerAfterCheckEnabled = false;
        _onDeleteAction = OnDeleteAction.UNSPECIFIED;
        _onUpdateAction = OnUpdateAction.UNSPECIFIED;
        _dataProviderNumber = 2;
        _pseudoTargetTable = false;
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateInheritanceMapping(type);
            annotateDiscriminatorValue(type);
            annotateEntityTriggers(type);
            annotatePersistentEntityClass(type);
        }
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            if (isEntityReference()) {
                annotateForeignKey(field);
            }
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(InheritanceMapping.class);
        valid.add(DiscriminatorValue.class);
        valid.add(EntityTriggers.class);
        valid.add(PersistentEntityClass.class);
        return valid;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        if (isProperty()) {
            valid.add(ForeignKey.class);
        }
        return valid;
    }

    private void annotateInheritanceMapping(Class<?> type) {
        /*
         * InheritanceMapping annotation cannot be "inherited"
         */
        _annotatedWithInheritanceMapping = type.isAnnotationPresent(InheritanceMapping.class);
        if (_annotatedWithInheritanceMapping) {
            InheritanceMapping annotation = type.getAnnotation(InheritanceMapping.class);
            _inheritanceMappingStrategy = annotation.strategy();
//          _discriminatorFieldName = annotation.discriminator();
//          if (StringUtils.isBlank(_discriminatorFieldName)) {
//              _discriminatorFieldName = null;
//              _discriminatorField = null;
//          } else {
//              _discriminatorField = getDiscriminatorField(_discriminatorFieldName, type);
//          }
        }
    }

    private void annotateDiscriminatorValue(Class<?> type) {
        /*
         * DiscriminatorValue annotation cannot be "inherited"
         */
        _annotatedWithDiscriminatorValue = type.isAnnotationPresent(DiscriminatorValue.class);
        if (_annotatedWithDiscriminatorValue) {
            DiscriminatorValue annotation = type.getAnnotation(DiscriminatorValue.class);
            _discriminatorValue = annotation.value();
            if (StringUtils.isBlank(_discriminatorValue)) {
                _discriminatorValue = null;
            }
        }
    }

    private void annotateEntityTriggers(Class<?> type) {
        /*
         * EntityTriggers annotation may be "inherited"
         */
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityTriggers.class);
        if (annotatedClass != null) {
            EntityTriggers annotation = annotatedClass.getAnnotation(EntityTriggers.class);
            if (annotation != null) {
                _annotatedWithEntityTriggers = true;
                _triggerBeforeValueEnabled = annotation.beforeValue().toBoolean(_triggerBeforeValueEnabled);
                _triggerAfterValueEnabled = annotation.afterValue().toBoolean(_triggerAfterValueEnabled);
                _triggerBeforeCheckEnabled = annotation.beforeCheck().toBoolean(_triggerBeforeCheckEnabled);
                _triggerAfterCheckEnabled = annotation.afterCheck().toBoolean(_triggerAfterCheckEnabled);
            }
        }
    }

    private void annotatePersistentEntityClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, PersistentEntityClass.class);
        if (annotatedClass != null) {
            PersistentEntityClass annotation = annotatedClass.getAnnotation(PersistentEntityClass.class);
            if (annotation != null) {
                _dataProviderNumber = annotation.dataProvider();
                _pseudoTargetTable = annotation.pseudoTargetTable().toBoolean(_pseudoTargetTable);
                _annotatedWithPersistentEntityClass = true;
            }
        }
    }

    private void annotateForeignKey(Field field) {
        _annotatedWithForeignKey = field.isAnnotationPresent(ForeignKey.class);
        if (_annotatedWithForeignKey) {
            ForeignKey annotation = field.getAnnotation(ForeignKey.class);
            _onDeleteAction = annotation.onDelete();
            _onUpdateAction = annotation.onUpdate();
        }
    }
    // </editor-fold>

    @Override
    void initializeInheritanceFields() {
        super.initializeInheritanceFields();
        Class<?> type = getDataType();
        PersistentEntity pent;
        if (isRootInstance()) {
            Class<?> baseClass = getBaseClass();
            if (baseClass != null) {
                Entity base = getDeclaringProject().getEntity(baseClass);
                if (base instanceof PersistentEntity) {
                    pent = (PersistentEntity) base;
                    if (pent.isTable()) {
                        _baseTableClass = baseClass;
                    } else {
                        _baseTableClass = pent.getBaseTableClass();
                    }
                    if (InheritanceMappingStrategy.UNSPECIFIED.equals(_inheritanceMappingStrategy)) {
                        _inheritanceMappingStrategy = pent.getInheritanceMappingStrategy();
                    }
                }
            }
        } else {
            Entity root = getDeclaringProject().getEntity(type);
            if (root instanceof PersistentEntity) {
                pent = (PersistentEntity) root;
                _baseTableClass = pent.getBaseTableClass();
                _inheritanceMappingStrategy = pent.getInheritanceMappingStrategy();
            }
        }
    }

    /**
     * @return the table indicator
     */
    @Override
    public boolean isTable() {
        if (isAbstractClass() && InheritanceMappingStrategy.TABLE_PER_CLASS.equals(_inheritanceMappingStrategy)) {
            return false;
        }
        Entity base = getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy ims = pent == null ? null : pent.getInheritanceMappingStrategy();
        return !InheritanceMappingStrategy.SINGLE_TABLE.equals(ims);
    }

    /**
     * @return the table indicator
     */
    @Override
    public boolean isNotTable() {
        return !isTable();
    }

    /**
     * @return the joined table indicator
     */
    @Override
    public boolean isJoinedTable() {
        Entity base = getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy ims = pent == null ? null : pent.getInheritanceMappingStrategy();
        return InheritanceMappingStrategy.JOINED.equals(ims);
    }

    /**
     * @return the joined table indicator
     */
    @Override
    public boolean isNotJoinedTable() {
        return !isJoinedTable();
    }

    /**
     * @return the discriminator type
     */
    public String getDiscriminatorType() {
        Class<?> dataType = _discriminatorProperty == null ? null : _discriminatorProperty.getDataType();
        if (dataType == null) {
            return null;
        } else if (Character.class.isAssignableFrom(dataType)) {
            return "CHAR";
        } else if (String.class.isAssignableFrom(dataType)) {
            return "STRING";
        } else if (Integer.class.isAssignableFrom(dataType)) {
            return "INTEGER";
        } else if (PersistentEnumerationEntityReference.class.isAssignableFrom(dataType)) {
            return "INTEGER";
        } else {
            return null;
        }
    }

    /**
     * @return the join base entity
     */
    @Override
    public PersistentEntity getJoinBaseEntity() {
        Entity base = getBaseTableRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy ims = pent == null ? null : pent.getInheritanceMappingStrategy();
        return InheritanceMappingStrategy.JOINED.equals(ims) ? pent : null;
    }

    /**
     * @return the properties that are columns
     */
    @Override
    public List<Property> getColumnsList() {
        List<Property> list = new ArrayList<>();
        Entity base = getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy baseIMS = pent == null ? null : pent.getInheritanceMappingStrategy();
        if (InheritanceMappingStrategy.SINGLE_TABLE.equals(baseIMS)) {
            return list;
        }
        if (InheritanceMappingStrategy.JOINED.equals(baseIMS)) {
            list.addAll(getJoinedPropertiesList());
        } else {
            list.addAll(getPropertiesList());
        }
        if (InheritanceMappingStrategy.SINGLE_TABLE.equals(_inheritanceMappingStrategy)) {
            list.addAll(getSinglePropertiesList(getDeclaringProject(), getSubclassesList()));
        }
        return list;
    }

    /**
     * @return the properties that are columns
     */
//  @Override
    public List<Property> getEntityTriggerColumnsList() {
        Class<?> baseTableClass = getBaseTableClass();
        if (baseTableClass == null) {
            return getPropertiesList();
        }
        if (isJoinedTable()) {
            return getJoinedPropertiesList();
        }
        List<Property> list = new ArrayList<>();
        Field field;
        Class<?> clazz;
        for (Property property : getPropertiesList()) {
            if (!property.isBaseField() && property.isInherited()) {
                field = property.getDeclaringField();
                clazz = field.getDeclaringClass();
                if (!baseTableClass.isAssignableFrom(clazz)) {
                    continue;
                }
            }
            list.add(property);
        }
        return list;
    }

    /**
     * @return the properties that are columns
     */
//  @Override
    public List<Property> getMatchingColumnsList(List<Property> properties) {
        List<Property> list = new ArrayList<>();
        if (properties == null || properties.isEmpty()) {
            return list;
        }
        List<Property> columns = getColumnsList();
        if (columns == null || columns.isEmpty()) {
            return list;
        }
        Field columnDeclaringField;
        for (Property column : columns) {
            columnDeclaringField = column.getDeclaringField();
            if (columnDeclaringField != null) {
                for (Property property : properties) {
                    if (columnDeclaringField.equals(property.getDeclaringField())) {
                        list.add(column);
                    }
                }
            }
        }
        return list;
    }

    /**
     * @return the properties that are columns
     */
    @Override
    public List<Property> getDataProviderColumnsList() {
        Entity base = getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
//      InheritanceMappingStrategy baseIMS = pent == null ? null : pent.getInheritanceMappingStrategy();
//      if (InheritanceMappingStrategy.JOINED.equals(baseIMS)) {
//          return getJoinedPropertiesList();
//      }
        return getPropertiesList();
    }

    /**
     * @return the properties that are not entities
     */
    public List<Property> getEntityClassColumnsList1() {
        List<Property> list = new ArrayList<>();
        Entity base = getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy baseIMS = pent == null ? null : pent.getInheritanceMappingStrategy();
        List<Property> properties;
        if (baseIMS == null || baseIMS.equals(InheritanceMappingStrategy.UNSPECIFIED)) {
            properties = getPropertiesList();
        } else {
            properties = getNonInheritedPropertiesList();
        }
        for (Property property : properties) {
            if (property instanceof Entity) {
                continue;
            }
            list.add(property);
        }
        return list;
    }

    /**
     * @return the properties that are entities
     */
    public List<Property> getEntityClassColumnsList2() {
        List<Property> list = new ArrayList<>();
        Entity base = getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy baseIMS = pent == null ? null : pent.getInheritanceMappingStrategy();
        List<Property> properties;
        if (baseIMS == null || baseIMS.equals(InheritanceMappingStrategy.UNSPECIFIED)) {
            properties = getPropertiesList();
        } else {
            properties = getNonInheritedPropertiesList();
        }
        for (Property property : properties) {
            if (property instanceof Entity) {
                list.add(property);
            }
        }
        return list;
    }

    /**
     * @return the properties that reference this entity
     */
    public List<Property> getEntityClassColumnsList3() {
        List<Property> list = new ArrayList<>();
        List<Property> properties = getReferencesList();
        for (Property property : properties) {
            if (property instanceof Entity) {
                if (property.depth() == 1) {
                    list.add(property);
                }
            }
        }
        return list;
    }

    /**
     * @return the expressions that are checks
     */
//  @Override
    public List<Expression> getChecksList() {
        List<Expression> list = new ArrayList<>();
        Field field;
        Class<?> clazz;
        Class<?> baseTableClass = getBaseTableClass();
        boolean joinedTable = isJoinedTable();
        for (Expression expression : getExpressionsList()) {
            if (expression instanceof Check) {
                field = expression.getDeclaringField();
                clazz = field.getType();
                if (Check.class.isAssignableFrom(clazz)) {
                    if (expression.isInherited()) {
                        if (joinedTable) {
                            continue;
                        }
                        if (baseTableClass != null) {
                            clazz = field.getDeclaringClass();
                            if (!baseTableClass.isAssignableFrom(clazz)) {
                                continue;
                            }
                        }
                    }
                    list.add(expression);
                }
            }
        }
        return list;
    }

    /**
     * @return the insertable rows
     */
//  @Override
    public List<Instance> getInsertableRowsList() {
        List<Instance> list = new ArrayList<>();
        Class<?> type = getDataType();
        Field field;
        Class<?> clazz;
        for (Instance instance : getInstancesList()) {
            field = instance.getDeclaringField();
            if (isJoinedTable()) {
                clazz = field.getDeclaringClass();
                if (!clazz.equals(type)) {
                    continue;
                }
            }
            list.add(instance);
        }
        return list;
    }

    private List<Property> getNonInheritedPropertiesList() {
        List<Property> list = new ArrayList<>();
        Class<?> type = getDataType();
        Field field;
        Class<?> clazz;
        for (Property property : getPropertiesList()) {
            field = property.getDeclaringField();
            clazz = field.getDeclaringClass();
            if (clazz.equals(type)) {
                list.add(property);
            }
        }
        return list;
    }

    private List<Property> getJoinedPropertiesList() {
        List<Property> list = new ArrayList<>();
        Class<?> type = getDataType();
        Field field;
        Class<?> clazz;
        for (Property property : getPropertiesList()) {
            if (property.isBaseField()) {
                list.add(property);
            } else {
                field = property.getDeclaringField();
                clazz = field.getDeclaringClass();
                if (clazz.equals(type)) {
                    list.add(property);
                }
            }
        }
        return list;
    }

    private List<Property> getSinglePropertiesList(Project project, List<Class<?>> subclasses) {
        List<Property> list = new ArrayList<>();
        Entity entity;
        Class<?> type;
        Field field;
        Class<?> clazz;
        PersistentEntity pent;
        InheritanceMappingStrategy ims;
        for (Class<?> subclass : subclasses) {
            entity = project.getEntity(subclass);
            type = entity.getDataType();
            for (Property property : entity.getPropertiesList()) {
                field = property.getDeclaringField();
                clazz = field.getDeclaringClass();
                if (clazz.equals(type)) {
                    list.add(property);
                }
            }
            pent = entity instanceof PersistentEntity ? (PersistentEntity) entity : null;
            ims = pent == null ? null : pent.getInheritanceMappingStrategy();
            if (InheritanceMappingStrategy.SINGLE_TABLE.equals(ims)) {
                list.addAll(getSinglePropertiesList(project, entity.getSubclassesList()));
            }
        }
        return list;
    }

    /**
     * query table
     */
    private QueryTable _queryTable;

    /**
     * @return the queryTable
     */
    @Override
    public QueryTable getQueryTable() {
        return getQueryTable(-1);
    }

    /**
     * @return the queryTable
     */
    public QueryTable getQueryTable(int maxDepth) {
        return getQueryTable(maxDepth, null);
    }

    /**
     * @return the queryTable
     */
    public QueryTable getQueryTable(VirtualEntityType virtualEntityType) {
        return getQueryTable(-1, virtualEntityType);
    }

    /**
     * @return the queryTable
     */
    public QueryTable getQueryTable(int maxDepth, VirtualEntityType virtualEntityType) {
        SqlProgrammer sp = ChiefProgrammer.getSqlProgrammer();
        if (sp == null) {
            throw new RuntimeException("null sql programmer");
        }
        if (_queryTable != null) {
            if (maxDepth == _queryTable.getMaxDepth() && sp.equals(_queryTable.getSqlProgrammer())) {
                VirtualEntityType newvet = virtualEntityType;
                VirtualEntityType oldvet = _queryTable.getVirtualEntityType();
                if ((newvet == null && oldvet == null) || (newvet != null && newvet.equals(oldvet))) {
                    return _queryTable;
                }
            }
        }
        _queryTable = new QueryTable(this, maxDepth, virtualEntityType);
        return _queryTable;
    }

    /**
     * search query table
     */
    private QueryTable _searchQueryTable;

    /**
     * @return the data provider query table
     */
    public QueryTable getSearchQueryTable() {
        PersistentEntity reference;
        QueryTable queryTable;
        if (_searchQueryTable == null) {
            _searchQueryTable = new QueryTable(this, -1, null);
            List<Property> properties = getReferencesList();
            for (Property property : properties) {
                if (property instanceof PersistentEntity) {
                    reference = (PersistentEntity) property;
                    queryTable = reference.getQueryTable();
                    if (queryTable != null && reference.getSearchQueryFilter() != null) {
                        _searchQueryTable.merge(queryTable);
                    }
                }
            }
            List<Parameter> parameters = getParameterReferencesList();
            for (Parameter parameter : parameters) {
                if (parameter instanceof PersistentEntity) {
                    reference = (PersistentEntity) parameter;
                    queryTable = reference.getQueryTable();
                    if (queryTable != null && reference.getSearchQueryFilter() != null) {
                        _searchQueryTable.merge(queryTable);
                    }
                }
            }
        }
        return _searchQueryTable;
    }

    /**
     * entity list report
     */
    private Report _report;

    /**
     * @return the entity list report
     */
    public Report getEntityListReport() {
        if (_report == null) {
            _report = new Report(this);
        }
        return _report;
    }

    /**
     * @return the referenced columns list
     */
//  @Override
    public List<Property> getChecksColumnsList() {
        return new ArrayList<>(getChecksColumnsMap().values());
    }

    /**
     * @return the referenced columns map
     */
//  @Override
    public Map<String, Property> getChecksColumnsMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        List<Expression> checks = getChecksList();
        for (Expression check : checks) {
            map.putAll(check.getReferencedColumnsMap());
        }
        return map;
    }

    /**
     * @return the referenced joins list
     */
//  @Override
    public List<QueryJoin> getChecksJoinsList() {
        return new ArrayList<>(getChecksJoinsMap().values());
    }

    /**
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getChecksJoinsMap() {
        Map<String, QueryJoin> map = new TreeMap<>();
        QueryTable queryTable = getQueryTable();
        List<Expression> checks = getChecksList();
        for (Expression check : checks) {
            map.putAll(check.getReferencedJoinsMap(queryTable));
        }
        return map;
    }

    /**
     * @return the referenced columns list
     */
//  @Override
    public List<Property> getInitialValueColumnsList() {
        return new ArrayList<>(getInitialValueColumnsMap().values());
    }

    /**
     * @return the referenced columns map
     */
//  @Override
    public Map<String, Property> getInitialValueColumnsMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        Object initialValue;
        Property initialProperty;
        Expression initialExpression;
        List<Property> columns = getColumnsList();
        for (Property column : columns) {
            initialValue = column.getInitialValue();
            if (initialValue instanceof Property) {
                initialProperty = (Property) initialValue;
                map.put(initialProperty.getPathString(), initialProperty);
            } else if (initialValue instanceof Expression) {
                initialExpression = (Expression) initialValue;
                map.putAll(initialExpression.getReferencedColumnsMap());
            }
        }
        return map;
    }

    /**
     * @return the referenced joins list
     */
//  @Override
    public List<QueryJoin> getInitialValueJoinsList() {
        return new ArrayList<>(getInitialValueJoinsMap().values());
    }

    /**
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getInitialValueJoinsMap() {
        Map<String, QueryJoin> map = new TreeMap<>();
        QueryTable queryTable = getQueryTable();
        Object initialValue;
        Property initialProperty;
        Expression initialExpression;
        List<Property> columns = getColumnsList();
        for (Property column : columns) {
            initialValue = column.getInitialValue();
            if (initialValue instanceof Property) {
                initialProperty = (Property) initialValue;
                map.putAll(queryTable.getReferencedJoinsMap(initialProperty));
            } else if (initialValue instanceof Expression) {
                initialExpression = (Expression) initialValue;
                map.putAll(initialExpression.getReferencedJoinsMap(queryTable));
            }
        }
        return map;
    }

    /**
     * @return the referenced columns list
     */
//  @Override
    public List<Property> getDefaultValueColumnsList() {
        return new ArrayList<>(getDefaultValueColumnsMap().values());
    }

    /**
     * @return the referenced columns map
     */
//  @Override
    public Map<String, Property> getDefaultValueColumnsMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        Object defaultValue;
        Property defaultProperty;
        Expression defaultExpression;
        List<Property> columns = getColumnsList();
        for (Property column : columns) {
            defaultValue = column.getDefaultValue();
            if (defaultValue instanceof Property) {
                defaultProperty = (Property) defaultValue;
                map.put(defaultProperty.getPathString(), defaultProperty);
            } else if (defaultValue instanceof Expression) {
                defaultExpression = (Expression) defaultValue;
                map.putAll(defaultExpression.getReferencedColumnsMap());
            }
        }
        return map;
    }

    /**
     * @return the referenced joins list
     */
//  @Override
    public List<QueryJoin> getDefaultValueJoinsList() {
        return new ArrayList<>(getDefaultValueJoinsMap().values());
    }

    /**
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getDefaultValueJoinsMap() {
        Map<String, QueryJoin> map = new TreeMap<>();
        QueryTable queryTable = getQueryTable();
        Object defaultValue;
        Property defaultProperty;
        Expression defaultExpression;
        List<Property> columns = getColumnsList();
        for (Property column : columns) {
            defaultValue = column.getDefaultValue();
            if (defaultValue instanceof Property) {
                defaultProperty = (Property) defaultValue;
                map.putAll(queryTable.getReferencedJoinsMap(defaultProperty));
            } else if (defaultValue instanceof Expression) {
                defaultExpression = (Expression) defaultValue;
                map.putAll(defaultExpression.getReferencedJoinsMap(queryTable));
            }
        }
        return map;
    }

    @Override
    public List<Property> getJoinedTablePropertiesList() {
        return new ArrayList<>(getJoinedTablePropertiesMap().values());
    }

    @Override
    public Map<String, Property> getJoinedTablePropertiesMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        Field field;
        Class<?> clazz;
        Class<?> type = getDataType();
        Property property;
        Map<String, Property> thisPropertiesMap = getPropertiesMap();
        for (String key : thisPropertiesMap.keySet()) {
            property = thisPropertiesMap.get(key);
            if (property.isBaseField()) {
                map.put(key, property);
            } else {
                field = property.getDeclaringField();
                clazz = field.getDeclaringClass();
                if (clazz.equals(type)) {
                    map.put(key, property);
                }
            }
        }
        return map;
    }

    @Override
    public List<Property> getJoinedTableMatchingPropertiesList(Map<String, Property> someProperties) {
        return new ArrayList<>(getJoinedTableMatchingPropertiesMap(someProperties).values());
    }

    @Override
    public Map<String, Property> getJoinedTableMatchingPropertiesMap(Map<String, Property> someProperties) {
        Map<String, Property> map = new LinkedHashMap<>();
        Map<String, Property> thisProperties = getPropertiesMap();
        Map<String, Property> thisJoinedTableProperties = getJoinedTablePropertiesMap();
        Property property;
        for (String key : someProperties.keySet()) {
            property = thisJoinedTableProperties.containsKey(key) ? null : thisProperties.get(key);
            if (property != null) {
                map.put(key, property);
            }
        }
        return map;
    }

    @Override
    public List<Property> getSingleJoinedTablePropertiesList(Map<String, Property> someProperties) {
        return new ArrayList<>(getSingleJoinedTablePropertiesMap(someProperties).values());
    }

    @Override
    public Map<String, Property> getSingleJoinedTablePropertiesMap(Map<String, Property> someProperties) {
        Map<String, Property> map = new LinkedHashMap<>();
        Field field;
        Class<?> clazz;
        Class<?> type = getDataType();
        Property property;
        Map<String, Property> thisPropertiesMap = getPropertiesMap();
        for (String key : thisPropertiesMap.keySet()) {
            property = thisPropertiesMap.get(key);
            if (property.isBaseField()) {
                map.put(key, property);
            } else if (someProperties.containsKey(key)) {
            } else {
                map.put(key, property);
            }
        }
        return map;
    }

    Set<String> crossReferencedExpressionsSet;

    @Override
    public Set<String> getCrossReferencedExpressionsSet() {
        if (crossReferencedExpressionsSet == null) {
            crossReferencedExpressionsSet = new LinkedHashSet<>();
            List<Expression> expressions = getExpressionsList();
            for (Expression expression : expressions) {
                crossReferencedExpressionsSet.addAll(expression.getCrossReferencedExpressionsSet(this));
            }
            Object value;
            Expression expression;
            List<Property> properties = getColumnsList();
            for (Property property : properties) {
                value = property.getInitialValue();
                if (value instanceof Expression) {
                    expression = (Expression) value;
                    crossReferencedExpressionsSet.addAll(expression.getCrossReferencedExpressionsSet(this));
                }
                value = property.getDefaultValue();
                if (value instanceof Expression) {
                    expression = (Expression) value;
                    crossReferencedExpressionsSet.addAll(expression.getCrossReferencedExpressionsSet(this));
                }
            }
        }
        return crossReferencedExpressionsSet;
    }

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends PersistentEntityReferenceWrapper> getDefaultWrapperClass() {
        return PersistentEntityReferenceWrapper.class;
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
                if (isEntityReference()) {
                    string += fee + tab + "foreignKey" + faa + isForeignKey() + foo;
                    string += fee + tab + "onDeleteAction" + faa + _onDeleteAction + foo;
                    string += fee + tab + "onUpdateAction" + faa + _onUpdateAction + foo;
                } else {
                    string += fee + tab + "baseTableClass" + faa + _baseTableClass + foo;
                    string += fee + tab + "inheritanceMappingStrategy" + faa + _inheritanceMappingStrategy + foo;
                    if (InheritanceMappingStrategy.UNSPECIFIED.equals(_inheritanceMappingStrategy)) {
                    } else {
//                      string += fee + tab + "discriminatorFieldName" + faa + _discriminatorFieldName + foo;
//                      string += fee + tab + "discriminatorField" + faa + _discriminatorField + foo;
                        string += fee + tab + "discriminatorProperty" + faa + _discriminatorProperty + foo;
                        string += fee + tab + "discriminatorValue" + faa + _discriminatorValue + foo;
                    }
                    string += fee + tab + "triggerBeforeValueEnabled" + faa + _triggerBeforeValueEnabled + foo;
                    string += fee + tab + "triggerAfterValueEnabled" + faa + _triggerAfterValueEnabled + foo;
                    string += fee + tab + "triggerBeforeCheckEnabled" + faa + _triggerBeforeCheckEnabled + foo;
                    string += fee + tab + "triggerAfterCheckEnabled" + faa + _triggerAfterCheckEnabled + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
