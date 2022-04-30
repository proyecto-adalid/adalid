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

import adalid.core.enums.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public interface Parameter extends DataArtifact, ValuedArtifact {

    /**
     * @return the auditable
     */
    boolean isAuditable();

    /**
     * @return the password
     */
    boolean isPassword();

    /**
     * @return the nullable
     */
    boolean isRequiredParameter();

    /**
     * @return the hidden field indicator
     */
    boolean isHiddenField();

    /**
     * @return the sequence number
     */
    int getSequenceNumber();

    /**
     * @return the rendering filter's read-only indicator
     */
    boolean isRenderingFilterReadOnly();

    /**
     * @return the rendering filter
     */
    BooleanExpression getRenderingFilter();

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de parámetros en vistas (páginas) de ejecución de operaciones
     * de negocio. Solo si se cumplen los criterios del filtro, el valor de la propiedad o el parámetro será visible en las vistas (páginas) de
     * registro o ejecución de operaciones de negocio.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro
     */
    void setRenderingFilter(BooleanExpression renderingFilter);

    /**
     * @return the requiring filter
     */
    BooleanExpression getRequiringFilter();

    /**
     * El método setRequiringFilter se utiliza para establecer el filtro de obligatoriedad de parámetros en vistas (páginas) de ejecución de
     * operaciones de negocio. Solo si se cumplen los criterios del filtro, el valor de la propiedad o el parámetro será obligatoriamente requerido en
     * las vistas (páginas) de registro o ejecución de operaciones de negocio.
     *
     * @param requiringFilter expresión booleana que se utiliza como filtro
     */
    void setRequiringFilter(BooleanExpression requiringFilter);

    /**
     * @return the modifying filter
     */
    BooleanExpression getModifyingFilter();

    /**
     * El método setModifyingFilter se utiliza para establecer el filtro de anulación de parámetros en vistas (páginas) de ejecución de operaciones de
     * negocio. Solo si se cumplen los criterios del filtro, el valor de la propiedad o el parámetro será anulado en las vistas (páginas) de registro
     * o ejecución de operaciones de negocio.
     *
     * @param modifyingFilter expresión booleana que se utiliza como filtro
     */
    void setModifyingFilter(BooleanExpression modifyingFilter);

    /**
     * @return the nullifying filter
     */
    BooleanExpression getNullifyingFilter();

    /**
     * El método setNullifyingFilter se utiliza para establecer el filtro de anulación de parámetros en vistas (páginas) de ejecución de operaciones
     * de negocio. Solo si se cumplen los criterios del filtro, el valor de la propiedad o el parámetro será anulado en las vistas (páginas) de
     * registro o ejecución de operaciones de negocio.
     *
     * @param nullifyingFilter expresión booleana que se utiliza como filtro
     */
    void setNullifyingFilter(BooleanExpression nullifyingFilter);

    /**
     * @return the instance reference indicator
     */
    boolean isInstanceReferenceField();

    /**
     * @return the file reference indicator
     */
    boolean isFileReferenceField();

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
     * @return the linked field name
     */
    String getLinkedFieldName();

    /**
     * @return the linked field
     */
    Field getLinkedField();

    /**
     * @return the linked property
     */
    Property getLinkedProperty();

    /**
     * @return the linked column name
     */
    String getLinkedColumnName();

    /**
     * @return the linked column operator
     */
    StandardRelationalOp getLinkedColumnOperator();

    /**
     * @return the parameter path list
     */
    List<Artifact> getParameterPathList();

    /**
     * @return the initial value referencing parameters list
     */
    List<Parameter> getInitialValueReferencingParametersList();

    /**
     * @return the list of artifacts referencing this artifact in their initial value
     */
    Map<String, Parameter> getInitialValueReferencingParameters();

    /**
     * @param recursively recursively
     * @return the list of artifacts referencing this artifact in their initial value
     */
    Map<String, Parameter> getInitialValueReferencingParameters(boolean recursively);

    /**
     * @return the list of parameters referencing this parameter in their max value
     */
    Map<String, Parameter> getMaxValueReferencingParameters();

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their max value
     */
    Map<String, Parameter> getMaxValueReferencingParameters(boolean recursively);

    /**
     * @return the list of parameters referencing this parameter in their min value
     */
    Map<String, Parameter> getMinValueReferencingParameters();

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their min value
     */
    Map<String, Parameter> getMinValueReferencingParameters(boolean recursively);

    /**
     * @return the list of parameters referencing this parameter in their modifying filter
     */
    Map<String, Parameter> getModifyingFilterReferencingParameters();

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their modifying filter
     */
    Map<String, Parameter> getModifyingFilterReferencingParameters(boolean recursively);

    /**
     * @return the list of parameters referencing this parameter in their rendering filter
     */
    Map<String, Parameter> getRenderingFilterReferencingParameters();

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their rendering filter
     */
    Map<String, Parameter> getRenderingFilterReferencingParameters(boolean recursively);

    /**
     * @param recursively recursively
     * @param readOnly true, to process read-only filters; false, to process read-write filters; null, to process all filters
     * @return the list of parameters referencing this parameter in their rendering filter
     */
    Map<String, Parameter> getRenderingFilterReferencingParameters(boolean recursively, Boolean readOnly);

    /**
     * @return the list of parameters referencing this parameter in their requiring filter
     */
    Map<String, Parameter> getRequiringFilterReferencingParameters();

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their requiring filter
     */
    Map<String, Parameter> getRequiringFilterReferencingParameters(boolean recursively);

    /**
     * @return the list of parameters referencing this parameter in their search query filter
     */
    Map<String, Parameter> getSearchQueryFilterReferencingParameters();

    /**
     * @param recursively recursively
     * @return the list of parameters referencing this parameter in their search query filter
     */
    Map<String, Parameter> getSearchQueryFilterReferencingParameters(boolean recursively);

}
