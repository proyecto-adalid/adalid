/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.operations;

import adalid.core.enums.BasicDatabaseOperationType;
import adalid.core.interfaces.Artifact;

/**
 * @author Jorge Campins
 */
public class UpdateOperation extends BasicDatabaseProcessOperation {

    public UpdateOperation() {
        super();
        init();
    }

    public UpdateOperation(Artifact declaringArtifact) {
        super(declaringArtifact);
        init();
    }

    private void init() {
        _basicDatabaseOperationType = BasicDatabaseOperationType.UPDATE;
    }

}
