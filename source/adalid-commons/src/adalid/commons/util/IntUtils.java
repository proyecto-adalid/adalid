/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.util;

/**
 * @author Jorge Campins
 */
public class IntUtils {

    public static final int FALSE = 0;

    public static final int TRUE = 1;

    public static int valueOf(Boolean b) {
        return b == null || !b ? FALSE : TRUE;
    }

    public static int valueOf(Integer i) {
        return i == null ? 0 : i;
    }

    public static int valueOf(Integer i, int j) {
        return i == null ? j : i;
    }

    public static int valueOf(Long l) {
        return l == null ? 0 : l.intValue();
    }

    public static int valueOf(Long l, int j) {
        return l == null ? j : l.intValue();
    }

    private static int version = -1;

    public static synchronized int getNewId() {
        if (++version == 100000) {
            version = 0;
        }
        long thread = Thread.currentThread().getId();
        return version + 100000 * (int) (thread % 10000L); // 9 digitos: 4t + 5v
    }

}
