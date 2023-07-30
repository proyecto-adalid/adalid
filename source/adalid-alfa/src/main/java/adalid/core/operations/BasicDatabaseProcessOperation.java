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
package adalid.core.operations;

import adalid.core.*;
import adalid.core.interfaces.*;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public abstract class BasicDatabaseProcessOperation extends BasicDatabaseOperation {

    public BasicDatabaseProcessOperation() {
        super();
    }

    /**
     * El método addTransition se utiliza para agregar una transición a la lista de transiciones que lleva a cabo la operación.
     *
     * @param x estado inicial
     * @param y estado final
     */
    public void addTransition(State x, State y) {
        Transition transition = new Transition(x, y);
        addTransition(transition);
    }

    /**
     * El método addTransition se utiliza para agregar una transición a la lista de transiciones que lleva a cabo la operación.
     *
     * @param transition la transición a agregar
     */
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
