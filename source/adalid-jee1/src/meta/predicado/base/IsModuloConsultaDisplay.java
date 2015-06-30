/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.predicado.base;

import adalid.core.Display;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityReference;
import adalid.core.interfaces.EnumerationEntity;
import adalid.core.predicates.IsReadingDisplay;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsModuloConsultaDisplay extends IsReadingDisplay {

    public IsModuloConsultaDisplay() {
    }

    public IsModuloConsultaDisplay(Predicate entityPredicate) {
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
        Entity master = display.getMaster();
        EntityReference reference = display.getReference();
        if (master == null && reference == null) {
            return true;
        }
        if (master == null || reference == null) {
            return false;
        }
        Class<?> entityDataClass = entity.getDataClass();
        Class<?> masterDataClass = master.getDataClass();
        if (entityDataClass.equals(masterDataClass)) {
            return false;
        }
        if (EnumerationEntity.class.isAssignableFrom(entityDataClass)) {
            return false;
        }
        if (EnumerationEntity.class.isAssignableFrom(masterDataClass)) {
            return false;
        }
//      ResourceType entityType = entity.getResourceType();
//      ResourceType masterType = master.getResourceType();
//      return entityType.equals(masterType);
        return true;
    }

}
