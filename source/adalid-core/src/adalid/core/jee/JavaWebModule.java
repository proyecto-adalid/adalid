/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.jee;

import adalid.core.DisplaySet;
import adalid.core.Page;
import adalid.core.interfaces.Entity;
import java.util.List;

/**
 * @author Jorge Campins
 */
public interface JavaWebModule extends JavaModule {

    List<Page> getPagesList();

    List<DisplaySet> getPageSetsList();

    List<Entity> getDisplayableEntitiesList();

}
