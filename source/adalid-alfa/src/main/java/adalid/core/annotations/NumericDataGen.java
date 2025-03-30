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
 * La anotación EntityReferenceDataGen se utiliza para controlar la generación de datos para propiedades numéricas.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NumericDataGen {

    /**
     * type especifica el método para generar datos para la propiedad. Su valor es uno de los elementos de la enumeración DataGenType. Seleccione
     * DEFAULT para generar el valor por omisión de la propiedad (para dar un valor por omisión a una propiedad se utiliza el método setDefaultValue).
     * Seleccione SERIES para generar un valor en serie, mediante la fórmula min + factor × secuencia, donde el valor de min y factor viene dado por
     * sus correspondientes elementos, descritos más adelante; y el valor de secuencia se obtiene de una serie cíclica que se define utilizando los
     * elementos start, stop y step, también descritos más adelante. Seleccione RANDOM para generar un valor aleatorio, mediante la fórmula min +
     * factor × f(random), donde el valor de min y factor viene dado por sus correspondientes elementos, descritos más adelante; la acción que realiza
     * la función f viene dada por el elemento action, también descrito más adelante; y el valor de random se obtiene de manera aleatoria entre min y
     * max, donde el valor de min y max viene dado por sus correspondientes elementos, también descritos más adelante. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es RANDOM.
     *
     * @return type
     */
    DataGenType type() default DataGenType.UNSPECIFIED;

    /**
     * start especifica el primer número de la serie. Debe ser un número entero del 1 al 2.000.000.000, y tiene que ser menor que el valor
     * especificado, o determinado, para el elemento stop. El valor predeterminado es 1.
     *
     * @return start
     */
    int start() default 1;

    /**
     * stop especifica el último número de la serie. Debe ser un número entero del 1 al 2.100.000.000, y tiene que ser mayor que el valor
     * especificado, o determinado, para el elemento start. El valor predeterminado es 2.100.000.000.
     *
     * @return stop
     */
    int stop() default Constants.MAX_SERIES_STOP;

    /**
     * step especifica el intervalo entre los números de la serie. Debe ser un número entero del 1 al 1.000.000, y tiene que ser menor o igual que la
     * diferencia entre stop y start. El valor predeterminado es 1.
     *
     * @return step
     */
    int step() default 1;

    /**
     * nullable especifica el porcentaje de valores nulos que se generarán. Este elemento es relevante solo si el valor especificado, o determinado,
     * para el elemento type es RANDOM. El valor de nullable debe ser un número entero del 0 al 100. El valor predeterminado es 0. Si el valor de
     * nullable es 100, solo se generarán valores nulos.
     *
     * @return nullable
     */
    int nullable() default 10; // 0 <= nullable <= 100

    /**
     * min especifica el mínimo número random generado. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento type
     * es RANDOM o SERIES. Debe ser un número entero del 2147483648 al 2147483647. El valor predeterminado es el valor mínimo de la propiedad (para
     * dar un valor mínimo a una propiedad se utiliza el método setMinValue). El número random generado es un resultado intermedio que puede ser
     * redondeado o truncado, multiplicado por factor y comparado contra el valor mínimo y máximo de la propiedad para obtener el resultado final. En
     * ningún caso el resultado final será menor que el valor mínimo de la propiedad.
     *
     * @return min
     */
    String min() default ""; // 0;

    /**
     * max especifica el máximo número random generado. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento type
     * es RANDOM o SERIES. Debe ser un número entero del 2147483648 al 2147483647. El valor predeterminado es el valor máximo de la propiedad (para
     * dar un valor máximo a una propiedad se utiliza el método setMaxValue). El número random generado es un resultado intermedio que puede ser
     * redondeado o truncado, multiplicado por factor y comparado contra el valor mínimo y máximo de la propiedad para obtener el resultado final. En
     * ningún caso el valor generado será mayor que el valor máximo de la propiedad.
     *
     * @return max
     */
    String max() default ""; // 1000000000; // 2100000000;

    /**
     * action especifica la acción que se realiza sobre el número random generado. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento type es RANDOM. Su valor es uno de los elementos de la enumeración DataGenNumericAction. Seleccione ROUND o
     * TRUNCATE para redondear o truncar el número random generado antes de multiplicarlo por factor. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para no ejecutar ninguna acción previa a la multiplicación por factor.
     *
     * @return action
     */
    DataGenNumericAction action() default DataGenNumericAction.UNSPECIFIED;

    /**
     * factor especifica el factor que multiplica al número random generado. Este elemento es relevante solo si el valor especificado, o determinado,
     * para el elemento type es RANDOM o SERIES. Puede ser cualquier número decimal. El valor predeterminado es 1.
     *
     * @return factor
     */
    String factor() default ""; // 1;

    /**
     * function especifica el nombre de la función definida por el usuario que se utilizará para generar los datos. Este elemento es relevante solo si
     * el valor especificado, o determinado, para el elemento type es RANDOM o SERIES. La función no se ejecuta si el valor generado por el método
     * RANDOM o SERIES es nulo. Los parámetros que recibe la función son:
     * <ul>
     * <li>tabla: nombre de la tabla; su tipo de dato corresponde a java.lang.String.
     * <li>columna: nombre de la columna; su tipo de dato corresponde a java.lang.String.
     * <li>clave: clave primaria de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>secuencia: número de secuencia de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>valor: valor calculado por el método RANDOM o SERIES; su tipo de dato corresponde a java.lang.Number.
     * </ul>
     *
     * @return function
     */
    String function() default "";

}
