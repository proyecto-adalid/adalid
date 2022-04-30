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
 * RunSetupScript muestra el diálogo para buscar y seleccionar un script (archivo .sh) de instalación y ejecuta el script seleccionado.
 * <p>
 * RunSetupScript utiliza xterm para ejecutar el script en una ventana de terminal estándar; si no dispone de xterm, y no desea instalarlo, puede
 * utilizar otro emulador, cambiando el valor de la propiedad process.builder.start.command.for.linux del archivo bootstrapping.properties que se
 * encuentra en el subdirectorio src/main/resources.
 *
 * @author ADALID meta-jee2-archetype
 */
public class RunSetupScript extends adalid.util.io.SysCmdRunner {

    /**
     * DIR almacena la ruta del directorio de scripts de instalación del proyecto, relativa al directorio raíz del workspace.
     * <p>
     * El campo PROJECT_BASE contiene inicialmente el nombre predeterminado del directorio base del proyecto Maestro101; posteriormente el nombre del
     * directorio base del último proyecto maestro ejecutado. El directorio base se crea en el directorio raíz del workspace.
     */
    private static final String DIR = ${package}.meta.util.Aid.PROJECT_BASE + "/source/management/resources/scripts/windows/release";

    public static void main(String[] args) {
        executeSystemCommand(DIR);
    }

}
