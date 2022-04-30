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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación TimeField se utiliza para establecer atributos de propiedades y parámetros Time.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TimeField {

    /**
     * precision específica la precisión o cantidad de decimales (en los segundos) de la propiedad o parámetro. Su valor debe ser un número entero
     * entre 0 y 6. El valor predeterminado dependerá de cada plataforma.
     *
     * @return precision
     */
    int precision() default -1; // Constants.DEFAULT_TIME_PRECISION;

    /**
     * minHour específica el menor valor que puede tomar la hora en esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y 23. El
     * valor predeterminado es 0.
     *
     * @return minHour
     */
    int minHour() default -1; // 0;

    /**
     * maxHour específica el mayor valor que puede tomar la hora en esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y 23. El
     * valor predeterminado es 23.
     *
     * @return maxHour
     */
    int maxHour() default -1; // 23;

    /**
     * stepHour específica el valor del incremento de la hora de esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y 23. El valor
     * predeterminado es 1.
     *
     * @return stepHour
     */
    int stepHour() default -1; // 1;

    /**
     * minMinute específica el menor valor que pueden tomar los minutos en esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y
     * 59. El valor predeterminado es 0.
     *
     * @return minMinute
     */
    int minMinute() default -1; // 0;

    /**
     * maxMinute específica el mayor valor que pueden tomar los minutos en esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y
     * 59. El valor predeterminado es 59.
     *
     * @return maxMinute
     */
    int maxMinute() default -1; // 59;

    /**
     * stepMinute específica el valor del incremento de los minutos hora de esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y
     * 59. El valor predeterminado es 1.
     *
     * @return stepMinute
     */
    int stepMinute() default -1; // 1;

    /**
     * minSecond específica el menor valor que pueden tomar los segundos en esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y
     * 59. El valor predeterminado es 0.
     *
     * @return minSecond
     */
    int minSecond() default -1; // 0;

    /**
     * maxSecond específica el mayor valor que pueden tomar los segundos en esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y
     * 59. El valor predeterminado es 59.
     *
     * @return maxSecond
     */
    int maxSecond() default -1; // 59;

    /**
     * stepSecond específica el valor del incremento de los segundos hora de esta propiedad o parámetro. Su valor debe ser un número entero entre 0 y
     * 59. El valor predeterminado es 1.
     *
     * @return stepSecond
     */
    int stepSecond() default -1; // 1;

    /**
     * converter específica el nombre de un componente personalizado que permite convertir el valor de la propiedad o parámetro.
     *
     * @return converter
     */
    String converter() default "";

    /**
     * validator específica el nombre de un componente personalizado que permite validar el valor de la propiedad o parámetro.
     *
     * @return validator
     */
    String validator() default "";

}
