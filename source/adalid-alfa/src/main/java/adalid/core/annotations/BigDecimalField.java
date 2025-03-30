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
 * La anotación BigDecimalField se utiliza para establecer atributos de propiedades y parámetros BigDecimal.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BigDecimalField {

    /**
     * precision específica la precisión o cantidad de dígitos significativos de la propiedad o parámetro. Su valor debe ser un número entero entre 1
     * y 60. El valor predeterminado es 16.
     *
     * @return precision
     */
    int precision() default -1; // Constants.DEFAULT_DECIMAL_PRECISION;

    /**
     * scale específica la escala o cantidad de decimales de la propiedad o parámetro. Su valor debe ser un número entero entre 0 y 60 y debe ser
     * menor o igual a precision. El valor predeterminado es 2 si precision es mayor que 2; de lo contrario, es el valor de precision, lo cual define
     * números sin parte entera.
     *
     * @return scale
     */
    int scale() default -1; // Constants.DEFAULT_DECIMAL_SCALE;

}
