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

import adalid.commons.util.*;
import adalid.core.interfaces.*;

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
        super();
    }

    public Transition(State x, State y) {
        super();
        init(x, y);
    }

    private void init(State x, State y) {
        _x = x;
        _y = y;
        String name = StrUtils.getArtifactName(x + "_" + y);
        setName(name);
    }

    /**
     * El método settle se utiliza para especificar el estado inicial y el estado final de la transición.
     *
     * @param x estado inicial
     * @param y estado final
     */
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
