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
package adalid.core.comparators;

import adalid.core.*;
import java.util.Comparator;

/**
 * @author Jorge Campins
 */
public class ByInstanceKeyValue implements Comparator<Instance> {

    @Override
    public int compare(Instance o1, Instance o2) {
        if (o1 != null && o2 != null) {
            Object k1 = o1.getInstanceKeyValue();
            Object k2 = o2.getInstanceKeyValue();
            if (k1 instanceof Integer && k2 instanceof Integer) {
                Integer v1 = (Integer) k1;
                Integer v2 = (Integer) k2;
                return v1.compareTo(v2);
            }
            if (k1 instanceof Long && k2 instanceof Long) {
                Long v1 = (Long) k1;
                Long v2 = (Long) k2;
                return v1.compareTo(v2);
            }
            if (k1 instanceof String && k2 instanceof String) {
                String v1 = (String) k1;
                String v2 = (String) k2;
                return v1.compareTo(v2);
            }
        }
        return 0;
    }

}
