/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.comparators;

import adalid.core.Display;
import adalid.core.Project;
import adalid.core.XS2;
import adalid.core.interfaces.Entity;
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

    private ByDisplayEntityDeclaringFieldName() {
        this(null);
    }

    private ByDisplayEntityDeclaringFieldName(Project module) {
        if (module != null) {
            String fieldType, fieldName;
            for (Field field : XS2.getFields(module.getClass())) {
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
