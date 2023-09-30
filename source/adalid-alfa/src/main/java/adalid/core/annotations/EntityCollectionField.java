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
 * La anotación EntityCollectionField se utiliza para establecer atributos de colecciones (propiedades EntityCollection).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntityCollectionField {

    /**
     * access especifica el tipo de control de acceso de la colección. Su valor es uno de los elementos de la enumeración PropertyAccess. Seleccione
     * RESTRICTED_WRITING o RESTRICTED_READING para especificar acceso restringido de escritura o lectura, respectivamente. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para especificar acceso no restringido.
     *
     * @return access
     */
    PropertyAccess access() default PropertyAccess.UNSPECIFIED;

    /**
     * format especifica el orden de búsqueda de la vista (página) para entrada de datos de la colección. Su valor es uno de los elementos de la
     * enumeración DataEntryFormat. Seleccione DETAIL_OR_TABLE o TABLE_OR_DETAIL para buscar primero la vista de formato detallado o tabular,
     * respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es DETAIL_OR_TABLE.
     *
     * @return format
     */
    DataEntryFormat format() default DataEntryFormat.UNSPECIFIED;

    /**
     * create indica si la colección es, o no, requerida por la operación insert de las vistas (páginas) de registro. Este elemento es relevante solo
     * si el valor especificado, o determinado, para el elemento cascade de la anotación OneToMany de la colección es ALL o incluye PERSIST. Su valor
     * es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la colección es requerida por la operación insert; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es TRUE si el elemento cascade de la anotación OneToMany de la colección incluye PERSIST; en caso contrario es
     * FALSE.
     *
     * @return create
     */
    Kleenean create() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * update indica si la colección es, o no, requerida por la operación update de las vistas (páginas) de registro. Este elemento es relevante solo
     * si el valor especificado, o determinado, para el elemento cascade de la anotación OneToMany de la colección es ALL o incluye MERGE. Su valor es
     * uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la colección es requerida por la operación update; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es TRUE si el elemento cascade de la anotación OneToMany de la colección incluye MERGE; en caso contrario es FALSE.
     *
     * @return update
     */
    Kleenean update() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * detail indica si la colección es, o no, visible en las vistas (páginas) de consulta y registro detallado. Este elemento es relevante solo si el
     * valor especificado, o determinado, para el elemento cascade de la anotación OneToMany de la colección es ALL o incluye REFRESH. Su valor es uno
     * de los elementos de la enumeración Kleenean. Seleccione TRUE si la colección es visible; en caso contrario, seleccione FALSE. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE si
     * el elemento cascade de la anotación OneToMany de la colección incluye REFRESH; en caso contrario es FALSE.
     *
     * @return detail
     */
    Kleenean detail() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * inlineHelp especifica el atributo de la colección que se debe utilizar como ayuda en línea. Su valor es uno de los elementos de la enumeración
     * InlineHelpType. Seleccione SHORT_DESCRIPTION para utilizar la descripción corta de la colección, establecida con el método
     * setDefaultShortDescription o con el método setLocalizedShortDescription. Seleccione DESCRIPTION para utilizar la descripción corta de la
     * colección, si ésta fue establecida; o, de lo contrario, la descripción de la colección, establecida con el método setDefaultDescription o con
     * el método setLocalizedDescription. Seleccione NONE si desea que la colección no tenga ayuda en línea. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es DESCRIPTION.
     *
     * @return access
     */
    InlineHelpType inlineHelp() default InlineHelpType.UNSPECIFIED;

}
