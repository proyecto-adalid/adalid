/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.comparators;

import adalid.core.Project;
import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * @author Jorge Campins
 */
public class ByProjectDeclaringFieldName implements Comparator<Project> {

    @Override
    public int compare(Project o1, Project o2) {
        if (o1 != null && o2 != null) {
            return getName(o1).compareTo(getName(o2));
        }
        return 0;
    }

    private String getName(Project p) {
        Field declaringField = p.getDeclaringField();
        return declaringField == null ? p.getName() : declaringField.getName();
    }

}
