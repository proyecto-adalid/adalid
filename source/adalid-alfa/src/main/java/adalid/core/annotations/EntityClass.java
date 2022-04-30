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
 * La anotación EntityClass se utiliza para establecer atributos básicos de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityClass {

    /**
     * base indica si la entidad es, o no, una entidad de la base operativa de la aplicación. La base operativa es el conjunto de entidades necesarias
     * para la prestación de los servicios básicos de la aplicación. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si
     * la entidad es una entidad de la base operativa; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return base
     */
    Kleenean base() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * catalog indica si la entidad es, o no, una entidad del catálogo de la aplicación. El catálogo es el conjunto de entidades de configuración
     * básica (netadata) de la aplicación. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la entidad es una entidad
     * del catálogo; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return catalog
     */
    Kleenean catalog() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * independent indica si la entidad es, o no, existencialmente independiente. Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE si la entidad es existencialmente independiente; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return independent
     */
    Kleenean independent() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * viewType especifica el tipo de vistas permitido para la entidad. Su valor es uno de los elementos de la enumeración EntityViewType. Seleccione
     * INDEPENDENT, MASTER_DETAIL o BOTH para permitir la generación de vistas independientes, maestro/detalle o ambas, respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es INDEPENDENT si la entidad es una enumeración; de lo contrario, BOTH.
     *
     * @return viewType
     */
    EntityViewType viewType() default EntityViewType.UNSPECIFIED;

    /**
     * variant indica si la entidad es, o no, una entidad variante. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la
     * entidad es variante; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return variant
     */
    Kleenean variant() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * resourceType especifica el tipo de recurso de la entidad. Su valor es uno de los elementos de la enumeración ResourceType. Seleccione
     * CONFIGURATION, TESTING u OPERATION si la entidad es de configuración, prueba u operación, respectivamente. Alternativamente, omita el elemento
     * o seleccione UNSPECIFIED si el tipo de recurso es indeterminado.
     *
     * @return resourceType
     */
    ResourceType resourceType() default ResourceType.UNSPECIFIED;

    /**
     * resourceGender especifica el género gramatical de la entidad. Su valor es uno de los elementos de la enumeración ResourceGender. Seleccione
     * MASCULINE, FEMININE, COMMON o NEUTER si la entidad es de género masculino, femenino, común o neutro, respectivamente. Alternativamente, omita
     * el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado es NEUTER.
     *
     * @return resourceGender
     */
    ResourceGender resourceGender() default ResourceGender.UNSPECIFIED;

//  String propertiesPrefix() default "";
//
//  String propertiesSuffix() default "";
//
    /**
     * collectionName especifica el nombre de colección de la entidad
     *
     * @return collectionName
     */
    String collectionName() default "";

    /**
     * helpDocument especifica el documento incrustado de ayuda de la entidad.
     *
     * Si utiliza la plataforma jee2, puede ser una URL o un <b>iframe</b> que incluya la URL del documento.
     *
     * También se puede especificar un documento incrustado diferente para cada formato de vista de la entidad, mediante las anotaciones
     * EntityTableView, EntityDetailView, EntityTreeView y EntityConsoleView.
     *
     * Las vistas utilizarán el documento incrustado definido para el correspondiente formato de vista de la entidad, la entidad de la vista, el
     * módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, no habrá un
     * documento incrustado disponible para la vista.
     *
     * @return helpDocument
     */
    String helpDocument() default "";

    /**
     * helpFile especifica la ruta y el nombre del archivo de ayuda de la entidad.
     *
     * Si utiliza la plataforma jee2, la ruta del archivo debe ser relativa al subdirectorio especificado mediante el atributo extraordinario
     * HELP_RESOURCES_FOLDER del proyecto maestro, y cuyo valor por omisión es el subdirectorio resources/help/custom-made del directorio
     * src/main/webapp del módulo Web de la aplicación.
     *
     * Cada vista (página) de la entidad podría tener su propio archivo de ayuda, según lo establecido mediante el elemento helpFileAutoName. También
     * se puede especificar un archivo diferente para cada formato de vista de la entidad, mediante las anotaciones EntityTableView, EntityDetailView,
     * EntityTreeView y EntityConsoleView.
     *
     * La vista que no tenga su propio archivo de ayuda utilizará el definido para el correspondiente formato de vista de la entidad, la entidad de la
     * vista, el módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, la
     * página de ayuda no estará disponible para la vista.
     *
     * @return helpFile
     */
    String helpFile() default "";

    /**
     * helpFileAutoName especifica el método de generación automática del nombre del archivo de ayuda de las vistas (páginas) de la entidad. Su valor
     * es uno de los elementos de la enumeración HelpFileAutoName. Seleccione DISPLAY o ENTITY para que generar automáticamente el nombre del archivo
     * de ayuda de las vistas con el nombre de la vista o con el de la entidad de la vista, respectivamente; alternativamente, omita el elemento o
     * seleccione NONE para utilizar el valor predeterminado del atributo. Seleccione ENTITY para utilizar los archivos de ayuda generados por la
     * clase utilitaria <b>WebAppHelp</b>. El valor predeterminado del atributo es el valor del elemento helpFileAutoName de la anotación
     * ProjectModule del módulo al que pertenece la entidad.
     *
     * @return helpFileAutoName
     */
    HelpFileAutoName helpFileAutoName() default HelpFileAutoName.UNSPECIFIED;

    /**
     * helpFileAutoType especifica el tipo (la extensión sin el punto) de los nombres de archivo de ayuda generados automáticamente. Este elemento es
     * relevante solo si el valor especificado, o determinado, para el elemento helpFileAutoName es DISPLAY o ENTITY. Su valor puede ser <b>html</b>,
     * <b>xhtml</b>, <b>pdf</b>, <b>gif</b>, <b>jpg</b>, <b>jpeg</b> o <b>png</b>. Especifique <b>xhtml</b> para utilizar los archivos de ayuda
     * generados por la clase utilitaria <b>WebAppHelp</b>. El valor predeterminado del atributo es el valor del elemento helpFileAutoType de la
     * anotación ProjectModule del módulo al que pertenece la entidad.
     *
     * @return helpFileAutoType
     */
    String helpFileAutoType() default ""; // Constants.DEFAULT_HELP_FILE_TYPE;

    /**
     * startWith específica el valor de la clave primaria de la primera instancia definida en la meta entidad. Su valor debe ser un número entero
     * entre 0 y 2.147.483.647. El valor predeterminado es 1.
     *
     * @return startWith
     */
    int startWith() default 1;

}
