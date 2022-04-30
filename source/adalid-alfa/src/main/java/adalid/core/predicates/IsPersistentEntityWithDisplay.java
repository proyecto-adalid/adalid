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
