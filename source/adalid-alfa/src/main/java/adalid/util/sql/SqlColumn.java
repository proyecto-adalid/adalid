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
public class SqlColumn extends SqlArtifact {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlTable _table;

    private int _position;

    private String _type;

    private String _sqlDataType;

    private String _sqlType;

    private int _length;

    private int _precision;

    private int _scale;

    private boolean _collision;

    private boolean _updatable;

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
     * @return the sql type
     */
    public String getSqlType() {
        return _sqlType == null ? _sqlDataType : _sqlType;
    }

    /**
     * @param sqlType the sql type to set
     */
    void setSqlType(String sqlType) {
        _sqlType = sqlType;
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
     * @return the collision indicator
     */
    public boolean isCollision() {
        return _collision;
    }

    /**
     * @param collision the problematic indicator to set
     */
    void setCollision(boolean collision) {
        _collision = collision;
    }

    /**
     * @return the updatable indicator
     */
    public boolean isUpdatable() {
        return _updatable;
    }

    /**
     * @param updatable the updatable indicator to set
     */
    void setUpdatable(boolean updatable) {
        _updatable = updatable;
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

    /**
     * @return the discriminator indicator
     */
    public boolean isDiscriminator() {
        return !_nullable && _foreign && matches("integer", "discriminador");
    }

    /**
     * @return the name indicator
     */
    public boolean isName() {
        return !_nullable && matches("string", "nombre");
    }

    /**
     * @return the description indicator
     */
    public boolean isDescription() {
        return matches("string", "descripcion");
    }

    public boolean matches(String type, String role) {
        String table = _table.getName();
        String name = getName();
        return _type.equalsIgnoreCase(type)
            && (name.equalsIgnoreCase(role) || name.equalsIgnoreCase(role + '_' + table) || name.equalsIgnoreCase(table + '_' + role));
    }

    public boolean isOrdinary() {
        return !isSpecial();
    }

    public boolean isSpecial() {
        return equals(_table.getPrimaryKey()) || equals(_table.getVersion()) || equals(_table.getBusinessKey());
    }

    public String getTrueType() {
        /*
        String name = getName().toLowerCase();
        if (_type.equals("integer") && name.startsWith("es_")) {
            return "boolean";
        } else if (_type.equals("timestamp") && name.startsWith("fecha") && !name.startsWith("fecha_hora")) {
            return "date";
        } else if (_type.equals("timestamp") && name.startsWith("hora")) {
            return "time";
        } else {
            return _type;
        }
         */
        return _type;
    }

    public String getMetajavaType() {
        if (_primary || _foreignTable == null) {
        } else {
            return _foreignTable.getCapitalizedJavaName();
        }
        switch (_type) {
            case "blob":
                return "BinaryProperty";
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

    public String getQualifiedName() {
        return getTable().getName() + "." + getName();
    }
    // </editor-fold>

}
