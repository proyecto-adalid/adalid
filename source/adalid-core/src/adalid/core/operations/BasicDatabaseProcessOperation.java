/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.operations;

import adalid.core.Transition;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.State;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public abstract class BasicDatabaseProcessOperation extends BasicDatabaseOperation {

    public BasicDatabaseProcessOperation() {
        super();
    }

    public BasicDatabaseProcessOperation(Artifact declaringArtifact) {
        super(declaringArtifact);
    }

    public void addTransition(State x, State y) {
        Transition transition = new Transition(x, y);
        addTransition(transition);
    }

    public void addTransition(Transition transition) {
        if (transition != null) {
            String key = transition.getName();
            if (key != null) {
                Map<String, Transition> map = getTransitionsMap();
                if (map.containsKey(key)) {
                } else {
                    map.put(key, transition);
                }
            }
        }
    }

}
