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
 * La anotación AddAttributesMethod se utiliza para designar métodos como métodos Add Attributes. Todo método Add Attributes debe ser público,
 * estático y sin valor de retorno; con un único parámetro que implemente, directa o indirectamente, la interfaz Artifact; y decorado con la anotación
 * AddAttributesMethod. El nombre del método puede ser cualquiera que cumpla con las reglas de Java.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AddAttributesMethod {

    /**
     * value especifica el orden de ejecución del método Add Attributes. El orden de ejecución de los métodos Add Attributes está determinado, en
     * primer lugar, por el orden en el que se agregan las clases a la lista y, en segundo lugar, por el valor especificado en la anotación
     * AddAttributesMethod. Si el orden de ejecución dentro de la clase no es relevante, este valor puede ser omitido.
     *
     * @return value
     */
    int value() default 0;

}
