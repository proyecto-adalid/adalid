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
 * La anotación InstanceReference se utiliza para designar un meta parámetro como referencia a la instancia de la entidad de la operación. Por lo
 * tanto, solo se puede designar como referencia a la instancia a meta parámetros de la clase de la entidad a la que corresponde la operación. Cada
 * operación puede tener una o referencia a la instancia. Si no tiene referencia a la instancia, la operación es una operación de clase, es decir, una
 * operación que, al ejecutarse, afecta un número indeterminado de instancias de la clase.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InstanceReference {

    boolean value() default true;

}
