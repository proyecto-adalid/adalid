/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.annotations;

import adalid.core.Constants;
import adalid.core.enums.ReportQueryType;
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
public @interface ReportOperationClass {

    String name() default "";

    String view() default "";

    String viewField() default "";

    ReportQueryType type() default ReportQueryType.DYNAMIC;

    int rowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    int detailRowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    int summaryRowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    int chartRowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    SortOption sortOption() default SortOption.ASC;

}
