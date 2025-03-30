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
public enum PropertyType {

    PRIMITIVE(true, false, false),
    REFERENCE(true, false, false),
    BASE(true, false, false),
    EXTENSION(true, true, true),
    FOREIGN(true, true, true),
    AGGREGATE(true, true, true),
    VIRTUAL(true, true, true);

    private final boolean persistentEntityProperty;

    private final boolean proceduralEntityProperty;

    private final boolean virtualEntityProperty;

    PropertyType(boolean persistent, boolean procedural, boolean virtual) {
        persistentEntityProperty = persistent;
        proceduralEntityProperty = procedural;
        virtualEntityProperty = virtual;
    }

    /**
     * @return the persistentEntityProperty
     */
    public boolean isPersistentEntityProperty() {
        return persistentEntityProperty;
    }

    /**
     * @return the proceduralEntityProperty
     */
    public boolean isProceduralEntityProperty() {
        return proceduralEntityProperty;
    }

    /**
     * @return the virtualEntityProperty
     */
    public boolean isVirtualEntityProperty() {
        return virtualEntityProperty;
    }

}
