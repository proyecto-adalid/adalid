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
 * La anotación DiscriminatorValue se utiliza para especificar el valor de la columna discriminadora (vea Anotación DiscriminatorColumn) que
 * identifica la subclase a la que pertenecen las instancias de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DiscriminatorValue {

    /**
     * value especifica el valor de la columna discriminadora que identifica la subclase a la que pertenecen las instancias de la entidad. Si la
     * columna discriminadora es una referencia a una entidad que corresponde a una enumeración, entonces este valor debe ser el valor de la clave
     * primaria de la entidad referenciada.
     *
     * @return value
     */
    String value();

}
