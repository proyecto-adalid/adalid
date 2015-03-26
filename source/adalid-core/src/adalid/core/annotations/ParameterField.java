/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.enums.Kleenean;
import adalid.core.enums.StandardRelationalOp;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ParameterField {

    Kleenean auditable() default Kleenean.UNSPECIFIED; // TRUE

    Kleenean password() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean required() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean hidden() default Kleenean.UNSPECIFIED; // FALSE

    Kleenean submit() default Kleenean.UNSPECIFIED; // FALSE

    String linkedField() default "";

    String linkedColumn() default "";

    StandardRelationalOp operator() default StandardRelationalOp.EQ;

    int sequence() default 0;

}
