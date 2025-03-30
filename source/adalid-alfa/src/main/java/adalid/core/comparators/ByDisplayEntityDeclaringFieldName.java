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
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public class ByDisplayEntityDeclaringFieldName implements Comparator<Display> {

    private final Map<String, String> _map = new LinkedHashMap<>();

    private static final String HIGH = "" + Character.MAX_VALUE;

    public static ByDisplayEntityDeclaringFieldName getInstance(Project module) {
        return new ByDisplayEntityDeclaringFieldName(module);
    }

    private ByDisplayEntityDeclaringFieldName(Project module) {
        if (module != null) {
            String fieldType, fieldName;
            for (Field field : XS2.getFields(module.getClass(), Project.class)) {
                if (Entity.class.isAssignableFrom(field.getType())) {
                    fieldType = field.getType().getSimpleName();
                    fieldName = field.getName();
                    _map.put(fieldType, fieldName);
                }
            }
        }
    }

    @Override
    public int compare(Display o1, Display o2) {
        if (o1 != null && o2 != null) {
            String n1 = o1.getName();
            String n2 = o2.getName();
            if (!_map.isEmpty()) {
                Entity e1 = o1.getEntity();
                Entity e2 = o2.getEntity();
                String f1 = e1 == null ? null : _map.get(e1.getDataClass().getSimpleName());
                String f2 = e2 == null ? null : _map.get(e2.getDataClass().getSimpleName());
                String x1 = f1 == null ? HIGH : f1;
                String x2 = f2 == null ? HIGH : f2;
                return x1.equals(x2) ? n1.compareTo(n2) : x1.compareTo(x2);
            }
            return n1.compareTo(n2);
        }
        return 0;
    }

}
