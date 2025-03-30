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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class LogUtils {

    public static Level check(Level level, Level defaultLevel, Level maxLevel) {
        Level coalesced = coalesce(level, defaultLevel);
        return fair(coalesced) && fair(maxLevel) ? minimum(coalesced, maxLevel) : Level.OFF;
    }

    public static Level coalesce(Level level1, Level level2) {
        return level1 == null ? level2 : level1;
    }

    public static boolean fair(Level level) {
        return level != null && !level.equals(Level.OFF) && !level.equals(Level.ALL);
    }

    public static boolean foul(Level level) {
        return !fair(level);
    }

    public static boolean fair(Logger logger, Level level) {
        return logger != null && fair(level) && logger.isEnabledFor(level);
    }

    public static boolean foul(Logger logger, Level level) {
        return !fair(logger, level);
    }

    public static Level maximum(Level level1, Level level2) {
        Level l1 = coalesce(level1, Level.ALL);
        Level l2 = coalesce(level2, Level.ALL);
        return l1.isGreaterOrEqual(l2) ? l1 : l2;
    }

    public static Level minimum(Level level1, Level level2) {
        Level l1 = coalesce(level1, Level.OFF);
        Level l2 = coalesce(level2, Level.OFF);
        return l1.isGreaterOrEqual(l2) ? l2 : l1;
    }

}
