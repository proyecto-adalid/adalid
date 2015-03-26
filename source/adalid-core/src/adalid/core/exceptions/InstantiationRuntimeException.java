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
public class InstantiationRuntimeException extends RuntimeException {

    public InstantiationRuntimeException() {
        super();
    }

    public InstantiationRuntimeException(String message) {
        super(message);
    }

    public InstantiationRuntimeException(Throwable cause) {
        super(cause);
    }

    public InstantiationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
