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

import adalid.commons.properties.*;
import adalid.commons.velocity.*;
import adalid.util.Platform;
import adalid.util.io.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class MetaFolderSql {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final Logger logger = Logger.getLogger(MetaFolderSql.class);

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";

    private static final String S = RegexPathFilter.SEPARATOR;

    private static final String D = "\\.";

    private static final long MAX_FILE_SIZE_BYTES = 5242880L;

    private static final double MAX_FILE_SIZE_MEGAS = MAX_FILE_SIZE_BYTES / 1048576D;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final File rootFolder;

    private final Path rootFolderPath;

    private final File metaFolder;

    private final Path metaFolderPath;

    private final FolderWrapper metaFolderWrapper;

    private final File baseFolder;

    private final Path baseFolderPath;

    private int readingWarnings, readingErrors;

    private final Map<Path, FileWrapper> files;

    private final Map<Path, FolderWrapper> folders;

    private final Map<String, Integer> fileTypes;
    // </editor-fold>

    public MetaFolderSql(String path) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(2);
        logger.info("max-file-size=" + nf.format(MAX_FILE_SIZE_MEGAS) + "MB");
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
                metaFolder = file;
            } else {
                metaFolder = new File(rootFolder.getAbsolutePath(), path);
            }
        }
        metaFolderPath = Paths.get(metaFolder.getPath());
        logger.info("meta-folder=" + metaFolderPath);
        if (metaFolder.isDirectory()) {
            if (metaFolder.isHidden()) {
                throw new IllegalArgumentException(metaFolderPath + " is a hidden directory");
            }
        } else {
            throw new IllegalArgumentException(metaFolderPath + " is not a directory");
        }
        baseFolder = metaFolder.getParentFile();
        baseFolderPath = Paths.get(baseFolder.getPath());
        logger.info("base-folder=" + baseFolderPath);
        files = new TreeMap<>();
        folders = new TreeMap<>();
        fileTypes = new TreeMap<>();
        metaFolderWrapper = new FolderWrapper(metaFolder);
        folders.put(getRelativeToBasePath(metaFolder), metaFolderWrapper);
    }

    public boolean read() {
        logger.info("read");
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
//      Path base = metaFolderPath;
        Path base = metaFolderPath.getParent();
        Collection<File> list = FileUtils.listFiles(metaFolder, fileFilter(), dirFilter());
        FileWrapper wrapper;
        long bytes, lines;
        String type;
        for (File file : list) {
            wrapper = new FileWrapper(file);
            wrapper.read();
            bytes = wrapper.getBytes();
            lines = wrapper.getLines();
            if (wrapper.isNotEmpty()) {
                files.put(wrapper.getPath(), wrapper);
                type = wrapper.getType();
                updateFileTypes(type);
                updateFolders(wrapper);
            }
            readingWarnings += wrapper.getReadingWarnings();
            readingErrors += wrapper.getReadingErrors();
            logger.debug("file=" + wrapper.getRelativePath(base) + ", " + wrapper.getCharset() + ", " + bytes + ", " + lines);
        }
        updateFolders();
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

    private void updateFolders(FileWrapper fileWrapper) {
        File parent = fileWrapper.getFile().getParentFile();
        if (parent != null) {
            FolderWrapper parentWrapper;
            Path parentPath = getRelativeToBasePath(parent);
            if (folders.containsKey(parentPath)) {
                parentWrapper = folders.get(parentPath);
            } else {
                parentWrapper = new FolderWrapper(parent);
                folders.put(parentPath, parentWrapper);
            }
            parentWrapper.getBytesSizes().get(fileWrapper.getBytesSize()).next();
            parentWrapper.getLinesSizes().get(fileWrapper.getLinesSize()).next();
            parentWrapper.setLocalFiles(parentWrapper.getLocalFiles() + 1);
            parentWrapper.setLocalBytes(parentWrapper.getLocalBytes() + fileWrapper.getBytes());
            parentWrapper.setLocalLines(parentWrapper.getLocalLines() + fileWrapper.getLines());
            parentWrapper.setTotalFiles(parentWrapper.getTotalFiles() + 1);
            parentWrapper.setTotalBytes(parentWrapper.getTotalBytes() + fileWrapper.getBytes());
            parentWrapper.setTotalLines(parentWrapper.getTotalLines() + fileWrapper.getLines());
            if (parent.equals(metaFolder)) {
            } else {
                updateFolders(fileWrapper, parent);
            }
        }
    }

    private void updateFolders(FileWrapper fileWrapper, File folder) {
        File parent = folder.getParentFile();
        if (parent != null) {
            FolderWrapper parentWrapper;
            Path parentPath = getRelativeToBasePath(parent);
            if (folders.containsKey(parentPath)) {
                parentWrapper = folders.get(parentPath);
            } else {
                parentWrapper = new FolderWrapper(parent);
                folders.put(parentPath, parentWrapper);
            }
            parentWrapper.getBytesSizes().get(fileWrapper.getBytesSize()).next();
            parentWrapper.getLinesSizes().get(fileWrapper.getLinesSize()).next();
            parentWrapper.setTotalFiles(parentWrapper.getTotalFiles() + 1);
            parentWrapper.setTotalBytes(parentWrapper.getTotalBytes() + fileWrapper.getBytes());
            parentWrapper.setTotalLines(parentWrapper.getTotalLines() + fileWrapper.getLines());
            if (parent.equals(metaFolder)) {
            } else {
                updateFolders(fileWrapper, parent);
            }
        }
    }

    private void updateFolders() {
        double totalFiles = metaFolderWrapper.getTotalFiles();
        double totalBytes = metaFolderWrapper.getTotalBytes();
        double totalLines = metaFolderWrapper.getTotalLines();
        double filesShare, bytesShare, linesShare;
        for (FolderWrapper wrapper : folders.values()) {
            filesShare = totalFiles == 0 ? 0 : wrapper.getTotalFiles() / totalFiles;
            bytesShare = totalBytes == 0 ? 0 : wrapper.getTotalBytes() / totalBytes;
            linesShare = totalLines == 0 ? 0 : wrapper.getTotalLines() / totalLines;
            wrapper.setFilesShare(filesShare);
            wrapper.setBytesShare(bytesShare);
            wrapper.setLinesShare(linesShare);
        }
    }

    private Path getRelativeToBasePath(File file) {
        Path path = Paths.get(file.getPath());
        if (path.startsWith(baseFolderPath)) {
            try {
                return baseFolderPath.relativize(path);
            } catch (IllegalArgumentException ex) {
                return path;
            }
        } else {
            return path;
        }
    }

    private void printSummary() {
        logger.info(files.size() + " files ");
        logger.info(folders.size() + " folders ");
        logger.info(fileTypes.size() + " file types ");
//      for (String type : fileTypes.keySet()) {
//          logger.info(type + "=" + fileTypes.get(type));
//      }
        logger.info(readingWarnings + " reading warnings ");
        logger.info(readingErrors + " reading errors ");
    }

    private IOFileFilter fileFilter() {
//      IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter sizeFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.sizeFileFilter(MAX_FILE_SIZE_BYTES));
        // <editor-fold defaultstate="collapsed">
//      IOFileFilter[] ayes = new IOFileFilter[]{
//          new RegexFileFilter(B + X + D + "bat" + E),
//          new RegexFileFilter(B + X + D + "conf" + E),
//          new RegexFileFilter(B + X + D + "css" + E),
//          new RegexFileFilter(B + X + D + "java" + E),
//          new RegexFileFilter(B + X + D + "jrtx" + E),
//          new RegexFileFilter(B + X + D + "jrxml" + E),
//          new RegexFileFilter(B + X + D + "js" + E),
//          new RegexFileFilter(B + X + D + "jsp" + E),
//          new RegexFileFilter(B + X + D + "jspf" + E),
//          new RegexFileFilter(B + X + D + "mf" + E),
//          new RegexFileFilter(B + X + D + "password" + E),
//          new RegexFileFilter(B + X + D + "properties" + E),
//          new RegexFileFilter(B + X + D + "psql" + E),
//          new RegexFileFilter(B + X + D + "sh" + E),
//          new RegexFileFilter(B + X + D + "sql" + E),
//          new RegexFileFilter(B + X + D + "tld" + E),
//          new RegexFileFilter(B + X + D + "txt" + E),
//          new RegexFileFilter(B + X + D + "vm" + E),
//          new RegexFileFilter(B + X + D + "xml" + E)
//      };
//      IOFileFilter ayesFileFilter = FileFilterUtils.or(ayes);
        // </editor-fold>
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "[0-9]*" + E),
            new RegexFileFilter(B + X + D + "arial" + E),
            new RegexFileFilter(B + X + D + "class" + E),
            new RegexFileFilter(B + X + D + "cli" + E),
            new RegexFileFilter(B + X + D + "conf" + E),
            new RegexFileFilter(B + X + D + "db" + E),
            new RegexFileFilter(B + X + D + "doc" + E),
            new RegexFileFilter(B + X + D + "docx" + E),
            new RegexFileFilter(B + X + D + "ear" + E),
            new RegexFileFilter(B + X + D + "err" + E),
            new RegexFileFilter(B + X + D + "gif" + E),
            new RegexFileFilter(B + X + D + "jar" + E),
            new RegexFileFilter(B + X + D + "jasper" + E),
            new RegexFileFilter(B + X + D + "jpg" + E),
            new RegexFileFilter(B + X + D + "key" + E),
            new RegexFileFilter(B + X + D + "lnk" + E),
            new RegexFileFilter(B + X + D + "log" + E),
            new RegexFileFilter(B + X + D + "lst" + E),
            new RegexFileFilter(B + X + D + "out" + E),
            new RegexFileFilter(B + X + D + "pdf" + E),
            new RegexFileFilter(B + X + D + "png" + E),
            new RegexFileFilter(B + X + D + "ppt" + E),
            new RegexFileFilter(B + X + D + "pptx" + E),
            new RegexFileFilter(B + X + D + "war" + E),
            new RegexFileFilter(B + X + D + "xls" + E),
            new RegexFileFilter(B + X + D + "xlsx" + E),
            new RegexFileFilter(B + X + D + "zip" + E),
            new RegexFileFilter(B + D + X + E)
        };
        IOFileFilter noesFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
        IOFileFilter filter = FileFilterUtils.and(sizeFileFilter, noesFileFilter);
        return filter;
    }

    private IOFileFilter dirFilter() {
        IOFileFilter makeFileFilter = FileFilterUtils.makeCVSAware(FileFilterUtils.makeSVNAware(null));
        IOFileFilter metaFileFilter = new RegexPathFilter(B + X + S + "velocity" + S + "templates" + S + "meta" + S + X + E);
        IOFileFilter mesaFileFilter = FileFilterUtils.notFileFilter(metaFileFilter);
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + D + "git" + E),
            new RegexFileFilter(B + D + "settings" + E),
            new RegexFileFilter(B + "backup" + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "logs" + E),
            new RegexFileFilter(B + "nbproject" + E),
            new RegexFileFilter(B + "private" + E),
            new RegexFileFilter(B + "target" + E),
            new RegexFileFilter(B + "test" + E)
        };
        IOFileFilter noesFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
        IOFileFilter filter = FileFilterUtils.and(makeFileFilter, mesaFileFilter, noesFileFilter);
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
     * @return the meta folder
     */
    public File getMetaFolder() {
        return metaFolder;
    }

    /**
     * @return the meta folder path
     */
    public Path getMetaFolderPath() {
        return metaFolderPath;
    }

    /**
     * @return the meta folder wrapper
     */
    public FolderWrapper getMetaFolderWrapper() {
        return metaFolderWrapper;
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
    public Map<Path, FileWrapper> getFiles() {
        return files;
    }

    /**
     * @return the file folders map
     */
    public Map<Path, FolderWrapper> getFolders() {
        return folders;
    }

    /**
     * @return the file types map
     */
    public Map<String, Integer> getFileTypes() {
        return fileTypes;
    }

    public void write() {
        logger.info("write");
        Writer writer = new Writer(this, "root");
        writer.write(Platform.META_FOLDER_SQL);
    }

}
