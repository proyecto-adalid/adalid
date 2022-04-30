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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoJava1 extends ProyectoBase {

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
        initializeJobSchedules();
    }

    protected ModuloConsulta consulta;

    protected ModuloProcesamiento procesamiento;

    protected ModuloRegistro registro;

    protected PaqueteConsultaRecursosBasicos consultaRecursosBasicos;

    protected PaqueteProcesamientoRecursosBasicos procesamientoRecursosBasicos;

    protected PaqueteRegistroRecursosBasicos registroRecursosBasicos;

    protected final JobSchedule taskNotifierSchedule = new JobSchedule("taskNotifierSchedule");

    protected final JobSchedule dailyProcessSchedule = new JobSchedule("dailyProcessSchedule");

    protected final JobSchedule weeklyProcessSchedule = new JobSchedule("weeklyProcessSchedule");

    protected final JobSchedule monthlyProcessSchedule = new JobSchedule("monthlyProcessSchedule");

    private final List<JobSchedule> _jobScheduleList = new ArrayList<>();

    private final List<String> _predefinedJobScheduleNames = new ArrayList<>();

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
     * internet para su funcionamiento (por ejemplo, si la página Inicio de Sesión incluye, o no, un elemento Google reCaptcha). El valor
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
     * @return true if authenticated users automatic registration should be enabled; false otherwise
     */
    public boolean isAuthenticatedUserAutomaticRegistrationEnabled() {
        return _authenticatedUserAutomaticRegistrationEnabled && !SecurityRealmType.JDBC.equals(getSecurityRealmType());
    }

    /**
     * El método setAuthenticatedUserAutomaticRegistrationEnabled se utiliza para especificar si el proyecto generado debe registrar automáticamente,
     * o no, los usuarios que se conectan y no están registrados en la base de datos. Un usuario se puede conectar sin estar registrado en la base de
     * datos solo si el tipo de dominio de seguridad del proyecto no es JDBC.
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
