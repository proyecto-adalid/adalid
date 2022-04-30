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
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class RegexPathFilter implements IOFileFilter {

    private static final String OS_NAME = System.getProperty("os.name");

    private static final boolean WINDOWS = StringUtils.containsIgnoreCase(OS_NAME, "windows");

    private static final String separator = System.getProperty("file.separator");

    public static final String SEPARATOR = separator.equals("\\") ? "\\\\" : "\\" + separator;

    private final Pattern pattern;

    public RegexPathFilter(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern");
        }
        this.pattern = WINDOWS ? Pattern.compile(pattern, Pattern.CASE_INSENSITIVE) : Pattern.compile(pattern);
    }

    @Override
    public boolean accept(File dir) {
        return pattern.matcher(dir.getPath()).matches();
    }

    @Override
    public boolean accept(File dir, String name) {
        return accept(new File(dir, name));
    }

}
