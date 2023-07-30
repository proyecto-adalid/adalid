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
public class BarraBotonesAccion2 extends AbstractHelpWriter {

    public BarraBotonesAccion2(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String str = ""
            + "La primera columna de la tabla corresponde a la casilla de selección. "
            + "La casilla permite seleccionar filas para luego ejecutar una acción sobre cada una de ellas, "
            + "haciendo clic en alguno de los siguientes botones de la barra de botones de acción de la página: Editar, Eliminar y Ejecutar. "
            + "Alternativamente, estas y otras acciones de pueden ejecutar haciendo clic en los botones de la barra de botones de acción de la fila, "
            + "que aparece en la segunda columna. Esta barra solo muestra los botones de las acciones disponibles en el momento. "
            + "La tercera columna muestra el número de la fila; "
            + "este número no forma parte del registro almacenado en la base de datos; "
            + "es la posición de la fila dentro del conjunto de filas que se obtiene como resultado de la consulta a la base de datos. "
            + "Cuando se está consultando, si existe una página de consulta y/o registro detallado para la entidad, "
            + "el número de la fila es un hipervínculo que abre esa página para mostrar la fila. "
            + h3("Barra de botones de acción de la fila")
            + "Cada fila de la tabla incluye una barra de botones de acción, la cual puede contener los siguientes botones:"
            + ul(
                ic("fa fa-list") + " para abrir el menú de contexto. "
                + "Ese menú incluye una lista de páginas relacionadas y una lista de funciones de negocio de la entidad. "
                + "En la lista de páginas se puede seleccionar una página para abrirla y "
                + "en la lista de funciones se puede seleccionar una función para ejecutarla.",
                ic("fa fa-edit") + " para editar la fila. Este botón solo se muestra en páginas de registro.",
                ic("fa fa-trash") + " para eliminar la fila. Este botón solo se muestra en páginas de registro.",
                ic("fa fa-reply") + " descartar los cambios realizados a la fila. Este botón solo se muestra en páginas de registro, al agregar o editar la fila.",
                ic("fa fa-arrow-left") + " para retornar la fila. Este botón solo se muestra en diálogos de búsqueda."
            )
            + h3("Barra de títulos de la tabla")
            + "La barra de títulos de la tabla incluye componentes que permiten ordenar y filtrar las filas visibles. "
            + "El botón " + ic("fa fa-exchange fa-rotate-90") + " que se encuentra a la derecha del nombre de cada columna permite ordenar las filas visibles. "
            + "Al hacer clic sobre el botón, las filas se ordenan ascendentemente por el valor de esa columna. "
            + "Al hacer nuevamente clic sobre el botón las filas se ordenan descendentemente. "
            + "Para ordenar por más de una columna se debe presionar la tecla SHIFT al hacer clic sobre el botón de las columnas adicionales. "
            + BR + BR
            + "El componente que se encuentra por debajo del nombre de cada columna permite filtrar aún más el resultado "
            + "obtenido al consultar la base de datos con los criterios especificados en el diálogo de criterios de búsqueda; "
            + "de esta manera, el usuario puede ocultar algunas filas sin necesidad de hacer una nueva consulta (a la base de datos). "
            + "La barra muestra un cuadro de texto para las columnas que corresponden a referencias a otras entidades, "
            + "campos alfanuméricos, numéricos y temporales, es decir, fechas, horas y " + i("timestamps") + " (campos que tienen fecha y hora). "
            + "La barra muestra una lista desplegable para las columnas que corresponden a campos lógicos. "
            + h4("Referencias a otras entidades y campos alfanuméricos")
            + "El operador de comparación predeterminado para referencias a otras entidades y campos alfanuméricos es " + b("Comienza por") + ". "
            + "Es posible filtrar utilizando otros operadores de comparación mediante el uso de caracteres especiales:"
            + ul(
                iux + star + " para ver las filas que en la columna tienen un valor que comienza por " + iux + "; "
                + "este es el operador predeterminado para referencias a otras entidades y campos alfanuméricos y, por lo tanto, "
                + "basta con escribir solo " + iux + ", sin el asterisco.",
                star + iux + " para ver las filas que en la columna tienen un valor que termina en " + iux,
                star + iux + star + " para ver las filas que en la columna tienen un valor que contiene " + iux,
                not + iux + star + " para ver las filas que en la columna tienen un valor que no comienza por " + iux,
                not + star + iux + " para ver las filas que en la columna tienen un valor que no termina en " + iux,
                not + star + iux + star + " para ver las filas que en la columna tienen un valor que no contiene " + iux,
                b("=") + iux + " para ver las filas que en la columna tienen un valor igual a " + iux,
                b(FWGTS) + iux + " para ver las filas que en la columna tienen un valor mayor que " + iux,
                b(">=") + iux + " para ver las filas que en la columna tienen un valor mayor o igual a " + iux,
                b(FWLTS) + iux + " para ver las filas que en la columna tienen un valor menor que " + iux,
                b("<=") + iux + " para ver las filas que en la columna tienen un valor menor o igual a " + iux,
                not + iux + " para ver las filas que en la columna tienen un valor no igual a " + iux,
                not + b("=") + iux + " para ver las filas que en la columna tienen un valor no igual a " + iux,
                b("<>") + iux + " para ver las filas que en la columna tienen un valor no igual a " + iux
            )
            + "El resultado será el mismo si la cadena de caracteres " + iux + " está escrita en mayúsculas o en minúsculas. "
            + BR + BR
            + "Si " + iux + " contiene asteriscos o signos de admiración, éstos deben escribirse entre barras diagonales "
            + "para evitar que se utilicen para determinar el operador de comparación; "
            + "por ejemplo, para ver las filas con un campo alfanumérico que comienza por un asterisco, "
            + "en el cuadro de texto correspondiente se debe escribir " + b("/*/*") + " en lugar de " + b("**") + ". "
            + h4("Campos numéricos y temporales")
            + "El operador de comparación predeterminado para campos numéricos y temporales es " + b("Es igual a") + ". "
            + "Es posible filtrar utilizando otros operadores de comparación mediante el uso de caracteres especiales:"
            + ul(
                b("=") + iux + " para ver las filas que en la columna tienen un valor igual a " + iux + "; "
                + "este es el operador predeterminado para campos numéricos y temporales y, por lo tanto, "
                + "basta con escribir solo " + iux + ", sin el signo igual.",
                b(FWGTS) + iux + " para ver las filas que en la columna tienen un valor mayor que " + iux,
                b(">=") + iux + " para ver las filas que en la columna tienen un valor mayor o igual a " + iux,
                b(FWLTS) + iux + " para ver las filas que en la columna tienen un valor menor que " + iux,
                b("<=") + iux + " para ver las filas que en la columna tienen un valor menor o igual a " + iux,
                not + iux + " para ver las filas que en la columna tienen un valor no igual a " + iux,
                not + b("=") + iux + " para ver las filas que en la columna tienen un valor no igual a " + iux,
                b("<>") + iux + " para ver las filas que en la columna tienen un valor no igual a " + iux
            )
            + "En el caso de campos temporales el valor de " + iux + " no necesariamente tiene que ser una fecha, una hora o un " + i("timestamp") + "; "
            + "también puede ser un " + b("sumando o sustraendo temporal") + ", es decir, "
            + "un intervalo de tiempo para sumar o restar a la fecha actual o al " + i("timestamp") + " actual. "
            + "Los intervalos se representan mediante un número entero, positivo (sin signo o precedido del signo más) "
            + "o negativo (precedido del signo menos), seguido por una letra que identifica la unidad de tiempo del intervalo. Las letras válidas son: "
            + b("D") + " (días), " + b("M") + " (meses), " + b("A") + " (años), "
            + b("h") + " (horas), " + b("m") + " (minutos), y " + ("s") + " (segundos). "
            + "Los intervalos definidos con letras mayúsculas (D, M, A) se suman o restan (según el signo del número) a la fecha actual. "
            + "Los intervalos definidos con letras minúsculas (h, m, s) se suman o restan (según el signo del número) al " + i("timestamp") + " actual. "
            + "Por ejemplo, para ver las filas con una fecha en los últimos 3 meses, el valor de " + iux + " sería " + b(">-3M") + ". "
            + h4("Campos lógicos")
            + "El operador de comparación para campos lógicos es siempre " + b("Es igual a") + ". "
            + "La lista desplegable permite seleccionar " + b("Sí") + " o " + b("No") + ", "
            + "equivalentes a " + b("Es verdadero") + " o " + b("Es falso") + ", respectivamente. "
            + "";
        /**/
        return str;
    }

}
