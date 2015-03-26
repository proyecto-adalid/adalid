/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.i18n;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.FilUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
class Mapper {

    private static final Logger logger = Logger.getLogger(Mapper.class);

    private static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");

    static Mapper map(String project) {
        return new Mapper(project);
    }

    private Mapper(String project) {
        _project = project;
        map();
    }

    private String _project;

    public String getProject() {
        return _project;
    }

    Map<String, List<File>> _map = new TreeMap<>();

    public Map<String, List<File>> getMap() {
        return _map;
    }

    private boolean map() {
        File rootFolder = PropertiesHandler.getRootFolder();
        if (FilUtils.isNotVisibleDirectory(rootFolder)) {
            return false;
        }
        String projectSource = rootFolder.getPath() + FILE_SEPARATOR + _project + FILE_SEPARATOR + "source";
        File projectSourceFile = new File(projectSource);
        if (FilUtils.isNotVisibleDirectory(projectSourceFile)) {
            return false;
        }
        RegexFileFilter fileFilter = new RegexFileFilter("^Bundle.*\\.properties$");
        RegexFileFilter dirFilter1 = new RegexFileFilter("build");
        RegexFileFilter dirFilter2 = new RegexFileFilter("velocity");
        IOFileFilter notFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(dirFilter1, dirFilter2));
        Collection<File> bundles = FileUtils.listFiles(projectSourceFile, fileFilter, notFileFilter);
        String base;
        List<File> list;
        for (File bundle : bundles) {
            base = base(bundle);
            if (_map.containsKey(base)) {
                list = _map.get(base);
            } else {
                list = new ArrayList<>();
                _map.put(base, list);
            }
            list.add(bundle);
        }
        return true;
    }

    void info() {
        String name, locale;
        List<File> list;
        int m = 0;
        int n = 0;
        Set<String> keySet = _map.keySet();
        for (String key : keySet) {
            logger.info(key);
            list = _map.get(key);
            for (File bundle : list) {
                name = bundle.getName();
                locale = locale(name);
                if (locale == null) {
                    m++;
                } else {
                    n++;
                }
                logger.info("\t" + substringAfter(bundle.getPath(), join("src")));
            }
        }
        logger.info("bundles map size = " + _map.size());
        logger.info("mapped bundles = " + (m + n));
        logger.info("mapped base bundles = " + m);
        logger.info("mapped locale bundles = " + n);
    }

    private String base(File bundle) {
        String name = bundle.getName();
        String base = _project + FILE_SEPARATOR;
        base += StringUtils.substringAfterLast(bundle.getParent(), join(_project));
        if (name.contains("_")) {
            base = base + FILE_SEPARATOR + StringUtils.substringBefore(name, "_");
        } else {
            base = base + FILE_SEPARATOR + StringUtils.substringBeforeLast(name, ".");
        }
        return base;
    }

    private String locale(String name) {
        String substringBeforeLast = StringUtils.substringBeforeLast(name, ".");
        String substringAfter = StringUtils.substringAfter(substringBeforeLast, "_");
        return StringUtils.trimToNull(substringAfter);
    }

    private String substringAfter(String str, String separator) {
        return str.contains(separator) ? StringUtils.substringAfter(str, separator) : str;
    }

    private String join(String... strings) {
        return FILE_SEPARATOR + StringUtils.join(strings, FILE_SEPARATOR) + FILE_SEPARATOR;
    }

}
