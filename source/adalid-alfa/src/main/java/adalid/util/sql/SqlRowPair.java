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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class SqlRowPair {

    private final String _name;

    private final SqlRow _newRow, _oldRow;

    private final List<SqlRowValuePair> _updatedRowValues = new ArrayList<>();

    public SqlRowPair(SqlRow newRow, SqlRow oldRow) {
        _name = newRow == null ? oldRow.getName() : newRow.getName();
        _newRow = newRow;
        _oldRow = oldRow;
    }

    public String getName() {
        return _name;
    }

    public SqlRow getNewRow() {
        return _newRow;
    }

    public SqlRow getOldRow() {
        return _oldRow;
    }

    public List<SqlRowValuePair> getUpdatedRowValues() {
        return _updatedRowValues;
    }

}
