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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
class SysCmdCommand {

    private static final Logger logger = Logger.getLogger(SysCmdCommand.class);

    private static final String WIN_CLI = "cmd.exe";

    private static final File RUNTIME = SysCmd.singleRuntimeFile ? FilUtils.getUserHomeFile() : FilUtils.getUserHomeFile("runtime");

    private static final boolean WINDOWS = Utility.WINDOWS;

    String command;

    final List<String> commands;

    final List<String> env;

    File dir, run;

    FileFilter filter;

    Exception exception;

    SysCmdCommand(String command, List<String> env, File dir) {
        this.command = command;
        this.commands = new ArrayList<>();
        this.env = env != null ? env : new ArrayList<>();
        this.dir = dir;
        this.init();
    }

    String[] command() {
        return StringUtils.split(command, ' ');
    }

    String[] env() {
        if (env == null || env.isEmpty()) {
            return new String[0];
        }
        String[] array = new String[env.size()];
        return env.toArray(array);
    }

    private void init() {
        File f1 = null;
        SysCmd os = null;
        if (command == null || dir == null) {
        } else if (StringUtils.startsWithIgnoreCase(command, WIN_CLI)) {
        } else if (StringUtils.endsWithIgnoreCase(command, ".sh")) {
            f1 = new File(command);
            os = WINDOWS ? SysCmd.WSL : SysCmd.Linux;
            filter = FilUtils.nameEndsWithFilter(".sh");
        } else if (WINDOWS && StringUtils.endsWithIgnoreCase(command, ".bat")) {
            f1 = new File(command);
            os = SysCmd.Windows;
            filter = FilUtils.nameEndsWithFilter(".bat");
        }
        if (f1 == null || os == null) {
            return;
        }
        String pdqName = f1.getName();
        String pdqPath = f1.getAbsolutePath();
        String dirPath = f1.isAbsolute() ? f1.getParent() : dir.getAbsolutePath();
        String comando = f1.isAbsolute() ? pdqPath : dirPath + File.separator + pdqName;
        File f2 = cf(comando);
        if (f2 == null) {
            return;
        }
        addCommands(os, pdqName, dirPath, comando);
        run = write(f2, pdqName);
        if (run != null) {
            String pattern = StringUtils.defaultIfBlank(os.commandPattern, os.defaultPattern);
            command = pattern.replace("$SCRIPT", run.getName());
            dir = run.getParentFile();
        }
    }

    private void addCommands(SysCmd os, String pdqName, String dirPath, String comando) {
        switch (os) {
            case Linux:
                addCommandsForLinux(pdqName, dirPath, comando);
                break;
            case Windows:
                addCommandsForWindows(pdqName, dirPath, comando);
                break;
            case WSL:
                addCommandsForLinux(pdqName, wslPath(dirPath), wslPath(comando));
                break;
        }
    }

    private void addCommandsForLinux(String pdqName, String dirPath, String comando) {
        String hyphens = StringUtils.repeat("-", comando.length());
        commands.add("#!/bin/bash");
        commands.add("echo " + hyphens);
        commands.add("echo " + comando);
        commands.add("echo " + hyphens);
        commands.add("cd " + dirPath);
        commands.add("source " + pdqName);
        commands.add("read -p \"Press any key to continue . . . \" -n 1");
        commands.add("exit");
    }

    private void addCommandsForWindows(String pdqName, String dirPath, String comando) {
        String hyphens = StringUtils.repeat("-", comando.length());
        commands.add("@echo " + hyphens);
        commands.add("@echo " + comando);
        commands.add("@echo " + hyphens);
        commands.add("@set first_bat=\"%~f0\"");
        commands.add("@cd/d " + dirPath);
        commands.add("@call " + pdqName);
        commands.add("@if defined first_bat pause");
        commands.add("@exit");
    }

    private String wslPath(String path) {
        String after = StringUtils.substringAfter(path, ":");
        String before = StringUtils.substringBefore(path, ":");
        String wslPath = StringUtils.isBlank(after) ? before : "/mnt/" + before.toLowerCase() + after;
        return wslPath.replace('\\', '/');
    }

    private File cf(String comando) {
        File cf = new File(comando);
        if (!cf.isFile()) {
            exception = new FileNotFoundException(comando);
            logger.error(exception);
            return null;
        }
        return cf;
    }

    private File write(File cf, String batName) {
        File file = runnerFile(cf, batName);
        Path path = Paths.get(file.getAbsolutePath());
        StandardOpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE};
        try {
            Files.createDirectories(path.getParent());
//          Files.write(path, commands, options);
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, options);) {
                for (final String line : commands) {
                    writer.write(line);
                    writer.write('\n');
                }
                writer.flush();
            }
            return file;
        } catch (IOException ex) {
            exception = ex;
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return null;
    }

    private File runnerFile(File cf, String batName) {
        if (SysCmd.singleRuntimeFile) {
            String x1 = StringUtils.substringAfterLast(batName, ".");
            String x2 = x1.isBlank() ? x1 : "." + x1;
            return new File(RUNTIME, SysCmdRunner.class.getCanonicalName() + x2);
        } else {
            String dp = delimitedPath(cf.getParentFile());
            return new File(RUNTIME, dp + batName);
        }
    }

    private String delimitedPath(File dir) {
        String root = PropertiesHandler.getRootFolder().getAbsolutePath();
        String path = dir.getAbsolutePath();
        if (path.startsWith(root)) {
            path = path.substring(root.length());
        }
        char separator = '@';
        String dp = FilUtils.delimitedPath(path, separator);
        return dp == null ? "" : dp + separator;
    }

}
