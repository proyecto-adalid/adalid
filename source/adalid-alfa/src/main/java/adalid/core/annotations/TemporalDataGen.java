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
 * La anotación EntityReferenceDataGen se utiliza para controlar la generación de datos para propiedades temporales.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TemporalDataGen {

    /**
     * type especifica el método para generar datos para la propiedad. Su valor es uno de los elementos de la enumeración DataGenType. Seleccione
     * DEFAULT para generar el valor por omisión de la propiedad (para dar un valor por omisión a una propiedad se utiliza el método setDefaultValue).
     * Seleccione SERIES para generar un valor en serie, mediante la fórmula min + secuencia, donde el valor de min viene dado por su correspondiente
     * elemento, descrito más adelante; el valor de secuencia se obtiene de una serie cíclica que se define utilizando los elementos start, stop y
     * step, también descritos más adelante; y el intervalo al cual se suma el valor de secuencia viene dado por el valor del elemento interval,
     * también descrito más adelante. Seleccione RANDOM para generar un valor aleatorio, mediante la fórmula min + random, donde el valor de min viene
     * dado por su correspondiente elemento, descrito más adelante; y el valor de random es un número de días, minutos o segundos, para DateProperty,
     * TimeProperty o TimestampProperty, respectivamente, que se obtiene de manera aleatoria entre min y max. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es RANDOM.
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
     * min especifica el mínimo valor generado. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento type es
     * RANDOM o SERIES. Puede ser un valor constante, acorde con el tipo de dato. El formato de una constante de tipo Date es yyyy-mm-dd. El formato
     * de una constante de tipo Time es hh:mm:ss. El formato de una constante de tipo Timestamp es yyyy-mm-dd hh:mm:ss. También puede ser una
     * expresión para calcular un valor relativo a la fecha y hora de ejecución. Escriba un número entero, positivo o negativo, seguido de una letra
     * que identifique el intervalo de tiempo al cual desea sumar el número. La letra D mayúscula corresponde a día; la M mayúscula, a mes; la Y
     * mayúscula, a año; la h minúscula, a hora; la m minúscula, a minuto; y la s minúscula, a segundo. El valor predeterminado para las propiedades
     * de tipo DateProperty es la fecha actual menos 60 años. El valor predeterminado para las propiedades de tipo TimeProperty es la 00:00. El valor
     * predeterminado para las propiedades de tipo TimestampProperty es la fecha actual menos 60 años y la hora actual.
     *
     * @return min
     */
    String min() default "";

    /**
     * max especifica el máximo valor generado. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento type es
     * RANDOM o SERIES. Puede ser un valor constante, acorde con el tipo de dato. El formato de una constante de tipo Date es yyyy-mm-dd. El formato
     * de una constante de tipo Time es hh:mm:ss. El formato de una constante de tipo Timestamp es yyyy-mm-dd hh:mm:ss. También puede ser una
     * expresión para calcular un valor relativo a la fecha y hora de ejecución. Escriba un número entero, positivo o negativo, seguido de una letra
     * que identifique el intervalo de tiempo al cual desea sumar el número. La letra D mayúscula corresponde a día; la M mayúscula, a mes; la Y
     * mayúscula, a año; la h minúscula, a hora; la m minúscula, a minuto; y la s minúscula, a segundo. El valor predeterminado para las propiedades
     * de tipo DateProperty es la fecha actual más 60 años. El valor predeterminado para las propiedades de tipo TimeProperty es la 23:59. El valor
     * predeterminado para las propiedades de tipo TimestampProperty es la fecha actual más 60 años y la hora actual.
     *
     * @return max
     */
    String max() default "";

    /**
     * interval especifica el intervalo que se incrementa al calcular valores en serie. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento type es SERIES. Su valor es uno de los elementos de la enumeración DataGenTemporalInterval. Seleccione YEAR,
     * MONTH, DAY, HOUR, MINUTE o SECOND para sumar el valor de secuencia al año, mes, día, hora, minuto o segundo, respectivamente. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado para las propiedades de
     * tipo DateProperty y TimestampProperty es DAY. El valor omisión para las propiedades de tipo TimeProperty es HOUR.
     *
     * @return interval
     */
    DataGenTemporalInterval interval() default DataGenTemporalInterval.UNSPECIFIED;

    /**
     * function especifica el nombre de la función definida por el usuario que se utilizará para generar los datos. Este elemento es relevante solo si
     * el valor especificado, o determinado, para el elemento type es RANDOM o SERIES. La función no se ejecuta si el valor generado por el método
     * RANDOM o SERIES es nulo. Los parámetros que recibe la función son:
     * <ul>
     * <li>tabla: nombre de la tabla; su tipo de dato corresponde a java.lang.String.
     * <li>columna: nombre de la columna; su tipo de dato corresponde a java.lang.String.
     * <li>clave: clave primaria de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>secuencia: número de secuencia de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>valor: valor calculado por el método RANDOM o SERIES; su tipo de dato corresponde a java.sql.Date o java.sql.Time o java.sql.Timestamp,
     * según corresponda.
     * </ul>
     *
     * @return function
     */
    String function() default "";

}
