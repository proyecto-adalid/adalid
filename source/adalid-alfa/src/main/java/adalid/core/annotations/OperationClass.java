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
 * La anotación OperationClass se utiliza para establecer atributos básicos de la operación. Es válida para cualquier clase de operación de negocio.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OperationClass {

    /**
     * confirmation indica si las vistas (páginas) de procesamiento deben solicitar, o no, confirmación al ejecutar la operación. Su valor es uno de
     * los elementos de la enumeración Kleenean. Seleccione TRUE para solicitar confirmación; en caso contrario, seleccione FALSE. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es el
     * establecido con el método <b>setBusinessOperationConfirmationRequired</b> del proyecto maestro.
     *
     * @return confirmation
     */
    Kleenean confirmation() default Kleenean.UNSPECIFIED; // FALSE

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
    OperationAccess access() default OperationAccess.RESTRICTED;

    /**
     * asynchronous indica si la operación se debe ejecutar de manera síncrona o asíncrona. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE si se debe ejecutar de manera asíncrona; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE para operaciones de
     * clase y FALSE para operaciones de instancia.
     * <p>
     * Este elemento no es relevante si la operación es un proceso de exportación (extensión de ExportOperation), un informe (extensión de
     * ReportOperation), o un procedimiento almacenado en la base de datos (extensión de ProcedureOperation) de tipo VOID, ya que tales operaciones
     * siempre se deben ejecutar de manera asíncrona.
     *
     * @return asynchronous
     */
    Kleenean asynchronous() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * shell indica si la operación se debe ejecutar utilizando un proceso nativo del sistema operativo, cuando el uso de procesos nativos esté
     * permitido para la clase de operación (vea los métodos setExporterShellEnabled, setReporterShellEnabled y setSqlAgentShellEnabled). Solo aplica
     * si la operación es un proceso de exportación (extensión de ExportOperation), un informe (extensión de ReportOperation), u otra clase de
     * operación de negocio (extensión de ProcessOperation o ProcedureOperation) que esté implementada mediante una función o procedimiento almacenado
     * en la base de datos y que se ejecute asincrónicamente. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para
     * utilizar un proceso nativo; seleccione FALSE para utilizar un subproceso del servidor de aplicaciones. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE, si la operación es un
     * proceso de exportación, un informe o un procedimiento almacenado en la base de datos de tipo VOID; en los demás casos, FALSE.
     * <p>
     * Este elemento no es relevante si la operación es un procedimiento almacenado en la base de datos de tipo VOID, ya que tales operaciones siempre
     * se deben ejecutar utilizando un proceso nativo.
     *
     * @return shell
     */
    Kleenean shell() default Kleenean.UNSPECIFIED;

    /**
     * complex indica si la operación es, o no, una operación compleja. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE
     * si la operación es compleja; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return complex
     */
    Kleenean complex() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * logging especifica cuando se deben registrar pistas de auditoría de la ejecución de la operación. Su valor es uno de los elementos de la
     * enumeración OperationLogging. Seleccione SUCCESS, FAILURE o BOTH si las pistas se deben registrar cuando la operación se ejecute exitosamente,
     * cuando se produzca un error al ejecutar la operación, o en ambos casos, respectivamente. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es SUCCESS.
     * <p>
     * Este elemento no es relevante si el tipo de control de acceso de la operación es PRIVATE, ya que nunca se registran pistas de auditoría para
     * tales operaciones.
     *
     * @return logging
     */
    OperationLogging logging() default OperationLogging.UNSPECIFIED; // SUCCESS

}
