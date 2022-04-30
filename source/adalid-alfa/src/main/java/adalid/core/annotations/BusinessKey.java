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
 * La anotación BusinessKey se utiliza para designar una propiedad como clave de negocio de la entidad. Cada entidad puede tener una sola clave de
 * negocio. Las claves de negocio, también llamadas claves naturales, son claves candidatas (por lo tanto no admiten valores duplicados) que
 * representan atributos del mundo real con los que los usuarios de la aplicación están familiarizados y, por lo tanto, permiten identificar
 * fácilmente las entidades. Solo se puede designar como clave de negocio a propiedades de la clase StringProperty.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BusinessKey {

    boolean value() default true;

}
