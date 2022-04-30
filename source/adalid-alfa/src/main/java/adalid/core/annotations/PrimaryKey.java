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
 * La anotación PrimaryKey se utiliza para designar una propiedad como clave primaria de la entidad. Cada entidad puede tener una sola clave primaria.
 * Solo se puede designar como clave primaria a propiedades de dos clases: IntegerProperty y LongProperty. Al utilizar la plataforma jee2, si la
 * entidad representa una enumeración y, por lo tanto, su correspondiente meta entidad extiende, directa o indirectamente, la clase
 * AbstractPersistentEnumerationEntity, entonces debe tener como clave primaria una propiedad de la clase IntegerProperty; las demás entidades deben
 * tener como clave primaria una propiedad de la clase LongProperty.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {

    boolean value() default true;

}
