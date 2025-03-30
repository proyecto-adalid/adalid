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

import adalid.core.predicates.*;
import java.util.List;
import meta.modulo.base.ModuloBase;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsModuloRegistroResidualDisplay extends IsModuloRegistroDisplay {

    public IsModuloRegistroResidualDisplay(ModuloBase modulo) {
        _modulo = modulo;
        init();
    }

    private void init() {
        setEntityPredicate(new IsOperationalEntity());
    }

    private final ModuloBase _modulo;

    public ModuloBase getModulo() {
        return _modulo;
    }

    @Override
    public boolean evaluate(Object object) {
        if (super.evaluate(object)) {
        } else {
            return false;
        }
        Predicate predicate;
        List<? extends ModuloBase> siblings = _modulo.getSiblings();
        for (ModuloBase sibling : siblings) {
            predicate = sibling.getPagePredicate();
            if (predicate == null || predicate.evaluate(object)) {
                return false;
            }
        }
        return true;
    }

}
