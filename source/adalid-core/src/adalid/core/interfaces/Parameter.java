/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.StandardRelationalOp;
import java.lang.reflect.Field;
import java.util.List;

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
     * @return the submitField indicator
     */
    boolean isSubmitField();

    /**
     * @return the sequence number
     */
    int getSequenceNumber();

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

}
