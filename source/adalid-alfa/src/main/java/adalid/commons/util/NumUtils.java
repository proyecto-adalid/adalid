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

import adalid.commons.bundles.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class NumUtils {

    // <editor-fold defaultstate="collapsed" desc="format/parse">
    private static final String BUNDLE_KEY_PREFIX = NumUtils.class.getName() + ".";

    private static final Map<Locale, String> _decimalSeparatorMap = new LinkedHashMap<>();

    public static String getDecimalSeparator() {
        Locale locale = Bundle.getLocale();
        if (_decimalSeparatorMap.containsKey(locale)) {
            return _decimalSeparatorMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "decimal.separator.char");
        return StringUtils.defaultIfBlank(string, ".");
    }

    public static void setDecimalSeparator(Locale locale, char separator) {
        if (locale != null) {
            if (separator == ' ') {
                _decimalSeparatorMap.remove(locale);
            } else {
                _decimalSeparatorMap.put(locale, "" + separator);
            }
        }
    }

    private static final Map<Locale, String> _thousandSeparatorMap = new LinkedHashMap<>();

    public static String getThousandSeparator() {
        Locale locale = Bundle.getLocale();
        if (_thousandSeparatorMap.containsKey(locale)) {
            return _thousandSeparatorMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "thousand.separator.char");
        return StringUtils.defaultIfBlank(string, ",");
    }

    public static void setThousandSeparator(Locale locale, char separator) {
        if (locale != null) {
            if (separator == ' ') {
                _thousandSeparatorMap.remove(locale);
            } else {
                _thousandSeparatorMap.put(locale, "" + separator);
            }
        }
    }

    public static String format(Number number) {
        return format(number, Locale.getDefault());
    }

    public static String format(Number number, Locale locale) {
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        if (number instanceof BigDecimal) {
            BigDecimal big = (BigDecimal) number;
            formatter.setMaximumFractionDigits(big.scale());
        }
        return formatter.format(number);
    }

    public static Number parse(String value) {
        return parse(value, false);
    }

    public static Number parse(String value, boolean integerOnly) {
        return parse(value, integerOnly, Locale.getDefault());
    }

    public static Number parse(String value, boolean integerOnly, Locale locale) {
        NumberFormat parser = NumberFormat.getNumberInstance(locale);
        parser.setParseIntegerOnly(integerOnly);
        if (parser instanceof DecimalFormat) {
            DecimalFormat decimalParser = (DecimalFormat) parser;
            decimalParser.setParseBigDecimal(true);
        }
        ParsePosition position = new ParsePosition(0);
        Number number = parser.parse(value, position);
        int i = position.getIndex();
        int l = value.length();
        return i < l ? null : number;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ceiling">
    public static Number ceiling(Number dividend, int divisor) {
        return dividend == null || divisor == 0 ? dividend
            : dividend instanceof Byte ? ceiling((Byte) dividend, divisor)
                : dividend instanceof Short ? ceiling((Short) dividend, divisor)
                    : dividend instanceof Integer ? ceiling((Integer) dividend, divisor)
                        : dividend instanceof Long ? ceiling((Long) dividend, divisor)
                            : dividend instanceof Float ? ceiling((Float) dividend, divisor)
                                : dividend instanceof Double ? ceiling((Double) dividend, divisor)
                                    : dividend instanceof BigInteger ? ceiling((BigInteger) dividend, divisor)
                                        : dividend instanceof BigDecimal ? ceiling((BigDecimal) dividend, divisor)
                                            : null;
    }

    public static Byte ceiling(Byte dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        int factor = dividend / divisor + 1;
        int product = factor * divisor;
        return product < Byte.MIN_VALUE || product > Byte.MAX_VALUE ? dividend : (byte) product;
    }

    public static Short ceiling(Short dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        int factor = dividend / divisor + 1;
        int product = factor * divisor;
        return product < Short.MIN_VALUE || product > Short.MAX_VALUE ? dividend : (short) product;
    }

    public static Integer ceiling(Integer dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        long factor = dividend / divisor + 1;
        long product = factor * divisor;
        return product < Integer.MIN_VALUE || product > Integer.MAX_VALUE ? dividend : (int) product;
    }

    public static Long ceiling(Long dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        long factor = dividend / divisor + 1;
        long limit = Long.signum(factor) == Integer.signum(divisor) ? Long.MAX_VALUE : Long.MIN_VALUE;
        boolean overflow = factor > 0 && factor > limit / divisor || factor < 0 && factor < limit / divisor;
        long product = overflow ? dividend : factor * divisor;
        return product;
    }

    public static Float ceiling(Float dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        double factor = Math.floor(dividend / divisor) + 1;
        double product = factor * divisor;
        return product < Float.MIN_VALUE || product > Float.MAX_VALUE ? dividend : (float) product;
    }

    public static Double ceiling(Double dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        double factor = Math.floor(dividend / divisor) + 1;
        double limit = Math.signum(factor) == Integer.signum(divisor) ? Double.MAX_VALUE : Double.MIN_VALUE;
        boolean overflow = factor > 0 && factor > limit / divisor || factor < 0 && factor < limit / divisor;
        double product = overflow ? dividend : factor * divisor;
        return product;
    }

    public static BigInteger ceiling(BigInteger dividend, int divisor) {
        BigInteger denominator = BigInteger.valueOf(divisor);
        if (dividend == null || divisor == 0 || dividend.remainder(denominator).equals(BigInteger.ZERO)) {
            return dividend;
        }
        BigInteger factor = dividend.divide(denominator).add(BigInteger.ONE);
        BigInteger product = factor.multiply(denominator);
        return product;
    }

    public static BigDecimal ceiling(BigDecimal dividend, int divisor) {
        BigDecimal denominator = BigDecimal.valueOf(divisor);
        if (dividend == null || divisor == 0 || dividend.remainder(denominator).compareTo(BigDecimal.ZERO) == 0) {
            return dividend;
        }
        BigDecimal factor = dividend.divideToIntegralValue(denominator).add(BigDecimal.ONE);
        BigDecimal product = factor.multiply(denominator);
        return product;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="floor">
    public static Number floor(Number dividend, int divisor) {
        return dividend == null || divisor == 0 ? dividend
            : dividend instanceof Byte ? floor((Byte) dividend, divisor)
                : dividend instanceof Short ? floor((Short) dividend, divisor)
                    : dividend instanceof Integer ? floor((Integer) dividend, divisor)
                        : dividend instanceof Long ? floor((Long) dividend, divisor)
                            : dividend instanceof Float ? floor((Float) dividend, divisor)
                                : dividend instanceof Double ? floor((Double) dividend, divisor)
                                    : dividend instanceof BigInteger ? floor((BigInteger) dividend, divisor)
                                        : dividend instanceof BigDecimal ? floor((BigDecimal) dividend, divisor)
                                            : null;
    }

    public static Byte floor(Byte dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        int factor = dividend / divisor;
        int product = factor * divisor;
        return product < Byte.MIN_VALUE || product > Byte.MAX_VALUE ? dividend : (byte) product;
    }

    public static Short floor(Short dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        int factor = dividend / divisor;
        int product = factor * divisor;
        return product < Short.MIN_VALUE || product > Short.MAX_VALUE ? dividend : (short) product;
    }

    public static Integer floor(Integer dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        long factor = dividend / divisor;
        long product = factor * divisor;
        return product < Integer.MIN_VALUE || product > Integer.MAX_VALUE ? dividend : (int) product;
    }

    public static Long floor(Long dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        long factor = dividend / divisor;
        long limit = Long.signum(factor) == Integer.signum(divisor) ? Long.MAX_VALUE : Long.MIN_VALUE;
        boolean overflow = factor > 0 && factor > limit / divisor || factor < 0 && factor < limit / divisor;
        long product = overflow ? dividend : factor * divisor;
        return product;
    }

    public static Float floor(Float dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        double factor = Math.floor(dividend / divisor);
        double product = factor * divisor;
        return product < Float.MIN_VALUE || product > Float.MAX_VALUE ? dividend : (float) product;
    }

    public static Double floor(Double dividend, int divisor) {
        if (dividend == null || divisor == 0 || dividend % divisor == 0) {
            return dividend;
        }
        double factor = Math.floor(dividend / divisor);
        double limit = Math.signum(factor) == Integer.signum(divisor) ? Double.MAX_VALUE : Double.MIN_VALUE;
        boolean overflow = factor > 0 && factor > limit / divisor || factor < 0 && factor < limit / divisor;
        double product = overflow ? dividend : factor * divisor;
        return product;
    }

    public static BigInteger floor(BigInteger dividend, int divisor) {
        BigInteger denominator = BigInteger.valueOf(divisor);
        if (dividend == null || divisor == 0 || dividend.remainder(denominator).equals(BigInteger.ZERO)) {
            return dividend;
        }
        BigInteger factor = dividend.divide(denominator);
        BigInteger product = factor.multiply(denominator);
        return product;
    }

    public static BigDecimal floor(BigDecimal dividend, int divisor) {
        BigDecimal denominator = BigDecimal.valueOf(divisor);
        if (dividend == null || divisor == 0 || dividend.remainder(denominator).compareTo(BigDecimal.ZERO) == 0) {
            return dividend;
        }
        BigDecimal factor = dividend.divideToIntegralValue(denominator);
        BigDecimal product = factor.multiply(denominator);
        return product;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="round">
    public static Number round(Number dividend, int divisor) {
        return dividend == null || divisor == 0 ? dividend
            : dividend instanceof Byte ? round((Byte) dividend, divisor)
                : dividend instanceof Short ? round((Short) dividend, divisor)
                    : dividend instanceof Integer ? round((Integer) dividend, divisor)
                        : dividend instanceof Long ? round((Long) dividend, divisor)
                            : dividend instanceof Float ? round((Float) dividend, divisor)
                                : dividend instanceof Double ? round((Double) dividend, divisor)
                                    : dividend instanceof BigInteger ? round((BigInteger) dividend, divisor)
                                        : dividend instanceof BigDecimal ? round((BigDecimal) dividend, divisor)
                                            : null;
    }

    public static Byte round(Byte dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        int remainder = dividend % divisor;
        return remainder == 0 ? dividend
            : remainder < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }

    public static Short round(Short dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        int remainder = dividend % divisor;
        return remainder == 0 ? dividend
            : remainder < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }

    public static Integer round(Integer dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        int remainder = dividend % divisor;
        return remainder == 0 ? dividend
            : remainder < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }

    public static Long round(Long dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        long remainder = dividend % divisor;
        return remainder == 0 ? dividend
            : (int) remainder < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }

    public static Float round(Float dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        double remainder = dividend % divisor;
        return remainder == 0 ? dividend
            : (int) remainder < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }

    public static Double round(Double dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        double remainder = dividend % divisor;
        return remainder == 0 ? dividend
            : (int) remainder < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }

    public static BigInteger round(BigInteger dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        BigInteger remainder = dividend.remainder(BigInteger.valueOf(divisor));
        return remainder.equals(BigInteger.ZERO) ? dividend
            : remainder.intValue() < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }

    public static BigDecimal round(BigDecimal dividend, int divisor) {
        if (dividend == null || divisor == 0) {
            return dividend;
        }
        BigDecimal remainder = dividend.remainder(BigDecimal.valueOf(divisor));
        return remainder.compareTo(BigDecimal.ZERO) == 0 ? dividend
            : remainder.intValue() < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="multiple">
    public static boolean multiple(Number dividend, int divisor) {
        return dividend == null || divisor == 0 ? false
            : dividend instanceof Byte ? multiple((Byte) dividend, divisor)
                : dividend instanceof Short ? multiple((Short) dividend, divisor)
                    : dividend instanceof Integer ? multiple((Integer) dividend, divisor)
                        : dividend instanceof Long ? multiple((Long) dividend, divisor)
                            : dividend instanceof Float ? multiple((Float) dividend, divisor)
                                : dividend instanceof Double ? multiple((Double) dividend, divisor)
                                    : dividend instanceof BigInteger ? multiple((BigInteger) dividend, divisor)
                                        : dividend instanceof BigDecimal ? multiple((BigDecimal) dividend, divisor)
                                            : true;
    }

    public static boolean multiple(Byte dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend % divisor == 0;
    }

    public static boolean multiple(Short dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend % divisor == 0;
    }

    public static boolean multiple(Integer dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend % divisor == 0;
    }

    public static boolean multiple(Long dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend % divisor == 0;
    }

    public static boolean multiple(Float dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend % divisor == 0;
    }

    public static boolean multiple(Double dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend % divisor == 0;
    }

    public static boolean multiple(BigInteger dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend.remainder(BigInteger.valueOf(divisor)).equals(BigInteger.ZERO);
    }

    public static boolean multiple(BigDecimal dividend, int divisor) {
        return dividend != null && divisor != 0 && dividend.remainder(BigDecimal.valueOf(divisor)).compareTo(BigDecimal.ZERO) == 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="new">
    public static Byte newByte(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return pdq;
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : Byte.valueOf(pdq);
        } else {
            return Byte.valueOf(obj.toString());
        }
    }

    public static Short newShort(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return pdq;
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : Short.valueOf(pdq);
        } else {
            return Short.valueOf(obj.toString());
        }
    }

    public static Integer newInteger(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return pdq.intValue();
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return pdq.intValue();
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return pdq;
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return pdq.intValue();
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return pdq.intValue();
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return pdq.intValue();
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return pdq.intValue();
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return pdq.intValue();
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : Integer.valueOf(pdq);
        } else {
            return Integer.valueOf(obj.toString());
        }
    }

    public static Long newLong(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return pdq.longValue();
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return pdq.longValue();
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return pdq.longValue();
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return pdq;
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return pdq.longValue();
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return pdq.longValue();
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return pdq.longValue();
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return pdq.longValue();
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : Long.valueOf(pdq);
        } else {
            return Long.valueOf(obj.toString());
        }
    }

    public static Float newFloat(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return pdq.floatValue();
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return pdq.floatValue();
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return pdq.floatValue();
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return pdq.floatValue();
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return pdq;
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return pdq.floatValue();
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return pdq.floatValue();
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return pdq.floatValue();
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : Float.valueOf(pdq);
        } else {
            return Float.valueOf(obj.toString());
        }
    }

    public static Double newDouble(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return pdq.doubleValue();
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return pdq.doubleValue();
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return pdq.doubleValue();
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return pdq.doubleValue();
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return pdq.doubleValue();
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return pdq;
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return pdq.doubleValue();
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return pdq.doubleValue();
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : Double.valueOf(pdq);
        } else {
            return Double.valueOf(obj.toString());
        }
    }

    public static BigInteger newBigInteger(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return BigInteger.valueOf(pdq.longValue());
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return BigInteger.valueOf(pdq.longValue());
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return BigInteger.valueOf(pdq.longValue());
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return BigInteger.valueOf(pdq);
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return BigInteger.valueOf(pdq.longValue());
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return BigInteger.valueOf(pdq.longValue());
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return BigInteger.ZERO.add(pdq);
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return BigInteger.valueOf(pdq.longValue());
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : new BigInteger(pdq);
        } else {
            return new BigInteger(obj.toString());
        }
    }

    public static BigDecimal newBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return new BigDecimal(pdq.intValue());
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return new BigDecimal(pdq.intValue());
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return new BigDecimal(pdq);
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return new BigDecimal(pdq);
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return new BigDecimal(pdq.doubleValue());
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return new BigDecimal(pdq);
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return new BigDecimal(pdq);
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return BigDecimal.ZERO.add(pdq);
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : new BigDecimal(pdq);
        } else {
            return new BigDecimal(obj.toString());
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="conversion methods">
    public static Byte numberToByte(Object obj) {
        return obj instanceof Number ? newByte(obj) : null;
    }

    public static Short numberToShort(Object obj) {
        return obj instanceof Number ? newShort(obj) : null;
    }

    public static Integer numberToInteger(Object obj) {
        return obj instanceof Number ? newInteger(obj) : null;
    }

    public static Long numberToLong(Object obj) {
        return obj instanceof Number ? newLong(obj) : null;
    }

    public static Float numberToFloat(Object obj) {
        return obj instanceof Number ? newFloat(obj) : null;
    }

    public static Double numberToDouble(Object obj) {
        return obj instanceof Number ? newDouble(obj) : null;
    }

    public static BigInteger numberToBigInteger(Object obj) {
        return obj instanceof Number ? newBigInteger(obj) : null;
    }

    public static BigDecimal numberToBigDecimal(Object obj) {
        return obj instanceof Number ? newBigDecimal(obj) : null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="more conversion methods">
    public static Number toNumber(Object object, Class<? extends Number> targetClass) {
        if (object == null) {
            return null;
        }
        Class<?> sourceClass = object.getClass();
        if (targetClass == null || targetClass.isAssignableFrom(sourceClass)) {
            return (Number) object;
        } else if (targetClass.equals(Byte.class)) {
            return toByte(object);
        } else if (targetClass.equals(Short.class)) {
            return toShort(object);
        } else if (targetClass.equals(Integer.class)) {
            return toInteger(object);
        } else if (targetClass.equals(Long.class)) {
            return toLong(object);
        } else if (targetClass.equals(Float.class)) {
            return toFloat(object);
        } else if (targetClass.equals(Double.class)) {
            return toDouble(object);
        } else if (targetClass.equals(BigInteger.class)) {
            return toBigInteger(object);
        } else if (targetClass.equals(BigDecimal.class)) {
            return toBigDecimal(object);
        } else {
            return (Number) object;
        }
    }

    public static Byte toByte(Object obj) {
        return obj == null ? null : obj instanceof Byte ? (Byte) obj : newByte(obj);
    }

    public static Short toShort(Object obj) {
        return obj == null ? null : obj instanceof Short ? (Short) obj : newShort(obj);
    }

    public static Integer toInteger(Object obj) {
        return obj == null ? null : obj instanceof Integer ? (Integer) obj : newInteger(obj);
    }

    public static Long toLong(Object obj) {
        return obj == null ? null : obj instanceof Long ? (Long) obj : newLong(obj);
    }

    public static Float toFloat(Object obj) {
        return obj == null ? null : obj instanceof Float ? (Float) obj : newFloat(obj);
    }

    public static Double toDouble(Object obj) {
        return obj == null ? null : obj instanceof Double ? (Double) obj : newDouble(obj);
    }

    public static BigInteger toBigInteger(Object obj) {
        return obj == null ? null : obj instanceof BigInteger ? (BigInteger) obj : newBigInteger(obj);
    }

    public static BigDecimal toBigDecimal(Object obj) {
        return obj == null ? null : obj instanceof BigDecimal ? (BigDecimal) obj : newBigDecimal(obj);
    }
    // </editor-fold>

}
