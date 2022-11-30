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

    /**
     * https://www.i18nqa.com/debug/table-iso8859-1-vs-windows-1252.html
     *
     * ISO-8859-1 (also called Latin-1) is identical to Windows-1252 (also called CP1252) except for the code points 128-159 (0x80-0x9F). ISO-8859-1
     * assigns several control codes in this range. Windows-1252 has several characters, punctuation, arithmetic and business symbols assigned to
     * these code points.
     *
     * Mislabeling text encoded in Windows-1252 as ISO-8859-1 and then converting from ISO-8859-1 to Unicode or other encodings causes the characters
     * in the range 128-159 to be lost. They are converted as if they were control codes and typically display as white space, a specialized question
     * mark, or a square showing the 4 hex digits of the code point. Using an ISO-8859-1 font that does not have the correct glyphs for the
     * Windows-1252 characters will cause the characters to be displayed incorrectly.
     */
    public static final Charset WINDOWS_CHARSET = Charset.forName("windows-1252");

    /*
    public static final Charset[] STANDARD_CHARSETS = new Charset[]{
        StandardCharsets.US_ASCII,
        StandardCharsets.ISO_8859_1,
        StandardCharsets.UTF_8,
        StandardCharsets.UTF_16BE,
        StandardCharsets.UTF_16LE,
        StandardCharsets.UTF_16
    };

    /**/
    public static final Charset[] DEFAULT_CHARSETS = new Charset[]{
        StandardCharsets.UTF_8,
        WINDOWS_CHARSET
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
