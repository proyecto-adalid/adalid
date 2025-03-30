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
public class CharacterData extends CharacterPrimitive {

    public static final Character SPACE = ' ';

    public static final String EMPTY = "";

    {
        XS2.setDataClass(this, adalid.core.data.types.CharacterData.class);
//      XS2.setDataType(this, Character.class);
        XS2.setDataType(this, String.class);
    }

}
