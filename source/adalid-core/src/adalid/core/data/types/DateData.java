/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.data.types;

import adalid.core.XS2;
import adalid.core.primitives.TemporalPrimitive;
import java.sql.Date;

/**
 * @author Jorge Campins
 */
public class DateData extends TemporalPrimitive {

    public static final Date EPOCH = new Date(getEpochInMillis());

    {
        XS2.setDataClass(this, DateData.class);
        XS2.setDataType(this, Date.class);
    }

}
