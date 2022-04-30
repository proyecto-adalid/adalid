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
 * La anotación ForeignKey se utiliza para generar una clave foránea en la base de datos para la referencia (propiedad que hace referencia a otra
 * entidad).
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {

    /**
     * onDelete especifica la acción del gestor de base de datos al eliminar filas de la tabla de la base de datos correspondiente a la entidad
     * referenciada. Su valor es uno de los elementos de la enumeración OnDeleteAction. Seleccione NONE, CASCADE o NULLIFY para impedir la eliminación
     * cuando existan referencias a la instancia eliminada; eliminar las filas que contienen referencias a la instancia eliminada; o actualizar las
     * filas que contienen referencias a la instancia eliminada, colocando valor nulo en la columna correspondiente a la referencia, respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es NONE.
     *
     * @return onDelete
     */
    OnDeleteAction onDelete() default OnDeleteAction.UNSPECIFIED;

    /**
     * onUpdate especifica la acción del gestor de base de datos al modificar la clave primaria de filas de la tabla de la base de datos
     * correspondiente a la entidad referenciada. Su valor es uno de los elementos de la enumeración OnUpdateAction. Seleccione NONE, CASCADE o
     * NULLIFY para impedir la actualización cuando existan referencias a la instancia modificada; actualizar las filas que contienen referencias a la
     * instancia modificada, colocando el nuevo valor de la clave primaria en la columna correspondiente a la referencia; o actualizar las filas que
     * contienen referencias a la instancia modificada, colocando valor nulo en la columna correspondiente a la referencia, respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es NONE.
     *
     * @return onUpdate
     */
    OnUpdateAction onUpdate() default OnUpdateAction.UNSPECIFIED;

}
