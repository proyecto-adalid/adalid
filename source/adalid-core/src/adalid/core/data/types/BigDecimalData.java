/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.data.types;

import adalid.core.XS2;
import adalid.core.primitives.NumericPrimitive;
import java.math.BigDecimal;

/**
 * @author Jorge Campins
 */
public class BigDecimalData extends NumericPrimitive {

    public static final BigDecimal ZERO = BigDecimal.ZERO;

    {
        XS2.setDataClass(this, BigDecimalData.class);
        XS2.setDataType(this, BigDecimal.class);
    }

    private Integer _precision;

    private Integer _scale;

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
        setMinMaxNumbers();
    }

    /**
     * @return the scale
     */
    public Integer getScale() {
        return _scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Integer scale) {
        XS2.checkAccess();
        _scale = scale;
        setMinMaxNumbers();
    }

    private void setMinMaxNumbers() {
        BigDecimal max = null;
        BigDecimal min = null;
        int p = _precision == null ? 0 : _precision;
        int s = _scale == null ? 0 : _scale;
        if (p > s) {
            max = BigDecimal.TEN.pow(p - s - 1);
            min = BigDecimal.ZERO.subtract(max);
        }
        setMinNumber(min);
        setMaxNumber(max);
    }

}
