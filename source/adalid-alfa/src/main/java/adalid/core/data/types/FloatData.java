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

/**
 * @author Jorge Campins
 */
public class FloatData extends NumericPrimitive {

//  public static final Float ZERO = 0.0F;
    public static final Double ZERO = 0.0D;

    {
        XS2.setDataClass(this, FloatData.class);
//      XS2.setDataType(this, Float.class);
        XS2.setDataType(this, Double.class);
        setMinValue(ZERO);
//      setMinNumber(Float.MIN_VALUE);
        setMinNumber(0.0F - Float.MAX_VALUE);
        setMaxNumber(Float.MAX_VALUE);
    }

}
