/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Jorge Campins
 */
public class NumUtils {

    // <editor-fold defaultstate="collapsed" desc="ceiling">
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
        if (dividend == null || divisor == 0 || dividend.remainder(denominator).equals(BigDecimal.ZERO)) {
            return dividend;
        }
        BigDecimal factor = dividend.divideToIntegralValue(denominator).add(BigDecimal.ONE);
        BigDecimal product = factor.multiply(denominator);
        return product;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="floor">
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
        if (dividend == null || divisor == 0 || dividend.remainder(denominator).equals(BigDecimal.ZERO)) {
            return dividend;
        }
        BigDecimal factor = dividend.divideToIntegralValue(denominator);
        BigDecimal product = factor.multiply(denominator);
        return product;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="round">
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
        return remainder.equals(BigDecimal.ZERO) ? dividend
            : remainder.intValue() < divisor / 2 ? floor(dividend, divisor) : ceiling(dividend, divisor);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="multiple">
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
        return dividend != null && divisor != 0 && dividend.remainder(BigDecimal.valueOf(divisor)).equals(BigDecimal.ZERO);
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
            return pdq.trim().isEmpty() ? null : new Byte(pdq);
        } else {
            return new Byte(obj.toString());
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
            return pdq.trim().isEmpty() ? null : new Short(pdq);
        } else {
            return new Short(obj.toString());
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
            return pdq.trim().isEmpty() ? null : new Integer(pdq);
        } else {
            return new Integer(obj.toString());
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
            return pdq.trim().isEmpty() ? null : new Long(pdq);
        } else {
            return new Long(obj.toString());
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
            return pdq.trim().isEmpty() ? null : new Float(pdq);
        } else {
            return new Float(obj.toString());
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
            return pdq.trim().isEmpty() ? null : new Double(pdq);
        } else {
            return new Double(obj.toString());
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
