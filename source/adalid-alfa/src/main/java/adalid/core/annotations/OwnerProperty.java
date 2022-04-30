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
 * La anotación OwnerProperty se utiliza para designar una referencia (propiedad que hace referencia a otra entidad) como propiedad propietario de la
 * entidad. Cada entidad puede tener una sola propiedad propietario. Solo se puede designar como propiedad propietario a referencias a la entidad
 * Usuario del proyecto (vea Método setUserEntityClass). Al establecer la propiedad propietario, la entidad se convierte en personalizable, es decir,
 * en una entidad que permite implementar control de acceso personalizado (por usuario) a las instancias de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OwnerProperty {

    boolean value() default true;

}
