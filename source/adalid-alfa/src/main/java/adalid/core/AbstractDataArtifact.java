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

import adalid.commons.bundles.*;
import adalid.commons.util.*;
import adalid.core.annotations.*;
import adalid.core.comparators.*;
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.primitives.*;
import adalid.core.properties.*;
import adalid.core.wrappers.*;
import java.io.ObjectStreamClass;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import static adalid.core.WHR.*;

/**
 * @author Jorge Campins
 */
public abstract class AbstractDataArtifact extends AbstractArtifact implements AnnotatableArtifact, DataArtifact, Parameter, CalculableProperty {

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(DataArtifact.class);

    private static final String EOL = "\n";

    private static final int L = LARGE_SIZE, M = MEDIUM_SIZE, S = SMALL_SIZE;

    public static final String CONVERTER_REGEX = "^[a-zA-Z]\\w*$";

    public static final String VALIDATOR_REGEX = "^[a-zA-Z]\\w*$";

    /**
     * dataClass
     */
    private Class<?> _dataClass;

    /**
     * dataType
     */
    private Class<?> _dataType;

    /**
     *
     */
    private Class<?> _segmentEntityClass; // since 20210218

    /**
     *
     */
    private PropertyAccess _propertyAccess = PropertyAccess.UNSPECIFIED;

    /**
     *
     */
    private boolean _auditable = true;

    /**
     *
     */
    private boolean _password;

    /**
     *
     */
    private Boolean _required;

    /**
     *
     */
    private boolean _hiddenField;

    /**
     *
     */
    private Boolean _createField;

    /**
     *
     */
    private Boolean _updateField;

    /**
     *
     */
    private Boolean _searchField;

    /**
     *
     */
    private Boolean _filterField;

    /**
     *
     */
    private Boolean _sortField;

    /**
     *
     */
    private Boolean _tableField;

    /**
     *
     */
    private boolean _detailField = true;

    /**
     *
     */
    private Boolean _columnField;

    /**
     *
     */
    private Boolean _reportField;

    /**
     *
     */
    private Boolean _exportField;

    /**
     *
     */
    private boolean _headertextlessField;

    /**
     *
     */
    private Boolean _headingField;

    /**
     *
     */
    private Boolean _overlayField;

    /**
     *
     */
    private Boolean _prominentField;

    /**
     *
     */
    private Boolean _immutableField;

    /**
     *
     */
    Boolean _serializableField;

    /**
     *
     */
    Boolean _serializableIUID;

    /**
     *
     */
    private DefaultCondition _defaultCondition = DefaultCondition.IF_NULL;

    /**
     *
     */
    private Checkpoint _defaultCheckpoint = Checkpoint.UNSPECIFIED;

    /**
     *
     */
    private String _defaultFunction;

    /**
     *
     */
    private String _anchorFieldName;

    /**
     *
     */
    private Field _anchorField;

    /**
     *
     */
    private Property _anchorProperty;

    /**
     *
     */
    private Parameter _anchorParameter;

    /**
     *
     */
    private AnchorType _anchorType = AnchorType.UNLINKED;

    /**
     *
     */
    private AnchorType _firstAnchoredFieldAnchorType = AnchorType.UNLINKED;

    /**
     *
     */
    private boolean _anchoringLinkedDetailFields;

    /**
     *
     */
    private boolean _anchoringLinkedParameters;

    /**
     *
     */
//  private String _defaultAnchorLabel;
    private final Map<Locale, String> _localizedAnchorLabel = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultAnchoredLabel;
    private final Map<Locale, String> _localizedAnchoredLabel = new LinkedHashMap<>();

    /**
     *
     */
    private int _sequenceNumber;

    /**
     *
     */
    private String _displaySortKey = "";

    /**
     *
     */
    private AggregateFunction _aggregateFunction = AggregateFunction.UNSPECIFIED;

    /**
     *
     */
    private String _aggregateTitle = "";

    /**
     *
     */
    private final Map<Locale, String> _localizedAggregateTitle = new LinkedHashMap<>();

    /**
     *
     */
    private boolean _calculable;

    /**
     *
     */
    private boolean _nullable = true;

    /**
     *
     */
    private boolean _insertable = true;

    /**
     *
     */
    private boolean _updateable = true;

    /**
     *
     */
    private boolean _unique;

    /**
     *
     */
    private boolean _indexed;

    /**
     *
     */
    private String _transitionUserFieldName;

    /**
     *
     */
    private Field _transitionUserField;

    /**
     *
     */
    private Entity _transitionUserEntity;

    /**
     *
     */
    private String _transitionDateTimeFieldName;

    /**
     *
     */
    private Field _transitionDateTimeField;

    /**
     *
     */
    private Property _transitionDateTimeProperty;

    /**
     *
     */
    private String _linkedFieldName;

    /**
     *
     */
    private Field _linkedField;

    /**
     *
     */
    private Entity _linkedEntity;

    /**
     *
     */
    private Property _linkedProperty;

    /**
     *
     */
    private String _linkedColumnName;

    /**
     *
     */
    private StandardRelationalOp _linkedColumnOperator = StandardRelationalOp.EQ; // StandardRelationalOp.UNSPECIFIED?

    /**
     *
     */
    private String _masterHeadingSnippetFileName = "";

    /**
     *
     */
    private String _readingTableSnippetFileName = "";

    /**
     *
     */
    private String _writingTableSnippetFileName = "";

    /**
     *
     */
    private String _readingDetailSnippetFileName = "";

    /**
     *
     */
    private String _writingDetailSnippetFileName = "";

    /**
     *
     */
    private String _processingConsoleSnippetFileName = "";

    /**
     *
     */
    private String _missingValueGraphicImageName;

    /**
     *
     */
    private String _nullValueGraphicImageName;

    /**
     *
     */
    private String _unnecessaryValueGraphicImageName;

    /**
     *
     */
    private CharacterExpression _graphicImageNameExpression;

    /**
     *
     */
    private boolean _nullValueGraphicImageNameExpression;

    /**
     *
     */
    private boolean _graphicImageStyleClassNameExpression;

    /**
     *
     */
    private final Map<Locale, Map<String, String>> _localizedGraphicImageTooltip = new LinkedHashMap<>();

    /**
     *
     */
    private long _sequencePropertyStart = 1;

    /**
     *
     */
    private long _sequencePropertyStop = Long.MAX_VALUE;

    /**
     *
     */
    private long _sequencePropertyStep = 1;

    /**
     *
     */
    DataGenType _dataGenType = DataGenType.UNSPECIFIED;

    /**
     *
     */
    private Boolean _loremIpsum;

    /**
     *
     */
    private int _dataGenSeriesStart = 1;

    /**
     *
     */
    private int _dataGenSeriesStop = Constants.MAX_SERIES_STOP;

    /**
     *
     */
    private int _dataGenSeriesStep = 1;

    /**
     *
     */
    private String _dataGenFunction;

    /**
     *
     */
    int _dataGenNullable = 10;

    /**
     *
     */
    private int _dataGenTrueable = 50;

    /**
     *
     */
    private String _dataGenPattern;

    /**
     *
     */
    private String _dataGenPrefix;

    /**
     *
     */
    private String _dataGenSuffix;

    /**
     *
     */
    private String _dataGenMin;

    /**
     *
     */
    private String _dataGenMax;

    /**
     *
     */
    private TemporalAddend _dataGenMinTemporalAddend;

    /**
     *
     */
    private TemporalAddend _dataGenMaxTemporalAddend;

    /**
     *
     */
    private Object _dataGenMinValue;

    /**
     *
     */
    private Object _dataGenMaxValue;

    /**
     *
     */
    private DataGenTemporalInterval _dataGenTemporalInterval = DataGenTemporalInterval.UNSPECIFIED;

    /**
     *
     */
    private DataGenNumericAction _dataGenNumericAction = DataGenNumericAction.UNSPECIFIED;

    /**
     *
     */
    private Object _dataGenFactor;

    /**
     *
     */
    private boolean _renderingFilterReadOnly;

    /**
     *
     */
    private BooleanExpression _renderingFilter;

    /**
     *
     */
    private BooleanExpression _requiringFilter;

    /**
     *
     */
    private BooleanExpression _modifyingFilter;

    /**
     *
     */
    private BooleanExpression _nullifyingFilter;

    /**
     * annotated with BaseField
     */
    private boolean _annotatedWithBaseField;

    /**
     * annotated with PrimaryKey
     */
    private boolean _annotatedWithPrimaryKey;

    /**
     * annotated with SequenceProperty
     */
    private boolean _annotatedWithSequenceProperty;

    /**
     * annotated with VersionProperty
     */
    private boolean _annotatedWithVersionProperty;

    /**
     * annotated with NameProperty
     */
    private boolean _annotatedWithNameProperty;

    /**
     * annotated with DescriptionProperty
     */
    private boolean _annotatedWithDescriptionProperty;

    /**
     * annotated with ImageProperty
     */
    private boolean _annotatedWithImageProperty;

    /**
     * annotated with InactiveIndicator
     */
    private boolean _annotatedWithInactiveIndicator;

    /**
     * annotated with UrlProperty
     */
    private boolean _annotatedWithUrlProperty;

    /**
     * annotated with ParentProperty
     */
    private boolean _annotatedWithParentProperty;

    /**
     * annotated with OwnerProperty
     */
    private boolean _annotatedWithOwnerProperty;

    /**
     * annotated with UserProperty
     */
    private boolean _annotatedWithUserProperty;

    /**
     * annotated with SegmentProperty
     */
    private boolean _annotatedWithSegmentProperty;

    /**
     * annotated with UniqueKey
     */
    private boolean _annotatedWithUniqueKey;

    /**
     * annotated with BusinessKey
     */
    private boolean _annotatedWithBusinessKey;

    /**
     * annotated with DiscriminatorColumn
     */
    private boolean _annotatedWithDiscriminatorColumn;

    /**
     * annotated with StateProperty
     */
    private boolean _annotatedWithStateProperty;

    /**
     * annotated with ColumnField
     */
    private boolean _annotatedWithColumnField;

    /**
     * annotated with BigDecimalField
     */
    private boolean _annotatedWithBigDecimalField;

    /**
     * annotated with BigIntegerField
     */
    private boolean _annotatedWithBigIntegerField;

    /**
     * annotated with BooleanField
     */
    private boolean _annotatedWithBooleanField;

    /**
     * annotated with BooleanField
     */
    private boolean _annotatedWithNumericField;

    /**
     * annotated with StringField
     */
    private boolean _annotatedWithStringField;

    /**
     * annotated with DateField
     */
    private boolean _annotatedWithDateField;

    /**
     * annotated with TimeField
     */
    private boolean _annotatedWithTimeField;

    /**
     * annotated with TimestampField
     */
    private boolean _annotatedWithTimestampField;

    /**
     * annotated with ParameterField
     */
    private boolean _annotatedWithParameterField;

    /**
     * annotated with PropertyField
     */
    private boolean _annotatedWithPropertyField;

    /**
     * annotated with PropertyAggregation
     */
    private boolean _annotatedWithPropertyAggregation;

    /**
     * annotated with InstanceReference
     */
    private boolean _annotatedWithInstanceReference;

    /**
     * annotated with EmbeddedDocument
     */
    private boolean _annotatedWithEmbeddedDocument;

    /**
     * annotated with FileReference
     */
    private boolean _annotatedWithFileReference;

    /**
     * annotated with GraphicImage
     */
    private boolean _annotatedWithGraphicImage;

    /**
     * annotated with MasterSequence
     */
    private boolean _annotatedWithMasterSequence;

    /**
     * annotated with UniformResourceLocator
     */
    private boolean _annotatedWithUniformResourceLocator;

    /**
     * annotated with VariantString
     */
    private boolean _annotatedWithVariantString;

    /**
     * annotated with DataGen
     */
    private boolean _annotatedWithDataGen;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return the SQL name
     */
    @Override
    public String getSqlName() {
        if (isProperty()) {
            Entity declaringEntity = getDeclaringEntity();
            if (declaringEntity.isRootInstance()) {
                return super.getSqlName();
            }
            String name = getName();
            if (name != null) {
                Entity root = declaringEntity.getRoot();
                for (Property rootProperty : root.getPropertiesList()) {
                    if (name.equals(rootProperty.getName())) {
                        return rootProperty.getSqlName();
                    }
                }
            }
        }
        return super.getSqlName();
    }

    /**
     * @return the key property list
     */
    public List<KeyProperty> getKeyProperties() {
        List<KeyProperty> list = new ArrayList<>();
        if (isPrimaryKeyProperty()) {
            list.add(KeyProperty.PRIMARY_KEY);
        }
        if (isSequenceProperty()) {
            list.add(KeyProperty.SEQUENCE);
        }
        if (isVersionProperty()) {
            list.add(KeyProperty.VERSION);
        }
        if (isNumericKeyProperty()) {
            list.add(KeyProperty.NUMERIC_KEY);
        }
        if (isCharacterKeyProperty()) {
            list.add(KeyProperty.CHARACTER_KEY);
        }
        if (isNameProperty()) {
            list.add(KeyProperty.NAME);
        }
        if (isDescriptionProperty()) {
            list.add(KeyProperty.DESCRIPTION);
        }
        if (isImageProperty()) {
            list.add(KeyProperty.IMAGE);
        }
        if (isInactiveIndicatorProperty()) {
            list.add(KeyProperty.INACTIVE_INDICATOR);
        }
        if (isUrlProperty()) {
            list.add(KeyProperty.URL);
        }
        if (isParentProperty()) {
            list.add(KeyProperty.PARENT);
        }
        if (isOwnerProperty()) {
            list.add(KeyProperty.OWNER);
        }
        if (isUserProperty()) {
            list.add(KeyProperty.USER);
        }
        if (isSegmentProperty()) {
            list.add(KeyProperty.SEGMENT);
        }
        if (isUniqueKeyProperty()) {
            list.add(KeyProperty.UNIQUE_KEY);
        }
        if (isBusinessKeyProperty()) {
            list.add(KeyProperty.BUSINESS_KEY);
        }
        if (isDiscriminatorProperty()) {
            list.add(KeyProperty.DISCRIMINATOR);
        }
        if (isStateProperty()) {
            list.add(KeyProperty.STATE);
        }
        return list;
    }

    /**
     * @return the corresponding property at the declaring entity's root instance
     */
    @Override
    public Property getPropertyAtRoot() {
        if (isProperty()) {
            Entity declaringEntity = getDeclaringEntity();
            if (declaringEntity.isRootInstance()) {
                return this;
            }
            String name = getName();
            if (name != null) {
                Entity root = declaringEntity.getRoot();
                for (Property property : root.getPropertiesList()) {
                    if (name.equals(property.getName())) {
                        return property;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return the data class
     */
    @Override
    public Class<?> getDataClass() {
        return _dataClass;
    }

    /**
     * @param clazz the data class to set
     */
    void setDataClass(Class<?> clazz) {
        _dataClass = clazz;
    }

    /**
     * @return the data type
     */
    @Override
    public Class<?> getDataType() {
        return _dataType;
    }

    /**
     * @param type the data type to set
     */
    void setDataType(Class<?> type) {
        _dataType = type;
    }

    /**
     * @return the segment entity class
     */
    @Override
    public Class<?> getSegmentEntityClass() { // since 20210218
        return _segmentEntityClass == null ? null : _segmentEntityClass.equals(Entity.class) ? getDataType() : _segmentEntityClass;
    }

    /**
     * @return the Serial Version UID of the data class
     */
    @Override
    public Long getSerialVersionUID() { // since 19/01/2023
        return getSerialVersionUID(getDataClass());
    }

    protected Long getSerialVersionUID(Class<?> dataClass) { // since 19/01/2023
        ObjectStreamClass objectStreamClass = dataClass == null ? null : ObjectStreamClass.lookup(dataClass);
        return objectStreamClass == null ? null : objectStreamClass.getSerialVersionUID();
    }

    /**
     * @return true if the field defines a base field
     */
    @Override
    public boolean isBaseField() {
        return _annotatedWithBaseField || isPrimaryKeyProperty(); // || isSequenceProperty() || isVersionProperty(); // 20200523
    }

    /**
     * @return true if the field defines a key field
     */
    @Override
    public boolean isKeyField() {
        return isPrimaryKeyProperty()
            || isSequenceProperty()
            || isUniqueKeyProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty();
    }

    /**
     * @return the property access
     */
    @Override
    public PropertyAccess getPropertyAccess() {
        return _password ? PropertyAccess.RESTRICTED_READING
            : (_propertyAccess == null || _propertyAccess.equals(PropertyAccess.UNSPECIFIED)) ? PropertyAccess.UNRESTRICTED : _propertyAccess;
    }

    private boolean unrestrictedReadingField() {
        return !restrictedReadingField();
    }

    private boolean restrictedReadingField() {
        return restrictedReadingAccess() || isFileReferenceField();
    }

    private boolean restrictedReadingAccess() {
        return PropertyAccess.RESTRICTED_READING.equals(getPropertyAccess());
    }

    protected void setPropertyAccess(PropertyAccess propertyAccess) {
        _propertyAccess = coalesce(propertyAccess, PropertyAccess.UNSPECIFIED);
    }

    /**
     * @return the auditable indicator
     */
    @Override
    public boolean isAuditable() {
        return _auditable && !_password && !isBinaryData();
    }

    protected void setAuditable(boolean auditable) {
        _auditable = auditable;
    }

    /**
     * @return the password indicator
     */
    @Override
    public boolean isPassword() {
        return _password;
    }

    protected void setPassword(boolean password) {
        _password = password;
    }

    /**
     * @return the read only indicator
     */
    @Override
    public boolean isReadOnly() {
        return !isCreateField() && !isUpdateField();
    }

    /**
     * @return the required indicator
     */
    protected Boolean isRequired() {
        return isParameter() ? isRequiredParameter() : isProperty() ? isRequiredProperty() : coalesce(_required, false);
    }

    /**
     * @return the required parameter indicator
     */
    @Override
    public boolean isRequiredParameter() {
        return !isHiddenField() && !isBinaryData() && BitUtils.valueOf(_required);
    }

    /**
     * @return the required property indicator
     */
    @Override
    public boolean isRequiredProperty() {
        return !isHiddenField() && !isBinaryData() && BitUtils.valueOf(_required, implicitRequiredProperty());
    }

    private boolean implicitRequiredProperty() {
        return !_calculable
            && !_nullable
            && !isDiscriminatorProperty()
            && getDefaultValue() == null;
    }

    protected void setRequired(Boolean required) {
        _required = required;
    }

    /**
     * @return the hidden field indicator
     */
    @Override
    public boolean isHiddenField() {
        return _hiddenField;
    }

    /**
     * @return the hidden entity reference field indicator
     */
    @Override
    public boolean isHiddenEntityReferenceField() {
        return isHiddenField() && isEntity();
    }

    protected void setHiddenField(boolean hiddenField) {
        _hiddenField = hiddenField;
    }

    /**
     * @return the create field indicator
     */
    @Override
    public boolean isCreateField() {
        return !isHiddenField() && !isPrimaryKeyProperty() && !isSequenceProperty() && !isVersionProperty() && !isBinaryData()
            && (_calculable ? !isFileReferenceFieldWithoutBlob() : _insertable) && BitUtils.valueOf(_createField, implicitCreateField());
    }

    private boolean implicitCreateField() {
        return isInsertable() && !defaultUnconditionallyOnInsert() && isUnlinkedProperty() && isRequiredProperty() && !isEnumerationEntityProperty();
    }

    private boolean defaultUnconditionallyOnInsert() {
        return getDefaultValue() != null && (DefaultCondition.UNCONDITIONALLY.equals(_defaultCondition)
            || DefaultCondition.UNCONDITIONALLY_ON_INSERT.equals(_defaultCondition));
    }

    protected void setCreateField(Boolean createField) {
        _createField = createField;
    }

    /**
     * @return the update field indicator
     */
    @Override
    public boolean isUpdateField() {
        return !isHiddenField() && !isPrimaryKeyProperty() && !isSequenceProperty() && !isVersionProperty() && !isBinaryData()
            && (_calculable ? !isFileReferenceFieldWithoutBlob() : _updateable) && BitUtils.valueOf(_updateField, implicitUpdateField());
    }

    private boolean implicitUpdateField() {
        return isUpdateable() && !defaultUnconditionallyOnUpdate() && isUnlinkedProperty() && !isEnumerationEntityProperty();
    }

    private boolean defaultUnconditionallyOnUpdate() {
        return getDefaultValue() != null && (DefaultCondition.UNCONDITIONALLY.equals(_defaultCondition)
            || DefaultCondition.UNCONDITIONALLY_ON_UPDATE.equals(_defaultCondition));
    }

    private boolean isUnlinkedProperty() {
        if (isProperty()) {
            List<Parameter> parametersList;
            Entity declaringEntity = getDeclaringEntity();
            List<Operation> businessOperationsList = declaringEntity.getBusinessOperationsList();
            for (Operation operation : businessOperationsList) {
                if (operation instanceof ProcessOperation && OperationKind.INSTANCE.equals(operation.getOperationKind())) {
                    parametersList = operation.getParametersList();
                    for (Parameter parameter : parametersList) {
                        if (this.equals(parameter.getLinkedProperty())) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    protected void setUpdateField(Boolean updateField) {
        _updateField = updateField;
    }

    /**
     * @return the search field indicator
     */
    @Override
    public boolean isSearchField() {
        return !isHiddenField() && !isBinaryData() && !isCharacterLargeObject() && !isVariantStringField()
            && !_password && BitUtils.valueOf(_searchField, implicitSearchField());
    }

    private boolean implicitSearchField() {
        return implicitSearchFieldDisjunction() && unrestrictedReadingField();
    }

    private boolean implicitSearchFieldDisjunction() {
        return isTableField()
            || isUniqueKeyProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isDiscriminatorProperty()
            || isNameProperty()
            || isInactiveIndicatorProperty();
    }

    protected void setSearchField(Boolean searchField) {
        _searchField = searchField;
    }

    /**
     * @return the filter field indicator
     */
    @Override
    public boolean isFilterField() {
        return !isHiddenField() && !isBinaryData() && !isCharacterLargeObject() && !isVariantStringField()
            && !_password && BitUtils.valueOf(_filterField, implicitFilterField());
    }

    private boolean implicitFilterField() {
        return isSearchField() || unrestrictedReadingField();
    }

    protected void setFilterField(Boolean filterField) {
        _filterField = filterField;
    }

    /**
     * @return the sort field indicator
     */
    @Override
    public boolean isSortField() {
        return !isHiddenField() && !isBinaryData() && !isCharacterLargeObject() && !_password && BitUtils.valueOf(_sortField, unrestrictedReadingField());
    }

    protected void setSortField(Boolean sortField) {
        _sortField = sortField;
    }

    /**
     * @return the table field indicator
     */
    @Override
    public boolean isTableField() {
        return !isHiddenField() && !isUndisplayableBinaryData() && !_password && BitUtils.valueOf(_tableField, implicitTableField());
    }

    private boolean implicitTableField() {
        return isRequiredProperty()
            || isUniqueKeyProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isDiscriminatorProperty()
            || isNameProperty()
            || isStateProperty()
            || isInactiveIndicatorProperty();
    }

    private boolean requiredTableField() {
        return isTableField() && isRequiredProperty();
    }

    protected void setTableField(Boolean tableField) {
        _tableField = tableField;
    }

    /**
     * @return the detail field indicator
     */
    @Override
    public boolean isDetailField() {
        return !isHiddenField() && !isUndisplayableBinaryData() && _detailField;
    }

    protected void setDetailField(boolean detailField) {
        _detailField = detailField;
    }

    /**
     * @return the column field indicator
     */
    @Override
    public boolean isColumnField() {
        return !isHiddenField() && !isBinaryData() && !_password && BitUtils.valueOf(_columnField, !isFileReferenceField());
    }

    protected void setColumnField(Boolean columnField) {
        _columnField = columnField;
    }

    /**
     * @return the report field indicator
     */
    @Override
    public boolean isReportField() {
        return !isHiddenField() && !isBinaryData() && !_password && BitUtils.valueOf(_reportField, implicitReportField());
    }

    private boolean implicitReportField() {
        return implicitReportFieldDisjunction() && unrestrictedReadingField();
    }

    private boolean implicitReportFieldDisjunction() {
        return isRequiredProperty()
            || isUniqueKeyProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isDiscriminatorProperty()
            || isNameProperty()
            || isInactiveIndicatorProperty();
    }

    protected void setReportField(Boolean reportField) {
        _reportField = reportField;
    }

    /**
     * @return the export field indicator
     */
    @Override
    public boolean isExportField() {
        return !isHiddenField() && !isBinaryData() && !_password && BitUtils.valueOf(_exportField, unrestrictedReadingField());
    }

    protected void setExportField(Boolean exportField) {
        _exportField = exportField;
    }

    /**
     * @return the headertextless field indicator
     */
    @Override
    public boolean isHeadertextlessField() {
        return !isHiddenField() && !isBinaryData() && _headertextlessField;
    }

    protected void setHeadertextlessField(boolean headertextlessField) {
        _headertextlessField = headertextlessField;
    }

    /**
     * @return the heading field indicator
     */
    @Override
    public boolean isHeadingField() {
        // since 06/06/2022 unrestrictedReadingField instead of !restrictedReadingAccess()
        return !isHiddenField() && !isUndisplayableBinaryData() && !isCharacterLargeObject() && !isVariantStringField() && unrestrictedReadingField()
            && !_password && BitUtils.valueOf(_headingField, implicitHeadingField());
    }

    private boolean implicitHeadingField() {
        return isBusinessKeyProperty()
            || isNameProperty()
            || isDiscriminatorProperty()
            || isInactiveIndicatorProperty()
            || isUniqueKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isStateProperty()
            || isCodelessNamelessEntityUniqueKeyProperty();
    }

    protected void setHeadingField(Boolean headingField) {
        _headingField = headingField;
    }

    /**
     * @return the overlay field indicator
     */
    @Override
    public boolean isOverlayField() {
        return !isHiddenField() && !isUndisplayableBinaryData() && !isCharacterLargeObject() && !isVariantStringField() && unrestrictedReadingField()
            && !_password && BitUtils.valueOf(_overlayField, implicitOverlayField());
    }

    private boolean implicitOverlayField() {
        return implicitOverlayFieldDisjunction(); // && unrestrictedReadingField();
    }

    private boolean implicitOverlayFieldDisjunction() {
        if (isBusinessKeyProperty() || isNameProperty()) {
            return false;
        }
        return isImplicitOverlayImageProperty()
            || isDiscriminatorProperty()
            || isStateProperty()
            || isInactiveIndicatorProperty()
            || isUniqueKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isCodelessNamelessEntityUniqueKeyProperty();
    }

    protected boolean isImplicitOverlayImageProperty() {
        return isImageProperty();
    }

    private boolean isCodelessNamelessEntityUniqueKeyProperty() {
        PersistentEntity pent = getDeclaringPersistentEntityRoot();
        return pent != null
            && pent.getBusinessKeyProperty() == null
            && pent.getNameProperty() == null
            && pent.getUniqueKeyPropertiesList().contains(this);
    }

    protected void setOverlayField(Boolean overlayField) {
        _overlayField = overlayField;
    }

    /**
     * A prominent field should be visible at all times.
     *
     * @return the prominent field indicator
     */
    public boolean isProminentField() {
        return !isHiddenField() && !isUndisplayableBinaryData() && !restrictedReadingAccess() && BitUtils.valueOf(_prominentField, implicitProminentField());
    }

    private boolean implicitProminentField() {
        return requiredTableField() // isRequiredProperty or requiredTableField ?
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isUniqueKeyProperty()
            || isDiscriminatorProperty()
            || isInactiveIndicatorProperty()
            || isNameProperty()
            || isOwnerProperty()
            || isParentProperty()
            || isSegmentProperty()
            || isStateProperty()
            || isHeadingField();
    }

    protected void setProminentField(Boolean prominentField) {
        _prominentField = prominentField;
    }

    /**
     * @return the immutable field indicator
     */
    @Override
    public boolean isImmutableField() {
        return !isUpdateField() && BitUtils.valueOf(_immutableField);
    }

    protected void setImmutableField(Boolean immutableField) {
        _immutableField = immutableField;
    }

    /**
     * @return the serializable field indicator
     */
    @Override
    public boolean isSerializableField() {
        return BitUtils.valueOf(_serializableField, implicitSerializableField());
    }

    /**
     * El valor implícito de serializable es falso si la propiedad es calculable, tiene acceso de lectura restringido, es de tipo binario o es una
     * referencia oculta a una entidad; de lo contrario, es verdadero.
     *
     * @return valor implícito de serializable
     */
    private boolean implicitSerializableField() {
        return !isCalculable()
            && !isPassword()
            && !isBinaryData();
    }

    /**
     * @return the transient field indicator
     */
    @Override
    public boolean isTransientField() {
        return !isSerializableField();
    }

    protected void setSerializableField(Boolean serializableField) {
        _serializableField = serializableField;
    }

    /**
     * @return the serializable IUID indicator
     */
    @Override
    public boolean isSerializableIUID() {
        return isEntity() && BitUtils.valueOf(_serializableIUID, isTransientField());
    }

    protected void setSerializableIUID(Boolean serializableIUID) {
        _serializableIUID = serializableIUID;
    }

    /**
     * @return the default condition
     */
    @Override
    public DefaultCondition getDefaultCondition() {
        return _defaultCondition;
    }

    protected void setDefaultCondition(DefaultCondition defaultCondition) {
        _defaultCondition = coalesce(defaultCondition, DefaultCondition.IF_NULL);
    }

    /**
     * @return the default checkpoint
     */
    @Override
    public Checkpoint getDefaultCheckpoint() {
        return _defaultCheckpoint == null || _defaultCheckpoint.equals(Checkpoint.UNSPECIFIED) ? Checkpoint.WHEREVER_POSSIBLE : _defaultCheckpoint;
    }

    Checkpoint defaultCheckpoint() {
        return coalesce(_defaultCheckpoint, Checkpoint.UNSPECIFIED);
    }

    protected void setDefaultCheckpoint(Checkpoint defaultCheckpoint) {
        _defaultCheckpoint = coalesce(defaultCheckpoint, Checkpoint.UNSPECIFIED);
    }

    /**
     * @return the default function
     */
    @Override
    public String getDefaultFunction() {
        return _defaultFunction;
    }

    protected void setDefaultFunction(String defaultFunction) {
        _defaultFunction = StringUtils.trimToNull(defaultFunction);
    }

    /**
     * @return the anchor field name
     */
    @Override
    public String getAnchorFieldName() {
        return _anchorFieldName;
    }

    /**
     * @return the anchor field
     */
    @Override
    public Field getAnchorField() {
        return _anchorField;
    }

    /**
     * @return the anchor property
     */
    @Override
    public Property getAnchorProperty() {
//      return _anchorProperty == null ? calculableAnchorProperty() : _anchorProperty;
        return _anchorProperty;
    }

    /*
    private Property calculableAnchorProperty() {
        Object calculableValue = isCreateField() || isUpdateField() ? null : getCalculableValue();
        if (calculableValue instanceof Property) {
            Entity declaringEntity = getDeclaringEntity();
            Property property = (Property) calculableValue;
            List<Artifact> pathList = property.getPathList();
            for (Artifact artifact : pathList) {
                if (artifact instanceof Property && artifact.getDeclaringEntity() == declaringEntity) {
                    return (Property) artifact;
                }
            }
        }
        return null;
    }

    /**/
    /**
     * @return the anchor parameter
     */
    @Override
    public Parameter getAnchorParameter() {
        return _anchorParameter;
    }

    /**
     * @return the anchor type
     */
    @Override
    public AnchorType getAnchorType() {
        return _anchorType;
    }

    /**
     * @return the anchor type of the first anchored field
     */
    @Override
    public AnchorType getFirstAnchoredFieldAnchorType() {
        return _firstAnchoredFieldAnchorType;
    }

    /**
     * Sets the first anchored field anchor type
     *
     * @param anchorType the anchor type of the first anchored field
     */
    @Override
    public void setFirstAnchoredFieldAnchorType(AnchorType anchorType) {
        _firstAnchoredFieldAnchorType = anchorType;
    }

    /**
     * @return the anchoring linked detail fields indicator
     */
    @Override
    public boolean isAnchoringLinkedDetailFields() {
        return _anchoringLinkedDetailFields;
    }

    /**
     * Sets the anchoring linked detail fields indicator
     *
     * @param b the anchoring detail fields indicator to set
     */
    @Override
    public void setAnchoringLinkedDetailFields(boolean b) {
        _anchoringLinkedDetailFields = b;
    }

    /**
     * @return the anchoring linked parameters indicator
     */
    @Override
    public boolean isAnchoringLinkedParameters() {
        return _anchoringLinkedParameters;
    }

    /**
     * Sets the anchoring linked parameters indicator
     *
     * @param b the anchoring parameters indicator to set
     */
    @Override
    public void setAnchoringLinkedParameters(boolean b) {
        _anchoringLinkedParameters = b;
    }

    /**
     * @return the default anchor label
     */
    @Override
    public String getDefaultAnchorLabel() {
        return getLocalizedAnchorLabel(null);
    }

    /**
     * El método setDefaultAnchorLabel se utiliza para establecer la etiqueta del grupo de propiedades ancladas a la propiedad que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultAnchorLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del grupo de propiedades ancladas
     */
    @Override
    public void setDefaultAnchorLabel(String defaultAnchorLabel) {
        setLocalizedAnchorLabel(null, defaultAnchorLabel);
    }

    /**
     * @param locale the locale for the anchor label
     * @return the localized anchor label
     */
    @Override
    public String getLocalizedAnchorLabel(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedAnchorLabel.get(l);
    }

    /**
     * El método setLocalizedAnchorLabel se utiliza para establecer la etiqueta del grupo de propiedades ancladas a la propiedad que se almacena en el
     * archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedAnchorLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del grupo de propiedades ancladas
     */
    @Override
    public void setLocalizedAnchorLabel(Locale locale, String localizedAnchorLabel) {
        Locale l = localeWritingKey(locale);
        if (localizedAnchorLabel == null) {
            _localizedAnchorLabel.remove(l);
        } else {
            _localizedAnchorLabel.put(l, localizedAnchorLabel);
        }
    }

    /**
     * @return the default anchored label
     */
    @Override
    public String getDefaultAnchoredLabel() {
        return getLocalizedAnchoredLabel(null);
    }

    /**
     * El método setDefaultAnchoredLabel se utiliza para establecer la etiqueta de la propiedad dentro del grupo de propiedades ancladas que se
     * almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultAnchoredLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta dentro del grupo de propiedades
     * ancladas
     */
    @Override
    public void setDefaultAnchoredLabel(String defaultAnchoredLabel) {
        setLocalizedAnchoredLabel(null, defaultAnchoredLabel);
    }

    /**
     * @param locale the locale for the anchored label
     * @return the localized anchored label
     */
    @Override
    public String getLocalizedAnchoredLabel(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedAnchoredLabel.get(l);
    }

    /**
     * El método setLocalizedAnchoredLabel se utiliza para establecer la etiqueta de la propiedad dentro del grupo de propiedades ancladas que se
     * almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario
     * no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedAnchoredLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta dentro del grupo de propiedades
     * ancladas
     */
    @Override
    public void setLocalizedAnchoredLabel(Locale locale, String localizedAnchoredLabel) {
        Locale l = localeWritingKey(locale);
        if (localizedAnchoredLabel == null) {
            _localizedAnchoredLabel.remove(l);
        } else {
            _localizedAnchoredLabel.put(l, localizedAnchoredLabel);
        }
    }

    @Override
    protected void copyLocalizedStrings(Artifact artifact) {
        super.copyLocalizedStrings(artifact);
        if (artifact != this) {
            if (artifact instanceof AbstractDataArtifact that) {
                _localizedAnchorLabel.clear();
                _localizedAnchorLabel.putAll(that._localizedAnchorLabel);
                _localizedAnchoredLabel.clear();
                _localizedAnchoredLabel.putAll(that._localizedAnchoredLabel);
            }
        }
    }

    /**
     * @return the sequence number
     */
    @Override
    public int getSequenceNumber() {
        if (_sequenceNumber == 0) {
            if (isParameter()) {
                Operation declaringOperation = getDeclaringOperation();
                List<Parameter> parameters = declaringOperation == null ? null : declaringOperation.getParametersList();
                return parameters == null || parameters.isEmpty() ? 0 : parameters.indexOf(this) + 1;
            }
            if (isProperty()) {
                Entity declaringEntity = getDeclaringEntity();
                List<Property> properties = declaringEntity == null ? null : declaringEntity.getPropertiesList();
                return properties == null || properties.isEmpty() ? 0 : properties.indexOf(this) + 1;
            }
        }
        return _sequenceNumber;
    }

    /**
     * @return the display sort key
     */
    @Override
    public String getDisplaySortKey() {
        return _displaySortKey;
    }

    /**
     * Sets the display sort key
     *
     * @param key the display sort key to set
     */
    @Override
    public void setDisplaySortKey(String key) {
        _displaySortKey = StringUtils.trimToEmpty(key);
    }

    /**
     * @return the aggregate function
     */
    @Override
    public AggregateFunction getAggregateFunction() {
        return AggregateFunction.UNSPECIFIED.equals(_aggregateFunction) ? null : _aggregateFunction;
    }

    protected void setAggregateFunction(AggregateFunction aggregateFunction) {
        _aggregateFunction = coalesce(aggregateFunction, AggregateFunction.UNSPECIFIED);
    }

    /**
     * @return the aggregate title
     */
    @Override
    public String getAggregateTitle() {
        return _aggregateFunction == null ? "" : StrUtils.coalesce(getLocalizedAggregateTitle(null), _aggregateFunction.getNameTag(), _aggregateTitle);
    }

    protected void setAggregateTitle(String aggregateTitle) {
        setLocalizedAggregateTitle(null, StringUtils.trimToEmpty(aggregateTitle));
    }

    /**
     * @param locale the locale for the aggregate title
     * @return the localized aggregate title
     */
//  @Override
    public String getLocalizedAggregateTitle(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedAggregateTitle.get(l);
    }

    /**
     * El método setLocalizedAggregateTitle se utiliza para establecer el título de la función de agregación que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor del título.
     *
     * Solo es necesario establecer el título si la función es CUSTOM_MADE, pero puede usarse para especificar un título diferente al predeterminado
     * de las demás funciones.
     *
     * @param locale configuración regional
     * @param localizedAggregateTitle una frase que describe sucintamente la función de agregación
     */
//  @Override
    public void setLocalizedAggregateTitle(Locale locale, String localizedAggregateTitle) {
        Locale l = localeWritingKey(locale);
        if (localizedAggregateTitle == null) {
            _localizedAggregateTitle.remove(l);
        } else {
            _localizedAggregateTitle.put(l, localizedAggregateTitle);
        }
    }

    /**
     * @return the calculable indicator
     */
    @Override
    public boolean isCalculable() {
        return _calculable || isCalculable(getDeclaringField()); // 13/05/2021
    }

    protected boolean isCalculable(Field field) {
        ColumnField annotation = field == null ? null : field.getAnnotation(ColumnField.class);
        return annotation != null && annotation.calculable().toBoolean(_calculable);
    }

    protected void setCalculable(boolean calculable) {
        _calculable = calculable;
    }

    /**
     * @return true if it is a calculated property; otherwise false
     */
    @Override
    public boolean isCalculatedProperty() {
        return calculatedProperty(this);
    }

    /**
     * @return the nullable indicator
     */
    @Override
    public boolean isNullable() {
        return _nullable;
    }

    protected void setNullable(boolean nullable) {
        _nullable = nullable;
    }

    /**
     * @return the insertable indicator
     */
    @Override
    public boolean isInsertable() {
        return _insertable;
    }

    protected void setInsertable(boolean insertable) {
        _insertable = insertable;
    }

    /**
     * @return the updateable indicator
     */
    @Override
    public boolean isUpdateable() {
        return _updateable;
    }

    protected void setUpdateable(boolean updateable) {
        _updateable = updateable;
    }

    /**
     * @return the mandatory-for-insert indicator
     */
    @Override
    public boolean isMandatoryForInsert() {
        return !isOptionalForInsert();
    }

    /**
     * @return the optional-for-insert indicator
     */
    @Override
    public boolean isOptionalForInsert() {
        if (isCalculable() || isNullable()) {
            return true;
        }
        if (StringUtils.isNotBlank(getDefaultFunction()) || getDefaultValue() != null) {
            switch (getDefaultCheckpoint()) {
                case DATABASE_TRIGGER, WHEREVER_POSSIBLE -> {
                    switch (getDefaultCondition()) {
                        case IF_NULL, IF_NULL_ON_INSERT, UNCONDITIONALLY, UNCONDITIONALLY_ON_INSERT -> {
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity != null) {
            if (isSequenceProperty() || isVersionProperty()) {
                return true;
            }
            if (isPrimaryKeyProperty() && declaringEntity.isNonEnumerationEntity()) {
                return true;
            }
            if (isDiscriminatorProperty()) {
                /*
                if (declaringEntity instanceof PersistentEntity) {
                    PersistentEntity persistentEntity = (PersistentEntity) declaringEntity;
                    if (StringUtils.isNotBlank(persistentEntity.getDiscriminatorValue())) {
                        return true;
                    }
                }
                /**/
                return true;
            }
        }
        return false;
    }

    /**
     * @return the unique indicator
     */
    @Override
    public boolean isUnique() {
        return _unique || _annotatedWithUniqueKey
            || isPrimaryKeyProperty()
            || isSequenceProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty();
    }

    protected void setUnique(boolean unique) {
        _unique = unique;
    }

    @Override
    public boolean isIndexed() {
        return _indexed
            || isNameProperty()
            || isIndexableEntityReference();
    }

    protected void setIndexed(boolean indexed) {
        _indexed = indexed;
    }

    protected boolean isIndexableEntityReference() {
        return isEntity() && ((Entity) this).getRoot().isInvariantEntity();
    }

    /**
     * @return the instance reference indicator
     */
    @Override
    public boolean isInstanceReferenceField() {
        return _annotatedWithInstanceReference;
    }

    /**
     * @return the embedded document indicator
     */
    @Override
    public boolean isEmbeddedDocumentField() {
        return _annotatedWithEmbeddedDocument;
    }

    /**
     * @return the file reference indicator
     */
    @Override
    public boolean isFileReferenceField() {
        return _annotatedWithFileReference;
    }

    private boolean isFileReferenceFieldWithoutBlob() {
        if (isFileReferenceField()) {
            if (isStringData()) {
                StringData data = (StringData) this;
                Property blobProperty = data.getBlobProperty();
                return blobProperty == null || blobProperty.isCalculable() || blobProperty.isCalculatedProperty();
            }
        }
        return false;
    }

    /**
     * @return the graphic image indicator
     */
    @Override
    public boolean isGraphicImageField() {
        return _annotatedWithGraphicImage || _annotatedWithImageProperty;
    }

    /**
     * @return the master sequence indicator
     */
    @Override
    public boolean isMasterSequenceField() {
        return _annotatedWithMasterSequence;
    }

    /**
     * @return the URL indicator
     */
    @Override
    public boolean isUniformResourceLocatorField() {
        return _annotatedWithUniformResourceLocator;
    }

    /**
     * @return the variant string indicator
     */
    @Override
    public boolean isVariantStringField() {
        return _annotatedWithVariantString;
    }

    /**
     * @return the transition user field name
     */
    public String getTransitionUserFieldName() {
        return _transitionUserFieldName;
    }

    /**
     * @return the transition user field
     */
    public Field getTransitionUserField() {
        return _transitionUserField;
    }

    /**
     * @return the transition user entity reference
     */
    public Entity getTransitionUserEntity() {
        return _transitionUserEntity;
    }

    /**
     * @return the transition date/time field name
     */
    public String getTransitionDateTimeFieldName() {
        return _transitionDateTimeFieldName;
    }

    /**
     * @return the transition date/time field
     */
    public Field getTransitionDateTimeField() {
        return _transitionDateTimeField;
    }

    /**
     * @return the transition date/time property
     */
    public Property getTransitionDateTimeProperty() {
        return _transitionDateTimeProperty;
    }

    /**
     * @return the linked field name
     */
    @Override
    public String getLinkedFieldName() {
        return _linkedFieldName;
    }

    /**
     * @return the linked field
     */
    @Override
    public Field getLinkedField() {
        return _linkedField;
    }

    /**
     * @return the linked property
     */
    @Override
    public Property getLinkedProperty() {
        if (_linkedProperty == null && _linkedField != null && _linkedEntity != null) {
            _linkedProperty = XS1.getProperty(_linkedField, _linkedEntity, true);
        }
        return _linkedProperty;
    }

    /**
     * @return the linked column name
     */
    @Override
    public String getLinkedColumnName() {
        return _linkedColumnName;
    }

    /**
     * @return the linked column operator
     */
    @Override
    public StandardRelationalOp getLinkedColumnOperator() {
        return _linkedColumnOperator;
    }

    /**
     * @return the master-heading snippet file name
     */
    public String getMasterHeadingSnippetFileName() {
        return _masterHeadingSnippetFileName;
    }

    protected void setMasterHeadingSnippetFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            _masterHeadingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _masterHeadingSnippetFileName = fileName;
        } else if (isLoggableProperty()) {
            logger.error(getName() + " master heading snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading-table snippet file name
     */
    public String getReadingTableSnippetFileName() {
        return _readingTableSnippetFileName;
    }

    protected void setReadingTableSnippetFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            _readingTableSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableSnippetFileName = fileName;
        } else if (isLoggableProperty()) {
            logger.error(getName() + " reading table snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing-table snippet file name
     */
    public String getWritingTableSnippetFileName() {
        return _writingTableSnippetFileName;
    }

    protected void setWritingTableSnippetFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            _writingTableSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableSnippetFileName = fileName;
        } else if (isLoggableProperty()) {
            logger.error(getName() + " writing table snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading-detail snippet file name
     */
    public String getReadingDetailSnippetFileName() {
        return _readingDetailSnippetFileName;
    }

    protected void setReadingDetailSnippetFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            _readingDetailSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingDetailSnippetFileName = fileName;
        } else if (isLoggableProperty()) {
            logger.error(getName() + " reading detail snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing-detail snippet file name
     */
    public String getWritingDetailSnippetFileName() {
        return _writingDetailSnippetFileName;
    }

    protected void setWritingDetailSnippetFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            _writingDetailSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingDetailSnippetFileName = fileName;
        } else if (isLoggableProperty()) {
            logger.error(getName() + " writing detail snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the processing console snippet file name
     */
    public String getProcessingConsoleSnippetFileName() {
        return _processingConsoleSnippetFileName;
    }

    protected void setProcessingConsoleSnippetFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            _processingConsoleSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _processingConsoleSnippetFileName = fileName;
        } else if (isParameter()) {
            logger.error(getName() + " processing console snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the missing value graphic image name of a property
     */
    public String getMissingValueGraphicImageName() {
        if (isProperty() && _nullable) {
            if (_missingValueGraphicImageName != null) {
                return _missingValueGraphicImageName;
            }
            return TLC.getProject().getMissingValueGraphicImageName();
        }
        return null;
    }

    /**
     * El método setMissingValueGraphicImageName se utiliza para establecer el nombre de imagen gráfica de valor requerido no especificado asociada a
     * la propiedad. La imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param name nombre de imagen gráfica de valor requerido no especificado asociada a la propiedad
     */
    public void setMissingValueGraphicImageName(String name) {
        _missingValueGraphicImageName = fairGraphicImageName(name);
    }

    public boolean isMissingValueGraphicImageNameFontAwesomeClass() {
        return isFontAwesomeClass(getMissingValueGraphicImageName());
    }

    /**
     * @return the null value graphic image name of a property
     */
    public String getNullValueGraphicImageName() {
        if (isProperty() && _nullable) {
            if (_nullValueGraphicImageName != null) {
                return _nullValueGraphicImageName;
            }
            return TLC.getProject().getNullValueGraphicImageName();
        }
        return null;
    }

    /**
     * El método setNullValueGraphicImageName se utiliza para establecer el nombre de imagen gráfica de valor nulo asociada a la propiedad. La imagen
     * de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param name nombre de imagen gráfica de valor nulo asociada a la propiedad
     */
    public void setNullValueGraphicImageName(String name) {
        _nullValueGraphicImageName = fairGraphicImageName(name);
    }

    public boolean isNullValueGraphicImageNameFontAwesomeClass() {
        return isFontAwesomeClass(getNullValueGraphicImageName());
    }

    /**
     * El método setNullValueGraphicImageExpression se utiliza para establecer la expresión que determina el nombre de la imagen de valor nulo
     * asociada a la propiedad. El nombre de la imagen debe ser establecido previamente mediante el método setNullValueGraphicImageName. La imagen de
     * la propiedad se utiliza para resaltar sua valores nulos en las vistas (páginas) de consulta y registro.
     */
    @Override
    public void setNullValueGraphicImageExpression() {
        String name = getNullValueGraphicImageName();
        if (name != null && _graphicImageNameExpression == null) {
            CharacterExpression expression = isNull().then(name);
            _nullValueGraphicImageNameExpression = true;
            if (isFontAwesomeClass(name)) {
                setGraphicImageFontAwesomeClassNameExpression(expression);
            } else {
                setGraphicImageNameExpression(expression);
            }
            setNullValueGraphicImageTooltip(name, ENGLISH);
            setNullValueGraphicImageTooltip(name, SPANISH);
        }
    }

    public boolean isNullValueGraphicImageNameExpression() {
        return _nullValueGraphicImageNameExpression;
    }

    private void setNullValueGraphicImageTooltip(String name, Locale locale) {
        String tooltip = Bundle.getTrimmedToNullString("BundleParametros.null.value.graphic.image.tooltip", locale);
        setLocalizedGraphicImageTooltip(locale, name, tooltip);
    }

    /**
     * @return the unnecessary value graphic image name of a property
     */
    public String getUnnecessaryValueGraphicImageName() {
        if (isProperty()) {
            if (_unnecessaryValueGraphicImageName != null) {
                return _unnecessaryValueGraphicImageName;
            }
            return TLC.getProject().getUnnecessaryValueGraphicImageName();
        }
        return null;
    }

    /**
     * El método setUnnecessaryValueGraphicImageName se utiliza para establecer el nombre de imagen gráfica de valor innecesario (no aplicable)
     * asociada a la propiedad. La imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param name nombre de imagen gráfica de valor innecesario asociada a la propiedad
     */
    public void setUnnecessaryValueGraphicImageName(String name) {
        _unnecessaryValueGraphicImageName = fairGraphicImageName(name);
    }

    public boolean isUnnecessaryValueGraphicImageNameFontAwesomeClass() {
        return isFontAwesomeClass(getUnnecessaryValueGraphicImageName());
    }

    /**
     * @return the graphic image name expression of a property
     */
    @Override
    public CharacterExpression getGraphicImageNameExpression() {
        return _graphicImageNameExpression;
    }

    /**
     * El método setGraphicImageNameExpression se utiliza para establecer la expresión que determina el nombre de la imagen asociada a la propiedad.
     * La imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param expression expresión que determina el nombre de la imagen asociada a la propiedad
     */
    public void setGraphicImageNameExpression(CharacterExpression expression) {
        _graphicImageStyleClassNameExpression = false;
        setGraphicImageNameCharacterExpression(expression);
    }

    /**
     * El método setGraphicImageFontAwesomeClassNameExpression se utiliza para establecer la expresión que determina el nombre de la clase Font
     * Awesome de la imagen asociada a la propiedad. La imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y
     * registro.
     *
     * @param expression expresión que determina el nombre de la clase Font Awesome de la imagen asociada a la propiedad
     */
    public void setGraphicImageFontAwesomeClassNameExpression(CharacterExpression expression) {
        setGraphicImageStyleClassNameExpression(expression);
    }

    /**
     * El método setGraphicImageStyleClassNameExpression se utiliza para establecer la expresión que determina el nombre de la clase de estilo de la
     * imagen asociada a la propiedad. La imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param expression expresión que determina el nombre de la clase de estilo de la imagen asociada a la propiedad
     */
    public void setGraphicImageStyleClassNameExpression(CharacterExpression expression) {
        _graphicImageStyleClassNameExpression = true;
        setGraphicImageNameCharacterExpression(expression);
    }

    private void setGraphicImageNameCharacterExpression(CharacterExpression expression) {
        String message = "failed to set graphic image name expression of " + getFullName();
        if (isParameter()) {
            message += "; graphic image name expressions are not valid for parameters; setting ignored";
            logger.warn(message);
            Project.increaseParserWarningCount();
        } else if (expression == null) {
            if (isLoggable()) {
                message += "; supplied expression is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else {
            _graphicImageNameExpression = expression;
        }
    }

    /**
     * @return true if the graphic image name expression returns a Font Awesome class name; false if it returns an actual image name
     */
    public boolean isGraphicImageFontAwesomeClassNameExpression() {
        return isGraphicImageStyleClassNameExpression();
    }

    /**
     * @return true if the graphic image name expression returns a style class name; false if it returns an actual image name
     */
    public boolean isGraphicImageStyleClassNameExpression() {
        return _graphicImageStyleClassNameExpression;
    }

    /**
     * @param graphicImageName graphic image name
     * @return the default tooltip of the graphic image
     */
    public String getDefaultGraphicImageTooltip(String graphicImageName) {
        return getLocalizedGraphicImageTooltip(null, graphicImageName);
    }

    /**
     * El método setDefaultGraphicImageTooltip se utiliza para establecer la descripción emergente (tooltip) de la imagen gráfica de la propiedad que
     * se almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción. La imagen de la
     * propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param graphicImageName nombre de la imagen gráfica
     * @param defaultTooltip una o más oraciones que describen muy brevemente el significado de la imagen gráfica de la propiedad
     */
    public void setDefaultGraphicImageTooltip(String graphicImageName, String defaultTooltip) {
        setLocalizedGraphicImageTooltip(null, graphicImageName, defaultTooltip);
    }

    /**
     * @return the default tooltip map of the property
     */
    public Map<String, String> getDefaultGraphicImageTooltipMap() {
        return getLocalizedGraphicImageTooltipMap(null);
    }

    /**
     * @param locale the locale for the tooltip
     * @param graphicImageName graphic image name
     * @return the localized tooltip of the graphic image
     */
    public String getLocalizedGraphicImageTooltip(Locale locale, String graphicImageName) {
        if (StringUtils.isBlank(graphicImageName)) {
            return null;
        }
        Locale l = localeReadingKey(locale);
        String g = StrUtils.escapeBundleKey(graphicImageName);
        Map<String, String> map = _localizedGraphicImageTooltip.get(l);
        return map == null ? null : map.get(g);
    }

    /**
     * El método setLocalizedGraphicImageTooltip se utiliza para establecer la descripción emergente (tooltip) de la imagen gráfica de la propiedad
     * que se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el
     * usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción. La
     * imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param locale configuración regional
     * @param graphicImageName nombre de la imagen gráfica
     * @param localizedTooltip una o más oraciones que describen muy brevemente el significado de la imagen gráfica de la propiedad
     */
    public void setLocalizedGraphicImageTooltip(Locale locale, String graphicImageName, String localizedTooltip) {
        if (StringUtils.isBlank(graphicImageName)) {
            return;
        }
        Locale l = localeWritingKey(locale);
        String g = StrUtils.escapeBundleKey(graphicImageName);
        String t = StringUtils.trimToNull(localizedTooltip);
        Map<String, String> map = _localizedGraphicImageTooltip.get(l);
        if (map == null) {
            if (t == null) {
                return;
            }
            map = new LinkedHashMap<>();
            _localizedGraphicImageTooltip.put(l, map);
        }
        if (t == null) {
            map.remove(g);
        } else {
            map.put(g, t);
        }
    }

    /**
     * @param locale the locale for the tooltip map
     * @return the localized tooltip map of the property
     */
    public Map<String, String> getLocalizedGraphicImageTooltipMap(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedGraphicImageTooltip.get(l);
    }

    /**
     * @return the sequence start
     */
//  @Override
    public long getSequencePropertyStart() {
        return _sequencePropertyStart;
    }

    /**
     * Sets the sequence start value
     *
     * @param value the sequence start value to set
     */
    public void setSequencePropertyStart(long value) {
        boolean log = depth() == 0;
        if (isSequenceProperty()) {
            _sequencePropertyStart = Math.min(Long.MAX_VALUE, Math.max(1, value));
            if (log && value != _sequencePropertyStart) {
                logger.warn(getFullName() + ".setSequencePropertyStart argument is out of range; set to " + _sequencePropertyStart);
                Project.increaseParserWarningCount();
            }

        } else if (log) {
            logger.warn("method setSequencePropertyStart is not valid for " + getFullName() + "; setting ignored");
            Project.increaseParserWarningCount();
        }
    }

    /**
     * @return the sequence stop
     */
//  @Override
    public long getSequencePropertyStop() {
        return _sequencePropertyStop;
    }

    /**
     * Sets the sequence stop value
     *
     * @param value the sequence stop value to set
     */
    public void setSequencePropertyStop(long value) {
        boolean log = depth() == 0;
        if (isSequenceProperty()) {
            _sequencePropertyStop = Math.min(Long.MAX_VALUE, Math.max(1, value));
            if (log && value != _sequencePropertyStop) {
                logger.warn(getFullName() + ".setSequencePropertyStop argument is out of range; set to " + _sequencePropertyStop);
                Project.increaseParserWarningCount();
            }
        } else if (log) {
            logger.warn("method setSequencePropertyStop is not valid for " + getFullName() + "; setting ignored");
            Project.increaseParserWarningCount();
        }
    }

    /**
     * @return the sequence step
     */
//  @Override
    public long getSequencePropertyStep() {
        return _sequencePropertyStep;
    }

    /**
     * Sets the sequence step value
     *
     * @param value the sequence step value to set
     */
    public void setSequencePropertyStep(long value) {
        boolean log = depth() == 0;
        if (isSequenceProperty()) {
            _sequencePropertyStep = Math.min(Long.MAX_VALUE, Math.max(1, value));
            if (log && value != _sequencePropertyStep) {
                logger.warn(getFullName() + ".setSequencePropertyStep argument is out of range; set to " + _sequencePropertyStep);
                Project.increaseParserWarningCount();
            }
        } else if (log) {
            logger.warn("method setSequencePropertyStep is not valid for " + getFullName() + "; setting ignored");
            Project.increaseParserWarningCount();
        }
    }

    /**
     * @return the sequence enabled indicator
     */
//  @Override
    public boolean isSequencePropertyEnabled() {
        return _annotatedWithSequenceProperty && validStartStopStep(_sequencePropertyStart, _sequencePropertyStop, _sequencePropertyStep);
    }

    /**
     * @return the sequence data generation disabled indicator
     */
    @Override
    public boolean isSequencePropertyDataGenDisabled() {
        return !validStartStopStep(_sequencePropertyStart, _dataGenSeriesStop, _sequencePropertyStep)
            || _sequencePropertyStart > Constants.MAX_SERIES_START || _sequencePropertyStep > Constants.MAX_SERIES_STEP;
    }

    boolean validStartStopStep(long start, long stop, long step) {
        return start < stop && step <= (stop - start);
    }

    /**
     * @return the data generation type
     */
//  @Override
    public DataGenType getDataGenType() {
        return _dataGenType;
    }

//  @Override
    public boolean isDataGenTypeSpecified() {
        return !DataGenType.UNSPECIFIED.equals(_dataGenType);
    }

    /**
     * @return the character Lorem Ipsum indicator
     */
//  @Override
    public Boolean isLoremIpsum() {
        return _loremIpsum;
    }

    /**
     * @return the series start
     */
//  @Override
    public int getDataGenSeriesStart() {
        return _dataGenSeriesStart;
    }

    /**
     * @return the series stop
     */
//  @Override
    public int getDataGenSeriesStop() {
        return _dataGenSeriesStop;
    }

    /**
     * @return the series step
     */
//  @Override
    public int getDataGenSeriesStep() {
        return _dataGenSeriesStep;
    }

    /**
     * @return the entity data generation enabled indicator
     */
//  @Override
    public boolean isDataGenSeriesEnabled() {
        return _annotatedWithDataGen && validStartStopStep(_dataGenSeriesStart, _dataGenSeriesStop, _dataGenSeriesStep);
    }

    /**
     * @return the data generation user-defined function name
     */
//  @Override
    public String getDataGenFunction() {
        return _dataGenFunction;
    }

    /**
     * Sets the data generation user-defined function
     *
     * @param function the data generation user-defined function to set
     */
//  @Override
    public void setDataGenFunction(String function) {
        String f = StringUtils.trimToNull(function);
        if (f != null) {
            _dataGenFunction = f.equalsIgnoreCase("null") ? null : f;
        } else if (isDataGenTypeSpecified()) {
            _dataGenFunction = null;
        }
    }

    /**
     * @return the data nullable
     */
//  @Override
    public int getDataGenNullable() {
        return _dataGenNullable;
    }

    /**
     * @return the data trueable percentage
     */
//  @Override
    public int getDataGenTrueable() {
        return _dataGenTrueable;
    }

    /**
     * @return the data generation pattern
     */
//  @Override
    public String getDataGenPattern() {
        return _dataGenPattern;
    }

    /**
     * @return the data generation prefix
     */
//  @Override
    public String getDataGenPrefix() {
        return _dataGenPrefix;
    }

    /**
     * @return the data generation suffix
     */
//  @Override
    public String getDataGenSuffix() {
        return _dataGenSuffix;
    }

    /**
     * @return the data min
     */
//  @Override
    public String getDataGenMin() {
        return _dataGenMin;
    }

    /**
     * @return the data min temporal addend
     */
//  @Override
    public TemporalAddend getDataGenMinTemporalAddend() {
        return _dataGenMinTemporalAddend;
    }

    /**
     * @return the data min value
     */
//  @Override
    public Object getDataGenMinValue() {
        return _dataGenMinValue;
    }

    /**
     * @return the data max
     */
//  @Override
    public String getDataGenMax() {
        return _dataGenMax;
    }

    /**
     * @return the data max temporal addend
     */
//  @Override
    public TemporalAddend getDataGenMaxTemporalAddend() {
        return _dataGenMaxTemporalAddend;
    }

    /**
     * @return the data max value
     */
//  @Override
    public Object getDataGenMaxValue() {
        return _dataGenMaxValue;
    }

    /**
     * @return the data gen temporal interval
     */
//  @Override
    public DataGenTemporalInterval getDataGenTemporalInterval() {
        return _dataGenTemporalInterval;
    }

    /**
     * @return the data gen numeric action
     */
//  @Override
    public DataGenNumericAction getDataGenNumericAction() {
        return _dataGenNumericAction;
    }

    /**
     * @return the data factor
     */
//  @Override
    public Object getDataGenFactor() {
        return _dataGenFactor == null && isNumericPrimitive() ? 1 : _dataGenFactor;
    }

    /**
     * @return the rendering filter's read-only indicator
     */
    @Override
    public boolean isRenderingFilterReadOnly() {
        return _renderingFilterReadOnly;
    }

    /**
     * @return the rendering filter
     */
    @Override
    public BooleanExpression getRenderingFilter() {
        return _renderingFilter;
    }

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de propiedades en vistas (páginas) de consulta y/o registro,
     * y de parámetros en vistas (páginas) de ejecución de operaciones de negocio. En las instancias de la entidad que no cumplen con los criterios
     * del filtro, la propiedad o parámetro será invisible.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro para lectura y escritura.
     */
    @Override
    public void setRenderingFilter(BooleanExpression renderingFilter) {
        setRenderingFilter(renderingFilter, false);
    }

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de propiedades en vistas (páginas) de consulta y/o registro,
     * y de parámetros en vistas (páginas) de ejecución de operaciones de negocio. En las instancias de la entidad que no cumplen con los criterios
     * del filtro, la propiedad o parámetro será invisible.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro
     * @param readOnly true, si el filtro solo aplica para lectura; false, si también aplica para escritura (al agregar o editar).
     */
    @Override
    public void setRenderingFilter(BooleanExpression renderingFilter, boolean readOnly) {
        boolean log = isParameter() || isLoggableProperty();
        String message = "failed to set rendering filter of " + getFullName();
        if (renderingFilter == null) {
            if (log) {
                message += "; supplied expression is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (renderingFilter instanceof BooleanPrimitive) {
            _renderingFilter = renderingFilter.isTrue();
        } else {
            _renderingFilter = renderingFilter;
        }
        _renderingFilterReadOnly = readOnly;
    }

    /**
     * @return the requiring filter
     */
    @Override
    public BooleanExpression getRequiringFilter() {
        return _requiringFilter;
    }

    /**
     * El método setRequiringFilter se utiliza para establecer el filtro de obligatoriedad de propiedades en vistas (páginas) de registro, y de
     * parámetros en vistas (páginas) de ejecución de operaciones de negocio. Solo si se cumplen los criterios del filtro, el valor de la propiedad o
     * el parámetro será obligatoriamente requerido en las vistas (páginas) de registro o ejecución de operaciones de negocio.
     *
     * @param requiringFilter expresión booleana que se utiliza como filtro
     */
    @Override
    public void setRequiringFilter(BooleanExpression requiringFilter) {
        boolean log = isParameter() || isLoggableProperty();
        String message = "failed to set requiring filter of " + getFullName();
        if (requiringFilter == null) {
            if (log) {
                message += "; supplied expression is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (requiringFilter instanceof BooleanPrimitive) {
            _requiringFilter = requiringFilter.isTrue();
        } else {
            _requiringFilter = requiringFilter;
        }
    }

    /**
     * @return the modifying filter
     */
    @Override
    public BooleanExpression getModifyingFilter() {
        return _modifyingFilter;
    }

    /**
     * El método setModifyingFilter se utiliza para establecer el filtro de modificación de propiedades en vistas (páginas) de registro, y de
     * parámetros en vistas (páginas) de ejecución de operaciones de negocio. Solo si se cumplen los criterios del filtro, el valor de la propiedad o
     * el parámetro podrá ser modificado en las vistas (páginas) de registro o ejecución de operaciones de negocio.
     *
     * @param modifyingFilter expresión booleana que se utiliza como filtro
     */
    @Override
    public void setModifyingFilter(BooleanExpression modifyingFilter) {
        boolean log = isParameter() || isLoggableProperty();
        String message = "failed to set modifying filter of " + getFullName();
        if (modifyingFilter == null) {
            if (log) {
                message += "; supplied expression is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (modifyingFilter instanceof BooleanPrimitive) {
            _modifyingFilter = modifyingFilter.isTrue();
        } else {
            _modifyingFilter = modifyingFilter;
        }
    }

    /**
     * @return the nullifying filter
     */
    @Override
    public BooleanExpression getNullifyingFilter() {
        return _nullifyingFilter;
    }

    /**
     * El método setNullifyingFilter se utiliza para establecer el filtro de anulación de propiedades en vistas (páginas) de registro, y de parámetros
     * en vistas (páginas) de ejecución de operaciones de negocio. Solo si se cumplen los criterios del filtro, el valor de la propiedad o el
     * parámetro será anulado en las vistas (páginas) de registro o ejecución de operaciones de negocio.
     *
     * @param nullifyingFilter expresión booleana que se utiliza como filtro
     */
    @Override
    public void setNullifyingFilter(BooleanExpression nullifyingFilter) {
        boolean log = isParameter() || isLoggableProperty();
        String message = "failed to set nullifying filter of " + getFullName();
        if (nullifyingFilter == null) {
            if (log) {
                message += "; supplied expression is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (nullifyingFilter instanceof BooleanPrimitive) {
            _nullifyingFilter = nullifyingFilter.isTrue();
        } else {
            _nullifyingFilter = nullifyingFilter;
        }
    }

    /**
     * @return the enclosing steps list
     */
    @Override
    public List<Step> getEnclosingSteps() {
        List<Step> list = new ArrayList<>();
        if (isProperty()) {
            Entity declaringEntity = getDeclaringEntity();
            List<Step> stepsList = declaringEntity.getStepsList();
            List<StepField> stepFieldsList;
            for (Step step : stepsList) {
                stepFieldsList = step.getStepFieldsList();
                for (StepField stepField : stepFieldsList) {
                    Property sfp = stepField.getProperty();
                    if (sfp != null) {
                        if (sfp.equals(this)) {
                            list.add(step);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public boolean isEnclosedInAtLeastOneStep() {
        if (isProperty()) {
            Entity declaringEntity = getDeclaringEntity();
            List<Step> stepsList = declaringEntity.getStepsList();
            List<StepField> stepFieldsList;
            for (Step step : stepsList) {
                stepFieldsList = step.getStepFieldsList();
                for (StepField stepField : stepFieldsList) {
                    Property sfp = stepField.getProperty();
                    if (sfp != null) {
                        if (sfp.equals(this)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return the enclosing tabs list
     */
    @Override
    public List<Tab> getEnclosingTabs() {
        List<Tab> list = new ArrayList<>();
        if (isProperty()) {
            Entity declaringEntity = getDeclaringEntity();
            List<Tab> tabsList = declaringEntity.getTabsList();
            List<TabField> tabFieldsList;
            for (Tab tab : tabsList) {
                tabFieldsList = tab.getTabFieldsList();
                for (TabField tabField : tabFieldsList) {
                    Property tfp = tabField.getProperty();
                    if (tfp != null) {
                        if (tfp.equals(this)) {
                            list.add(tab);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public boolean isEnclosedInAtLeastOneTab() {
        if (isProperty()) {
            Entity declaringEntity = getDeclaringEntity();
            List<Tab> tabsList = declaringEntity.getTabsList();
            List<TabField> tabFieldsList;
            for (Tab tab : tabsList) {
                tabFieldsList = tab.getTabFieldsList();
                for (TabField tabField : tabFieldsList) {
                    Property tfp = tabField.getProperty();
                    if (tfp != null) {
                        if (tfp.equals(this)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return the BaseField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithBaseField() {
        return _annotatedWithBaseField;
    }

    /**
     * @return PrimaryKey annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithPrimaryKey() {
        return _annotatedWithPrimaryKey;
    }

    /**
     * @return SequenceProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithSequenceProperty() {
        return _annotatedWithSequenceProperty;
    }

    /**
     * @return VersionProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithVersionProperty() {
        return _annotatedWithVersionProperty;
    }

    /**
     * @return NameProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithNameProperty() {
        return _annotatedWithNameProperty;
    }

    /**
     * @return DescriptionProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithDescriptionProperty() {
        return _annotatedWithDescriptionProperty;
    }

    /**
     * @return ImageProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithImageProperty() {
        return _annotatedWithImageProperty;
    }

    /**
     * @return InactiveIndicator annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithInactiveIndicator() {
        return _annotatedWithInactiveIndicator;
    }

    /**
     * @return UrlProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithUrlProperty() {
        return _annotatedWithUrlProperty;
    }

    /**
     * @return ParentProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithParentProperty() {
        return _annotatedWithParentProperty;
    }

    /**
     * @return OwnerProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithOwnerProperty() {
        return _annotatedWithOwnerProperty;
    }

    /**
     * @return UserProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithUserProperty() {
        return _annotatedWithUserProperty;
    }

    /**
     * @return SegmentProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithSegmentProperty() {
        return _annotatedWithSegmentProperty;
    }

    /**
     * @return UniqueKey annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithUniqueKey() {
        return _annotatedWithUniqueKey;
    }

    /**
     * @return BusinessKey annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithBusinessKey() {
        return _annotatedWithBusinessKey;
    }

    /**
     * @return DiscriminatorColumn annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithDiscriminatorColumn() {
        return _annotatedWithDiscriminatorColumn;
    }

    /**
     * @return StateProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithStateProperty() {
        return _annotatedWithStateProperty;
    }

    /**
     * @return the ColumnField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithColumnField() {
        return _annotatedWithColumnField;
    }

    /**
     * @return the BigDecimalField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithBigDecimalField() {
        return _annotatedWithBigDecimalField;
    }

    /**
     * @return the BigIntegerField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithBigIntegerField() {
        return _annotatedWithBigIntegerField;
    }

    /**
     * @return the BooleanField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithBooleanField() {
        return _annotatedWithBooleanField;
    }

    /**
     * @return the NumericField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithNumericField() {
        return _annotatedWithNumericField;
    }

    /**
     * @return the StringField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithStringField() {
        return _annotatedWithStringField;
    }

    /**
     * @return the DateField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithDateField() {
        return _annotatedWithDateField;
    }

    /**
     * @return the TimeField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithTimeField() {
        return _annotatedWithTimeField;
    }

    /**
     * @return the TimestampField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithTimestampField() {
        return _annotatedWithTimestampField;
    }

    /**
     * @return the ParameterField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithParameterField() {
        return _annotatedWithParameterField;
    }

    /**
     * @return the PropertyField annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithPropertyField() {
        return _annotatedWithPropertyField;
    }

    /**
     * @return the PropertyAggregation annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithPropertyAggregation() {
        return _annotatedWithPropertyAggregation;
    }

    /**
     * @return the InstanceReference annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithInstanceReference() {
        return _annotatedWithInstanceReference;
    }

    /**
     * @return the EmbeddedDocument annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithEmbeddedDocument() {
        return _annotatedWithEmbeddedDocument;
    }

    /**
     * @return the FileReference annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithFileReference() {
        return _annotatedWithFileReference;
    }

    /**
     * @return the UniformResourceLocator annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithUniformResourceLocator() {
        return _annotatedWithUniformResourceLocator;
    }

    /**
     * @return the VariantString annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithVariantString() {
        return _annotatedWithVariantString;
    }

    /**
     * @return the DataGen annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithDataGen() {
        return _annotatedWithDataGen;
    }
    // </editor-fold>

    @Override
    public boolean finish() {
        boolean ok = super.finish();
        if (ok) {
            checkColumnFieldElements();
        }
        return ok;
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            if (isParameter()) {
                annotateBigDecimalField(field);
                annotateBigIntegerField(field);
                annotateBooleanField(field);
                annotateNumericField(field);
                annotateStringField(field);
                annotateDateField(field);
                annotateTimeField(field);
                annotateTimestampField(field);
                annotateParameterField(field); // must be executed before annotateFileReference
                annotateFileReference(field);
                annotateInstanceReference(field);
            }
            if (isProperty()) {
                annotateKeyProperties(field);
                annotateBaseField(field);
                annotateColumnField(field);
                annotateBigDecimalField(field);
                annotateBigIntegerField(field);
                annotateBooleanField(field);
                annotateNumericField(field);
                annotateStringField(field);
                annotateDateField(field);
                annotateTimeField(field);
                annotateTimestampField(field);
                annotateEmbeddedDocument(field);
                annotateFileReference(field);
                annotateGraphicImage(field);
                annotateMasterSequenceProperty(field);
                annotateUniformResourceLocator(field);
                annotateVariantString(field);
                annotatePropertyField(field);
                annotatePropertyAggregation(field);
                annotateDataGen(field);
            }
        }
    }

    private void annotateKeyProperties(Field field) {
        annotatePrimaryKey(field);
        annotateSequenceProperty(field);
        annotateVersionProperty(field);
        annotateNameProperty(field);
        annotateDescriptionProperty(field);
        annotateImageProperty(field);
        annotateInactiveIndicator(field);
        annotateUrlProperty(field);
        annotateParentProperty(field);
        annotateOwnerProperty(field);
        annotateUserProperty(field);
        annotateSegmentProperty(field);
        annotateUniqueKey(field);
        annotateBusinessKey(field);
        annotateDiscriminatorColumn(field);
        annotateStateProperty(field);
        if (!casting(field)) {
            initializeKeyPropertyAnnotations();
        }
    }

    private boolean casting(Field field) {
        return field.isAnnotationPresent(CastingField.class);
    }

    private void annotatePrimaryKey(Field field) {
        PrimaryKey annotation = field.getAnnotation(PrimaryKey.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = PrimaryKey.class;
            boolean log = depth() == 0;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.PRIMARY_KEY);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithPrimaryKey = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateSequenceProperty(Field field) {
        SequenceProperty annotation = field.getAnnotation(SequenceProperty.class);
        if (annotation != null) {
            Class<? extends Annotation> annotationClass = SequenceProperty.class;
            boolean log = depth() == 0;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.SEQUENCE);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithSequenceProperty = true;
                    _sequencePropertyStart = Math.min(Long.MAX_VALUE, Math.max(1, annotation.start()));
                    _sequencePropertyStop = Math.min(Long.MAX_VALUE, Math.max(1, annotation.stop()));
                    _sequencePropertyStep = Math.min(Long.MAX_VALUE, Math.max(1, annotation.step()));
                    if (log && !isSequencePropertyEnabled()) {
                        String message = getFullName() + " has invalid values for elements start/stop/step of its SequenceProperty annotation: "
                            + _sequencePropertyStart + "/" + _sequencePropertyStop + "/" + _sequencePropertyStep;
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateVersionProperty(Field field) {
        VersionProperty annotation = field.getAnnotation(VersionProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = VersionProperty.class;
            boolean log = depth() == 0;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.VERSION);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithVersionProperty = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateNameProperty(Field field) {
        NameProperty annotation = field.getAnnotation(NameProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = NameProperty.class;
            boolean log = depth() == 0;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.NAME);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithNameProperty = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateDescriptionProperty(Field field) {
        DescriptionProperty annotation = field.getAnnotation(DescriptionProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = DescriptionProperty.class;
            boolean log = depth() == 0;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.DESCRIPTION);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithDescriptionProperty = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateImageProperty(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = ImageProperty.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.IMAGE);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithImageProperty = true;
                BinaryData data = (BinaryData) this;
                ImageProperty annotation = field.getAnnotation(ImageProperty.class);
                /**/
                // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
                /*
                int displayWidth = specified(annotation.displayWidth(), data.getDisplayWidth());
                int displayHeight = specified(annotation.displayHeight(), data.getDisplayHeight());
                boolean resizable = annotation.resizable().toBoolean(data.isResizable());
                if (displayWidth < 0) {
                    displayWidth = 0;
                } else if (displayWidth < 1 || displayWidth > Constants.MAX_DISPLAY_WIDTH) {
                    displayWidth = 0;
                    if (log) {
                        logger.error(fieldName + " has an invalid display width");
                        Project.increaseParserErrorCount();
                    }
                }
                if (displayHeight < 0) {
                    displayHeight = 0;
                } else if (displayHeight < 1 || displayHeight > Constants.MAX_DISPLAY_HEIGHT) {
                    displayHeight = 0;
                    if (log) {
                        logger.error(fieldName + " has an invalid display height");
                        Project.increaseParserErrorCount();
                    }
                }
                if (displayWidth == 0 && displayHeight == 0) {
                    displayWidth = Constants.DEFAULT_IMAGE_WIDTH;
                    displayHeight = Constants.DEFAULT_IMAGE_HEIGHT;
                    resizable = true;
                } else if (displayWidth == 0) {
                    displayWidth = displayHeight;
                    resizable = true;
                } else if (displayHeight == 0) {
                    displayHeight = displayWidth > Constants.MAX_DISPLAY_HEIGHT ? Constants.MAX_DISPLAY_HEIGHT : displayWidth;
                    resizable = true;
                }
                data.setDisplayWidth(displayWidth);
                data.setDisplayHeight(displayHeight);
                data.setResizable(resizable);
                /**/
                // </editor-fold>
                /**/
                AvatarShape avatarShape = specified(AvatarShape.NONE.name(), annotation.avatarShape(), data.getAvatarShape());
                AvatarDefault avatarDefault = specified(AvatarDefault.NONE.name(), annotation.avatarDefault(), data.getAvatarDefault());
                int avatarWidth = greaterThanZero(annotation.avatarWidth(), data.getAvatarWidth());
                int avatarHeight = greaterThanZero(annotation.avatarHeight(), data.getAvatarHeight());
                /**/
                // <editor-fold defaultstate="collapsed" desc="until 23/01/2024">
                /*
                int[] displayWidth = Arrays.copyOf(annotation.displayWidth(), COMPONENT_DISPLAY_SIZE.length);
                int[] displayHeight = Arrays.copyOf(annotation.displayHeight(), COMPONENT_DISPLAY_SIZE.length);
                /**/
                // </editor-fold>
                /**/
                int[] displayWidth = specified(annotation.displayWidth(), data.getDisplayWidth());
                int[] displayHeight = specified(annotation.displayHeight(), data.getDisplayHeight());
                boolean resizable = annotation.resizable().toBoolean(data.isResizable());
                /**/
                WHR whrL = new WHR(fieldName, log, L, displayWidth[L], displayHeight[L], MIN_IMG_W, MIN_IMG_H, DEF_IMG_W[L], DEF_IMG_H[L]);
                WHR whrM = new WHR(fieldName, log, M, displayWidth[M], displayHeight[M], MIN_IMG_W, MIN_IMG_H, DEF_IMG_W[M], DEF_IMG_H[M]);
                WHR whrS = new WHR(fieldName, log, S, displayWidth[S], displayHeight[S], MIN_IMG_W, MIN_IMG_H, DEF_IMG_W[S], DEF_IMG_H[S]);
                /**/
                data.setAvatarShape(avatarShape);
                data.setAvatarDefault(avatarDefault);
                data.setAvatarWidth(avatarWidth);
                data.setAvatarHeight(avatarHeight);
                data.setLargeDisplayWidth(whrL.displayWidth);
                data.setLargeDisplayHeight(whrL.displayHeight);
                data.setMediumDisplayWidth(whrM.displayWidth);
                data.setMediumDisplayHeight(whrM.displayHeight);
                data.setSmallDisplayWidth(whrS.displayWidth);
                data.setSmallDisplayHeight(whrS.displayHeight);
                data.setResizable(resizable || whrL.resizable || whrM.resizable || whrS.resizable);
                /**/
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateInactiveIndicator(Field field) {
        InactiveIndicator annotation = field.getAnnotation(InactiveIndicator.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = InactiveIndicator.class;
            boolean log = depth() == 0;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.INACTIVE_INDICATOR);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithInactiveIndicator = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateUrlProperty(Field field) {
        Class<? extends Annotation> annotationClass = UrlProperty.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.URL);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithUrlProperty = true;
                StringData data = (StringData) this;
                UrlProperty annotation = field.getAnnotation(UrlProperty.class);
                UrlType urlType = specified(annotation.urlType(), data.getUrlType());
                DisplayMode urlDisplayMode = specified(annotation.urlDisplayMode(), data.getUrlDisplayMode());
                UrlDisplayType urlDisplayType = specified(annotation.urlDisplayType(), data.getUrlDisplayType());
                String[] sourceURLs = null;
                String searchURL = null;
                Pattern pattern = data.getPattern();
                boolean encoding = annotation.encoding().toBoolean(data.isEncodingEnabled());
                if (UrlType.EXTERNAL.equals(urlType)) {
                    sourceURLs = specified(annotation.sourceURLs(), data.getSourceURLs());
                    searchURL = specified(annotation.searchURL(), data.getSearchURL());
                    if (pattern == null) {
                        pattern = Pattern.compile(Constants.URL_REGEX);
                    }
                }
                data.setUrlType(urlType);
                data.setUrlDisplayMode(urlDisplayMode);
                data.setUrlDisplayType(urlDisplayType);
                data.setSourceURLs(sourceURLs);
                data.setSearchURL(searchURL);
                data.setPattern(pattern);
                data.setEncodingEnabled(encoding);
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateParentProperty(Field field) {
        ParentProperty annotation = field.getAnnotation(ParentProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = ParentProperty.class;
            Class<?> declaringClass = field.getDeclaringClass();
            Class<?>[] validTypes = new Class<?>[]{declaringClass};
            boolean log = depth() == 1;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.PARENT, validTypes);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithParentProperty = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateOwnerProperty(Field field) {
        OwnerProperty annotation = field.getAnnotation(OwnerProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = OwnerProperty.class;
            boolean log = depth() == 1;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.OWNER);
            if (aye) {
                Class<?> fieldType = field.getType();
                Class<? extends Entity> userEntityClass = TLC.getProject().getUserEntityClass();
                if (userEntityClass != null && userEntityClass.isAssignableFrom(fieldType)) {
                    Field previous = getDeclaringArtifact().put(annotationClass, field);
                    if (previous == null) {
                        _annotatedWithOwnerProperty = true;
                    } else if (log) {
                        XS1.logDuplicateAnnotation(field, annotationClass, previous);
                    }
                } else if (log) {
                    String message = userEntityClass + " is not assignable from " + fieldType;
                    XS1.logFieldAnnotationErrorMessage(field, annotationClass, message);
                }
            }
        }
    }

    private void annotateUserProperty(Field field) {
        UserProperty annotation = field.getAnnotation(UserProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = UserProperty.class;
            boolean log = depth() == 1;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.USER);
            if (aye) {
                Class<?> fieldType = field.getType();
                Class<? extends Entity> userEntityClass = TLC.getProject().getUserEntityClass();
                if (userEntityClass != null && userEntityClass.isAssignableFrom(fieldType)) {
                    Field previous = getDeclaringArtifact().put(annotationClass, field);
                    if (previous == null) {
                        _annotatedWithUserProperty = true;
                    } else if (log) {
                        XS1.logDuplicateAnnotation(field, annotationClass, previous);
                    }
                } else if (log) {
                    String message = userEntityClass + " is not assignable from " + fieldType;
                    XS1.logFieldAnnotationErrorMessage(field, annotationClass, message);
                }
            }
        }
    }

    private void annotateSegmentProperty(Field field) {
        SegmentProperty annotation = field.getAnnotation(SegmentProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = SegmentProperty.class;
            boolean log = isLoggable(); // since 20210218
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.SEGMENT);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithSegmentProperty = true;
                    _segmentEntityClass = annotation.entityClass(); // since 20210218
                    if (field.isAnnotationPresent(PrimaryKey.class)) { // since 20220715
                        Class<?> declaringClass = field.getDeclaringClass();
                        if (_segmentEntityClass.equals(declaringClass)) {
                            // properly set
                        } else if (_segmentEntityClass.equals(Entity.class)) {
                            _segmentEntityClass = declaringClass;
                        } else {
                            if (log) {
                                String message = getDeclaringEntity() + " is not assignable from " + _segmentEntityClass;
                                XS1.logFieldAnnotationErrorMessage(field, annotationClass, message);
                                String declaringClassSimpleName = declaringClass.getSimpleName();
                                String hint = "remove element entityClass from the SegmentProperty annotation of "
                                    + declaringClassSimpleName + "." + field.getName() + "; "
                                    + "alternatively, set it to either Entity.class or " + declaringClassSimpleName + ".class";
                                TLC.getProject().getParser().info(hint);
                            }
                            _segmentEntityClass = declaringClass;
                        }
                    }
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateUniqueKey(Field field) {
        UniqueKey annotation = field.getAnnotation(UniqueKey.class);
        if (annotation != null && annotation.value()) {
            boolean log = isLoggable(); // since 20210218
            _annotatedWithUniqueKey = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.UNIQUE_KEY);
        }
    }

    private void annotateBusinessKey(Field field) {
        BusinessKey annotation = field.getAnnotation(BusinessKey.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = BusinessKey.class;
            boolean log = depth() == 0;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.BUSINESS_KEY);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithBusinessKey = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateDiscriminatorColumn(Field field) {
        DiscriminatorColumn annotation = field.getAnnotation(DiscriminatorColumn.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = DiscriminatorColumn.class;
            boolean log = isLoggable(); // since 20210218
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.DISCRIMINATOR);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithDiscriminatorColumn = true;
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void annotateStateProperty(Field field) {
        StateProperty annotation = field.getAnnotation(StateProperty.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = StateProperty.class;
            boolean log = depth() == 1;
            boolean aye = XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.STATE);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithStateProperty = true;
                    setTransitionUserEntity(annotation);
                    setTransitionDateTimeProperty(annotation);
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
    }

    private void setTransitionUserEntity(StateProperty annotation) {
        String fieldName = annotation.transitionUser();
        if (StringUtils.isNotBlank(fieldName)) {
            _transitionUserFieldName = fieldName;
            boolean log = isLoggableProperty();
            Entity declaringEntity = getDeclaringEntity();
            String[] strings = {declaringEntity.getName(), getName(), "transitionUserField"};
            String role = StringUtils.join(strings, ".");
            Class<? extends Entity> userEntityClass = TLC.getProject().getUserEntityClass();
            Class<?>[] validTypes = new Class<?>[]{userEntityClass};
            Field field = userEntityClass == null ? null : XS1.getField(log, role, fieldName, declaringEntity.getClass(), Entity.class, validTypes);
            if (field != null) {
                _transitionUserField = field;
                Property property = XS1.getProperty(field, declaringEntity);
                if (property instanceof Entity entity) {
                    if (!property.isCalculatedProperty()) {
                        _transitionUserEntity = entity;
                    } else if (log) {
                        String message = getFullName() + " has an calculable transitionUser field name in its StateProperty annotation";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                } else if (log) {
                    String message = getFullName() + " has an invalid transitionUser field name in its StateProperty annotation";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private void setTransitionDateTimeProperty(StateProperty annotation) {
        String fieldName = annotation.transitionDateTime();
        if (StringUtils.isNotBlank(fieldName)) {
            _transitionDateTimeFieldName = fieldName;
            boolean log = isLoggableProperty();
            Entity declaringEntity = getDeclaringEntity();
            String[] strings = {declaringEntity.getName(), getName(), "transitionDateTimeField"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = new Class<?>[]{DateProperty.class, TimestampProperty.class};
            Field field = XS1.getField(log, role, fieldName, declaringEntity.getClass(), Entity.class, validTypes);
            if (field != null) {
                _transitionDateTimeField = field;
                Property property = XS1.getProperty(field, declaringEntity);
                if (property != null) {
                    if (!property.isCalculatedProperty()) {
                        _transitionDateTimeProperty = property;
                    } else if (log) {
                        String message = getFullName() + " has a calculable transitionDateTime field name in its StateProperty annotation";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                } else if (log) {
                    String message = getFullName() + " has an invalid transitionDateTime field name in its StateProperty annotation";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private void initializeKeyPropertyAnnotations() {
        if (isKeyField()) {
            _nullable = false;
            _unique = true;
        }
        if (isPrimaryKeyProperty()) {
            _updateable = false;
            hideKeyProperty();
        }
        if (isSequenceProperty()) {
            _updateable = false;
            hideKeyProperty();
        }
        if (isVersionProperty()) {
            _nullable = false;
            hideKeyProperty();
            NumericPrimitive primitive = (NumericPrimitive) this;
            primitive.setInitialValue(0);
            primitive.setDefaultValue(0);
            primitive.setMinValue(0);
        }
        if (isNumericKeyProperty()) {
            NumericPrimitive primitive = (NumericPrimitive) this;
            primitive.setMinValue(0);
        }
        if (isCharacterKeyProperty()) {
            StringData data = (StringData) this;
            data.setMaxLength(Project.getDefaultCharacterKeyMaxLength());
        }
        if (isBusinessKeyProperty()) {
            if (isNumericPrimitive()) {
                NumericPrimitive primitive = (NumericPrimitive) this;
                primitive.setMinValue(0);
            }
            if (isStringData()) {
                StringData data = (StringData) this;
                data.setMaxLength(Project.getDefaultCharacterKeyMaxLength());
                data.setDataGenFunction("util.string_codigo_entidad");
            }
        }
        if (isNameProperty()) {
            _nullable = false;
            StringData data = (StringData) this;
            data.setMaxLength(Project.getDefaultNamePropertyMaxLength());
            data.setDataGenFunction("util.string_nombre_entidad");
        }
        if (isDescriptionProperty()) {
            StringData data = (StringData) this;
            data.setMaxLength(Project.getDefaultDescriptionPropertyMaxLength());
            data.setDataGenFunction("util.string_descripcion_entidad");
        }
        if (isUrlProperty()) {
            StringData data = (StringData) this;
            data.setMaxLength(Project.getDefaultUrlPropertyMaxLength());
        }
        if (isInactiveIndicatorProperty()) {
            _nullable = false;
            BooleanPrimitive primitive = (BooleanPrimitive) this;
            if (primitive.getInitialValue() == null) {
                primitive.setInitialValue(false);
            }
            if (primitive.getDefaultValue() == null) {
                primitive.setDefaultValue(false);
            }
        }
        if (isOwnerProperty()) {
            _nullable = false;
            _createField = false;
            _updateField = false;
            _defaultCondition = DefaultCondition.IF_NULL;
            _defaultCheckpoint = Checkpoint.USER_INTERFACE;
            Entity owner = (Entity) this;
            if (owner.getInitialValue() == null) {
                owner.setInitialValue(SpecialEntityValue.CURRENT_USER);
            }
            if (owner.getDefaultValue() == null) {
                owner.setDefaultValue(SpecialEntityValue.CURRENT_USER);
            }
        }
        if (isUserProperty()) {
            _nullable = true;
            _createField = false;
            _updateField = false;
            _defaultCondition = DefaultCondition.UNCONDITIONALLY;
            _defaultCheckpoint = Checkpoint.USER_INTERFACE;
            Entity user = (Entity) this;
            user.setInitialValue(SpecialEntityValue.CURRENT_USER);
            user.setDefaultValue(SpecialEntityValue.CURRENT_USER);
        }
        if (isDiscriminatorProperty()) {
            _nullable = false;
            _updateable = false;
            _hiddenField = getDeclaringEntityRoot().getSubclassesMap().isEmpty();
            _createField = false;
            _updateField = false;
        }
    }

    private void hideKeyProperty() {
        _hiddenField = true;
        _createField = false;
        _updateField = false;
        _searchField = false;
        _filterField = false;
//      _sortField = false;
        _tableField = false;
//      _detailField = false;
        _columnField = false;
        _reportField = false;
        _exportField = false;
        _headertextlessField = false;
        _headingField = false;
        _overlayField = false;
        _prominentField = false;
        _immutableField = false;
//      _serializableField = false;
//      _serializeIUID = false;
    }

    private void annotateBaseField(Field field) {
        BaseField annotation = field.getAnnotation(BaseField.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = BaseField.class;
            Class<?>[] validTypes = new Class<?>[]{Property.class};
            boolean log = isLoggable(); // since 20210218
            _annotatedWithBaseField = XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        }
    }

    private void annotateColumnField(Field field) {
        Class<? extends Annotation> annotationClass = ColumnField.class;
        Class<?>[] validTypes = new Class<?>[]{Property.class};
        boolean log = isLoggable(); // since 20210218
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            ColumnField annotation = field.getAnnotation(ColumnField.class);
            _annotatedWithColumnField = true;
            _calculable = annotation.calculable().toBoolean(_calculable);
            _nullable = annotation.nullable().toBoolean(_calculable || _nullable);
            _insertable = annotation.insertable().toBoolean(!_calculable && _insertable);
            _updateable = annotation.updateable().toBoolean(!_calculable && _updateable);
            _unique = annotation.unique().toBoolean(!_calculable && _unique);
            _indexed = annotation.indexed().toBoolean(!_calculable && _indexed);
        }
        if (log) {
            checkColumnFieldElements(field);
        }
    }

    private void checkColumnFieldElements(Field field) {
        String fieldName = fullFieldName(field);
        if (_calculable) {
            if (!_nullable) {
                logger.error(fieldName + " is calculable and therefore must be nullable");
                Project.increaseParserErrorCount();
            }
            if (_insertable) {
                logger.error(fieldName + " is calculable and therefore cannot be insertable");
                Project.increaseParserErrorCount();
            }
            if (_updateable) {
                logger.error(fieldName + " is calculable and therefore cannot be updateable");
                Project.increaseParserErrorCount();
            }
            if (isUnique()) {
                logger.error(fieldName + " is calculable and therefore cannot be unique");
                Project.increaseParserErrorCount();
            }
            if (_indexed) {
                logger.error(fieldName + " is calculable and therefore cannot be indexed");
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkColumnFieldElements() {
        if (isProperty() && isLoggable() && !isCalculable()) {
            String fieldName = getFullName();
            if (_nullable && isUnique()) {
                logger.error(fieldName + " is nullable and therefore cannot be unique");
                Project.increaseParserErrorCount();
            }
            if (_indexed) {
                if (this instanceof StringData data) {
                    int maxLength = IntUtils.valueOf(data.getMaxLength());
                    if (maxLength < 1) {
                        logger.error(fieldName + " is a CLOB property and therefore cannot be indexed");
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private void annotateBigDecimalField(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = BigDecimalField.class;
        Class<?>[] validTypes = new Class<?>[]{BigDecimalParameter.class, BigDecimalProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        BigDecimalData data = null;
        int precision = -1;
        int scale = -1;
        if (this instanceof BigDecimalData bigDecimalData) {
            data = bigDecimalData;
            precision = data.getRawPrecision();
            scale = data.getRawScale();
        }
        /**/
        if (aye) {
            _annotatedWithBigDecimalField = true;
            BigDecimalField annotation = field.getAnnotation(BigDecimalField.class);
            precision = specified(annotation.precision(), precision);
            scale = specified(annotation.scale(), scale);
        }
        if (data != null && (aye || !casting(field))) {
            if (precision < 1) {
                precision = Constants.DEFAULT_DECIMAL_PRECISION;
            } else if (precision > Constants.MAX_DECIMAL_PRECISION) {
                precision = Constants.MAX_DECIMAL_PRECISION;
                if (log) {
                    logger.error(fieldName + " has an invalid decimal precision");
                    Project.increaseParserErrorCount();
                }
            }
            if (scale < 0) {
                scale = precision > Constants.DEFAULT_DECIMAL_SCALE ? Constants.DEFAULT_DECIMAL_SCALE : precision;
            } else if (scale > precision) {
                scale = precision;
                if (log) {
                    logger.error(fieldName + " has an invalid decimal scale");
                    Project.increaseParserErrorCount();
                }
            }
            data.setPrecision(precision);
            data.setScale(scale);
        }
    }

    private void annotateBigIntegerField(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = BigIntegerField.class;
        Class<?>[] validTypes = new Class<?>[]{BigIntegerParameter.class, BigIntegerProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        BigIntegerData data = null;
        int precision = -1;
        if (this instanceof BigIntegerData bigIntegerData) {
            data = bigIntegerData;
            precision = data.getRawPrecision();
        }
        /**/
        if (aye) {
            _annotatedWithBigIntegerField = true;
            BigIntegerField annotation = field.getAnnotation(BigIntegerField.class);
            precision = specified(annotation.precision(), precision);
        }
        if (data != null && (aye || !casting(field))) {
            if (precision < 1) {
                precision = Constants.DEFAULT_DECIMAL_PRECISION;
            } else if (precision > Constants.MAX_DECIMAL_PRECISION) {
                precision = Constants.MAX_DECIMAL_PRECISION;
                if (log) {
                    logger.error(fieldName + " has an invalid numeric precision");
                    Project.increaseParserErrorCount();
                }
            }
            data.setPrecision(precision);
        }
    }

    private void annotateBooleanField(Field field) {
//      String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = BooleanField.class;
        Class<?>[] validTypes = new Class<?>[]{BooleanParameter.class, BooleanProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        BooleanData data = null;
        BooleanDisplayType displayType = BooleanDisplayType.UNSPECIFIED;
        if (this instanceof BooleanData booleanData) {
            data = booleanData;
            displayType = data.rawBooleanDisplayType();
        }
        /**/
        if (aye) {
            _annotatedWithBooleanField = true;
            BooleanField annotation = field.getAnnotation(BooleanField.class);
            displayType = specified(annotation.displayType(), displayType);
        }
        if (data != null && (aye || !casting(field))) {
            data.setBooleanDisplayType(displayType);
        }
    }

    private void annotateNumericField(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = NumericField.class;
        Class<?>[] validTypes = new Class<?>[]{NumericPrimitive.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        NumericPrimitive data = null;
        NumericFieldType type = null;
        int divisor = -1;
        DivisorRule divisorRule = null;
        String symbol = "";
        SymbolPosition symbolPosition = null;
        Boolean symbolSeparator = null;
        String converter = "";
        String validator = "";
        if (this instanceof NumericPrimitive numericPrimitive) {
            data = numericPrimitive;
            type = data.getConverterType();
            divisor = data.getDivisor();
            divisorRule = data.getDivisorRule();
            symbol = data.getSymbol();
            symbolPosition = data.getSymbolPosition();
            symbolSeparator = data.isSymbolSeparator();
            converter = data.getSpecialConverterName();
            validator = data.getSpecialValidatorName();
        }
        /**/
        if (aye) {
            _annotatedWithNumericField = true;
            NumericField annotation = field.getAnnotation(NumericField.class);
            type = specified(annotation.type(), type);
            divisor = specified(annotation.divisor(), divisor);
            divisorRule = specified(annotation.divisorRule(), divisorRule);
            symbol = specified(annotation.symbol(), symbol);
            symbolPosition = specified(annotation.symbolPosition(), symbolPosition);
            symbolSeparator = annotation.symbolSeparator().toBoolean(symbolSeparator);
            converter = specified(annotation.converter(), converter);
            validator = specified(annotation.validator(), validator);
        }
        if (data != null && (aye || !casting(field))) {
            if (divisorRule == null || divisorRule.equals(DivisorRule.UNSPECIFIED)) {
                divisor = 0;
            } else if (divisor < 0) {
                divisor = Constants.DEFAULT_NUMERIC_DIVISOR;
            } else if (divisor < 1 || divisor > maxDivisor()) {
                divisor = 1;
                if (log) {
                    logger.error(fieldName + " has an invalid divisor");
                    Project.increaseParserErrorCount();
                }
            }
            if (StringUtils.isNotBlank(converter)) {
                if (!converter.matches(CONVERTER_REGEX)) {
                    converter = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid converter name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            if (StringUtils.isNotBlank(validator)) {
                if (!validator.matches(VALIDATOR_REGEX)) {
                    validator = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid validator name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            data.setConverterType(type);
            data.setDivisor(divisor);
            data.setDivisorRule(divisorRule);
            data.setSymbol(symbol);
            data.setSymbolPosition(symbolPosition);
            data.setSymbolSeparator(coalesce(symbolSeparator, defaultSymbolSeparator(symbol, symbolPosition, type)));
            data.setSpecialConverterName(converter);
            data.setSpecialValidatorName(validator);
        }
    }

    private int maxDivisor() {
        if (this instanceof ByteData) {
            return Constants.MAX_BYTE_DIVISOR;
        }
        if (this instanceof ShortData) {
            return Constants.MAX_SHORT_DIVISOR;
        }
        return Constants.MAX_INTEGER_DIVISOR;
    }

    private boolean defaultSymbolSeparator(String symbol, SymbolPosition symbolPosition, NumericFieldType type) {
        int l = symbol.length();
        int i = l > 0 ? SymbolPosition.PREFIX.equals(symbolPosition) ? l - 1 : 0 : -1;
        return i >= 0 ? Character.isLetterOrDigit(symbol.charAt(i))
            : !NumericFieldType.CURRENCY.equals(type) && !NumericFieldType.PERCENT.equals(type);
    }

    private void annotateStringField(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = StringField.class;
        Class<?>[] validTypes = new Class<?>[]{StringParameter.class, StringProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        StringData data = null;
        int maxLength = -1;
        int minLength = -1;
        String mask = "";
        char slotChar = '_';
        String regex = "";
        String converter = "";
        String validator = "";
        AutoComplete autoComplete = AutoComplete.UNSPECIFIED;
        LetterCase letterCase = LetterCase.UNSPECIFIED;
        boolean allowDiacritics = false;
        boolean richTextFormat = false;
        boolean translatable = false;
        if (this instanceof StringData stringData) {
            data = stringData;
            maxLength = IntUtils.valueOf(data.getMaxLength(), -1);
            minLength = data.getMinLength();
            mask = data.getInputMask();
            slotChar = data.getSlotChar();
            regex = data.getPatternRegex();
            converter = data.getSpecialConverterName();
            validator = data.getSpecialValidatorName();
            autoComplete = data.getAutoComplete();
            letterCase = data.getLetterCase();
            allowDiacritics = data.isAllowDiacritics();
            richTextFormat = data.isRichTextFormat();
            translatable = data.isTranslatable();
        }
        /**/
        if (aye) {
            _annotatedWithStringField = true;
            StringField annotation = field.getAnnotation(StringField.class);
            /* until 28/04/2022
            maxLength = specified(annotation.maxLength(), maxLength);
            /**/
            int aml = annotation.maxLength();
            if (aml < -1000000000 || aml > -1) {
                maxLength = aml;
            }
            /**/
            minLength = specified(annotation.minLength(), minLength);
            mask = specified(annotation.mask(), mask);
            slotChar = specified(annotation.slotChar(), slotChar);
            regex = specified(annotation.regex(), regex);
            converter = specified(annotation.converter(), converter);
            validator = specified(annotation.validator(), validator);
            autoComplete = specified(annotation.autoComplete(), autoComplete);
            letterCase = specified(annotation.letterCase(), letterCase);
            allowDiacritics = annotation.allowDiacritics().toBoolean(allowDiacritics);
            richTextFormat = annotation.richTextFormat().toBoolean(richTextFormat);
            translatable = annotation.translatable().toBoolean(translatable);
            if (richTextFormat) {
                maxLength = 0;
                if (log && aml > 0) {
                    logger.warn("max length of " + fieldName + " was ignored; rich text format properties must be defined with no limit");
                    Project.increaseParserWarningCount();
                }
            }
        }
        if (data != null && (aye || !casting(field))) {
            int projectMaximumStringFieldMaxLength = Project.getMaximumStringFieldMaxLength();
            int projectMaximumStringIndexMaxLength = Project.getMaximumStringIndexMaxLength();
            if (maxLength < 0) {
                maxLength = positiveStringFieldMaxLength(field, maxLength);
            } else if (maxLength > projectMaximumStringFieldMaxLength) {
                maxLength = projectMaximumStringFieldMaxLength;
                if (log) {
                    logger.error("max length of " + fieldName + " exceeds the defined string field limit (" + projectMaximumStringFieldMaxLength + ")");
                    Project.increaseParserErrorCount();
                }
            } else if ((maxLength == 0 || maxLength > projectMaximumStringIndexMaxLength) && (isUnique() || isIndexed())) {
                maxLength = projectMaximumStringIndexMaxLength;
                if (log) {
                    logger.error("max length of " + fieldName + " exceeds the defined string index limit (" + projectMaximumStringIndexMaxLength + ")");
                    Project.increaseParserErrorCount();
                }
            }
            /**/
            if (minLength < 0) {
                minLength = 0;
            } else if (maxLength > 0 && minLength > maxLength) {
                minLength = maxLength;
                if (log) {
                    logger.error(fieldName + " has an invalid min length");
                    Project.increaseParserErrorCount();
                }
            }
            if (StringUtils.isBlank(mask)) {
                mask = "";
                slotChar = '_';
            } else {
                if (!StrUtils.isASCII(mask)) {
                    mask = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid input mask");
                        Project.increaseParserErrorCount();
                    }
                }
                if (!StrUtils.isASCII(slotChar)) {
                    slotChar = '_';
                    if (log) {
                        logger.error(fieldName + " has an invalid slot char");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            Pattern pattern = null;
            if (StringUtils.isNotBlank(regex)) {
                try {
                    pattern = Pattern.compile(regex);
                } catch (PatternSyntaxException ex) {
                    if (log) {
                        logger.error(fieldName + " has an invalid regular expression");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            if (StringUtils.isNotBlank(converter)) {
                if (!converter.matches(CONVERTER_REGEX)) {
                    converter = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid converter name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            if (StringUtils.isNotBlank(validator)) {
                if (!validator.matches(VALIDATOR_REGEX)) {
                    validator = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid validator name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            data.setMaxLength(maxLength < 1 ? null : maxLength);
            data.setMinLength(minLength);
            data.setInputMask(mask);
            data.setSlotChar(slotChar);
            data.setAutoComplete(autoComplete);
            data.setLetterCase(letterCase);
            data.setAllowDiacritics(allowDiacritics);
            data.setRichTextFormat(richTextFormat);
            data.setTranslatable(translatable);
            data.setPattern(pattern);
            data.setSpecialConverterName(converter);
            data.setSpecialValidatorName(validator);
        }
    }

    private int positiveStringFieldMaxLength(Field field, int maxLength) {
        if (maxLength >= 0) {
            return maxLength;
        } else if (maxLength == Project.CHARACTER_KEY_MAX_LENGTH) { // indexado
            return Project.getDefaultCharacterKeyMaxLength(); // getDefaultCharacterKeyMaxLength es menor o igual que getDefaultStringIndexMaxLength
        } else if (maxLength == Project.NAME_PROPERTY_MAX_LENGTH) { // indexado
            return Project.getDefaultNamePropertyMaxLength(); // getDefaultNamePropertyMaxLength es menor o igual que getDefaultStringIndexMaxLength
        } else if (maxLength == Project.STRING_INDEX_MAX_LENGTH || isUnique() || isIndexed()) { // indexado
            return Project.getDefaultStringIndexMaxLength();
        } else if (maxLength == Project.DESCRIPTION_PROPERTY_MAX_LENGTH) {
            return Project.getDefaultDescriptionPropertyMaxLength();
        } else if (maxLength == Project.URL_PROPERTY_MAX_LENGTH || field.isAnnotationPresent(UniformResourceLocator.class)) {
            return Project.getDefaultUrlPropertyMaxLength();
        } else if (maxLength == Project.EMBEDDED_DOCUMENT_MAX_LENGTH || field.isAnnotationPresent(EmbeddedDocument.class)) {
            return Project.getDefaultEmbeddedDocumentMaxLength();
        } else if (maxLength == Project.FILE_REFERENCE_MAX_LENGTH || field.isAnnotationPresent(FileReference.class)) {
            return Project.getDefaultFileReferenceMaxLength();
        } else if (maxLength == Project.STRING_FIELD_MAX_LENGTH) {
            return Project.getMaximumStringFieldMaxLength();
        } else {
            return Project.getDefaultStringFieldMaxLength();
        }
    }

    private void annotateDateField(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = DateField.class;
        Class<?>[] validTypes = new Class<?>[]{DateParameter.class, DateProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        DateData data = null;
        boolean disabledWeekends = false;
        boolean disabledWeekdays = false;
        boolean disabledHolidays = false;
        int yearRange = -1;
        String converter = "";
        String validator = "";
        if (this instanceof DateData dateData) {
            data = dateData;
            disabledWeekends = data.isDisabledWeekends();
            disabledWeekdays = data.isDisabledWeekdays();
            disabledHolidays = data.isDisabledHolidays();
            yearRange = data.getYearRange();
            converter = data.getSpecialConverterName();
            validator = data.getSpecialValidatorName();
        }
        /**/
        if (aye) {
            _annotatedWithDateField = true;
            DateField annotation = field.getAnnotation(DateField.class);
            disabledWeekends = annotation.disabledWeekends().toBoolean(disabledWeekends);
            disabledWeekdays = annotation.disabledWeekdays().toBoolean(disabledWeekdays);
            disabledHolidays = annotation.disabledHolidays().toBoolean(disabledHolidays);
            yearRange = specified(annotation.yearRange(), yearRange);
            converter = specified(annotation.converter(), converter);
            validator = specified(annotation.validator(), validator);
        }
        if (data != null && (aye || !casting(field))) {
            if (yearRange < 0) {
                yearRange = Constants.DEFAULT_YEAR_RANGE;
            } else if (yearRange < 1 || yearRange > Constants.MAX_YEAR_RANGE) {
                yearRange = Constants.MAX_YEAR_RANGE;
                if (log) {
                    logger.error(fieldName + " has an invalid decimal precision");
                    Project.increaseParserErrorCount();
                }
            }
            if (StringUtils.isNotBlank(converter)) {
                if (!converter.matches(CONVERTER_REGEX)) {
                    converter = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid converter name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            if (StringUtils.isNotBlank(validator)) {
                if (!validator.matches(VALIDATOR_REGEX)) {
                    validator = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid validator name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            data.setDisabledWeekends(disabledWeekends);
            data.setDisabledWeekdays(disabledWeekdays);
            data.setDisabledHolidays(disabledHolidays);
            data.setYearRange(yearRange);
            data.setSpecialConverterName(converter);
            data.setSpecialValidatorName(validator);
        }
    }

    private void annotateTimeField(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = TimeField.class;
        Class<?>[] validTypes = new Class<?>[]{TimeParameter.class, TimeProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        TimeData data = null;
        int precision = -1;
        int minHour = -1;
        int maxHour = -1;
        int stepHour = -1;
        int minMinute = -1;
        int maxMinute = -1;
        int stepMinute = -1;
        int minSecond = -1;
        int maxSecond = -1;
        int stepSecond = -1;
        String converter = "";
        String validator = "";
        if (this instanceof TimeData timeData) {
            data = timeData;
            precision = data.getPrecision();
            minHour = data.getMinHour();
            maxHour = data.getMaxHour();
            stepHour = data.getStepHour();
            minMinute = data.getMinMinute();
            maxMinute = data.getMaxMinute();
            stepMinute = data.getStepMinute();
            minSecond = data.getMinSecond();
            maxSecond = data.getMaxSecond();
            stepSecond = data.getStepSecond();
            converter = data.getSpecialConverterName();
            validator = data.getSpecialValidatorName();
        }
        /**/
        if (aye) {
            _annotatedWithTimeField = true;
            TimeField annotation = field.getAnnotation(TimeField.class);
            precision = specified(annotation.precision(), precision);
            minHour = specified(annotation.minHour(), minHour);
            maxHour = specified(annotation.maxHour(), maxHour);
            stepHour = specified(annotation.stepHour(), stepHour);
            minMinute = specified(annotation.minMinute(), minMinute);
            maxMinute = specified(annotation.maxMinute(), maxMinute);
            stepMinute = specified(annotation.stepMinute(), stepMinute);
            minSecond = specified(annotation.minSecond(), minSecond);
            maxSecond = specified(annotation.maxSecond(), maxSecond);
            stepSecond = specified(annotation.stepSecond(), stepSecond);
            converter = specified(annotation.converter(), converter);
            validator = specified(annotation.validator(), validator);
            if (precision < 0) {
                precision = Constants.DEFAULT_TIME_PRECISION;
            } else if (precision > Constants.MAX_TIME_PRECISION) {
                precision = Constants.MAX_TIME_PRECISION;
                if (log) {
                    logger.error(fieldName + " has an invalid precision");
                    Project.increaseParserErrorCount();
                }
            }
            if (maxHour < 0) {
                maxHour = 23;
            } else if (maxHour > 23) {
                maxHour = 23;
                if (log) {
                    logger.error(fieldName + " has an invalid maximum hour");
                    Project.increaseParserErrorCount();
                }
            }
            if (minHour < 0) {
                minHour = 0;
            } else if (minHour > maxHour) {
                minHour = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid minimum hour");
                    Project.increaseParserErrorCount();
                }
            }
            if (stepHour < 0) {
                stepHour = 1;
            } else if (stepHour < 1 || stepHour > 23) {
                stepHour = 1;
                if (log) {
                    logger.error(fieldName + " has an invalid hour step");
                    Project.increaseParserErrorCount();
                }
            }
            if (maxMinute < 0) {
                maxMinute = 59;
            } else if (maxMinute > 59) {
                maxMinute = 59;
                if (log) {
                    logger.error(fieldName + " has an invalid maximum minute");
                    Project.increaseParserErrorCount();
                }
            }
            if (minMinute < 0) {
                minMinute = 0;
            } else if (minMinute > maxMinute) {
                minMinute = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid minimum minute");
                    Project.increaseParserErrorCount();
                }
            }
            if (stepMinute < 0) {
                stepMinute = 1;
            } else if (stepMinute < 1 || stepMinute > 59) {
                stepMinute = 1;
                if (log) {
                    logger.error(fieldName + " has an invalid minute step");
                    Project.increaseParserErrorCount();
                }
            }
            if (maxSecond < 0) {
                maxSecond = 59;
            } else if (maxSecond > 59) {
                maxSecond = 59;
                if (log) {
                    logger.error(fieldName + " has an invalid maximum second");
                    Project.increaseParserErrorCount();
                }
            }
            if (minSecond < 0) {
                minSecond = 0;
            } else if (minSecond > maxSecond) {
                minSecond = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid minimum second");
                    Project.increaseParserErrorCount();
                }
            }
            if (stepSecond < 0) {
                stepSecond = 1;
            } else if (stepSecond < 1 || stepSecond > 59) {
                stepSecond = 1;
                if (log) {
                    logger.error(fieldName + " has an invalid second step");
                    Project.increaseParserErrorCount();
                }
            }
            if (StringUtils.isNotBlank(converter)) {
                if (!converter.matches(CONVERTER_REGEX)) {
                    converter = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid converter name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            if (StringUtils.isNotBlank(validator)) {
                if (!validator.matches(VALIDATOR_REGEX)) {
                    validator = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid validator name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
        if (data != null && (aye || !casting(field))) {
            data.setPrecision(precision);
            data.setMinHour(minHour);
            data.setMaxHour(maxHour);
            data.setStepHour(stepHour);
            data.setMinMinute(minMinute);
            data.setMaxMinute(maxMinute);
            data.setStepMinute(stepMinute);
            data.setMinSecond(minSecond);
            data.setMaxSecond(maxSecond);
            data.setStepSecond(stepSecond);
            data.setSpecialConverterName(converter);
            data.setSpecialValidatorName(validator);
        }
    }

    private void annotateTimestampField(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = TimestampField.class;
        Class<?>[] validTypes = new Class<?>[]{TimestampParameter.class, TimestampProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        TimestampData data = null;
        boolean disabledWeekends = false;
        boolean disabledWeekdays = false;
        boolean disabledHolidays = false;
        int yearRange = -1;
        int precision = -1;
        int minHour = -1;
        int maxHour = -1;
        int stepHour = -1;
        int minMinute = -1;
        int maxMinute = -1;
        int stepMinute = -1;
        int minSecond = -1;
        int maxSecond = -1;
        int stepSecond = -1;
        String converter = "";
        String validator = "";
        if (this instanceof TimestampData timestampData) {
            data = timestampData;
            disabledWeekends = data.isDisabledWeekends();
            disabledWeekdays = data.isDisabledWeekdays();
            disabledHolidays = data.isDisabledHolidays();
            yearRange = data.getYearRange();
            precision = data.getPrecision();
            minHour = data.getMinHour();
            maxHour = data.getMaxHour();
            stepHour = data.getStepHour();
            minMinute = data.getMinMinute();
            maxMinute = data.getMaxMinute();
            stepMinute = data.getStepMinute();
            minSecond = data.getMinSecond();
            maxSecond = data.getMaxSecond();
            stepSecond = data.getStepSecond();
            converter = data.getSpecialConverterName();
            validator = data.getSpecialValidatorName();
        }
        /**/
        if (aye) {
            _annotatedWithTimestampField = true;
            TimestampField annotation = field.getAnnotation(TimestampField.class);
            disabledWeekends = annotation.disabledWeekends().toBoolean(disabledWeekends);
            disabledWeekdays = annotation.disabledWeekdays().toBoolean(disabledWeekdays);
            disabledHolidays = annotation.disabledHolidays().toBoolean(disabledHolidays);
            yearRange = specified(annotation.yearRange(), yearRange);
            precision = specified(annotation.precision(), precision);
            minHour = specified(annotation.minHour(), minHour);
            maxHour = specified(annotation.maxHour(), maxHour);
            stepHour = specified(annotation.stepHour(), stepHour);
            minMinute = specified(annotation.minMinute(), minMinute);
            maxMinute = specified(annotation.maxMinute(), maxMinute);
            stepMinute = specified(annotation.stepMinute(), stepMinute);
            minSecond = specified(annotation.minSecond(), minSecond);
            maxSecond = specified(annotation.maxSecond(), maxSecond);
            stepSecond = specified(annotation.stepSecond(), stepSecond);
            converter = specified(annotation.converter(), converter);
            validator = specified(annotation.validator(), validator);
            if (yearRange < 0) {
                yearRange = Constants.DEFAULT_YEAR_RANGE;
            } else if (yearRange < 1 || yearRange > Constants.MAX_YEAR_RANGE) {
                yearRange = Constants.MAX_YEAR_RANGE;
                if (log) {
                    logger.error(fieldName + " has an invalid decimal precision");
                    Project.increaseParserErrorCount();
                }
            }
            if (precision < 0) {
                precision = Constants.DEFAULT_TIME_PRECISION;
            } else if (precision > Constants.MAX_TIME_PRECISION) {
                precision = Constants.MAX_TIME_PRECISION;
                if (log) {
                    logger.error(fieldName + " has an invalid precision");
                    Project.increaseParserErrorCount();
                }
            }
            if (maxHour < 0) {
                maxHour = 23;
            } else if (maxHour > 23) {
                maxHour = 23;
                if (log) {
                    logger.error(fieldName + " has an invalid maximum hour");
                    Project.increaseParserErrorCount();
                }
            }
            if (minHour < 0) {
                minHour = 0;
            } else if (minHour > maxHour) {
                minHour = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid minimum hour");
                    Project.increaseParserErrorCount();
                }
            }
            if (stepHour < 0) {
                stepHour = 1;
            } else if (stepHour < 1 || stepHour > 23) {
                stepHour = 1;
                if (log) {
                    logger.error(fieldName + " has an invalid hour step");
                    Project.increaseParserErrorCount();
                }
            }
            if (maxMinute < 0) {
                maxMinute = 59;
            } else if (maxMinute > 59) {
                maxMinute = 59;
                if (log) {
                    logger.error(fieldName + " has an invalid maximum minute");
                    Project.increaseParserErrorCount();
                }
            }
            if (minMinute < 0) {
                minMinute = 0;
            } else if (minMinute > maxMinute) {
                minMinute = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid minimum minute");
                    Project.increaseParserErrorCount();
                }
            }
            if (stepMinute < 0) {
                stepMinute = 1;
            } else if (stepMinute < 1 || stepMinute > 59) {
                stepMinute = 1;
                if (log) {
                    logger.error(fieldName + " has an invalid minute step");
                    Project.increaseParserErrorCount();
                }
            }
            if (maxSecond < 0) {
                maxSecond = 59;
            } else if (maxSecond > 59) {
                maxSecond = 59;
                if (log) {
                    logger.error(fieldName + " has an invalid maximum second");
                    Project.increaseParserErrorCount();
                }
            }
            if (minSecond < 0) {
                minSecond = 0;
            } else if (minSecond > maxSecond) {
                minSecond = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid minimum second");
                    Project.increaseParserErrorCount();
                }
            }
            if (stepSecond < 0) {
                stepSecond = 1;
            } else if (stepSecond < 1 || stepSecond > 59) {
                stepSecond = 1;
                if (log) {
                    logger.error(fieldName + " has an invalid second step");
                    Project.increaseParserErrorCount();
                }
            }
            if (StringUtils.isNotBlank(converter)) {
                if (!converter.matches(CONVERTER_REGEX)) {
                    converter = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid converter name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            if (StringUtils.isNotBlank(validator)) {
                if (!validator.matches(VALIDATOR_REGEX)) {
                    validator = "";
                    if (log) {
                        logger.error(fieldName + " has an invalid validator name");
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
        if (data != null && (aye || !casting(field))) {
            data.setDisabledWeekends(disabledWeekends);
            data.setDisabledWeekdays(disabledWeekdays);
            data.setDisabledHolidays(disabledHolidays);
            data.setYearRange(yearRange);
            data.setPrecision(precision);
            data.setMinHour(minHour);
            data.setMaxHour(maxHour);
            data.setStepHour(stepHour);
            data.setMinMinute(minMinute);
            data.setMaxMinute(maxMinute);
            data.setStepMinute(stepMinute);
            data.setMinSecond(minSecond);
            data.setMaxSecond(maxSecond);
            data.setStepSecond(stepSecond);
            data.setSpecialConverterName(converter);
            data.setSpecialValidatorName(validator);
        }
    }

    private void annotateEmbeddedDocument(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = EmbeddedDocument.class;
        Class<?>[] validTypes = new Class<?>[]{StringProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            _annotatedWithEmbeddedDocument = true;
            StringData data = (StringData) this;
            EmbeddedDocument annotation = field.getAnnotation(EmbeddedDocument.class);
            String searchURL = specified(annotation.searchURL(), data.getSearchURL());
            String[] sourceURLs = specified(annotation.sourceURLs(), data.getEmbeddedDocumentURLs());
            EmbeddedDocumentType sourceType = specified(annotation.sourceType(), data.getEmbeddedDocumentType());
            EmbeddedDocumentStyle style = specified(annotation.style(), data.getEmbeddedDocumentStyle());
            /**/
            // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
            /*
            int displayWidth = specified(annotation.displayWidth(), data.getDisplayWidth());
            int displayHeight = specified(annotation.displayHeight(), data.getDisplayHeight());
            boolean resizable = annotation.resizable().toBoolean(data.isResizable());
            /**/
            // </editor-fold>
            /**/
            // <editor-fold defaultstate="collapsed" desc="until 23/01/2024">
            /*
            int[] displayWidth = Arrays.copyOf(annotation.displayWidth(), COMPONENT_DISPLAY_SIZE.length);
            int[] displayHeight = Arrays.copyOf(annotation.displayHeight(), COMPONENT_DISPLAY_SIZE.length);
            /**/
            // </editor-fold>
            /**/
            int[] displayWidth = specified(annotation.displayWidth(), data.getDisplayWidth());
            int[] displayHeight = specified(annotation.displayHeight(), data.getDisplayHeight());
            boolean resizable = annotation.resizable().toBoolean(data.isResizable());
            /**/
            Boolean frameBorder = annotation.frameBorder().toBoolean(data.getFrameBorder());
            Boolean encryptedMedia = annotation.encryptedMedia().toBoolean(data.getEncryptedMedia());
            Boolean accelerometer = annotation.accelerometer().toBoolean(data.getAccelerometer());
            Boolean autoplay = annotation.autoplay().toBoolean(data.getAutoplay());
            Boolean gyroscope = annotation.gyroscope().toBoolean(data.getGyroscope());
            Boolean pictureInPicture = annotation.pictureInPicture().toBoolean(data.getPictureInPicture());
            Boolean fullScreen = annotation.fullScreen().toBoolean(data.getFullScreen());
            EmbeddedDocumentLoading loading = annotation.loading();
            EmbeddedDocumentPolicy referrerPolicy = annotation.referrerPolicy();
            EmbeddedDocumentSandbox sandbox = annotation.sandbox();
            boolean encoding = annotation.encoding().toBoolean(data.isEncodingEnabled());
            /**/
            // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
            /*
            if (displayWidth < 0) {
                displayWidth = 0;
            } else if (displayWidth < 1 || displayWidth > Constants.MAX_DISPLAY_WIDTH) {
                displayWidth = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid display width");
                    Project.increaseParserErrorCount();
                }
            }
            if (displayHeight < 0) {
                displayHeight = 0;
            } else if (displayHeight < 1 || displayHeight > Constants.MAX_DISPLAY_HEIGHT) {
                displayHeight = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid display height");
                    Project.increaseParserErrorCount();
                }
            }
            if (displayWidth == 0 && displayHeight == 0) {
                displayWidth = Constants.DEFAULT_DOCUMENT_WIDTH;
                displayHeight = Constants.DEFAULT_DOCUMENT_HEIGHT;
                resizable = true;
            } else if (displayWidth == 0) {
                displayWidth = displayHeight;
                resizable = true;
            } else if (displayHeight == 0) {
                displayHeight = displayWidth > Constants.MAX_DISPLAY_HEIGHT ? Constants.MAX_DISPLAY_HEIGHT : displayWidth;
                resizable = true;
            }
            /**/
            // </editor-fold>
            /**/
            data.setSearchURL(searchURL); // before setEmbeddedDocumentURLs to allow that method to set a default value for searchURL
            data.setEmbeddedDocumentURLs(sourceURLs);
            data.setEmbeddedDocumentType(sourceType);
            data.setEmbeddedDocumentStyle(style);
            /**/
            // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
            /*
            data.setDisplayWidth(displayWidth);
            data.setDisplayHeight(displayHeight);
            data.setResizable(resizable);
            /**/
            // </editor-fold>
            /**/
            WHR whrL = new WHR(fieldName, log, L, displayWidth[L], displayHeight[L], MIN_DOC_W, MIN_DOC_H, DEF_DOC_W[L], DEF_DOC_H[L]);
            WHR whrM = new WHR(fieldName, log, M, displayWidth[M], displayHeight[M], MIN_DOC_W, MIN_DOC_H, DEF_DOC_W[M], DEF_DOC_H[M]);
            WHR whrS = new WHR(fieldName, log, S, displayWidth[S], displayHeight[S], MIN_DOC_W, MIN_DOC_H, DEF_DOC_W[S], DEF_DOC_H[S]);
            /**/
            data.setLargeDisplayWidth(whrL.displayWidth);
            data.setLargeDisplayHeight(whrL.displayHeight);
            data.setMediumDisplayWidth(whrM.displayWidth);
            data.setMediumDisplayHeight(whrM.displayHeight);
            data.setSmallDisplayWidth(whrS.displayWidth);
            data.setSmallDisplayHeight(whrS.displayHeight);
            data.setResizable(resizable || whrL.resizable || whrM.resizable || whrS.resizable);
            /**/
            data.setFrameBorder(frameBorder);
            data.setEncryptedMedia(encryptedMedia);
            data.setAccelerometer(accelerometer);
            data.setAutoplay(autoplay);
            data.setGyroscope(gyroscope);
            data.setPictureInPicture(pictureInPicture);
            data.setFullScreen(fullScreen);
            data.setLoading(loading);
            data.setReferrerPolicy(referrerPolicy);
            data.setSandbox(sandbox);
            data.setEncodingEnabled(encoding);
        }
    }

    private void annotateFileReference(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = FileReference.class;
        Class<?>[] validTypes = new Class<?>[]{StringParameter.class, StringProperty.class};
        boolean log = isParameter() || depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            _annotatedWithFileReference = true;
            StringData data = (StringData) this;
            FileReference annotation = field.getAnnotation(FileReference.class);
            Boolean autoStart = annotation.autoStart().toBoolean(data.fileUploadAutoStart());
            Boolean virusScan = annotation.virusScan().toBoolean(data.fileUploadVirusScan());
            int fileLimit = specified(annotation.fileLimit(), data.getFileUploadFileLimit());
            int undoLimit = specified(annotation.undoLimit(), data.getFileUploadUndoLimit());
            int maxInputFileSize = specified(annotation.max(), data.getMaxInputFileSize());
            MimeType[] types = specified(annotation.types(), data.getValidInputFileTypes());
            String regex = specified(annotation.regex(), data.getValidInputFilePatternRegex());
            UploadStorageOption uploadStorageOption = specified(annotation.storage(), data.getUploadStorageOption());
            String pathTemplate = specified(annotation.pathTemplate(), data.getPathTemplate());
            String blobFieldName = specified(annotation.blobField(), data.getBlobFieldName());
            String joinFieldName = specified(annotation.joinField(), data.getJoinFieldName());
            String loadFieldName = specified(annotation.loadField(), data.getLoadFieldName());
            String textFieldName = specified(annotation.textField(), data.getTextFieldName());
            if (fileLimit < 0) {
                fileLimit = -1;
            } else if (fileLimit < 1 || fileLimit > Constants.MAX_UPLOAD_FILE_LIMIT) {
                fileLimit = -1;
                if (log) {
                    logger.error(fieldName + " has an invalid file limit");
                    Project.increaseParserErrorCount();
                }
            }
            if (undoLimit < 0) {
                undoLimit = -1;
            } else if (undoLimit > Constants.MAX_UPLOAD_UNDO_LIMIT) {
                undoLimit = -1;
                if (log) {
                    logger.error(fieldName + " has an invalid undo limit");
                    Project.increaseParserErrorCount();
                }
            }
            if (maxInputFileSize < 0) {
                maxInputFileSize = -1;
            }
            Pattern pattern = null;
            if (StringUtils.isNotBlank(regex)) {
                try {
                    pattern = Pattern.compile(regex);
                } catch (PatternSyntaxException ex) {
                    if (log) {
                        logger.error(fieldName + " has an invalid regular expression");
                        Project.increaseParserErrorCount();
                    }
                }
            }
            if (UploadStorageOption.FILE.equals(uploadStorageOption) && StringUtils.isNotBlank(blobFieldName)) {
                uploadStorageOption = UploadStorageOption.UNSPECIFIED;
                if (log) {
                    logger.error(fieldName + " has an invalid file reference: blob field cannot be specified if storage is FILE");
                    Project.increaseParserErrorCount();
                }
            }
            if (autoStart != null) {
                data.setFileUploadAutoStart(autoStart);
            }
            if (virusScan != null) {
                data.setFileUploadVirusScan(virusScan);
            }
            data.setFileUploadFileLimit(fileLimit);
            data.setFileUploadUndoLimit(undoLimit);
            data.setMaxInputFileSize(maxInputFileSize);
            data.setValidInputFileTypes(types);
            data.setValidInputFilePattern(pattern);
            data.setUploadStorageOption(uploadStorageOption);
            data.setPathTemplate(pathTemplate);
            setFieldReferenceBlobProperty(blobFieldName, fieldName);
            setFieldReferenceJoinProperty(joinFieldName, fieldName);
            setFieldReferenceLoadProperty(loadFieldName, fieldName);
            setFieldReferenceTextProperty(textFieldName, fieldName);
            setFieldReferenceLoadPropertyCurrentValue();
        }
    }

    private void setFieldReferenceBlobProperty(String blobFieldName, String fieldName) {
        StringData data = (StringData) this;
        if (StringUtils.isNotBlank(blobFieldName)) {
            data.setBlobFieldName(blobFieldName);
            boolean log = isParameter() || depth() == 0;
            Operation declaringOperation = getDeclaringOperation();
            Entity declaringEntity = declaringOperation == null ? getDeclaringEntity() : declaringOperation.getDeclaringEntity();
            Artifact declaringArtifact = declaringOperation == null ? declaringEntity : declaringOperation;
            ProcessOperation process = declaringOperation instanceof ProcessOperation ? (ProcessOperation) declaringOperation : null;
            Class<? extends Entity> constructionType = process == null ? null : process.getConstructionType();
            boolean differentEntity = false;
            if (constructionType != null && !constructionType.equals(declaringEntity.getDataClass())) {
                declaringEntity = TLC.getProject().getEntity(constructionType);
                differentEntity = true;
            }
            String[] strings = {declaringArtifact.getName(), getName(), "blobField"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = new Class<?>[]{BinaryProperty.class};
            Field blobField = XS1.getField(log, role, blobFieldName, declaringEntity.getClass(), Entity.class, validTypes);
            if (blobField != null) {
                Field previous = data.setBlobField(blobField);
                if (log && previous != null && previous != blobField) {
                    logger.warn(fieldName + " blob field was previously assigned to another file reference");
                    Project.increaseParserWarningCount();
                }
                data.setBlobEntity(declaringEntity);
                Property blobProperty = XS1.getProperty(blobField, declaringEntity, differentEntity);
                if (blobProperty != null) {
                    data.setBlobProperty(blobProperty);
                }
            }
        }
    }

    private void setFieldReferenceJoinProperty(String joinFieldName, String fieldName) {
        StringData data = (StringData) this;
        if (StringUtils.isNotBlank(joinFieldName)) {
            data.setJoinFieldName(joinFieldName);
            boolean log = isParameter() || depth() == 0;
            Operation declaringOperation = getDeclaringOperation();
            Entity declaringEntity = declaringOperation == null ? getDeclaringEntity() : declaringOperation.getDeclaringEntity();
            Artifact declaringArtifact = declaringOperation == null ? declaringEntity : declaringOperation;
            ProcessOperation process = declaringOperation instanceof ProcessOperation ? (ProcessOperation) declaringOperation : null;
            Class<? extends Entity> constructionType = process == null ? null : process.getConstructionType();
            boolean differentEntity = false;
            if (constructionType != null && !constructionType.equals(declaringEntity.getDataClass())) {
                declaringEntity = TLC.getProject().getEntity(constructionType);
                differentEntity = true;
            }
            String[] strings = {declaringArtifact.getName(), getName(), "joinField"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = joinFieldValidTypes();
            Field joinField = XS1.getField(log, role, joinFieldName, declaringEntity.getClass(), Entity.class, validTypes);
            if (joinField != null) {
                Field previous = data.setJoinField(joinField);
                if (log && previous != null && previous != joinField) {
                    logger.warn(fieldName + " join field was previously assigned to another file reference");
                    Project.increaseParserWarningCount();
                }
                data.setJoinEntity(declaringEntity);
                Property joinProperty = XS1.getProperty(joinField, declaringEntity, differentEntity);
                if (joinProperty != null) {
                    data.setJoinProperty(joinProperty);
                }
            }
        }
    }

    private void setFieldReferenceLoadProperty(String loadFieldName, String fieldName) {
        StringData data = (StringData) this;
        if (StringUtils.isNotBlank(loadFieldName)) {
            data.setLoadFieldName(loadFieldName);
            boolean log = isParameter() || depth() == 0;
            Operation declaringOperation = getDeclaringOperation();
            Entity declaringEntity = declaringOperation == null ? getDeclaringEntity() : declaringOperation.getDeclaringEntity();
            Artifact declaringArtifact = declaringOperation == null ? declaringEntity : declaringOperation;
            ProcessOperation process = declaringOperation instanceof ProcessOperation ? (ProcessOperation) declaringOperation : null;
            Class<? extends Entity> constructionType = process == null ? null : process.getConstructionType();
            boolean differentEntity = false;
            if (constructionType != null && !constructionType.equals(declaringEntity.getDataClass())) {
                declaringEntity = TLC.getProject().getEntity(constructionType);
                differentEntity = true;
            }
            String[] strings = {declaringArtifact.getName(), getName(), "loadField"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = new Class<?>[]{DateProperty.class, TimestampProperty.class};
            Field loadField = XS1.getField(log, role, loadFieldName, declaringEntity.getClass(), Entity.class, validTypes);
            if (loadField != null) {
                Field previous = data.setLoadField(loadField);
                if (log && previous != null && previous != loadField) {
                    logger.warn(fieldName + " load field was previously assigned to another file reference");
                    Project.increaseParserWarningCount();
                }
                data.setLoadEntity(declaringEntity);
                Property loadProperty = XS1.getProperty(loadField, declaringEntity, differentEntity);
                if (loadProperty != null) {
                    data.setLoadProperty(loadProperty);
                }
            }
        }
    }

    private void setFieldReferenceTextProperty(String textFieldName, String fieldName) {
        StringData data = (StringData) this;
        if (StringUtils.isNotBlank(textFieldName)) {
            data.setTextFieldName(textFieldName);
            boolean log = isParameter() || depth() == 0;
            Operation declaringOperation = getDeclaringOperation();
            Entity declaringEntity = declaringOperation == null ? getDeclaringEntity() : declaringOperation.getDeclaringEntity();
            Artifact declaringArtifact = declaringOperation == null ? declaringEntity : declaringOperation;
            ProcessOperation process = declaringOperation instanceof ProcessOperation ? (ProcessOperation) declaringOperation : null;
            Class<? extends Entity> constructionType = process == null ? null : process.getConstructionType();
            boolean differentEntity = false;
            if (constructionType != null && !constructionType.equals(declaringEntity.getDataClass())) {
                declaringEntity = TLC.getProject().getEntity(constructionType);
                differentEntity = true;
            }
            String[] strings = {declaringArtifact.getName(), getName(), "textField"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = new Class<?>[]{StringProperty.class};
            Field textField = XS1.getField(log, role, textFieldName, declaringEntity.getClass(), Entity.class, validTypes);
            if (textField != null) {
                Field previous = data.setTextField(textField);
                if (log && previous != null && previous != textField) {
                    logger.warn(fieldName + " text field was previously assigned to another file reference");
                    Project.increaseParserWarningCount();
                }
                data.setTextEntity(declaringEntity);
                Property textProperty = XS1.getProperty(textField, declaringEntity, differentEntity);
                if (textProperty != null) {
                    data.setTextProperty(textProperty);
                }
            }
        }
    }

    private void setFieldReferenceLoadPropertyCurrentValue() {
        StringData data = (StringData) this;
        Operation declaringOperation = getDeclaringOperation();
        if (declaringOperation != null) {
            Parameter instanceParameter = declaringOperation.getInstanceParameter();
            if (instanceParameter instanceof Entity instanceEntity) {
                Property loadProperty = data.getLoadProperty();
                if (loadProperty != null) {
                    String loadFieldName = loadProperty.getName();
                    if (StringUtils.isNotBlank(loadFieldName)) {
                        for (Property instanceProperty : instanceEntity.getPropertiesList()) {
                            if (loadFieldName.equals(instanceProperty.getName())) {
                                if (instanceProperty instanceof DateProperty dateProperty) {
                                    dateProperty.setCurrentValue(SpecialTemporalValue.CURRENT_DATE);
                                } else if (instanceProperty instanceof TimestampProperty timestampProperty) {
                                    timestampProperty.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private Class<?>[] joinFieldValidTypes() {
        Class<? extends Entity> clazz = TLC.getProject().getUploadedFileEntityClass();
        Class<? extends Entity> valid = clazz == null ? Entity.class : clazz;
        return new Class<?>[]{valid};
    }

    private void annotateGraphicImage(Field field) {
        String fieldName = fullFieldName(field);
        Class<? extends Annotation> annotationClass = GraphicImage.class;
        Class<?>[] validTypes = new Class<?>[]{BinaryProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            _annotatedWithGraphicImage = true;
            BinaryData data = (BinaryData) this;
            GraphicImage annotation = field.getAnnotation(GraphicImage.class);
            /**/
            // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
            /*
            int displayWidth = specified(annotation.displayWidth(), data.getDisplayWidth());
            int displayHeight = specified(annotation.displayHeight(), data.getDisplayHeight());
            boolean resizable = annotation.resizable().toBoolean(data.isResizable());
            if (displayWidth < 0) {
                displayWidth = 0;
            } else if (displayWidth < 1 || displayWidth > Constants.MAX_DISPLAY_WIDTH) {
                displayWidth = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid display width");
                    Project.increaseParserErrorCount();
                }
            }
            if (displayHeight < 0) {
                displayHeight = 0;
            } else if (displayHeight < 1 || displayHeight > Constants.MAX_DISPLAY_HEIGHT) {
                displayHeight = 0;
                if (log) {
                    logger.error(fieldName + " has an invalid display height");
                    Project.increaseParserErrorCount();
                }
            }
            if (displayWidth == 0 && displayHeight == 0) {
                displayWidth = Constants.DEFAULT_IMAGE_WIDTH;
                displayHeight = Constants.DEFAULT_IMAGE_HEIGHT;
                resizable = true;
            } else if (displayWidth == 0) {
                displayWidth = displayHeight;
                resizable = true;
            } else if (displayHeight == 0) {
                displayHeight = displayWidth > Constants.MAX_DISPLAY_HEIGHT ? Constants.MAX_DISPLAY_HEIGHT : displayWidth;
                resizable = true;
            }
            data.setDisplayWidth(displayWidth);
            data.setDisplayHeight(displayHeight);
            data.setResizable(resizable);
            /**/
            // </editor-fold>
            /**/
            // <editor-fold defaultstate="collapsed" desc="until 23/01/2024">
            /*
            int[] displayWidth = Arrays.copyOf(annotation.displayWidth(), COMPONENT_DISPLAY_SIZE.length);
            int[] displayHeight = Arrays.copyOf(annotation.displayHeight(), COMPONENT_DISPLAY_SIZE.length);
            /**/
            // </editor-fold>
            /**/
            int[] displayWidth = specified(annotation.displayWidth(), data.getDisplayWidth());
            int[] displayHeight = specified(annotation.displayHeight(), data.getDisplayHeight());
            boolean resizable = annotation.resizable().toBoolean(data.isResizable());
            /**/
            WHR whrL = new WHR(fieldName, log, L, displayWidth[L], displayHeight[L], MIN_IMG_W, MIN_IMG_H, DEF_IMG_W[L], DEF_IMG_H[L]);
            WHR whrM = new WHR(fieldName, log, M, displayWidth[M], displayHeight[M], MIN_IMG_W, MIN_IMG_H, DEF_IMG_W[M], DEF_IMG_H[M]);
            WHR whrS = new WHR(fieldName, log, S, displayWidth[S], displayHeight[S], MIN_IMG_W, MIN_IMG_H, DEF_IMG_W[S], DEF_IMG_H[S]);
            /**/
            data.setLargeDisplayWidth(whrL.displayWidth);
            data.setLargeDisplayHeight(whrL.displayHeight);
            data.setMediumDisplayWidth(whrM.displayWidth);
            data.setMediumDisplayHeight(whrM.displayHeight);
            data.setSmallDisplayWidth(whrS.displayWidth);
            data.setSmallDisplayHeight(whrS.displayHeight);
            data.setResizable(resizable || whrL.resizable || whrM.resizable || whrS.resizable);
            /**/
        }
    }

    private void annotateMasterSequenceProperty(Field field) {
        Class<? extends Annotation> annotationClass = MasterSequence.class;
        Class<?>[] validTypes = new Class<?>[]{IntegerProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            _annotatedWithMasterSequence = true;
            IntegerProperty property = (IntegerProperty) this;
            MasterSequence annotation = field.getAnnotation(MasterSequence.class);
            String fieldName = annotation.masterField();
            int start = Math.min(10000, Math.max(1, annotation.start()));
            int step = Math.min(10000, Math.max(1, annotation.step()));
            NextValueRule rule = annotation.nextValueRule();
            setMasterSequenceMasterProperty(fieldName);
            property.setMasterSequenceStart(start);
            property.setMasterSequenceStep(step);
            property.setMasterSequenceNextValueRule(rule);
        }
    }

    private void setMasterSequenceMasterProperty(String masterFieldName) {
        IntegerProperty property = (IntegerProperty) this;
        if (StringUtils.isNotBlank(masterFieldName)) {
            property.setMasterSequenceMasterFieldName(masterFieldName);
            boolean log = depth() == 0;
            Entity declaringEntity = getDeclaringEntity();
            String[] strings = {declaringEntity.getName(), getName(), "masterField"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = new Class<?>[]{EntityReference.class};
            Field masterField = XS1.getField(log, role, masterFieldName, declaringEntity.getClass(), Entity.class, validTypes);
            if (masterField != null) {
                property.setMasterSequenceMasterField(masterField);
                property.setMasterSequenceMasterEntity(declaringEntity);
                Property masterProperty = XS1.getProperty(masterField, declaringEntity);
                if (masterProperty instanceof EntityReference masterEntityReference) {
                    MasterDetailView masterDetailView = masterEntityReference.getMasterDetailView();
                    if (MasterDetailView.TABLE.equals(masterDetailView) || MasterDetailView.TABLE_AND_DETAIL.equals(masterDetailView)) {
                        property.setMasterSequenceMasterProperty(masterProperty);
                        masterEntityReference.setMasterSequenceMasterField(true);
                    } else if (log) {
                        String message = getFullName() + " has an invalid master field in its MasterSequence annotation";
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                } else if (log) {
                    String message = getFullName() + " has an invalid master field name in its MasterSequence annotation";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private void annotateUniformResourceLocator(Field field) {
        Class<? extends Annotation> annotationClass = UniformResourceLocator.class;
        Class<?>[] validTypes = new Class<?>[]{StringProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            _annotatedWithUniformResourceLocator = true;
            StringData data = (StringData) this;
            UniformResourceLocator annotation = field.getAnnotation(UniformResourceLocator.class);
            UrlType urlType = specified(annotation.urlType(), data.getUrlType());
            DisplayMode urlDisplayMode = specified(annotation.urlDisplayMode(), data.getUrlDisplayMode());
            UrlDisplayType urlDisplayType = specified(annotation.urlDisplayType(), data.getUrlDisplayType());
            String[] sourceURLs = null;
            String searchURL = null;
            Pattern pattern = data.getPattern();
            boolean encoding = annotation.encoding().toBoolean(data.isEncodingEnabled());
            if (UrlType.EXTERNAL.equals(urlType)) {
                sourceURLs = specified(annotation.sourceURLs(), data.getSourceURLs());
                searchURL = specified(annotation.searchURL(), data.getSearchURL());
                if (pattern == null) {
                    pattern = Pattern.compile(Constants.URL_REGEX);
                }
            }
            data.setUrlType(urlType);
            data.setUrlDisplayMode(urlDisplayMode);
            data.setUrlDisplayType(urlDisplayType);
            data.setSourceURLs(sourceURLs);
            data.setSearchURL(searchURL);
            data.setPattern(pattern);
            data.setEncodingEnabled(encoding);
        }
    }

    private void annotateVariantString(Field field) {
        VariantString annotation = field.getAnnotation(VariantString.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = VariantString.class;
            Class<?>[] validTypes = new Class<?>[]{StringProperty.class};
            boolean log = depth() == 0;
            _annotatedWithVariantString = XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        }
    }

    private void annotateInstanceReference(Field field) {
        InstanceReference annotation = field.getAnnotation(InstanceReference.class);
        if (annotation != null && annotation.value()) {
            Class<? extends Annotation> annotationClass = InstanceReference.class;
            Operation declaringOperation = getDeclaringOperation();
            Class<?> declaringClass = declaringOperation.getDeclaringField().getDeclaringClass();
            Class<?>[] validTypes = new Class<?>[]{declaringClass};
            boolean log = true;
            boolean aye = XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
            if (aye) {
                Field previous = getDeclaringArtifact().put(annotationClass, field);
                if (previous == null) {
                    _annotatedWithInstanceReference = true;
                    declaringOperation.setOperationKind(OperationKind.INSTANCE);
                } else if (log) {
                    XS1.logDuplicateAnnotation(field, annotationClass, previous);
                }
            }
        }
        /**/
        if (_annotatedWithInstanceReference) {
            _required = true;
        }
    }

    private void annotateParameterField(Field field) {
        Class<? extends Annotation> annotationClass = ParameterField.class;
        boolean aye = field.isAnnotationPresent(annotationClass);
        if (aye) {
            ParameterField annotation = field.getAnnotation(ParameterField.class);
            _annotatedWithParameterField = true;
            _auditable = annotation.auditable().toBoolean(_auditable);
            _password = annotation.password().toBoolean(_password);
            _required = _annotatedWithInstanceReference ? Boolean.TRUE : annotation.required().toBoolean(_required);
            _hiddenField = !_annotatedWithInstanceReference && annotation.hidden().toBoolean(_hiddenField);
            _linkedFieldName = annotation.linkedField();
            _linkedColumnName = annotation.linkedColumn();
            _linkedColumnOperator = annotation.operator();
            setAnchorParameter(annotation);
//          _sequenceNumber = Math.max(0, annotation.sequence());
            _sequenceNumber = annotation.sequence();
            setInlineHelpType(annotation.inlineHelp());
            linkField(namesakable() ? getName() : _linkedFieldName);
            String fileName = annotation.snippet();
            if (StringUtils.isNotBlank(fileName)) {
                setProcessingConsoleSnippetFileName(fileName);
            }
        }
    }

    private void setAnchorParameter(ParameterField annotation) {
        String anchorFieldName = annotation.anchor();
        if (StringUtils.isNotBlank(anchorFieldName)) {
            boolean log = isLoggable();
            if (anchorFieldName.equals(getName())) {
                if (log) {
                    String message = "no anchor defined for " + getFullName() + "; a parameter cannot be anchored to itself.";
                    logger.warn(message);
                    Project.increaseParserWarningCount();
                }
            } else {
                _anchorFieldName = anchorFieldName;
                Operation declaringOperation = getDeclaringOperation();
                String[] strings = {declaringOperation.getName(), getName(), "anchorField"};
                String role = StringUtils.join(strings, ".");
                Class<?>[] validTypes = null;
                Field anchorField = XS1.getField(log, role, anchorFieldName, declaringOperation.getClass(), Operation.class, validTypes);
                if (anchorField == null) {
                    if (log) {
                        String message = "no anchor defined for " + getFullName() + "; it has an invalid anchor field name.";
                        logger.warn(message);
                        Project.increaseParserWarningCount();
                    }
                } else {
                    _anchorField = anchorField;
                    Parameter anchorParameter = XS1.getParameter(anchorField, declaringOperation);
                    if (anchorParameter == null) {
                        if (log) {
                            String message = "no anchor defined for " + getFullName() + "; it has an invalid anchor parameter name.";
                            logger.warn(message);
                            Project.increaseParserWarningCount();
                        }
                    } else {
                        _anchorParameter = anchorParameter;
                        _anchorType = annotation.anchorType();
                    }
                }
            }
        }
    }

    private boolean namesakable() {
        if (StringUtils.isBlank(_linkedFieldName) && StringUtils.isBlank(_linkedColumnName)) {
            Operation declaringOperation = getDeclaringOperation();
            if (declaringOperation instanceof ExportOperation export) {
                return ExportQueryType.DYNAMIC.equals(export.getQueryType());
            }
            if (declaringOperation instanceof ReportOperation report) {
                return ReportQueryType.DYNAMIC.equals(report.getQueryType());
            }
        }
        return false;
    }

    private void linkField(String name) {
        if (StringUtils.isNotBlank(name)) {
            Operation declaringOperation = getDeclaringOperation();
            Entity declaringEntity = declaringOperation.getDeclaringEntity();
            ProcessOperation process = declaringOperation instanceof ProcessOperation ? (ProcessOperation) declaringOperation : null;
            Class<? extends Entity> constructionType = process == null ? null : process.getConstructionType();
            boolean differentEntity = false;
            if (constructionType != null && !constructionType.equals(declaringEntity.getDataClass())) {
                declaringEntity = TLC.getProject().getEntity(constructionType);
                differentEntity = true;
            }
            String[] strings = {declaringOperation.getName(), getName(), "linkedField"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = _dataClass == null ? new Class<?>[]{} : new Class<?>[]{_dataClass};
            _linkedField = XS1.getField(true, role, name, declaringEntity.getClass(), Entity.class, validTypes);
            if (_linkedField != null) {
                _linkedFieldName = name;
                _linkedEntity = declaringEntity;
                _linkedProperty = XS1.getProperty(_linkedField, declaringEntity, differentEntity);
            }
        }
    }

    private void annotatePropertyField(Field field) {
        Class<? extends Annotation> annotationClass = PropertyField.class;
        boolean log = isLoggable();
        boolean aye = field.isAnnotationPresent(annotationClass);
        if (aye) {
            String fileName;
            PropertyField annotation = field.getAnnotation(PropertyField.class);
            _annotatedWithPropertyField = true;
            _propertyAccess = specified(annotation.access(), _propertyAccess);
            _auditable = annotation.auditable().toBoolean(_auditable);
            _password = annotation.password().toBoolean(_password);
            _required = annotation.required().toBoolean(_required);
            _hiddenField = annotation.hidden().toBoolean(_hiddenField);
            _createField = annotation.create().toBoolean(_createField);
            _updateField = annotation.update().toBoolean(_updateField);
            _searchField = annotation.search().toBoolean(_searchField);
            _filterField = annotation.filter().toBoolean(_filterField);
            _sortField = annotation.sort().toBoolean(_sortField);
            _tableField = annotation.table().toBoolean(_tableField);
            _detailField = annotation.detail().toBoolean(_detailField);
            _columnField = annotation.column().toBoolean(_columnField);
            _reportField = annotation.report().toBoolean(_reportField);
            _exportField = annotation.export().toBoolean(_exportField);
            _headertextlessField = annotation.headertextless().toBoolean(_headertextlessField);
            _headingField = annotation.heading().toBoolean(_headingField);
            _overlayField = annotation.overlay().toBoolean(_overlayField);
            _prominentField = annotation.prominent().toBoolean(_prominentField);
            _immutableField = annotation.immutable().toBoolean(_immutableField);
            _serializableField = annotation.serializable().toBoolean(_serializableField);
            _serializableIUID = annotation.serializableIUID().toBoolean(_serializableIUID);
            _defaultCondition = specified(annotation.defaultCondition(), _defaultCondition);
            _defaultCheckpoint = specified(annotation.defaultCheckpoint(), _defaultCheckpoint);
            _defaultFunction = StringUtils.defaultIfBlank(StringUtils.trimToNull(annotation.defaultFunction()), _defaultFunction);
            setAnchorProperty(annotation);
//          _sequenceNumber = Math.max(0, annotation.sequence());
            _sequenceNumber = annotation.sequence();
            setInlineHelpType(annotation.inlineHelp());
            /**/
            fileName = annotation.masterHeadingSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setMasterHeadingSnippetFileName(fileName);
            }
            fileName = annotation.readingTableSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setReadingTableSnippetFileName(fileName);
            }
            fileName = annotation.writingTableSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setWritingTableSnippetFileName(fileName);
            }
            fileName = annotation.readingDetailSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setReadingDetailSnippetFileName(fileName);
            }
            fileName = annotation.writingDetailSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setWritingDetailSnippetFileName(fileName);
            }
        }
        if (log) {
            checkPropertyFieldElements();
        }
    }

    private void setAnchorProperty(PropertyField annotation) {
        String anchorFieldName = annotation.anchor();
        if (StringUtils.isNotBlank(anchorFieldName)) {
            boolean log = isLoggable();
            if (anchorFieldName.equals(getName())) {
                if (log) {
                    String message = "no anchor defined for " + getFullName() + "; a property cannot be anchored to itself.";
                    logger.warn(message);
                    Project.increaseParserWarningCount();
                }
            } else {
                _anchorFieldName = anchorFieldName;
                Entity declaringEntity = getDeclaringEntity();
                String[] strings = {declaringEntity.getName(), getName(), "anchorField"};
                String role = StringUtils.join(strings, ".");
                Class<?>[] validTypes = null;
                Field anchorField = XS1.getField(log, role, anchorFieldName, declaringEntity.getClass(), Entity.class, validTypes);
                if (anchorField == null) {
                    if (log) {
                        String message = "no anchor defined for " + getFullName() + "; it has an invalid anchor field name.";
                        logger.warn(message);
                        Project.increaseParserWarningCount();
                    }
                } else {
                    _anchorField = anchorField;
                    Property anchorProperty = XS1.getProperty(anchorField, declaringEntity);
                    if (anchorProperty == null) {
                        if (log) {
                            String message = "no anchor defined for " + getFullName() + "; it has an invalid anchor property name.";
                            logger.warn(message);
                            Project.increaseParserWarningCount();
                        }
                    } else {
                        _anchorProperty = anchorProperty;
                        _anchorType = annotation.anchorType();
                    }
                }
            }
        }
    }

    private void checkPropertyFieldElements() {
        if (_defaultFunction != null && _defaultFunction.matches("^.*\\bold\\..*$")) {
            switch (_defaultCondition) {
                case IF_NULL_ON_UPDATE, UNCONDITIONALLY_ON_UPDATE -> {
                }
                default -> {
                    String message = getFullName() + " default function cannot reference \"old\" pseudo-record "
                        + "due to its default condition; old is valid only for IF_NULL_ON_UPDATE and UNCONDITIONALLY_ON_UPDATE";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
        }
        if (restrictedReadingAccess()) {
            if (BitUtils.valueOf(_headingField)) {
                String message = getFullName() + " cannot be a heading property due to its restricted reading access";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
            if (BitUtils.valueOf(_overlayField)) {
                String message = getFullName() + " cannot be an overlay property due to its restricted reading access";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
            if (BitUtils.valueOf(_prominentField)) {
                String message = getFullName() + " cannot be a prominent property due to its restricted reading access";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
        }
    }

    private void annotatePropertyAggregation(Field field) {
        Class<? extends Annotation> annotationClass = PropertyAggregation.class;
        boolean aye = field.isAnnotationPresent(annotationClass);
        if (aye) {
            PropertyAggregation annotation = field.getAnnotation(PropertyAggregation.class);
            _annotatedWithPropertyAggregation = true;
            _aggregateFunction = specified(annotation.function(), _aggregateFunction);
            _aggregateTitle = specified(annotation.title(), _aggregateTitle);
            if (!_aggregateTitle.isEmpty()) {
                setAggregateTitle(_aggregateTitle);
            }

        }
        _aggregateFunction = validAggregateFunction();
    }

    private AggregateFunction validAggregateFunction() {
        if (_aggregateFunction == null || _aggregateFunction.equals(AggregateFunction.UNSPECIFIED)) {
            return AggregateFunction.UNSPECIFIED;
        }
        if (isNumericPrimitive()) {
            return _aggregateFunction;
        }
        if (isTemporalPrimitive()) {
            switch (_aggregateFunction) {
                case COUNT, MINIMUM, MAXIMUM -> {
                    return _aggregateFunction;
                }
            }
            logUsingCountAggregation();
            return AggregateFunction.COUNT;
        }
        if (isEntity() || isBooleanPrimitive() || isCharacterPrimitive()) {
            if (_aggregateFunction.equals(AggregateFunction.COUNT)) {
                return _aggregateFunction;
            }
            logUsingCountAggregation();
            return AggregateFunction.COUNT;
        }
        logNonAggregable();
        return AggregateFunction.UNSPECIFIED;
    }

    private void logUsingCountAggregation() {
        if (isLoggable()) {
            String message = _aggregateFunction + " cannot be applied to " + getFullName() + "; using COUNT instead";
            logger.warn(message);
            Project.increaseParserWarningCount();
        }
    }

    private void logNonAggregable() {
        if (isLoggable()) {
            String message = "no aggregation can be applied to " + getFullName();
            logger.error(message);
            Project.increaseParserErrorCount();
        }
    }

    private void annotateDataGen(Field field) {
        annotateBooleanDataGen(field);
        annotateCharacterDataGen(field);
        annotateNumericDataGen(field);
        annotateTemporalDataGen(field);
    }

    private void annotateBooleanDataGen(Field field) {
        Class<? extends Annotation> annotationClass = BooleanDataGen.class;
        Class<?>[] validTypes = new Class<?>[]{BooleanProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            BooleanDataGen annotation = field.getAnnotation(BooleanDataGen.class);
            _annotatedWithDataGen = true;
            _dataGenType = annotation.type();
            setDataGenFunction(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenTrueable = Math.min(100, Math.max(0, annotation.trueable()));
            if (_dataGenNullable + _dataGenTrueable > 100) {
                _dataGenNullable = 100 - _dataGenTrueable;
            }
        }
    }

    private void annotateCharacterDataGen(Field field) {
        Class<? extends Annotation> annotationClass = CharacterDataGen.class;
        Class<?>[] validTypes = new Class<?>[]{CharacterProperty.class, StringProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            CharacterDataGen annotation = field.getAnnotation(CharacterDataGen.class);
            _annotatedWithDataGen = true;
            _dataGenType = annotation.type();
            _loremIpsum = annotation.loremIpsum().toBoolean();
            _dataGenSeriesStart = Math.min(Constants.MAX_SERIES_START, Math.max(1, annotation.start()));
            _dataGenSeriesStop = Math.min(Constants.MAX_SERIES_STOP, Math.max(1, annotation.stop()));
            _dataGenSeriesStep = Math.min(Constants.MAX_SERIES_STEP, Math.max(1, annotation.step()));
            if (log && !isDataGenSeriesEnabled()) {
                String message = getFullName() + " has invalid values for elements start/stop/step of its CharacterDataGen annotation: "
                    + _dataGenSeriesStart + "/" + _dataGenSeriesStop + "/" + _dataGenSeriesStep;
                logger.error(message);
                Project.increaseParserErrorCount();
            }
            setDataGenFunction(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenPattern = StringUtils.trimToNull(annotation.pattern());
            _dataGenPrefix = StrUtils.ltrimToNull(annotation.prefix());
            _dataGenSuffix = StrUtils.rtrimToNull(annotation.suffix());
        }
    }

    private void annotateNumericDataGen(Field field) {
        Class<? extends Annotation> annotationClass = NumericDataGen.class;
        Class<?>[] validTypes = new Class<?>[]{BigDecimalProperty.class, BigIntegerProperty.class,
            ByteProperty.class, ShortProperty.class, IntegerProperty.class, LongProperty.class,
            FloatProperty.class, DoubleProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            NumericDataGen annotation = field.getAnnotation(NumericDataGen.class);
            _annotatedWithDataGen = true;
            _dataGenType = annotation.type();
            _dataGenSeriesStart = Math.min(Constants.MAX_SERIES_START, Math.max(1, annotation.start()));
            _dataGenSeriesStop = Math.min(Constants.MAX_SERIES_STOP, Math.max(1, annotation.stop()));
            _dataGenSeriesStep = Math.min(Constants.MAX_SERIES_STEP, Math.max(1, annotation.step()));
            if (log && !isDataGenSeriesEnabled()) {
                String message = getFullName() + " has invalid values for elements start/stop/step of its NumericDataGen annotation: "
                    + _dataGenSeriesStart + "/" + _dataGenSeriesStop + "/" + _dataGenSeriesStep;
                logger.error(message);
                Project.increaseParserErrorCount();
            }
            setDataGenFunction(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenMin = StringUtils.trimToNull(annotation.min());
            _dataGenMax = StringUtils.trimToNull(annotation.max());
            _dataGenMinValue = someIntegerValue(field, _dataGenMin);
            _dataGenMaxValue = someIntegerValue(field, _dataGenMax);
            _dataGenNumericAction = annotation.action();
            _dataGenFactor = someBigDecimalValue(field, annotation.factor());
        }
    }

    private void annotateTemporalDataGen(Field field) {
        Class<? extends Annotation> annotationClass = TemporalDataGen.class;
        Class<?>[] validTypes = new Class<?>[]{DateProperty.class, TimeProperty.class, TimestampProperty.class};
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            TemporalDataGen annotation = field.getAnnotation(TemporalDataGen.class);
            _annotatedWithDataGen = true;
            _dataGenType = annotation.type();
            _dataGenSeriesStart = Math.min(Constants.MAX_SERIES_START, Math.max(1, annotation.start()));
            _dataGenSeriesStop = Math.min(Constants.MAX_SERIES_STOP, Math.max(1, annotation.stop()));
            _dataGenSeriesStep = Math.min(Constants.MAX_SERIES_STEP, Math.max(1, annotation.step()));
            if (log && !isDataGenSeriesEnabled()) {
                String message = getFullName() + " has invalid values for elements start/stop/step of its TemporalDataGen annotation: "
                    + _dataGenSeriesStart + "/" + _dataGenSeriesStop + "/" + _dataGenSeriesStep;
                logger.error(message);
                Project.increaseParserErrorCount();
            }
            setDataGenFunction(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenMin = StringUtils.trimToNull(annotation.min());
            _dataGenMax = StringUtils.trimToNull(annotation.max());
            _dataGenMinTemporalAddend = someTemporalAddend(_dataGenMin);
            _dataGenMaxTemporalAddend = someTemporalAddend(_dataGenMax);
            _dataGenMinValue = someTemporalValue(field, _dataGenMin);
            _dataGenMaxValue = someTemporalValue(field, _dataGenMax);
            _dataGenTemporalInterval = annotation.interval();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="some methods">
    private Integer someIntegerValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            logInvalidDataExpression(field, "integer", e);
        }
        return null;
    }

    private BigDecimal someBigDecimalValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        try {
            return new BigDecimal(string);
        } catch (Exception e) {
            logInvalidDataExpression(field, "decimal", e);
        }
        return null;
    }

    private TemporalAddend someTemporalAddend(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        if (this instanceof DateData) {
            TemporalAddend addend = someDateTemporalAddend(string);
            return addend == null || addend.isBadValue() ? null : addend;
        }
        if (this instanceof TimeData) {
            TemporalAddend addend = someTimeTemporalAddend(string);
            return addend == null || addend.isBadValue() ? null : addend;
        }
        if (this instanceof TimestampData) {
            TemporalAddend addend = someTimestampTemporalAddend(string);
            return addend == null || addend.isBadValue() ? null : addend;
        }
        return null;
    }

    private java.util.Date someTemporalValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        if (this instanceof DateData) {
            return someDateValue(field, string);
        }
        if (this instanceof TimeData) {
            return someTimeValue(field, string);
        }
        if (this instanceof TimestampData) {
            return someTimestampValue(field, string);
        }
        return null;
    }

    private final Date currentDate = TimeUtils.currentDate();

    private Date someDateValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        TemporalAddend addend = someDateTemporalAddend(string);
        if (addend == null) {
            try {
                return Date.valueOf(string);
            } catch (Exception e) {
                logInvalidDataExpression(field, "date", e);
                return null;
            }
        }
        if (addend.isBadValue()) {
            logBadTemporalAddend(field, "date", addend);
            return null;
        }
        return TimeUtils.addDate(currentDate, Math.toIntExact(addend.getQuantity()), addend.getUnitCode());
    }

    private final Time currentTime = TimeUtils.currentTime();

    private Time someTimeValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        TemporalAddend addend = someTimeTemporalAddend(string);
        if (addend == null) {
            try {
                return Time.valueOf(string);
            } catch (Exception e) {
                logInvalidDataExpression(field, "time", e);
                return null;
            }
        }
        if (addend.isBadValue()) {
            logBadTemporalAddend(field, "time", addend);
            return null;
        }
        return TimeUtils.addTime(currentTime, Math.toIntExact(addend.getQuantity()), addend.getUnitCode());
    }

    private final Timestamp currentTimestamp = TimeUtils.currentTimestamp();

    private Timestamp someTimestampValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        TemporalAddend addend = someTimestampTemporalAddend(string);
        if (addend == null) {
            try {
                return Timestamp.valueOf(string);
            } catch (Exception e) {
                logInvalidDataExpression(field, "timestamp", e);
                return null;
            }
        }
        if (addend.isBadValue()) {
            logBadTemporalAddend(field, "timestamp", addend);
            return null;
        }
        return TimeUtils.addTimestamp(currentTimestamp, Math.toIntExact(addend.getQuantity()), addend.getUnitCode());
    }

    private TemporalAddend someDateTemporalAddend(String string) {
        return TemporalAddend.temporalAddendValueOf(string,
            TemporalAddend.DATE_UNITS,
            TemporalAddend.DAYS,
            TemporalAddend.MIN_INT_VALUE,
            TemporalAddend.MAX_INT_VALUE
        );
    }

    private TemporalAddend someTimeTemporalAddend(String string) {
        return TemporalAddend.temporalAddendValueOf(string,
            TemporalAddend.TIME_UNITS,
            TemporalAddend.HOURS,
            TemporalAddend.MIN_INT_VALUE,
            TemporalAddend.MAX_INT_VALUE
        );
    }

    private TemporalAddend someTimestampTemporalAddend(String string) {
        return TemporalAddend.temporalAddendValueOf(string,
            TemporalAddend.DATE_TIME_UNITS,
            TemporalAddend.DAYS,
            TemporalAddend.MIN_INT_VALUE,
            TemporalAddend.MAX_INT_VALUE
        );
    }

    private void logInvalidDataExpression(Field field, String string, Exception e) {
        assert e != null;
        boolean log = depth() == 0;
        if (log) {
            String fieldName = fullFieldName(field);
            logger.error(fieldName + " has an invalid " + string + " data expression");
            Project.increaseParserErrorCount();
        }
    }

    private void logBadTemporalAddend(Field field, String string, TemporalAddend addend) {
        boolean log = depth() == 0;
        if (log) {
            String fieldName = fullFieldName(field);
            logger.error(fieldName + " has an invalid " + string + " data expression"
                + "; allowed value range is \"" + addend.getMinValue() + "\" to \"" + addend.getMaxValue() + "\""
            );
            Project.increaseParserErrorCount();
        }
    }

    private String fullFieldName(Field field) {
        return getDeclaringArtifactClassSimpleName() + "." + field.getName();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instanceof">
    /**
     * @return true if is a Primitive; otherwise false
     */
    @Override
    public boolean isPrimitive() {
        return this instanceof Primitive;
    }

    /**
     * @return true if is a BinaryPrimitive; otherwise false
     */
    @Override
    public boolean isBinaryPrimitive() {
        return this instanceof BinaryPrimitive;
    }

    /**
     * @return true if is a BooleanPrimitive; otherwise false
     */
    @Override
    public boolean isBooleanPrimitive() {
        return this instanceof BooleanPrimitive;
    }

    /**
     * @return true if is a CharacterPrimitive; otherwise false
     */
    @Override
    public boolean isCharacterPrimitive() {
        return this instanceof CharacterPrimitive;
    }

    /**
     * @return true if is a NumericPrimitive; otherwise false
     */
    @Override
    public boolean isNumericPrimitive() {
        return this instanceof NumericPrimitive;
    }

    /**
     * @return true if is a TemporalPrimitive; otherwise false
     */
    @Override
    public boolean isTemporalPrimitive() {
        return this instanceof TemporalPrimitive;
    }

    /**
     * @return true if is a BigDecimalData; otherwise false
     */
    @Override
    public boolean isBigDecimalData() {
        return this instanceof BigDecimalData;
    }

    /**
     * @return true if is a BigIntegerData; otherwise false
     */
    @Override
    public boolean isBigIntegerData() {
        return this instanceof BigIntegerData;
    }

    /**
     * @return true if is a BinaryData; otherwise false
     */
    @Override
    public boolean isBinaryData() {
        return this instanceof BinaryData;
    }

    private boolean isUndisplayableBinaryData() {
        return isBinaryData() && !isGraphicImageField();
    }

    /**
     * @return true if is a BooleanData; otherwise false
     */
    @Override
    public boolean isBooleanData() {
        return this instanceof BooleanData;
    }

    /**
     * @return true if is a ByteData; otherwise false
     */
    @Override
    public boolean isByteData() {
        return this instanceof ByteData;
    }

    /**
     * @return true if is a CharacterData; otherwise false
     */
    @Override
    public boolean isCharacterData() {
        return this instanceof CharacterData;
    }

    /**
     * @return true if is a DateData; otherwise false
     */
    @Override
    public boolean isDateData() {
        return this instanceof DateData;
    }

    /**
     * @return true if is a DoubleData; otherwise false
     */
    @Override
    public boolean isDoubleData() {
        return this instanceof DoubleData;
    }

    /**
     * @return true if is a FloatData; otherwise false
     */
    @Override
    public boolean isFloatData() {
        return this instanceof FloatData;
    }

    /**
     * @return true if is a IntegerData; otherwise false
     */
    @Override
    public boolean isIntegerData() {
        return this instanceof IntegerData;
    }

    /**
     * @return true if is a LongData; otherwise false
     */
    @Override
    public boolean isLongData() {
        return this instanceof LongData;
    }

    /**
     * @return true if is a ShortData; otherwise false
     */
    @Override
    public boolean isShortData() {
        return this instanceof ShortData;
    }

    /**
     * @return true if is a StringData; otherwise false
     */
    @Override
    public boolean isStringData() {
        return this instanceof StringData;
    }

    private boolean isCharacterLargeObject() {
        return this instanceof StringData && ((StringData) this).isLargeObject();
    }

    /**
     * @return true if is a TimeData; otherwise false
     */
    @Override
    public boolean isTimeData() {
        return this instanceof TimeData;
    }

    /**
     * @return true if is a TimestampData; otherwise false
     */
    @Override
    public boolean isTimestampData() {
        return this instanceof TimestampData;
    }

    /**
     * @return true if is an entity; otherwise false
     */
    @Override
    public boolean isEntity() {
        return this instanceof Entity;
    }

    /**
     * @return true if is a contextual entity; otherwise false
     */
    @Override
    public boolean isContextualEntity() {
        return this instanceof ContextualEntity;
    }

    /**
     * @return true if is a enumeration entity; otherwise false
     */
    @Override
    public boolean isEnumerationEntity() {
        return this instanceof EnumerationEntity;
    }

    /**
     * @return true if is a boolean enumeration entity; otherwise false
     */
    @Override
    public boolean isBooleanEnumerationEntity() {
        return this instanceof BooleanEnumerationEntity;
    }

    /**
     * @return true if is a non-enumeration entity; otherwise false
     */
    @Override
    public boolean isNonEnumerationEntity() {
        return isEntity() && !isEnumerationEntity();
    }

    /**
     * @return true if is a database entity; otherwise false
     */
    @Override
    public boolean isDatabaseEntity() {
        return this instanceof DatabaseEntity;
    }

    /**
     * @return true if is a persistent entity; otherwise false
     */
    @Override
    public boolean isPersistentEntity() {
        return this instanceof PersistentEntity;
    }

    /**
     * @return true if is a persistent enumeration entity; otherwise false
     */
    @Override
    public boolean isPersistentEnumerationEntity() {
        return this instanceof PersistentEnumerationEntity;
    }

    /**
     * @return true if is a persistent non-enumeration entity; otherwise false
     */
    @Override
    public boolean isPersistentNonEnumerationEntity() {
        return isPersistentEntity() && !isPersistentEnumerationEntity();
    }

    /**
     * @return true if is a overlayable entity reference; otherwise false
     */
    @Override
    public boolean isOverlayableEntityReference() {
        return false;
    }

    /**
     * @return true if is a Parameter; otherwise false
     */
    @Override
    public boolean isParameter() {
        return this instanceof Parameter
            && getDeclaringField() != null
            && getDeclaringArtifact() instanceof Operation;
    }

    public boolean isInstanceParameter() {
        if (isParameter()) {
            Operation operation = (Operation) getDeclaringArtifact();
            Parameter parameter = (Parameter) this;
            return OperationKind.INSTANCE.equals(operation.getOperationKind()) && parameter.isInstanceReferenceField();
        }
        return false;
    }

    /**
     * @return true if is a Property; otherwise false
     */
    @Override
    public boolean isProperty() {
        return this instanceof Property
            && getDeclaringField() != null
            && getDeclaringArtifact() instanceof Entity;
    }
//
//  public boolean isParameterProperty() {
//      return isProperty() && isClassInPath(Operation.class);
//  }

    private boolean isLoggableProperty() {
        return isProperty() && isLoggable();
    }

    private boolean isLoggable() {
        return this instanceof Entity ? depth() == 1 : depth() == 0;
    }

    private boolean isEnumerationEntityProperty() {
        return isProperty()
            && getDeclaringArtifact() instanceof EnumerationEntity;
    }

    @Override
    public boolean isPrimaryKeyProperty() {
        return isProperty()
            && (_annotatedWithPrimaryKey
            || getDeclaringField().equals(getDeclaringEntity().getPrimaryKeyField()));
    }

    @Override
    public boolean isSequenceProperty() {
        return isProperty()
            && (_annotatedWithSequenceProperty
            || getDeclaringField().equals(getDeclaringEntity().getSequenceField()));
    }

    @Override
    public boolean isVersionProperty() {
        return isProperty()
            && (_annotatedWithVersionProperty
            || getDeclaringField().equals(getDeclaringEntity().getVersionField()));
    }

    @Override
    public boolean isNumericKeyProperty() {
        return isProperty() && getDeclaringField().equals(getDeclaringEntity().getNumericKeyField());
    }

    @Override
    public boolean isCharacterKeyProperty() {
        return isProperty() && getDeclaringField().equals(getDeclaringEntity().getCharacterKeyField());
    }

    @Override
    public boolean isNameProperty() {
        return isProperty()
            && (_annotatedWithNameProperty
            || getDeclaringField().equals(getDeclaringEntity().getNameField()));
    }

    @Override
    public boolean isDescriptionProperty() {
        return isProperty()
            && (_annotatedWithDescriptionProperty
            || getDeclaringField().equals(getDeclaringEntity().getDescriptionField()));
    }

    @Override
    public boolean isImageProperty() {
        return isProperty()
            && (_annotatedWithImageProperty
            || getDeclaringField().equals(getDeclaringEntity().getImageField()));
    }

    @Override
    public boolean isInactiveIndicatorProperty() {
        return isProperty()
            && (_annotatedWithInactiveIndicator
            || getDeclaringField().equals(getDeclaringEntity().getInactiveIndicatorField()));
    }

    @Override
    public boolean isUrlProperty() {
        return isProperty()
            && (_annotatedWithUrlProperty
            || getDeclaringField().equals(getDeclaringEntity().getUrlField()));
    }

    @Override
    public boolean isParentProperty() {
        return isProperty()
            && (_annotatedWithParentProperty
            || getDeclaringField().equals(getDeclaringEntity().getParentField()));
    }

    @Override
    public boolean isOwnerProperty() {
        return isProperty()
            && (_annotatedWithOwnerProperty
            || getDeclaringField().equals(getDeclaringEntity().getOwnerField()));
    }

    @Override
    public boolean isUserProperty() {
        return isProperty()
            && (_annotatedWithUserProperty
            || getDeclaringField().equals(getDeclaringEntity().getUserField()));
    }

    @Override
    public boolean isSegmentProperty() {
        return isProperty()
            && (_annotatedWithSegmentProperty
            || getDeclaringField().equals(getDeclaringEntity().getSegmentField()));
    }

    @Override
    public boolean isUniqueKeyProperty() {
        return isProperty() && isUnique();
    }

    @Override
    public boolean isBusinessKeyProperty() {
        return isProperty()
            && (_annotatedWithBusinessKey
            || getDeclaringField().equals(getDeclaringEntity().getBusinessKeyField()));
    }

    @Override
    public boolean isDiscriminatorProperty() {
        return _annotatedWithDiscriminatorColumn;
    }

    @Override
    public boolean isStateProperty() {
        return _annotatedWithStateProperty;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="parameter/property path list">
    /**
     * @return the parameter path list
     */
    @Override
//  @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getParameterPathList() {
        List<Artifact> list = new ArrayList<>();
        addToParameterPathList(list, this);
        return list;
    }

    private void addToParameterPathList(List<Artifact> list, Artifact artifact) {
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        if (declaringArtifact == null || declaringArtifact instanceof Operation) {
        } else {
            addToParameterPathList(list, declaringArtifact);
        }
        list.add(artifact);
    }

    /**
     * @return the parameter property path list
     */
//  @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getParameterPropertyPathList() {
        List<Artifact> list = new ArrayList<>();
        addToParameterPropertyPathList(list, this);
        return list;
    }

    private void addToParameterPropertyPathList(List<Artifact> list, Artifact artifact) {
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        if (declaringArtifact == null || declaringArtifact instanceof Operation) {
            return;
        } else {
            addToParameterPropertyPathList(list, declaringArtifact);
        }
        list.add(artifact);
    }

    /**
     * @return the property path list
     */
    @Override
//  @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getPropertyPathList() {
        List<Artifact> list = new ArrayList<>();
        addToPropertyPathList(list, this);
        return list;
    }

    private void addToPropertyPathList(List<Artifact> list, Artifact artifact) {
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        if (declaringArtifact == null) {
            if (artifact instanceof Entity) {
            } else {
                list.add(artifact);
            }
        } else {
            addToPropertyPathList(list, declaringArtifact);
            list.add(artifact);
        }
    }

    /**
     * @return the property-parameter path list
     */
    @Override
//  @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getPropertyParameterPathList() {
        return getParameterPathList();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="master dependent">
    /**
     * @param master master
     * @return return true if this is a master-dependent property of master; false otherwise
     */
    public boolean isMasterDependentPropertyOf(EntityReference master) {
        if (isProperty()) {
            String fullName = getFullName();
            if (master == null || master.getFullName().equals(fullName)) {
                return false;
            }
            List<Property> masterDependentProperties = master.getMasterDependentProperties();
            if (masterDependentProperties != null && masterDependentProperties.contains(this)) {
                logger.trace(fullName + " is a master-dependent property of " + master.getName());
                return true;
            }
            if (isCalculable()) {
                Expression calculableValue = calculableValueExpression(getCalculableValue());
                if (referencedByExpression(calculableValue, master)) {
                    logger.trace(fullName + " is a master-dependent property of " + master.getName() + " · " + calculableValue.getDataType());
                    return true;
                }
            }
        }
        return false;
    }

    private Expression calculableValueExpression(Object calculableValueObject) {
        return calculableValueObject instanceof Primitive ? (Primitive) calculableValueObject
            : (calculableValueObject instanceof Entity) ? ((Entity) calculableValueObject).self() : null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="initial value referencing siblings">
    /**
     * @return the list of artifacts referencing this artifact in their initial value
     */
    public Map<String, ? extends DataArtifact> getInitialValueReferencingSiblings() {
        return getInitialValueReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their initial value
     */
    public Map<String, ? extends DataArtifact> getInitialValueReferencingSiblings(boolean recursively) {
        return isProperty() ? getInitialValueReferencingProperties(recursively)
            : isParameter() ? getInitialValueReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of artifacts referencing this artifact (if it is an instance parameter) in their initial value
     */
    public Map<String, ? extends DataArtifact> getInstanceParameterInitialValueReferencingSiblings() {
        return getInstanceParameterInitialValueReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact (if it is an instance parameter) in their initial value
     */
    public Map<String, ? extends DataArtifact> getInstanceParameterInitialValueReferencingSiblings(boolean recursively) {
        return isInstanceParameter() ? getInitialValueReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of parameters referencing this parameter in their initial value
     */
    @Override
    public Map<String, Parameter> getInitialValueReferencingParameters() {
        return getInitialValueReferencingParameters(false);
    }

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their initial value
     */
    @Override
    public Map<String, Parameter> getInitialValueReferencingParameters(boolean recursively) {
        Map<String, Parameter> initialValueReferencingParameters = initialValueReferencingParametersMap();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        Expression initialValue;
        for (Parameter parameter : parametersList) {
            if (!parameter.getFullName().equals(fullName)) {
                initialValue = initialValueExpression(parameter.getInitialValue());
                if (referencedByExpression(initialValue)) {
                    logger.trace("parameter " + fullName + " is referenced by the initial value expression of " + parameter.getName());
                    initialValueReferencingParameters.put(parameter.getPathString(), parameter);
                    if (recursively) {
                        initialValueReferencingParameters.putAll(parameter.getInitialValueReferencingParameters(recursively));
                    }
                }
            }
        }
        return initialValueReferencingParameters;
    }

    // @templates/jee2/web/java/pages/blocks/console/write-parameter-accessors.vm
    public Map<String, Parameter> getInitialValueReferencingParametersSortedBySequence(boolean recursively) {
        Map<String, Parameter> sorted = new LinkedHashMap<>();
        Map<String, Parameter> unsorted = getInitialValueReferencingParameters(recursively);
        List<Parameter> list = new ArrayList<>(unsorted.values());
        list = (List<Parameter>) ColUtils.sort(list, new ByParameterSequence());
        for (Parameter parameter : list) {
            sorted.put(parameter.getPathString(), parameter);
        }
        return sorted;
    }

    private final List<Parameter> _initialValueReferencingParametersList = new ArrayList<>();

    private Map<String, Parameter> initialValueReferencingParametersMap() {
        Map<String, Parameter> initialValueReferencingParameters = new LinkedHashMap<>();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        for (Parameter parameter : _initialValueReferencingParametersList) {
            if (!parameter.getFullName().equals(fullName)) {
                if (parametersList.contains(parameter)) {
                    initialValueReferencingParameters.put(parameter.getPathString(), parameter);
                }
            }
        }
        return initialValueReferencingParameters;
    }

    /**
     * @return the initial value referencing parameters list
     */
    @Override
    public List<Parameter> getInitialValueReferencingParametersList() {
        return _initialValueReferencingParametersList;
    }

    /**
     * El método addInitialValueReferencedParameters se utiliza para agregar otros parámetros de la misma operación al mapa de parámetros
     * referenciados por la expresión de valor inicial de este parámetro. Si este parámetro tiene valor inicial, su valor es reinicializado al cambiar
     * el valor de cualquiera de los parámetros referenciados al ejecutar la operación en la vista (página) de procesamiento.
     *
     * @param parameters uno o más parámetros de la misma operación
     */
    public void addInitialValueReferencedParameters(Parameter... parameters) {
        if (parameters != null) {
            for (Parameter parameter : parameters) {
                if (parameter != null) {
                    parameter.getInitialValueReferencingParametersList().add(this);
                }
            }
        }
    }

    /**
     * @return the list of properties referencing this property in their initial value
     */
    @Override
    public Map<String, Property> getInitialValueReferencingProperties() {
        return getInitialValueReferencingProperties(false);
    }

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their initial value
     */
    @Override
    public Map<String, Property> getInitialValueReferencingProperties(boolean recursively) {
        Map<String, Property> initialValueReferencingProperties = initialValueReferencingPropertiesMap();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        Expression initialValue;
        for (Property property : propertiesList) {
            if (!property.getFullName().equals(fullName)) {
                initialValue = initialValueExpression(property.getInitialValue());
                if (referencedByExpression(initialValue)) {
                    logger.trace("property " + fullName + " is referenced by the initial value expression of " + property.getName());
                    initialValueReferencingProperties.put(property.getPathString(), property);
                    if (recursively) {
                        initialValueReferencingProperties.putAll(property.getInitialValueReferencingProperties(recursively));
                    }
                }
            }
        }
        return initialValueReferencingProperties;
    }

    // @templates/jee2/web/java/pages/blocks/archetype/jsf-components.vm
    public Map<String, Property> getInitialValueReferencingPropertiesSortedBySequence(boolean recursively) {
        Map<String, Property> sorted = new LinkedHashMap<>();
        Map<String, Property> unsorted = getInitialValueReferencingProperties(recursively);
        List<Property> list = new ArrayList<>(unsorted.values());
//      list = (List<Property>) ColUtils.sort(list, new ByPropertySequence());
        list = (List<Property>) ColUtils.sort(list, new ByPropertyDisplaySortKey());
        for (Property property : list) {
            sorted.put(property.getPathString(), property);
        }
        return sorted;
    }

    private final List<Property> _initialValueReferencingPropertiesList = new ArrayList<>();

    private Map<String, Property> initialValueReferencingPropertiesMap() {
        Map<String, Property> initialValueReferencingProperties = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        for (Property property : _initialValueReferencingPropertiesList) {
            if (!property.getFullName().equals(fullName)) {
                if (propertiesList.contains(property)) {
                    initialValueReferencingProperties.put(property.getPathString(), property);
                }
            }
        }
        return initialValueReferencingProperties;
    }

    /**
     * @return the initial value referencing properties list
     */
    @Override
    public List<Property> getInitialValueReferencingPropertiesList() {
        return _initialValueReferencingPropertiesList;
    }

    /**
     * El método addInitialValueReferencedProperties se utiliza para agregar otras propiedades de la misma entidad al mapa de propiedades
     * referenciadas por la expresión de valor inicial de esta propiedad. Si esta propiedad tiene valor inicial, su valor es reinicializado al cambiar
     * el valor de cualquiera de las propiedades referenciadas al agregar o editar en las vistas (páginas) de registro.
     *
     * @param properties una o más propiedades de la misma entidad
     */
    public void addInitialValueReferencedProperties(Property... properties) {
        if (properties != null) {
            for (Property property : properties) {
                if (property != null) {
                    property.getInitialValueReferencingPropertiesList().add(this);
                }
            }
        }
    }

    private Expression initialValueExpression(Object initialValueObject) {
        return initialValueObject instanceof Expression ? (Expression) initialValueObject
            : (initialValueObject instanceof Entity) ? ((Entity) initialValueObject).self() : null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="max value referencing siblings">
    /**
     * @return the list of artifacts referencing this artifact in their max value
     */
    public Map<String, ? extends DataArtifact> getMaxValueReferencingSiblings() {
        return getMaxValueReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their max value
     */
    public Map<String, ? extends DataArtifact> getMaxValueReferencingSiblings(boolean recursively) {
        return isProperty() ? getMaxValueReferencingProperties(recursively)
            : isParameter() ? getMaxValueReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of parameters referencing this parameter in their max value
     */
    @Override
    public Map<String, Parameter> getMaxValueReferencingParameters() {
        return getMaxValueReferencingParameters(false);
    }

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their max value
     */
    @Override
    public Map<String, Parameter> getMaxValueReferencingParameters(boolean recursively) {
        Map<String, Parameter> maxValueReferencingParameters = new LinkedHashMap<>();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        Expression maxValue;
        for (Parameter parameter : parametersList) {
            if (!parameter.getFullName().equals(fullName)) {
                maxValue = maxValueExpression(parameter);
                if (referencedByExpression(maxValue)) {
                    logger.trace("parameter " + fullName + " is referenced by the max value expression of " + parameter.getName());
                    maxValueReferencingParameters.put(parameter.getPathString(), parameter);
                    if (recursively) {
                        maxValueReferencingParameters.putAll(parameter.getMaxValueReferencingParameters(recursively));
                    }
                }
            }
        }
        return maxValueReferencingParameters;
    }

    /**
     * @return the list of properties referencing this property in their max value
     */
    @Override
    public Map<String, Property> getMaxValueReferencingProperties() {
        return getMaxValueReferencingProperties(false);
    }

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their max value
     */
    @Override
    public Map<String, Property> getMaxValueReferencingProperties(boolean recursively) {
        Map<String, Property> maxValueReferencingProperties = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        Expression maxValue;
        for (Property property : propertiesList) {
            if (!property.getFullName().equals(fullName)) {
                maxValue = maxValueExpression(property);
                if (referencedByExpression(maxValue)) {
                    logger.trace("property " + fullName + " is referenced by the max value expression of " + property.getName());
                    maxValueReferencingProperties.put(property.getPathString(), property);
                    if (recursively) {
                        maxValueReferencingProperties.putAll(property.getMaxValueReferencingProperties(recursively));
                    }
                }
            }
        }
        return maxValueReferencingProperties;
    }

    private Expression maxValueExpression(DataArtifact da) {
        if (da instanceof IntervalizedArtifact ia) {
            Object maxValueObject = ia.getMaxValue();
            return maxValueObject instanceof Expression ? (Expression) maxValueObject
                : (maxValueObject instanceof Entity) ? ((Entity) maxValueObject).self() : null;
        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="min value referencing siblings">
    /**
     * @return the list of artifacts referencing this artifact in their min value
     */
    public Map<String, ? extends DataArtifact> getMinValueReferencingSiblings() {
        return getMinValueReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their min value
     */
    public Map<String, ? extends DataArtifact> getMinValueReferencingSiblings(boolean recursively) {
        return isProperty() ? getMinValueReferencingProperties(recursively)
            : isParameter() ? getMinValueReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of parameters referencing this parameter in their min value
     */
    @Override
    public Map<String, Parameter> getMinValueReferencingParameters() {
        return getMinValueReferencingParameters(false);
    }

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their min value
     */
    @Override
    public Map<String, Parameter> getMinValueReferencingParameters(boolean recursively) {
        Map<String, Parameter> minValueReferencingParameters = new LinkedHashMap<>();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        Expression minValue;
        for (Parameter parameter : parametersList) {
            if (!parameter.getFullName().equals(fullName)) {
                minValue = minValueExpression(parameter);
                if (referencedByExpression(minValue)) {
                    logger.trace("parameter " + fullName + " is referenced by the min value expression of " + parameter.getName());
                    minValueReferencingParameters.put(parameter.getPathString(), parameter);
                    if (recursively) {
                        minValueReferencingParameters.putAll(parameter.getMinValueReferencingParameters(recursively));
                    }
                }
            }
        }
        return minValueReferencingParameters;
    }

    /**
     * @return the list of properties referencing this property in their min value
     */
    @Override
    public Map<String, Property> getMinValueReferencingProperties() {
        return getMinValueReferencingProperties(false);
    }

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their min value
     */
    @Override
    public Map<String, Property> getMinValueReferencingProperties(boolean recursively) {
        Map<String, Property> minValueReferencingProperties = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        Expression minValue;
        for (Property property : propertiesList) {
            if (!property.getFullName().equals(fullName)) {
                minValue = minValueExpression(property);
                if (referencedByExpression(minValue)) {
                    logger.trace("property " + fullName + " is referenced by the min value expression of " + property.getName());
                    minValueReferencingProperties.put(property.getPathString(), property);
                    if (recursively) {
                        minValueReferencingProperties.putAll(property.getMinValueReferencingProperties(recursively));
                    }
                }
            }
        }
        return minValueReferencingProperties;
    }

    private Expression minValueExpression(DataArtifact da) {
        if (da instanceof IntervalizedArtifact ia) {
            Object minValueObject = ia.getMinValue();
            return minValueObject instanceof Expression ? (Expression) minValueObject
                : (minValueObject instanceof Entity) ? ((Entity) minValueObject).self() : null;
        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="modifying filter referencing siblings">
    /**
     * @return the list of artifacts referencing this artifact in their modifying filter
     */
    public Map<String, ? extends DataArtifact> getModifyingFilterReferencingSiblings() {
        return getModifyingFilterReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their modifying filter
     */
    public Map<String, ? extends DataArtifact> getModifyingFilterReferencingSiblings(boolean recursively) {
        /*
        return isProperty() ? getModifyingFilterReferencingProperties(recursively) : new LinkedHashMap<>();
        /**/
        return isProperty() ? getModifyingFilterReferencingProperties(recursively)
            : isParameter() ? getModifyingFilterReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of parameters referencing this parameter in their modifying filter
     */
    @Override
    public Map<String, Parameter> getModifyingFilterReferencingParameters() {
        return getModifyingFilterReferencingParameters(false);
    }

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their modifying filter
     */
    @Override
    public Map<String, Parameter> getModifyingFilterReferencingParameters(boolean recursively) {
        Map<String, Parameter> modifyingFilterReferencingParameters = new LinkedHashMap<>();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        BooleanExpression modifyingFilter;
        for (Parameter parameter : parametersList) {
            if (!parameter.getFullName().equals(fullName)) {
                modifyingFilter = parameter.getModifyingFilter();
                if (referencedByExpression(modifyingFilter)) {
                    logger.trace("parameter " + fullName + " is referenced by the modifying filter of " + parameter.getName());
                    modifyingFilterReferencingParameters.put(parameter.getPathString(), parameter);
                    if (recursively) {
                        modifyingFilterReferencingParameters.putAll(parameter.getModifyingFilterReferencingParameters(recursively));
                    }
                }
            }
        }
        return modifyingFilterReferencingParameters;
    }

    /**
     * @return the list of properties referencing this property in their modifying filter
     */
    @Override
    public Map<String, Property> getModifyingFilterReferencingProperties() {
        return getModifyingFilterReferencingProperties(false);
    }

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their modifying filter
     */
    @Override
    public Map<String, Property> getModifyingFilterReferencingProperties(boolean recursively) {
        Map<String, Property> modifyingFilterReferencingProperties = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        BooleanExpression modifyingFilter;
        for (Property property : propertiesList) {
            if (!property.getFullName().equals(fullName)) {
                modifyingFilter = property.getModifyingFilter();
                if (referencedByExpression(modifyingFilter)) {
                    logger.trace("property " + fullName + " is referenced by the modifying filter of " + property.getName());
                    modifyingFilterReferencingProperties.put(property.getPathString(), property);
                    if (recursively) {
                        modifyingFilterReferencingProperties.putAll(property.getModifyingFilterReferencingProperties(recursively));
                    }
                }
            }
        }
        return modifyingFilterReferencingProperties;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="rendering filter referencing siblings">
    /**
     * @return the list of artifacts referencing this artifact in their rendering filter
     */
    public Map<String, ? extends DataArtifact> getRenderingFilterReferencingSiblings() {
        return getRenderingFilterReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their rendering filter
     */
    public Map<String, ? extends DataArtifact> getRenderingFilterReferencingSiblings(boolean recursively) {
        return isProperty() ? getRenderingFilterReferencingProperties(recursively)
            : isParameter() ? getRenderingFilterReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of artifacts referencing this artifact in their rendering filter if such filter is read-only
     */
    public Map<String, ? extends DataArtifact> getReadingRenderingFilterReferencingSiblings() {
        return getReadingRenderingFilterReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their rendering filter if such filter is read-only
     */
    public Map<String, ? extends DataArtifact> getReadingRenderingFilterReferencingSiblings(boolean recursively) {
        return isProperty() ? getRenderingFilterReferencingProperties(recursively, true)
            : isParameter() ? getRenderingFilterReferencingParameters(recursively, true) : new LinkedHashMap<>();
    }

    /**
     * @return the list of artifacts referencing this artifact in their rendering filter if such filter is read-write
     */
    public Map<String, ? extends DataArtifact> getWritingRenderingFilterReferencingSiblings() {
        return getWritingRenderingFilterReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their rendering filter if such filter is read-write
     */
    public Map<String, ? extends DataArtifact> getWritingRenderingFilterReferencingSiblings(boolean recursively) {
        return isProperty() ? getRenderingFilterReferencingProperties(recursively, false)
            : isParameter() ? getRenderingFilterReferencingParameters(recursively, false) : new LinkedHashMap<>();
    }

    /**
     * @return the list of parameters referencing this parameter in their rendering filter
     */
    @Override
    public Map<String, Parameter> getRenderingFilterReferencingParameters() {
        return getRenderingFilterReferencingParameters(false);
    }

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their rendering filter
     */
    @Override
    public Map<String, Parameter> getRenderingFilterReferencingParameters(boolean recursively) {
        return getRenderingFilterReferencingParameters(recursively, null);
    }

    /**
     * @param recursively recursively
     * @param readOnly true, to process read-only filters; false, to process read-write filters; null, to process all filters
     * @return the list of parameters referencing this parameter in their rendering filter
     */
    @Override
    public Map<String, Parameter> getRenderingFilterReferencingParameters(boolean recursively, Boolean readOnly) {
        Map<String, Parameter> renderingFilterReferencingParameters = new LinkedHashMap<>();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        BooleanExpression renderingFilter;
        for (Parameter parameter : parametersList) {
            if (!parameter.getFullName().equals(fullName)) {
                renderingFilter = parameter.getRenderingFilter();
                if (checkRenderingFilter(readOnly, parameter.isRenderingFilterReadOnly())) {
                    if (referencedByExpression(renderingFilter)) {
                        logger.trace("parameter " + fullName + " is referenced by the rendering filter of " + parameter.getName());
                        renderingFilterReferencingParameters.put(parameter.getPathString(), parameter);
                        if (recursively) {
                            renderingFilterReferencingParameters.putAll(parameter.getRenderingFilterReferencingParameters(recursively, readOnly));
                        }
                    }
                }
            }
        }
        return renderingFilterReferencingParameters;
    }

    /**
     * @return the list of properties referencing this property in their rendering filter
     */
    @Override
    public Map<String, Property> getRenderingFilterReferencingProperties() {
        return getRenderingFilterReferencingProperties(false);
    }

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their rendering filter
     */
    @Override
    public Map<String, Property> getRenderingFilterReferencingProperties(boolean recursively) {
        return getRenderingFilterReferencingProperties(recursively, null);
    }

    /**
     * @param recursively recursively
     * @param readOnly true, to process read-only filters; false, to process read-write filters; null, to process all filters
     * @return the list of properties referencing this property in their rendering filter
     */
    @Override
    public Map<String, Property> getRenderingFilterReferencingProperties(boolean recursively, Boolean readOnly) {
        Map<String, Property> renderingFilterReferencingProperties = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        BooleanExpression renderingFilter;
        for (Property property : propertiesList) {
            if (!property.getFullName().equals(fullName)) {
                renderingFilter = property.getRenderingFilter();
                if (checkRenderingFilter(readOnly, property.isRenderingFilterReadOnly())) {
                    if (referencedByExpression(renderingFilter)) {
                        logger.trace("property " + fullName + " is referenced by the rendering filter of " + property.getName());
                        renderingFilterReferencingProperties.put(property.getPathString(), property);
                        if (recursively) {
                            renderingFilterReferencingProperties.putAll(property.getRenderingFilterReferencingProperties(recursively, readOnly));
                        }
                    }
                }
            }
        }
        return renderingFilterReferencingProperties;
    }

    private boolean checkRenderingFilter(Boolean readOnly, boolean renderingFilterReadOnly) {
        return readOnly == null || readOnly == renderingFilterReadOnly;
    }

    /**
     * @return the list of artifacts referencing this artifact in their rendering filter
     */
    public Map<String, EntityCollection> getRenderingFilterReferencingCollections() {
        Map<String, EntityCollection> renderingFilterReferencingCollections = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<EntityCollection> entityCollectionsList = declaringEntity.getEntityCollectionsList();
        BooleanExpression renderingFilter;
        for (EntityCollection collection : entityCollectionsList) {
            renderingFilter = collection.getRenderingFilter();
            if (referencedByExpression(renderingFilter)) {
                logger.trace("property " + fullName + " is referenced by the rendering filter of " + collection.getName());
                renderingFilterReferencingCollections.put(collection.getPathString(), collection);
            }
        }
        return renderingFilterReferencingCollections;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="requiring filter referencing siblings">
    /**
     * @return the list of artifacts referencing this artifact in their requiring filter
     */
    public Map<String, ? extends DataArtifact> getRequiringFilterReferencingSiblings() {
        return getRequiringFilterReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their requiring filter
     */
    public Map<String, ? extends DataArtifact> getRequiringFilterReferencingSiblings(boolean recursively) {
        return isProperty() ? getRequiringFilterReferencingProperties(recursively)
            : isParameter() ? getRequiringFilterReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of parameters referencing this parameter in their requiring filter
     */
    @Override
    public Map<String, Parameter> getRequiringFilterReferencingParameters() {
        return getRequiringFilterReferencingParameters(false);
    }

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their requiring filter
     */
    @Override
    public Map<String, Parameter> getRequiringFilterReferencingParameters(boolean recursively) {
        Map<String, Parameter> requiringFilterReferencingParameters = new LinkedHashMap<>();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        BooleanExpression requiringFilter;
        for (Parameter parameter : parametersList) {
            if (!parameter.getFullName().equals(fullName)) {
                requiringFilter = parameter.getRequiringFilter();
                if (referencedByExpression(requiringFilter)) {
                    logger.trace("parameter " + fullName + " is referenced by the requiring filter of " + parameter.getName());
                    requiringFilterReferencingParameters.put(parameter.getPathString(), parameter);
                    if (recursively) {
                        requiringFilterReferencingParameters.putAll(parameter.getRequiringFilterReferencingParameters(recursively));
                    }
                }
            }
        }
        return requiringFilterReferencingParameters;
    }

    /**
     * @return the list of properties referencing this property in their requiring filter
     */
    @Override
    public Map<String, Property> getRequiringFilterReferencingProperties() {
        return getRequiringFilterReferencingProperties(false);
    }

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their requiring filter
     */
    @Override
    public Map<String, Property> getRequiringFilterReferencingProperties(boolean recursively) {
        Map<String, Property> requiringFilterReferencingProperties = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        BooleanExpression requiringFilter;
        for (Property property : propertiesList) {
            if (!property.getFullName().equals(fullName)) {
                requiringFilter = property.getRequiringFilter();
                if (referencedByExpression(requiringFilter)) {
                    logger.trace("property " + fullName + " is referenced by the requiring filter of " + property.getName());
                    requiringFilterReferencingProperties.put(property.getPathString(), property);
                    if (recursively) {
                        requiringFilterReferencingProperties.putAll(property.getRequiringFilterReferencingProperties(recursively));
                    }
                }
            }
        }
        return requiringFilterReferencingProperties;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="search query filter referencing siblings">
    /**
     * @return the list of artifacts referencing this artifact in their search query filter
     */
    public Map<String, ? extends DataArtifact> getSearchQueryFilterReferencingSiblings() {
        return getSearchQueryFilterReferencingSiblings(false);
    }

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their search query filter
     */
    public Map<String, ? extends DataArtifact> getSearchQueryFilterReferencingSiblings(boolean recursively) {
        return isProperty() ? getSearchQueryFilterReferencingProperties(recursively)
            : isParameter() ? getSearchQueryFilterReferencingParameters(recursively) : new LinkedHashMap<>();
    }

    /**
     * @return the list of parameters referencing this parameter in their search query filter
     */
    @Override
    public Map<String, Parameter> getSearchQueryFilterReferencingParameters() {
        return getSearchQueryFilterReferencingParameters(false);
    }

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their search query filter
     */
    @Override
    public Map<String, Parameter> getSearchQueryFilterReferencingParameters(boolean recursively) {
        Map<String, Parameter> searchQueryFilterReferencingParameters = new LinkedHashMap<>();
        String fullName = getFullName();
        Operation declaringOperation = getDeclaringOperation();
        List<Parameter> parametersList = declaringOperation.getParametersList();
        Entity entity;
        BooleanExpression searchQueryFilter;
        for (Parameter parameter : parametersList) {
            if (parameter instanceof Entity && !parameter.getFullName().equals(fullName)) {
                entity = (Entity) parameter;
                searchQueryFilter = entity.getSearchQueryFilter();
                if (referencedByExpression(searchQueryFilter)) {
                    logger.trace("parameter " + fullName + " is referenced by the search query filter of " + parameter.getName());
                    searchQueryFilterReferencingParameters.put(parameter.getPathString(), parameter);
                    if (recursively) {
                        searchQueryFilterReferencingParameters.putAll(parameter.getSearchQueryFilterReferencingParameters(recursively));
                    }
                }
            }
        }
        return searchQueryFilterReferencingParameters;
    }

    /**
     * @return the list of properties referencing this property in their search query filter
     */
    @Override
    public Map<String, Property> getSearchQueryFilterReferencingProperties() {
        return getSearchQueryFilterReferencingProperties(false);
    }

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their search query filter
     */
    @Override
    public Map<String, Property> getSearchQueryFilterReferencingProperties(boolean recursively) {
        Map<String, Property> searchQueryFilterReferencingProperties = new LinkedHashMap<>();
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        List<Property> propertiesList = declaringEntity.getPropertiesList();
        Entity entity;
        BooleanExpression searchQueryFilter;
        for (Property property : propertiesList) {
            if (property instanceof Entity && !property.getFullName().equals(fullName)) {
                entity = (Entity) property;
                searchQueryFilter = entity.getSearchQueryFilter();
                if (referencedByExpression(searchQueryFilter)) {
                    logger.trace("property " + fullName + " is referenced by the search query filter of " + property.getName());
                    searchQueryFilterReferencingProperties.put(property.getPathString(), property);
                    if (recursively) {
                        searchQueryFilterReferencingProperties.putAll(property.getSearchQueryFilterReferencingProperties(recursively));
                    }
                }
            }
        }
        return searchQueryFilterReferencingProperties;
    }
    // </editor-fold>

    private boolean referencedByExpression(Expression expression) {
        return referencedByExpression(expression, this);
    }

    private boolean referencedByExpression(Expression expression, Artifact artifact) {
        if (expression != null) {
            String pathString = artifact == null ? getPathString() : artifact.getPathString();
            String pathPrefix = pathString + ".";
//          return expression.getReferencedColumnsMap().containsKey(pathString);
            Set<String> keys = expression.getReferencedColumnsMap().keySet();
            for (String key : keys) {
                if (key.equals(pathString) || key.startsWith(pathPrefix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends DataArtifactWrapper> getDefaultWrapperClass() {
        return DataArtifactWrapper.class;
    }

    /**
     * @return the calculable value
     */
    @Override
    public Object getCalculableValue() {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="filter tag fields, getters and setters">
    /**
     * @return the rendering filter tag
     */
//  @Override
    public String getRenderingFilterTag() {
        return getLocalizedRenderingFilterTag(null);
    }

    /**
     * El método setRenderingFilterTag se utiliza para establecer la descripción del filtro de presentación de propiedades y parámetros que se
     * almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de presentación de la propiedad o el parámetro
     */
//  @Override
    public void setRenderingFilterTag(String tag) {
        setLocalizedRenderingFilterTag(null, tag);
    }

    /**
     * @return the requiring filter tag
     */
//  @Override
    public String getRequiringFilterTag() {
        return getLocalizedRequiringFilterTag(null);
    }

    /**
     * El método setRequiringFilterTag se utiliza para establecer la descripción del filtro de obligatoriedad de propiedades y parámetros que se
     * almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de obligatoriedad de la propiedad o el parámetro
     */
//  @Override
    public void setRequiringFilterTag(String tag) {
        setLocalizedRequiringFilterTag(null, tag);
    }

    /**
     * @return the modifying filter tag
     */
//  @Override
    public String getModifyingFilterTag() {
        return getLocalizedModifyingFilterTag(null);
    }

    /**
     * El método setModifyingFilterTag se utiliza para establecer la descripción del filtro de modificación de propiedades y parámetros que se
     * almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de modificación de la propiedad o el parámetro
     */
//  @Override
    public void setModifyingFilterTag(String tag) {
        setLocalizedModifyingFilterTag(null, tag);
    }

    /**
     * @return the nullifying filter tag
     */
//  @Override
    public String getNullifyingFilterTag() {
        return getLocalizedNullifyingFilterTag(null);
    }

    /**
     * El método setNullifyingFilterTag se utiliza para establecer la descripción del filtro de anulación de propiedades y parámetros que se almacena
     * en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de anulación de la propiedad o el parámetro
     */
//  @Override
    public void setNullifyingFilterTag(String tag) {
        setLocalizedNullifyingFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedRenderingFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the rendering filter tag
     */
//  @Override
    public String getLocalizedRenderingFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedRenderingFilterTag.get(l);
    }

    /**
     * El método setLocalizedRenderingFilterTag se utiliza para establecer la descripción del filtro de presentación de propiedades y parámetros que
     * se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el
     * usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de presentación de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedRenderingFilterTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedRenderingFilterTag.remove(l);
        } else {
            _localizedRenderingFilterTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the requiring filter as a label or identifier.
     */
    private final Map<Locale, String> _localizedRequiringFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the requiring filter tag
     */
//  @Override
    public String getLocalizedRequiringFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedRequiringFilterTag.get(l);
    }

    /**
     * El método setLocalizedRequiringFilterTag se utiliza para establecer la descripción del filtro de obligatoriedad de propiedades y parámetros que
     * se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el
     * usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de obligatoriedad de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedRequiringFilterTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedRequiringFilterTag.remove(l);
        } else {
            _localizedRequiringFilterTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the modifying filter as a label or identifier.
     */
    private final Map<Locale, String> _localizedModifyingFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the modifying filter tag
     */
//  @Override
    public String getLocalizedModifyingFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedModifyingFilterTag.get(l);
    }

    /**
     * El método setLocalizedModifyingFilterTag se utiliza para establecer la descripción del filtro de modificación de propiedades y parámetros que
     * se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el
     * usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de modificación de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedModifyingFilterTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedModifyingFilterTag.remove(l);
        } else {
            _localizedModifyingFilterTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the nullifying filter as a label or identifier.
     */
    private final Map<Locale, String> _localizedNullifyingFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the nullifying filter tag
     */
//  @Override
    public String getLocalizedNullifyingFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedNullifyingFilterTag.get(l);
    }

    /**
     * El método setLocalizedNullifyingFilterTag se utiliza para establecer la descripción del filtro de anulación de propiedades y parámetros que se
     * almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario
     * no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de anulación de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedNullifyingFilterTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedNullifyingFilterTag.remove(l);
        } else {
            _localizedNullifyingFilterTag.put(l, tag);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="value tag fields, getters and setters">
    /**
     * @return the calculable value tag
     */
    @Override
    public String getCalculableValueTag() {
        return getLocalizedCalculableValueTag(null);
    }

    /**
     * El método setCalculableValueTag se utiliza para establecer la descripción del valor calculable de propiedades y parámetros que se almacena en
     * el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor calculable de la propiedad o el parámetro
     */
    @Override
    public void setCalculableValueTag(String tag) {
        setLocalizedCalculableValueTag(null, tag);
    }

    /**
     * @return the initial value tag
     */
    @Override
    public String getInitialValueTag() {
        return getLocalizedInitialValueTag(null);
    }

    /**
     * El método setInitialValueTag se utiliza para establecer la descripción del valor inicial de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor inicial de la propiedad o el parámetro
     */
    @Override
    public void setInitialValueTag(String tag) {
        setLocalizedInitialValueTag(null, tag);
    }

    /**
     * @return the default value tag
     */
    @Override
    public String getDefaultValueTag() {
        return getLocalizedDefaultValueTag(null);
    }

    /**
     * El método setDefaultValueTag se utiliza para establecer la descripción del valor por omisión de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor por omisión de la propiedad o el parámetro
     */
    @Override
    public void setDefaultValueTag(String tag) {
        setLocalizedDefaultValueTag(null, tag);
    }

    /**
     * @return the current value tag
     */
    @Override
    public String getCurrentValueTag() {
        return getLocalizedCurrentValueTag(null);
    }

    /**
     * El método setCurrentValueTag se utiliza para establecer la descripción del valor actual de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor actual de la propiedad o el parámetro
     */
    @Override
    public void setCurrentValueTag(String tag) {
        setLocalizedCurrentValueTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the value expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedCalculableValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the calculable value tag
     */
//  @Override
    public String getLocalizedCalculableValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedCalculableValueTag.get(l);
    }

    /**
     * El método setLocalizedCalculableValueTag se utiliza para establecer la descripción del valor calculable de propiedades y parámetros que se
     * almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario
     * no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor calculable de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedCalculableValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedCalculableValueTag.remove(l);
        } else {
            _localizedCalculableValueTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the initial value as a label or identifier.
     */
    private final Map<Locale, String> _localizedInitialValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the initial value tag
     */
//  @Override
    public String getLocalizedInitialValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedInitialValueTag.get(l);
    }

    /**
     * El método setLocalizedInitialValueTag se utiliza para establecer la descripción del valor inicial de propiedades y parámetros que se almacena
     * en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor inicial de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedInitialValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedInitialValueTag.remove(l);
        } else {
            _localizedInitialValueTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the default value as a label or identifier.
     */
    private final Map<Locale, String> _localizedDefaultValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the default value tag
     */
//  @Override
    public String getLocalizedDefaultValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedDefaultValueTag.get(l);
    }

    /**
     * El método setLocalizedDefaultValueTag se utiliza para establecer la descripción del valor por omisión de propiedades y parámetros que se
     * almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario
     * no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor por omisión de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedDefaultValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedDefaultValueTag.remove(l);
        } else {
            _localizedDefaultValueTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the current value as a label or identifier.
     */
    private final Map<Locale, String> _localizedCurrentValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the current value tag
     */
//  @Override
    public String getLocalizedCurrentValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedCurrentValueTag.get(l);
    }

    /**
     * El método setLocalizedCurrentValueTag se utiliza para establecer la descripción del valor actual de propiedades y parámetros que se almacena en
     * el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor actual de la propiedad o el parámetro
     */
//  @Override
    public void setLocalizedCurrentValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedCurrentValueTag.remove(l);
        } else {
            _localizedCurrentValueTag.put(l, tag);
        }
    }
    // </editor-fold>

    protected boolean validCalculableValue(Object object) {
        if (isParameter()) {
            String message = "failed to set calculable value of " + getFullName();
            message += "; method setCalculableValueExpression is not valid for parameters; setting ignored";
            logger.warn(message);
            Project.increaseParserWarningCount();
            return true;
        }
        if (object == null) {
            if (isLoggable()) {
                logger.error("null is not a valid calculable value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (incompatibleValueType(object)) {
            if (isLoggable()) {
                logger.error(incompatibleValueTypeMessage(object, "calculable"));
                Project.increaseParserErrorCount();
            }
            return false;
        }
        return true;
    }

    protected boolean validInitialValue(Object object) {
        boolean log = isParameter() || isLoggableProperty();
        // <editor-fold defaultstate="collapsed" desc="comment">
//      if (isParameterProperty()) {
//          return true;
//      }
        /* commented since 20200203
        if (isCalculable()) {
            if (log) {
                logger.error(getFullName() + " is calculable and therefore does not support initial value");
                Project.increaseParserErrorCount();
            }
            return false;
        }
        /**/
        // </editor-fold>
        if (object == null) {
            if (log) {
                logger.error("null is not a valid initial value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (incompatibleValueType(object)) {
            if (isLoggable()) {
                logger.error(incompatibleValueTypeMessage(object, "initial"));
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (object instanceof SpecialTemporalValue && !validSpecialTemporalValue((SpecialTemporalValue) object)) {
            if (log) {
                logger.error(object + " is not a valid initial value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        // <editor-fold defaultstate="collapsed" desc="comment">
        /*
        if (isParameter()) {
            if (object instanceof Instance) {
            } else if (object instanceof Artifact) {
                if (log) {
                    Artifact artifact = (Artifact) object;
                    logger.error(artifact.getFullName() + " is not a valid initial value for parameter " + getFullName());
                    Project.increaseParserErrorCount();
                }
                return false;
            } else if (object instanceof NamedValue && this instanceof Entity) {
                if (log) {
                    NamedValue namedValue = (NamedValue) object;
                    logger.error(namedValue.name() + " is not a valid initial value for parameter " + getFullName());
                    Project.increaseParserErrorCount();
                }
                return false;
            }
        }
        **/
        // </editor-fold>
        return true;
    }

    protected boolean validDefaultValue(Object object) {
        boolean log = isParameter() || isLoggableProperty();
        // <editor-fold defaultstate="collapsed" desc="comment">
//      if (isParameterProperty()) {
//          return true;
//      }
        // </editor-fold>
        if (isCalculable()) {
            if (log) {
                logger.error(getFullName() + " is calculable and therefore does not support default value");
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (object == null) {
            if (log) {
                logger.error("null is not a valid default value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (incompatibleValueType(object)) {
            if (isLoggable()) {
                logger.error(incompatibleValueTypeMessage(object, "default"));
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (object instanceof SpecialTemporalValue && !validSpecialTemporalValue((SpecialTemporalValue) object)) {
            if (log) {
                logger.error(object + " is not a valid default value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (TLC.getProject().isDatabaseDefaultValuesMustBeSingleEntityExpression()) {
            Entity thisDeclaringEntity = getDeclaringEntity();
            if (thisDeclaringEntity != null) {
                if (object instanceof Primitive primitive) {
                    Entity thatDeclaringEntity = primitive.getDeclaringEntity();
                    if (thatDeclaringEntity != null && thatDeclaringEntity != thisDeclaringEntity) {
                        setDefaultCheckpointToUserInterface(log);
                    }
                } else if (object instanceof Expression expression) {
                    if (!expression.isSingleEntityExpression(thisDeclaringEntity)) {
                        setDefaultCheckpointToUserInterface(log);
                    }
                }
            }
        }
        return true;
    }

    private void setDefaultCheckpointToUserInterface(boolean log) {
        if (Checkpoint.UNSPECIFIED.equals(defaultCheckpoint())) {
            setDefaultCheckpoint(Checkpoint.USER_INTERFACE);
            if (log) {
                logger.warn(getFullName() + " default value checkpoint set to USER_INTERFACE");
                Project.increaseParserWarningCount();
            }
        }
    }

    protected boolean validCurrentValue(Object object) {
        boolean log = isParameter() || isLoggableProperty();
        if (object == null) {
            if (log) {
                logger.error("null is not a valid current value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (incompatibleValueType(object)) {
            if (isLoggable()) {
                logger.error(incompatibleValueTypeMessage(object, "current"));
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (object instanceof SpecialTemporalValue && !validSpecialTemporalValue((SpecialTemporalValue) object)) {
            if (log) {
                logger.error(object + " is not a valid current value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        return true;
    }

    protected boolean validMinimumValue(Object object) {
        boolean log = isParameter() || depth() == 0;
        if (object == null) {
            if (log) {
                logger.error("null is not a valid minimum value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (incompatibleValueType(object)) {
            if (isLoggable()) {
                logger.error(incompatibleValueTypeMessage(object, "minimum"));
                Project.increaseParserErrorCount();
            }
            return false;
        }
        return true;
    }

    protected boolean validMaximumValue(Object object) {
        boolean log = isParameter() || depth() == 0;
        if (object == null) {
            if (log) {
                logger.error("null is not a valid maximum value for " + getFullName());
                Project.increaseParserErrorCount();
            }
            return false;
        }
        if (incompatibleValueType(object)) {
            if (isLoggable()) {
                logger.error(incompatibleValueTypeMessage(object, "maximum"));
                Project.increaseParserErrorCount();
            }
            return false;
        }
        return true;
    }

    protected boolean validSpecialTemporalValue(SpecialTemporalValue value) {
        return switch (value) {
            case CURRENT_DATE ->
                isDateData() || isTimestampData();
            case CURRENT_TIME ->
                isTimeData() || isTimestampData();
            case CURRENT_TIMESTAMP ->
                isTimestampData();
            default ->
                isTemporalPrimitive();
        };
    }

    private boolean incompatibleValueType(Object object) { // since 20201208
        if (_dataType != null && object instanceof TypedArtifact) {
            Class<?> dataType = ((TypedArtifact) object).getDataType();
            if (dataType != null && !dataType.equals(_dataType)) {
                if (Entity.class.isAssignableFrom(dataType)) {
                    return !_dataType.isAssignableFrom(dataType) && !sameEntitySimpleName(dataType);
                }
                Class<?>[] types = {Boolean.class, String.class, Number.class, java.util.Date.class};
                for (Class<?> type : types) {
                    if (type.isAssignableFrom(dataType)) {
                        return !type.isAssignableFrom(_dataType);
                    }
                }
            }
        }
        return false;
    }

    private boolean sameEntitySimpleName(Class<?> dataType) { // since 20201210
        return Entity.class.isAssignableFrom(_dataType) && dataType.getSimpleName().equals(_dataType.getSimpleName());
    }

    private String incompatibleValueTypeMessage(Object object, String adjective) { // since 20201208
        TypedArtifact typedArtifact = (TypedArtifact) object;
        Class<?> objClass = typedArtifact.getClass();
        Class<?> dataType = typedArtifact.getDataType();
        String objectName = typedArtifact.getPartialName();
        String fee = "the value of " + (objClass.equals(dataType) ? "" : objClass.getName() + " ");
        String faa = "(" + dataType.getName() + ") " + (StringUtils.isBlank(objectName) ? "" : objectName + " ");
        String foo = "is not a valid " + (StringUtils.isBlank(adjective) ? "" : adjective + " ");
        String fum = "value for (" + _dataType.getName() + ") " + getFullName();
        return fee + faa + foo + fum;
    }

    /**
     * @return the table column's entity name
     */
    @Override
    public String getTableColumnEntityName() {
        Entity pent = isProperty() ? getDeclaringFieldPersistentEntityTableRoot() : null;
        return pent == null ? null : tableColumnEntityName(pent);
    }

    private String tableColumnEntityName(Entity entity) {
        Entity base = entity.getBaseRoot();
        PersistentEntity pent = base instanceof PersistentEntity ? (PersistentEntity) base : null;
        InheritanceMappingStrategy ims = pent == null ? null : pent.getInheritanceMappingStrategy();
        return InheritanceMappingStrategy.SINGLE_TABLE.equals(ims) ? tableColumnEntityName(pent) : entity.getRoot().getName();
    }

    /**
     * @return the property size in pixels
     */
    @Override
    public int getPixels() {
        if (isEnumerationEntityPrimaryKey()) {
            return 48;
        } else if (isEntity() || isHiddenField() || isBinaryData() || isPassword()) {
            return 0;
        } else if (this instanceof BooleanPrimitive) {
            return 48; // 50/1000
        } else if (this instanceof CharacterData) {
            return 48; // 50/1000
        } else if (this instanceof StringData data) {
            EncodingType encoding = data.getEncodingType();
            if (encoding != null && !encoding.equals(EncodingType.UNSPECIFIED)) {
                return 0;
            }
            if (isVariantStringField()) {
                return 120; // 125/1000
            }
            Integer maxLength = data.getMaxLength();
            if (maxLength == null) {
                return 384; // 400/1000
            } else if (maxLength > 500) {
                return 384; // 400/1000
            } else if (maxLength > 100) {
                return 240; // 250/1000
            } else if (maxLength > 50) {
                return 192; // 200/1000
            } else if (maxLength > 20) {
                return 144; // 150/1000
            } else if (maxLength > 10) {
                return 120; // 125/1000
            } else {
                return 72; // 75/1000
            }
        } else if (this instanceof ByteData || this instanceof ShortData || this instanceof IntegerData) {
            return 72; // 75/1000
        } else if (this instanceof LongData) {
            return 128;
        } else if (this instanceof NumericPrimitive) {
            return 120; // 125/1000
        } else if (this instanceof TimestampData) {
            return 120; // 125/1000
        } else if (this instanceof TemporalPrimitive) {
            return 72; // 75/1000
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnPixels() {
        return isEnumerationEntityPrimaryKey() || isColumnField() ? getPixels() : 0;
    }

    private boolean isEnumerationEntityPrimaryKey() {
        return isPrimaryKeyProperty() && getDeclaringEntity() instanceof EnumerationEntity;
    }

    public boolean isSinglePropertyOfUniqueKey() {
        /* until 05/12/2023
        Entity root = getDeclaringEntityRoot();
        if (root != null) {
            List<Key> keys = root.getKeysList();
            for (Key key : keys) {
                if (key.isUnique() && key.isSingleProperty() && this.equals(key.getTheProperty())) {
                    return true;
                }
            }
        }
        return false;
        /**/
        Key key = getSinglePropertyUniqueKey();
        return key != null;
    }

    public Key getSinglePropertyUniqueKey() {
        Entity root = getDeclaringEntityRoot();
        if (root != null) {
            List<Key> keys = root.getKeysList();
            for (Key key : keys) {
                if (key.isUnique() && key.isSingleProperty() && this.equals(key.getTheProperty())) {
                    return key;
                }
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="aggregates">
    private final List<NumericAggregate> _aggregates = new ArrayList<>();

    public List<NumericAggregate> getAggregates() {
        return _aggregates;
    }

    /**
     * El método keepCountOn se utiliza para establecer las propiedades donde se lleva la cuenta de los valores no nulos de esta propiedad.
     *
     * @param properties una o más propiedades donde se lleva la cuenta de los valores no nulos de esta propiedad; deben ser propiedades numéricas, no
     * calculables, de una entidad directamente referenciada
     */
    public void keepCountOn(Property... properties) {
        addAggregate(RowsAggregateOp.COUNT, properties);
    }

    protected void addAggregate(RowsAggregateOp operator, Property... properties) {
        boolean log = depth() == 0;
        String message = "failed to set aggregate property of " + getFullName();
        String xIsNotY = " is not a non-calculable numerical property of an entity stored in the same table as ";
        if (!isProperty() || isCalculable(getDeclaringField())) {
            message += "; methods keepCountOn, keepTallyOn and keepSumOn are not valid for parameters or calculable properties; setting ignored";
            logger.warn(message);
            Project.increaseParserWarningCount();
        } else {
            Entity thisDeclaringEntity = getDeclaringEntity();
            String thisTableColumnEntityName = getTableColumnEntityName();
            for (Property property : properties) {
                if (property instanceof NumericPrimitive && property.isProperty() && !isCalculable(property.getDeclaringField())) {
                    Entity thatDeclaringEntity = property.getDeclaringEntity();
                    Entity thenDeclaringEntity = thatDeclaringEntity.getDeclaringEntity();
//                  String thatTableColumnEntityName = property.getTableColumnEntityName();
                    String thatDeclaringEntityTableColumnEntityName = ((Property) thatDeclaringEntity).getTableColumnEntityName();
                    boolean ok = !isCalculable(thatDeclaringEntity.getDeclaringField());
                    if (ok && thisDeclaringEntity == thenDeclaringEntity && thisTableColumnEntityName.equals(thatDeclaringEntityTableColumnEntityName)) {
                        _aggregates.add(new NumericAggregate(operator, property));
                    } else if (log) {
                        String hint = "; " + property.getFullName() + xIsNotY + thisDeclaringEntity.getFullName();
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                } else if (log) {
                    String name = property == null ? "null property argument" : property.getFullName();
                    String hint = "; " + name + xIsNotY + thisDeclaringEntity.getFullName();
                    logger.error(message + hint);
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    protected void checkAggregates() {
        if (!_aggregates.isEmpty()) {
            final Integer ZERO = 0;
            RowsAggregateOp operator;
            Property property;
            String fullName, hint;
            String message = "failed to set aggregate property of " + getFullName();
            String warning = "check aggregate properties of " + getFullName();
            for (NumericAggregate aggregate : _aggregates) {
                operator = aggregate.getOperator();
                property = aggregate.getProperty();
                fullName = property.getFullName();
                if (property instanceof NumericPrimitive) {
                    NumericPrimitive propertyAtRoot = (NumericPrimitive) property.getPropertyAtRoot();
                    if (propertyAtRoot.isPrimaryKeyProperty()) {
                        hint = "; " + fullName + " is the primary key property";
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                    if (propertyAtRoot.isNumericKeyProperty() && !propertyAtRoot.isPrimaryKeyProperty()) {
                        hint = "; " + fullName + " is the numeric key property";
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                    if (propertyAtRoot.isSequenceProperty()) {
                        hint = "; " + fullName + " is the sequence property";
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                    if (propertyAtRoot.isDiscriminatorProperty()) {
                        hint = "; " + fullName + " is the discriminator property";
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                    if (propertyAtRoot.isVersionProperty()) {
                        hint = "; " + fullName + " is the version property";
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                    if (propertyAtRoot.isUniqueKeyProperty()) {
                        hint = "; " + fullName + " is a unique key property";
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                    if (propertyAtRoot.isCalculable()) {
                        hint = "; " + fullName + " is calculable";
                        logger.error(message + hint);
                        Project.increaseParserErrorCount();
                    }
                    if (propertyAtRoot.isCreateField()) {
                        hint = "; " + fullName + " is creatable";
                        logger.warn(warning + hint);
                        Project.increaseParserWarningCount();
                    }
                    if (propertyAtRoot.isUpdateField()) {
                        hint = "; " + fullName + " is updatable";
                        logger.warn(warning + hint);
                        Project.increaseParserWarningCount();
                    }
                    if (RowsAggregateOp.COUNT.equals(operator) || RowsAggregateOp.SUM.equals(operator)) {
                        if (!ZERO.equals(propertyAtRoot.getDefaultValue())) {
                            hint = "; " + fullName + " default value is not 0";
                            logger.warn(warning + hint);
                            Project.increaseParserWarningCount();
                        }
                        if (RowsAggregateOp.COUNT.equals(operator)) {
                            if (!(propertyAtRoot instanceof IntegerProperty || propertyAtRoot instanceof LongProperty)) {
                                hint = "; " + fullName + " is not an Integer or Long property";
                                logger.warn(warning + hint);
                                Project.increaseParserWarningCount();
                            }
                        } else if (this instanceof NumericPrimitive numericPrimitive) {
                            Number maxNumber1 = numericPrimitive.getMaxNumber();
                            Number minNumber1 = numericPrimitive.getMinNumber();
                            if (maxNumber1 != null && minNumber1 != null) {
                                Number maxNumber2 = propertyAtRoot.getMaxNumber();
                                Number minNumber2 = propertyAtRoot.getMinNumber();
                                if (maxNumber2 != null && minNumber2 != null) {
                                    if (!ObjUtils.lteq(maxNumber1, maxNumber2) || !ObjUtils.gteq(minNumber1, minNumber2)) {
                                        hint = "; " + fullName + " is not large enough to store the aggregation";
                                        logger.warn(warning + hint);
                                        Project.increaseParserWarningCount();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
            if (verbose) {
                string += fee + tab + "dataType" + faa + _dataType + foo;
                if (getDeclaringField() != null && getDeclaringArtifact() != null) {
                    if (isParameter()) {
                        string += fee + tab + "auditable" + faa + _auditable + foo;
                        string += fee + tab + "password" + faa + _password + foo;
                        string += fee + tab + "required" + faa + _required + foo;
                        string += fee + tab + "linkedFieldName" + faa + _linkedFieldName + foo;
                        string += fee + tab + "linkedField" + faa + _linkedField + foo;
                        string += fee + tab + "linkedProperty" + faa + _linkedProperty + foo;
                        string += fee + tab + "linkedColumnName" + faa + _linkedColumnName + foo;
                        string += fee + tab + "linkedColumnOperator" + faa + _linkedColumnOperator + foo;
                    }
                    if (isProperty()) {
                        string += fee + tab + "baseField" + faa + isBaseField() + foo;
                        string += fee + tab + "keyField" + faa + isKeyField() + foo;
                        string += fee + tab + "auditable" + faa + _auditable + foo;
                        string += fee + tab + "password" + faa + _password + foo;
                        string += fee + tab + "required" + faa + _required + foo;
                        string += fee + tab + "hiddenField" + faa + _hiddenField + foo;
                        string += fee + tab + "createField" + faa + _createField + foo;
                        string += fee + tab + "updateField" + faa + _updateField + foo;
                        string += fee + tab + "searchField" + faa + _searchField + foo;
                        string += fee + tab + "filterField" + faa + _filterField + foo;
                        string += fee + tab + "tableField" + faa + _tableField + foo;
                        string += fee + tab + "detailField" + faa + _detailField + foo;
                        string += fee + tab + "columnField" + faa + _columnField + foo;
                        string += fee + tab + "reportField" + faa + _reportField + foo;
                        string += fee + tab + "exportField" + faa + _exportField + foo;
                        string += fee + tab + "calculable" + faa + _calculable + foo;
                        string += fee + tab + "nullable" + faa + _nullable + foo;
                        string += fee + tab + "insertable" + faa + _insertable + foo;
                        string += fee + tab + "updateable" + faa + _updateable + foo;
                        string += fee + tab + "unique" + faa + _unique + foo;
                        string += fee + tab + "indexed" + faa + _indexed + foo;
                    }
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
