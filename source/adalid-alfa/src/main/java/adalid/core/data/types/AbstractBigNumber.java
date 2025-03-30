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
package adalid.core.data.types;

import adalid.core.*;
import adalid.core.primitives.*;
import java.math.BigDecimal;

/**
 * @author Jorge Campins
 */
public abstract class AbstractBigNumber extends NumericPrimitive {

    public static final BigDecimal ZERO = BigDecimal.ZERO;

    private int _precision = -1; // Constants.DEFAULT_DECIMAL_PRECISION;

    private int _scale = -1; // Constants.DEFAULT_DECIMAL_SCALE;

    public int getRawPrecision() {
        return _precision;
    }

    public int getRawScale() {
        return _scale;
    }

    /**
     * @return the precision
     */
    public int getPrecision() {
        final int d = Constants.DEFAULT_DECIMAL_PRECISION;
        final int m = Constants.MAX_DECIMAL_PRECISION;
        final int p = _precision < 1 ? d : _precision > m ? m : _precision;
        return p;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(int precision) {
        checkScope();
        _precision = precision;
        setMinMaxNumbers();
    }

    /**
     * @return the scale
     */
    public int getScale() {
        final int d = Constants.DEFAULT_DECIMAL_SCALE;
        final int p = getPrecision();
        final int s = _scale < 0 ? p > d ? d : p : _scale > p ? p : _scale;
        return s;
    }

    /**
     * @param scale the scale to set
     */
    void setScale(int scale) {
        _scale = scale;
        setMinMaxNumbers();
    }

    void setMinMaxNumbers() {
        final int p = getPrecision();
        final int s = getScale();
        final BigDecimal one = BigDecimal.ONE.divide(BigDecimal.TEN.pow(s));
        final BigDecimal max = BigDecimal.TEN.pow(p - s).subtract(one);
        final BigDecimal min = BigDecimal.ZERO.subtract(max);
        setMinNumber(min);
        setMaxNumber(max);
    }

}
