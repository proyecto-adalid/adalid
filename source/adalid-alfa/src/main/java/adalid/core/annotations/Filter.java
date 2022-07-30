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
 * La anotación Filter se utiliza para especificar los filtros automáticos que las vistas (páginas) implementan en la búsqueda del valor de la
 * referencia (propiedad o parámetro que hace referencia a otra entidad).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Deprecated
public @interface Filter {

    /**
     * inactive indica si se deben filtrar (ignorar), o no, las instancias de la entidad referenciada que se encuentran inactivas (eliminadas
     * lógicamente). Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para filtrar; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es FALSE.
     *
     * @return inactive
     */
    Kleenean inactive() default Kleenean.UNSPECIFIED;

    /**
     * owner indica si se deben filtrar (ignorar), o no, las instancias de la entidad referenciada que son propiedad de un usuario diferente al que
     * realiza la búsqueda (vea Anotación OwnerProperty). Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para filtrar;
     * en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return owner
     */
    Kleenean owner() default Kleenean.UNSPECIFIED;

    /**
     * segment indica si se deben filtrar (ignorar), o no, las instancias de la entidad referenciada que no pertenecen a uno de los segmentos
     * autorizados al usuario que realiza la búsqueda (vea Anotación SegmentProperty). Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE para filtrar; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar
     * el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return segment
     */
    Kleenean segment() default Kleenean.UNSPECIFIED;

}
