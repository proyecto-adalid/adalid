/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.operations;

import adalid.core.Operation;
import adalid.core.enums.BasicDatabaseOperationType;
import adalid.core.interfaces.Artifact;

/**
 * @author Jorge Campins
 */
public abstract class BasicDatabaseOperation extends Operation {

    public BasicDatabaseOperation() {
        super();
    }

    public BasicDatabaseOperation(Artifact declaringArtifact) {
        super(declaringArtifact);
    }

    BasicDatabaseOperationType _basicDatabaseOperationType;

    /**
     * @return the basic database operation type
     */
    public BasicDatabaseOperationType getBasicDatabaseOperationType() {
        return _basicDatabaseOperationType;
    }

}
