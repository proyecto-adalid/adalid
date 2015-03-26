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
public class SqlColumn extends SqlArtifact {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private SqlTable _table;

    private int _position;

    private String _type;

    private String _sqlDataType;

    private int _length;

    private int _precision;

    private int _scale;

    private boolean _nullable;

    private boolean _primary;

    private boolean _unique;

    private boolean _booleanish;

    private boolean _foreign;

    private String _foreignTableName;

    private SqlTable _foreignTable;

    private String _default;

    private String _sqlDefaultValue;
    // </editor-fold>

    public SqlColumn(SqlTable table) {
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
     * @return the type
     */
    public String getType() {
        return _type;
    }

    /**
     * @param type the type to set
     */
    void setType(String type) {
        _type = type;
    }

    /**
     * @return the sql data type
     */
    public String getSqlDataType() {
        return _sqlDataType;
    }

    /**
     * @param sqlDataType the sql data type to set
     */
    void setSqlDataType(String sqlDataType) {
        _sqlDataType = sqlDataType;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return _length;
    }

    /**
     * @param length the length to set
     */
    void setLength(int length) {
        _length = length;
    }

    /**
     * @return the precision
     */
    public int getPrecision() {
        return _precision;
    }

    /**
     * @param precision the precision to set
     */
    void setPrecision(int precision) {
        _precision = precision;
    }

    /**
     * @return the scale
     */
    public int getScale() {
        return _scale;
    }

    /**
     * @param scale the scale to set
     */
    void setScale(int scale) {
        _scale = scale;
    }

    /**
     * @return the nullable indicator
     */
    public boolean isNullable() {
        return _nullable;
    }

    /**
     * @param nullable the nullable indicator to set
     */
    void setNullable(boolean nullable) {
        _nullable = nullable;
    }

    /**
     * @return the primary indicator
     */
    public boolean isPrimary() {
        return _primary;
    }

    /**
     * @param primary the primary indicator to set
     */
    void setPrimary(boolean primary) {
        _primary = primary;
        if (_primary && _table != null) {
            if (_table.getPrimaryKey() == null) {
                _table.setPrimaryKey(this);
            }
        }
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
        if (_unique && _type != null && _table != null) {
            if (_table.getBusinessKey() == null && _type.equals("string")) {
                _table.setBusinessKey(this);
            }
        }
    }

    /**
     * @return the booleanish indicator
     */
    public boolean isBooleanish() {
        return _booleanish;
    }

    /**
     * @param booleanish the booleanish indicator to set
     */
    void setBooleanish(boolean booleanish) {
        _booleanish = booleanish;
    }

    /**
     * @return the foreign indicator
     */
    public boolean isForeign() {
        return _foreign;
    }

    /**
     * @param foreign the foreign indicator to set
     */
    void setForeign(boolean foreign) {
        _foreign = foreign;
    }

    /**
     * @return the foreign table name
     */
    public String getForeignTableName() {
        return _foreignTableName;
    }

    /**
     * @param foreignTableName the foreign table name to set
     */
    void setForeignTableName(String foreignTableName) {
        _foreignTableName = foreignTableName;
    }

    /**
     * @return the foreign table
     */
    public SqlTable getForeignTable() {
        return _foreignTable;
    }

    /**
     * @param foreignTable the foreign table to set
     */
    void setForeignTable(SqlTable foreignTable) {
        _foreignTable = foreignTable;
    }

    /**
     * @return the default value
     */
    public String getDefault() {
        return _default;
    }

    /**
     * @param defaultValue the default value to set
     */
    void setDefault(String defaultValue) {
        _default = defaultValue;
    }

    /**
     * @return the sql default value
     */
    public String getSqlDefaultValue() {
        return _sqlDefaultValue;
    }

    /**
     * @param sqlDefaultValue the sql default value to set
     */
    void setSqlDefaultValue(String sqlDefaultValue) {
        _sqlDefaultValue = sqlDefaultValue;
    }

    /**
     * @return the version indicator
     */
    public boolean isVersion() {
        return !_nullable && matches("long", "version");
    }

    public boolean matches(String type, String role) {
        String table = _table.getName();
        String name = getName();
        return _type.equals(type) && (name.equals(role) || name.equals(role + '_' + table) || name.equals(table + '_' + role));
    }

    public boolean isOrdinary() {
        return !isSpecial();
    }

    public boolean isSpecial() {
        return equals(_table.getPrimaryKey()) || equals(_table.getVersion()) || equals(_table.getBusinessKey());
    }

    public String getTrueType() {
        if (_type.equals("integer") && getName().startsWith("es_")) {
            return "boolean";
        } else if (_type.equals("timestamp") && getName().startsWith("fecha") && !getName().startsWith("fecha_hora")) {
            return "date";
        } else if (_type.equals("timestamp") && getName().startsWith("hora")) {
            return "time";
        } else {
            return _type;
        }
    }

    public String getMetajavaType() {
        if (_primary || _foreignTable == null) {
        } else {
            return _foreignTable.getCapitalizedJavaName();
        }
        switch (_type) {
            case "boolean":
                return "BooleanProperty";
            case "char":
                return "CharacterProperty";
            case "string":
                return "StringProperty";
            case "short":
                return "ShortProperty";
            case "integer":
                return "IntegerProperty";
            case "long":
                return "LongProperty";
            case "decimal":
                return "BigDecimalProperty";
            case "float":
                return "FloatProperty";
            case "double":
                return "DoubleProperty";
            case "date":
                return "DateProperty";
            case "time":
                return "TimeProperty";
            case "timestamp":
                return "TimestampProperty";
            default:
                return "Property";
        }
    }

    public String getSqlType() {
        switch (_sqlDataType) {
            case "char":
            case "character":
            case "character varying":
            case "varchar":
            case "text":
                if (_length > 0) {
                    return _sqlDataType + "(" + _length + ")";
                }
                break;
            case "decimal":
            case "numeric":
            case "money":
                if (_precision > 0) {
                    if (_scale > 0) {
                        return _sqlDataType + "(" + _precision + ", " + _scale + ")";
                    }
                    return _sqlDataType + "(" + _precision + ")";
                }
                break;
            case "time":
            case "time with time zone":
            case "time without time zone":
            case "timestamp":
            case "timestamp with time zone":
            case "timestamp without time zone":
                if (_precision > 0) {
                    int i = _sqlDataType.indexOf(" ");
                    return i < 0
                        ? _sqlDataType + "(" + _precision + ")"
                        : _sqlDataType.substring(0, i) + "(" + _precision + ")" + _sqlDataType.substring(i);
                }
                break;
            default:
                break;
        }
        return _sqlDataType;
    }

    public String getQualifiedName() {
        return getTable().getName() + "." + getName();
    }
    // </editor-fold>

}
