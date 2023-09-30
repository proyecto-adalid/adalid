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
 * La anotación MasterProject se utiliza para establecer atributos del proyecto maestro.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MasterProject {

    /**
     * alias especifica el código alterno del proyecto. El alias solo puede contener letras minúsculas y números, debe comenzar por una letra, y no
     * puede ser "meta" o "workspace". Se recomienda utilizar un alias que tenga el nombre de su proyecto como prefijo.
     *
     * @return alias
     */
    String alias() default "";

    /**
     * acronym especifica el acrónimo del proyecto. El acrónimo solo puede contener letras y números, debe comenzar por una letra, y no puede ser
     * "meta" o "workspace". El valor predeterminado del atributo es el alias del proyecto en letras mayúsculas.
     *
     * @return acronym
     */
    String acronym() default "";

    /**
     * helpDocument especifica el documento incrustado de ayuda del proyecto.
     *
     * Si utiliza la plataforma jee2, puede ser una URL o un <b>iframe</b> que incluya la URL del documento.
     *
     * Cada módulo y cada entidad del proyecto podrían tener su propio documento incrustado de ayuda, según lo establecido mediante los elementos de
     * sus anotaciones ProjectModule y EntityClass. También se puede especificar un documento incrustado diferente para cada formato de vista de cada
     * entidad, mediante las anotaciones EntityTableView, EntityDetailView, EntityTreeView y EntityConsoleView.
     *
     * Las vistas utilizarán el documento incrustado definido para el correspondiente formato de vista de la entidad, la entidad de la vista, el
     * módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, no habrá un
     * documento incrustado disponible para la vista.
     *
     * @return helpDocument
     */
    String helpDocument() default "";

    /**
     * helpFile especifica la ruta y el nombre del archivo de ayuda del proyecto.
     *
     * Si utiliza la plataforma jee2, la ruta del archivo debe ser relativa al subdirectorio especificado mediante el atributo extraordinario
     * HELP_RESOURCES_FOLDER del proyecto maestro, y cuyo valor por omisión es el subdirectorio resources/help/custom-made del directorio
     * src/main/webapp del módulo Web de la aplicación.
     *
     * Cada módulo, cada entidad y cada vista (página) de cada entidad del proyecto podrían tener su propio archivo de ayuda, según lo establecido
     * mediante los elementos de las anotaciones ProjectModule y EntityClass de cada módulo y cada entidad. También se puede especificar un archivo
     * diferente para cada formato de vista de cada entidad, mediante las anotaciones EntityTableView, EntityDetailView, EntityTreeView y
     * EntityConsoleView.
     *
     * La vista que no tenga su propio archivo de ayuda utilizará el definido para el correspondiente formato de vista de la entidad, la entidad de la
     * vista, el módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, la
     * página de ayuda no estará disponible para la vista.
     *
     * @return helpFile
     */
    String helpFile() default "";

    /**
     * helpFileAutoName especifica el método de generación automática del nombre del archivo de ayuda de las vistas (páginas) del proyecto. Su valor
     * es uno de los elementos de la enumeración HelpFileAutoName. Seleccione DISPLAY o ENTITY para que generar automáticamente el nombre del archivo
     * de ayuda de las vistas con el nombre de la vista o con el de la entidad de la vista, respectivamente; alternativamente, omita el elemento o
     * seleccione NONE para no generar nombres de archivos de ayuda de vistas automáticamente. Seleccione ENTITY para utilizar los archivos de ayuda
     * generados por la clase utilitaria <b>WebAppHelp</b>. El valor predeterminado del atributo es NONE.
     *
     * @return helpFileAutoName
     */
    HelpFileAutoName helpFileAutoName() default HelpFileAutoName.UNSPECIFIED;

    /**
     * helpFileAutoType especifica el tipo (la extensión sin el punto) de los nombres de archivo de ayuda generados automáticamente. Este elemento es
     * relevante solo si el valor especificado, o determinado, para el elemento helpFileAutoName es DISPLAY o ENTITY. Su valor puede ser <b>html</b>,
     * <b>xhtml</b>, <b>pdf</b>, <b>gif</b>, <b>jpg</b>, <b>jpeg</b> o <b>png</b>. Especifique <b>xhtml</b> para utilizar los archivos de ayuda
     * generados por la clase utilitaria <b>WebAppHelp</b>. El valor predeterminado del atributo es <b>html</b>.
     *
     * @return helpFileAutoType
     */
    String helpFileAutoType() default ""; // Constants.DEFAULT_HELP_FILE_TYPE;

    /**
     * runnable se utiliza para designar un proyecto maestro como ejecutable por la clase Runner.
     *
     * @return runnable
     */
    boolean runnable() default true;

}
