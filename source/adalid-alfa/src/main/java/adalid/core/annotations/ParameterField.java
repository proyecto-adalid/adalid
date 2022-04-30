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

import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación ParameterField se utiliza para establecer atributos básicos del parámetro.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ParameterField {

    /**
     * auditable indica si el parámetro se debe incluir, o no, en las pistas de auditoría de la operación. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para incluir el parámetro; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE para parámetros que
     * corresponden a "objetos binarios" o a contraseñas; y TRUE para las demás parámetros.
     *
     * @return auditable
     */
    Kleenean auditable() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * password indica si el parámetro es, o no, una contraseña. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si el
     * parámetro es una contraseña; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return password
     */
    Kleenean password() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * required indica si el parámetro es, o no, obligatoriamente requerido por las vistas (páginas) de ejecución de operaciones de negocio. Su valor
     * es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si el parámetro es obligatoriamente requerido; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE. Este elemento es irrelevante cuando el parámetro es la referencia a la instancia de la entidad de la
     * operación (vea Anotación InstanceReference); en este caso, el parámetro siempre es requerido.
     *
     * @return required
     */
    Kleenean required() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * hidden indica si el parámetro permanece, o no, oculto en las vistas (páginas) de ejecución de operaciones de negocio. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE si el parámetro permanece oculta; en caso contrario, seleccione FALSE. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return hidden
     */
    Kleenean hidden() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * linkedField especifica el nombre de la propiedad que corresponde a este parámetro. Este elemento es relevante solo para parámetros de
     * operaciones para generar archivos e informes con consulta dinámica (vea Anotación ExportOperationClass y Anotación ReportOperationClass). El
     * nombre SQL de esta propiedad (vea Método setSqlName) es utilizado como nombre de columna para agregar la correspondiente comparación a la
     * cláusula WHERE de la operación, a menos que también se especifique el elemento linkedColumn de esta misma anotación.
     *
     * @return linkedField
     */
    String linkedField() default "";

    /**
     * linkedColumn especifica el nombre de la columna de la tabla que corresponde a este parámetro. Este elemento es relevante solo para parámetros
     * de operaciones para generar archivos e informes con consulta dinámica (vea Anotación ExportOperationClass y Anotación ReportOperationClass).
     * Este nombre es utilizado para agregar la correspondiente comparación a la cláusula WHERE de la operación.
     *
     * @return linkedColumn
     */
    String linkedColumn() default "";

    /**
     * operator especifica el operador escalar de comparación a utilizar. Su valor es uno de los elementos de la enumeración StandardRelationalOp. El
     * apéndice 2 muestra la comparación correspondiente a cada elemento de la enumeración. El valor predeterminado es EQ. Este elemento es relevante
     * solo para parámetros de operaciones para generar archivos e informes con consulta dinámica (vea Anotación ExportOperationClass y Anotación
     * ReportOperationClass).
     *
     * @return operator
     */
    StandardRelationalOp operator() default StandardRelationalOp.EQ;

    /**
     * snippet especifica la ruta y el nombre del snippet del parámetro en las vistas (páginas) de ejecución de operaciones de negocio.
     *
     * Si utiliza la plataforma jee2, los snippets se deben agregar en el subdirectorio resources/snippets/custom-made del directorio src/main/webapp
     * del módulo Web, o en algún subdirectorio de resources/snippets/custom-made; si el valor de snippet no comienza por
     * <b>/resources/snippets/custom-made/</b>, ese prefijo se le agrega automáticamente. Además, los snippets deben ser archivos xhtml; si el valor
     * de snippet no termina con <b>.xhtml</b>, ese sufijo se le agrega automáticamente.
     *
     * @return snippet
     */
    String snippet() default "";

    /**
     * sequence específica el número de secuencia o posición relativa en la que se muestra el parámetro en las vistas (páginas) de ejecución de
     * operaciones de negocio. Su valor debe ser un número entero entre 0 y 2.147.483.647. Alternativamente, omita el elemento para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es 0. Si todas los parámetros tienen el mismo número de secuencia (0 o
     * cualquier otro), entonces las vistas las muestran en el orden en el mismo orden en el que las meta parámetros están definidos en la meta
     * operación.
     *
     * @return sequence
     */
    int sequence() default 0;

}
