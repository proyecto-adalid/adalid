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
package meta.proyecto.comun.help.es;

import static adalid.commons.util.MarkupUtils.*;

/**
 * @author Jorge Campins
 */
public class ControlTareas {

    public static String getHelpText() {
        String str = ""
            + "La gestión de tareas consiste en llevar el control de las tareas que cada usuario debe realizar. "
            + "Mediante el uso de restricciones, estados y disparadores, se pueden especificar las operaciones (funciones) que deben ejecutarse "
            + "cuando una instancia de una clase de entidad llega a un estado determinado. "
            + "El gestor de tareas evalúa el estado de la instancia después de agregarla o actualizarla, y también después de ejecutar una función "
            + "de negocio sobre la instancia. "
            + "Se evalúan todos los estados finales de las transiciones definidas para la operación. "
            + "Si la entidad alcanzó un estado para el cual existe un disparador, entonces el gestor procede a notificar a los usuarios que deben "
            + "ejecutar la operación especificada en el disparador. "
            + "El gestor envía una notificación a todos aquellos usuarios que \"pueden y deben\" ejecutar la operación. "
            + "Al notificar se toma en cuenta si la autorización es personalizada, segmentada o supervisada. "
            + "Cuando el usuario recibe una notificación puede asumir la responsabilidad de ejecutar la tarea. "
            + "Automáticamente el gestor de tareas oculta la notificación de los demás usuarios que \"pueden y deben\" ejecutar la tarea. "
            + "Si después de asumir una tarea, un usuario decide que no puede ejecutarla, entonces puede abandonarla. "
            + "Automáticamente el gestor de tareas vuelve a mostrar la notificación a todos los usuarios que \"pueden y deben\" ejecutar la tarea. "
            + "Además, si cuenta con la autorización correspondiente, un usuario podrá asignar o relevar la responsabilidad de ejecutar una tarea a "
            + "otro usuario; y cancelar la ejecución de una tarea cuando no sea posible o no sea necesario realizarla. "
            + "El gestor de tareas también actualiza automáticamente la condición de la tarea a \"ejecutada\" cuando se ejecuta exitosamente la "
            + "correspondiente operación."
            + BR + BR
            + "La gestión de tareas incluye las siguientes páginas de consulta: "
            + ul(
                "Consulta de Notificaciones de Tareas",
                "Consulta Detallada de Notificaciones de Tareas"
            )
            + "";
        /**/
        return str;
    }

}
