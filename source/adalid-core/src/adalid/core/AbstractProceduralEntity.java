/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.DatabaseEntityType;
import adalid.core.enums.ProceduralEntityType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.ProceduralEntity;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
public abstract class AbstractProceduralEntity extends AbstractDatabaseEntity implements ProceduralEntity {

    {
        setDatabaseEntityType(DatabaseEntityType.PROCEDURAL);
    }

    private ProceduralEntityType _proceduralEntityType;

    /**
     * @return the procedural entity type
     */
    @Override
    public ProceduralEntityType getProceduralEntityType() {
        return _proceduralEntityType;
    }

    /**
     * @param proceduralEntityType the procedural entity type to set
     */
    void setProceduralEntityType(ProceduralEntityType proceduralEntityType) {
        _proceduralEntityType = proceduralEntityType;
    }

    public AbstractProceduralEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

}
