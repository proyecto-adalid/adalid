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
package adalid.core.exceptions;

/**
 * @author Jorge Campins
 */
public class UnexpectedRuntimeException extends RuntimeException {

    public UnexpectedRuntimeException() {
        super();
    }

    public UnexpectedRuntimeException(String message) {
        super(message);
    }

    public UnexpectedRuntimeException(Throwable cause) {
        super(cause);
    }

    public UnexpectedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
