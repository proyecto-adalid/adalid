/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.util;

import java.util.Set;

/**
 * @author Jorge Campins
 */
public class SetUtils {

    public static int getIndex(Set<? extends Object> set, Object value) {
        if (set == null || value == null) {
            return -1;
        }
        int result = 0;
        for (Object element : set) {
            if (value.equals(element)) {
                return result;
            }
            result++;
        }
        return -1;
    }

}
