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
 * La anotación SequenceProperty se utiliza para designar una propiedad como propiedad secuencia de la entidad. Cada entidad puede tener una sola
 * propiedad secuencia. Las secuencias son claves candidatas (por lo tanto no admiten valores duplicados) que permiten solo valores numéricos. Solo se
 * puede designar como propiedad secuencia a propiedades de la clase LongProperty.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SequenceProperty {

    /**
     * start especifica el primer número de la secuencia. Debe ser un número entero del 1 al 9.223.372.036.854.775.807, y tiene que ser menor que el
     * valor especificado, o determinado, para el elemento stop. El valor predeterminado es 1.
     *
     * @return start
     */
    long start() default 1;

    /**
     * stop especifica el último número de la secuencia. Debe ser un número entero del 1 al 9.223.372.036.854.775.807, y tiene que ser mayor que el
     * valor especificado, o determinado, para el elemento start. El valor predeterminado es 9.223.372.036.854.775.807.
     *
     * @return stop
     */
    long stop() default Long.MAX_VALUE;

    /**
     * step especifica el intervalo entre los números de la secuencia. Debe ser un número entero del 1 al 9.223.372.036.854.775.807, y tiene que ser
     * menor o igual que la diferencia entre stop y start. El valor predeterminado es 1.
     *
     * @return step
     */
    long step() default 1;

}
