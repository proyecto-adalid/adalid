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
 * La anotación EntityReferenceAccessControl se utiliza para configurar la forma en que las vistas (páginas) convierten y validan la referencia
 * (propiedad o parámetro que hace referencia a otra entidad).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface EntityReferenceConversionValidation {

    /**
     * customConverter específica si existe un componente personalizado que permite convertir el valor de la propiedad o parámetro.
     *
     * @return customConverter
     */
    Kleenean customConverter() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * restrictedAccess indica si es una referencia con o sin acceso restringido por las reglas de control de acceso. Su valor es uno de los elementos
     * de la enumeración Kleenean. Seleccione TRUE para que sea una referencia con acceso restringido; o FALSE, para que sea una referencia sin acceso
     * restringido. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es TRUE.
     *
     * @return restrictedAccess
     */
    Kleenean restrictedAccess() default Kleenean.UNSPECIFIED; // TRUE

}
