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
 * La anotación EntityJsonCustomization se utiliza para especificar atributos relacionados con JSON.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityJsonCustomization {

    /**
     * serializer específica el nombre de la clase (relativo al paquete raíz del proyecto) del componente personalizado para serializar la entidad
     *
     * @return serializer
     */
    String serializer() default "";

    /**
     * deserializer específica el nombre de la clase (relativo al paquete raíz del proyecto) del componente personalizado para deserializar la entidad
     *
     * @return deserializer
     */
    String deserializer() default "";

}
