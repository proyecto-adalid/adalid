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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class FileWrapper {

    private static final Logger logger = Logger.getLogger(FileWrapper.class);

    private static final Charset[] STANDARD_CHARSETS = new Charset[]{
        StandardCharsets.ISO_8859_1,
        StandardCharsets.UTF_8,
        StandardCharsets.UTF_16,
        StandardCharsets.UTF_16BE,
        StandardCharsets.UTF_16LE,
        StandardCharsets.US_ASCII
    };

    private final File file;

    private final Path path;

    private String type;

    private Charset charset;

    private List<String> list;

    private int readingWarnings, readingErrors;

    public FileWrapper(File sourceFile) {
        file = sourceFile;
        path = Paths.get(sourceFile.getPath());
        // <editor-fold defaultstate="collapsed">
//      String contentType = URLConnection.guessContentTypeFromName(file.getName());
//      String contentType;
//      try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
//          contentType = URLConnection.guessContentTypeFromStream(is);
//      } catch (IOException ex) {
//          contentType = null;
//      }
//      String contentType;
//      try {
//          contentType = Files.probeContentType(path);
//      } catch (IOException ex) {
//          contentType = null;
//      }
//      type = contentType;
        // </editor-fold>
    }

    public List<String> read() {
        String fileName = file.getName();
        String fileType = StringUtils.substringAfterLast(fileName, ".");
        if (StringUtils.isBlank(fileType)) {
            fileType = fileName;
        } else {
            fileType = fileType.toLowerCase();
        }
        type = "binary";
        charset = null;
        for (Charset cs : STANDARD_CHARSETS) {
            try {
                list = Files.readAllLines(path, cs);
                if (isEmpty()) {
                    readingWarnings++;
                    logger.warn(file.getPath() + " is empty ");
                }
                type = "text/" + fileType;
                charset = cs;
                return list;
            } catch (IOException ex) {
                readingWarnings++;
                logger.warn(ex);
                logger.warn(file.getPath() + " could not be read using " + cs);
            }
        }
        readingErrors++;
        logger.error(file.getPath() + " could not be read using a standard charset ");
        return null;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @return the length of the file in bytes
     */
    public long getBytes() {
        return file == null ? 0 : file.length();
    }

    /**
     * @return the size corresponding to the number of bytes
     */
    public FileBytesSize getBytesSize() {
        long bytes = getBytes();
        return bytes <= FileBytesSize.TINY.limit ? FileBytesSize.TINY
            : bytes <= FileBytesSize.SMALL.limit ? FileBytesSize.SMALL
                : bytes <= FileBytesSize.MEDIUM.limit ? FileBytesSize.MEDIUM
                    : bytes <= FileBytesSize.LARGE.limit ? FileBytesSize.LARGE
                        : FileBytesSize.HUGE;
    }

    /**
     * @return the path
     */
    public Path getPath() {
        return path;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * @return the lines
     */
    public List<String> getLinesList() {
        return list;
    }

    /**
     * @return the length of the file in lines
     */
    public int getLines() {
        return list == null ? 0 : list.size();
    }

    /**
     * @return the size corresponding to the number of lines
     */
    public FileLinesSize getLinesSize() {
        long lines = getLines();
        return lines <= FileLinesSize.TINY.limit ? FileLinesSize.TINY
            : lines <= FileLinesSize.SMALL.limit ? FileLinesSize.SMALL
                : lines <= FileLinesSize.MEDIUM.limit ? FileLinesSize.MEDIUM
                    : lines <= FileLinesSize.LARGE.limit ? FileLinesSize.LARGE
                        : FileLinesSize.HUGE;
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
     * @return true if the file is a text file; false otherwise
     */
    public boolean isText() {
        return StringUtils.startsWithIgnoreCase(type, "text");
    }

    /**
     * @return true if the file is an empty file; false otherwise
     */
    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public Path getRelativePath(Path base) {
        if (path.startsWith(base)) {
            try {
                return base.relativize(path);
            } catch (IllegalArgumentException ex) {
                return path;
            }
        } else {
            return path;
        }
    }

}
