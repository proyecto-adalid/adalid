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
import adalid.core.interfaces.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación ConstructionOperationClass se utiliza para establecer atributos de meta operaciones que extienden la clase ProcessOperation. Las
 * operaciones decoradas con esta anotación se incluyen en la lista de procesos de negocio que se pueden utilizar como alternativa para agregar
 * instancias de la clase de entidad que la operación construye.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConstructionOperationClass {

    /**
     * type especifica la clase de entidad que la operación construye. No puede ser una clase de entidad abstracta (decorada con la anotación
     * AbstractClass).
     *
     * @return type
     */
    Class<? extends Entity> type();

    /**
     * onsuccess especifica la acción que se debe llevar a cabo al finalizar la operación, cuando ésta se ejecuta con éxito. Su valor es uno de los
     * elementos de la enumeración OnConstructionOperationSuccess. Seleccione DISPLAY_NEW_INSTANCE para mostrar la instancia construída abriendo la
     * vista (página) de consulta o registro de esa clase de entidad. Alternativamente, omita el elemento o seleccione UNSPECIFIED para no llevar a
     * cabo una acción.
     *
     * @return onsuccess
     */
    OnConstructionOperationSuccess onsuccess() default OnConstructionOperationSuccess.UNSPECIFIED;

}
