/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.predicates;

import adalid.core.interfaces.Entity;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author Jorge Campins
 */
public class IsEntityNameNotExcluded implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof Entity) {
            Entity entity = (Entity) object;
            String name = entity.getName();
            return name != null && !ArrayUtils.contains(_excludedNames, name);
        }
        return false;
    }

    /**
     * the excluded names array.
     */
    private String[] _excludedNames;

    /**
     * @return the excluded names array
     */
    public String[] getExcludedNames() {
        return _excludedNames;
    }

    /**
     * @param names the excluded names array to set
     */
    public void setExcludedNames(String[] names) {
        _excludedNames = names;
    }

}
