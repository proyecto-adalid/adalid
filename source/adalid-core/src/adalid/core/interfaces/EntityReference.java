/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;

/**
 * @author Jorge Campins
 */
public interface EntityReference extends Entity, Parameter, Property {
//
//  /**
//   * @return the extension
//   */
//  boolean isExtension();

    /**
     * @return the oneToOne
     */
    boolean isOneToOne();

    /**
     * @return the manyToOne
     */
    boolean isManyToOne();

    /**
     * @return the navigability
     */
    Navigability getNavigability();

    /**
     * @return the masterDetailView
     */
    MasterDetailView getMasterDetailView();

}
