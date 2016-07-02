/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.velocity;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.FilUtils;
import adalid.util.Utility;
import adalid.util.io.RegexPathFilter;
import adalid.util.io.SmallFile;
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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class MavenArchetypeBuilder extends Utility {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    protected static final String B = "^";

    protected static final String E = "$";

    protected static final String X = ".*";

    protected static final String S = RegexPathFilter.SEPARATOR;

    protected static final String D = "\\.";

    protected static final String NOSX = "[^" + S + "]*";

    protected static final String BONL = "(?!"; // begin of negative lookahead

    protected static final String EONL = ")"; // end of negative lookahead

    protected static final String FS = System.getProperties().getProperty("file.separator");

    protected static final Logger logger = Logger.getLogger(MavenArchetypeBuilder.class);

    protected static final Charset WINDOWS_CHARSET = Charset.forName("windows-1252");
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private String group;

    private String artifact;

    private String project;

    private String PROJECT;

    private String packageName;

    private String PACKAGENAME;

    private String rootFolderPath;

    private String projectFolderPath;

    private String velocityFolderPath;

    private String velocityArchetypeTargetFolderPath;

    private String velocityTemplatesTargetFolderPath;

    private File rootFolder;

    private File projectFolder;

    private File velocityFolder;

    private File velocityArchetypeTargetFolder;

    private File velocityTemplatesTargetFolder;

    private int binaryFilesCopied, textFilesCopied, readingWarnings, readingErrors, writingErrors;

    private final Set<String> classes = new TreeSet<>();

    private final Set<String> sources = new TreeSet<>();

    private final Set<String> resources = new TreeSet<>();

    private final Map<String, Map<String, Integer>> extensionCharsetMap = new TreeMap<>();
    // </editor-fold>

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group.toLowerCase();
    }

    /**
     * @return the artifact
     */
    public String getArtifact() {
        return artifact;
    }

    /**
     * @param artifact the artifact to set
     */
    public void setArtifact(String artifact) {
        this.artifact = artifact.toLowerCase();
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
        this.project = project.toLowerCase();
    }

    /**
     * @return the package
     */
    public String getPackage() {
        return packageName;
    }

    /**
     * @param packageName the package to set
     */
    public void setPackage(String packageName) {
        this.packageName = packageName.toLowerCase();
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

    public void build() {
        init();
        printParameters();
        try {
            cleanVelocityDirectories();
            copyBinaryFiles();
            copyTextFiles();
            writeArchetypeDescriptor();
        } catch (IOException ex) {
            logger.fatal(ex);
        }
        printSummary();
    }

    private void init() {
        if (StringUtils.isBlank(group)) {
            throw new RuntimeException("group is missing");
        }
        if (StringUtils.isBlank(artifact)) {
            throw new RuntimeException("artifact is missing");
        }
        if (StringUtils.isBlank(project)) {
            throw new RuntimeException("project is missing");
        }
        if (StringUtils.isBlank(packageName)) {
            throw new RuntimeException("package is missing");
        }
        PROJECT = project.toUpperCase();
        PACKAGENAME = packageName.toUpperCase();
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
            projectFolderPath = rootFolderPath + FS + project + FS + "source" + FS + artifact;
        }
        projectFolder = new File(projectFolderPath);
        if (!projectFolder.isDirectory()) {
            throw new RuntimeException("invalid project folder: " + projectFolder);
        }
        if (StringUtils.isBlank(velocityFolderPath)) {
            velocityFolderPath = rootFolderPath + FS + project + FS + "source" + FS + artifact + "-archetype";
        }
        velocityFolder = new File(velocityFolderPath);
        if (!velocityFolder.isDirectory()) {
            velocityFolder.mkdirs();
            if (!velocityFolder.isDirectory()) {
                throw new RuntimeException("invalid velocity folder: " + velocityFolder);
            }
        }
        velocityArchetypeTargetFolderPath = velocityFolderPath + FS + "src" + FS + "main" + FS + "resources" + FS + "META-INF" + FS + "maven";
        velocityArchetypeTargetFolder = new File(velocityArchetypeTargetFolderPath);
        velocityTemplatesTargetFolderPath = velocityFolderPath + FS + "src" + FS + "main" + FS + "resources" + FS + "archetype-resources";
        velocityTemplatesTargetFolder = new File(velocityTemplatesTargetFolderPath);
        binaryFilesCopied = 0;
        textFilesCopied = 0;
        readingWarnings = 0;
        readingErrors = 0;
        writingErrors = 0;
        sources.clear();
        resources.clear();
        extensionCharsetMap.clear();
    }

    private void printParameters() {
        logger.info("group=" + group);
        logger.info("artifact=" + artifact);
        logger.info("project=" + project);
        logger.info("package=" + packageName);
        logger.info("rootFolderPath=" + rootFolderPath);
        logger.info("projectFolderPath=" + projectFolderPath);
        logger.info("velocityFolderPath=" + velocityFolderPath);
    }

    private void cleanVelocityDirectories() throws IOException {
        FileUtils.deleteDirectory(velocityArchetypeTargetFolder);
        velocityArchetypeTargetFolder.mkdirs();
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
            } catch (IOException ex) {
                writingErrors++;
                logger.fatal(ex);
                logger.fatal("\t" + source + " could not be copied ");
            }
        }
        return true;
    }

    private boolean copyTextFiles() {
        Collection<File> files = FileUtils.listFiles(projectFolder, textFileFilter(), textDirFilter());
//      ColUtils.sort(files);
        String source, target, targetParent, template, clazz;
        boolean java;
        Charset cs;
        SmallFile smallSource;
        for (File file : files) {
            source = file.getPath();
            java = StringUtils.endsWithIgnoreCase(source, ".java");
            target = source.replace(projectFolderPath, velocityTemplatesTargetFolderPath);
            template = StringUtils.removeStart(target, velocityTemplatesTargetFolderPath + FS).replace('\\', '/');
            clazz = StringUtils.removeStartIgnoreCase(StringUtils.removeEndIgnoreCase(template, ".java"), "src/main/java/").replace('/', '.');
            if (java) {
                classes.add(clazz);
                sources.add(template);
            } else {
                resources.add(template);
            }
        }
        String alias = alias(false);
        String ALIAS = alias(true);
        String packageX1 = packageName + ".";
        String packageX2 = packageName + ";";
        List<String> sourceLines;
        List<String> targetLines = new ArrayList<>();
        for (File file : files) {
            source = file.getPath();
            java = StringUtils.endsWithIgnoreCase(source, ".java");
            target = source.replace(projectFolderPath, velocityTemplatesTargetFolderPath);
            targetParent = StringUtils.substringBeforeLast(target, FS);
            targetLines.clear();
            FilUtils.mkdirs(targetParent);
            smallSource = new SmallFile(source);
            sourceLines = smallSource.read();
            check(smallSource);
            if (smallSource.isNotEmpty()) {
                for (String line : sourceLines) {
                    if (StringUtils.isNotBlank(line)) {
                        line = line.replace(group, "${groupId}");
                        line = line.replace(artifact, "${artifactId}");
                        line = line.replace(project + "ap", alias + "ap");
                        line = line.replace(PROJECT + "AP", ALIAS + "AP");
                        line = line.replace("package " + packageX1, "package ${package}." + packageX1);
                        line = line.replace("package " + packageX2, "package ${package}." + packageX2);
                        for (String name : classes) {
                            if (name.startsWith(packageX1)) {
                                line = line.replace(name, "${package}." + name);
                            }
                        }
                    }
                    targetLines.add(line);
                }
            }
            cs = java ? StandardCharsets.UTF_8 : WINDOWS_CHARSET;
            if (write(target, targetLines, cs)) {
                textFilesCopied++;
            }
        }
        return true;
    }

    private String alias(boolean shift) {
        final String sep = "[\\_\\-\\.]+";
        final String invalid = "[^a-z0-9]";
        String quoted = Pattern.quote(packageName);
        String prefix = "^" + quoted + sep;
        String suffix = sep + quoted + "$";
        String toUpperCase = shift ? ".toUpperCase()" : "";
        return "${artifactId.toLowerCase()" + remove(prefix) + remove(suffix) + remove(invalid) + toUpperCase + "}";
    }

    private String remove(String regex) {
        return ".replaceAll('" + regex + "', '')";
    }

    private void writeArchetypeDescriptor() {
        final String LF = "\n";
        final String HT = StringUtils.repeat(" ", 4);
        final String L1 = "<archetype" + LF + HT
            + "xmlns=\"http://maven.apache.org/plugins/maven-archetype-plugin/archetype/1.0.0\"" + LF + HT
            + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + LF + HT
            + "xsi:schemaLocation=\"http://maven.apache.org/plugins/maven-archetype-plugin/archetype/1.0.0" + LF + HT
            + "http://maven.apache.org/xsd/archetype-1.0.0.xsd\">";

        String target = velocityArchetypeTargetFolderPath + FS + "archetype.xml";
        List<String> targetLines = new ArrayList<>();
        targetLines.add(L1);
        targetLines.add(HT + "<id>adalid-" + artifact + "-archetype</id>");
        targetLines.add(HT + "<sources>");
        for (String path : sources) {
            if (path.startsWith("src/main/java/")) {
                targetLines.add(HT + HT + "<source>" + path + "</source>");
            }
        }
        targetLines.add(HT + "</sources>");
        targetLines.add(HT + "<resources>");
        for (String path : resources) {
            if (path.startsWith("src/main/resources/")) {
                targetLines.add(HT + HT + "<resource>" + path + "</resource>");
            }
        }
        targetLines.add(HT + "</resources>");
        targetLines.add("</archetype>");
        write(target, targetLines, StandardCharsets.UTF_8);
    }

    private void printSummary() {
        for (String name : classes) {
            logger.info(name);
        }
        for (String name : sources) {
            logger.info(name);
        }
        for (String name : resources) {
            logger.info(name);
        }
        Map<String, Integer> map;
        for (String extension : extensionCharsetMap.keySet()) {
            map = extensionCharsetMap.get(extension);
            logger.trace(extension);
            for (String charset : map.keySet()) {
                logger.trace("\t" + StringUtils.rightPad(charset + " ", 20, '.') + " " + map.get(charset));
            }
        }
        logger.info(binaryFilesCopied + " binary files copied ");
        logger.info(textFilesCopied + " text files copied ");
        logger.info(binaryFilesCopied + textFilesCopied + " files copied ");
        logger.info(readingWarnings + " reading warnings ");
        logger.info(readingErrors + " reading errors ");
        logger.info(writingErrors + " writing errors ");
    }

    protected IOFileFilter binaryFileFilter() {
        IOFileFilter[] ayes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "png" + E)
        };
        return FileFilterUtils.or(ayes);
    }

    protected IOFileFilter binaryDirFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + D + "settings" + E),
            new RegexFileFilter(B + "target" + E),
            new RegexFileFilter(B + "test" + E),
            new RegexFileFilter(B + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    protected IOFileFilter textFileFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexPathFilter(B + X + S + "meta" + S + "util" + S + "I18N" + X + E),
            new RegexPathFilter(B + X + S + "meta" + S + "util" + S + "Meta" + X + E),
            new RegexPathFilter(B + X + S + "meta" + S + "util" + S + "Velocity" + X + E),
            new RegexFileFilter(B + D + "gitignore" + E),
            new RegexFileFilter(B + D + "classpath" + E),
            new RegexFileFilter(B + D + "project" + E),
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "lnk" + E),
            new RegexFileFilter(B + X + D + "log" + E),
            new RegexFileFilter(B + X + D + "png" + E),
            new RegexFileFilter(B + "Thumbs" + D + "db" + E),
            new RegexFileFilter(B + "nb-configuration" + D + "xml" + E),
            new RegexFileFilter(B + "nbactions" + D + "xml" + E),
            new RegexFileFilter(B + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
    }

    protected IOFileFilter textDirFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexPathFilter(B + X + S + "source" + S + "meta" + S + "resources" + E),
            new RegexPathFilter(B + X + S + "meta" + S + "proyecto" + S + "base" + E),
            new RegexFileFilter(B + D + "settings" + E),
            new RegexFileFilter(B + "target" + E),
            new RegexFileFilter(B + "test" + E),
            new RegexFileFilter(B + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
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
