/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.properties.SortedProperties;
import adalid.commons.util.StrUtils;
import adalid.core.Operation;
import adalid.core.Project;
import adalid.core.annotations.ProjectModule;
import adalid.core.enums.Kleenean;
import adalid.core.enums.ProjectStage;
import adalid.core.enums.SecurityRealmType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.Property;
import adalid.core.programmers.AbstractJavaProgrammer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import meta.entidad.comun.configuracion.basica.Aplicacion;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.Dominio;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.enumeracion.base.TipoModuloBase;
import meta.paquete.base.PaqueteBase;
import meta.paquete.comun.PaqueteConsultaRecursosBasicos;
import meta.paquete.comun.PaqueteRegistroRecursosBasicos;
import meta.proyecto.comun.EntidadesBasicas;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoJava extends Project {

    private final Logger logger = Logger.getLogger(Project.class);

    protected static final String VERSION_JAVA = "java.version";

    protected static final Map<String, String> ENBG = new LinkedHashMap<>(); // Entity Name Bundle Getters

    protected static final Set<String> PAKS; // Package Alias Keyword Set

    static {
        ENBG.put(Aplicacion.class.getSimpleName(), "BundleWebui.getNombreAplicacion");
        ENBG.put(ClaseRecurso.class.getSimpleName(), "BundleDominios.getNombreClaseRecurso");
        ENBG.put(Dominio.class.getSimpleName(), "BundleDominios.getNombreDominio");
//      ENBG.put(DominioParametro.class.getSimpleName(), "BundleParametros.getNombreDominioParametro");
        ENBG.put(Funcion.class.getSimpleName(), "BundleFunciones.getNombreFuncion");
        ENBG.put(FuncionParametro.class.getSimpleName(), "BundleParametros.getNombreFuncionParametro");
        ENBG.put(GrupoProceso.class.getSimpleName(), "BundleFunciones.getNombreGrupoProceso");
//      ENBG.put(OpcionMenu.class.getSimpleName(), "BundleMenus.getNombreOpcionMenu");
        ENBG.put(Pagina.class.getSimpleName(), "BundleWebui.getNombrePagina");
        ENBG.put(Parametro.class.getSimpleName(), "BundleParametros.getNombreParametro");
        /**/
        PAKS = AbstractJavaProgrammer.getJavaKeywords();
        PAKS.add("context");
        PAKS.add("pages");
    }

    public static String getEntityNameBundleGetter(Entity entity) {
        return entity == null ? null : getEntityNameBundleGetter(entity.getName());
    }

    public static String getEntityNameBundleGetter(String entityName) {
        return entityName == null ? null : ENBG.get(entityName);
    }

    public static String getEsquemaEntidadesComunes() {
        String string = getString("adalid.schema");
        return StringUtils.trimToEmpty(string);
    }

    public ProyectoJava() {
        super();
        init();
    }

    private void init() {
        setUserEntityClass(meta.entidad.comun.control.acceso.Usuario.class);
    }

    @ProjectModule(menu = Kleenean.FALSE, role = Kleenean.FALSE)
    EntidadesBasicas entidadesBasicas;

    protected ModuloConsulta consulta;

    protected ModuloProcesamiento procesamiento;

    protected ModuloRegistro registro;

    protected PaqueteConsultaRecursosBasicos consultaRecursosBasicos;

    protected PaqueteRegistroRecursosBasicos registroRecursosBasicos;

    @Override
    public boolean analyze() {
        boolean analyzed = super.analyze();
        analyzed &= analyzePackages();
        if (analyzed) {
//          loadDictionary();
            initDictionary();
            buildDictionary();
//          storeDictionary();
        }
        return analyzed;
    }

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final String BASE_NAME = ProyectoJava.class.getName();

    private static final ResourceBundle RB = ResourceBundle.getBundle(BASE_NAME);

    private static final String DIR = System.getProperties().getProperty("user.dir");

    private static final String SEP = System.getProperties().getProperty("file.separator");

    private static final String PROPERTIES_SUFFIX = ".properties";

    private static final String DICTIONARY_DIR = DIR + SEP + "dictionary";

    private static final String DICTIONARY_NEXT_ID_KEY = "$next$id$";

    private static final String ENTITIES_DICTIONARY = DICTIONARY_DIR + SEP + "entities" + PROPERTIES_SUFFIX;

    private static final String OPERATIONS_DICTIONARY = DICTIONARY_DIR + SEP + "operations" + PROPERTIES_SUFFIX;

    private static final String PAGES_DICTIONARY = DICTIONARY_DIR + SEP + "pages" + PROPERTIES_SUFFIX;

    private static final String PARAMETERS_DICTIONARY = DICTIONARY_DIR + SEP + "parameters" + PROPERTIES_SUFFIX;

    private static final Locale DEFAULT_LOCALE = defaultLocale();

    private static final List<String> SQL_FOLDERS_LIST = getStringList("sql.folders");
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields' public getters">
    public static Locale getDefaultLocale() {
        return DEFAULT_LOCALE;
    }

    public static List<String> getSqlFoldersList() {
        return SQL_FOLDERS_LIST;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields' private getters">
    private static Locale defaultLocale() {
        String tag = getString("locale.tag");
        return tag == null ? Locale.getDefault() : Locale.forLanguageTag(tag);
    }

    private static List<String> getStringList(String key) {
        String string = getString(key);
        if (string == null) {
            return null;
        }
        String[] strings = StringUtils.split(string, ", ");
        return Arrays.asList(strings);
    }

    private static String getString(String key) {
        try {
            String string = RB.getString(key);
            return StringUtils.trimToNull(string);
        } catch (Exception e) {
            return null;
        }
    }
    // </editor-fold>

    private boolean _dictionaryEnabled;

    private Properties _entitiesDictionary;

    private Properties _operationsDictionary;

    private Properties _pagesDictionary;

    private Properties _parametersDictionary;

    private String _earProjectName;

    private String _ejbProjectName;

    private String _libProjectName;

    private String _resourcesProjectName;

    private String _webProjectName;

    private String _baseFolderName;

    private String _databaseName;

    private String _rootFolderName;

    private String _rootPackageName;

    private boolean _internetAccessAllowed;

    private boolean _projectMailingEnabled;

    private ProjectStage _projectStage;

    private SecurityRealmType _securityRealmType;

    private String _securityRealmName;

    private String _roleBasedAccessControllerName;

    /**
     * @return true if the dictionary is enabled; false otherwise
     */
    public boolean isDictionaryEnabled() {
        return _dictionaryEnabled;
    }

    /**
     * enables the dictionary
     */
    public void enableDictionary() {
        _dictionaryEnabled = true;
    }

    /**
     * @return the entities dictionary
     */
    public Properties getEntitiesDictionary() {
        return _entitiesDictionary;
    }

    /**
     * @return the operations dictionary
     */
    public Properties getOperationsDictionary() {
        return _operationsDictionary;
    }

    /**
     * @return the pages dictionary
     */
    public Properties getPagesDictionary() {
        return _pagesDictionary;
    }

    /**
     * @return the parameters dictionary
     */
    public Properties getParametersDictionary() {
        return _parametersDictionary;
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
     * @param entity
     * @return the entity number
     */
    public String getEntityNumber(Entity entity) {
        if (entity == null) {
            return "?";
        }
//      String key = entity.getName();
        String key = entity.getDataType().getSimpleName();
        return getEntityNumber(key);
    }

    /**
     * @param key
     * @return the entity number
     */
    public String getEntityNumber(String key) {
        if (StringUtils.isBlank(key)) {
            return "?";
        }
        if (_dictionaryEnabled) {
            return _entitiesDictionary.getProperty(key, "?");
        }
        return StrUtils.getLongNumericCode(StrUtils.getHumplessCase(key, '_'));
    }

    /**
     * @param operation
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
     * @param name
     * @param declaringEntity
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
     * @param key
     * @return the operation number
     */
    public String getOperationNumber(String key) {
        if (StringUtils.isBlank(key)) {
            return "?";
        }
        if (_dictionaryEnabled) {
            return _operationsDictionary.getProperty(key, "?");
        }
        return StrUtils.getLongNumericCode(StrUtils.getHumplessCase(key, '_'));
    }

    /**
     * @param key
     * @return the page number
     */
    public String getPageNumber(String key) {
        if (StringUtils.isBlank(key)) {
            return "?";
        }
        if (_dictionaryEnabled) {
            return _pagesDictionary.getProperty(key, "?");
        }
        return StrUtils.getLongNumericCode(StrUtils.getHumplessCase(key, '_'));
    }

    /**
     * @param artifact
     * @return the parameter number
     */
    public String getParameterNumber(Artifact artifact) {
        if (artifact == null) {
            return "?";
        }
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        if (artifact instanceof Property && declaringArtifact instanceof Entity) {
            String name = artifact.getName();
            Entity declaringEntity = (Entity) declaringArtifact;
            return getParameterNumber(name, declaringEntity);
        }
        if (artifact instanceof Parameter && declaringArtifact instanceof Operation) {
            String name = artifact.getName();
            Operation declaringOperation = (Operation) declaringArtifact;
            return getParameterNumber(name, declaringOperation);
        }
        return "?";
    }

    /**
     * @param name
     * @param declaringEntity
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
     * @param name
     * @param declaringOperation
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
     * @param key
     * @return the parameter number
     */
    public String getParameterNumber(String key) {
        if (StringUtils.isBlank(key)) {
            return "?";
        }
        if (_dictionaryEnabled) {
            return _parametersDictionary.getProperty(key, "?");
        }
        return StrUtils.getLongNumericCode(StrUtils.getHumplessCase(key, '_'));
    }

    private boolean analyzePackages() {
        boolean ok = true;
        String alias, dotted;
        String[] words;
        TipoModuloBase tipo;
        Set<String> names, allNames;
        Set<String> aliases = new LinkedHashSet<>();
        Set<String> consultas = new LinkedHashSet<>();
        Set<String> procesos = new LinkedHashSet<>();
        Set<String> registros = new LinkedHashSet<>();
        List<? extends PaqueteBase> packages = getPackages();
        for (PaqueteBase paquete : packages) {
            alias = paquete.getAlias();
            if (StringUtils.isBlank(alias)) {
                logger.error(paquete + " is invalid; its alias is null");
                ok = false;
            } else if (aliases.add(alias)) {
                dotted = StrUtils.getLowerCaseIdentifier(alias, '.');
                words = StringUtils.split(dotted, '.');
                for (String word : words) {
                    for (String keyword : PAKS) {
                        if (keyword.equalsIgnoreCase(word)) {
                            logger.error(paquete + " is invalid; its alias contains keyword \"" + keyword + "\"");
                            ok = false;
                        }
                    }
                }
            } else {
                logger.error(paquete + " is invalid; duplicate alias \"" + alias + "\"");
                ok = false;
            }
            names = paquete.getLocallyDeclaredEntityClassSimpleNames();
            if (names.isEmpty()) {
                logger.error(paquete + " is invalid; it has no entities locally declared");
                ok = false;
            }
            allNames = null;
            tipo = paquete.getTipo();
            if (tipo == null) {
                logger.error(paquete + " is invalid; its type is null");
                ok = false;
            } else {
                switch (tipo) {
                    case CONSULTA:
                        allNames = consultas;
                        break;
                    case PROCESAMIENTO:
                        allNames = procesos;
                        break;
                    case REGISTRO:
                        allNames = registros;
                        break;
                    default:
                        logger.error(paquete + " is invalid; its type is " + tipo);
                        ok = false;
                        break;
                }
            }
            if (allNames == null || names.isEmpty()) {
            } else {
                for (String name : names) {
                    if (allNames.add(name)) {
                    } else {
                        logger.error(name + " is declared in more than one package of type " + tipo);
                        ok = false;
                    }
                }
            }
        }
        return ok;
    }

    private List<? extends PaqueteBase> getPackages() {
        PaqueteBase paquete;
        List<PaqueteBase> packages = new ArrayList<>();
        List<Project> modules = getModulesList();
        for (Project module : modules) {
            if (module instanceof PaqueteBase) {
                paquete = (PaqueteBase) module;
                packages.add(paquete);
            }
        }
        return packages;
    }

    private void loadDictionary() {
        _entitiesDictionary = PropertiesHandler.loadProperties(ENTITIES_DICTIONARY, true, Level.WARN);
        _operationsDictionary = PropertiesHandler.loadProperties(OPERATIONS_DICTIONARY, true, Level.WARN);
        _pagesDictionary = PropertiesHandler.loadProperties(PAGES_DICTIONARY, true, Level.WARN);
        _parametersDictionary = PropertiesHandler.loadProperties(PARAMETERS_DICTIONARY, true, Level.WARN);
    }

    private void initDictionary() {
        _entitiesDictionary = new SortedProperties();
        _operationsDictionary = new SortedProperties();
        _pagesDictionary = new SortedProperties();
        _parametersDictionary = new SortedProperties();
    }

    private void buildDictionary() {
        addKeys(_entitiesDictionary, getEntityKeys());
        addKeys(_operationsDictionary, getOperationKeys());
        addKeys(_pagesDictionary, getPageKeys());
        addKeys(_parametersDictionary, getParameterKeys());
    }

    private void addKeys(Properties properties, Set<String> keys) {
        String id$ = properties.getProperty(DICTIONARY_NEXT_ID_KEY);
        long id = StringUtils.isNumeric(id$) ? new Long(id$) : 1;
        for (String key : keys) {
            if (properties.containsKey(key)) {
            } else {
                properties.setProperty(key, "" + id++);
            }
        }
        properties.setProperty(DICTIONARY_NEXT_ID_KEY, "" + id);
    }

    private void storeDictionary() {
        PropertiesHandler.storeProperties(_entitiesDictionary, ENTITIES_DICTIONARY, getName());
        PropertiesHandler.storeProperties(_operationsDictionary, OPERATIONS_DICTIONARY, getName());
        PropertiesHandler.storeProperties(_pagesDictionary, PAGES_DICTIONARY, getName());
        PropertiesHandler.storeProperties(_parametersDictionary, PARAMETERS_DICTIONARY, getName());
    }

    /**
     * @return the ear project name
     */
    public String getEarProjectName() {
        return StringUtils.defaultIfBlank(_earProjectName, getDefaultEarProjectName());
    }

    /**
     * @param earProjectName the ear project name to set
     */
    public void setEarProjectName(String earProjectName) {
        _earProjectName = StrUtils.getLowerCaseIdentifier(earProjectName, '-');
    }

    /**
     * @return the ejb project name
     */
    public String getEjbProjectName() {
        return StringUtils.defaultIfBlank(_ejbProjectName, getDefaultEjbProjectName());
    }

    /**
     * @param ejbProjectName the ejb project name to set
     */
    public void setEjbProjectName(String ejbProjectName) {
        _ejbProjectName = StrUtils.getLowerCaseIdentifier(ejbProjectName, '-');
    }

    /**
     * @return the lib project name
     */
    public String getLibProjectName() {
        return StringUtils.defaultIfBlank(_libProjectName, getDefaultLibProjectName());
    }

    /**
     * @param libProjectName the lib project name to set
     */
    public void setLibProjectName(String libProjectName) {
        _libProjectName = StrUtils.getLowerCaseIdentifier(libProjectName, '-');
    }

    /**
     * @return the resources project name
     */
    public String getResourcesProjectName() {
        return StringUtils.defaultIfBlank(_resourcesProjectName, getDefaultResourcesProjectName());
    }

    /**
     * @param resourcesProjectName the resources project name to set
     */
    public void setResourcesProjectName(String resourcesProjectName) {
        _resourcesProjectName = StrUtils.getLowerCaseIdentifier(resourcesProjectName, '-');
    }

    /**
     * @return the web project name
     */
    public String getWebProjectName() {
        return StringUtils.defaultIfBlank(_webProjectName, getDefaultWebProjectName());
    }

    /**
     * @param webProjectName the web project name to set
     */
    public void setWebProjectName(String webProjectName) {
        _webProjectName = StrUtils.getLowerCaseIdentifier(webProjectName, '-');
    }

    /**
     * @return the base folder name
     */
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
     * @param databaseName the database name to set
     */
    public void setDatabaseName(String databaseName) {
        _databaseName = StrUtils.getLowerCaseIdentifier(databaseName, '-');
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
     * @return the root package name
     */
    public String getRootPackageName() {
        return StringUtils.defaultIfBlank(_rootPackageName, getDefaultRootPackageName());
    }

    /**
     * @param rootPackageName the root package name to set
     */
    public void setRootPackageName(String rootPackageName) {
        _rootPackageName = StrUtils.getLowerCaseIdentifier(rootPackageName, '.');
    }

    /**
     * @return true if internet access should be allowed; false otherwise
     */
    public boolean isInternetAccessAllowed() {
        return _internetAccessAllowed;
    }

    /**
     * @param allowed true if internet access is allowed; false otherwise
     */
    public void setInternetAccessAllowed(boolean allowed) {
        _internetAccessAllowed = allowed;
    }

    /**
     * @return true if project mailing should be enabled; false otherwise
     */
    public boolean isProjectMailingEnabled() {
        return _projectMailingEnabled;
    }

    /**
     * @param enabled true if project mailing should be enabled; false otherwise
     */
    public void setProjectMailingEnabled(boolean enabled) {
        _projectMailingEnabled = enabled;
    }

    /**
     * @return the project stage
     */
    public ProjectStage getProjectStage() {
        return _projectStage == null ? ProjectStage.DEVELOPMENT : _projectStage;
    }

    /**
     * @param stage the project stage to set
     */
    public void setProjectStage(ProjectStage stage) {
        _projectStage = stage;
    }

    /**
     * @return the security realm type
     */
    public SecurityRealmType getSecurityRealmType() {
        return _securityRealmType == null ? SecurityRealmType.JDBC : _securityRealmType;
    }

    /**
     * @param securityRealmType the security realm type to set
     */
    public void setSecurityRealmType(SecurityRealmType securityRealmType) {
        _securityRealmType = securityRealmType;
    }

    /**
     * @return the security realm name
     */
    public String getSecurityRealmName() {
        return StringUtils.defaultIfBlank(_securityRealmName, getDefaultSecurityRealmName());
    }

    /**
     * @param securityRealmName the security realm name to set
     */
    public void setSecurityRealmName(String securityRealmName) {
        _securityRealmName = StrUtils.getLowerCaseIdentifier(securityRealmName, '-');
    }

    /**
     * @return the role-based-access-controller (RBAC) name
     */
    public String getRoleBasedAccessControllerName() {
        return StringUtils.defaultIfBlank(_roleBasedAccessControllerName, getDefaultRoleBasedAccessControllerName());
    }

    /**
     * @param roleBasedAccessControllerName the role-based-access-controller (RBAC) name to set
     */
    public void setRoleBasedAccessControllerName(String roleBasedAccessControllerName) {
        _roleBasedAccessControllerName = StrUtils.getIdentifier(roleBasedAccessControllerName);
    }

    protected String getDefaultEarProjectName() {
        return getAlias();
    }

    protected String getDefaultEjbProjectName() {
        return getAlias() + "-ejb";
    }

    protected String getDefaultLibProjectName() {
        return getAlias() + "-lib";
    }

    protected String getDefaultResourcesProjectName() {
        return getAlias() + "-resources";
    }

    protected String getDefaultWebProjectName() {
        return getAlias() + "-war";
    }

    protected String getDefaultDatabaseName() {
        return getAlias();
    }

    protected String getDefaultBaseFolderName() {
        return getAlias();
    }

    protected String getDefaultRootFolderName() {
        return getAlias();
    }

    protected String getDefaultRootPackageName() {
        return getAlias();
    }

    protected String getDefaultSecurityRealmName() {
        return getAlias() + "-" + getSecurityRealmType().name().toLowerCase() + "-" + "realm";
    }

    protected String getDefaultRoleBasedAccessControllerName() {
        return getSecurityRealmType().name();
    }

}
