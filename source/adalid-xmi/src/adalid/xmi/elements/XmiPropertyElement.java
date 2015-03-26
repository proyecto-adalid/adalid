/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.types.XmiProperty;
import adalid.xmi.util.UmlTags;
import adalid.xmi.util.XmiTags;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author cmedina
 */
public class XmiPropertyElement extends XmiElement {

    private static Logger logger = Logger.getLogger(XmiPropertyElement.class);

    private String initialValue;

    public String getTypeModifier() {
        String typeModifier = "";
        if (element.getElementsByTagName(XmiTags.TYPE_MODIFIER_TAG).getLength() == 1) {
            Element newElement = (Element) element.getElementsByTagName(XmiTags.TYPE_MODIFIER_TAG).item(0);
            typeModifier = newElement.getAttribute(XmiTags.XMI_VALUE_TAG);
        }
        return typeModifier;
    }

    public XmiPropertyElement(Element _element, XmiDomTree _xmiDomTree) {
        super(_element, _xmiDomTree);
        //fillTaggedValuesMap();
    }

    public XmiPropertyElement(XmiElement _xmiElement, XmiDomTree _xmiDomTree) {
        super(_xmiElement, _xmiDomTree);
        //fillTaggedValuesMap();
    }

    public String getInitialValue() {
        if (element.getElementsByTagName(XmiTags.INITIAL_VALUE_TAG).getLength() == 1) {
            Element newElement = (Element) element.getElementsByTagName(XmiTags.INITIAL_VALUE_TAG).item(0);
            initialValue = newElement.getAttribute(XmiTags.VALUE_TAG);
        }
        return initialValue;
    }

    public boolean validatePropertyElement() {
        return (element.getAttribute(XmiTags.XMI_TYPE_TAG).equals(XmiTags.UML_PROPERTY_TAG)
            && element.getAttribute(XmiTags.ASSOCIATION_TAG).isEmpty()
            && validatePropertyStereotype());
    }

    private boolean validatePropertyStereotype() {
        NodeList stereotypeNodeList = element.getElementsByTagName(XmiTags.STEREOTYPE_TAG);
        for (int i = 0; i < stereotypeNodeList.getLength(); i++) {
            Element stereotypeNode = (Element) stereotypeNodeList.item(i);
            if (xmiDomTree.stereotypeIdMap.get(stereotypeNode.getAttribute(XmiTags.XMI_VALUE_TAG)).equalsIgnoreCase(XmiTags.META_PROPERTY_STEREOTYPE)) {
                return true;
            }
        }
        return false;
    }

    public XmiProperty getPropertyFromElement() {
        XmiProperty property = null;
        if (!getTypeModifier().isEmpty()) {
            return null;
        }
        if (!getType().isEmpty()) {
            property = extractDirectProperty();
        } else {
            property = extractNestedProperty();
        }
        if (property != null) {
            property.setName(this.getNameTag());
            property.setInitialValue(this.getInitialValue());
            setTaggedValues(property);
        }
        return property;
    }

    private void setTaggedValues(XmiProperty property) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            //logger.info("tagged Values for "+this.getNameTag());
            if (taggedValues.get(UmlTags.alias) != null) {
                property.setAlias(taggedValues.get(UmlTags.alias));
            }
            //logger.info("taggedValue alias "+property.getAlias());
            if (taggedValues.get(UmlTags.defaultLabel) != null) {
                property.setDefaultLabel(taggedValues.get(UmlTags.defaultLabel));
            }
            //logger.info("taggedValue default label "+property.getDefaultLabel());
            if (taggedValues.get(UmlTags.defaultShortLabel) != null) {
                property.setDefaultShortLabel(taggedValues.get(UmlTags.defaultShortLabel));
            }
            if (taggedValues.get(UmlTags.defaultCollectionLabel) != null) {
                property.setDefaultCollectionLabel(taggedValues.get(UmlTags.defaultCollectionLabel));
            }
            //logger.info("taggedValue default short label"+property.getDefaultShortLabel());
            if (taggedValues.get(UmlTags.defaultDescription) != null) {
                property.setDefaultDescription(StringUtils.replace(taggedValues.get(UmlTags.defaultDescription), "\n", "\\n"));
            }
            //logger.info("taggedValue default short label "+property.getDefaultDescription());
            if (taggedValues.get(UmlTags.defaultShortDescription) != null) {
                property.setDefaultShortDescription(taggedValues.get(UmlTags.defaultShortDescription));
            }
            //logger.info("taggedValue default short description"+property.getDefaultShortDescription());
            if (taggedValues.get(UmlTags.defaultTooltip) != null) {
                property.setDefaultTooltip(taggedValues.get(UmlTags.defaultTooltip));
            }
            //logger.info("taggedValue default Tooltip"+property.getDefaultTooltip());
            if (taggedValues.get(UmlTags.auditable) != null) {
                property.setAuditable(Boolean.parseBoolean(taggedValues.get(UmlTags.auditable)));
            }
            //logger.info("taggedValue auditable "+property.isAuditable());
            if (taggedValues.get(UmlTags.required) != null) {
                property.setRequired(Boolean.parseBoolean(taggedValues.get(UmlTags.required)));
            }
            //logger.info("taggedValue required "+property.isRequired());
            if (taggedValues.get(UmlTags.hidden) != null) {
                property.setHidden(Boolean.parseBoolean(taggedValues.get(UmlTags.hidden)));
            }
            //logger.info("taggedValue hidden "+property.isHidden());
            if (taggedValues.get(UmlTags.create) != null) {
                property.setCreate(Boolean.parseBoolean(taggedValues.get(UmlTags.create)));
            }
            //logger.info("taggedValue create "+property.isCreate());
            if (taggedValues.get(UmlTags.update) != null) {
                property.setUpdate(Boolean.parseBoolean(taggedValues.get(UmlTags.update)));
            }
            //logger.info("taggedValue update "+property.isUpdate());
            if (taggedValues.get(UmlTags.search) != null) {
                property.setSearch(Boolean.parseBoolean(taggedValues.get(UmlTags.search)));
            }
            //logger.info("taggedValue search "+property.isSearch());
            if (taggedValues.get(UmlTags.filter) != null) {
                property.setFilter(Boolean.parseBoolean(taggedValues.get(UmlTags.filter)));
            }
            //logger.info("taggedValue filter "+property.isFilter());
            if (taggedValues.get(UmlTags.table) != null) {
                property.setTable(Boolean.parseBoolean(taggedValues.get(UmlTags.table)));
            }
            //logger.info("taggedValue table "+property.isTable());
            if (taggedValues.get(UmlTags.detail) != null) {
                property.setDetail(Boolean.parseBoolean(taggedValues.get(UmlTags.detail)));
            }
            //logger.info("taggedValue detail "+property.isDetail());
            if (taggedValues.get(UmlTags.report) != null) {
                property.setReport(Boolean.parseBoolean(taggedValues.get(UmlTags.report)));
            }
            //logger.info("taggedValue report "+property.isReport());
            if (taggedValues.get(UmlTags.export) != null) {
                property.setExport(Boolean.parseBoolean(taggedValues.get(UmlTags.export)));
            }
            //logger.info("taggedValue export "+property.isExport());
            if (taggedValues.get(UmlTags.submit) != null) {
                property.setSubmit(Boolean.parseBoolean(taggedValues.get(UmlTags.submit)));
            }
            //logger.info("taggedValue submit "+property.isSubmit());
            if (taggedValues.get(UmlTags.defaultValue) != null) {
                property.setDefaultValue(taggedValues.get(UmlTags.defaultValue));
            }
            //logger.info("taggedValue default value "+property.getDefaultValue());
            if (taggedValues.get(UmlTags.precision) != null) {
                property.setPrecision(Integer.parseInt(taggedValues.get(UmlTags.precision)));
            }
            //logger.info("taggedValue precision "+property.getPrecision());
            if (taggedValues.get(UmlTags.scale) != null) {
                property.setScale(Integer.parseInt(taggedValues.get(UmlTags.scale)));
            }
            //logger.info("taggedValue scale "+property.getScale());
            if (taggedValues.get(UmlTags.minLength) != null) {
                property.setMinLength(Integer.parseInt(taggedValues.get(UmlTags.minLength)));
            }
            //logger.info("taggedValue min length "+property.getMinLength());
            if (taggedValues.get(UmlTags.maxLength) != null) {
                property.setMaxLength(Integer.parseInt(taggedValues.get(UmlTags.maxLength)));
            }
            //logger.info("taggedValue max length "+property.getMaxLength());
            if (taggedValues.get(UmlTags.calculable) != null) {
                property.setCalculable(Boolean.parseBoolean(taggedValues.get(UmlTags.calculable)));
            }
            //logger.info("taggedValue calculable "+property.isCalculable());
            if (taggedValues.get(UmlTags.nullable) != null) {
                property.setNullable(Boolean.parseBoolean(taggedValues.get(UmlTags.nullable)));
            }
            //logger.info("taggedValue nullable "+property.isNullable());
            if (taggedValues.get(UmlTags.insertable) != null) {
                property.setInsertable(Boolean.parseBoolean(taggedValues.get(UmlTags.insertable)));
            }
            //logger.info("taggedValue insertable "+property.isInsertable());
            if (taggedValues.get(UmlTags.updateable) != null) {
                property.setUpdateable(Boolean.parseBoolean(taggedValues.get(UmlTags.updateable)));
            }
            //logger.info("taggedValue updateable "+property.isUpdateable());
            if (taggedValues.get(UmlTags.unique) != null) {
                property.setUnique(Boolean.parseBoolean(taggedValues.get(UmlTags.unique)));
            }
            //logger.info("taggedValue unique "+property.isUnique());
            if (taggedValues.get(UmlTags.fileReference) != null) {
                property.setFileReference(Boolean.parseBoolean(taggedValues.get(UmlTags.fileReference)));
            }
            //logger.info("fileReference "+property.isFileReference());
            if (taggedValues.get(UmlTags.inactiveIndicator) != null) {
                property.setInactiveIndicator(Boolean.parseBoolean(taggedValues.get(UmlTags.inactiveIndicator)));
            }
        } catch (NullPointerException e) {
            logger.error("Undefined Tagged Value for Property");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            logger.error("Invalid format for tag ");
            e.printStackTrace();
        }
    }

    private XmiProperty extractDirectProperty() {
        XmiProperty property = null;
        String propertyType, dataTypeName;
        if (xmiDomTree.dataTypeIdMap.get(this.getType()) != null) {
            dataTypeName = xmiDomTree.dataTypeIdMap.get(this.getType());
            propertyType = XmiDomTree.properties.getProperty(dataTypeName);
            if (propertyType != null) {
                property = new XmiProperty(propertyType + XmiTags.PROPERTY, false, null, false);
            } else {
                logger.error("Unknown data type : " + dataTypeName);
            }
        } else if (xmiDomTree.entityIdMap.get(this.getType()) != null) {
            property = new XmiProperty(xmiDomTree.entityIdMap.get(this.getType()));
        } else if (xmiDomTree.associationEntityIdMap.get(this.getType()) != null) {
            property = new XmiProperty(xmiDomTree.associationEntityIdMap.get(this.getType()));
        } else {
            logger.error("Unknown type : " + this.getType());
        }
        return property;
    }

    private XmiProperty extractNestedProperty() {
        XmiProperty property = null;
        NodeList types = element.getElementsByTagName(XmiTags.TYPE_TAG);
        if (types.getLength() != 1) {
            //TODO: create exception Too May Types for parameter
            logger.error("Invalid Type for Property : " + this.getNameTag());
        } else {
            Element innerElement = (Element) types.item(0);
            String typeName = StringUtils.substringAfter(innerElement.getAttribute(XmiTags.HREF_TAG), XmiTags.DATATYPE_URI_TAG);
            String propertyType;
            if (typeName != null) {
                propertyType = XmiDomTree.properties.getProperty(typeName);
                if (propertyType != null) {
                    property = new XmiProperty(propertyType, false, null, false);
                } else {
                    logger.error("Unknown type name : " + propertyType);
                }
            }

        }
        return property;
    }

}
