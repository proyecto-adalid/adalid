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
package adalid.util.sql;

import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsCustomIndex implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof SqlIndex index) {
            SqlColumn column = index.getSingleColumn();
            if (column == null) {
                return true;  // multi-column index
            } else if (column.isSpecial()) {
                return false; // primary key, version or business key
            } else if (column.isName() || column.isForeign()) {
                return index.isUnique();
            }
            return true;
        }
        return false;
    }

}
