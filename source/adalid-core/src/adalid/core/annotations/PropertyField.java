/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.enums.DefaultCondition;
import adalid.core.enums.Kleenean;
import adalid.core.enums.PropertyAccess;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyField {

    PropertyAccess access() default PropertyAccess.UNSPECIFIED;

    Kleenean auditable() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean password() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean required() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean hidden() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean create() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean update() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean search() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean filter() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean table() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean detail() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean report() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean export() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean submit() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean headertextless() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean heading() default Kleenean.UNSPECIFIED; // FALSE

    DefaultCondition defaultCondition() default DefaultCondition.IF_NULL;

    int sequence() default 0;

}
