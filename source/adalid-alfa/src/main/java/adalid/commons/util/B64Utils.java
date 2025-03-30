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

import java.util.Base64;

/**
 * @author Jorge Campins
 */
public class B64Utils {

    public static String encode(String string) {
        return string == null ? null : Base64.getEncoder().encodeToString(string.getBytes());
    }

    public static String decode(String string) {
        return string == null ? null : new String(Base64.getDecoder().decode(string));
    }

    public static String encodeFilename(String string) {
        return encodeUrl(string);
    }

    public static String decodeFilename(String string) {
        return decodeUrl(string);
    }

    public static String encodeMime(String string) {
        return string == null ? null : Base64.getMimeEncoder().encodeToString(string.getBytes());
    }

    public static String decodeMime(String string) {
        return string == null ? null : new String(Base64.getMimeDecoder().decode(string));
    }

    public static String encodeUrl(String string) {
        return string == null ? null : Base64.getUrlEncoder().encodeToString(string.getBytes());
    }

    public static String decodeUrl(String string) {
        return string == null ? null : new String(Base64.getUrlDecoder().decode(string));
    }

}
