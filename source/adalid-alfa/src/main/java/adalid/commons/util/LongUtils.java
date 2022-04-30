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
public class LongUtils {

    public static final long FALSE = 0L;

    public static final long TRUE = 1L;

    public static long valueOf(Boolean b) {
        return b == null || !b ? FALSE : TRUE;
    }

    public static long valueOf(Integer i) {
        return i == null ? 0 : i;
    }

    public static long valueOf(Integer i, long j) {
        return i == null ? j : i;
    }

    public static long valueOf(Long l) {
        return l == null ? 0 : l;
    }

    public static long valueOf(Long l, long j) {
        return l == null ? j : l;
    }

    public static Long valueOf(String string, Long defaultValue) {
        if (string != null && !string.isEmpty() && string.matches("^-?\\d{1,19}$")) {
            try {
                return Long.valueOf(string);
            } catch (NumberFormatException e) {
            }
        }
        return defaultValue;
    }

    public static Long valueOf(Object o) {
        return o instanceof Long ? (Long) o : null;
    }

    public static long defaultIfNull(Long l, long defaultValue) {
        return l == null ? defaultValue : l;
    }

    public static long getNewId(String string) {
        String number = StrUtils.getLongNumericCode(string);
        return number == null ? getNewId() : Long.valueOf(number);
    }

    public static long getNewId() {
        long millis = TimeUtils.currentTimeMillis();
        long thread = Thread.currentThread().getId();
        return 100000L * millis + 10L * (thread % 10000L); // 18 digitos: 13m + 5t
    }

}
