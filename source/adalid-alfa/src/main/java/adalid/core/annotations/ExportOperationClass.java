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
 * La anotación ExportOperationClass se utiliza para establecer atributos de meta operaciones que extienden la clase ExportOperation (operaciones de
 * negocio para generar archivos).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExportOperationClass {

    /**
     * name especifica el nombre alterno del procedimiento de generación del archivo.
     *
     * @return name
     */
    String name() default "";

    /**
     * view especifica el nombre de la vista SQL que se utiliza para la generación del archivo.
     *
     * @return view
     */
    String view() default "";

    /**
     * viewField especifica el nombre de la vista de la entidad que se utiliza para la generación del archivo. Si alguna de las propiedades de detalle
     * de la vista tiene una función de agregación, entonces la operación puede generar el archivo tanto detallado como resumido; en caso contrario,
     * solo puede generar el archivo detallado.
     *
     * @return viewField
     */
    String viewField() default "";

    /**
     * type especifica el tipo de consulta de la operación. Su valor es uno de los elementos de la enumeración ExportQueryType. Seleccione DYNAMIC o
     * PARAMETERIZED si la consulta es dinámica o parametrizada, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es DYNAMIC. Las consultas dinámicas son aquellas que
     * dinámicamente construyen la cláusula WHERE de la consulta a partir de los parámetros no nulos de la operación y de sus respectivos operadores
     * escalares de comparación (vea Anotación ParameterField). Las consultas parametrizadas son consultas estáticas (su cláusula WHERE está
     * prestablecida) que utilizan todos los parámetros (aun los nulos) de la operación.
     *
     * @return type
     */
    ExportQueryType type() default ExportQueryType.DYNAMIC;

    /**
     * fileTypes especifica uno o más tipos de archivo que puede generar la operación. Su valor es una lista de elementos de la enumeración
     * ExportFileType. Incluya CSV para generar archivos de valores separados por coma o punto y coma, dependiendo de la configuración regional.
     * Incluya TSV para generar archivos de valores separados por tabulaciones. El valor predeterminado del atributo es solamente CSV.
     *
     * @return fileTypes
     */
    ExportFileType[] fileTypes() default {ExportFileType.CSV};

    /**
     * rowsLimit específica el número de máximo de filas que se deben exportar. Su valor debe ser un número entero entre 0 y 1.000.000. Utilice 0
     * cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return rowsLimit
     */
    int rowsLimit() default Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    /**
     * detailRowsLimit específica el número de máximo de filas que se deben exportar al generar archivos detallados. Su valor debe ser un número
     * entero entre 0 y 1.000.000. Utilice 0 cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return detailRowsLimit
     */
    int detailRowsLimit() default Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    /**
     * summaryRowsLimit específica el número de máximo de filas que se deben exportar al generar archivos resumidos. Su valor debe ser un número
     * entero entre 0 y 1.000.000. Utilice 0 cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return summaryRowsLimit
     */
    int summaryRowsLimit() default Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    /**
     * chartRowsLimit específica el número de máximo de filas que se deben exportar al generar archivos gráficos. Su valor debe ser un número entero
     * entre 0 y 1.000.000. Utilice 0 cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return chartRowsLimit
     */
    int chartRowsLimit() default Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    /**
     * sortOption especifica el criterio de ordenamiento por omisión de las filas exportadas. Su valor es uno de los elementos de la enumeración
     * SortOption. Seleccione ASC o DESC para exportar las filas ordenadas por el valor de su clave primaria, de manera ascendente o descendente,
     * respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es ASC.
     *
     * @return sortOption
     */
    SortOption sortOption() default SortOption.ASC;

}
