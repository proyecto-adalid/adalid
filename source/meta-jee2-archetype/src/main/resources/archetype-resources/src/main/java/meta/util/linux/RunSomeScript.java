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

import adalid.util.*;

/**
 * RunSomeScript sirve para buscar, seleccionar y ejecutar un script (archivo .sh).
 * <p>
 * RunSomeScript utiliza xterm para ejecutar el script en una ventana de terminal estándar; si no dispone de xterm, y no desea instalarlo, puede
 * utilizar otro emulador, cambiando el valor de la propiedad process.builder.start.command.for.linux del archivo bootstrapping.properties que se
 * encuentra en el subdirectorio src/main/resources.
 *
 * @author ADALID meta-jee2-archetype
 */
@RunnableClass(false)
public class RunSomeScript extends adalid.util.io.SysCmdRunner {

    /**
     * DIR almacena la ruta del directorio que contiene el script a ejecutar. La ruta puede ser absoluta o relativa al directorio raíz del workspace.
     * Si DIR es nulo, se utiliza el directorio raíz del workspace.
     */
    private static final String DIR = null;

    /**
     * SCRIPT almacena el nombre del script a ejecutar. Si es nulo, se muestra el diálogo para buscar y seleccionar el script a ejecutar, comenzando
     * la búsqueda en DIR.
     */
    private static final String SCRIPT = null;

    public static void main(String[] args) {
        executeSystemCommand(DIR, SCRIPT);
    }

}
