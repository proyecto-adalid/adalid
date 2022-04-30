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
 * La anotación InstanceDataGen se utiliza para controlar la generación de datos para propiedades que hacen referencia a enumeraciones.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InstanceDataGen {

    /**
     * weight especifica el peso de la instancia. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento type de la
     * anotación @EntityReferenceDataGen es RANDOM. El valor de weight debe ser un número entero del 0 al 100. El valor predeterminado es 1. Si todos
     * los pesos son iguales la distribución tiende a ser homogénea.
     *
     * @return weight
     */
    int weight() default 1;

}
