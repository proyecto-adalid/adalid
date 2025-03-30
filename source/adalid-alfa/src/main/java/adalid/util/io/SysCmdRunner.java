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

import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SysCmdRunner extends Utility {

    private static final Logger logger = Logger.getLogger(SysCmdRunner.class);

    private static final String MORE_INTERACTIVE = SysCmd.class.getSimpleName() + "="; // unset environment variable

    private static final String LESS_INTERACTIVE = MORE_INTERACTIVE + SysCmd.class.getCanonicalName(); // set environment variable

    private static final String RELEASE = "/resources/scripts/windows/release/";

    public static void main(String[] args) {
        executeSystemCommand();
    }

    public static Process executeSystemCommand() {
        return executeSystemCommand(null);
    }

    public static Process executeSystemCommand(String folder) {
        return executeSystemCommand(folder, null);
    }

    public static Process executeSystemCommand(String folder, String command) {
        List<String> env = null;
        return executeSystemCommand(folder, command, env);
    }

    public static Process executeSystemCommand(String folder, String command, String... vars) {
        List<String> env = vars == null || vars.length == 0 ? null : Arrays.asList(vars);
        return executeSystemCommand(folder, command, env);
    }

    public static Process executeSystemCommand(String folder, String command, List<String> env) {
        File dir = directoryOf(folder);
        return executeSystemCommand(dir, commandOf(dir, command), env);
    }

    public static void log(Process process) {
        if (process != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info(line);
                }
            } catch (IOException ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        }
    }

    public static void send(Process process, String string) {
        if (process != null) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(string);
            } catch (IOException ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        }
    }

    private static File directoryOf(String folder) {
        File rootFolder = PropertiesHandler.getRootFolder();
        logger.info("root-folder=" + rootFolder);
        if (StringUtils.isBlank(folder)) {
            folder = rootFolder.getAbsolutePath();
        }
        File dir = new File(folder);
        if (!dir.isAbsolute()) {
            dir = new File(rootFolder, folder);
        }
        if (dir.isDirectory()) {
            logger.info("working-dir=" + dir);
        } else {
            logger.error(dir + " does not exist or it is not a directory; using the root folder instead");
            dir = rootFolder;
            logger.warn("working-dir=" + rootFolder);
        }
        return dir;
    }

    private static String commandOf(File dir, String command) {
        if (StringUtils.isBlank(command)) {
            List<FileNameExtensionFilter> filters = new ArrayList<>();
            if (WINDOWS) {
                filters.add(new FileNameExtensionFilter("*.bat", "bat"));
            }
            filters.add(new FileNameExtensionFilter("*.sh", "sh"));
            command = chooseFile(dir.getAbsolutePath(), filters);
            if (StringUtils.isBlank(command)) {
                logger.warn("no file selected; execution cancelled");
                return null;
            }
        }
        return command;
    }

    private static Process executeSystemCommand(File dir, String command, List<String> env) {
        if (dir == null || command == null) {
            return null;
        }
        SysCmdCommand scc = new SysCmdCommand(command, env, dir);
        if (scc.exception != null) {
            logger.warn("execution aborted due to previous errors");
            return null;
        }
        if (isNotReleaseScript(command)) {
            addLessInteractiveEnvironmentVariable(scc.env);
        }
        try {
            log(scc);
            Process process = start(scc);
            logger.warn("a new window was opened to execute the command or batch script");
            deleteOldFiles(scc);
            updateProjectBuilderDictionary(SysCmdRunner.class);
            return process;
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return null;
    }

    private static Process start(SysCmdCommand scc) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(scc.command());
        if (scc.env != null && !scc.env.isEmpty()) {
            Map<String, String> environment = processBuilder.environment();
            for (String var : scc.env) {
                String[] tokens = StringUtils.split(var, '=');
                if (tokens != null && tokens.length == 2) {
                    environment.put(tokens[0], tokens[1]);
                }
            }
        }
        processBuilder.directory(scc.dir);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    private static void log(SysCmdCommand scc) {
        logger.info("runtime-cmd=" + scc.command);
        logger.info("runtime-env=" + Arrays.toString(scc.env()));
        logger.info("runtime-dir=" + scc.dir);
        if (scc.run != null) {
            logger.info("runtime-bat=" + scc.run);
            for (String command : scc.commands) {
                if (command.startsWith("@echo") || command.startsWith("echo")) {
                    continue;
                }
                logger.info(command);
            }
        }
    }

    private static int deleteOldFiles(SysCmdCommand scc) {
        if (SysCmd.singleRuntimeFile) {
            return 0;
        }
        File[] batList = batFileList(scc);
        if (batList == null || batList.length < 100) {
            return 0;
        }
        Timestamp someTimeAgo = TimeUtils.addTimestamp(TimeUtils.actualTimestamp(), -24, 'h');
        FileTime someFileTimeAgo = FileTime.fromMillis(someTimeAgo.getTime());
        logger.info("runtime-dir contains " + batList.length + " script files");
        logger.info("script files in runtime-dir last modified before " + someTimeAgo + " will be deleted");
        int deleted = 0;
        String runName = scc.run.getName();
        String fileName;
        Path filePath;
        BasicFileAttributes fileAttributes;
        FileTime /*creationTime, lastAccessed,*/ lastModified;
        for (File file : batList) {
            fileName = file.getName();
            if (StringUtils.equalsIgnoreCase(fileName, runName)) {
                continue;
            }
            try {
                filePath = Paths.get(file.getAbsolutePath());
                fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
//              creationTime = fileAttributes.creationTime();
//              lastAccessed = fileAttributes.lastAccessTime();
                lastModified = fileAttributes.lastModifiedTime();
                if (someFileTimeAgo.compareTo(lastModified) > 0) {
                    FileUtils.deleteQuietly(file);
                    deleted++;
                }
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
        logger.info(deleted == 1 ? "Only one file was deleted" : deleted + " files were deleted");
        return deleted;
    }

    private static File[] batFileList(SysCmdCommand scc) {
        File dir = scc == null || scc.run == null ? null : scc.run.getParentFile();
        FileFilter filter = scc == null ? null : scc.filter;
        return dir == null || filter == null ? null : dir.listFiles(filter);
    }

    private static boolean isNotReleaseScript(String command) {
        return !isReleaseScript(command);
    }

    private static boolean isReleaseScript(String command) {
        return StringUtils.containsIgnoreCase(command.replace('\\', '/'), RELEASE);
    }

    private static boolean addLessInteractiveEnvironmentVariable(List<String> env) {
        if (env == null) {
            return false;
        }
        for (String var : env) {
            if (StringUtils.startsWithIgnoreCase(var, MORE_INTERACTIVE)) {
                return false;
            }
        }
        env.add(LESS_INTERACTIVE);
        return true;
    }

}
