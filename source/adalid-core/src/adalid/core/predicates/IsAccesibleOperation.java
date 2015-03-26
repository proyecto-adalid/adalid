/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.predicates;

import adalid.core.Operation;
import adalid.core.enums.OperationAccess;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsAccesibleOperation implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof Operation) {
            Operation operation = (Operation) object;
            OperationAccess access = operation.getOperationAccess();
            if (access != null) {
                switch (access) {
                    case PUBLIC:
                    case PROTECTED:
                    case RESTRICTED:
                        return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

}
