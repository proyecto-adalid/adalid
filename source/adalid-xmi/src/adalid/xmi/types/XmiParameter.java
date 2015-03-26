/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.types;

import adalid.commons.util.StrUtils;
import adalid.xmi.util.Typenames;
import adalid.xmi.util.UmlTags;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author cmedina
 */
public class XmiParameter {

    private String parameterType;

    private boolean entityParameter = false;

    private String packageName = "";

    private boolean ignored = false;

    private String name;

    private String defaultValue = "";

    private String alias = "";

    private String defaultLabel = "";

    private String defaultShortLabel = "";

    private String defaultDescription = "";

    private String defaultShortDescription = "";

    private String defaultCollectionLabel = "";

    private String defaultTooltip = "";

    private boolean nullable = false;

    private boolean auditable = false;

    private String referenceSearchType = "UNSPECIFIED";

    private String initialValue = "";

    private int precision;

    private int scale;

    private int minLength;

    private int maxLength;

    private boolean fileReference = false;

    private boolean bigDecimalField = false;

    private boolean stringField = false;

    private boolean timeField = false;

    private boolean timestampField = false;

    private String initialValueString = "";

    private String defaultValueString = "";

    private String minLengthString = "";

    private String maxLengthString = "";

    private String precisionString = "";

    private String scaleString = "";

    private String parameterFieldString = "";

    private String bigDecimalFieldString = "";

    private String stringFieldString = "";

    private String timestampFieldString = "";

    private String timeFieldString = "";

    private String entityReferenceSearchString = "";

    private boolean customizedParameter = false;

    public XmiParameter(String parameterType) {
        this.parameterType = parameterType;
        this.entityParameter = false;
    }

    public XmiParameter(String parameterType, String parameterName) {
        this.parameterType = parameterType;
        this.name = parameterName;
    }

    public XmiParameter(XmiPersistentEntity entity) {
        this.parameterType = entity.getName();
        this.entityParameter = true;
        this.packageName = entity.getPackageName();
    }

    /**
     * @return the parameterType
     */
    public String getParameterType() {
        if (!this.entityParameter) {
            return parameterType;
        } else {
            if (!packageName.isEmpty()) {
                return packageName + "." + parameterType;
            } else {
                return parameterType;
            }
        }
    }

    /**
     * @param parameterType the parameterType to set
     */
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * @return the entityParameter
     */
    public boolean isEntityParameter() {
        return entityParameter;
    }

    /**
     * @param entityParameter the entityParameter to set
     */
    public void setEntityParameter(boolean entityParameter) {
        this.entityParameter = entityParameter;
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
     * @return the parameterName
     */
    public String getName() {
        return name;
    }

    /**
     * @param parameterName the parameterName to set
     */
    public void setName(String parameterName) {
        this.name = parameterName;
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
     * @return the required
     */
    public Boolean getNullable() {
        return isNullable();
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(Boolean nullable) {
        this.setNullable((boolean) nullable);
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
    public void setAuditable(boolean auditable) {
        this.auditable = auditable;
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
     * @return the referenceSearchType
     */
    public String getReferenceSearchType() {
        return referenceSearchType;
    }

    /**
     * @param referenceSearchType the referenceSearchType to set
     */
    public void setReferenceSearchType(String referenceSearchType) {
        this.referenceSearchType = referenceSearchType;
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
     * @return the fileReference
     */
    public boolean isFileReference() {
        return fileReference;
    }

    /**
     * @param fileReference the fileReference to set
     */
    public void setFileReference(boolean fileReference) {
        this.fileReference = fileReference;
    }

    /**
     * @return the bigDecimalField
     */
    public boolean isBigDecimalField() {
        bigDecimalField = this.getParameterType().equals(Typenames.BigDecimalParameter);
        return bigDecimalField;
    }

    /**
     * @param bigDecimalField the bigDecimalField to set
     */
    public void setBigDecimalField(boolean bigDecimalField) {
        this.bigDecimalField = bigDecimalField;
    }

    /**
     * @return the stringField
     */
    public boolean isStringField() {
        stringField = this.getParameterType().equals(Typenames.StringParameter);
        return stringField;
    }

    /**
     * @param stringField the stringField to set
     */
    public void setStringField(boolean stringField) {
        this.stringField = stringField;
    }

    /**
     * @return the timeField
     */
    public boolean isTimeField() {
        return timeField;
    }

    /**
     * @param timeField the timeField to set
     */
    public void setTimeField(boolean timeField) {
        timeField = this.getParameterType().equals(Typenames.TimeParameter);
        this.timeField = timeField;
    }

    /**
     * @return the timestampField
     */
    public boolean isTimestampField() {
        timestampField = this.getParameterType().equals(Typenames.TimestampParameter);
        return timestampField;
    }

    /**
     * @param timestampField the timestampField to set
     */
    public void setTimestampField(boolean timestampField) {
        this.timestampField = timestampField;
    }

    public boolean isValidReferenceSearchType() {
        return (referenceSearchType.equals(UmlTags.LIST)
            || referenceSearchType.equals(UmlTags.PAGE)
            || referenceSearchType.equals(UmlTags.NONE));
    }

    public String getInitialValueString() {
        if (StrUtils.getObjeto(this.getInitialValue(), String.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.StringParameter)) {
            initialValueString = "\"" + this.getInitialValue() + "\"";
        } else if ((StrUtils.getObjeto(this.getInitialValue(), Integer.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.IntegerParameter))
            || (StrUtils.getObjeto(this.getInitialValue(), Double.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.DoubleParameter))
            || (StrUtils.getObjeto(this.getInitialValue(), Boolean.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.BooleanParameter))) {
            initialValueString = this.getInitialValue();
        }
        return initialValueString;
    }

    public String getDefaultValueString() {
        if (StrUtils.getObjeto(this.getDefaultValue(), String.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.StringParameter)) {
            defaultValueString = "\"" + this.getDefaultValue() + "\"";
        } else if ((StrUtils.getObjeto(this.getDefaultValue(), Integer.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.IntegerParameter))
            || (StrUtils.getObjeto(this.getDefaultValue(), Double.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.DoubleParameter))
            || (StrUtils.getObjeto(this.getDefaultValue(), Boolean.class) != null && this.getParameterType().equalsIgnoreCase(Typenames.BooleanParameter))) {
            defaultValueString = this.getDefaultValue();
        }
        return defaultValueString;
    }

    public String getMinLengthString() {
        if (this.getParameterType().equals(Typenames.StringParameter) && this.getMinLength() != 0) {
            minLengthString = String.valueOf(this.getMinLength());
        }
        //System.out.println("minLength="+minLengthString+".");
        return minLengthString;
    }

    public String getMaxLengthString() {
        if (this.getParameterType().equals(Typenames.StringParameter) && this.getMaxLength() != 0) {
            maxLengthString = String.valueOf(this.getMaxLength());
        }
        //System.out.println("maxLength="+maxLengthString+".");
        return maxLengthString;
    }

    public String getPrecisionString() {
        if (this.getParameterType().equals(Typenames.BigDecimalParameter)
            || this.getParameterType().equals(Typenames.TimestampParameter)
            || this.getParameterType().equals(Typenames.TimeParameter)
            && this.getPrecision() != 0) {
            precisionString = String.valueOf(this.getPrecision());
        }
        //System.out.println("precision="+precisionString+".");
        return precisionString;
    }

    public String getScaleString() {
        if (this.getParameterType().equals(Typenames.BigDecimalParameter)
            && this.getScale() != 0) {
            scaleString = String.valueOf(this.getScale());
        }
        //System.out.println("scale="+scaleString+".");
        return scaleString;
    }

    //TODO: linkedField y Operator
    public String getParameterFieldString() {
        parameterFieldString = "";
        if (!nullable) {
            parameterFieldString += ", required = Kleenean.TRUE";
        }
        //TODO: habilitar el siguiente if despues de corregir el modelo (todos los parámetors están no auditables)
//      if (!auditable) {
//          parameterFieldString += ", auditable = Kleenean.FALSE";
//      }
        return StringUtils.removeStart(parameterFieldString, ", ");
    }

    public boolean isCustomizedParameter() {
        customizedParameter = (!alias.isEmpty()
            || !defaultLabel.isEmpty()
            || !defaultShortLabel.isEmpty()
            || !defaultDescription.isEmpty()
            || !defaultShortDescription.isEmpty()
            || !defaultTooltip.isEmpty()
            || !this.getInitialValueString().isEmpty()
            || !this.getDefaultValueString().isEmpty());
        return customizedParameter;
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

}
