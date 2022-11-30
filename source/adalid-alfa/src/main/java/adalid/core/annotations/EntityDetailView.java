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

import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación EntityDetailView se utiliza para controlar la generación de las vistas (páginas) de consulta y/o registro detallado de la entidad,
 * conocidas como Detalles de Consulta y/o Detalles de Registro, respectivamente.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityDetailView {

    /**
     * enabled indica si se debe, o no, generar las vistas. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para generar
     * las vistas; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return enabled
     */
    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * heading indica si las vistas Maestro/Detalle, donde la entidad es el detalle, muestran, o no, un encabezado con propiedades del maestro. Este
     * elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para mostrar el encabezado; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return heading
     */
    Kleenean heading() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * menu especifica el tipo de vistas (páginas) generadas que deben ser accesibles desde el menú principal de la aplicación. Su valor es uno de los
     * elementos de la enumeración ViewMenuOption. Seleccione ALL para agregar una opción de menú para todas las vistas generadas, sin importar su
     * tipo. Seleccione READING o WRITING para agregar una opción solo para las vistas de consulta o las de registro, respectivamente. Seleccione NONE
     * para no agregar opciones de menú para las vistas generadas. El valor predeterminado del atributo es NONE.
     *
     * Este elemento no aplica a vistas Maestro/Detalle.
     *
     * @return menu
     */
    ViewMenuOption menu() default ViewMenuOption.NONE;

    /**
     * inserting indica si la opción de menú generada para la vista de registro debe, o no, abrir la página en modo de inserción. Su valor es uno de
     * los elementos de la enumeración Kleenean. este elemento solo es relevante si el valor del elemento menu es ALL o WRITING. Seleccione TRUE para
     * abrir la página en modo de inserción; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return inserting
     */
    Kleenean inserting() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * helpDocument especifica el documento incrustado de ayuda de las vistas (páginas) de consulta y/o registro detallado de la entidad.
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
     * helpFile especifica la ruta y el nombre del archivo de ayuda de las vistas (páginas) de consulta y/o registro detallado de la entidad.
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
     * readingViewHeadSnippet especifica la ruta y el nombre del snippet del encabezado en las vistas (páginas) de consulta detallada de la entidad.
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
     * de consulta detallada de la entidad.
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
     * de consulta detallada de la entidad.
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
     * writingViewHeadSnippet especifica la ruta y el nombre del snippet del encabezado en las vistas (páginas) de registro detallado de la entidad.
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
     * de registro detallado de la entidad.
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
     * de registro detallado de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewWesternToolbarSnippet
     */
    String writingViewWesternToolbarSnippet() default "";

}
