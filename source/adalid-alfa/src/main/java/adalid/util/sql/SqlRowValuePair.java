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
public class SqlRowValuePair {

    private final String _name;

    private final SqlRowValue _newValue, _oldValue;

    public SqlRowValuePair(SqlRowValue newValue, SqlRowValue oldValue) {
        _name = newValue == null ? oldValue.getName() : newValue.getName();
        _newValue = newValue;
        _oldValue = oldValue;
    }

    public boolean isUpdatableColumn() {
        return _oldValue != null && _oldValue.getColumn() != null && _oldValue.getColumn().isUpdatable();
    }

    public String getName() {
        return _name;
    }

    public SqlRowValue getNewValue() {
        return _newValue;
    }

    public SqlRowValue getOldValue() {
        return _oldValue;
    }

    public String getNewLiteral() {
        return _newValue == null ? "null" : _newValue.getLiteral();
    }

    public String getOldLiteral() {
        return _oldValue == null ? "null" : _oldValue.getLiteral();
    }

}
