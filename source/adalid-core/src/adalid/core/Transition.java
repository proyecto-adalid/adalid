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
public class Transition extends AbstractArtifact {

    private State _x;

    private State _y;

    /**
     * @return the x
     */
    public State getX() {
        return _x;
    }

    /**
     * @return the y
     */
    public State getY() {
        return _y;
    }

    public Transition() {
    }

    public Transition(State x, State y) {
        _x = x;
        _y = y;
        setName(x + "_" + y);
    }

    public void settle(State x, State y) {
        _x = x;
        _y = y;
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                if (_x != null) {
                    string += _x.toString(n + 1, "x", false, fields, false);
                }
                if (_y != null) {
                    string += _y.toString(n + 1, "y", false, fields, false);
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
