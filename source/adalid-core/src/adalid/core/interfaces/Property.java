/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.Tab;
import adalid.core.enums.DefaultCondition;
import adalid.core.enums.PropertyAccess;
import java.util.List;

/**
 * @author Jorge Campins
 */
public interface Property extends DataArtifact, ValuedArtifact {

    /**
     * @return the baseField indicator
     */
    boolean isBaseField();

    /**
     * @return the keyField indicator
     */
    boolean isKeyField();

    /**
     * @return the property access
     */
    PropertyAccess getPropertyAccess();

    /**
     * @return the auditable indicator
     */
    boolean isAuditable();

    /**
     * @return the password indicator
     */
    boolean isPassword();

    /**
     * @return the required indicator
     */
    boolean isReadOnly();

    /**
     * @return the required indicator
     */
    boolean isRequiredProperty();

    /**
     * @return the hidden field indicator
     */
    boolean isHiddenField();

    /**
     * @return the create field indicator
     */
    boolean isCreateField();

    /**
     * @return the update field indicator
     */
    boolean isUpdateField();

    /**
     * @return the searchField indicator
     */
    boolean isSearchField();

    /**
     * @return the filterField indicator
     */
    boolean isFilterField();

    /**
     * @return the tableField indicator
     */
    boolean isTableField();

    /**
     * @return the detailField indicator
     */
    boolean isDetailField();

    /**
     * @return the reportField indicator
     */
    boolean isReportField();

    /**
     * @return the exportField indicator
     */
    boolean isExportField();

    /**
     * @return the submitField indicator
     */
    boolean isSubmitField();

    /**
     * @return the headertextlessField indicator
     */
    boolean isHeadertextlessField();

    /**
     * @return the headingField indicator
     */
    boolean isHeadingField();

    /**
     * @return the default condition
     */
    DefaultCondition getDefaultCondition();

    /**
     * @return the sequence number
     */
    int getSequenceNumber();

    /**
     * @return the calculable indicator
     */
    boolean isCalculable();

    /**
     * @return the nullable indicator
     */
    boolean isNullable();

    /**
     * @return the insertable indicator
     */
    boolean isInsertable();

    /**
     * @return the updateable indicator
     */
    boolean isUpdateable();

    /**
     * @return the unique indicator
     */
    boolean isUnique();

    /**
     * @return the rendering filter
     */
    BooleanExpression getRenderingFilter();

    /**
     * @param renderingFilter the rendering filter to set
     */
    void setRenderingFilter(BooleanExpression renderingFilter);

    /**
     * @return the requiring filter
     */
    BooleanExpression getRequiringFilter();

    /**
     * @param requiringFilter the requiring filter to set
     */
    void setRequiringFilter(BooleanExpression requiringFilter);

    /**
     * @return the modifying filter
     */
    BooleanExpression getModifyingFilter();

    /**
     * @param modifyingFilter the modifying filter to set
     */
    void setModifyingFilter(BooleanExpression modifyingFilter);

    /**
     * @return the nullifying filter
     */
    BooleanExpression getNullifyingFilter();

    /**
     * @param nullifyingFilter the nullifying filter to set
     */
    void setNullifyingFilter(BooleanExpression nullifyingFilter);

    /**
     * @return the enclosing tabs list
     */
    List<Tab> getEnclosingTabs();

    /**
     * @return the file reference indicator
     */
    boolean isFileReferenceField();

    /**
     * @return true if it is the Primary Key property; otherwise false
     */
    boolean isPrimaryKeyProperty();

    /**
     * @return true if it is the Version property; otherwise false
     */
    boolean isVersionProperty();

    /**
     * @return true if it is the Numeric Key property; otherwise false
     */
    boolean isNumericKeyProperty();

    /**
     * @return true if it is the Character Key property; otherwise false
     */
    boolean isCharacterKeyProperty();

    /**
     * @return true if it is the Name property; otherwise false
     */
    boolean isNameProperty();

    /**
     * @return true if it is the Description property; otherwise false
     */
    boolean isDescriptionProperty();

    /**
     * @return true if it is the Inactive Indicator property; otherwise false
     */
    boolean isInactiveIndicatorProperty();

    /**
     * @return true if it is the URL property; otherwise false
     */
    boolean isUrlProperty();

    /**
     * @return true if it is the Parent property; otherwise false
     */
    boolean isParentProperty();

    /**
     * @return true if it is the Owner property; otherwise false
     */
    boolean isOwnerProperty();

    /**
     * @return true if it is the segment property; otherwise false
     */
    boolean isSegmentProperty();

    /**
     * @return true if it is a Unique Key property; otherwise false
     */
    boolean isUniqueKeyProperty();

    /**
     * @return true if it is the Business Key property; otherwise false
     */
    boolean isBusinessKeyProperty();

    /**
     * @return true if it is the Discriminator property; otherwise false
     */
    boolean isDiscriminatorProperty();

    /**
     * @return true if it is a Primitive; otherwise false
     */
    boolean isPrimitive();

    /**
     * @return true if it is a BinaryPrimitive; otherwise false
     */
    boolean isBinaryPrimitive();

    /**
     * @return true if it is a BooleanPrimitive; otherwise false
     */
    boolean isBooleanPrimitive();

    /**
     * @return true if it is a CharacterPrimitive; otherwise false
     */
    boolean isCharacterPrimitive();

    /**
     * @return true if it is a NumericPrimitive; otherwise false
     */
    boolean isNumericPrimitive();

    /**
     * @return true if it is a TemporalPrimitive; otherwise false
     */
    boolean isTemporalPrimitive();

    /**
     * @return true if it is a BigDecimalData; otherwise false
     */
    boolean isBigDecimalData();

    /**
     * @return true if it is a BigIntegerData; otherwise false
     */
    boolean isBigIntegerData();

    /**
     * @return true if it is a BinaryData; otherwise false
     */
    boolean isBinaryData();

    /**
     * @return true if it is a BooleanData; otherwise false
     */
    boolean isBooleanData();

    /**
     * @return true if it is a ByteData; otherwise false
     */
    boolean isByteData();

    /**
     * @return true if it is a CharacterData; otherwise false
     */
    boolean isCharacterData();

    /**
     * @return true if it is a DateData; otherwise false
     */
    boolean isDateData();

    /**
     * @return true if it is a DoubleData; otherwise false
     */
    boolean isDoubleData();

    /**
     * @return true if it is a FloatData; otherwise false
     */
    boolean isFloatData();

    /**
     * @return true if it is a IntegerData; otherwise false
     */
    boolean isIntegerData();

    /**
     * @return true if it is a LongData; otherwise false
     */
    boolean isLongData();

    /**
     * @return true if it is a ShortData; otherwise false
     */
    boolean isShortData();

    /**
     * @return true if it is a StringData; otherwise false
     */
    boolean isStringData();

    /**
     * @return true if it is a TimeData; otherwise false
     */
    boolean isTimeData();

    /**
     * @return true if it is a TimestampData; otherwise false
     */
    boolean isTimestampData();

    /**
     * @return true if it is an entity; otherwise false
     */
    boolean isEntity();

    /**
     * @return true if it is a contextual entity; otherwise false
     */
    boolean isContextualEntity();

    /**
     * @return true if it is a enumeration entity; otherwise false
     */
    boolean isEnumerationEntity();

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
     * @return the property path list
     */
    List<Artifact> getPropertyPathList();

    /**
     * @return the property size in pixels
     */
    int getPixels();

}
