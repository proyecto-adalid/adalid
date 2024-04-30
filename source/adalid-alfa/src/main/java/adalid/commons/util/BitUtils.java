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
import adalid.commons.enums.*;

/**
 * @author Jorge Campins
 */
public class BitUtils {

    public static Boolean getAsBoolean(Object o) {
        return o == null ? null : toBoolean(o);
    }

    public static String getAsString(Object o) {
        return o == null ? null : toBoolean(o).toString();
    }

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
        if (o instanceof Boolean pdq) {
            return pdq;
        } else if (o instanceof Character pdq) {
            return valueOf(pdq);
        } else if (o instanceof String pdq) {
            return valueOf(pdq);
        } else if (o instanceof Number pdq) {
            return valueOf(pdq);
        } else {
            return o != null;
        }
    }

    public static String getLabel(Boolean b) {
        return getLabel(b, EnumBitLabelSet.YES_OR_NO);
    }

    public static String getLabel(Boolean b, EnumBitLabelSet bls) {
        if (b == null) {
            return Bundle.getString("bit.null");
        }
        return switch (bls) {
            case YES_OR_NO ->
                b ? Bundle.getString("bit.yes") : Bundle.getString("bit.no");
            case ACTIVE_OR_INACTIVE ->
                b ? Bundle.getString("bit.active") : Bundle.getString("bit.inactive");
            case ON_OR_OFF ->
                b ? Bundle.getString("bit.on") : Bundle.getString("bit.off");
            case POSITIVE_OR_NEGATIVE ->
                b ? Bundle.getString("bit.positive") : Bundle.getString("bit.negative");
            case PRESENT_OR_ABSENT ->
                b ? Bundle.getString("bit.present") : Bundle.getString("bit.absent");
            case TRUE_OR_FALSE ->
                b ? Bundle.getString("bit.true") : Bundle.getString("bit.false");
            default ->
                b ? Bundle.getString("bit.yes") : Bundle.getString("bit.no");
        };
    }

    public static Boolean newBoolean(Object obj) {
        return obj == null ? null : obj instanceof Boolean ? (Boolean) obj : valueOf(obj);
    }

    public static Boolean toBoolean(Object obj) {
        return obj == null ? null : obj instanceof Boolean ? (Boolean) obj : valueOf(obj);
    }

}
