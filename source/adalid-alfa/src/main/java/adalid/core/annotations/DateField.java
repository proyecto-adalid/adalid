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
 * La anotación DateField se utiliza para establecer atributos de propiedades y parámetros Date.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateField {

    /**
     * disabledWeekends indica si el valor de la propiedad o parámetro puede ser, o no, un día de fin de semana (Sábado o Domingo). Su valor es uno de
     * los elementos de la enumeración Kleenean. Seleccione FALSE si el valor de la propiedad o parámetro puede ser un día de fin de semana; en caso
     * contrario, seleccione TRUE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El
     * valor predeterminado del atributo es FALSE.
     *
     * @return disabledWeekends
     */
    Kleenean disabledWeekends() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * disabledWeekdays indica si el valor de la propiedad o parámetro puede ser, o no, un día de semana (Lunes a Viernes). Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione FALSE si el valor de la propiedad o parámetro puede ser un día de semana; en caso contrario,
     * seleccione TRUE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es FALSE.
     *
     * @return disabledWeekdays
     */
    Kleenean disabledWeekdays() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * disabledHolidays indica si el valor de la propiedad o parámetro puede ser, o no, un día feriado. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione FALSE si el valor de la propiedad o parámetro puede ser un día feriado; en caso contrario, seleccione TRUE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es FALSE.
     *
     * @return disabledHolidays
     */
    Kleenean disabledHolidays() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * yearRange específica el intervalo predeterminado del año para esta propiedad o parámetro. Su valor debe ser un número entero entre 5 y 100. El
     * valor predeterminado es 5.
     *
     * @return yearRange
     */
    int yearRange() default -1; // Constants.DEFAULT_YEAR_RANGE;

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
