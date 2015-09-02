/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.Instance;
import adalid.core.Key;
import adalid.core.Operation;
import adalid.core.Project;
import adalid.core.Tab;
import adalid.core.Transition;
import adalid.core.Trigger;
import adalid.core.View;
import adalid.core.annotations.AllocationOverride;
import adalid.core.enums.BusinessKeyType;
import adalid.core.enums.DisplayMode;
import adalid.core.enums.HierarchyNodeType;
import adalid.core.enums.ListStyle;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.OperationLogging;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SearchType;
import adalid.core.enums.SortOption;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.EntityOrderedPairX;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public interface Entity extends Comparable<Entity>, DataArtifact {

    /**
     * @return the initialised indicator
     */
    boolean isInitialised();

    /**
     * @return the settled indicator
     */
    boolean isSettled();

    /**
     * @return the finalised indicator
     */
    boolean isFinalised();

    /**
     * @return the root indicator
     */
    boolean isRootInstance();

    /**
     * @return the explicitly declared indicator
     */
    boolean isExplicitlyDeclared();

    /**
     * @return the implicitly declared indicator
     */
    boolean isImplicitlyDeclared();

    /**
     * @return the reference index
     */
    int getReferenceIndex();

    /**
     * @param reference
     * @return the default label
     */
    String getDefaultLabel(EntityReference reference);

    /**
     * @param reference
     * @return the default short label
     */
    String getDefaultShortLabel(EntityReference reference);

    /**
     * @param reference
     * @return the default collection label
     */
    String getDefaultCollectionLabel(EntityReference reference);

    /**
     * @param reference
     * @return the default collection short label
     */
    String getDefaultCollectionShortLabel(EntityReference reference);

    /**
     * @return the properties list
     */
    List<Property> getPropertiesList();

    /**
     * @return the references list
     */
    List<Property> getReferencesList();

    /**
     * @return the parameter references list
     */
    List<Parameter> getParameterReferencesList();

    /**
     * @return the keys list
     */
    List<Key> getKeysList();

    /**
     * @return the tabs list
     */
    List<Tab> getTabsList();

    /**
     * @return the views list
     */
    List<View> getViewsList();

    /**
     * @return the instances list
     */
    List<Instance> getInstancesList();

    /**
     * @return the named values list
     */
    List<NamedValue> getNamedValuesList();

    /**
     * @return the expressions list
     */
    List<Expression> getExpressionsList();

    /**
     * @return the transitions list
     */
    List<Transition> getTransitionsList();

    /**
     * @return the operations list
     */
    List<Operation> getOperationsList();

    /**
     * @return the triggers list
     */
    List<Trigger> getTriggersList();

    /**
     * @return the properties map
     */
    Map<String, Property> getPropertiesMap();

    /**
     * @return the references map
     */
    Map<String, Property> getReferencesMap();

    /**
     * @return the parameter references map
     */
    Map<String, Parameter> getParameterReferencesMap();

    /**
     * @return the keys map
     */
    Map<String, Key> getKeysMap();

    /**
     * @return the tabs map
     */
    Map<String, Tab> getTabsMap();

    /**
     * @return the views map
     */
    Map<String, View> getViewsMap();

    /**
     * @return the instances map
     */
    Map<String, Instance> getInstancesMap();

    /**
     * @return the named values map
     */
    Map<String, NamedValue> getNamedValuesMap();

    /**
     * @return the transitions map
     */
    Map<String, Transition> getTransitionsMap();

    /**
     * @return the expressions map
     */
    Map<String, Expression> getExpressionsMap();

    /**
     * @return the operations map
     */
    Map<String, Operation> getOperationsMap();

    /**
     * @return the triggers map
     */
    Map<String, Trigger> getTriggersMap();

    /**
     * @return the operation classes Map
     */
    Map<String, Class<?>> getOperationClassesMap();

    /**
     * @return true if the entity is an abstract entity class
     */
    boolean isAbstractClass();

    /**
     * @return the base class
     */
    Class<?> getBaseClass();

    /**
     * @return the direct known subclasses list
     */
    List<Class<?>> getSubclassesList();

    /**
     * @return the direct known subclasses map
     */
    Map<String, Class<?>> getSubclassesMap();

    /**
     * @return the allocation overrides list
     */
    List<AllocationOverride> getAllocationOverridesList();

    /**
     * @return the allocation overrides map
     */
    Map<String, AllocationOverride> getAllocationOverridesMap();

    /**
     * @return the primary key field name
     */
    String getPrimaryKeyFieldName();

    /**
     * @return the version field name
     */
    String getVersionFieldName();

    /**
     * @return the numeric key field name
     */
    String getNumericKeyFieldName();

    /**
     * @return the character key field name
     */
    String getCharacterKeyFieldName();

    /**
     * @return the name field name
     */
    String getNameFieldName();

    /**
     * @return the description field name
     */
    String getDescriptionFieldName();

    /**
     * @return the inactive indicator field name
     */
    String getInactiveIndicatorFieldName();

    /**
     * @return the url field name
     */
    String getUrlFieldName();

    /**
     * @return the parent field name
     */
    String getParentFieldName();

    /**
     * @return the owner field name
     */
    String getOwnerFieldName();

    /**
     * @return the segment field name
     */
    String getSegmentFieldName();

    /**
     * @return the business key field name
     */
    String getBusinessKeyFieldName();

    /**
     * @return the primary key field
     */
    Field getPrimaryKeyField();

    /**
     * @return the version field
     */
    Field getVersionField();

    /**
     * @return the numeric key field
     */
    Field getNumericKeyField();

    /**
     * @return the character key field
     */
    Field getCharacterKeyField();

    /**
     * @return the name field
     */
    Field getNameField();

    /**
     * @return the description field
     */
    Field getDescriptionField();

    /**
     * @return the inactive indicator field
     */
    Field getInactiveIndicatorField();

    /**
     * @return the url field
     */
    Field getUrlField();

    /**
     * @return the parent field
     */
    Field getParentField();

    /**
     * @return the owner field
     */
    Field getOwnerField();

    /**
     * @return the segment field
     */
    Field getSegmentField();

    /**
     * @return the business key field
     */
    Field getBusinessKeyField();

    /**
     * @return the primary key property
     */
    Property getPrimaryKeyProperty();

    /**
     * @return the version property
     */
    LongProperty getVersionProperty();

    /**
     * @return the numeric key property
     */
    IntegerProperty getNumericKeyProperty();

    /**
     * @return the character key property
     */
    StringProperty getCharacterKeyProperty();

    /**
     * @return the name property
     */
    StringProperty getNameProperty();

    /**
     * @return the description property
     */
    StringProperty getDescriptionProperty();

    /**
     * @return the inactive indicator property
     */
    BooleanProperty getInactiveIndicatorProperty();

    /**
     * @return the url property
     */
    StringProperty getUrlProperty();

    /**
     * @return the parent property
     */
    Entity getParentProperty();

    /**
     * @return the owner property
     */
    Entity getOwnerProperty();

    /**
     * @return the segment property
     */
    Entity getSegmentProperty();

    /**
     * @return the business key property
     */
    Property getBusinessKeyProperty();

    /**
     * @return the business key type
     */
    BusinessKeyType getBusinessKeyType();

    /**
     * @return the existentially independent indicator
     */
    boolean isExistentiallyIndependent();

    /**
     * @return the resource yype
     */
    ResourceType getResourceType();

    /**
     * @return the resource gender
     */
    ResourceGender getResourceGender();

    /**
     * @return the properties prefix
     */
    String getPropertiesPrefix();

    /**
     * @return the properties suffix
     */
    String getPropertiesSuffix();

    /**
     * @return the help file name
     */
    String getHelpFileName();

    /**
     * @return the select allowed indicator
     */
    boolean isSelectEnabled();

//  /**
//   * @return the select restricted indicator
//   */
//  boolean isSelectRestricted();
//
    /**
     * @return the select operation access mode
     */
    OperationAccess isSelectOperationAccess();

    /**
     * @return the start-with
     */
    int getStartWith();

    /**
     * @return the select rows limit
     */
    int getSelectRowsLimit();

    /**
     * @return the select sort option
     */
    SortOption getSelectSortOption();

    /**
     * @return the insert allowed indicator
     */
    boolean isInsertEnabled();

//  /**
//   * @return the insert restricted indicator
//   */
//  boolean isInsertRestricted();
//
    /**
     * @return the insert operation access mode
     */
    OperationAccess isInsertOperationAccess();

    /**
     * @return the insert logging mode
     */
    OperationLogging getInsertLogging();

    /**
     * @return the update allowed indicator
     */
    boolean isUpdateEnabled();

//  /**
//   * @return the update restricted indicator
//   */
//  boolean isUpdateRestricted();
//
    /**
     * @return the update operation access mode
     */
    OperationAccess isUpdateOperationAccess();

    /**
     * @return the update logging mode
     */
    OperationLogging getUpdateLogging();

    /**
     * @return the delete allowed indicator
     */
    boolean isDeleteEnabled();

//  /**
//   * @return the delete restricted indicator
//   */
//  boolean isDeleteRestricted();
//
    /**
     * @return the delete operation access mode
     */
    OperationAccess isDeleteOperationAccess();

    /**
     * @return the delete logging mode
     */
    OperationLogging getDeleteLogging();

    /**
     * @return the report allowed indicator
     */
    boolean isReportEnabled();

    /**
     * @return the report rows limit
     */
    int getReportRowsLimit();

    /**
     * @return the report sort option
     */
    SortOption getReportSortOption();

    /**
     * @return the export allowed indicator
     */
    boolean isExportEnabled();

    /**
     * @return the export rows limit
     */
    int getExportRowsLimit();

    /**
     * @return the export sort option
     */
    SortOption getExportSortOption();

    /**
     * @return the table view allowed indicator
     */
    boolean isTableViewEnabled();

    /**
     * @return the detail view allowed indicator
     */
    boolean isDetailViewEnabled();

    /**
     * @return the tree view allowed indicator
     */
    boolean isTreeViewEnabled();

    /**
     * @return the console view allowed indicator
     */
    boolean isConsoleViewEnabled();

    /**
     * @return the console view allowed indicator
     */
    boolean isWarningsEnabled();

    /**
     * @return the SQL code generation enabled indicator
     */
    boolean isSqlCodeGenEnabled();

    /**
     * @return the search type
     */
    SearchType getSearchType();

    /**
     * @return the list style
     */
    ListStyle getListStyle();

    /**
     * @return the search display mode
     */
    DisplayMode getSearchDisplayMode();

    /**
     * @return the search query filter
     */
    BooleanExpression getSearchQueryFilter();

    /**
     * @return the select filter
     */
    BooleanExpression getSelectFilter();

    /**
     * @param reference
     * @return the insert filter
     */
    BooleanExpression getInsertFilter(EntityReference reference);

    /**
     * @return the update filter
     */
    BooleanExpression getUpdateFilter();

    /**
     * @return the delete filter
     */
    BooleanExpression getDeleteFilter();

    /**
     * @param reference
     * @return the master/detail filter
     */
    BooleanExpression getMasterDetailFilter(EntityReference reference);

    /**
     * @return true if the entity is master of at least one detail
     */
    boolean isWritingPageMaster();

    void initialise();

    void settle();

    void finalise();

    Object addAttribute(Property property, String name, Object value);

    /**
     * @return the declaring project, if any; null otherwise
     */
    Project getDeclaringProject();

    /**
     * @return the root entity instance of the same class
     */
    Entity getRoot();

    /**
     * @return the root entity instance of the base class
     */
    Entity getBaseRoot();

    /**
     * @return the root entity instance of the top of the class hierarchy
     */
    Entity getHierarchyRoot();

    /**
     * @return the class hierarchy node type; null if the entity is not part of a hierarchy
     */
    HierarchyNodeType getHierarchyNodeType();

    /**
     * @return the direct known extensions list
     */
    List<Entity> getExtensionsList();

    /**
     * @return the direct known extensions map
     */
    Map<String, Entity> getExtensionsMap();

    /**
     * @return the CRUD operation list
     */
    List<Operation> getCrudOperationsList();

    /**
     * @return the user-defined business operation list
     */
    List<Operation> getBusinessOperationsList();

    BooleanComparisonX isNull();

    BooleanComparisonX isNotNull();

    BooleanComparisonX isEqualTo(Entity y);

    BooleanComparisonX isEqualTo(Instance y);

    BooleanComparisonX isEqualTo(EntityExpression y);

    BooleanComparisonX isNotEqualTo(Entity y);

    BooleanComparisonX isNotEqualTo(Instance y);

    BooleanComparisonX isNotEqualTo(EntityExpression y);

    BooleanComparisonX isNullOrEqualTo(Entity y);

    BooleanComparisonX isNullOrEqualTo(Instance y);

    BooleanComparisonX isNullOrEqualTo(EntityExpression y);

    BooleanComparisonX isNullOrNotEqualTo(Entity y);

    BooleanComparisonX isNullOrNotEqualTo(Instance y);

    BooleanComparisonX isNullOrNotEqualTo(EntityExpression y);

    EntityOrderedPairX coalesce(Entity y);

    EntityOrderedPairX coalesce(Instance y);

    EntityOrderedPairX coalesce(EntityExpression y);

}
