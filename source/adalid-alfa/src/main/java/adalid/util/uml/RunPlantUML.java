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
package adalid.util.uml;

import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.util.*;
import adalid.util.io.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
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
public class RunPlantUML extends Utility {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final Logger logger = Logger.getLogger(RunPlantUML.class);

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";
//
//  private static final String S = RegexPathFilter.SEPARATOR;

    private static final String D = "\\.";
    // </editor-fold>

    public static void main(String[] args) {
        run(USER_DIR);
    }

    public static boolean run(String path) {
        return run(path, false);
    }

    public static boolean run(String path, boolean detail) {
        logger.info("run(" + path + ")");
        try {
            RunPlantUML runner = new RunPlantUML(path, detail);
            return runner.run();
        } catch (IllegalArgumentException e) {
            logger.error(ThrowableUtils.getString(e));
        }
        return false;
    }

    public static boolean run(String path, List<String> details) {
        logger.info("run(" + path + ", " + details + ")");
        try {
            RunPlantUML runner = new RunPlantUML(path, details);
            return runner.run();
        } catch (IllegalArgumentException e) {
            logger.error(ThrowableUtils.getString(e));
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final File rootFolder;

    private final Path rootFolderPath;

    private final File resourcesFolder;

    private final Path resourcesFolderPath;

    private final File baseFolder;

    private final Path baseFolderPath;

    private final File chosenFile;

    private final Map<Path, SmallFile> files;

    private final Map<String, Integer> fileTypes;

    private boolean detailAll;

    private List<String> detailPatterns;

    private int filesRead, filesSkipped, filesWritten;

    private int readingErrors, writingErrors;
    // </editor-fold>

    private RunPlantUML(String path) {
        rootFolder = PropertiesHandler.getRootFolder();
        if (rootFolder == null) {
            throw new RuntimeException("root folder is missing or invalid");
        }
        rootFolderPath = Paths.get(rootFolder.getPath());
        logger.info("root-folder=" + rootFolderPath);
        String from = StringUtils.defaultIfBlank(FilUtils.replaceSlashes(path), rootFolder.getPath());
        logger.info("from-folder=" + from);
        from = chooseFileOrDirectory(from, false, new FileNameRegexFilter("directories or text files", "^.*\\.txt$", true));
        if (from == null) {
            throw new IllegalArgumentException("No file or directory selected, operation cancelled");
        }
        File file = new File(from);
        if (file.isAbsolute()) {
            resourcesFolder = file;
        } else {
            resourcesFolder = new File(rootFolder.getAbsolutePath(), from);
        }
        resourcesFolderPath = Paths.get(resourcesFolder.getPath());
        if (resourcesFolder.isDirectory()) {
            chosenFile = null;
            logger.info("chosen-path=" + resourcesFolderPath);
            if (resourcesFolder.isHidden()) {
                throw new IllegalArgumentException(resourcesFolderPath + " is a hidden directory");
            }
        } else if (resourcesFolder.isFile()) {
            chosenFile = resourcesFolder;
            logger.info("chosen-file=" + chosenFile.getPath());
        } else {
            throw new IllegalArgumentException(resourcesFolderPath + " is not a directory");
        }
        baseFolder = resourcesFolder.getParentFile();
        baseFolderPath = Paths.get(baseFolder.getPath());
        logger.info("parent-path=" + baseFolderPath);
        files = new TreeMap<>();
        fileTypes = new TreeMap<>();
    }

    private RunPlantUML(String path, boolean detail) {
        this(path);
        this.detailAll = detail;
    }

    private RunPlantUML(String path, List<String> details) {
        this(path);
        this.detailAll = false;
        this.detailPatterns = details;
    }

    private boolean run() {
//      logger.info("RunPlantUML" + StrUtils.enclose(resourcesFolderPath.toString()));
        logger.info("RunPlantUML" + StrUtils.enclose(resourcesFolderPath.getFileName().toString()));
        init();
        readFiles();
        printSummary();
        updateProjectBuilderDictionary(RunPlantUML.class);
        return readingErrors == 0;
    }

    private void init() {
        filesRead = 0;
        filesSkipped = 0;
        filesWritten = 0;
        readingErrors = 0;
        writingErrors = 0;
    }

    private boolean readFiles() {
        Collection<File> list = files();
        SmallFile sf;
        List<String> lines;
        Charset charset;
        String name;
        String extension;
        for (File file : list) {
            sf = new SmallFile(file.getPath());
            sf.read();
            name = file.getName();
//          name = sf.getName();
            lines = sf.getLines();
            charset = sf.getCharset();
            extension = StringUtils.defaultIfBlank(sf.getExtension().toLowerCase(), "?");
            if (charset == null) {
                readingErrors++;
                logger.error(name + " could not be read using any of the specified character sets ");
            } else if (sf.isEmpty()) {
                filesRead++;
                filesSkipped++;
                logger.warn(name + " was skipped, it is empty ");
            } else if (validFile(lines)) {
                filesRead++;
//              logger.info(name + " " + lines.get(0));
                files.put(sf.getPath(), sf);
                updateFileTypes(charset + "");
                updateFileTypes(charset + " / " + extension);
                File image = PlantUML.generateImage(file);
                if (image == null) {
                    writingErrors++;
                    logger.error(name + " could not be generated ");
                } else {
                    filesWritten++;
                    logger.info(image.getName() + " successfully generated ");
                }
            } else {
                filesRead++;
                filesSkipped++;
                logger.info(name + " was skipped, it is not a PlantUML file ");
            }
        }
        return true;
    }

    private Collection<File> files() {
        return chosenFile != null ? Collections.singletonList(chosenFile) : FileUtils.listFiles(resourcesFolder, fileFilter(), dirFilter());
    }

    private static final String at_start = "@start", at_end = "@end";

    private static final int start_length = at_start.length(), end_length = at_end.length();

    private boolean validFile(List<String> lines) {
        if (lines == null || lines.size() < 2) {
            return false;
        }
        String firstLine = lines.get(0);
        String lastLine = lines.get(lines.size() - 1);
        return firstLine.length() > start_length && lastLine.length() > end_length
            && firstLine.startsWith(at_start) && lastLine.startsWith(at_end)
            && firstLine.substring(start_length).equals(lastLine.substring(end_length));
    }

    private void updateFileTypes(String type) {
        if (fileTypes.containsKey(type)) {
            int count = fileTypes.get(type);
            fileTypes.put(type, ++count);
        } else {
            fileTypes.put(type, 1);
        }
    }

    private static final boolean printDetail = false;

    private void printSummary() {
        if (printDetail) {
            String tab;
            logger.info(files.size() + " files ");
            logger.info(fileTypes.size() + " file types ");
            for (String type : fileTypes.keySet()) {
                tab = type.contains("/") ? "\t" : "";
                logger.info(tab + type + " = " + fileTypes.get(type));
                printDetail(type);
            }
        }
        logger.info(filesRead + " files read ");
        logger.info(filesSkipped + " files skipped ");
        logger.info(filesWritten + " files written ");
        logger.info(readingErrors + " reading errors ");
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
            new RegexFileFilter(B + X + D + "txt" + E)
        };
        IOFileFilter ayesFileFilter = FileFilterUtils.or(ayes);
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, ayesFileFilter);
        return filter;
    }

    private IOFileFilter dirFilter() {
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "git" + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "target" + E)
        };
        return FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
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
