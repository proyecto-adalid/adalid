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
package adalid.core.interfaces;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public interface Entity extends Comparable<Entity>, DataArtifact, EntityReferenceContainer {

    /**
     * @return the initialised indicator
     */
    boolean isInitialised();
//
//  /**
//   * @return the prepared indicator
//   */
//  boolean isPrepared();

    /**
     * @return the settled indicator
     */
    boolean isSettled();

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
     * @param reference entity reference
     * @return the default label
     */
    String getDefaultLabel(EntityReference reference);

    /**
     * @param reference entity reference
     * @return the default short label
     */
    String getDefaultShortLabel(EntityReference reference);

    /**
     * @param reference entity reference
     * @return the default collection label
     */
    String getDefaultCollectionLabel(EntityReference reference);

    /**
     * @param reference entity reference
     * @return the default collection short label
     */
    String getDefaultCollectionShortLabel(EntityReference reference);

    /**
     * @param format the display format
     * @return the localized menu option label
     */
    String getDefaultMenuOptionLabel(DisplayFormat format);

    /**
     * @param locale locale
     * @param reference entity reference
     * @return the localized label
     */
    String getLocalizedLabel(Locale locale, EntityReference reference);

    /**
     * @param locale locale
     * @param reference entity reference
     * @return the localized short label
     */
    String getLocalizedShortLabel(Locale locale, EntityReference reference);

    /**
     * @param locale locale
     * @param reference entity reference
     * @return the localized collection label
     */
    String getLocalizedCollectionLabel(Locale locale, EntityReference reference);

    /**
     * @param locale locale
     * @param reference entity reference
     * @return the localized collection short label
     */
    String getLocalizedCollectionShortLabel(Locale locale, EntityReference reference);

    /**
     * @param locale the locale for the label
     * @param format the display format
     * @return the localized menu option label
     */
    String getLocalizedMenuOptionLabel(Locale locale, DisplayFormat format);

    /**
     * @return the number of reference properties
     */
    int getReferencePropertiesCount();

    /**
     * @return the properties list
     */
    List<Property> getPropertiesList();

    /**
     * @return the entity collection list
     */
    List<EntityCollection> getEntityCollectionsList();

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
     * @return the steps list
     */
    List<Step> getStepsList();

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
     * @return the operation classes list
     */
    List<Class<?>> getOperationClassesList();

    /**
     * @return the triggers list
     */
    List<Trigger> getTriggersList();

    /**
     * @return the calculable properties list
     */
    List<Property> getCalculablePropertiesList();

    /**
     * @return the overlay properties list
     */
    List<Property> getOverlayPropertiesList();

    /**
     * @return the query properties list
     */
    List<Property> getQueryPropertiesList();

    /**
     * @return the properties map
     */
    Map<String, Property> getPropertiesMap();

    /**
     * @return the entity collection map
     */
    Map<String, EntityCollection> getEntityCollectionsMap();

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
     * @return the staps map
     */
    Map<String, Step> getStepsMap();

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
     * @return the expressions map
     */
    Map<String, Expression> getExpressionsMap();

    /**
     * @return the transitions map
     */
    Map<String, Transition> getTransitionsMap();

    /**
     * @return the operations map
     */
    Map<String, Operation> getOperationsMap();

    /**
     * @return the operation classes map
     */
    Map<String, Class<?>> getOperationClassesMap();

    /**
     * @return the triggers map
     */
    Map<String, Trigger> getTriggersMap();

    /**
     * @return the calculable properties map
     */
    Map<String, Property> getCalculablePropertiesMap();

    /**
     * @return the overlay properties map
     */
    Map<String, Property> getOverlayPropertiesMap();

    /**
     * @return the query properties map
     */
    Map<String, Property> getQueryPropertiesMap();

    /**
     * @param type an entity class
     * @return the main entity reference from another entity class
     */
    EntityReference getMainEntityReferenceFrom(Class<?> type);

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
     * @return the sequence field name
     */
    String getSequenceFieldName();

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
     * @return the image field name
     */
    String getImageFieldName();

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
     * @return the user field name
     */
    String getUserFieldName();

    /**
     * @return the segment field name
     */
    String getSegmentFieldName();

    /**
     * @return the business key field name
     */
    String getBusinessKeyFieldName();

    /**
     * @return the state field name
     */
    String getStateFieldName();

    default String getBusinessKeyValueOf(Instance instance) {
        return instance == null ? null : instance.getName();
    }

    default Object getDefaultPropertyValueOf(Instance instance, Property property) {
        return null;
    }

    /**
     * @return the primary key field
     */
    Field getPrimaryKeyField();

    /**
     * @return the sequence field
     */
    Field getSequenceField();

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
     * @return the image field
     */
    Field getImageField();

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
     * @return the user field
     */
    Field getUserField();

    /**
     * @return the segment field
     */
    Field getSegmentField();

    /**
     * @return the business key field
     */
    Field getBusinessKeyField();

    /**
     * @return the state field
     */
    Field getStateField();

    /**
     * @return the primary key property
     */
    Property getPrimaryKeyProperty();

    /**
     * @return the sequence property
     */
    LongProperty getSequenceProperty();

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
     * @return the image property
     */
    BinaryProperty getImageProperty();

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
     * @return the user property
     */
    Entity getUserProperty();

    /**
     * @return the segment property
     */
    DataArtifact getSegmentProperty(); // since 20210218

    /**
     * @return the business key property
     */
    Property getBusinessKeyProperty();

    /**
     * @return the business key type
     */
    BusinessKeyType getBusinessKeyType();

    /**
     * @return the state property
     */
    Entity getStateProperty();

    /**
     * @return the basic operation entity indicator
     */
    boolean isBasicOperationEntity();

    /**
     * @return the catalog entity indicator
     */
    boolean isCatalogEntity();

    /**
     * @return true if it is a contextual entity; otherwise false
     */
    boolean isContextualEntity();

    /**
     * @return true if it is a enumeration entity; otherwise false
     */
    boolean isEnumerationEntity();

    /**
     * @return true if it is a non-enumeration entity; otherwise false
     */
    boolean isNonEnumerationEntity();

    /**
     * @return true if it is a database entity; otherwise false
     */
    boolean isDatabaseEntity();

    /**
     * @return true if it is a persistent entity; otherwise false
     */
    boolean isPersistentEntity();

    /**
     * @return true if it is a persistent enumeration entity; otherwise false
     */
    boolean isPersistentEnumerationEntity();

    /**
     * @return true if it is a persistent non-enumeration entity; otherwise false
     */
    boolean isPersistentNonEnumerationEntity();

    /**
     * @return true if it is an entity collector; otherwise false
     */
    boolean isEntityCollector();

    /**
     * @return true if is a overlayable entity; otherwise false
     */
    boolean isOverlayableEntity();

    /**
     * @return true if is a overlayable entity reference; otherwise false
     */
    boolean isOverlayableEntityReference();

    /**
     * @return the existentially independent indicator
     */
    boolean isExistentiallyIndependent();

    /**
     * @return the entity view type
     */
    EntityViewType getEntityViewType();

    /**
     * @return the variant entity indicator
     */
    boolean isVariantEntity();

    /**
     * @return the invariant entity indicator
     */
    boolean isInvariantEntity();

    /**
     * @return true if the segment property and the owner property are the same property
     */
    boolean isSegmentEqualToOwner();

    /**
     * @return true if the segment property and the primary key property are the same property
     */
    boolean isSegmentEqualToPrimaryKey();

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
     * @return the collection name
     */
    String getCollectionName();

    /**
     * @return the help document
     */
    String getHelpDocument();

    /**
     * sets the help document.
     *
     * @param document definición del documento incrustado de ayuda
     */
    void setHelpDocument(String document);

    /**
     * @return the help file name
     */
    String getHelpFileName();

    /**
     * sets the help file name.
     *
     * @param fileName nombre del archivo de ayuda
     */
    void setHelpFileName(String fileName);

    /**
     * @return the help file auto name
     */
    HelpFileAutoName getHelpFileAutoName();

    /**
     * @return the help file auto type
     */
    String getHelpFileAutoType();

    /**
     * @return the select allowed indicator
     */
    boolean isSelectEnabled();

    /**
     * @param enabled the select enabled indicator to set
     */
    void setSelectEnabled(boolean enabled);

    /**
     * @return the select operation access mode
     */
    OperationAccess isSelectOperationAccess();

    /**
     * @return the select onload option
     */
    SelectOnloadOption getSelectOnloadOption();

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

    /**
     * @param enabled the insert enabled indicator to set
     */
    void setInsertEnabled(boolean enabled);

    /**
     * @return the insert confirmation required indicator
     */
    boolean isInsertConfirmationRequired();

    /**
     * @return the insert operation access mode
     */
    OperationAccess getInsertOperationAccess();

    /**
     * @return the insert logging mode
     */
    OperationLogging getInsertLogging();

    /**
     * @return the update allowed indicator
     */
    boolean isUpdateEnabled();

    /**
     * @param enabled the update enabled indicator to set
     */
    void setUpdateEnabled(boolean enabled);

    /**
     * @return the update confirmation required indicator
     */
    boolean isUpdateConfirmationRequired();

    /**
     * @return the update operation access mode
     */
    OperationAccess getUpdateOperationAccess();

    /**
     * @return the update logging mode
     */
    OperationLogging getUpdateLogging();

    /**
     * @return the delete allowed indicator
     */
    boolean isDeleteEnabled();

    /**
     * @param enabled the delete enabled indicator to set
     */
    void setDeleteEnabled(boolean enabled);

    /**
     * @return the delete confirmation required indicator
     */
    boolean isDeleteConfirmationRequired();

    /**
     * @return the delete operation access mode
     */
    OperationAccess getDeleteOperationAccess();

    /**
     * @return the delete logging mode
     */
    OperationLogging getDeleteLogging();

    /**
     * @return the report allowed indicator
     */
    boolean isReportEnabled();

    /**
     * @param enabled the report enabled indicator to set
     */
    void setReportEnabled(boolean enabled);

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
     * @param enabled the export enabled indicator to set
     */
    void setExportEnabled(boolean enabled);

    /**
     * @return the export rows limit
     */
    int getExportRowsLimit();

    /**
     * @return the export sort option
     */
    SortOption getExportSortOption();

    /**
     * @return the foreign entity class indicator
     */
    boolean isForeignEntityClass();

    /**
     * El método setForeignEntityClass se utiliza para especificar si la entidad se debe agregar, o no, al conjunto de entidades foráneas de la
     * aplicación. Las entidades foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos de la aplicación, sino en
     * otra que tipicamente reside en un servidor diferente.
     *
     * @param foreignEntityClass true o false para agregar o no la entidad, respectivamente; null, para agregar la entidad solo si pertenece a un
     * módulo de entidades foráneas (vea el elemento foreign de la Anotación ProjectModule)
     */
    void setForeignEntityClass(Boolean foreignEntityClass);

    /**
     * @return the private entity class indicator
     */
    boolean isPrivateEntityClass();

    /**
     * El método setPrivateEntityClass se utiliza para especificar si la entidad se debe agregar, o no, al conjunto de entidades privadas de la
     * aplicación. Las entidades privadas son entidades para las que no se deben generar vistas.
     *
     * @param privateEntityClass true o false para agregar o no la entidad, respectivamente; null, para agregar la entidad solo si pertenece a un
     * módulo de entidades privadas (vea el elemento privacy de la Anotación ProjectModule)
     */
    void setPrivateEntityClass(Boolean privateEntityClass);

    /**
     * @return the link-outer-children indicator
     */
    boolean isLinkOuterChildren();

    /**
     * @return the link-outer-collaterals indicator
     */
    boolean isLinkOuterCollaterals();

    /**
     * @return the link-outer-siblings indicator
     */
    boolean isLinkOuterSiblings();

    /**
     * @return the table view enabled indicator
     */
    boolean isTableViewEnabled();

    /**
     * @param enabled the table view enabled indicator to set
     */
    void setTableViewEnabled(boolean enabled);

    /**
     * @return the table-view-with-insert-enabled indicator
     */
    boolean isTableViewWithInsertEnabled();

    /**
     * @return the table-view-with-update-enabled indicator
     */
    boolean isTableViewWithUpdateEnabled();

    /**
     * @return the table-view-with-delete-enabled indicator
     */
    boolean isTableViewWithDeleteEnabled();

    /**
     * @return the table-view-with-master-heading indicator
     */
    boolean isTableViewWithMasterHeading();

    /**
     * @return the table view help document
     */
    String getTableViewHelpDocument();

    /**
     * @return the table view help file name
     */
    String getTableViewHelpFileName();

    /**
     * @return the detail view enabled indicator
     */
    boolean isDetailViewEnabled();

    /**
     * @param enabled the detail view enabled indicator to set
     */
    void setDetailViewEnabled(boolean enabled);

    /**
     * @return the detail view help document
     */
    String getDetailViewHelpDocument();

    /**
     * @return the detail view help file name
     */
    String getDetailViewHelpFileName();

    /**
     * @return the tree view enabled indicator
     */
    boolean isTreeViewEnabled();

    /**
     * @param enabled the tree view enabled indicator to set
     */
    void setTreeViewEnabled(boolean enabled);

    /**
     * @return the tree view help document
     */
    String getTreeViewHelpDocument();

    /**
     * @return the tree view help file name
     */
    String getTreeViewHelpFileName();

    /**
     * @return the console view enabled indicator
     */
    boolean isConsoleViewEnabled();

    /**
     * @param enabled the console view enabled indicator to set
     */
    void setConsoleViewEnabled(boolean enabled);

    /**
     * @return the console view help document
     */
    String getConsoleViewHelpDocument();

    /**
     * @return the console view help file name
     */
    String getConsoleViewHelpFileName();

    /**
     * @return the warnings enabled indicator
     */
    boolean isWarningsEnabled();

    /**
     * @return the special expressions warnings enabled indicator
     */
    boolean isSpecialExpressionsWarningsEnabled();

    /**
     * @return the unusual expressions warnings enabled indicator
     */
    boolean isUnusualExpressionsWarningsEnabled();

    /**
     * @return the business process logic code generation enabled indicator
     */
    boolean isBplCodeGenEnabled();

    /**
     * @param enabled the business process logic code generation enabled indicator to set
     */
    void setBplCodeGenEnabled(boolean enabled);

    /**
     * @return the business web service code generation enabled indicator
     */
    boolean isBwsCodeGenEnabled();

    /**
     * @param enabled the business web service code generation enabled indicator to set
     */
    void setBwsCodeGenEnabled(boolean enabled);

    /**
     * @return the data access facade code generation enabled indicator
     */
    boolean isDafCodeGenEnabled();

    /**
     * @param enabled the data access facade code generation enabled indicator to set
     */
    void setDafCodeGenEnabled(boolean enabled);

    /**
     * @return the facade web service code generation enabled indicator
     */
    boolean isFwsCodeGenEnabled();

    /**
     * @param enabled the facade web service code generation enabled indicator to set
     */
    void setFwsCodeGenEnabled(boolean enabled);

    /**
     * @return the GUI code generation enabled indicator
     */
    boolean isGuiCodeGenEnabled();

    /**
     * @param enabled the GUI code generation enabled indicator to set
     */
    void setGuiCodeGenEnabled(boolean enabled);

    /**
     * @return the SQL code generation enabled indicator
     */
    boolean isSqlCodeGenEnabled();

    /**
     * @param enabled the SQL code generation enabled indicator to set
     */
    void setSqlCodeGenEnabled(boolean enabled);

    /**
     * @return the entity state machine code generation enabled indicator
     */
    boolean isEntityStateCodeGenEnabled();

    /**
     * @return the entity class diagram generation indicator
     */
    boolean isEntityClassDiagramGenEnabled();

    /**
     * @return the entity state diagram generation indicator
     */
    boolean isEntityStateDiagramGenEnabled();

    /**
     * @return the entity insert activity diagram generation indicator
     */
    boolean isEntityInsertActivityDiagramGenEnabled();

    /**
     * @return the entity update activity diagram generation indicator
     */
    boolean isEntityUpdateActivityDiagramGenEnabled();

    /**
     * @param name property's name
     * @return the property with the specified name
     */
    Property getProperty(String name);

    /**
     * @return the reference style
     */
    EntityReferenceStyle getReferenceStyle();

    /**
     * @return the reference filter-by
     */
    EntityReferenceProperty getReferenceFilterBy();

    /**
     * @return the reference filter-by proerty
     */
    Property getReferenceFilterByProperty();

    /**
     * @return the reference sort-by
     */
    EntityReferenceProperty getReferenceSortBy();

    /**
     * @return the reference sort-by proerty
     */
    Property getReferenceSortByProperty();

    /**
     * @return the search type
     */
    SearchType getSearchType();

    /**
     * @return the list style
     */
    ListStyle getListStyle();

    /**
     * @return the search display format
     */
    SearchDisplayFormat getSearchDisplayFormat();

    /**
     * @return the search display mode
     */
    DisplayMode getSearchDisplayMode();

    /**
     * @return the origin (protocol + server + port) of the application containing the entity views
     */
    String getApplicationOrigin();

    void setApplicationOrigin(String origin);

    /**
     * @return the context-root of the application containing the entity views
     */
    String getApplicationContextRoot();

    void setApplicationContextRoot(String contextRoot);

    /**
     * @return the path of the processing consoles within the context-root of the application that contains the entity views
     */
    String getApplicationConsolePath();

    void setApplicationConsolePath(String consolePath);

    /**
     * @return the path of the reading views within the context-root of the application that contains the entity views
     */
    String getApplicationReadingPath();

    void setApplicationReadingPath(String readingPath);

    /**
     * @return the path of the writing views within the context-root of the application that contains the entity views
     */
    String getApplicationWritingPath();

    void setApplicationWritingPath(String writingPath);

    /**
     * @return true if the views are in the generated application (default origin and context-root)
     */
    boolean isApplicationDefaultLocation();

    /**
     * @return the search query filter
     */
    BooleanExpression getSearchQueryFilter();

    /**
     * @return the select filter
     */
    BooleanExpression getSelectFilter();

    /**
     * @param reference entity reference
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
     * @param reference entity reference
     * @return the master/detail filter
     */
    BooleanExpression getMasterDetailFilter(EntityReference reference);

    /**
     * @return the initial value
     */
    Object getInitialValue();

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    void setInitialValue(Entity initialValue);

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    void setInitialValue(Instance initialValue);

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    void setInitialValue(EntityExpression initialValue);

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    void setInitialValue(SpecialEntityValue initialValue);

    /**
     * @return the default value
     */
    Object getDefaultValue();

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    void setDefaultValue(Entity defaultValue);

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    void setDefaultValue(Instance defaultValue);

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    void setDefaultValue(EntityExpression defaultValue);

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    void setDefaultValue(SpecialEntityValue defaultValue);

    /**
     * @return true if the entity has at least one display
     */
    boolean isDisplayAvailable();

    void setDisplayAvailable(Boolean displayAvailable);

    /**
     * @return true if the entity is master of at least one detail
     */
    boolean isWritingPageMaster();

    void initialise();

    void prepare();

    void settle();

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
     * @return the ancestors list
     */
    List<Entity> getAncestorsList();

    /**
     * @return the known extensions list
     */
    List<Entity> getExtensionsList();

    /**
     * @return the direct known extensions map
     */
    Map<String, Entity> getExtensionsMap();

    /**
     * @return the overlay entities list
     */
    List<Entity> getOverlayEntitiesList();

    /**
     * @return the overlay entities map
     */
    Map<String, Entity> getOverlayEntitiesMap();

    /**
     * @return the accesible operations overlay entities list
     */
    List<Entity> getAccesibleOperationsOverlayEntitiesList();

    /**
     * @return the accesible operations overlay entities map
     */
    Map<String, Entity> getAccesibleOperationsOverlayEntitiesMap();

    /**
     * @param <T> generic operation type
     * @param type operation type
     * @return the operation of the specified class
     */
    <T extends Operation> T getOperation(Class<T> type);

    /**
     * @return the CRUD operation list
     */
    List<Operation> getCrudOperationsList();

    /**
     * @return the user-defined business operation list
     */
    List<Operation> getBusinessOperationsList();

    /**
     * @return the user-defined accesible business operation list
     */
    List<Operation> getAccesibleBusinessOperationsList();

    /**
     * @return the array of properties that should be excluded from the heading of Master/Detail views (pages)
     */
    Property[] getRemoveHeadingPropertyArray();

    /**
     * @return true if the array contains the properties that should be excluded from the heading of Master/Detail views (pages); false, if the array
     * contains the properties that should not be excluded from the heading.
     */
    boolean isRemovePropertiesInRemoveHeadingPropertyArray();

    /**
     * El método <b>isNull</b> contruye una expresión lógica que genera la comparación de esta entidad con el valor nulo. La comparación resulta en
     * verdadero si el valor de la entidad es nulo.
     *
     * @return expresión lógica que genera la comparación con el valor nulo.
     */
    @Override
    BooleanComparisonX isNull();

    /**
     * El método <b>isNotNull</b> contruye una expresión lógica que genera la comparación de esta entidad con el valor nulo. La comparación resulta en
     * verdadero si el valor de la entidad no es nulo.
     *
     * @return expresión lógica que genera la comparación con el valor nulo.
     */
    @Override
    BooleanComparisonX isNotNull();

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la entidad que recibe como
     * argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(Entity y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la instancia que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(Instance y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isEqualTo(EntityExpression y);

    /**
     * El método <b>isEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
//  BooleanComparisonX isEqualTo(SpecialEntityValue y);
//
    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la entidad que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(Entity y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la instancia que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(Instance y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNotEqualTo(EntityExpression y);

    /**
     * El método <b>isNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
//  BooleanComparisonX isNotEqualTo(SpecialEntityValue y);
//
    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la entidad que recibe
     * como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(Entity y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la instancia que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(Instance y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrEqualTo(EntityExpression y);

    /**
     * El método <b>isNullOrEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
//  BooleanComparisonX isNullOrEqualTo(SpecialEntityValue y);
//
    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la entidad que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(Entity y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la instancia que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(Instance y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
    BooleanComparisonX isNullOrNotEqualTo(EntityExpression y);

    /**
     * El método <b>isNullOrNotEqualTo</b> contruye una expresión lógica que genera la comparación de esta entidad (operando X) con la expresión que
     * recibe como argumento (operando Y). La comparación resulta en verdadero si el valor del operando X es nulo o si ambos operandos no son iguales.
     *
     * @param y operando Y
     * @return expresión lógica que genera la comparación de ambos operandos.
     */
//  BooleanComparisonX isNullOrNotEqualTo(SpecialEntityValue y);
//
    /**
     * El método <b>self</b> contruye una expresión que da como resultado esta entidad.
     *
     * @return expresión que da como resultado esta entidad.
     */
    EntityScalarX self();

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta entidad (operando X) y de la entidad que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que genera el primero de de los operandos que no sea nulo.
     */
    EntityOrderedPairX coalesce(Entity y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta entidad (operando X) y de la instancia que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que genera el primero de de los operandos que no sea nulo.
     */
    EntityOrderedPairX coalesce(Instance y);

    /**
     * El método <b>coalesce</b> contruye una expresión que genera la comparación de esta entidad (operando X) y de la expresión que recibe como
     * argumento (operando Y) con el valor nulo, en ese orden. La expresión generada retorna el primero de los operandos que no sea nulo.
     *
     * @param y operando Y
     * @return expresión que genera el primero de de los operandos que no sea nulo.
     */
    EntityOrderedPairX coalesce(EntityExpression y);

}
