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
package adalid.core.predicates;

import adalid.core.*;
import adalid.core.interfaces.*;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsReportOperationInEntity implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof Entity entity) {
            IsReportOperation isReportOperation = new IsReportOperation();
            for (Operation operation : entity.getOperationsList()) {
                if (isReportOperation.evaluate(operation)) {
                    return true;
                }
            }
        }
        return false;
    }

}
