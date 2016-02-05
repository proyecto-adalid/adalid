/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.sql;

import adalid.commons.util.StrUtils;
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
            text = "a null name value will not added to row " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_values.containsKey(name)) {
            text = "value " + name + " already added to row " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _values.put(name, value);
        }
    }

}
