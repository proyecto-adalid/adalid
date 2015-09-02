/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.interfaces.NamedValue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class NamedValueCache implements NamedValue {

    private static final Map<String, NamedValue> cache = Collections.synchronizedMap(new HashMap<String, NamedValue>());

    public static NamedValue getInstance(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        NamedValue namedValue;
        if (cache.containsKey(name)) {
            namedValue = cache.get(name);
        } else {
            namedValue = new NamedValueCache(name);
            cache.put(name, namedValue);
        }
        return namedValue;
    }

    private final String _name;

    private NamedValueCache(String name) {
        _name = name;
    }

    @Override
    public String name() {
        return _name;
    }

}
