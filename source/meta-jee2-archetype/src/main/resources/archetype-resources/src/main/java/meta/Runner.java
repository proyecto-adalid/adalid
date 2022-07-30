#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta;

import adalid.util.*;

/**
 * Runner sirve para buscar, seleccionar y ejecutar un proyecto maestro o una clase utilitaria.
 * <p>
 * Para ejecutar las clases del paquete meta.util.linux, Runner utiliza xterm para ejecutar los scripts en una ventana de terminal estándar; si no
 * dispone de xterm, y no desea instalarlo, puede utilizar otro emulador, cambiando el valor de la propiedad process.builder.start.command.for.linux
 * del archivo bootstrapping.properties que se encuentra en el subdirectorio src/main/resources.
 *
 * @author ADALID meta-jee2-archetype
 */
@RunnableClass(false)
class Runner extends adalid.util.Runner {

    public static void main(String[] args) {
        run(Runner.class);
    }

}
