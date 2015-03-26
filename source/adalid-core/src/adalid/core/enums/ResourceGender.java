/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.enums;

import adalid.commons.bundles.Bundle;

/**
 * @author Jorge Campins
 */
public enum ResourceGender {

    UNSPECIFIED, MASCULINE, FEMININE, COMMON, NEUTER;

    public String getConnector() {
        String suffix = "connector";
        String key = ResourceGender.class.getSimpleName() + "." + name() + "." + suffix;
        return Bundle.getTrimmedToEmptyString(key);
    }

}
