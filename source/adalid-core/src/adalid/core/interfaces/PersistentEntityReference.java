/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;

/**
 * @author Jorge Campins
 */
public interface PersistentEntityReference extends PersistentEntity, EntityReference {

    /**
     * @return true is the entity defines a foreign key
     */
    boolean isForeignKey();

    /**
     * @return the onDeleteAction
     */
    OnDeleteAction getOnDeleteAction();

    /**
     * @return the onUpdateAction
     */
    OnUpdateAction getOnUpdateAction();

}
