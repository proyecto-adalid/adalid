/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.predicates;

import adalid.core.Display;
import adalid.core.Project;
import adalid.core.TLC;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.PersistentEntity;
import java.util.List;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsPersistentEntityWithDisplay implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof PersistentEntity) {
            Entity entity = (Entity) object;
            Project project = TLC.getProject();
            if (project != null) {
                List<? extends Display> displays = project.getDisplaysList();
                if (displays != null) {
                    for (Display display : displays) {
                        if (entity.equals(display.getEntity())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
