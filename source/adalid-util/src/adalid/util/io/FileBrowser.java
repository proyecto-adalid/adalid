/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.io;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.StrUtils;
import adalid.util.Utility;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
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
public class FileBrowser extends Utility {

    private static final Logger logger = Logger.getLogger(FileBrowser.class);

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";

    private static final String S = RegexPathFilter.SEPARATOR;

    private static final String D = "\\.";

    public static void main(String[] args) {
        browse("adalid/source/adalid-oracle/resources");
        browse("adalid/source/adalid-prime/resources");
        browse("adalid/source/development/resources/velocity");
    }

    public static boolean browse(String path) {
        logger.info("browse" + StrUtils.enclose(path));
        FileBrowser fileBrowser = new FileBrowser(path);
        return fileBrowser.browse();
    }

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final File rootFolder;

    private final Path rootFolderPath;

    private final File resourcesFolder;

    private final Path resourcesFolderPath;

    private final File baseFolder;

    private final Path baseFolderPath;

    private int readingWarnings, readingErrors;

    private final Map<Path, SmallFile> files;

    private final Map<String, Integer> fileTypes;
    // </editor-fold>

    private FileBrowser(String path) {
        rootFolder = PropertiesHandler.getRootFolder();
        if (rootFolder == null) {
            throw new RuntimeException("root folder is missing or invalid");
        }
        rootFolderPath = Paths.get(rootFolder.getPath());
        logger.info("root-folder=" + rootFolderPath);
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

    private boolean browse() {
        logger.info("browse" + StrUtils.enclose(resourcesFolderPath.toString()));
        init();
        readFiles();
        printSummary();
        return readingErrors == 0;
    }

    private void init() {
        readingWarnings = 0;
        readingErrors = 0;
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
            }
        }
        return true;
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
        logger.info(files.size() + " files ");
        logger.info(fileTypes.size() + " file types ");
        Charset charset;
        String extension;
        final String ASCII = StandardCharsets.US_ASCII.toString();
        final String UTF_8 = StandardCharsets.UTF_8.toString();
        boolean b1, b2;
        String baseFolderPathString = baseFolderPath.toString();
        for (String type : fileTypes.keySet()) {
            logger.info(type + " = " + fileTypes.get(type));
            b1 = type.startsWith(ASCII) && !type.endsWith("?");
//          b2 = type.startsWith(UTF_8) && (type.endsWith("java") || type.endsWith("jrxml"));
            if (b1) {
            } else {
                for (SmallFile sf : files.values()) {
                    charset = sf.getCharset();
                    extension = StringUtils.defaultIfBlank(sf.getExtension(), "?");
                    if (charset != null && type.startsWith(charset.toString()) && type.endsWith(extension)) {
                        logger.info("\t" + StringUtils.removeStartIgnoreCase(sf.getName(), baseFolderPathString));
                    }
                }
            }
        }
        logger.info(readingWarnings + " reading warnings ");
        logger.info(readingErrors + " reading errors ");
        logger.info("");
    }

    private IOFileFilter fileFilter() {
        IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "class" + E),
            new RegexFileFilter(B + X + D + "db" + E),
            new RegexFileFilter(B + X + D + "ear" + E),
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jar" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "lnk" + E),
            new RegexFileFilter(B + X + D + "log" + E),
            new RegexFileFilter(B + X + D + "png" + E),
            new RegexFileFilter(B + X + D + "war" + E),
            new RegexFileFilter(B + X + D + "zip" + E),
            new RegexFileFilter(B + D + X + E)
        };
        IOFileFilter noesFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, noesFileFilter);
        return filter;
    }

    private IOFileFilter dirFilter() {
        return FileFilterUtils.makeCVSAware(FileFilterUtils.makeSVNAware(null));
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
