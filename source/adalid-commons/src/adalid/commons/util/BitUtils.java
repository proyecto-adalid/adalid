/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.util;

import adalid.commons.bundles.Bundle;
import adalid.commons.enums.EnumBitLabelSet;

/**
 * @author Jorge Campins
 */
public class BitUtils {

    public static boolean valueOf(Boolean b, boolean value) {
        return b != null ? b : value;
    }

    public static boolean valueOf(Boolean b) {
        return b != null && b;
    }

    public static boolean valueOf(Character c) {
        return c != null && valueOf(c.charValue());
    }

    public static boolean valueOf(char c) {
        return !(c == ' ' || c == 'n' || c == 'N' || c == '0');
    }

    public static boolean valueOf(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        } else if (s.length() == 1) {
            return valueOf(s.charAt(0));
        }
        return !(s.equalsIgnoreCase("false")
            || s.equalsIgnoreCase(Bundle.getString("bit.no"))
            || s.equalsIgnoreCase(Bundle.getString("bit.inactive"))
            || s.equalsIgnoreCase(Bundle.getString("bit.off"))
            || s.equalsIgnoreCase(Bundle.getString("bit.negative"))
            || s.equalsIgnoreCase(Bundle.getString("bit.absent"))
            || s.equalsIgnoreCase(Bundle.getString("bit.false")));
    }

    public static boolean valueOf(Number n) {
        return n != null && n.longValue() != 0L;
    }

    public static boolean valueOf(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof Character) {
            return valueOf((Character) o);
        } else if (o instanceof String) {
            return valueOf((String) o);
        } else if (o instanceof Number) {
            return valueOf((Number) o);
        } else {
            return o != null;
        }
    }

    public static String getLabel(Boolean b) {
        return getLabel(b, EnumBitLabelSet.YES_OR_NO);
    }

    public static String getLabel(Boolean b, EnumBitLabelSet bls) {
        switch (bls) {
            case YES_OR_NO:
                return b ? Bundle.getString("bit.yes") : Bundle.getString("bit.no");
            case ACTIVE_OR_INACTIVE:
                return b ? Bundle.getString("bit.active") : Bundle.getString("bit.inactive");
            case ON_OR_OFF:
                return b ? Bundle.getString("bit.on") : Bundle.getString("bit.off");
            case POSITIVE_OR_NEGATIVE:
                return b ? Bundle.getString("bit.positive") : Bundle.getString("bit.negative");
            case PRESENT_OR_ABSENT:
                return b ? Bundle.getString("bit.present") : Bundle.getString("bit.absent");
            case TRUE_OR_FALSE:
                return b ? Bundle.getString("bit.true") : Bundle.getString("bit.false");
            default:
                return b ? Bundle.getString("bit.yes") : Bundle.getString("bit.no");
        }
    }

}
