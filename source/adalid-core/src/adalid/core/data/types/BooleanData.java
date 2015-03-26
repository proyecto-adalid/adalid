/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.data.types;

import adalid.core.XS2;
import adalid.core.primitives.BooleanPrimitive;

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

}
