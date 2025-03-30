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
 * La anotación EntityReferenceDisplay se utiliza para configurar la forma en que las vistas (páginas) muestran el valor de la referencia (propiedad o
 * parámetro que hace referencia a otra entidad) y otros atributos relacionados.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface EntityReferenceDisplay {

    /**
     * avatar indica si la imagen de la entidad se debe utilizar como avatar, o no, en las vistas (páginas) que muestran el valor de la referencia.
     * Este elemento es relevante solo si la entidad tiene avatar, es decir, una propiedad imagen cuyo atributo avatarShape (valor especificado, o
     * determinado, para el elemento avatarShape de la anotación ImageProperty) sea distinto de NONE. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para utilizar la imagen como avatar; en caso contrario, seleccione FALSE. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return avatar
     */
    Kleenean avatar() default Kleenean.UNSPECIFIED; // Kleenean.TRUE;

    /**
     * style especifica la forma en que las vistas (páginas) muestran el valor de la referencia. Su valor es uno de los elementos de la enumeración
     * EntityReferenceStyle. Seleccione CHARACTER_KEY, NAME, CHARACTER_KEY_AND_NAME o NAME_AND_CHARACTER_KEY para que la vista muestre la clave
     * alfanumérica (o de negocio), el nombre, la clave alfanumérica y el nombre, o el nombre y la clave alfanumérica, respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es NAME. Si el valor es alguna de las opciones que incluyen NAME, y la entidad referenciada no tiene nombre, la página mostrará la
     * clave alfanumérica en su lugar. Si el valor es alguna de las opciones que incluyen CHARACTER_KEY, y la entidad referenciada no tiene clave
     * alfanumérica, la página mostrará la clave primaria en su lugar.
     *
     * @return listStyle
     */
    EntityReferenceStyle style() default EntityReferenceStyle.UNSPECIFIED;

    /**
     * filter especifica la propiedad de la entidad referenciada que será utilizada por la función de filtro rápido de las vistas (páginas) de
     * presentación tabular. Su valor es uno de los elementos de la enumeración EntityReferenceProperty. Seleccione CHARACTER_KEY o NAME para que la
     * vista utilice la clave alfanumérica (o de negocio) o el nombre, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED
     * para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es CHARACTER_KEY. Si se selecciona NAME, y la entidad
     * referenciada no tiene nombre, la página utilizará la clave alfanumérica en su lugar. Si se selecciona CHARACTER_KEY, y la entidad referenciada
     * no tiene clave alfanumérica, la página utilizará la clave primaria en su lugar.
     *
     * @return filter
     */
    EntityReferenceProperty filter() default EntityReferenceProperty.UNSPECIFIED;

    /**
     * filterType especifica el tipo de componente que será utilizado para filtrar las filas visibles en las vistas (páginas) de presentación tabular.
     * Su valor es uno de los elementos de la enumeración EntityReferenceFilterType. Seleccione LIST para que la vista utilice una lista desplegable
     * (drop-down list); esta opción solo es válida si la entidad referenciada es una enumeración. Seleccione TEXTBOX para que la vista utilice un
     * cuadro de texto. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es TEXTBOX.
     *
     * @return filterType
     */
    EntityReferenceFilterType filterType() default EntityReferenceFilterType.UNSPECIFIED;

    /**
     * sort especifica la propiedad de la entidad referenciada que será utilizada por la función de ordenado rápido de las vistas (páginas) de
     * presentación tabular. Su valor es uno de los elementos de la enumeración EntityReferenceProperty. Seleccione CHARACTER_KEY o NAME para que la
     * vista utilice la clave alfanumérica (o de negocio) o el nombre, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED
     * para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es CHARACTER_KEY. Si se selecciona NAME, y la entidad
     * referenciada no tiene nombre, la página utilizará la clave alfanumérica en su lugar. Si se selecciona CHARACTER_KEY, y la entidad referenciada
     * no tiene clave alfanumérica, la página utilizará la clave primaria en su lugar.
     *
     * @return sort
     */
    EntityReferenceProperty sort() default EntityReferenceProperty.UNSPECIFIED;

}
