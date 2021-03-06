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
public class IsFairTable implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof SqlTable) {
            SqlTable table = (SqlTable) object;
//          return table.isEnumeration() ? isFairEnumerationTable(table) : isFairTable(table);
            return table.getPrimaryKey() != null;
        }
        return false;
    }

    boolean isFairEnumerationTable(SqlTable table) {
        return table.getPrimaryKey() != null
            && table.getPrimaryKey().getType().equals("integer")
            && table.getBusinessKey() != null
            && table.getBusinessKey().getType().equals("string");
    }

    boolean isFairTable(SqlTable table) {
        return table.getPrimaryKey() != null
            && table.getPrimaryKey().getType().equals("long");
//          && table.getVersion() != null
//          && table.getVersion().getType().equals("long");
    }

}
