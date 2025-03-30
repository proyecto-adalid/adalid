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
 * La anotación EntityTriggers se utiliza para configurar la generación de disparadores (triggers) de la tabla de la base de datos correspondiente a
 * la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityTriggers {

    /**
     * beforeValue indica si los triggers before insert row y before update row deben, o no, ejecutar la función before_value antes de asignar el
     * valor por omisión a las columnas de la tabla. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para ejecutar la
     * función; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return beforeValue
     */
    Kleenean beforeValue() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * afterValue indica si los triggers before insert row y before update row deben, o no, ejecutar la función after_value después de asignar el
     * valor por omisión a las columnas de la tabla. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para ejecutar la
     * función; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return afterValue
     */
    Kleenean afterValue() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * beforeCheck indica si los triggers before insert row y before update row deben, o no, ejecutar la función before_check antes de comprobar las
     * restricciones (check constraints) de la tabla. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para ejecutar la
     * función; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return beforeCheck
     */
    Kleenean beforeCheck() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * afterCheck indica si los triggers before insert row y before update row deben, o no, ejecutar la función after_check después de comprobar las
     * restricciones (check constraints) de la tabla. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para ejecutar la
     * función; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return afterCheck
     */
    Kleenean afterCheck() default Kleenean.UNSPECIFIED; // FALSE

}
