/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.velocity;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.FilUtils;
import adalid.commons.util.StrUtils;
import adalid.util.Utility;
import adalid.util.io.RegexPathFilter;
import adalid.util.io.SmallFile;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SecondBaseBuilder extends Utility {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    protected static final String B = "^";

    protected static final String E = "$";

    protected static final String X = ".*";

    protected static final String S = RegexPathFilter.SEPARATOR;

    protected static final String D = "\\.";

    protected static final String NSX = "[^" + S + "]*";

    protected static final String FS = System.getProperties().getProperty("file.separator");

    protected static final String VM = ".vm";

    protected static final Logger logger = Logger.getLogger(SecondBaseBuilder.class);

    protected static final Charset WINDOWS_CHARSET = Charset.forName("windows-1252");
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private String project;

    private String PROJECT;

    private String platform;

    private String rootFolderPath;

    private String projectFolderPath;

    private String velocityFolderPath;

    private String velocityPlatformsTargetFolderPath;

    private String velocityTemplatesTargetFolderPath;

    private File rootFolder;

    private File projectFolder;

    private File velocityFolder;

    private File velocityPlatformsTargetFolder;

    private File velocityTemplatesTargetFolder;

    private String[] optionalEntityNames, preservableFileNames, preservableFileExpressions;

    private int binaryFilesCopied, textFilesCopied, propertiesFilesCreated, readingWarnings, readingErrors, writingErrors;

    private final Map<String, Map<String, Integer>> extensionCharsetMap = new TreeMap<>();
    // </editor-fold>

    /**
     * @return the project
     */
    public String getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(String project) {
        this.project = project;
    }

    /**
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * @return the rootFolderPath
     */
    public String getRootFolderPath() {
        return rootFolderPath;
    }

    /**
     * @param rootFolderPath the rootFolderPath to set
     */
    public void setRootFolderPath(String rootFolderPath) {
        this.rootFolderPath = FilUtils.fixPath(rootFolderPath);
    }

    /**
     * @return the projectFolderPath
     */
    public String getProjectFolderPath() {
        return projectFolderPath;
    }

    /**
     * @param projectFolderPath the projectFolderPath to set
     */
    public void setProjectFolderPath(String projectFolderPath) {
        this.projectFolderPath = FilUtils.fixPath(projectFolderPath);
    }

    /**
     * @return the velocityFolderPath
     */
    public String getVelocityFolderPath() {
        return velocityFolderPath;
    }

    /**
     * @param velocityFolderPath the velocityFolderPath to set
     */
    public void setVelocityFolderPath(String velocityFolderPath) {
        this.velocityFolderPath = FilUtils.fixPath(velocityFolderPath);
    }

    /**
     * @return the root folder file
     */
    public File getRootFolder() {
        return rootFolder;
    }

    /**
     * @return the project folder file
     */
    public File getProjectFolder() {
        return projectFolder;
    }

    /**
     * @return the velocity folder file
     */
    public File getVelocityFolder() {
        return velocityFolder;
    }

    /**
     * @return the optional entity names array
     */
    public String[] getOptionalEntityNames() {
        return optionalEntityNames;
    }

    /**
     * @param optionalEntityNames the optional entity names array to set
     */
    public void setOptionalEntityNames(String[] optionalEntityNames) {
        this.optionalEntityNames = optionalEntityNames;
    }

    /**
     * @return the preservable file names array
     */
    public String[] getPreservableFileNames() {
        return preservableFileNames;
    }

    /**
     * @param preservableFileNames the preservable file names array to set
     */
    public void setPreservableFileNames(String[] preservableFileNames) {
        this.preservableFileNames = preservableFileNames;
    }

    /**
     * @return the preservable file expressions array
     */
    public String[] getPreservableFileExpressions() {
        return preservableFileExpressions;
    }

    /**
     * @param preservableFileExpressions the preservable file expressions array to set
     */
    public void setPreservableFileExpressions(String[] preservableFileExpressions) {
        this.preservableFileExpressions = preservableFileExpressions;
    }

    public void build() {
        init();
        printParameters();
        try {
            cleanVelocityDirectories();
            copyBinaryFiles();
            copyTextFiles();
        } catch (IOException ex) {
            logger.fatal(ex);
        }
        printSummary();
    }

    private void init() {
        if (StringUtils.isBlank(project)) {
            throw new RuntimeException("project is missing");
        }
        PROJECT = project.toUpperCase();
        if (StringUtils.isBlank(platform)) {
            platform = project.substring(0, 4);
        }
        if (StringUtils.isBlank(rootFolderPath)) {
            rootFolder = PropertiesHandler.getRootFolder();
            rootFolderPath = rootFolder.getPath();
        } else {
            rootFolder = new File(rootFolderPath);
        }
        if (rootFolder == null || !rootFolder.isDirectory()) {
            throw new RuntimeException("invalid root folder: " + rootFolder);
        }
        if (StringUtils.isBlank(projectFolderPath)) {
            projectFolderPath = rootFolderPath + FS + project;
        }
        projectFolder = new File(projectFolderPath);
        if (!projectFolder.isDirectory()) {
            throw new RuntimeException("invalid project folder: " + projectFolder);
        }
        if (StringUtils.isBlank(velocityFolderPath)) {
            velocityFolderPath = rootFolderPath + FS + "adalid" + FS + "source" + FS + "development" + FS + "resources" + FS + "velocity";
        }
        velocityFolder = new File(velocityFolderPath);
        if (!velocityFolder.isDirectory()) {
            velocityFolder.mkdirs();
            if (!velocityFolder.isDirectory()) {
                throw new RuntimeException("invalid velocity folder: " + velocityFolder);
            }
        }
        velocityPlatformsTargetFolderPath = velocityFolderPath + FS + "platforms" + FS + platform + FS + "base";
        velocityTemplatesTargetFolderPath = velocityFolderPath + FS + "templates" + FS + platform + FS + "base";
        velocityPlatformsTargetFolder = new File(velocityPlatformsTargetFolderPath);
        velocityTemplatesTargetFolder = new File(velocityTemplatesTargetFolderPath);
        if (optionalEntityNames == null) {
            optionalEntityNames = optionalEntityNames();
        }
        if (preservableFileNames == null) {
            preservableFileNames = preservableFileNames();
        }
        if (preservableFileExpressions == null) {
            preservableFileExpressions = preservableFileExpressions();
        }
        binaryFilesCopied = 0;
        textFilesCopied = 0;
        propertiesFilesCreated = 0;
        readingWarnings = 0;
        readingErrors = 0;
        writingErrors = 0;
        extensionCharsetMap.clear();
    }

    private void printParameters() {
        logger.info("project=" + project);
        logger.info("platform=" + platform);
        logger.info("rootFolderPath=" + rootFolderPath);
        logger.info("projectFolderPath=" + projectFolderPath);
        logger.info("velocityFolderPath=" + velocityFolderPath);
//      logger.info("velocityPlatformsTargetFolderPath=" + velocityPlatformsTargetFolderPath);
//      logger.info("velocityTemplatesTargetFolderPath=" + velocityTemplatesTargetFolderPath);
    }

    private void cleanVelocityDirectories() throws IOException {
        FileUtils.deleteDirectory(velocityPlatformsTargetFolder);
        velocityPlatformsTargetFolder.mkdirs();
        FileUtils.deleteDirectory(velocityTemplatesTargetFolder);
        velocityTemplatesTargetFolder.mkdirs();
    }

    private static final String PROJECT_ALIAS = "${project.alias}";

    private static final String PROJECT_ALIAS_UPPER_CASE = "${project.alias.toUpperCase()}";

    private static final String DATABASE_NAME = "${project.databaseName}";

    private static final String ROOT_FOLDER_SLASHED_PATH = "${rootFolderSlashedPath}";

    private static final String BASE_FOLDER_NAME = "${project.baseFolderName}";

    private static final String ROOT_FOLDER_NAME = "${project.rootFolderName}";

    private static final String ROOT_PACKAGE_NAME = "${project.rootPackageName}";

    private boolean copyBinaryFiles() {
        Collection<File> files = FileUtils.listFiles(projectFolder, binaryFileFilter(), binaryDirFilter());
//      ColUtils.sort(files);
        String source, target, folder;
        for (File file : files) {
            source = file.getPath();
            target = source.replace(projectFolderPath, velocityTemplatesTargetFolderPath);
            folder = StringUtils.substringBeforeLast(target, FS);
            FilUtils.mkdirs(folder);
            try {
                copy(source, target);
                binaryFilesCopied++;
                createBinaryFilePropertiesFile(source, target);
            } catch (IOException ex) {
                writingErrors++;
                logger.fatal(ex);
                logger.fatal("\t" + source + " could not be copied ");
            }
        }
        return true;
    }

    private void createBinaryFilePropertiesFile(String source, String target) {
        List<String> lines = new ArrayList<>();
        String properties = source.replace(projectFolderPath, velocityPlatformsTargetFolderPath) + ".properties";
        String folder = StringUtils.substringBeforeLast(properties, FS);
        String template = StringUtils.substringAfter(target, velocityFolderPath + FS).
            replace(FS, "/");
        String path = StringUtils.substringBeforeLast(StringUtils.substringAfter(source, projectFolderPath), FS).
            replace(FS, "/").
            replace(project, PROJECT_ALIAS);
        path = replaceAliasWithRootFolderName(path);
        path = finalisePath(path);
        String file = StringUtils.substringAfterLast(source, FS).
            replace(project, PROJECT_ALIAS);
        lines.add("template = " + template);
        lines.add("template-type = document");
        lines.add("path = " + path);
        lines.add("file = " + file);
        lines.add("preserve = true");
        FilUtils.mkdirs(folder);
        if (write(properties, lines, WINDOWS_CHARSET)) {
            propertiesFilesCreated++;
        }
    }

    private static final String PACKAGE_REGEX = "^package [\\w\\.]*;$";

    private boolean copyTextFiles() {
        Collection<File> files = FileUtils.listFiles(projectFolder, textFileFilter(), textDirFilter());
//      ColUtils.sort(files);
        String source, target, targetParent;
        boolean java, jrxml, properties, sh, bat, xml;
        String[] precedingWords = {"extends", "import", "new", "@see", "@throws"};
        SmallFile smallSource;
        List<String> sourceLines;
        List<String> targetLines = new ArrayList<>();
        for (File file : files) {
            source = file.getPath();
            java = StringUtils.endsWithIgnoreCase(source, ".java");
            jrxml = StringUtils.endsWithIgnoreCase(source, ".jrxml");
            properties = StringUtils.endsWithIgnoreCase(source, ".properties");
            sh = StringUtils.endsWithIgnoreCase(source, ".sh");
            bat = StringUtils.endsWithIgnoreCase(source, ".bat");
            xml = StringUtils.endsWithIgnoreCase(source, ".xml");
            target = source.replace(projectFolderPath, velocityTemplatesTargetFolderPath) + VM;
            targetParent = StringUtils.substringBeforeLast(target, FS);
            targetLines.clear();
            FilUtils.mkdirs(targetParent);
            smallSource = new SmallFile(source);
            sourceLines = smallSource.read();
            check(smallSource);
            if (smallSource.isNotEmpty()) {
                for (String line : sourceLines) {
                    if (StringUtils.isNotBlank(line)) {
                        if (java && line.matches(PACKAGE_REGEX)) {
                            line = "package $package;";
                        } else {
                            line = line.replace("$", "${dollar}");
                            line = line.replace("#", "${pound}");
                            line = line.replace("\\", "${backslash}");
                            line = line.replace(project, PROJECT_ALIAS);
                            line = line.replace(PROJECT, PROJECT_ALIAS_UPPER_CASE);
                            if (java) {
                                for (String word : precedingWords) {
                                    line = replaceAliasWithRootPackageName(line, word + " ", ".");
                                }
                                line = replaceAliasWithRootPackageName(line, "String BASE_NAME = ", ".");
                                line = replaceAliasWithRootPackageName(line, "new ResourceBundleHandler(", ".");
                            }
                            if (jrxml) {
                                line = replaceAliasWithRootPackageName(line, "<import value=\"", ".");
                            }
                            if (properties) {
                                line = StrUtils.replaceAfter(line, PROJECT_ALIAS + ".", ROOT_PACKAGE_NAME + ".", "${pound}");
                                line = StrUtils.replaceAfter(line, PROJECT_ALIAS + ".", ROOT_PACKAGE_NAME + ".", "file.resource.loader.class");
                                line = replaceAliasWithRootPackageName(line, "log4j.category.", "=");
                                line = replaceAliasWithRootPackageName(line, "log4j.additivity.", "=");
                            }
                            if (sh) {
                                line = StrUtils.replaceAfter(line, PROJECT_ALIAS, DATABASE_NAME, "dbname=");
                            }
                            if (bat) {
                                line = StrUtils.replaceAfter(line, PROJECT_ALIAS, DATABASE_NAME, "dbname=");
                            }
                            if (xml) {
                                line = replaceAliasWithDatabaseName(line, "jdbc/", "");
                                line = replaceAliasWithDatabaseName(line, "localhost:5432/", "");
                                line = StrUtils.replaceAfter(line, PROJECT_ALIAS + "-pool", DATABASE_NAME + "-pool", "pool-name=");
                                line = replaceAliasWithRootPackageName(line, "<logger category=\"", "\">");
                            }
                        }
                    }
                    targetLines.add(line);
                }
            }
            if (write(target, targetLines, WINDOWS_CHARSET)) {
                textFilesCopied++;
                createTextFilePropertiesFile(source, target);
            }
        }
        return true;
    }

    private String replaceAliasWithDatabaseName(String line, String prefix, String suffix) {
        return line.replace(prefix + PROJECT_ALIAS + suffix, prefix + DATABASE_NAME + suffix);
    }

    private String replaceAliasWithRootPackageName(String line, String prefix, String suffix) {
        return line.replace(prefix + PROJECT_ALIAS + suffix, prefix + ROOT_PACKAGE_NAME + suffix);
    }

    private static final String SRC = "/src/";

    private void createTextFilePropertiesFile(String source, String target) {
        boolean java = StringUtils.endsWithIgnoreCase(source, ".java");
        boolean bundle = StringUtils.endsWithIgnoreCase(source, ".properties");
        File sourceFile = new File(source);
        String sourceFileName = sourceFile.getName();
        String sourceFileSimpleName = StringUtils.substringBeforeLast(sourceFileName, ".");
        String sourceFolderName = sourceFile.getParentFile().getName();
        String sourceFolderSimpleName = StringUtils.substringBeforeLast(sourceFolderName, ".");
        String sourceEntityName = getOptionalEntityName(sourceFileSimpleName, sourceFolderSimpleName);
        String properties = source.replace(projectFolderPath, velocityPlatformsTargetFolderPath) + ".properties";
        String folder = StringUtils.substringBeforeLast(properties, FS);
        String template = StringUtils.substringAfter(target, velocityFolderPath + FS).
            replace(FS, "/");
        String path = StringUtils.substringBeforeLast(StringUtils.substringAfter(source, projectFolderPath), FS).
            replace(FS, "/").
            replace(project, PROJECT_ALIAS).
            replace("eclipse.settings", ".settings");
        path = replaceAliasWithRootFolderName(path);
        String pack = null;
        if (java || bundle) {
            String s1 = StringUtils.substringAfter(path, SRC);
            if (StringUtils.contains(s1, PROJECT_ALIAS)) {
                String s2 = StringUtils.substringBefore(s1, PROJECT_ALIAS);
                String s3 = SRC + s2;
                String s4 = StringUtils.substringBefore(path, s3) + s3;
                String s5 = StringUtils.substringAfter(s1, PROJECT_ALIAS).replace("/", ".");
                path = StringUtils.removeEnd(s4, "/");
                pack = ROOT_PACKAGE_NAME + s5;
            }
        }
        path = finalisePath(path);
        String file = StringUtils.substringAfterLast(source, FS).
            replace(project, PROJECT_ALIAS).
            replace("eclipse.project", ".project");
        List<String> lines = new ArrayList<>();
        lines.add("template = " + template);
//      lines.add("template-type = velocity");
        lines.add("path = " + path);
        if (StringUtils.isNotBlank(pack)) {
            lines.add("package = " + pack);
        }
        lines.add("file = " + file);
        if (sourceFileSimpleName.equals("eclipse") || sourceFolderSimpleName.equals("eclipse") || sourceFolderSimpleName.equals("nbproject")) {
            lines.add("disabled = true");
        } else if (sourceEntityName != null) {
            lines.add("disabled-when-missing = " + sourceEntityName);
        }
        if (preservable(sourceFile)) {
            lines.add("preserve = true");
        }
        lines.add("dollar.string = $");
        lines.add("pound.string = #");
        lines.add("backslash.string = \\\\");
        FilUtils.mkdirs(folder);
        if (write(properties, lines, WINDOWS_CHARSET)) {
            propertiesFilesCreated++;
        }
    }

    private boolean preservable(File sourceFile) {
        if (preservableFileNames != null) {
            String sourceFileName = sourceFile.getName();
            if (ArrayUtils.contains(preservableFileNames, sourceFileName)) {
                return true;
            }
        }
        if (preservableFileExpressions != null) {
            String sourceFilePath = sourceFile.getPath();
            for (String string : preservableFileExpressions) {
                if (sourceFilePath.matches(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final String SOURCE_PROJECT_ALIAS = "/source/" + PROJECT_ALIAS + "/";

    private static final String SOURCE_PROJECT_ROOT_FOLDER_NAME = "/source/" + ROOT_FOLDER_NAME + "/";

    private String replaceAliasWithRootFolderName(String path) {
        String p = path + "/";
        if (StringUtils.startsWithIgnoreCase(p, SOURCE_PROJECT_ALIAS)) {
            p = SOURCE_PROJECT_ROOT_FOLDER_NAME + StringUtils.removeStartIgnoreCase(p, SOURCE_PROJECT_ALIAS);
            p = StringUtils.removeEnd(p, "/");
            return p;
        }
        return path;
    }

    private static final String POM_PROJECT_FOLDER_NAME = SOURCE_PROJECT_ROOT_FOLDER_NAME;

    private static final String EAR_PROJECT_FOLDER_NAME = SOURCE_PROJECT_ROOT_FOLDER_NAME + PROJECT_ALIAS + "-ear/";

    private static final String EJB_PROJECT_FOLDER_NAME = SOURCE_PROJECT_ROOT_FOLDER_NAME + PROJECT_ALIAS + "-ejb/";

    private static final String WAR_PROJECT_FOLDER_NAME = SOURCE_PROJECT_ROOT_FOLDER_NAME + PROJECT_ALIAS + "-web/";

    private static final String LIB_PROJECT_FOLDER_NAME = SOURCE_PROJECT_ROOT_FOLDER_NAME + PROJECT_ALIAS + "-lib/";

    private static final String RESOURCES_PROJECT_FOLDER_NAME = SOURCE_PROJECT_ROOT_FOLDER_NAME + PROJECT_ALIAS + "-resources/";

    private String finalisePath(String path) {
        String p = path + "/";
        if (StringUtils.startsWithIgnoreCase(p, SOURCE_PROJECT_ROOT_FOLDER_NAME)) {
            if (StringUtils.startsWithIgnoreCase(p, RESOURCES_PROJECT_FOLDER_NAME)) {
                p = "${resourcesProjectFolderPath}/" + StringUtils.removeStartIgnoreCase(p, RESOURCES_PROJECT_FOLDER_NAME);
                p = p.replace("/src/main/resources/META-INF/", "/${resourcesConfigurationFilesFolder}/");
                p = p.replace("/src/main/resources/", "/${resourcesBundlesFolder}/");
                p = p.replace("/src/main/java/", "/${resourcesJavaMoreFolder}/");
            } else if (StringUtils.startsWithIgnoreCase(p, LIB_PROJECT_FOLDER_NAME)) {
                p = "${libProjectFolderPath}/" + StringUtils.removeStartIgnoreCase(p, LIB_PROJECT_FOLDER_NAME);
                p = p.replace("/src/main/resources/META-INF/", "/${libConfigurationFilesFolder}/");
                p = p.replace("/src/main/java/", "/${libJavaMoreFolder}/");
            } else if (StringUtils.startsWithIgnoreCase(p, WAR_PROJECT_FOLDER_NAME)) {
                p = "${webProjectFolderPath}/" + StringUtils.removeStartIgnoreCase(p, WAR_PROJECT_FOLDER_NAME);
                p = p.replace("/src/main/resources/META-INF/", "/${warConfigurationFilesFolder}/");
                p = p.replace("/src/main/java/", "/${webJavaMoreFolder}/");
                p = p.replace("/src/main/webapp/WEB-INF/", "/${webConfigurationFilesFolder}/");
                p = p.replace("/src/main/webapp/", "/${webPagesFolder}/");
            } else if (StringUtils.startsWithIgnoreCase(p, EJB_PROJECT_FOLDER_NAME)) {
                p = "${ejbProjectFolderPath}/" + StringUtils.removeStartIgnoreCase(p, EJB_PROJECT_FOLDER_NAME);
                p = p.replace("/src/main/resources/META-INF/", "/${ejbConfigurationFilesFolder}/");
                p = p.replace("/src/main/java/", "/${ejbJavaMoreFolder}/");
            } else if (StringUtils.startsWithIgnoreCase(p, EAR_PROJECT_FOLDER_NAME)) {
                p = "${earProjectFolderPath}/" + StringUtils.removeStartIgnoreCase(p, EAR_PROJECT_FOLDER_NAME);
                p = p.replace("/src/main/application/META-INF/", "/${earConfigurationFilesFolder}/");
            } else if (StringUtils.startsWithIgnoreCase(p, POM_PROJECT_FOLDER_NAME)) {
                p = "${rootProjectFolderPath}/" + StringUtils.removeStartIgnoreCase(p, POM_PROJECT_FOLDER_NAME);
            } else {
                p = ROOT_FOLDER_SLASHED_PATH + "/" + BASE_FOLDER_NAME + p;
            }
            p = StringUtils.removeEnd(p, "/");
            return p;
        }
        return ROOT_FOLDER_SLASHED_PATH + "/" + BASE_FOLDER_NAME + path;
    }

    private String getOptionalEntityName(String sourceFileSimpleName, String sourceFolderSimpleName) {
        if (optionalEntityNames != null) {
            for (String entityName : optionalEntityNames) {
                if (check1(sourceFileSimpleName, sourceFolderSimpleName, entityName)) {
                    return entityName;
                }
            }
        }
        return null;
    }

    private boolean check1(String sourceFileSimpleName, String sourceFolderSimpleName, String entityName) {
        String underscoredName = StrUtils.getHumplessCase(entityName, '_');
        String hyphenatedName = StrUtils.getHumplessCase(entityName, '-');
        return check2(sourceFileSimpleName, sourceFolderSimpleName, underscoredName)
            || check2(sourceFileSimpleName, sourceFolderSimpleName, hyphenatedName)
            || check2(sourceFileSimpleName, sourceFolderSimpleName, entityName);
    }

    private boolean check2(String sourceFileSimpleName, String sourceFolderSimpleName, String someName) {
        String delimited = "$" + someName + "$";
        return sourceFileSimpleName.equalsIgnoreCase(someName)
            || sourceFolderSimpleName.equalsIgnoreCase(someName)
            || StringUtils.containsIgnoreCase(sourceFileSimpleName, delimited)
            || StringUtils.containsIgnoreCase(sourceFolderSimpleName, delimited);
    }

    private void printSummary() {
        logger.info(binaryFilesCopied + " binary files copied ");
        logger.info(textFilesCopied + " text files copied ");
        logger.info(binaryFilesCopied + textFilesCopied + " files copied ");
        logger.info(propertiesFilesCreated + " files created ");
        logger.info(readingWarnings + " reading warnings ");
        logger.info(readingErrors + " reading errors ");
        logger.info(writingErrors + " writing errors ");
        Map<String, Integer> map;
        for (String extension : extensionCharsetMap.keySet()) {
            map = extensionCharsetMap.get(extension);
            logger.trace(extension);
            for (String charset : map.keySet()) {
                logger.trace("\t" + StringUtils.rightPad(charset + " ", 20, '.') + " " + map.get(charset));
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="binaryFileFilter">
    // </editor-fold>
    protected IOFileFilter binaryFileFilter() {
        IOFileFilter[] ayes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "png" + E)
        };
        return FileFilterUtils.or(ayes);
    }

    // <editor-fold defaultstate="collapsed" desc="binaryDirFilter">
//  protected IOFileFilter binaryDirFilter() {
//      IOFileFilter[] noes = new IOFileFilter[]{
//          new RegexFileFilter(B + D + "git" + E),
//          new RegexFileFilter(B + D + "svn" + E),
//          new RegexFileFilter(B + D + "settings" + E),
//          new RegexFileFilter(B + "build" + E),
//          new RegexFileFilter(B + "dist" + E),
//          new RegexFileFilter(B + "private" + E),
//          new RegexFileFilter(B + "target" + E),
//          new RegexFileFilter(B + "test" + E)
//      };
//      return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
//  }
    // </editor-fold>
    protected IOFileFilter binaryDirFilter() {
        String projectRoot = getRootFolder().getName() + S + project;
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexPathFilter(B + X + S + projectRoot + S + "source" + S + "development" + E),
            new RegexPathFilter(B + X + S + projectRoot + S + "source" + S + "management" + E),
            new RegexFileFilter(B + D + "git" + E),
            new RegexFileFilter(B + D + "svn" + E),
            new RegexFileFilter(B + D + "settings" + E),
            new RegexFileFilter(B + "META-INF" + E),
            new RegexFileFilter(B + "WEB-INF" + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "junk" + E),
            new RegexFileFilter(B + "private" + E),
            new RegexFileFilter(B + "release" + E),
            new RegexFileFilter(B + "target" + E),
            new RegexFileFilter(B + "test" + E),
            new RegexFileFilter(B + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    // <editor-fold defaultstate="collapsed" desc="textFileFilter">
//  protected IOFileFilter textFileFilter() {
//      IOFileFilter[] noes = new IOFileFilter[]{
//          new RegexFileFilter(B + D + "gitignore" + E),
//          new RegexFileFilter(B + D + "classpath" + E),
//          new RegexFileFilter(B + D + "project" + E),
//          new RegexFileFilter(B + X + D + "cli" + E),
//          new RegexFileFilter(B + X + D + "gif" + E),
//          new RegexFileFilter(B + X + D + "jpg" + E),
//          new RegexFileFilter(B + X + D + "lnk" + E),
//          new RegexFileFilter(B + X + D + "log" + E),
//          new RegexFileFilter(B + X + D + "png" + E),
//          new RegexFileFilter(B + X + D + "db" + E),
//          new RegexFileFilter(B + "ant-deploy" + D + "xml" + E),
//          new RegexFileFilter(B + "build-impl" + D + "xml" + E),
//          new RegexFileFilter(B + "genfiles" + D + "properties" + E)
//      };
//      return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
//  }
    // </editor-fold>
    protected IOFileFilter textFileFilter() {
        String projectRoot = getRootFolder().getName() + S + project;
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexPathFilter(B + X + S + projectRoot + S + NSX + E),
            new RegexFileFilter(B + D + "gitignore" + E),
            new RegexFileFilter(B + D + "classpath" + E),
            new RegexFileFilter(B + D + "project" + E),
            new RegexFileFilter(B + X + D + "cli" + E),
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "lnk" + E),
            new RegexFileFilter(B + X + D + "log" + E),
            new RegexFileFilter(B + X + D + "png" + E),
            new RegexFileFilter(B + "Thumbs" + D + "db" + E),
            new RegexFileFilter(B + "ant-deploy" + D + "xml" + E),
            new RegexFileFilter(B + "build-impl" + D + "xml" + E),
            new RegexFileFilter(B + "build" + D + "xml" + E),
            new RegexFileFilter(B + "genfiles" + D + "properties" + E),
            new RegexFileFilter(B + "pom" + D + "xml" + E),
            new RegexFileFilter(B + "package-info" + D + "java" + E),
            new RegexFileFilter(B + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    // <editor-fold defaultstate="collapsed" desc="textDirFilter">
//  protected IOFileFilter textDirFilter() {
//      IOFileFilter[] noes = new IOFileFilter[]{
//          new RegexFileFilter(B + D + "git" + E),
//          new RegexFileFilter(B + D + "svn" + E),
//          new RegexFileFilter(B + D + "settings" + E),
//          new RegexFileFilter(B + "build" + E),
//          new RegexFileFilter(B + "dist" + E),
//          new RegexFileFilter(B + "private" + E),
//          new RegexFileFilter(B + "target" + E),
//          new RegexFileFilter(B + "test" + E)
//      };
//      return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
//  }
    // </editor-fold>
    protected IOFileFilter textDirFilter() {
        String projectRoot = getRootFolder().getName() + S + project;
        String application = projectRoot + S + "source" + S + project;
        String bundlesPack = application + S + project + "-resources" + S + "src" + S + "main" + S + "resources" + S + project + S + "lib";
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexPathFilter(B + X + S + projectRoot + S + "source" + S + "development" + E),
            new RegexPathFilter(B + X + S + projectRoot + S + "source" + S + "management" + E),
            new RegexPathFilter(B + X + S + bundlesPack + E),
            new RegexPathFilter(B + X + S + "src" + S + "more" + S + "java" + E),
            new RegexPathFilter(B + X + S + "src" + S + "more" + S + "resources" + E),
            //* RegexPathFilter(B + X + S + "src" + S + "main" + S + "webapp" + S + X + S + "base" + E),
            //* RegexPathFilter(B + X + S + "src" + S + "main" + S + "webapp" + S + X + S + "custom-base" + E),
            new RegexFileFilter(B + D + "git" + E),
            new RegexFileFilter(B + D + "svn" + E),
            new RegexFileFilter(B + D + "settings" + E),
            new RegexFileFilter(B + "META-INF" + E),
            new RegexFileFilter(B + "WEB-INF" + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "junk" + E),
            new RegexFileFilter(B + "private" + E),
            new RegexFileFilter(B + "release" + E),
            new RegexFileFilter(B + "target" + E),
            new RegexFileFilter(B + "test" + E),
            new RegexFileFilter(B + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    protected String[] optionalEntityNames() {
        return null;
    }

    protected String[] preservableFileNames() {
        return new String[]{
            ".classpath",
            ".project",
            "build.xml",
            "nb-configuration.xml",
            "pom.xml"
        };
    }

    protected String[] preservableFileExpressions() {
        String projectRoot = getRootFolder().getName() + S + project;
        String application = projectRoot + S + "source" + S + project;
        return new String[]{
            B + X + S + application + S + X + D + "settings" + S + X + E,
            B + X + S + application + S + X + "nbproject" + S + X + E,
            B + X + S + application + S + X + D + "css" + E,
            B + X + S + application + S + X + D + "jrtx" + E,
            B + X + S + application + S + X + S + "webapp" + S + X + S + "custom-base" + S + X + E
        };
    }

    private void copy(String source, String target) throws IOException {
        Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);
        //overwrite existing file, if exists
        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(sourcePath, targetPath, options);
    }

    private void check(SmallFile sf) {
        String cs;
        Integer count;
        Map<String, Integer> map;
        if (sf != null) {
            Charset charset = sf.getCharset();
            String name = sf.getName();
            String extension = StringUtils.defaultIfBlank(sf.getExtension().toLowerCase(), "?");
            if (charset == null) {
                readingErrors++;
                logger.error(name + " could not be read using any of the specified character sets ");
            } else if (sf.isEmpty()) {
                readingWarnings++;
                logger.warn(name + " is empty ");
            } else {
                cs = charset.toString();
                switch (cs) {
                    case "UTF-8":
                        if (extension.equalsIgnoreCase("java")) {
                        } else {
                            logger.warn(cs + "/" + extension + " " + name);
                        }
                        break;
                    case "ISO_8859_1":
                        logger.warn(cs + "/" + extension + " " + name);
                        break;
                    default:
                        break;
                }
                map = extensionCharsetMap.get(extension);
                if (map == null) {
                    map = new TreeMap<>();
                }
                count = map.get(cs);
                if (count == null) {
                    map.put(cs, 1);
                } else {
                    map.put(cs, ++count);
                }
                extensionCharsetMap.put(extension, map);
            }
        }
    }

    private boolean write(String target, List<String> lines, Charset charset) {
        try {
            Path path = Paths.get(target);
            Files.write(path, lines, charset);
            return true;
        } catch (IOException ex) {
            writingErrors++;
            logger.fatal(ex);
            logger.fatal("\t" + target + " could not be written ");
        }
        return false;
    }

}
