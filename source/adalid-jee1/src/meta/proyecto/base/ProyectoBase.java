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
public abstract class ProyectoBase extends Project {

    private final Logger logger = Logger.getLogger(Project.class);

    protected static final String PLATAFORMA_BASE = "jee1ap101";

    protected static final String PLATAFORMA_NETBEANS_POSTGRESQL_GLASSFISH = "jee1af101";

    protected static final String PLATAFORMA_NETBEANS_POSTGRESQL_JBOSS = "jee1af102";

    protected static final String PLATAFORMA_ECLIPSE_POSTGRESQL_GLASSFISH = "jee1af201";

    protected static final String PLATAFORMA_ECLIPSE_POSTGRESQL_JBOSS = "jee1af202";

    protected static final String VERSION_JAVA = "java.version";

    protected static final String VERSION_GLASSFISH = "glassfish.version";

    protected static final String VERSION_JBOSS = "jboss.version";

    protected static final String VERSION_POSTGRESQL = "postgresql.version";

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

    public ProyectoBase() {
        super();
        setUserEntityClass(meta.entidad.comun.control.acceso.Usuario.class);
        getSingularPlatforms().add(PLATAFORMA_BASE);
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
    private static final String BASE_NAME = ProyectoBase.class.getName();

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

    private boolean _internetAccessAllowed;

    private String _baseFolderName;

    private String _databaseName;

    private String _rootFolderName;

    private String _rootPackageName;

    private String _securityRealmName;

    private String _roleBasedAccessControllerName;

    private SecurityRealmType _securityRealmType = SecurityRealmType.JDBC;

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
            } else {
                if (aliases.add(alias)) {
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
     * @return true if internet access is allowed; false otherwise
     */
    public boolean isInternetAccessAllowed() {
        return _internetAccessAllowed;
    }

    /**
     * @param internetAccessAllowed true if internet access is allowed; false otherwise
     */
    public void setInternetAccessAllowed(boolean internetAccessAllowed) {
        _internetAccessAllowed = internetAccessAllowed;
    }

    /**
     * @return the base folder name
     */
    public String getBaseFolderName() {
        return StringUtils.defaultIfBlank(_baseFolderName, getDefaultFolderName());
    }

    /**
     * @param baseFolderName the base folder name to set
     */
    public void setBaseFolderName(String baseFolderName) {
        _baseFolderName = baseFolderName;
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
        _databaseName = databaseName;
    }

    /**
     * @return the root folder name
     */
    public String getRootFolderName() {
        return StringUtils.defaultIfBlank(_rootFolderName, getDefaultFolderName());
    }

    /**
     * @param rootFolderName the root folder name to set
     */
    public void setRootFolderName(String rootFolderName) {
        _rootFolderName = rootFolderName;
    }

    /**
     * @return the root package name
     */
    public String getRootPackageName() {
        return StringUtils.defaultIfBlank(_rootPackageName, getDefaultPackageName());
    }

    /**
     * @param rootPackageName the root package name to set
     */
    public void setRootPackageName(String rootPackageName) {
        _rootPackageName = rootPackageName;
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
        _securityRealmName = securityRealmName;
    }

    /**
     * @return the role-based-access-controller (RBAC) name
     */
    public String getRoleBasedAccessControllerName() {
        return StringUtils.defaultIfBlank(_roleBasedAccessControllerName, "$LOWER_CASE_CODE");
    }

    /**
     * @param roleBasedAccessControllerName the role-based-access-controller (RBAC) name to set
     */
    public void setRoleBasedAccessControllerName(String roleBasedAccessControllerName) {
        _roleBasedAccessControllerName = roleBasedAccessControllerName;
    }

    /**
     * @return the security realm type
     */
    public SecurityRealmType getSecurityRealmType() {
        return _securityRealmType;
    }

    /**
     * @param securityRealmType the security realm type to set
     */
    public void setSecurityRealmType(SecurityRealmType securityRealmType) {
        _securityRealmType = securityRealmType;
    }

    protected String getDefaultDatabaseName() {
        String string = StringUtils.defaultIfBlank(getAlias(), getName());
        return StrUtils.getLowerCaseIdentifier(string, '-');
    }

    protected String getDefaultFolderName() {
        String string = StringUtils.defaultIfBlank(getAlias(), getName());
        return StrUtils.getLowerCaseIdentifier(string, '-');
    }

    protected String getDefaultPackageName() {
        String string = StringUtils.defaultIfBlank(getAlias(), getName());
        return StrUtils.getLowerCaseIdentifier(string, '.');
    }

    protected String getDefaultSecurityRealmName() {
        String string = StringUtils.defaultIfBlank(getAlias(), getName());
        String prefix = StrUtils.getLowerCaseIdentifier(string, '-');
        String srtype = _securityRealmType == null ? "jdbc" : _securityRealmType.name().toLowerCase();
        String suffix = "realm";
        return prefix + "-" + srtype + "-" + suffix;
    }

}
