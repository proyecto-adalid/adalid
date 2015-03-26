/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.DatabaseEntityType;
import adalid.core.enums.VirtualEntityType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.VirtualEntity;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
public abstract class AbstractVirtualEntity extends AbstractDatabaseEntity implements VirtualEntity {

    {
        setDatabaseEntityType(DatabaseEntityType.VIRTUAL);
    }

    private VirtualEntityType _virtualEntityType;

    /**
     * @return the virtual entity type
     */
    @Override
    public VirtualEntityType getVirtualEntityType() {
        return _virtualEntityType;
    }

    /**
     * @param virtualEntityType the virtual entity type to set
     */
    void setVirtualEntityType(VirtualEntityType virtualEntityType) {
        _virtualEntityType = virtualEntityType;
    }

    public AbstractVirtualEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

}
