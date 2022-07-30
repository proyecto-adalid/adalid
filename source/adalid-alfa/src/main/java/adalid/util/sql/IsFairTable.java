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
public class IsFairTable implements Predicate {

    @Override
    public boolean evaluate(Object object) {
        /* until 13/07/2022
        if (object instanceof SqlTable) {
            SqlTable table = (SqlTable) object;
//          return table.isEnumeration() ? isFairEnumerationTable(table) : isFairTable(table);
            return table.getPrimaryKey() != null;
        }
        return false;
        /**/
        return object instanceof SqlTable;
    }

    /*
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

    /**/
}
