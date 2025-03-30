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
public class Favoritos extends AbstractHelpWriter {

    public Favoritos(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String url = "/" + getWebProjectName() + directory + "entity/PaginaUsuario." + getWebPageFileExtension();
        String bid = b("Favorites");
        String str = ""
            + "The page " + bid + " is an special " + i("Registration Table") + " "
            + "that works with instances of the entity " + a(url, "Favorites") + ". "
            + "The rows in the table contain hyperlinks that allow you to open application pages. "
            + "Clicking on the name of a page in a row of the table opens the corresponding page. "
            + BR + BR
            + "The page " + bid + " only shows the hyperlinks of pages that the user has added to his favorite page list, "
            + "as long as those pages are still authorized to the user; the pages added but not authorized "
            + "remain hidden until, eventually, the user obtains the necessary authorization again. "
            + BR + BR
            + "The page " + bid + " does not allow adding or editing, but deleting. "
            + "To add pages to your favorite page list use the "
            + ic("fa fa-star-o") + " button on the toolbar when you are on the page you want to add."
            + "";
        /**/
        return str;
    }

}
