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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación StateProperty se utiliza para designar una propiedad como propiedad estado de la entidad. Cada entidad puede tener una sola propiedad
 * estado. Solo se puede designar como propiedad estado a propiedades que hacen referencia a enumeraciones.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StateProperty {

    boolean value() default true;

    /**
     * transitionUser especifica el nombre de la propiedad donde se almacena la referencia al usuario que ejecutó la operación de negocio que produjo
     * la última transición. La clase de la propiedad especificada debe ser la clase de la entidad <b>Usuario</b> del proyecto.
     *
     * @return transitionUser
     */
    String transitionUser() default "";

    /**
     * transitionDateTime especifica el nombre de la propiedad donde se almacena la fecha, o fecha y hora, de la última transición. La clase de la
     * propiedad especificada debe ser DateProperty o TimestampProperty.
     *
     * @return transitionDateTime
     */
    String transitionDateTime() default "";

}
