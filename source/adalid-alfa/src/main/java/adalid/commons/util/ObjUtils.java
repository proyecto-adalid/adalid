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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ObjUtils {

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && b != null && a.equals(b));
    }

    // <editor-fold defaultstate="collapsed" desc="conversion methods">
    public static Boolean toBoolean(Object o) {
        if (o == null) {
            return null;
        }
        return BitUtils.newBoolean(o);
    }

    public static Character toCharacter(Object o) {
        String string = toString(o);
        return string == null ? null : string.isEmpty() ? null : string.charAt(0);
    }

    public static String toString(Object o) {
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    public static Byte toByte(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newByte(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Short toShort(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newShort(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer toInteger(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newInteger(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long toLong(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newLong(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Float toFloat(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newFloat(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double toDouble(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newDouble(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static BigInteger toBigInteger(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newBigInteger(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static BigDecimal toBigDecimal(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return NumUtils.newBigDecimal(o);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Date toDate(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return TimeUtils.newDate(o);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Time toTime(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return TimeUtils.newTime(o);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Timestamp toTimestamp(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return TimeUtils.newTimestamp(o);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="comparison expression methods">
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

    public static Boolean isNull(Object o) {
        return o == null;
    }

    public static Boolean isNotNull(Object o) {
        return o != null;
    }

    public static Boolean isTrue(Object o) {
        return o != null && toBoolean(o);
    }

    public static Boolean isFalse(Object o) {
        return o != null && !toBoolean(o);
    }

    public static Boolean eq(Object x, Object y) {
        Integer i = compare(x, y);
        return (i != null) ? i == 0 : x != null && y != null && x.equals(y);
    }

    public static Boolean neq(Object x, Object y) {
        Integer i = compare(x, y);
        return (i != null) ? i != 0 : x != null && y != null && !x.equals(y);
    }

    public static Boolean gt(Object x, Object y) {
        Integer i = compare(x, y);
        return (i != null && i > 0);
    }

    public static Boolean gteq(Object x, Object y) {
        Integer i = compare(x, y);
        return (i != null && i >= 0);
    }

    public static Boolean lt(Object x, Object y) {
        Integer i = compare(x, y);
        return (i != null && i < 0);
    }

    public static Boolean lteq(Object x, Object y) {
        Integer i = compare(x, y);
        return (i != null && i <= 0);
    }

    public static Integer compare(Object x, Object y) {
        if (x == null || y == null) {
            return null;
        }
        if (x instanceof Boolean && y instanceof Boolean) {
            Boolean bx = (Boolean) x;
            Boolean by = (Boolean) y;
            return bx.compareTo(by);
        }
        if (x instanceof String && y instanceof String) {
            String sx = (String) x;
            String sy = (String) y;
            return sx.compareTo(sy);
        }
        if (x instanceof Number && y instanceof Number) {
            BigDecimal nx = NumUtils.numberToBigDecimal(x);
            BigDecimal ny = NumUtils.numberToBigDecimal(y);
            return nx.compareTo(ny);
        }
        if (x instanceof java.util.Date && y instanceof java.util.Date) {
            java.util.Date dx = (java.util.Date) x;
            java.util.Date dy = (java.util.Date) y;
            return dx.compareTo(dy);
        }
        if (x instanceof String || y instanceof String) {
            String sx = toString(x);
            String sy = toString(y);
            if (sx != null && sy != null) {
                return sx.compareTo(sy);
            }
        }
        return null;
    }

    public static boolean between(Object object, Object minimum, Object maximum) {
        if (object == null) {
            return false;
        } else if (object instanceof String) {
            String val = StrUtils.toString(object);
            String min = StrUtils.toString(minimum);
            String max = StrUtils.toString(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof Byte) {
            Byte val = NumUtils.toByte(object);
            Byte min = NumUtils.toByte(minimum);
            Byte max = NumUtils.toByte(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof Short) {
            Short val = NumUtils.toShort(object);
            Short min = NumUtils.toShort(minimum);
            Short max = NumUtils.toShort(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof Integer) {
            Integer val = NumUtils.toInteger(object);
            Integer min = NumUtils.toInteger(minimum);
            Integer max = NumUtils.toInteger(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof Long) {
            Long val = NumUtils.toLong(object);
            Long min = NumUtils.toLong(minimum);
            Long max = NumUtils.toLong(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof Float) {
            Float val = NumUtils.toFloat(object);
            Float min = NumUtils.toFloat(minimum);
            Float max = NumUtils.toFloat(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof Double) {
            Double val = NumUtils.toDouble(object);
            Double min = NumUtils.toDouble(minimum);
            Double max = NumUtils.toDouble(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof BigInteger) {
            BigInteger val = NumUtils.toBigInteger(object);
            BigInteger min = NumUtils.toBigInteger(minimum);
            BigInteger max = NumUtils.toBigInteger(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof BigDecimal) {
            BigDecimal val = NumUtils.toBigDecimal(object);
            BigDecimal min = NumUtils.toBigDecimal(minimum);
            BigDecimal max = NumUtils.toBigDecimal(maximum);
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else if (object instanceof java.util.Date) {
            java.util.Date val = (java.util.Date) object;
            java.util.Date min = (java.util.Date) minimum;
            java.util.Date max = (java.util.Date) maximum;
            return (min == null || val.compareTo(min) >= 0) && (max == null || val.compareTo(max) <= 0);
        } else {
            return false;
        }
    }

    public static Boolean startsWith(Object x, Object y) {
        if (x == null || y == null) {
            return false;
        } else if (x instanceof String) {
            return y instanceof String
                ? StringUtils.startsWithIgnoreCase(((String) x), ((String) y))
                : StringUtils.startsWithIgnoreCase(((String) x), toString(y));
        }
        return startsWith(toString(x), y);
    }

    public static Boolean notStartsWith(Object x, Object y) {
        return x != null && y != null && !startsWith(x, y);
    }

    public static Boolean contains(Object x, Object y) {
        if (x == null || y == null) {
            return false;
        } else if (x instanceof String) {
            return y instanceof String
                ? StringUtils.containsIgnoreCase(((String) x), ((String) y))
                : StringUtils.containsIgnoreCase(((String) x), toString(y));
        }
        return contains(toString(x), y);
    }

    public static Boolean notContains(Object x, Object y) {
        return x != null && y != null && !contains(x, y);
    }

    public static Boolean endsWith(Object x, Object y) {
        if (x == null || y == null) {
            return false;
        } else if (x instanceof String) {
            return y instanceof String
                ? StringUtils.endsWithIgnoreCase(((String) x), ((String) y))
                : StringUtils.endsWithIgnoreCase(((String) x), toString(y));
        }
        return endsWith(toString(x), y);
    }

    public static Boolean notEndsWith(Object x, Object y) {
        return x != null && y != null && !endsWith(x, y);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="comparison expression methods (is null or)">
    public static Boolean isNullOrTrue(Object o) {
        return o == null || isTrue(o);
    }

    public static Boolean isNullOrFalse(Object o) {
        return o == null || isFalse(o);
    }

    public static Boolean isNullOrEq(Object x, Object y) {
        return x == null || eq(x, y);
    }

    public static Boolean isNullOrNeq(Object x, Object y) {
        return x == null || neq(x, y);
    }

    public static Boolean isNullOrGt(Object x, Object y) {
        return x == null || gt(x, y);
    }

    public static Boolean isNullOrGteq(Object x, Object y) {
        return x == null || gteq(x, y);
    }

    public static Boolean isNullOrLt(Object x, Object y) {
        return x == null || lt(x, y);
    }

    public static Boolean isNullOrLteq(Object x, Object y) {
        return x == null || lteq(x, y);
    }

    public static Boolean isNullOrStartsWith(Object x, Object y) {
        return x == null || startsWith(x, y);
    }

    public static Boolean isNullOrNotStartsWith(Object x, Object y) {
        return x == null || notStartsWith(x, y);
    }

    public static Boolean isNullOrContains(Object x, Object y) {
        return x == null || contains(x, y);
    }

    public static Boolean isNullOrNotContains(Object x, Object y) {
        return x == null || notContains(x, y);
    }

    public static Boolean isNullOrEndsWith(Object x, Object y) {
        return x == null || endsWith(x, y);
    }

    public static Boolean isNullOrNotEndsWith(Object x, Object y) {
        return x == null || notEndsWith(x, y);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="data aggregate expression methods">
    @SuppressWarnings("unchecked")
    public static <T> T coalesce(T... objects) {
        for (T obj : objects) {
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }

    public static Long count(Object... objects) {
        long i = 0;
        for (Object obj : objects) {
            if (obj != null) {
                i++;
            }
        }
        return i;
    }

    public static <T> T maximum(T... objects) {
        T max = null;
        for (T obj : objects) {
            if (obj != null) {
                max = max == null ? obj : gt(obj, max) ? obj : max;
            }
        }
        return max;
    }

    public static <T> T minimum(T... objects) {
        T min = null;
        for (T obj : objects) {
            if (obj != null) {
                min = min == null ? obj : lt(obj, min) ? obj : min;
            }
        }
        return min;
    }

    public static Boolean and(Object... objects) {
        for (Object obj : objects) {
            if (isTrue(obj)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static Boolean nand(Object... objects) {
        return !and(objects);
    }

    public static Boolean or(Object... objects) {
        for (Object obj : objects) {
            if (isTrue(obj)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean nor(Object... objects) {
        return !or(objects);
    }

    public static Boolean naxor(Object... objects) {
        boolean b = false;
        for (Object obj : objects) {
            if (isTrue(obj)) {
                if (b) {
                    return false;
                }
                b = true;
            }
        }
        return b;
    }

    public static Boolean naxnor(Object... objects) {
        return !naxor(objects);
    }

    public static Boolean norOrNaxor(Object... objects) {
        return nor(objects) || naxor(objects);
    }

    public static String concat(Object... objects) {
        String string = "";
        for (Object obj : objects) {
            /*
            if (obj instanceof String) {
                string += obj;
            }
            **/
            if (obj != null) {
                string += obj.toString();
            }
        }
        return string;
    }

    public static BigDecimal sum(Object... objects) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal augend;
        for (Object obj : objects) {
            augend = NumUtils.numberToBigDecimal(obj);
            /*
            if (augend == null) {
                return null;
            }
            **/
            if (augend != null) {
                result = result.add(augend);
            }
        }
        return result;
    }

    public static BigDecimal product(Object... objects) {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal multiplicand;
        int i = 0;
        for (Object obj : objects) {
            multiplicand = NumUtils.numberToBigDecimal(obj);
            /*
            if (multiplicand == null) {
                return null;
            }
            **/
            if (multiplicand != null) {
                i++;
                result = result.multiply(multiplicand);
            }
        }
        return i > 1 ? result : null;
    }

    public static BigDecimal average(Object... objects) {
        BigDecimal dividend = sum(objects);
        if (dividend == null) {
            return null;
        }
        long count = count(objects);
        if (count == 0) {
            return null;
        }
        BigDecimal divisor = BigDecimal.valueOf(count);
        return dividend.divide(divisor, RoundingMode.HALF_UP);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ordered-pair expression methods">
    public static <T> T nullif(T x, T y) {
        return eq(x, y) ? null : x;
    }

    public static Boolean xor(Object x, Object y) {
        return !isTrue(x) && isTrue(y) || isTrue(x) && !isTrue(y);
    }

    public static Boolean xnor(Object x, Object y) {
        return !xor(x, y);
    }

    public static Boolean xImpliesY(Object x, Object y) {
        return !isTrue(x) || isTrue(y);
    }

    public static BigDecimal xPlusY(Object x, Object y) {
        BigDecimal bx = NumUtils.numberToBigDecimal(x);
        BigDecimal by = NumUtils.numberToBigDecimal(y);
        return bx == null || by == null ? null : bx.add(by);
    }

    public static BigDecimal xMinusY(Object x, Object y) {
        BigDecimal bx = NumUtils.numberToBigDecimal(x);
        BigDecimal by = NumUtils.numberToBigDecimal(y);
        return bx == null || by == null ? null : bx.subtract(by);
    }

    public static BigDecimal xMultipliedByY(Object x, Object y) {
        BigDecimal bx = NumUtils.numberToBigDecimal(x);
        BigDecimal by = NumUtils.numberToBigDecimal(y);
        return bx == null || by == null ? null : bx.multiply(by);
    }

    public static BigDecimal xDividedIntoY(Object x, Object y) {
        BigDecimal bx = NumUtils.numberToBigDecimal(x);
        BigDecimal by = NumUtils.numberToBigDecimal(y);
        return bx == null || by == null || by.compareTo(BigDecimal.ZERO) == 0 ? null : bx.divide(by, RoundingMode.HALF_UP);
    }

    public static BigDecimal xRaisedToTheY(Object x, Object y) {
        BigDecimal bx = NumUtils.numberToBigDecimal(x);
        Integer iy = NumUtils.numberToInteger(y);
        return bx == null || iy == null ? null : bx.pow(iy);
    }

    public static java.util.Date addYears(Object x, Object y) {
        Timestamp tx = TimeUtils.toTimestamp(x);
        Integer iy = tx == null ? null : NumUtils.numberToInteger(y);
        Timestamp tz = tx == null || iy == null ? null : TimeUtils.addTimestamp(tx, iy, 'Y');
        return tz == null ? null : TimeUtils.getFittestSqlExtension(tz);
    }

    public static java.util.Date addMonths(Object x, Object y) {
        Timestamp tx = TimeUtils.toTimestamp(x);
        Integer iy = tx == null ? null : NumUtils.numberToInteger(y);
        Timestamp tz = tx == null || iy == null ? null : TimeUtils.addTimestamp(tx, iy, 'M');
        return tz == null ? null : TimeUtils.getFittestSqlExtension(tz);
    }

    public static java.util.Date addWeeks(Object x, Object y) {
        Timestamp tx = TimeUtils.toTimestamp(x);
        Integer iy = tx == null ? null : NumUtils.numberToInteger(y);
        Timestamp tz = tx == null || iy == null ? null : TimeUtils.addTimestamp(tx, iy * 7, 'D');
        return tz == null ? null : TimeUtils.getFittestSqlExtension(tz);
    }

    public static java.util.Date addDays(Object x, Object y) {
        Timestamp tx = TimeUtils.toTimestamp(x);
        Integer iy = tx == null ? null : NumUtils.numberToInteger(y);
        Timestamp tz = tx == null || iy == null ? null : TimeUtils.addTimestamp(tx, iy, 'D');
        return tz == null ? null : TimeUtils.getFittestSqlExtension(tz);
    }

    public static java.util.Date addHours(Object x, Object y) {
        Timestamp tx = TimeUtils.toTimestamp(x);
        Integer iy = tx == null ? null : NumUtils.numberToInteger(y);
        Timestamp tz = tx == null || iy == null ? null : TimeUtils.addTimestamp(tx, iy, 'h');
        return tz == null ? null : TimeUtils.getFittestSqlExtension(tz);
    }

    public static java.util.Date addMinutes(Object x, Object y) {
        Timestamp tx = TimeUtils.toTimestamp(x);
        Integer iy = tx == null ? null : NumUtils.numberToInteger(y);
        Timestamp tz = tx == null || iy == null ? null : TimeUtils.addTimestamp(tx, iy, 'm');
        return tz == null ? null : TimeUtils.getFittestSqlExtension(tz);
    }

    public static java.util.Date addSeconds(Object x, Object y) {
        Timestamp tx = TimeUtils.toTimestamp(x);
        Integer iy = tx == null ? null : NumUtils.numberToInteger(y);
        Timestamp tz = tx == null || iy == null ? null : TimeUtils.addTimestamp(tx, iy, 's');
        return tz == null ? null : TimeUtils.getFittestSqlExtension(tz);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="scalar expression methods">
    public static Boolean not(Object o) {
        return o != null && !toBoolean(o);
    }

    public static String lower(Object o) {
        String string = o instanceof String ? ((String) o) : null;
        return string == null ? null : string.toLowerCase();
    }

    public static String upper(Object o) {
        String string = o instanceof String ? ((String) o) : null;
        return string == null ? null : string.toUpperCase();
    }

    public static String capitalize(Object o) {
        String string = o instanceof String ? ((String) o) : null;
        return string == null ? null : StringUtils.capitalize(string);
    }

    public static String uncapitalize(Object o) {
        String string = o instanceof String ? ((String) o) : null;
        return string == null ? null : StringUtils.uncapitalize(string);
    }

    public static String trim(Object o) {
        String string = o instanceof String ? ((String) o) : null;
        return string == null ? null : string.trim();
    }

    public static String ltrim(Object o) {
        String string = o instanceof String ? ((String) o) : null;
        return string == null ? null : StrUtils.ltrim(string);
    }

    public static String rtrim(Object o) {
        String string = o instanceof String ? ((String) o) : null;
        return string == null ? null : StrUtils.rtrim(string);
    }

    public static Number modulus(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Byte) {
            Byte pdq = (Byte) o;
            return NumUtils.newByte(Math.abs(pdq));
        } else if (o instanceof Short) {
            Short pdq = (Short) o;
            return NumUtils.newShort(Math.abs(pdq));
        } else if (o instanceof Integer) {
            Integer pdq = (Integer) o;
            return Math.abs(pdq);
        } else if (o instanceof Long) {
            Long pdq = (Long) o;
            return Math.abs(pdq);
        } else if (o instanceof Float) {
            Float pdq = (Float) o;
            return Math.abs(pdq);
        } else if (o instanceof Double) {
            Double pdq = (Double) o;
            return Math.abs(pdq);
        } else if (o instanceof BigInteger) {
            BigInteger pdq = (BigInteger) o;
            return pdq.abs();
        } else if (o instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) o;
            return pdq.abs();
        }
        return null;
    }

    public static Number opposite(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Byte) {
            Byte pdq = (Byte) o;
            return NumUtils.newByte(0 - pdq);
        } else if (o instanceof Short) {
            Short pdq = (Short) o;
            return NumUtils.newShort(0 - pdq);
        } else if (o instanceof Integer) {
            Integer pdq = (Integer) o;
            return 0 - pdq;
        } else if (o instanceof Long) {
            Long pdq = (Long) o;
            return 0L - pdq;
        } else if (o instanceof Float) {
            Float pdq = (Float) o;
            return 0.0F - pdq;
        } else if (o instanceof Double) {
            Double pdq = (Double) o;
            return 0.0D - pdq;
        } else if (o instanceof BigInteger) {
            BigInteger pdq = (BigInteger) o;
            return pdq.negate();
        } else if (o instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) o;
            return pdq.negate();
        }
        return null;
    }

    public static BigDecimal reciprocal(Object o) {
        BigDecimal pdq = NumUtils.numberToBigDecimal(o);
        return pdq == null || pdq.compareTo(BigDecimal.ZERO) == 0 ? null : BigDecimal.ONE.divide(pdq, RoundingMode.HALF_UP);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="generic methods">
    @SuppressWarnings("unchecked")
    public static <X> X coalesceX(X... objects) {
        return (X) coalesce(objects);
    }
    // </editor-fold>

}
