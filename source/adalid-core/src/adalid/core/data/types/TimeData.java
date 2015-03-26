/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.data.types;

import adalid.core.XS2;
import adalid.core.primitives.TemporalPrimitive;
import java.sql.Time;

/**
 * @author Jorge Campins
 */
public class TimeData extends TemporalPrimitive {

    public static final Time EPOCH = new Time(getEpochInMillis());

    {
        XS2.setDataClass(this, TimeData.class);
        XS2.setDataType(this, Time.class);
    }

    private Integer _precision;

    /**
     * @return the precision
     */
    public Integer getPrecision() {
        return _precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(Integer precision) {
        XS2.checkAccess();
        _precision = precision;
    }

}
