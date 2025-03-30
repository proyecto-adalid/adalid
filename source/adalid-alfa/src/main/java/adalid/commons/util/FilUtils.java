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
package adalid.commons.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class FilUtils {

    private static final Logger logger = Logger.getLogger(FilUtils.class);

    private static final String OS_NAME = System.getProperty("os.name");

    private static final boolean WINDOWS = StringUtils.containsIgnoreCase(OS_NAME, "windows");

    private static final String ENV_VAR_LEFT_MARK = WINDOWS ? "%" : "${";

    private static final String ENV_VAR_RIGHT_MARK = WINDOWS ? "%" : "}";

    private static final String HOME_ENV_VAR = WINDOWS ? "USERPROFILE" : "HOME";

    private static final String HOME_VAR = ENV_VAR_LEFT_MARK + HOME_ENV_VAR + ENV_VAR_RIGHT_MARK;

    private static final String HOME_DIR = System.getenv(HOME_ENV_VAR);

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String USER_HOME = System.getProperty("user.home");

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final String DOT = ".";

    private static final String SLASH = "/";

    private static final String BACKSLASH = "\\";

    private static final String WORKSPACE_FOLDER_NAME = "workspace";

    private static final String workspace_folder_path;

    private static final String[] user_dir_folder_keys, user_home_folder_keys, workspace_folder_keys;

    static {
        user_dir_folder_keys = regexFolderKeys("USER_DIR");
        user_home_folder_keys = regexFolderKeys("USER_HOME");
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
        /*
        String key1 = "(?i)\\$" + WORKSPACE_FOLDER_NAME;
        String key2 = "(?i)\\$\\{" + WORKSPACE_FOLDER_NAME + "\\}";
        return new String[]{key1, key2};
        **/
        return regexFolderKeys(WORKSPACE_FOLDER_NAME);
    }

    private static String[] regexFolderKeys(String folderName) {
        String key1 = "(?i)\\$" + folderName;
        String key2 = "(?i)\\$\\{" + folderName + "\\}";
        return new String[]{key1, key2};
    }

    public static File getUserDirFile(String... names) {
        return new File(getUserDirPath(names));
    }

    public static File getUserHomeFile(String... names) {
        return new File(getUserHomePath(names));
    }

    public static String getUserDirPath(String... names) {
        return USER_DIR + subfolderPath(names);
    }

    public static String getUserHomePath(String... names) {
        return USER_HOME + subfolderPath(names);
    }

    private static String subfolderPath(String... names) {
        return names == null || names.length == 0 ? "" : FILE_SEP + StringUtils.join(names, FILE_SEP);
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

    public static String fixSlashedPath(String path) {
        return slashedPath(fixPath(path));
    }

    public static String fixPath(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        String string = path.trim();
        string = string.replace(FILE_SEP, SLASH);
        string = replaceAll(string, user_dir_folder_keys, USER_DIR);
        string = replaceAll(string, user_home_folder_keys, USER_HOME);
        string = replaceAll(string, workspace_folder_keys, workspace_folder_path);
        string = string.replace(SLASH, FILE_SEP);
        return string;
    }

    private static String replaceAll(String string, String[] keys, String path) {
        if (StringUtils.isBlank(path)) {
            return string;
        }
        String replacement = path.replace(FILE_SEP, SLASH);
        for (String regex : keys) {
            string = string.replaceAll(regex, replacement);
        }
        return string;
    }

    public static String replaceSlashes(String path) {
        return StringUtils.isBlank(path) ? null : StringUtils.replaceChars(path.trim(), SLASH + BACKSLASH, FILE_SEP + FILE_SEP);
    }

    public static String slashedPath(String path) {
        return StringUtils.isBlank(path) ? null : StringUtils.replace(path.trim(), FILE_SEP, SLASH);
    }

    public static String packagePath(String pack) {
        return StringUtils.isBlank(pack) ? null : StringUtils.replace(pack.trim(), DOT, FILE_SEP);
    }

    public static String packageSlashedPath(String pack) {
        return StringUtils.isBlank(pack) ? null : StringUtils.replace(pack.trim(), DOT, SLASH);
    }

    public static String delimitedPath(File file) {
        return delimitedPath(file, null);
    }

    public static String delimitedPath(File file, char separator) {
        return delimitedPath(file, "" + separator);
    }

    public static String delimitedPath(File file, String separator) {
        return delimitedPath(file, separator, 0);
    }

    public static String delimitedPath(File file, char separator, int parts) {
        return delimitedPath(file, "" + separator, parts);
    }

    public static String delimitedPath(File file, String separator, int parts) {
        return file == null ? null : delimitedPath(file.getAbsolutePath(), separator, parts);
    }

    public static String delimitedPath(String path) {
        return delimitedPath(path, null);
    }

    public static String delimitedPath(String path, char separator) {
        return delimitedPath(path, "" + separator);
    }

    public static String delimitedPath(String path, String separator) {
        return delimitedPath(path, separator, 0);
    }

    public static String delimitedPath(String path, char separator, int parts) {
        return delimitedPath(path, "" + separator, parts);
    }

    public static String delimitedPath(String path, String separator, int parts) {
        String substringAfter = StringUtils.substringAfter(path, File.separator);
        if (StringUtils.isBlank(substringAfter)) {
            return null;
        }
        String[] split = StringUtils.split(substringAfter, File.separator);
        String[] array = (String[]) ArrayUtils.subarray(split, parts > 0 ? split.length - parts : 0, split.length);
        return StringUtils.join(array, StringUtils.defaultIfBlank(separator, File.separator));
    }

    public static String scanTextFile(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }
        File file = new File(path);
        if (file.isFile()) {
            try (Scanner scanner = new Scanner(file).useDelimiter("\\Z")) {
                return StringUtils.trimToNull(scanner.next()); // Read the entire content as a single token
            } catch (FileNotFoundException ex) {
                logger.warn(path + " is missing");
            }
        } else {
            logger.warn(path + " is missing or invalid");
        }
        return null;
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
            String sep = System.getProperty("path.separator");
            String[] pathnames = StringUtils.split(path, sep);
            for (String pathname : pathnames) {
                b &= exists(pathname);
            }
        }
        return b;
    }

    public static boolean isAbsolutePath(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.isAbsolute();
        }
        return b;
    }

    public static boolean isAbsolutePath(File file) {
        return file != null && file.isAbsolute();
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
        return (File file) -> isVisibleDirectory(file);
    }

    public static FileFilter nameContainsFilter(String string) {
        return (File file) -> isVisibleFile(file) && StringUtils.containsIgnoreCase(file.getName(), string);
    }

    public static FileFilter nameEndsWithFilter(String suffix) {
        return (File file) -> isVisibleFile(file) && StringUtils.endsWithIgnoreCase(file.getName(), suffix);
    }

    public static FileFilter nameStartsWithFilter(String prefix) {
        return (File file) -> isVisibleFile(file) && StringUtils.startsWithIgnoreCase(file.getName(), prefix);
    }

}
