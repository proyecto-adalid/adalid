/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.BitUtils;
import adalid.commons.util.TimeUtils;
import adalid.core.annotations.BaseField;
import adalid.core.annotations.BigDecimalField;
import adalid.core.annotations.BooleanDataGen;
import adalid.core.annotations.BooleanField;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.CharacterDataGen;
import adalid.core.annotations.CharacterKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.DiscriminatorColumn;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.InactiveIndicator;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.NumericDataGen;
import adalid.core.annotations.NumericField;
import adalid.core.annotations.NumericKey;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.ParentProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.SegmentProperty;
import adalid.core.annotations.StringField;
import adalid.core.annotations.TemporalDataGen;
import adalid.core.annotations.TimeField;
import adalid.core.annotations.TimestampField;
import adalid.core.annotations.UniqueKey;
import adalid.core.annotations.UrlProperty;
import adalid.core.annotations.VersionProperty;
import adalid.core.data.types.BigDecimalData;
import adalid.core.data.types.BigIntegerData;
import adalid.core.data.types.BinaryData;
import adalid.core.data.types.BooleanData;
import adalid.core.data.types.ByteData;
import adalid.core.data.types.CharacterData;
import adalid.core.data.types.DateData;
import adalid.core.data.types.DoubleData;
import adalid.core.data.types.FloatData;
import adalid.core.data.types.IntegerData;
import adalid.core.data.types.LongData;
import adalid.core.data.types.ShortData;
import adalid.core.data.types.StringData;
import adalid.core.data.types.TimeData;
import adalid.core.data.types.TimestampData;
import adalid.core.enums.BooleanDisplayType;
import adalid.core.enums.DataGenNumericAction;
import adalid.core.enums.DataGenTemporalInterval;
import adalid.core.enums.DataGenType;
import adalid.core.enums.DefaultCondition;
import adalid.core.enums.DivisorRule;
import adalid.core.enums.KeyProperty;
import adalid.core.enums.MimeType;
import adalid.core.enums.OperationKind;
import adalid.core.enums.PropertyAccess;
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.enums.StandardRelationalOp;
import adalid.core.enums.UploadStorageOption;
import adalid.core.enums.UrlDisplayType;
import adalid.core.enums.UrlType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.ContextualEntity;
import adalid.core.interfaces.DataArtifact;
import adalid.core.interfaces.DatabaseEntity;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EnumerationEntity;
import adalid.core.interfaces.NamedValue;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.PersistentEnumerationEntity;
import adalid.core.interfaces.Property;
import adalid.core.parameters.BigDecimalParameter;
import adalid.core.parameters.BooleanParameter;
import adalid.core.parameters.StringParameter;
import adalid.core.parameters.TimeParameter;
import adalid.core.parameters.TimestampParameter;
import adalid.core.primitives.BinaryPrimitive;
import adalid.core.primitives.BooleanPrimitive;
import adalid.core.primitives.CharacterPrimitive;
import adalid.core.primitives.NumericPrimitive;
import adalid.core.primitives.TemporalPrimitive;
import adalid.core.properties.BigDecimalProperty;
import adalid.core.properties.BigIntegerProperty;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.ByteProperty;
import adalid.core.properties.CharacterProperty;
import adalid.core.properties.DateProperty;
import adalid.core.properties.DoubleProperty;
import adalid.core.properties.FloatProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.ShortProperty;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimeProperty;
import adalid.core.properties.TimestampProperty;
import adalid.core.wrappers.DataArtifactWrapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractDataArtifact extends AbstractArtifact implements DataArtifact, Parameter, Property {

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(DataArtifact.class);

    private static final String EOL = "\n";

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
    private PropertyAccess _propertyAccess;

    /**
     *
     */
    private boolean _auditable;

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
    private Boolean _tableField;

    /**
     *
     */
    private boolean _detailField;

    /**
     *
     */
    private Boolean _reportField;

    /**
     *
     */
    private boolean _exportField;

    /**
     *
     */
    private boolean _submitField;

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
    private DefaultCondition _defaultCondition;

    /**
     *
     */
    private int _sequenceNumber;

    /**
     *
     */
    private boolean _calculable;

    /**
     *
     */
    private boolean _nullable;

    /**
     *
     */
    private boolean _insertable;

    /**
     *
     */
    private boolean _updateable;

    /**
     *
     */
    private boolean _unique;

    /**
     *
     */
    private BooleanDisplayType _booleanDisplayType;

    /**
     *
     */
    private int _divisor;

    /**
     *
     */
    private DivisorRule _divisorRule;

    /**
     *
     */
    private UrlType _urlType;

    /**
     *
     */
    private UrlDisplayType _urlDisplayType;

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
    private Property _linkedProperty;

    /**
     *
     */
    private String _linkedColumnName;

    /**
     *
     */
    private StandardRelationalOp _linkedColumnOperator;

    /**
     *
     */
    DataGenType _dataGenType;

    /**
     *
     */
    int _dataGenSeriesStart;

    /**
     *
     */
    int _dataGenSeriesStop;

    /**
     *
     */
    int _dataGenSeriesStep;

    /**
     *
     */
    String _dataGenFunction;

    /**
     *
     */
    int _dataGenNullable;

    /**
     *
     */
    private int _dataGenTrueable;

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
    private Object _dataGenMin;

    /**
     *
     */
    private Object _dataGenMax;

    /**
     *
     */
    private DataGenTemporalInterval _dataGenTemporalInterval;

    /**
     *
     */
    private DataGenNumericAction _dataGenNumericAction;

    /**
     *
     */
    private Object _dataGenFactor;

    /**
     *
     */
    private int _maxInputFileSize;

    /**
     *
     */
    private MimeType[] _validInputFileTypes;

    /**
     *
     */
    private UploadStorageOption _uploadStorageOption;

    /**
     *
     */
    private String _joinFieldName;

    /**
     *
     */
    private Field _joinField;

    /**
     *
     */
    private Property _joinProperty;

    /**
     *
     */
    private boolean _updateableFileReference;

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
     * annotated with VersionProperty
     */
    private boolean _annotatedWithVersionProperty;

    /**
     * annotated with NumericKey
     */
    private boolean _annotatedWithNumericKey;

    /**
     * annotated with CharacterKey
     */
    private boolean _annotatedWithCharacterKey;

    /**
     * annotated with NameProperty
     */
    private boolean _annotatedWithNameProperty;

    /**
     * annotated with DescriptionProperty
     */
    private boolean _annotatedWithDescriptionProperty;

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
     * annotated with ColumnField
     */
    private boolean _annotatedWithColumnField;

    /**
     * annotated with BigDecimalField
     */
    private boolean _annotatedWithBigDecimalField;

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
     * annotated with InstanceReference
     */
    private boolean _annotatedWithInstanceReference;

    /**
     * annotated with FileReference
     */
    private boolean _annotatedWithFileReference;

    /**
     * annotated with DataGen
     */
    private boolean _annotatedWithDataGen;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
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
     * @return true if the field defines a base field
     */
    @Override
    public boolean isBaseField() {
        return _annotatedWithBaseField || isPrimaryKeyProperty() || isVersionProperty();
    }

    /**
     * @return true if the field defines a base field
     */
    @Override
    public boolean isKeyField() {
        return isPrimaryKeyProperty()
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
        return PropertyAccess.UNSPECIFIED.equals(_propertyAccess)
            ? _password ? PropertyAccess.RESTRICTED_READING : PropertyAccess.UNRESTRICTED
            : _propertyAccess;
    }

    /**
     * @return the auditable indicator
     */
    @Override
    public boolean isAuditable() {
        return _auditable && !_password && !isBinaryData();
    }

    /**
     * @return the password indicator
     */
    @Override
    public boolean isPassword() {
        return _password;
    }

    /**
     * @return the read only indicator
     */
    @Override
    public boolean isReadOnly() {
        return !isCreateField() && !isUpdateField();
    }

    /**
     * @return the required parameter indicator
     */
    @Override
    public boolean isRequiredParameter() {
        return !isHiddenField() && BitUtils.valueOf(_required);
    }

    /**
     * @return the required property indicator
     */
    @Override
    public boolean isRequiredProperty() {
        return !isHiddenField() && BitUtils.valueOf(_required, implicitRequiredProperty());
    }

    private boolean implicitRequiredProperty() {
        return !_calculable
            && !_nullable
            && !isDiscriminatorProperty()
            && getInitialValue() == null
            && getDefaultValue() == null;
    }

    /**
     * @return the hidden field indicator
     */
    @Override
    public boolean isHiddenField() {
        return _hiddenField || isBinaryData();
    }

    /**
     * @return the create field indicator
     */
    @Override
    public boolean isCreateField() {
        return !isHiddenField() && _insertable && BitUtils.valueOf(_createField, implicitCreateField());
    }

    private boolean implicitCreateField() {
        return isRequiredProperty()
            && isUnlinkedProperty();
    }

    /**
     * @return the update field indicator
     */
    @Override
    public boolean isUpdateField() {
        return !isHiddenField() && _updateable && BitUtils.valueOf(_updateField, implicitUpdateField());
    }

    private boolean implicitUpdateField() {
        return isUnlinkedProperty();
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

    /**
     * @return the search field indicator
     */
    @Override
    public boolean isSearchField() {
        return !isHiddenField() && !_password && BitUtils.valueOf(_searchField, implicitSearchField());
    }

    private boolean implicitSearchField() {
        return isTableField()
            || isUniqueKeyProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isDiscriminatorProperty()
            || isNameProperty()
            || isInactiveIndicatorProperty();
    }

    /**
     * @return the filter field indicator
     */
    @Override
    public boolean isFilterField() {
        return !isHiddenField() && !_password && BitUtils.valueOf(_filterField, implicitFilterField());
    }

    private boolean implicitFilterField() {
//      if (isSearchField()) {
//          return true;
//      }
//      if (isStringData()) {
//          StringData stringData = (StringData) this;
//          Integer maxLength = stringData.getMaxLength();
//          return maxLength != null && maxLength <= 200;
//      }
        return true;
    }

    /**
     * @return the table field indicator
     */
    @Override
    public boolean isTableField() {
        return !isHiddenField() && BitUtils.valueOf(_tableField, implicitTableField());
    }

    private boolean implicitTableField() {
        return isRequiredProperty()
            || isUniqueKeyProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isDiscriminatorProperty()
            || isNameProperty()
            || isInactiveIndicatorProperty();
    }

    /**
     * @return the detail field indicator
     */
    @Override
    public boolean isDetailField() {
        return !isHiddenField() && _detailField;
    }

    /**
     * @return the report field indicator
     */
    @Override
    public boolean isReportField() {
        return !isHiddenField() && !_password && BitUtils.valueOf(_reportField, implicitReportField());
    }

    private boolean implicitReportField() {
        return isRequiredProperty()
            || isUniqueKeyProperty()
            || isBusinessKeyProperty()
            || isCharacterKeyProperty()
            || isNumericKeyProperty()
            || isDiscriminatorProperty()
            || isNameProperty()
            || isInactiveIndicatorProperty();
    }

    /**
     * @return the export field indicator
     */
    @Override
    public boolean isExportField() {
        return !isHiddenField() && !_password && _exportField;
    }

    /**
     * @return the submit field indicator
     */
    @Override
    public boolean isSubmitField() {
        return !isHiddenField() && _submitField;
    }

    /**
     * @return the headertextless field indicator
     */
    @Override
    public boolean isHeadertextlessField() {
        return !isHiddenField() && _headertextlessField;
    }

    /**
     * @return the heading field indicator
     */
    @Override
    public boolean isHeadingField() {
        return !isHiddenField() && !_password && BitUtils.valueOf(_headingField, isTableField());
    }

    /**
     * @return the default condition
     */
    @Override
    public DefaultCondition getDefaultCondition() {
        return _defaultCondition;
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
     * @return the calculable indicator
     */
    @Override
    public boolean isCalculable() {
        return _calculable;
    }

    /**
     * @return the nullable indicator
     */
    @Override
    public boolean isNullable() {
        return _nullable;
    }

    /**
     * @return the insertable indicator
     */
    @Override
    public boolean isInsertable() {
        return _insertable;
    }

    /**
     * @return the updateable indicator
     */
    @Override
    public boolean isUpdateable() {
        return _updateable;
    }

    /**
     * @return the unique indicator
     */
    @Override
    public boolean isUnique() {
        return !_nullable && (_annotatedWithUniqueKey || _unique);
    }

    /**
     * @return the boolean display type
     */
//  @Override
    public BooleanDisplayType getBooleanDisplayType() {
        if (BooleanDisplayType.UNSPECIFIED.equals(_booleanDisplayType)) {
            return (isParameter() && isRequiredParameter()) || (isProperty() && !isNullable())
                ? BooleanDisplayType.CHECKBOX : BooleanDisplayType.DROPDOWN;
        }
        return _booleanDisplayType;
    }

    /**
     * @return the divisor
     */
//  @Override
    public int getDivisor() {
        return _divisor;
    }

    /**
     * @return the divisor rule
     */
//  @Override
    public DivisorRule getDivisorRule() {
        return _divisorRule;
    }

    /**
     * @return the url displayType
     */
//  @Override -- Implements method from: URL (StringProperty)
    public UrlType getUrlType() {
        return _urlType;
    }

    /**
     * @return the url display type
     */
//  @Override -- Implements method from: URL (StringProperty)
    public UrlDisplayType getUrlDisplayType() {
        return _urlDisplayType;
    }

    /**
     * @return the instance reference indicator
     */
    @Override
    public boolean isInstanceReferenceField() {
        return _annotatedWithInstanceReference;
    }

    /**
     * @return the file reference indicator
     */
    @Override
    public boolean isFileReferenceField() {
        return _annotatedWithFileReference;
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
     * @return the data generation type
     */
//  @Override
    public DataGenType getDataGenType() {
        return _dataGenType;
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
        return _annotatedWithDataGen && _dataGenSeriesStart <= _dataGenSeriesStop && _dataGenSeriesStep > 0;
    }

    /**
     * @return the data generation user-defined function name
     */
//  @Override
    public String getDataGenFunction() {
        return _dataGenFunction;
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
    public Object getDataGenMin() {
        return _dataGenMin == null && isNumericPrimitive() ? 0 : _dataGenMin;
    }

    /**
     * @return the data max
     */
//  @Override
    public Object getDataGenMax() {
        if (_dataGenMax == null && isNumericPrimitive()) {
            if (isByteData()) {
                return Byte.MAX_VALUE;
            }
            if (isShortData()) {
                return Short.MAX_VALUE;
            }
            return 1000000000;
        }
        return _dataGenMax;
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
     * @return the max input file size
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public int getMaxInputFileSize() {
        return _maxInputFileSize;
    }

    /**
     * @return the valid input file types
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public MimeType[] getValidInputFileTypes() {
        return _validInputFileTypes;
    }

    /**
     * @return the upload storage option
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public UploadStorageOption getUploadStorageOption() {
        return _uploadStorageOption;
    }

    /**
     * @return the join field name
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public String getJoinFieldName() {
        return _joinFieldName;
    }

    /**
     * @return the join field
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Field getJoinField() {
        return _joinField;
    }

    /**
     * @return the join property
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Property getJoinProperty() {
        return _joinProperty;
    }

    /**
     * @return the updateable file reference indicator
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public boolean isUpdateableFileReference() {
        return _updateableFileReference;
    }

    /**
     * @return the rendering filter
     */
    @Override
    public BooleanExpression getRenderingFilter() {
        return _renderingFilter;
    }

    /**
     * @param renderingFilter the rendering filter to set
     */
    @Override
    public void setRenderingFilter(BooleanExpression renderingFilter) {
        _renderingFilter = renderingFilter instanceof BooleanPrimitive ? renderingFilter.isTrue() : renderingFilter;
    }

    /**
     * @return the requiring filter
     */
    @Override
    public BooleanExpression getRequiringFilter() {
        return _requiringFilter;
    }

    /**
     * @param requiringFilter the requiring filter to set
     */
    @Override
    public void setRequiringFilter(BooleanExpression requiringFilter) {
        _requiringFilter = requiringFilter instanceof BooleanPrimitive ? requiringFilter.isTrue() : requiringFilter;
    }

    /**
     * @return the modifying filter
     */
    @Override
    public BooleanExpression getModifyingFilter() {
        return _modifyingFilter;
    }

    /**
     * @param modifyingFilter the modifying filter to set
     */
    @Override
    public void setModifyingFilter(BooleanExpression modifyingFilter) {
        _modifyingFilter = modifyingFilter instanceof BooleanPrimitive ? modifyingFilter.isTrue() : modifyingFilter;
    }

    /**
     * @return the nullifying filter
     */
    @Override
    public BooleanExpression getNullifyingFilter() {
        return _nullifyingFilter;
    }

    /**
     * @param nullifyingFilter the nullifying filter to set
     */
    @Override
    public void setNullifyingFilter(BooleanExpression nullifyingFilter) {
        _nullifyingFilter = nullifyingFilter instanceof BooleanPrimitive ? nullifyingFilter.isTrue() : nullifyingFilter;
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
                    if (this.equals(tabField.getProperty())) {
                        list.add(tab);
                        break;
                    }
                }
            }
        }
        return list;
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
     * @return VersionProperty annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithVersionProperty() {
        return _annotatedWithVersionProperty;
    }

    /**
     * @return NumericKey annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithNumericKey() {
        return _annotatedWithNumericKey;
    }

    /**
     * @return CharacterKey annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithCharacterKey() {
        return _annotatedWithCharacterKey;
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
     * @return the InstanceReference annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithInstanceReference() {
        return _annotatedWithInstanceReference;
    }

    /**
     * @return the FileReference annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithFileReference() {
        return _annotatedWithFileReference;
    }

    /**
     * @return the DataGen annotation indicator
     */
//  @Override
    public boolean isAnnotatedWithDataGen() {
        return _annotatedWithDataGen;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithPrimaryKey = false;
        _annotatedWithVersionProperty = false;
        _annotatedWithNumericKey = false;
        _annotatedWithCharacterKey = false;
        _annotatedWithNameProperty = false;
        _annotatedWithDescriptionProperty = false;
        _annotatedWithInactiveIndicator = false;
        _annotatedWithUrlProperty = false;
        _annotatedWithParentProperty = false;
        _annotatedWithOwnerProperty = false;
        _annotatedWithSegmentProperty = false;
        _annotatedWithUniqueKey = false;
        _annotatedWithBusinessKey = false;
        _annotatedWithDiscriminatorColumn = false;
        _annotatedWithBaseField = false;
        _annotatedWithColumnField = false;
        _annotatedWithBigDecimalField = false;
        _annotatedWithBooleanField = false;
        _annotatedWithNumericField = false;
        _annotatedWithStringField = false;
        _annotatedWithTimeField = false;
        _annotatedWithTimestampField = false;
        _annotatedWithFileReference = false;
        _annotatedWithDataGen = false;
        _annotatedWithInstanceReference = false;
        _annotatedWithParameterField = false;
        _annotatedWithPropertyField = false;
        _propertyAccess = PropertyAccess.UNSPECIFIED;
        _auditable = true;
        _password = false;
        _required = null;
        _hiddenField = false;
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity instanceof EnumerationEntity) {
            _createField = false;
            _updateField = false;
        } else {
            _createField = null;
            _updateField = null;
        }
        _searchField = null;
        _filterField = null;
        _tableField = null;
        _detailField = true;
        _reportField = null;
        _exportField = true;
        _submitField = false;
        _headertextlessField = false;
        _headingField = null;
        _defaultCondition = DefaultCondition.IF_NULL;
        _sequenceNumber = 0;
        _calculable = false;
        _nullable = true;
        _insertable = true;
        _updateable = true;
        _unique = false;
        _booleanDisplayType = BooleanDisplayType.UNSPECIFIED;
        _divisor = 0;
        _divisorRule = DivisorRule.UNSPECIFIED;
        _urlType = UrlType.UNSPECIFIED;
        _urlDisplayType = UrlDisplayType.UNSPECIFIED;
        _linkedFieldName = null;
        _linkedField = null;
        _linkedProperty = null;
        _linkedColumnName = null;
        _linkedColumnOperator = StandardRelationalOp.EQ;
        _dataGenType = DataGenType.UNSPECIFIED;
        _dataGenSeriesStart = 1;
//      _dataGenSeriesStop = Integer.MAX_VALUE;
        _dataGenSeriesStop = 10000;
        _dataGenSeriesStep = 1;
        _dataGenFunction = null;
        _dataGenNullable = 0;
        _dataGenTrueable = 50;
        _dataGenPattern = null;
        _dataGenPrefix = null;
        _dataGenSuffix = null;
        _dataGenMin = null;
        _dataGenMax = null;
        _dataGenTemporalInterval = DataGenTemporalInterval.UNSPECIFIED;
        _dataGenNumericAction = DataGenNumericAction.UNSPECIFIED;
        _dataGenFactor = null;
        _maxInputFileSize = 1000000;
        _validInputFileTypes = new MimeType[]{};
        _uploadStorageOption = UploadStorageOption.UNSPECIFIED;
        _joinFieldName = null;
        _joinField = null;
        _joinProperty = null;
        _updateableFileReference = false;
    }

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
                annotateBooleanField(field);
                annotateNumericField(field);
                annotateStringField(field);
                annotateTimeField(field);
                annotateTimestampField(field);
                annotateFileReference(field);
                annotateInstanceReference(field);
                annotateParameterField(field);
            }
            if (isProperty()) {
                annotateKeyProperties(field);
                annotateBaseField(field);
                annotateColumnField(field);
                annotateBigDecimalField(field);
                annotateBooleanField(field);
                annotateNumericField(field);
                annotateStringField(field);
                annotateTimeField(field);
                annotateTimestampField(field);
                annotateFileReference(field);
                annotatePropertyField(field);
                annotateDataGen(field);
            }
        }
    }

    private void annotateKeyProperties(Field field) {
        annotatePrimaryKey(field);
        annotateVersionProperty(field);
        annotateNumericKey(field);
        annotateCharacterKey(field);
        annotateNameProperty(field);
        annotateDescriptionProperty(field);
        annotateInactiveIndicator(field);
        annotateUrlProperty(field);
        annotateParentProperty(field);
        annotateOwnerProperty(field);
        annotateSegmentProperty(field);
        annotateUniqueKey(field);
        annotateBusinessKey(field);
        annotateDiscriminatorColumn(field);
        initializeKeyPropertyAnnotations();
    }

    private void annotatePrimaryKey(Field field) {
        Class<? extends Annotation> annotationClass = PrimaryKey.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.PRIMARY_KEY);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithPrimaryKey = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateVersionProperty(Field field) {
        Class<? extends Annotation> annotationClass = VersionProperty.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.VERSION);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithVersionProperty = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateNumericKey(Field field) {
        Class<? extends Annotation> annotationClass = NumericKey.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.NUMERIC_KEY);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithNumericKey = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateCharacterKey(Field field) {
        Class<? extends Annotation> annotationClass = CharacterKey.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.CHARACTER_KEY);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithCharacterKey = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateNameProperty(Field field) {
        Class<? extends Annotation> annotationClass = NameProperty.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.NAME);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithNameProperty = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateDescriptionProperty(Field field) {
        Class<? extends Annotation> annotationClass = DescriptionProperty.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.DESCRIPTION);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithDescriptionProperty = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateInactiveIndicator(Field field) {
        Class<? extends Annotation> annotationClass = InactiveIndicator.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.INACTIVE_INDICATOR);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithInactiveIndicator = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
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
                UrlProperty annotation = field.getAnnotation(UrlProperty.class);
                _urlType = annotation.urlType();
                _urlDisplayType = annotation.urlDisplayType();
                _annotatedWithUrlProperty = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateParentProperty(Field field) {
//      Class<?> declaringClass = getDeclaringEntity().getDataClass();
        Class<?> declaringClass = field.getDeclaringClass();
        Class<? extends Annotation> annotationClass = ParentProperty.class;
        Class<?>[] validTypes = new Class<?>[]{declaringClass};
        boolean log = depth() == 1;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.PARENT, validTypes);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithParentProperty = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateOwnerProperty(Field field) {
        Class<? extends Annotation> annotationClass = OwnerProperty.class;
        boolean log = depth() == 1;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.OWNER);
        /**/
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

    private void annotateSegmentProperty(Field field) {
        Class<? extends Annotation> annotationClass = SegmentProperty.class;
        boolean log = depth() == 1;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.SEGMENT);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithSegmentProperty = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateUniqueKey(Field field) {
        Class<? extends Annotation> annotationClass = UniqueKey.class;
        boolean log = this instanceof Entity ? depth() == 1 : depth() == 0;
        _annotatedWithUniqueKey = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.UNIQUE_KEY);
    }

    private void annotateBusinessKey(Field field) {
        Class<? extends Annotation> annotationClass = BusinessKey.class;
        boolean log = depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.BUSINESS_KEY);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithBusinessKey = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
    }

    private void annotateDiscriminatorColumn(Field field) {
        Class<? extends Annotation> annotationClass = DiscriminatorColumn.class;
        boolean log = this instanceof Entity ? depth() == 1 : depth() == 0;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkKeyPropertyFieldAnnotation(log, field, KeyProperty.DISCRIMINATOR);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithDiscriminatorColumn = true;
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
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
            _hiddenField = true;
        }
        if (isVersionProperty()) {
            _nullable = false;
            _hiddenField = true;
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
            data.setMaxLength(Constants.DEFAULT_CHARACTER_KEY_MAX_LENGTH);
        }
        if (isBusinessKeyProperty()) {
            if (isNumericPrimitive()) {
                NumericPrimitive primitive = (NumericPrimitive) this;
                primitive.setMinValue(0);
            }
            if (isStringData()) {
                StringData data = (StringData) this;
                data.setMaxLength(Constants.DEFAULT_CHARACTER_KEY_MAX_LENGTH);
            }
        }
        if (isNameProperty()) {
            _nullable = false;
            StringData data = (StringData) this;
            data.setMaxLength(Constants.DEFAULT_NAME_PROPERTY_MAX_LENGTH);
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
        if (isDiscriminatorProperty()) {
            _nullable = false;
            _updateable = false;
            _hiddenField = getDeclaringEntityRoot().getSubclassesMap().isEmpty();
        }
    }

    private void annotateBaseField(Field field) {
        Class<? extends Annotation> annotationClass = BaseField.class;
        Class<?>[] validTypes = new Class<?>[]{Property.class};
        boolean log = this instanceof Entity ? depth() == 1 : depth() == 0;
        _annotatedWithBaseField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
    }

    private void annotateColumnField(Field field) {
        Class<? extends Annotation> annotationClass = ColumnField.class;
        Class<?>[] validTypes = new Class<?>[]{Property.class};
        boolean log = this instanceof Entity ? depth() == 1 : depth() == 0;
        _annotatedWithColumnField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithColumnField) {
            ColumnField annotation = field.getAnnotation(ColumnField.class);
            _calculable = annotation.calculable().toBoolean(_calculable);
            _nullable = annotation.nullable().toBoolean(_nullable);
            _insertable = annotation.insertable().toBoolean(_insertable);
            _updateable = annotation.updateable().toBoolean(_updateable);
            _unique = annotation.unique().toBoolean(_unique);
        }
    }

    private void annotateBigDecimalField(Field field) {
        Class<? extends Annotation> annotationClass = BigDecimalField.class;
        Class<?>[] validTypes = new Class<?>[]{BigDecimalParameter.class, BigDecimalProperty.class};
        boolean log = isParameter() || depth() == 0;
        _annotatedWithBigDecimalField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithBigDecimalField) {
            BigDecimalField annotation = field.getAnnotation(BigDecimalField.class);
            int precision = annotation.precision();
            int scale = annotation.scale();
            BigDecimalData data = (BigDecimalData) this;
            if (precision >= 1 && precision <= Constants.MAX_DECIMAL_PRECISION) {
                data.setPrecision(precision);
                if (scale <= precision) {
                    data.setScale(scale > 0 ? scale : 0);
                } else if (log) {
                    logger.error(getDeclaringArtifactClassName() + "." + field.getName() + " has an invalid decimal scale");
                    TLC.getProject().getParser().increaseErrorCount();
                }
            } else if (log) {
                logger.error(getDeclaringArtifactClassName() + "." + field.getName() + " has an invalid decimal precision");
                TLC.getProject().getParser().increaseErrorCount();
            }
        }
    }

    private void annotateBooleanField(Field field) {
        Class<? extends Annotation> annotationClass = BooleanField.class;
        Class<?>[] validTypes = new Class<?>[]{BooleanParameter.class, BooleanProperty.class};
        boolean log = isParameter() || depth() == 0;
        _annotatedWithBooleanField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithBooleanField) {
            BooleanField annotation = field.getAnnotation(BooleanField.class);
            _booleanDisplayType = annotation.displayType();
        }
    }

    private void annotateNumericField(Field field) {
        Class<? extends Annotation> annotationClass = NumericField.class;
        Class<?>[] validTypes = new Class<?>[]{NumericPrimitive.class};
        boolean log = isParameter() || depth() == 0;
        _annotatedWithNumericField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithNumericField) {
            NumericField annotation = field.getAnnotation(NumericField.class);
            _divisor = Math.min(maxDivisor(), Math.max(1, annotation.divisor()));
            _divisorRule = annotation.divisorRule();
        }
    }

    private int maxDivisor() {
        if (this instanceof ByteData) {
            return Constants.MAX_BYTE_DIVISOR;
        }
        if (this instanceof ShortData) {
            return Constants.MAX_SHORT_DIVISOR;
        }
        if (this instanceof NumericPrimitive) {
            return Constants.MAX_INTEGER_DIVISOR;
        }
        return 0;
    }

    private void annotateStringField(Field field) {
        Class<? extends Annotation> annotationClass = StringField.class;
        Class<?>[] validTypes = new Class<?>[]{StringParameter.class, StringProperty.class};
        boolean log = isParameter() || depth() == 0;
        _annotatedWithStringField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithStringField) {
            StringField annotation = field.getAnnotation(StringField.class);
            int maxLength = annotation.maxLength();
            int minLength = annotation.minLength();
            String regex = annotation.regex();
            StringData data = (StringData) this;
            if (maxLength >= 1 && maxLength <= Constants.MAX_STRING_LENGTH) {
                data.setMaxLength(maxLength);
                data.setMinLength(minLength >= 0 ? minLength <= maxLength ? minLength : maxLength : 0);
            } else if (minLength >= 0 && minLength <= Constants.MAX_STRING_LENGTH) {
                data.setMinLength(minLength);
            }
            data.setLetterCase(annotation.letterCase());
            data.setAllowDiacritics(annotation.allowDiacritics().toBoolean(true));
            if (StringUtils.isNotBlank(regex)) {
                try {
                    data.setPattern(Pattern.compile(regex));
                } catch (PatternSyntaxException ex) {
                    if (log) {
                        logger.error(getDeclaringArtifactClassName() + "." + field.getName() + " has an invalid regular expression");
                        TLC.getProject().getParser().increaseErrorCount();
                    }
                }
            }
        }
    }

    private void annotateTimeField(Field field) {
        Class<? extends Annotation> annotationClass = TimeField.class;
        Class<?>[] validTypes = new Class<?>[]{TimeParameter.class, TimeProperty.class};
        boolean log = isParameter() || depth() == 0;
        _annotatedWithTimeField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithTimeField) {
            TimeField annotation = field.getAnnotation(TimeField.class);
            int precision = annotation.precision();
            TimeData data = (TimeData) this;
            if (precision >= 0 && precision <= Constants.MAX_TIME_PRECISION) {
                data.setPrecision(precision);
            }
        }
    }

    private void annotateTimestampField(Field field) {
        Class<? extends Annotation> annotationClass = TimestampField.class;
        Class<?>[] validTypes = new Class<?>[]{TimestampParameter.class, TimestampProperty.class};
        boolean log = isParameter() || depth() == 0;
        _annotatedWithTimestampField = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithTimestampField) {
            TimestampField annotation = field.getAnnotation(TimestampField.class);
            int precision = annotation.precision();
            TimestampData data = (TimestampData) this;
            if (precision >= 0 && precision <= Constants.MAX_TIME_PRECISION) {
                data.setPrecision(precision);
            }
        }
    }

    private void annotateFileReference(Field field) {
        Class<? extends Annotation> annotationClass = FileReference.class;
        Class<?>[] validTypes = new Class<?>[]{StringParameter.class, StringProperty.class};
        boolean log = isParameter() || depth() == 0;
        _annotatedWithFileReference = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (_annotatedWithFileReference) {
            FileReference annotation = field.getAnnotation(FileReference.class);
            int max = annotation.max();
            if (max > 0) {
                _maxInputFileSize = max;
            }
            MimeType[] types = annotation.types();
            if (types != null && types.length > 0) {
                _validInputFileTypes = types;
            }
            _uploadStorageOption = annotation.storage();
            switch (_uploadStorageOption) {
                case FILE:
                    break;
                default:
                    _joinFieldName = annotation.joinField();
                    break;
            }
            if (StringUtils.isNotBlank(_joinFieldName)) {
                Operation declaringOperation = getDeclaringOperation();
                Entity declaringEntity = declaringOperation.getDeclaringEntity();
                String[] strings = new String[]{declaringOperation.getName(), getName(), "joinField"};
                String role = StringUtils.join(strings, ".");
                _joinField = XS1.getField(true, role, _joinFieldName, declaringEntity.getClass(), Entity.class, new Class<?>[]{});
                if (_joinField != null) {
                    _joinProperty = XS1.getProperty(_joinField, declaringEntity);
                }
            }
            _updateableFileReference = annotation.updateable().toBoolean(_updateableFileReference);
        }
    }

    private void annotateInstanceReference(Field field) {
        Operation declaringOperation = getDeclaringOperation();
//      Class<?> declaringClass = declaringOperation.getDeclaringEntity().getDataType();
//      Class<?> declaringClass = field.getDeclaringClass().getDeclaringClass();
        Class<?> declaringClass = declaringOperation.getDeclaringField().getDeclaringClass();
        Class<? extends Annotation> annotationClass = InstanceReference.class;
        Class<?>[] validTypes = new Class<?>[]{declaringClass};
        boolean log = true;
        boolean aye = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (aye) {
            Field previous = getDeclaringArtifact().put(annotationClass, field);
            if (previous == null) {
                _annotatedWithInstanceReference = true;
                declaringOperation.setOperationKind(OperationKind.INSTANCE);
            } else if (log) {
                XS1.logDuplicateAnnotation(field, annotationClass, previous);
            }
        }
        /**/
        if (_annotatedWithInstanceReference) {
            _required = true;
        }
    }

    private void annotateParameterField(Field field) {
        _annotatedWithParameterField = field.isAnnotationPresent(ParameterField.class);
        if (_annotatedWithParameterField) {
            ParameterField annotation = field.getAnnotation(ParameterField.class);
            _auditable = annotation.auditable().toBoolean(_auditable);
            _password = annotation.password().toBoolean(_password);
            _required = _annotatedWithInstanceReference ? Boolean.TRUE : annotation.required().toBoolean(_required);
            _hiddenField = !_annotatedWithInstanceReference && annotation.hidden().toBoolean(_hiddenField);
            _submitField = annotation.submit().toBoolean(_submitField);
            _linkedFieldName = annotation.linkedField();
            _linkedColumnName = annotation.linkedColumn();
            _linkedColumnOperator = annotation.operator();
//          _sequenceNumber = Math.max(0, annotation.sequence());
            _sequenceNumber = annotation.sequence();
            if (StringUtils.isNotBlank(_linkedFieldName)) {
                Operation declaringOperation = getDeclaringOperation();
                Entity declaringEntity = declaringOperation.getDeclaringEntity();
                Class<?>[] validTypes = _dataClass == null ? new Class<?>[]{} : new Class<?>[]{_dataClass};
                String[] strings = new String[]{declaringOperation.getName(), getName(), "linkedField"};
                String role = StringUtils.join(strings, ".");
                _linkedField = XS1.getField(true, role, _linkedFieldName, declaringEntity.getClass(), Entity.class, validTypes);
                if (_linkedField != null) {
                    _linkedProperty = XS1.getProperty(_linkedField, declaringEntity);
                }
            }
        }
    }

    private void annotatePropertyField(Field field) {
        _annotatedWithPropertyField = field.isAnnotationPresent(PropertyField.class);
        if (_annotatedWithPropertyField) {
            PropertyField annotation = field.getAnnotation(PropertyField.class);
            _propertyAccess = annotation.access();
            _auditable = annotation.auditable().toBoolean(_auditable);
            _password = annotation.password().toBoolean(_password);
            _required = annotation.required().toBoolean(_required);
            _hiddenField = annotation.hidden().toBoolean(_hiddenField);
            _createField = annotation.create().toBoolean(_createField);
            _updateField = annotation.update().toBoolean(_updateField);
            _searchField = annotation.search().toBoolean(_searchField);
            _filterField = annotation.filter().toBoolean(_filterField);
            _tableField = annotation.table().toBoolean(_tableField);
            _detailField = annotation.detail().toBoolean(_detailField);
            _reportField = annotation.report().toBoolean(_reportField);
            _exportField = annotation.export().toBoolean(_exportField);
            _submitField = annotation.submit().toBoolean(_submitField);
            _headertextlessField = annotation.headertextless().toBoolean(_headertextlessField);
            _headingField = annotation.heading().toBoolean(_headingField);
            _defaultCondition = annotation.defaultCondition();
//          _sequenceNumber = Math.max(0, annotation.sequence());
            _sequenceNumber = annotation.sequence();
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
        boolean annotated = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (annotated) {
            BooleanDataGen annotation = field.getAnnotation(BooleanDataGen.class);
            _dataGenType = annotation.type();
            _dataGenFunction = StringUtils.trimToNull(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenTrueable = Math.min(100, Math.max(0, annotation.trueable()));
            if (_dataGenNullable + _dataGenTrueable > 100) {
                _dataGenNullable = 100 - _dataGenTrueable;
            }
            _annotatedWithDataGen = true;
        }
    }

    private void annotateCharacterDataGen(Field field) {
        Class<? extends Annotation> annotationClass = CharacterDataGen.class;
        Class<?>[] validTypes = new Class<?>[]{CharacterProperty.class, StringProperty.class};
        boolean log = depth() == 0;
        boolean annotated = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (annotated) {
            CharacterDataGen annotation = field.getAnnotation(CharacterDataGen.class);
            _dataGenType = annotation.type();
//          _dataGenSeriesStart = annotation.start();
//          _dataGenSeriesStop = annotation.stop();
//          _dataGenSeriesStep = annotation.step();
            _dataGenSeriesStart = Math.min(10000, Math.max(1, annotation.start()));
            _dataGenSeriesStop = Math.min(10000, Math.max(1, annotation.stop()));
            _dataGenSeriesStep = Math.min(10000, Math.max(1, annotation.step()));
            _dataGenFunction = StringUtils.trimToNull(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenPattern = StringUtils.trimToNull(annotation.pattern());
            _dataGenPrefix = StringUtils.trimToNull(annotation.prefix());
            _dataGenSuffix = StringUtils.trimToNull(annotation.suffix());
            _annotatedWithDataGen = true;
        }
    }

    private void annotateNumericDataGen(Field field) {
        Class<? extends Annotation> annotationClass = NumericDataGen.class;
        Class<?>[] validTypes = new Class<?>[]{BigDecimalProperty.class, BigIntegerProperty.class,
            ByteProperty.class, ShortProperty.class, IntegerProperty.class, LongProperty.class,
            FloatProperty.class, DoubleProperty.class};
        boolean log = depth() == 0;
        boolean annotated = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (annotated) {
            NumericDataGen annotation = field.getAnnotation(NumericDataGen.class);
            _dataGenType = annotation.type();
//          _dataGenSeriesStart = annotation.start();
//          _dataGenSeriesStop = annotation.stop();
//          _dataGenSeriesStep = annotation.step();
            _dataGenSeriesStart = Math.min(10000, Math.max(1, annotation.start()));
            _dataGenSeriesStop = Math.min(10000, Math.max(1, annotation.stop()));
            _dataGenSeriesStep = Math.min(10000, Math.max(1, annotation.step()));
            _dataGenFunction = StringUtils.trimToNull(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenMin = someIntegerValue(field, annotation.min());
            _dataGenMax = someIntegerValue(field, annotation.max());
            _dataGenNumericAction = annotation.action();
            _dataGenFactor = someBigDecimalValue(field, annotation.factor());
            _annotatedWithDataGen = true;
        }
    }

    private void annotateTemporalDataGen(Field field) {
        Class<? extends Annotation> annotationClass = TemporalDataGen.class;
        Class<?>[] validTypes = new Class<?>[]{DateProperty.class, TimeProperty.class, TimestampProperty.class};
        boolean log = depth() == 0;
        boolean annotated = field.isAnnotationPresent(annotationClass)
            && XS1.checkFieldAnnotation(log, field, annotationClass, validTypes);
        /**/
        if (annotated) {
            TemporalDataGen annotation = field.getAnnotation(TemporalDataGen.class);
            _dataGenType = annotation.type();
//          _dataGenSeriesStart = annotation.start();
//          _dataGenSeriesStop = annotation.stop();
//          _dataGenSeriesStep = annotation.step();
            _dataGenSeriesStart = Math.min(10000, Math.max(1, annotation.start()));
            _dataGenSeriesStop = Math.min(10000, Math.max(1, annotation.stop()));
            _dataGenSeriesStep = Math.min(10000, Math.max(1, annotation.step()));
            _dataGenFunction = StringUtils.trimToNull(annotation.function());
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
            _dataGenMin = someTemporalValue(field, annotation.min());
            _dataGenMax = someTemporalValue(field, annotation.max());
            _dataGenTemporalInterval = annotation.interval();
            _annotatedWithDataGen = true;
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
        } catch (Exception e) {
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
        Date someRelativeDate = someRelativeDateValue(string);
        if (someRelativeDate != null) {
            return someRelativeDate;
        }
        try {
            return Date.valueOf(string);
        } catch (Exception e) {
            logInvalidDataExpression(field, "date", e);
        }
        return null;
    }

    private Date someRelativeDateValue(String string) {
        char[] validUnits = {'Y', 'M', 'D'};
        TemporalAddend addend = temporalAddendValueOf(string, validUnits, 'D');
        if (addend == null) {
            return null;
        }
        return TimeUtils.addDate(currentDate, addend.number, addend.unit);
    }

    private final Time currentTime = TimeUtils.currentTime();

    private Time someTimeValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        Time someRelativeTime = someRelativeTimeValue(string);
        if (someRelativeTime != null) {
            return someRelativeTime;
        }
        try {
            return Time.valueOf(string);
        } catch (Exception e) {
            logInvalidDataExpression(field, "time", e);
        }
        return null;
    }

    private Time someRelativeTimeValue(String string) {
        char[] validUnits = {'h', 'm', 's'};
        TemporalAddend addend = temporalAddendValueOf(string, validUnits, 'h');
        if (addend == null) {
            return null;
        }
        return TimeUtils.addTime(currentTime, addend.number, addend.unit);
    }

    private final Timestamp currentTimestamp = TimeUtils.currentTimestamp();

    private Timestamp someTimestampValue(Field field, String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        Timestamp someRelativeTimestamp = someRelativeTimestampValue(string);
        if (someRelativeTimestamp != null) {
            return someRelativeTimestamp;
        }
        try {
            return Timestamp.valueOf(string);
        } catch (Exception e) {
            logInvalidDataExpression(field, "timestamp", e);
        }
        return null;
    }

    private Timestamp someRelativeTimestampValue(String string) {
        char[] validUnits = {'Y', 'M', 'D', 'h', 'm', 's'};
        TemporalAddend addend = temporalAddendValueOf(string, validUnits, 'D');
        if (addend == null) {
            return null;
        }
        return TimeUtils.addTimestamp(currentTimestamp, addend.number, addend.unit);
    }

    private void logInvalidDataExpression(Field field, String string, Exception e) {
        if (depth() == 0) {
            logger.error(getDeclaringArtifactClassName() + "." + field.getName() + " has an invalid " + string + " data expression");
            TLC.getProject().getParser().increaseErrorCount();
        }
    }

    private TemporalAddend temporalAddendValueOf(String string, char[] validUnits, char defaultUnit) {
        String chopped;
        String trimmed = StringUtils.trimToNull(string);
        if (trimmed == null) {
            return null;
        }
        int end = trimmed.length() - 1;
        char unit = trimmed.charAt(end);
        if (Character.isDigit(unit)) {
            chopped = trimmed;
            unit = defaultUnit;
        } else if (end > 0 && ArrayUtils.contains(validUnits, unit)) {
            chopped = trimmed.substring(0, end);
        } else {
            return null;
        }
        try {
            Integer number = Integer.valueOf(chopped);
            return new TemporalAddend(number, unit);
        } catch (Exception e) {
            return null;
        }
    }

    private class TemporalAddend {

        int number;

        char unit;

        TemporalAddend(int i, char c) {
            number = i;
            unit = c;
        }

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
     * @return true if is a Parameter; otherwise false
     */
    @Override
    public boolean isParameter() {
        return this instanceof Parameter
            && getDeclaringField() != null
            && getDeclaringArtifact() instanceof Operation;
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

    @Override
    public boolean isPrimaryKeyProperty() {
        return isProperty()
            && (_annotatedWithPrimaryKey
            || getDeclaringField().equals(getDeclaringEntity().getPrimaryKeyField()));
    }

    @Override
    public boolean isVersionProperty() {
        return isProperty()
            && (_annotatedWithVersionProperty
            || getDeclaringField().equals(getDeclaringEntity().getVersionField()));
    }

    @Override
    public boolean isNumericKeyProperty() {
        return isProperty()
            && (_annotatedWithNumericKey
            || getDeclaringField().equals(getDeclaringEntity().getNumericKeyField()));
    }

    @Override
    public boolean isCharacterKeyProperty() {
        return isProperty()
            && (_annotatedWithCharacterKey
            || getDeclaringField().equals(getDeclaringEntity().getCharacterKeyField()));
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
    // </editor-fold>

    /**
     * @return the parameter path list
     */
    @Override
    @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getParameterPathList() {
        List list = new ArrayList<>();
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
     * @return the property path list
     */
    @Override
    @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getPropertyPathList() {
        List list = new ArrayList<>();
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
     * @return the default wrapper class
     */
    @Override
    public Class<? extends DataArtifactWrapper> getDefaultWrapperClass() {
        return DataArtifactWrapper.class;
    }

    protected boolean validInitialValue(Object object) {
//      if (isParameterProperty()) {
//          return true;
//      }
        if (object == null) {
            logger.error("null is not a valid initial value for " + getFullName());
            TLC.getProject().getParser().increaseErrorCount();
            return false;
        }
        if (object instanceof SpecialTemporalValue && !validSpecialTemporalValue((SpecialTemporalValue) object)) {
            logger.error(object + " is not a valid initial value for " + getFullName());
            TLC.getProject().getParser().increaseErrorCount();
            return false;
        }
        if (isParameter()) {
            if (object instanceof Instance) {
            } else if (object instanceof Artifact) {
                Artifact artifact = (Artifact) object;
                logger.error(artifact.getFullName() + " is not a valid initial value for parameter " + getFullName());
                TLC.getProject().getParser().increaseErrorCount();
                return false;
            } else if (object instanceof NamedValue && this instanceof Entity) {
                NamedValue namedValue = (NamedValue) object;
                logger.error(namedValue.name() + " is not a valid initial value for parameter " + getFullName());
                TLC.getProject().getParser().increaseErrorCount();
                return false;
            }
        }
        return true;
    }

    protected boolean validDefaultValue(Object object) {
//      if (isParameterProperty()) {
//          return true;
//      }
        if (object == null) {
            logger.error("null is not a valid default value for " + getFullName());
            TLC.getProject().getParser().increaseErrorCount();
            return false;
        }
        if (object instanceof SpecialTemporalValue && !validSpecialTemporalValue((SpecialTemporalValue) object)) {
            logger.error(object + " is not a valid default value for " + getFullName());
            TLC.getProject().getParser().increaseErrorCount();
            return false;
        }
        return true;
    }

    protected boolean validCurrentValue(Object object) {
        if (object == null) {
            logger.error("null is not a valid current value for " + getFullName());
            TLC.getProject().getParser().increaseErrorCount();
            return false;
        }
        if (object instanceof SpecialTemporalValue && !validSpecialTemporalValue((SpecialTemporalValue) object)) {
            logger.error(object + " is not a valid current value for " + getFullName());
            TLC.getProject().getParser().increaseErrorCount();
            return false;
        }
        return true;
    }

    protected boolean validSpecialTemporalValue(SpecialTemporalValue value) {
        switch (value) {
            case CURRENT_DATE:
                return isDateData() || isTimestampData();
            case CURRENT_TIME:
                return isTimeData() || isTimestampData();
            case CURRENT_TIMESTAMP:
                return isTimestampData();
            default:
                return isTemporalPrimitive();
        }
    }

    /**
     * @return the property size in pixels
     */
    @Override
    public int getPixels() {
        if (isEntity() || isHiddenField() || isPassword()) {
            return 0;
        } else if (this instanceof BooleanPrimitive) {
            return 48;
        } else if (this instanceof CharacterData) {
            return 48;
        } else if (this instanceof StringData) {
            StringData string = (StringData) this;
            Integer maxLength = string.getMaxLength();
            if (maxLength == null) {
                return 384;
            } else if (maxLength > 100) {
                return 256;
            } else if (maxLength > 50) {
                return 192;
            } else if (maxLength > 20) {
                return 128;
            } else if (maxLength > 10) {
                return 64;
            } else {
                return 48;
            }
        } else if (this instanceof ByteData || this instanceof ShortData || this instanceof IntegerData) {
            return 64;
        } else if (this instanceof NumericPrimitive) {
            return 128;
        } else if (this instanceof TimestampData) {
            return 104;
        } else if (this instanceof TemporalPrimitive) {
            return 64;
        } else {
            return 0;
        }
    }

    public boolean isSinglePropertyOfUniqueKey() {
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
                string += fee + tab + "dataType" + faa + _dataType + foo;
                if (getDeclaringField() != null && getDeclaringArtifact() != null) {
//                  string += fee + tab + "baseField" + faa + _annotatedWithBaseField + foo;
//                  string += fee + tab + "argumentField" + faa + _annotatedWithArgumentField + foo;
//                  string += fee + tab + "columnField" + faa + _annotatedWithColumnField + foo;
//                  string += fee + tab + "PropertyField" + faa + _annotatedWithPropertyField + foo;
//                  string += fee + tab + "PrimaryKey" + faa + _annotatedWithPrimaryKey + foo;
//                  string += fee + tab + "VersionProperty" + faa + _annotatedWithVersionProperty + foo;
//                  string += fee + tab + "NumericKey" + faa + _annotatedWithNumericKey + foo;
//                  string += fee + tab + "CharacterKey" + faa + _annotatedWithCharacterKey + foo;
//                  string += fee + tab + "NameProperty" + faa + _annotatedWithNameProperty + foo;
//                  string += fee + tab + "DescriptionProperty" + faa + _annotatedWithDescriptionProperty + foo;
//                  string += fee + tab + "InactiveIndicator" + faa + _annotatedWithInactiveIndicator + foo;
//                  string += fee + tab + "UrlProperty" + faa + _annotatedWithUrlProperty + foo;
//                  string += fee + tab + "ParentProperty" + faa + _annotatedWithParentProperty + foo;
//                  string += fee + tab + "OwnerProperty" + faa + _annotatedWithOwnerProperty + foo;
//                  string += fee + tab + "SegmentProperty" + faa + _annotatedWithSegmentProperty + foo;
//                  string += fee + tab + "UniqueKey" + faa + _annotatedWithUniqueKey + foo;
//                  string += fee + tab + "BusinessKey" + faa + _annotatedWithBusinessKey + foo;
//                  string += fee + tab + "DiscriminatorColumn" + faa + _annotatedWithDiscriminatorColumn + foo;
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
                        string += fee + tab + "reportField" + faa + _reportField + foo;
                        string += fee + tab + "exportField" + faa + _exportField + foo;
                        string += fee + tab + "submitField" + faa + _submitField + foo;
                        string += fee + tab + "calculable" + faa + _calculable + foo;
                        string += fee + tab + "nullable" + faa + _nullable + foo;
                        string += fee + tab + "insertable" + faa + _insertable + foo;
                        string += fee + tab + "updateable" + faa + _updateable + foo;
                        string += fee + tab + "unique" + faa + _unique + foo;
                    }
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
