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
package adalid.jee2.help.en;

import adalid.core.Project;
import adalid.jee2.help.AbstractHelpWriter;

import static adalid.commons.util.MarkupUtils.*;

/**
 * @author Jorge Campins
 */
public class Personalizacion extends AbstractHelpWriter {

    public Personalizacion(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String url = "/" + getWebProjectName() + directory + "entity/PaginaEspecial." + getWebPageFileExtension();
        String bid = b("Settings");
        String str = ""
            + "The page " + bid + " is an special " + i("Processing Console") + " "
            + "that allows the user to perform the following functions:"
            + ul(
                b("Change settings") + ": Change your personal settings, that is, your start page, GUI theme, etc.",
                b("Change password") + ": Change your password to access the application.",
                b("Upload portrait") + ": Upload an image file (usually your portrait) "
                + "to display as button image in the " + i("Page Header") + ".",
                b("Crop portrait") + ": Crop the previously loaded image.",
                b("Take portrait") + ": Take a photo (usually your portrait) using your computer's camera,"
                + "to display as button image in the " + i("Page Header") + "."
            )
            + h3("Change settings")
            + "Using the function " + b("Change settings") + " the user can modify his personal settings. "
            + "The parameters of this function are:"
            + ul(
                b("start page") + ": specifies the page that opens when the user starts a new session. "
                + "Possible values are:",
                ul(
                    u("Default") + ": opens a page selected by the application. "
                    + "If the user has notifications of pending tasks, it would be the page " + b("Tasks") + "; "
                    + "otherwise page " + b("Start Menu") + ".",
                    u("Menu") + ": opens the page " + b("Start Menu") + ".",
                    u("Favorites") + ": opens the page " + b("Favorites") + ".",
                    u("Tasks") + ": opens the page " + b("Tasks") + ".",
                    u("Menu page") + ": opens the page specified in the parameter " + b("menu page") + ". "
                    + "Usually this would be a generated page that can also be opened from the " + b("Start Menu") + ".",
                    u("Other page") + ": opens the page specified in the parameter " + b("other page") + ". "
                    + "Usually this would be a special (non-generated) page that cannot be opened from the " + b("Start Menu") + ". "
                    + "Special pages are defined using the registration page of " + a(url, "Special Pages") + " "
                    + "from the " + b("Configuration") + " menu of the " + b("Access Control") + " module."
                ),
                b("theme") + ": specifies the theme of the interface. "
                + "A theme is a series of graphic elements that modify the external appearance of the application. "
                + "The list of themes includes at least all the " + i("community") + " themes from the component library (PrimeFaces), "
                + "and might include some " + i("premium") + " themes as well. "
                + "This parameter is only available when the application was generated with the option to allow changing the theme.",
                b("rows per page") + ": specifies the initial number of rows per page in the tabular query and/or registration pages.",
                b("inline help") + ": specifies whether or not the field descriptions should be displayed "
                + "in the detailed query and/or registration pages and in the processing consoles.",
                b("confirm discarding") + ": specifies whether or not to confirm when discarding changes on the registration pages.",
                b("confirm end session") + ": specifies whether or not to confirm the end of the session, "
                + "by clicking the button " + ic("fa fa-sign-out") + " on the toolbar."
            )
            + h3("Change password")
            + "The function " + b("Change password") + " is only available when the application uses the JDBC authentication mechanism, "
            + "which uses the credentials stored in the application's database. "
            + "Such an authentication mechanism is very useful for development and test environments. "
            + "In production environments another authentication mechanism is normally used, such as LDAP."
            + BR + BR
            + "To change your password, type your current password in the field " + b("password") + ", "
            + "and then type your new password twice, in the fields " + b("new password") + ". "
            + "Finally click the button " + b("Process") + " on the toolbar."
            + "";
        /**/
        return str;
    }

}
