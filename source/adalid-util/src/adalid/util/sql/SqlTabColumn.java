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
public class SqlTabColumn extends SqlArtifact {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlTab _tab;

    private final SqlColumn _column;

    private int _position;
    // </editor-fold>

    public SqlTabColumn(SqlTab tab, SqlColumn column) {
        _tab = tab;
        _column = column;
        setName(_tab.getName() + "$" + _column.getName());
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    /**
     * @return the tab
     */
    public SqlTab getTab() {
        return _tab;
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

    public String getQualifiedName() {
        return getTab().getName() + "." + getTab().getTable().getName() + "." + getName();
    }
    // </editor-fold>

}
