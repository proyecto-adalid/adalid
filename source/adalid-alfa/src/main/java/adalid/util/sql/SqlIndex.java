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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlIndex extends SqlArtifact {

    private static final Logger logger = Logger.getLogger(SqlIndex.class);

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlTable _table;

    private int _position;

    private boolean _unique;

    private SqlIndexColumn _lastIndexColumn;

    private final Map<String, SqlIndexColumn> _columns = new LinkedHashMap<>();
    // </editor-fold>

    public SqlIndex(SqlTable table) {
        _table = table;
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    /**
     * @return the table
     */
    public SqlTable getTable() {
        return _table;
    }

    /**
     * @return the ordinal position
     */
    public int getPosition() {
        return _position;
    }

    /**
     * @param position the ordinal position to set
     */
    void setPosition(int position) {
        _position = position;
    }

    /**
     * @return the unique indicator
     */
    public boolean isUnique() {
        return _unique;
    }

    /**
     * @param unique the unique indicator to set
     */
    void setUnique(boolean unique) {
        _unique = unique;
    }

    /**
     * @return the columns map
     */
    public Map<String, SqlIndexColumn> getColumnsMap() {
        return _columns;
    }

    /**
     * @return the columns list
     */
    public Collection<SqlIndexColumn> getColumns() {
        return _columns.values();
    }

    public String getQualifiedName() {
        return getTable().getName() + "." + getName();
    }
    // </editor-fold>

    /**
     * @return the single column index flag
     */
    public boolean isSingleColumnIndex() {
        return _lastIndexColumn != null && _columns.size() == 1;
    }

    /**
     * @return the column
     */
    public SqlColumn getSingleColumn() {
        return !isSingleColumnIndex() ? null : _lastIndexColumn.getColumn();
    }

    /**
     * @return the option
     */
    public String getSingleColumnOption() {
        return !isSingleColumnIndex() ? null : _lastIndexColumn.getOption();
    }

//  private int columns;
//
    void add(SqlIndexColumn column) {
//      column.setPosition(++columns);
        String name = column.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name column will not be added to index " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_columns.containsKey(name)) {
            text = "column " + name + " already added to index " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _lastIndexColumn = column;
            _columns.put(name, column);
        }
    }

}
