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
package meta.proyecto.comun.en;

import adalid.core.*;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaUsuarioCorreo;
import meta.entidad.comun.operacion.basica.TransicionTareaUsuario;

/**
 * @author Jorge Campins
 */
public class TaskControl extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of ControlTareas's attributes">
        setLocalizedLabel(ENGLISH, "Task Control");
        setLocalizedLabel(SPANISH, "Control de Tareas");
//      setLocalizedShortDescription(ENGLISH, "Task Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Tareas");
        setLocalizedDescription(ENGLISH, ""
            + "Task Control consists of keeping track of the tasks that each user must perform. "
            + "By using restrictions, states and triggers, you can specify the operations (functions) that must be executed "
            + "when an instance of an entity class reaches a certain state. "
            + "The task controller evaluates the status of the instance after adding or updating it, and also after executing a function "
            + "business on the instance. "
            + "All final states of the transitions defined for the operation are evaluated. "
            + "If the entity reached a state for which there is a trigger, then the task controller proceeds to notify the users that should "
            + "execute the operation specified on the trigger. "
            + "The task controller sends a notification to all those users who \"can and should\" execute the operation. "
            + "When notifying, it is taken into account if the authorization is personalized, segmented or supervised. "
            + "When the user receives a notification, he can take responsibility for executing the task. "
            + "Automatically the task manager hides the notification from other users that \"can and should\" execute the task. "
            + "If after assuming a task, a user decides that he cannot execute it, then he can leave it. "
            + "Automatically the task manager returns to show the notification to all users who \"can and should \" execute the task. "
            + "In addition, with the corresponding authorization, a user can assign or relieve responsibility of executing a task to "
            + "another user; and cancel the execution of a task when it is not possible or no longer necessary to perform it. "
            + "The task controller also updates the condition of the task to \"executed\" when the corresponding operation is successfully executed."
            + "");
        setLocalizedDescription(SPANISH, ""
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
            + "");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(x010);
        System.out.println(x01010);
        System.out.println(x01020);
    }
    // </editor-fold>

    TareaUsuario x010;

    TareaUsuarioCorreo x01010;

    TransicionTareaUsuario x01020;

}
