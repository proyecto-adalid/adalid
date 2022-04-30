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

import adalid.commons.util.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public class FolderWrapper {

    private final File file;

    private final Path path;

    private final Map<FileBytesSize, Sequence> bytesSizes;

    private final Map<FileLinesSize, Sequence> linesSizes;

    private long localFiles, localBytes, localLines;

    private long totalFiles, totalBytes, totalLines;

    private double filesShare, bytesShare, linesShare;

    public FolderWrapper(File folder) {
        file = folder;
        path = Paths.get(folder.getPath());
        bytesSizes = new LinkedHashMap<>();
        linesSizes = new LinkedHashMap<>();
        initBytesSizes();
        initLinesSizes();
    }

    private void initBytesSizes() {
        for (FileBytesSize bs : FileBytesSize.values()) {
            bytesSizes.put(bs, Sequence.startWith(0));
        }
    }

    private void initLinesSizes() {
        for (FileLinesSize ls : FileLinesSize.values()) {
            linesSizes.put(ls, Sequence.startWith(0));
        }
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @return the path
     */
    public Path getPath() {
        return path;
    }

    /**
     * @return the bytes sizes
     */
    public Map<FileBytesSize, Sequence> getBytesSizes() {
        return bytesSizes;
    }

    /**
     * @return the lines sizes
     */
    public Map<FileLinesSize, Sequence> getLinesSizes() {
        return linesSizes;
    }

    /**
     * @return the files
     */
    public long getLocalFiles() {
        return localFiles;
    }

    /**
     * @param files the files to set
     */
    public void setLocalFiles(long files) {
        this.localFiles = files;
    }

    /**
     * @return the bytes
     */
    public long getLocalBytes() {
        return localBytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setLocalBytes(long bytes) {
        this.localBytes = bytes;
    }

    /**
     * @return the lines
     */
    public long getLocalLines() {
        return localLines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLocalLines(long lines) {
        this.localLines = lines;
    }

    /**
     * @return the total files
     */
    public long getTotalFiles() {
        return totalFiles;
    }

    /**
     * @param totalFiles the total files to set
     */
    public void setTotalFiles(long totalFiles) {
        this.totalFiles = totalFiles;
    }

    /**
     * @return the total bytes
     */
    public long getTotalBytes() {
        return totalBytes;
    }

    /**
     * @param totalBytes the total bytes to set
     */
    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    /**
     * @return the total lines
     */
    public long getTotalLines() {
        return totalLines;
    }

    /**
     * @param totalLines the total lines to set
     */
    public void setTotalLines(long totalLines) {
        this.totalLines = totalLines;
    }

    /**
     * @return the files share
     */
    public double getFilesShare() {
        return filesShare;
    }

    /**
     * @param filesShare the files share to set
     */
    public void setFilesShare(double filesShare) {
        this.filesShare = filesShare;
    }

    /**
     * @return the bytes share
     */
    public double getBytesShare() {
        return bytesShare;
    }

    /**
     * @param bytesShare the bytes share to set
     */
    public void setBytesShare(double bytesShare) {
        this.bytesShare = bytesShare;
    }

    /**
     * @return the lines share
     */
    public double getLinesShare() {
        return linesShare;
    }

    /**
     * @param linesShare the lines share to set
     */
    public void setLinesShare(double linesShare) {
        this.linesShare = linesShare;
    }

}
