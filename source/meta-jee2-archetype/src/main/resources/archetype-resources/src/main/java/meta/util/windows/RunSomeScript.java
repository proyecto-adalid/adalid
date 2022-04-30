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

/**
 * RunSomeScript sirve para buscar, seleccionar y ejecutar un script (archivo .bat).
 *
 * @author ADALID meta-jee2-archetype
 */
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
