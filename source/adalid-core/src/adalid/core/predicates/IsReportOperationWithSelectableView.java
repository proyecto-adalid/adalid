/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.predicates;

import adalid.core.ReportOperation;
import adalid.core.View;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsReportOperationWithSelectableView implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof ReportOperation) {
            ReportOperation operation = (ReportOperation) object;
            View view = operation.getView();
            return view != null && view.isSelectable();
        }
        return false;
    }

}
