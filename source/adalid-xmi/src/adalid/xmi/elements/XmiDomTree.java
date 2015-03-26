/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.commons.properties.PropertiesHandler;
import adalid.xmi.properties.MappingBundle;
import adalid.xmi.types.XmiOperation;
import adalid.xmi.types.XmiParameter;
import adalid.xmi.types.XmiPersistentEntity;
import adalid.xmi.types.XmiProperty;
import adalid.xmi.types.XmiRow;
import adalid.xmi.types.XmiRowField;
import adalid.xmi.util.UmlTags;
import adalid.xmi.util.XmiTags;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;

/**
 *
 * @author cmedina
 */
public class XmiDomTree {

    private static Logger logger = Logger.getLogger(XmiDomTree.class);

    static final Properties properties = PropertiesHandler.loadProperties(MappingBundle.getResourceBundle());

    private TreeWalker xmiTree;

    Map<String, XmiElement> elementIdMap = new LinkedHashMap<String, XmiElement>();

    Map<String, String> dataTypeIdMap = new LinkedHashMap<String, String>();

    Map<String, String> stereotypeIdMap = new LinkedHashMap<String, String>();

    Map<String, XmiPersistentEntity> entityIdMap = new LinkedHashMap<String, XmiPersistentEntity>();

    Map<String, XmiPersistentEntity> associationEntityIdMap = new LinkedHashMap<String, XmiPersistentEntity>();

    Map<String, XmiProperty> propertyIdMap = new LinkedHashMap<String, XmiProperty>();

    List<XmiEntityElement> entityElementList = new ArrayList<XmiEntityElement>();

    List<XmiElement> memberElementList = new ArrayList<XmiElement>();

    List<XmiAssociationElement> associationElementList = new ArrayList<XmiAssociationElement>();

    List<XmiAssociationEntityElement> associationEntityElementList = new ArrayList<XmiAssociationEntityElement>();

    List<XmiPersistentEntity> entitiesList = new ArrayList<XmiPersistentEntity>();

    // <editor-fold defaultstate="collapsed" desc="Class Constructors">
    /**
     *
     * @param filePath
     * @param defaultPackageName
     * @throws Exception
     */
    public XmiDomTree(String filePath, String defaultPackageName) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
        xmiTree = ((DocumentTraversal) document).createTreeWalker(document.getDocumentElement(), NodeFilter.SHOW_ALL, new XmiNodeFilter(), true);
        initializeCollections(defaultPackageName);
        checkXmiVersion(document);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Element Validators">
    public static boolean isStereotypeElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_STEREOTYPE_TAG);
    }

    public static boolean isDatatypeElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_DATATYPE_TAG);
    }

    public static boolean isPackageElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_PACKAGE_TAG);
    }

    public static boolean isEntityElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_CLASS_TAG)
            && element.hasStereotype(XmiTags.META_ENTITY_STEREOTYPE);

    }

    public static boolean isEnumerationElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_CLASS_TAG)
            && element.hasStereotype(XmiTags.META_ENUMERATION_STEREOTYPE);

    }

    public static boolean isEntityOrEnumerationElement(XmiElement element) {
        return (isEntityElement(element) || isEnumerationElement(element) || isAssociationEntityElement(element));
    }

    public static boolean isAssociationEntityElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_ASSOCIATION_CLASS_TAG)
            && element.hasStereotype(XmiTags.META_ENTITY_STEREOTYPE);
    }

    public static boolean isPropertyElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_PROPERTY_TAG)
            && element.hasStereotype(XmiTags.META_PROPERTY_STEREOTYPE);
    }

    public static boolean isOperationElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_OPERATION_TAG)
            && element.hasStereotype(XmiTags.META_OPERATION_STEREOTYPE);
    }

    public static boolean isRowElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_PROPERTY_TAG)
            && element.hasStereotype(XmiTags.CONSTANT_STEREOTYPE);
    }

    public static boolean isParameterElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_PARAMETER_TAG)
            && element.hasStereotype(XmiTags.META_PARAMETER_STEREOTYPE);
    }

    public static boolean isAssociationElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_ASSOCIATION_TAG)
            && (element.hasStereotype(XmiTags.META_ONE_TO_ONE_STEREOTYPE)
            ^ element.hasStereotype(XmiTags.META_MANY_TO_ONE_STEREOTYPE)
            ^ element.hasStereotype(XmiTags.META_MANY_TO_MANY_STEREOTYPE));
    }

    public static boolean isManyToManyAssociationElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_ASSOCIATION_TAG)
            && element.hasStereotype(XmiTags.META_MANY_TO_MANY_STEREOTYPE);
    }

    public static boolean isManyToOneAssociationElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_ASSOCIATION_TAG)
            && element.hasStereotype(XmiTags.META_MANY_TO_ONE_STEREOTYPE);
    }

    public static boolean isOneToOneAssociationElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_ASSOCIATION_TAG)
            && element.hasStereotype(XmiTags.META_ONE_TO_ONE_STEREOTYPE);
    }

    public static boolean isAssociationEdgeElement(XmiElement element) {
        return (element.getType() != null
            && element.getXmiType().equalsIgnoreCase(XmiTags.UML_PROPERTY_TAG));
    }

    public static boolean isGeneralizationElement(XmiElement element) {
        return element.getXmiType().equalsIgnoreCase(XmiTags.UML_GENERALIZATION_TAG);
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Class collections initializers">
    private void initializeCollections(String defaultPackageName) {
        //common elements
        fillElementIdMap(xmiTree);
        fillStereotypeIdMap();
        fillDatatypeIdMap();
        //packages
        //TODO quitar el paquete por defecto
        setPackageToElements(xmiTree, defaultPackageName);
        //entities
        fillEntityElementList();
        fillEntityIdMap();
        //associations
        fillAssociationElementList();
        //association Entities
        fillAssociationEntityElementList();
        fillAssociationEntityIdMap();
    }

    private void setPackageToElements(TreeWalker tw, String currentPackage) {
        Node n = tw.getCurrentNode();
        XmiElement element = new XmiElement((Element) n, this);
        if (isPackageElement(element)) {
            if (currentPackage.isEmpty()) {
                currentPackage = element.getNameTag();
            } else {
                currentPackage = currentPackage + XmiTags.SEPARATOR + element.getNameTag();
            }
            logger.debug("Current package has changed " + currentPackage);
        } else if (elementIdMap.get(element.getXmiId()) != null) {
            elementIdMap.get(element.getXmiId()).setPackageContainer(currentPackage);
        }
        for (Node child = tw.firstChild();
            child != null;
            child = tw.nextSibling()) {
            setPackageToElements(tw, currentPackage);
        }
        tw.setCurrentNode(n);
    }
    //se usa para poder buscar las propiedades que referencian una entidad de asociacion
    //es recursivo por eso recibe a tw

    private void fillElementIdMap(TreeWalker tw) {
        Node n = tw.getCurrentNode();
        Element element = (Element) n;
        if (element.getAttributeNode(XmiTags.XMI_ID_TAG) != null) {
            XmiElement xmiElement = new XmiElement(element, this);
            elementIdMap.put(xmiElement.getXmiId(), xmiElement);
            //System.out.println("entity "+xmiElement.getNameTag()+" type "+xmiElement.getXmiType());
        }
        for (Node child = tw.firstChild();
            child != null;
            child = tw.nextSibling()) {
            fillElementIdMap(tw);
        }
        tw.setCurrentNode(n);
    }

    private void fillStereotypeIdMap() {
        for (XmiElement element : elementIdMap.values()) {
            if (isStereotypeElement(element)) {
                stereotypeIdMap.put(element.getXmiId(), element.getNameTag());
            }
        }
    }

    private void fillDatatypeIdMap() {
        for (XmiElement element : elementIdMap.values()) {
            if (isDatatypeElement(element)) {
                dataTypeIdMap.put(element.getXmiId(), element.getNameTag());

            }
        }
    }

    private void fillEntityElementList() {
        logger.debug("Filling Entity Element List");
        for (XmiElement currNode : elementIdMap.values()) {
            if (isEntityElement(currNode) || isEnumerationElement(currNode)) {
                XmiEntityElement entityElement = new XmiEntityElement(currNode, this);
                entityElementList.add(entityElement);
                logger.debug("Adding Entity Element NameTag: " + entityElement.getNameTag());
            }
        }
    }

    private void fillEntityIdMap() {
        logger.debug("Filling EntityIdMap");
        for (XmiEntityElement entityElement : entityElementList) {
            XmiPersistentEntity entity = entityElement.getEntityFromElement();
            entityIdMap.put(entityElement.getXmiId(), entity);
            logger.debug("Adding Entity (" + entityElement.getXmiId() + ", " + entityElement.getNameTag() + ") To entityIdMap");
        }
    }

    private void fillAssociationEntityElementList() {
        logger.debug("Filling Association Entity Element List");
        for (XmiElement currNode : elementIdMap.values()) {
            if (isAssociationEntityElement(currNode)) {
                XmiAssociationEntityElement xmiAssociationEntityElement = new XmiAssociationEntityElement(currNode, this);
                if (isEntityOrEnumerationElement(elementIdMap.get(xmiAssociationEntityElement.getFromElement().getType()))
                    && isEntityOrEnumerationElement(elementIdMap.get(xmiAssociationEntityElement.getToElement().getType()))) {
                    associationEntityElementList.add(xmiAssociationEntityElement);
                    logger.debug("Adding Association Entity Element NameTag: " + xmiAssociationEntityElement.getNameTag() + " To entityIdMap");
                } else {
                    logger.error("Could not parse Many to Many Association Entity " + xmiAssociationEntityElement.getNameTag() + ": Invalid Edges");
                }
            }
        }
    }

    private void fillAssociationEntityIdMap() {
        logger.debug("Filling AssociationEntityIdMap");
        for (XmiAssociationEntityElement entityElement : associationEntityElementList) {
            XmiPersistentEntity entity = entityElement.getEntityFromElement();
            associationEntityIdMap.put(entityElement.getXmiId(), entity);
            logger.debug("Adding Association Entity (" + entityElement.getXmiId() + ", " + entity.getName() + ") to associationEntityIdMap");
        }
    }

    private void fillAssociationElementList() {
        logger.debug("Filling Association Element List");
        for (XmiElement currElement : elementIdMap.values()) {
            if (isAssociationElement(currElement)) {
                XmiAssociationElement xmiAssociationElement = new XmiAssociationElement(currElement, this);
                if (elementIdMap.get(xmiAssociationElement.getFromElement().getType()) == null
                    || elementIdMap.get(xmiAssociationElement.getToElement().getType()) == null) {
                    logger.error("Invalid association element " + currElement.getXmiId());
                } else if (isEntityOrEnumerationElement(elementIdMap.get(xmiAssociationElement.getFromElement().getType()))
                    && isEntityOrEnumerationElement(elementIdMap.get(xmiAssociationElement.getToElement().getType()))) {
                    associationElementList.add(xmiAssociationElement);
                    logger.debug("Adding Association Element " + xmiAssociationElement.getFromElement().getNameTag() + "->" + xmiAssociationElement.getToElement().getNameTag() + " ID " + xmiAssociationElement.getXmiId());
                } else {
                    logger.error("Could not Add Association Element " + xmiAssociationElement.getFromElement().getNameTag() + "->" + xmiAssociationElement.getToElement().getNameTag() + " : Invalid Edges");
                }
            }
        }
    }

    // </editor-fold>
    public List<XmiPersistentEntity> getEntitiesList() {
        if (entitiesList.isEmpty()) {
            logger.debug("Parsing Entities List");
            List<XmiPersistentEntity> initialEntityList = getInitialEntityList();
            for (XmiPersistentEntity xmiPersistentEntity : initialEntityList) {
                if (xmiPersistentEntity != null) {
                    if (xmiPersistentEntity.isEnumeration()) {
                        logger.debug("Parsing Enumeration " + xmiPersistentEntity.getName());
                    } else {
                        logger.debug("Parsing Entity name " + xmiPersistentEntity.getName());
                    }
                    for (XmiProperty property : this.getPropertyMap(xmiPersistentEntity).values()) {
                        if (property != null) {
                            xmiPersistentEntity.getPropertiesMap().put(property.getName(), property);
                            logger.info("Property Created " + xmiPersistentEntity.getName() + "." + property.getName() + ":" + property.getPropertyType());
                        }
                    }
                    for (XmiOperation operation : this.getOperationMap(xmiPersistentEntity).values()) {
                        if (operation != null) {
                            logger.info("Operation Created " + xmiPersistentEntity.getName() + "." + operation.getName());
                            for (XmiParameter parameter : operation.getParametersMap().values()) {
                                if (parameter != null) {
                                    logger.info("Parameter created " + xmiPersistentEntity.getName() + "." + operation.getName() + "." + parameter.getName() + ":" + parameter.getParameterType());
                                }
                            }
                            xmiPersistentEntity.getOperationsMap().put(operation.getName(), operation);
                        }
                    }
                    for (XmiRow row : this.getRowsMap(xmiPersistentEntity).values()) {
                        if (row != null) {
                            logger.info("Row created " + xmiPersistentEntity.getName() + "." + row.getName());
                            for (XmiRowField rowField : row.getRowFieldsMap().values()) {
                                if (rowField != null) {
                                    logger.info("RowField created " + xmiPersistentEntity.getName() + "." + row.getName() + "." + rowField.getName() + "=" + rowField.getValue() + " : " + rowField.getType());
                                }
                            }
                            xmiPersistentEntity.getRowsMap().put(row.getName(), row);
                        }
                    }
                    entitiesList.add(xmiPersistentEntity);
                }
            }
            logger.info("checking inheritance for the model ");
            for (XmiPersistentEntity entity : entitiesList) {
                XmiPersistentEntity baseEntity = null;
                if (searchEntityElement(entity) != null) {
                    XmiEntityElement entityElement = searchEntityElement(entity);
                    //System.out.println("entidad base de "+entityElement.getNameTag());
                    baseEntity = entityElement.parseBaseEntity();
                    //0. si no tengo clase padre tambien puedo tener discriminador pero desde taggedValue
                    //1. verificar si tengo padre
                    if (baseEntity != null) {
                        entity.setBaseEntity(baseEntity);
                        //parse inheritance for operations
                        for (XmiOperation operation : entity.getOperationsMap().values()) {
                            for (XmiOperation baseOperation : baseEntity.getOperationsMap().values()) {
                                if (operation.getName().equalsIgnoreCase(baseOperation.getName())) {
                                    if (!baseEntity.getPackageName().isEmpty()) {
                                        operation.setBaseOperationName(baseEntity.getPackageName() + "." + baseEntity.getName() + "." + baseOperation.getOperationNameString());
                                    } else {
                                        operation.setBaseOperationName(baseEntity.getName() + "." + baseOperation.getOperationNameString());
                                    }
                                    logger.info("Operation " + operation.getName() + " extends " + operation.getBaseOperationName());
                                }
                            }
                        }
                        //1.1 Verificar discriminador (solo para las entidades concretas)
                        if (!entity.isAbstractEntity()) {
                            if (entity.getDiscriminatorValue().isEmpty() && baseEntity.getInheritanceStrategy().equals(UmlTags.SINGLE_TABLE)) {
                                logger.warn("Discriminator Value for non abstract entity " + entity.getName() + " is Empty when base entity is SINGLE_TABLE");
                            } else if (!entity.getDiscriminatorValue().isEmpty()) {
                                logger.info("Generalization created " + baseEntity.getName() + "->" + entity.getName() + " discriminator value =" + entity.getDiscriminatorValue());
                            }
                        }
                    }
                    //2. verificar si tengo hijos
                    for (XmiEntityElement candidateElement : entityElementList) {
                        if (candidateElement.getXmiId().equals(entityElement.getXmiId())) {
                            continue;
                        }
                        if (candidateElement.isChildOf(entityElement)) {
                            XmiPersistentEntity child = entityIdMap.get(candidateElement.getXmiId());
                            entity.getChildrenMap().put(child.getName(), child);
                        }
                    }
                    if (!entity.getChildrenMap().isEmpty()) {
                        //2.1 si tengo hijos siempre tendre un inheritanceStrategy por defecto es single_table
                        if (entity.getInheritanceStrategy().isEmpty()) {
                            logger.warn(entity.getName() + " Inheritance Mapping Strategy is empty, SINGLE_TABLE assumed ");
                            entity.setInheritanceStrategy(UmlTags.SINGLE_TABLE);
                        }//si es single_table debo validar discriminator property
                        else if (entity.getInheritanceStrategy().equals(UmlTags.SINGLE_TABLE)) {
                            if (entity.getDiscriminatorProperty().isEmpty()) {
                                logger.warn("Discriminator Property " + entity.getDiscriminatorProperty() + " is Empty for " + entity.getName() + ", it will be provided by Persistence Provider ");
                            } else if (entity.getValidDiscriminatorProperty().isEmpty()) {
                                logger.warn("Discriminator Property " + entity.getDiscriminatorProperty() + "is not valid for " + entity.getName() + ", it will be provided by Persistence Provider ");
                            }
                        }//si es cualquier otra inheritanceStrategy ignora el discriminator pero advierte
                        else if (entity.isValidInheritanceMapping()) {
                            if (!entity.getDiscriminatorProperty().isEmpty()
                                && entity.getValidDiscriminatorProperty().isEmpty()) {
                                logger.warn("Discriminator Property " + entity.getDiscriminatorProperty() + " is not valid for " + entity.getName() + " it will be ignored");
                            }
                        }// si no es inheritanceStrategy valida debo mostrar error y colocar single_table
                        else {
                            logger.error("Invalid Inheritance Mapping Strategy : " + entity.getInheritanceStrategy() + " for " + entity.getName());
                            entity.setInheritanceStrategy(UmlTags.SINGLE_TABLE);
                        }
                    }
                }
            }

            logger.info("Parsing Many to Many Associations");
            List<XmiPersistentEntity> manyToManyEntitiesList = this.getManyToManyEntitiesList();
            for (XmiPersistentEntity manyToManyEntity : manyToManyEntitiesList) {
                if (manyToManyEntity != null) {
                    logger.info("Adding Many To Many Association Entity " + manyToManyEntity.getName());
                    entitiesList.add(manyToManyEntity);
                }
            }
            logger.info("Checking integrity for the model");
            for (XmiPersistentEntity formedEntity : entitiesList) {
                if (validateIntegrity(formedEntity)) {
                    logger.debug("Entity " + formedEntity.getName() + " has a valid Id");
                } else if (!formedEntity.isEnumeration()) {
                    logger.debug("Entity " + formedEntity.getName() + " doesn't have a valid Id");
//                    XmiProperty newIdProperty = new XmiProperty(Typenames.LongProperty, false, null);
//                    newIdProperty.setName("Id");
//                    newIdProperty.setName(formedEntity.getNextIdPropertyName());
//                    newIdProperty.setNullable(false);
//                    newIdProperty.setUnique(true);
//                    logger.debug("Adding Id property " +formedEntity.getName()+"."+ newIdProperty.getName() + ":" + newIdProperty.getPropertyType());
//                    formedEntity.getPropertiesMap().put(newIdProperty.getName(), newIdProperty);
//                    formedEntity.setPrimaryKeyProperty(newIdProperty.getName());
                    //ignoreAllReferences(formedEntity);
                }
            }
            Iterator it1 = entitiesList.iterator();
            while (it1.hasNext()) {
                XmiPersistentEntity checkedEntity = (XmiPersistentEntity) it1.next();
                if (checkedEntity.isIgnored()) {
                    logger.debug("Ignoring entity : " + checkedEntity.getName());
                    it1.remove();
                }
            }

        }
        return entitiesList;
    }

    private void ignoreAllReferences(XmiPersistentEntity xmiPersistentEntity) {
        //System.out.println("entro con "+xmiPersistentEntity.getName()+" ignorado "+xmiPersistentEntity.isIgnored());
        for (XmiPersistentEntity currentEntity : entitiesList) {
            if (currentEntity == xmiPersistentEntity) {
                //System.out.println("encontro igualdad "+xmiPersistentEntity.getName());
                currentEntity.setIgnored(true);
                continue;
            } else if (currentEntity.getBaseEntity() != null && currentEntity.getBaseEntity() == xmiPersistentEntity) {
                currentEntity.setBaseEntity(null);
                logger.debug("Ignoring base Entity for " + currentEntity.getName());
            } else {
                for (XmiProperty currentProperty : currentEntity.getPropertiesMap().values()) {
                    if (currentProperty.getPropertyType().equals(xmiPersistentEntity.getName())) {
                        currentProperty.setIgnored(true);
                        logger.debug("Ignoring property " + currentProperty.getName());
                    }
                }
                for (XmiOperation currentOperation : currentEntity.getOperationsMap().values()) {
                    for (XmiParameter currentParameter : currentOperation.getParametersMap().values()) {
                        if (currentParameter.getParameterType().equals(xmiPersistentEntity.getName())) {
                            currentParameter.setIgnored(true);
                            logger.debug("Ignoring parameter " + currentParameter.getName());
                        }
                    }
                }
            }
        }
        //System.out.println("salio con "+xmiPersistentEntity.getName()+"ignorado "+xmiPersistentEntity.isIgnored());
    }

    public final void checkXmiVersion(Document document) throws Exception {
        if (document.getElementsByTagName("xmi:XMI").getLength() == 1) {
            Element element = (Element) document.getElementsByTagName("xmi:XMI").item(0);
            if (element.hasAttribute("xmi:version")) {
                String version = element.getAttribute("xmi:version");
                if (version.equals("2.1")) {
                    logger.debug("Parsing Xmi File: Version " + version);
                } else {
                    logger.fatal("Invalid XMI Version");
                    throw new Exception("Invalid XMI Version");
                }
            }
        } else {
            throw new Exception();
        }
    }

    private XmiEntityElement searchEntityElement(XmiPersistentEntity entity) {
        for (XmiEntityElement entityElement : entityElementList) {
            if (entityElement.getNameTag().equals(entity.getName())) {
                return entityElement;
            }
        }
        return null;
    }

    private XmiAssociationEntityElement searchAssociationEntityElement(XmiPersistentEntity entity) {
        for (int i = 0; i < associationEntityElementList.size(); i++) {
            if (associationEntityElementList.get(i).getNameTag().equals(entity.getName())) {
                return associationEntityElementList.get(i);
            }
        }
        return null;
    }

    private XmiAssociationElement searchAssociationElement(XmiPersistentEntity entity) {
        for (int i = 0; i < associationElementList.size(); i++) {
            if (associationElementList.get(i).getXmiId().equals(entity.getAssociationId())) {
                return associationElementList.get(i);
            }
        }
        return null;
    }

    private Map<String, XmiProperty> getSimplePropertyMap(XmiPersistentEntity entity) {
        Map<String, XmiProperty> propertyMap = new LinkedHashMap<String, XmiProperty>();
        if (searchEntityElement(entity) != null) {
            XmiEntityElement entityElement = searchEntityElement(entity);
            propertyMap = entityElement.parseSimplePropertyMap();
        } else if (searchAssociationEntityElement(entity) != null) {
            XmiAssociationEntityElement associationEntityElement = searchAssociationEntityElement(entity);
            if (associationEntityElement != null) {
                propertyMap = associationEntityElement.parsePropertyMap();
            }
        } else {
            logger.error("Couldn't find simple properties for entity: " + entity.getName());
        }
        return propertyMap;
    }

    //solo es valido para entidades normales, las de asociacion se procesan aparte
    private Map<String, XmiProperty> getAssociationPropertyMap(XmiPersistentEntity entity) {
        Map<String, XmiProperty> propertyMap = new LinkedHashMap<String, XmiProperty>();
        if (searchEntityElement(entity) != null) {
            XmiEntityElement entityElement = searchEntityElement(entity);
            propertyMap = entityElement.parseAssociationPropertyMap();
        } else if (searchAssociationEntityElement(entity) != null) {
            XmiAssociationEntityElement associationEntityElement = searchAssociationEntityElement(entity);
            propertyMap = associationEntityElement.parseAssociationPropertyMap();
        }
        return propertyMap;
    }

    //buscar todas las entidades que extiendan a entity
    public Map<String, XmiPersistentEntity> getChildrenEntityMap(XmiPersistentEntity entity) {
        Map<String, XmiPersistentEntity> childrenMap = new LinkedHashMap<String, XmiPersistentEntity>();
        if (searchEntityElement(entity) != null) {
            XmiEntityElement entityElement = searchEntityElement(entity);
            for (XmiEntityElement element : entityElementList) {
                if (element.getXmiId().equals(entityElement.getXmiId())) {
                    continue;
                }
                if (element.isChildOf(entityElement)) {
                    XmiPersistentEntity child = entityIdMap.get(element.getXmiId());
                    childrenMap.put(child.getName(), child);
                }
            }
        } else if (searchAssociationEntityElement(entity) == null) {
            logger.error("Couldn't find children Entity Map for entity: " + entity.getName());
        }
        return childrenMap;
    }

    //debo retornar todas las entidades de una vez para poder instanciarlas dentro de otras
    public List<XmiPersistentEntity> getInitialEntityList() {
        List<XmiPersistentEntity> initialEntityList = new ArrayList<XmiPersistentEntity>();
        for (XmiPersistentEntity entity : entityIdMap.values()) {
            if (entity != null) {
                initialEntityList.add(entity);
            }
        }
        for (XmiPersistentEntity entity : associationEntityIdMap.values()) {
            if (entity != null) {
                initialEntityList.add(entity);
            }
        }
        return initialEntityList;
    }

    public List<XmiPersistentEntity> getManyToManyEntitiesList() {
        List<XmiPersistentEntity> manyToManyEntityList = new ArrayList<XmiPersistentEntity>();
        for (int i = 0; i < associationElementList.size(); i++) {
            if (associationElementList.get(i).isManyToManyAssociation()) {
                XmiPersistentEntity newEntity = associationElementList.get(i).getAssociationEntity();
                if (newEntity != null) {
                    logger.debug("Created many to many Association Entity " + newEntity.getName());
                    manyToManyEntityList.add(newEntity);
                } else {
                    logger.error("Could not create many to many Association Entity ");
                }
            }
        }
        return manyToManyEntityList;
    }

    public Map<String, XmiProperty> getPropertyMap(XmiPersistentEntity entity) {
        //logger.debug("Parsing simple properties for " + entity.getName());
        //System.out.println("entrando con "+entity.getName());
        Map<String, XmiProperty> propertyMap = getSimplePropertyMap(entity);
        //logger.debug("Parsing association properties for " + entity.getName());
        Map<String, XmiProperty> associationPropertyMap = getAssociationPropertyMap(entity);
        for (int i = 0; i < associationPropertyMap.size(); i++) {
            XmiProperty property = (XmiProperty) associationPropertyMap.values().toArray()[i];
            String propertyName = property.getName();
            if (propertyMap.get(propertyName) != null) {
                ((XmiProperty) propertyMap.get(propertyName)).combine(property);
            } else {
                propertyMap.put(propertyName, property);
            }
        }
//        logger.info("Parsing children properties for " + entity.getName());
//        Map<String, XmiProperty> childrenPropertyMap = getChildrenPropertyMap(entity);
//        propertyMap.putAll(childrenPropertyMap);
        return propertyMap;
    }

    public Map<String, XmiOperation> getOperationMap(XmiPersistentEntity entity) {
        Map<String, XmiOperation> operationMap = new LinkedHashMap<String, XmiOperation>();
        if (searchEntityElement(entity) != null) {
            XmiEntityElement entityElement = searchEntityElement(entity);
            operationMap = entityElement.parseOperationMap();
        } else if (searchAssociationEntityElement(entity) != null) {
            XmiAssociationEntityElement associationEntityElement = searchAssociationEntityElement(entity);
            operationMap = associationEntityElement.parseOperationMap();
        } else {
            logger.error("Couldn't find Xmi element for entity: " + entity.getName());
        }
        return operationMap;
    }

    public Map<String, XmiRow> getRowsMap(XmiPersistentEntity entity) {
        Map<String, XmiRow> rowsMap = new LinkedHashMap<String, XmiRow>();
        if (searchEntityElement(entity) != null) {
            XmiEntityElement entityElement = searchEntityElement(entity);
            rowsMap = entityElement.parseRowsMap(entity);
        } else if (searchAssociationEntityElement(entity) != null) {
            XmiAssociationEntityElement associationEntityElement = searchAssociationEntityElement(entity);
            rowsMap = associationEntityElement.parseRowsMap(entity);
        } else {
            logger.error("Couldn't find Xmi element for entity: " + entity.getName());
        }
        return rowsMap;
    }

    public boolean isEnumeration(XmiPersistentEntity entity) {
        if (searchEntityElement(entity) != null) {
            XmiEntityElement entityElement = searchEntityElement(entity);
            return isEnumerationElement(entityElement);
        }
        return false;
    }

    public boolean isEntity(XmiPersistentEntity entity) {
        if (searchEntityElement(entity) != null) {
            XmiEntityElement entityElement = searchEntityElement(entity);
            return isEntityElement(entityElement);
        } else if (searchAssociationEntityElement(entity) != null) {
            XmiAssociationEntityElement entityElement = searchAssociationEntityElement(entity);
            return isAssociationEntityElement(entityElement);
        }
        return false;
    }

    public boolean isManyToManyAssociationEntity(XmiPersistentEntity entity) {
        if (searchAssociationElement(entity) != null) {
            XmiAssociationElement associationElement = searchAssociationElement(entity);
            return isManyToManyAssociationElement(associationElement);
        }
        return false;
    }

    public XmiPersistentEntity getAssociationEdgeEntity(XmiPersistentEntity entity, boolean isFrom) {
        XmiPersistentEntity associationEdgeEntity;
        if (searchAssociationElement(entity) != null) {
            XmiAssociationElement association = searchAssociationElement(entity);
            if (isFrom) {
                associationEdgeEntity = entityIdMap.get(association.getFromElement().getType());
            } else {
                associationEdgeEntity = entityIdMap.get(association.getToElement().getType());
            }
        } else {
            associationEdgeEntity = null;
        }
        return associationEdgeEntity;
    }

    /**
     * @param entity the entity to validate
     * @return true if entity is valid; false otherwise
     */
    public boolean validateIntegrity(XmiPersistentEntity entity) {
        boolean valid = false;
        //los enums deben tener una propiedad clave primaria y una clave de caracter
        if (entity.isEnumeration()) {
            if (!entity.getValidPrimaryKeyProperty().isEmpty()
                && !entity.getValidCharacterKeyProperty().isEmpty()) {
                valid = true;
            }
        }//las entidades deben tener una clave primaria
        else if (entity.isEntity() || entity.isAssociationEntity()) {
            if (!entity.getValidPrimaryKeyProperty().isEmpty()) {
                valid = true;
            }
        } else if (entity.isManyToManyAssociationEntity()) {
            XmiPersistentEntity fromEntity = getAssociationEdgeEntity(entity, true);
            XmiPersistentEntity toEntity = getAssociationEdgeEntity(entity, false);
            if (fromEntity != null && toEntity != null) {
                valid = validateIntegrity(fromEntity) && validateIntegrity(toEntity);
            }
        }
        return valid;
    }

    // <editor-fold defaultstate="collapsed" desc="XmiNodeFilter (Nested Class) ">
    private static class XmiNodeFilter implements NodeFilter {

        public short acceptNode(Node n) {
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                if (e.getAttributeNode(XmiTags.XMI_ID_TAG) != null
                    || e.getElementsByTagName(XmiTags.OWNED_MEMBER_TAG).getLength() > 0) {
                    return NodeFilter.FILTER_ACCEPT;
                }
            }
            return NodeFilter.FILTER_REJECT;
        }

    }
    // </editor-fold>
}
