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

import java.io.PrintStream;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Jorge Campins
 */
public class SysUtils {

    private static final Logger logger = Logger.getLogger(SysUtils.class);

    public static void setOut() {
        setOut(UTF_8);
    }

    public static void setOut(String charsetName) {
        setOut(charsetName == null || charsetName.isBlank() ? UTF_8 : Charset.forName(charsetName, UTF_8));
    }

    public static void setOut(Charset charset) {
        Charset oldCharset = System.out instanceof PrintStream ps ? ps.charset() : null;
        Charset newCharset = charset == null ? UTF_8 : charset;
        if (newCharset.equals(oldCharset)) {
            logger.trace("System.out charset was already set to " + newCharset);
        } else {
            System.setOut(new PrintStream(System.out, true, newCharset));
            if (oldCharset != null) {
                logger.trace("System.out charset was previously set to " + oldCharset);
            }
            logger.trace("System.out charset is now set to " + newCharset);
        }
    }

}
