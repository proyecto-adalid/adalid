/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
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
