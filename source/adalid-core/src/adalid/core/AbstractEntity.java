/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.StrUtils;
import adalid.commons.util.ThrowableUtils;
import adalid.core.annotations.AbstractClass;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.AllocationOverride;
import adalid.core.annotations.AllocationOverrides;
import adalid.core.annotations.BaseField;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.CastingField;
import adalid.core.annotations.CharacterKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityBusinessKey;
import adalid.core.annotations.EntityCharacterKey;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityCodeGen;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDataGen;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDescriptionProperty;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityExportOperation;
import adalid.core.annotations.EntityInactiveIndicator;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntityNameProperty;
import adalid.core.annotations.EntityNumericKey;
import adalid.core.annotations.EntityOwnerProperty;
import adalid.core.annotations.EntityParentProperty;
import adalid.core.annotations.EntityPrimaryKey;
import adalid.core.annotations.EntityReferenceDataGen;
import adalid.core.annotations.EntityReferenceSearch;
import adalid.core.annotations.EntityReportOperation;
import adalid.core.annotations.EntitySegmentProperty;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.EntityUrlProperty;
import adalid.core.annotations.EntityVersionProperty;
import adalid.core.annotations.EntityWarnings;
import adalid.core.annotations.Extension;
import adalid.core.annotations.Filter;
import adalid.core.annotations.InactiveIndicator;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.LastUserProperty;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.NumericKey;
import adalid.core.annotations.OneToOne;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.ParentProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.SegmentProperty;
import adalid.core.annotations.UniqueKey;
import adalid.core.annotations.UrlProperty;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.BusinessKeyType;
import adalid.core.enums.DisplayMode;
import adalid.core.enums.HierarchyNodeType;
import adalid.core.enums.KeyProperty;
import adalid.core.enums.ListStyle;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.OperationLogging;
import adalid.core.enums.OperationType;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SearchType;
import adalid.core.enums.SortOption;
import adalid.core.enums.SpecialEntityValue;
import adalid.core.exceptions.UnexpectedRuntimeException;
import adalid.core.expressions.BooleanComparisonX;
import adalid.core.expressions.BooleanDataAggregateX;
import adalid.core.expressions.BooleanOrderedPairX;
import adalid.core.expressions.BooleanScalarX;
import adalid.core.expressions.EntityOrderedPairX;
import adalid.core.expressions.XB;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityExpression;
import adalid.core.interfaces.EntityReference;
import adalid.core.interfaces.EnumerationEntity;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.NamedValue;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.State;
import adalid.core.operations.DeleteOperation;
import adalid.core.operations.InsertOperation;
import adalid.core.operations.SelectOperation;
import adalid.core.operations.UpdateOperation;
import adalid.core.primitives.BooleanPrimitive;
import adalid.core.primitives.CharacterPrimitive;
import adalid.core.primitives.NumericPrimitive;
import adalid.core.primitives.TemporalPrimitive;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import adalid.core.wrappers.EntityWrapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractEntity extends AbstractDataArtifact implements EntityReference {

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

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(Entity.class);

    private static final String EOL = "\n";

    /**
     *
     */
    private boolean _initialised;

    /**
     *
     */
    private boolean _settled;

    /**
     *
     */
    private boolean _finalised;

    /**
     *
     */
    private Project _project;

    /**
     *
     */
    private boolean _rootInstance;

    /**
     *
     */
    private boolean _explicitlyDeclared;

    /**
     *
     */
    private boolean _implicitlyDeclared;

    /**
     *
     */
    private int _referenceIndex;

    /**
     *
     */
    private final Map<EntityReference, String> _defaultLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<EntityReference, String> _defaultShortLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<EntityReference, String> _defaultCollectionLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<EntityReference, String> _defaultCollectionShortLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final EntityAtlas _atlas = new EntityAtlas(this);

    /**
     * baseClass: concrete entity superclass, if any (null otherwise)
     */
    private Class<?> _baseClass;

    /**
     * subclasses: direct known subclasses
     */
    private final Map<String, Class<?>> _subclasses = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<String, AllocationOverride> _allocationOverrides = new LinkedHashMap<>();

    /**
     *
     */
    private String _primaryKeyFieldName;

    /**
     *
     */
    private Field _primaryKeyField;

    /**
     * primaryKeyProperty: reference to the entity primary key property, if any (null otherwise)
     */
    private Property _primaryKeyProperty;

    /**
     *
     */
    private String _versionFieldName;

    /**
     *
     */
    private Field _versionField;

    /**
     * versionProperty: reference to the entity version property, if any (null otherwise)
     */
    private LongProperty _versionProperty;

    /**
     *
     */
    private String _numericKeyFieldName;

    /**
     *
     */
    private Field _numericKeyField;

    /**
     * numericKeyProperty: reference to the entity numeric key property, if any (null otherwise)
     */
    private IntegerProperty _numericKeyProperty;

    /**
     *
     */
    private String _characterKeyFieldName;

    /**
     *
     */
    private Field _characterKeyField;

    /**
     * characterKeyProperty: reference to the entity character key property, if any (null otherwise)
     */
    private StringProperty _characterKeyProperty;

    /**
     *
     */
    private String _nameFieldName;

    /**
     *
     */
    private Field _nameField;

    /**
     * nameProperty: reference to the entity name (short description) property, if any (null otherwise)
     */
    private StringProperty _nameProperty;

    /**
     *
     */
    private String _descriptionFieldName;

    /**
     *
     */
    private Field _descriptionField;

    /**
     * descriptionProperty: reference to the entity description property, if any (null otherwise)
     */
    private StringProperty _descriptionProperty;

    /**
     *
     */
    private String _inactiveIndicatorFieldName;

    /**
     *
     */
    private Field _inactiveIndicatorField;

    /**
     * inactiveIndicatorProperty: reference to the entity inactive indicator property, if any (null otherwise)
     */
    private BooleanProperty _inactiveIndicatorProperty;

    /**
     *
     */
    private boolean _foreignInactiveIndicatorProperty;

    /**
     *
     */
    private String _urlFieldName;

    /**
     *
     */
    private Field _urlField;

    /**
     * urlProperty: reference to the entity URL property, if any (null otherwise)
     */
    private StringProperty _urlProperty;

    /**
     *
     */
    private String _parentFieldName;

    /**
     *
     */
    private Field _parentField;

    /**
     * parentProperty: parent entity reference, if any (null otherwise)
     */
    private Entity _parentProperty;

    /**
     *
     */
    private String _ownerFieldName;

    /**
     *
     */
    private Field _ownerField;

    /**
     * ownerProperty: owner (user) entity reference, if any (null otherwise)
     */
    private Entity _ownerProperty;

    /**
     *
     */
    private boolean _foreignOwnerProperty;

    /**
     *
     */
    private String _segmentFieldName;

    /**
     *
     */
    private Field _segmentField;

    /**
     * segmentProperty: reference to the entity segment property, if any (null otherwise)
     */
    private Entity _segmentProperty;

    /**
     *
     */
    private boolean _foreignSegmentProperty;

    /**
     *
     */
    private String _businessKeyFieldName;

    /**
     *
     */
    private Field _businessKeyField;

    /**
     * businessKeyProperty: reference to the entity business key property, if any (null otherwise)
     */
    private Property _businessKeyProperty;

    /**
     * businessKeyType: character key, numeric key or unspecified
     */
    private BusinessKeyType _businessKeyType;

    /**
     * existentiallyIndependent: existentially independent entity indicator
     */
    private boolean _existentiallyIndependent;

    /**
     * resourceType: configuration, operation or unspecified
     */
    private ResourceType _resourceType;

    /**
     * resourceGender: masculine, feminine, common, neuter or unspecified
     */
    private ResourceGender _resourceGender;

    /**
     *
     */
    private String _propertiesPrefix;

    /**
     *
     */
    private String _propertiesSuffix;

    /**
     *
     */
    private String _helpFileName;

    /**
     *
     */
    private int _startWith;

    /**
     *
     */
    private int _seriesStart;

    /**
     *
     */
    private int _seriesStop;

    /**
     *
     */
    private int _seriesStep;

    /**
     *
     */
    private boolean _selectEnabled;

//  /**
//   *
//   */
//  private boolean _selectRestricted;
//
    /**
     *
     */
    private OperationAccess _selectOperationAccess;

    /**
     *
     */
    private int _selectRowsLimit;

    /**
     *
     */
    SortOption _selectSortOption;

    /**
     *
     */
    private boolean _insertEnabled;

//  /**
//   *
//   */
//  private boolean _insertRestricted;
//
    /**
     *
     */
    private OperationAccess _insertOperationAccess;

    /**
     *
     */
    private OperationLogging _insertLogging;

    /**
     *
     */
    private boolean _updateEnabled;

//  /**
//   *
//   */
//  private boolean _updateRestricted;
//
    /**
     *
     */
    private OperationAccess _updateOperationAccess;

    /**
     *
     */
    private OperationLogging _updateLogging;

    /**
     *
     */
    private boolean _deleteEnabled;

//  /**
//   *
//   */
//  private boolean _deleteRestricted;
//
    /**
     *
     */
    private OperationAccess _deleteOperationAccess;

    /**
     *
     */
    private OperationLogging _deleteLogging;

    /**
     *
     */
    private boolean _reportEnabled;

    /**
     *
     */
    private int _reportRowsLimit;

    /**
     *
     */
    SortOption _reportSortOption;

    /**
     *
     */
    private boolean _exportEnabled;

    /**
     *
     */
    private int _exportRowsLimit;

    /**
     *
     */
    SortOption _exportSortOption;

    /**
     *
     */
    private Boolean _tableViewEnabled;

    /**
     *
     */
    private boolean _tableViewWithInsertEnabled;

    /**
     *
     */
    private boolean _tableViewWithUpdateEnabled;

    /**
     *
     */
    private boolean _tableViewWithDeleteEnabled;

    /**
     *
     */
    private boolean _tableViewWithMasterHeading;
//
//  /**
//   *
//   */
//  private int _tableViewRows;
//
//  /**
//   *
//   */
//  private int _tableViewWidth;

    /**
     *
     */
    private boolean _detailViewEnabled;

    /**
     *
     */
    private boolean _detailViewWithMasterHeading;
//
//  /**
//   *
//   */
//  private int _detailViewWidth;

    /**
     *
     */
    private boolean _treeViewEnabled;

    /**
     *
     */
    private boolean _consoleViewEnabled;
//
//  /**
//   *
//   */
//  private int _consoleViewWidth;

    /**
     *
     */
    private boolean _warningsEnabled;

    /**
     *
     */
    private boolean _sqlCodeGenEnabled;

    /**
     * annotated with EntityReferenceSearch
     */
    private SearchType _searchType;

    /**
     * annotated with EntityReferenceSearch
     */
    private ListStyle _listStyle;

    /**
     * annotated with EntityReferenceSearch
     */
    private DisplayMode _searchDisplayMode;

    /**
     * search query filter
     */
    private BooleanExpression _searchQueryFilter;

    /**
     * select filter
     */
    private BooleanExpression _selectFilter;

    /**
     * insert filter
     */
    private final Map<EntityReference, BooleanExpression> _insertFilterByReferenceMap = new LinkedHashMap<>();

    /**
     * update filter
     */
    private BooleanExpression _updateFilter;

    /**
     * delete filter
     */
    private BooleanExpression _deleteFilter;

    /**
     * master/detail filter
     */
    private final Map<EntityReference, BooleanExpression> _masterDetailFilterByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private boolean _filterInactiveIndicatorProperty;

    /**
     *
     */
    private boolean _filterOwnerProperty;

    /**
     *
     */
    private boolean _filterSegmentProperty;

    /**
     * manyToOne: annotated with ManyToOne
     */
    private Navigability _navigability;

    /**
     * manyToOne: annotated with ManyToOne
     */
    private MasterDetailView _masterDetailView;

    /**
     *
     */
    private Object _initialValue;

    /**
     *
     */
    private Object _defaultValue;

    /**
     *
     */
    private Object _currentValue;

    /**
     *
     */
    private Object _orderBy;

    /**
     *
     */
    private Tab _defaultTab;

    /**
     * annotated with AbstractClass
     */
    private boolean _annotatedWithAbstractClass;

    /**
     * annotated with AllocationOverride
     */
    private boolean _annotatedWithAllocationOverride;

    /**
     * annotated with AllocationOverrides
     */
    private boolean _annotatedWithAllocationOverrides;

    /**
     * annotated with EntityPrimaryKey
     */
    private boolean _annotatedWithEntityPrimaryKey;

    /**
     * annotated with EntityVersionProperty
     */
    private boolean _annotatedWithEntityVersionProperty;

    /**
     * annotated with EntityNumericKey
     */
    private boolean _annotatedWithEntityNumericKey;

    /**
     * annotated with EntityCharacterKey
     */
    private boolean _annotatedWithEntityCharacterKey;

    /**
     * annotated with EntityNameProperty
     */
    private boolean _annotatedWithEntityNameProperty;

    /**
     * annotated with EntityDescriptionProperty
     */
    private boolean _annotatedWithEntityDescriptionProperty;

    /**
     * annotated with EntityInactiveIndicator
     */
    private boolean _annotatedWithEntityInactiveIndicator;

    /**
     * annotated with EntityUrlProperty
     */
    private boolean _annotatedWithEntityUrlProperty;

    /**
     * annotated with EntityParentProperty
     */
    private boolean _annotatedWithEntityParentProperty;

    /**
     * annotated with EntityOwnerProperty
     */
    private boolean _annotatedWithEntityOwnerProperty;

    /**
     * annotated with EntitySegmentProperty
     */
    private boolean _annotatedWithEntitySegmentProperty;

    /**
     * annotated with EntityBusinessKey
     */
    private boolean _annotatedWithEntityBusinessKey;

    /**
     * annotated with EntityClass
     */
    private boolean _annotatedWithEntityClass;

    /**
     * annotated with EntityDataGen
     */
    private boolean _annotatedWithEntityDataGen;

    /**
     * annotated with EntitySelectOperation
     */
    private boolean _annotatedWithEntitySelectOperation;

    /**
     * annotated with EntityInsertOperation
     */
    private boolean _annotatedWithEntityInsertOperation;

    /**
     * annotated with EntityUpdateOperation
     */
    private boolean _annotatedWithEntityUpdateOperation;

    /**
     * annotated with EntityDeleteOperation
     */
    private boolean _annotatedWithEntityDeleteOperation;

    /**
     * annotated with EntityReportOperation
     */
    private boolean _annotatedWithEntityReportOperation;

    /**
     * annotated with EntityExportOperation
     */
    private boolean _annotatedWithEntityExportOperation;

    /**
     * annotated with EntityTableView
     */
    private boolean _annotatedWithEntityTableView;

    /**
     * annotated with EntityDetailView
     */
    private boolean _annotatedWithEntityDetailView;

    /**
     * annotated with EntityTreeView
     */
    private boolean _annotatedWithEntityTreeView;

    /**
     * annotated with EntityConsoleView
     */
    private boolean _annotatedWithEntityConsoleView;

    /**
     * annotated with EntityWarnings
     */
    private boolean _annotatedWithEntityWarnings;

    /**
     * annotated with EntityCodeGen
     */
    private boolean _annotatedWithEntityCodeGen;

    /**
     * annotated with EntityReferenceSearch
     */
    private boolean _annotatedWithEntityReferenceSearch;

    /**
     * annotated with Filter
     */
    private boolean _annotatedWithFilter;

    /**
     * annotated with Extension
     */
    private boolean _annotatedWithExtension;

    /**
     * annotated with OneToOne
     */
    private boolean _annotatedWithOneToOne;

    /**
     * annotated with ManyToOne
     */
    private boolean _annotatedWithManyToOne;

    /**
     * annotated with EntityReferenceDataGen
     */
    private boolean _annotatedWithEntityReferenceDataGen;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return the initialised indicator
     */
    @Override
    public boolean isInitialised() {
        return _initialised;
    }

    /**
     * @return the settled indicator
     */
    @Override
    public boolean isSettled() {
        return _settled;
    }

    /**
     * @return the finalised indicator
     */
    @Override
    public boolean isFinalised() {
        return _finalised;
    }

    /**
     * @return the root-instance indicator
     */
    @Override
    public boolean isRootInstance() {
        return _rootInstance;
    }

    /**
     * @return the explicitly declared indicator
     */
    @Override
    public boolean isExplicitlyDeclared() {
        return _explicitlyDeclared;
    }

    /**
     * @return the implicitly declared indicator
     */
    @Override
    public boolean isImplicitlyDeclared() {
        return _implicitlyDeclared;
    }

    /**
     * @return the reference index
     */
    @Override
    public int getReferenceIndex() {
        return _referenceIndex;
    }

    /**
     * @param referenceIndex the reference index to set
     */
    void setReferenceIndex(int referenceIndex) {
        _referenceIndex = referenceIndex;
    }

    /**
     * @param reference
     * @return the default label
     */
    @Override
    public String getDefaultLabel(EntityReference reference) {
        return _defaultLabelByReferenceMap.get(reference);
    }

    /**
     * @param reference the reference to label
     * @param defaultLabel the default label to set
     */
//  @Override
    public void setDefaultLabel(EntityReference reference, String defaultLabel) {
        _defaultLabelByReferenceMap.put(reference, defaultLabel);
    }

    /**
     * @param reference
     * @return the default short label
     */
    @Override
    public String getDefaultShortLabel(EntityReference reference) {
        return _defaultShortLabelByReferenceMap.get(reference);
    }

    /**
     * @param reference the reference to label
     * @param defaultShortLabel the default short label to set
     */
//  @Override
    public void setDefaultShortLabel(EntityReference reference, String defaultShortLabel) {
        _defaultShortLabelByReferenceMap.put(reference, defaultShortLabel);
    }

    /**
     * @param reference
     * @return the default collection label
     */
    @Override
    public String getDefaultCollectionLabel(EntityReference reference) {
        return _defaultCollectionLabelByReferenceMap.get(reference);
    }

    /**
     * @param reference the reference to label
     * @param defaultCollectionLabel the default collection label to set
     */
//  @Override
    public void setDefaultCollectionLabel(EntityReference reference, String defaultCollectionLabel) {
        _defaultCollectionLabelByReferenceMap.put(reference, defaultCollectionLabel);
    }

    /**
     * @param reference
     * @return the default collection short label
     */
    @Override
    public String getDefaultCollectionShortLabel(EntityReference reference) {
        return _defaultCollectionShortLabelByReferenceMap.get(reference);
    }

    /**
     * @param reference the reference to label
     * @param defaultCollectionShortLabel the default collection short label to set
     */
//  @Override
    public void setDefaultCollectionShortLabel(EntityReference reference, String defaultCollectionShortLabel) {
        _defaultCollectionShortLabelByReferenceMap.put(reference, defaultCollectionShortLabel);
    }

    @Override
    public List<Property> getPropertiesList() {
        return _atlas.getPropertiesList();
    }

    @Override
    public List<Property> getReferencesList() {
        return _atlas.getReferencesList();
    }

    @Override
    public List<Parameter> getParameterReferencesList() {
        return _atlas.getParameterReferencesList();
    }

    @Override
    public List<Key> getKeysList() {
        return _atlas.getKeysList();
    }

    @Override
    public List<Tab> getTabsList() {
        return _atlas.getTabsList();
    }

    @Override
    public List<View> getViewsList() {
        return _atlas.getViewsList();
    }

    @Override
    public List<Instance> getInstancesList() {
        return _atlas.getInstancesList();
    }

    @Override
    public List<NamedValue> getNamedValuesList() {
        return _atlas.getNamedValuesList();
    }

    @Override
    public List<Expression> getExpressionsList() {
        return _atlas.getExpressionsList();
    }

    @Override
    public List<Transition> getTransitionsList() {
        return _atlas.getTransitionsList();
    }

    @Override
    public List<Operation> getOperationsList() {
        return _atlas.getOperationsList();
    }

    @Override
    public List<Trigger> getTriggersList() {
        return _atlas.getTriggersList();
    }

    @Override
    public Map<String, Property> getPropertiesMap() {
        return _atlas.getPropertiesMap();
    }

    @Override
    public Map<String, Property> getReferencesMap() {
        return _atlas.getReferencesMap();
    }

    @Override
    public Map<String, Parameter> getParameterReferencesMap() {
        return _atlas.getParameterReferencesMap();
    }

    @Override
    public Map<String, Key> getKeysMap() {
        return _atlas.getKeysMap();
    }

    @Override
    public Map<String, Tab> getTabsMap() {
        return _atlas.getTabsMap();
    }

    @Override
    public Map<String, View> getViewsMap() {
        return _atlas.getViewsMap();
    }

    @Override
    public Map<String, Instance> getInstancesMap() {
        return _atlas.getInstancesMap();
    }

    @Override
    public Map<String, NamedValue> getNamedValuesMap() {
        return _atlas.getNamedValuesMap();
    }

    @Override
    public Map<String, Expression> getExpressionsMap() {
        return _atlas.getExpressionsMap();
    }

    @Override
    public Map<String, Transition> getTransitionsMap() {
        return _atlas.getTransitionsMap();
    }

    @Override
    public Map<String, Operation> getOperationsMap() {
        return _atlas.getOperationsMap();
    }

    @Override
    public Map<String, Trigger> getTriggersMap() {
        return _atlas.getTriggersMap();
    }

    @Override
    public Map<String, Class<?>> getOperationClassesMap() {
        return _atlas.getOperationClassesMap();
    }

    /**
     * @return true if the entity is an abstract entity class
     */
    @Override
    public boolean isAbstractClass() {
        return _annotatedWithAbstractClass;
    }

    /**
     * @return the baseClass
     */
    @Override
    public Class<?> getBaseClass() {
        return _baseClass;
    }

    /**
     * @return the direct known subclasses list
     */
    @Override
    public List<Class<?>> getSubclassesList() {
        return new ArrayList<>(getSubclassesMap().values());
    }

    /**
     * @return the direct known subclasses map
     */
    @Override
    public Map<String, Class<?>> getSubclassesMap() {
        return _subclasses;
    }

    /**
     * @return the allocation overrides list
     */
    @Override
    public List<AllocationOverride> getAllocationOverridesList() {
        return new ArrayList<>(getAllocationOverridesMap().values());
    }

    /**
     * @return the allocation overrides map
     */
    @Override
    public Map<String, AllocationOverride> getAllocationOverridesMap() {
        return _allocationOverrides;
    }

    /**
     * @return the primaryKeyFieldName
     */
    @Override
    public String getPrimaryKeyFieldName() {
        return _primaryKeyFieldName;
    }

    /**
     * @return the primaryKeyField
     */
    @Override
    public Field getPrimaryKeyField() {
        return _primaryKeyField;
    }

    /**
     * @return the primaryKeyProperty
     */
    @Override
    public Property getPrimaryKeyProperty() {
        return _primaryKeyProperty;
    }

    /**
     * @return the versionFieldName
     */
    @Override
    public String getVersionFieldName() {
        return _versionFieldName;
    }

    /**
     * @return the versionField
     */
    @Override
    public Field getVersionField() {
        return _versionField;
    }

    /**
     * @return the versionProperty
     */
    @Override
    public LongProperty getVersionProperty() {
        return _versionProperty;
    }

    /**
     * @return the numericKeyFieldName
     */
    @Override
    public String getNumericKeyFieldName() {
        return _numericKeyFieldName;
    }

    /**
     * @return the numericKeyField
     */
    @Override
    public Field getNumericKeyField() {
        return _numericKeyField;
    }

    /**
     * @return the numericKeyProperty
     */
    @Override
    public IntegerProperty getNumericKeyProperty() {
        return _numericKeyProperty;
    }

    /**
     * @return the characterKeyFieldName
     */
    @Override
    public String getCharacterKeyFieldName() {
        return _characterKeyFieldName;
    }

    /**
     * @return the characterKeyField
     */
    @Override
    public Field getCharacterKeyField() {
        return _characterKeyField;
    }

    /**
     * @return the characterKeyProperty
     */
    @Override
    public StringProperty getCharacterKeyProperty() {
        return _characterKeyProperty;
    }

    /**
     * @return the nameFieldName
     */
    @Override
    public String getNameFieldName() {
        return _nameFieldName;
    }

    /**
     * @return the nameField
     */
    @Override
    public Field getNameField() {
        return _nameField;
    }

    /**
     * @return the nameProperty
     */
    @Override
    public StringProperty getNameProperty() {
        return _nameProperty;
    }

    /**
     * @return the descriptionFieldName
     */
    @Override
    public String getDescriptionFieldName() {
        return _descriptionFieldName;
    }

    /**
     * @return the descriptionField
     */
    @Override
    public Field getDescriptionField() {
        return _descriptionField;
    }

    /**
     * @return the descriptionProperty
     */
    @Override
    public StringProperty getDescriptionProperty() {
        return _descriptionProperty;
    }

    /**
     * @return the inactiveIndicatorFieldName
     */
    @Override
    public String getInactiveIndicatorFieldName() {
        return _inactiveIndicatorFieldName;
    }

    /**
     * @return the inactiveIndicatorField
     */
    @Override
    public Field getInactiveIndicatorField() {
        return _inactiveIndicatorField;
    }

    /**
     * @return the inactive indicator property
     */
    @Override
    public BooleanProperty getInactiveIndicatorProperty() {
        return _inactiveIndicatorProperty;
    }

    /**
     * @return the urlFieldName
     */
    @Override
    public String getUrlFieldName() {
        return _urlFieldName;
    }

    /**
     * @return the urlField
     */
    @Override
    public Field getUrlField() {
        return _urlField;
    }

    /**
     * @return the url property
     */
    @Override
    public StringProperty getUrlProperty() {
        return _urlProperty;
    }

    /**
     * @return the parentFieldName
     */
    @Override
    public String getParentFieldName() {
        return _parentFieldName;
    }

    /**
     * @return the parentField
     */
    @Override
    public Field getParentField() {
        return _parentField;
    }

    /**
     * @return the parentProperty
     */
    @Override
    public Entity getParentProperty() {
        return _parentProperty;
    }

    /**
     * @return the ownerFieldName
     */
    @Override
    public String getOwnerFieldName() {
        return _ownerFieldName;
    }

    /**
     * @return the ownerField
     */
    @Override
    public Field getOwnerField() {
        return _ownerField;
    }

    /**
     * @return the ownerProperty
     */
    @Override
    public Entity getOwnerProperty() {
        return _ownerProperty;
    }

    /**
     * @return the segmentFieldName
     */
    @Override
    public String getSegmentFieldName() {
        return _segmentFieldName;
    }

    /**
     * @return the segmentField
     */
    @Override
    public Field getSegmentField() {
        return _segmentField;
    }

    /**
     * @return the segmentProperty
     */
    @Override
    public Entity getSegmentProperty() {
        return _segmentProperty;
    }

    /**
     * @return the businessKeyFieldName
     */
    @Override
    public String getBusinessKeyFieldName() {
        return _businessKeyFieldName;
    }

    /**
     * @return the businessKeyField
     */
    @Override
    public Field getBusinessKeyField() {
        return _businessKeyField;
    }

    /**
     * @return the businessKeyProperty
     */
    @Override
    public Property getBusinessKeyProperty() {
        return _businessKeyProperty;
    }

    /**
     * @return true if the inactive indicator is a foreign property
     */
    public boolean isForeignInactiveIndicatorProperty() {
        return _foreignInactiveIndicatorProperty;
    }

    /**
     * @return true if the owner is a foreign property
     */
    public boolean isForeignOwnerProperty() {
        return _foreignOwnerProperty;
    }

    /**
     * @return true if the segment is a foreign property
     */
    public boolean isForeignSegmentProperty() {
        return _foreignSegmentProperty;
    }

    /**
     * @return the businessKeyType
     */
    @Override
    public BusinessKeyType getBusinessKeyType() {
        return _businessKeyType;
    }

    /**
     * @return the existentially independent indicator
     */
    @Override
    public boolean isExistentiallyIndependent() {
        return _existentiallyIndependent;
    }

    /**
     * @return the resource type
     */
    @Override
    public ResourceType getResourceType() {
        return _resourceType;
    }

    /**
     * @return the resource gender
     */
    @Override
    public ResourceGender getResourceGender() {
        return _resourceGender;
    }

    /**
     * @return the properties prefix
     */
    @Override
    public String getPropertiesPrefix() {
        return _propertiesPrefix;
    }

    /**
     * @return the properties suffix
     */
    @Override
    public String getPropertiesSuffix() {
        return _propertiesSuffix;
    }

    /**
     * @return the help file name
     */
    @Override
    public String getHelpFileName() {
        return _helpFileName;
    }

    /**
     * sets the help file name.
     *
     * @param helpFileName
     */
    protected void setHelpFileName(String helpFileName) {
        _helpFileName = helpFileName;
    }

    /**
     * @return the start-with
     */
    @Override
    public int getStartWith() {
        return _startWith;
    }

    /**
     * @return the series start
     */
//  @Override
    public int getSeriesStart() {
        return _seriesStart;
    }

    /**
     * @return the series stop
     */
//  @Override
    public int getSeriesStop() {
        return _seriesStop;
    }

    /**
     * @return the series step
     */
//  @Override
    public int getSeriesStep() {
        return _seriesStep;
    }

    /**
     * @return the entity data generation enabled indicator
     */
    public boolean isDataGenEnabled() {
        return _annotatedWithEntityDataGen && _seriesStart <= _seriesStop && _seriesStep > 0;
    }

    /**
     * @return the select enabled indicator
     */
    @Override
    public boolean isSelectEnabled() {
        return _selectEnabled;
    }

//  /**
//   * @return the select restricted indicator
//   */
//  @Override
//  public boolean isSelectRestricted() {
//      return _selectRestricted;
//  }
//
    /**
     * @return the select operation access mode
     */
    @Override
    public OperationAccess isSelectOperationAccess() {
        return _selectOperationAccess;
    }

    /**
     * @return the select rows limit
     */
    @Override
    public int getSelectRowsLimit() {
        return _selectRowsLimit;
    }

    /**
     * @return the select sort option
     */
    @Override
    public SortOption getSelectSortOption() {
        return _selectSortOption;
    }

    /**
     * @return the insert enabled indicator
     */
    @Override
    public boolean isInsertEnabled() {
        return _insertEnabled && !_annotatedWithAbstractClass;
    }

//  /**
//   * @return the insert restricted indicator
//   */
//  @Override
//  public boolean isInsertRestricted() {
//      return _insertRestricted;
//  }
//
    /**
     * @return the insert operation access mode
     */
    @Override
    public OperationAccess isInsertOperationAccess() {
        return _insertOperationAccess;
    }

    /**
     * @return the insert logging mode
     */
    @Override
    public OperationLogging getInsertLogging() {
        return _insertLogging;
    }

    /**
     * @return the update enabled indicator
     */
    @Override
    public boolean isUpdateEnabled() {
        return _updateEnabled;
    }

//  /**
//   * @return the update restricted indicator
//   */
//  @Override
//  public boolean isUpdateRestricted() {
//      return _updateRestricted;
//  }
//
    /**
     * @return the update operation access mode
     */
    @Override
    public OperationAccess isUpdateOperationAccess() {
        return _updateOperationAccess;
    }

    /**
     * @return the update logging mode
     */
    @Override
    public OperationLogging getUpdateLogging() {
        return _updateLogging;
    }

    /**
     * @return the delete enabled indicator
     */
    @Override
    public boolean isDeleteEnabled() {
        return _deleteEnabled;
    }

//  /**
//   * @return the delete restricted indicator
//   */
//  @Override
//  public boolean isDeleteRestricted() {
//      return _deleteRestricted;
//  }
//
    /**
     * @return the delete operation access mode
     */
    @Override
    public OperationAccess isDeleteOperationAccess() {
        return _deleteOperationAccess;
    }

    /**
     * @return the delete logging mode
     */
    @Override
    public OperationLogging getDeleteLogging() {
        return _deleteLogging;
    }

    /**
     * @return the report enabled indicator
     */
    @Override
    public boolean isReportEnabled() {
        return _reportEnabled;
    }

    /**
     * @return the report rows limit
     */
    @Override
    public int getReportRowsLimit() {
        return _reportRowsLimit;
    }

    /**
     * @return the report sort option
     */
    @Override
    public SortOption getReportSortOption() {
        return _reportSortOption;
    }

    /**
     * @return the export enabled indicator
     */
    @Override
    public boolean isExportEnabled() {
        return _exportEnabled;
    }

    /**
     * @return the export rows limit
     */
    @Override
    public int getExportRowsLimit() {
        return _exportRowsLimit;
    }

    /**
     * @return the export sort option
     */
    @Override
    public SortOption getExportSortOption() {
        return _exportSortOption;
    }

    /**
     * @return the table-view enabled indicator
     */
    @Override
    public boolean isTableViewEnabled() {
        return _tableViewEnabled == null ? false : _tableViewEnabled;
    }

    /**
     * @return the table-view-with-insert-enabled indicator
     */
//  @Override
    public boolean isTableViewWithInsertEnabled() {
        return _tableViewWithInsertEnabled;
    }

    /**
     * @return the table-view-with-update-enabled indicator
     */
//  @Override
    public boolean isTableViewWithUpdateEnabled() {
        return _tableViewWithUpdateEnabled;
    }

    /**
     * @return the table-view-with-delete-enabled indicator
     */
//  @Override
    public boolean isTableViewWithDeleteEnabled() {
        return _tableViewWithDeleteEnabled;
    }

    /**
     * @return the table-view-with-master-heading indicator
     */
//  @Override
    public boolean isTableViewWithMasterHeading() {
        return _tableViewWithMasterHeading;
    }
//
//  /**
//   * @return the table view rows
//   */
////@Override
//  public int getTableViewRows() {
//      return _tableViewRows;
//  }
//
//  /**
//   * @return the table view width
//   */
////@Override
//  public int getTableViewWidth() {
//      return _tableViewWidth;
//  }

    /**
     * @return the detail-view enabled indicator
     */
    @Override
    public boolean isDetailViewEnabled() {
        return _detailViewEnabled;
    }

    /**
     * @return the detail-view-with-master-heading indicator
     */
//  @Override
    public boolean isDetailViewWithMasterHeading() {
        return _detailViewWithMasterHeading;
    }
//
//  /**
//   * @return the detail view width
//   */
////@Override
//  public int getDetailViewWidth() {
//      return _detailViewWidth;
//  }

    /**
     * @return the tree-view enabled indicator
     */
    @Override
    public boolean isTreeViewEnabled() {
        return _treeViewEnabled && _parentProperty != null;
    }

    /**
     * @return the console-view enabled indicator
     */
    @Override
    public boolean isConsoleViewEnabled() {
        return _consoleViewEnabled;
    }
//
//  /**
//   * @return the console view width
//   */
////@Override
//  public int getConsoleViewWidth() {
//      return _consoleViewWidth;
//  }

    /**
     * @return the warnings enabled indicator
     */
    @Override
    public boolean isWarningsEnabled() {
        return _warningsEnabled;
    }

    /**
     * @return the SQL code generation enabled indicator
     */
    @Override
    public boolean isSqlCodeGenEnabled() {
        return _sqlCodeGenEnabled;
    }

    /**
     * @return the search type
     */
    @Override
    public SearchType getSearchType() {
        return SearchType.UNSPECIFIED.equals(_searchType)
            ? this instanceof EnumerationEntity ? SearchType.LIST : SearchType.DISPLAY
            : _searchType;
    }

    /**
     * @return the list style
     */
    @Override
    public ListStyle getListStyle() {
        return _listStyle;
    }

    /**
     * @return the search display mode
     */
    @Override
    public DisplayMode getSearchDisplayMode() {
        return _searchDisplayMode;
    }

    /**
     * @return the search query filter
     */
    @Override
    public BooleanExpression getSearchQueryFilter() {
        if (_searchQueryFilter == null) {
            if (isInstanceReferenceField()) {
                BooleanExpression exp = defaultInstanceParameterSearchQueryFilter();
                if (exp != null) {
                    return exp;
                }
            }
            if (_filterInactiveIndicatorProperty || _filterOwnerProperty || _filterSegmentProperty) {
                return isNotNull();
            }
        }
        return _searchQueryFilter;
    }

    private BooleanExpression defaultInstanceParameterSearchQueryFilter() {
        Operation operation = getDeclaringOperation();
        List<State> list = operation.getInitialStatesList();
        int size = list.size();
        if (size == 0) {
            return null;
        }
        Expression exp;
        Map<String, Expression> map = getExpressionsMap();
        State[] array = new State[size];
        int i = 0;
        for (State state : list) {
            exp = map.get(state.getName());
            array[i++] = (State) exp;
        }
        switch (array.length) {
            case 1:
                return array[0];
            case 2:
                return or(array[0], array[1]);
            default:
                return or(array[0], array[1], (State[]) ArrayUtils.subarray(array, 2, array.length));
        }
    }

    /**
     * @param filter the search query filter to set
     */
    public void setSearchQueryFilter(BooleanExpression filter) {
        String message = "failed to set search query filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _searchQueryFilter = filter.isTrue();
        } else {
            _searchQueryFilter = filter;
        }
    }

    /**
     * @return the select filter
     */
    @Override
    public BooleanExpression getSelectFilter() {
        return _selectFilter;
    }

    /**
     * @param filter the select filter to set
     */
    public void setSelectFilter(BooleanExpression filter) {
        String message = "failed to set select filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _selectFilter = filter.isTrue();
        } else {
            _selectFilter = filter;
        }
    }

    /**
     * @param reference
     * @return the insert filter
     */
    @Override
    public BooleanExpression getInsertFilter(EntityReference reference) {
        return _insertFilterByReferenceMap.get(reference);
    }

    /**
     * @param filter the insert filter to set
     */
    public void setInsertFilter(BooleanExpression filter) {
        String message = "failed to set insert filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        Entity master = filter.getDeclaringEntity();
        if (master == null) {
            message += "; supplied expression declaring entity is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        EntityReference reference;
        if (master instanceof EntityReference) {
            reference = (EntityReference) master;
        } else {
            message += "; supplied expression declaring entity is not an entity reference";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        Entity detail = master.getDeclaringEntity();
        if (detail == null || !detail.equals(this)) {
            message += "; supplied expression declaring entity is not a valid entity reference";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        if (filter instanceof BooleanPrimitive) {
            _insertFilterByReferenceMap.put(reference, filter.isTrue());
        } else {
            _insertFilterByReferenceMap.put(reference, filter);
        }
    }

    /**
     * @return the insert filter by reference map
     */
    public Map<EntityReference, BooleanExpression> getInsertFilterByReferenceMap() {
        return _insertFilterByReferenceMap;
    }

    /**
     * @return the update filter
     */
    @Override
    public BooleanExpression getUpdateFilter() {
        return _updateFilter;
    }

    /**
     * @param filter the update filter to set
     */
    public void setUpdateFilter(BooleanExpression filter) {
        String message = "failed to set update filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _updateFilter = filter.isTrue();
        } else {
            _updateFilter = filter;
        }
    }

    /**
     * @return the delete filter
     */
    @Override
    public BooleanExpression getDeleteFilter() {
        return _deleteFilter;
    }

    /**
     * @param filter the delete filter to set
     */
    public void setDeleteFilter(BooleanExpression filter) {
        String message = "failed to set delete filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _deleteFilter = filter.isTrue();
        } else {
            _deleteFilter = filter;
        }
    }

    /**
     * @param reference
     * @return the master/detail filter
     */
    @Override
    public BooleanExpression getMasterDetailFilter(EntityReference reference) {
        return _masterDetailFilterByReferenceMap.get(reference);
    }

    /**
     * @param filter the master/detail filter to set
     */
    public void setMasterDetailFilter(BooleanExpression filter) {
        String message = "failed to set master/detail filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        Entity master = filter.getDeclaringEntity();
        if (master == null) {
            message += "; supplied expression declaring entity is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        EntityReference reference;
        if (master instanceof EntityReference) {
            reference = (EntityReference) master;
        } else {
            message += "; supplied expression declaring entity is not an entity reference";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        Entity detail = master.getDeclaringEntity();
        if (detail == null || !detail.equals(this)) {
            message += "; supplied expression declaring entity is not a valid entity reference";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return;
        }
        if (filter instanceof BooleanPrimitive) {
            _masterDetailFilterByReferenceMap.put(reference, filter.isTrue());
        } else {
            _masterDetailFilterByReferenceMap.put(reference, filter);
        }
    }

    /**
     * @return the master/detail filter by reference map
     */
    public Map<EntityReference, BooleanExpression> getMasterDetailFilterByReferenceMap() {
        return _masterDetailFilterByReferenceMap;
    }

    /**
     * @return the filter inactive indicator property
     */
    public boolean isFilterInactiveIndicatorProperty() {
        return _filterInactiveIndicatorProperty;
    }

    /**
     * @return the filter owner property
     */
    public boolean isFilterOwnerProperty() {
        return _filterOwnerProperty;
    }

    /**
     * @return the filter segment property
     */
    public boolean isFilterSegmentProperty() {
        return _filterSegmentProperty;
    }

    /**
     * @return true if the entity is an extension of the declaring entity
     */
//  @Override
    public boolean isExtension() {
        return _annotatedWithExtension;
    }

    /**
     * @return true if the entity defines a one-to-one relationship
     */
    @Override
    public boolean isOneToOne() {
        return _annotatedWithOneToOne;
    }

    /**
     * @return true if the entity defines a manty-to-one relationship
     */
    @Override
    public boolean isManyToOne() {
        return _annotatedWithManyToOne;
    }

    /**
     * @return the navigability
     */
    @Override
    public Navigability getNavigability() {
        return _navigability;
    }

    /**
     * @return the master/detail view
     */
    @Override
    public MasterDetailView getMasterDetailView() {
        MasterDetailView masterDetailView = _masterDetailView;
        if (MasterDetailView.UNSPECIFIED.equals(masterDetailView)) {
            masterDetailView = MasterDetailView.NONE;
            if (isManyToOne()) {
                ResourceType rt1 = getResourceType();
                if (rt1 != null && !rt1.equals(ResourceType.UNSPECIFIED)) {
                    Entity declaringEntity = getDeclaringEntity();
                    ResourceType rt2 = declaringEntity == null ? null : declaringEntity.getResourceType();
                    if (declaringEntity != null && rt1.equals(rt2)) {
                        if (declaringEntity.isExistentiallyIndependent()) {
                            masterDetailView = MasterDetailView.TABLE;
                        } else {
                            masterDetailView = MasterDetailView.TABLE_AND_DETAIL;
                        }
                    }
                }
            }
        }
        return masterDetailView;
    }

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return _initialValue;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(Entity initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(Instance initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(EntityExpression initialValue) {
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * @param initialValue the initial value to set
     */
    public void setInitialValue(SpecialEntityValue initialValue) {
        _initialValue = validInitialValue(initialValue) && validSpecialEntityValue(initialValue) ? initialValue : null;
    }

    /**
     * @return the default value
     */
    @Override
    public Object getDefaultValue() {
        return _defaultValue;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(Entity defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(Instance defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(EntityExpression defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @param defaultValue the default value to set
     */
    public void setDefaultValue(SpecialEntityValue defaultValue) {
        _defaultValue = validDefaultValue(defaultValue) && validSpecialEntityValue(defaultValue) ? defaultValue : null;
    }

    /**
     * @return the current value
     */
    @Override
    public Object getCurrentValue() {
        return _currentValue;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(Entity currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(Instance currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(EntityExpression currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * @param currentValue the current value to set
     */
    public void setCurrentValue(SpecialEntityValue currentValue) {
        _currentValue = validCurrentValue(currentValue) && validSpecialEntityValue(currentValue) ? currentValue : null;
    }

    private boolean validSpecialEntityValue(SpecialEntityValue value) {
        Class<? extends Entity> userEntityClass;
        if (value != null) {
            switch (value) {
                case CURRENT_USER:
                    userEntityClass = TLC.getProject().getUserEntityClass();
                    if (userEntityClass != null && userEntityClass.isAssignableFrom(getClass())) {
                        return true;
                    }
                    break;
                default:
                    return true;
            }
        }
        String message = value + " is not a valid value for " + getFullName();
        logger.error(message);
        TLC.getProject().getParser().increaseErrorCount();
        return false;
    }

    /**
     * @return the order by object
     */
    public Object getOrderBy() {
        return _orderBy;
    }

    /**
     * @return the order by property
     */
    public Property getOrderByProperty() {
        return _orderBy instanceof Property ? (Property) _orderBy : null;
    }

    /**
     * @return the order by properties
     */
    public Property[] getOrderByProperties() {
        return _orderBy instanceof Property[] ? (Property[]) _orderBy : null;
    }

    /**
     * @return the order by key
     */
    public Key getOrderByKey() {
        return _orderBy instanceof Key ? (Key) _orderBy : null;
    }

    /**
     * @param orderBy the order by property to set
     */
    public void setOrderBy(Property orderBy) {
        _orderBy = orderBy;
    }

    /**
     * @param orderBy the order by properties to set
     */
    public void setOrderBy(Property... orderBy) {
        _orderBy = orderBy;
    }

    /**
     * @param orderBy the order by key to set
     */
    public void setOrderBy(Key orderBy) {
        _orderBy = orderBy;
    }

    /**
     * @return the default tab
     */
    public Tab getDefaultTab() {
        if (_defaultTab == null) {
            List<Tab> tabs = getTabsList();
            if (tabs != null && !tabs.isEmpty()) {
                _defaultTab = tabs.get(0);
            }
        }
        return _defaultTab;
    }

    /**
     * @param tab the default tab to set
     */
    public void setDefaultTab(Tab tab) {
        _defaultTab = tab;
    }

    /**
     * @return the default tab sequence number
     */
    public int getDefaultTabSequenceNumber() {
        Tab tab = getDefaultTab();
        return tab == null ? 0 : tab.getSequenceNumber();
    }

    /**
     * @return true if the entity is master of at least one detail
     */
    @Override
    public boolean isWritingPageMaster() {
        EntityReference reference;
        MasterDetailView masterDetailView;
        Class<? extends Entity> declaring;
        Entity detail;
        List<Property> properties = getReferencesList();
        for (Property property : properties) {
            reference = (EntityReference) property;
            if (reference.isManyToOne()) {
                masterDetailView = reference.getMasterDetailView();
                switch (masterDetailView) {
                    case TABLE:
//                  case DETAIL:
                    case TABLE_AND_DETAIL:
                        declaring = reference.getDeclaringEntity().getClass();
                        if (declaring != null) {
                            detail = _project.getEntity(declaring);
                            if (detail != null) { // && detail.isSelectEnabled()
                                if (detail.isInsertEnabled() || detail.isUpdateEnabled() || detail.isDeleteEnabled()) {
                                    return true;
                                }
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    /**
     * @return the AbstractClass annotation indicator
     */
    public boolean isAnnotatedWithAbstractClass() {
        return _annotatedWithAbstractClass;
    }

    /**
     * @return the AllocationOverride annotation indicator
     */
    public boolean isAnnotatedWithAllocationOverride() {
        return _annotatedWithAllocationOverride;
    }

    /**
     * @return the AllocationOverrides annotation indicator
     */
    public boolean isAnnotatedWithAllocationOverrides() {
        return _annotatedWithAllocationOverrides;
    }

    /**
     * @return the EntityPrimaryKey annotation indicator
     */
    public boolean isAnnotatedWithEntityPrimaryKey() {
        return _annotatedWithEntityPrimaryKey;
    }

    /**
     * @return the EntityVersionProperty annotation indicator
     */
    public boolean isAnnotatedWithEntityVersionProperty() {
        return _annotatedWithEntityVersionProperty;
    }

    /**
     * @return the EntityNumericKey annotation indicator
     */
    public boolean isAnnotatedWithEntityNumericKey() {
        return _annotatedWithEntityNumericKey;
    }

    /**
     * @return the EntityCharacterKey annotation indicator
     */
    public boolean isAnnotatedWithEntityCharacterKey() {
        return _annotatedWithEntityCharacterKey;
    }

    /**
     * @return the EntityNameProperty annotation indicator
     */
    public boolean isAnnotatedWithEntityNameProperty() {
        return _annotatedWithEntityNameProperty;
    }

    /**
     * @return the EntityDescriptionProperty annotation indicator
     */
    public boolean isAnnotatedWithEntityDescriptionProperty() {
        return _annotatedWithEntityDescriptionProperty;
    }

    /**
     * @return the EntityInactiveIndicator annotation indicator
     */
    public boolean isAnnotatedWithEntityInactiveIndicator() {
        return _annotatedWithEntityInactiveIndicator;
    }

    /**
     * @return the EntityUrlProperty annotation indicator
     */
    public boolean isAnnotatedWithEntityUrlProperty() {
        return _annotatedWithEntityUrlProperty;
    }

    /**
     * @return the EntityParentProperty annotation indicator
     */
    public boolean isAnnotatedWithEntityParentProperty() {
        return _annotatedWithEntityParentProperty;
    }

    /**
     * @return the EntityOwnerProperty annotation indicator
     */
    public boolean isAnnotatedWithEntityOwnerProperty() {
        return _annotatedWithEntityOwnerProperty;
    }

    /**
     * @return the EntitySegmentProperty annotation indicator
     */
    public boolean isAnnotatedWithEntitySegmentProperty() {
        return _annotatedWithEntitySegmentProperty;
    }

    /**
     * @return the EntityBusinessKey annotation indicator
     */
    public boolean isAnnotatedWithEntityBusinessKey() {
        return _annotatedWithEntityBusinessKey;
    }

    /**
     * @return the EntityClass annotation indicator
     */
    public boolean isAnnotatedWithEntityClass() {
        return _annotatedWithEntityClass;
    }

    /**
     * @return the EntityDataGen annotation indicator
     */
    public boolean isAnnotatedWithEntityDataGen() {
        return _annotatedWithEntityDataGen;
    }

    /**
     * @return the EntitySelectOperation annotation indicator
     */
    public boolean isAnnotatedWithEntitySelectOperation() {
        return _annotatedWithEntitySelectOperation;
    }

    /**
     * @return the EntityInsertOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityInsertOperation() {
        return _annotatedWithEntityInsertOperation;
    }

    /**
     * @return the EntityUpdateOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityUpdateOperation() {
        return _annotatedWithEntityUpdateOperation;
    }

    /**
     * @return the EntityDeleteOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityDeleteOperation() {
        return _annotatedWithEntityDeleteOperation;
    }

    /**
     * @return the EntityReportOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityReportOperation() {
        return _annotatedWithEntityReportOperation;
    }

    /**
     * @return the EntityExportOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityExportOperation() {
        return _annotatedWithEntityExportOperation;
    }

    /**
     * @return the EntityTableView annotation indicator
     */
    public boolean isAnnotatedWithEntityTableView() {
        return _annotatedWithEntityTableView;
    }

    /**
     * @return the EntityDetailView annotation indicator
     */
    public boolean isAnnotatedWithEntityDetailView() {
        return _annotatedWithEntityDetailView;
    }

    /**
     * @return the EntityTreeView annotation indicator
     */
    public boolean isAnnotatedWithEntityTreeView() {
        return _annotatedWithEntityTreeView;
    }

    /**
     * @return the EntityConsoleView annotation indicator
     */
    public boolean isAnnotatedWithEntityConsoleView() {
        return _annotatedWithEntityConsoleView;
    }

    /**
     * @return the EntityWarnings annotation indicator
     */
    public boolean isAnnotatedWithEntityWarnings() {
        return _annotatedWithEntityWarnings;
    }

    /**
     * @return the EntityCodeGen annotation indicator
     */
    public boolean isAnnotatedWithEntityCodeGen() {
        return _annotatedWithEntityCodeGen;
    }

    /**
     * @return the EntityReferenceSearch annotation indicator
     */
    public boolean isAnnotatedWithEntityReferenceSearch() {
        return _annotatedWithEntityReferenceSearch;
    }

    /**
     * @return the Filter annotation indicator
     */
    public boolean isAnnotatedWithFilter() {
        return _annotatedWithFilter;
    }

    /**
     * @return the Extension annotation indicator
     */
    public boolean isAnnotatedWithExtension() {
        return _annotatedWithExtension;
    }

    /**
     * @return the OneToOne annotation indicator
     */
    public boolean isAnnotatedWithOneToOne() {
        return _annotatedWithOneToOne;
    }

    /**
     * @return the ManyToOne annotation indicator
     */
    public boolean isAnnotatedWithManyToOne() {
        return _annotatedWithManyToOne;
    }

    /**
     * @return the EntityReferenceDataGen annotation indicator
     */
    public boolean isAnnotatedWithEntityReferenceDataGen() {
        return _annotatedWithEntityReferenceDataGen;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CRUD operations">
    protected SelectOperation select;

    protected InsertOperation insert;

    protected UpdateOperation update;

    protected DeleteOperation delete;

    /**
     * @return the select operation
     */
    public SelectOperation getSelectOperation() {
        return select;
    }

    /**
     * @return the insert operation
     */
    public InsertOperation getInsertOperation() {
        return insert;
    }

    /**
     * @return the update operation
     */
    public UpdateOperation getUpdateOperation() {
        return update;
    }

    /**
     * @return the delete operation
     */
    public DeleteOperation getDeleteOperation() {
        return delete;
    }
    // </editor-fold>

    public AbstractEntity(Artifact declaringArtifact, Field declaringField) {
        super();
        init(declaringArtifact, declaringField);
    }

    private void init(Artifact declaringArtifact, Field declaringField) {
        if (declaringArtifact == null) {
            throw new IllegalArgumentException("null declaring artifact");
        }
        Class<?> namedClass = getNamedClass();
        String className = namedClass.getSimpleName();
        String fieldName = declaringField == null ? className : declaringField.getName();
        setDataClass(namedClass);
        setDataType(namedClass);
        if (declaringArtifact instanceof Project) {
            _project = (Project) declaringArtifact;
            _rootInstance = true;
            setDeclaredIndicators();
            /*
             * data type, project and the root indicator must be set before calling setDeclared
             * they are all used at initializeInheritanceFields, which is called by setDeclared
             */
            setDeclared(className);
            track("allocate");
        } else {
            Project currentProject = TLC.getProject();
            if (currentProject == null) {
                throw new UnexpectedRuntimeException("null current project");
            }
            _project = currentProject;
            _rootInstance = false;
            setDeclaredIndicators();
            setName(fieldName);
            setDeclaringArtifact(declaringArtifact);
            setDeclaringField(declaringField);
            track("allocate");
            initialise();
            settle();
        }
    }

    private void setDeclaredIndicators() {
        ProjectEntityReference reference = _project.getEntityReference(getDataType());
        _explicitlyDeclared = reference.isExplicit();
        _implicitlyDeclared = reference.isImplicit();
    }

    // <editor-fold defaultstate="collapsed" desc="check">
//  private void check(Artifact declaringArtifact) {
//      if (declaringArtifact == null) {
//          Class<?> namedClass = getNamedClass();
//          String methodName = namedClass.getName() + '.' + XS.GET_INSTANCE;
//          int lineNumber = XS.getLineNumber(methodName, true);
//          if (lineNumber == 0) {
//              methodName = namedClass.getName() + '.' + "<init>";
//              lineNumber = XS.getLineNumber(methodName, false);
//              String msg = "null declaring artifact";
//              if (lineNumber > 0) {
//                  msg += " at " + methodName + "(" + namedClass.getSimpleName() + ".java:" + lineNumber + ")";
//              }
//              throw new IllegalArgumentException(msg);
//          }
//      }
//      String message = "";
//      message += getAbstractSuperclass().getSimpleName() + " ";
//      message += getNamedClass().getSimpleName() + " " + hashCode();
//      if (declaringArtifact != null) {
//          message += " @ ";
//          message += XS.getNamedClass(declaringArtifact).getSimpleName() + " " + declaringArtifact.hashCode();
//      }
//      logger.trace(message);
//  }
    // </editor-fold>
//
    @Override
    public final void initialise() {
        XS1.checkAccess();
        if (_initialised) {
            return;
        }
        _initialised = true;
        Artifact declaringArtifact = getDeclaringArtifact();
        if (declaringArtifact == null) {
            TLC.removeAllocationSettings();
            _atlas.initialiseFields(Property.class);
            _atlas.initialiseFields(Key.class);
            _atlas.initialiseFields(Tab.class);
            _atlas.initialiseFields(View.class);
            _atlas.initialiseFields(Instance.class);
            _atlas.initialiseFields(NamedValue.class);
            _atlas.initialiseFields(Expression.class);
            _atlas.initialiseFields(Transition.class);
            _atlas.checkOperationClasses();
            _atlas.initialiseFields(Operation.class);
            _atlas.checkOperationFields();
            _atlas.initialiseFields(Trigger.class);
        } else {
            _atlas.initialiseFields(Property.class);
            _atlas.initialiseFields(Instance.class);
            _atlas.initialiseFields(NamedValue.class);
            _atlas.initialiseFields(Expression.class);
        }
    }

    @Override
    public final void settle() {
        XS1.checkAccess();
        if (_settled) {
            return;
        }
        _settled = true;
        Artifact declaringArtifact = getDeclaringArtifact();
        settleAttributes();
        if (declaringArtifact == null) {
            settleProperties();
            settleLinks();
            settleKeys();
            settleTabs();
            settleViews();
            settleInstances();
//          settleNamedValues();
            settleExpressions();
            verifyNames(Entity.class, Expression.class);
            settleFilters();
            settleTransitions();
            settleOperations();
            settleTriggers();
//          verifyNames(Entity.class);
        } else {
            if (declaringArtifact instanceof Operation) {
//              settleProperties();
//              settleInstances();
//              settleNamedValues();
                settleExpressions();
            }
            verifyLabels();
        }
    }

    private void verifyLabels() {
        String fieldName = StrUtils.getHumplessCase(getName());
        String shortName = StrUtils.removeWords(fieldName, EntityReference.class, "_");
        String className = StrUtils.getHumplessCase(getDataClass().getSimpleName());
        boolean unsettle = !className.equals(shortName);
        if (unsettle) {
            setDefaultLabel(null);
            setDefaultShortLabel(null);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="settle">
    protected void settleAttributes() {
        track("settleAttributes");
    }

    protected void settleProperties() {
        track("settleProperties");
    }

    protected void settleLinks() {
        track("settleLinks");
    }

    protected void settleKeys() {
        track("settleKeys");
    }

    protected void settleTabs() {
        track("settleTabs");
    }

    protected void settleViews() {
        track("settleViews");
    }

    protected void settleInstances() {
        track("settleInstances");
    }

    protected void settleExpressions() {
        track("settleExpressions");
    }

    protected void settleFilters() {
        track("settleFilters");
    }

    protected void settleTransitions() {
        track("settleTransitions");
    }

    protected void settleOperations() {
        track("settleOperations");
        Class<?> type;
        Object o;
        Operation operation;
        Class<?> clazz = getClass();
        Class<?> top = Entity.class;
        for (Field field : XS1.getFields(clazz, top)) {
            field.setAccessible(true);
            type = field.getType();
            if (Operation.class.isAssignableFrom(type) && isNotRestricted(field)) {
                String errmsg = "failed to get field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o instanceof Operation) {
                        operation = (Operation) o;
                        operation.settle();
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    TLC.getProject().getParser().increaseErrorCount();
                }
            }
        }
    }

    protected void settleTriggers() {
        track("settleTriggers");
    }
    // </editor-fold>

    public void linkForeignInactiveIndicatorProperty(BooleanProperty foreignInactiveIndicatorProperty) {
//      if (isParameter() || isParameterProperty()) {
//          return;
//      }
        String message = "failed to link foreign inactive indicator property of " + getFullName();
        if (foreignInactiveIndicatorProperty == null) {
            message += "; supplied foreign property is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else {
            _inactiveIndicatorProperty = foreignInactiveIndicatorProperty;
        }
    }

    public void linkForeignOwnerProperty(Entity foreignOwnerProperty) {
//      if (isParameter() || isParameterProperty()) {
//          return;
//      }
        String message = "failed to link foreign owner property of " + getFullName();
        if (foreignOwnerProperty == null) {
            message += "; supplied foreign property is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else {
            Class<?> foreignOwnerPropertyClass = foreignOwnerProperty.getClass();
            Class<? extends Entity> userEntityClass = TLC.getProject().getUserEntityClass();
            if (userEntityClass != null && userEntityClass.isAssignableFrom(foreignOwnerPropertyClass)) {
                Field field = foreignOwnerProperty.getDeclaringField();
                boolean aye = field.isAnnotationPresent(OwnerProperty.class);
                if (aye) {
                    _ownerProperty = foreignOwnerProperty;
                } else {
                    message += "; " + field.getDeclaringClass().getSimpleName() + "." + field.getName() + " is not an owner property";
                    logger.error(message);
                    TLC.getProject().getParser().increaseErrorCount();
                }
            } else {
                message += "; " + userEntityClass + " is not assignable from " + foreignOwnerPropertyClass;
                logger.error(message);
                TLC.getProject().getParser().increaseErrorCount();
            }
        }
    }

    public void linkForeignSegmentProperty(Entity foreignSegmentProperty) {
//      if (isParameter() || isParameterProperty()) {
//          return;
//      }
        String message = "failed to link foreign segment property of " + getFullName();
        if (foreignSegmentProperty == null) {
            message += "; supplied foreign property is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else {
            Field field = foreignSegmentProperty.getDeclaringField();
            boolean aye = field.isAnnotationPresent(SegmentProperty.class);
            if (aye) {
                _segmentProperty = foreignSegmentProperty;
            } else {
                message += "; " + field.getDeclaringClass().getSimpleName() + "." + field.getName() + " is not a segment property";
                logger.error(message);
                TLC.getProject().getParser().increaseErrorCount();
            }
        }
    }

    private boolean isNotRestricted(Field field) {
        int modifiers = field.getModifiers();
        return !(Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers));
    }

    @Override
    public void finalise() {
        XS1.checkAccess();
        track("finalise");
        if (_finalised) {
            return;
        }
        _finalised = true;
        _atlas.finalise();
        setKeyFields();
        setBusinessKey();
        setKeyProperties();
        setForeignKeyFields();
        setTableViewEnabledIndicator();
        setBasicDatabaseOperationsAttributes();
        check();
    }

    @Override
    public Object addAttribute(Property property, String name, Object value) {
        return property == null ? null : property.addAttribute(name, value);
    }

    void setKeyFields() {
        setPrimaryKeyField();
        setVersionField();
        setNumericKeyField();
        setCharacterKeyField();
        setNameField();
        setDescriptionField();
        setInactiveIndicatorField();
        setUrlField();
        setParentField();
        setOwnerField();
        setSegmentField();
        setBusinessKeyField();
        setUniqueKeyFields();
    }

    void setPrimaryKeyField() {
        Field field = getAnnotations().get(PrimaryKey.class);
        if (field != null) {
            Class<?> type = getDataType();
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            if (field.equals(getPrimaryKeyField(fieldName, type))) {
                _primaryKeyFieldName = fieldName;
                _primaryKeyField = field;
                if (IntegerProperty.class.isAssignableFrom(fieldType) && _numericKeyField == null) {
                    _numericKeyFieldName = fieldName;
                    _numericKeyField = field;
                }
            }
        }
    }

    void setVersionField() {
        Field field = getAnnotations().get(VersionProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getVersionField(fieldName, type))) {
                _versionFieldName = fieldName;
                _versionField = field;
            }
        }
    }

    void setNumericKeyField() {
        Field field = getAnnotations().get(NumericKey.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getNumericKeyField(fieldName, type))) {
                _numericKeyFieldName = fieldName;
                _numericKeyField = field;
            }
        }
    }

    void setCharacterKeyField() {
        Field field = getAnnotations().get(CharacterKey.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getCharacterKeyField(fieldName, type))) {
                _characterKeyFieldName = fieldName;
                _characterKeyField = field;
            }
        }
    }

    void setNameField() {
        Field field = getAnnotations().get(NameProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getNameField(fieldName, type))) {
                _nameFieldName = fieldName;
                _nameField = field;
            }
        }
    }

    void setDescriptionField() {
        Field field = getAnnotations().get(DescriptionProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getDescriptionField(fieldName, type))) {
                _descriptionFieldName = fieldName;
                _descriptionField = field;
            }
        }
    }

    void setInactiveIndicatorField() {
        Field field = getAnnotations().get(InactiveIndicator.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getInactiveIndicatorField(fieldName, type))) {
                _inactiveIndicatorFieldName = fieldName;
                _inactiveIndicatorField = field;
            }
        }
    }

    void setUrlField() {
        Field field = getAnnotations().get(UrlProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getUrlField(fieldName, type))) {
                _urlFieldName = fieldName;
                _urlField = field;
            }
        }
    }

    void setParentField() {
        Field field = getAnnotations().get(ParentProperty.class);
        if (field != null) {
            String fieldName = field.getName();
            if (field.equals(getParentField(fieldName, field.getDeclaringClass()))) {
                _parentFieldName = fieldName;
                _parentField = field;
            }
        }
    }

    void setOwnerField() {
        Field field = getAnnotations().get(OwnerProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getOwnerField(fieldName, type))) {
                _ownerFieldName = fieldName;
                _ownerField = field;
            }
        }
    }

    void setSegmentField() {
        Field field = getAnnotations().get(SegmentProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getSegmentField(fieldName, type))) {
                _segmentFieldName = fieldName;
                _segmentField = field;
            }
        }
    }

    void setBusinessKeyField() {
        Field field = getAnnotations().get(BusinessKey.class);
        if (field != null) {
            Class<?> type = getDataType();
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            if (field.equals(getBusinessKeyField(fieldName, type))) {
                _businessKeyFieldName = fieldName;
                _businessKeyField = field;
                if (IntegerProperty.class.isAssignableFrom(fieldType)) {
                    _numericKeyFieldName = fieldName;
                    _numericKeyField = field;
                }
                if (StringProperty.class.isAssignableFrom(fieldType)) {
                    _characterKeyFieldName = fieldName;
                    _characterKeyField = field;
                }
            }
        }
    }

    void setUniqueKeyFields() {
        Class<?> type;
        Object o;
        Class<?> clazz = getClass();
        Class<?> top = Entity.class;
        for (Field field : XS1.getFields(clazz, top)) {
            field.setAccessible(true);
            type = field.getType();
            if (Property.class.isAssignableFrom(type) && isNotRestricted(field)) {
                String errmsg = "failed to get field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o instanceof Property) {
                        setKeyField(field);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    TLC.getProject().getParser().increaseErrorCount();
                }
            }
        }
    }

    void setKeyField(Field field) {
        Class<?> type = getDataType();
        Class<?> fieldType = field.getType();
        String fieldName = field.getName();
        if (field.isAnnotationPresent(UniqueKey.class)) {
            if (field.equals(getUniqueKeyField(fieldName, type))) {
                if (IntegerProperty.class.isAssignableFrom(fieldType) && _numericKeyField == null) {
                    _numericKeyFieldName = fieldName;
                    _numericKeyField = field;
                }
                if (StringProperty.class.isAssignableFrom(fieldType) && _characterKeyField == null) {
                    _characterKeyFieldName = fieldName;
                    _characterKeyField = field;
                }
            }
        }
    }

    void setKeyProperties() {
        Object keyProperty;
        keyProperty = getKeyProperty(_primaryKeyField);
        if (keyProperty instanceof Property) {
            _primaryKeyProperty = (Property) keyProperty;
        }
        keyProperty = getKeyProperty(_versionField);
        if (keyProperty instanceof LongProperty) {
            _versionProperty = (LongProperty) keyProperty;
        }
        keyProperty = getKeyProperty(_numericKeyField);
        if (keyProperty instanceof IntegerProperty) {
            _numericKeyProperty = (IntegerProperty) keyProperty;
        }
        keyProperty = getKeyProperty(_characterKeyField);
        if (keyProperty instanceof StringProperty) {
            _characterKeyProperty = (StringProperty) keyProperty;
        }
        keyProperty = getKeyProperty(_nameField);
        if (keyProperty instanceof StringProperty) {
            _nameProperty = (StringProperty) keyProperty;
        }
        keyProperty = getKeyProperty(_descriptionField);
        if (keyProperty instanceof StringProperty) {
            _descriptionProperty = (StringProperty) keyProperty;
        }
        keyProperty = getKeyProperty(_inactiveIndicatorField);
        if (keyProperty instanceof BooleanProperty) {
            _inactiveIndicatorProperty = (BooleanProperty) keyProperty;
        }
        keyProperty = getKeyProperty(_urlField);
        if (keyProperty instanceof StringProperty) {
            _urlProperty = (StringProperty) keyProperty;
        }
        keyProperty = getKeyProperty(_parentField);
        if (keyProperty instanceof Entity) {
            _parentProperty = (Entity) keyProperty;
        }
        keyProperty = getKeyProperty(_ownerField);
        if (keyProperty instanceof Entity) {
            _ownerProperty = (Entity) keyProperty;
        }
        keyProperty = getKeyProperty(_segmentField);
        if (keyProperty instanceof Entity) {
            _segmentProperty = (Entity) keyProperty;
        }
        keyProperty = getKeyProperty(_businessKeyField);
        if (keyProperty instanceof Property) {
            _businessKeyProperty = (Property) keyProperty;
        }
    }

    Object getKeyProperty(Field field) {
        if (field != null) {
            try {
                return field.get(this);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Throwable cause = ThrowableUtils.getCause(ex);
                String message = ex.equals(cause) ? null : ex.getMessage();
                logger.error(message, cause);
                TLC.getProject().getParser().increaseErrorCount();
            }
        }
        return null;
    }

    private void setForeignKeyFields() {
        setForeignInactiveIndicatorProperty();
        setForeignOwnerProperty();
        setForeignSegmentProperty();
    }

    private void setForeignInactiveIndicatorProperty() {
        if (_inactiveIndicatorField == null && _inactiveIndicatorProperty != null) {
            String name = _inactiveIndicatorProperty.getName();
            String role = "inactive indicator property";
            String rootName = getRoot().getName();
            String message = "failed to link foreign " + role + " at entity " + rootName;
            Entity declaringEntity = _inactiveIndicatorProperty.getDeclaringEntity();
            if (declaringEntity == null) {
                message += "; declaring entity of \"" + name + "\" is null";
                logger.error(message);
                TLC.getProject().getParser().increaseErrorCount();
            } else {
                String declaringEntityName = declaringEntity.getRoot().getName();
                Field declaringField = _inactiveIndicatorProperty.getDeclaringField();
                if (declaringField == null) {
                    message += "; declaring field of \"" + name + "\" is null";
                    logger.error(message);
                    TLC.getProject().getParser().increaseErrorCount();
                } else {
                    String declaringFieldName = declaringField.getName();
                    BooleanProperty inactiveIndicatorProperty = declaringEntity.getInactiveIndicatorProperty();
                    if (_inactiveIndicatorProperty.equals(inactiveIndicatorProperty)) {
                        _inactiveIndicatorField = declaringField;
                        _inactiveIndicatorFieldName = declaringFieldName;
                        _foreignInactiveIndicatorProperty = true;
                    } else {
                        message += "; " + declaringFieldName + " is not the " + role + " of " + declaringEntityName;
                        logger.error(message);
                        TLC.getProject().getParser().increaseErrorCount();
                    }
                }
            }
        }
    }

    private void setForeignOwnerProperty() {
        if (_ownerField == null && _ownerProperty != null) {
            String name = _ownerProperty.getName();
            String role = "owner property";
            String rootName = getRoot().getName();
            String message = "failed to link foreign " + role + " at entity " + rootName;
            Entity declaringEntity = _ownerProperty.getDeclaringEntity();
            if (declaringEntity == null) {
                message += "; declaring entity of \"" + name + "\" is null";
                logger.error(message);
                TLC.getProject().getParser().increaseErrorCount();
            } else {
                String declaringEntityName = declaringEntity.getRoot().getName();
                Field declaringField = _ownerProperty.getDeclaringField();
                if (declaringField == null) {
                    message += "; declaring field of \"" + name + "\" is null";
                    logger.error(message);
                    TLC.getProject().getParser().increaseErrorCount();
                } else {
                    String declaringFieldName = declaringField.getName();
                    Entity ownerProperty = declaringEntity.getOwnerProperty();
                    if (_ownerProperty.equals(ownerProperty)) {
                        _ownerField = declaringField;
                        _ownerFieldName = declaringFieldName;
                        _foreignOwnerProperty = true;
                    } else {
                        message += "; " + declaringFieldName + " is not the " + role + " of " + declaringEntityName;
                        logger.error(message);
                        TLC.getProject().getParser().increaseErrorCount();
                    }
                }
            }
        }
    }

    private void setForeignSegmentProperty() {
        if (_segmentField == null && _segmentProperty != null) {
            String name = _segmentProperty.getName();
            String role = "segment property";
            String rootName = getRoot().getName();
            String message = "failed to link foreign " + role + " at entity " + rootName;
            Entity declaringEntity = _segmentProperty.getDeclaringEntity();
            if (declaringEntity == null) {
                message += "; declaring entity of \"" + name + "\" is null";
                logger.error(message);
                TLC.getProject().getParser().increaseErrorCount();
            } else {
                String declaringEntityName = declaringEntity.getRoot().getName();
                Field declaringField = _segmentProperty.getDeclaringField();
                if (declaringField == null) {
                    message += "; declaring field of \"" + name + "\" is null";
                    logger.error(message);
                    TLC.getProject().getParser().increaseErrorCount();
                } else {
                    String declaringFieldName = declaringField.getName();
                    Entity segmentProperty = declaringEntity.getSegmentProperty();
                    if (_segmentProperty.equals(segmentProperty)) {
                        _segmentField = declaringField;
                        _segmentFieldName = declaringFieldName;
                        _foreignSegmentProperty = true;
                    } else {
                        message += "; " + declaringFieldName + " is not the " + role + " of " + declaringEntityName;
                        logger.error(message);
                        TLC.getProject().getParser().increaseErrorCount();
                    }
                }
            }
        }
    }

    private void setTableViewEnabledIndicator() {
        if (_tableViewEnabled == null) {
            if (this instanceof EnumerationEntity) {
                _tableViewEnabled = false;
                if (_updateEnabled) {
                    List<Property> list = _atlas.getPropertiesList();
                    for (Property property : list) {
                        if (property.isTableField() && property.isUpdateField()) {
                            _tableViewEnabled = true;
                            break;
                        }
                    }
                }
            } else {
                _tableViewEnabled = true;
            }
        }
    }

    private void setBasicDatabaseOperationsAttributes() {
        Operation operation;
        /**/
        operation = (Operation) select;
        if (select != null) {
            operation.setOperationAccess(_selectOperationAccess);
        }
        /**/
        operation = (Operation) insert;
        if (insert != null) {
            operation.setOperationAccess(_insertOperationAccess);
            operation.setOperationLogging(_insertLogging);
        }
        /**/
        operation = (Operation) update;
        if (update != null) {
            operation.setOperationAccess(_updateOperationAccess);
            operation.setOperationLogging(_updateLogging);
        }
        /**/
        operation = (Operation) delete;
        if (delete != null) {
            operation.setOperationAccess(_deleteOperationAccess);
            operation.setOperationLogging(_deleteLogging);
        }
    }

    private void check() {
        checkExpressions();
        if (_rootInstance) {
            checkTriggers();
            if (_warningsEnabled) {
                checkVisibleFields();
                if (_businessKeyProperty == null) {
                    checkBusinessKeyProperty();
                }
            }
        }
    }

    private void checkExpressions() {
        Object o;
        Expression e;
        e = getSelectFilter();
        if (e != null) {
            verifyExpression(e);
        }
        e = getUpdateFilter();
        if (e != null) {
            verifyExpression(e);
        }
        e = getDeleteFilter();
        if (e != null) {
            verifyExpression(e);
        }
        for (Property property : _atlas.getPropertiesList()) {
            e = property.getRenderingFilter();
            if (e != null) {
                verifyExpression(e);
            }
            e = property.getRequiringFilter();
            if (e != null) {
                verifyExpression(e);
            }
            e = property.getModifyingFilter();
            if (e != null) {
                verifyExpression(e);
            }
            e = property.getNullifyingFilter();
            if (e != null) {
                verifyExpression(e);
            }
            if (property instanceof Entity) {
                Entity entity = (Entity) property;
                e = entity.getSearchQueryFilter();
                if (e != null) {
                    verifyExpression(e);
                }
            }
            o = property.getInitialValue();
            if (o instanceof Expression) {
                e = (Expression) o;
                verifyExpression(e);
            }
            o = property.getDefaultValue();
            if (o instanceof Expression) {
                e = (Expression) o;
                verifyExpression(e);
            }
            o = property.getCurrentValue();
            if (o instanceof Expression) {
                e = (Expression) o;
                verifyExpression(e);
            }
        }
        for (Tab tab : _atlas.getTabsList()) {
            e = tab.getRenderingFilter();
            if (e != null) {
                verifyExpression(e);
            }
        }
        for (Expression expression : _atlas.getExpressionsList()) {
            if (expression != null) {
                verifyExpression(expression);
            }
        }
    }

    private void checkTriggers() {
        String tag;
        String message;
        State state;
        ProcessOperation operation;
        List<State> initialStatesList;
        List<Trigger> triggers = getTriggersList();
        for (Trigger trigger : triggers) {
            tag = " trigger " + trigger.getName() + " at entity " + getName();
            state = trigger.getState();
            operation = trigger.getOperation();
            if (state == null) {
                message = "invalid" + tag;
                message += "; trigger state is null";
                logger.warn(message);
                TLC.getProject().getParser().increaseWarningCount();
            } else if (operation == null) {
                message = "invalid" + tag;
                message += "; trigger operation is null";
                logger.warn(message);
                TLC.getProject().getParser().increaseWarningCount();
            } else {
                initialStatesList = operation.getInitialStatesList();
                if (initialStatesList == null || !initialStatesList.contains(state)) {
                    message = "suspicious" + tag;
                    message += "; " + state.getName() + " is not an initial state of " + operation.getName();
                    logger.warn(message);
                    TLC.getProject().getParser().increaseWarningCount();
                }
            }
        }
    }

    private void checkVisibleFields() {
        String msg1;
        String name = getName();
        int visibleTableField = 0;
        int visibleReportField = 0;
        int visibleRequired = 0;
        List<Property> list = _atlas.getPropertiesList();
        for (Property property : list) {
            if (property.isHiddenField()) {
                continue;
            }
            if (property.isTableField()) {
                visibleTableField++;
            }
            if (property.isReportField()) {
                visibleReportField++;
            }
            if (property.isRequiredProperty()) {
                visibleRequired++;
            }
        }
        if (visibleTableField == 0) {
            msg1 = name + " does not have any visible table fields";
            logger.warn(msg1);
            TLC.getProject().getParser().increaseWarningCount();
        }
        if (visibleReportField == 0) {
            msg1 = name + " does not have any visible report fields";
            logger.warn(msg1);
            TLC.getProject().getParser().increaseWarningCount();
        }
        if (visibleRequired == 0) {
            msg1 = name + " does not have any visible required properties";
            logger.warn(msg1);
            TLC.getProject().getParser().increaseWarningCount();
        }
    }

    private void checkBusinessKeyProperty() {
        String msg1;
        String msg2;
        String name = getName();
        msg1 = name + " does not have a business key property";
        List<Property> references = _atlas.getReferencesList();
        int size = references.size();
        if (size > 0) {
            size = 0;
            Entity declaringEntity;
            List<Property> declaringEntityProperties;
            EntityReference entityReference;
            for (Property reference : references) {
                declaringEntity = reference.getDeclaringEntity();
                if (declaringEntity.isExistentiallyIndependent()) {
                    size++;
                    continue;
                }
                declaringEntityProperties = declaringEntity.getPropertiesList();
                for (Property property : declaringEntityProperties) {
                    if (property.equals(reference)) {
                        continue;
                    }
                    if (property instanceof EntityReference) {
                        entityReference = (EntityReference) property;
                        if (MasterDetailView.NONE.equals(entityReference.getMasterDetailView())) {
                            continue;
                        }
                        size++;
                        break;
                    }
                }
            }
            if (size > 0) {
                msg2 = msg1 + " and is referenced by " + size + " non-exclusively-existentially-dependent "
                    + (size == 1 ? "property" : "properties");
                logger.warn(msg2);
                TLC.getProject().getParser().increaseWarningCount();
            }
        }
        size = _atlas.getParameterReferencesList().size();
        if (size > 0) {
            msg2 = msg1 + " and is referenced by " + size + (size == 1 ? " parameter" : " parameters");
            logger.warn(msg2);
            TLC.getProject().getParser().increaseWarningCount();
        }
    }

    @Override
    void setDeclared(String name, Artifact declaringArtifact, Field declaringField, int declaringFieldIndex) {
        super.setDeclared(name, declaringArtifact, declaringField, declaringFieldIndex);
        initializeInheritanceFields();
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithAbstractClass = false;
        _annotatedWithAllocationOverride = false;
        _annotatedWithAllocationOverrides = false;
        _annotatedWithEntityPrimaryKey = false;
        _annotatedWithEntityVersionProperty = false;
        _annotatedWithEntityNumericKey = false;
        _annotatedWithEntityCharacterKey = false;
        _annotatedWithEntityNameProperty = false;
        _annotatedWithEntityDescriptionProperty = false;
        _annotatedWithEntityInactiveIndicator = false;
        _annotatedWithEntityUrlProperty = false;
        _annotatedWithEntityParentProperty = false;
        _annotatedWithEntityOwnerProperty = false;
        _annotatedWithEntitySegmentProperty = false;
        _annotatedWithEntityBusinessKey = false;
        _annotatedWithEntityClass = false;
        _annotatedWithEntityDataGen = false;
        _annotatedWithEntitySelectOperation = false;
        _annotatedWithEntityInsertOperation = false;
        _annotatedWithEntityUpdateOperation = false;
        _annotatedWithEntityDeleteOperation = false;
        _annotatedWithEntityReportOperation = false;
        _annotatedWithEntityExportOperation = false;
        _annotatedWithEntityTableView = false;
        _annotatedWithEntityDetailView = false;
        _annotatedWithEntityTreeView = false;
        _annotatedWithEntityConsoleView = false;
        _annotatedWithEntityWarnings = false;
        _annotatedWithEntityCodeGen = false;
        _annotatedWithEntityReferenceSearch = false;
        _annotatedWithFilter = false;
        _annotatedWithExtension = false;
        _annotatedWithOneToOne = false;
        _annotatedWithManyToOne = false;
        _annotatedWithEntityReferenceDataGen = false;
        _businessKeyType = BusinessKeyType.UNSPECIFIED;
        _existentiallyIndependent = true;
        _resourceType = ResourceType.UNSPECIFIED;
        _resourceGender = ResourceGender.UNSPECIFIED;
        _propertiesPrefix = "";
        _propertiesSuffix = "";
        _helpFileName = "";
        _startWith = 1;
        _seriesStart = 1;
        _seriesStop = 100;
        _seriesStep = 1;
        _selectEnabled = true;
//      _selectRestricted = true;
        _selectOperationAccess = OperationAccess.UNSPECIFIED;
        _selectRowsLimit = Constants.DEFAULT_SELECT_ROWS_LIMIT;
        _selectSortOption = SortOption.ASC;
        _insertEnabled = true;
//      _insertRestricted = true;
        _insertOperationAccess = OperationAccess.UNSPECIFIED;
        _insertLogging = OperationLogging.UNSPECIFIED;
        _updateEnabled = true;
//      _updateRestricted = true;
        _updateOperationAccess = OperationAccess.UNSPECIFIED;
        _updateLogging = OperationLogging.UNSPECIFIED;
        _deleteEnabled = true;
//      _deleteRestricted = true;
        _deleteOperationAccess = OperationAccess.UNSPECIFIED;
        _deleteLogging = OperationLogging.UNSPECIFIED;
        _reportEnabled = true;
        _reportRowsLimit = Constants.DEFAULT_REPORT_ROWS_LIMIT;
        _reportSortOption = SortOption.ASC;
        _exportEnabled = true;
        _exportRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;
        _exportSortOption = SortOption.ASC;
        _tableViewEnabled = null;
        _tableViewWithInsertEnabled = true;
        _tableViewWithUpdateEnabled = true;
        _tableViewWithDeleteEnabled = true;
        _tableViewWithMasterHeading = true;
//      _tableViewRows = 10;
//      _tableViewWidth = 1200;
        _detailViewEnabled = true;
        _detailViewWithMasterHeading = true;
//      _detailViewWidth = 1200;
        _treeViewEnabled = true;
        _consoleViewEnabled = true;
//      _consoleViewWidth = 1200;
        _warningsEnabled = true;
        _sqlCodeGenEnabled = true;
        _searchType = SearchType.UNSPECIFIED;
        _listStyle = ListStyle.UNSPECIFIED;
        _searchDisplayMode = DisplayMode.UNSPECIFIED;
        _filterInactiveIndicatorProperty = false;
        _filterOwnerProperty = false;
        _filterSegmentProperty = false;
        _navigability = Navigability.UNSPECIFIED;
        _masterDetailView = MasterDetailView.UNSPECIFIED;
        _primaryKeyFieldName = null;
        _primaryKeyField = null;
        _primaryKeyProperty = null;
        _versionFieldName = null;
        _versionField = null;
        _versionProperty = null;
        _numericKeyFieldName = null;
        _numericKeyField = null;
        _numericKeyProperty = null;
        _characterKeyFieldName = null;
        _characterKeyField = null;
        _characterKeyProperty = null;
        _nameFieldName = null;
        _nameField = null;
        _nameProperty = null;
        _descriptionFieldName = null;
        _descriptionField = null;
        _descriptionProperty = null;
        _inactiveIndicatorFieldName = null;
        _inactiveIndicatorField = null;
        _inactiveIndicatorProperty = null;
        _foreignInactiveIndicatorProperty = false;
        _urlFieldName = null;
        _urlField = null;
        _urlProperty = null;
        _parentFieldName = null;
        _parentField = null;
        _parentProperty = null;
        _ownerFieldName = null;
        _ownerField = null;
        _ownerProperty = null;
        _foreignOwnerProperty = false;
        _segmentFieldName = null;
        _segmentField = null;
        _segmentProperty = null;
        _foreignSegmentProperty = false;
        _businessKeyFieldName = null;
        _businessKeyField = null;
        _businessKeyProperty = null;
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateAbstractClass(type);
            annotateAllocationOverride(type);
            annotateAllocationOverrides(type);
            annotateEntityPrimaryKey(type);
            annotateEntityVersionProperty(type);
            annotateEntityNumericKey(type);
            annotateEntityCharacterKey(type);
            annotateEntityNameProperty(type);
            annotateEntityDescriptionProperty(type);
            annotateEntityInactiveIndicator(type);
            annotateEntityUrlProperty(type);
            annotateEntityParentProperty(type);
            annotateEntityOwnerProperty(type);
            annotateEntitySegmentProperty(type);
            annotateEntityBusinessKey(type);
            annotateEntityClass(type);
            annotateEntityDataGen(type);
            annotateEntitySelectOperation(type);
            annotateEntityInsertOperation(type);
            annotateEntityUpdateOperation(type);
            annotateEntityDeleteOperation(type);
            annotateEntityReportOperation(type);
            annotateEntityExportOperation(type);
            annotateEntityTableView(type);
            annotateEntityDetailView(type);
            annotateEntityTreeView(type);
            annotateEntityConsoleView(type);
            annotateEntityWarnings(type);
            annotateEntityCodeGen(type);
            annotateEntityReferenceSearch(type);
        }
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            if (isEntityReference()) {
                annotateEntityReferenceDataGen(field);
                annotateEntityReferenceSearch(field);
                annotateFilter(field);
                annotateExtension(field);
                if (!isExtension()) {
                    annotateOneToOne(field);
                    if (!isOneToOne()) {
                        annotateManyToOne(field);
                    }
                }
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(AbstractClass.class);
        valid.add(AllocationOverride.class);
        valid.add(AllocationOverrides.class);
        valid.add(EntityBusinessKey.class);
        valid.add(EntityCharacterKey.class);
        valid.add(EntityClass.class);
        valid.add(EntityConsoleView.class);
        valid.add(EntityWarnings.class);
        valid.add(EntityCodeGen.class);
        valid.add(EntityDeleteOperation.class);
        valid.add(EntityDescriptionProperty.class);
        valid.add(EntityDetailView.class);
        valid.add(EntityExportOperation.class);
        valid.add(EntityInactiveIndicator.class);
        valid.add(EntityInsertOperation.class);
        valid.add(EntityNameProperty.class);
        valid.add(EntityNumericKey.class);
        valid.add(EntityOwnerProperty.class);
        valid.add(EntityParentProperty.class);
        valid.add(EntityPrimaryKey.class);
        valid.add(EntityReferenceSearch.class);
        valid.add(EntityReportOperation.class);
        valid.add(EntitySegmentProperty.class);
        valid.add(EntitySelectOperation.class);
        valid.add(EntityTableView.class);
        valid.add(EntityTreeView.class);
        valid.add(EntityUpdateOperation.class);
        valid.add(EntityUrlProperty.class);
        valid.add(EntityVersionProperty.class);
        if (this instanceof EnumerationEntity) {
        } else {
            valid.add(EntityDataGen.class);
        }
        return valid;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(Allocation.class);
        if (isParameter()) {
            valid.add(EntityReferenceSearch.class);
            valid.add(Filter.class);
            valid.add(InstanceReference.class);
            valid.add(ParameterField.class);
        }
        if (isProperty()) {
            valid.add(BaseField.class);
            valid.add(CastingField.class);
            valid.add(ColumnField.class);
            valid.add(EntityReferenceDataGen.class);
            valid.add(EntityReferenceSearch.class);
            valid.add(Extension.class);
            valid.add(Filter.class);
            valid.add(LastUserProperty.class);
            valid.add(ManyToOne.class);
            valid.add(OneToOne.class);
            valid.add(OwnerProperty.class);
            valid.add(ParentProperty.class);
            valid.add(PropertyField.class);
            valid.add(SegmentProperty.class);
            valid.add(UniqueKey.class);
        }
        return valid;
    }

    private void annotateAbstractClass(Class<?> type) {
        // AbstractClass annotation cannot be "inherited"
        _annotatedWithAbstractClass = type.isAnnotationPresent(AbstractClass.class);
//      if (_annotatedWithAbstractClass) {
//          AbstractClass annotation = type.getAnnotation(AbstractClass.class);
//      }
    }

    private void annotateAllocationOverride(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, AllocationOverride.class);
        if (annotatedClass != null) {
            AllocationOverride annotation = annotatedClass.getAnnotation(AllocationOverride.class);
            if (annotation != null) {
                _annotatedWithAllocationOverride = putAllocationOverride(annotation, type);
            }
        }
    }

    private void annotateAllocationOverrides(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, AllocationOverrides.class);
        if (annotatedClass != null) {
            AllocationOverrides annotation = annotatedClass.getAnnotation(AllocationOverrides.class);
            if (annotation != null) {
                for (AllocationOverride value : annotation.value()) {
                    _annotatedWithAllocationOverrides |= putAllocationOverride(value, type);
                }
            }
        }
    }

    private boolean putAllocationOverride(AllocationOverride annotation, Class<?> type) {
        if (annotation != null) {
            String name = annotation.field();
            if (StringUtils.isNotBlank(name)) {
                Field field = XS1.getEntityField(name, type);
                if (field != null) {
                    _allocationOverrides.put(name, annotation);
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityPrimaryKey(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityPrimaryKey.class);
        if (annotatedClass != null) {
            EntityPrimaryKey annotation = annotatedClass.getAnnotation(EntityPrimaryKey.class);
            if (annotation != null) {
                _primaryKeyFieldName = annotation.value();
                if (StringUtils.isBlank(_primaryKeyFieldName)) {
                    _primaryKeyFieldName = null;
                } else {
                    _primaryKeyField = getPrimaryKeyField(_primaryKeyFieldName, type);
                    _annotatedWithEntityPrimaryKey = _primaryKeyField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityVersionProperty(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityVersionProperty.class);
        if (annotatedClass != null) {
            EntityVersionProperty annotation = annotatedClass.getAnnotation(EntityVersionProperty.class);
            if (annotation != null) {
                _versionFieldName = annotation.value();
                if (StringUtils.isBlank(_versionFieldName)) {
                    _versionFieldName = null;
                } else {
                    _versionField = getVersionField(_versionFieldName, type);
                    _annotatedWithEntityVersionProperty = _versionField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityNumericKey(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityNumericKey.class);
        if (annotatedClass != null) {
            EntityNumericKey annotation = annotatedClass.getAnnotation(EntityNumericKey.class);
            if (annotation != null) {
                _numericKeyFieldName = annotation.value();
                if (StringUtils.isBlank(_numericKeyFieldName)) {
                    _numericKeyFieldName = null;
                } else {
                    _numericKeyField = getNumericKeyField(_numericKeyFieldName, type);
                    _annotatedWithEntityNumericKey = _numericKeyField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityCharacterKey(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityCharacterKey.class);
        if (annotatedClass != null) {
            EntityCharacterKey annotation = annotatedClass.getAnnotation(EntityCharacterKey.class);
            if (annotation != null) {
                _characterKeyFieldName = annotation.value();
                if (StringUtils.isBlank(_characterKeyFieldName)) {
                    _characterKeyFieldName = null;
                } else {
                    _characterKeyField = getCharacterKeyField(_characterKeyFieldName, type);
                    _annotatedWithEntityCharacterKey = _characterKeyField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityNameProperty(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityNameProperty.class);
        if (annotatedClass != null) {
            EntityNameProperty annotation = annotatedClass.getAnnotation(EntityNameProperty.class);
            if (annotation != null) {
                _nameFieldName = annotation.value();
                if (StringUtils.isBlank(_nameFieldName)) {
                    _nameFieldName = null;
                } else {
                    _nameField = getNameField(_nameFieldName, type);
                    _annotatedWithEntityNameProperty = _nameField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityDescriptionProperty(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDescriptionProperty.class);
        if (annotatedClass != null) {
            EntityDescriptionProperty annotation = annotatedClass.getAnnotation(EntityDescriptionProperty.class);
            if (annotation != null) {
                _descriptionFieldName = annotation.value();
                if (StringUtils.isBlank(_descriptionFieldName)) {
                    _descriptionFieldName = null;
                } else {
                    _descriptionField = getDescriptionField(_descriptionFieldName, type);
                    _annotatedWithEntityDescriptionProperty = _descriptionField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityInactiveIndicator(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityInactiveIndicator.class);
        if (annotatedClass != null) {
            EntityInactiveIndicator annotation = annotatedClass.getAnnotation(EntityInactiveIndicator.class);
            if (annotation != null) {
                _inactiveIndicatorFieldName = annotation.value();
                if (StringUtils.isBlank(_inactiveIndicatorFieldName)) {
                    _inactiveIndicatorFieldName = null;
                } else {
                    _inactiveIndicatorField = getInactiveIndicatorField(_inactiveIndicatorFieldName, type);
                    _annotatedWithEntityInactiveIndicator = _inactiveIndicatorField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityUrlProperty(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityUrlProperty.class);
        if (annotatedClass != null) {
            EntityUrlProperty annotation = annotatedClass.getAnnotation(EntityUrlProperty.class);
            if (annotation != null) {
                _urlFieldName = annotation.value();
                if (StringUtils.isBlank(_urlFieldName)) {
                    _urlFieldName = null;
                } else {
                    _urlField = getUrlField(_urlFieldName, type);
                    _annotatedWithEntityUrlProperty = _urlField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityParentProperty(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityParentProperty.class);
        if (annotatedClass != null) {
            EntityParentProperty annotation = annotatedClass.getAnnotation(EntityParentProperty.class);
            if (annotation != null) {
                _parentFieldName = annotation.value();
                if (StringUtils.isBlank(_parentFieldName)) {
                    _parentFieldName = null;
                } else {
                    Field field = getParentField(_parentFieldName, Entity.class);
                    _parentField = field == null ? null : getParentField(_parentFieldName, field.getDeclaringClass());
                    _annotatedWithEntityParentProperty = _parentField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityOwnerProperty(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityOwnerProperty.class);
        if (annotatedClass != null) {
            EntityOwnerProperty annotation = annotatedClass.getAnnotation(EntityOwnerProperty.class);
            if (annotation != null) {
                _ownerFieldName = annotation.value();
                if (StringUtils.isBlank(_ownerFieldName)) {
                    _ownerFieldName = null;
                } else {
                    _ownerField = getOwnerField(_ownerFieldName, type);
                    _annotatedWithEntityOwnerProperty = _ownerField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntitySegmentProperty(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntitySegmentProperty.class);
        if (annotatedClass != null) {
            EntitySegmentProperty annotation = annotatedClass.getAnnotation(EntitySegmentProperty.class);
            if (annotation != null) {
                _segmentFieldName = annotation.value();
                if (StringUtils.isBlank(_segmentFieldName)) {
                    _segmentFieldName = null;
                } else {
                    _segmentField = getSegmentField(_segmentFieldName, type);
                    _annotatedWithEntitySegmentProperty = _segmentField != null;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateEntityBusinessKey(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityBusinessKey.class);
        if (annotatedClass != null) {
            EntityBusinessKey annotation = annotatedClass.getAnnotation(EntityBusinessKey.class);
            if (annotation != null) {
                _businessKeyType = annotation.value();
                _annotatedWithEntityBusinessKey = true;
            }
        }
        setBusinessKey();
    }

    private void setBusinessKey() {
        if (_businessKeyFieldName == null || _businessKeyField == null) {
            switch (_businessKeyType) {
                case CHARACTER_KEY_PROPERTY:
                    if (_characterKeyField != null) {
                        _businessKeyFieldName = _characterKeyFieldName;
                        _businessKeyField = _characterKeyField;
                    }
                    break;
                case NUMERIC_KEY_PROPERTY:
                    if (_numericKeyField != null) {
                        _businessKeyFieldName = _numericKeyFieldName;
                        _businessKeyField = _numericKeyField;
                    }
                    break;
                case UNSPECIFIED:
                    if (_characterKeyField != null) {
                        _businessKeyFieldName = _characterKeyFieldName;
                        _businessKeyField = _characterKeyField;
                    } else if (_numericKeyField != null) {
                        _businessKeyFieldName = _numericKeyFieldName;
                        _businessKeyField = _numericKeyField;
                    }
                    break;
            }
        }
    }

    private void annotateEntityClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityClass.class);
        if (annotatedClass != null) {
            EntityClass annotation = annotatedClass.getAnnotation(EntityClass.class);
            if (annotation != null) {
                _existentiallyIndependent = annotation.independent().toBoolean(_existentiallyIndependent);
                _resourceType = annotation.resourceType();
                _resourceGender = annotation.resourceGender();
//              _propertiesPrefix = annotation.propertiesPrefix();
//              _propertiesSuffix = annotation.propertiesSuffix();
                _helpFileName = annotation.helpFile();
                _startWith = Math.max(0, annotation.startWith());
                _annotatedWithEntityClass = true;
            }
        }
    }

    private void annotateEntityDataGen(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDataGen.class);
        if (annotatedClass != null) {
            EntityDataGen annotation = annotatedClass.getAnnotation(EntityDataGen.class);
            if (annotation != null) {
                _seriesStart = Math.min(10000, Math.max(0, annotation.start()));
                _seriesStop = Math.min(10000, Math.max(0, annotation.stop()));
                _seriesStep = Math.min(10000, Math.max(0, annotation.step()));
                _annotatedWithEntityDataGen = true;
            }
        }
    }

    private void annotateEntitySelectOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntitySelectOperation.class);
        if (annotatedClass != null) {
            EntitySelectOperation annotation = annotatedClass.getAnnotation(EntitySelectOperation.class);
            if (annotation != null) {
                _selectEnabled = annotation.enabled().toBoolean(_selectEnabled);
//              _selectRestricted = annotation.restricted().toBoolean(_selectRestricted);
                _selectOperationAccess = annotation.access();
                _selectRowsLimit = Math.min(10000, Math.max(0, annotation.rowsLimit()));
                _selectSortOption = annotation.sortOption();
                _annotatedWithEntitySelectOperation = true;
            }
        }
    }

    private void annotateEntityInsertOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityInsertOperation.class);
        if (annotatedClass != null) {
            EntityInsertOperation annotation = annotatedClass.getAnnotation(EntityInsertOperation.class);
            if (annotation != null) {
                _insertEnabled = annotation.enabled().toBoolean(_insertEnabled);
//              _insertRestricted = annotation.restricted().toBoolean(_insertRestricted);
                _insertOperationAccess = annotation.access();
                _insertLogging = annotation.logging();
                _annotatedWithEntityInsertOperation = true;
            }
        }
    }

    private void annotateEntityUpdateOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityUpdateOperation.class);
        if (annotatedClass != null) {
            EntityUpdateOperation annotation = annotatedClass.getAnnotation(EntityUpdateOperation.class);
            if (annotation != null) {
                _updateEnabled = annotation.enabled().toBoolean(_updateEnabled);
//              _updateRestricted = annotation.restricted().toBoolean(_updateRestricted);
                _updateOperationAccess = annotation.access();
                _updateLogging = annotation.logging();
                _annotatedWithEntityUpdateOperation = true;
            }
        }
    }

    private void annotateEntityDeleteOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDeleteOperation.class);
        if (annotatedClass != null) {
            EntityDeleteOperation annotation = annotatedClass.getAnnotation(EntityDeleteOperation.class);
            if (annotation != null) {
                _deleteEnabled = annotation.enabled().toBoolean(_deleteEnabled);
//              _deleteRestricted = annotation.restricted().toBoolean(_deleteRestricted);
                _deleteOperationAccess = annotation.access();
                _deleteLogging = annotation.logging();
                _annotatedWithEntityDeleteOperation = true;
            }
        }
    }

    private void annotateEntityReportOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityReportOperation.class);
        if (annotatedClass != null) {
            EntityReportOperation annotation = annotatedClass.getAnnotation(EntityReportOperation.class);
            if (annotation != null) {
                _reportEnabled = annotation.enabled().toBoolean(_reportEnabled);
                _reportRowsLimit = Math.min(1000000, Math.max(0, annotation.rowsLimit()));
                _reportSortOption = annotation.sortOption();
                _annotatedWithEntityReportOperation = true;
            }
        }
    }

    private void annotateEntityExportOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityExportOperation.class);
        if (annotatedClass != null) {
            EntityExportOperation annotation = annotatedClass.getAnnotation(EntityExportOperation.class);
            if (annotation != null) {
                _exportEnabled = annotation.enabled().toBoolean(_exportEnabled);
                _exportRowsLimit = Math.min(1000000, Math.max(0, annotation.rowsLimit()));
                _exportSortOption = annotation.sortOption();
                _annotatedWithEntityExportOperation = true;
            }
        }
    }

    private void annotateEntityTableView(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityTableView.class);
        if (annotatedClass != null) {
            EntityTableView annotation = annotatedClass.getAnnotation(EntityTableView.class);
            if (annotation != null) {
                _tableViewEnabled = annotation.enabled().toBoolean();
                _tableViewWithInsertEnabled = annotation.inserts().toBoolean(_tableViewWithInsertEnabled);
                _tableViewWithUpdateEnabled = annotation.updates().toBoolean(_tableViewWithUpdateEnabled);
                _tableViewWithDeleteEnabled = annotation.deletes().toBoolean(_tableViewWithDeleteEnabled);
                _tableViewWithMasterHeading = annotation.heading().toBoolean(_tableViewWithMasterHeading);
//              _tableViewRows = Math.min(50, Math.max(1, annotation.rows()));
//              _tableViewWidth = Math.min(2400, Math.max(800, annotation.width()));
                _annotatedWithEntityTableView = true;
            }
        }
    }

    private void annotateEntityDetailView(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDetailView.class);
        if (annotatedClass != null) {
            EntityDetailView annotation = annotatedClass.getAnnotation(EntityDetailView.class);
            if (annotation != null) {
                _detailViewEnabled = annotation.enabled().toBoolean(_detailViewEnabled);
                _annotatedWithEntityDetailView = true;
                _detailViewWithMasterHeading = annotation.heading().toBoolean(_detailViewWithMasterHeading);
//              _detailViewWidth = Math.min(2400, Math.max(800, annotation.width()));
            }
        }
    }

    private void annotateEntityTreeView(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityTreeView.class);
        if (annotatedClass != null) {
            EntityTreeView annotation = annotatedClass.getAnnotation(EntityTreeView.class);
            if (annotation != null) {
                _treeViewEnabled = annotation.enabled().toBoolean(_treeViewEnabled);
                _annotatedWithEntityTreeView = true;
            }
        }
    }

    private void annotateEntityConsoleView(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityConsoleView.class);
        if (annotatedClass != null) {
            EntityConsoleView annotation = annotatedClass.getAnnotation(EntityConsoleView.class);
            if (annotation != null) {
                _consoleViewEnabled = annotation.enabled().toBoolean(_consoleViewEnabled);
                _annotatedWithEntityConsoleView = true;
//              _consoleViewWidth = Math.min(2400, Math.max(800, annotation.width()));
            }
        }
    }

    private void annotateEntityWarnings(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityWarnings.class);
        if (annotatedClass != null) {
            EntityWarnings annotation = annotatedClass.getAnnotation(EntityWarnings.class);
            if (annotation != null) {
                _warningsEnabled = annotation.enabled().toBoolean(_warningsEnabled);
                _annotatedWithEntityWarnings = true;
            }
        }
    }

    private void annotateEntityCodeGen(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityCodeGen.class);
        if (annotatedClass != null) {
            EntityCodeGen annotation = annotatedClass.getAnnotation(EntityCodeGen.class);
            if (annotation != null) {
                _sqlCodeGenEnabled = annotation.sql().toBoolean(_sqlCodeGenEnabled);
                _annotatedWithEntityCodeGen = true;
            }
        }
    }

    private void annotateEntityReferenceSearch(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityReferenceSearch.class);
        if (annotatedClass != null) {
            EntityReferenceSearch annotation = annotatedClass.getAnnotation(EntityReferenceSearch.class);
            if (annotation != null) {
                _searchType = annotation.searchType();
                _listStyle = annotation.listStyle();
                _searchDisplayMode = annotation.displayMode();
                _annotatedWithEntityReferenceSearch = true;
            }
        }
    }

    private void annotateEntityReferenceSearch(Field field) {
        _annotatedWithEntityReferenceSearch = field.isAnnotationPresent(EntityReferenceSearch.class);
        if (_annotatedWithEntityReferenceSearch) {
            EntityReferenceSearch annotation = field.getAnnotation(EntityReferenceSearch.class);
            _searchType = annotation.searchType();
            _listStyle = annotation.listStyle();
            _searchDisplayMode = annotation.displayMode();
        }
    }

    private void annotateFilter(Field field) {
        _annotatedWithFilter = field.isAnnotationPresent(Filter.class);
        if (_annotatedWithFilter) {
            Filter annotation = field.getAnnotation(Filter.class);
            _filterInactiveIndicatorProperty = annotation.inactive().toBoolean(_filterInactiveIndicatorProperty);
            _filterOwnerProperty = annotation.owner().toBoolean(_filterOwnerProperty);
            _filterSegmentProperty = annotation.segment().toBoolean(_filterSegmentProperty);
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateExtension(Field field) {
        _annotatedWithExtension = field.isAnnotationPresent(Extension.class);
//      if (_annotatedWithExtension) {
//          Extension annotation = field.getAnnotation(Extension.class);
//      }
    }

    private void annotateOneToOne(Field field) {
        _annotatedWithOneToOne = field.isAnnotationPresent(OneToOne.class);
        if (_annotatedWithOneToOne) {
            OneToOne annotation = field.getAnnotation(OneToOne.class);
            _navigability = annotation.navigability();
        }
    }

    private void annotateManyToOne(Field field) {
        _annotatedWithManyToOne = field.isAnnotationPresent(ManyToOne.class);
        if (_annotatedWithManyToOne) {
            ManyToOne annotation = field.getAnnotation(ManyToOne.class);
            _navigability = annotation.navigability();
            _masterDetailView = annotation.view();
        }
    }

    private void annotateEntityReferenceDataGen(Field field) {
        _annotatedWithEntityReferenceDataGen = field.isAnnotationPresent(EntityReferenceDataGen.class);
        if (_annotatedWithEntityReferenceDataGen) {
            EntityReferenceDataGen annotation = field.getAnnotation(EntityReferenceDataGen.class);
            _dataGenType = annotation.type();
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
        }
    }
    // </editor-fold>

    void initializeInheritanceFields() {
        Class<?> type = getDataType();
        if (_rootInstance) {
            _baseClass = XS1.getConcreteSuperclass(getClass());
            if (_baseClass != null) {
                Entity baseEntity = _project.getEntity(_baseClass);
                if (baseEntity != null) {
                    baseEntity.getSubclassesMap().put(type.getName(), type);
                }
            }
        } else {
            Entity rootEntity = _project.getEntity(type);
            _baseClass = rootEntity.getBaseClass();
        }
    }

    @Override
    public Project getDeclaringProject() {
        return _project;
    }

    /**
     * @return the root entity instance of the same class
     */
    @Override
    public Entity getRoot() {
        return getClassMainInstance();
    }

    /**
     * @return the root entity instance of the base class
     */
    @Override
    public Entity getBaseRoot() {
        return getBaseClassMainInstance();
    }

    /**
     * @return the root entity instance of the same class
     */
    @Override
    public Entity getHierarchyRoot() {
        return _baseClass == null ? getRoot() : getBaseClassMainInstance().getHierarchyRoot();
    }

    /**
     * @return the class hierarchy node type; null if the entity is not part of a hierarchy
     */
    @Override
    public HierarchyNodeType getHierarchyNodeType() {
        return _baseClass == null
            ? _subclasses.isEmpty() ? null : HierarchyNodeType.ROOT
            : _subclasses.isEmpty() ? HierarchyNodeType.LEAF : HierarchyNodeType.BRANCH;
    }

    /**
     * @return the direct known extensions list
     */
    @Override
    public List<Entity> getExtensionsList() {
        return new ArrayList<>(getExtensionsMap().values());
    }

    /**
     * @return the direct known extensions map
     */
    @Override
    public Map<String, Entity> getExtensionsMap() {
        Map<String, Entity> entities = new LinkedHashMap<>();
        Entity ext;
//      PersistentEntity pent;
//      InheritanceMappingStrategy ims;
        List<Class<?>> subclasses = getMainInstanceSubclassesList();
        if (subclasses != null) {
            for (Class<?> subclass : subclasses) {
                ext = _project.getEntity(subclass);
                entities.put(subclass.getName(), ext);
//              pent = ext instanceof PersistentEntity ? (PersistentEntity) ext : null;
//              ims = pent == null ? null : pent.getInheritanceMappingStrategy();
//              if (InheritanceMappingStrategy.SINGLE_TABLE.equals(ims)) {
                entities.putAll(ext.getExtensionsMap());
//              }
            }
        }
        return entities;
    }

    public List<State> getStatesList() {
        List<State> list = new ArrayList<>();
        list.addAll(getStatesMap().values());
        return list;
    }

    public Map<String, State> getStatesMap() {
        Map<String, State> map = new LinkedHashMap<>();
        Map<String, Expression> expressions = _atlas.getExpressionsMap();
        Expression value;
        Class<?> fieldType;
        for (String key : expressions.keySet()) {
            value = expressions.get(key);
            if (value instanceof State) {
                fieldType = value.getDeclaringField().getType();
                if (State.class.isAssignableFrom(fieldType)) {
                    map.put(value.getName(), (State) value);
                }
            }
        }
        return map;
    }

    private Entity getClassMainInstance() {
        if (_rootInstance) {
            return this;
        }
        return _project.getEntity(getDataType());
    }

    private Entity getBaseClassMainInstance() {
        return _baseClass == null ? null : _project.getEntity(_baseClass);
    }

    private List<Class<?>> getMainInstanceSubclassesList() {
        Entity main = getClassMainInstance();
        return main == null ? getSubclassesList() : main.getSubclassesList();
    }

    boolean isEntityReference() {
        Field field = getDeclaringField();
        if (field != null) {
            Class<?> type = field.getType();
            if (type != null) {
                return EntityReference.class.isAssignableFrom(type);
            }
        }
        return false;
    }

    /**
     * @return the operation keys
     */
    public Set<String> getOperationKeys() {
        Set<String> set = new TreeSet<>();
        set.addAll(getDefaultCrudOperationKeys());
        set.addAll(getUserDefinedOperationKeys());
        return set;
    }

    /**
     * @return the CRUD operation keys
     */
    public Set<String> getDefaultCrudOperationKeys() {
        Set<String> set = new TreeSet<>();
        String[] operations = Operation.getCrudOperationKeys();
        set.addAll(Arrays.asList(operations));
        return set;
    }

    /**
     * @return the user-defined operation keys
     */
    public Set<String> getUserDefinedOperationKeys() {
        Set<String> set = new TreeSet<>();
        for (Operation operation : getOperationsList()) {
            set.add(operation.getName());
        }
        return set;
    }

    /**
     * @return the CRUD operation list
     */
    @Override
    public List<Operation> getCrudOperationsList() {
        ArrayList<Operation> list = new ArrayList<>();
        if (select != null) {
            list.add(select);
        }
        if (insert != null) {
            list.add(insert);
        }
        if (update != null) {
            list.add(update);
        }
        if (delete != null) {
            list.add(delete);
        }
        return list;
    }

    /**
     * @return the user-defined business operation list
     */
    @Override
    public List<Operation> getBusinessOperationsList() {
        ArrayList<Operation> list = new ArrayList<>();
        OperationType operationType;
        for (Operation operation : getOperationsList()) {
            operationType = operation.getOperationType();
            switch (operationType) {
                case EXPORT:
                case REPORT:
                case PROCEDURE:
                case PROCESS:
                    list.add(operation);
                    break;
            }
        }
        return list;
    }

    /**
     * @return the parameter keys
     */
    public Set<String> getParameterKeys() {
        Set<String> set = new TreeSet<>();
        for (Property property : getPropertiesList()) {
            set.add(property.getName());
        }
        for (Operation operation : getOperationsList()) {
            for (Parameter parameter : operation.getParametersList()) {
                set.add(parameter.getName());
            }
        }
        return set;
    }

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends EntityWrapper> getDefaultWrapperClass() {
        return EntityWrapper.class;
    }

    // <editor-fold defaultstate="collapsed" desc="EntityExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Entity.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Entity.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(Entity y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(Instance y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(Entity y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(Instance y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(Entity y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(Instance y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Entity y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Instance y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(Entity y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(Instance y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(EntityExpression y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(Entity o) {
        Entity that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            string += _atlas.fieldsToString(n, key, verbose, fields, maps);
            if (verbose) {
//              string += fee + tab + "rootInstance" + faa + _rootInstance + foo;
                string += fee + tab + "explicitlyDeclared" + faa + _explicitlyDeclared + foo;
                string += fee + tab + "implicitlyDeclared" + faa + _implicitlyDeclared + foo;
                if (isEntityReference()) {
                    string += fee + tab + "extension" + faa + isExtension() + foo;
                    string += fee + tab + "oneToOne" + faa + isOneToOne() + foo;
                    string += fee + tab + "manyToOne" + faa + isManyToOne() + foo;
                    string += fee + tab + "navigability" + faa + _navigability + foo;
                    string += fee + tab + "masterDetailView" + faa + _masterDetailView + foo;
                } else {
                    string += fee + tab + "abstract" + faa + _annotatedWithAbstractClass + foo;
                    string += fee + tab + "baseClass" + faa + _baseClass + foo;
                    string += fee + tab + "subclassesList" + faa + getMainInstanceSubclassesList() + foo;
                    string += fee + tab + "primaryKeyProperty" + faa + _primaryKeyProperty + foo;
                    string += fee + tab + "versionProperty" + faa + _versionProperty + foo;
                    string += fee + tab + "numericKeyProperty" + faa + _numericKeyProperty + foo;
                    string += fee + tab + "characterKeyProperty" + faa + _characterKeyProperty + foo;
                    string += fee + tab + "nameProperty" + faa + _nameProperty + foo;
                    string += fee + tab + "descriptionProperty" + faa + _descriptionProperty + foo;
                    string += fee + tab + "inactiveIndicatorProperty" + faa + _inactiveIndicatorProperty + foo;
                    string += fee + tab + "urlProperty" + faa + _urlProperty + foo;
                    string += fee + tab + "parentProperty" + faa + _parentProperty + foo;
                    string += fee + tab + "ownerProperty" + faa + _ownerProperty + foo;
                    string += fee + tab + "segmentProperty" + faa + _segmentProperty + foo;
                    string += fee + tab + "businessKeyProperty" + faa + _businessKeyProperty + foo;
                    string += fee + tab + "businessKeyType" + faa + _businessKeyType + foo;
                    string += fee + tab + "existentiallyIndependent" + faa + _existentiallyIndependent + foo;
                    string += fee + tab + "resourceType" + faa + _resourceType + foo;
                    string += fee + tab + "resourceGender" + faa + _resourceGender + foo;
//                  string += fee + tab + "propertiesPrefix" + faa + _propertiesPrefix + foo;
//                  string += fee + tab + "propertiesSuffix" + faa + _propertiesSuffix + foo;
                    string += fee + tab + "selectEnabled" + faa + _selectEnabled + foo;
//                  string += fee + tab + "selectRestricted" + faa + _selectRestricted + foo;
                    string += fee + tab + "selectRowsLimit" + faa + _selectRowsLimit + foo;
                    string += fee + tab + "selectSortOption" + faa + _selectSortOption + foo;
                    string += fee + tab + "insertEnabled" + faa + _insertEnabled + foo;
//                  string += fee + tab + "insertRestricted" + faa + _insertRestricted + foo;
                    string += fee + tab + "updateEnabled" + faa + _updateEnabled + foo;
//                  string += fee + tab + "updateRestricted" + faa + _updateRestricted + foo;
                    string += fee + tab + "deleteEnabled" + faa + _deleteEnabled + foo;
//                  string += fee + tab + "deleteRestricted" + faa + _deleteRestricted + foo;
                    string += fee + tab + "reportEnabled" + faa + _reportEnabled + foo;
                    string += fee + tab + "reportRowsLimit" + faa + _reportRowsLimit + foo;
                    string += fee + tab + "reportSortOption" + faa + _reportSortOption + foo;
                    string += fee + tab + "exportEnabled" + faa + _exportEnabled + foo;
                    string += fee + tab + "exportRowsLimit" + faa + _exportRowsLimit + foo;
                    string += fee + tab + "exportSortOption" + faa + _exportSortOption + foo;
                    string += fee + tab + "tableViewEnabled" + faa + _tableViewEnabled + foo;
                    string += fee + tab + "detailViewEnabled" + faa + _detailViewEnabled + foo;
                    string += fee + tab + "treeViewEnabled" + faa + _treeViewEnabled + foo;
                    string += fee + tab + "consoleViewEnabled" + faa + _consoleViewEnabled + foo;
                    string += fee + tab + "warningsEnabled" + faa + _warningsEnabled + foo;
                }
                string += fee + tab + "searchType" + faa + _searchType + foo;
                string += fee + tab + "listStyle" + faa + _listStyle + foo;
                string += fee + tab + "searchDisplayMode" + faa + _searchDisplayMode + foo;
            }
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        string += _atlas.mapsToString(n, key, verbose, fields, maps, depth() == 0);
        return string;
    }

    @Override
    protected String getValueString(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Instance) {
            Instance instance = (Instance) value;
            return getValueString(instance.getDeclaringArtifact(), instance.getName());
        } else {
            return super.getValueString(value);
        }
    }
    // </editor-fold>

    private Field getPrimaryKeyField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            LongProperty.class,
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.PRIMARY_KEY, name, type, validTypes);
    }

    private Field getVersionField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            LongProperty.class,
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.VERSION, name, type, validTypes);
    }

    private Field getNumericKeyField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.NUMERIC_KEY, name, type, validTypes);
    }

    private Field getCharacterKeyField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.CHARACTER_KEY, name, type, validTypes);
    }

    private Field getNameField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.NAME, name, type, validTypes);
    }

    private Field getDescriptionField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.DESCRIPTION, name, type, validTypes);
    }

    private Field getInactiveIndicatorField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            BooleanProperty.class,
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.INACTIVE_INDICATOR, name, type, validTypes);
    }

    private Field getUrlField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.URL, name, type, validTypes);
    }

    private Field getParentField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            type
        };
        return getKeyPropertyField(KeyProperty.PARENT, name, type, validTypes);
    }

    private Field getOwnerField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.OWNER, name, type, validTypes);
    }

    private Field getSegmentField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.SEGMENT, name, type, validTypes);
    }

    private Field getUniqueKeyField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            BooleanPrimitive.class,
            CharacterPrimitive.class,
            NumericPrimitive.class,
            TemporalPrimitive.class,
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.UNIQUE_KEY, name, type, validTypes);
    }

    private Field getBusinessKeyField(String name, Class<?> type) {
        Class<?>[] validTypes = new Class<?>[]{
            IntegerProperty.class,
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.BUSINESS_KEY, name, type, validTypes);
    }

    Field getKeyPropertyField(KeyProperty keyProperty, String name, Class<?> type, Class<?>... validTypes) {
        String role = keyProperty.name().replace('_', ' ');
        return XS1.getField(depth() == 0, role, name, type, Entity.class, validTypes);
    }
//
//  private Field getKeyPropertyReferenceField(String name) {
//      if (StringUtils.isNotBlank(name)) {
//          Class<?>[] validTypes = new Class<?>[]{EntityReference.class};
//          return XS1.getField(depth() == 0, name, name, getClass(), Entity.class, validTypes);
//      }
//      return null;
//  }

    private void track(String method) {
        track(method, this);
    }

    private void track(String method, Object... parameters) {
        TLC.getProject().getParser().track(depth(), round(), getClassPath(), method, parameters);
    }

}
