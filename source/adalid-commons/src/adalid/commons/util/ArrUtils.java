/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.util;

import java.lang.reflect.Array;

/**
 * @author Jorge Campins
 */
public class ArrUtils {

    @SuppressWarnings("unchecked")
    public static <T> T[] join(Class<T> type, T[]... arrays) {
        if (type == null || arrays == null || arrays.length == 0) {
            return null;
        }
        int length = 0;
        for (T[] array : arrays) {
            if (array != null) {
                length += array.length;
            }
        }
        if (length == 0) {
            return null;
        }
        T[] join = (T[]) Array.newInstance(type, length);
        int position = 0;
        for (T[] array : arrays) {
            if (array != null && array.length > 0) {
                System.arraycopy(array, 0, join, position, array.length);
                position += array.length;
            }
        }
        return join;
    }

    public static <T> T[] clone(T[] array) {
        return array == null ? null : array.clone();
    }

}
