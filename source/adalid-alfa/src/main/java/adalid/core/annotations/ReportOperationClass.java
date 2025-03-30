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
 * La anotación ReportOperationClass se utiliza para establecer atributos de meta operaciones que extienden la clase ReportOperation (operaciones de
 * negocio para generar informes).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ReportOperationClass {

    /**
     * name especifica el nombre alterno del procedimiento de generación del informe.
     *
     * @return name
     */
    String name() default "";

    /**
     * view especifica el nombre de la vista SQL que se utiliza para la generación del informe.
     *
     * @return view
     */
    String view() default "";

    /**
     * viewField especifica el nombre de la vista de la entidad que se utiliza para la generación del informe. Si alguna de las propiedades de detalle
     * de la vista tiene una función de agregación, entonces la operación puede generar el informe tanto detallado como resumido; si además tiene al
     * menos una propiedad de control y, por ende, al menos un grupo de agregación, entonces la operación también puede generar el informe gráfico; si
     * la meta-vista no tiene agregaciones ni grupos, entonces la operación solo puede generar el informe detallado.
     *
     * @return viewField
     */
    String viewField() default "";

    /**
     * type especifica el tipo de consulta de la operación. Su valor es uno de los elementos de la enumeración ReportQueryType. Seleccione DYNAMIC o
     * PARAMETERIZED si la consulta es dinámica o parametrizada, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es DYNAMIC. Las consultas dinámicas son aquellas que
     * dinámicamente construyen la cláusula WHERE de la consulta a partir de los parámetros no nulos de la operación y de sus respectivos operadores
     * escalares de comparación (vea Anotación ParameterField). Las consultas parametrizadas son consultas estáticas (su cláusula WHERE está
     * prestablecida) que utilizan todos los parámetros (aun los nulos) de la operación.
     *
     * @return type
     */
    ReportQueryType type() default ReportQueryType.DYNAMIC;

    /**
     * fileTypes especifica uno o más tipos de archivo que puede generar la operación. Su valor es una lista de elementos de la enumeración
     * ReportFileType. Incluya PDF (siglas en inglés de Portable Document Format, «formato de documento portátil») para generar archivos que puedan
     * ser abiertos con Adobe Acrobat u otra aplicación similar. Incluya RTF (siglas en inglés de Rich Text Format, «formato de texto enriquecido»)
     * para generar archivos de texto estándar que contengan "texto enriquecido". Incluya ODT para generar archivos que puedan ser abiertos con
     * OpenOffice Writer u otro programa similar. Incluya ODS para generar archivos que puedan ser abiertos con OpenOffice Calc u otro programa
     * similar. Incluya HTML para generar archivos de texto escritos con lenguaje de marcado de hipertexto (HTML), típicamente páginas web. Incluya
     * DOCX para generar archivos que puedan ser abiertos con Microsoft Word u otro programa similar. Incluya PPTX para generar archivos que puedan
     * ser abiertos con Microsoft PowerPoint u otro programa similar. Incluya XLSX para generar archivos que puedan ser abiertos con Microsoft Excel u
     * otro programa similar. El valor predeterminado del atributo es solamente PDF.
     *
     * @return fileTypes
     */
    ReportFileType[] fileTypes() default {ReportFileType.PDF};

    /**
     * rowsLimit específica el número de máximo de filas que se deben reportar. Su valor debe ser un número entero entre 0 y 1.000.000. Utilice 0
     * cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return rowsLimit
     */
    int rowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    /**
     * detailRowsLimit específica el número de máximo de filas que se deben reportar al generar informes detallados. Su valor debe ser un número
     * entero entre 0 y 1.000.000. Utilice 0 cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return detailRowsLimit
     */
    int detailRowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    /**
     * summaryRowsLimit específica el número de máximo de filas que se deben reportar al generar informes resumidos. Su valor debe ser un número
     * entero entre 0 y 1.000.000. Utilice 0 cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return summaryRowsLimit
     */
    int summaryRowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    /**
     * chartRowsLimit específica el número de máximo de filas que se deben reportar al generar informes gráficos. Su valor debe ser un número entero
     * entre 0 y 1.000.000. Utilice 0 cuando no exista límite. El valor predeterminado es 10.000
     *
     * @return chartRowsLimit
     */
    int chartRowsLimit() default Constants.DEFAULT_REPORT_ROWS_LIMIT;

    /**
     * chartTypes especifica uno o más tipos de gráfico que puede generar la operación. Su valor es una lista de elementos de la enumeración
     * ReportChartType. Incluya BAR, STACKED_BAR, AREA, STACKED_AREA o LINE para generar gráficos de barras, barras apiladas, áreas, áreas apiladas o
     * líneas, con series por agregación, respectivamente. Incluya BAR_BY_GROUP, STACKED_BAR_BY_GROUP, AREA_BY_GROUP, STACKED_AREA_BY_GROUP o
     * LINE_BY_GROUP para generar gráficos de barras, barras apiladas, áreas, áreas apiladas o líneas, con series por grupo, respectivamente. Incluya
     * PIE para generar gráficos de torta. Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es solamente BAR.
     *
     * @return chartTypes
     */
    ReportChartType[] chartTypes() default {ReportChartType.BAR};

    /**
     * sortOption especifica el criterio de ordenamiento por omisión de las filas reportadas. Su valor es uno de los elementos de la enumeración
     * SortOption. Seleccione ASC o DESC para exportar las filas ordenadas por el valor de su clave primaria, de manera ascendente o descendente,
     * respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es ASC.
     *
     * @return sortOption
     */
    SortOption sortOption() default SortOption.ASC;

}
