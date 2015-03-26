/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.util;

/**
 * @author Jorge Campins
 */
public class ThrowableUtils {

    public static String getString(Throwable throwable) {
        return throwable == null ? Throwable.class.getName()
            : throwable.getCause() != null ? getString(throwable.getCause())
            : isNotBlank(throwable.getLocalizedMessage()) ? getString(throwable.getLocalizedMessage())
            : isNotBlank(throwable.getMessage()) ? getString(throwable.getMessage())
            : isNotBlank(throwable.toString()) ? getString(throwable.toString())
            : Throwable.class.getSimpleName();
    }

    public static Throwable getCause(Throwable throwable) {
        return throwable == null ? null : throwable.getCause() == null ? throwable : getCause(throwable.getCause());
    }

    private static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }

    private static boolean isNotBlank(String string) {
        return !isBlank(string);
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
