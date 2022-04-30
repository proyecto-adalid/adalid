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
package adalid.util.meta.sql;

import adalid.commons.*;
import adalid.commons.enums.*;
import adalid.commons.interfaces.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.commons.velocity.*;
import java.io.File;
import java.io.FileFilter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

/**
 * @author Jorge Campins
 */
public class MetaPlatformSql {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static Level _alertLevel = Level.WARN;

    private static Level _detailLevel = Level.OFF;

    private static Level _trackingLevel = Level.OFF;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields' public getters and setters">
    /**
     * @return the alert messages logging level
     */
    public static Level getAlertLevel() {
        return _alertLevel;
    }

    /**
     * @param level the alert messages logging level to set; WARN will be used if level is null; ERROR and FATAL are downgraded to WARN; OFF disables
     * alert messages logging
     */
    public static void setAlertLevel(Level level) {
        _alertLevel = LogUtils.check(level, Level.WARN, Level.WARN);
    }

    /**
     * @return the detail messages logging level
     */
    public static Level getDetailLevel() {
        return _detailLevel;
    }

    /**
     * @param level the detail messages logging level to set; TRACE will be used if level is null; WARN, ERROR and FATAL are downgraded to INFO; OFF
     * disables detail messages logging
     */
    public static void setDetailLevel(Level level) {
        _detailLevel = LogUtils.check(level, Level.OFF, Level.INFO);
    }

    /**
     * @return the tracking messages logging level
     */
    public static Level getTrackingLevel() {
        return _trackingLevel;
    }

    /**
     * @param level the tracking messages logging level to set; TRACE will be used if level is null; WARN, ERROR and FATAL are downgraded to INFO; OFF
     * disables tracking messages logging
     */
    public static void setTrackingLevel(Level level) {
        _trackingLevel = LogUtils.check(level, Level.OFF, Level.INFO);
    }

    /**
     * @return the alert messages logging logginglevel
     */
    public static LoggingLevel getAlertLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_alertLevel);
    }

    /**
     * @param logginglevel the alert messages logging logginglevel to set; WARN will be used if logginglevel is null; ERROR and FATAL are downgraded
     * to WARN; OFF disables alert messages logging
     */
    public static void setAlertLoggingLevel(LoggingLevel logginglevel) {
        setAlertLevel(logginglevel.getLevel());
    }

    /**
     * @return the detail messages logging logginglevel
     */
    public static LoggingLevel getDetailLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_detailLevel);
    }

    /**
     * @param logginglevel the detail messages logging logginglevel to set; TRACE will be used if logginglevel is null; WARN, ERROR and FATAL are
     * downgraded to INFO; OFF disables detail messages logging
     */
    public static void setDetailLoggingLevel(LoggingLevel logginglevel) {
        setDetailLevel(logginglevel.getLevel());
    }

    /**
     * @return the tracking messages logging logginglevel
     */
    public static LoggingLevel getTrackingLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_trackingLevel);
    }

    /**
     * @param logginglevel the tracking messages logging logginglevel to set; TRACE will be used if logginglevel is null; WARN, ERROR and FATAL are
     * downgraded to INFO; OFF disables tracking messages logging
     */
    public static void setTrackingLoggingLevel(LoggingLevel logginglevel) {
        setTrackingLevel(logginglevel.getLevel());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static final fields">
    private static final Logger logger = Logger.getLogger(MetaPlatformSql.class);

    private static final String FILE_RESOURCE_LOADER_PATH = "file.resource.loader.path";

    private static final String FILE_RESOURCE_LOADER_PATH_FILTER = "file.resource.loader.path.filter";

    private static final String OS_NAME = System.getProperty("os.name");

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final String FILE_SEPARATOR_CHARS = "/\\";

    private static final String[] FILE_SEPARATOR_STRINGS = {"/", "\\"};

    private static final String EOL = "\n";

    private static final String HINT = EOL + "hint: ";

    private static final String DOT = ".";

    /*
    private static final String HYPHEN = "-";

    /**/
    private static final String SLASH = "/";

    /*
    private static final String CARET = "^";

    private static final String DOLLAR = "$";

    private static final String ASTERISK = "*";

    private static final String DO_CASCADED_DELETE = "do.cascaded.delete";

    private static final String DO_ISOLATED_DELETE = "do.isolated.delete";

    /**/
    private static final String DOT_STRING = ".string";

    private static final String DOT_CLASS = ".class";

    private static final String DOT_INSTANCE = ".instance";

    private static final String DOT_PROGRAMMER = ".programmer";

    private static final String DOT_WRAPPER = ".wrapper";

    private static final String[] DOT_SUFFIXES = {DOT_STRING, DOT_CLASS, DOT_INSTANCE, DOT_PROGRAMMER, DOT_WRAPPER};

    private static final String PROPERTIES_SUFFIX = ".properties";

    private static final FileFilter propertiesFileFilter = FilUtils.nameEndsWithFilter(PROPERTIES_SUFFIX);

    /*
    private static final IOFileFilter ignoreVersionControlFilter = FileFilterUtils.makeCVSAware(FileFilterUtils.makeSVNAware(null));

    /**/
    private static final File bootstrappingRootFolder = PropertiesHandler.getRootFolder();

    private static final File[] bootstrappingVelocityFolders = PropertiesHandler.getVelocityFolders();

    private static final File bootstrappingVelocityPropertiesFile = PropertiesHandler.getVelocityPropertiesFile();

    private static final File[] bootstrappingPlatformsFolders = PropertiesHandler.getPlatformsFolders();

    private static final boolean bootstrappingRootFolderNotVisible = FilUtils.isNotVisibleDirectory(bootstrappingRootFolder);

    private static final boolean bootstrappingVelocityPropertiesFileNotVisible = FilUtils.isNotVisibleFile(bootstrappingVelocityPropertiesFile);

    private static final boolean WINDOWS = StringUtils.containsIgnoreCase(OS_NAME, "windows");
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="template properties file keys">
    private static final String TP_DISABLED = "disabled";

    private static final String TP_DISABLED_MISSING = "disabled-when-missing";

    private static final String TP_TEMPLATE = "template";

    private static final String TP_TYPE = "template-type";

    private static final String TP_TYPE_DOCUMENT = "document";

    private static final String TP_TYPE_VELOCITY = "velocity";

    private static final String[] TP_TYPE_ARRAY = {TP_TYPE_DOCUMENT, TP_TYPE_VELOCITY};

    private static final String TP_PATH = "path";

    private static final String TP_PACKAGE = "package";

    private static final String TP_FILE = "file";

    private static final String TP_PRESERVE = "preserve";

    private static final String TP_ENCODING = "template-encoding";

    private static final String TP_CHARSET = "file-encoding";

    private static final String TP_EXECUTE_COMMAND = "execute-" + (WINDOWS ? "windows" : "linux") + "-command";

    private static final String TP_EXECUTE_DIRECTORY = "execute-directory";

    private static final String TP_FOR_EACH = "for-each";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Velocity Context keys">
    private static final String VC_PLATFORM = "platform";

    private static final String VC_TEMPLATE = "template";

    private static final String VC_TEMPLATE_PATH = "templatePath";

    private static final String VC_PATH = "path";

    private static final String VC_PACKAGE = "package";

    private static final String VC_FILE = "file";

    private static final String VC_ROOT_PATH = "rootFolderPath";

    private static final String VC_ROOT_SLASHED_PATH = "rootFolderSlashedPath";

    private static final String VC_USER_PATH = "userFolderPath";

    private static final String VC_USER_SLASHED_PATH = "userFolderSlashedPath";

    private static final String VC_BUILD_DATE = "buildDate";

    private static final String VC_BUILD_TIMESTAMP = "buildTimestamp";

    private static final String VC_FILE_PATH = "filePath";

    private static final String VC_FILE_NAME = "fileName";

    private static final String VC_FILE_PATH_NAME = "filePathName";

    private static final String VC_FILE_PATH_FILE = "filePathFile";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final String platform;

    private PlatformBean platformBean;

    private int platforms = 0;

    private int templates = 0;

    private int disabledTemplates = 0;

    private int preservedFiles = 0;

    private int copiedFiles = 0;

    private int mergedFiles = 0;

    private int warnings = 0;

    private int errors = 0;

    private Set<String> optionalResourceNames;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields' public getters and setters">
    public Set<String> getOptionalResourceNames() {
        return optionalResourceNames;
    }

    public void setOptionalResourceNames(Set<String> optionalResourceNames) {
        this.optionalResourceNames = optionalResourceNames;
    }
    // </editor-fold>

    public MetaPlatformSql(String platform) {
        this.platform = platform;
    }

    public boolean read() {
        log(Level.INFO, "read", "platform=" + platform);
        resetCounters();
        if (isInvalidBootstrapping()) {
            return false;
        }
        File platformPropertiesFile;
        WriterContext basicWriterContext;
        String platformPropertiesFileName = platform + PROPERTIES_SUFFIX;
        for (File bootstrappingPlatformsFolder : bootstrappingPlatformsFolders) {
            platformPropertiesFile = new File(bootstrappingPlatformsFolder, platformPropertiesFileName);
            if (FilUtils.isVisibleFile(platformPropertiesFile)) {
                platformBean = new PlatformBean(platform, platformPropertiesFile);
                basicWriterContext = newWriterContext();
                readPlatform(basicWriterContext);
                return errors == 0;
            }
        }
        return false;
    }

    private void readPlatform(WriterContext basicWriterContext) {
        File platformPropertiesFile = platformBean.getPropertiesFile();
        logger.info("propertiesFile=" + platformPropertiesFile.getPath());
        try {
            VelocityContext platformContext = basicWriterContext.getVelocityContextClone();
            platformContext.put(VC_PLATFORM, platform);
            TLB.setProgrammers(basicWriterContext.programmers);
            TLB.setWrapperClasses(basicWriterContext.wrapperClasses);
            putProperties(platformContext, platformPropertiesFile);
            Properties properties = mergeProperties(platformContext, platformPropertiesFile);
            putStrings(platformContext, properties);
            WriterContext platformWriterContext = newWriterContext(platformContext);
            File platformsFolder = platformPropertiesFile.getParentFile();
            ExtendedProperties platformExtendedProperties = PropertiesHandler.getExtendedProperties(platformPropertiesFile);
            String[] pathnames = platformExtendedProperties.getStringArray(FILE_RESOURCE_LOADER_PATH);
            String[] pathfilters = platformExtendedProperties.getStringArray(FILE_RESOURCE_LOADER_PATH_FILTER);
            Map<String, File> folders = new LinkedHashMap<>();
            if (pathnames == null || pathnames.length == 0) {
            } else {
                for (File folder : bootstrappingPlatformsFolders) {
                    folders.putAll(FilUtils.directoriesMap(folder, pathnames, folder));
                }
            }
            if (pathfilters != null && pathfilters.length > 0) {
                String[] keyArray = folders.keySet().toArray(ArrUtils.arrayOf(String.class));
                String slashedFilter, slashedPath;
                for (String pathfilter : pathfilters) {
                    slashedFilter = pathfilter.replace(FILE_SEPARATOR, SLASH);
                    for (String key : keyArray) {
                        slashedPath = key.replace(FILE_SEPARATOR, SLASH) + SLASH;
                        if (slashedPath.contains(slashedFilter)) {
                            folders.remove(key);
                            logger.debug(pathfilter + " excludes " + key);
                        }
                    }
                }
            }
            File[] templateFiles;
            TemplateBean templateBean;
            String platformsFolderPath = platformsFolder.getPath(); // + FILE_SEPARATOR;
            for (File folder : folders.values()) {
                log(_detailLevel, "read", "path=" + StringUtils.removeStart(folder.getPath(), platformsFolderPath));
                templateFiles = folder.listFiles(propertiesFileFilter);
                Arrays.sort(templateFiles);
                for (File templatePropertiesFile : templateFiles) {
                    templateBean = new TemplateBean(platformBean, templatePropertiesFile);
                    readTemplate(platformWriterContext, templateBean);
                    templates++;
                }
            }
            platforms++;
        } catch (Throwable throwable) {
            error(throwable);
        }
        printSummary();
    }

    private void readTemplate(WriterContext platformWriterContext, TemplateBean templateBean) {
        File templatePropertiesFile = templateBean.getPropertiesFile();
        log(_trackingLevel, "read", "template=" + templatePropertiesFile);
        VelocityContext templateContext = platformWriterContext.getVelocityContextClone();
        putProperties(templateContext, templatePropertiesFile);
        WriterContext templateWriterContext = newWriterContext(templateContext);
        try {
            readFile(templateWriterContext, templateBean);
        } catch (Throwable throwable) {
            error(throwable);
        }
    }

    private void readFile(WriterContext templateWriterContext, TemplateBean templateBean) {
        File templatePropertiesFile = templateBean.getPropertiesFile();
        VelocityContext fileContext = templateWriterContext.getVelocityContextClone();
        Properties properties = mergeProperties(fileContext, templatePropertiesFile);
        putStrings(fileContext, properties);
        String userPath = pathString(USER_DIR);
        String temptype = StringUtils.defaultIfBlank(properties.getProperty(TP_TYPE), TP_TYPE_VELOCITY);
        String template = StringUtils.trimToNull(properties.getProperty(TP_TEMPLATE));
        String filePath = StringUtils.trimToNull(properties.getProperty(TP_PATH));
        String filePack = StringUtils.trimToNull(properties.getProperty(TP_PACKAGE));
        String fileName = StringUtils.trimToNull(properties.getProperty(TP_FILE));
        String preserve = StringUtils.trimToNull(properties.getProperty(TP_PRESERVE));
        String charset1 = StringUtils.trimToNull(properties.getProperty(TP_ENCODING));
        String charset2 = StringUtils.trimToNull(properties.getProperty(TP_CHARSET));
        String disabled = properties.getProperty(TP_DISABLED, Boolean.FALSE.toString());
        String disabledMissing = properties.getProperty(TP_DISABLED_MISSING);
        String executeCommand = StringUtils.trimToNull(properties.getProperty(TP_EXECUTE_COMMAND));
        String executeDirectory = StringUtils.trimToNull(properties.getProperty(TP_EXECUTE_DIRECTORY));
        String forEach = StringUtils.trimToNull(properties.getProperty(TP_FOR_EACH));
        String hint = ", check property \"{0}\" at file \"{1}\"";
        if (ArrayUtils.contains(TP_TYPE_ARRAY, temptype)) {
        } else {
            String pattern = "failed to obtain a valid template type" + hint;
            String message = MessageFormat.format(pattern, TP_TYPE, templatePropertiesFile);
            logger.error(message);
            errors++;
            return;
        }
        if (template == null) {
            String pattern = "failed to obtain a valid template name" + hint;
            String message = MessageFormat.format(pattern, TP_TEMPLATE, templatePropertiesFile);
            logger.error(message);
            errors++;
            return;
        }
        if (fileName == null) {
            String pattern = "failed to obtain a valid file name" + hint;
            String message = MessageFormat.format(pattern, TP_FILE, templatePropertiesFile);
            logger.error(message);
            errors++;
            return;
        }
        String templatePathString = pathString(template);
        String templatePath = StringUtils.substringBeforeLast(templatePathString, FILE_SEPARATOR);
        fileContext.put(VC_TEMPLATE, StringEscapeUtils.escapeJava(templatePathString));
        fileContext.put(VC_TEMPLATE_PATH, StringUtils.replace(templatePath, FILE_SEPARATOR, SLASH));
        fileContext.put(VC_FILE, fileName);
        if (filePath == null) {
            filePath = userPath;
        } else {
            filePath = pathString(filePath);
            if (isRelativePath(filePath)) {
                if (filePath.startsWith(FILE_SEPARATOR)) {
                    filePath = userPath + filePath;
                } else {
                    filePath = userPath + FILE_SEPARATOR + filePath;
                }
            }
        }
        fileContext.put(VC_PATH, StringEscapeUtils.escapeJava(filePath));
        if (filePack != null) {
            filePath += FILE_SEPARATOR + pathString(StringUtils.replace(filePack, DOT, SLASH));
            fileContext.put(VC_PACKAGE, dottedString(filePack));
        }
        File path = new File(filePath);
        String fullname = path.getPath() + FILE_SEPARATOR + fileName;
        fileContext.put(VC_FILE_PATH, StringEscapeUtils.escapeJava(filePath));
        fileContext.put(VC_FILE_NAME, StringEscapeUtils.escapeJava(fileName));
        fileContext.put(VC_FILE_PATH_NAME, StringEscapeUtils.escapeJava(fullname));
        fileContext.put(VC_FILE_PATH_FILE, path);
        /**/
        templateBean.setPath(templatePathString);
        templateBean.setType(temptype);
        templateBean.setEncoding(charset1);
        templateBean.setForEach(forEach);
        templateBean.setTargetPath(filePath);
        templateBean.setTargetPackage(filePack);
        templateBean.setTargetFile(fileName);
        templateBean.setTargetFileEncoding(charset2);
        templateBean.setExecuteCommand(executeCommand);
        templateBean.setExecuteDirectory(executeDirectory);
        templateBean.setDisabled(BitUtils.valueOf(disabled));
        templateBean.setDisabledWhenMissing(BitUtils.valueOf(disabledMissing));
        templateBean.setPreserveExistingFile(BitUtils.valueOf(preserve));
        templateBean.setBytes(0);
        templateBean.setLines(0);
    }

    private WriterContext newWriterContext() {
        TLB.clearProgrammers();
        TLB.clearWrapperClasses();
        WriterContext context = new WriterContext();
        context.velocityContext = newVelocityContext();
        context.programmers = TLB.getProgrammers();
        context.wrapperClasses = TLB.getWrapperClasses();
        return context;
    }

    private WriterContext newWriterContext(VelocityContext velocityContext) {
        WriterContext context = new WriterContext();
        context.velocityContext = velocityContext;
        context.programmers = TLB.getProgrammersClone();
        context.wrapperClasses = TLB.getWrapperClassesClone();
        return context;
    }

    private VelocityContext newVelocityContext() {
        VelocityContext context = new VelocityContext();
        /*
         * classes used in global macros must be loaded here
         */
        putClass(context, adalid.commons.velocity.VelocityAid.class);
        putClass(context, adalid.commons.util.FilUtils.class);
        putClass(context, adalid.commons.util.ManUtils.class);
        putClass(context, adalid.commons.util.StrUtils.class);
        putClass(context, adalid.commons.util.TimeUtils.class);
        putClass(context, org.apache.commons.lang.StringUtils.class);
        putClass(context, org.apache.commons.lang.StringEscapeUtils.class);
        /*
         * java type classes
         */
        putClass(context, java.lang.Boolean.class);
        putClass(context, java.lang.Byte.class);
        putClass(context, java.lang.Character.class);
        putClass(context, java.lang.Double.class);
        putClass(context, java.lang.Float.class);
        putClass(context, java.lang.Integer.class);
        putClass(context, java.lang.Long.class);
        putClass(context, java.lang.Short.class);
        putClass(context, java.lang.String.class);
        putClass(context, java.lang.System.class);
        putClass(context, java.math.BigDecimal.class);
        putClass(context, java.math.BigInteger.class);
        putClass(context, java.sql.Date.class);
        putClass(context, java.sql.Time.class);
        putClass(context, java.sql.Timestamp.class);
        //
        String path = getRoot().getPath();
        //
        context.put(VC_ROOT_PATH, StringEscapeUtils.escapeJava(path));
        context.put(VC_ROOT_SLASHED_PATH, path.replace('\\', '/'));
        context.put(VC_USER_PATH, StringEscapeUtils.escapeJava(USER_DIR));
        context.put(VC_USER_SLASHED_PATH, USER_DIR.replace('\\', '/'));
        context.put(VC_BUILD_DATE, TimeUtils.simpleDateString());
        context.put(VC_BUILD_TIMESTAMP, TimeUtils.simpleTimestampString());
        //
        logger.info(VC_ROOT_PATH + "=" + path);
        logger.info(VC_USER_PATH + "=" + USER_DIR);
        //
        return context;
    }

    private void putClass(VelocityContext context, Class<?> clazz) {
        context.put(clazz.getSimpleName(), clazz);
    }

    /**
     * Adds .string, .class, .instance, .programmer and .wrapper properties to the velocity context *
     */
    @SuppressWarnings("unchecked") // unchecked cast
    private void putProperties(VelocityContext context, File propertiesFile) {
        String hint = HINT + "check property \"{0}\" at file \"{1}\"";
        String pattern1 = "failed to load {2}" + hint;
//      String pattern2 = "failed to initialise {2}" + hint;
//      String pattern3 = "{2} does not implement {3}" + hint;
//      String pattern4 = "{2} is not a valid wrapper for {3}" + hint;
        String string1;
        String string2;
        String message;
//      String argument;
        Object object2;
//      Class<?> clazz1;
//      Class<?> clazz2;
//      Class<? extends Wrapper> wrapperClass;
//      Class<? extends Wrappable> wrappableClass;
//      Class<?> parameterType;
        String velocityKey;
        Properties properties = PropertiesHandler.loadProperties(propertiesFile);
        Set<String> stringPropertyNames = properties.stringPropertyNames();
        for (String name : stringPropertyNames) {
            checkPropertyName(name, propertiesFile);
            if (StringUtils.endsWithIgnoreCase(name, DOT_STRING)) {
                string1 = StringUtils.removeEndIgnoreCase(name, DOT_STRING);
                string2 = StringUtils.trimToEmpty(properties.getProperty(name));
                velocityKey = StrUtils.getCamelCase(string1, true);
                context.put(velocityKey, string2);
            } else if (StringUtils.endsWithIgnoreCase(name, DOT_CLASS)) {
                string1 = StringUtils.removeEndIgnoreCase(name, DOT_CLASS);
                string2 = StringUtils.trimToEmpty(properties.getProperty(name));
                message = MessageFormat.format(pattern1, name, propertiesFile, string2);
                object2 = getClassForName(string2, message);
                if (object2 != null) {
                    velocityKey = StrUtils.getCamelCase(string1, true);
                    context.put(velocityKey, object2);
                    continue;
                }
                throw new RuntimeException(message, new IllegalArgumentException(string2));
            }
        }
    }

    private void checkPropertyName(String name, File file) {
        String pattern = "invalid property name \"{0}\" at file \"{1}\"";
        String message = MessageFormat.format(pattern, StringUtils.trimToEmpty(name), file);
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException(message);
        }
        String low = name.toLowerCase();
        if (StringUtils.endsWithAny(low, DOT_SUFFIXES)) {
            int endIndex = StringUtils.lastIndexOfAny(low, DOT_SUFFIXES);
            if (endIndex == 0) {
                throw new RuntimeException(message);
            }
        }
    }

    private Class<?> getClassForName(String className, String message) {
        if (StringUtils.isBlank(className)) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(message, ex);
        }
    }

    /**
     * Gets the final properties set using the properties file as a velocity template
     */
    private Properties mergeProperties(VelocityContext context, File propertiesFile) {
        String template = getTemplate(propertiesFile);
        StringWriter sw = mergeTemplate(context, template);
        if (sw != null) {
            byte[] buffer = sw.toString().getBytes();
            Properties properties = PropertiesHandler.loadProperties(buffer);
            return properties;
        }
        return null;
    }

    /**
     * Gets the final properties set using the properties file as a velocity template
     */
    /*
    private ExtendedProperties mergeExtendedProperties(VelocityContext context, File propertiesFile) {
        String template = getTemplate(propertiesFile);
        StringWriter sw = mergeTemplate(context, template);
        if (sw != null) {
            byte[] buffer = sw.toString().getBytes();
            ExtendedProperties properties = PropertiesHandler.getExtendedProperties(buffer);
            return properties;
        }
        return null;
    }

    /**/
    private String getTemplate(File propertiesFile) {
        String slashedPath;
        String shortestPath = null;
        String propertiesFilePath = propertiesFile.getPath().replace(FILE_SEPARATOR, SLASH);
        String[] velocityFileResourceLoaderPathArray = VelocityEngineer.getFileResourceLoaderPathArray();
        if (velocityFileResourceLoaderPathArray != null && velocityFileResourceLoaderPathArray.length > 0) {
            for (String path : velocityFileResourceLoaderPathArray) {
//              slashedPath = path.replace(FILE_SEPARATOR, SLASH);
                slashedPath = StringUtils.removeEnd(path.replace(FILE_SEPARATOR, SLASH), SLASH) + SLASH;
                if (StringUtils.startsWithIgnoreCase(propertiesFilePath, slashedPath)) {
                    if (shortestPath == null || slashedPath.length() < shortestPath.length()) {
                        shortestPath = slashedPath;
                    }
                }
            }
        }
        if (shortestPath == null) {
            return propertiesFile.getName();
        }
        String template = StringUtils.removeStartIgnoreCase(propertiesFilePath, shortestPath);
        return StringUtils.removeStartIgnoreCase(template, SLASH);
    }

    private StringWriter mergeTemplate(VelocityContext context, String template) {
        String message = "failed to merge \"" + template + "\"";
        try {
            return VelocityEngineer.merge(context, template);
        } catch (Exception ex) {
            throw new RuntimeException(message, ex);
        }
    }

    private void putStrings(VelocityContext context, Properties properties) {
        String string1;
        String string2;
        String velocityKey;
        Set<String> stringPropertyNames = properties.stringPropertyNames();
        for (String name : stringPropertyNames) {
            if (StringUtils.endsWithIgnoreCase(name, DOT_STRING)) {
                string1 = StringUtils.removeEndIgnoreCase(name, DOT_STRING);
                string2 = StringUtils.trimToEmpty(properties.getProperty(name));
                velocityKey = StrUtils.getCamelCase(string1, true);
                context.put(velocityKey, string2);
            }
        }
    }

    private File getRoot() {
        File root = PropertiesHandler.getRootFolder();
        if (FilUtils.isVisibleDirectory(root)) {
            return root;
        }
        root = new File(USER_DIR + FILE_SEPARATOR + "test");
        if (FilUtils.isVisibleDirectory(root) || root.mkdirs()) {
            return root;
        }
        return new File(USER_DIR);
    }

    /*
    private String rootlessPath(File file) {
        return StringUtils.removeStart(StringUtils.removeStartIgnoreCase(file.getPath(), getRoot().getPath()), FILE_SEPARATOR);
    }

    /**/
    private String dottedString(String string) {
        return trimSplitJoin(string, FILE_SEPARATOR_CHARS, DOT);
    }

    private String pathString(String string) {
        String trimmed = StringUtils.trimToNull(string);
        if (trimmed == null) {
            return null;
        }
        String trimSplitJoin = trimSplitJoin(trimmed, FILE_SEPARATOR_CHARS, FILE_SEPARATOR);
        return StringUtils.startsWithAny(trimmed, FILE_SEPARATOR_STRINGS)
            ? FILE_SEPARATOR + trimSplitJoin : trimSplitJoin;
    }

    private String trimSplitJoin(Object object, String separatorChars, String separator) {
        String casted = object == null ? null : object instanceof String ? (String) object : object.toString();
        String string = StringUtils.trimToNull(casted);
        if (string == null) {
            return null;
        }
        String[] tokens = StringUtils.split(string, separatorChars);
        String join = StringUtils.join(tokens, separator);
        return join;
    }

    private boolean isAbsolutePath(String string) {
        String trimmed = StringUtils.trimToNull(string);
        if (trimmed == null) {
            return false;
        }
        if (WINDOWS) {
            String left = StringUtils.left(trimmed, 2);
            return left.matches("[a-zA-Z]\\:");
        } else {
            return StringUtils.startsWithAny(trimmed, FILE_SEPARATOR_STRINGS);
        }
    }

    private boolean isRelativePath(String string) {
        return StringUtils.isNotBlank(string) && !isAbsolutePath(string);
    }

    private void printSummary() {
        logger.info("platforms=" + platforms);
        logger.info("templates=" + templates);
        logger.info("disabled-templates=" + disabledTemplates);
        logger.info("enabled-templates=" + (templates - disabledTemplates));
        logger.info("files=" + (preservedFiles + copiedFiles + mergedFiles));
        logger.info("preserved-files=" + preservedFiles);
        logger.info("written-files=" + (copiedFiles + mergedFiles));
        logger.info("copied-files=" + copiedFiles);
        logger.info("generated-files=" + mergedFiles);
        if (warnings == 0) {
            logger.info("warnings=" + warnings);
        } else {
            logger.warn("warnings=" + warnings);
        }
        if (errors == 0) {
            logger.info("errors=" + errors);
        } else {
            logger.warn("errors=" + errors);
        }
    }

    private void resetCounters() {
        platforms = 0;
        templates = 0;
        disabledTemplates = 0;
        preservedFiles = 0;
        copiedFiles = 0;
        mergedFiles = 0;
        warnings = 0;
        errors = 0;
    }

    private boolean isInvalidBootstrapping() {
        boolean invalid = false;
        if (bootstrappingRootFolderNotVisible) {
            logger.error("root folder is missing or invalid");
            invalid = true;
        }
        if (bootstrappingVelocityFolders == null || bootstrappingVelocityFolders.length == 0) {
            logger.error("velocity folder is missing or invalid");
            invalid = true;
        }
        if (bootstrappingVelocityPropertiesFileNotVisible) {
            logger.error("velocity properties file is missing or invalid");
            invalid = true;
        }
        if (bootstrappingPlatformsFolders == null || bootstrappingPlatformsFolders.length == 0) {
            logger.error("platforms folder is missing or invalid");
            invalid = true;
        }
        return invalid;
    }

    /*
    private void log(Level level, String message) {
        if (LogUtils.foul(logger, level)) {
            return;
        }
        logger.log(level, message);
    }

    /**/
    private void log(Level level, String method, Object... parameters) {
        if (LogUtils.foul(logger, level)) {
            return;
        }
        String message = signature(method, parameters);
        logger.log(level, message);
    }

    /*
    private void log(Level level, VelocityContext context) {
        if (LogUtils.foul(logger, level)) {
            return;
        }
        Object[] keys = context.getKeys();
        Arrays.sort(keys);
        logger.log(level, "context.keys.length=" + keys.length);
        int n = 0;
        for (Object key : keys) {
            Object value = context.get("" + key);
            logger.log(level, key + "=" + value);
            n++;
        }
        logger.log(level, "context.keys.listed=" + n);
    }

    /**/
    private String signature(String method, Object... parameters) {
        String pattern = "{0}({1})";
        return MessageFormat.format(pattern, method, StringUtils.join(parameters, ", "));
    }

    private void error(Throwable throwable) {
        Throwable cause = ThrowableUtils.getCause(throwable);
        String message = throwable.equals(cause) ? throwable.getClass().getSimpleName() : throwable.getMessage();
        logger.error(message, cause);
        errors++;
    }

    private class WriterContext {

        private VelocityContext velocityContext;

        Map<String, Programmer> programmers;

        Map<String, Class<? extends Wrapper>> wrapperClasses;

        private VelocityContext getVelocityContextClone() {
            return (VelocityContext) velocityContext.clone();
        }

    }

    public void write() {
        log(Level.INFO, "write", "platform=" + platform);
        Writer writer = new Writer(platformBean, "root");
        writer.write("meta-platform-sql");
    }

}
