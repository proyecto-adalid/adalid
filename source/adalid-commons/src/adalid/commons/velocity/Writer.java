/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.velocity;

import adalid.commons.TLB;
import adalid.commons.enums.LoggingLevel;
import adalid.commons.i18n.EnglishNoun;
import adalid.commons.interfaces.Programmer;
import adalid.commons.interfaces.Wrappable;
import adalid.commons.interfaces.Wrapper;
import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.BitUtils;
import adalid.commons.util.FilUtils;
import adalid.commons.util.StrUtils;
import adalid.commons.util.ThrowableUtils;
import adalid.commons.util.TimeUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

/**
 * @author Jorge Campins
 */
public class Writer {

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
        _alertLevel = check(level, Level.WARN, Level.WARN);
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
        _detailLevel = check(level, Level.OFF, Level.INFO);
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
        _trackingLevel = check(level, Level.OFF, Level.INFO);
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

    // <editor-fold defaultstate="collapsed" desc="static fields' private getters and setters">
    private static Level check(Level level, Level defaultLevel, Level maxLevel) {
        Level coalesced = coalesce(level, defaultLevel);
        return coalesced.equals(Level.OFF) ? coalesced : minimum(coalesced, maxLevel);
    }
//
//  private static Level maximum(Level level1, Level level2) {
//      Level l1 = coalesce(level1, Level.ALL);
//      Level l2 = coalesce(level2, Level.ALL);
//      return l1.isGreaterOrEqual(l2) ? l1 : l2;
//  }

    private static Level minimum(Level level1, Level level2) {
        Level l1 = coalesce(level1, Level.OFF);
        Level l2 = coalesce(level2, Level.OFF);
        return l1.isGreaterOrEqual(l2) ? l2 : l1;
    }

    private static Level coalesce(Level level1, Level level2) {
        return level1 == null ? level2 == null ? Level.ALL : level2 : level1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static final fields">
    private static final Logger logger = Logger.getLogger(Writer.class);

    private static final String FILE_RESOURCE_LOADER_PATH = "file.resource.loader.path";

    private static final String FILE_RESOURCE_LOADER_PATH_FILTER = "file.resource.loader.path.filter";

    private static final String OS_NAME = System.getProperties().getProperty("os.name");

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");

    private static final String FILE_SEPARATOR_CHARS = "/\\";

    private static final String[] FILE_SEPARATOR_STRINGS = {"/", "\\"};

    private static final String EOL = "\n";

    private static final String HINT = EOL + "hint: ";

    private static final String DOT = ".";

    private static final String HYPHEN = "-";

    private static final String SLASH = "/";

    private static final String CARET = "^";

    private static final String DOLLAR = "$";

    private static final String ASTERISK = "*";

    private static final String DO_CASCADED_DELETE = "do.cascaded.delete";

    private static final String DO_ISOLATED_DELETE = "do.isolated.delete";

    private static final String DOT_STRING = ".string";

    private static final String DOT_CLASS = ".class";

    private static final String DOT_INSTANCE = ".instance";

    private static final String DOT_PROGRAMMER = ".programmer";

    private static final String DOT_WRAPPER = ".wrapper";

    private static final String[] DOT_SUFFIXES = {DOT_STRING, DOT_CLASS, DOT_INSTANCE, DOT_PROGRAMMER, DOT_WRAPPER};

    private static final String PROPERTIES_SUFFIX = ".properties";

    private static final FileFilter propertiesFileFilter = FilUtils.nameEndsWithFilter(PROPERTIES_SUFFIX);

    private static final IOFileFilter ignoreVersionControlFilter = FileFilterUtils.makeCVSAware(FileFilterUtils.makeSVNAware(null));

    private static final File bootstrappingRootFolder = PropertiesHandler.getRootFolder();

    private static final File[] bootstrappingVelocityFolders = PropertiesHandler.getVelocityFolders();

    private static final File bootstrappingVelocityPropertiesFile = PropertiesHandler.getVelocityPropertiesFile();

    private static final File[] bootstrappingPlatformsFolders = PropertiesHandler.getPlatformsFolders();

    private static final boolean bootstrappingRootFolderNotVisible = FilUtils.isNotVisibleDirectory(bootstrappingRootFolder);

//  private static final boolean bootstrappingVelocityFolderNotVisible = FilUtils.isNotVisibleDirectory(bootstrappingVelocityFolder);
//
    private static final boolean bootstrappingVelocityPropertiesFileNotVisible = FilUtils.isNotVisibleFile(bootstrappingVelocityPropertiesFile);

//  private static final boolean bootstrappingPlatformsFolderNotVisible = FilUtils.isNotVisibleDirectory(bootstrappingPlatformsFolder);
//
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

    private static final String TP_EXECUTE_COMMAND = "execute-command";

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
    private Object subject;

    private String subjectKey;
//
//  private File velocityPropertiesFile;
//
//  private ExtendedProperties velocityExtendedProperties;
//
//  private String[] velocityFileResourceLoaderPathArray;

    private int platforms = 0;

    private int templates = 0;

    private int disabledTemplates = 0;

    private int preservedFiles = 0;

    private int copiedFiles = 0;

    private int mergedFiles = 0;

    private int warnings = 0;

    private int errors = 0;

    Set<String> optionalResourceNames;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields' public getters and setters">
    /**
     * @return the subject
     */
    public Object getSubject() {
        return subject;
    }

    /**
     * @return the subject key
     */
    public String getSubjectKey() {
        return subjectKey;
    }

    public Set<String> getOptionalResourceNames() {
        return optionalResourceNames;
    }

    public void setOptionalResourceNames(Set<String> optionalResourceNames) {
        this.optionalResourceNames = optionalResourceNames;
    }
    // </editor-fold>

    public Writer() {
        this(null, null);
    }

    public Writer(Object subject) {
        this(subject, null);
    }

    public Writer(Object subject, String subjectKey) {
        this.subject = subject == null ? this : subject;
        this.subjectKey = StringUtils.isBlank(subjectKey) ? StringUtils.uncapitalize(this.subject.getClass().getSimpleName()) : subjectKey.trim();
        logger.info(this.subjectKey + "=" + this.subject.getClass().getName() + "(" + this.subject + ")");
//      velocityPropertiesFile = PropertiesHandler.getVelocityPropertiesFile();
//      velocityExtendedProperties = PropertiesHandler.getExtendedProperties(velocityPropertiesFile);
//      velocityFileResourceLoaderPathArray = velocityExtendedProperties.getStringArray(FILE_RESOURCE_LOADER_PATH);
    }

//  public boolean write() {
//      log(Level.INFO, "write");
//      resetCounters();
//      if (isInvalidBootstrapping()) {
//          return false;
//      }
//      WriterContext basicWriterContext = newWriterContext();
//      File[] platformFiles = bootstrappingPlatformsFolder.listFiles(propertiesFileFilter);
//      Arrays.sort(platformFiles);
//      for (File platformPropertiesFile : platformFiles) {
//          writePlatform(basicWriterContext, platformPropertiesFile);
//      }
//      return errors == 0;
//  }
//
    public boolean write(String platform) {
        log(Level.INFO, "write", "platform=" + platform);
        resetCounters();
        if (isInvalidBootstrapping()) {
            return false;
        }
        String platformPropertiesFileName = platform + PROPERTIES_SUFFIX;
        for (File bootstrappingPlatformsFolder : bootstrappingPlatformsFolders) {
            File platformPropertiesFile = new File(bootstrappingPlatformsFolder, platformPropertiesFileName);
            if (FilUtils.isVisibleFile(platformPropertiesFile)) {
                WriterContext basicWriterContext = newWriterContext();
                writePlatform(basicWriterContext, platformPropertiesFile);
                return errors == 0;
            }
        }
        return false;
    }

    private void writePlatform(WriterContext basicWriterContext, File platformPropertiesFile) {
        logger.info("propertiesFile=" + platformPropertiesFile.getPath());
        try {
            VelocityContext platformContext = basicWriterContext.getVelocityContextClone();
            String platform = StringUtils.removeEndIgnoreCase(platformPropertiesFile.getName(), PROPERTIES_SUFFIX);
            platformContext.put(VC_PLATFORM, platform);
            TLB.setProgrammers(basicWriterContext.programmers);
            TLB.setWrapperClasses(basicWriterContext.wrapperClasses);
            putProperties(platformContext, platformPropertiesFile);
            Properties properties = mergeProperties(platformContext, platformPropertiesFile);
            putStrings(platformContext, properties);
            ExtendedProperties mergeExtendedProperties = mergeExtendedProperties(platformContext, platformPropertiesFile);
            deletePreviouslyGeneratedFiles(mergeExtendedProperties);
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
                String[] keyArray = folders.keySet().toArray(new String[folders.keySet().size()]);
                String slashedFilter, slashedPath;
                for (String pathfilter : pathfilters) {
                    slashedFilter = pathfilter.replace(FILE_SEPARATOR, SLASH);
                    for (String key : keyArray) {
                        slashedPath = key.replace(FILE_SEPARATOR, SLASH) + SLASH;
                        if (StringUtils.containsIgnoreCase(slashedPath, slashedFilter)) {
                            folders.remove(key);
                            logger.debug(pathfilter + " excludes " + key);
                        }
                    }
                }
            }
            File[] templateFiles;
            Properties templateProperties;
            String disabled;
            String disabledMissing;
            String pattern;
            String template;
            String message;
            String platformsFolderPath = platformsFolder.getPath(); // + FILE_SEPARATOR;
            for (File folder : folders.values()) {
                log(_detailLevel, "write", "path=" + StringUtils.removeStart(folder.getPath(), platformsFolderPath));
                templateFiles = folder.listFiles(propertiesFileFilter);
                Arrays.sort(templateFiles);
                for (File templatePropertiesFile : templateFiles) {
                    if (reject(templatePropertiesFile, pathfilters)) {
                        continue;
                    }
                    templateProperties = PropertiesHandler.loadProperties(templatePropertiesFile);
                    disabled = templateProperties.getProperty(TP_DISABLED, Boolean.FALSE.toString());
                    disabledMissing = templateProperties.getProperty(TP_DISABLED_MISSING);
                    templates++;
                    if (BitUtils.valueOf(disabled)) {
                        disabledTemplates++;
                        template = StringUtils.removeEndIgnoreCase(templatePropertiesFile.getName(), PROPERTIES_SUFFIX);
                        pattern = "template \"{0}\" ignored, check property \"{1}\" at file \"{2}\"";
                        message = MessageFormat.format(pattern, template, TP_DISABLED, templatePropertiesFile);
                        log(_alertLevel, message);
                        warnings++;
                    } else if (missing(disabledMissing)) {
                        disabledTemplates++;
                        template = StringUtils.removeEndIgnoreCase(templatePropertiesFile.getName(), PROPERTIES_SUFFIX);
                        pattern = "template \"{0}\" ignored because {3} is missing, check property \"{1}\" at file \"{2}\"";
                        message = MessageFormat.format(pattern, template, TP_DISABLED_MISSING, templatePropertiesFile, disabledMissing);
                        log(_alertLevel, message);
                        warnings++;
                    } else {
                        writeTemplate(platformWriterContext, templatePropertiesFile);
                    }
                }
            }
            platforms++;
        } catch (Throwable throwable) {
            error(throwable);
        }
        printSummary();
    }

    private boolean reject(File templatePropertiesFile, String[] pathfilters) {
        return !accept(templatePropertiesFile, pathfilters);
    }

    private boolean accept(File templatePropertiesFile, String[] pathfilters) {
        if (templatePropertiesFile == null) {
            return false;
        }
        if (pathfilters == null || pathfilters.length == 0) {
            return true;
        }
        String name = StringUtils.removeEndIgnoreCase(templatePropertiesFile.getName(), PROPERTIES_SUFFIX);
        String lowerName = name.toLowerCase();
        String fileFilter;
        String anything = ".*";
        String delimiters = "[\\W]";
        String word = "[\\w\\-]*";
        for (String pathfilter : pathfilters) {
            fileFilter = pathfilter.replace(FILE_SEPARATOR, SLASH);
            fileFilter = fileFilter.toLowerCase();
            fileFilter = StringUtils.removeStart(fileFilter, SLASH);
            fileFilter = StringUtils.removeEnd(fileFilter, SLASH);
            if (fileFilter.isEmpty()) {
                continue;
            }
            if (fileFilter.matches(word)) {
                if (lowerName.matches(CARET + anything + delimiters + fileFilter + delimiters + anything + DOLLAR)) {
                    logger.debug("filter " + ASTERISK + pathfilter + ASTERISK + " excludes " + name);
                    return false;
                }
                if (lowerName.matches(CARET + fileFilter + delimiters + anything)) {
                    logger.debug("filter " + pathfilter + ASTERISK + " excludes " + name);
                    return false;
                }
                if (lowerName.matches(anything + delimiters + fileFilter + DOLLAR)) {
                    logger.debug("filter " + ASTERISK + pathfilter + " excludes " + name);
                    return false;
                }
                if (lowerName.matches(CARET + fileFilter + DOLLAR)) {
                    logger.debug("filter " + pathfilter + " excludes " + name);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean missing(String resourceName) {
        return StringUtils.isNotBlank(resourceName) && optionalResourceNames != null && !optionalResourceNames.contains(resourceName);
    }

    private void writeTemplate(WriterContext platformWriterContext, File templatePropertiesFile) {
        log(_trackingLevel, "write", "template=" + templatePropertiesFile);
        VelocityContext templateContext = platformWriterContext.getVelocityContextClone();
        TLB.setProgrammers(platformWriterContext.programmers);
        TLB.setWrapperClasses(platformWriterContext.wrapperClasses);
        putProperties(templateContext, templatePropertiesFile);
        WriterContext templateWriterContext = newWriterContext(templateContext);
        try {
            ForEach forEach = new ForEach(templatePropertiesFile);
            if (forEach.start == null) {
                writeFile(templateWriterContext, templatePropertiesFile);
            } else {
                writeTemplate(templateWriterContext, templatePropertiesFile, forEach.start, subject);
            }
        } catch (Throwable throwable) {
            error(throwable);
        }
    }

    @SuppressWarnings("unchecked") // unchecked conversion & method invocation
    private void writeTemplate(WriterContext templateWriterContext, File templatePropertiesFile, ForEachVariable v, Object o) {
        Object object = invoke(templatePropertiesFile, v, o);
        if (object instanceof Collection<?>) {
            Collection<?> collection = new ArrayList<>((Collection<?>) object);
            if (collection.isEmpty()) {
                return;
            }
            if (v.predicate != null) {
                CollectionUtils.filter(collection, v.predicate);
                if (collection.isEmpty()) {
                    return;
                }
            }
            if (v.comparator != null) {
                if (collection instanceof List<?>) {
                    List<?> list = (List<?>) collection;
                    Collections.sort(list, v.comparator); // unchecked conversion & method invocation
                }
            }
            for (Object element : collection) {
                writeTemplate(templateWriterContext, templatePropertiesFile, v, element, true);
            }
        } else if (object != null && (v.predicate == null || v.predicate.evaluate(object))) {
            writeTemplate(templateWriterContext, templatePropertiesFile, v, object, true);
        }
    }

    private void writeTemplate(WriterContext templateWriterContext, File templatePropertiesFile, ForEachVariable v, Object o, boolean b) {
        templateWriterContext.velocityContext.put(v.token, o);
        logger.debug(v.token + "=" + o);
        if (v.next == null) {
            writeFile(templateWriterContext, templatePropertiesFile);
        } else {
            writeTemplate(templateWriterContext, templatePropertiesFile, v.next, o);
        }
    }

    private void writeFile(WriterContext templateWriterContext, File templatePropertiesFile) {
        VelocityContext fileContext = templateWriterContext.getVelocityContextClone();
        TLB.setProgrammers(templateWriterContext.programmers);
        TLB.setWrapperClasses(templateWriterContext.wrapperClasses);
        Properties properties = mergeProperties(fileContext, templatePropertiesFile);
        putStrings(fileContext, properties);
//      String rootPath = getRootPath(fileContext);
        String userPath = pathString(USER_DIR);
        String temptype = StringUtils.defaultIfBlank(properties.getProperty(TP_TYPE), TP_TYPE_VELOCITY);
        String template = StringUtils.trimToNull(properties.getProperty(TP_TEMPLATE));
        String filePath = StringUtils.trimToNull(properties.getProperty(TP_PATH));
        String filePack = StringUtils.trimToNull(properties.getProperty(TP_PACKAGE));
        String fileName = StringUtils.trimToNull(properties.getProperty(TP_FILE));
        String preserve = StringUtils.trimToNull(properties.getProperty(TP_PRESERVE));
        String charset1 = StringUtils.trimToNull(properties.getProperty(TP_ENCODING));
        String charset2 = StringUtils.trimToNull(properties.getProperty(TP_CHARSET));
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
//          filePath = rootPath;
            filePath = userPath;
        } else {
            filePath = pathString(filePath);
            if (isRelativePath(filePath)) {
                if (filePath.startsWith(FILE_SEPARATOR)) {
//                  filePath = rootPath + filePath;
                    filePath = userPath + filePath;
                } else {
//                  filePath = rootPath + FILE_SEPARATOR + filePath;
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
        if (path.exists()) {
            if (path.isDirectory() && path.canWrite()) {
            } else {
                String pattern = "{2} is not a valid directory" + hint;
                String message = MessageFormat.format(pattern, TP_PATH, templatePropertiesFile, path);
                logger.error(message);
                errors++;
                return;
            }
        } else if (path.mkdirs()) {
        } else {
            String pattern = "{2} is not a valid path" + hint;
            String message = MessageFormat.format(pattern, TP_PATH, templatePropertiesFile, path);
            logger.error(message);
            errors++;
            return;
        }
        String fullname = path.getPath() + FILE_SEPARATOR + fileName;
        if (BitUtils.valueOf(preserve) && FilUtils.exists(fullname)) {
            preservedFiles++;
            String pattern = "file {2} already exists and will not be replaced" + hint;
            String message = MessageFormat.format(pattern, TP_PRESERVE, templatePropertiesFile, fullname);
            log(_detailLevel, message);
            return;
        }
        if (charset1 != null && !Charset.isSupported(charset1)) {
            String pattern = "{2} is not a supported character set" + hint;
            String message = MessageFormat.format(pattern, TP_ENCODING, templatePropertiesFile, charset1);
            logger.error(message);
            errors++;
            return;
        }
        if (charset2 != null && !Charset.isSupported(charset2)) {
            String pattern = "{2} is not a supported character set" + hint;
            String message = MessageFormat.format(pattern, TP_CHARSET, templatePropertiesFile, charset2);
            logger.error(message);
            errors++;
            return;
        }
        fileContext.put(VC_FILE_PATH, StringEscapeUtils.escapeJava(filePath));
        fileContext.put(VC_FILE_NAME, StringEscapeUtils.escapeJava(fileName));
        fileContext.put(VC_FILE_PATH_NAME, StringEscapeUtils.escapeJava(fullname));
        fileContext.put(VC_FILE_PATH_FILE, path);
        if (temptype.equals(TP_TYPE_VELOCITY)) {
            writeFile(fileContext, template, fullname, charset1, charset2);
        } else {
            writeFile(template, fullname);
        }
        executeFile(fileContext, templatePropertiesFile);
    }

    private void writeFile(VelocityContext context, String template, String filename, String charset1, String charset2) {
        log(_trackingLevel, "writeFile", "template=" + template, "filename=" + filename);
        log(Level.DEBUG, context);
        String message = "failed to write file \"" + filename + "\" using template \"" + template + "\"";
        try {
            VelocityEngineer.write(context, template, filename, charset1, charset2);
            mergedFiles++;
        } catch (Exception ex) {
            throw new RuntimeException(message, ex);
        }
    }

    private void writeFile(String document, String filename) {
        log(_trackingLevel, "writeFile", "document=" + document, "filename=" + filename);
        String message = "failed to write file \"" + filename + "\" copying document \"" + document + "\"";
        String path = FILE_SEPARATOR + document.replaceAll(SLASH, "\\" + FILE_SEPARATOR);
        String[] fileResourceLoaderPathArray = VelocityEngineer.getFileResourceLoaderPathArray();
        if (fileResourceLoaderPathArray != null && fileResourceLoaderPathArray.length > 0) {
            File source, target;
            try {
                for (String frlp : fileResourceLoaderPathArray) {
                    source = new File(frlp + path);
                    if (FilUtils.isVisibleFile(source)) {
                        target = new File(filename);
                        FileUtils.copyFile(source, target);
                        copiedFiles++;
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(message, ex);
            }
        }
    }

    private void executeFile(VelocityContext fileContext, File templatePropertiesFile) {
        Properties properties = mergeProperties(fileContext, templatePropertiesFile);
        String command = StringUtils.trimToNull(properties.getProperty(TP_EXECUTE_COMMAND));
        String directory = StringUtils.trimToNull(properties.getProperty(TP_EXECUTE_DIRECTORY));
        if (command != null) {
            StrTokenizer tokenizer = new StrTokenizer(command);
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(tokenizer.getTokenArray());
            if (directory != null) {
                File dir = new File(directory);
                if (dir.exists() && dir.isDirectory()) {
                    if (dir.isAbsolute()) {
                        processBuilder.directory(dir);
                    } else {
                        processBuilder.directory(dir.getAbsoluteFile());
                    }
                }
            }
            try {
                Process process = processBuilder.start();
                int w = process.waitFor();
                int x = process.exitValue();
                logger.info(command + " = " + w + "," + x);
            } catch (IOException | InterruptedException ex) {
                error(ex);
            }
        }
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
        putClass(context, java.text.ChoiceFormat.class);
        putClass(context, java.text.DateFormat.class);
        putClass(context, java.text.DateFormatSymbols.class);
        putClass(context, java.text.DecimalFormat.class);
        putClass(context, java.text.DecimalFormatSymbols.class);
        putClass(context, java.text.MessageFormat.class);
        putClass(context, java.text.NumberFormat.class);
        putClass(context, java.text.SimpleDateFormat.class);
        putClass(context, java.util.Locale.class);
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
        context.put(subjectKey, subject);
        context.put(VC_ROOT_PATH, StringEscapeUtils.escapeJava(path));
        context.put(VC_ROOT_SLASHED_PATH, path.replace('\\', '/'));
        context.put(VC_USER_PATH, StringEscapeUtils.escapeJava(USER_DIR));
        context.put(VC_USER_SLASHED_PATH, USER_DIR.replace('\\', '/'));
        context.put(VC_BUILD_DATE, TimeUtils.simpleDateString());
        context.put(VC_BUILD_TIMESTAMP, TimeUtils.simpleTimestampString());
        //
        logger.info(subjectKey + "=" + subject);
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
        String pattern2 = "failed to initialise {2}" + hint;
        String pattern3 = "{2} does not implement {3}" + hint;
        String pattern4 = "{2} is not a valid wrapper for {3}" + hint;
        String string1;
        String string2;
        String message;
        String argument;
//      Object object1;
        Object object2;
        Class<?> clazz1;
        Class<?> clazz2;
        Class<? extends Wrapper> wrapperClass;
        Class<? extends Wrappable> wrappableClass;
        Class<?> parameterType;
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
            } else if (StringUtils.endsWithIgnoreCase(name, DOT_INSTANCE)) {
                string1 = StringUtils.removeEndIgnoreCase(name, DOT_INSTANCE);
                string2 = StringUtils.trimToEmpty(properties.getProperty(name));
                message = MessageFormat.format(pattern2, name, propertiesFile, string2);
                object2 = getNewInstanceForName(string2, message);
                if (object2 != null) {
                    velocityKey = StrUtils.getCamelCase(string1, true);
                    context.put(velocityKey, object2);
                    continue;
                }
                throw new RuntimeException(message, new IllegalArgumentException(string2));
            } else if (StringUtils.endsWithIgnoreCase(name, DOT_PROGRAMMER)) {
                string1 = StringUtils.removeEndIgnoreCase(name, DOT_PROGRAMMER);
                string2 = StringUtils.trimToEmpty(properties.getProperty(name));
                message = MessageFormat.format(pattern2, name, propertiesFile, string2);
                object2 = getNewInstanceForName(string2, message);
                if (object2 != null) {
                    if (object2 instanceof Programmer) {
                        velocityKey = StrUtils.getCamelCase(string1, true);
                        context.put(velocityKey, object2);
                        TLB.setProgrammer(velocityKey, (Programmer) object2);
                        continue;
                    } else {
                        message = MessageFormat.format(pattern3, name, propertiesFile, string2, Programmer.class);
                    }
                }
                throw new RuntimeException(message, new IllegalArgumentException(string2));
            } else if (StringUtils.endsWithIgnoreCase(name, DOT_WRAPPER)) {
                string1 = StringUtils.removeEndIgnoreCase(name, DOT_WRAPPER);
                string2 = StringUtils.trimToEmpty(properties.getProperty(name));
                message = MessageFormat.format(pattern1, name, propertiesFile, string1);
                argument = string1;
                clazz1 = getClassForName(string1, message);
                if (clazz1 != null) {
                    if (Wrappable.class.isAssignableFrom(clazz1)) {
                        message = MessageFormat.format(pattern1, name, propertiesFile, string2);
                        argument = string2;
                        clazz2 = getClassForName(string2, message);
                        if (clazz2 != null) {
                            if (Wrapper.class.isAssignableFrom(clazz2)) {
                                wrapperClass = (Class<? extends Wrapper>) clazz2; // unchecked cast
                                wrappableClass = (Class<? extends Wrappable>) clazz1; // unchecked cast
                                parameterType = getConstructorParameterType(wrapperClass, wrappableClass);
                                if (parameterType != null) {
                                    TLB.setWrapperClass(wrappableClass, wrapperClass);
                                    continue;
                                } else {
                                    message = MessageFormat.format(pattern4, name, propertiesFile, wrapperClass, wrappableClass);
                                }
                            } else {
                                message = MessageFormat.format(pattern3, name, propertiesFile, string2, Wrapper.class);
                            }
                        }
                    } else {
                        message = MessageFormat.format(pattern3, name, propertiesFile, string1, Wrappable.class);
                    }
                }
                throw new RuntimeException(message, new IllegalArgumentException(argument));
            }
        }
    }

    private Class<?> getConstructorParameterType(Class<?> wrapper, Class<?> wrappable) {
        Class<?> parameterType = null;
        Constructor<?>[] constructors = wrapper.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(wrappable)) {
                if (parameterType == null || parameterType.isAssignableFrom(parameterTypes[0])) {
                    parameterType = parameterTypes[0];
                }
            }
        }
        return parameterType;
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

    private Object getNewInstanceForName(String className, String message) {
        Class<?> clazz = getClassForName(className, message);
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
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
//      return StringUtils.removeStartIgnoreCase(template, SLASH);
        return template;
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

    @SuppressWarnings("unchecked")
    private void deletePreviouslyGeneratedFiles(ExtendedProperties properties) {
        String[] stringArray;
        Set<String> stringPropertyNames = properties.keySet();
        for (String name : stringPropertyNames) {
            switch (name) {
                case DO_CASCADED_DELETE:
                    stringArray = properties.getStringArray(name);
                    deletePreviouslyGeneratedFiles(name, stringArray, true);
                    break;
                case DO_ISOLATED_DELETE:
                    stringArray = properties.getStringArray(name);
                    deletePreviouslyGeneratedFiles(name, stringArray, false);
                    break;
            }
        }
    }

    private void deletePreviouslyGeneratedFiles(String name, String[] stringArray, boolean recursive) {
        String root = getRoot().getPath();
        String raiz = root.replace('\\', '/');
        String path, pathname, wildcard;
        String recursively = recursive ? "and all its subdirectories" : "";
        File directory;
        IOFileFilter fileFilter;
        IOFileFilter dirFilter = recursive ? ignoreVersionControlFilter : null;
        Collection<File> matchingFiles;
        Arrays.sort(stringArray);
        for (String string : stringArray) {
            pathname = StringUtils.substringBeforeLast(string, SLASH);
            wildcard = StringUtils.substringAfterLast(string, SLASH);
            if (StringUtils.isBlank(wildcard)) {
                logger.error("invalid property: " + name + "=" + string);
                continue;
            }
            directory = new File(pathname);
//          if (FilUtils.isNotVisibleDirectory(directory)) {
            if (FilUtils.isNotWritableDirectory(directory)) {
                log(_alertLevel, pathname + " is not a valid directory, check property: " + name + "=" + string);
                continue;
            }
            log(_detailLevel, "deleting files " + wildcard + " from " + StringUtils.removeStartIgnoreCase(pathname, raiz) + " " + recursively);
            fileFilter = new WildcardFileFilter(wildcard);
            matchingFiles = FileUtils.listFiles(directory, fileFilter, dirFilter);
            for (File file : matchingFiles) {
                path = file.getPath();
                logger.trace("deleting " + StringUtils.removeStartIgnoreCase(path, root));
                FileUtils.deleteQuietly(file);
            }
        }
    }

    private Object invoke(File templatePropertiesFile, ForEachVariable variable, Object object) {
        String pattern = "failed to get a valid getter for for-each variable \"{0}\"";
        pattern += HINT + "a valid getter is a public zero-arguments method that returns either an object or a collection";
        String message;
        if (variable.getter == null) {
            Set<String> getters = gettersOf(variable.token);
            String strip = StringUtils.strip(getters.toString(), "[]");
            pattern += HINT + object.getClass() + " doesn''t implement any of these: " + strip;
            pattern += HINT + "add property \"{0}.getter\" to file \"{1}\"";
            message = MessageFormat.format(pattern, variable.token, templatePropertiesFile);
            return invoke(object, getters, message);
        } else {
            pattern += HINT + "check property \"{0}.getter\" at file \"{1}\"";
            message = MessageFormat.format(pattern, variable.token, templatePropertiesFile);
            return invoke(object, variable.getter, message);
        }
    }

    private Object invoke(Object object, Set<String> getters, String message) {
        Method[] methods = object.getClass().getMethods();
        for (String getter : getters) {
            for (Method method : methods) {
                if (getter.equals(method.getName())) {
                    if (method.getParameterTypes().length == 0) {
                        if (void.class.equals(method.getReturnType())) {
                            break;
                        } else {
                            return invoke(object, method);
                        }
                    }
                }
            }
        }
        throw new RuntimeException(message, new NoSuchMethodException(hintless(message)));
    }

    private Object invoke(Object object, String method, String NoSuchMethodExceptionMessage) {
        try {
            return invoke(object, object.getClass().getMethod(method));
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(NoSuchMethodExceptionMessage, ex);
        }
    }

    private Object invoke(Object object, Method method) {
        try {
            method.setAccessible(true);
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Set<String> gettersOf(String token) {
        Set<String> set = new LinkedHashSet<>();
        set.add(listGetterOf(token));
        set.add(pluralGetterOf(token));
        set.add(singularGetterOf(token));
        set.add(defaultGetterOf(token));
        return set;
    }

    private String listGetterOf(String token) {
        String symbol = pluralIdentifierOf(token);
        String getter = StrUtils.getCamelCase("get-" + symbol + "-list", true);
        return getter;
    }

    private String pluralGetterOf(String token) {
        String symbol = pluralIdentifierOf(token);
        String getter = StrUtils.getCamelCase("get-" + symbol, true);
        return getter;
    }

    private String singularGetterOf(String token) {
        String symbol = singularIdentifierOf(token);
        String getter = StrUtils.getCamelCase("get-" + symbol, true);
        return getter;
    }

    private String defaultGetterOf(String token) {
        String symbol = identifierOf(token);
        String getter = StrUtils.getCamelCase("get-" + symbol, true);
        return getter;
    }

    private String pluralIdentifierOf(String token) {
        String[] tokens = tokensOf(token);
        int i = tokens.length - 1;
        tokens[i] = EnglishNoun.pluralOf(tokens[i]);
        tokens[i] = StrUtils.getIdentifier(tokens[i], "");
        return identifierOf(tokens);
    }

    private String singularIdentifierOf(String token) {
        String[] tokens = tokensOf(token);
        int i = tokens.length - 1;
        tokens[i] = EnglishNoun.singularOf(tokens[i]);
        tokens[i] = StrUtils.getIdentifier(tokens[i], "");
        return identifierOf(tokens);
    }

    private String identifierOf(String token) {
        String[] tokens = tokensOf(token);
        return identifierOf(tokens);
    }

    private String identifierOf(String[] tokens) {
        String join = StringUtils.join(tokens, '-');
        return StrUtils.getCamelCase(join);
    }

    private String[] tokensOf(String token) {
        String string = StrUtils.getCamelCase(token, true);
        String[] tokens = StringUtils.splitByCharacterTypeCamelCase(string);
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = StrUtils.getIdentifier(tokens[i], "");
        }
        return tokens;
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
//
//  private String getRootPath(VelocityContext context) {
//      Object object = context.get(VC_ROOT_PATH);
//      String string = trimSplitJoin(object, FILE_SEPARATOR_CHARS, FILE_SEPARATOR);
//      return string == null ? getRoot().getPath() : string;
//  }

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

    private void log(Level level, String message) {
        if (foul(logger, level)) {
            return;
        }
        logger.log(level, message);
    }

    private void log(Level level, String method, Object... parameters) {
        if (foul(logger, level)) {
            return;
        }
        String message = signature(method, parameters);
        logger.log(level, message);
    }

    private void log(Level level, VelocityContext context) {
        if (foul(logger, level)) {
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

    private boolean fair(Logger logger, Level level) {
        return logger != null && level != null && logger.isEnabledFor(level) && !level.equals(Level.OFF);
    }

    private boolean foul(Logger logger, Level level) {
        return !fair(logger, level);
    }

    private String hintless(String message) {
        int i = message == null ? 0 : message.indexOf(HINT);
        return message != null && i > 0 ? message.substring(0, i) : message;
    }

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

    private class ForEach {

        private final String string;

        private ForEachVariable start;

        private ForEach(File templatePropertiesFile) {
            ExtendedProperties templateExtendedProperties = PropertiesHandler.getExtendedProperties(templatePropertiesFile);
            string = templateExtendedProperties.getString(TP_FOR_EACH);
            String[] tokens = StringUtils.split(string, '.');
            if (tokens != null) {
                String token;
                ForEachVariable variable;
                ForEachVariable previous;
                Map<String, ForEachVariable> variables = new LinkedHashMap<>();
                for (int i = 0; i < tokens.length; i++) {
                    token = tokens[i];
                    if (variables.containsKey(token)) {
                        String pattern = "{0} (token \"{1}\" is used more than once)";
                        String message = MessageFormat.format(pattern, string, token);
                        throw new IllegalArgumentException(message);
                    }
                    variable = new ForEachVariable();
                    variable.token = token;
                    variable.getter = templateExtendedProperties.getString(token + ".getter");
                    variable.predicate = getPredicate(templateExtendedProperties, token);
                    variable.comparator = getComparator(templateExtendedProperties, token);
                    if (i == 0) {
                        start = variable;
                    } else {
                        previous = variables.get(tokens[i - 1]);
                        previous.next = variable;
                    }
                    variables.put(token, variable);
                }
            }
        }

        private Predicate getPredicate(ExtendedProperties properties, String keyPreffix) {
            String key;
            String[] classNames;
            Predicate p;
            List<Predicate> predicates = new ArrayList<>();
            key = keyPreffix + ".predicate";
            classNames = properties.getStringArray(key);
            for (String name : classNames) {
                p = getPredicate(name);
                if (p != null) {
                    predicates.add(p);
                }
            }
            key = keyPreffix + ".not.predicate";
            classNames = properties.getStringArray(key);
            for (String name : classNames) {
                p = getPredicate(name);
                if (p != null) {
                    predicates.add(PredicateUtils.notPredicate(p));
                }
            }
            return predicates.isEmpty() ? null : getPredicate(properties, predicates, keyPreffix);
        }

        private Predicate getPredicate(String className) {
            String message = "failed to create a new instance of " + className;
            Object p = getNewInstanceForName(className, message);
            return p instanceof Predicate ? (Predicate) p : null;
        }

        private Predicate getPredicate(ExtendedProperties properties, List<Predicate> predicates, String keyPreffix) {
            String key = keyPreffix + ".predicate.join";
            String operator = properties.getString(key, "all");
            String[] operators = new String[]{"all", "any", "none", "one"};
            int i = ArrayUtils.indexOf(operators, operator.toLowerCase());
            switch (i) {
                case 1:
                    return PredicateUtils.anyPredicate(predicates);
                case 2:
                    return PredicateUtils.nonePredicate(predicates);
                case 3:
                    return PredicateUtils.onePredicate(predicates);
                default:
                    return PredicateUtils.allPredicate(predicates);
            }
        }

        @SuppressWarnings("unchecked") // unchecked cast
        private Comparator<?> getComparator(ExtendedProperties properties, String keyPreffix) {
            String key = keyPreffix + ".comparator";
            String[] classNames = properties.getStringArray(key);
            List<Comparator<?>> comparators = new ArrayList<>();
            Comparator<?> c;
            for (String name : classNames) {
                c = getComparator(name);
                if (c != null) {
                    comparators.add(c);
                }
            }
            return comparators.isEmpty() ? null : (Comparator<?>) ComparatorUtils.chainedComparator(comparators); // unchecked cast
        }

        @SuppressWarnings("unchecked") // unchecked cast
        private Comparator<?> getComparator(String className) {
            String message = "failed to create a new instance of " + className;
            Object c = getNewInstanceForName(className, message);
            return c instanceof Comparator<?> ? (Comparator<?>) c : null; // unchecked cast
        }

        @Override
        public String toString() {
            return ForEach.class.getSimpleName() + "{" + string + "}";
        }

    }

    private class ForEachVariable {

        private String token;

        private String getter;

        private Predicate predicate;

        private Comparator comparator;

        private ForEachVariable next;

        @Override
        public String toString() {
            return ForEachVariable.class.getSimpleName() + "{token=" + token + ", getter=" + getter + "}";
        }

    }

    private class WriterContext {

        private VelocityContext velocityContext;

        Map<String, Programmer> programmers;

        Map<String, Class<? extends Wrapper>> wrapperClasses;

        private VelocityContext getVelocityContextClone() {
            return (VelocityContext) velocityContext.clone();
        }

    }

}
