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
 * La anotación EntityReferenceDataGen se utiliza para controlar la generación de datos para propiedades lógicas.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BooleanDataGen {

    /**
     * type especifica el método para generar datos para la propiedad. Su valor es uno de los elementos de la enumeración DataGenType. Seleccione
     * DEFAULT para generar el valor por omisión de la propiedad (para dar un valor por omisión a una propiedad se utiliza el método setDefaultValue).
     * Seleccione SERIES para generar un valor en serie. Seleccione RANDOM para generar un valor aleatorio. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es RANDOM.
     *
     * @return type
     */
    DataGenType type() default DataGenType.UNSPECIFIED;

    /**
     * nullable especifica el porcentaje de valores nulos que se generarán. Este elemento es relevante solo si el valor especificado, o determinado,
     * para el elemento type es RANDOM. El valor de nullable debe ser un número entero del 0 al 100. El valor predeterminado es 0. Si el valor de
     * nullable es 100, solo se generarán valores nulos. Si nullable + trueable &gt; 100, entonces se ajustará el valor de nullable a 100 - trueable,
     * y no se generarán valores FALSE.
     *
     * @return nullable
     */
    int nullable() default 10; // 0 <= nullable <= 100

    /**
     * trueable especifica el porcentaje de valores TRUE que se generarán. Este elemento es relevante solo si el valor especificado, o determinado,
     * para el elemento type es RANDOM. El valor de trueable debe ser un número entero del 0 al 100. El valor predeterminado es 0. Si el valor de
     * trueable es 0, todos los valores no nulos serán FALSE. Si el valor de nullable es 100, solo se generarán valores TRUE. Si nullable + trueable
     * &gt; 100, entonces se ajustará el valor de nullable a 100 - trueable, y no se generarán valores FALSE.
     *
     * @return trueable
     */
    int trueable() default (50); // 0 <= trueable <= 100

    /**
     * function especifica el nombre de la función definida por el usuario que se utilizará para generar los datos. Este elemento es relevante solo si
     * el valor especificado, o determinado, para el elemento type es RANDOM o SERIES. La función no se ejecuta si el valor generado por el método
     * RANDOM o SERIES es nulo. Los parámetros que recibe la función son:
     * <ul>
     * <li>tabla: nombre de la tabla; su tipo de dato corresponde a java.lang.String.
     * <li>columna: nombre de la columna; su tipo de dato corresponde a java.lang.String.
     * <li>clave: clave primaria de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>secuencia: número de secuencia de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>valor: valor calculado por el método RANDOM o SERIES; su tipo de dato corresponde a java.lang.Boolean.
     * </ul>
     *
     * @return function
     */
    String function() default "";

}
