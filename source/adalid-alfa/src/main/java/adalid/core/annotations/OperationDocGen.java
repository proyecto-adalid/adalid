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
 * La anotación OperationDocGen se utiliza para controlar la generación de documentación para una operación.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OperationDocGen {

    /**
     * activityDiagram indica si se genera, o no, el diagrama de actividad de la operación. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para generar el diagrama; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return activityDiagram
     */
    Kleenean activityDiagram() default Kleenean.UNSPECIFIED; // TRUE

}
