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
public class BC { // Base Converter

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static final int MAX_BASE = SYMBOLS.length();

    public static String fromDecimal(Number number) {
        int i = number == null ? 0 : number.intValue();
        return fromDecimal(i);
    }

    public static String fromDecimal(int i) {
        return fromDecimal(i, MAX_BASE);
    }

    public static String fromDecimal(int i, int base) {
        int a = Math.abs(i);
        int b = Math.abs(base);
        if (b > MAX_BASE) {
            b = MAX_BASE;
        }
        int q = a / b;
        int r = a % b;
        return (q == 0 ? "" : fromDecimal(q, b)) + SYMBOLS.charAt(r);
    }

}
