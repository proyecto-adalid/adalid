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
public class Menu extends AbstractHelpWriter {

    public Menu(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String url = "/" + getWebProjectName() + directory + getAlias() + "." + getWebPageFileExtension();
        String bid = b("Start Menu");
        String str = ""
            + "The page " + bid + " is an special " + i("Query Tree") + ". "
            + "The top nodes of the tree correspond to the " + a(url, "application modules") + ". "
            + "In the node of each module are the branches that correspond to the packages of the module: Inquiry, Processing and Registration. "
            + "In the node of each package are the leaves of the tree (terminal nodes) that correspond to the "
            + "pages of the package operation entities; and, if they exist, there could also be a branch for "
            + "the leaves that correspond to the pages of the package configuration entities. "
            + "The leaves of the tree are hyperlinks that allow you to open pages of the application. "
            + BR + BR
            + "Clicking on a collapsed node of the tree expands it. "
            + "Clicking on an expanded node of the tree collapses it. "
            + "Clicking on a leaf of the tree opens the corresponding page. "
            + BR + BR
            + "The page " + bid + " only shows the hyperlinks of pages authorized to the user. "
            + "";
        /**/
        return str;
    }

}
