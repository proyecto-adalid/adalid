/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.comparators;

import adalid.core.annotations.AddAttributesMethod;
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
