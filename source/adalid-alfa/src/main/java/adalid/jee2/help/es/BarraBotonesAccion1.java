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
public class BarraBotonesAccion1 extends AbstractHelpWriter {

    public BarraBotonesAccion1(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String str = ""
            + h3("Páginas para gestión de entidades")
            + "Cada entidad de la aplicación puede tener varios tipos de páginas de consulta y registro. "
            + "Éstos pueden ser cualquier combinación de las modalidades, formatos y presentaciones disponibles. "
            + "Las páginas de consulta son páginas que trabajan en modalidad " + b("Solo Lectura") + "; "
            + "las páginas de registro, en modalidad " + b("Lectura y Escritura") + ". "
            + "Estas últimas, además de consultar, también permiten agregar, editar y eliminar. "
            + BR + BR
            + "Hay dos formatos disponibles, tanto para páginas de consulta como de registro. Los formatos disponibles son: "
            + ul(
                b("Independiente") + ": páginas que muestran información de todos los registros.",
                b("Maestro/Detalle") + ": páginas que muestran información solo de los registros (detalles) relacionados con otro registro (maestro) previamente seleccionado."
            )
            + "Hay tres presentaciones disponibles, tanto para páginas de consulta como de registro. Las presentaciones disponibles son: "
            + ul(
                b("Tabular") + ": páginas que muestran la información de varios registros a la vez, mediante una tabla. "
                + "Pueden ser páginas de formato " + b("Independiente") + " o " + b("Maestro/Detalle") + ".",
                b("Árbol") + ": páginas que muestran los registros de manera jerárquica. "
                + "Son páginas de formato " + b("Independiente") + ".",
                b("Forma") + ": páginas que muestran la información de un solo registro a la vez, mediante un formulario columnar. "
                + "Pueden ser páginas de formato " + b("Independiente") + " o " + b("Maestro/Detalle") + ", con o sin pestañas, con o sin asistente."
            )
            + "Adicionalmente, cada entidad también puede tener una página para la ejecución de procesos de negocio y otros procesos, "
            + "tales como generación de informes, carga y descarga de archivos, etc. "
            + "Estas páginas son conocidas como " + b("Consolas de Procesamiento") + "."
            + h3("Barra de Botones de Acción")
            + "Los botones que aparecen en la barra varían dependiendo del tipo de la página actual; "
            + "aquellos botones que no puedan ser utilizados en un momento dado estarán inhabilitados. "
            + "La siguiente lista incluye todos los botones que pueden aparecer en la barra:"
            + ul(
                ic("fa fa-home") + " Abrir la página de inicio la aplicación. "
                + "Cada usuario puede elegir su página de inicio, mediante la función " + b("Cambiar configuración") + " de la página " + b("Personalización") + ". "
                + "Por lo general, si el usuario tiene notificaciones de tareas pendientes, la página de inicio predeterminada sería " + b("Consulta de Tareas") + "; "
                + "de lo contrario, sería " + b("Menú de Inicio") + ".",
                ic("fa fa-star") + " Abrir la página " + b("Favoritos") + ". "
                + "En esa página se despliega la lista de hipervínculos de lugares favoritos del usuario; "
                + "esto es, las páginas de la aplicación que el usuario ha agregado a su lista de páginas favoritas.",
                ic("fa fa-star-o") + " Agregar la página actual a la lista de páginas favoritas.",
                ic("fa fa-tasks") + " Abrir la página " + b("Consulta de Tareas") + ", "
                + "para consultar y ejecutar operaciones sobre las notificaciones de tareas.",
                ic("xs xs-function") + " Abrir la página " + b("Bitácora de Funciones") + ", "
                + "para consultar los rastros de auditoría de la ejecución de operaciones de registro y procesos de negocio.",
                ic("fa fa-print") + " Abrir la página " + b("Bitácora de Informes") + ", "
                + "para consultar los rastros de auditoría de la generación de informes y archivos.",
                ic("fa fa-gears") + " Abrir la página " + b("Bitácora de Procesos") + ", "
                + "para consultar los rastros de auditoría de los procesos de negocio.",
                ic("fa fa-folder-open") + " " + b("Abrir") + " Mostrar la lista de páginas relacionadas. "
                + "En la lista se puede seleccionar una página para abrirla. Solo aparece en páginas de Consulta y Registro.",
                ic("fa fa-gear") + " " + b("Ejecutar") + " Mostrar la lista de funciones de negocio de la página. "
                + "En la lista se puede seleccionar una función para ejecutarla sobre el(los) registro(s) seleccionado(s). "
                + "Solo aparece en páginas de Consulta y Registro. "
                + "La lista incluye solo funciones de instancia de la entidad. "
                + "Las funciones de instancia son aquellas que operan sobre un solo registro y, por lo general, "
                + "tienen como primer parámetro obligatorio la referencia al registro con el que operan. "
                + "El contenido de la lista depende de la presentación de la página. "
                + "En páginas de presentación Tabular incluye solo funciones cuyo único parámetro obligatorio y sin valor predeterminado sea "
                + "la referencia al registro, de manera que pueda ser ejecutada con cada una de los registros (filas de la tabla) seleccionados, "
                + "utilizando valores predeterminados para los demás parámetros de la función (si los tiene). "
                + "En páginas de presentación Forma, la lista incluye todas las funciones de instancia de la entidad; "
                + "aquellas cuyo único parámetro obligatorio y sin valor predeterminado sea la referencia al registro, "
                + "se ejecutan utilizando valores predeterminados para los demás parámetros de la función (si los tiene); "
                + "para ejecutar las demás funciones se abre la Consola de Procesamiento. "
                + "Note que, en la lista, la etiqueta de esas funciones termina con puntos suspensivos; "
                + "además, su texto de ayuda (que aparece al apuntar con el ratón), indica que se abrirá la Consola para ejecutar la función.",
                ic("fa fa-gear") + " " + b("Procesar") + " Ejecutar el proceso seleccionado. "
                + "Solo aparece en Consolas de Procesamiento.",
                ic("fa fa-filter") + " Mostrar el diálogo para especificar los criterios de búsqueda de la consulta. "
                + "Solo aparece en páginas de presentación Tabular.",
                ic("fa fa-file-text") + " Mostrar el diálogo para guardar el resultado de la consulta. "
                + "Solo aparece en páginas de presentación Tabular.",
                ic("fa fa-repeat") + " Volver a consultar, utilizando los criterios de búsqueda de la última consulta. "
                + "Solo aparece en páginas de Consulta y Registro.",
                ic("fa fa-step-backward") + " Mostrar el primer registro del resultado de la consulta. "
                + "Solo aparece en páginas de presentación Forma.",
                ic("fa fa-backward") + " Mostrar el registro anterior. "
                + "Solo aparece en páginas de presentación Forma.",
                ic("fa fa-forward") + " Mostrar el registro siguiente. "
                + "Solo aparece en páginas de presentación Forma.",
                ic("fa fa-step-forward") + " Mostrar el último registro del resultado de la consulta. "
                + "Solo aparece en páginas de presentación Forma.",
                ic("fa fa-plus") + " Agregar un nuevo registro. "
                + "Solo aparece en páginas de Registro.",
                ic("fa fa-plus-square") + " Agregar un nuevo registro. "
                + "Solo aparece en páginas de Registro de entidades que son extendidas al menos por otra entidad.",
                ic("fa fa-plus-square-o") + " Abrir la página que permite seleccionar uno o más registros para agregar. "
                + "Solo aparece en páginas de Registro que tengan habilitada la función de Adición Rápida.",
                ic("fa fa-plus-circle") + " Abrir la página que permite agregar un nuevo registro utilizando una operación de negocio. "
                + "Solo aparece en páginas de Registro de entidades que tengan al menos una operación de negocio para agregar registros.",
                ic("fa fa-edit") + " Editar el(los) registro(s) seleccionado(s). Solo aparece en páginas de Registro.",
                ic("fa fa-trash") + " Eliminar el(los) registro(s) seleccionado(s). Solo aparece en páginas de Registro.",
                ic("fa fa-reply-all") + " Descartar los cambios realizados a los registros agregados o editados. Solo aparece en páginas de Registro.",
                ic("fa fa-check") + " Validar los cambios realizados a los campos de la página.",
                ic("fa fa-check-square") + " Validar los cambios realizados a los campos de la página y retornar a la página que contiene la colección. "
                + "Solo aparece en páginas de Registro de colecciones.",
                ic("fa fa-save") + " Confirmar (guardar en la base de datos) los cambios realizados a los registros agregados o editados. "
                + "Solo aparece en páginas de Registro. ",
                ic("fa fa-arrow-circle-up") + " Ir al principio de la página.",
                ic("fa fa-arrow-circle-down") + " Ir al final de la página.",
                ic("fa fa-arrow-left") + " Retornar el(los) registro(s) seleccionado(s). Solo aparece en diálogos de búsqueda.",
                ic("fa fa-close") + " Cerrar el diálogo sin retornar ningún registro. Solo aparece en diálogos.",
                ic("fa fa-question") + " Mostrar la ayuda para la página actual.",
                ic("fa fa-youtube") + " Mostrar el tutorial para la página actual.",
                ic("fa fa-book") + " Abrir la página de documentación de la aplicación.",
                ic("fa fa-question-circle") + " Mostrar esta ayuda.",
                ic("fa fa-sign-out") + " Finalizar la sesión de trabajo."
            )
            + "El botón para abrir la página " + b("Consulta de Tareas") + " "
            + "puede aparecer decorado con el número de tareas asignadas al usuario. "
            + "Los botones para abrir las páginas " + b("Bitácora de Informes") + " y " + b("Bitácora de Procesos") + " "
            + "pueden aparecer decorados con el número de informes y procesos no leídos ni descargados que tiene el usuario. "
            + BR + BR
            + "En la barra también aparece el " + b("Icono de Mensaje Principal") + ". "
            + "Después de generar un archivo o informe, esta imagen muestra el icono correspondiente al tipo de objeto generado; "
            + "en otros casos, muestra la severidad del mensaje que aparece en la " + b("Línea de Mensaje Principal") + ". "
            + "Al hacer clic en el " + b("Icono de Mensaje Principal") + " el contenido de la página se desplaza verticalmente hasta el final, "
            + "de manera que el " + b("Cuadro de Mensajes") + " quede visible. "
            + h3("Personalización")
            + "Las funciones de la página " + b("Personalización") + " le permiten personalizar la apariencia y el comportamiento de la aplicación. "
            + "En el encabezado de página hay un botón para abrir la página " + b("Personalización") + ". "
            + "La imagen original de ese botón es " + img("user.jpe", 18, 18) + " y puede ser sustituida en " + b("Personalización") + " ejecutando "
            + "las funciones " + b("Cargar retrato") + " o " + b("Tomar retrato") + "."
            + h3("Botones Agregar, Buscar, Ver Detalle y Dar un vistazo")
            + "En las páginas de consulta y/o registro, "
            + "las referencias de la entidad de la página a otras entidades pueden ser implementadas mediante una lista desplegable, "
            + "o por un cuadro de texto para escribir el código de la entidad referenciada y los siguientes botones de acción: "
            + ul(
                ic("fa fa-plus") + " Comenzar un diálogo de inserción, abriendo la página de registro detallado de la entidad referenciada. "
                + "Puede devolver el código de la instancia agregada a la página que hace la referencia "
                + "haciendo clic en el botón " + ic("fa fa-arrow-left") + " de la barra de botones de acción. "
                + "Este botón solo está disponible al agregar o editar.",
                ic("fa fa-search") + " Comenzar un diálogo de búsqueda, abriendo la página de consulta o registro de la entidad referenciada. "
                + "Puede devolver el código de la instancia seleccionada a la página que hace la referencia "
                + "haciendo clic en el botón " + ic("fa fa-arrow-left") + " de la barra de botones de acción de la fila, "
                + "en páginas de presentación Tabular, "
                + "o de la barra de botones de acción, en páginas de presentación Forma. "
                + "Este botón solo está disponible al agregar o editar.",
                ic("fa fa-th-list") + " Abrir la página de consulta detallada de la entidad referenciada.",
                ic("fa fa-eye") + " Mostrar el cuadro de consulta rápida de la entidad referenciada."
            )
            + "";
        /**/
        return str;
    }

}
