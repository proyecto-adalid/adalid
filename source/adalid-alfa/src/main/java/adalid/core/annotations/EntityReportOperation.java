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

import adalid.core.*;
import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación EntityReportOperation se utiliza para establecer atributos de la operación report de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityReportOperation {

    /**
     * enabled indica si las vistas (páginas) de consulta y registro deben permitir, o no, reportar el resultado de la consulta. Su valor es uno de
     * los elementos de la enumeración Kleenean. Seleccione TRUE para permitir reportar; en caso contrario, seleccione FALSE. Alternativamente, omita
     * el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return enabled
     */
    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * rowsLimit específica el número de máximo de filas que se deben reportar. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento enabled es TRUE. Su valor debe ser un número entero entre 0 y 1.000.000. Utilice 0 cuando no exista límite. El
     * valor predeterminado es 10.000
     *
     * @return rowsLimit
     */
    int rowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    /**
     * sortOption especifica el criterio de ordenamiento por omisión de las filas reportadas. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la enumeración SortOption. Seleccione ASC o
     * DESC para exportar las filas ordenadas por el valor de su clave primaria, de manera ascendente o descendente, respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es ASC. Este criterio de ordenamiento se utiliza solo si no se establece otro criterio mediante el método setOrderBy.
     *
     * @return sortOption
     */
    SortOption sortOption() default SortOption.ASC;

}
