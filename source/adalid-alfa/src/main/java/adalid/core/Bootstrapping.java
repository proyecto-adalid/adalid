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
package adalid.core;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.BitUtils;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
class Bootstrapping {

    private static final Logger logger = Logger.getLogger(Bootstrapping.class);

    private static final Map<String, String> map = new TreeMap<>();

    static final ExtendedProperties bootstrapping = PropertiesHandler.getBootstrapping();

    static final boolean checkReferencesToEnclosingEntityMembersWithinEnclosedOperations
        = getBoolean("check.references.to.enclosing.entity.members.within.enclosed.operations", "true");

    static final boolean addHeadAndToolbarSnippetsEvenWhenNoneAreDefined
        = getBoolean("add.head.and.toolbar.snippets.even.when.none.are.defined", "false");

    static final boolean reuseDefaultEntityLabelsWhenClassNameContainsPropertyFieldName
        = getBoolean("reuse.default.entity.labels.when.class.name.contains.property.field.name", "true");

    private static boolean getBoolean(String key, String defaultValue) {
        String value = bootstrapping.getString(key, defaultValue);
        map.put(key, value);
        return BitUtils.valueOf(value);
    }

    static {
        for (String key : map.keySet()) {
            logger.trace(key + " = " + map.get(key));
        }
    }

}
