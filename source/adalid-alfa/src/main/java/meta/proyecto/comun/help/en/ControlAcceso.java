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
public class ControlAcceso {

    public static String getHelpText() {
        String str = ""
            + "The access control module allows you to establish the permissions of each user to execute the functions of the application. "
            + "The access control module is based on the access control model by roles, where functions (operations) and "
            + "users are assigned to roles, and a user's permissions are the sum of the permissions of the roles the user is assigned to. "
            + "The model is extended to incorporate read and/or write authorizations at the field level and also the schemas of "
            + "personalization, segmentation and supervision. "
            + "Personalization is defining an owner for an entity class and then granting permissions to execute a function "
            + "only on instances of the class owned by the user. "
            + "Segmentation consists of defining sets of segments for an entity class and then granting permissions to execute a "
            + "function only on instances of the class belonging to the selected set. "
            + "An entity class can be personalized and segmented at the same time, and when segmenting using the owner you get the "
            + "supervision scheme. "
            + "Using that schema, a user can be authorized not only to instances of the class that belong to him, but also to those that "
            + "belong to the users he or she supervises, directly and/or indirectly. "
            + "On the other hand, when assigning a function to a role you can specify whether or not the function is a task of the users assigned to the role; "
            + "this allows to differentiate who can and who \"can and should\" execute a function. "
            + "The module provides an authentication mechanism, very useful for development and test environments. "
            + "In the production environment a standard authentication mechanism should be used, such as LDAP. "
            + BR + BR
            + "The Access Control module includes processing consoles and query and registration pages for the following classes of entities: "
            + ul(
                "Segment Sets",
                "Elements of Segment Sets",
                "Roles",
                "Filters by Role",
                "Functions by Role",
                "Parameters of Functions by Role",
                "Favorites by Role",
                "Users by Role",
                "Users",
                "Roles per User"
            )
            + "";
        /**/
        return str;
    }

}
