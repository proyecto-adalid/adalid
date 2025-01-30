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

import adalid.core.Constants;
import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación EntityTreeView se utiliza para controlar la generación de las vistas (páginas) de consulta y/o registro jerárquico de la entidad,
 * conocidas como Árboles de Consulta y/o Árboles de Registro, respectivamente. Solo aplica si la entidad es jerárquica, es decir, tiene una relación
 * de varios-a-uno con ella misma y la propiedad que establece tal relación ha sido designada como propiedad padre (vea Anotación ParentProperty).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityTreeView {

    /**
     * enabled indica si se debe, o no, generar las vistas. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para generar
     * las vistas; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return enabled
     */
    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * menu especifica el tipo de vistas (páginas) generadas que deben ser accesibles desde el menú principal de la aplicación. Su valor es uno de los
     * elementos de la enumeración ViewMenuOption. Seleccione ALL para agregar una opción de menú para todas las vistas generadas, sin importar su
     * tipo. Seleccione READING o WRITING para agregar una opción solo para las vistas de consulta o las de registro, respectivamente. Seleccione NONE
     * para no agregar opciones de menú para las vistas generadas. El valor predeterminado del atributo es NONE.
     *
     * @return menu
     */
    ViewMenuOption menu() default ViewMenuOption.NONE;

    /**
     * rootLimit específica el máximo número de nodos raíz (sin nodo superior) que muestra la vista. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento enabled es TRUE. Su valor debe ser un número entero entre 5 y 1000. El valor predeterminado es
     * 100.
     *
     * @return rootLimit
     */
    int rootLimit() default Constants.DEFAULT_ROWS_PER_PAGE_LIMIT;

    /**
     * branchLimit específica el máximo número de nodos por rama que muestra la vista. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento enabled es TRUE. Su valor debe ser un número entero entre 5 y 1000. El valor predeterminado es 100.
     *
     * @return branchLimit
     */
    int branchLimit() default Constants.DEFAULT_ROWS_PER_PAGE_LIMIT;

    /**
     * helpDocument especifica el documento incrustado de ayuda de las vistas (páginas) de consulta y/o registro jerárquico de la entidad.
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
     * helpFile especifica la ruta y el nombre del archivo de ayuda de las vistas (páginas) de consulta y/o registro jerárquico de la entidad.
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
     * readingViewHeadSnippet especifica la ruta y el nombre del snippet del encabezado en las vistas (páginas) de consulta jerárquica de la entidad.
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
     * de consulta jerárquica de la entidad.
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
     * de consulta jerárquica de la entidad.
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
     * readingViewAboveTreeSnippet especifica la ruta y el nombre del snippet ubicado encima del árbol en las vistas (páginas) de consulta jerárquica
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewAboveTreeSnippet
     */
    String readingViewAboveTreeSnippet() default "";

    /**
     * readingViewBelowTreeSnippet especifica la ruta y el nombre del snippet ubicado debajo del árbol en las vistas (páginas) de consulta jerárquica
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingViewBelowTreeSnippet
     */
    String readingViewBelowTreeSnippet() default "";

    /**
     * writingViewHeadSnippet especifica la ruta y el nombre del snippet del encabezado en las vistas (páginas) de registro jerárquico de la entidad.
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
     * de registro jerárquico de la entidad.
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
     * de registro jerárquico de la entidad.
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
     * writingViewAboveTreeSnippet especifica la ruta y el nombre del snippet ubicado encima del árbol en las vistas (páginas) de registro jerárquico
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewAboveTreeSnippet
     */
    String writingViewAboveTreeSnippet() default "";

    /**
     * writingViewBelowTreeSnippet especifica la ruta y el nombre del snippet ubicado debajo del árbol en las vistas (páginas) de registro jerárquico
     * de la entidad.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingViewBelowTreeSnippet
     */
    String writingViewBelowTreeSnippet() default "";

}
