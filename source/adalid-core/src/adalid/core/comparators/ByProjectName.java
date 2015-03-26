/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.comparators;

import adalid.core.Project;
import java.util.Comparator;

/**
 * @author Jorge Campins
 */
public class ByProjectName implements Comparator<Project> {

    @Override
    public int compare(Project o1, Project o2) {
        if (o1 != null && o2 != null) {
            return o1.getName().compareTo(o2.getName());
        }
        return 0;
    }

}
