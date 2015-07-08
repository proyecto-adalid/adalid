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
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
public class BaseBuilder {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final Logger logger = Logger.getLogger(BaseBuilder.class);

    private static final String project = "jee1ap101";

    private static final String PROJECT = "JEE1AP101";

    private static final String FS = System.getProperties().getProperty("file.separator");

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";

    private static final String S = RegexPathFilter.SEPARATOR;

    private static final String D = "\\.";

    private static final String application = S + project + S + "source" + S + project + S;

    private static final String management = S + project + S + "source" + S + "management" + S;

    private static final String resources = S + project + S + "source" + S + "management" + S + "resources" + S;

    private static final Charset[] STANDARD_CHARSETS = new Charset[]{
        StandardCharsets.ISO_8859_1,
        StandardCharsets.UTF_8,
        StandardCharsets.UTF_16,
        StandardCharsets.UTF_16BE,
        StandardCharsets.UTF_16LE,
        StandardCharsets.US_ASCII
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final File rootFolder;

    private final File projectFolder;

    private final File velocityFolder;

    private final File velocityPlatformsTargetFolder;

    private final File velocityTemplatesTargetFolder;

    private final String rootFolderPath;

    private final String projectFolderPath;

    private final String velocityFolderPath;

    private final String velocityPlatformsTargetFolderPath;

    private final String velocityTemplatesTargetFolderPath;

    private final String[] preservableFiles;

    private int binaryFilesCopied, textFilesCopied, propertiesFilesCreated, readingWarnings, readingErrors, writingErrors;

    private boolean excludeOracle;

    private Set<String> optionalEntityNames;
    // </editor-fold>

    public BaseBuilder() {
        rootFolder = PropertiesHandler.getRootFolder();
        if (rootFolder != null && rootFolder.isDirectory()) {
            rootFolderPath = rootFolder.getPath();
            projectFolderPath = rootFolderPath + FS + project;
            velocityFolderPath = rootFolderPath + FS + "adalid" + FS + "source" + FS + "development" + FS + "resources" + FS + "velocity";
            velocityPlatformsTargetFolderPath = velocityFolderPath + FS + "platforms" + FS + "jee1" + FS + "base";
            velocityTemplatesTargetFolderPath = velocityFolderPath + FS + "templates" + FS + "jee1" + FS + "base";
            preservableFiles = new String[]{"javascript3.js"};
            projectFolder = new File(projectFolderPath);
            velocityFolder = new File(velocityFolderPath);
            if (velocityFolder.isDirectory()) {
                velocityPlatformsTargetFolder = new File(velocityPlatformsTargetFolderPath);
                velocityTemplatesTargetFolder = new File(velocityTemplatesTargetFolderPath);
            } else {
                throw new RuntimeException("invalid velocity folder: " + velocityFolder);
            }
        } else {
            throw new RuntimeException("invalid root folder: " + rootFolder);
        }
    }

    public boolean isExcludeOracle() {
        return excludeOracle;
    }

    public void setExcludeOracle(boolean excludeOracle) {
        this.excludeOracle = excludeOracle;
    }

    public Set<String> getOptionalEntityNames() {
        return optionalEntityNames;
    }

    public void setOptionalEntityNames(Set<String> optionalEntityNames) {
        this.optionalEntityNames = optionalEntityNames;
    }

    public void build() {
        init();
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
        binaryFilesCopied = 0;
        textFilesCopied = 0;
        propertiesFilesCreated = 0;
        readingWarnings = 0;
        readingErrors = 0;
        writingErrors = 0;
    }

    private void cleanVelocityDirectories() throws IOException {
        FileUtils.deleteDirectory(velocityPlatformsTargetFolder);
        velocityPlatformsTargetFolder.mkdirs();
        FileUtils.deleteDirectory(velocityTemplatesTargetFolder);
        velocityTemplatesTargetFolder.mkdirs();
    }

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
            replace(project, "${project.alias}");
        String file = StringUtils.substringAfterLast(source, FS).
            replace(project, "${project.alias}");
        lines.add("template = " + template);
        lines.add("template-type = document");
        lines.add("path = ${rootFolderSlashedPath}/${project.alias}" + path);
        lines.add("file = " + file);
        lines.add("preserve = true");
        FilUtils.mkdirs(folder);
        if (write(properties, lines, StandardCharsets.ISO_8859_1)) {
            propertiesFilesCreated++;
        }
    }

    private boolean copyTextFiles() {
        Collection<File> files = FileUtils.listFiles(projectFolder, textFileFilter(), textDirFilter());
//      ColUtils.sort(files);
        String source, target, targetParent;
        SmallFile smallSource;
        List<String> sourceLines;
        List<String> targetLines = new ArrayList<>();
        for (File file : files) {
            source = file.getPath();
            target = source.replace(projectFolderPath, velocityTemplatesTargetFolderPath);
            targetParent = StringUtils.substringBeforeLast(target, FS);
            targetLines.clear();
            FilUtils.mkdirs(targetParent);
            smallSource = new SmallFile(source);
            sourceLines = smallSource.read();
            if (smallSource.isNotEmpty()) {
                for (String line : sourceLines) {
                    if (StringUtils.isNotBlank(line)) {
                        line = line.replace("$", "${dollar}");
                        line = line.replace("#", "${pound}");
                        line = line.replace("\\", "${backslash}");
                        line = line.replace(project, "${project.alias}");
                        line = line.replace(PROJECT, "${project.alias.toUpperCase()}");
                    }
                    targetLines.add(line);
                }
            }
            if (write(target, targetLines, smallSource.charset)) {
                textFilesCopied++;
                createTextFilePropertiesFile(source, target);
            }
        }
        return true;
    }

    private void createTextFilePropertiesFile(String source, String target) {
        File sourceFile = new File(source);
        String sourceFileName = StringUtils.substringBeforeLast(sourceFile.getName(), ".");
        String sourceFilextName = sourceFile.getName();
        String sourceFolderName = StringUtils.substringBeforeLast(sourceFile.getParentFile().getName(), ".");
        String sourceEntityName = getOptionalEntityName(sourceFileName, sourceFolderName);
        String properties = source.replace(projectFolderPath, velocityPlatformsTargetFolderPath) + ".properties";
        String folder = StringUtils.substringBeforeLast(properties, FS);
        String template = StringUtils.substringAfter(target, velocityFolderPath + FS).
            replace(FS, "/");
        String path = StringUtils.substringBeforeLast(StringUtils.substringAfter(source, projectFolderPath), FS).
            replace(FS, "/").
            replace(project, "${project.alias}").
            replace("eclipse.settings", ".settings");
        String file = StringUtils.substringAfterLast(source, FS).
            replace(project, "${project.alias}").
            replace("eclipse.project", ".project");
        List<String> lines = new ArrayList<>();
        lines.add("template = " + template);
//      lines.add("template-type = velocity");
        lines.add("path = ${rootFolderSlashedPath}/${project.alias}" + path);
        lines.add("file = " + file);
        if (sourceFileName.equals("eclipse") || sourceFolderName.equals("eclipse") || sourceFolderName.equals("nbproject")) {
            lines.add("disabled = true");
        } else if (sourceEntityName != null) {
            lines.add("disabled-when-missing = " + sourceEntityName);
        }
        if (source.endsWith(".css") || source.endsWith(".jrtx")) {
            lines.add("preserve = true");
        } else if (ArrayUtils.contains(preservableFiles, sourceFilextName)) {
            lines.add("preserve = true");
        }
        lines.add("dollar.string = $");
        lines.add("pound.string = #");
        lines.add("backslash.string = \\\\");
        FilUtils.mkdirs(folder);
        if (write(properties, lines, StandardCharsets.ISO_8859_1)) {
            propertiesFilesCreated++;
        }
    }

    private String getOptionalEntityName(String sourceFileName, String sourceFolderName) {
        for (String entityName : optionalEntityNames) {
            if (check1(sourceFileName, sourceFolderName, entityName)) {
                return entityName;
            }
        }
        return null;
    }

    private boolean check1(String sourceFileName, String sourceFolderName, String entityName) {
        String underscoredName = StrUtils.getHumplessCase(entityName, '_');
        String hyphenatedName = StrUtils.getHumplessCase(entityName, '-');
        return check2(sourceFileName, sourceFolderName, underscoredName)
            || check2(sourceFileName, sourceFolderName, hyphenatedName)
            || check2(sourceFileName, sourceFolderName, entityName);
    }

    private boolean check2(String sourceFileName, String sourceFolderName, String someName) {
        String delimited = "$" + someName + "$";
        return sourceFileName.equalsIgnoreCase(someName)
            || sourceFolderName.equalsIgnoreCase(someName)
            || StringUtils.containsIgnoreCase(sourceFileName, delimited)
            || StringUtils.containsIgnoreCase(sourceFolderName, delimited);
    }

    private void printSummary() {
        logger.info(binaryFilesCopied + " binary files copied ");
        logger.info(textFilesCopied + " text files copied ");
        logger.info(binaryFilesCopied + textFilesCopied + " files copied ");
        logger.info(propertiesFilesCreated + " files created ");
        logger.info(readingWarnings + " reading warnings ");
        logger.info(readingErrors + " reading errors ");
        logger.info(writingErrors + " writing errors ");
    }

    // <editor-fold defaultstate="collapsed" desc="filters">
    private IOFileFilter binaryFileFilter() {
        IOFileFilter[] ayes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "png" + E)
        };
        return FileFilterUtils.or(ayes);
    }

    private IOFileFilter binaryDirFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "private" + E),
            new RegexFileFilter(B + "release" + E),
            new RegexFileFilter(B + "test" + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    private IOFileFilter textFileFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexPathFilter(B + X + application + project + "-ejb" + S + "nbproject" + S + "project" + D + "properties" + E),
            new RegexPathFilter(B + X + application + project + "-ejb" + S + "src" + S + "conf" + S + "persistence" + D + "xml" + E),
            new RegexPathFilter(B + X + application + project + "-ejb" + S + "src" + S + "conf" + S + "sun-ejb-jar" + D + "xml" + E),
            new RegexPathFilter(B + X + application + project + "-lib" + S + "nbproject" + S + "project" + D + "properties" + E),
            new RegexPathFilter(B + X + application + project + "-resources" + S + "nbproject" + S + "project" + D + "properties" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "nbproject" + S + "project" + D + "properties" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "WEB-INF" + S + "faces-config" + D + "xml" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "WEB-INF" + S + "glassfish-web" + D + "xml" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "WEB-INF" + S + "web" + D + "xml" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "ayuda" + D + "html" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "error" + D + "jsp" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "index" + D + "html" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "index" + D + "jsp" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "login" + D + "jsp" + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "redirect" + D + "html" + E),
            new RegexPathFilter(B + X + application + "src" + S + "conf" + S + "application" + D + "xml" + E),
            new RegexPathFilter(B + X + application + "src" + S + "conf" + S + "glassfish-application" + D + "xml" + E),
            new RegexPathFilter(B + X + management + "[\\w\\-\\_]*" + D + "bat" + E),
            new RegexPathFilter(B + X + management + "[\\w\\-\\_]*" + D + "password" + E),
            new RegexPathFilter(B + X + management + "[\\w\\-\\_]*" + D + "sh" + E),
            new RegexPathFilter(B + X + management + "[\\w\\-\\_]*" + D + "txt" + E),
            new RegexPathFilter(B + X + resources + "reporting" + S + X + D + "jasper" + E),
            new RegexPathFilter(B + X + resources + "reporting" + S + X + D + "jrxml" + E),
            new RegexPathFilter(B + X + resources + "reporting" + S + X + D + "pdf" + E),
            new RegexPathFilter(B + X + resources + "scripts" + S + "windows" + S + "postgresql" + S + "dump-table-tokens" + D + "txt" + E),
            new RegexPathFilter(B + X + management + "setup" + S + "scripts" + S + "linux" + S + "ln-s" + D + "sh" + E),
            new RegexPathFilter(B + X + management + "setup" + S + "scripts" + S + "linux" + S + "variables-conf" + D + "sh" + E),
            new RegexPathFilter(B + X + management + "setup" + S + "scripts" + S + "linux" + S + "variables-home" + D + "sh" + E),
            new RegexPathFilter(B + X + management + "setup" + S + "scripts" + S + "windows" + S + "variables-conf" + D + "bat" + E),
            new RegexPathFilter(B + X + management + "setup" + S + "scripts" + S + "windows" + S + "variables-home" + D + "bat" + E),
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "lnk" + E),
            new RegexFileFilter(B + X + D + "log" + E),
            new RegexFileFilter(B + X + D + "png" + E),
            new RegexFileFilter(B + "Thumbs" + D + "db" + E),
            new RegexFileFilter(B + "ant-deploy" + D + "xml" + E),
            new RegexFileFilter(B + "build-impl" + D + "xml" + E),
            new RegexFileFilter(B + "build" + D + "xml" + E),
            new RegexFileFilter(B + "delete-generated-files" + D + X + E),
            new RegexFileFilter(B + "eclipse" + D + "classpath" + E),
            //  RegexFileFilter(B + "eclipse" + D + "project" + E),
            new RegexFileFilter(B + "genfiles" + D + "properties" + E),
            new RegexFileFilter(B + "org" + D + "eclipse" + D + "wst" + D + "common" + D + "project" + D + "facet" + D + "core" + D + "xml" + E),
            excludeOracle ? new RegexFileFilter(B + "variables-oracle" + D + X + E) : new RegexFileFilter(B + E)
        };
//      return FileFilterUtils.and(FileFilterUtils.or(ayes), FileFilterUtils.notFileFilter(FileFilterUtils.or(noes)));
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    private IOFileFilter textDirFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexPathFilter(B + X + application + "EarContent" + X + E),
            new RegexPathFilter(B + X + application + "eclipse.settings" + X + E),
            new RegexPathFilter(B + X + application + project + "-ejb" + S + "ejbModule" + X + E),
            new RegexPathFilter(B + X + application + project + "-ejb" + S + "setup" + X + E),
            new RegexPathFilter(B + X + application + project + "-ejb" + S + "src" + S + "java" + S + "crop" + X + E),
            new RegexPathFilter(B + X + application + project + "-lib" + S + "src" + S + "java" + S + "crop" + X + E),
            new RegexPathFilter(B + X + application + project + "-resources" + S + "src" + S + "crop" + X + E),
            //  RegexPathFilter(B + X + application + project + "-resources" + S + "src" + S + "i18n" + S + "en" + S + project + X + E),
            new RegexPathFilter(B + X + application + project + "-resources" + S + "src" + S + "i18n" + S + "es" + S + project + X + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "src" + S + "java" + S + "crop" + X + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "META-INF" + X + E),
            new RegexPathFilter(B + X + application + project + "-war" + S + "web" + S + "crop" + X + E),
            new RegexPathFilter(B + X + application + "nbproject" + X + E),
            new RegexPathFilter(B + X + management + "backup" + X + E),
            new RegexPathFilter(B + X + management + "dist" + X + E),
            new RegexPathFilter(B + X + management + "logs" + X + E),
            new RegexPathFilter(B + X + resources + "config" + X + E),
            new RegexPathFilter(B + X + resources + "database" + S + "postgresql" + S + "data" + X + E),
            new RegexPathFilter(B + X + resources + "database" + S + "postgresql" + S + "data-migration" + X + E),
            new RegexPathFilter(B + X + resources + "database" + S + "postgresql" + S + "views" + S + "custom-made" + X + E),
            new RegexPathFilter(B + X + resources + "database" + S + "postgresql" + S + "views" + S + "data-provider" + X + E),
            new RegexPathFilter(B + X + resources + "database" + S + "postgresql" + S + "views" + S + "jasper-report" + X + E),
            new RegexPathFilter(B + X + resources + "database" + S + "postgresql" + S + "views" + S + "system" + X + E),
            //  RegexPathFilter(B + X + resources + "reporting" + X + E),
            new RegexPathFilter(B + X + management + "sql" + X + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "private" + E),
            new RegexFileFilter(B + "release" + E),
            new RegexFileFilter(B + "test" + E),
            excludeOracle ? new RegexFileFilter(B + "oracle" + E) : new RegexFileFilter(B + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }
    // </editor-fold>

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

    private class SmallFile {

        private SmallFile(String path) {
            this.name = path;
            this.path = Paths.get(path);
        }

        private final String name;

        private final Path path;

        private Charset charset;

        private List<String> lines;

        private List<String> read() {
            charset = null;
            for (Charset cs : STANDARD_CHARSETS) {
                try {
                    lines = Files.readAllLines(path, cs);
                    if (isEmpty()) {
                        readingWarnings++;
                        logger.warn(name + " is empty ");
                    }
                    charset = cs;
                    return lines;
                } catch (IOException ex) {
                    readingWarnings++;
                    logger.warn(ex);
                    logger.warn("\t" + name + " could not be read using " + cs);
                }
            }
            readingErrors++;
            logger.error(name + " could not be read using a standard charset ");
            return null;
        }

        private boolean isEmpty() {
            return lines == null || lines.isEmpty();
        }

        private boolean isNotEmpty() {
            return !isEmpty();
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
