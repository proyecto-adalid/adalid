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
public class SqlIndexColumn extends SqlArtifact {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlIndex _index;

    private final SqlColumn _column;

    private int _position;

    private String _option;
    // </editor-fold>

    public SqlIndexColumn(SqlIndex index, SqlColumn column) {
        _index = index;
        _column = column;
        init();
    }

    private void init() {
        setName(_index.getName() + "$" + _column.getName());
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    /**
     * @return the index
     */
    public SqlIndex getIndex() {
        return _index;
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
     * @return the option
     */
    public String getOption() {
        return _option;
    }

    /**
     * @param option the option to set
     */
    void setOption(String option) {
        _option = option;
    }

    public String getQualifiedName() {
        return getIndex().getName() + "." + getIndex().getTable().getName() + "." + getName();
    }
    // </editor-fold>

}
