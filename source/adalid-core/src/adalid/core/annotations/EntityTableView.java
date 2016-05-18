/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.enums.Kleenean;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityTableView {

    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean inserts() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean updates() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean deletes() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean heading() default Kleenean.UNSPECIFIED; // FALSE

    int rows() default 10; // 1 <= rows <= 50
//
//  int width() default 1200; // 800 <= width <= 2400

}
