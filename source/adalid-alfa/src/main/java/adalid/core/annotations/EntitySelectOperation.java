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

import adalid.core.*;
import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación EntitySelectOperation se utiliza para establecer atributos de la operación select de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntitySelectOperation {

    /**
     * enabled indica si, además de las vistas (páginas) de registro, también se deben generar vistas de consulta de la entidad. Su valor es uno de
     * los elementos de la enumeración Kleenean. Seleccione TRUE para generar las vistas de consulta; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return enabled
     */
    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * access especifica el tipo de control de acceso de la operación. Su valor es uno de los elementos de la enumeración OperationAccess. Seleccione
     * PRIVATE, PUBLIC, PROTECTED o RESTRICTED si la operación es de acceso privado, público, protegido o restringido, respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es RESTRICTED. Las operaciones con acceso privado no pueden ser ejecutadas directamente por los usuarios del sistema. Son ejecutadas
     * solo por otras operaciones, a través de la Interfaz de Programación (API). Las operaciones con acceso público, protegido y restringido si
     * pueden ser ejecutadas directamente por los usuarios del sistema, a través de la Interfaz de Usuario (UI). Las operaciones con acceso público
     * pueden ser ejecutadas por todos los usuarios del sistema, aun cuando no tengan autorización explícita para ello. Las operaciones con acceso
     * protegido pueden ser ejecutadas por usuarios designados como súper-usuario o por usuarios explícitamente autorizados. Al igual que las
     * operaciones con acceso protegido, las operaciones con acceso restringido pueden ser ejecutadas por usuarios designados como súper-usuario o por
     * usuarios explícitamente autorizados. Además, a diferencia de las operaciones con acceso protegido, las operaciones personalizables con acceso
     * restringido, también pueden ser ejecutadas por usuarios que no tengan autorización explícita, pero solo sobre las instancias de la entidad que
     * sean propiedad del usuario.
     *
     * @return access
     */
    OperationAccess access() default OperationAccess.UNSPECIFIED; // RESTRICTED

    /**
     * onload especifica la acción que las vistas de consulta y registro deben ejecutar al comenzar. Su valor es uno de los elementos de la
     * enumeración SelectOnloadOption. Seleccione EXECUTE para que las vistas ejecuten la operación select al comenzar; seleccione PROMPT para que las
     * vistas soliciten criterios de búsqueda al comenzar. Alternativamente, omita el elemento o seleccione DEFAULT para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es PROMPT.
     *
     * @return onload
     */
    SelectOnloadOption onload() default SelectOnloadOption.DEFAULT; // PROMPT

    /**
     * rowsLimit específica el número de máximo de filas que muestra la vista. Su valor debe ser un número entero entre 0 y 10.000. Utilice 0 cuando
     * no exista límite. El valor predeterminado es 100.
     *
     * @return rowsLimit
     */
    int rowsLimit() default Constants.DEFAULT_SELECT_ROWS_LIMIT;

    /**
     * sortOption especifica el criterio de ordenamiento por omisión de las filas que muestra la vista. Su valor es uno de los elementos de la
     * enumeración SortOption. Seleccione ASC o DESC para mostrar las filas ordenadas por el valor de su clave primaria, de manera ascendente o
     * descendente, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo.
     * El valor predeterminado del atributo es ASC. Este criterio de ordenamiento se utiliza solo si no se establece otro criterio mediante el método
     * setOrderBy.
     *
     * @return sortOption
     */
    SortOption sortOption() default SortOption.ASC;

}
