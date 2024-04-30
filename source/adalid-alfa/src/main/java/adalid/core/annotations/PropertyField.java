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
 * La anotación PropertyField se utiliza para establecer atributos básicos de la propiedad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyField {

    /**
     * access especifica el tipo de control de acceso de la propiedad. Su valor es uno de los elementos de la enumeración PropertyAccess. Seleccione
     * RESTRICTED_WRITING o RESTRICTED_READING para especificar acceso restringido de escritura o lectura, respectivamente. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para especificar acceso no restringido.
     *
     * @return access
     */
    PropertyAccess access() default PropertyAccess.UNSPECIFIED;

    /**
     * auditable indica si la propiedad se debe incluir, o no, en las pistas de auditoría de las funciones de insert y update de la tabla de la base
     * de datos que corresponde a la entidad. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para incluir la propiedad;
     * en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es FALSE para propiedades que corresponden a "objetos binarios" o a contraseñas; y TRUE para las
     * demás propiedades.
     *
     * @return auditable
     */
    Kleenean auditable() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * password indica si la propiedad es, o no, una contraseña. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la
     * propiedad es una contraseña; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return password
     */
    Kleenean password() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * required indica si la propiedad es, o no, obligatoriamente requerida por las vistas (páginas) de registro. Su valor es uno de los elementos de
     * la enumeración Kleenean. Seleccione TRUE si la propiedad es obligatoriamente requerida; en caso contrario, seleccione FALSE. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE si
     * la propiedad es una columna calculable o que admite nulos (vea Anotación ColumnField), es la columna discriminadora (vea Anotación
     * DiscriminatorColumn), o tiene valor por omisión (vea Método setDefaultValue); en caso contrario es TRUE.
     *
     * @return required
     */
    Kleenean required() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * hidden indica si la propiedad permanece, o no, oculta en las vistas (páginas) e informes. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE si la propiedad permanece oculta; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return hidden
     */
    Kleenean hidden() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * create indica si la propiedad es, o no, requerida por la operación insert de las vistas (páginas) de registro. Este elemento es relevante solo
     * si la propiedad es insertable (vea Anotación ColumnField). Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la
     * propiedad es requerida por la operación insert; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE si la propiedad no es requerida o
     * tiene valor por omisión incondicional en la operación insert (vea los elementos required y defaultCondition de esta misma anotación), o no es
     * una columna insertable (vea Anotación ColumnField), o está enlazada a un parámetro de un proceso de instancia (vea Anotación ParameterField);
     * en caso contrario es TRUE.
     *
     * @return create
     */
    Kleenean create() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * update indica si la propiedad es, o no, requerida por la operación update de las vistas (páginas) de registro. Este elemento es relevante solo
     * si la propiedad es actualizable (vea Anotación ColumnField). Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la
     * propiedad es requerida por la operación update; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE si la propiedad tiene valor por
     * omisión incondicional en la operación update (vea el elemento defaultCondition de esta misma anotación), o no es una columna actualizable (vea
     * Anotación ColumnField), o está enlazada a un parámetro de un proceso de instancia (vea Anotación ParameterField); en caso contrario es TRUE.
     *
     * @return update
     */
    Kleenean update() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * createViaAPI indica si la propiedad es, o no, requerida por la operación insert del API. Este elemento no es relevante si la propiedad está
     * designada como clave primaria, columna discriminadora, secuencia, o versión de la entidad; si es la clave primaria, entonces es requerida; si
     * es la columna discriminadora, la secuencia o la versión, entonces no es requerida. Tampoco es relevante si la propiedad no es insertable, es
     * calculable o tiene valor por omisión incondicional en la operación insert; tales propiedades no son requeridas. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es requerida por la operación insert del API; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE si la propiedad está enlazada a un parámetro de un proceso de instancia; en caso contrario es TRUE
     *
     * @return createViaAPI
     */
    Kleenean createViaAPI() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * updateViaAPI indica si la propiedad es, o no, requerida por la operación update del API. Este elemento no es relevante si la propiedad está
     * designada como clave primaria, columna discriminadora, secuencia, o versión de la entidad; si es la versión, entonces es requerida; si es la
     * clave primaria, la columna discriminadora o la secuencia, entonces no es requerida. Tampoco es relevante si la propiedad no es actualizable, es
     * calculable o tiene valor por omisión incondicional en la operación update; tales propiedades no son requeridas. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es requerida por la operación update del API; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE si la entidad es una enumeración o si la propiedad está enlazada a un parámetro de un proceso de
     * instancia; en caso contrario es TRUE.
     *
     * @return updateViaAPI
     */
    Kleenean updateViaAPI() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * search indica si la propiedad es, o no, un criterio de búsqueda básica en las vistas (páginas) de consulta y registro. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es un criterio de búsqueda básica; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE si la propiedad no es una contraseña y no tiene acceso restringido de lectura (vea los elementos password y access de esta
     * misma anotación), y además cumple al menos una de las siguientes condiciones: no admite valores duplicados (vea Anotación ColumnField); o es
     * una clave única (vea Anotación UniqueKey); o es la clave de negocio (vea Anotación BusinessKey); o es la propiedad nombre (vea Anotación
     * NameProperty); o es la columna discriminadora (vea Anotación DiscriminatorColumn); o es el indicador de inactividad (vea Anotación
     * InactiveIndicator); o es visible en las vistas (páginas) de consulta y registro tabular (vea el elemento table de esta misma anotación); en
     * caso contrario, es FALSE.
     *
     * @return search
     */
    Kleenean search() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * filter indica si la propiedad es, o no, un criterio de búsqueda avanzada en las vistas (páginas) de consulta y registro. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es un criterio de búsqueda avanzada; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es FALSE si la propiedad es una contraseña (vea el elemento password de esta misma anotación); en caso contrario es TRUE.
     *
     * @return filter
     */
    Kleenean filter() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * sort indica si la propiedad es, o no, un criterio de ordenamiento en las vistas (páginas) de consulta y registro. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es un criterio de ordenamiento; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return sort
     */
    Kleenean sort() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * table indica si la propiedad es, o no, visible en las vistas (páginas) de consulta y registro tabular. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE si la propiedad es visible; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE si la propiedad no
     * admite valores duplicados (vea Anotación ColumnField); o es una clave única (vea Anotación UniqueKey); o es la clave de negocio (vea Anotación
     * BusinessKey); o es la propiedad nombre (vea Anotación NameProperty); o es la columna discriminadora (vea Anotación DiscriminatorColumn); o es
     * el indicador de inactividad (vea Anotación InactiveIndicator); o es requerida (vea el elemento required de esta misma anotación); en caso
     * contrario es FALSE.
     *
     * @return table
     */
    Kleenean table() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * detail indica si la propiedad es, o no, visible en las vistas (páginas) de consulta y registro detallado. Su valor es uno de los elementos de
     * la enumeración Kleenean. Seleccione TRUE si la propiedad es visible; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return detail
     */
    Kleenean detail() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * column indica si la propiedad puede ser, o no, una columna de vistas definidas por el usuario final. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE si la propiedad puede ser una columna; en caso contrario, seleccione FALSE. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE si la
     * propiedad es una contraseña (vea el elemento password de esta misma anotación); en caso contrario es TRUE.
     *
     * @return column
     */
    Kleenean column() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * report indica si la propiedad es, o no, incluida en el informe producido por la operación report de las vistas (páginas) de consulta y
     * registro. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es incluida; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es TRUE si la propiedad no admite valores duplicados (vea Anotación ColumnField); o es una clave única (vea
     * Anotación UniqueKey); o es la clave de negocio (vea Anotación BusinessKey); o es la propiedad nombre (vea Anotación NameProperty); o es la
     * columna discriminadora (vea Anotación DiscriminatorColumn); o es el indicador de inactividad (vea Anotación InactiveIndicator); o es requerida
     * (vea el elemento required de esta misma anotación); en caso contrario, o si la propiedad es una contraseña (vea el elemento password de esta
     * misma anotación), es FALSE.
     *
     * @return report
     */
    Kleenean report() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * export indica si la propiedad es, o no, incluida en el informe producido por la operación export de las vistas (páginas) de consulta y
     * registro. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es incluida; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE si la propiedad es una contraseña (vea el elemento password de esta misma anotación); en caso contrario es
     * TRUE.
     *
     * @return export
     */
    Kleenean export() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * headertextless indica si la propiedad es, o no, una propiedad sin título en las vistas (páginas) de consulta y registro tabular. Su valor es
     * uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es una propiedad sin título; en caso contrario, seleccione
     * FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado
     * del atributo es FALSE
     *
     * @return headertextless
     */
    Kleenean headertextless() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * heading indica si la propiedad es, o no, parte del encabezado en las vistas (páginas) Maestro/Detalle, donde la entidad es el maestro. Su valor
     * es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es una propiedad del encabezado; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE
     *
     * @return heading
     */
    Kleenean heading() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * overlay indica si la propiedad es, o no, parte del panel de vista rápida en las vistas (páginas) donde la entidad es referenciada. Su valor es
     * uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es una propiedad del panel de vista rápida; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE
     *
     * @return overlay
     */
    Kleenean overlay() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * prominent indica si la propiedad es, o no, prominente. Una propiedad prominente debería ser visible aun cuando no sea actualizable. Su valor es
     * uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es prominente; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es FALSE
     *
     * @return prominent
     */
    Kleenean prominent() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * immutable indica si la propiedad es, o no, inmutable. Solo aplica a propiedades de una enumeración. El valor de las propiedades inmutables se
     * almacena en el bundle de recursos básicos de la aplicación generada, de modo que el valor puede ser obtenido sin acceder a la base de datos. Su
     * valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad es inmutable; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es FALSE
     *
     * @return immutable
     */
    Kleenean immutable() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * serializable indica si la propiedad es, o no, serializable. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la
     * propiedad es serializable; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es FALSE si la propiedad es calculable, o es un password, o es de tipo
     * binario; de lo contrario, TRUE.
     *
     * @return serializable
     */
    Kleenean serializable() default Kleenean.UNSPECIFIED;

    /**
     * serializableIUID indica si la propiedad se debe serializar, o no, como un IUID (Item Unique Identification). No aplica a propiedades primitivas
     * (solo a referencias a entidades). Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la propiedad se debe
     * serializar como un IUID; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es FALSE si la propiedad es serializable; de lo contrario, TRUE.
     *
     * @return serializableIUID
     */
    Kleenean serializableIUID() default Kleenean.UNSPECIFIED;

    /**
     * defaultCondition especifica en qué circunstancias aplicar el valor por omisión de la propiedad. Su valor es uno de los elementos de la
     * enumeración DefaultCondition. Seleccione IF_NULL_ON_INSERT, IF_NULL_ON_UPDATE, IF_NULL, UNCONDITIONALLY_ON_INSERT, UNCONDITIONALLY_ON_UPDATE o
     * UNCONDITIONALLY para aplicar el valor por omisión si el valor es nulo en la operación insert; o si el valor es nulo en la operación update; o
     * si el valor es nulo, tanto en insert como en update; o incondicionalmente en la operación insert; o incondicionalmente en la operación update;
     * o incondicionalmente tanto en insert como en update, respectivamente. Alternativamente, omita el elemento para utilizar el valor predeterminado
     * del atributo. El valor predeterminado del atributo es IF_NULL.
     *
     * @return defaultCondition
     */
    DefaultCondition defaultCondition() default DefaultCondition.UNSPECIFIED;

    /**
     * defaultCheckpoint especifica en qué componente o componentes se debe aplicar el valor por omisión de la propiedad. Su valor es uno de los
     * elementos de la enumeración Checkpoint. Seleccione DATABASE_TRIGGER o USER_INTERFACE si el valor por omisión se debe aplicar solamente en un
     * disparador (trigger) de la base de datos o en la interfaz de usuario, respectivamente. Seleccione WHEREVER_POSSIBLE para aplicar el valor por
     * omisión en ambos componentes (primero en la interfaz de usuario y luego en el disparador), siempre que sea posible; esta es la opción
     * predeterminada. Cuando se especifica WHEREVER_POSSIBLE, si el valor especificado para el elemento defaultCondition es UNCONDITIONALLY,
     * UNCONDITIONALLY_ON_INSERT o UNCONDITIONALLY_ON_UPDATE, el valor aplicado en el disparador se sobrepone al valor aplicado en la interfaz de
     * usuario.
     *
     * @return defaultCheckpoint
     */
    Checkpoint defaultCheckpoint() default Checkpoint.UNSPECIFIED;

    /**
     * defaultFunction especifica el nombre de la función definida por el usuario que se utilizará para obtener el valor por omisión de la propiedad.
     * La función solo se ejecutará en los disparadores (triggers) de la base de datos; por lo tanto, este elemento es relevante si el valor
     * especificado para el elemento defaultCheckpoint es DATABASE_TRIGGER; también si el elemento defaultCheckpoint es WHEREVER_POSSIBLE, pero solo
     * si el valor del elemento defaultCondition es UNCONDITIONALLY, UNCONDITIONALLY_ON_INSERT o UNCONDITIONALLY_ON_UPDATE, ya que el valor se aplica
     * en el disparador sin importar el valor aplicado en la interfaz de usuario. La función puede recibir como parámetros valores constantes o
     * referencias a otras columnas de la misma tabla, calificadas con el seudo-registro
     * <b>new</b>; y si el valor especificado para el elemento defaultCondition es IF_NULL_ON_UPDATE o UNCONDITIONALLY_ON_UPDATE, entonces tales
     * referencias también podrían ser calificadas con el seudo-registro <b>old</b>.
     *
     * @return defaultFunction
     */
    String defaultFunction() default "";

    /**
     * masterHeadingSnippet especifica la ruta y el nombre del snippet de la propiedad en el encabezado del Maestro en las vistas (páginas)
     * Maestro/Detalle.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return masterHeadingSnippet
     */
    String masterHeadingSnippet() default "";

    /**
     * readingTableSnippet especifica la ruta y el nombre del snippet de la propiedad en las vistas (páginas) de consulta de presentación tabular.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingTableSnippet
     */
    String readingTableSnippet() default "";

    /**
     * writingTableSnippet especifica la ruta y el nombre del snippet de la propiedad en las vistas (páginas) de registro de presentación tabular.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingTableSnippet
     */
    String writingTableSnippet() default "";

    /**
     * readingDetailSnippet especifica la ruta y el nombre del snippet de la propiedad en las vistas (páginas) de consulta de presentación detallada.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return readingDetailSnippet
     */
    String readingDetailSnippet() default "";

    /**
     * writingDetailSnippet especifica la ruta y el nombre del snippet de la propiedad en las vistas (páginas) de registro de presentación detallada.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return writingDetailSnippet
     */
    String writingDetailSnippet() default "";

    /**
     * anchor especifica el nombre de otra propiedad a continuación de la cual se muestra esta propiedad en las vistas (páginas) de consulta y
     * registro.
     *
     * @return anchor
     */
    String anchor() default "";

    /**
     * anchorType especifica el tipo de anclaje de la propiedad. Este elemento es relevante solo si la propiedad está anclada a otra propiedad, usando
     * el elemento anchor. Su valor es uno de los elementos de la enumeración AnchorType. Seleccione UNLINKED para mostrar la propiedad como un
     * elemento contiguo pero independiente de su propiedad ancla. Seleccione BLOCK para mostrar la propiedad conjuntamente con su propiedad ancla,
     * como un elemento de bloque, comenzando en una nueva línea y ocupando todo el ancho disponible. Seleccione INLINE para mostrar la propiedad
     * conjuntamente con su propiedad ancla, como un elemento en línea. Seleccione INLINE_BLOCK para mostrar la propiedad como un elemento en línea,
     * para la lectura; y como un elemento de bloque, para la escritura. Alternativamente, omita el elemento para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es UNLINKED.
     *
     * @return anchorType
     */
    AnchorType anchorType() default AnchorType.UNLINKED;

    /**
     * responsivePriority específica la prioridad de la columna correspondiente a la propiedad en las vistas (páginas) de consulta y registro tabular.
     * La prioridad solo es relevante si el elemento table es TRUE y el modo "responsive" de la tabla es PRIORITY. Su valor debe ser un número entero
     * entre 0 y 6. Un valor más bajo significa una prioridad más alta. Las columnas con valores de prioridad más bajos se mostrarán primero en
     * pantallas más pequeñas. A medida que aumenta el tamaño de la pantalla, se mostrarán las columnas con valores de prioridad más altos.
     *
     * @return sequence
     */
    int responsivePriority() default 0;

    /**
     * sequence específica el número de secuencia o posición relativa en la que se muestra la propiedad en las vistas (páginas) de consulta y
     * registro. Su valor debe ser un número entero entre 0 y 2.147.483.647. Alternativamente, omita el elemento para utilizar el valor predeterminado
     * del atributo. El valor predeterminado del atributo es 0. Si todas las propiedades tienen el mismo número de secuencia (0 o cualquier otro),
     * entonces las vistas las muestran en el mismo orden en el que las meta propiedades están definidas en la meta entidad.
     *
     * @return sequence
     */
    int sequence() default 0;

    /**
     * inlineHelp especifica el atributo de la propiedad que se debe utilizar como ayuda en línea. Su valor es uno de los elementos de la enumeración
     * InlineHelpType. Seleccione SHORT_DESCRIPTION para utilizar la descripción corta de la propiedad, establecida con el método
     * setDefaultShortDescription o con el método setLocalizedShortDescription. Seleccione DESCRIPTION para utilizar la descripción corta de la
     * propiedad, si ésta fue establecida; o, de lo contrario, la descripción de la propiedad, establecida con el método setDefaultDescription o con
     * el método setLocalizedDescription. Seleccione NONE si desea que la propiedad no tenga ayuda en línea. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es DESCRIPTION.
     *
     * @return access
     */
    InlineHelpType inlineHelp() default InlineHelpType.UNSPECIFIED;

}
