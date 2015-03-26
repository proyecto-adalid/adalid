/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.data.types;

import adalid.core.XS2;
import adalid.core.primitives.NumericPrimitive;

/**
 * @author Jorge Campins
 */
public class BigIntegerData extends NumericPrimitive {

//  public static final BigInteger ZERO = BigInteger.ZERO;
    public static final Long ZERO = 0L;

    {
        XS2.setDataClass(this, BigIntegerData.class);
//      XS2.setDataType(this, BigInteger.class);
        XS2.setDataType(this, Long.class);
        setMinNumber(Long.MIN_VALUE);
        setMaxNumber(Long.MAX_VALUE);
    }

}
