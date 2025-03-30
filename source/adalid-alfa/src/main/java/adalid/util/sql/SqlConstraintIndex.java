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

import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class SqlConstraintIndex extends SqlArtifact {

    private final SqlTable _table;

    private String _type, _tableName, _createStatement, _dropStatement;

    public SqlConstraintIndex(SqlTable table) {
        _table = table;
    }

    public SqlTable getTable() {
        return _table;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public String getTableName() {
        return _tableName;
    }

    public void setTableName(String tableName) {
        _tableName = tableName;
    }

    public String getCreateStatement() {
        return _createStatement;
    }

    public String getNamelessCreateStatement() {
        String name = " " + getName() + " ";
        return StringUtils.replace(_createStatement, name, " ? ");
    }

    public void setCreateStatement(String statement) {
        _createStatement = statement;
    }

    public String getDropStatement() {
        return _dropStatement;
    }

    public void setDropStatement(String statement) {
        _dropStatement = statement;
    }

}
