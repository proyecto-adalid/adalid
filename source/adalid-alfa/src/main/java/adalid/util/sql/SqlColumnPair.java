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

/**
 * @author Jorge Campins
 */
public class SqlColumnPair {

    private final SqlColumn _newColumn, _oldColumn;

    private boolean _odd, _equals, _equates, _casts, _refers;

    public SqlColumnPair(SqlColumn newColumn, SqlColumn oldColumn) {
        _newColumn = newColumn;
        _oldColumn = oldColumn;
        if (oldColumn == null) {
            _odd = true;
        } else {
            _equals = SqlMerger.equals(newColumn, oldColumn);
            _equates = SqlMerger.equates(newColumn, oldColumn);
            _casts = SqlMerger.casts(newColumn, oldColumn);
            _refers = SqlMerger.refers(newColumn, oldColumn);
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

    public boolean refers() {
        return _refers;
    }

}
