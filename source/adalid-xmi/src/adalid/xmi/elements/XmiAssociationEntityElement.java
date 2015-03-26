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
public class XmiAssociationEntityElement extends XmiEntityElement {

    private static Logger logger = Logger.getLogger(XmiAssociationEntityElement.class);

    private XmiAssociationEdgeElement fromElement;

    private XmiAssociationEdgeElement toElement;

    public XmiAssociationEntityElement(Element _element, XmiDomTree _xmiDomTree) {
        super(_element, _xmiDomTree);
        fillAssociationEntityEdges();
    }

    public XmiAssociationEntityElement(XmiElement _xmiElement, XmiDomTree _xmiDomTree) {
        super(_xmiElement, _xmiDomTree);
        fillAssociationEntityEdges();
    }

    public XmiElement getFromElement() {
        return fromElement;
    }

    public XmiElement getToElement() {
        return toElement;
    }

    private void fillAssociationEntityEdges() {
        XmiElement currNode;
        NodeList nodeList = element.getElementsByTagName(XmiTags.MEMBER_END_TAG);
        if (nodeList.getLength() != 2) {
            logger.error("Too many edges for Association Entity Node id: " + getXmiId());
        }
        currNode = new XmiElement((Element) nodeList.item(0), xmiDomTree);
        if (xmiDomTree.elementIdMap.get(currNode.getIdRef()) != null) {
            fromElement = new XmiAssociationEdgeElement(xmiDomTree.elementIdMap.get(currNode.getIdRef()), xmiDomTree);

        } else {
            logger.error("Invalid Association Edge: " + currNode.getIdRef());
        }
        currNode = new XmiElement((Element) nodeList.item(1), xmiDomTree);
        if (xmiDomTree.elementIdMap.get(currNode.getIdRef()) != null) {
            toElement = new XmiAssociationEdgeElement(xmiDomTree.elementIdMap.get(currNode.getIdRef()), xmiDomTree);
        } else {
            logger.error("Invalid Association Edge: " + currNode.getIdRef());
        }

    }

    private XmiProperty parseAssociationEdgeProperty(XmiAssociationEdgeElement element) {
        XmiProperty newProperty = new XmiProperty(xmiDomTree.entityIdMap.get(element.getType()).getName(),
            true,
            xmiDomTree.entityIdMap.get(element.getType()).getPackageName(),
            xmiDomTree.entityIdMap.get(element.getType()).isEnumeration());
        newProperty.setManyToOne(true);
        if (!element.getNameTag().isEmpty()) {
            newProperty.setName(StringUtils.uncapitalize(element.getNameTag()));
        } else {
            newProperty.setName(StringUtils.uncapitalize(newProperty.getPropertyType()));
        }
        if (newProperty.isEnumerationProperty()
            || !element.isNavigable()) {
            newProperty.setManyToOneNavigability(UmlTags.UNIDIRECTIONAL);
        } else {
            newProperty.setManyToOneNavigability(UmlTags.BIDIRECTIONAL);
        }
        return newProperty;
    }

    public Map<String, XmiProperty> parsePropertyMap() {
        logger.debug("Parsing Association Entity " + this.getNameTag());
        Map<String, XmiProperty> propertyMap = super.parseSimplePropertyMap();
        if (this.isManyToMany()) {
            logger.debug(this.getNameTag() + " is Many To Many Association Entity");
            XmiProperty newFromProperty = parseAssociationEdgeProperty(fromElement);
            logger.debug("New From Property " + newFromProperty.getName() + " : " + newFromProperty.getPropertyType());
            parseTaggedValues(newFromProperty, true);
            if (propertyMap.get(newFromProperty.getName()) != null) {
                //TODO: eliminar navegabilidad bidireccional
                propertyMap.get(newFromProperty.getName()).setManyToOne(true);
                propertyMap.get(newFromProperty.getName()).setManyToOneNavigability(UmlTags.BIDIRECTIONAL);
                propertyMap.get(newFromProperty.getName()).combine(newFromProperty);
            } else {
                //TODO: eliminar navegabilidad bidireccional
                newFromProperty.setManyToOne(true);
                newFromProperty.setManyToOneNavigability(UmlTags.BIDIRECTIONAL);
                propertyMap.put(newFromProperty.getName(), newFromProperty);
            }
            XmiProperty newToProperty = parseAssociationEdgeProperty(toElement);
            logger.debug("New To Property " + newToProperty.getName() + " : " + newToProperty.getPropertyType());
            parseTaggedValues(newToProperty, false);
            if (propertyMap.get(newToProperty.getName()) != null) {
                //TODO: eliminar navegabilidad bidireccional
                propertyMap.get(newToProperty.getName()).setManyToOne(true);
                propertyMap.get(newToProperty.getName()).setManyToOneNavigability(UmlTags.BIDIRECTIONAL);
                propertyMap.get(newToProperty.getName()).combine(newToProperty);
            } else {
                //TODO: eliminar navegabilidad bidireccional
                newToProperty.setManyToOne(true);
                newToProperty.setManyToOneNavigability(UmlTags.BIDIRECTIONAL);
                propertyMap.put(newToProperty.getName(), newToProperty);
            }
        } else if (this.isManyToOne()) {
//            logger.debug(this.getNameTag() + " Parsing Many To One Association Entity");
//            if (fromElement.isOneMultiplicity()) {
//                XmiProperty newProperty = parseAssociationEdgeProperty(fromElement);
//                logger.info("New Property " + newProperty.getName() + " : " + newProperty.getPropertyType());
//                parseTaggedValues(newProperty);
//            } else if (toElement.isOneMultiplicity()) {
//                XmiProperty newProperty = parseAssociationEdgeProperty(toElement);
//                logger.info("New Property " + newProperty.getName() + " : " + newProperty.getPropertyType());
//                parseTaggedValues(newProperty);
//            }
        } else {
            logger.error("Invalid Multiplicity for Association Entity " + this.getNameTag() + ". Only <<ManytoMany>> and <<ManyToOne>> are accepted");
        }
        return propertyMap;
    }

    public Boolean isManyToMany() {
        if (fromElement.isManyMultiplicity()
            && toElement.isManyMultiplicity()
            && this.hasStereotype(XmiTags.META_MANY_TO_MANY_STEREOTYPE)) {
            return true;
        }
        return false;
    }

    public Boolean isManyToOne() {
        if ((fromElement.isManyMultiplicity()
            && toElement.isOneMultiplicity()
            || fromElement.isOneMultiplicity()
            && toElement.isManyMultiplicity())
            && this.hasStereotype(XmiTags.META_MANY_TO_ONE_STEREOTYPE)) {
            return true;
        }
        return false;

    }

    private void parseTaggedValues(XmiProperty newProperty, boolean isFromEdge) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            if (isFromEdge) {
                //from element tagged values
                //logger.info("tagged Values for "+newProperty.getName());
                newProperty.setOnDeleteAction(taggedValues.get(UmlTags.fromOnDeleteAction));
                //logger.info("taggedValue on delete "+newProperty.getOnDeleteAction());
                newProperty.setOnUpdateAction(taggedValues.get(UmlTags.fromOnUpdateAction));
                //logger.info("taggedValue on update "+newProperty.getOnUpdateAction());
                newProperty.setReferenceSearchType(taggedValues.get(UmlTags.fromReferenceSearchType));
                //logger.info("taggedValue reference search type "+newProperty.getReferenceSearchType());
                if (taggedValues.get(UmlTags.fromForeignKey) != null) {
                    newProperty.setForeignKey(Boolean.parseBoolean(taggedValues.get(UmlTags.fromForeignKey)));
                } else {
                    newProperty.setForeignKey(true);
                }
                //System.out.println("taggedValue foreign key "+taggedValues.get(UmlTags.fromForeignKey));
                newProperty.setMasterDetailView(taggedValues.get(UmlTags.fromMasterDetailView));
                if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                    && newProperty.isEnumerationProperty()) {
                    newProperty.setMasterDetailView(UmlTags.NONE);
                } else if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                    && !newProperty.isEnumerationProperty()) {
                    newProperty.setMasterDetailView(UmlTags.UNSPECIFIED);
                }
                //logger.info("taggedValue master Detail "+newProperty.getMasterDetailView());
                newProperty.setMaxRound(Integer.parseInt(taggedValues.get(UmlTags.fromMaxRound)));
                //logger.info("taggedValue maxRound "+newProperty.getMaxRound());
                newProperty.setMaxDepth(Integer.parseInt(taggedValues.get(UmlTags.fromMaxDepth)));
                //logger.info("taggedValue maxDepth "+newProperty.getMaxDepth());
            } else {
                //to element tagged values
                //logger.info("tagged Values for "+newProperty.getName());
                newProperty.setOnDeleteAction(taggedValues.get(UmlTags.toOnDeleteAction));
                //logger.info("taggedValue on delete "+newProperty.getOnDeleteAction());
                newProperty.setOnUpdateAction(taggedValues.get(UmlTags.toOnUpdateAction));
                //logger.info("taggedValue on update "+newProperty.getOnUpdateAction());
                newProperty.setReferenceSearchType(taggedValues.get(UmlTags.toReferenceSearchType));
                //logger.info("taggedValue reference search type "+newProperty.getReferenceSearchType());
                if (taggedValues.get(UmlTags.toForeignKey) != null) {
                    newProperty.setForeignKey(Boolean.parseBoolean(taggedValues.get(UmlTags.toForeignKey)));
                } else {
                    newProperty.setForeignKey(true);
                }
                //System.out.println("taggedValue foreign key 2 "+taggedValues.get(UmlTags.fromForeignKey));
                newProperty.setMasterDetailView(taggedValues.get(UmlTags.toMasterDetailView));
                if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                    && newProperty.isEnumerationProperty()) {
                    newProperty.setMasterDetailView(UmlTags.NONE);
                } else if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                    && !newProperty.isEnumerationProperty()) {
                    newProperty.setMasterDetailView(UmlTags.UNSPECIFIED);
                }
                //logger.info("taggedValue master Detail "+newProperty.getMasterDetailView());
                newProperty.setMaxRound(Integer.parseInt(taggedValues.get(UmlTags.toMaxRound)));
                //logger.info("taggedValue maxRound "+newProperty.getMaxRound());
                newProperty.setMaxDepth(Integer.parseInt(taggedValues.get(UmlTags.toMaxDepth)));
                //logger.info("taggedValue maxDepth "+newProperty.getMaxDepth());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error("Undefined Tagged Value for Association. Check the Profile Definition");
        }
    }

    private void parseTaggedValues(XmiProperty newProperty) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            //logger.info("tagged Values for "+newProperty.getName());
            newProperty.setOnDeleteAction(taggedValues.get(UmlTags.onDeleteAction));
            //logger.info("taggedValue on delete "+newProperty.getOnDeleteAction());
            newProperty.setOnUpdateAction(taggedValues.get(UmlTags.onUpdateAction));
            //logger.info("taggedValue on update "+newProperty.getOnUpdateAction());
            newProperty.setReferenceSearchType(taggedValues.get(UmlTags.referenceSearchType));
            //logger.info("taggedValue reference search type "+newProperty.getReferenceSearchType());
            newProperty.setForeignKey(Boolean.parseBoolean(taggedValues.get(UmlTags.foreignKey)));
            System.out.println("taggedValue foreign key 3" + newProperty.isForeignKey());
            newProperty.setMasterDetailView(taggedValues.get(UmlTags.masterDetailView));
            if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                && newProperty.isEnumerationProperty()) {
                newProperty.setMasterDetailView(UmlTags.NONE);
            } else if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                && !newProperty.isEnumerationProperty()) {
                newProperty.setMasterDetailView(UmlTags.UNSPECIFIED);
            }
            //logger.info("taggedValue master Detail "+newProperty.getMasterDetailView());
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error("Undefined Tagged Value for Association. Check the Profile Definition");
        }
    }

}
