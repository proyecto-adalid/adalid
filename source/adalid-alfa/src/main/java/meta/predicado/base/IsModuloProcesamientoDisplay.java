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
package meta.predicado.base;

import adalid.core.*;
import adalid.core.interfaces.*;
import adalid.core.predicates.*;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsModuloProcesamientoDisplay extends IsProcessingDisplay {

    public IsModuloProcesamientoDisplay() {
    }

    public IsModuloProcesamientoDisplay(Predicate entityPredicate) {
        _entityPredicate = entityPredicate;
    }

    /**
     * the entity predicate.
     */
    private Predicate _entityPredicate;

    /**
     * @return the entity predicate
     */
    public Predicate getEntityPredicate() {
        return _entityPredicate;
    }

    /**
     * @param predicate the entity predicate to set
     */
    public void setEntityPredicate(Predicate predicate) {
        _entityPredicate = predicate;
    }

    @Override
    public boolean evaluate(Object object) {
        if (super.evaluate(object)) {
        } else {
            return false;
        }
        Display display = (Display) object;
        Entity entity = display.getEntity();
        if (entity == null) {
            return false;
        }
        if (_entityPredicate == null || _entityPredicate.evaluate(entity)) {
        } else {
            return false;
        }
        return true;
    }

}
