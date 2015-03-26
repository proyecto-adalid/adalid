/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.data.types;

import adalid.core.XS2;
import adalid.core.primitives.CharacterPrimitive;

/**
 * @author Jorge Campins
 */
public class CharacterData extends CharacterPrimitive {

    public static final Character SPACE = ' ';

    public static final String EMPTY = "";

    {
        XS2.setDataClass(this, adalid.core.data.types.CharacterData.class);
//      XS2.setDataType(this, Character.class);
        XS2.setDataType(this, String.class);
    }

}
