/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.types;

/**
 *
 * @author cmedina
 */
import adalid.xmi.elements.XmiDomTree;
import adalid.xmi.util.Typenames;
import adalid.xmi.util.UmlTags;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class XmiPersistentEntity {

    private static Logger logger = Logger.getLogger(XmiPersistentEntity.class);

    private Map<String, XmiProperty> propertiesMap = new LinkedHashMap<>();

    private List<XmiProperty> propertiesList = new ArrayList<>();

    private List<XmiOperation> operationsList = new ArrayList<>();

    private Map<String, XmiOperation> operationsMap = new LinkedHashMap<>();

    private Map<String, XmiRow> rowsMap = new LinkedHashMap<>();

    private Map<String, XmiPersistentEntity> childrenMap = new LinkedHashMap<>();

    private List<XmiRow> rowsList = new ArrayList<>();

    //Entity elements from the model
    private String name = "";

    private boolean enumeration = false;

    private boolean entity = false;

    private boolean associationEntity = false;

    private boolean manyToManyAssociationEntity = false;

    private boolean ignored = false;

    private String type = Typenames.PersistentEntity;

    private String associationId = "";

    private XmiPersistentEntity baseEntity = null;

    private String entityDataType = "";

    private String inheritanceStrategy = "";

    private String discriminatorValue = "";

    private boolean abstractEntity = false;

    private String packageName = "";

    private String fullName = "";

    //Entity Attributes
    private String alias = "";

    private String defaultLabel = "";

    private String defaultShortLabel = "";

    private String defaultCollectionLabel = "";

    private String defaultDescription = "";

    private String defaultShortDescription = "";

    private String defaultTooltip = "";

    private boolean selectEnabled = true;

    private boolean insertEnabled = true;

    private boolean updateEnabled = true;

    private boolean deleteEnabled = true;

    private boolean reportEnabled = true;

    private boolean exportEnabled = true;

    private boolean tableViewEnabled = true;

    private boolean detailViewEnabled = true;

    private boolean treeViewEnabled = true;

    private boolean consoleViewEnabled = true;

    private String resourceType = "";

    private String propertiesPrefix = "";

    private String propertiesSuffix = "";

    private String primaryKeyProperty = "";

    private String numericKeyProperty = "";

    private String characterKeyProperty = "";

    private String nameProperty = "";

    private String descriptionProperty = "";

    private String ownerProperty = "";

    private String parentProperty = "";

    private String versionProperty = "";

    private String businessKeyProperty = "";

    private String businessKeyType = "UNSPECIFIED";

    private int selectRowsLimit = 500;

    private int reportRowsLimit = 1000;

    private boolean triggerBeforeValueEnabled = false;

    private boolean triggerAfterValueEnabled = false;

    private boolean triggerBeforeCheckEnabled = false;

    private boolean triggerAfterCheckEnabled = false;

    private String discriminatorProperty = "";

    private String nextIdPropertyName = "ID";

    private int nextIdCounter = 1;

    // valid Strings for velocity templates
    private boolean customizedAttributes = false;

    private boolean customizedProperties = false;

    private boolean entityClass = false;

    private String entityClassString = "";

    private String entitySelectOperationString = "";

    private String entityReportOperationString = "";

    private String entityInsertOperationString = "";

    private String entityDeleteOperationString = "";

    private String entityUpdateOperationString = "";

    private String entityExportOperationString = "";

    private String entityTableViewString = "";

    private String entityDetailViewString = "";

    private String entityTreeViewString = "";

    private String entityConsoleViewString = "";

    private boolean entityTriggers = false;

    private String entityTriggersString = "";

    private String resourceTypeString = "";

    private String businessKeyTypeString = "";

    private String selectRowsLimitString = "";

    private String reportRowsLimitString = "";

    private String inheritanceMappingString = "";

    private String validPrimaryKeyProperty = "";

    private String validNumericKeyProperty = "";

    private String validCharacterKeyProperty = "";

    private String validNameProperty = "";

    private String validDescriptionProperty = "";

    private String validOwnerProperty = "";

    private String validParentProperty = "";

    private String validVersionProperty = "";

    private String validDiscriminatorProperty = "";

    private String validBusinessKeyProperty = "";

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public XmiPersistentEntity() {
    }
    // </editor-fold>

    public void parseInheritance(XmiDomTree xmiTree) {
//        XmiPersistentEntity _baseEntity = xmiTree.getBaseEntity(this);
//        //0. si no tengo clase padre tambien puedo tener discriminador pero desde taggedValue
//        //1. verificar si tengo padre
//        if (_baseEntity != null) {
//            this.setBaseEntity(_baseEntity);
//            this.processInheritedOperations(_baseEntity);
//            //1.1 Verificar discriminador (solo para las entidades concretas)
//            if (!this.isAbstractEntity()) {
//                if (this.discriminatorValue.isEmpty() && _baseEntity.getInheritanceStrategy().equals(UmlTags.SINGLE_TABLE)) {
//                    logger.warn("Discriminator Value for non abstract entity " + this.getName() + " is Empty when base entity is SINGLE_TABLE");
//                } else if (!this.discriminatorValue.isEmpty()) {
//                    logger.info("Generalization created " + _baseEntity.getName() + "->" + this.getName() + " discriminator value =" + getDiscriminatorValue());
//                }
//            }
//        }
//        //2. verificar si tengo hijos
//        if (!xmiTree.getChildrenEntityMap(this).isEmpty()) {
//            //2.1 si tengo hijos siempre tendre un inheritanceStrategy por defecto es single_table
//            if (this.getInheritanceStrategy().isEmpty()) {
//                logger.warn("Inheritance Mapping Strategy is Empty for " + this.getName() + ", SINGLE_TABLE is assumed ");
//                this.setInheritanceStrategy(UmlTags.SINGLE_TABLE);
//            }//si es single_table debo validar discriminator property
//            else if (this.getInheritanceStrategy().equals(UmlTags.SINGLE_TABLE)) {
//                if (this.getDiscriminatorProperty().isEmpty()) {
//                    logger.warn("Discriminator Property is Empty for " + this.getName() + ", it will be provided by Persistence Provider ");
//                } else if (this.getValidDiscriminatorProperty().isEmpty()) {
//                    logger.warn("Discriminator Property is not valid type for " + this.getName());
//                }
//            }//si es cualquier otra inheritanceStrategy ignora el discriminator pero advierte
//            else if (isValidInheritanceMapping()) {
//                if (!this.discriminatorProperty.isEmpty()
//                        && this.getValidDiscriminatorProperty().isEmpty()) {
//                    logger.warn("Discriminator Property is not valid type for " + this.getName());
//                }
//            }// si no es inheritanceStrategy valida debo mostrar error y colocar single_table
//            else {
//                logger.error("Invalid Inheritance Mapping Strategy for " + this.getName() + ": " + this.getInheritanceStrategy());
//                this.setInheritanceStrategy(UmlTags.SINGLE_TABLE);
//            }
//        }
    }

    private void processInheritedOperations(XmiPersistentEntity _baseEntity) {
//        for(int i=0;i<this.getOperationsMap().size();i++){
//                XmiOperation operation=(XmiOperation)this.getOperationsMap().values().toArray()[i];
//                for(int j=0;j<_baseEntity.getOperationsMap().size();j++){
//                    XmiOperation baseOperation=(XmiOperation)_baseEntity.getOperationsMap().values().toArray()[j];
//                    if(operation.getName().equalsIgnoreCase(baseOperation.getName())){
//                        if(!_baseEntity.getPackageName().isEmpty())
//                            operation.setBaseOperationName(_baseEntity.getPackageName()+"."+_baseEntity.getName()+"."+baseOperation.getOperationNameString());
//                        else
//                            operation.setBaseOperationName(_baseEntity.getName()+"."+baseOperation.getOperationNameString());
//                    }
//                }
//            }
    }

    public void parseProperties(XmiDomTree xmiTree) {
//        Map<String, XmiProperty> _propertiesMap = xmiTree.getPropertyMap(this);
//        for (int i = 0; i < _propertiesMap.size(); i++) {
//            XmiProperty property = (XmiProperty) _propertiesMap.values().toArray()[i];
//            String propertyName = (String) _propertiesMap.keySet().toArray()[i];
//            this.getPropertiesMap().put(propertyName, property);
//            logger.info("property created " + this.getName() + "." + propertyName + " : " + property.getPropertyType());
//        }
    }

    public void parseOperations(XmiDomTree xmiTree) {
//        Map<String, XmiOperation> _operationsMap = xmiTree.getOperationMap(this);
//        for (int i = 0; i < _operationsMap.size(); i++) {
//            XmiOperation operation = (XmiOperation) _operationsMap.values().toArray()[i];
//            //property.setDeclaringArtifact(this);
//            String operationName = (String) _operationsMap.keySet().toArray()[i];
//            this.getOperationsMap().put(operationName, operation);
//            logger.info("Operation created " + this.getName() + "." + operationName + " : " + operation);
//            for (int j = 0; j < operation.getParametersMap().size(); j++) {
//                logger.info("Parameter created " + this.getName() + "." + operationName
//                        + " -> " + ((XmiParameter) operation.getParametersMap().values().toArray()[j]).getName() + " : "
//                        + ((XmiParameter) operation.getParametersMap().values().toArray()[j]).getParameterType());
//                operation.getParametersList().add((XmiParameter) operation.getParametersMap().values().toArray()[j]);
//            }
//        }
    }

    public void parseRows(XmiDomTree xmiDomTree) {
//        Map<String, XmiRow> _rowsMap = xmiDomTree.getRowsMap(this);
//        for (int i = 0; i < _rowsMap.size(); i++) {
//            XmiRow row = (XmiRow) _rowsMap.values().toArray()[i];
//            String rowName = (String) _rowsMap.keySet().toArray()[i];
//            this.getRowsMap().put(rowName, row);
//            logger.info("Row created " + this.getName() + "." + rowName);
//            for (int j = 0; j < row.getRowFieldsMap().size(); j++) {
//                logger.info("RowField " + this.getName() + "." + rowName
//                        + "." + row.getRowFieldsMap().keySet().toArray()[j] + " = "
//                        + ((XmiRowField) row.getRowFieldsMap().values().toArray()[j]).getValue());
//            }
//        }
//
//        for (XmiRow row : this.getRowsMap().values()) {
//            if (row != null) {
//                getRowsList().add(row);
//            }
//        }
    }

    /**
     * @return the propertiesMap
     */
    public Map<String, XmiProperty> getPropertiesMap() {
        return propertiesMap;
    }

    /**
     * @param propertiesMap the propertiesMap to set
     */
    public void setPropertiesMap(Map<String, XmiProperty> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    /**
     * @return the propertiesList
     */
    public List<XmiProperty> getPropertiesList() {
        if (propertiesList.isEmpty()) {
            for (XmiProperty property : this.getPropertiesMap().values()) {
                if (property != null && !property.isIgnored()) {
                    propertiesList.add(property);
                }
            }
        }
        return propertiesList;
    }

    /**
     * @param propertiesList the propertiesList to set
     */
    public void setPropertiesList(List<XmiProperty> propertiesList) {
        this.propertiesList = propertiesList;
    }

    /**
     * @return the operationsList
     */
    public List<XmiOperation> getOperationsList() {
        if (operationsList.isEmpty()) {
            for (XmiOperation operation : this.getOperationsMap().values()) {
                if (operation != null) {
                    operationsList.add(operation);
                }
            }
        }
        return operationsList;
    }

    /**
     * @param operationsList the operationsList to set
     */
    public void setOperationsList(List<XmiOperation> operationsList) {
        this.operationsList = operationsList;
    }

    /**
     * @return the operationsMap
     */
    public Map<String, XmiOperation> getOperationsMap() {
        return operationsMap;
    }

    /**
     * @param operationsMap the operationsMap to set
     */
    public void setOperationsMap(Map<String, XmiOperation> operationsMap) {
        this.operationsMap = operationsMap;
    }

    /**
     * @return the rowsMap
     */
    public Map<String, XmiRow> getRowsMap() {
        return rowsMap;
    }

    /**
     * @param rowsMap the rowsMap to set
     */
    public void setRowsMap(Map<String, XmiRow> rowsMap) {
        this.rowsMap = rowsMap;
    }

    /**
     * @return the rowsList
     */
    public List<XmiRow> getRowsList() {
        if (rowsList.isEmpty()) {
            for (XmiRow row : rowsMap.values()) {
                if (row != null) {
                    rowsList.add(row);
                }
            }
        }
        return rowsList;
    }

    /**
     * @param rowsList the rowsList to set
     */
    public void setRowsList(List<XmiRow> rowsList) {
        this.rowsList = rowsList;
    }

    /**
     * @return the childrenMap
     */
    public Map<String, XmiPersistentEntity> getChildrenMap() {
        return childrenMap;
    }

    /**
     * @param childrenMap the childrenMap to set
     */
    public void setChildrenMap(Map<String, XmiPersistentEntity> childrenMap) {
        this.childrenMap = childrenMap;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the enumeration
     */
    public boolean isEnumeration() {
        return enumeration;
    }

    /**
     * @param enumeration the enumeration to set
     */
    public void setEnumeration(boolean enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * @return the entity
     */
    public boolean isEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(boolean entity) {
        this.entity = entity;
    }

    /**
     * @return the associationEntity
     */
    public boolean isAssociationEntity() {
        return associationEntity;
    }

    /**
     * @param associationEntity the associationEntity to set
     */
    public void setAssociationEntity(boolean associationEntity) {
        this.associationEntity = associationEntity;
    }

    /**
     * @return the manyToManyAssociationEntity
     */
    public boolean isManyToManyAssociationEntity() {
        return manyToManyAssociationEntity;
    }

    /**
     * @param manyToManyAssociationEntity the manyToManyAssociationEntity to set
     */
    public void setManyToManyAssociationEntity(boolean manyToManyAssociationEntity) {
        this.manyToManyAssociationEntity = manyToManyAssociationEntity;
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
     * @return the type of the base entity if it exists, the default type Otherwise
     */
    public String getType() {
        if (baseEntity != null) {
            return baseEntity.getFullName();
        }
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the associationId
     */
    public String getAssociationId() {
        return associationId;
    }

    /**
     * @param associationId the associationId to set
     */
    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    /**
     * @return the baseEntity
     */
    public XmiPersistentEntity getBaseEntity() {
        return baseEntity;
    }

    /**
     * @param baseEntity the baseEntity to set
     */
    public void setBaseEntity(XmiPersistentEntity baseEntity) {
        this.baseEntity = baseEntity;
    }

    /**
     * @return the entityDataType
     */
    public String getEntityDataType() {
        return entityDataType;
    }

    /**
     * @param entityDataType the entityDataType to set
     */
    public void setEntityDataType(String entityDataType) {
        this.entityDataType = entityDataType;
    }

    /**
     * @return the inheritanceStrategy
     */
    public String getInheritanceStrategy() {
        return inheritanceStrategy;
    }

    /**
     * @param inheritanceStrategy the inheritanceStrategy to set
     */
    public void setInheritanceStrategy(String inheritanceStrategy) {
        this.inheritanceStrategy = inheritanceStrategy;
    }

    /**
     * @return the discriminatorValue
     */
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

    /**
     * @param discriminatorValue the discriminatorValue to set
     */
    public void setDiscriminatorValue(String discriminatorValue) {
        this.discriminatorValue = discriminatorValue;
    }

    /**
     * @return the abstractEntity
     */
    public boolean isAbstractEntity() {
        return abstractEntity;
    }

    /**
     * @param abstractEntity the abstractEntity to set
     */
    public void setAbstractEntity(boolean abstractEntity) {
        this.abstractEntity = abstractEntity;
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
     * @return the fullName
     */
    public String getFullName() {
        if (!packageName.isEmpty()) {
            fullName = packageName + '.' + name;
        } else {
            fullName = name;
        }
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     * @return the defaultDescription
     */
    public String getDefaultDescription() {
        return defaultDescription;
    }

    /**
     * @param defaultDescription the defaultDescription to set
     */
    public void setDefaultDescription(String defaultDescription) {
        this.defaultDescription = StringEscapeUtils.unescapeJava(defaultDescription);
    }

    /**
     * @return the defaultShortDescription
     */
    public String getDefaultShortDescription() {
        return defaultShortDescription;
    }

    /**
     * @param defaultShortDescription the defaultShortDescription to set
     */
    public void setDefaultShortDescription(String defaultShortDescription) {
        this.defaultShortDescription = StringEscapeUtils.unescapeJava(defaultShortDescription);
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
     * @return the selectEnabled
     */
    public boolean isSelectEnabled() {
        return selectEnabled;
    }

    /**
     * @param selectEnabled the selectEnabled to set
     */
    public void setSelectEnabled(boolean selectEnabled) {
        this.selectEnabled = selectEnabled;
    }

    /**
     * @return the insertEnabled
     */
    public boolean isInsertEnabled() {
        return insertEnabled;
    }

    /**
     * @param insertEnabled the insertEnabled to set
     */
    public void setInsertEnabled(boolean insertEnabled) {
        this.insertEnabled = insertEnabled;
    }

    /**
     * @return the updateEnabled
     */
    public boolean isUpdateEnabled() {
        return updateEnabled;
    }

    /**
     * @param updateEnabled the updateEnabled to set
     */
    public void setUpdateEnabled(boolean updateEnabled) {
        this.updateEnabled = updateEnabled;
    }

    /**
     * @return the deleteEnabled
     */
    public boolean isDeleteEnabled() {
        return deleteEnabled;
    }

    /**
     * @param deleteEnabled the deleteEnabled to set
     */
    public void setDeleteEnabled(boolean deleteEnabled) {
        this.deleteEnabled = deleteEnabled;
    }

    /**
     * @return the reportEnabled
     */
    public boolean isReportEnabled() {
        return reportEnabled;
    }

    /**
     * @param reportEnabled the reportEnabled to set
     */
    public void setReportEnabled(boolean reportEnabled) {
        this.reportEnabled = reportEnabled;
    }

    /**
     * @return the exportEnabled
     */
    public boolean isExportEnabled() {
        return exportEnabled;
    }

    /**
     * @param exportEnabled the exportEnabled to set
     */
    public void setExportEnabled(boolean exportEnabled) {
        this.exportEnabled = exportEnabled;
    }

    /**
     * @return the tableViewEnabled
     */
    public boolean isTableViewEnabled() {
        return tableViewEnabled;
    }

    /**
     * @param tableViewEnabled the tableViewEnabled to set
     */
    public void setTableViewEnabled(boolean tableViewEnabled) {
        this.tableViewEnabled = tableViewEnabled;
    }

    /**
     * @return the detailViewEnabled
     */
    public boolean isDetailViewEnabled() {
        return detailViewEnabled;
    }

    /**
     * @param detailViewEnabled the detailViewEnabled to set
     */
    public void setDetailViewEnabled(boolean detailViewEnabled) {
        this.detailViewEnabled = detailViewEnabled;
    }

    /**
     * @return the treeViewEnabled
     */
    public boolean isTreeViewEnabled() {
        return treeViewEnabled;
    }

    /**
     * @param treeViewEnabled the treeViewEnabled to set
     */
    public void setTreeViewEnabled(boolean treeViewEnabled) {
        this.treeViewEnabled = treeViewEnabled;
    }

    /**
     * @return the consoleViewEnabled
     */
    public boolean isConsoleViewEnabled() {
        return consoleViewEnabled;
    }

    /**
     * @param consoleViewEnabled the consoleViewEnabled to set
     */
    public void setConsoleViewEnabled(boolean consoleViewEnabled) {
        this.consoleViewEnabled = consoleViewEnabled;
    }

    /**
     * @return the resourceType
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * @param resourceType the resourceType to set
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * @return the propertiesPrefix
     */
    public String getPropertiesPrefix() {
        return propertiesPrefix;
    }

    /**
     * @param propertiesPrefix the propertiesPrefix to set
     */
    public void setPropertiesPrefix(String propertiesPrefix) {
        this.propertiesPrefix = propertiesPrefix;
    }

    /**
     * @return the propertiesSuffix
     */
    public String getPropertiesSuffix() {
        return propertiesSuffix;
    }

    /**
     * @param propertiesSuffix the propertiesSuffix to set
     */
    public void setPropertiesSuffix(String propertiesSuffix) {
        this.propertiesSuffix = propertiesSuffix;
    }

    /**
     * @return the primaryKeyProperty
     */
    public String getPrimaryKeyProperty() {
        return primaryKeyProperty;
    }

    /**
     * @param primaryKeyProperty the primaryKeyProperty to set
     */
    public void setPrimaryKeyProperty(String primaryKeyProperty) {
        this.primaryKeyProperty = primaryKeyProperty;
    }

    /**
     * @return the numericKeyProperty
     */
    public String getNumericKeyProperty() {
        return numericKeyProperty;
    }

    /**
     * @param numericKeyProperty the numericKeyProperty to set
     */
    public void setNumericKeyProperty(String numericKeyProperty) {
        this.numericKeyProperty = numericKeyProperty;
    }

    /**
     * @return the characterKeyProperty
     */
    public String getCharacterKeyProperty() {
        return characterKeyProperty;
    }

    /**
     * @param characterKeyProperty the characterKeyProperty to set
     */
    public void setCharacterKeyProperty(String characterKeyProperty) {
        this.characterKeyProperty = characterKeyProperty;
    }

    /**
     * @return the businessKeyProperty
     */
    public String getBusinessKeyProperty() {
        return businessKeyProperty;
    }

    /**
     * @param businessKeyProperty the businessKeyProperty to set
     */
    public void setBusinessKeyProperty(String businessKeyProperty) {
        this.businessKeyProperty = businessKeyProperty;
    }

    /**
     * @return the nameProperty
     */
    public String getNameProperty() {
        return nameProperty;
    }

    /**
     * @param nameProperty the nameProperty to set
     */
    public void setNameProperty(String nameProperty) {
        this.nameProperty = nameProperty;
    }

    /**
     * @return the descriptionProperty
     */
    public String getDescriptionProperty() {
        return descriptionProperty;
    }

    /**
     * @param descriptionProperty the descriptionProperty to set
     */
    public void setDescriptionProperty(String descriptionProperty) {
        this.descriptionProperty = descriptionProperty;
    }

    /**
     * @return the ownerProperty
     */
    public String getOwnerProperty() {
        return ownerProperty;
    }

    /**
     * @param ownerProperty the ownerProperty to set
     */
    public void setOwnerProperty(String ownerProperty) {
        this.ownerProperty = ownerProperty;
    }

    /**
     * @return the parentProperty
     */
    public String getParentProperty() {
        return parentProperty;
    }

    /**
     * @param parentProperty the parentProperty to set
     */
    public void setParentProperty(String parentProperty) {
        this.parentProperty = parentProperty;
    }

    /**
     * @return the versionProperty
     */
    public String getVersionProperty() {
        return versionProperty;
    }

    /**
     * @param versionProperty the versionProperty to set
     */
    public void setVersionProperty(String versionProperty) {
        this.versionProperty = versionProperty;
    }

    /**
     * @return the businessKeyType
     */
    public String getBusinessKeyType() {
        return businessKeyType;
    }

    /**
     * @param businessKeyType the businessKeyType to set
     */
    public void setBusinessKeyType(String businessKeyType) {
        this.businessKeyType = businessKeyType;
    }

    /**
     * @return the selectRowsLimit
     */
    public int getSelectRowsLimit() {
        return selectRowsLimit;
    }

    /**
     * @param selectRowsLimit the selectRowsLimit to set
     */
    public void setSelectRowsLimit(int selectRowsLimit) {
        this.selectRowsLimit = selectRowsLimit;
    }

    /**
     * @return the reportRowsLimit
     */
    public int getReportRowsLimit() {
        return reportRowsLimit;
    }

    /**
     * @param reportRowsLimit the reportRowsLimit to set
     */
    public void setReportRowsLimit(int reportRowsLimit) {
        this.reportRowsLimit = reportRowsLimit;
    }

    /**
     * @return the triggerBeforeValueEnabled
     */
    public boolean getTriggerBeforeValueEnabled() {
        return triggerBeforeValueEnabled;
    }

    /**
     * @param triggerBeforeValueEnabled the triggerBeforeValueEnabled to set
     */
    public void setTriggerBeforeValueEnabled(boolean triggerBeforeValueEnabled) {
        this.triggerBeforeValueEnabled = triggerBeforeValueEnabled;
    }

    /**
     * @return the triggerAfterValueEnabled
     */
    public boolean getTriggerAfterValueEnabled() {
        return triggerAfterValueEnabled;
    }

    /**
     * @param triggerAfterValueEnabled the triggerAfterValueEnabled to set
     */
    public void setTriggerAfterValueEnabled(boolean triggerAfterValueEnabled) {
        this.triggerAfterValueEnabled = triggerAfterValueEnabled;
    }

    /**
     * @return the triggerBeforeCheckEnabled
     */
    public boolean getTriggerBeforeCheckEnabled() {
        return triggerBeforeCheckEnabled;
    }

    /**
     * @param triggerBeforeCheckEnabled the triggerBeforeCheckEnabled to set
     */
    public void setTriggerBeforeCheckEnabled(boolean triggerBeforeCheckEnabled) {
        this.triggerBeforeCheckEnabled = triggerBeforeCheckEnabled;
    }

    /**
     * @return the triggerAfterCheckEnabled
     */
    public boolean getTriggerAfterCheckEnabled() {
        return triggerAfterCheckEnabled;
    }

    /**
     * @param triggerAfterCheckEnabled the triggerAfterCheckEnabled to set
     */
    public void setTriggerAfterCheckEnabled(boolean triggerAfterCheckEnabled) {
        this.triggerAfterCheckEnabled = triggerAfterCheckEnabled;
    }

    /**
     * @return the discriminatorProperty
     */
    public String getDiscriminatorProperty() {
        return discriminatorProperty;
    }

    /**
     * @param discriminatorProperty the discriminatorProperty to set
     */
    public void setDiscriminatorProperty(String discriminatorProperty) {
        this.discriminatorProperty = discriminatorProperty;
    }

    public String getNextIdPropertyName() {
        if (propertiesMap.containsKey(nextIdPropertyName)) {
            nextIdPropertyName = nextIdPropertyName + nextIdCounter;
            nextIdCounter++;
        }
        return nextIdPropertyName;
    }

    public boolean isValidInheritanceMapping() {
        return (inheritanceStrategy.equals(UmlTags.SINGLE_TABLE)
            || inheritanceStrategy.equals(UmlTags.JOINED)
            || inheritanceStrategy.equals(UmlTags.TABLE_PER_CLASS));
    }

    public boolean isValidBusinessKeyType() {
        return (businessKeyType.equals(UmlTags.NUMERIC)
            || businessKeyType.equals(UmlTags.CHARACTER));
    }

    public boolean isValidResourceType() {
        return (resourceType.equals(UmlTags.OPERATION)
            || resourceType.equals(UmlTags.CONFIGURATION));
    }

    public String getInheritanceMappingString() {
        if (!inheritanceStrategy.isEmpty() && isValidInheritanceMapping()) {
            inheritanceMappingString = "strategy = InheritanceMappingStrategy." + getInheritanceStrategy();
        }
//        if (!getValidDiscriminatorProperty().isEmpty()) {
//            inheritanceMappingString += ", discriminatedBy = \"" + validDiscriminatorProperty + "\"";
//        }
        return inheritanceMappingString;
    }

    public String getSelectRowsLimitString() {
        return selectRowsLimitString;
    }

    public String getReportRowsLimitString() {
        return reportRowsLimitString;
    }

    public String getBusinessKeyTypeString() {
        if (!businessKeyType.isEmpty()
            && isValidBusinessKeyType()) {
            businessKeyTypeString = "BusinessKeyType." + businessKeyType + "_PROPERTY";
        }
        return businessKeyTypeString;
    }

    public String getResourceTypeString() {
        if (!resourceType.isEmpty()
            && isValidResourceType()) {
            resourceTypeString = "ResourceType." + resourceType;
        }
        return resourceTypeString;
    }

    /**
     * @return the validPrimaryKeyProperty
     */
    public String getValidPrimaryKeyProperty() {
        if (!primaryKeyProperty.isEmpty()) {
            if (getPropertiesMap().get(primaryKeyProperty) != null) {
                XmiProperty property = getPropertiesMap().get(primaryKeyProperty);
                if (property.isValidNumericKey()) {
                    validPrimaryKeyProperty = primaryKeyProperty;
                }
            }
        }
        //System.out.println("Valid Primary key for "+this.getName()+"="+validPrimaryKeyProperty);
        return validPrimaryKeyProperty;
    }

    public String getValidNumericKeyProperty() {
        if (!numericKeyProperty.isEmpty()) {
            if (getPropertiesMap().get(numericKeyProperty) != null) {
                XmiProperty property = getPropertiesMap().get(numericKeyProperty);
                if (property.isValidNumericKey()) {
                    validNumericKeyProperty = numericKeyProperty;
                }
            }
        }
        //System.out.println("Valid Numeric key for "+this.getName()+"="+numericKeyProperty);
        return validNumericKeyProperty;
    }

    public String getValidCharacterKeyProperty() {
        if (!characterKeyProperty.isEmpty()) {
            if (getPropertiesMap().get(characterKeyProperty) != null) {
                XmiProperty property = getPropertiesMap().get(characterKeyProperty);
                if (property.isValidCharacterKey()) {
                    validCharacterKeyProperty = characterKeyProperty;
                }
            }
        }
        //System.out.println("Valid Character Key for "+this.getName()+"="+validCharacterKeyProperty+"=?"+characterKeyProperty);
        return validCharacterKeyProperty;
    }

    public String getValidNameProperty() {
        if (!nameProperty.isEmpty()) {
            if (getPropertiesMap().get(nameProperty) != null) {
                validNameProperty = nameProperty;
            }
        } else {
            //TODO: Eliminar la generalizacion de que la palabra nombre aparezca
            String nameKeyPropertyName = "nombre" + this.getName();
            if (getPropertiesMap().get(nameKeyPropertyName) != null) {
                XmiProperty property = getPropertiesMap().get(nameKeyPropertyName);
                if (property.getPropertyType().equals(Typenames.StringProperty)) {
                    validNameProperty = nameKeyPropertyName;
                }
            }
        }
        return validNameProperty;
    }

    public String getValidDescriptionProperty() {
        if (!descriptionProperty.isEmpty()) {
            if (getPropertiesMap().get(descriptionProperty) != null) {
                validDescriptionProperty = descriptionProperty;
            }
        } else {
            //TODO: Eliminar la generalizacion de que la palabra descripcion aparezca
            String descriptionKeyPropertyName = "descripcion" + this.getName();
            if (getPropertiesMap().get(descriptionKeyPropertyName) != null) {
                XmiProperty property = getPropertiesMap().get(descriptionKeyPropertyName);
                if (property.getPropertyType().equals(Typenames.StringProperty)) {
                    validDescriptionProperty = descriptionKeyPropertyName;
                }
            }
        }
        return validDescriptionProperty;
    }

    public String getValidOwnerProperty() {
        if (!ownerProperty.isEmpty()) {
            if (getPropertiesMap().get(ownerProperty) != null) {
                validOwnerProperty = ownerProperty;
            }
        }
        return validOwnerProperty;
    }

    public String getValidParentProperty() {
        if (!parentProperty.isEmpty()) {
            if (getPropertiesMap().get(parentProperty) != null) {
                validParentProperty = parentProperty;
            }
        }
        return validParentProperty;
    }

    public String getValidVersionProperty() {
        if (!versionProperty.isEmpty()) {
            if (getPropertiesMap().get(versionProperty) != null) {
                validVersionProperty = versionProperty;
            }
        }
        return validVersionProperty;
    }

    public String getValidDiscriminatorProperty() {
        if (!discriminatorProperty.isEmpty()) {
            if (getPropertiesMap().get(discriminatorProperty) != null) {
                XmiProperty property = getPropertiesMap().get(discriminatorProperty);
                if (property.isValidDiscriminator()) {
                    validDiscriminatorProperty = getDiscriminatorProperty();
                }
            }
        }
        return validDiscriminatorProperty;
    }

    public String getValidBusinessKeyProperty() {
        if (!businessKeyProperty.isEmpty()) {
            if (getPropertiesMap().get(businessKeyProperty) != null) {
                validBusinessKeyProperty = businessKeyProperty;
            }
        } else {
            //TODO: Eliminar la generalizacion de que la palabra codigo aparezca
            String businessKeyPropertyName = "codigo" + this.getName();
            if (getPropertiesMap().get(businessKeyPropertyName) != null) {
                XmiProperty property = getPropertiesMap().get(businessKeyPropertyName);
                if (property.getPropertyType().equals(Typenames.StringProperty)) {
                    validBusinessKeyProperty = businessKeyPropertyName;
                }
            }
        }
        return validBusinessKeyProperty;
    }

    public boolean getCustomizedAttributes() {
        customizedAttributes = (!alias.isEmpty()
            || !defaultLabel.isEmpty()
            || !defaultShortLabel.isEmpty()
            || !defaultDescription.isEmpty()
            || !defaultShortDescription.isEmpty()
            || !defaultTooltip.isEmpty());
        return customizedAttributes;
    }

    public boolean getCustomizedProperties() {
        for (XmiProperty property : propertiesList) {
            if (property.isCustomizedProperty()) {
                customizedProperties = true;
                break;
            }
        }
        return customizedProperties;
    }

    //TODO: Agregar independent, gender y startwith
    public boolean getEntityClass() {
        entityClass = ((this.isValidResourceType()
            && !this.getResourceTypeString().isEmpty()));
        return entityClass;
    }

    //TODO: Agregar independent, gender y startwith
    public String getEntityClassString() {
        if ((this.isValidResourceType() && !this.getResourceTypeString().isEmpty())) {
            entityClassString = "resourceType = " + this.getResourceTypeString();
//        if(!propertiesPrefix.isEmpty()){
//            if(!entityClassString.isEmpty()){
//                entityClassString=entityClassString+",propertiesPrefix=\""+propertiesPrefix+"\"";
//            }else{
//                entityClassString="propertiesPrefix=\""+propertiesPrefix+"\"";
//            }
//        }
//        if(!propertiesSuffix.isEmpty()){
//            if(!entityClassString.isEmpty()){
//                entityClassString=entityClassString+",propertiesSuffix=\""+propertiesSuffix+"\"";
//            }else{
//                entityClassString="propertiesSuffix=\""+propertiesSuffix+"\"";
//            }
//        }
        }
        return entityClassString;
    }

    //TODO: agregar restricted
    public String getEntitySelectOperationString() {
        if (!selectEnabled) {
            entitySelectOperationString = "enabled = Kleenean.FALSE";
        } else {
            if (selectRowsLimit != UmlTags.DEFAULT_SELECT_ROWS_LIMIT) {
                selectRowsLimitString = Integer.toString(selectRowsLimit);
                entitySelectOperationString = "rowsLimit = " + selectRowsLimitString;
            }
        }

        return entitySelectOperationString;
    }

    //TODO: agregar restricted
    public String getEntityReportOperationString() {
        if (!reportEnabled) {
            entityReportOperationString = "enabled = Kleenean.FALSE";
        } else {
            if (reportRowsLimit != UmlTags.DEFAULT_REPORT_ROWS_LIMIT) {
                reportRowsLimitString = Integer.toString(reportRowsLimit);
                entityReportOperationString = "rowsLimit = " + reportRowsLimitString;
            }
        }
        return entityReportOperationString;
    }

    //TODO: agregar restricted
    public String getEntityInsertOperationString() {
        if (!insertEnabled) {
            entityInsertOperationString = "enabled = Kleenean.FALSE";
        }
        return entityInsertOperationString;
    }

    //TODO: agregar restricted
    public String getEntityUpdateOperationString() {
        if (!updateEnabled) {
            entityUpdateOperationString = "enabled = Kleenean.FALSE";
        }
        return entityUpdateOperationString;
    }

    //TODO: agregar restricted
    public String getEntityDeleteOperationString() {
        if (!deleteEnabled) {
            entityDeleteOperationString = "enabled = Kleenean.FALSE";
        }
        return entityDeleteOperationString;
    }

    //TODO: agregar rowsLimit
    public String getEntityExportOperationString() {
        if (!exportEnabled) {
            entityExportOperationString = "enabled = Kleenean.FALSE";
        }
        return entityExportOperationString;
    }

    //TODO: agregar inserts updates y deletes
    public String getEntityTableViewString() {
        if (!tableViewEnabled) {
            entityTableViewString = "enabled = Kleenean.FALSE";
        }
        return entityTableViewString;
    }

    public String getEntityDetailViewString() {
        if (!detailViewEnabled) {
            entityDetailViewString = "enabled = Kleenean.FALSE";
        }
        return entityDetailViewString;
    }

    public String getEntityTreeViewString() {
        if (!treeViewEnabled) {
            entityTreeViewString = "enabled = Kleenean.FALSE";
        }
        return entityTreeViewString;
    }

    public String getEntityConsoleViewString() {
        if (!consoleViewEnabled) {
            entityConsoleViewString = "enabled = Kleenean.FALSE";
        }
        return entityConsoleViewString;
    }

    public boolean getEntityTriggers() {
        entityTriggers = this.triggerAfterCheckEnabled
            || this.triggerAfterValueEnabled
            || this.triggerBeforeCheckEnabled
            || this.triggerBeforeValueEnabled;
        return entityTriggers;
    }

    public String getEntityTriggersString() {
        entityTriggersString = "";
        if (triggerBeforeValueEnabled) {
            entityTriggersString += ", beforeValue = Kleenean.TRUE";
        }
        if (triggerAfterValueEnabled) {
            entityTriggersString += entityTriggersString + ", afterValue = Kleenean.TRUE";
        }
        if (triggerBeforeCheckEnabled) {
            entityTriggersString += entityTriggersString + ", beforeCheck = Kleenean.TRUE";
        }
        if (triggerAfterCheckEnabled) {
            entityTriggersString += entityTriggersString + ", afterCheck = Kleenean.TRUE";
        }
        return StringUtils.removeStart(entityTriggersString, ", ");
    }

    public List<String> getPackagesList() {
        List<String> packagesList = new ArrayList<>();
        for (XmiProperty currProperty : propertiesList) {
            if (currProperty.isEntityProperty()
                && !currProperty.getPackageName().isEmpty()) {
                packagesList.add(currProperty.getPackageName());
            }
        }
        return packagesList;
    }

}
