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
import adalid.core.expressions.*;
import adalid.core.primitives.*;

/**
 * @author Jorge Campins
 */
public class ShortData extends NumericPrimitive {

//  public static final Short ZERO = 0;
    public static final Integer ZERO = 0;

    {
        XS2.setDataClass(this, ShortData.class);
//      XS2.setDataType(this, Short.class);
        XS2.setDataType(this, Integer.class);
        setMinValue(ZERO);
        setMinNumber(Short.MIN_VALUE);
        setMaxNumber(Short.MAX_VALUE);
    }

    public CharacterOrderedPairX toZeroPaddedString() {
        return toZeroPaddedString(6);
    }

}
