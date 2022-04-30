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
import adalid.core.enums.*;
import adalid.core.primitives.*;

/**
 * @author Jorge Campins
 */
public class BooleanData extends BooleanPrimitive {

    public static final Boolean TRUE = Boolean.TRUE;

    public static final Boolean FALSE = Boolean.FALSE;

    {
        XS2.setDataClass(this, BooleanData.class);
        XS2.setDataType(this, Boolean.class);
    }

    private BooleanDisplayType _booleanDisplayType = BooleanDisplayType.UNSPECIFIED;

    /**
     * @return the boolean display type
     */
    public BooleanDisplayType getBooleanDisplayType() {
        if (BooleanDisplayType.UNSPECIFIED.equals(_booleanDisplayType)) {
            return (isParameter() && isRequiredParameter()) || (isProperty() && !isNullable())
                ? BooleanDisplayType.CHECKBOX : BooleanDisplayType.DROPDOWN;
        }
        return _booleanDisplayType;
    }

    /**
     * @return the raw boolean display type
     */
    public BooleanDisplayType rawBooleanDisplayType() {
        return _booleanDisplayType;
    }

    /**
     * @param displayType the boolean display type to set
     */
    public void setBooleanDisplayType(BooleanDisplayType displayType) {
        XS2.checkAccess();
        _booleanDisplayType = displayType == null ? BooleanDisplayType.UNSPECIFIED : displayType;
    }

}
