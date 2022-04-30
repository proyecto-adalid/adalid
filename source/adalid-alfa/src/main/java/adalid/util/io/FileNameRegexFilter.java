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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.filechooser.FileFilter;

/**
 * @author Jorge Campins
 */
public class FileNameRegexFilter extends FileFilter {

    private final String _description;

    private final Pattern _pattern;

    private final boolean _dirs;

    /**
     * Creates a {@code FileNameRegexFilter} with the specified description and regular expression.
     *
     * The returned {@code FileNameRegexFilter} will accept all directories and any file with a file name matching {@code regex}.
     *
     * @param description textual description for the filter, may be {@code null}
     * @param regex the accepted file name regular expression
     * @throws IllegalArgumentException if regex is null
     * @throws PatternSyntaxException if regex's syntax is invalid
     * @see #accept
     */
    public FileNameRegexFilter(String description, String regex) {
        this(description, regex, true);
    }

    /**
     * Creates a {@code FileNameRegexFilter} with the specified description and regular expression.
     *
     * The returned {@code FileNameRegexFilter} will accept all directories and any file with a file name matching {@code regex}.
     *
     * @param description textual description for the filter, may be {@code null}
     * @param regex the accepted file name regular expression
     * @param dirs true if directories are to be accepted
     * @throws IllegalArgumentException if regex is null
     * @throws PatternSyntaxException if regex's syntax is invalid
     * @see #accept
     */
    public FileNameRegexFilter(String description, String regex, boolean dirs) {
        if (regex == null) {
            throw new IllegalArgumentException("regex is null");
        }
        _description = description;
        _pattern = Pattern.compile(regex);
        _dirs = dirs;
    }

    /**
     * Tests the specified file, returning true if it is a directory and directories are to be accepted, or its name matches the regular expression of
     * this {@code FileFilter}.
     *
     * @param f the {@code File} to test
     * @return true if the file is to be accepted, false otherwise
     */
    @Override
    public boolean accept(File f) {
        return f != null && ((_dirs && f.isDirectory()) || (f.isFile() && _pattern.matcher(f.getName()).matches()));
    }

    /**
     * @return the description of this filter
     */
    @Override
    public String getDescription() {
        return _description;
    }

    /**
     * @return the regular expression files are tested against
     */
    public Pattern getPattern() {
        return _pattern;
    }

    /**
     * @return a string representation of this {@code FileNameRegexFilter}
     */
    @Override
    public String toString() {
        return super.toString() + "[description=" + getDescription() + ", regex=" + _pattern + "]";
    }

}
