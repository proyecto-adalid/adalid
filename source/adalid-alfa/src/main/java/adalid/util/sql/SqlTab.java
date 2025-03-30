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
public class SqlTab extends SqlArtifact {

    private static final Logger logger = Logger.getLogger(SqlTab.class);

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlTable _table;

    private int _position;

    private String _defaultLabel;

    private final Map<String, SqlTabColumn> _columns = new LinkedHashMap<>();
    // </editor-fold>

    public SqlTab(SqlTable table) {
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
     * @return the default label
     */
    public String getDefaultLabel() {
        return _defaultLabel;
    }

    /**
     * @param defaultLabel the default label to set
     */
    void setDefaultLabel(String defaultLabel) {
        _defaultLabel = defaultLabel;
    }

    /**
     * @return the columns map
     */
    public Map<String, SqlTabColumn> getColumnsMap() {
        return _columns;
    }

    /**
     * @return the columns list
     */
    public Collection<SqlTabColumn> getColumns() {
        return _columns.values();
    }

    public String getQualifiedName() {
        return getTable().getName() + "." + getName();
    }
    // </editor-fold>

    private int columns;

    void add(SqlTabColumn column) {
        column.setPosition(++columns);
        String name = column.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name column will not be added to tab " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_columns.containsKey(name)) {
            text = "column " + name + " already added to tab " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _columns.put(name, column);
        }
    }

}
