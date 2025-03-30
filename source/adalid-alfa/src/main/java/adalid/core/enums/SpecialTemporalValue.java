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
package adalid.core.enums;

import adalid.core.interfaces.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Jorge Campins
 */
public enum SpecialTemporalValue implements SpecialValue {

    NULL, CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP;

    @Override
    public Class<?> getDataType() {
        return switch (this) {
            case CURRENT_DATE ->
                Date.class;
            case CURRENT_TIME ->
                Time.class;
            case CURRENT_TIMESTAMP ->
                Timestamp.class;
            default ->
                Object.class;
        };
    }

}
