#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.windows;

import adalid.util.*;

/**
 * RunHomeSetup sirve para ejecutar el script home-setup.bat, el cual sirve para inicializar el directorio HOME.
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
@RunnableClass
public class RunHomeSetup extends adalid.util.io.SysCmdRunner {

    /**
     * DIR almacena el nombre del directorio base del proyecto.
     * <p>
     * El campo PROJECT_BASE contiene inicialmente el nombre predeterminado del directorio base del proyecto Maestro101; posteriormente el nombre del
     * directorio base del último proyecto maestro ejecutado. El directorio base se crea en el directorio raíz del workspace.
     */
    private static final String DIR = ${package}.meta.util.Aid.PROJECT_BASE;

    /**
     * SCRIPT almacena el nombre del script para inicializar el directorio HOME.
     */
    private static final String SCRIPT = "home-setup.bat";

    public static void main(String[] args) {
        executeSystemCommand(DIR, SCRIPT);
    }

}
