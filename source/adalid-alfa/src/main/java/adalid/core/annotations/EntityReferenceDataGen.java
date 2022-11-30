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
 * La anotación EntityReferenceDataGen se utiliza para controlar la generación de datos para propiedades que hacen referencia a otras entidades.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntityReferenceDataGen {

    /**
     * type especifica el método para generar datos para la propiedad. Su valor es uno de los elementos de la enumeración DataGenType. Seleccione
     * DEFAULT para generar el valor por omisión de la propiedad (para dar un valor por omisión a una propiedad se utiliza el método setDefaultValue).
     * Seleccione SERIES para generar un valor en serie. Seleccione RANDOM para generar un valor aleatorio.
     *
     * @return type
     */
    DataGenType type() default DataGenType.UNSPECIFIED;

    /**
     * nullable especifica el porcentaje de valores nulos que se generarán. Este elemento es relevante solo si el valor especificado, o determinado,
     * para el elemento type es RANDOM. El valor de nullable debe ser un número entero del 0 al 100. El valor predeterminado es 10. Si el valor de
     * nullable es 100, solo se generarán valores nulos.
     *
     * @return nullable
     */
    int nullable() default 10; // 0 <= nullable <= 100

}
