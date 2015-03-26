/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.enums.Kleenean;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityClass {

    Kleenean independent() default Kleenean.UNSPECIFIED; // TRUE

    ResourceType resourceType() default ResourceType.UNSPECIFIED;

    ResourceGender resourceGender() default ResourceGender.UNSPECIFIED;

//  String propertiesPrefix() default "";
//
//  String propertiesSuffix() default "";
//
    int startWith() default 1;

}
