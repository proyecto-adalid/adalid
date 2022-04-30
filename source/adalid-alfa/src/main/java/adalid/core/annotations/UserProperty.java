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
 * La anotación UserProperty se utiliza para designar una referencia (propiedad que hace referencia a otra entidad) como propiedad usuario de la
 * entidad. Cada entidad puede tener una sola propiedad usuario. Solo se puede designar como propiedad usuario a referencias a la entidad Usuario del
 * proyecto (vea Método setUserEntityClass). La propiedad usuario sirve para obtener el valor del usuario que inserta o modifica cada fila en los
 * disparadores (triggers) de base de datos de la tabla que corresponde a la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UserProperty {

    boolean value() default true;

}
