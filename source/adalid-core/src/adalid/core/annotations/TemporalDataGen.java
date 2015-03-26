/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.enums.DataGenTemporalInterval;
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
public @interface TemporalDataGen {

    DataGenType type() default DataGenType.UNSPECIFIED;

    int start() default 1;

    int stop() default 10000; // Integer.MAX_VALUE;

    int step() default 1;

    int nullable() default 0; // 0 <= nullable <= 100

    String min() default "";

    String max() default "";

    DataGenTemporalInterval interval() default DataGenTemporalInterval.UNSPECIFIED;

    String function() default "";

}
