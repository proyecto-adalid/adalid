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

    public static long defaultIfNull(Long l, long defaultValue) {
        return l == null ? defaultValue : l;
    }

    public static long getNewId(String string) {
        String number = StrUtils.getLongNumericCode(string); // 19 digitos: 1x + 10c + 3l + 5s;
        return number == null ? getNewId() : Long.valueOf(number);
    }

    public static long getNewId() {
        long millis = TimeUtils.currentTimeMillis();
        long thread = Thread.currentThread().getId();
        return 100000L * millis + 10L * (thread % 10000L); // 18 digitos: 13m + 5t
    }

}
