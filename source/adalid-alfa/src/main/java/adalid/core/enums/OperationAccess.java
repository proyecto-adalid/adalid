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
package adalid.core.enums;

/**
 * @author Jorge Campins
 */
public enum OperationAccess {

    UNSPECIFIED,
    /**
     * Las operaciones con acceso PRIVATE no pueden ser ejecutadas directamente por los usuarios del sistema. Son ejecutadas solo por otras
     * operaciones, a través de la Interfaz de Programación (API).
     */
    PRIVATE,
    /**
     * Las operaciones con acceso PUBLIC pueden ser ejecutadas por todos los usuarios del sistema, aún cuando no tengan autorización explícita para
     * hacerlo.
     */
    PUBLIC,
    /**
     * Las operaciones con acceso PROTECTED pueden ser ejecutadas por usuarios designados como súper-usuario ó por usuarios explícitamente autorizados
     * para hacerlo.
     */
    PROTECTED,
    /**
     * Las operaciones con acceso RESTRICTED pueden ser ejecutadas por usuarios designados como súper-usuario ó por usuarios explícitamente
     * autorizados para hacerlo. Además, a diferencia de las operaciones con acceso PROTECTED, las operaciones personalizables con acceso RESTRICTED,
     * también pueden ser ejecutadas por usuarios que no tengan autorización explícita para hacerlo, pero solo sobre las instancias de la clase de las
     * que el usuario sea el propietario.
     */
    RESTRICTED

}
