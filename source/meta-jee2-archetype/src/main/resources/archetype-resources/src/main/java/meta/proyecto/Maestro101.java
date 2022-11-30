#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.proyecto;

import adalid.commons.enums.*;
import adalid.core.annotations.*;
import adalid.jee2.constants.*;

/**
 * @author ADALID meta-jee2-archetype
 */
@MasterProject(alias = Maestro101.ALIAS) //, helpFile = Maestro101.ALIAS, helpFileAutoName = HelpFileAutoName.ENTITY, helpFileAutoType = "xhtml")
public class Maestro101 extends Maestro {

    public static final String ALIAS = "${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101";

    public static void main(String[] args) throws Exception {
        Maestro101.class.getDeclaredConstructor().newInstance().build(PLATAFORMA_MAVEN_POSTGRESQL_GLASSFISH);
    }

    @Override
    protected void setStaticAttributes() {
        super.setStaticAttributes();
//      setBootstrappingFileName("bootstrapping.properties");
//      setLocale(SPANISH);
//      setDecimalSeparator(ENGLISH, '.');
//      setDecimalSeparator(SPANISH, ',');
//      setThousandSeparator(ENGLISH, ',');
//      setThousandSeparator(SPANISH, '.');
//      setDateFormat(ENGLISH, "MM/dd/yyyy");
//      setDateFormat(SPANISH, "dd/MM/yyyy");
//      setTimeFormat(ENGLISH, "hh:mm a");
//      setTimeFormat(SPANISH, "hh:mm a");
//      setTimestampFormat(ENGLISH, "MM/dd/yyyy hh:mm a");
//      setTimestampFormat(SPANISH, "dd/MM/yyyy hh:mm a");
//      setAlertLoggingLevel(LoggingLevel.WARN);
//      setDetailLoggingLevel(LoggingLevel.INFO);
//      setDictionaryLoggingLevel(LoggingLevel.INFO);
//      setTrackingLoggingLevel(LoggingLevel.INFO);
//      setTransitionLoggingLevel(LoggingLevel.INFO);
//      setSpecialExpressionLoggingLevel(LoggingLevel.WARN);
//      setUnusualExpressionLoggingLevel(LoggingLevel.WARN);
//      setDefaultEntityCodeGenBPL(true);
//      setDefaultEntityCodeGenBWS(true);
//      setDefaultEntityCodeGenFWS(true);
//      setDefaultEntityCodeGenGUI(true);
//      setDefaultEntityCodeGenSMC(true);
//      setDefaultEntityCodeGenSQL(true);
//      setDefaultPropertyFieldSerializable(false);
//      setDefaultPropertyFieldSerializableIUID(true);
//      setDefaultStringFieldMaxLength(2000);
//      setDefaultStringIndexMaxLength(1596);
    }

    @Override
    public void configureBuilder() {
        super.configureBuilder();
        /**/
//      enableDictionary();
        /**/
//      setSupportedLocales(ENGLISH, SPANISH);
        /**/
//      setBusinessOperationConfirmationRequired(Kleenean.TRUE);
//      setDatabaseOperationConfirmationRequired(Kleenean.TRUE);
        /**/
        setMissingValueGraphicImageName(FA.MISSING_VALUE);
//      setNullValueGraphicImageName(FA.NULL_VALUE);
        setUnnecessaryValueGraphicImageName(FA.UNNECESSARY_VALUE);
        /**/
    }

    @Override
    public void configureGenerator() {
        super.configureGenerator();
        loadEnvironmentVariables(LoggingLevel.TRACE);
//      putEnvironmentVariable(CONTENT_ROOT_DIR_LINUX, "/opt/content-root");
//      putEnvironmentVariable(CONTENT_ROOT_DIR_WINDOWS, "%SystemDrive%/content-root");
//
//      putEnvironmentVariable(POSTGRESQL_DRIVER_ID, "pgjdbc4");
//      putEnvironmentVariable(POSTGRESQL_DRIVER_JAR, "postgresql-42.2.18.jar");
//      putEnvironmentVariable(POSTGRESQL_DRIVER_VERSION, "42.2.18");
//
//      putEnvironmentVariable(VERSION_POSTGRESQL, "15");
//      putEnvironmentVariable(VERSION_GLASSFISH, "5.1.0");
        putEnvironmentVariable(VERSION_JAVA, "1.8.0_333");
//      putEnvironmentVariable(VERSION_PRIMEFACES, "11.0.0");
//      putEnvironmentVariable(VERSION_PRIMEFACES_EXTENSIONS, "11.0.0");
//      putEnvironmentVariable(VERSION_GOOGLE_GSON, "2.9.0");
//      putEnvironmentVariable(VERSION_THIRD_PARTY_DIR, "4.0");
//      loadPrivateProperties(LoggingLevel.INFO);
//      setTheme(PrimeFacesThemes.SAGA);
//      setThemeSwitchingEnabled(true);
//      setInternetAccessAllowed(true);
//      setWebServicesEnabled(true);
//      setProjectMailingEnabled(true);
//      setProjectTextingEnabled(true);
//      setDefaultSMSProvider(ShortMessageServiceProvider.TWILIO);
//      setProjectRecaptchaEnabled(true);
//      setProjectRecaptchaSiteVerificationEnabled(true);
//      setProjectStage(ProjectStage.PRODUCTION);
//      setDatabaseName(ALIAS);
//      setWebAuthMethod(WebLoginAuthMethod.FORM);
//      setWebApiAuthMethod(WebLoginAuthMethod.BASIC);
//      setSecurityRealmName(ALIAS + "-auth-realm");
//      setSecurityRealmType(SecurityRealmType.LDAP);
//      setRoleBasedAccessControllerName("LDAP");
//      setRoleCodePrefix(ALIAS);
//      setAuthenticatedUserAutomaticRegistrationEnabled(false);
//      setAuthenticatedUserAutomaticSynchronizationEnabled(false);
//      setMessageDigestAlgorithm("SHA-256");
//      setExporterShellEnabled(true);
//      setReporterShellEnabled(true);
//      setSqlAgentShellEnabled(true);
//      setOperatingSystemShellKeepTempFiles(true);
//      setVersion("1.0.0");
//      taskNotifierSchedule.minute = "*/15";
//      addProjectDependency(dependencia adicional, tipo del módulo al que se debe agregar la dependencia adicional);
//      por ejemplo: addProjectDependency(MavenDependencies.PrimeFaces.omega(), ProjectModuleType.WEB);
    }

    @Override
    public void addDirectives() {
        super.addDirectives();
//      addFileExclusionPattern(expresión regular para evaluar el nombre de los archivos que se deben excluir);
//      por ejemplo: addFileExclusionPattern("^.*/data-migration/base/insert-select-past-data-[${backslash}${backslash}d]{8}-[${backslash}${backslash}d]{4}${backslash}${backslash}.sql${dollar}");
//      addFilePreservationPattern(expresión regular para evaluar el nombre de los archivos que se deben preservar);
//      por ejemplo: addFilePreservationPattern("^.*/${backslash}${backslash}.gitignore${dollar}"); // preserva los archivos .gitignore
    }

    @Override
    public void attachAddAttributesMethods() {
        super.attachAddAttributesMethods();
//      attachAddAttributesMethods(${package}.meta.psm.PostgreSQLAttributes.class);
    }

    @Override
    public void addAttributes() {
        super.addAttributes();
//      addAttribute(ProjectAttributeKeys.ALTERNATE_DOCUMENT_ROOT_SECURED, true);
//      addAttribute(ProjectAttributeKeys.ALWAYS_OPEN_CONTENT_VIEWER_AS_DIALOG, false);
//      addAttribute(ProjectAttributeKeys.CHECK_BOXES_ALLOWED, false);
//      addAttribute(ProjectAttributeKeys.CUSTOM_LAYOUT, true);
//      addAttribute(ProjectAttributeKeys.DISABLE_BPL_IMPL_GENERATION, true);
//      addAttribute(ProjectAttributeKeys.DISABLE_PAGE_ASSISTANT_GENERATION, true);
//      addAttribute(ProjectAttributeKeys.FACELETS_REFRESH_PERIOD, -1);
//      addAttribute(ProjectAttributeKeys.HELP_WINDOW_WIDTH_KEY, 3);
//      addAttribute(ProjectAttributeKeys.HCB_RENDERING, true);
//      addAttribute(ProjectAttributeKeys.HLB_RENDERING, true);
//      addAttribute(ProjectAttributeKeys.HRB_RENDERING, true);
//      addAttribute(ProjectAttributeKeys.HTML_SANITIZER_VERSION, "20200713.1");
//      addAttribute(ProjectAttributeKeys.INITIAL_PAGE_LOCATION, IPL.DEFAULT);
//      addAttribute(ProjectAttributeKeys.INITIAL_WINDOW_TARGET, IWT.DEFAULT);
//      addAttribute(ProjectAttributeKeys.MEDIA_FILE_EXPOSURE_RISK_ACCEPTABLE, true);
//      addAttribute(ProjectAttributeKeys.NUMERIC_BOXES_ALLOWED, false);
//      addAttribute(ProjectAttributeKeys.PERSISTENCE_UNIT_ECLIPSELINK_LOGGER, "DefaultLogger");
//      addAttribute(ProjectAttributeKeys.PERSISTENCE_UNIT_HIBERNATE_STATISTICS, "enabled");
//      addAttribute(ProjectAttributeKeys.RPH_DOUBLE_HEIGHT, true);
//      addAttribute(ProjectAttributeKeys.RPH_LEFT_IMAGE_HEIGHT, 60);
//      addAttribute(ProjectAttributeKeys.RPH_LEFT_IMAGE_WIDTH, 105);
//      addAttribute(ProjectAttributeKeys.RPH_NO_LEFT_IMAGE, false);
//      addAttribute(ProjectAttributeKeys.RPH_NO_RIGHT_IMAGE, false);
//      addAttribute(ProjectAttributeKeys.RPH_RIGHT_IMAGE_HEIGHT, 60);
//      addAttribute(ProjectAttributeKeys.RPH_RIGHT_IMAGE_WIDTH, 105);
//      addAttribute(ProjectAttributeKeys.SESSION_TIMEOUT, 5);
//      addAttribute(ProjectAttributeKeys.TASK_MAIL_PER_TASK, true);
//      addAttribute(ProjectAttributeKeys.TASK_TEXT_PER_TYPE, true);
    }

}
