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
package adalid.core;

import adalid.core.enums.*;
import adalid.core.interfaces.*;
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
    protected void setProceduralEntityType(ProceduralEntityType proceduralEntityType) { // protected avoids method never unused warning
        _proceduralEntityType = proceduralEntityType;
    }

    public AbstractProceduralEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

}
