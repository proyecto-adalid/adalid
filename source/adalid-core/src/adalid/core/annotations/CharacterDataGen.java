/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.enums.DataGenType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CharacterDataGen {

    DataGenType type() default DataGenType.UNSPECIFIED;

    int start() default 1;

    int stop() default 10000; // Integer.MAX_VALUE;

    int step() default 1;

    int nullable() default 0; // 0 <= nullable <= 100

    // 'a' for lower case letter
    // 'A' for upper case letter
    // '?' for lower or upper case letter
    // '0' for number
    // 'x' for lower case letter or number
    // 'X' for upper case letter or number
    // '*' for lower or upper case letter or number
    // '\' to precede an actual 'a', 'A', '?', '0', 'x', 'X', '*' or '\'
    String pattern() default "";

    String prefix() default "";

    String suffix() default "";

    String function() default "";

}
