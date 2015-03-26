/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.types.XmiParameter;
import adalid.xmi.util.UmlTags;
import adalid.xmi.util.XmiTags;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

/**
 *
 * @author cmedina
 */
/**
 *
 * @author cmedina
 */
public class XmiParameterElement extends XmiElement {

    private static Logger logger = Logger.getLogger(XmiParameterElement.class);

    private String defaultValue;

    public XmiParameterElement(Element _element, XmiDomTree _xmiDomTree) {
        super(_element, _xmiDomTree);
        //fillTaggedValuesMap();
    }

    public XmiParameterElement(XmiElement _xmiElement, XmiDomTree _xmiDomTree) {
        super(_xmiElement, _xmiDomTree);
        //fillTaggedValuesMap();
    }

    public String getTypeModifier() {
        String typeModifier = "";
        if (element.getElementsByTagName(XmiTags.TYPE_MODIFIER_TAG).getLength() == 1) {
            Element newElement = (Element) element.getElementsByTagName(XmiTags.TYPE_MODIFIER_TAG).item(0);
            typeModifier = newElement.getAttribute(XmiTags.XMI_VALUE_TAG);
        }
        return typeModifier;
    }

    public String getDefaultValue() {
        if (element.getElementsByTagName(XmiTags.INITIAL_VALUE_TAG).getLength() == 1) {
            Element newElement = (Element) element.getElementsByTagName(XmiTags.INITIAL_VALUE_TAG).item(0);
            defaultValue = newElement.getAttribute(XmiTags.VALUE_TAG);
        }
        return defaultValue;
    }

    public XmiParameter getParameterFromElement() {

        XmiParameter parameter = null;
        String parameterType, dataTypeName;
        if (!getTypeModifier().isEmpty()) {
            return null;
        }
        if (xmiDomTree.dataTypeIdMap.get(this.getType()) != null
            && !this.getNameTag().isEmpty()) {
            dataTypeName = xmiDomTree.dataTypeIdMap.get(this.getType());
            parameterType = XmiDomTree.properties.getProperty(dataTypeName);
            if (parameterType != null) {
                parameter = new XmiParameter(parameterType + XmiTags.PARAMETER);
            } else {
                logger.error("Unknown data type : " + dataTypeName);
            }
        } else if (xmiDomTree.entityIdMap.get(this.getType()) != null) {
            parameter = new XmiParameter(xmiDomTree.entityIdMap.get(this.getType()));
        } else {
            logger.error("Unknown type for parameter: " + this.getNameTag());
        }
        if (parameter != null) {
            parameter.setName(this.getNameTag());
            parameter.setDefaultValue(this.getDefaultValue());
            setTaggedValues(parameter);
        }
        return parameter;
    }

    private void setTaggedValues(XmiParameter parameter) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            if (taggedValues.get(UmlTags.alias) != null) {
                parameter.setAlias(taggedValues.get(UmlTags.alias));
            }
            if (taggedValues.get(UmlTags.defaultLabel) != null) {
                parameter.setDefaultLabel(taggedValues.get(UmlTags.defaultLabel));
            }
            if (taggedValues.get(UmlTags.defaultShortLabel) != null) {
                parameter.setDefaultShortLabel(taggedValues.get(UmlTags.defaultShortLabel));
            }
            if (taggedValues.get(UmlTags.defaultDescription) != null) {
                parameter.setDefaultDescription(StringUtils.replace(taggedValues.get(UmlTags.defaultDescription), "\n", "\\n"));
            }
            if (taggedValues.get(UmlTags.defaultShortDescription) != null) {
                parameter.setDefaultShortDescription(taggedValues.get(UmlTags.defaultShortDescription));
            }
            if (taggedValues.get(UmlTags.defaultCollectionLabel) != null) {
                parameter.setDefaultCollectionLabel(taggedValues.get(UmlTags.defaultCollectionLabel));
            }
            if (taggedValues.get(UmlTags.defaultTooltip) != null) {
                parameter.setDefaultTooltip(taggedValues.get(UmlTags.defaultTooltip));
            }
            if (taggedValues.get(UmlTags.initialValue) != null) {
                parameter.setInitialValue(taggedValues.get(UmlTags.initialValue));
            }
            if (taggedValues.get(UmlTags.initialValue) != null) {
                parameter.setInitialValue(taggedValues.get(UmlTags.initialValue));
            }
            if (taggedValues.get(UmlTags.nullable) != null) {
                parameter.setNullable(Boolean.parseBoolean(taggedValues.get(UmlTags.nullable)));
            }
            if (taggedValues.get(UmlTags.auditable) != null) {
                parameter.setAuditable(Boolean.parseBoolean(taggedValues.get(UmlTags.auditable)));
            }
            if (taggedValues.get(UmlTags.precision) != null) {
                parameter.setPrecision(Integer.parseInt(taggedValues.get(UmlTags.precision)));
            }
            if (taggedValues.get(UmlTags.scale) != null) {
                parameter.setScale(Integer.parseInt(taggedValues.get(UmlTags.scale)));
            }
            if (taggedValues.get(UmlTags.minLength) != null) {
                parameter.setMinLength(Integer.parseInt(taggedValues.get(UmlTags.minLength)));
            }
            if (taggedValues.get(UmlTags.maxLength) != null) {
                parameter.setMaxLength(Integer.parseInt(taggedValues.get(UmlTags.maxLength)));
            }
            if (taggedValues.get(UmlTags.fileReference) != null) {
                parameter.setFileReference(Boolean.parseBoolean(taggedValues.get(UmlTags.fileReference)));
            }
        } catch (NullPointerException e) {
            logger.error("Undefined Tagged Value for Entity");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            logger.error("Invalid format for tag ");
            e.printStackTrace();
        }
    }

}
