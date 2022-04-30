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
public abstract class AbstractContextualEntity extends AbstractEntity implements ContextualEntity {

    private ContextualEntityType _contextualEntityType;

    /**
     * @return the contextual entity type
     */
    @Override
    public ContextualEntityType getContextualEntityType() {
        return _contextualEntityType;
    }

    /**
     * @param contextualEntityType the contextual entity type to set
     */
    void setContextualEntityType(ContextualEntityType contextualEntityType) {
        _contextualEntityType = contextualEntityType;
    }

    public AbstractContextualEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

}
