/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.ContextualEntityType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.ContextualEntity;
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
