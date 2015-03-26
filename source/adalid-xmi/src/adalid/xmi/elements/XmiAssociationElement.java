/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.types.XmiPersistentEntity;
import adalid.xmi.types.XmiProperty;
import adalid.xmi.util.Typenames;
import adalid.xmi.util.UmlProfileTags;
import adalid.xmi.util.UmlTags;
import adalid.xmi.util.XmiTags;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author cmedina
 */
public class XmiAssociationElement extends XmiElement {

    private static Logger logger = Logger.getLogger(XmiAssociationElement.class);

    private XmiAssociationEdgeElement fromElement;

    private XmiAssociationEdgeElement toElement;

    public XmiAssociationElement(Element _element, XmiDomTree _xmiDomTree) {
        super(_element, _xmiDomTree);
        fillAssociationEdges();
        //fillTaggedValuesMap();
    }

    public XmiAssociationElement(XmiElement _xmiElement, XmiDomTree _xmiDomTree) {
        super(_xmiElement, _xmiDomTree);
        fillAssociationEdges();
        //fillTaggedValuesMap();

    }

    public XmiAssociationEdgeElement getFromElement() {
        return fromElement;
    }

    public XmiAssociationEdgeElement getToElement() {
        return toElement;
    }

    private void fillAssociationEdges() {
        XmiElement currNode;
        NodeList nodeList = element.getElementsByTagName(XmiTags.OWNED_END_TAG);
        if (nodeList.getLength() != 2) {
            logger.error("Too many edges for association Node id: " + getXmiId());
        }
        currNode = new XmiElement((Element) nodeList.item(0), xmiDomTree);
        if (XmiDomTree.isAssociationEdgeElement(currNode)) {
            fromElement = new XmiAssociationEdgeElement(currNode, xmiDomTree);
        } else {
            logger.error("Invalid association Edge: " + currNode.getXmiId());
        }
        currNode = new XmiElement((Element) nodeList.item(1), xmiDomTree);
        if (XmiDomTree.isAssociationEdgeElement(currNode)) {
            toElement = new XmiAssociationEdgeElement(currNode, xmiDomTree);
        } else {
            logger.error("Invalid association Edge: " + currNode.getXmiId());
        }
    }

    public boolean isOneToOneAssociation() {
        return (fromElement.isOneMultiplicity() && toElement.isOneMultiplicity()
            && isOneToOneStereotype());
    }

    public boolean isOneToOneStereotype() {
        return hasStereotype(XmiTags.META_ONE_TO_ONE_STEREOTYPE);
    }

    public boolean isManyToOneAssociation() {
        return (((fromElement.isOneMultiplicity() && toElement.isManyMultiplicity())
            || (toElement.isOneMultiplicity() && fromElement.isManyMultiplicity()))
            && isManyToOneStereotype());
    }

    public boolean isManyToOneStereotype() {
        return hasStereotype(XmiTags.META_MANY_TO_ONE_STEREOTYPE);
    }

    public boolean isManyToManyAssociation() {
        return (fromElement.isManyMultiplicity() && toElement.isManyMultiplicity()
            && isManyToManyStereotype());
    }

    public boolean isManyToManyStereotype() {
        return hasStereotype(XmiTags.META_MANY_TO_MANY_STEREOTYPE);
    }

    public boolean contains(XmiEntityElement element) {
        //System.out.println("comparando "+fromElement.getType()+" con "+element.getXmiId()+" "+fromElement.getType().equals(element.getXmiId()));
        //System.out.println("comparando "+toElement.getType()+" con "+element.getXmiId()+" "+toElement.getType().equals(element.getXmiId()));
        return (fromElement.getType().equals(element.getXmiId())
            || toElement.getType().equals(element.getXmiId()));
    }

    public XmiProperty getAssociationProperty(XmiEntityElement entityElement) {
        XmiProperty associationProperty = null;
        boolean isFrom;
        //0 ver si es circular
        if (fromElement.getType().equals(entityElement.getXmiId())
            && toElement.getType().equals(entityElement.getXmiId())
            && (xmiDomTree.entityIdMap.get(toElement.getType()) != null
            || xmiDomTree.associationEntityIdMap.get(toElement.getType()) != null)
            && (xmiDomTree.entityIdMap.get(fromElement.getType()) != null
            || xmiDomTree.associationEntityIdMap.get(fromElement.getType()) != null)) {
            //se procesan los dos extremos
            if (xmiDomTree.entityIdMap.get(toElement.getType()) != null) {
                XmiPersistentEntity associatedEntity = xmiDomTree.entityIdMap.get(toElement.getType());
                isFrom = true;
                associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
                if (associationProperty == null) {
                    associatedEntity = xmiDomTree.entityIdMap.get(toElement.getType());
                    isFrom = false;
                    associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
                }
            } else if (xmiDomTree.associationEntityIdMap.get(toElement.getType()) != null) {
                {
                    XmiPersistentEntity associatedEntity = xmiDomTree.associationEntityIdMap.get(toElement.getType());
                    isFrom = true;
                    associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
                    if (associationProperty == null) {
                        associatedEntity = xmiDomTree.associationEntityIdMap.get(toElement.getType());
                        isFrom = false;
                        associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
                    }
                }
            } else if (xmiDomTree.entityIdMap.get(toElement.getType()) != null) {
                XmiPersistentEntity associatedEntity = xmiDomTree.entityIdMap.get(toElement.getType());
                isFrom = true;
                associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
                if (associationProperty == null) {
                    associatedEntity = xmiDomTree.entityIdMap.get(toElement.getType());
                    isFrom = false;
                    associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
                }
            }

            //1 ver si es from o es to
        } else if (fromElement.getType().equals(entityElement.getXmiId())
            && (xmiDomTree.entityIdMap.get(toElement.getType()) != null
            || xmiDomTree.associationEntityIdMap.get(toElement.getType()) != null)) {
            //si es from entonces habra una nueva propiedad element.to
            if (xmiDomTree.entityIdMap.get(toElement.getType()) != null) {
                XmiPersistentEntity associatedEntity = xmiDomTree.entityIdMap.get(toElement.getType());
                isFrom = true;
                associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
            } else if (xmiDomTree.associationEntityIdMap.get(toElement.getType()) != null) {
                XmiPersistentEntity associatedEntity = xmiDomTree.associationEntityIdMap.get(toElement.getType());
                isFrom = true;
                associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
            }
        } else if (toElement.getType().equals(entityElement.getXmiId())
            && (xmiDomTree.entityIdMap.get(fromElement.getType()) != null
            || xmiDomTree.associationEntityIdMap.get(fromElement.getType()) != null)) {
            //si es to entonces habra una nueva propiedad element.from
            if (xmiDomTree.entityIdMap.get(fromElement.getType()) != null) {
                XmiPersistentEntity associatedEntity = xmiDomTree.entityIdMap.get(fromElement.getType());
                isFrom = false;
                associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
            } else if (xmiDomTree.associationEntityIdMap.get(fromElement.getType()) != null) {
                XmiPersistentEntity associatedEntity = xmiDomTree.associationEntityIdMap.get(fromElement.getType());
                isFrom = false;
                associationProperty = processAssociationProperty(entityElement, associatedEntity, isFrom);
            }
        }
        if (associationProperty != null) {
            associationProperty.setAssociationId(this.getXmiId());
        }
        return associationProperty;
    }

    private XmiProperty processAssociationProperty(XmiEntityElement thisElement, XmiPersistentEntity associatedEntity, boolean isFrom) {
        XmiProperty newProperty = null;
        if (isOneToOneAssociation()) {
            logger.info("Parsing single One to One association: " + thisElement.getNameTag() + "->" + associatedEntity.getName());
            //System.out.println("navega from "+this.getFromElement().isNavigable()+" nulo from "+this.getFromElement().isNullable());
            //System.out.println("navega to "+this.getToElement().isNavigable()+" nulo to "+this.getToElement().isNullable());

            newProperty = processOneToOneProperty(thisElement, associatedEntity, isFrom);
        }//many to one association starting from this
        else if (isManyToOneAssociation()) {
            logger.info("Parsing single Many to One association " + thisElement.getNameTag() + "->" + associatedEntity.getName());
            newProperty = processManyToOneProperty(thisElement, associatedEntity, isFrom);
        }
        return newProperty;
    }

    private XmiProperty processOneToOneProperty(XmiEntityElement thisElement, XmiPersistentEntity associatedEntity, boolean isFromEdge) {
        XmiProperty newProperty = null;
        XmiAssociationEdgeElement thisEdgeElement = null;
        XmiAssociationEdgeElement associatedEdgeElement = null;
        if (isFromEdge) {
            thisEdgeElement = fromElement;
            associatedEdgeElement = toElement;
        } else {
            thisEdgeElement = toElement;
            associatedEdgeElement = fromElement;
        }
        if (associatedEdgeElement == null) {
            logger.error("Can't find associated edge to " + thisElement.getXmiId());
            return null;
        }//caso 1: este extremo no es navegable, el otro si lo es: caso directo
        if (!thisEdgeElement.isNavigable() && associatedEdgeElement.isNavigable()) {
            newProperty = createProperty(associatedEdgeElement, associatedEntity);
        }//caso 2: ambos extremos tienen la misma navegabilidad: considera si acepta nulos
        else if (thisEdgeElement.isNavigable() == associatedEdgeElement.isNavigable()) {
            //caso 2.1: este extremo no acepta nulos, el otro si acepta: crea la propiedad
            if (thisEdgeElement.isNullable() && !associatedEdgeElement.isNullable()) {
                newProperty = createProperty(associatedEdgeElement, associatedEntity);
            }//caso 2.2: ambos extremos tiene la misma nulabilidad: considera origen destino
            else if (thisEdgeElement.isNullable() == associatedEdgeElement.isNullable()) {
                //caso 2.2.1 este extremo es el origen
                if (isFromEdge) {
                    newProperty = createProperty(associatedEdgeElement, associatedEntity);
                    //System.out.println("creando propiedad");
                }//caso 2.2.2 este extremo es el destino, no hace nada
                else {
                    //System.out.println("no hace nada ");
                    return null;
                }
            }//caso 2.3: este extremo acepta nulos y el otro no, no hace nada en esta pasada
            else {
                return null;
            }
        }//caso 3: este extremo es navegable y el otro no lo es: no hace nada en esta pasada
        else {
            return null;
        }
        if (newProperty != null) {
            //System.out.println("Asignando banderas");
            newProperty.setOneToOne(true);
            setOneToOneNavigability(newProperty, thisEdgeElement, associatedEdgeElement);
            setOneToOneTaggedValues(thisElement, newProperty);
        }
        return newProperty;
    }

    private XmiProperty createProperty(XmiAssociationEdgeElement associatedEdgeElement, XmiPersistentEntity associatedEntity) {
        XmiProperty newProperty = null;
        //caso 1: el extremo de asociacion tiene propiedad asociada, VP garantiza que la propiedad es de this entity, conserva su nombre
        if (!associatedEdgeElement.getReferencedAttributeId().isEmpty()) {
            if (xmiDomTree.propertyIdMap.get(associatedEdgeElement.getReferencedAttributeId()) != null) {
                if (!xmiDomTree.propertyIdMap.get(associatedEdgeElement.getReferencedAttributeId()).isEntityProperty()) {
                    logger.error("Invalid Reference Property " + xmiDomTree.propertyIdMap.get(associatedEdgeElement.getReferencedAttributeId()).getName());
                } else {
                    newProperty = xmiDomTree.propertyIdMap.get(associatedEdgeElement.getReferencedAttributeId());
                }
            } else {
                logger.error("Could not Find Association Property " + associatedEdgeElement.getReferencedAttributeId());
            }
        }//caso 2: el extremo de la asociacion no tiene propiedad asociada, se toma el role name como nombre de la nueva propiedad
        else if (!associatedEdgeElement.getNameTag().isEmpty()) {
            newProperty = new XmiProperty(associatedEntity.getName(), true, associatedEntity.getPackageName(), associatedEntity.isEnumeration());
            newProperty.setName(StringUtils.uncapitalize(associatedEdgeElement.getNameTag()));
        }//caso 3: no existe role name, por lo que la nueva propiedad toma el nombre de la entidad asociada
        else {
            newProperty = new XmiProperty(associatedEntity.getName(), true, associatedEntity.getPackageName(), associatedEntity.isEnumeration());
            newProperty.setName(StringUtils.uncapitalize(associatedEntity.getName()));
        }
        return newProperty;
    }

    private void setOneToOneNavigability(XmiProperty newProperty, XmiAssociationEdgeElement thisEdgeElement, XmiAssociationEdgeElement associatedEdgeElement) {
        if (newProperty != null) {
            if (newProperty.isEnumerationProperty()
                || !thisEdgeElement.isNavigable()
                || !associatedEdgeElement.isNavigable()) {
                newProperty.setOneToOneNavigability(UmlTags.UNIDIRECTIONAL);
                //System.out.println("BIDIRECCIONAL ");
            } else if (thisEdgeElement.isNavigable() && associatedEdgeElement.isNavigable()) {
                newProperty.setOneToOneNavigability(UmlTags.BIDIRECTIONAL);
                //System.out.println("UNIRECCIONAL ");
            } else {
                newProperty.setOneToOneNavigability(UmlTags.UNSPECIFIED);
                //System.out.println("UNSP ");
            }
        }
    }

    private XmiProperty processManyToOneProperty(XmiEntityElement thisElement, XmiPersistentEntity associatedEntity, boolean isFromEdge) {
        XmiProperty newProperty = null;
        XmiAssociationEdgeElement oneEdgeElement = null;
        XmiAssociationEdgeElement manyEdgeElement = null;
        if (isFromEdge) {
            if (fromElement.isOneMultiplicity() && toElement.isManyMultiplicity()) {
                //System.out.println("Do nothing for "+fromElement.getNameTag() +" in one to many association");
                return null;
            } else if (fromElement.isManyMultiplicity() && toElement.isOneMultiplicity()) {
                oneEdgeElement = toElement;
                manyEdgeElement = fromElement;
                newProperty = createProperty(oneEdgeElement, associatedEntity);
            } else {
                logger.error("Association is not one to many neither many to one");
            }
        } else {
            if (fromElement.isOneMultiplicity() && toElement.isManyMultiplicity()) {
                oneEdgeElement = fromElement;
                manyEdgeElement = toElement;
                newProperty = createProperty(oneEdgeElement, associatedEntity);
            } else if (fromElement.isManyMultiplicity() && toElement.isOneMultiplicity()) {
                //logger.info("Do nothing for "+thisElement.getEntityName()+" in one to many association");
                return null;
            } else {
                logger.error("Association is not one to many neither many to one");
                return null;
            }
        }
        if (newProperty != null) {
            newProperty.setManyToOne(true);
            setManyToOneNavigability(newProperty, oneEdgeElement, manyEdgeElement);
            setManyToOneTaggedValues(thisElement, newProperty);
        }
        return newProperty;
    }

    private void setManyToOneNavigability(XmiProperty newProperty, XmiAssociationEdgeElement oneEdgeElement, XmiAssociationEdgeElement manyEdgeElement) {
        if (newProperty.isEnumerationProperty()
            || oneEdgeElement.isNavigable() && !manyEdgeElement.isNavigable()) {
            newProperty.setManyToOneNavigability(UmlTags.UNIDIRECTIONAL);
        } else if (oneEdgeElement.isNavigable() && manyEdgeElement.isNavigable()) {
            newProperty.setManyToOneNavigability(UmlTags.BIDIRECTIONAL);
        } else {
            newProperty.setManyToOneNavigability(UmlTags.UNSPECIFIED);
        }
    }

    public XmiPersistentEntity getAssociationEntity() {
        XmiPersistentEntity newEntity = null;
        XmiProperty newFromProperty = null;
        XmiProperty newToProperty = null;
        String entityName = "";
        String packageName = "";
        //entidad que no existe pero se crea desde asociacion muchos a muchos
        entityName = parseManyToManyEntityName();
        //se captura el paquete por defecto del from
        packageName = parseManyToManyPackageName();
        if (!entityName.isEmpty()) {
            newEntity = new XmiPersistentEntity();
            newEntity.setName(entityName);
            newEntity.setPackageName(packageName);
            newEntity.setManyToManyAssociationEntity(true);
            newEntity.setEnumeration(false);
            newEntity.setEntity(false);
            newEntity.setResourceType(UmlProfileTags.CONFIGURATION);
            //propiedades
            newFromProperty = getManyToManyProperty(fromElement);
            newFromProperty.setManyToOne(true);
            newFromProperty.setTable(true);
            newFromProperty.setReport(true);
            if (newFromProperty != null && newEntity.getPropertiesMap().get(newFromProperty.getName()) == null) {
                setManyToManyTaggedValues(newFromProperty, true);
                logger.debug("adding FROM property " + newEntity.getName() + "." + newFromProperty.getName() + ":" + newFromProperty.getPropertyType());
                newEntity.getPropertiesMap().put(newFromProperty.getName(), newFromProperty);
            } else {
                logger.error("Could not create From property for " + newEntity.getName() + ". Entity will not be created");
                return null;
            }
            newToProperty = getManyToManyProperty(toElement);
            newToProperty.setManyToOne(true);
            newToProperty.setTable(true);
            newToProperty.setReport(true);
            if (newToProperty != null && newEntity.getPropertiesMap().get(newToProperty.getName()) == null) {
                setManyToManyTaggedValues(newToProperty, false);
                logger.debug("adding TO property " + newEntity.getName() + "." + newToProperty.getName() + ":" + newToProperty.getPropertyType());
                newEntity.getPropertiesMap().put(newToProperty.getName(), newToProperty);
            } else {
                logger.error("Could not create To property for " + newEntity.getName() + ". Entity will not be created");
                return null;
            }
        } else {
            logger.error("Empty entity name " + getXmiId() + ". Entity will not be created");
            newEntity = null;
        }
        //if (newEntity != null) {
        //XmiProperty newIdProperty = new XmiProperty(Typenames.LongProperty, false, null, false);
        //newIdProperty.setName(newEntity.getNextIdPropertyName());
        //newIdProperty.setNullable(false);
        //newIdProperty.setUnique(true);
        //logger.info("adding Id property " + newIdProperty.getName() + ":" + newIdProperty.getPropertyType());
        //newEntity.setAssociationId(this.getXmiId());
        //newEntity.getPropertiesMap().put(newIdProperty.getName(), newIdProperty);
        //newEntity.setPrimaryKeyProperty(newIdProperty.getName());
        //System.out.println("new primary key "+newEntity.getPrimaryKeyProperty());
        //}
        return newEntity;
    }

    private String parseManyToManyPackageName() {
        String packageName = "";
        if (xmiDomTree.entityIdMap.get(fromElement.getType()) != null) {
            packageName = xmiDomTree.entityIdMap.get(fromElement.getType()).getPackageName();
        } else if (xmiDomTree.entityIdMap.get(toElement.getType()) != null) {
            packageName = xmiDomTree.entityIdMap.get(toElement.getType()).getPackageName();
        }
        //System.out.println("devolviendo "+fromElement.getType());
        return packageName;
    }

    private String parseManyToManyEntityName() {
        String entityName = "";
        if (!getNameTag().isEmpty()) {
            entityName = getNameTag();
        } else if (!fromElement.getNameTag().isEmpty()
            && !toElement.getNameTag().isEmpty()) {
            entityName = StringUtils.capitalize(fromElement.getNameTag())
                + StringUtils.capitalize(toElement.getNameTag());
        } else if (xmiDomTree.entityIdMap.get(fromElement.getXmiType()) != null
            && xmiDomTree.entityIdMap.get(toElement.getXmiType()) != null) {
            entityName = (xmiDomTree.entityIdMap.get(fromElement.getXmiType())).getName()
                + (xmiDomTree.entityIdMap.get(toElement.getXmiType())).getName();
        }
        return entityName;
    }

    private XmiProperty getManyToManyProperty(XmiAssociationEdgeElement edgeElement) {
        XmiProperty newProperty = null;
        String propertyName = "";
        try {
            //System.out.println("Entrando con " + edgeElement.getNameTag() + "  " + edgeElement.getType() + " y  " + xmiDomTree.entityIdMap.get(edgeElement.getType()).getName());
            if (!edgeElement.getNameTag().isEmpty()) {
                propertyName = edgeElement.getNameTag();
            } else if (xmiDomTree.entityIdMap.get(edgeElement.getType()) != null) {
                propertyName = ((XmiPersistentEntity) xmiDomTree.entityIdMap.get(edgeElement.getXmiType())).getName();
            } else if (xmiDomTree.associationEntityIdMap.get(edgeElement.getType()) != null) {
                propertyName = ((XmiPersistentEntity) xmiDomTree.associationEntityIdMap.get(edgeElement.getXmiType())).getName();
            }
            if (!propertyName.isEmpty()) {
                if (xmiDomTree.entityIdMap.get(edgeElement.getType()) != null) {
                    newProperty = new XmiProperty(xmiDomTree.entityIdMap.get(edgeElement.getType()).getName(), true, xmiDomTree.entityIdMap.get(edgeElement.getType()).getPackageName(), xmiDomTree.entityIdMap.get(edgeElement.getType()).isEnumeration());
                    newProperty.setName(propertyName);
                } else if (xmiDomTree.associationEntityIdMap.get(edgeElement.getType()) != null) {
                    newProperty = new XmiProperty(xmiDomTree.associationEntityIdMap.get(edgeElement.getType()).getName(), true, xmiDomTree.associationEntityIdMap.get(edgeElement.getType()).getPackageName(), false);
                    newProperty.setName(propertyName);
                }
            }
            if (newProperty != null) {
                if (newProperty.isEnumerationProperty()
                    || !edgeElement.isNavigable()) {
                    newProperty.setManyToOneNavigability(UmlTags.UNIDIRECTIONAL);
                } else {
                    newProperty.setManyToOneNavigability(UmlTags.BIDIRECTIONAL);
                }
                newProperty.setNullable(edgeElement.isNullable());
            }
            return newProperty;
        } catch (Exception ex) {
            logger.error("Exception while trying to create new property " + edgeElement.getNameTag());
            return null;
        }
    }

    private XmiAssociationEdgeElement getAssociatedEdgeElement(XmiEntityElement thisElement) {
        XmiAssociationEdgeElement associatedEdgeElement = null;
        if (fromElement.getType().equals(thisElement.getXmiId())) {
            associatedEdgeElement = new XmiAssociationEdgeElement(toElement, xmiDomTree);
        } else if (toElement.getType().equals(thisElement.getXmiId())) {
            associatedEdgeElement = new XmiAssociationEdgeElement(fromElement, xmiDomTree);
        }
        return associatedEdgeElement;
    }

    private void setManyToManyTaggedValues(XmiProperty newProperty, boolean isFromEdge) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            if (isFromEdge) {
                //from element tagged values
                //logger.info("tagged Values for "+newProperty.getPropertyName());
                if (taggedValues.get(UmlTags.fromOnDeleteAction) != null) {
                    newProperty.setOnDeleteAction(taggedValues.get(UmlTags.fromOnDeleteAction));
                }
                //logger.info("taggedValue on delete "+newProperty.getOnDeleteAction());
                if (taggedValues.get(UmlTags.fromOnUpdateAction) != null) {
                    newProperty.setOnUpdateAction(taggedValues.get(UmlTags.fromOnUpdateAction));
                }
                //logger.info("taggedValue on update "+newProperty.getOnUpdateAction());
                if (taggedValues.get(UmlTags.fromReferenceSearchType) != null) {
                    newProperty.setReferenceSearchType(taggedValues.get(UmlTags.fromReferenceSearchType));
                }
                //logger.info("taggedValue reference search type "+newProperty.getReferenceSearchType());
                if (taggedValues.get(UmlTags.fromForeignKey) != null) {
                    if (taggedValues.get(UmlTags.fromForeignKey).equalsIgnoreCase(UmlTags.UNSPECIFIED)) {
                        newProperty.setForeignKey(true);
                    } else {
                        newProperty.setForeignKey(Boolean.parseBoolean(taggedValues.get(UmlTags.fromForeignKey)));
                    }
                }
                //System.out.println("taggedValue foreign key from "+newProperty.getName()+"= "+newProperty.isForeignKey());
                if (taggedValues.get(UmlTags.fromMasterDetailView) != null) {
                    newProperty.setMasterDetailView(taggedValues.get(UmlTags.fromMasterDetailView));
                    if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                        && newProperty.isEnumerationProperty()) {
                        newProperty.setMasterDetailView(UmlTags.NONE);
                    } else if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                        && !newProperty.isEnumerationProperty()) {
                        newProperty.setMasterDetailView(UmlTags.UNSPECIFIED);
                    }
                }
                //logger.info("taggedValue master Detail "+newProperty.getMasterDetailView());
                if (taggedValues.get(UmlTags.fromMaxRound) != null) {
                    newProperty.setMaxRound(Integer.parseInt(taggedValues.get(UmlTags.fromMaxRound)));
                }
                //logger.info("taggedValue max Round "+newProperty.getMaxRound());
                if (taggedValues.get(UmlTags.fromMaxDepth) != null) {
                    newProperty.setMaxDepth(Integer.parseInt(taggedValues.get(UmlTags.fromMaxDepth)));
                }
                //logger.info("taggedValue max Depth "+newProperty.getMaxDepth());
            } else {
                //to element tagged values
                //logger.info("tagged Values for "+newProperty.getPropertyName());
                if (taggedValues.get(UmlTags.toOnDeleteAction) != null) {
                    newProperty.setOnDeleteAction(taggedValues.get(UmlTags.toOnDeleteAction));
                }
                //logger.info("taggedValue on delete "+newProperty.getOnDeleteAction());
                if (taggedValues.get(UmlTags.toOnUpdateAction) != null) {
                    newProperty.setOnUpdateAction(taggedValues.get(UmlTags.toOnUpdateAction));
                }
                //logger.info("taggedValue on update "+newProperty.getOnUpdateAction());
                if (taggedValues.get(UmlTags.toReferenceSearchType) != null) {
                    newProperty.setReferenceSearchType(taggedValues.get(UmlTags.toReferenceSearchType));
                }
                //logger.info("taggedValue reference search type "+newProperty.getReferenceSearchType());
                if (taggedValues.get(UmlTags.toForeignKey) != null) {
                    if (taggedValues.get(UmlTags.toForeignKey).equalsIgnoreCase(UmlTags.UNSPECIFIED)) {
                        newProperty.setForeignKey(true);
                    } else {
                        newProperty.setForeignKey(Boolean.parseBoolean(taggedValues.get(UmlTags.toForeignKey)));
                    }
                }
                //System.out.println("taggedValue foreign key to "+newProperty.getName()+"= "+newProperty.isForeignKey());

                //logger.info("taggedValue foreign key "+newProperty.isForeignKey());
                if (taggedValues.get(UmlTags.toMasterDetailView) != null) {
                    newProperty.setMasterDetailView(taggedValues.get(UmlTags.toMasterDetailView));
                    if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                        && newProperty.isEnumerationProperty()) {
                        newProperty.setMasterDetailView(UmlTags.NONE);
                    } else if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                        && !newProperty.isEnumerationProperty()) {
                        newProperty.setMasterDetailView(UmlTags.UNSPECIFIED);
                    }
                }
                //logger.info("taggedValue master Detail "+newProperty.getMasterDetailView());
                if (taggedValues.get(UmlTags.toMaxRound) != null) {
                    newProperty.setMaxRound(Integer.parseInt(taggedValues.get(UmlTags.toMaxRound)));
                }
                //logger.info("taggedValue max Round "+newProperty.getMaxRound());
                if (taggedValues.get(UmlTags.toMaxDepth) != null) {
                    newProperty.setMaxDepth(Integer.parseInt(taggedValues.get(UmlTags.toMaxDepth)));
                }
                //logger.info("taggedValue max Depth "+newProperty.getMaxDepth());
            }
        } catch (NullPointerException e) {
            logger.error("Undefined Tagged Value for Association");
        } catch (NumberFormatException e) {
            logger.error("Invalid format for tag ");
        }
    }

    private void setOneToOneTaggedValues(XmiEntityElement thisElement, XmiProperty newProperty) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            if (taggedValues.get(UmlTags.onDeleteAction) != null) {
                newProperty.setOnDeleteAction(taggedValues.get(UmlTags.onDeleteAction));
            }
            //logger.info("taggedValue on delete "+newProperty.getOnDeleteAction());
            if (taggedValues.get(UmlTags.onUpdateAction) != null) {
                newProperty.setOnUpdateAction(taggedValues.get(UmlTags.onUpdateAction));
            }
            //logger.info("taggedValue on delete "+newProperty.getOnUpdateAction());
            if (taggedValues.get(UmlTags.referenceSearchType) != null) {
                newProperty.setReferenceSearchType(taggedValues.get(UmlTags.referenceSearchType));
            }
            //logger.info("taggedValue on delete "+newProperty.getReferenceSearchType());
            if (taggedValues.get(UmlTags.foreignKey) != null) {
                if (taggedValues.get(UmlTags.foreignKey).equalsIgnoreCase(UmlTags.UNSPECIFIED)) {
                    newProperty.setForeignKey(true);
                } else {
                    newProperty.setForeignKey(Boolean.parseBoolean(taggedValues.get(UmlTags.foreignKey)));
                }
            }
            //logger.info("taggedValue on delete "+newProperty.getForeignKey());
            if (taggedValues.get(UmlTags.maxRound) != null) {
                newProperty.setMaxRound(Integer.parseInt(taggedValues.get(UmlTags.maxRound)));
            }
            //logger.info("taggedValue max Round " + newProperty.getMaxRound());
            if (taggedValues.get(UmlTags.maxDepth) != null) {
                newProperty.setMaxDepth(Integer.parseInt(taggedValues.get(UmlTags.maxDepth)));
            }
            //logger.info("taggedValue max Depth "+newProperty.getMaxDepth());
        } catch (NullPointerException e) {
            logger.error("Undefined Tagged Value for Association");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            logger.error("Invalid format for tag ");
            e.printStackTrace();
        }

    }

    private void setManyToOneTaggedValues(XmiEntityElement thisElement, XmiProperty newProperty) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            //from element tagged values
            //logger.info("tagged Values for "+newProperty.getPropertyName());
            if (taggedValues.get(UmlTags.onDeleteAction) != null) {
                newProperty.setOnDeleteAction(taggedValues.get(UmlTags.onDeleteAction));
            }
            //logger.info("taggedValue on delete "+newProperty.getOnDeleteAction());
            if (taggedValues.get(UmlTags.onUpdateAction) != null) {
                newProperty.setOnUpdateAction(taggedValues.get(UmlTags.onUpdateAction));
            }
            //logger.info("taggedValue on update "+newProperty.getOnUpdateAction());
            if (taggedValues.get(UmlTags.referenceSearchType) != null) {
                newProperty.setReferenceSearchType(taggedValues.get(UmlTags.referenceSearchType));
            }
            //logger.info("taggedValue reference search type "+newProperty.getReferenceSearchType());
            if (taggedValues.get(UmlTags.foreignKey) != null) {
                if (taggedValues.get(UmlTags.foreignKey).equalsIgnoreCase(UmlTags.UNSPECIFIED)) {
                    newProperty.setForeignKey(true);
                } else {
                    newProperty.setForeignKey(Boolean.parseBoolean(taggedValues.get(UmlTags.foreignKey)));
                }
            }
            //logger.info("taggedValue foreign key "+newProperty.isForeignKey());
            if (taggedValues.get(UmlTags.masterDetailView) != null) {
                newProperty.setMasterDetailView(taggedValues.get(UmlTags.masterDetailView));
                if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                    && newProperty.isEnumerationProperty()) {
                    newProperty.setMasterDetailView(UmlTags.NONE);
                } else if (newProperty.getMasterDetailView().equals(UmlTags.UNSPECIFIED)
                    && !newProperty.isEnumerationProperty()) {
                    newProperty.setMasterDetailView(UmlTags.UNSPECIFIED);
                }
            }
            //logger.info("taggedValue master Detail "+newProperty.getMasterDetailView());
            if (taggedValues.get(UmlTags.maxRound) != null) {
                newProperty.setMaxRound(Integer.parseInt(taggedValues.get(UmlTags.maxRound)));
            }
            //logger.info("taggedValue max Round " + newProperty.getMaxRound());
            if (taggedValues.get(UmlTags.maxDepth) != null) {
                newProperty.setMaxDepth(Integer.parseInt(taggedValues.get(UmlTags.maxDepth)));
            }
            //logger.info("taggedValue max Depth " + newProperty.getMaxDepth());
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error("Undefined Tagged Value for Association");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("Invalid format for tag ");
        }
    }

}
