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
package adalid.jee2.meta.proyecto.base;

import adalid.commons.*;
import adalid.commons.enums.*;
import adalid.commons.interfaces.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.comparators.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.primitives.*;
import adalid.core.programmers.*;
import adalid.core.sql.*;
import adalid.jee2.ProjectObjectModel;
import adalid.jee2.SpecialPage;
import adalid.jee2.bundles.*;
import adalid.jee2.features.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.DominioParametro;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.entidad.comun.configuracion.basica.ModuloAplicacion;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.proyecto.comun.EntidadesBasicas;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class ProyectoBase extends Project implements SubjectProject, SpecialEntityPack {

    private static final Logger logger = Logger.getLogger(ProyectoBase.class);

    private static final String BASE_NAME = ProyectoBase.class.getName();

    private static final ResourceBundle RB = ResourceBundle.getBundle(BASE_NAME);

    // <editor-fold defaultstate="collapsed" desc="page components static final fields">
    private static final String PAGE_MAIN_FORM_ID = "mainForm";

    private static final String PAGE_NORTH_FORM_ID = "northForm";

    private static final String PAGE_SOUTH_FORM_ID = "southForm";

    private static final String PAGE_DATA_TABLE_ID = "dataTable";

    private static final String PAGE_DATA_TABLE_FILTER_ID = "filter";

    private static final String PAGE_DETAIL_PANEL_GRID_ID = "panelDetalle";

    private static final String PAGE_DETAIL_WRITING_PANEL_ID = "detailWritingPanel";

    private static final String PAGE_DIALOG_HEADER_ELEMENT_ID = "dialogHeaderElement";

    private static final String PAGE_WIZARD_LAST_STEP_ID = "lastStep";

    private static final String PAGE_MESSAGES_ID = "messages";

    private static final String PAGE_TREE_ID = "tree";

    private static final String PAGE_WIZARD_ID = PAGE_DETAIL_PANEL_GRID_ID;

    private static final String PAGE_DATA_TABLE_COLUMN_SUFFIX = "DataTableColumn"; // must match root-field.vm divString

    private static final String PAGE_DETAIL_PANEL_DIV_SUFFIX = "PanelDetalle"; // must match data-panel-detalle.vm divString

    private static final String PAGE_FIELD_SUFFIX = "Field"; // must match root-field.vm
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="page components ID getters">
    public String getPageMainFormID() {
        return PAGE_MAIN_FORM_ID;
    }

    public String getPageNorthFormID() {
        return PAGE_NORTH_FORM_ID;
    }

    public String getPageSouthFormID() {
        return PAGE_SOUTH_FORM_ID;
    }

    public String getPageDataTableID() {
        return PAGE_DATA_TABLE_ID;
    }

    public String getPageDataTableFilterID() {
        return PAGE_DATA_TABLE_FILTER_ID;
    }

    public String getPageDetailPanelGridID() {
        return PAGE_DETAIL_PANEL_GRID_ID;
    }

    public String getPageDetailWritingPanelID() {
        return PAGE_DETAIL_WRITING_PANEL_ID;
    }

    public String getPageDialogHeaderElementID() {
        return PAGE_DIALOG_HEADER_ELEMENT_ID;
    }

    public String getPageWizardLastStepID() {
        return PAGE_WIZARD_LAST_STEP_ID;
    }

    public String getPageMessagesID() {
        return PAGE_MESSAGES_ID;
    }

    public String getPageTreeID() {
        return PAGE_TREE_ID;
    }

    public String getPageWizardID() {
        return PAGE_WIZARD_ID;
    }

    public String getPageDataTableColumnSuffix() {
        return PAGE_DATA_TABLE_COLUMN_SUFFIX;
    }

    public String getPageDetailPanelDivSuffix() {
        return PAGE_DETAIL_PANEL_DIV_SUFFIX;
    }

    public String getPageFieldSuffix() {
        return PAGE_FIELD_SUFFIX;
    }
    // </editor-fold>

    /**
     * URL del directorio third-party
     */
    protected static final String URL_ZIP_THIRD_PARTY_DIR = "third.party.dir.zip.url";

    /**
     * Ruta del directorio third-party en Linux
     */
    protected static final String THIRD_PARTY_DIR_LINUX = "third.party.dir.linux";

    /**
     * Ruta del directorio third-party en Windows
     */
    protected static final String THIRD_PARTY_DIR_WINDOWS = "third.party.dir.windows";

    /**
     * Versión del directorio third-party
     */
    protected static final String THIRD_PARTY_DIR_VERSION = "third.party.dir.version";

    /**
     * Versión del directorio third-party
     */
    protected static final String VERSION_THIRD_PARTY_DIR = THIRD_PARTY_DIR_VERSION;

    // <editor-fold defaultstate="collapsed" desc="static fields public getters">
    /**/
    private static final String ADALID_SCHEMA = StringUtils.trimToEmpty(getString("adalid.schema"));

    public static String getEsquemaEntidadesComunes() {
        return ADALID_SCHEMA;
    }

    /* commented on 20210304
    private static final List<String> SQL_FOLDERS_LIST = getStringList("sql.folders");

    public static List<String> getSqlFoldersList() {
        return SQL_FOLDERS_LIST;
    }

    /**
     * @return the dictionary messages logging level
     */
    public static Level getDictionaryLevel() {
        return Dictionary.getInfoLevel();
    }

    /**
     * Sets the dictionary messages logging level
     *
     * @param level the dictionary messages logging level to set
     */
    public static void setDictionaryLevel(Level level) {
        Dictionary.setInfoLevel(level);
    }

    /**
     * @return the dictionary messages logging level
     */
    public static LoggingLevel getDictionaryLoggingLevel() {
        return LoggingLevel.getLoggingLevel(getDictionaryLevel());
    }

    /**
     * El método setDictionaryLoggingLevel del meta proyecto se utiliza para establecer el nivel de severidad de los mensajes informativos del
     * diccionario que se emiten al generar la aplicación. El valor predeterminado de esta propiedad es OFF (no emitir mensajes informativos del
     * diccionario).
     *
     * El método setDictionaryLoggingLevel es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar el
     * método build.
     *
     * @param level elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes informativos del diccionario que se
     * emiten al generar la aplicación. Especifique TRACE, DEBUG o INFO para emitir los mensajes con uno de esos niveles.
     */
    public static void setDictionaryLoggingLevel(LoggingLevel level) {
        setDictionaryLevel(level.getLevel());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields private getters">
    /* commented on 20210304
    private static List<String> getStringList(String key) {
        String string = getString(key);
        if (string == null) {
            return null;
        }
        String[] strings = StringUtils.split(string, ", ");
        return Arrays.asList(strings);
    }

    /**/
    private static String getString(String key) {
        try {
            String string = RB.getString(key);
            return StringUtils.trimToNull(string);
        } catch (Exception e) {
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="special entity getters">
    /**
     * @return the application message entity
     */
//  @Override
    public Entity getApplicationMessageEntity() {
        Class<? extends Entity> entityClass = getApplicationMessageEntityClass();
        return entityClass == null ? null : getEntity(entityClass);
    }

    /**
     * @return the segment set factory entity
     */
//  @Override
    public Entity getSegmentSetFactoryEntity() {
        Class<? extends Entity> entityClass = getSegmentSetFactoryEntityClass();
        return entityClass == null ? null : getEntity(entityClass);
    }

    /**
     * @return the uploaded file entity
     */
//  @Override
    public Entity getUploadedFileEntity() {
        Class<? extends Entity> entityClass = getUploadedFileEntityClass();
        return entityClass == null ? null : getEntity(entityClass);
    }

    /**
     * @return the user entity
     */
//  @Override
    public Entity getUserEntity() {
        Class<? extends Entity> entityClass = getUserEntityClass();
        return entityClass == null ? null : getEntity(entityClass);
    }

    /**
     * @return the version entity
     */
//  @Override
    public Entity getVersionEntity() {
        Class<? extends Entity> entityClass = getVersionEntityClass();
        return entityClass == null ? null : getEntity(entityClass);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="special entity pack">
    SpecialEntityPack specialEntityPack = new SpecialEntityPackDelegate();

    @Override
    public Class<? extends Entity> getApplicationMessageEntityClass() {
        return specialEntityPack.getApplicationMessageEntityClass();
    }

    @Override
    public void setApplicationMessageEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setApplicationMessageEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getSegmentSetFactoryEntityClass() {
        return specialEntityPack.getSegmentSetFactoryEntityClass();
    }

    @Override
    public void setSegmentSetFactoryEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setSegmentSetFactoryEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getTaskNotificationEntityClass() {
        return specialEntityPack.getTaskNotificationEntityClass();
    }

    @Override
    public void setTaskNotificationEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setTaskNotificationEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getUploadedFileEntityClass() {
        return specialEntityPack.getUploadedFileEntityClass();
    }

    @Override
    public void setUploadedFileEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setUploadedFileEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getUserEntityClass() {
        return specialEntityPack.getUserEntityClass();
    }

    @Override
    public void setUserEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setUserEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getVersionEntityClass() {
        return specialEntityPack.getVersionEntityClass();
    }

    @Override
    public void setVersionEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setVersionEntityClass(clazz);
    }

    @Override
    public List<Class<? extends Entity>> unsetSpecialEntityClasses() {
        return specialEntityPack.unsetSpecialEntityClasses();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="special native query segments">
    @Override
    public List<NativeQuerySegment> getSpecialNativeQuerySegments(Entity entity) {
        if (entity != null && entity.depth() == 0) {
            Property pk = entity.getPrimaryKeyProperty();
            if (pk instanceof NumericPrimitive) {
                List<Trigger> triggers = entity.getTriggersList();
                if (triggers != null && !triggers.isEmpty()) {
                    NumericPrimitive np = (NumericPrimitive) pk;
                    String usuario = specialValueOf(SpecialNumericValue.CURRENT_USER_ID);
                    String dominio = getEntityNumber(entity);
                    List<Entity> extensions = entity.getExtensionsList();
                    if (extensions != null && !extensions.isEmpty()) {
                        for (Entity extension : extensions) {
                            dominio += ", " + getEntityNumber(extension);
                        }
                    }
                    CondicionTarea condicion = getTypedEntity(CondicionTarea.class);
                    List<NativeQuerySegment> list = new ArrayList<>();
                    list.add(selectRecursosConTareasAsignadas(np, usuario, dominio, condicion));
                    list.add(selectRecursosConTareasPendientes(np, usuario, dominio, condicion));
                    return list;
                }
            }
        }
        return null;
    }

    private NativeQuerySegment selectRecursosConTareasAsignadas(NumericPrimitive pk, String usuario, String dominio, CondicionTarea condicion) {
        NativeQuerySegment qs = pk.isIn(NativeQuery.of(selectRecursosConTareasAsignadas(usuario, dominio, condicion)));
        qs.setLocalizedCollectionLabel(ENGLISH, "Records with tasks assigned to you");
        qs.setLocalizedCollectionLabel(SPANISH, "Registros con tareas que usted tiene asignadas");
        qs.setLocalizedCollectionShortLabel(ENGLISH, "Records with assigned tasks");
        qs.setLocalizedCollectionShortLabel(SPANISH, "Registros con tareas asignadas");
        return qs;
    }

    private NativeQuerySegment selectRecursosConTareasPendientes(NumericPrimitive pk, String usuario, String dominio, CondicionTarea condicion) {
        NativeQuerySegment qs = pk.isIn(NativeQuery.of(selectRecursosConTareasPendientes(usuario, dominio, condicion)));
        qs.setLocalizedCollectionLabel(ENGLISH, "Records with pending tasks that you can perform");
        qs.setLocalizedCollectionLabel(SPANISH, "Registros con tareas pendientes que usted puede realizar");
        qs.setLocalizedCollectionShortLabel(ENGLISH, "Records with pending tasks");
        qs.setLocalizedCollectionShortLabel(SPANISH, "Registros con tareas pendientes");
        return qs;
    }

    private static final String select_where_destinatario = "select recurso_valor from tarea_usuario where destinatario={0}",
        and_clase_recurso_1a = " and id_clase_recurso_valor in ({1})",
        and_clase_recurso_1b = " and id_clase_recurso_valor={1}",
        and_condicion_2a = " and (condicion={2} and responsable={0})",
        and_condicion_2b = " and (condicion={2} or (condicion={3} and responsable={0}))";

    private String selectRecursosConTareasAsignadas(String usuario, String dominio, CondicionTarea condicion) {
        String select = select_where_destinatario + (dominio.contains(",") ? and_clase_recurso_1a : and_clase_recurso_1b) + and_condicion_2a;
        Object asignada = condicion.ASIGNADA.getInstanceKeyValue();
        return StrUtils.getStringParametrizado(select, usuario, dominio, asignada);
    }

    private String selectRecursosConTareasPendientes(String usuario, String dominio, CondicionTarea condicion) {
        String select = select_where_destinatario + (dominio.contains(",") ? and_clase_recurso_1a : and_clase_recurso_1b) + and_condicion_2b;
        Object disponible = condicion.DISPONIBLE.getInstanceKeyValue();
        Object asignada = condicion.ASIGNADA.getInstanceKeyValue();
        return StrUtils.getStringParametrizado(select, usuario, dominio, disponible, asignada);
    }

    private String specialValueOf(SpecialNumericValue value) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? value.name().toLowerCase() + "()" : sqlProgrammer.getSpecialNumericValue(value);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="special pages">
    private final Map<String, SpecialPage> _projectSpecialPages = new TreeMap<>();

    public SpecialPage addSpecialPage(SpecialPage specialPage) {
        return _projectSpecialPages.put(specialPage.getCode(), specialPage);
    }

    public Map<String, SpecialPage> getProjectSpecialPagesMap() {
        return _projectSpecialPages;
    }

    public Collection<SpecialPage> getProjectSpecialPagesCollection() {
        return _projectSpecialPages.values();
    }
    // </editor-fold>

    static {
        setLocale(SPANISH);
    }

    public ProyectoBase() {
        super();
    }

    @Override
    public boolean beforeWriting() {
        boolean beforeWriting = super.beforeWriting();
        boolean checkBundles = checkBundles();
        return beforeWriting && checkBundles;
    }

    private boolean checkBundles() {
        boolean b0 = checkBundle(new BaseBundle());
        boolean b1 = checkBundle(new BundleMensajes());
        boolean b2 = checkBundle(new BundleWebui());
        return b0 && b1 && b2;
    }

    private boolean checkBundle(BundleAbstracto bundle) {
        int warnings = 0;
        for (String warning : bundle.getWarnings()) {
            logger.warn(warning);
            warnings++;
        }
        int errors = 0;
        for (String error : bundle.getErrors()) {
            logger.error(error);
            errors++;
        }
        increaseWriterWarnings(warnings);
        increaseWriterErrors(errors);
        return errors == 0;
    }

    @Override
    protected boolean afterWriting(boolean ok) {
        boolean afterWriting = super.afterWriting(ok);
        boolean storeDictionary = storeDictionary();
        boolean checkDictionary = checkDictionary();
        return afterWriting && storeDictionary && checkDictionary;
    }

    private final ProjectObjectModel pom = new ProjectObjectModel();

    @Override
    public ProjectObjectModelReader getProjectObjectModel() {
        return pom;
    }

    @Override
    public String getAdalidProjectVersion() {
        return pom.getProjectVersionNumber();
    }

    @Override
    protected void logAdalidProjectVersion() {
        pom.logProjectVersion();
    }

    @ProjectModule(menu = Kleenean.FALSE, role = Kleenean.FALSE)
    EntidadesBasicas entidadesBasicas;

    // <editor-fold defaultstate="collapsed" desc="private fields">
    private boolean _dictionaryEnabled;

    private boolean _projectBuilderDictionaryEnabled;

    private ProjectBuilderDictionary _projectBuilderDictionary;

    private SqlDictionary _entitiesDictionary, _entityParametersDictionary, _modulesDictionary,
        _operationsDictionary, _operationParametersDictionary, _pagesDictionary, _parametersDictionary;

    private String _baseFolderName;

    private String _databaseName;

    private String _databaseFormerSchemaName;

    private String _rootFolderName;

    private String _roleCodePrefix;

    private String _messageDigestAlgorithm;

    private boolean _sqlBusinessAuditTrail;

    private DatabaseLockingMechanism _databaseLockingMechanism;
    // </editor-fold>

    @Override
    public void configureBuilder() {
        super.configureBuilder();
        enableProjectBuilderDictionary();
    }

    /**
     * @return true if the dictionary is enabled; false otherwise
     */
    public boolean isDictionaryEnabled() {
        return _dictionaryEnabled;
    }

    /**
     * El método enableDictionary se utiliza para habilitar el diccionario de meta-data. Los archivos del diccionario son almacenados en el
     * subdirectorio dictionary/alias del subdirectorio especificado mediante la propiedad sql.dictionary.path del archivo bootstrapping.properties,
     * donde alias es el alias del proyecto generado
     */
    public void enableDictionary() {
        enableDictionary(getAlias());
    }

    /**
     * El método enableDictionary se utiliza para habilitar el diccionario de meta-data. Los archivos del diccionario son almacenados en el
     * subdirectorio dictionary/subdir del subdirectorio especificado mediante la propiedad sql.dictionary.path del archivo bootstrapping.properties.
     *
     * @param subdir nombre del subdirectorio donde son almacenados los archivos del diccionario. El nombre del subdirectorio solo puede contener
     * letras, números y guiones bajos (underscores). Si el nombre no es válido, se utiliza el alias del proyecto generado en su lugar.
     */
    public void enableDictionary(String subdir) {
        Dictionary.reset();
        String folder = subdir != null && subdir.matches("^\\w+$") ? subdir : getAlias();
        _dictionaryEnabled = true;
        _entitiesDictionary = SqlDictionary.load(ClaseRecurso.class, folder);
        _entityParametersDictionary = SqlDictionary.load(DominioParametro.class, folder);
        _modulesDictionary = SqlDictionary.load(ModuloAplicacion.class, folder);
        _operationsDictionary = SqlDictionary.load(Funcion.class, folder);
        _operationParametersDictionary = SqlDictionary.load(FuncionParametro.class, folder);
        _pagesDictionary = SqlDictionary.load(Pagina.class, folder);
        _parametersDictionary = SqlDictionary.load(Parametro.class, folder);
    }

    protected boolean isProjectBuilderDictionaryEnabled() {
        return _projectBuilderDictionaryEnabled;
    }

    protected void enableProjectBuilderDictionary() {
        _projectBuilderDictionary = ProjectBuilderDictionary.load();
        _projectBuilderDictionaryEnabled = true;
    }

    private boolean storeDictionary() {
        if (_dictionaryEnabled) {
            _entitiesDictionary.store();
            _entityParametersDictionary.store();
            _modulesDictionary.store();
            _operationsDictionary.store();
            _operationParametersDictionary.store();
            _pagesDictionary.store();
            _parametersDictionary.store();
        }
        return storeProjectBuilderDictionary();
    }

    private boolean storeProjectBuilderDictionary() {
        if (_projectBuilderDictionaryEnabled) {
            _projectBuilderDictionary.setLastExecutedProjectAlias(getAlias());
            _projectBuilderDictionary.setLastExecutedProjectBaseFolderName(getBaseFolderName());
            _projectBuilderDictionary.setLastExecutedProjectClassName(getClass().getCanonicalName());
            _projectBuilderDictionary.store();
        }
        return true;
    }

    private boolean checkDictionary() {
        if (_dictionaryEnabled) {
            Dictionary.printSummary();
            return Dictionary.getErrorCount() == 0;
        }
        return true;
    }

    @Override
    public String getLastVersionCode() {
        Entity versionEntity = getVersionEntity();
        if (versionEntity != null) {
            List<Instance> list = versionEntity.getInstancesList();
            if (list != null) {
                Instance last = null;
                Collection<Instance> instances = ColUtils.sort(list, new ByInstanceKeyValue());
                for (Instance instance : instances) {
                    last = instance;
                }
                if (last != null) {
                    Object label = last.getInstanceKeyLabel();
                    return label == null ? null : label.toString();
                }
            }
        }
        return null;
    }

    @Override
    public Object getVersionEnumeration() {
        return getVersionEntity();
    }

    /**
     * @return the entity keys
     */
    public Set<String> getEntityKeys() {
        Set<String> set = new TreeSet<>();
        set.addAll(getEntitiesMap().keySet());
        return set;
    }

    /**
     * @return the operation keys
     */
    public Set<String> getOperationKeys() {
        Set<String> set = new TreeSet<>();
        set.addAll(getDefaultCrudOperationKeys());
        set.addAll(getUserDefinedOperationKeys());
        return set;
    }

    /**
     * @return the CRUD operation keys
     */
    public Set<String> getDefaultCrudOperationKeys() {
        Set<String> set = new TreeSet<>();
        List<Entity> entities = getEntitiesList();
        String[] operations = Operation.getCrudOperationKeys();
        String simpleName;
        for (Entity entity : entities) {
            simpleName = entity.getDataType().getSimpleName();
            for (String name : operations) {
                set.add(simpleName + "." + name);
            }
        }
        return set;
    }

    /**
     * @return the user-defined operation keys
     */
    public Set<String> getUserDefinedOperationKeys() {
        Set<String> set = new TreeSet<>();
        List<Entity> entities = getEntitiesList();
        String simpleName;
        for (Entity entity : entities) {
            simpleName = entity.getDataType().getSimpleName();
            for (Operation operation : entity.getOperationsList()) {
                set.add(simpleName + "." + operation.getName());
            }
        }
        return set;
    }

    /**
     * @return the page keys
     */
    public Set<String> getPageKeys() {
        Set<String> set = new TreeSet<>();
        set.addAll(getDisplaysMap().keySet());
        return set;
    }

    /**
     * @return the parameter keys
     */
    public Set<String> getParameterKeys() {
        Set<String> set = new TreeSet<>();
        List<Entity> entities = getEntitiesList();
        String simpleName;
        for (Entity entity : entities) {
            simpleName = entity.getDataType().getSimpleName();
            for (Property property : entity.getPropertiesList()) {
                set.add(simpleName + "." + property.getName());
            }
            for (Operation operation : entity.getOperationsList()) {
                for (Parameter parameter : operation.getParametersList()) {
                    set.add(simpleName + "." + operation.getName() + "." + parameter.getName());
                }
            }
        }
        return set;
    }

    /**
     * @param entity entity
     * @return the entity number
     */
    public String getEntityNumber(Entity entity) {
        return entity == null ? "?" : getEntityNumber(entity.getDataType());
    }

    /**
     * @param clazz entity class
     * @return the entity number
     */
    public String getEntityNumber(Class<?> clazz) {
        return clazz == null || !Entity.class.isAssignableFrom(clazz) ? "?" : getEntityNumber(clazz.getSimpleName());
    }

    /**
     * @param key key
     * @return the entity number
     */
    public String getEntityNumber(String key) {
        return StringUtils.isBlank(key) ? "?" : longNumericCode(key, _entitiesDictionary);
    }

    /**
     * @param key key
     * @return the entity parameter number
     */
    public String getEntityParameterNumber(String key) {
        return StringUtils.isBlank(key) ? "?" : longNumericCode(key, _entityParametersDictionary);
    }

    /**
     * @param module module
     * @return the module number
     */
    public String getModuleNumber(Project module) {
        return module == null ? "?" : getModuleNumber(module.getClass());
    }

    /**
     * @param clazz module class
     * @return the module number
     */
    public String getModuleNumber(Class<?> clazz) {
        return clazz == null || !Project.class.isAssignableFrom(clazz) ? "?" : getModuleNumber(clazz.getCanonicalName());
    }

    /**
     * @param key key
     * @return the module number
     */
    public String getModuleNumber(String key) {
        return StringUtils.isBlank(key) ? "?" : longNumericCode(key, _modulesDictionary);
    }

    /**
     * @param operation operation
     * @return the operation number
     */
    public String getOperationNumber(Operation operation) {
        if (operation == null) {
            return "?";
        }
        String name = operation.getName();
        Entity declaringEntity = operation.getDeclaringEntity();
        return getOperationNumber(name, declaringEntity);
    }

    /**
     * @param name name
     * @param declaringEntity declaring entity
     * @return the operation number
     */
    public String getOperationNumber(String name, Entity declaringEntity) {
        if (StringUtils.isBlank(name) || declaringEntity == null) {
            return "?";
        }
        String simpleName = declaringEntity.getDataType().getSimpleName();
        String key = simpleName + "." + name;
        return getOperationNumber(key);
    }

    /**
     * @param key key
     * @return the operation number
     */
    public String getOperationNumber(String key) {
        return StringUtils.isBlank(key) ? "?" : longNumericCode(key, _operationsDictionary);
    }

    /**
     * @param key key
     * @return the operation parameter number
     */
    public String getOperationParameterNumber(String key) {
        return StringUtils.isBlank(key) ? "?" : longNumericCode(key, _operationParametersDictionary);
    }

    /**
     * @param key key
     * @return the page number
     */
    public String getPageNumber(String key) {
        return StringUtils.isBlank(key) ? "?" : longNumericCode(key, _pagesDictionary);
    }

    /**
     * @param artifact artifact
     * @return the parameter number
     */
    public String getParameterNumber(Artifact artifact) {
        if (artifact == null) {
            return "?";
        }
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        if (declaringArtifact instanceof Entity) {
            if (artifact instanceof Property || artifact instanceof EntityCollection) {
                String name = artifact.getName();
                Entity declaringEntity = (Entity) declaringArtifact;
                return getParameterNumber(name, declaringEntity);
            }
        } else if (declaringArtifact instanceof Operation) {
            if (artifact instanceof Parameter) {
                String name = artifact.getName();
                Operation declaringOperation = (Operation) declaringArtifact;
                return getParameterNumber(name, declaringOperation);
            }
        }
        return "?";
    }

    /**
     * @param name name
     * @param declaringEntity declaring entity
     * @return the parameter number
     */
    public String getParameterNumber(String name, Entity declaringEntity) {
        if (StringUtils.isBlank(name) || declaringEntity == null) {
            return "?";
        }
        String simpleName = declaringEntity.getDataType().getSimpleName();
        String key = simpleName + "." + name;
        return getParameterNumber(key);
    }

    /**
     * @param name name
     * @param declaringOperation declaring operation
     * @return the parameter number
     */
    public String getParameterNumber(String name, Operation declaringOperation) {
        if (StringUtils.isBlank(name) || declaringOperation == null) {
            return "?";
        }
        Entity declaringEntity = declaringOperation.getDeclaringEntity();
        if (declaringEntity == null) {
            return "?";
        }
        String simpleName = declaringEntity.getDataType().getSimpleName();
        String key = simpleName + "." + declaringOperation.getName() + "." + name;
        return getParameterNumber(key);
    }

    /**
     * @param key key
     * @return the parameter number
     */
    public String getParameterNumber(String key) {
        return StringUtils.isBlank(key) ? "?" : longNumericCode(key, _parametersDictionary);
    }

    private String longNumericCode(String key, Dictionary dictionary) {
        String humplessKey = StrUtils.getHumplessCase(key, '_');
        String numericCode = StrUtils.getLongNumericKeyCode(humplessKey);
        if (dictionary != null) {
            String value = dictionary.getProperty(key);
            if (Dictionary.isValidNumericCode(value)) {
                numericCode = value;
                dictionary.putProperty(key, numericCode);
            } else {
                dictionary.setProperty(key, numericCode);
            }
        }
        return numericCode;
    }

    /**
     * @return the base folder name
     */
    @Override
    public String getBaseFolderName() {
        return StringUtils.defaultIfBlank(_baseFolderName, getDefaultBaseFolderName());
    }

    /**
     * @param baseFolderName the base folder name to set
     */
    public void setBaseFolderName(String baseFolderName) {
        _baseFolderName = StrUtils.getFileName(baseFolderName);
    }

    /**
     * @return the database name
     */
    public String getDatabaseName() {
        return StringUtils.defaultIfBlank(_databaseName, getDefaultDatabaseName());
    }

    /**
     * El método setDatabaseName se utiliza para establecer el nombre de la base de datos del proyecto, en caso de que se deba utilizar un nombre
     * diferente al predeterminado; el nombre predeterminado es el alias del proyecto
     *
     * @param databaseName nombre de la base de datos
     */
    public void setDatabaseName(String databaseName) {
        _databaseName = StrUtils.getLowerCaseIdentifier(databaseName, '-');
    }

    /**
     * @return the database former schema name
     */
    public String getDatabaseFormerSchemaName() {
        return StringUtils.defaultIfBlank(_databaseFormerSchemaName, getDefaultDatabaseFormerSchemaName());
    }

    /**
     * El método setDatabaseFormerSchemaName se utiliza para establecer el nombre del esquema previo de la base de datos del proyecto, en caso de que
     * se deba utilizar un nombre diferente al predeterminado; el nombre predeterminado es former.
     *
     * @param databaseFormerSchemaName nombre del esquema previo
     */
    public void setDatabaseFormerSchemaName(String databaseFormerSchemaName) {
        _databaseFormerSchemaName = StrUtils.getLowerCaseIdentifier(databaseFormerSchemaName, '_');
    }

    /**
     * @return the root folder name
     */
    public String getRootFolderName() {
        return StringUtils.defaultIfBlank(_rootFolderName, getDefaultRootFolderName());
    }

    /**
     * @param rootFolderName the root folder name to set
     */
    public void setRootFolderName(String rootFolderName) {
        _rootFolderName = StrUtils.getFileName(rootFolderName);
    }

    /**
     * @return the role code prefix
     */
    public String getRoleCodePrefix() {
        return StringUtils.trimToEmpty(_roleCodePrefix);
    }

    /**
     * El método setRoleCodePrefix se utiliza para establecer el prefijo de código de rol del controlador de seguridad del proyecto. Esta propiedad
     * solo se utiliza si el controlador de seguridad del proyecto es LDAP o CIAM. El prefijo de código de rol se antepone a los códigos de los roles
     * definidos en la aplicación para determinar los correspondientes códigos en el directorio LDAP y/o en el gestor de CIAM. Dado que esta propiedad
     * no tiene valor predeterminado, si no se establece su valor, el código de cada rol en el directorio LDAP y/o en el gestor de CIAM debe ser igual
     * al código definido en la aplicación.
     *
     * @param roleCodePrefix prefijo de código de rol del controlador de seguridad del proyecto.
     */
    public void setRoleCodePrefix(String roleCodePrefix) {
//      _roleCodePrefix = StrUtils.getIdentifier(roleCodePrefix);
        _roleCodePrefix = StrUtils.diacriticlessAscii(roleCodePrefix);
    }

    /**
     * Returns a string that identifies the algorithm, independent of implementation details. The name should be a standard Java Security name (such
     * as "SHA", "MD5", and so on). See the MessageDigest section in the Java Cryptography Architecture Standard Algorithm Name Documentation for
     * information about standard algorithm names.
     *
     * @return the message digest algorithm
     */
    public String getMessageDigestAlgorithm() {
        return StringUtils.defaultIfBlank(_messageDigestAlgorithm, getDefaultMessageDigestAlgorithm());
    }

    /**
     * El método setMessageDigestAlgorithm se utiliza para especificar el nombre del algoritmo de encriptación del proyecto generado. El valor
     * predeterminado de esta propiedad es MD5.
     *
     * @param messageDigestAlgorithm nombre del algoritmo de encriptación del proyecto generado. Utilice como argumento el nombre de un algoritmo
     * soportado por la plataforma; por ejemplo: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512.
     */
    public void setMessageDigestAlgorithm(String messageDigestAlgorithm) {
        _messageDigestAlgorithm = StrUtils.getIdentifier(messageDigestAlgorithm, '-');
    }

    /**
     * @return true if SQL business functions audit trail should be enabled; false otherwise
     */
    public boolean isSqlBusinessAuditTrail() {
        return _sqlBusinessAuditTrail;
    }

    /**
     * @param enabled true if SQL business functions audit trail should be enabled; false otherwise
     */
    public void setSqlBusinessAuditTrail(boolean enabled) {
        _sqlBusinessAuditTrail = enabled;
    }

    /**
     * @return the database locking mechanism
     */
    public DatabaseLockingMechanism getDatabaseLockingMechanism() {
        return _databaseLockingMechanism == null ? getDefaultDatabaseLockingMechanism() : _databaseLockingMechanism;
    }

    /**
     * @param databaseLockingMechanism the database locking mechanism to set
     */
    public void setDatabaseLockingMechanism(DatabaseLockingMechanism databaseLockingMechanism) {
        _databaseLockingMechanism = databaseLockingMechanism;
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        System.out.println(entidadesBasicas);
    }
    // </editor-fold>

    protected String getDefaultBaseFolderName() {
        return getAlias();
    }

    protected String getDefaultDatabaseName() {
        return getAlias();
    }

    protected String getDefaultDatabaseFormerSchemaName() {
        return "former";
    }

    protected String getDefaultRootFolderName() {
        return getAlias();
    }

    protected String getDefaultMessageDigestAlgorithm() {
        return "MD5";
    }

    protected DatabaseLockingMechanism getDefaultDatabaseLockingMechanism() {
        return DatabaseLockingMechanism.ENTITY_VERSIONING;
    }

}
