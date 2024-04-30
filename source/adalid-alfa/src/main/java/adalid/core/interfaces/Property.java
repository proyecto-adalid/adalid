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
import adalid.core.enums.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public interface Property extends DataArtifact, ValuedArtifact {

    /**
     * @return the corresponding property at the declaring entity's root instance
     */
    Property getPropertyAtRoot();

    /**
     * @return the base field indicator
     */
    boolean isBaseField();

    /**
     * @return the key field indicator
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
     * @return the hidden entity reference field indicator
     */
    boolean isHiddenEntityReferenceField();

    /**
     * @return the GUI-creatable field indicator
     */
    boolean isCreateField();

    /**
     * @return the API-creatable field indicator
     */
    boolean isCreateFieldViaAPI();

    /**
     * @return the GUI-updatable field indicator
     */
    boolean isUpdateField();

    /**
     * @return the API-updatable field indicator
     */
    boolean isUpdateFieldViaAPI();

    /**
     * @return the search field indicator
     */
    boolean isSearchField();

    /**
     * @return the filter field indicator
     */
    boolean isFilterField();

    /**
     * @return the sort field indicator
     */
    boolean isSortField();

    /**
     * @return the table field indicator
     */
    boolean isTableField();

    /**
     * @return the detail field indicator
     */
    boolean isDetailField();

    /**
     * @return the column field indicator
     */
    boolean isColumnField();

    /**
     * @return the report field indicator
     */
    boolean isReportField();

    /**
     * @return the export field indicator
     */
    boolean isExportField();

    /**
     * @return the headertextless field indicator
     */
    boolean isHeadertextlessField();

    /**
     * @return the heading field indicator
     */
    boolean isHeadingField();

    /**
     * @return the overlay field indicator
     */
    boolean isOverlayField();

    /**
     * @return the immutable field indicator
     */
    boolean isImmutableField();

    /**
     * @return the serializable field indicator
     */
    boolean isSerializableField();

    /**
     * @return the transient field indicator
     */
    boolean isTransientField();

    /**
     * @return the serialize IUID indicator
     */
    boolean isSerializableIUID();

    /**
     * @return the sequence data generation disabled indicator
     */
    boolean isSequencePropertyDataGenDisabled();

    /**
     * @return the default condition
     */
    DefaultCondition getDefaultCondition();

    /**
     * @return the default checkpoint
     */
    Checkpoint getDefaultCheckpoint();

    /**
     * @return the default function
     */
    String getDefaultFunction();

    /**
     * @return the anchor field name
     */
    String getAnchorFieldName();

    /**
     * @return the anchor field
     */
    Field getAnchorField();

    /**
     * @return the anchor property
     */
    Property getAnchorProperty();

    /**
     * @return the anchor type
     */
    AnchorType getAnchorType();

    /**
     * @return the anchor type of the first anchored field
     */
    AnchorType getFirstAnchoredFieldAnchorType();

    /**
     * Sets the first anchored field anchor type
     *
     * @param anchorType the anchor type of the first anchored field
     */
    void setFirstAnchoredFieldAnchorType(AnchorType anchorType);

    /**
     * @return the anchoring linked detail fields indicator
     */
    boolean isAnchoringLinkedDetailFields();

    /**
     * Sets the anchoring linked detail fields indicator
     *
     * @param b the anchoring detail fields indicator to set
     */
    void setAnchoringLinkedDetailFields(boolean b);

    /**
     * @return the default anchor label
     */
    String getDefaultAnchorLabel();

    /**
     * El método setDefaultAnchorLabel se utiliza para establecer la etiqueta del grupo de propiedades ancladas a la propiedad que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultAnchorLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del grupo de propiedades ancladas
     */
    void setDefaultAnchorLabel(String defaultAnchorLabel);

    /**
     * @param locale locale
     * @return the localized anchor label
     */
    String getLocalizedAnchorLabel(Locale locale);

    /**
     * El método setLocalizedAnchorLabel se utiliza para establecer la etiqueta del grupo de propiedades ancladas a la propiedad que se almacena en el
     * archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedAnchorLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del grupo de propiedades ancladas
     */
    void setLocalizedAnchorLabel(Locale locale, String localizedAnchorLabel);

    /**
     * @return the default anchoring label
     */
    String getDefaultAnchoredLabel();

    /**
     * El método setDefaultAnchoredLabel se utiliza para establecer la etiqueta de la propiedad dentro del grupo de propiedades ancladas que se
     * almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultAnchoredLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta dentro del grupo de propiedades
     * ancladas
     */
    void setDefaultAnchoredLabel(String defaultAnchoredLabel);

    /**
     * @param locale locale
     * @return the localized anchoring label
     */
    String getLocalizedAnchoredLabel(Locale locale);

    /**
     * El método setLocalizedAnchoredLabel se utiliza para establecer la etiqueta de la propiedad dentro del grupo de propiedades ancladas que se
     * almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario
     * no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedAnchoredLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta dentro del grupo de propiedades
     * ancladas
     */
    void setLocalizedAnchoredLabel(Locale locale, String localizedAnchoredLabel);

    /**
     * @return the sequence number
     */
    int getSequenceNumber();

    /**
     * @return the display sort key
     */
    String getDisplaySortKey();

    /**
     * Sets the display sort key
     *
     * @param key the display sort key to set
     */
    void setDisplaySortKey(String key);

    /**
     * @return the aggregate function
     */
    AggregateFunction getAggregateFunction();

    /**
     * @return the aggregate title
     */
    String getAggregateTitle();

    /**
     * @return the calculable indicator
     */
    boolean isCalculable();

    /**
     * @return true if it is a calculated artifact; otherwise false
     */
    boolean isCalculatedProperty();

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
     * @return the mandatory-for-insert indicator
     */
    boolean isMandatoryForInsert();

    /**
     * @return the optional-for-insert indicator
     */
    boolean isOptionalForInsert();

    /**
     * @return the unique indicator
     */
    boolean isUnique();

    /**
     * @return the indexed indicator
     */
    boolean isIndexed();

    /**
     * @return the rendering filter's read-only indicator
     */
    boolean isRenderingFilterReadOnly();

    /**
     * @return the rendering filter
     */
    BooleanExpression getRenderingFilter();

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de propiedades en vistas (páginas) de consulta y/o registro.
     * Solo si se cumplen los criterios del filtro, el valor de la propiedad o el parámetro será visible en las vistas (páginas) de registro o
     * ejecución de operaciones de negocio.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro
     */
    void setRenderingFilter(BooleanExpression renderingFilter);

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de propiedades en vistas (páginas) de consulta y/o registro.
     * Solo si se cumplen los criterios del filtro, el valor de la propiedad o el parámetro será visible en las vistas (páginas) de registro o
     * ejecución de operaciones de negocio.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro
     * @param readOnly true, si el filtro solo aplica para la lectura; de lo contrario false.
     */
    void setRenderingFilter(BooleanExpression renderingFilter, boolean readOnly);

    /**
     * @return the requiring filter
     */
    BooleanExpression getRequiringFilter();

    /**
     * El método setRequiringFilter se utiliza para establecer el filtro de obligatoriedad de propiedades en vistas (páginas) de registro. Solo si se
     * cumplen los criterios del filtro, el valor de la propiedad o el parámetro será obligatoriamente requerido en las vistas (páginas) de registro o
     * ejecución de operaciones de negocio.
     *
     * @param requiringFilter expresión booleana que se utiliza como filtro
     */
    void setRequiringFilter(BooleanExpression requiringFilter);

    /**
     * @return the modifying filter
     */
    BooleanExpression getModifyingFilter();

    /**
     * El método setModifyingFilter se utiliza para establecer el filtro de modificación de propiedades en vistas (páginas) de registro. Solo si se
     * cumplen los criterios del filtro, el valor de la propiedad o el parámetro podrá ser modificado en las vistas (páginas) de registro o ejecución
     * de operaciones de negocio.
     *
     * @param modifyingFilter expresión booleana que se utiliza como filtro
     */
    void setModifyingFilter(BooleanExpression modifyingFilter);

    /**
     * @return the nullifying filter
     */
    BooleanExpression getNullifyingFilter();

    /**
     * El método setNullifyingFilter se utiliza para establecer el filtro de anulación de propiedades en vistas (páginas) de registro. Solo si se
     * cumplen los criterios del filtro, el valor de la propiedad o el parámetro será anulado en las vistas (páginas) de registro o ejecución de
     * operaciones de negocio.
     *
     * @param nullifyingFilter expresión booleana que se utiliza como filtro
     */
    void setNullifyingFilter(BooleanExpression nullifyingFilter);

    /**
     * El método setNullValueGraphicImageExpression se utiliza para establecer la expresión que determina el nombre de la imagen de valores nulos
     * asociada a la propiedad. El nombre de la imagen debe ser establecido previamente mediante el método setNullValueGraphicImageName. La imagen de
     * la propiedad se utiliza para resaltar sua valores nulos en las vistas (páginas) de consulta y/o registro.
     */
    void setNullValueGraphicImageExpression();

    /**
     * @return the enclosing steps list
     */
    List<Step> getEnclosingSteps();

    /**
     * @return the enclosing tabs list
     */
    List<Tab> getEnclosingTabs();

    /**
     * @return the embedded document indicator
     */
    boolean isEmbeddedDocumentField();

    /**
     * @return the file reference indicator
     */
    boolean isFileReferenceField();

    /**
     * @return the graphic image indicator
     */
    boolean isGraphicImageField();

    /**
     * @return the master sequence indicator
     */
    boolean isMasterSequenceField();

    /**
     * @return the URL indicator
     */
    boolean isUniformResourceLocatorField();

    /**
     * @return the variant string indicator
     */
    boolean isVariantStringField();

    /**
     * @return true if it is the Primary Key property; otherwise false
     */
    boolean isPrimaryKeyProperty();

    /**
     * @return true if it is the Sequence property; otherwise false
     */
    boolean isSequenceProperty();

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
     * @return true if it is the Image property; otherwise false
     */
    boolean isImageProperty();

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
     * @return true if it is the User property; otherwise false
     */
    boolean isUserProperty();

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
     * @return true if it is the State property; otherwise false
     */
    boolean isStateProperty();

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
     * @return true if it is a boolean enumeration entity; otherwise false
     */
    boolean isBooleanEnumerationEntity();

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
     * @return true if is a overlayable entity reference; otherwise false
     */
    boolean isOverlayableEntityReference();

    /**
     * @return the graphic image name expression of a property
     */
    CharacterExpression getGraphicImageNameExpression();

    /**
     * @return the property path list
     */
    List<Artifact> getPropertyPathList();

    /**
     * @return the initial value referencing properties list
     */
    List<Property> getInitialValueReferencingPropertiesList();

    /**
     * @return the property-parameter path list
     */
    List<Artifact> getPropertyParameterPathList();

    /**
     * @return the list of properties referencing this property in their initial value
     */
    Map<String, Property> getInitialValueReferencingProperties();

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their initial value
     */
    Map<String, Property> getInitialValueReferencingProperties(boolean recursively);

    /**
     * @return the list of properties referencing this property in their max value
     */
    Map<String, Property> getMaxValueReferencingProperties();

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their max value
     */
    Map<String, Property> getMaxValueReferencingProperties(boolean recursively);

    /**
     * @return the list of properties referencing this property in their min value
     */
    Map<String, Property> getMinValueReferencingProperties();

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their min value
     */
    Map<String, Property> getMinValueReferencingProperties(boolean recursively);

    /**
     * @return the list of properties referencing this property in their modifying filter
     */
    Map<String, Property> getModifyingFilterReferencingProperties();

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their modifying filter
     */
    Map<String, Property> getModifyingFilterReferencingProperties(boolean recursively);

    /**
     * @return the list of properties referencing this property in their rendering filter
     */
    Map<String, Property> getRenderingFilterReferencingProperties();

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their rendering filter
     */
    Map<String, Property> getRenderingFilterReferencingProperties(boolean recursively);

    /**
     * @param recursively recursively
     * @param readOnly true, to process read-only filters; false, to process read-write filters; null, to process all filters
     * @return the list of properties referencing this property in their rendering filter
     */
    Map<String, Property> getRenderingFilterReferencingProperties(boolean recursively, Boolean readOnly);

    /**
     * @return the list of properties referencing this property in their requiring filter
     */
    Map<String, Property> getRequiringFilterReferencingProperties();

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their requiring filter
     */
    Map<String, Property> getRequiringFilterReferencingProperties(boolean recursively);

    /**
     * @return the list of properties referencing this property in their search query filter
     */
    Map<String, Property> getSearchQueryFilterReferencingProperties();

    /**
     * @param recursively recursively
     * @return the list of properties referencing this property in their search query filter
     */
    Map<String, Property> getSearchQueryFilterReferencingProperties(boolean recursively);

    /**
     * @return the table column's entity name
     */
    String getTableColumnEntityName();

    /**
     * @return the property size in pixels
     */
    int getPixels();

    /**
     * @return the column size in pixels
     */
    int getColumnPixels();

}
