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
import adalid.util.io.RegexPathFilter;
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
public class BasicBuilder {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final Logger logger = Logger.getLogger(BasicBuilder.class);

    private static final String FS = System.getProperties().getProperty("file.separator");

    private static final String VM = ".vm";

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";

    private static final String S = RegexPathFilter.SEPARATOR;

    private static final String D = "\\.";

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
    private String project;

    private String proj;

    private String PROJECT;

    private String rootFolderPath;

    private String projectFolderPath;

    private String velocityFolderPath;

    private String velocityPlatformsTargetFolderPath;

    private String velocityTemplatesTargetFolderPath;

    private String[] preservableFiles = new String[]{"javascript3.js"};

    private boolean excludeOracle;

    private Set<String> optionalEntityNames;

    private File rootFolder;

    private File projectFolder;

    private File velocityFolder;

    private File velocityPlatformsTargetFolder;

    private File velocityTemplatesTargetFolder;

    private int binaryFilesCopied, textFilesCopied, propertiesFilesCreated, readingWarnings, readingErrors, writingErrors;
    // </editor-fold>

    public BasicBuilder() {
    }

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
     * @return the rootFolderPath
     */
    public String getRootFolderPath() {
        return rootFolderPath;
    }

    /**
     * @param rootFolderPath the rootFolderPath to set
     */
    public void setRootFolderPath(String rootFolderPath) {
        this.rootFolderPath = rootFolderPath;
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
        this.projectFolderPath = projectFolderPath;
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
        this.velocityFolderPath = velocityFolderPath;
    }

    /**
     * @return the preservableFiles
     */
    public String[] getPreservableFiles() {
        return preservableFiles;
    }

    /**
     * @param preservableFiles the preservableFiles to set
     */
    public void setPreservableFiles(String[] preservableFiles) {
        this.preservableFiles = preservableFiles;
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
        if (StringUtils.isBlank(project)) {
            throw new RuntimeException("project is missing");
        }
        proj = project.substring(0, 4);
        PROJECT = project.toUpperCase();
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
        velocityPlatformsTargetFolderPath = velocityFolderPath + FS + "platforms" + FS + proj + FS + "base";
        velocityTemplatesTargetFolderPath = velocityFolderPath + FS + "templates" + FS + proj + FS + "base";
        velocityPlatformsTargetFolder = new File(velocityPlatformsTargetFolderPath);
        velocityTemplatesTargetFolder = new File(velocityTemplatesTargetFolderPath);
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
            target = source.replace(projectFolderPath, velocityTemplatesTargetFolderPath) + VM;
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
            new RegexFileFilter(B + "genfiles" + D + "properties" + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    private IOFileFilter textDirFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + ".git" + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "junk" + E),
            new RegexFileFilter(B + "private" + E),
            new RegexFileFilter(B + "target" + E),
            new RegexFileFilter(B + "test" + E)
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
