/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.enums;

/**
 * @author Jorge Campins
 */
public enum OperationAccess {

    UNSPECIFIED, PRIVATE, PUBLIC, PROTECTED, RESTRICTED

}

/*
 * Las operaciones con acceso PRIVATE no pueden ser ejecutadas directamente por los usuarios del sistema. Son ejecutadas solo por otras operaciones, a
 * través de la Interfaz de Programación (API).
 *
 * Las operaciones con acceso PUBLIC, PROTECTED y RESTRICTED pueden ser ejecutadas directamente por los usuarios del sistema, a través de la Interfaz
 * de Usuario (UI).
 *
 * Las operaciones con acceso PUBLIC pueden ser ejecutadas por todos los usuarios del sistema, aún cuando no tengan autorización explícita para ello.
 *
 * Las operaciones con acceso PROTECTED pueden ser ejecutadas por usuarios designados como súper-usuario ó por usuarios explícitamente autorizados.
 *
 * Las operaciones con acceso RESTRICTED pueden ser ejecutadas por usuarios designados como súper-usuario ó por usuarios explícitamente autorizados.
 * Además, a diferencia de las operaciones con acceso PROTECTED, las operaciones personalizables con acceso RESTRICTED, también pueden ser ejecutadas
 * por usuarios que no tengan autorización explícita, pero solo sobre las instancias de la clase de las que el usuario sea el propietario.
 */
