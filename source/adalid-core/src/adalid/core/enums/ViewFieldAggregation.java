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
public enum ViewFieldAggregation {

    COUNT,
    MINIMUM,
    MAXIMUM,
    SUM,
    AVERAGE,
    DEVIATION,
    COUNT_MINIMUM_MAXIMUM,
    MINIMUM_MAXIMUM,
    SUM_COUNT_AVERAGE,
    SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM,
    AVERAGE_DEVIATION,
    AVERAGE_DEVIATION_MINIMUM_MAXIMUM;

    public String getNameTag() {
        String suffix = "name.tag";
        String key = ViewFieldAggregation.class.getSimpleName() + "." + name() + "." + suffix;
        String tag = Bundle.getTrimmedToNullString(key);
        return tag != null ? tag : name().toLowerCase();
    }

}
