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

import adalid.commons.util.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlRow extends SqlArtifact {

    private static final Logger logger = Logger.getLogger(SqlRow.class);

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlTable _table;

    private int _position;

    private final Map<String, SqlRowValue> _values = new LinkedHashMap<>();
    // </editor-fold>

    public SqlRow(SqlTable table) {
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
     * @return the values map
     */
    public Map<String, SqlRowValue> getValuesMap() {
        return _values;
    }

    /**
     * @return the values list
     */
    public Collection<SqlRowValue> getValues() {
        return _values.values();
    }

    /**
     * @return the primary key
     */
    public SqlRowValue getPrimaryKey() {
        SqlColumn primaryKey = _table.getPrimaryKey();
        if (primaryKey != null) {
            for (SqlRowValue value : _values.values()) {
                if (primaryKey.equals(value.getColumn())) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * @return the primary key value
     */
    public String getPrimaryKeyValue() {
        SqlRowValue primaryKey = getPrimaryKey();
        return primaryKey == null ? null : primaryKey.getValue();
    }

    /**
     * @return the business key
     */
    public SqlRowValue getBusinessKey() {
        SqlColumn businessKey = _table.getBusinessKey();
        if (businessKey != null) {
            for (SqlRowValue value : _values.values()) {
                if (businessKey.equals(value.getColumn())) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * @return the business key value
     */
    public String getBusinessKeyValue() {
        SqlRowValue businessKey = getBusinessKey();
        return businessKey == null ? null : businessKey.getValue();
    }

    /**
     * @return the row name
     */
    public String getJavaConstantName() {
        String name = getName();
        String prefix = name != null && Character.isDigit(name.charAt(0)) ? "_" : "";
        return prefix + StrUtils.getIdentificadorSqlUpperCase(name);
    }

    public String getQualifiedName() {
        return getTable().getName() + "." + getName();
    }
    // </editor-fold>

    private int values;

    void add(SqlRowValue value) {
        value.setPosition(++values);
        String name = value.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name value will not be added to row " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_values.containsKey(name)) {
            text = "value " + name + " already added to row " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _values.put(name, value);
        }
    }

}
