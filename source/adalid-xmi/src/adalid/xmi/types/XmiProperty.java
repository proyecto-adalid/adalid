/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.types;

import adalid.commons.util.StrUtils;
import adalid.xmi.util.Typenames;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author cmedina
 */
public class XmiProperty {

    private String propertyType = "";

    private String packageName = "";

    private String fullPropertyType = "";

    private boolean ignored = false;

    private boolean entityProperty = false;

    private boolean enumerationProperty = false;

    private boolean extension;

    private String initialValue = "";

    private String name = "";

    private String alias = "";

    private String defaultLabel = "";

    private String defaultShortLabel = "";

    private String defaultCollectionLabel = "";

    private String defaultDescription = "";

    private String defaultShortDescription = "";

    private String defaultTooltip = "";

    private String initialValueString = "";

    private String defaultValueString = "";

    private boolean auditable = true;

    private boolean required = false;

    private boolean hidden = false;

    private boolean create = true;

    private boolean update = true;

    private boolean search = false;

    private boolean filter = true;

    private boolean table = false;

    private boolean detail = true;

    private boolean report = false;

    private boolean export = true;

    private boolean submit = false;

    private String defaultValue = "";

    private int precision;

    private int scale;

    private int minLength = 0;

    private int maxLength = 100;

    private boolean calculable = false;

    private boolean nullable = true;

    private boolean insertable = true;

    private boolean updateable = true;

    private boolean unique = false;

    private boolean fileReference = false;

    private boolean inactiveIndicator = false;

    private boolean bigDecimalField = false;

    private boolean stringField = false;

    private boolean timeField = false;

    private boolean timestampField = false;

    private String manyToOneNavigability = "UNSPECIFIED";

    private boolean oneToOne = false;

    private boolean manyToOne = false;

    private String associationId = "";

    private String oneToOneNavigability = "UNSPECIFIED";
    //reference property tagged values

    private String referenceSearchType = "UNSPECIFIED";

    private String onDeleteAction = "NONE";

    private String onUpdateAction = "NONE";

    private boolean foreignKey;

    private String masterDetailView = "UNSPECIFIED";

    private int maxDepth = 0;

    private int maxRound = 0;

    private String propertyFieldString = "";

    private String columnFieldString = "";

    private String bigDecimalFieldString = "";

    private String stringFieldString = "";

    private String timeFieldString = "";

    private String timestampFieldString = "";

    private String foreignKeyString = "";

    private String manyToOneString = "";

    private String oneToOneString = "";

    private String allocationString = "";

    private boolean customizedProperty = false;

    public XmiProperty(String propertyType, boolean entityProperty, String packageName, boolean enumerationProperty) {
        this.propertyType = propertyType;
        this.packageName = packageName;
        this.setEntityProperty(entityProperty);
        this.setEnumerationProperty(enumerationProperty);
    }

    public XmiProperty(XmiPersistentEntity entity) {
        this.propertyType = entity.getName();
        this.packageName = entity.getPackageName();
        this.setEntityProperty(true);
        if (entity.isEnumeration()) {
            this.setEnumerationProperty(true);
        }
    }

    /**
     * @return the propertyType
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * @param propertyType the propertyType to set
     */
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the fullPropertyType
     */
    public String getFullPropertyType() {
        if (!packageName.isEmpty()) {
            fullPropertyType = packageName + "." + propertyType;
        } else {
            fullPropertyType = propertyType;
        }
        return fullPropertyType;
    }

    /**
     * @param fullPropertyType the fullPropertyType to set
     */
    public void setFullPropertyType(String fullPropertyType) {
        this.fullPropertyType = fullPropertyType;
    }

    /**
     * @return the ignored
     */
    public boolean isIgnored() {
        return ignored;
    }

    /**
     * @param ignored the ignored to set
     */
    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    /**
     * @return the entityProperty
     */
    public boolean isEntityProperty() {
        return entityProperty;
    }

    /**
     * @param entityProperty the entityProperty to set
     */
    public final void setEntityProperty(boolean entityProperty) {
        this.entityProperty = entityProperty;
    }

    /**
     * @return the enumerationProperty
     */
    public final boolean isEnumerationProperty() {
        return enumerationProperty;
    }

    /**
     * @param enumerationProperty the enumerationProperty to set
     */
    public final void setEnumerationProperty(boolean enumerationProperty) {
        this.enumerationProperty = enumerationProperty;
    }

    /**
     * @return the extension
     */
    public Boolean isExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(Boolean extension) {
        this.extension = extension;
    }

    /**
     * @return the propertyName
     */
    public String getName() {
        return name;
    }

    /**
     * @param propertyName the propertyName to set
     */
    public void setName(String propertyName) {
        this.name = propertyName;
    }

    public String getOneToOneNavigability() {
        return oneToOneNavigability;
    }

    public void setOneToOneNavigability(String _oneToOne) {
        if (!_oneToOne.isEmpty()) {
            this.oneToOneNavigability = _oneToOne;
        }
    }

    public String getManyToOneNavigability() {
        return manyToOneNavigability;
    }

    public void setManyToOneNavigability(String _manyToOne) {
        if (!_manyToOne.isEmpty()) {
            this.manyToOneNavigability = _manyToOne;
        }
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the defaultLabel
     */
    public String getDefaultLabel() {
        return StringEscapeUtils.unescapeJava(defaultLabel);
    }

    /**
     * @param defaultLabel the defaultLabel to set
     */
    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

    /**
     * @return the defaultShortLabel
     */
    public String getDefaultShortLabel() {
        return StringEscapeUtils.unescapeJava(defaultShortLabel);
    }

    /**
     * @param defaultShortLabel the defaultShortLabel to set
     */
    public void setDefaultShortLabel(String defaultShortLabel) {
        this.defaultShortLabel = defaultShortLabel;
    }

    /**
     * @return the defaultCollectionLabel
     */
    public String getDefaultCollectionLabel() {
        return StringEscapeUtils.unescapeJava(defaultCollectionLabel);
    }

    /**
     * @param defaultCollectionLabel the defaultCollectionLabel to set
     */
    public void setDefaultCollectionLabel(String defaultCollectionLabel) {
        this.defaultCollectionLabel = defaultCollectionLabel;
    }

    /**
     * @return the defaultDescription
     */
    public String getDefaultDescription() {
        return StringEscapeUtils.unescapeJava(defaultDescription);
    }

    /**
     * @param defaultDescription the defaultDescription to set
     */
    public void setDefaultDescription(String defaultDescription) {
        this.defaultDescription = defaultDescription;
    }

    /**
     * @return the defaultShortDescription
     */
    public String getDefaultShortDescription() {
        return StringEscapeUtils.unescapeJava(defaultShortDescription);
    }

    /**
     * @param defaultShortDescription the defaultShortDescription to set
     */
    public void setDefaultShortDescription(String defaultShortDescription) {
        this.defaultShortDescription = defaultShortDescription;
    }

    /**
     * @return the defaultTooltip
     */
    public String getDefaultTooltip() {
        return StringEscapeUtils.unescapeJava(defaultTooltip);
    }

    /**
     * @param defaultTooltip the defaultTooltip to set
     */
    public void setDefaultTooltip(String defaultTooltip) {
        this.defaultTooltip = defaultTooltip;
    }

    /**
     * @return the unique
     */
    public Boolean isUnique() {
        return unique;
    }

    /**
     * @param unique the unique to set
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    /**
     * @return the fileReference
     */
    public boolean isFileReference() {
        if (!this.propertyType.equals(Typenames.StringProperty)) {
            fileReference = false;
        }
        return fileReference;
    }

    /**
     * @param fileReference the fileReference to set
     */
    public void setFileReference(boolean fileReference) {
        this.fileReference = fileReference;
    }

    /**
     * @return the inactiveIndicator
     */
    public boolean isInactiveIndicator() {
        if (!this.propertyType.equals(Typenames.BooleanProperty)) {
            inactiveIndicator = false;
        }
        return inactiveIndicator;
    }

    /**
     * @param inactiveIndicator the inactiveIndicator to set
     */
    public void setInactiveIndicator(boolean inactiveIndicator) {
        this.inactiveIndicator = inactiveIndicator;
    }

    /**
     * @return the bigDecimalReference
     */
    public boolean isBigDecimalField() {
        bigDecimalField = this.getPropertyType().equals(Typenames.BigDecimalProperty);
        return bigDecimalField;
    }

    /**
     * @return the stringReference
     */
    public boolean isStringField() {
        stringField = this.getPropertyType().equals(Typenames.StringProperty);
        return stringField;
    }

    /**
     * @return the timeField
     */
    public boolean isTimeField() {
        timeField = this.getPropertyType().equals(Typenames.TimeProperty);
        return timeField;
    }

    /**
     * @return the timestampField
     */
    public boolean isTimestampField() {
        timestampField = this.getPropertyType().equals(Typenames.TimestampProperty);
        return timestampField;
    }

    /**
     * @return the table
     */
    public Boolean isTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(Boolean table) {
        this.table = table;
    }

    /**
     * @return the detail
     */
    public Boolean isDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    /**
     * @return the report
     */
    public Boolean isReport() {
        return report;
    }

    /**
     * @param report the report to set
     */
    public void setReport(Boolean report) {
        this.report = report;
    }

    /**
     * @return the export
     */
    public Boolean isExport() {
        return export;
    }

    /**
     * @param export the export to set
     */
    public void setExport(Boolean export) {
        this.export = export;
    }

    /**
     * @return the search
     */
    public Boolean isSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(Boolean search) {
        this.search = search;
    }

    /**
     * @return the filter
     */
    public Boolean isFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(Boolean filter) {
        this.filter = filter;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the precision
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * @return the scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * @return the minLength
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    /**
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the calculable
     */
    public boolean isCalculable() {
        return calculable;
    }

    /**
     * @param calculable the calculable to set
     */
    public void setCalculable(boolean calculable) {
        this.calculable = calculable;
    }

    /**
     * @return the auditable
     */
    public boolean isAuditable() {
        return auditable;
    }

    /**
     * @param auditable the auditable to set
     */
    public void setAuditable(Boolean auditable) {
        this.auditable = auditable;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * @return the insertable
     */
    public boolean isInsertable() {
        return insertable;
    }

    /**
     * @param insertable the insertable to set
     */
    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    /**
     * @return the updateable
     */
    public boolean isUpdateable() {
        return updateable;
    }

    /**
     * @param updateable the updateable to set
     */
    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    /**
     * @return the submit
     */
    public boolean isSubmit() {
        return submit;
    }

    /**
     * @param submit the submit to set
     */
    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

    /**
     * @return the hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return the required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the create
     */
    public boolean isCreate() {
        return create;
    }

    /**
     * @param create the create to set
     */
    public void setCreate(boolean create) {
        this.create = create;
    }

    /**
     * @return the update
     */
    public boolean isUpdate() {
        return update;
    }

    /**
     * @param update the update to set
     */
    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void combine(XmiProperty newProperty) {
        this.setOnDeleteAction(newProperty.getOnDeleteAction());
        this.setOnUpdateAction(newProperty.getOnDeleteAction());
        this.setReferenceSearchType(newProperty.getReferenceSearchType());
        this.setForeignKey(newProperty.isForeignKey());
        this.setMasterDetailView(newProperty.getMasterDetailView());

    }

    /**
     * @return the referenceSearchType
     */
    public String getReferenceSearchType() {
        return referenceSearchType;
    }

    /**
     * @param referenceSearchType the referenceSearchType to set
     */
    public void setReferenceSearchType(String referenceSearchType) {
        if (!referenceSearchType.isEmpty()) {
            this.referenceSearchType = referenceSearchType;
        }
    }

    /**
     * @return the onDeleteAction
     */
    public String getOnDeleteAction() {
        return onDeleteAction;
    }

    /**
     * @param onDeleteAction the onDeleteAction to set
     */
    public void setOnDeleteAction(String onDeleteAction) {
        if (!onDeleteAction.isEmpty()) {
            this.onDeleteAction = onDeleteAction;
        }
    }

    /**
     * @return the onUpdateAction
     */
    public String getOnUpdateAction() {
        return onUpdateAction;
    }

    /**
     * @param onUpdateAction the onUpdateAction to set
     */
    public void setOnUpdateAction(String onUpdateAction) {
        if (!onUpdateAction.isEmpty()) {
            this.onUpdateAction = onUpdateAction;
        }
    }

    /**
     * @return the foreignKey
     */
    public boolean isForeignKey() {
        return foreignKey;
    }

    /**
     * @param foreignKey the foreignKey to set
     */
    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * @return the masterDetailView
     */
    public String getMasterDetailView() {
        return masterDetailView;
    }

    /**
     * @param masterDetailView the masterDetailView to set
     */
    public void setMasterDetailView(String masterDetailView) {
        if (!masterDetailView.isEmpty()) {
            this.masterDetailView = masterDetailView;
        }
    }

    /**
     * @return the maxDepth
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * @param maxDepth the maxDepth to set
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * @return the maxRound
     */
    public int getMaxRound() {
        return maxRound;
    }

    /**
     * @param maxRound the maxRound to set
     */
    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }

    /**
     * @return the oneToOne
     */
    public boolean isOneToOne() {
        return oneToOne;
    }

    /**
     * @param oneToOne the oneToOne to set
     */
    public void setOneToOne(boolean oneToOne) {
        this.oneToOne = oneToOne;
    }

    /**
     * @return the manyToOne
     */
    public boolean isManyToOne() {
        return manyToOne;
    }

    /**
     * @param manyToOne the manyToOne to set
     */
    public void setManyToOne(boolean manyToOne) {
        this.manyToOne = manyToOne;
    }

    /**
     * @return the associationId
     */
    public String getAssociationId() {
        return associationId;
    }

    /**
     * @param associationId the associationId to set
     */
    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    /**
     * @return the initialValue
     */
    public String getInitialValue() {
        return initialValue;
    }

    /**
     * @param initialValue the initialValue to set
     */
    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }

    public String getInitialValueString() {
        if (StrUtils.getObjeto(this.getInitialValue(), String.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.StringProperty)) {
            initialValueString = "\"" + this.getInitialValue() + "\"";
        } else if ((StrUtils.getObjeto(this.getInitialValue(), Integer.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.IntegerProperty))
            || (StrUtils.getObjeto(this.getInitialValue(), Double.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.DoubleProperty))
            || (StrUtils.getObjeto(this.getInitialValue(), Boolean.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.BooleanProperty))) {
            initialValueString = this.getInitialValue();
        }
        return initialValueString;
    }

    public String getDefaultValueString() {
        if (StrUtils.getObjeto(this.getDefaultValue(), String.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.StringProperty)) {
            defaultValueString = "\"" + this.getDefaultValue() + "\"";
        } else if ((StrUtils.getObjeto(this.getDefaultValue(), Integer.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.IntegerProperty))
            || (StrUtils.getObjeto(this.getDefaultValue(), Double.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.DoubleProperty))
            || (StrUtils.getObjeto(this.getDefaultValue(), Boolean.class) != null && this.getPropertyType().equalsIgnoreCase(Typenames.BooleanProperty))) {
            defaultValueString = this.getDefaultValue();
        }
        return defaultValueString;
    }

    public boolean isValidNumericKey() {
        return (((propertyType.equalsIgnoreCase(Typenames.IntegerProperty)
            || propertyType.equalsIgnoreCase(Typenames.LongProperty)))
            && !this.isNullable());
    }

    public boolean isValidCharacterKey() {
        return (propertyType.equalsIgnoreCase(Typenames.StringProperty)
            && !this.isNullable());
    }

    public boolean isValidDiscriminator() {
        return (propertyType.equalsIgnoreCase(Typenames.IntegerProperty)
            || propertyType.equalsIgnoreCase(Typenames.StringProperty)
            || propertyType.equalsIgnoreCase(Typenames.CharacterProperty)
            || this.isEntityProperty());
    }

    public String getPropertyFieldString() {
        propertyFieldString = "";
        if (!auditable) {
            propertyFieldString += ", auditable = Kleenean.FALSE";
        }
        if (required) {
            propertyFieldString += ", required = Kleenean.TRUE";
        }
        if (hidden) {
            propertyFieldString += ", hidden = Kleenean.TRUE";
        }
        if (!insertable) {
            propertyFieldString += ", create = Kleenean.FALSE";
        }
        if (!updateable) {
            propertyFieldString += ", update = Kleenean.FALSE";
        }
        if (search) {
            propertyFieldString += ", search = Kleenean.TRUE";
        }
        if (!filter) {
            propertyFieldString += ", filter = Kleenean.FALSE";
        }
        if (table) {
            propertyFieldString += ", table = Kleenean.TRUE";
        }
        if (!detail) {
            propertyFieldString += ", detail = Kleenean.FALSE";
        }
        if (report) {
            propertyFieldString += ", report = Kleenean.TRUE";
        }
        if (!export) {
            propertyFieldString += ", export = Kleenean.FALSE";
        }
        if (submit) {
            propertyFieldString += ", submit = Kleenean.TRUE";
        }
        return StringUtils.removeStart(propertyFieldString, ", ");
    }

    /**
     * @param propertyFieldString the propertyFieldString to set
     */
    public void setPropertyFieldString(String propertyFieldString) {
        this.propertyFieldString = propertyFieldString;
    }

    /**
     * @return the columnFieldString
     */
    public String getColumnFieldString() {
        columnFieldString = "";
        if (calculable) {
            columnFieldString += ", calculable = Kleenean.TRUE";
        }
        if (!nullable) {
            columnFieldString += ", nullable = Kleenean.FALSE";
        }
        if (!create) {
            columnFieldString += ", insertable = Kleenean.FALSE";
        }
        if (!update) {
            columnFieldString += ", updateable = Kleenean.FALSE";
        }
        if (unique) {
            columnFieldString += ", unique = Kleenean.TRUE";
        }
        return StringUtils.removeStart(columnFieldString, ", ");
    }

    /**
     * @param columnFieldString the columnFieldString to set
     */
    public void setColumnFieldString(String columnFieldString) {
        this.columnFieldString = columnFieldString;
    }

    /**
     * @return the bigDecimalFieldString
     */
    public String getBigDecimalFieldString() {
        if (this.isBigDecimalField()) {
            if (this.getPrecision() != 0) {
                bigDecimalFieldString = "precision = " + precision;
            }
            if (this.getScale() != 0) {
                if (this.getPrecision() == 0) {
                    bigDecimalFieldString = "scale = " + scale;
                } else {
                    bigDecimalFieldString += ", scale = " + scale;
                }
            }
        }
        return bigDecimalFieldString;
    }

    /**
     * @param bigDecimalFieldString the bigDecimalFieldString to set
     */
    public void setBigDecimalFieldString(String bigDecimalFieldString) {
        this.bigDecimalFieldString = bigDecimalFieldString;
    }

    /**
     * @return the stringFieldString
     */
    public String getStringFieldString() {
        if (this.isStringField()) {
            //TODO: Colocar la longitud en el diagrama
            if (this.name.contains("codigo")) {
                maxLength = 50;
            } else if (this.name.contains("nombre")) {
                maxLength = 100;
            } else if (this.name.contains("descripcion")) {
                maxLength = 2000;
            } else if (this.name.contains("observaciones")) {
                maxLength = 2000;
            } else {
                maxLength = 100;
            }
            if (this.getMinLength() != 0) {
                stringFieldString = "minLength = " + minLength;
            }
            if (this.getMaxLength() != 0) {
                if (this.getMinLength() != 0) {
                    stringFieldString += ", maxLength = " + maxLength;
                } else {
                    stringFieldString = "maxLength = " + maxLength;
                }
            }
        }
        return stringFieldString;
    }

    /**
     * @param stringFieldString the stringFieldString to set
     */
    public void setStringFieldString(String stringFieldString) {
        this.stringFieldString = stringFieldString;
    }

    /**
     * @return the timeFieldString
     */
    public String getTimeFieldString() {
        if (this.isTimeField()
            && this.getPrecision() != 0) {
            timeFieldString = "precision = " + precision;
        }
        return timeFieldString;
    }

    /**
     * @param timeFieldString the timeFieldString to set
     */
    public void setTimeFieldString(String timeFieldString) {
        this.timeFieldString = timeFieldString;
    }

    /**
     * @return the timestampFieldString
     */
    public String getTimestampFieldString() {
        if (this.isTimestampField()
            && this.getPrecision() != 0) {
            timestampFieldString = "precision = " + precision;
        }
        return timestampFieldString;
    }

    /**
     * @param timestampFieldString the timestampFieldString to set
     */
    public void setTimestampFieldString(String timestampFieldString) {
        this.timestampFieldString = timestampFieldString;
    }

    /**
     * @return the foreignKeyString
     */
    public String getForeignKeyString() {
        if (foreignKey) {
            setForeignKeyString("onDelete = OnDeleteAction." + onDeleteAction + ", onUpdate = OnUpdateAction." + onUpdateAction);
        }
        return foreignKeyString;
    }

    /**
     * @param foreignKeyString the foreignKeyString to set
     */
    public void setForeignKeyString(String foreignKeyString) {
        this.foreignKeyString = foreignKeyString;
    }

    /**
     * @return the manyToOneString
     */
    public String getManyToOneString() {
        if (manyToOne) {
            setManyToOneString("navigability = Navigability." + manyToOneNavigability + ", view = MasterDetailView." + masterDetailView);
        }
        return manyToOneString;
    }

    /**
     * @param manyToOneString the manyToOneString to set
     */
    public void setManyToOneString(String manyToOneString) {
        this.manyToOneString = manyToOneString;
    }

    /**
     * @return the oneToOneString
     */
    public String getOneToOneString() {
        if (oneToOne) {
            setOneToOneString("navigability = Navigability." + oneToOneNavigability);
        }
        return oneToOneString;
    }

    /**
     * @param oneToOneString the oneToOneString to set
     */
    public void setOneToOneString(String oneToOneString) {
        this.oneToOneString = oneToOneString;
    }

    /**
     * @return the oneToOneString
     */
    public String getAllocationString() {
        if ((maxRound > 0 || maxDepth > 0) && entityProperty) {
            allocationString = "maxRound = " + maxRound + ", maxDepth = " + maxDepth;
        }
        return allocationString;
    }

    /**
     * @param allocationString the allocation string to set
     */
    public void setAllocationString(String allocationString) {
        this.allocationString = allocationString;
    }

    public boolean isCustomizedProperty() {
        customizedProperty = (!alias.isEmpty()
            || !defaultLabel.isEmpty()
            || !defaultShortLabel.isEmpty()
            || !defaultDescription.isEmpty()
            || !defaultShortDescription.isEmpty()
            || !defaultTooltip.isEmpty()
            || !this.getDefaultValueString().isEmpty());
        return customizedProperty;
    }

}
