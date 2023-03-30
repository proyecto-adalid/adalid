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
import adalid.core.programmers.*;
import adalid.core.properties.*;
import adalid.core.sql.*;
import adalid.core.wrappers.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
@EntityClass(resourceType = ResourceType.OPERATION)
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

    private static final String EOL = "\n";

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
    private InheritanceMappingStrategy _inheritanceMappingStrategy = InheritanceMappingStrategy.UNSPECIFIED;

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
    private OnDeleteAction _onDeleteAction = OnDeleteAction.UNSPECIFIED;

    /**
     *
     */
    private OnUpdateAction _onUpdateAction = OnUpdateAction.UNSPECIFIED;

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
     * annotated with ForeignKey
     */
    private boolean _annotatedWithForeignKey;

    /**
     * properties that are columns of a unique key
     */
    private final List<Property> _uniqueKeyPropertiesList = new ArrayList<>();

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            setUniqueKeyPropertiesList();
            if (isRootInstance()) {
                linkForeignQueryEntityReferences();
            }
        }
        return ok;
    }

    @Override
    public boolean finish() {
        boolean ok = super.finish();
        if (ok) {
            Artifact declaringArtifact = getDeclaringArtifact();
            if (declaringArtifact == null) {
                finishProperties();
                finishOperations();
            }
            check();
        }
        return ok;
    }

    private void finishProperties() {
        PersistentEntity entity;
        BooleanExpression filter;
        List<Property> properties = getPropertiesList();
        for (Property property : properties) {
            if (property instanceof PersistentEntity) {
                entity = (PersistentEntity) property;
                filter = entity.getSearchQueryFilter();
                if (filter != null) {
                    entity.getSearchQueryPropertiesMap();
                }
            }
        }
    }

    private void finishOperations() {
        List<Operation> operations = getBusinessOperationsList();
        for (Operation operation : operations) {
            operation.finish();
        }
    }

    /**
     * @return the properties that are columns of a unique key
     */
    @Override
    public List<Property> getUniqueKeyPropertiesList() {
        return _uniqueKeyPropertiesList;
    }

    private void setUniqueKeyPropertiesList() {
        _uniqueKeyPropertiesList.clear();
        List<Key> keys = getKeysList();
        List<KeyField> fields;
        if (keys != null) {
            for (Key key : keys) {
                if (key.isUnique() && key.isValidKeyFor(this)) {
                    fields = key.getKeyFieldsList();
                    if (fields != null) {
                        for (KeyField field : fields) {
                            _uniqueKeyPropertiesList.add(field.getProperty());
                        }
                    }
                }
            }
        }
    }

    protected void linkForeignQueryEntityReferences() {
        /*
        boolean starry, sans;
        starry = sans = false;
        switch (_entityQueryType) {
            case STARRY_LINE_SANS_ENUM:
                sans = true;
            case STARRY_LINE:
                starry = true;
                break;
        }
        if (starry) {
            List<Property> properties = getPropertiesList();
            for (Property property : properties) {
                if (property instanceof EntityReference && !(sans && property.isEnumerationEntity())) {
                    linkForeignQueryEntityReference((EntityReference) property);
                }
            }
        }
        /**/
    }

    private void check() {
        if (isRootInstance()) {
            checkPrimaryKeyProperty();
            checkSequenceProperty();
            checkVersionProperty();
            checkDiscriminatorProperty();
            checkDiscriminatorValue();
            checkInheritanceMappingStrategy();
            checkKeys();
            checkOrderBy();
        }
    }

    private void checkPrimaryKeyProperty() {
        Property primaryKeyProperty = getPrimaryKeyProperty();
        if (primaryKeyProperty == null) {
            String message = getName() + " does not have a primary key property";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (this instanceof EnumerationEntity) {
            if (!(primaryKeyProperty instanceof IntegerProperty)) {
                String message = getName() + " primary key is not an integer property";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else {
            if (!(primaryKeyProperty instanceof LongProperty)) {
                String message = getName() + " primary key is not a long property";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkSequenceProperty() {
        Property sequenceProperty = getSequenceProperty();
        if (sequenceProperty != null && sequenceProperty.isNotInherited()) {
            HierarchyNodeType hnt = getHierarchyNodeType();
            if (HierarchyNodeType.BRANCH.equals(hnt) || HierarchyNodeType.LEAF.equals(hnt)) {
                String message = getName() + " is a " + hnt.name().toLowerCase() + " and must inherit its sequence property from the hierarchy root";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkVersionProperty() {
        Property versionProperty = getVersionProperty();
        if (versionProperty == null) {
            if (collectable()) {
                if (isInsertEnabled()) {
                    String message = getName() + " allows inserting via collections but does not have a version property";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
            if (_versionPropertyWarningsEnabled) {
                if (isUpdateEnabled() || isDeleteEnabled()) {
                    String message = getName() + " allows updating and/or deleting  but does not have a version property";
                    logger.warn(message);
                    Project.increaseParserWarningCount();
                }
            }
        } else if (versionProperty.isNotInherited()) {
            HierarchyNodeType hnt = getHierarchyNodeType();
            if (HierarchyNodeType.BRANCH.equals(hnt) || HierarchyNodeType.LEAF.equals(hnt)) {
                String message = getName() + " is a " + hnt.name().toLowerCase() + " and must inherit its version property from the hierarchy root";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private boolean collectable() {
        for (Property property : getPropertiesList()) {
            if (property instanceof EntityReference) {
                if (((EntityReference) property).getMappedCollection() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkDiscriminatorProperty() {
        HierarchyNodeType hierarchyNodeType = getHierarchyNodeType();
        if (hierarchyNodeType == null) {
            if (_discriminatorPropertyWarningsEnabled) {
                if (_discriminatorProperty != null) {
                    String message = getName() + " has a discriminator property but it is not in a class hierarchy";
                    logger.warn(message);
                    Project.increaseParserWarningCount();
                }
            }
        } else if (_inheritanceMappingStrategy == null) {
        } else if (_inheritanceMappingStrategy.equals(InheritanceMappingStrategy.UNSPECIFIED)) {
        } else if (_discriminatorProperty == null) {
            switch (_inheritanceMappingStrategy) {
                case SINGLE_TABLE:
                case JOINED:
                    String message = getName() + " inheritance mapping strategy is " + _inheritanceMappingStrategy
                        + " but it does not have a discriminator property";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                    break;
            }
        } else if (_inheritanceMappingStrategy.equals(InheritanceMappingStrategy.TABLE_PER_CLASS)) {
            String message = getName() + " inheritance mapping strategy is TABLE_PER_CLASS and it has a discriminator property";
            logger.error(message);
            Project.increaseParserErrorCount();
        }
    }

    private void checkDiscriminatorValue() {
        HierarchyNodeType hierarchyNodeType = getHierarchyNodeType();
        if (hierarchyNodeType == null) {
            if (_discriminatorValue != null) {
                String message = getName() + " has a discriminator value but it is not in a class hierarchy";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (isAbstractClass()) {
            if (_discriminatorValue != null) {
                String message = getName() + " has a discriminator value but it is an abstract class";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (_discriminatorProperty == null) {
            if (_discriminatorValue != null) {
                String message = getName() + " has a discriminator value but it does not have a discriminator property";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (_inheritanceMappingStrategy == null) {
        } else if (_inheritanceMappingStrategy.equals(InheritanceMappingStrategy.UNSPECIFIED)) {
        } else if (_discriminatorValue == null) {
            switch (_inheritanceMappingStrategy) {
                case SINGLE_TABLE:
                case JOINED:
                    String message = getName() + " inheritance mapping strategy is " + _inheritanceMappingStrategy
                        + " but it does not have a discriminator value";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                    break;
            }
        } else if (_inheritanceMappingStrategy.equals(InheritanceMappingStrategy.TABLE_PER_CLASS)) {
            String message = getName() + " inheritance mapping strategy is TABLE_PER_CLASS and it has a discriminator  value";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (_discriminatorProperty.isEntity()) {
            Entity discriminatorEntity = (Entity) _discriminatorProperty;
            List<Instance> instances = discriminatorEntity.getInstancesList();
            String instanceKeyValue;
            boolean found = false;
            for (Instance instance : instances) {
                instanceKeyValue = "" + instance.getInstanceKeyValue();
                if (_discriminatorValue.equals(instanceKeyValue)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String message = getName() + " discriminator value \"" + _discriminatorValue
                    + "\" does not correspond to any instance of " + discriminatorEntity.getRoot().getName();
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkInheritanceMappingStrategy() {
        if (isRootInstance()) {
            logger.debug(StringUtils.rightPad("checkInheritanceMappingStrategy", 32)
                + "\t" + StringUtils.rightPad(getFullName(), 32)
                + "\t" + StringUtils.rightPad(Integer.toHexString(hashCode()), 8)
                + "\t" + _inheritanceMappingStrategy);
        }
        HierarchyNodeType hierarchyNodeType = getHierarchyNodeType();
        if (hierarchyNodeType == null) {
//          if (_inheritanceMappingStrategy != null) {
//              String message = getName() + " has a inheritance mapping strategy but it is not in a class hierarchy";
//              logger.error(message);
//              Project.increaseParserErrorCount();
//          }
        } else if (_inheritanceMappingStrategy == null) {
            String message = getName() + " is in a class hierarchy but the inheritance mapping strategy is not properly specified";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (InheritanceMappingStrategy.UNSPECIFIED.equals(_inheritanceMappingStrategy)) {
            String message = getName() + " is in a class hierarchy but the inheritance mapping strategy is " + _inheritanceMappingStrategy;
            logger.error(message);
            Project.increaseParserErrorCount();
        }
    }

    private void checkKeys() {
        String join, message;
        String name = getName();
        List<String> propertyNames;
        List<Key> keys = getKeysList();
        for (Key key : keys) {
            if (key.isInherited()) {
                continue;
            }
            propertyNames = key.invalidPropertyNames(this);
            if (!propertyNames.isEmpty()) {
                join = StringUtils.join(propertyNames, " and ");
                message = key.getFullName() + " is not a valid key";
                if (propertyNames.size() == 1) {
                    message += "; " + join;
                    message += " is not a valid key field for " + name;
                } else if (propertyNames.size() > 1) {
                    message += "; " + StringUtils.replace(join, " and ", ", ", propertyNames.size() - 2);
                    message += " are not valid key fields for " + name;
                }
                logger.error(message);
                Project.increaseParserErrorCount();
            }
            propertyNames = key.dubiousPropertyNames(this);
            if (!propertyNames.isEmpty()) {
                join = StringUtils.join(propertyNames, " and ");
                message = key.getFullName() + " is a dubious key";
                if (propertyNames.size() == 1) {
                    message += "; " + join;
                    message += " is a dubious key field for " + name;
                } else if (propertyNames.size() > 1) {
                    message += "; " + StringUtils.replace(join, " and ", ", ", propertyNames.size() - 2);
                    message += " are dubious key fields for " + name;
                }
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
        }
    }

    private void checkOrderBy() {
        Property[] orderByProperties = getOrderByProperties();
        if (orderByProperties != null) {
            String message;
            for (Property property : orderByProperties) {
                if (property == null) {
                    message = getName() + " has at least one null property in its order-by attribute";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                    break;
                }
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
     * @return true is the entity defines a foreign key
     */
    @Override
    public boolean isForeignKey() {
        return _annotatedWithForeignKey && isRootEntitySqlCodeGenEnabled();
    }

    private boolean isRootEntitySqlCodeGenEnabled() {
        Entity root = isTable() ? getRoot() : getBaseTableRoot();
        return root != null && root.isSqlCodeGenEnabled();
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
        /*
        Class<?>[] validTypes = new Class<?>[]{
            IntegerProperty.class,
            StringProperty.class,
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.DISCRIMINATOR, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.DISCRIMINATOR, name, type); // since 20201214
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateInheritanceMapping(type);
            annotateDiscriminatorValue(type);
            annotateEntityTriggers(type);
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
        if (isRootInstance()) {
            logger.debug(StringUtils.rightPad("annotateInheritanceMapping", 32)
                + "\t" + StringUtils.rightPad(getFullName(), 32)
                + "\t" + StringUtils.rightPad(Integer.toHexString(hashCode()), 8)
                + "\t" + _inheritanceMappingStrategy);
        }
        /*
         * InheritanceMapping annotation cannot be "inherited"
         * unless the annotated class has the same simple name as the type parameter (since 09/02/2023)
         */
        //
        /* until 09/02/2023
        _annotatedWithInheritanceMapping = type.isAnnotationPresent(InheritanceMapping.class);
        if (_annotatedWithInheritanceMapping) {
            InheritanceMapping annotation = type.getAnnotation(InheritanceMapping.class);
            _inheritanceMappingStrategy = annotation.strategy();
        }
        /**/
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, InheritanceMapping.class);
        if (annotatedClass != null && annotatedClass.getSimpleName().equals(type.getSimpleName())) {
            InheritanceMapping annotation = annotatedClass.getAnnotation(InheritanceMapping.class);
            if (annotation != null) {
                _annotatedWithInheritanceMapping = true;
                _inheritanceMappingStrategy = annotation.strategy();
            }
        }
    }

    private void annotateDiscriminatorValue(Class<?> type) {
        /*
         * DiscriminatorValue annotation cannot be "inherited"
         * unless the annotated class has the same simple name as the type parameter (since 09/02/2023)
         */
        //
        /* until 09/02/2023
        _annotatedWithDiscriminatorValue = type.isAnnotationPresent(DiscriminatorValue.class);
        if (_annotatedWithDiscriminatorValue) {
            DiscriminatorValue annotation = type.getAnnotation(DiscriminatorValue.class);
            _discriminatorValue = StringUtils.trimToNull(annotation.value());
        }
        /**/
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, DiscriminatorValue.class);
        if (annotatedClass != null && annotatedClass.getSimpleName().equals(type.getSimpleName())) {
            DiscriminatorValue annotation = annotatedClass.getAnnotation(DiscriminatorValue.class);
            if (annotation != null) {
                _annotatedWithDiscriminatorValue = true;
                _discriminatorValue = StringUtils.trimToNull(annotation.value());
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
            logger.debug(StringUtils.rightPad("initializeInheritanceFields", 32)
                + "\t" + StringUtils.rightPad(getFullName(), 32)
                + "\t" + StringUtils.rightPad(Integer.toHexString(hashCode()), 8)
                + "\t" + _inheritanceMappingStrategy);
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
        if (list.isEmpty()) {
            return list;
        }
        List<Property> columns = new ArrayList<>();
        for (Property property : list) {
            if (property.isCalculable()) {
                continue;
            }
            columns.add(property);
        }
        return columns;
    }

    /**
     * @return the properties that are columns
     */
//  @Override
    public List<Property> getEntityTriggerColumnsList() {
        Class<?> baseTableClass = getBaseTableClass();
        if (baseTableClass == null) {
            return getEntityTriggerColumnsList(getPropertiesList());
        }
        if (isJoinedTable()) {
            return getEntityTriggerColumnsList(getJoinedPropertiesList());
        }
        /* commented since 20200523
        List<Property> list = new ArrayList<>();
        Field field;
        Class<?> clazz;
        for (Property property : getPropertiesList()) {
            if (property.isCalculable()) {
                continue;
            }
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
        /**/
        return getEntityTriggerColumnsList(getPropertiesList());
    }

    private List<Property> getEntityTriggerColumnsList(List<Property> properties) {
        List<Property> list = new ArrayList<>();
        for (Property property : properties) {
            if (property.isCalculable()) {
                continue;
            }
            list.add(property);
        }
        return list;
    }

    /**
     * @param properties properties
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
//      Entity base = getBaseRoot();
//      PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
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
        return getChecksList(null);
    }

    /**
     * @param checkpoint the point at which the check is performed
     * @return the expressions that are checks checked at the specified checkpoint
     */
//  @Override
    public List<Expression> getChecksList(Checkpoint checkpoint) {
        return getChecksList(checkpoint, true);
    }

    /**
     * @param checkpoint the point at which the check is performed
     * @param inheritedless exclude inherited checks or not
     * @return the expressions that are checks checked at the specified checkpoint
     */
//  @Override
    public List<Expression> getChecksList(Checkpoint checkpoint, boolean inheritedless) {
        List<Expression> list = new ArrayList<>();
        Check check;
        Checkpoint point;
        Field field;
        Class<?> clazz;
        Class<?> baseTableClass = getBaseTableClass();
        boolean joinedTable = isJoinedTable();
        for (Expression expression : getExpressionsList()) {
            if (expression instanceof Check) {
                check = (Check) expression;
                point = check.getCheckpoint();
                if (checkpoint == null || checkpoint.equals(point) || Checkpoint.WHEREVER_POSSIBLE.equals(point)) {
                    field = expression.getDeclaringField();
                    clazz = field.getType();
                    if (Check.class.isAssignableFrom(clazz)) {
                        if (inheritedless && expression.isInherited()) {
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
                if (!clazz.getSimpleName().equals(type.getSimpleName())) { // comparison of simple names from 09/02/2023
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
            if (clazz.getSimpleName().equals(type.getSimpleName())) { // comparison of simple names from 09/02/2023
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
                if (clazz.getSimpleName().equals(type.getSimpleName())) { // comparison of simple names from 09/02/2023
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
                if (clazz.getSimpleName().equals(type.getSimpleName())) { // comparison of simple names from 09/02/2023
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
     * @param maxDepth max depth
     * @return the queryTable
     */
    public QueryTable getQueryTable(int maxDepth) {
        return getQueryTable(maxDepth, null);
    }

    /**
     * @param virtualEntityType virtual entity type
     * @return the queryTable
     */
    public QueryTable getQueryTable(VirtualEntityType virtualEntityType) {
        return getQueryTable(-1, virtualEntityType);
    }

    /**
     * @param maxDepth max depth
     * @param virtualEntityType virtual entity type
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
     * @return the data searchQueryTable
     */
    @Override
    public QueryTable getSearchQueryTable() {
        SqlProgrammer sp = ChiefProgrammer.getSqlProgrammer();
        if (sp == null) {
            throw new RuntimeException("null sql programmer");
        }
        if (_searchQueryTable != null && sp.equals(_searchQueryTable.getSqlProgrammer())) {
            return _searchQueryTable;
        }
        PersistentEntity reference;
        QueryTable queryTable;
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
        return _searchQueryTable;
    }

    /**
     * @return the searchQueryPropertiesList
     */
    @Override
    public List<Property> getSearchQueryPropertiesList() {
        return new ArrayList<>(getSearchQueryPropertiesMap().values());
    }

    /**
     * @return the searchQueryPropertiesMap
     */
    @Override
    public Map<String, Property> getSearchQueryPropertiesMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity root = getRoot();
        if (root instanceof PersistentEntity) {
            PersistentEntity pent = (PersistentEntity) root;
            QueryTable rootQueryTable = pent.getQueryTable();
            List<Property> rootQueryPropertiesList = pent.getQueryPropertiesList();
            QueryTable thisSearchQueryTable = getSearchQueryTable();
            Map<String, Property> thisSearchQueryTableColumnsMap = thisSearchQueryTable.getSelectColumnsMap();
            String key, alias, message;
            for (Property property : rootQueryPropertiesList) {
                key = property.getPathString();
                alias = rootQueryTable.getSqlAlias(property);
                if (thisSearchQueryTableColumnsMap.containsKey(alias)) {
                    map.put(key, thisSearchQueryTableColumnsMap.get(alias));
                } else {
                    message = property.getFullName() + " missing from " + fullName + " search query properties list; increase allocation parameters";
                    /*
                    logger.error(message);
                    Project.increaseParserErrorCount();
                    **/
                    log(Project.getTransitionLevel(), message);
                    TLC.getProject().getParser().increaseTransitionCount();
                }
            }
        }
        return map;
    }

    private static void log(Level level, String message) {
        if (LogUtils.fair(level)) {
            logger.log(level, message);
        }
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
        return getChecksColumnsList(null);
    }

    /**
     * @param checkpoint the point at which the check is performed
     * @return the referenced columns list
     */
//  @Override
    public List<Property> getChecksColumnsList(Checkpoint checkpoint) {
        return new ArrayList<>(getChecksColumnsMap(checkpoint).values());
    }

    /**
     * @return the referenced columns map
     */
//  @Override
    public Map<String, Property> getChecksColumnsMap() {
        return getChecksColumnsMap(null);
    }

    /**
     * @param checkpoint the point at which the check is performed
     * @return the referenced columns map
     */
//  @Override
    public Map<String, Property> getChecksColumnsMap(Checkpoint checkpoint) {
        Map<String, Property> map = new LinkedHashMap<>();
        List<Expression> checks = getChecksList(checkpoint);
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
        return getChecksJoinsList(null);
    }

    /**
     * @param checkpoint the point at which the check is performed
     * @return the referenced joins list
     */
//  @Override
    public List<QueryJoin> getChecksJoinsList(Checkpoint checkpoint) {
        return new ArrayList<>(getChecksJoinsMap(checkpoint).values());
    }

    /**
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getChecksJoinsMap() {
        return getChecksJoinsMap(null);
    }

    /**
     * @param checkpoint the point at which the check is performed
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getChecksJoinsMap(Checkpoint checkpoint) {
        Map<String, QueryJoin> map = new TreeMap<>();
        QueryTable queryTable = getQueryTable();
        List<Expression> checks = getChecksList(checkpoint);
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
     * @param checkpoints valid checkpoint names (null for all)
     * @param conditions valid conditions names (null for all)
     * @return the referenced columns list
     */
//  @Override
    public List<Property> getDefaultValueColumnsList(String checkpoints, String conditions) {
        return new ArrayList<>(getDefaultValueColumnsMap(checkpoints, conditions).values());
    }

    /**
     * @return the referenced columns map
     */
//  @Override
    public Map<String, Property> getDefaultValueColumnsMap() {
        return getDefaultValueColumnsMap(null, null);
    }

    /**
     * @param checkpoints valid checkpoint names (null for all)
     * @param conditions valid conditions names (null for all)
     * @return the referenced columns map for sql functions
     */
//  @Override
    public Map<String, Property> getDefaultValueColumnsMap(String checkpoints, String conditions) {
        Map<String, Property> map = new LinkedHashMap<>();
        Object defaultValue;
        Property defaultProperty;
        Expression defaultExpression;
        String[] checkpointsArray = StringUtils.split(StringUtils.remove(StringUtils.upperCase(checkpoints), ' '), ',');
        String[] conditionsArray = StringUtils.split(StringUtils.remove(StringUtils.upperCase(conditions), ' '), ',');
//      List<Property> columns = getColumnsList();
        List<Property> columns = getEntityTriggerColumnsList();
        for (Property column : columns) {
            if (checkpointsArray == null || ArrayUtils.contains(checkpointsArray, column.getDefaultCheckpoint().name())) {
                if (conditionsArray == null || ArrayUtils.contains(conditionsArray, column.getDefaultCondition().name())) {
                    defaultValue = column.getDefaultValue();
                    if (defaultValue instanceof Property) {
                        defaultProperty = (Property) defaultValue;
                        map.put(defaultProperty.getPathString(), defaultProperty);
                    } else if (defaultValue instanceof Expression) {
                        defaultExpression = (Expression) defaultValue;
                        map.putAll(defaultExpression.getReferencedColumnsMap());
                    }
                }
            }
        }
        return map;
    }

    /**
     * @return the referenced joins list
     */
//  @Override
    public List<QueryJoin> getDefaultValueJoinsList() {
        return getDefaultValueJoinsList(null, null);
    }

    /**
     * @param checkpoints valid checkpoint names (null for all)
     * @param conditions valid conditions names (null for all)
     * @return the referenced joins list
     */
//  @Override
    public List<QueryJoin> getDefaultValueJoinsList(String checkpoints, String conditions) {
        return getDefaultValueJoinsList(checkpoints, conditions, false);
    }

    /**
     * @param checkpoints valid checkpoint names (null for all)
     * @param conditions valid conditions names (null for all)
     * @param calculableless exclude values referencing calculable properties
     * @return the referenced joins list
     */
//  @Override
    public List<QueryJoin> getDefaultValueJoinsList(String checkpoints, String conditions, boolean calculableless) {
        return new ArrayList<>(getDefaultValueJoinsMap(checkpoints, conditions, calculableless).values());
    }

    /**
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getDefaultValueJoinsMap() {
        return getDefaultValueJoinsMap(null, null);
    }

    /**
     * @param checkpoints valid checkpoint names (null for all)
     * @param conditions valid conditions names (null for all)
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getDefaultValueJoinsMap(String checkpoints, String conditions) {
        return getDefaultValueJoinsMap(checkpoints, conditions, false);
    }

    /**
     * @param checkpoints valid checkpoint names (null for all)
     * @param conditions valid conditions names (null for all)
     * @param calculableless exclude values referencing calculable properties
     * @return the referenced joins map
     */
//  @Override
    public Map<String, QueryJoin> getDefaultValueJoinsMap(String checkpoints, String conditions, boolean calculableless) {
        Map<String, QueryJoin> map = new TreeMap<>();
        QueryTable queryTable = getQueryTable();
        Object defaultValue;
        Property defaultProperty;
        Expression defaultExpression;
        String[] checkpointsArray = StringUtils.split(StringUtils.remove(StringUtils.upperCase(checkpoints), ' '), ',');
        String[] conditionsArray = StringUtils.split(StringUtils.remove(StringUtils.upperCase(conditions), ' '), ',');
//      List<Property> columns = getColumnsList();
        List<Property> columns = getEntityTriggerColumnsList();
        for (Property column : columns) {
            if (checkpointsArray == null || ArrayUtils.contains(checkpointsArray, column.getDefaultCheckpoint().name())) {
                if (conditionsArray == null || ArrayUtils.contains(conditionsArray, column.getDefaultCondition().name())) {
                    defaultValue = column.getDefaultValue();
                    if (defaultValue instanceof Property) {
                        defaultProperty = (Property) defaultValue;
                        if (!(calculableless && calculatedProperty(defaultProperty))) {
                            map.putAll(queryTable.getReferencedJoinsMap(defaultProperty));
                        }
                    } else if (defaultValue instanceof Expression) {
                        defaultExpression = (Expression) defaultValue;
                        map.putAll(defaultExpression.getReferencedJoinsMap(queryTable));
                    }
                }
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
                if (clazz.getSimpleName().equals(type.getSimpleName())) { // comparison of simple names from 09/02/2023
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
//      Field field;
//      Class<?> clazz;
//      Class<?> type = getDataType();
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
