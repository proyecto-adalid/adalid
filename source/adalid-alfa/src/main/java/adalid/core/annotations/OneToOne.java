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
 * La anotación OneToOne se utiliza para establecer atributos de una referencia (propiedad que hace referencia a otra entidad) para relaciones con
 * cardinalidad uno-a-uno.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OneToOne {

    /**
     * main indica si la relación con la entidad referenciada corresponde, o no, a la relación principal con esa entidad. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE si la relación es la principal; en caso contrario, seleccione FALSE. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return main
     */
    Kleenean main() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * fetch especifica si la operación fetch obtiene la entidad relacionada simultáneamente con la entidad (EAGER) o posteriormente, por demanda
     * (LAZY). Su valor es uno de los elementos de la enumeración FetchType. Seleccione EAGER o LAZY o, alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es LAZY.
     *
     * @return fetch
     */
    FetchType fetch() default FetchType.UNSPECIFIED;

    /**
     * cascade especifica una o más operaciones que deben ser propagadas en cascada. Su valor es uno de los elementos de la enumeración CascadeType.
     * Omita el elemento o seleccione UNSPECIFIED para no propagar operaciones. Seleccione ALL para propagar todas las operaciones. Seleccione
     * PERSIST, MERGE, REMOVE, REFRESH y/o DETACH para propagar la operación persist, merge, remove, refresh y/o detach, respectivamente.
     *
     * @return cascade
     */
    CascadeType[] cascade() default CascadeType.UNSPECIFIED;

    /**
     * navigability especifica la navegabilidad entre las entidades relacionadas. Su valor es uno de los elementos de la enumeración Navigability.
     * Seleccione BIDIRECTIONAL o UNIDIRECTIONAL para especificar navegabilidad bidireccional o unidireccional, respectivamente. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es
     * UNIDIRECTIONAL. La navegabilidad bidireccional utiliza apuntadores en ambas entidades relacionadas para permitir ir de una a otra, en ambos
     * sentidos. La navegabilidad unidireccional solo utiliza apuntadores en la entidad que contiene la referencia hacia la entidad referenciada.
     *
     * @return navigability
     */
    Navigability navigability() default Navigability.UNSPECIFIED;

    /**
     * detailView especifica la disponibilidad de vistas (páginas) Maestro/Detalle de la entidad que tengan a la entidad referenciada como maestro. Su
     * valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE o FALSE para generar vistas detalladas, o ninguna vista,
     * respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE.
     *
     * @return detailView
     */
    Kleenean detailView() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * menu especifica si las vistas (páginas) Maestro/Detalle generadas deben ser accesibles desde el menú de contexto de la entidad. Su valor es uno
     * de los elementos de la enumeración Kleenean. Seleccione TRUE si las vistas deben ser accesibles; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return menu
     */
    Kleenean menu() default Kleenean.UNSPECIFIED;

    /**
     * viewSequence específica el número de secuencia o posición relativa en la que se muestran las vistas (páginas) Maestro/Detalle de la entidad. Su
     * valor debe ser un número entero entre 0 y 2.147.483.647. Alternativamente, omita el elemento para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es 0. Si todas las vistas tienen el mismo número de secuencia (0 o cualquier otro), entonces las
     * vistas las muestran ordenadas por el nombre de la vista.
     *
     * @return viewSequence
     */
    int viewSequence() default 0;

}
