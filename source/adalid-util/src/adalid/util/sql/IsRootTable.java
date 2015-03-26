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
public class IsRootTable implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof SqlTable) {
            SqlTable table = (SqlTable) object;
            return table.isRoot();
        }
        return false;
    }

}
