/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.sql;

/**
 * @author Jorge Campins
 */
public class SqlColumnPair {

    private final SqlColumn _newColumn, _oldColumn;

    private boolean _odd, _equals, _equates, _casts;

    public SqlColumnPair(SqlColumn newColumn, SqlColumn oldColumn) {
        _newColumn = newColumn;
        _oldColumn = oldColumn;
        if (oldColumn == null) {
            _odd = true;
        } else {
            _equals = SqlMerger.equals(newColumn, oldColumn);
            _equates = SqlMerger.equates(newColumn, oldColumn);
            _casts = SqlMerger.casts(newColumn, oldColumn);
        }
    }

    public SqlColumn getNewColumn() {
        return _newColumn;
    }

    public SqlColumn getOldColumn() {
        return _oldColumn;
    }

    public boolean added() {
        return _odd;
    }

    public boolean equals() {
        return _equals;
    }

    public boolean equates() {
        return _equates;
    }

    public boolean casts() {
        return _casts;
    }

}
