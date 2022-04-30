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

/**
 * @author Jorge Campins
 */
enum SysCmd {

    Linux("process.builder.start.command.for.linux", "xterm -e bash $SCRIPT"),
    Windows("process.builder.start.command.for.windows", "cmd.exe /c start $SCRIPT"),
    WSL("process.builder.start.command.for.windows.subsystem.for.linux", "cmd.exe /c start bash $SCRIPT");

    final String commandPattern, defaultPattern;

    private SysCmd(String key, String pattern) {
        commandPattern = PropertiesHandler.getBootstrapping().getString(key);
        defaultPattern = pattern;
    }

    static final boolean singleRuntimeFile = !adalid.util.Utility.WINDOWS;

}
