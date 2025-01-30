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
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class SqlRoutineParameter extends SqlArtifact {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlRoutine _routine;

    private int _position;

    private String _type;

    private String _sqlDataType;

    private boolean _required;

    private boolean _hidden;

    private String _default;

    private String _sqlDefaultValue;
    // </editor-fold>

    public SqlRoutineParameter(SqlRoutine routine) {
        _routine = routine;
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    /**
     * @return the routine
     */
    public SqlRoutine getRoutine() {
        return _routine;
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
     * @return the required indicator
     */
    public boolean isRequired() {
        return _required;
    }

    /**
     * @param required the required indicator to set
     */
    void setRequired(boolean required) {
        _required = required;
    }

    /**
     * @return the hidden indicator
     */
    public boolean isHidden() {
        return _hidden;
    }

    /**
     * @param hidden the hidden indicator to set
     */
    void setHidden(boolean hidden) {
        _hidden = hidden;
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

    public boolean matches(String type, String role) {
        if (StringUtils.isBlank(type) || StringUtils.isBlank(role)) {
            return false;
        }
        String name = getName();
        String table = getRoutine().getTable().getName();
        return matches(type, role, name, table);
    }

    private boolean matches(String type, String role, String name, String table) {
        return _type.equals(type) && (name.equals(role) || name.equals(role + '_' + table) || name.equals(table + '_' + role));
    }

    public SqlTable getReferencedTable() {
        String type;
        String role;
        String name = getName();
        SqlColumn namesake = getNamesakeColumn();
        if (namesake != null && namesake.isForeign()) {
            return namesake.getForeignTable();
        }
        String table;
        SqlColumn pk;
        SqlReader reader = getRoutine().getTable().getReader();
        Collection<SqlTable> tables = reader.getTables();
        for (SqlTable sqlTable : tables) {
            table = sqlTable.getName();
            pk = sqlTable.getPrimaryKey();
            if (pk != null) {
                type = pk.getType();
                role = remove(pk.getName(), table);
                if (matches(type, role, name, table)) {
                    return sqlTable;
                }
            }
        }
        return null;
    }

    /**
     * @return the namesake column
     */
    public SqlColumn getNamesakeColumn() {
        String name = getName();
        return getNamesakeColumn(name);
    }

    /**
     * @param name name
     * @return the namesake column
     */
    public SqlColumn getNamesakeColumn(String name) {
        SqlColumn namesake = getRoutine().getTable().getSqlColumn(name);
        return namesake != null && namesake.getType().equals(getTrueType()) ? namesake : null;
    }

    /**
     * @return the foreign table
     */
    public SqlTable getForeignTable() {
        SqlColumn namesake = getNamesakeColumn();
        return namesake == null ? null : namesake.getForeignTable();
    }

    /**
     * @return the foreign indicator
     */
    public boolean isForeign() {
        SqlColumn namesake = getNamesakeColumn();
        return namesake != null && namesake.isForeign();
    }

    private String remove(String name, String table) {
        return StringUtils.removeStart(StringUtils.removeEnd(name, "_" + table), table + "_");
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
        return switch (_type) {
            case "boolean" ->
                "BooleanParameter";
            case "char" ->
                "CharacterParameter";
            case "string" ->
                "StringParameter";
            case "short" ->
                "ShortParameter";
            case "integer" ->
                "IntegerParameter";
            case "long" ->
                "LongParameter";
            case "decimal" ->
                "BigDecimalParameter";
            case "float" ->
                "FloatParameter";
            case "double" ->
                "DoubleParameter";
            case "date" ->
                "DateParameter";
            case "time" ->
                "TimeParameter";
            case "timestamp" ->
                "TimestampParameter";
            default ->
                "Parameter";
        };
    }

    public String getQualifiedName() {
        return getRoutine().getName() + "." + getRoutine().getTable().getName() + "." + getName();
    }
    // </editor-fold>

}
