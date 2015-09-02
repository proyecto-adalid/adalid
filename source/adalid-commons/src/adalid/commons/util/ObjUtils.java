/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ObjUtils {

    public static boolean isBlank(Object object) {
        if (object == null) {
            return true;
        }
        if (object.getClass().isArray()) {
            Object[] array = (Object[]) object;
            if (array.length > 0) {
                for (Object element : array) {
                    if (!isBlank(element)) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (object instanceof KVP) {
            KVP kvp = (KVP) object;
            String string = kvp.toString();
            return StringUtils.isBlank(string);
        }
        if (object instanceof String) {
            String string = (String) object;
            return StringUtils.isBlank(string);
        }
        return false;
    }

}
