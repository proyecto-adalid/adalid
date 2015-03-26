/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.comparators;

import adalid.core.interfaces.Entity;
import java.util.Comparator;

/**
 * @author Jorge Campins
 */
public class ByEntityName implements Comparator<Entity> {

    @Override
    public int compare(Entity o1, Entity o2) {
        if (o1 != null && o2 != null) {
            return o1.getName().compareTo(o2.getName());
        }
        return 0;
    }

}
