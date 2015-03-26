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
