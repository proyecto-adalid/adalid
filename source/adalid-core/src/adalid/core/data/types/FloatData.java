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
public class FloatData extends NumericPrimitive {

//  public static final Float ZERO = 0.0F;
    public static final Double ZERO = 0.0;

    {
        XS2.setDataClass(this, FloatData.class);
//      XS2.setDataType(this, Float.class);
        XS2.setDataType(this, Double.class);
        setMinNumber(Float.MIN_VALUE);
        setMaxNumber(Float.MAX_VALUE);
    }

}
