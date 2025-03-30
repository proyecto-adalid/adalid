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
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class FolderBrowser extends Utility {

    private static final Logger logger = Logger.getLogger(FolderBrowser.class);

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";

    private static final String D = "\\.";

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final File rootFolder;

    private final Path rootFolderPath;

    private final File resourcesFolder;

    private final Path resourcesFolderPath;

    private final Path resourcesFolderRelativePath;

    private final File baseFolder;

    private final Path baseFolderPath;

    private final Map<Path, SmallFile> files;

    private final Map<String, Integer> fileTypes;

    private final Map<Path, Set<Path>> folders;

    private boolean detailAll;

    private List<String> detailPatterns;

    private int readingWarnings, readingErrors;
    // </editor-fold>

    public FolderBrowser(String path) {
        rootFolder = PropertiesHandler.getRootFolder();
        if (rootFolder == null) {
            throw new RuntimeException("root folder is missing or invalid");
        }
        rootFolderPath = Paths.get(rootFolder.getPath());
        logger.info("root-folder=" + rootFolderPath);
        path = chooseDirectory(path);
        if (path == null) {
            throw new IllegalArgumentException("null folder path");
        }
        File file = new File(path);
        if (file.isAbsolute()) {
            resourcesFolder = file;
        } else {
            resourcesFolder = new File(rootFolder.getAbsolutePath(), path);
        }
        resourcesFolderPath = Paths.get(resourcesFolder.getPath());
        resourcesFolderRelativePath = getRelativePath(resourcesFolderPath);
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
        folders = new TreeMap<>();
    }

    public FolderBrowser(String path, boolean detail) {
        this(path);
        this.detailAll = detail;
    }

    public FolderBrowser(String path, List<String> details) {
        this(path);
        this.detailAll = false;
        this.detailPatterns = details;
    }

    public boolean browse() {
        logger.info("browse" + StrUtils.enclose(resourcesFolderPath.toString()));
        init();
        doFiles();
        printSummary();
        updateProjectBuilderDictionary(FolderBrowser.class);
        return readingErrors == 0;
    }

    private void init() {
        readingWarnings = 0;
        readingErrors = 0;
    }

    protected boolean doFiles() {
        folders.clear();
        Collection<File> list = FileUtils.listFiles(resourcesFolder, fileFilter(), dirFilter());
        for (File file : list) {
            addFolder(file);
            processFile(file);
        }
        return true;
    }

    private Path getRelativePath(Path path) {
        if (path.startsWith(resourcesFolderPath)) {
            try {
                return resourcesFolderPath.relativize(path);
            } catch (IllegalArgumentException ex) {
                return path;
            }
        } else {
            return path;
        }
    }

    private void addFolder(File file) {
        Path path = Paths.get(file.getPath());
        addFolder(getRelativePath(path), true);
    }

    private void addFolder(Path path, boolean file) {
        Path parent = path.getParent();
        boolean rfp = parent == null;
        if (rfp) {
            parent = resourcesFolderRelativePath;
        }
        Set<Path> set = folders.get(parent);
        if (set == null) {
            if (!rfp) {
                addFolder(parent, false);
            }
            set = new TreeSet<>();
            folders.put(parent, set);
        }
        if (file) {
            set.add(path);
        }
    }

    protected void processFile(File file) {
        SmallFile sf;
        Charset charset;
        String name;
        String extension;
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
        }
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
        logger.info("");
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

    protected IOFileFilter fileFilter() {
        return defaultFileFilter();
    }

    protected final IOFileFilter defaultFileFilter() {
        IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter fileExtensions = defaultFileFilterExcludedFileExtensions();
        IOFileFilter noNameDotFiles = defaultFileFilterExcludedNoNameDotFiles();
        IOFileFilter[] noes = new IOFileFilter[]{fileExtensions, noNameDotFiles};
        IOFileFilter noesFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, noesFileFilter);
        return filter;
    }

    private RegexFileFilter defaultFileFilterExcludedFileExtensions() {
        String group = "(?i)(class|db|ear|err|jar|lnk|log|nbm|out|war|zip)";
        String regex = B + "(" + X + ")" + "(" + D + ")" + group + E;
        logger.info("ExcludedFileExtensions=" + regex);
        return new RegexFileFilter(regex);
    }

    private RegexFileFilter defaultFileFilterExcludedNoNameDotFiles() {
        String group = "(?i)(classpath|gitignore|project)";
        String regex = B + "(" + D + ")" + group + E;
        logger.info("ExcludedNoNameDotFiles=" + regex);
        return new RegexFileFilter(regex);
    }

    protected final IOFileFilter selectedFilesFilter() {
        IOFileFilter filter = FileFilterUtils.or(selectedBinaryFilesFilter(), selectedImageFilesFilter(), selectedTextFilesFilter());
        return filter;
    }

    protected final IOFileFilter selectedBinaryFilesFilter() {
        String group = "(?i)(doc|docx|pdf|ppt|pptx|xls|xlsx)";
        String regex = B + "(" + X + ")" + "(" + D + ")" + group + E;
        logger.info("selectedBinaryFilesFilter=" + regex);
        IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter fileExtensions = new RegexFileFilter(regex);
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, fileExtensions);
        return filter;
    }

    protected final IOFileFilter selectedImageFilesFilter() {
        String group = "(?i)(bmp|git|jpe?g|png|tiff)";
        String regex = B + "(" + X + ")" + "(" + D + ")" + group + E;
        logger.info("selectedImageFilesFilter=" + regex);
        IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter fileExtensions = new RegexFileFilter(regex);
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, fileExtensions);
        return filter;
    }

    protected final IOFileFilter selectedTextFilesFilter() {
        String group = "(?i)(bat|bpmn|conf|css|csv|html|java|jrtx|jrxml|js|jsp|jspf|properties|psql|sh|sql|tsv|txt|vm|xhtml|xml)";
        String regex = B + "(" + X + ")" + "(" + D + ")" + group + E;
        logger.info("selectedTextFilesFilter=" + regex);
        IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter fileExtensions = new RegexFileFilter(regex);
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, fileExtensions);
        return filter;
    }

    protected IOFileFilter dirFilter() {
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
     * @return the folders set
     */
    public Map<Path, Set<Path>> getFolders() {
        return folders;
    }

    /**
     * @return the file types map
     */
    public Map<String, Integer> getFileTypes() {
        return fileTypes;
    }

}
