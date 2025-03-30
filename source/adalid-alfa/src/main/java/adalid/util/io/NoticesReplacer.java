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
package adalid.util.io;

import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class NoticesReplacer extends Utility {

    private static final Logger logger = Logger.getLogger(NoticesReplacer.class);

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";

    private static final String D = "\\.";

    private static final String PACKAGE_REGEX = "^package [\\w\\.]*;$";

    public static void main(String[] args) {
        replace("adalid\\source\\adalid-zymogen");
    }

    public static boolean replace(String path) {
        return replace(path, false);
    }

    public static boolean replace(String path, boolean detail) {
        logger.info("replace" + StrUtils.enclose(path));
        NoticesReplacer replacer = new NoticesReplacer(path, detail);
        return replacer.replace();
    }

    public static boolean replace(String path, List<String> details) {
        logger.info("replace" + StrUtils.enclose(path));
        NoticesReplacer replacer = new NoticesReplacer(path, details);
        return replacer.replace();
    }

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final File rootFolder;

    private final Path rootFolderPath;

    private final File noticesFile;

    private final SmallFile noticesSmallFile;

    private final List<String> noticesSmallFileLines;

    private final File resourcesFolder;

    private final Path resourcesFolderPath;

    private final File baseFolder;

    private final Path baseFolderPath;

    private final Map<Path, SmallFile> files;

    private final Map<String, Integer> fileTypes;

    private boolean detailAll;

    private List<String> detailPatterns;

    private int readingWarnings, readingErrors;

    private int javaFilesCopied, javaFilesSkipped, writingErrors;
    // </editor-fold>

    private NoticesReplacer(String path) {
        rootFolder = PropertiesHandler.getRootFolder();
        if (rootFolder == null) {
            throw new RuntimeException("root folder is missing or invalid");
        }
        rootFolderPath = Paths.get(rootFolder.getPath());
        logger.info("root-folder=" + rootFolderPath);
        noticesFile = new File(rootFolder.getAbsolutePath(), "adalid\\source\\documents\\license-notices.txt");
        if (noticesFile.isFile()) {
            logger.info("notices-file=" + noticesFile);
            noticesSmallFile = new SmallFile(noticesFile.getPath());
            noticesSmallFileLines = noticesSmallFile.read();
        } else {
            throw new IllegalArgumentException(noticesFile.getPath() + " is not a file");
        }
        if (path == null) {
            throw new IllegalArgumentException("null folder path");
        } else {
            File file = new File(path);
            if (file.isAbsolute()) {
                resourcesFolder = file;
            } else {
                resourcesFolder = new File(rootFolder.getAbsolutePath(), path);
            }
        }
        resourcesFolderPath = Paths.get(resourcesFolder.getPath());
        logger.info("resources-folder=" + resourcesFolderPath);
        if (resourcesFolder.isDirectory()) {
            if (resourcesFolder.isHidden()) {
                throw new IllegalArgumentException(resourcesFolderPath + " is a hidden directory");
            }
        } else {
            throw new IllegalArgumentException(resourcesFolderPath + " is not a directory");
        }
        baseFolder = resourcesFolder.getParentFile();
        baseFolderPath = Paths.get(baseFolder.getPath());
        logger.info("base-folder=" + baseFolderPath);
        files = new TreeMap<>();
        fileTypes = new TreeMap<>();

    }

    private NoticesReplacer(String path, boolean detail) {
        this(path);
        this.detailAll = detail;
    }

    private NoticesReplacer(String path, List<String> details) {
        this(path);
        this.detailAll = false;
        this.detailPatterns = details;
    }

    private boolean replace() {
        logger.info("replace" + StrUtils.enclose(resourcesFolderPath.toString()));
        init();
        readFiles();
        printSummary();
        updateProjectBuilderDictionary(NoticesReplacer.class);
        return readingErrors == 0;
    }

    private void init() {
        readingWarnings = 0;
        readingErrors = 0;
        javaFilesCopied = 0;
        javaFilesSkipped = 0;
        writingErrors = 0;
    }

    private boolean readFiles() {
        Collection<File> list = FileUtils.listFiles(resourcesFolder, fileFilter(), dirFilter());
        SmallFile sf;
        Charset charset;
        String name;
        String extension;
        for (File file : list) {
            sf = new SmallFile(file.getPath());
            sf.read();
            charset = sf.getCharset();
            name = sf.getName();
            extension = StringUtils.defaultIfBlank(sf.getExtension().toLowerCase(), "?");
            if (charset == null) {
                readingErrors++;
                logger.error(name + " could not be read using any of the specified character sets ");
            } else if (sf.isEmpty()) {
                readingWarnings++;
                logger.warn(name + " is empty ");
            } else {
                files.put(sf.getPath(), sf);
                updateFileTypes(charset + "");
                updateFileTypes(charset + " / " + extension);
                rewrite(sf);
            }
        }
        return true;
    }

    private boolean rewrite(SmallFile smallSource) {
        String source = smallSource.getName();
        String shorty = StringUtils.removeStartIgnoreCase(source, resourcesFolderPath.toString());
        String[] thirdPartyJava = {"package-info.java", "EnglishNoun.java", "HexUtils.java", "ImageInfo.java", "lib.core.opencsv"};
        for (String regex : thirdPartyJava) {
            if (source.matches("^.*" + regex + ".*$")) {
                logger.warn("skipping " + shorty);
                javaFilesSkipped++;
                return false;
            }
        }
        List<String> sourceLines = smallSource.read();
        List<String> targetLines = new ArrayList<>();
        boolean head = true;
        boolean skip = true;
        if (smallSource.isNotEmpty()) {
            for (String line : sourceLines) {
                if (StringUtils.isNotBlank(line)) {
                    if (line.matches(PACKAGE_REGEX)) {
                        skip = false;
                    }
                }
                if (skip) {
                    if (head) {
                        head = false;
                        targetLines.addAll(noticesSmallFileLines);
                    }
                } else {
                    targetLines.add(line);
                }
            }
        }
        if (head) {
            logger.warn("check " + shorty);
        }
        if (skip) {
            readingErrors++;
            logger.error(source + " package statement is missing ");
        } else if (rewrite(smallSource, targetLines)) {
            javaFilesCopied++;
        }
        return true;
    }

    private boolean rewrite(SmallFile smallSource, List<String> lines) {
        String target = smallSource.getName();
        String shorty = StringUtils.removeStartIgnoreCase(target, resourcesFolderPath.toString());
        logger.info("rewrite " + shorty);
        try {
            Files.write(smallSource.getPath(), lines, smallSource.getCharset());
            return true;
        } catch (IOException ex) {
            writingErrors++;
            logger.fatal(ThrowableUtils.getString(ex), ex);
            logger.fatal("\t" + shorty + " could not be rewritten ");
        }
        return false;
    }

    private void updateFileTypes(String type) {
        if (fileTypes.containsKey(type)) {
            int count = fileTypes.get(type);
            fileTypes.put(type, ++count);
        } else {
            fileTypes.put(type, 1);
        }
    }

    private void printSummary() {
        String tab;
        logger.info(files.size() + " files ");
        logger.info(fileTypes.size() + " file types ");
        for (String type : fileTypes.keySet()) {
            tab = type.contains("/") ? "\t" : "";
            logger.info(tab + type + " = " + fileTypes.get(type));
            printDetail(type);
        }
        logger.info(readingWarnings + " reading warnings ");
        logger.info(readingErrors + " reading errors ");
        logger.info(javaFilesCopied + " files copied ");
        logger.info(javaFilesSkipped + " files skipped ");
        logger.info(writingErrors + " writing errors ");
    }

    private void printDetail(String type) {
        if (detailAll || matches(type)) {
            Charset charset;
            String extension;
            String baseFolderPathString = baseFolderPath.toString();
            String tab = type.contains("/") ? "\t" : "";
            for (SmallFile sf : files.values()) {
                charset = sf.getCharset();
                extension = StringUtils.defaultIfBlank(sf.getExtension(), "?");
                if (charset != null && type.equals(charset + " / " + extension)) {
                    logger.info(tab + "\t" + StringUtils.removeStartIgnoreCase(sf.getName(), baseFolderPathString));
                }
            }
        }
    }

    private boolean matches(String type) {
        if (detailPatterns == null || detailPatterns.isEmpty()) {
            return false;
        }
        for (String regex : detailPatterns) {
            if (type.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    private IOFileFilter fileFilter() {
        IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter[] ayes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "java" + E)
        };
        IOFileFilter ayesFileFilter = FileFilterUtils.or(ayes);
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, ayesFileFilter);
        return filter;
    }

    private IOFileFilter dirFilter() {
        IOFileFilter makeCVSSVNAware = FileFilterUtils.makeCVSAware(FileFilterUtils.makeSVNAware(null));
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "git" + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "target" + E)
        };
        IOFileFilter noesFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
        IOFileFilter filter = FileFilterUtils.and(makeCVSSVNAware, noesFileFilter);
        return filter;
    }

    /**
     * @return the root folder
     */
    public File getRootFolder() {
        return rootFolder;
    }

    /**
     * @return the root folder path
     */
    public Path getRootFolderPath() {
        return rootFolderPath;
    }

    /**
     * @return the resources folder
     */
    public File getResourcesFolder() {
        return resourcesFolder;
    }

    /**
     * @return the resources folder path
     */
    public Path getResourcesFolderPath() {
        return resourcesFolderPath;
    }

    /**
     * @return the base folder
     */
    public File getBaseFolder() {
        return baseFolder;
    }

    /**
     * @return the base folder path
     */
    public Path getBaseFolderPath() {
        return baseFolderPath;
    }

    /**
     * @return the reading warnings count
     */
    public int getReadingWarnings() {
        return readingWarnings;
    }

    /**
     * @return the reading errors count
     */
    public int getReadingErrors() {
        return readingErrors;
    }

    /**
     * @return the files map
     */
    public Map<Path, SmallFile> getFiles() {
        return files;
    }

    /**
     * @return the file types map
     */
    public Map<String, Integer> getFileTypes() {
        return fileTypes;
    }

}
