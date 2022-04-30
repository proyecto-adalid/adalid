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
public class SqlRowValue extends SqlArtifact {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlRow _row;

    private final SqlColumn _column;

    private int _position;

    private Object _object;

    private String _value;

    private String _literal;
    // </editor-fold>

    public SqlRowValue(SqlRow row, SqlColumn column) {
        _row = row;
        _column = column;
        init();
    }

    private void init() {
        String name = _row.getName() + "$" + _column.getName();
        setName(name);
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    /**
     * @return the row
     */
    public SqlRow getRow() {
        return _row;
    }

    /**
     * @return the column
     */
    public SqlColumn getColumn() {
        return _column;
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
     * @return the object
     */
    public Object getObject() {
        return _object;
    }

    /**
     * @param object the object to set
     */
    void setObject(Object object) {
        _object = object;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return _value;
    }

    /**
     * @param value the value to set
     */
    void setValue(String value) {
        _value = value;
    }

    /**
     * @return the literal
     */
    public String getLiteral() {
        return _literal == null ? "null" : _literal;
    }

    /**
     * @param literal the literal to set
     */
    void setLiteral(String literal) {
        _literal = literal;
    }

    /**
     * @return the primary key value indicator
     */
    public boolean isPrimaryKeyValue() {
        return getColumn() == getColumn().getTable().getPrimaryKey();
    }

    /**
     * @return the business key value indicator
     */
    public boolean isBusinessKeyValue() {
        return getColumn() == getColumn().getTable().getBusinessKey();
    }

    public String getQualifiedName() {
        return getRow().getName() + "." + getRow().getTable().getName() + "." + getName();
    }

    public String getUpdateStatement() {
        return "update " + getColumn().getTable().getName()
            + " set " + getColumn().getName() + " = " + getLiteral()
            + " where " + getColumn().getTable().getPrimaryKey().getName() + " = " + getRow().getPrimaryKeyValue() + ";";
    }
    // </editor-fold>

}
