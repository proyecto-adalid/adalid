/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.interfaces.State;

/**
 * @author Jorge Campins
 */
public class Trigger extends AbstractArtifact {

    private State _state;

    private ProcessOperation _operation;

    /**
     * @return the boolean expression
     */
    public State getState() {
        return _state;
    }

    /**
     * @return the operation
     */
    public ProcessOperation getOperation() {
        return _operation;
    }

    public Trigger() {
        super();
    }

    public Trigger(State state, ProcessOperation operation) {
        super();
        _state = state;
        _operation = operation;
        if (state != null && operation != null) {
            setName(operation.getName() + "_" + state.getName());
        }
    }

    public void settle(State state, ProcessOperation operation) {
        _state = state;
        _operation = operation;
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                if (_state != null) {
                    string += _state.toString(n + 1, "state", false, fields, false);
                }
                if (_operation != null) {
                    string += _operation.toString(n + 1, "operation", false, fields, false);
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
