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
public class DialogoFiltrarConsulta extends AbstractHelpWriter {

    public DialogoFiltrarConsulta(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String str = ""
            + "Este diálogo le permite realizar consultas a través de campos de búsqueda predefinidos o de filtros previamente definidos por usted. "
            + BR + BR
            + "El primer campo del diálogo es " + b("filtro") + ", "
            + "una lista desplegable que permite seleccionar un filtro de búsqueda previamente definido por usted. "
            + "A continuación de la lista se encuentran los siguientes botones: "
            + ul(
                ic("fa fa-plus") + " para definir un nuevo filtro.",
                ic("fa fa-copy") + " para duplicar el filtro seleccionado.",
                ic("fa fa-table") + " para abrir la página de registro de filtros de búsqueda.",
                ic("fa fa-th") + " para ver y editar los criterios del filtro seleccionado."
            )
            + "En algunas páginas, el diálogo incluye la lista desplegable " + b("segmento") + " después de " + b("filtro") + ". "
            + "Los segmentos son filtros definidos por el diseñador de la aplicación. "
            + BR + BR
            + "Después de " + b("filtro") + " y " + b("segmento") + " están los campos de búsqueda predefinidos. "
            + "Para cada campo puede seleccionar un operador de comparación en la correspondiente lista desplegable. "
            + "Salvo en el caso de campos lógicos, el operador de comparación requiere un valor. "
            + "El componente que se utiliza para especificar ese valor se encuentra a continuación de la lista desplegable del operador de comparación; "
            + "ese componente puede ser otra lista desplegable, o un cuadro de texto. "
            + "En el caso de cuadros de texto para referencias a entidades, el valor debe ser el código de la entidad; "
            + "puede escribirlo o buscarlo, haciendo clic en el botón que se encuentra inmediatamente a la derecha del cuadro de texto. "
            + "En el caso de campos de tipo temporal, es decir, fechas, horas y " + i("timestamps") + " (campos que tienen fecha y hora), "
            + "el valor no necesariamente tiene que ser una fecha, una hora o un " + i("timestamp") + "; "
            + "también puede ser un " + b("sumando o sustraendo temporal") + ", es decir, "
            + "un intervalo de tiempo para sumar o restar a la fecha actual o al " + i("timestamp") + " actual. "
            + "Los intervalos se representan mediante un número entero, positivo (sin signo o precedido del signo más) "
            + "o negativo (precedido del signo menos), seguido por una letra que identifica la unidad de tiempo del intervalo. Las letras válidas son: "
            + b("D") + " (días), " + b("M") + " (meses), " + b("A") + " (años), "
            + b("h") + " (horas), " + b("m") + " (minutos), y " + ("s") + " (segundos). "
            + "Los intervalos definidos con letras mayúsculas (D, M, A) se suman o restan (según el signo del número) a la fecha actual. "
            + "Los intervalos definidos con letras minúsculas (h, m, s) se suman o restan (según el signo del número) al " + i("timestamp") + " actual. "
            + "Por ejemplo, para obtener los registros con una fecha en los últimos 3 meses, se especificaría el operador de comparación "
            + b("Es mayor que") + " y el valor " + b("-3M") + " (con signo negativo). "
            + BR + BR
            + "El resultado de la consulta será el conjunto de registros que cumplen con todos los criterios especificados. "
            + BR + BR
            + "En la parte inferior del diálogo se encuentran los botones que corresponden a las demás acciones disponibles. Estas son: "
            + ul(
                b("Aplicar") + ": ejecutar una consulta con los criterios de búsqueda especificados.",
                b("Borrar") + ": descartar todos los criterios de búsqueda especificados. "
                + "El botón " + ic("fa fa-trash") + " de cada criterio permite descartar solo ese criterio.",
                b("Guardar") + ": guardar un nuevo filtro con los criterios especificados más los del filtro de búsqueda especificado.",
                b("Ocultar") + ": ocultar el diálogo.",
                b("Ayuda") + ": mostrar esta ayuda."
            )
            + "En las páginas que permiten agregar nuevos registros, si no existen registros que cumplan con los criterios de búsqueda especificados, "
            + "aparecen automáticamente uno o más botones " + b("Agregar") + ", justo debajo de los demás botones de acción. "
            + BR + BR
            + "El resultado de la consulta se almacena temporalmente en un caché de memoria; "
            + "así se reduce la transferencia de datos entre el servidor de aplicaciones y el servidor de base de datos. "
            + "Para evitar el uso indiscriminado del caché de memoria, las páginas suelen tener un límite máximo de registros que pueden mostrar; "
            + "por lo general ese límite es de 100 registros. Los campos y filtros de búsqueda sirven para acotar el conjunto de resultados. "
            + BR + BR
            + "El resultado de la consulta se puede almacenar de forma permanente como un archivo que puede visualizarse o descargarse "
            + "para ser procesado con una aplicación instalada en su computador. "
            + "El botón " + ic("fa fa-file-text") + " de la barra de botones de acción muestra el menú de opciones para guardar el resultado de la consulta. "
            + "";
        /**/
        return str;
    }

}
