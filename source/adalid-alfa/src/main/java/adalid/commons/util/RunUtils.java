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

import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class RunUtils {

    public static void logMemory(Logger logger) {
        logMemory(logger, null);
    }

    public static void logMemory(Logger logger, String remarks) {
        long tm = Runtime.getRuntime().totalMemory();
        long fm = Runtime.getRuntime().freeMemory();
        long um = tm - fm;
        if (remarks != null) {
            logger.info(remarks);
        }
        logger.info("used-memory=" + amount(um));
        logger.info("free-memory=" + amount(fm));
        logger.info("full-memory=" + amount(tm));
    }

    private static String amount(long bytes) {
        return bytes / divisor(bytes) + suffix(bytes);
    }

    private static long divisor(long bytes) {
        for (int i = 3; i > 0; i--) {
            long p = (long) Math.pow(2, 10 * i);
            if (bytes > 20 * p) {
                return p;
            }
        }
        return 1L;
    }

    private static final String suffix[] = {"b", "k", "m", "g"};

    private static String suffix(long bytes) {
        for (int i = 3; i > 0; i--) {
            long p = (long) Math.pow(2, 10 * i);
            if (bytes > 20 * p) {
                return suffix[i];
            }
        }
        return suffix[0];
    }

}
