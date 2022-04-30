#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.linux;

/**
 * RunZipRelease sirve para ejecutar el script release.sh, el cual sirve para generar el zip de instalación.
 * <p>
 * RunZipRelease utiliza xterm para ejecutar el script en una ventana de terminal estándar; si no dispone de xterm, y no desea instalarlo, puede
 * utilizar otro emulador, cambiando el valor de la propiedad process.builder.start.command.for.linux del archivo bootstrapping.properties que se
 * encuentra en el subdirectorio src/main/resources.
 *
 * @author ADALID meta-jee2-archetype
 */
public class RunZipRelease extends adalid.util.io.SysCmdRunner {

    /**
     * DIR almacena la ruta del directorio de scripts de desarrollo del proyecto, relativa al directorio raíz del workspace.
     * <p>
     * El campo PROJECT_BASE contiene inicialmente el nombre predeterminado del directorio base del proyecto Maestro101; posteriormente el nombre del
     * directorio base del último proyecto maestro ejecutado. El directorio base se crea en el directorio raíz del workspace.
     */
    private static final String DIR = ${package}.meta.util.Aid.PROJECT_BASE + "/source/development/resources/scripts/windows";

    /**
     * SCRIPT almacena el nombre del script para generar el zip de instalación.
     */
    private static final String SCRIPT = "release.sh";

    public static void main(String[] args) {
        executeSystemCommand(DIR, SCRIPT);
    }

}
