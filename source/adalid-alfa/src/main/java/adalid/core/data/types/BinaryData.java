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
public class BinaryData extends BinaryPrimitive {

    public static final String EMPTY = "";

    {
        XS2.setDataClass(this, BinaryData.class);
        XS2.setDataType(this, byte[].class);
    }

    private int _displayWidth = -1; // Constants.DEFAULT_IMAGE_WIDTH;

    public int getDisplayWidth() {
        return _displayWidth;
    }

    public void setDisplayWidth(int width) {
        XS2.checkAccess();
        _displayWidth = width;
    }

    private int _displayHeight = -1; // Constants.DEFAULT_IMAGE_HEIGHT;

    public int getDisplayHeight() {
        return _displayHeight;
    }

    public void setDisplayHeight(int height) {
        XS2.checkAccess();
        _displayHeight = height;
    }

    private boolean _resizable = true;

    public boolean isResizable() {
        return _resizable;
    }

    public void setResizable(boolean resizable) {
        XS2.checkAccess();
        _resizable = resizable;
    }

}
