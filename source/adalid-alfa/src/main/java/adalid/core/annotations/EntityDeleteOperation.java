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
 * La anotación EntityDeleteOperation se utiliza para establecer atributos de la operación delete de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityDeleteOperation {

    /**
     * enabled indica si las vistas (páginas) de registro deben permitir, o no, eliminar instancias de la entidad, es decir, eliminar filas de la
     * tabla de la base de datos correspondiente a la entidad. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para
     * permitir eliminar; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return enabled
     */
    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * confirmation indica si las vistas (páginas) de registro deben solicitar, o no, confirmación al eliminar instancias de la entidad, es decir,
     * eliminar filas de la tabla de la base de datos correspondiente a la entidad. Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE para solicitar confirmación; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED
     * para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es el establecido con el método
     * <b>setDatabaseOperationConfirmationRequired</b> del proyecto maestro.
     *
     * @return confirmation
     */
    Kleenean confirmation() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * access especifica el tipo de control de acceso de la operación. Este elemento es relevante solo si el valor especificado, o determinado, para
     * el elemento enabled es TRUE. Su valor es uno de los elementos de la enumeración OperationAccess. Seleccione PRIVATE, PUBLIC, PROTECTED o
     * RESTRICTED si la operación es de acceso privado, público, protegido o restringido, respectivamente. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es RESTRICTED. Las operaciones
     * con acceso privado no pueden ser ejecutadas directamente por los usuarios del sistema. Son ejecutadas solo por otras operaciones, a través de
     * la Interfaz de Programación (API). Las operaciones con acceso público, protegido y restringido si pueden ser ejecutadas directamente por los
     * usuarios del sistema, a través de la Interfaz de Usuario (UI). Las operaciones con acceso público pueden ser ejecutadas por todos los usuarios
     * del sistema, aun cuando no tengan autorización explícita para ello. Las operaciones con acceso protegido pueden ser ejecutadas por usuarios
     * designados como súper-usuario o por usuarios explícitamente autorizados. Al igual que las operaciones con acceso protegido, las operaciones con
     * acceso restringido pueden ser ejecutadas por usuarios designados como súper-usuario o por usuarios explícitamente autorizados. Además, a
     * diferencia de las operaciones con acceso protegido, las operaciones personalizables con acceso restringido, también pueden ser ejecutadas por
     * usuarios que no tengan autorización explícita, pero solo sobre las instancias de la entidad que sean propiedad del usuario.
     *
     * @return access
     */
    OperationAccess access() default OperationAccess.UNSPECIFIED; // RESTRICTED;

    /**
     * logging especifica cuando se deben registrar pistas de auditoría de la ejecución de la operación. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la enumeración OperationLogging. Seleccione
     * SUCCESS, FAILURE o BOTH si las pistas se deben registrar cuando la operación se ejecute exitosamente, cuando se produzca un error al ejecutar
     * la operación, o en ambos casos, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es SUCCESS.
     * <p>
     * Este elemento no es relevante si el tipo de control de acceso de la operación es PRIVATE, ya que nunca se registran pistas de auditoría para
     * tales operaciones.
     *
     * @return logging
     */
    OperationLogging logging() default OperationLogging.UNSPECIFIED; // SUCCESS

}
