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
 * La anotación MasterSequence se utiliza para establecer atributos para calcular el siguiente valor de propiedades numéricas. Solo aplica a
 * propiedades de la clase IntegerProperty y solo se calcula en las vistas (páginas) Maestro/Detalle de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MasterSequence {

    /**
     * masterField especifica el nombre de la propiedad que define el maestro de las vistas (páginas) Maestro/Detalle de la entidad en las que se debe
     * calcular el siguiente valor de la propiedad.
     *
     * @return masterField
     */
    String masterField() default "";

    /**
     * start especifica el primer número de la serie. Debe ser un número entero del 1 al 10.000. El valor predeterminado es 1.
     *
     * @return start
     */
    int start() default 1;

    /**
     * step específica el operando para la regla nextValueRule. Su valor debe ser un número entero entre 1 y 10.000. El valor predeterminado es 1.
     *
     * @return step
     */
    int step() default 1;

    /**
     * nextValueRule especifica la regla para calcular el siguiente valor de la propiedad. Su valor es uno de los elementos de la enumeración
     * NextValueRule. Seleccione ADD para calcular el siguiente valor de la propiedad sumando el valor de step al último valor calculado, Seleccione
     * CEILING para que el siguiente valor de la propiedad sea el menor múltiplo de step que sea mayor al último valor calculado. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar la regla predeterminada. La regla predeterminada es ADD. En cualquier caso, la primera
     * instancia de un maestro tendrá el valor especificado en el elemento start.
     *
     * @return nextValueRule
     */
    NextValueRule nextValueRule() default NextValueRule.UNSPECIFIED;

}
