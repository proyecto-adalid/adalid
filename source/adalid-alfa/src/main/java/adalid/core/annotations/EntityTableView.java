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
package adalid.core.annotations;

import adalid.core.*;
import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación EntityTableView se utiliza para controlar la generación de las vistas (páginas) de consulta y/o registro tabular de la entidad,
 * conocidas como Tablas de Consulta y/o Tablas de Registro, respectivamente.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityTableView {

    /**
     * enabled indica si se debe, o no, generar las vistas. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para generar
     * las vistas; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return enabled
     */
    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * inserts indica si las vistas deben permitir, o no, agregar nuevas instancias de la entidad, es decir, insertar nuevas filas a la tabla de la
     * base de datos correspondiente a la entidad. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled
     * es TRUE. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir agregar; en caso contrario, seleccione
     * FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado
     * del atributo es TRUE.
     *
     * @return inserts
     */
    Kleenean inserts() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * updates indica si las vistas deben permitir, o no, modificar instancias de la entidad, es decir, actualizar filas de la tabla de la base de
     * datos correspondiente a la entidad. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE.
     * Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir modificar; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return updates
     */
    Kleenean updates() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * deletes indica si las vistas deben permitir, o no, eliminar instancias de la entidad, es decir, eliminar filas de la tabla de la base de datos
     * correspondiente a la entidad. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su
     * valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir eliminar; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return deletes
     */
    Kleenean deletes() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * heading indica si las vistas Maestro/Detalle, donde la entidad es el detalle, muestran, o no, un encabezado con propiedades del maestro. Este
     * elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para mostrar el encabezado; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return heading
     */
    Kleenean heading() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * quickFilter indica si las vistas generadas incluyen, o no, un snippet para suministrar criterios de búsqueda. Este elemento es relevante solo
     * si se especificó la ruta del snippet mediante el método setProjectFilterSnippetPath o si la entidad tiene una clave de negocio (vea la
     * anotación BusinessKey) y/o una propiedad nombre (vea la anotación NameProperty), en cuyo caso se incluye el snippet predeterminado
     * Constants.QUICK_FILTER_SNIPPET. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para incluir el snippet; en caso
     * contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo.
     * El valor predeterminado del atributo es FALSE.
     *
     * @return quickFilter
     */
    Kleenean quickFilter() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * stickyHeader indica si las tablas de las vistas generadas incluyen, o no, un encabezado fijo. El encabezado fijo permanece visible durante el
     * desplazamiento. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para incluir el encabezado fijo; en caso
     * contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo.
     * El valor predeterminado del atributo se puede establecer utilizando el método setEntityTableViewWithStickyHeaderDefaultValue del proyecto
     * maestro; si no se establece, es FALSE.
     *
     * @return quickFilter
     */
    Kleenean stickyHeader() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * rowsLimit específica el máximo número de filas por página que muestra la vista. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento enabled es TRUE. Su valor debe ser un número entero entre 5 y 1000. El valor predeterminado es 100.
     *
     * @return rowsLimit
     */
    int rowsLimit() default Constants.DEFAULT_ROWS_PER_PAGE_LIMIT;

    /**
     * rows específica el número inicial de filas por página que muestra la vista. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento enabled es TRUE. Su valor debe ser un número entero entre 1 y el valor del elemento rowsLimit. El valor
     * predeterminado es 10
     *
     * @return rows
     */
    int rows() default Constants.DEFAULT_ROWS_PER_PAGE;

    /**
     * responsiveMode especifica el modo "responsive" de la tabla. Su valor es uno de los elementos de la enumeración TableResponsiveMode. Seleccione
     * PRIORITY para mostrar las columnas de la tabla dependiendo de su prioridad, según el tamaño de la pantalla. Seleccione REFLOW para mostrar
     * todas las columnas, apiladas o no, según el tamaño de la pantalla. Omita el elemento o seleccione NONE para que la tabla no sea "responsive".
     *
     * @return responsiveMode
     */
    TableResponsiveMode responsiveMode() default TableResponsiveMode.NONE;

    /**
     * menu especifica el tipo de vistas (páginas) generadas que deben ser accesibles desde el menú principal de la aplicación. Su valor es uno de los
     * elementos de la enumeración ViewMenuOption. Seleccione ALL para agregar una opción de menú para todas las vistas generadas, sin importar su
     * tipo. Seleccione READING o WRITING para agregar una opción solo para las vistas de consulta o las de registro, respectivamente. Seleccione NONE
     * para no agregar opciones de menú para las vistas generadas. El valor predeterminado del atributo es ALL.
     *
     * @return menu
     */
    ViewMenuOption menu() default ViewMenuOption.ALL;

    /**
     * helpDocument especifica el documento incrustado de ayuda de las vistas (páginas) de consulta y/o registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, puede ser una URL o un <b>iframe</b> que incluya la URL del documento.
     *
     * Las vistas utilizarán el documento incrustado definido para el correspondiente formato de vista de la entidad, la entidad de la vista, el
     * módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, no habrá un
     * documento incrustado disponible para la vista.
     *
     * @return helpDocument
     */
    String helpDocument() default "";

    /**
     * helpFile especifica la ruta y el nombre del archivo de ayuda de las vistas (páginas) de consulta y/o registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, la ruta del archivo debe ser relativa al subdirectorio especificado mediante el atributo extraordinario
     * HELP_RESOURCES_FOLDER del proyecto maestro, y cuyo valor por omisión es el subdirectorio resources/help/custom-made del directorio
     * src/main/webapp del módulo Web de la aplicación.
     *
     * Cada vista de la entidad podría tener su propio archivo de ayuda, según lo establecido mediante el elemento helpFileAutoName de la anotación
     * EntityClass.
     *
     * La vista que no tenga su propio archivo de ayuda utilizará el definido para el correspondiente formato de vista de la entidad, la entidad de la
     * vista, el módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, la
     * página de ayuda no estará disponible para la vista.
     *
     * @return helpFile
     */
    String helpFile() default "";

    /**
     * readingViewHeadSnippet especifica la ruta y el nombre del snippet del encabezado en las vistas (páginas) de consulta tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewHeadSnippet
     */
    String readingViewHeadSnippet() default "";

    /**
     * readingViewEasternToolbarSnippet especifica la ruta y el nombre del snippet de la barra de botones de acción oriental en las vistas (páginas)
     * de consulta tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewEasternToolbarSnippet
     */
    String readingViewEasternToolbarSnippet() default "";

    /**
     * readingViewWesternToolbarSnippet especifica la ruta y el nombre del snippet de la barra de botones de acción occidental en las vistas (páginas)
     * de consulta tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewWesternToolbarSnippet
     */
    String readingViewWesternToolbarSnippet() default "";

    /**
     * readingViewAboveTableSnippet especifica la ruta y el nombre del snippet ubicado encima de la tabla en las vistas (páginas) de consulta tabular
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewAboveTableSnippet
     */
    String readingViewAboveTableSnippet() default "";

    /**
     * readingViewBelowTableSnippet especifica la ruta y el nombre del snippet ubicado debajo de la tabla en las vistas (páginas) de consulta tabular
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewBelowTableSnippet
     */
    String readingViewBelowTableSnippet() default "";

    /**
     * readingViewRowActionSnippet especifica la ruta y el nombre del snippet de la columna de botones de acción de las filas de la tabla en las
     * vistas (páginas) de consulta tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewRowActionSnippet
     */
    String readingViewRowActionSnippet() default "";

    /**
     * readingViewRowStatusSnippet especifica la ruta y el nombre del snippet de la columna de iconos de estado de las filas de la tabla en las vistas
     * (páginas) de consulta tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewRowStatusSnippet
     */
    String readingViewRowStatusSnippet() default "";

    /**
     * readingViewRowNumberSnippet especifica la ruta y el nombre del snippet de la columna de números de secuencia de las filas de la tabla en las
     * vistas (páginas) de consulta tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewRowNumberSnippet
     */
    String readingViewRowNumberSnippet() default "";

    /**
     * writingViewHeadSnippet especifica la ruta y el nombre del snippet del encabezado en las vistas (páginas) de registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewHeadSnippet
     */
    String writingViewHeadSnippet() default "";

    /**
     * writingViewEasternToolbarSnippet especifica la ruta y el nombre del snippet de la barra de botones de acción oriental en las vistas (páginas)
     * de registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewEasternToolbarSnippet
     */
    String writingViewEasternToolbarSnippet() default "";

    /**
     * writingViewWesternToolbarSnippet especifica la ruta y el nombre del snippet de la barra de botones de acción occidental en las vistas (páginas)
     * de registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewWesternToolbarSnippet
     */
    String writingViewWesternToolbarSnippet() default "";

    /**
     * writingViewAboveTableSnippet especifica la ruta y el nombre del snippet ubicado encima de la tabla en las vistas (páginas) de registro tabular
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewAboveTableSnippet
     */
    String writingViewAboveTableSnippet() default "";

    /**
     * writingViewBelowTableSnippet especifica la ruta y el nombre del snippet ubicado debajo de la tabla en las vistas (páginas) de registro tabular
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewBelowTableSnippet
     */
    String writingViewBelowTableSnippet() default "";

    /**
     * writingViewRowActionSnippet especifica la ruta y el nombre del snippet de la columna de botones de acción de las filas de la tabla en las
     * vistas (páginas) de registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewRowActionSnippet
     */
    String writingViewRowActionSnippet() default "";

    /**
     * writingViewRowStatusSnippet especifica la ruta y el nombre del snippet de la columna de iconos de estado de las filas de la tabla en las vistas
     * (páginas) de registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewRowStatusSnippet
     */
    String writingViewRowStatusSnippet() default "";

    /**
     * writingViewRowNumberSnippet especifica la ruta y el nombre del snippet de la columna de números de secuencia de las filas de la tabla en las
     * vistas (páginas) de registro tabular de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewRowNumberSnippet
     */
    String writingViewRowNumberSnippet() default "";

}
