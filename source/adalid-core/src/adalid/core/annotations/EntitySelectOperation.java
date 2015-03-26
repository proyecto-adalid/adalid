/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.Constants;
import adalid.core.enums.Kleenean;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.SortOption;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntitySelectOperation {

    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

//  Kleenean restricted() default Kleenean.UNSPECIFIED; // TRUE
    OperationAccess access() default OperationAccess.UNSPECIFIED; // RESTRICTED

    int rowsLimit() default Constants.DEFAULT_SELECT_ROWS_LIMIT;

    SortOption sortOption() default SortOption.ASC;

}
