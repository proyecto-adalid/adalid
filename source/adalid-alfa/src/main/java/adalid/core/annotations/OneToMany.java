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
import adalid.core.interfaces.Entity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación OneToMany se utiliza para establecer una asociación de varios valores con multiplicidad de uno a varios.
 *
 * El elemento targetEntity debe usarse para especificar la clase de entidad de destino y el elemento mappedBy debe usarse para especificar el campo
 * de relación o la propiedad de la entidad que es propietaria de la relación.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OneToMany {

    /**
     * targetEntity especifica la clase de entidad que es el objetivo de la asociación. La clase debe tener una relación bidireccional de varios-a-uno
     * con la entidad que define la colección.
     *
     * @return targetEntity
     */
    Class<? extends Entity> targetEntity() default Entity.class;

    /**
     * mappedBy especifica el nombre del campo que posee la relación. Ese campo es la referencia (propiedad que hace referencia a otra entidad)
     * definida en <b>targetEntity</b> que establece la relación bidireccional de varios-a-uno con la entidad que define la colección.
     *
     * @return mappedBy
     */
    String mappedBy() default "";

    /**
     * fetch especifica si la operación fetch obtiene la colección simultáneamente con la entidad (EAGER) o posteriormente, por demanda (LAZY). Su
     * valor es uno de los elementos de la enumeración FetchType. Seleccione EAGER o LAZY o, alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es EAGER si los elementos de la colección
     * son actualizables; de lo contrario, LAZY.
     *
     * @return fetch
     */
    FetchType fetch() default FetchType.UNSPECIFIED;

    /**
     * cascade especifica una o más operaciones que deben ser propagadas en cascada. Su valor es uno de los elementos de la enumeración CascadeType.
     * Seleccione UNSPECIFIED para no propagar operaciones. Seleccione ALL para propagar todas las operaciones. Seleccione PERSIST, MERGE, REMOVE,
     * REFRESH y/o DETACH para propagar la operación persist, merge, remove, refresh y/o detach, respectivamente. Alternativamente, omita el elemento
     * para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es {CascadeType.PERSIST, CascadeType.REFRESH}
     *
     * @return cascade
     */
    CascadeType[] cascade() default {}; // CascadeType.PERSIST, CascadeType.REFRESH

    /**
     * orphanRemoval especifica si se aplica la operación de eliminación a las entidades que se han eliminado de la relación y si se conecta en
     * cascada la operación de eliminación a esas entidades. Su valor es uno de los elementos de la enumeración Kleenean. El valor predeterminado del
     * atributo es TRUE si los elementos de la colección son actualizables; de lo contrario, FALSE.
     *
     * @return orphanRemoval
     */
    Kleenean orphanRemoval() default Kleenean.UNSPECIFIED; // FALSE

}
