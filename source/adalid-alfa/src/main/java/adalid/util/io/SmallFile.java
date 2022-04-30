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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class SmallFile {

    static final Charset[] STANDARD_CHARSETS = new Charset[]{
        StandardCharsets.US_ASCII,
        StandardCharsets.ISO_8859_1,
        StandardCharsets.UTF_8,
        StandardCharsets.UTF_16BE,
        StandardCharsets.UTF_16LE,
        StandardCharsets.UTF_16
    };

    static final Charset WINDOWS_CHARSET = Charset.forName("windows-1252");

    static final Charset[] DEFAULT_CHARSETS = new Charset[]{
        StandardCharsets.US_ASCII,
        StandardCharsets.UTF_8,
        WINDOWS_CHARSET,
        StandardCharsets.ISO_8859_1
    };

    public SmallFile(String path) {
        init(path, DEFAULT_CHARSETS);
    }

    public SmallFile(String path, Charset[] charsets) {
        init(path, charsets);
    }

    private void init(String path, Charset[] charsets) {
        _name = path;
//      _extension = StringUtils.substringAfter(StringUtils.substringAfterLast(path, FS), ".");
        _extension = StringUtils.trimToEmpty(StringUtils.substringAfterLast(path, "."));
        _path = Paths.get(path);
        _charsets = charsets;
    }

    private String _name;

    private String _extension;

    private Path _path;

    private Charset[] _charsets;

    private Charset _charset;

    private List<String> _lines;

    public List<String> read() {
        Charset[] charsets = _charsets == null ? DEFAULT_CHARSETS : _charsets;
        _charset = null;
        for (Charset cs : charsets) {
            try {
                _lines = Files.readAllLines(_path, cs);
                _charset = cs;
                return _lines;
            } catch (IOException ex) {
            }
        }
        return null;
    }

    public String getName() {
        return _name;
    }

    public String getExtension() {
        return _extension;
    }

    public Path getPath() {
        return _path;
    }

    public Charset[] getCharsets() {
        return _charsets;
    }

    public Charset getCharset() {
        return _charset;
    }

    public List<String> getLines() {
        return _lines;
    }

    public boolean isEmpty() {
        return _lines == null || _lines.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

}
