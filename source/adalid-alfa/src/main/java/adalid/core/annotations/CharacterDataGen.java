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
 * La anotación EntityReferenceDataGen se utiliza para controlar la generación de datos para propiedades alfanuméricas.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CharacterDataGen {

    /**
     * type especifica el método para generar datos para la propiedad. Su valor es uno de los elementos de la enumeración DataGenType. Seleccione
     * DEFAULT para generar el valor por omisión de la propiedad (para dar un valor por omisión a una propiedad se utiliza el método setDefaultValue).
     * Seleccione SERIES para generar un valor en serie, concatenando prefix, secuencia y suffix, donde el valor de prefix y suffix viene dado por sus
     * correspondientes elementos, descritos más adelante; y el valor de secuencia se obtiene de una serie cíclica que se define utilizando los
     * elementos start, stop y step, también descritos más adelante. Seleccione RANDOM para generar un valor aleatorio. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es RANDOM.
     *
     * @return type
     */
    DataGenType type() default DataGenType.UNSPECIFIED;

    Kleenean loremIpsum() default Kleenean.UNSPECIFIED;

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

    // 'a' for lower case letter
    // 'A' for upper case letter
    // '?' for lower or upper case letter
    // '0' for number
    // 'x' for lower case letter or number
    // 'X' for upper case letter or number
    // '*' for lower or upper case letter or number
    // '\' to precede an actual 'a', 'A', '?', '0', 'x', 'X', '*' or '\'
    /**
     * pattern especifica la cadena de caracteres que se utiliza como patrón en él método RANDOM. Los caracteres del patrón se copian al resultado o
     * se sustituyen por caracteres generados aleatoriamente, de la siguiente manera: cada letra a minúscula se sustituye por una letra minúscula;
     * cada letra A mayúscula, por una letra mayúscula; cada signo de interrogación (?), por una letra, minúscula o mayúscula; cada dígito 0, por un
     * dígito; cada letra x minúscula, por un dígito o una letra minúscula; cada letra X mayúscula, por un dígito o una letra mayúscula; y cada
     * asterisco (*), por un dígito o una letra, minúscula o mayúscula; los demás caracteres son copiados. Para evitar la sustitución de alguno de los
     * caracteres especiales previamente mencionados (a, A, ?, 0, x, X, *), éste debe ser precedido por una barra oblicua inversa (\). Cuando un
     * carácter es precedido por una barra oblicua inversa se omite la barra y se copia el carácter al resultado; por lo tanto, para copiar una barra
     * oblicua inversa debe escribirla dos veces seguidas (\\).
     *
     * @return pattern
     */
    String pattern() default "";

    /**
     * prefix especifica la cadena de caracteres que se utiliza como prefijo de los valores generados por los métodos RANDOM y SERIES.
     *
     * @return prefix
     */
    String prefix() default "";

    /**
     * suffix especifica la cadena de caracteres que se utiliza como sufijo de los valores generados por los métodos RANDOM y SERIES.
     *
     * @return suffix
     */
    String suffix() default "";

    /**
     * function especifica el nombre de la función definida por el usuario que se utilizará para generar los datos. Este elemento es relevante solo si
     * el valor especificado, o determinado, para el elemento type es RANDOM o SERIES. La función no se ejecuta si el valor generado por el método
     * RANDOM o SERIES es nulo. Los parámetros que recibe la función son:
     * <ul>
     * <li>tabla: nombre de la tabla; su tipo de dato corresponde a java.lang.String.
     * <li>columna: nombre de la columna; su tipo de dato corresponde a java.lang.String.
     * <li>clave: clave primaria de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>secuencia: número de secuencia de la fila; su tipo de dato corresponde a java.lang.Integer.
     * <li>valor: valor calculado por el método RANDOM o SERIES; su tipo de dato corresponde a java.lang.String.
     * </ul>
     *
     * @return function
     */
    String function() default "";

}
