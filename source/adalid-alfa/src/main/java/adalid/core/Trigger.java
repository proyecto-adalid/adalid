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

import adalid.core.interfaces.*;

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
        init(state, operation);
    }

    private void init(State state, ProcessOperation operation) {
        _state = state;
        _operation = operation;
        if (state != null && operation != null) {
            setName(operation.getName() + "_" + state.getName());
        }
    }

    /**
     * El método settle se utiliza para especificar las acciones que se deben tomar cuando una instancia de una entidad alcanza un estado determinado.
     *
     * @param state estado de la entidad
     * @param operation operación de negocio de la entidad que se debe ejecutar
     */
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
