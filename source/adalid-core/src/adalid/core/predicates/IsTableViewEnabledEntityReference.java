/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.predicates;

import adalid.core.enums.MasterDetailView;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityReference;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsTableViewEnabledEntityReference implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof EntityReference) {
            EntityReference reference = (EntityReference) object;
            Entity declaringEntity = reference.getDeclaringEntity();
            if (declaringEntity != null) { // && declaringEntity.isSelectEnabled()
//              Entity parentProperty = declaringEntity.getParentProperty();
//              if (declaringEntity.isTreeViewEnabled() && reference.equals(parentProperty)) {
//                  return false;
//              }
                MasterDetailView masterDetailView = reference.getMasterDetailView();
                return MasterDetailView.TABLE.equals(masterDetailView)
                    || MasterDetailView.TABLE_AND_DETAIL.equals(masterDetailView);
            }
        }
        return false;
    }

}
