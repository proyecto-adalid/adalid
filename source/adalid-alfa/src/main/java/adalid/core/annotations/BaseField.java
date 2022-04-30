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
 * La anotación BaseField se utiliza para designar las propiedades básicas de la entidad. Esta anotación solo es relevante cuando la entidad es
 * extendida y la estrategia para generar las tablas de la base de datos de la jerarquía es JOINED (vea Anotación InheritanceMapping). Cuando la
 * estrategia es JOINED, las tablas generadas no tienen columnas para propiedades heredadas a menos que la propiedad heredada sea una propiedad
 * básica. Las claves primarias y las propiedades versión son implícitamente básicas y, por lo tanto, no es necesario decorarlas con la anotación
 * BaseField.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BaseField {

    boolean value() default true;

}
