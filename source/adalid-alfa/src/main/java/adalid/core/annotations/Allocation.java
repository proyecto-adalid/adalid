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
 * La anotación Allocation se utiliza para establecer límites a la instanciación de las referencias de la entidad y de los meta parámetros
 * referenciales de la operación. Estos límites se deben ajustar en función de las expresiones programadas en la meta entidad o meta operación, ya que
 * éstas pueden utilizar propiedades de entidades referenciadas. Si una expresión utiliza una propiedad de una entidad referenciada que está fuera de
 * alcance (out-of-scope) entonces, al generar la aplicación, se produce un NullPointerException en la instrucción correspondiente a la expresión. La
 * solución a este problema es aumentar los límites establecidos (explícitamente o por omisión) para la instanciación de la referencia que está fuera
 * de alcance.
 *
 * El primero de los límites determina la profundidad máxima que se puede alcanzar al instanciar la referencia. Las propiedades de la entidad y de
 * cada meta parámetro tienen profundidad 1. Las propiedades de las entidades referenciadas tienen profundidad 2. Las propiedades de las entidades
 * referenciadas por las entidades referenciadas en la entidad tienen profundidad 3, y así sucesivamente, ad-infinitum.
 *
 * El segundo de los límites determina la cantidad máxima de referencias circulares que se puede alcanzar al instanciar una referencia. Una referencia
 * circular es una referencia a la misma entidad, hecha de forma directa o indirecta (en otras palabras, hecha a cualquier profundidad).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Allocation {

    /**
     * maxDepth específica la profundidad máxima de la referencia. Su valor debe ser un número entero mayor o igual a 1. El valor predeterminado es 1.
     *
     * @return maxDepth
     */
    int maxDepth() default 1;

    /**
     * maxRound específica la cantidad máxima de referencias circulares de la referencia. Su valor debe ser un número entero mayor o igual a 0. El
     * valor predeterminado es 1 para las propiedades padre y 0 para las demás referencias.
     *
     * @return maxRound
     */
    int maxRound() default 0;

}
