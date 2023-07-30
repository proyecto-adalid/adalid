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

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.jee.JavaWebProject;
import adalid.core.programmers.*;
import adalid.jee2.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import meta.entidad.comun.configuracion.basica.Aplicacion;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.Dominio;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.enumeracion.base.TipoModuloBase;
import meta.modulo.base.ModuloConsulta;
import meta.modulo.base.ModuloProcesamiento;
import meta.modulo.base.ModuloRegistro;
import meta.paquete.base.PaqueteBase;
import meta.paquete.comun.PaqueteConsultaRecursosBasicos;
import meta.paquete.comun.PaqueteProcesamientoRecursosBasicos;
import meta.paquete.comun.PaqueteRegistroRecursosBasicos;
import meta.psm.ProjectAttributeKeys;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoJava1 extends ProyectoBase implements JavaWebProject {

    private static final Logger logger = Logger.getLogger(Project.class);

    protected static final boolean TYPELESS_REALM_NAME = true;

    /**
     * Ruta del directorio de contenido estático de la aplicación en Linux. El directorio debe existir antes de definir las propiedades del sistema en
     * el servidor de aplicaciones y el servidor de aplicaciones se debe configurar para utilizar este directorio en lugar del predeterminado.
     */
    protected static final String CONTENT_ROOT_DIR_LINUX = "content.root.dir.linux";

    /**
     * Ruta del directorio de contenido estático de la aplicación en Windows. El directorio debe existir antes de definir las propiedades del sistema
     * en el servidor de aplicaciones y el servidor de aplicaciones se debe configurar para utilizar este directorio en lugar del predeterminado.
     * Utilice / como separador en lugar de \. Por ejemplo: C:/xyz2ap101/content-root
     */
    protected static final String CONTENT_ROOT_DIR_WINDOWS = "content.root.dir.windows";

    /**
     * Ruta del directorio HOME de la aplicación en Linux generada con la plataforma de solo base de datos. El directorio debe existir antes de
     * definir las propiedades del sistema en el servidor de aplicaciones y el servidor de aplicaciones se debe configurar para utilizar este
     * directorio en lugar del predeterminado.
     */
    protected static final String DATABASE_HOME_DIR_LINUX = "database.home.dir.linux";

    /**
     * Ruta del directorio HOME de la aplicación en Windows generada con la plataforma de solo base de datos. El directorio debe existir antes de
     * definir las propiedades del sistema en el servidor de aplicaciones y el servidor de aplicaciones se debe configurar para utilizar este
     * directorio en lugar del predeterminado. Utilice / como separador en lugar de \. Por ejemplo: C:/xyz2ap101/database-home
     */
    protected static final String DATABASE_HOME_DIR_WINDOWS = "database.home.dir.windows";

    /**
     * Ruta del directorio HOME de Java en Linux
     */
    protected static final String JAVA_HOME_DIR_LINUX = "java.home.dir.linux";

    /**
     * Ruta del directorio HOME de Java en Windows
     */
    protected static final String JAVA_HOME_DIR_WINDOWS = "java.home.dir.windows";

    /**
     * Versión de Java
     */
    protected static final String JAVA_VERSION = "java.version";

    /**
     * Versión de Java
     */
    protected static final String VERSION_JAVA = JAVA_VERSION;

    protected static final WebLoginAuthMethod[] SUPPORTED_WEB_API_AUTH_METHODS = {
        WebLoginAuthMethod.BASIC,
        WebLoginAuthMethod.KEYCLOAK,
        WebLoginAuthMethod.OIDC
    };

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

    public ProyectoJava1() {
        super();
        initializeImageFiles();
        initializeJobSchedules();
    }

    protected ModuloConsulta consulta;

    protected ModuloProcesamiento procesamiento;

    protected ModuloRegistro registro;

    protected PaqueteConsultaRecursosBasicos consultaRecursosBasicos;

    protected PaqueteProcesamientoRecursosBasicos procesamientoRecursosBasicos;

    protected PaqueteRegistroRecursosBasicos registroRecursosBasicos;

    protected final ImageFile pageBookmarkIcon = new ImageFile("PBI");

    protected final ImageFile headerLeftBanner = new ImageFile("HLB");

    protected final ImageFile headerRightBanner = new ImageFile("HRB");

    protected final ImageFile welcomePageBanner = new ImageFile("WPB");

    protected final ImageFile changePasswordBanner = new ImageFile("CPB");

    protected final ImageFile helpPageLogo = new ImageFile("HPL");

    private final List<ImageFile> _headerBannerList = new ArrayList<>();

    private final List<String> _predefinedHeaderBannerNames = new ArrayList<>();

    protected final JobSchedule taskNotifierSchedule = new JobSchedule("taskNotifierSchedule");

    protected final JobSchedule dailyProcessSchedule = new JobSchedule("dailyProcessSchedule");

    protected final JobSchedule weeklyProcessSchedule = new JobSchedule("weeklyProcessSchedule");

    protected final JobSchedule monthlyProcessSchedule = new JobSchedule("monthlyProcessSchedule");

    private final List<JobSchedule> _jobScheduleList = new ArrayList<>();

    private final List<String> _predefinedJobScheduleNames = new ArrayList<>();

    private void initializeImageFiles() {
        addImageFile(pageBookmarkIcon);
        addImageFile(headerLeftBanner);
        addImageFile(headerRightBanner);
        addImageFile(welcomePageBanner);
        addImageFile(changePasswordBanner);
        addImageFile(helpPageLogo);
        /**/
        pageBookmarkIcon.path = "/resources/images/base/favicon.png";
        pageBookmarkIcon.height = 16;
        /**/
        headerLeftBanner.path = "/resources/images/base/index_hlb.png";
        headerLeftBanner.height = 36;
        /**/
        headerRightBanner.path = "/resources/images/base/index_hrb.png";
        headerRightBanner.height = 36;
        /**/
        welcomePageBanner.path = "/resources/images/base/index.png";
        welcomePageBanner.height = 250;
        /**/
        changePasswordBanner.path = "/resources/images/base/index.png";
        changePasswordBanner.width = 250;
        /**/
        helpPageLogo.path = "/resources/images/base/logo.png";
        helpPageLogo.height = 60;
        /**/
    }

    /**
     * @return the header banner list
     */
    public List<ImageFile> getImageFileList() {
        return _headerBannerList;
    }

    private void addImageFile(ImageFile imageFile) {
        _headerBannerList.add(imageFile);
        _predefinedHeaderBannerNames.add(imageFile.getImageFileName());
    }

    private void initializeJobSchedules() {
        addJobSchedule(taskNotifierSchedule);
        addJobSchedule(dailyProcessSchedule);
        addJobSchedule(weeklyProcessSchedule);
        addJobSchedule(monthlyProcessSchedule);
//      @Schedule(hour = "*", minute = "*/15", persistent = false) // cada cuarto de hora
        taskNotifierSchedule.hour = "*";
        taskNotifierSchedule.minute = "*/15";
        taskNotifierSchedule.second = "30";
//      @Schedule(hour = "4") // todos los dias, a las 4:00 AM
        dailyProcessSchedule.hour = "4";
//      @Schedule(dayOfWeek = "Mon", hour = "2") // los lunes, a las 2:00 AM
        weeklyProcessSchedule.hour = "2";
        weeklyProcessSchedule.dayOfWeek = "Mon";
//      @Schedule(dayOfMonth = "1", hour = "0") // los primeros de mes, a las 12:00 AM
        monthlyProcessSchedule.hour = "0";
        monthlyProcessSchedule.dayOfMonth = "1";
    }

    /**
     * @return the job schedule list
     */
    public List<JobSchedule> getJobScheduleList() {
        return _jobScheduleList;
    }

    private void addJobSchedule(JobSchedule jobSchedule) {
        _jobScheduleList.add(jobSchedule);
        _predefinedJobScheduleNames.add(jobSchedule.getJobScheduleName());
    }

    /**
     * El método addJobSchedule permite agregar horarios adicionales para que sean incluidos en la aplicación generada. Después de agregar un horario,
     * éste se puede configurar de la misma forma que los horarios predefinidos.
     *
     * El método addJobSchedule se debe ejecutar en el método configureGenerator del proyecto maestro.
     *
     * @param name nombre del horario; debe ser un identificador válido de Java y no puede ser el nombre de un horario predefinido. A partir del
     * nombre del horario se obtiene el nombre de la correspondiente clase generada.
     * @return el horario agregado
     */
    protected JobSchedule addJobSchedule(String name) {
        int size = 1 + _jobScheduleList.size();
        String scheduleName = "JobSchedule" + size;
        String remarks = "; the name of the added job schedule will be \"" + scheduleName + "\"";
        String givenName = StringUtils.trimToNull(name);
        if (givenName == null) {
            logger.warn("the argument to addJobSchedule is null" + remarks);
        } else if (!givenName.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
            logger.warn("\"" + givenName + "\" is an invalid job schedule name" + remarks);
        } else if (_predefinedJobScheduleNames.stream().anyMatch(givenName::equalsIgnoreCase)) {
            logger.warn("\"" + givenName + "\" is a predefined job schedule name" + remarks);
        } else {
            scheduleName = givenName;
        }
        String previousName;
        for (JobSchedule jobSchedule : _jobScheduleList) {
            previousName = jobSchedule.getJobScheduleName();
            if (scheduleName.equalsIgnoreCase(previousName)) {
                logger.warn("job schedule \"" + previousName + "\" has already been added to the list; reusing the previously added one");
                return jobSchedule;
            }
        }
        JobSchedule jobSchedule = new JobSchedule(scheduleName);
        _jobScheduleList.add(jobSchedule);
        return jobSchedule;
    }

    @Override
    public boolean analyze() {
        boolean analyzed = super.analyze();
        analyzed &= analyzePackages();
        return analyzed;
    }

    private String _addOnsProjectName;

    private String _earProjectName;

    private String _ejbProjectName;

    private String _libProjectName;

    private String _libDirProjectName;

    private String _resourcesProjectName;

    private String _webProjectName;

    private String _webApiProjectName;

    private String _rootPackageName;

    private String _persistenceRootPackageName;

    private String _googleRecaptchaSiteKey;

    private String _googleRecaptchaSecretKey;

    private int _projectChangeForgottenPasswordTimeout = 15;

    private boolean _multiApplication;

    private boolean _flushAfterEachInsert = true;

    private boolean _flushAfterEachUpdate = true;

    private boolean _flushAfterEachDelete = true;

    private boolean _internetAccessAllowed;

    private boolean _webServicesEnabled;

    private boolean _projectMailingEnabled;

    private boolean _projectMailDebuggingEnabled;

    private boolean _projectTextingEnabled;

    private boolean _projectTextDebuggingEnabled;

    private boolean _projectRecaptchaEnabled;

    private boolean _projectRecaptchaSiteVerificationEnabled;

    private boolean _projectAssignNewPasswordEnabled = true;

    private boolean _projectChangeOwnPasswordEnabled = true;

    private boolean _projectChangeForgottenPasswordEnabled = true;

    private boolean _exporterShellEnabled;

    private boolean _reporterShellEnabled;

    private boolean _sqlAgentShellEnabled = true;

    private Boolean _operatingSystemShellKeepTempFiles;

    private ShortMessageServiceProvider _defaultSMSProvider = ShortMessageServiceProvider.UNSPECIFIED;

    private ProjectStage _projectStage;

    private WebLoginAuthMethod _webAuthMethod = WebLoginAuthMethod.FORM;

    private WebLoginAuthMethod _webApiAuthMethod = WebLoginAuthMethod.BASIC;

    private SecurityRealmType _securityRealmType = SecurityRealmType.JDBC;

    private String _securityRealmName;

    private String _roleBasedAccessControllerName;

    private String _webSecurityPrefix;

    private boolean _authenticatedUserAutomaticRegistrationEnabled = true; // enable by default for LDAP and CIAM

    private boolean _authenticatedUserAutomaticSynchronizationEnabled = true; // enable by default for LDAP and CIAM

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

    public void addBeanAttribute(String classSimpleName) {
        addBeanAttribute(classSimpleName, null);
    }

    public void addBeanAttribute(String classSimpleName, String beanName) {
        final String key = "bean.name";
        if (StringUtils.isNotBlank(classSimpleName)) {
            String cn = StringUtils.capitalize(classSimpleName.trim());
            String bn = StringUtils.uncapitalize(StringUtils.defaultIfBlank(StringUtils.trim(beanName), cn));
            addAttribute(cn + "." + key, bn);
        }
    }

    /**
     * @return the add-ons project name
     */
    public String getAddOnsProjectName() {
        return StringUtils.defaultIfBlank(_addOnsProjectName, getDefaultAddOnsProjectName());
    }

    /**
     * @param addOnsProjectName the add-ons project name to set
     */
    public void setAddOnsProjectName(String addOnsProjectName) {
        _addOnsProjectName = StrUtils.getLowerCaseIdentifier(addOnsProjectName, '-');
    }

    /**
     * @return the ear project name
     */
    @Override
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
    @Override
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
     * @return the lib-dir project name
     */
    public String getLibDirProjectName() {
        return StringUtils.defaultIfBlank(_libDirProjectName, getDefaultLibDirProjectName());
    }

    /**
     * @param libDirProjectName the lib-dir project name to set
     */
    public void setLibDirProjectName(String libDirProjectName) {
        _libDirProjectName = StrUtils.getLowerCaseIdentifier(libDirProjectName, '-');
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
    @Override
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
     * @return the web-api project name
     */
    @Override
    public String getWebApiProjectName() {
        return StringUtils.defaultIfBlank(_webApiProjectName, getDefaultWebApiProjectName());
    }

    /**
     * @param webApiProjectName the web-api project name to set
     */
    public void setWebApiProjectName(String webApiProjectName) {
        _webApiProjectName = StrUtils.getLowerCaseIdentifier(webApiProjectName, '-');
    }

    /**
     * @return the web project pages extension
     */
    @Override
    public String getWebPageFileExtension() {
        return StringUtils.defaultIfBlank(getStringAttribute(ProjectAttributeKeys.WEB_PAGES_FILE_EXTENSION), "xhtml");
    }

    /**
     * @return the root package name
     */
    public String getRootPackageName() {
        return StringUtils.defaultIfBlank(_rootPackageName, getDefaultRootPackageName());
    }

    /**
     * @param packageName the root package name to set
     */
    public void setRootPackageName(String packageName) {
        _rootPackageName = StrUtils.getLowerCaseIdentifier(packageName, '.');
    }

    /**
     * @return the persistence root package name to set
     */
    public String getPersistenceRootPackageName() {
        return StringUtils.defaultIfBlank(_persistenceRootPackageName, getDefaultPersistenceRootPackageName());
    }

    /**
     * @param packageName the persistence root package name to set
     */
    public void setPersistenceRootPackageName(String packageName) {
        _persistenceRootPackageName = StrUtils.getLowerCaseIdentifier(packageName, '.');
    }

    /**
     * @return the Google reCAPTCHA site key
     */
    public String getGoogleRecaptchaSiteKey() {
        return StringUtils.defaultIfBlank(_googleRecaptchaSiteKey, getDefaultGoogleRecaptchaSiteKey());
    }

    /**
     * @param key the Google reCAPTCHA site key to set
     */
    public void setGoogleRecaptchaSiteKey(String key) {
        _googleRecaptchaSiteKey = key;
    }

    /**
     * @return the Google reCAPTCHA secret key
     */
    public String getGoogleRecaptchaSecretKey() {
        return StringUtils.defaultIfBlank(_googleRecaptchaSecretKey, getDefaultGoogleRecaptchaSecretKey());
    }

    /**
     * @param key the Google reCAPTCHA secret key to set
     */
    public void setGoogleRecaptchaSecretKey(String key) {
        _googleRecaptchaSecretKey = key;
    }

    /**
     * @return the single-application project indicator
     */
//  @Override
    public boolean isSingleApplication() {
        return !isMultiApplication();
    }

    /**
     * @return the multi-application project indicator
     */
    @Override
    public boolean isMultiApplication() {
        return _multiApplication;
    }

    /**
     * El método setMultiApplication se utiliza para especificar si el proyecto generado puede utilizar, o no, vistas (páginas) de aplicaciones
     * empresariales de otros proyectos. Por lo general, el módulo Web de un proyecto contiene todas las vistas correspondientes a las entidades
     * referenciadas en el proyecto; pero también podría utilizar vistas que se encuentren en el módulo Web de la aplicación empresarial de otro
     * proyecto.
     *
     * @param multiple true para permitir el uso de vistas (páginas) de aplicaciones empresariales de otros proyectos
     */
    protected void setMultiApplication(boolean multiple) {
        _multiApplication = multiple;
    }

    /**
     * @return true if flush must be executed after each insert; false otherwise
     */
    public boolean isFlushAfterEachInsert() {
        return _flushAfterEachInsert;
    }

    /**
     * El método setFlushAfterEachInsert se utiliza para especificar si el proyecto generado debe sincronizar, o no, el contexto de persistencia
     * después de cada persist correspondiente al insert de una fila en una página de CRUD. El valor predeterminado de esta propiedad es true (se
     * sincroniza después de cada persist).
     *
     * @param enabled true, si el proyecto generado debe sincronizar el contexto de persistencia después de cada persist; de lo contrario false.
     */
    public void setFlushAfterEachInsert(boolean enabled) {
        _flushAfterEachInsert = enabled;
    }

    /**
     * @return true if flush must be executed after each update; false otherwise
     */
    public boolean isFlushAfterEachUpdate() {
        return _flushAfterEachUpdate;
    }

    /**
     * El método setFlushAfterEachUpdate se utiliza para especificar si el proyecto generado debe sincronizar, o no, el contexto de persistencia
     * después de cada merge correspondiente al update en una fila en una página de CRUD. El valor predeterminado de esta propiedad es true (se
     * sincroniza después de cada merge).
     *
     * @param enabled true, si el proyecto generado debe sincronizar el contexto de persistencia después de cada merge; de lo contrario false.
     */
    public void setFlushAfterEachUpdate(boolean enabled) {
        _flushAfterEachUpdate = enabled;
    }

    /**
     * @return true if flush must be executed after each delete; false otherwise
     */
    public boolean isFlushAfterEachDelete() {
        return _flushAfterEachDelete;
    }

    /**
     * El método setFlushAfterEachDelete se utiliza para especificar si el proyecto generado debe sincronizar, o no, el contexto de persistencia
     * después de cada remove correspondiente al delete de una fila en una página de CRUD. El valor predeterminado de esta propiedad es true (se
     * sincroniza después de cada remove).
     *
     * @param enabled true, si el proyecto generado debe sincronizar el contexto de persistencia después de cada remove; de lo contrario false.
     */
    public void setFlushAfterEachDelete(boolean enabled) {
        _flushAfterEachDelete = enabled;
    }

    /**
     * @return true if internet access should be allowed; false otherwise
     */
    public boolean isInternetAccessAllowed() {
        return _internetAccessAllowed;
    }

    /**
     * El método setInternetAccessAllowed se utiliza para especificar si el proyecto generado incluye, o no, características que requieren acceso a
     * internet para su funcionamiento (por ejemplo, si la página Inicio de Sesión incluye, o no, un elemento Google reCAPTCHA). El valor
     * predeterminado de esta propiedad es false (no se incluyen características que requieren acceso a internet).
     *
     * @param allowed true, si el proyecto generado incluye características que requieren acceso a internet; de lo contrario false.
     */
    public void setInternetAccessAllowed(boolean allowed) {
        _internetAccessAllowed = allowed;
    }

    /**
     * @return true if web services should be disabled; false otherwise
     */
    public boolean isWebServicesDisabled() {
        return !isWebServicesEnabled();
    }

    /**
     * @return true if web services should be enabled; false otherwise
     */
    public boolean isWebServicesEnabled() {
        return _webServicesEnabled;
    }

    /**
     * El método setWebServicesEnabled se utiliza para especificar si el proyecto generado incluye, o no, servicios web. El valor predeterminado de
     * esta propiedad es false (no se incluyen servicios web). La generación de los servicios para ejecutar operaciones de negocio también depende del
     * elemento <b>bws</b> de la anotación <b>EntityCodeGen</b> de cada entidad, y del elemento <b>serviceable</b> de la anotación
     * <b>ProcessOperationClass</b> de cada operación. La generación de los servicios para ejecutar operaciones de consulta y registro (CRUD) también
     * depende del elemento <b>fws</b> de la anotación <b>EntityCodeGen</b> de cada entidad.
     *
     * @param enabled true, si el proyecto generado incluye servicios web; de lo contrario false.
     */
    public void setWebServicesEnabled(boolean enabled) {
        _webServicesEnabled = enabled;
    }

    /**
     * @return true if project mailing should be enabled; false otherwise
     */
    public boolean isProjectMailingEnabled() {
        return _projectMailingEnabled;
    }

    /**
     * El método setProjectMailingEnabled se utiliza para especificar si el proyecto generado incluye, o no, características que requieren acceso a un
     * servidor de correo electrónico (e-mail) para su funcionamiento (por ejemplo, si las notificaciones de tareas se envían, o no, por e-mail). El
     * valor predeterminado de esta propiedad es false (no se incluyen características que requieren acceso a un servidor de e-mail).
     *
     * @param enabled true, si el proyecto generado incluye características que requieren acceso a un servidor de e-mail; de lo contrario false.
     */
    public void setProjectMailingEnabled(boolean enabled) {
        _projectMailingEnabled = enabled;
    }

    /**
     * @return true if project mail debugging should be enabled; false otherwise
     */
    public boolean isProjectMailDebuggingEnabled() {
        return _projectMailDebuggingEnabled;
    }

    /**
     * El método setProjectMailDebuggingEnabled se utiliza para especificar si el proyecto generado incluye, o no, los métodos de depuración de
     * características que requieren acceso a un servidor de correo electrónico (e-mail) para su funcionamiento (por ejemplo, si las notificaciones de
     * tareas se envían, o no, por e-mail). El valor predeterminado de esta propiedad es false (no se incluyen opciones de depuración).
     *
     * @param enabled true, si el proyecto generado incluye características que requieren acceso a un servidor de e-mail; de lo contrario false.
     */
    public void setProjectMailDebuggingEnabled(boolean enabled) {
        _projectMailDebuggingEnabled = enabled;
    }

    /**
     * @return true if project texting should be enabled; false otherwise
     */
    public boolean isProjectTextingEnabled() {
        return _projectTextingEnabled;
    }

    /**
     * El método setProjectTextingEnabled se utiliza para especificar si el proyecto generado incluye, o no, características que requieren acceso a un
     * servidor de mensajería de texto (SMS) para su funcionamiento (por ejemplo, si las notificaciones de tareas se envían, o no, por SMS). El valor
     * predeterminado de esta propiedad es false (no se incluyen características que requieren acceso a un servidor de SMS).
     *
     * @param enabled true, si el proyecto generado incluye características que requieren acceso a un servidor de SMS; de lo contrario false.
     */
    public void setProjectTextingEnabled(boolean enabled) {
        _projectTextingEnabled = enabled;
    }

    /**
     * @return true if project text debugging should be enabled; false otherwise
     */
    public boolean isProjectTextDebuggingEnabled() {
        return _projectTextDebuggingEnabled;
    }

    /**
     * El método setProjectTextDebuggingEnabled se utiliza para especificar si el proyecto generado incluye, o no, los métodos de depuración de
     * características que requieren acceso a un servidor de mensajería de texto (SMS) para su funcionamiento (por ejemplo, si las notificaciones de
     * tareas se envían, o no, por SMS). El valor predeterminado de esta propiedad es false (no se incluyen opciones de depuración).
     *
     * @param enabled true, si el proyecto generado incluye características que requieren acceso a un servidor de SMS; de lo contrario false.
     */
    public void setProjectTextDebuggingEnabled(boolean enabled) {
        _projectTextDebuggingEnabled = enabled;
    }

    /**
     * @return true if project recaptcha should be enabled; false otherwise
     */
    public boolean isProjectRecaptchaEnabled() {
        return _internetAccessAllowed && _projectRecaptchaEnabled;
    }

    /**
     * El método setProjectRecaptchaEnabled se utiliza para especificar si el proyecto generado utiliza, o no, Google reCAPTCHA. El valor
     * predeterminado de esta propiedad es false (no se utiliza Google reCAPTCHA).
     *
     * @param enabled true, si el proyecto generado utiliza Google reCAPTCHA; de lo contrario false.
     */
    public void setProjectRecaptchaEnabled(boolean enabled) {
        _projectRecaptchaEnabled = enabled;
    }

    /**
     * @return true if project recaptcha site verification should be enabled; false otherwise
     */
    public boolean isProjectRecaptchaSiteVerificationEnabled() {
        return _internetAccessAllowed && _projectRecaptchaEnabled && _projectRecaptchaSiteVerificationEnabled;
    }

    /**
     * @return true if project recaptcha site verification should be enabled; false otherwise
     */
    public boolean isProjectRecaptchaSiteVerificationDisabled() {
        return !isProjectRecaptchaSiteVerificationEnabled();
    }

    /**
     * El método setProjectRecaptchaSiteVerificationEnabled se utiliza para especificar si el proyecto generado debe o no hacer la verificación del
     * servidor cuando utiliza Google reCAPTCHA. El valor predeterminado de esta propiedad es false (no se debe hacer la verificación del servidor).
     *
     * @param enabled true, si el proyecto generado debe hacer la verificación del servidor cuando utiliza Google reCAPTCHA; de lo contrario false.
     */
    public void setProjectRecaptchaSiteVerificationEnabled(boolean enabled) {
        _projectRecaptchaSiteVerificationEnabled = enabled;
    }

    /**
     * @return true if project assign new password feature should be enabled; false otherwise
     */
    public boolean isProjectAssignNewPasswordEnabled() {
        return _projectAssignNewPasswordEnabled && SecurityRealmType.JDBC.equals(getSecurityRealmType());
    }

    /**
     * El método setProjectAssignNewPasswordEnabled se utiliza para especificar si el proyecto generado debe tener habilitada, o no, la función de
     * asignación de contraseñas de usuario. Esa función permite, a los usuarios debidamente autorizados, asignar una nueva contraseña a otro usuario,
     * sin tener que conocer la contraseña actual. Este atributo es relevante solo si el tipo de dominio de seguridad del proyecto es JDBC, en cuyo
     * caso su valor predeterminado es true (función habilitada).
     *
     * @param enabled true, si el proyecto generado debe tener habilitada la función de asignación de contraseñas de usuario; de lo contrario false.
     */
    public void setProjectAssignNewPasswordEnabled(boolean enabled) {
        _projectAssignNewPasswordEnabled = enabled;
    }

    /**
     * @return true if project change own password feature should be enabled; false otherwise
     */
    public boolean isProjectChangeOwnPasswordEnabled() {
        return _projectChangeOwnPasswordEnabled && SecurityRealmType.JDBC.equals(getSecurityRealmType());
    }

    /**
     * El método setProjectChangeOwnPasswordEnabled se utiliza para especificar si el proyecto generado debe tener habilitada, o no, la función de
     * cambio de las propias contraseñas de usuario. Esa función permite a cada usuario cambiar su propia contraseña, pero solo si recuerda su
     * contraseña actual. Este atributo es relevante solo si el tipo de dominio de seguridad del proyecto es JDBC, en cuyo caso su valor
     * predeterminado es true (función habilitada).
     *
     * @param enabled true, si el proyecto generado debe tener habilitada la función de cambio de las propias contraseñas de usuario; de lo contrario
     * false.
     */
    public void setProjectChangeOwnPasswordEnabled(boolean enabled) {
        _projectChangeOwnPasswordEnabled = enabled;
    }

    /**
     * @return true if project change forgotten password feature should be enabled; false otherwise
     */
    public boolean isProjectChangeForgottenPasswordEnabled() {
        return _projectChangeForgottenPasswordEnabled && _internetAccessAllowed && _projectMailingEnabled && SecurityRealmType.JDBC.equals(getSecurityRealmType());
    }

    /**
     * El método setProjectChangeForgottenPasswordEnabled se utiliza para especificar si el proyecto generado debe tener habilitada, o no, la función
     * de cambio de contraseñas olvidadas de usuario. Esa función permite, a cada usuario que tenga registrada una cuenta de correo electrónico,
     * cambiar su propia contraseña, sin tener que recordar su contraseña actual; para ello requiere acceso a algún servidor de correo electrónico a
     * través de Internet. Este atributo es relevante solo si el tipo de dominio de seguridad del proyecto es JDBC, en cuyo caso su valor
     * predeterminado es true (función habilitada).
     *
     * @param enabled true, si el proyecto generado debe tener habilitada la función de cambio de contraseñas olvidadas de usuario; de lo contrario
     * false.
     */
    public void setProjectChangeForgottenPasswordEnabled(boolean enabled) {
        _projectChangeForgottenPasswordEnabled = enabled;
    }

    /**
     * @return change forgotten password timeout
     */
    public int getProjectChangeForgottenPasswordTimeout() {
        return _projectChangeForgottenPasswordTimeout;
    }

    /**
     * El método setProjectChangeForgottenPasswordTimeout se utiliza para especificar el tiempo de espera, expresado en minutos, de la función de
     * cambio de contraseñas olvidadas de usuario. El tiempo de espera predeterminado es de 15 minutos.
     *
     * @param timeout tiempo de espera, expresado en minutos. Debe ser un número entre 5 y 60. Si es menor que 5 se utiliza 5; si es mayor que 60 se
     * utiliza 60.
     */
    public void setProjectChangeForgottenPasswordTimeout(int timeout) {
        _projectChangeForgottenPasswordTimeout = timeout < 5 ? 5 : timeout > 60 ? 60 : timeout;
    }

    /**
     * @return true if exporter shell should be enabled; false otherwise
     */
    public boolean isExporterShellEnabled() {
        return _exporterShellEnabled;
    }

    /**
     * El método setExporterShellEnabled se utiliza para especificar si el proyecto generado puede utilizar, o no, procesos nativos del sistema
     * operativo para exportar archivos. El valor predeterminado de esta propiedad es false (no se pueden utilizar procesos nativos).
     *
     * @param enabled true, si el proyecto generado utiliza procesos nativos del sistema operativo para exportar archivos; de lo contrario false.
     */
    public void setExporterShellEnabled(boolean enabled) {
        _exporterShellEnabled = enabled;
    }

    /**
     * @return true if reporter shell should be enabled; false otherwise
     */
    public boolean isReporterShellEnabled() {
        return _reporterShellEnabled;
    }

    /**
     * El método setReporterShellEnabled se utiliza para especificar si el proyecto generado puede utilizar, o no, procesos nativos del sistema
     * operativo para producir informes. El valor predeterminado de esta propiedad es false (no se pueden utilizar procesos nativos).
     *
     * @param enabled true, si el proyecto generado utiliza procesos nativos del sistema operativo para producir informes; de lo contrario false.
     */
    public void setReporterShellEnabled(boolean enabled) {
        _reporterShellEnabled = enabled;
    }

    /**
     * @return true if sql agent shell should be enabled; false otherwise
     */
    public boolean isSqlAgentShellEnabled() {
        return _sqlAgentShellEnabled;
    }

    /**
     * El método setSqlAgentShellEnabled se utiliza para especificar si el proyecto generado puede utilizar, o no, procesos nativos del sistema
     * operativo para ejecutar procedimientos almacenados en la base de datos. El valor predeterminado de esta propiedad es true (se pueden utilizar
     * procesos nativos).
     *
     * @param enabled true, si el proyecto generado utiliza procesos nativos del sistema operativo para ejecutar procedimientos almacenados en la base
     * de datos; de lo contrario false.
     */
    public void setSqlAgentShellEnabled(boolean enabled) {
        _sqlAgentShellEnabled = enabled;
    }

    /**
     * @return true if temporary files should be kept
     */
    public boolean getOperatingSystemShellKeepTempFiles() {
        return BitUtils.valueOf(_operatingSystemShellKeepTempFiles, getDefaultOperatingSystemShellKeepTempFiles());
    }

    /**
     * El método setOperatingSystemShellKeepTempFiles se utiliza para especificar si los procesos nativos del sistema operativo conservan o eliminan
     * sus archivos temporales al finalizar. El valor predeterminado de esta propiedad es true en ambientes de desarrollo y pruebas, y false en los
     * demás ambientes.
     *
     * @param keep true, si los procesos nativos del sistema operativo deben conservar sus archivos temporales al finalizar; de lo contrario false.
     */
    public void setOperatingSystemShellKeepTempFiles(Boolean keep) {
        _operatingSystemShellKeepTempFiles = keep;
    }

    /**
     * @return the default SMS provider
     */
    public ShortMessageServiceProvider getDefaultSMSProvider() {
        return specified(_defaultSMSProvider, getDefaultShortMessageServiceProvider());
    }

    public boolean defaultSMSProviderIs(String name) {
        return getDefaultSMSProvider().name().equals(name);
    }

    public boolean defaultSMSProviderIsNot(String name) {
        return !defaultSMSProviderIs(name);
    }

    /**
     * El método setDefaultSMSProvider se utiliza para especificar el proveedor de mensajería de texto predeterminado. El valor puede ser cualquiera
     * de los elementos de la enumeración ShortMessageServiceProvider, es decir: UNSPECIFIED, TELEMO_GROUP o TWILIO. El valor predeterminado de esta
     * propiedad es TWILIO.
     *
     * @param provider proveedor de mensajería de texto predeterminado.
     */
    public void setDefaultSMSProvider(ShortMessageServiceProvider provider) {
        _defaultSMSProvider = provider != null ? provider : ShortMessageServiceProvider.UNSPECIFIED;
    }

    /**
     * @return the project stage
     */
    public ProjectStage getProjectStage() {
        return _projectStage == null ? getDefaultProjectStage() : _projectStage;
    }

    /**
     * El método setProjectStage se utiliza para especificar la etapa en la que se encuentra el proyecto generado. El valor puede ser cualquiera de
     * los elementos de la enumeración ProjectStage, es decir: DEVELOPMENT, TESTING, ACCEPTANCE, o PRODUCTION. El valor predeterminado de esta
     * propiedad es DEVELOPMENT.
     *
     * @param stage etapa en la que se encuentra el proyecto generado; especifique DEVELOPMENT, TESTING, ACCEPTANCE, o PRODUCTION si el proyecto está
     * en Desarrollo, Pruebas, Pruebas de Aceptación o Producción, respectivamente.
     */
    public void setProjectStage(ProjectStage stage) {
        _projectStage = stage;
    }

    /**
     * @param server application server name (GlassFish, JBoss or WildFly)
     * @return the Web module authentication mechanism
     */
    public String getWebAuthMethod(String server) {
        boolean adaptado = StringUtils.equalsIgnoreCase(server, "JBoss") || StringUtils.equalsIgnoreCase(server, "WildFly");
        WebLoginAuthMethod method = getWebAuthMethod();
        WebLoginAuthMethod metodo = WebLoginAuthMethod.KEYCLOAK.equals(method) ? adaptado ? method : getDefaultWebAuthMethod() : method;
        return metodo.getMethod();
    }

    /**
     * @return the Web module authentication mechanism
     */
    public WebLoginAuthMethod getWebAuthMethod() {
        return _webAuthMethod == null ? getDefaultWebAuthMethod() : _webAuthMethod;
    }

    /**
     * El método setWebAuthMethod se utiliza para establecer el mecanismo de autenticación del módulo Web del proyecto. El valor predeterminado de
     * esta propiedad es FORM.
     *
     * @param method elemento de la enumeración WebAppAuthMethod que corresponde al mecanismo de autenticación del módulo Web del proyecto.
     * @see <a href="https://javaee.github.io/tutorial/security-webtier002.html#specifying-authentication-mechanisms">Authentication Mechanisms</a>
     */
    public void setWebAuthMethod(WebLoginAuthMethod method) {
        _webAuthMethod = method;
    }

    /**
     * @param server application server name (GlassFish, JBoss or WildFly)
     * @return the Web module authentication mechanism
     */
    public String getWebApiAuthMethod(String server) {
        boolean adaptado = StringUtils.equalsIgnoreCase(server, "JBoss") || StringUtils.equalsIgnoreCase(server, "WildFly");
        WebLoginAuthMethod method = getWebApiAuthMethod();
        WebLoginAuthMethod metodo = WebLoginAuthMethod.KEYCLOAK.equals(method) ? adaptado ? method : getDefaultWebApiAuthMethod() : method;
        return metodo.getMethod();
    }

    /**
     * @return the Web API module authentication mechanism
     */
    public WebLoginAuthMethod getWebApiAuthMethod() {
        return _webApiAuthMethod != null && ArrayUtils.contains(SUPPORTED_WEB_API_AUTH_METHODS, _webApiAuthMethod) ? _webApiAuthMethod
            : getDefaultWebApiAuthMethod();
    }

    /**
     * El método setWebApiAuthMethod se utiliza para establecer el mecanismo de autenticación del módulo Web API del proyecto. El valor predeterminado
     * de esta propiedad es BASIC.
     *
     * @param method elemento de la enumeración WebAppAuthMethod que corresponde al mecanismo de autenticación del módulo Web API del proyecto.
     * @see <a href="https://javaee.github.io/tutorial/security-webtier002.html#specifying-authentication-mechanisms">Authentication Mechanisms</a>
     */
    public void setWebApiAuthMethod(WebLoginAuthMethod method) {
        _webApiAuthMethod = method;
    }

    /**
     * @return the security realm type
     */
    public SecurityRealmType getSecurityRealmType() {
        return _securityRealmType == null ? getDefaultSecurityRealmType() : _securityRealmType;
    }

    /**
     * El método setSecurityRealmType se utiliza para establecer el tipo de dominio de seguridad del proyecto. El valor predeterminado de esta
     * propiedad es JDBC.
     *
     * @param securityRealmType elemento de la enumeración SecurityRealmType que corresponde al tipo de dominio de seguridad del proyecto. Especifique
     * JDBC si el controlador de seguridad del proyecto es un controlador de base de datos. Especifique LDAP si el controlador de seguridad del
     * proyecto cumple el protocolo LDAP. Especifique CIAM si el controlador es un gestor de CIAM.
     */
    public void setSecurityRealmType(SecurityRealmType securityRealmType) {
        _securityRealmType = securityRealmType;
        /* no ejecutar setRoleBasedAccessControllerName para que, por default, la aplicación controle la asignación de los roles de los usuarios
        if (StringUtils.isBlank(_roleBasedAccessControllerName)) {
            setRoleBasedAccessControllerName(securityRealmType);
        }
        /**/
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
     * El método setRoleBasedAccessControllerName se utiliza para establecer el nombre del controlador de seguridad del proyecto. El valor
     * predeterminado de esta propiedad es el alias del proyecto.
     *
     * @param roleBasedAccessControllerName nombre del controlador de seguridad del proyecto. Especifique LDAP o CIAM si el controlador de seguridad
     * del proyecto es LDAP o CIAM y desea que el controlador, además de la autenticación, también controle la asignación de los roles de los
     * usuarios; si desea que el controlador solo controle la autenticación y la aplicación controle la asignación de los roles de los usuarios,
     * entonces especifique el alias del proyecto.
     */
    public void setRoleBasedAccessControllerName(String roleBasedAccessControllerName) {
        _roleBasedAccessControllerName = StrUtils.getIdentifier(roleBasedAccessControllerName);
    }

    /**
     * El método setRoleBasedAccessControllerName se utiliza para establecer el nombre del controlador de seguridad del proyecto. El valor
     * predeterminado de esta propiedad es el alias del proyecto.
     *
     * @param securityRealmType elemento de la enumeración SecurityRealmType que corresponde al tipo de dominio de seguridad del proyecto. Especifique
     * LDAP o CIAM si el controlador de seguridad del proyecto es LDAP o CIAM y desea que el controlador, además de la autenticación, también controle
     * la asignación de los roles de los usuarios; si desea que el controlador solo controle la autenticación y la aplicación controle la asignación
     * de los roles de los usuarios, entonces especifique JDBC.
     */
    public void setRoleBasedAccessControllerName(SecurityRealmType securityRealmType) {
        String name = SecurityRealmType.JDBC.equals(securityRealmType) ? getAlias() : securityRealmType.name();
        setRoleBasedAccessControllerName(name);
    }

    /**
     * @return the web project security prefix
     */
    public String getWebSecurityPrefix() {
        return StringUtils.defaultIfBlank(_webSecurityPrefix, getWebProjectName());
    }

    /**
     * @param prefix the web project security prefix to set
     */
    public void setWebSecurityPrefix(String prefix) {
        _webSecurityPrefix = StrUtils.getLowerCaseIdentifier(prefix, '-');
    }

    /**
     * @return true if authenticated users automatic registration should be enabled; false otherwise
     */
    public boolean isAuthenticatedUserAutomaticRegistrationEnabled() {
        return _authenticatedUserAutomaticRegistrationEnabled && !SecurityRealmType.JDBC.equals(getSecurityRealmType());
    }

    /**
     * El método setAuthenticatedUserAutomaticRegistrationEnabled se utiliza para especificar si el proyecto generado debe registrar automáticamente,
     * o no, los usuarios que se conectan y no están registrados en la base de datos. Un usuario se puede conectar sin estar registrado en la base de
     * datos si el tipo de dominio de seguridad del proyecto no es JDBC.
     *
     * @param enabled true, si el proyecto generado debe registrar automáticamente, o no, los usuarios que se conectan y no están registrados en la
     * base de datos; de lo contrario false.
     */
    public void setAuthenticatedUserAutomaticRegistrationEnabled(boolean enabled) {
        _authenticatedUserAutomaticRegistrationEnabled = enabled;
    }

    /**
     * @return true if authenticated users automatic synchronization should be enabled; false otherwise
     */
    public boolean isAuthenticatedUserAutomaticSynchronizationEnabled() {
        return _authenticatedUserAutomaticSynchronizationEnabled && !SecurityRealmType.JDBC.equals(getSecurityRealmType());
    }

    /**
     * El método setAuthenticatedUserAutomaticSynchronizationEnabled se utiliza para especificar si el proyecto generado debe sincronizar
     * automáticamente, o no, los roles de los usuarios cuando se conectan. Este atributo es relevante solo si el tipo de dominio de seguridad del
     * proyecto no es JDBC.
     *
     * @param enabled true, si el proyecto generado debe sincronizar automáticamente, o no, los roles de los usuarios cuando se conectan; de lo
     * contrario false.
     */
    public void setAuthenticatedUserAutomaticSynchronizationEnabled(boolean enabled) {
        _authenticatedUserAutomaticSynchronizationEnabled = enabled;
    }

    protected String getDefaultAddOnsProjectName() {
        return getAlias() + "-add-ons";
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

    protected String getDefaultLibDirProjectName() {
        return getAlias() + "-lib-dir";
    }

    protected String getDefaultResourcesProjectName() {
        return getAlias() + "-resources";
    }

    protected String getDefaultWebProjectName() {
        return getAlias() + "-war";
    }

    protected String getDefaultWebApiProjectName() {
        return getAlias() + "-war-api";
    }

    protected String getDefaultRootPackageName() {
        return getAlias();
    }

    protected String getDefaultPersistenceRootPackageName() {
        return getRootPackageName();
    }

    protected String getDefaultGoogleRecaptchaSiteKey() {
        return "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI";
    }

    protected String getDefaultGoogleRecaptchaSecretKey() {
        return "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";
    }

    protected boolean getDefaultOperatingSystemShellKeepTempFiles() {
        ProjectStage stage = getProjectStage();
        return ProjectStage.DEVELOPMENT.equals(stage) || ProjectStage.TESTING.equals(stage);
    }

    protected ShortMessageServiceProvider getDefaultShortMessageServiceProvider() {
        return _projectTextingEnabled || _projectTextDebuggingEnabled ? ShortMessageServiceProvider.TWILIO : ShortMessageServiceProvider.UNSPECIFIED;
    }

    protected ProjectStage getDefaultProjectStage() {
        return ProjectStage.DEVELOPMENT;
    }

    protected WebLoginAuthMethod getDefaultWebAuthMethod() {
        return WebLoginAuthMethod.FORM;
    }

    protected WebLoginAuthMethod getDefaultWebApiAuthMethod() {
        return WebLoginAuthMethod.BASIC;
    }

    protected SecurityRealmType getDefaultSecurityRealmType() {
        return SecurityRealmType.JDBC;
    }

    protected String getDefaultSecurityRealmName() {
        return getAlias() + "-" + (TYPELESS_REALM_NAME ? "auth" : getSecurityRealmType().name().toLowerCase()) + "-" + "realm";
    }

    protected String getDefaultRoleBasedAccessControllerName() {
        /*
        El valor predeterminado de esta propiedad es el nombre del tipo de dominio de seguridad del proyecto (vea Método setSecurityRealmType).
        return getSecurityRealmType().name();
        **/
        return getAlias();
    }

}
