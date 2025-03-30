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
package adalid.jee2.help.es;

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
        String bid = b("Menú de Inicio");
        String str = ""
            + "La página " + bid + " es un " + i("Árbol de Consulta") + " especial. "
            + "Los nodos superiores del árbol corresponden a los " + a(url, "módulos de la aplicación") + ". "
            + "En el nodo de cada módulo están las ramas que corresponden a los paquetes del módulo: Consulta, Procesamiento y Registro. "
            + "En el nodo de cada paquete se encuentran las hojas del árbol (nodos terminales) que corresponden a las "
            + "páginas de las entidades de operación del paquete; y, si existen, también podría haber una rama para "
            + "las hojas que corresponden a las páginas de las entidades de configuración del paquete. "
            + "Las hojas del árbol son hipervínculos que permiten abrir páginas de la aplicación. "
            + BR + BR
            + "Al hacer clic sobre un nodo contraído del árbol, éste se expande. "
            + "Al hacer clic sobre un nodo expandido del árbol, éste se contrae. "
            + "Al hacer clic sobre una hoja del árbol, se abre la página correspondiente. "
            + BR + BR
            + "La página " + bid + " solo muestra los hipervínculos de páginas autorizadas al usuario. "
            + "";
        /**/
        return str;
    }

}
