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
public class Favoritos extends AbstractHelpWriter {

    public Favoritos(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String url = "/" + getWebProjectName() + directory + "entity/PaginaUsuario." + getWebPageFileExtension();
        String bid = b("Favoritos");
        String str = ""
            + "La página " + bid + " es una " + i("Tabla de Registro") + " especial "
            + "que trabaja con instancias de la entidad " + a(url, "Favoritos") + ". "
            + "Las filas de la tabla contienen hipervínculos que permiten abrir páginas de la aplicación. "
            + "Al hacer clic sobre el nombre de una página en una fila de la tabla, se abre la página correspondiente. "
            + BR + BR
            + "La página " + bid + " solo muestra los hipervínculos de páginas que el usuario ha agregado a su lista de páginas favoritas, "
            + "siempre y cuando esas páginas sigan estando autorizadas al usuario; las páginas agregadas, pero no autorizadas, "
            + "permanecen ocultas hasta que, eventualmente, el usuario obtenga nuevamente la autorización necesaria. "
            + BR + BR
            + "La página " + bid + " no permite agregar ni editar, pero si eliminar. "
            + "Para agregar páginas a su lista de páginas favoritas, utilice el botón "
            + ic("fa fa-star-o") + " de la " + i("Barra de Botones de Acción") + " cuando se encuentre en la página que desea agregar."
            + "";
        /**/
        return str;
    }

}
