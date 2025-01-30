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

    public static Integer valueOf(String string, Integer defaultValue) {
        if (string != null && !string.isEmpty() && string.matches("^-?\\d{1,10}$")) {
            try {
                return Integer.valueOf(string);
            } catch (NumberFormatException e) {
            }
        }
        return defaultValue;
    }

    public static Integer valueOf(Object o) {
        return o instanceof Integer ? (Integer) o : null;
    }

    private static int version = -1;

    public static synchronized int getNewId() {
        if (++version == 100000) {
            version = 0;
        }
        long thread = Thread.currentThread().threadId(); // getId(); is deprecated since JDK 19
        return version + 100000 * (int) (thread % 10000L); // 9 digitos: 4t + 5v
    }

}
