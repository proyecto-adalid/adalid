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
package adalid.core.enums;

import adalid.commons.bundles.*;

/**
 * @author Jorge Campins
 */
public enum ViewFieldAggregation {

    COUNT(1),
    MINIMUM(2),
    MAXIMUM(4),
    SUM(8),
    AVERAGE(16),
    DEVIATION(32),
    COUNT_MINIMUM_MAXIMUM(7), // 1 + 2 + 4
    MINIMUM_MAXIMUM(6), // 2 + 4
    SUM_COUNT_AVERAGE(25), // 8 + 1 + 16
    SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM(63), // 8 + 1 + 16 + 32 + 2 + 4
    AVERAGE_DEVIATION(48), // 16 + 32
    AVERAGE_DEVIATION_MINIMUM_MAXIMUM(54); // 16 + 32 + 2 + 4

    private final int number;

    private ViewFieldAggregation(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getNameTag() {
        String suffix = "name.tag";
        String key = ViewFieldAggregation.class.getSimpleName() + "." + name() + "." + suffix;
        String tag = Bundle.getTrimmedToNullString(key);
        return tag != null ? tag : name().toLowerCase();
    }

}
