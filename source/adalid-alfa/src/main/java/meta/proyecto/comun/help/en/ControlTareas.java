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
package meta.proyecto.comun.help.en;

import static adalid.commons.util.MarkupUtils.*;

/**
 * @author Jorge Campins
 */
public class ControlTareas {

    public static String getHelpText() {
        String str = ""
            + "Task Management consists of keeping track of the tasks that each user must perform. "
            + "By using constraints, states and triggers, you can specify the operations (functions) that should be executed "
            + "when an instance of an entity class reaches a certain state. "
            + "The task manager evaluates the state of the instance after adding or updating it, and also after executing a function "
            + "of business on the instance. "
            + "All final states of the transitions defined for the operation are evaluated. "
            + "If the entity has reached a state for which a trigger exists, then the manager proceeds to notify users that should "
            + "execute the operation specified in the trigger. "
            + "The manager sends a notification to all users who \"can and should\" execute the operation. "
            + "When notifying, it is taken into account if the authorization is personalized, segmented or supervised. "
            + "When the user receives a notification he can take responsibility for running the task. "
            + "The task manager automatically hides the notification from other users that \"can and should\" run the task. "
            + "If after assuming a task, a user decides that he can't execute it, then he can abandon it. "
            + "The task manager automatically redisplays the notification to all users who \"can and should\" run the task. "
            + "In addition, if authorized, a user may assign or relieve responsibility for running a task to or from "
            + "another user; and cancel the execution of a task when it is not possible or necessary to perform it. "
            + "The task manager also automatically updates the task status to \"executed\" when the "
            + "corresponding operation is performed."
            + BR + BR
            + "Task management includes the following query pages: "
            + ul(
                "Task Notifications",
                "Detailed Task Notifications"
            )
            + "";
        /**/
        return str;
    }

}
