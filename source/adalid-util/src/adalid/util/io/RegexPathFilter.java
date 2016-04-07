/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
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

    private static final String OS_NAME = System.getProperties().getProperty("os.name");

    private static final boolean WINDOWS = StringUtils.containsIgnoreCase(OS_NAME, "windows");

    private static final String separator = System.getProperties().getProperty("file.separator");

    public static final String SEPARATOR = separator.equals("\\") ? "\\\\" : "\\" + separator;

    private final Pattern _pattern;

    public RegexPathFilter(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern");
        }
        _pattern = WINDOWS ? Pattern.compile(pattern, Pattern.CASE_INSENSITIVE) : Pattern.compile(pattern);
    }

    @Override
    public boolean accept(File dir) {
        String path = dir.getPath();
        boolean matches = _pattern.matcher(dir.getPath()).matches();
        return matches;
    }

    @Override
    public boolean accept(File dir, String name) {
        return accept(new File(dir, name));
    }

}
