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
package adalid.core.enums;

/**
 * @author Jorge Campins
 */
public enum Kleenean {

    UNSPECIFIED(null), TRUE(true), FALSE(false);

    private final Boolean _value;

    Kleenean(Boolean value) {
        _value = value;
    }

    public static Kleenean valueOf(Boolean value) {
        return value == null ? UNSPECIFIED : value ? TRUE : FALSE;
    }

    public Boolean toBoolean() {
        return _value;
    }

    public Boolean toBoolean(Boolean unspecified) {
        return _value == null ? unspecified : _value;
    }

    public boolean toBoolean(boolean unspecified) {
        return _value == null ? unspecified : (boolean) _value;
    }

}
