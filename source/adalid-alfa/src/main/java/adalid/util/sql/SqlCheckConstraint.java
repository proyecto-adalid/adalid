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
public class SqlCheckConstraint extends SqlArtifact {

    private final SqlTable _table;

    private String _type, _tableName, _constraintDefinition;

    private MetaCheckConstraint _metaCheckConstraint;

    public SqlCheckConstraint(SqlTable table) {
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

    public String getConstraintDefinition() {
        return _constraintDefinition;
    }

    public void setConstraintDefinition(String statement) {
        _constraintDefinition = statement;
    }

    public MetaCheckConstraint getMetaCheckConstraint() {
        if (_metaCheckConstraint == null) {
            _metaCheckConstraint = new MetaCheckConstraint(this);
        }
        return _metaCheckConstraint;
    }

}
