/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.predicado.base;

import adalid.core.predicates.IsOperationalEntity;
import java.util.List;
import meta.proyecto.base.ModuloBase;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsModuloProcesamientoResidualDisplay extends IsModuloProcesamientoDisplay {

    public IsModuloProcesamientoResidualDisplay(ModuloBase modulo) {
        _modulo = modulo;
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
