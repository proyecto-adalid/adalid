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
 * La anotación EntityDataGen se utiliza para controlar la generación de datos para una entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityDataGen {

    /**
     * start especifica el primer número de la serie. Debe ser un número entero del 1 al 2.000.000.000 y debe ser menor o igual que el valor
     * especificado, o determinado, para el elemento stop. El valor predeterminado es 1.
     *
     * @return start
     */
    int start() default 1;

    /**
     * stop especifica el último número de la serie. Debe ser un número entero del 1 al 2.100.000.000 y debe ser mayor o igual que el valor
     * especificado, o determinado, para el elemento start. El valor predeterminado es 100.
     *
     * @return stop
     */
    int stop() default 100;

    /**
     * step especifica el intervalo entre los números de la serie. Debe ser un número entero del 1 al 1.000.000, y tiene que ser menor o igual que la
     * diferencia entre stop y start. El valor predeterminado es 1.
     *
     * @return step
     */
    int step() default 1;

}
