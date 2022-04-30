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

import adalid.core.annotations.*;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * @author Jorge Campins
 */
public class ByMethodSequence implements Comparator<Method> {

    @Override
    public int compare(Method o1, Method o2) {
        if (o1 != null && o2 != null) {
            AddAttributesMethod a1 = o1.getAnnotation(AddAttributesMethod.class);
            AddAttributesMethod a2 = o2.getAnnotation(AddAttributesMethod.class);
            int s1 = a1 == null ? 0 : a1.value();
            int s2 = a2 == null ? 0 : a2.value();
            return s1 - s2;
        }
        return 0;
    }

}
