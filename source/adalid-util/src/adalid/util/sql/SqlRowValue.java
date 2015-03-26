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
public class SqlRowValue extends SqlArtifact {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private SqlRow _row;

    private SqlColumn _column;

    private int _position;

    private String _value;
    // </editor-fold>

    public SqlRowValue(SqlRow row, SqlColumn column) {
        _row = row;
        _column = column;
        setName(_row.getName() + "$" + _column.getName());
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

    public String getQualifiedName() {
        return getRow().getName() + "." + getRow().getTable().getName() + "." + getName();
    }
    // </editor-fold>

}
