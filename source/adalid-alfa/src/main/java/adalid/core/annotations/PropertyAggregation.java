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
 * La anotación PropertyAggregation se utiliza para establecer la función de agregación de la propiedad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyAggregation {

    /**
     * function específica la función de agregación de la propiedad. Su valor es uno de los elementos de la enumeración AggregateFunction. Seleccione
     * COUNT, MINIMUM, MAXIMUM, SUM, AVERAGE o CUSTOM_MADE para especificar cuenta, mínimo, máximo, suma, promedio o agregación programada por el
     * usuario, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El
     * valor predeterminado del atributo es COUNT.
     *
     * @return function
     */
    AggregateFunction function() default AggregateFunction.COUNT; // AggregateFunction.UNSPECIFIED?

    /**
     * title especifica el título de la función de agregación. Solo es necesario si la función es CUSTOM_MADE, pero puede usarse para especificar un
     * título diferente al predeterminado de las demás funciones.
     *
     * @return title
     */
    String title() default "";

}
