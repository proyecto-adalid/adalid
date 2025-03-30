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

import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ThrowableUtils {

    public static String getString(Throwable throwable) {
        if (throwable == null) {
            return Throwable.class.getName();
        }
        String string;
        Throwable cause = throwable.getCause();
        if (cause != null) {
            return getString(cause);
        }
        string = throwable.getLocalizedMessage();
        if (StringUtils.isNotBlank(string)) {
            return getString(string);
        }
        string = throwable.getMessage();
        if (StringUtils.isNotBlank(string)) {
            return getString(string);
        }
        string = throwable.toString();
        if (StringUtils.isNotBlank(string)) {
            return getString(string);
        }
        return Throwable.class.getSimpleName();
    }

    public static Throwable getCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Throwable cause = throwable.getCause();
        return cause == null ? throwable : getCause(cause);
    }

    private static String getString(String string) {
        int i = string.indexOf("PSQLException:");
        if (i >= 0) {
            i = string.indexOf("ERROR:", i);
            if (i >= 0) {
                int j = string.indexOf("Error Code:", i);
                if (j >= 0) {
                    return string.substring(i, j);
                }
            }
        }
        return string;
    }

}
