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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public class SqlTableWrapper {

    private final SqlTable _table;

    private final SqlMerger _merger;

    private final List<SqlColumnPair> _columnPairs = new ArrayList<>();

    private boolean _addedColumns;

    private boolean _droppedColumns;

    private boolean _slightlyModifiedColumns;

    private boolean _heavilyModifiedColumns;

    public SqlTableWrapper(SqlTable table, SqlMerger merger) {
        _table = table;
        _merger = merger;
    }

    public SqlTable getTable() {
        return _table;
    }

    public List<SqlColumnPair> getColumnPairs() {
        return _columnPairs;
    }

    public List<SqlColumnPair> getOrderedPairs() {
        List<SqlColumnPair> list = new ArrayList<>(_columnPairs);
        Comparator<SqlColumnPair> comparator = new SqlColumnPairPositionComparator();
        Collections.sort(list, comparator);
        return list;
    }

    SqlColumnPair addColumnPair(SqlColumn newColumn, SqlColumn oldColumn) {
        SqlColumnPair pair = new SqlColumnPair(newColumn, oldColumn);
        _columnPairs.add(pair);
        return pair;
    }

    public boolean hasAddedColumns() {
        return _addedColumns;
    }

    public void setAddedColumns(boolean b) {
        _addedColumns = b;
    }

    public boolean hasDroppedColumns() {
        return _droppedColumns;
    }

    public void setDroppedColumns(boolean b) {
        _droppedColumns = b;
    }

    public boolean hasSlightlyModifiedColumns() {
        return _slightlyModifiedColumns;
    }

    public void setSlightlyModifiedColumns(boolean b) {
        _slightlyModifiedColumns = b;
    }

    public boolean hasHeavilyModifiedColumns() {
        return _heavilyModifiedColumns;
    }

    public void setHeavilyModifiedColumns(boolean b) {
        _heavilyModifiedColumns = b;
    }

    public boolean isMigrateable() {
        return !_table.isCatalogTable() && !_table.isEnumeration();
    }

    public boolean isCopyable() {
        if (_addedColumns || _droppedColumns || _heavilyModifiedColumns) {
            return false;
        }
        String foreignTableName;
        Set<String> currentKeyTableNames = _merger.getCurrentKeyTableNames();
        for (SqlColumnPair pair : _columnPairs) {
            foreignTableName = pair.getNewColumn().getForeignTableName();
            if (foreignTableName != null && currentKeyTableNames.contains(foreignTableName)) {
                return false;
            }
        }
        return true;
    }

}
