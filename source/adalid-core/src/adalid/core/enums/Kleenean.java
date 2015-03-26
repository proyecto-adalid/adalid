/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
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

    public Boolean toBoolean() {
        return _value;
    }

    public Boolean toBoolean(Boolean unspecified) {
        return _value == null ? unspecified : _value;
    }

    public boolean toBoolean(boolean unspecified) {
        return _value == null ? unspecified : _value;
    }

}
