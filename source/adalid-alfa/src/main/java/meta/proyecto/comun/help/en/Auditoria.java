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
public class Auditoria {

    public static String getHelpText() {
        String str = ""
            + "The Audit module keeps a record of the execution of all functions, processes and reports of the application and of all "
            + "the files uploaded to the server. "
            + BR + BR
            + "The Audit module includes processing consoles and query pages for the following entity classes: "
            + ul(
                "Attached Files",
                "Function Audit Trails",
                "Function Parameter Audit Trails",
                "Report Audit Trails",
                "Process Audit Trails"
            )
            + "Access to audit logs is personalized and, at the same time, segmented by performing user. "
            + "This means that unless you receive another authorization, you will only be able to see the audit trails of yourself and your supervisees. "
            + "";
        /**/
        return str;
    }

}
