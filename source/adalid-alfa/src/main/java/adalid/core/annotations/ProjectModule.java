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

import static adalid.core.enums.RoleType.*;

/**
 * La anotación ProjectModule se utiliza para establecer atributos de módulos en un proyecto maestro.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ProjectModule {

    /**
     * menu indica si las vistas (páginas) generadas para las entidades que integran el módulo deben ser, o no, accesibles desde el menú principal de
     * la aplicación. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si las vistas (páginas) deben ser accesibles desde
     * el menú; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return menu
     */
    Kleenean menu() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * role indica si se deben generar, o no, roles específicos para las operaciones de las entidades que integran el módulo. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE para generar roles específicos; en caso contrario, seleccione FALSE. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return role
     */
    Kleenean role() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * foreign indica si las entidades que integran el módulo se deben agregar, o no, al conjunto de entidades foráneas de la aplicación. Las
     * entidades foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos de la aplicación, sino en otra que
     * tipicamente reside en un servidor diferente. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si las entidades se
     * deben agregar al conjunto de entidades foráneas; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return foreign
     */
    Kleenean foreign() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * privacy indica si las entidades que integran el módulo se deben agregar, o no, al conjunto de entidades privadas de la aplicación. Las
     * entidades privadas son entidades para las que no se deben generar vistas. Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE si las entidades se deben agregar al conjunto de entidades privadas; en caso contrario, seleccione FALSE. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return privacy
     */
    Kleenean privacy() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * menuType especifica el tipo de vistas (páginas) que se deben incluir en el menú del módulo. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento menu es TRUE. Su valor es uno de los elementos de la enumeración MenuType. Especifique INQUIRY,
     * PROCESSING o REGISTRATION para incluir solamente vistas de Consulta, Procesamiento o Registro, respectivamente. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para incluir vistas de cualquier tipo.
     *
     * @return menuType
     */
    MenuType menuType() default MenuType.UNSPECIFIED;

    /**
     * roleTypes especifica los tipos de rol que se deben generar para el módulo. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento role es TRUE. Su valor es una lista de elementos de la enumeración RoleType. Incluya REGISTRAR, PROCESSOR,
     * READER, CONFIGURATOR y/o MANAGER para generar roles de tipo REGISTRADOR, PROCESADOR, LECTOR, CONFIGURADOR y/o GESTOR, respectivamente.
     * Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es generar todos
     * los tipos de rol, es decir: {REGISTRAR, PROCESSOR, READER, CONFIGURATOR, MANAGER}.
     *
     * @return roleTypes
     */
    RoleType[] roleTypes() default {REGISTRAR, PROCESSOR, READER, CONFIGURATOR, MANAGER};

    /**
     * helpDocument especifica el documento incrustado de ayuda del módulo. Si utiliza la plataforma jee2, puede ser una URL o un <b>iframe</b> que
     * incluya la URL del documento.
     *
     * Cada entidad del módulo podría tener su propio documento incrustado de ayuda, según lo establecido mediante los elementos de su anotación
     * EntityClass. También se puede especificar un documento incrustado diferente para cada formato de vista de cada entidad, mediante las
     * anotaciones EntityTableView, EntityDetailView, EntityTreeView y EntityConsoleView.
     *
     * Las vistas utilizarán el documento incrustado definido para el correspondiente formato de vista de la entidad, la entidad de la vista, el
     * módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, no habrá un
     * documento incrustado disponible para la vista.
     *
     * @return helpDocument
     */
    String helpDocument() default "";

    /**
     * helpFile especifica la ruta y el nombre del archivo de ayuda del módulo.
     *
     * Si utiliza la plataforma jee2, la ruta del archivo debe ser relativa al subdirectorio especificado mediante el atributo extraordinario
     * HELP_RESOURCES_FOLDER del proyecto maestro, y cuyo valor por omisión es el subdirectorio resources/help/custom-made del directorio
     * src/main/webapp del módulo Web de la aplicación.
     *
     * Cada entidad y cada vista (página) de cada entidad del módulo podrían tener su propio archivo de ayuda, según lo establecido mediante los
     * elementos de la anotación EntityClass de cada entidad. También se puede especificar un archivo diferente para cada formato de vista de cada
     * entidad, mediante las anotaciones EntityTableView, EntityDetailView, EntityTreeView y EntityConsoleView.
     *
     * La vista que no tenga su propio archivo de ayuda utilizará el definido para el correspondiente formato de vista de la entidad, la entidad de la
     * vista, el módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, la
     * página de ayuda no estará disponible para la vista.
     *
     * @return helpFile
     */
    String helpFile() default "";

    /**
     * helpFileAutoName especifica el método de generación automática del nombre del archivo de ayuda de las vistas (páginas) del módulo. Su valor es
     * uno de los elementos de la enumeración HelpFileAutoName. Seleccione DISPLAY o ENTITY para que generar automáticamente el nombre del archivo de
     * ayuda de las vistas con el nombre de la vista o con el de la entidad de la vista, respectivamente; alternativamente, omita el elemento o
     * seleccione NONE para utilizar el valor predeterminado del atributo. Seleccione ENTITY para utilizar los archivos de ayuda generados por la
     * clase utilitaria <b>WebAppHelp</b>. El valor predeterminado del atributo es el valor del elemento helpFileAutoName de la anotación
     * MasterProject del proyecto maestro.
     *
     * @return helpFileAutoName
     */
    HelpFileAutoName helpFileAutoName() default HelpFileAutoName.UNSPECIFIED;

    /**
     * helpFileAutoType especifica el tipo (la extensión sin el punto) de los nombres de archivo de ayuda generados automáticamente. Este elemento es
     * relevante solo si el valor especificado, o determinado, para el elemento helpFileAutoName es DISPLAY o ENTITY. Su valor puede ser <b>html</b>,
     * <b>xhtml</b>, <b>pdf</b>, <b>gif</b>, <b>jpg</b>, <b>jpeg</b> o <b>png</b>. Especifique <b>xhtml</b> para utilizar los archivos de ayuda
     * generados por la clase utilitaria <b>WebAppHelp</b>. El valor predeterminado del atributo es el valor del elemento helpFileAutoType de la
     * anotación MasterProject del proyecto maestro.
     *
     * @return helpFileAutoType
     */
    String helpFileAutoType() default ""; // Constants.DEFAULT_HELP_FILE_TYPE;

}
