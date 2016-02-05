/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.sql;

import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
public class IsCustomIndex implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof SqlIndex) {
            SqlIndex index = (SqlIndex) object;
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
