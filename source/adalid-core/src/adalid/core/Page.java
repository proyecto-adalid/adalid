/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.DisplayType;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class Page extends Display {

    public Page(String name) {
        super(name);
        setDisplayType(DisplayType.PAGE);
    }

    /**
     * @return the fields list
     */
    @Override
    public List<PageField> getFields() {
        return null;
    }

    /**
     * @return the master heading fields list
     */
    @Override
    public List<PageField> getMasterHeadingFields() {
        return null;
    }

}
