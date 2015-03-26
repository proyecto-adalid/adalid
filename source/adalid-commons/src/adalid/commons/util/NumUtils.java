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

    public static Integer newInteger(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return new Integer(pdq.intValue());
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return new Integer(pdq.intValue());
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return new Integer(pdq.intValue());
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return new Integer(pdq.intValue());
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return new Integer(pdq.intValue());
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return new Integer(pdq.intValue());
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return new Integer(pdq.intValue());
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return new Integer(pdq.intValue());
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
            return new Long(pdq.longValue());
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return new Long(pdq.longValue());
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return new Long(pdq.longValue());
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return new Long(pdq.longValue());
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return new Long(pdq.longValue());
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return new Long(pdq.longValue());
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return new Long(pdq.longValue());
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return new Long(pdq.longValue());
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : new Long(pdq);
        } else {
            return new Long(obj.toString());
        }
    }

    public static Double newDouble(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Byte) {
            Byte pdq = (Byte) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof Short) {
            Short pdq = (Short) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof Integer) {
            Integer pdq = (Integer) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return new Double(pdq.doubleValue());
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : new Double(pdq);
        } else {
            return new Double(obj.toString());
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
            return new BigDecimal(pdq.intValue());
        } else if (obj instanceof Long) {
            Long pdq = (Long) obj;
            return new BigDecimal(pdq.longValue());
        } else if (obj instanceof BigInteger) {
            BigInteger pdq = (BigInteger) obj;
            return new BigDecimal(pdq);
        } else if (obj instanceof BigDecimal) {
            BigDecimal pdq = (BigDecimal) obj;
            return BigDecimal.ZERO.add(pdq);
        } else if (obj instanceof Double) {
            Double pdq = (Double) obj;
            return new BigDecimal(pdq.doubleValue());
        } else if (obj instanceof Float) {
            Float pdq = (Float) obj;
            return new BigDecimal(pdq.doubleValue());
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return pdq.trim().isEmpty() ? null : new BigDecimal(pdq);
        } else {
            return new BigDecimal(obj.toString());
        }
    }

}
