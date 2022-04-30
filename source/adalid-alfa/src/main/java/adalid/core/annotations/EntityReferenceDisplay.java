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
     * style especifica la forma en que las vistas (páginas) muestran el valor de la referencia. Su valor es uno de los elementos de la enumeración
     * EntityReferenceStyle. Seleccione CHARACTER_KEY, NAME, CHARACTER_KEY_AND_NAME o NAME_AND_CHARACTER_KEY para que la vista muestre la clave
     * alfanumérica (o de negocio), el nombre, la clave alfanumérica y el nombre, o el nombre y la clave alfanumérica, respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es CHARACTER_KEY_AND_NAME. Si se selecciona alguna de las opciones que incluyen NAME, y la entidad referenciada no tiene nombre, la
     * página mostrará la clave alfanumérica en su lugar. Si se selecciona alguna de las opciones que incluyen CHARACTER_KEY, y la entidad
     * referenciada no tiene clave alfanumérica, la página mostrará la clave primaria en su lugar.
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
