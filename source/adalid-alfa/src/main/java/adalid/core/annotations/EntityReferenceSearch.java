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
 * La anotación EntityReferenceSearch se utiliza para configurar la forma en que las vistas (páginas) de implementan la búsqueda y/o selección del
 * valor de la referencia (propiedad o parámetro que hace referencia a otra entidad).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface EntityReferenceSearch {

    /**
     * searchType especifica el tipo de búsqueda y/o selección. Su valor es uno de los elementos de la enumeración SearchType. Seleccione LIST, RADIO,
     * DISPLAY o NONE para buscar y/o seleccionar mediante una lista desplegable (drop-down list), un grupo de botones de radio (radio buttons), una
     * vista (página), o para no implementar ningún mecanismo de búsqueda y/o selección, respectivamente. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es LIST, si la entidad
     * corresponde a una enumeración; y DISPLAY, en los demás casos.
     *
     * @return searchType
     */
    SearchType searchType() default SearchType.UNSPECIFIED;

    /**
     * listStyle especifica el tipo de lista desplegable que se utiliza para la selección. Este elemento es relevante solo cuando se selecciona
     * mediante una lista desplegable (drop-down list). Su valor es uno de los elementos de la enumeración ListStyle. Seleccione CHARACTER_KEY, NAME,
     * CHARACTER_KEY_AND_NAME, NAME_AND_CHARACTER_KEY, PRIMARY_KEY_AND_CHARACTER_KEY o PRIMARY_KEY_AND_NAME para que la lista desplegable muestre la
     * clave alfanumérica (o de negocio), el nombre, la clave alfanumérica y el nombre, el nombre y la clave alfanumérica, la clave primaria y la
     * clave alfanumérica, o la clave primaria y el nombre, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es CHARACTER_KEY.
     *
     * @return listStyle
     */
    ListStyle listStyle() default ListStyle.UNSPECIFIED;

    /**
     * radioColumns especifica el número de botones por linea en el grupo de botones de radio que se utiliza para la selección. Este elemento es
     * relevante solo cuando se selecciona mediante un grupo de botones de radio (radio buttons). Debe ser un número del 0 al 10. Si su valor es 0,
     * entonces todas las opciones se muestran en una sola linea. El valor predeterminado del atributo es 0.
     *
     * @return radioColumns
     */
    int radioColumns() default 0;

    /**
     * displayFormat especifica el tipo de vista (página) que se utiliza para la búsqueda y selección. Este elemento es relevante solo cuando se busca
     * y selecciona mediante una vista (página). Su valor es uno de los elementos de la enumeración SearchDisplayFormat. Seleccione TABLE o TREE para
     * utilizar una vista (página) de presentación tabular o jerárquica (árbol), respectivamente. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TABLE.
     *
     * @return displayFormat
     */
    SearchDisplayFormat displayFormat() default SearchDisplayFormat.UNSPECIFIED;

    /**
     * displayMode especifica el tipo de vista (página) que se utiliza para la búsqueda y selección. Este elemento es relevante solo cuando se busca y
     * selecciona mediante una vista (página). Su valor es uno de los elementos de la enumeración DisplayMode. Seleccione READING o WRITING para
     * utilizar una vista (página) de solo consulta o una vista (página) de registro, respectivamente. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es READING.
     *
     * @return displayMode
     */
    DisplayMode displayMode() default DisplayMode.UNSPECIFIED;

}
