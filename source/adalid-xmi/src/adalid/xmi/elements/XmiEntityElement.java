/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.types.XmiOperation;
import adalid.xmi.types.XmiPersistentEntity;
import adalid.xmi.types.XmiProperty;
import adalid.xmi.types.XmiRow;
import adalid.xmi.util.Typenames;
import adalid.xmi.util.UmlTags;
import adalid.xmi.util.XmiTags;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author cmedina
 */
public class XmiEntityElement extends XmiElement {

    private static Logger logger = Logger.getLogger(XmiEntityElement.class);

    private List<XmiPropertyElement> propertyElementList = new ArrayList<XmiPropertyElement>();

    private List<XmiOperationElement> operationElementList = new ArrayList<XmiOperationElement>();

    private List<XmiRowElement> rowElementList = new ArrayList<XmiRowElement>();

    public XmiEntityElement(Element _element, XmiDomTree xmiDomTree) {
        super(_element, xmiDomTree);
        fillPropertyElementList();
        fillOperationElementList();
        fillRowElementList();
        //fillTaggedValuesMap();
    }

    public XmiEntityElement(XmiElement _xmiElement, XmiDomTree xmiDomTree) {
        super(_xmiElement, xmiDomTree);
        fillPropertyElementList();
        fillOperationElementList();
        fillRowElementList();
    }

    protected final void fillPropertyElementList() {
        NodeList nodeList = element.getElementsByTagName(XmiTags.OWNED_ATTRIBUTE_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            XmiElement currNode = new XmiElement((Element) nodeList.item(i), xmiDomTree);
            if (XmiDomTree.isPropertyElement(currNode)) {
                XmiPropertyElement xmiPropertyElement = new XmiPropertyElement(currNode, xmiDomTree);
                propertyElementList.add(xmiPropertyElement);
            }
        }
    }

    private void fillRowElementList() {
        NodeList nodeList = element.getElementsByTagName(XmiTags.OWNED_ATTRIBUTE_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            XmiElement currNode = new XmiElement((Element) nodeList.item(i), xmiDomTree);
            if (XmiDomTree.isRowElement(currNode)) {
                XmiRowElement xmiRowElement = new XmiRowElement(currNode, xmiDomTree);
                rowElementList.add(xmiRowElement);
            }
        }
    }

    protected final void fillOperationElementList() {
        NodeList nodeList = element.getElementsByTagName(XmiTags.OWNED_OPERATION_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            XmiElement currNode = new XmiElement((Element) nodeList.item(i), xmiDomTree);
            if (XmiDomTree.isOperationElement(currNode)) {
                XmiOperationElement xmiOperationElement = new XmiOperationElement(currNode, xmiDomTree);
                operationElementList.add(xmiOperationElement);
            }
        }
    }

    public XmiPersistentEntity getEntityFromElement() {
        XmiPersistentEntity entity = new XmiPersistentEntity();
        entity.setName(getNameTag());
        entity.setEntityDataType(getNameTag());
        entity.setPackageName(getPackageContainer());
        entity.setAbstractEntity(isAbstractEntityElement());
        if (XmiDomTree.isEnumerationElement(this)) {
            entity.setEnumeration(true);
            entity.setType(Typenames.PersistentEnumerationEntity);
        } else {
            entity.setEntity(true);
        }
        setTaggedValues(entity);
        return entity;
    }

    public final Map<String, XmiProperty> parseSimplePropertyMap() {
        Map<String, XmiProperty> simplePropertyMap = new LinkedHashMap<String, XmiProperty>();
        logger.debug("Parsing simple PropertyMap for Entity Element " + this.getNameTag());
        for (int i = 0; i < propertyElementList.size(); i++) {
            XmiPropertyElement propertyElement = propertyElementList.get(i);
            XmiProperty property = propertyElement.getPropertyFromElement();
            if (property != null
                && !propertyElement.getNameTag().isEmpty()
                && !propertyElement.getXmiId().isEmpty()) {
                simplePropertyMap.put(propertyElement.getNameTag(), property);
                xmiDomTree.propertyIdMap.put(propertyElement.getXmiId(), property);
                logger.debug("Adding Simple Property (" + propertyElement.getXmiId() + ", " + property.getName() + ") to propertyIdMap");
            }
        }
        return simplePropertyMap;
    }

    public final Map<String, XmiProperty> parseAssociationPropertyMap() {
        Map<String, XmiProperty> associationPropertyMap = new LinkedHashMap<String, XmiProperty>();
        logger.debug("Parsing association PropertyMap for Entity Element " + this.getNameTag());
        for (int i = 0; i < xmiDomTree.associationElementList.size(); i++) {
            XmiAssociationElement associationElement = xmiDomTree.associationElementList.get(i);
            //System.out.println("consultando "+associationElement.getXmiId());
            if (associationElement.contains(this)) {
                //System.out.println("entro "+this.getNameTag());
                XmiProperty property = associationElement.getAssociationProperty(this);
                //System.out.println("propery "+property);
                if (property != null) {
                    associationPropertyMap.put(property.getName(), property);
                    logger.debug("Adding Association Property (" + property.getName() + ", " + property.getName() + ") to associationPropertyIdMap");
                }
            }
        }
        return associationPropertyMap;
    }

    protected final Map<String, XmiOperation> parseOperationMap() {
        Map<String, XmiOperation> operationMap = new LinkedHashMap<String, XmiOperation>();
        logger.debug("Parsing operationMap for Entity Element " + this.getNameTag());
        for (int i = 0; i < operationElementList.size(); i++) {
            XmiOperationElement operationElement = operationElementList.get(i);
            XmiOperation operation = operationElement.getOperationFromElement();
            if (operation != null
                && !operationElement.getNameTag().isEmpty()
                && !operationElement.getXmiId().isEmpty()) {
                operationMap.put(operationElement.getNameTag(), operation);
                logger.debug("Adding Operation (" + operationElement.getNameTag() + ", " + operation.getName() + ") to operationIdMap");
            }
        }
        return operationMap;
    }
    //es el unico que recibe entity porque debe conocer el orden de los properties

    protected final Map<String, XmiRow> parseRowsMap(XmiPersistentEntity entity) {
        Map<String, XmiRow> rowsMap = new LinkedHashMap<String, XmiRow>();
        int rowsCounter = 0;
        logger.debug("Parsing rowsMap for Entity Element " + this.getNameTag());
        for (int i = 0; i < rowElementList.size(); i++) {
            XmiRowElement rowElement = rowElementList.get(i);
            XmiRow row = rowElement.getRowFromElement(entity, rowsCounter);
            if (row != null) {
                rowsCounter++;
                rowsMap.put(rowElement.getNameTag(), row);
                logger.debug("Adding Row (" + rowElement.getNameTag() + ", " + row.getName() + ") to rowsIdMap");
            }
        }
        return rowsMap;
    }

    protected void setTaggedValues(XmiPersistentEntity entity) {
        Map<String, String> taggedValues = (LinkedHashMap<String, String>) this.getTaggedValuesMap();
        try {
            if (taggedValues.get(UmlTags.alias) != null) {
                entity.setAlias(taggedValues.get(UmlTags.alias));
            }
            //logger.info("taggedValue alias = "+entity.getDefaultLabel());
            if (taggedValues.get(UmlTags.defaultLabel) != null) {
                entity.setDefaultLabel(taggedValues.get(UmlTags.defaultLabel));
            }
            //logger.info("taggedValue defaultLabel = "+entity.getDefaultLabel());
            if (taggedValues.get(UmlTags.defaultShortLabel) != null) {
                entity.setDefaultShortLabel(taggedValues.get(UmlTags.defaultShortLabel));
            }
            //logger.info("taggedValue alias = "+entity.getDefaultCollectionLabel());
            if (taggedValues.get(UmlTags.defaultCollectionLabel) != null) {
                entity.setDefaultCollectionLabel(taggedValues.get(UmlTags.defaultCollectionLabel));
            }
            //logger.info("taggedValue defaultShortLabel = "+entity.getDefaultShortLabel());
            if (taggedValues.get(UmlTags.defaultDescription) != null) {
                entity.setDefaultDescription(StringUtils.replace(taggedValues.get(UmlTags.defaultDescription), "\n", "\\n"));
            }
            //logger.info("taggedValue defaultDescription = "+entity.getDefaultDescription());
            if (taggedValues.get(UmlTags.defaultShortDescription) != null) {
                entity.setDefaultShortDescription(taggedValues.get(UmlTags.defaultShortDescription));
            }
            //logger.info("taggedValue defaultShortDescription = "+entity.getDefaultShortDescription());
            if (taggedValues.get(UmlTags.defaultTooltip) != null) {
                entity.setDefaultTooltip(taggedValues.get(UmlTags.defaultTooltip));
            }
            //logger.info("taggedValue defaultTooltip = "+entity.getDefaultTooltip());
            if (taggedValues.get(UmlTags.selectEnabled) != null) {
                entity.setSelectEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.selectEnabled)));
            }
            //logger.info("taggedValue selectAllowed = "+entity.getSelectAllowed());
            if (taggedValues.get(UmlTags.insertEnabled) != null) {
                entity.setInsertEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.insertEnabled)));
            }
            //logger.info("taggedValue insertEnabled = "+entity.getInsertEnabled());
            if (taggedValues.get(UmlTags.updateEnabled) != null) {
                entity.setUpdateEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.updateEnabled)));
            }
            //logger.info("taggedValue updateEnabled = "+entity.getUpdateEnabled());
            if (taggedValues.get(UmlTags.deleteEnabled) != null) {
                entity.setDeleteEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.deleteEnabled)));
            }
            //logger.info("taggedValue deleteEnabled = "+entity.getDeleteEnabled());
            if (taggedValues.get(UmlTags.reportEnabled) != null) {
                entity.setReportEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.reportEnabled)));
            }
            //logger.info("taggedValue reportEnabled = "+entity.getReportEnabled());
            if (taggedValues.get(UmlTags.exportEnabled) != null) {
                entity.setExportEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.exportEnabled)));
            }
            //logger.info("taggedValue exportEnabled = "+entity.getExportEnabled());
            if (taggedValues.get(UmlTags.tableViewEnabled) != null) {
                entity.setTableViewEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.tableViewEnabled)));
            }
            //logger.info("taggedValue tableViewEnabled = "+entity.getTableViewEnabled());
            if (taggedValues.get(UmlTags.detailViewEnabled) != null) {
                entity.setDetailViewEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.detailViewEnabled)));
            }
            //logger.info("taggedValue detailViewEnabled = "+entity.getDetailViewEnabled());
            if (taggedValues.get(UmlTags.treeViewEnabled) != null) {
                entity.setTreeViewEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.treeViewEnabled)));
            }
            //logger.info("taggedValue treeViewEnabled = "+entity.getTreeViewEnabled());
            if (taggedValues.get(UmlTags.consoleViewEnabled) != null) {
                entity.setConsoleViewEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.consoleViewEnabled)));
            }
            //logger.info("taggedValue consoleViewAllowed = "+entity.isConsoleViewEnabled());
            if (taggedValues.get(UmlTags.resourceType) != null) {
                entity.setResourceType(taggedValues.get(UmlTags.resourceType));
            }
            //logger.info("taggedValue resourceType = "+entity.getResourceType());
            if (taggedValues.get(UmlTags.propertiesPrefix) != null) {
                entity.setPropertiesPrefix(taggedValues.get(UmlTags.propertiesPrefix));
            }
            //logger.info("taggedValue propertiesPrefix = "+entity.getPropertiesPrefix());
            if (taggedValues.get(UmlTags.propertiesSuffix) != null) {
                entity.setPropertiesSuffix(taggedValues.get(UmlTags.propertiesSuffix));
            }
            //logger.info("taggedValue propertiesSuffix= "+entity.getPropertiesSuffix());
            if (taggedValues.get(UmlTags.primaryKeyProperty) != null) {
                entity.setPrimaryKeyProperty(taggedValues.get(UmlTags.primaryKeyProperty));
            }
            //logger.info("taggedValue primaryKeyProperty = "+entity.getPrimaryKeyProperty());
            if (taggedValues.get(UmlTags.numericKeyProperty) != null) {
                entity.setNumericKeyProperty(taggedValues.get(UmlTags.numericKeyProperty));
            }
            //logger.info("taggedValue numericKeyProperty = "+entity.getNumericKeyProperty());
            if (taggedValues.get(UmlTags.characterKeyProperty) != null) {
                entity.setCharacterKeyProperty(taggedValues.get(UmlTags.characterKeyProperty));
            }
            //logger.info("taggedValue characterKeyProperty = "+entity.getCharacterKeyProperty()+"  +  "+taggedValues.get(UmlTags.characterKeyProperty));
            if (taggedValues.get(UmlTags.nameProperty) != null) {
                entity.setNameProperty(taggedValues.get(UmlTags.nameProperty));
            }
            //logger.info("taggedValue nameProperty = "+entity.getNameProperty());
            if (taggedValues.get(UmlTags.descriptionProperty) != null) {
                entity.setDescriptionProperty(taggedValues.get(UmlTags.descriptionProperty));
            }
            //logger.info("taggedValue descriptionProperty = "+entity.getDescriptionProperty());
            if (taggedValues.get(UmlTags.ownerProperty) != null) {
                entity.setOwnerProperty(taggedValues.get(UmlTags.ownerProperty));
            }
            //logger.info("taggedValue ownerProperty = "+entity.getOwnerProperty());
            if (taggedValues.get(UmlTags.parentProperty) != null) {
                entity.setParentProperty(taggedValues.get(UmlTags.parentProperty));
            }
            //logger.info("taggedValue parentProperty = "+entity.getParentProperty());
            if (taggedValues.get(UmlTags.versionProperty) != null) {
                entity.setVersionProperty(taggedValues.get(UmlTags.versionProperty));
            }
            //logger.info("taggedValue businessKeyProperty = "+entity.getParentProperty());
            if (taggedValues.get(UmlTags.businessKeyProperty) != null) {
                entity.setVersionProperty(taggedValues.get(UmlTags.businessKeyProperty));
            }
            //logger.info("taggedValue versionProperty = "+entity.getVersionProperty());
            if (taggedValues.get(UmlTags.businessKeyType) != null) {
                entity.setBusinessKeyType(taggedValues.get(UmlTags.businessKeyType));
            }
            //logger.info("taggedValue businessKeyType = "+entity.getBusinesKeyType());
            if (taggedValues.get(UmlTags.selectRowsLimit) != null) {
                entity.setSelectRowsLimit(Integer.parseInt(taggedValues.get(UmlTags.selectRowsLimit)));
            }
            //logger.info("taggedValue selectRowsLimit = "+entity.getSelectRowsLimit());
            if (taggedValues.get(UmlTags.reportRowsLimit) != null) {
                entity.setReportRowsLimit(Integer.parseInt(taggedValues.get(UmlTags.reportRowsLimit)));
            }
            //logger.info("taggedValue reportRowsLimit = "+entity.getReportRowsLimit());
            if (taggedValues.get(UmlTags.triggerBeforeValueEnabled) != null) {
                entity.setTriggerBeforeValueEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.triggerBeforeValueEnabled)));
            }
            //logger.info("taggedValue triggerBeforeDefault = "+entity.getTriggerBeforeDefaultEnabled());
            if (taggedValues.get(UmlTags.triggerAfterValueEnabled) != null) {
                entity.setTriggerAfterValueEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.triggerAfterValueEnabled)));
            }
            //logger.info("taggedValue triggerAfterDefault = "+entity.getTriggerBeforeDefaultEnabled());
            if (taggedValues.get(UmlTags.triggerBeforeCheckEnabled) != null) {
                entity.setTriggerBeforeCheckEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.triggerBeforeCheckEnabled)));
            }
            //logger.info("taggedValue triggerBeforeCheck = "+entity.getTriggerBeforeCheckEnabled());
            if (taggedValues.get(UmlTags.triggerAfterCheckEnabled) != null) {
                entity.setTriggerAfterCheckEnabled(Boolean.parseBoolean(taggedValues.get(UmlTags.triggerAfterCheckEnabled)));
            }
            //logger.info("taggedValue triggerAfterCheck = "+entity.getTriggerAfterCheckEnabled());
            if (taggedValues.get(UmlTags.discriminatorProperty) != null) {
                entity.setDiscriminatorProperty(taggedValues.get(UmlTags.discriminatorProperty));
            }
            //logger.info("taggedValue discriminatorProperty = "+entity.getDiscriminatorProperty()+":"+entity.getName());
            if (taggedValues.get(UmlTags.inheritanceMappingStrategy) != null) {
                entity.setInheritanceStrategy(taggedValues.get(UmlTags.inheritanceMappingStrategy));
            }
            //logger.info("taggedValue inheritanceMappingStrategy = "+entity.getInheritanceStrategy()+":"+entity.getName());
            if (taggedValues.get(UmlTags.discriminatorValue) != null) {
                entity.setDiscriminatorValue(taggedValues.get(UmlTags.discriminatorValue));
            }
            //logger.info("taggedValue discriminatorValue = "+entity.getDiscriminatorValue()+":"+entity.getName());
        } catch (NullPointerException e) {
            logger.error("Undefined Tagged Value for Entity");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            logger.error("Invalid format for tag ");
            e.printStackTrace();
        }
    }

    boolean isChildOf(XmiEntityElement entityElement) {
        NodeList nodeList = element.getElementsByTagName(XmiTags.GENERALIZATION_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            XmiElement parentElement = new XmiElement((Element) nodeList.item(i), xmiDomTree);
            if (parentElement.getAttribute("general").equals(entityElement.getXmiId())) {
                return true;
            }
        }
        return false;
    }

    public XmiPersistentEntity parseBaseEntity() {
        XmiPersistentEntity baseEntity = null;
        NodeList nodeList = element.getElementsByTagName(XmiTags.GENERALIZATION_TAG);
        if (nodeList.getLength() > 1) {
            logger.warn("Entity has more than one base Entities. Multiple Inheritance is not supported " + nodeList.getLength());
        } else if (nodeList.getLength() == 1) {
            XmiElement parentElement = new XmiElement((Element) nodeList.item(0), xmiDomTree);
            if (XmiDomTree.isGeneralizationElement(parentElement)
                && xmiDomTree.entityIdMap.get(parentElement.getAttribute(XmiTags.GENERAL_TAG)) != null) {
                baseEntity = xmiDomTree.entityIdMap.get(parentElement.getAttribute(XmiTags.GENERAL_TAG));
            }
        }
        return baseEntity;
    }

    //busca el inheritancestrategy dentro del entity, debe estar etiquetado con ORM Persistable
//    public String parseInheritanceStrategy(){
//        String inheritanceStrategy="",numberStrategy="";
//        if(XmiDomTree.isORMPersistableElement(this)){
//            NodeList nodeList = element.getElementsByTagName(XmiTags.ORM_DETAIL_TAG);
//            if (nodeList.getLength() >0) {
//                XmiElement ormElement = new XmiElement((Element) nodeList.item(0), xmiDomTree);
//                numberStrategy=ormElement.getAttribute(XmiTags.INHERITANCE_STRATEGY_TAG);
//                if(Integer.parseInt(numberStrategy)==0){
//                    inheritanceStrategy=UmlTags.SINGLE_TABLE;
//                }else if(Integer.parseInt(numberStrategy)==1){
//                    inheritanceStrategy=UmlTags.JOINED;
//                }else if(Integer.parseInt(numberStrategy)==2){
//                    inheritanceStrategy=UmlTags.TABLE_PER_CLASS;
//                }
//            }
//        }
//        return inheritanceStrategy;
//    }
    //el discriminatorValue solo se incluye
    //esta sobreescribe el taggedValue
//    public String parseDiscriminatorValue(){
//        String discriminatorValue="";
//        if(XmiDomTree.isORMPersistableElement(this)){
//            NodeList nodeList = element.getElementsByTagName(XmiTags.ORM_DETAIL_TAG);
//            if (nodeList.getLength() >0) {
//                XmiElement ormElement = new XmiElement((Element) nodeList.item(0), xmiDomTree);
//                discriminatorValue=ormElement.getAttribute(XmiTags.DISCRIMINATOR_VALUE_TAG);
//            }
//        }
//        return discriminatorValue;
//    }
    public boolean isAbstractEntityElement() {
        return Boolean.parseBoolean(element.getAttribute(XmiTags.IS_ABSTRACT_TAG));
    }

}
