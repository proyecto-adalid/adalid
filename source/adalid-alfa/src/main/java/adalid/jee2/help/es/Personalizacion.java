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
public class Personalizacion extends AbstractHelpWriter {

    public Personalizacion(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String url = "/" + getWebProjectName() + directory + "entity/PaginaEspecial." + getWebPageFileExtension();
        String bid = b("Personalización");
        String str = ""
            + "La página " + bid + " es una " + i("Consola de Procesamiento") + " especial "
            + "que permite al usuario ejecutar las siguientes funciones:"
            + ul(
                b("Cambiar configuración") + ": Cambiar su configuración personal, es decir, su página de inicio, tema de la interfaz gráfica, etc.",
                b("Cambiar contraseña") + ": Cambiar su contraseña para acceder a la aplicación.",
                b("Cargar retrato") + ": Cargar un archivo de imagen (usualmente su retrato) "
                + "para mostrar como imagen del botón en el " + i("Encabezado de Página") + ".",
                b("Recortar retrato") + ": Recortar la imagen previamente cargada.",
                b("Tomar retrato") + ": Tomar una fotografía (usualmente su retrato) utilizando la cámara de su computador, "
                + "para mostrar como imagen del botón en el " + i("Encabezado de Página") + "."
            )
            + h3("Cambiar configuración")
            + "Mediante la función " + b("Cambiar configuración") + " el usuario puede modificar su configuración personal. "
            + "Los parámetros de esta función son:"
            + ul(
                b("página de inicio") + ": especifica la página que se abre cuando el usuario inicia una nueva sesión de trabajo. "
                + "Los valores posibles son:",
                ul(
                    u("Predeterminada") + ": abre una página seleccionada por la aplicación. "
                    + "Si el usuario tiene notificaciones de tareas pendientes, sería la página " + b("Consulta de Tareas") + "; "
                    + "de lo contrario, la página " + b("Menú de Inicio") + ".",
                    u("Menú") + ": abre la página " + b("Menú de Inicio") + ".",
                    u("Favoritos") + ": abre la página " + b("Favoritos") + ".",
                    u("Tareas") + ": abre la página " + b("Consulta de Tareas") + ".",
                    u("Página del menú") + ": abre la página que se especifica en el parámetro " + b("página del menú") + ". "
                    + "Por lo general, esta sería una página generada que también se puede abrir desde el " + b("Menú de Inicio") + ".",
                    u("Otra página") + ": abre la página que se especifica en el parámetro " + b("otra página") + ". "
                    + "Por lo general, esta sería una página especial (no generada), que no se puede abrir desde el " + b("Menú de Inicio") + ". "
                    + "Las páginas especiales se definen utilizando la página de registro de " + a(url, "Páginas Especiales") + " "
                    + "del menú de " + b("Configuración") + " del módulo " + b("Control de Acceso") + "."
                ),
                b("tema") + ": especifica el tema de la interfaz. "
                + "Un tema es una serie de elementos gráficos que modifican la apariencia externa de la aplicación. "
                + "La lista de temas incluye al menos todos los temas " + i("comunitarios") + " de la biblioteca de componentes (PrimeFaces), "
                + "y podría incluir también algunos temas " + i("premium") + ". "
                + "Este parámetro solo está disponible cuando la aplicación fue generada con la opción de permitir cambiar el tema.",
                b("filas por página") + ": especifica el número inicial de filas por página en las páginas de consulta y/o registro tabular.",
                b("ayuda en línea") + ": especifica si se deben mostrar, o no, las descripciones de los campos "
                + "en las páginas de consulta y/o registro detallado y en las consolas de procesamiento.",
                b("confirmar al descartar") + ": especifica si se debe confirmar, o no, al descartar los cambios en las páginas de registro.",
                b("confirmar al finalizar la sesión") + ": especifica si se debe confirmar, o no, la finalización de la sesión de trabajo, "
                + "al hacer clic en el botón " + ic("fa fa-sign-out") + " de la " + i("Barra de Botones de Acción") + "."
            )
            + h3("Cambiar contraseña")
            + "La función " + b("Cambiar contraseña") + " solo está disponible cuando la aplicación usa el mecanismo de autenticación JDBC, "
            + "el cual utiliza las credenciales almacenadas en la base de datos de la aplicación. "
            + "Tal mecanismo de autenticación es muy útil para ambientes de desarrollo y pruebas. "
            + "En ambientes de producción normalmente se utiliza otro mecanismo de autenticación, como LDAP."
            + BR + BR
            + "Para cambiar su contraseña, escriba su contraseña actual en el campo " + b("contraseña") + ", "
            + "y luego escriba su nueva contraseña dos veces, en los campos " + b("nueva contraseña") + ". "
            + "Finalmente haga clic en el botón " + b("Procesar") + " de la " + i("Barra de Botones de Acción") + "."
            + "";
        /**/
        return str;
    }

}
