/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class FilUtils {

    private static final String OS_NAME = System.getProperties().getProperty("os.name");

    private static final boolean WINDOWS = StringUtils.containsIgnoreCase(OS_NAME, "windows");

    private static final String ENV_VAR_LEFT_MARK = WINDOWS ? "%" : "${";

    private static final String ENV_VAR_RIGHT_MARK = WINDOWS ? "%" : "}";

    private static final String HOME_ENV_VAR = WINDOWS ? "USERPROFILE" : "HOME";

    private static final String HOME_VAR = ENV_VAR_LEFT_MARK + HOME_ENV_VAR + ENV_VAR_RIGHT_MARK;

    private static final String HOME_DIR = System.getenv(HOME_ENV_VAR);

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String USER_HOME = System.getProperties().getProperty("user.home");

    private static final String FILE_SEP = System.getProperties().getProperty("file.separator");

    private static final String WORKSPACE_FOLDER_NAME = "workspace";

    private static final String workspace_folder_path;

    private static final String[] workspace_folder_keys;

    static {
        workspace_folder_path = workspaceFolderPath();
        workspace_folder_keys = workspaceFolderKeys();
    }

    private static String workspaceFolderPath() {
        File dir = workspaceFolderFile(new File(USER_DIR));
        if (dir != null) {
            return dir.getPath();
        }
        String suffix = FILE_SEP + WORKSPACE_FOLDER_NAME;
        String search = suffix + FILE_SEP;
//      logger.trace("looking last occurrence of \"" + search + "\" within \"" + USER_DIR + "\"");
        int i = StringUtils.lastIndexOfIgnoreCase(USER_DIR, search);
        String base = i < 0 ? USER_HOME : USER_DIR.substring(0, i);
        return base + suffix;
    }

    private static File workspaceFolderFile(File dir) {
//      logger.trace("looking file " + WORKSPACE_FOLDER_NAME + " at " + dir.getPath());
        File file = new File(dir.getPath() + FILE_SEP + WORKSPACE_FOLDER_NAME);
        File parent = dir.getParentFile();
        return isVisibleFile(file) ? dir : parent == null ? null : workspaceFolderFile(parent);
    }

    private static String[] workspaceFolderKeys() {
        String key1 = "(?i)\\$" + WORKSPACE_FOLDER_NAME;
        String key2 = "(?i)\\$\\{" + WORKSPACE_FOLDER_NAME + "\\}";
        return new String[]{key1, key2};
    }

    public static String getDefaultEnvironmentalWorkspaceFolderPath() {
        return HOME_VAR + FILE_SEP + WORKSPACE_FOLDER_NAME;
    }

    public static String getCurrentEnvironmentalWorkspaceFolderPath() {
        if (StringUtils.isBlank(HOME_DIR) && StringUtils.isBlank(USER_HOME)) {
            return workspace_folder_path;
        }
        if (StringUtils.startsWithIgnoreCase(workspace_folder_path, HOME_DIR)) {
            return HOME_VAR + StringUtils.removeStartIgnoreCase(workspace_folder_path, HOME_DIR);
        }
        if (StringUtils.startsWithIgnoreCase(workspace_folder_path, USER_HOME)) {
            return HOME_VAR + StringUtils.removeStartIgnoreCase(workspace_folder_path, USER_HOME);
        }
        return workspace_folder_path;
    }

    public static String getWorkspaceFolderPath() {
        return workspace_folder_path;
    }

    public static String fixPath(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        String string = StringUtils.replace(path.trim(), "/", FILE_SEP);
        String replacement = workspace_folder_path.replace("\\", "\\\\");
        for (String workspace_folder_key : workspace_folder_keys) {
            string = string.replaceAll(workspace_folder_key, replacement);
        }
        return string;
    }

    public static File getDirectory(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.isDirectory();
            if (b) {
                return file;
            }
        }
        return null;
    }

    public static File getFile(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.isFile();
            if (b) {
                return file;
            }
        }
        return null;
    }

    public static boolean exists(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.exists();
        }
        return b;
    }

    public static boolean isDirectory(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.isDirectory();
        }
        return b;
    }

    public static boolean isFile(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.isFile();
        }
        return b;
    }

    public static boolean isPath(String path) {
        boolean b = StringUtils.isNotBlank(path);
        if (b) {
            String sep = System.getProperties().getProperty("path.separator");
            String[] pathnames = StringUtils.split(path, sep);
            for (String pathname : pathnames) {
                b &= exists(pathname);
            }
        }
        return b;
    }

    public static boolean isVisibleDirectory(File file) {
        return file != null && file.isDirectory() && !file.isHidden();
    }

    public static boolean isNotVisibleDirectory(File file) {
        return !isVisibleDirectory(file);
    }

    public static boolean isReadableDirectory(File file) {
        return file != null && file.isDirectory() && file.canRead();
    }

    public static boolean isNotReadableDirectory(File file) {
        return !isReadableDirectory(file);
    }

    public static boolean isWritableDirectory(File file) {
        return file != null && file.isDirectory() && file.canWrite();
    }

    public static boolean isNotWritableDirectory(File file) {
        return !isWritableDirectory(file);
    }

    public static boolean isVisibleFile(File file) {
        return file != null && file.isFile() && !file.isHidden();
    }

    public static boolean isNotVisibleFile(File file) {
        return !isVisibleFile(file);
    }

    public static boolean isReadableFile(File file) {
        return file != null && file.isFile() && file.canRead();
    }

    public static boolean isNotReadableFile(File file) {
        return !isReadableFile(file);
    }

    public static boolean isWritableFile(File file) {
        return file != null && file.isFile() && file.canWrite();
    }

    public static boolean isNotWritableFile(File file) {
        return !isWritableFile(file);
    }

    public static boolean mkdir(String dir) {
        File file = new File(dir);
        return file.isDirectory() || file.mkdirs();
    }

    public static boolean mkdirs(String dirs) {
        File file = new File(dirs);
        return file.isDirectory() || file.mkdirs();
    }

    public static List<String> directoriesPathList(File file) {
        List<String> list = new ArrayList<>();
        if (isVisibleDirectory(file)) {
            list.add(file.getPath());
            for (File sub : file.listFiles(visibleDirectoryFilter())) {
                list.addAll(directoriesPathList(sub));
            }
        }
        return list;
    }

    public static Map<String, File> directoriesMap(File file) {
        Map<String, File> map = new LinkedHashMap<>();
        if (isVisibleDirectory(file)) {
            map.put(file.getPath(), file);
            for (File sub : file.listFiles(visibleDirectoryFilter())) {
                map.putAll(directoriesMap(sub));
            }
        }
        return map;
    }

    public static Map<String, File> directoriesMap(File directory, String[] pathnames) {
        return directoriesMap(directory, pathnames, null);
    }

    public static Map<String, File> directoriesMap(File directory, String[] pathnames, File top) {
        Map<String, File> map = new LinkedHashMap<>();
        File file;
        for (String pathname : pathnames) {
            file = new File(directory, pathname);
            if (isVisibleDirectory(top)) {
                map.putAll(parentDirectoriesMap(file, top));
            }
            map.putAll(directoriesMap(file));
        }
        return map;
    }

    public static Map<String, File> parentDirectoriesMap(File file) {
        return parentDirectoriesMap(file, null);
    }

    public static Map<String, File> parentDirectoriesMap(File file, File top) {
        Map<String, File> map = new LinkedHashMap<>();
        File parent;
        File topdir = isVisibleDirectory(top) ? top : null;
        if (isVisibleDirectory(file) || isVisibleFile(file)) {
            parent = file.getParentFile();
            if (isVisibleDirectory(parent)) {
                if (parent.equals(topdir)) {
                } else {
                    map.putAll(parentDirectoriesMap(parent, topdir));
                    map.put(parent.getPath(), parent);
                }
            }
        }
        return map;
    }

    public static FileFilter visibleDirectoryFilter() {
        return new FileFilter() {

            @Override
            public boolean accept(File file) {
                return isVisibleDirectory(file);
            }

        };
    }

    public static FileFilter nameContainsFilter(final String string) {
        return new FileFilter() {

            @Override
            public boolean accept(File file) {
                return isVisibleFile(file)
                    && StringUtils.containsIgnoreCase(file.getName(), string);
            }

        };
    }

    public static FileFilter nameEndsWithFilter(final String suffix) {
        return new FileFilter() {

            @Override
            public boolean accept(File file) {
                return isVisibleFile(file)
                    && StringUtils.endsWithIgnoreCase(file.getName(), suffix);
            }

        };
    }

    public static FileFilter nameStartsWithFilter(final String prefix) {
        return new FileFilter() {

            @Override
            public boolean accept(File file) {
                return isVisibleFile(file)
                    && StringUtils.startsWithIgnoreCase(file.getName(), prefix);
            }

        };
    }

}
