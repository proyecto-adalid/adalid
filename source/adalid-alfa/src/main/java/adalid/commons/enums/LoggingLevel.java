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
package adalid.commons.enums;

import org.apache.log4j.Level;

/**
 * @author Jorge Campins
 */
public enum LoggingLevel {

    OFF, TRACE, DEBUG, INFO, WARN, ERROR, FATAL, ALL;

    public Level getLevel() {
        return switch (this) {
            case OFF ->
                Level.OFF;
            case TRACE ->
                Level.TRACE;
            case DEBUG ->
                Level.DEBUG;
            case INFO ->
                Level.INFO;
            case WARN ->
                Level.WARN;
            case ERROR ->
                Level.ERROR;
            case FATAL ->
                Level.FATAL;
            case ALL ->
                Level.ALL;
            default ->
                Level.OFF;
        };
    }

    public static LoggingLevel getLoggingLevel(Level level) {
        if (level == null) {
            return LoggingLevel.OFF;
        } else if (Level.OFF.equals(level)) {
            return LoggingLevel.OFF;
        } else if (Level.TRACE.equals(level)) {
            return LoggingLevel.TRACE;
        } else if (Level.DEBUG.equals(level)) {
            return LoggingLevel.DEBUG;
        } else if (Level.INFO.equals(level)) {
            return LoggingLevel.INFO;
        } else if (Level.WARN.equals(level)) {
            return LoggingLevel.WARN;
        } else if (Level.ERROR.equals(level)) {
            return LoggingLevel.ERROR;
        } else if (Level.FATAL.equals(level)) {
            return LoggingLevel.FATAL;
        } else if (Level.ALL.equals(level)) {
            return LoggingLevel.ALL;
        } else {
            return LoggingLevel.OFF;
        }
    }

}
