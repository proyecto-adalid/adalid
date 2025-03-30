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
 * La anotación BinaryField se utiliza para establecer atributos de propiedades BinaryProperty.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BinaryField {

    /**
     * fetch especifica si la operación de consulta obtiene el valor de la propiedad simultáneamente con el resto de las propiedades de la entidad o
     * posteriormente, por demanda. Su valor es uno de los elementos de la enumeración FetchType. Seleccione EAGER para obtener el valor de la
     * propiedad simultáneamente con el resto de las propiedades de la entidad; seleccione LAZY para obtenerlo posteriormente, por demanda;
     * alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es EAGER.
     *
     * @return fetch
     */
    FetchType fetch() default FetchType.UNSPECIFIED;

}
