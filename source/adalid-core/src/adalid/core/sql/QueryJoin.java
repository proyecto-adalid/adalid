/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.sql;

import adalid.core.enums.QueryJoinOp;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.SqlProgrammer;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class QueryJoin {

    private QueryJoinOp _operator = QueryJoinOp.INNER;

    private QueryTable _leftTable;

    private Property _leftColumn;

    private QueryTable _rightTable;

    private Property _rightColumn;

    private final SqlProgrammer _sqlProgrammer;

    QueryJoin(SqlProgrammer sqlProgrammer) {
        _sqlProgrammer = sqlProgrammer;
    }

    /**
     * @return the join operator
     */
    public QueryJoinOp getOperator() {
        return _operator;
    }

    /**
     * @param operator the join operator to set
     */
    void setOperator(QueryJoinOp operator) {
        _operator = operator;
    }

    /**
     * @return the left table
     */
    public QueryTable getLeftTable() {
        return _leftTable;
    }

    /**
     * @param table the query table to set as left table
     */
    void setLeftTable(QueryTable table) {
        _leftTable = table;
    }

    /**
     * @return the left column
     */
    public Property getLeftColumn() {
        return _leftColumn;
    }

    /**
     * @param property the property to set as left column
     */
    void setLeftColumn(Property property) {
        _leftColumn = property;
    }

    /**
     * @return the right table
     */
    public QueryTable getRightTable() {
        return _rightTable;
    }

    /**
     * @param table the query table to set as right table
     */
    void setRightTable(QueryTable table) {
        _rightTable = table;
    }

    /**
     * @return the right column
     */
    public Property getRightColumn() {
        return _rightColumn;
    }

    /**
     * @param property the property to set as right column
     */
    void setRightColumn(Property property) {
        _rightColumn = property;
    }

    /**
     * @return the SQL statement
     */
    public String getSqlSelectStatement() {
        return getSqlSelectStatement(null);
    }

    /**
     * @param referencedColumns
     * @return the SQL statement
     */
    public String getSqlSelectStatement(List<Property> referencedColumns) {
        return getSqlSelectStatement(referencedColumns, false);
    }

    /**
     * @param referencedColumns
     * @param into
     * @return the SQL statement
     */
    public String getSqlSelectStatement(List<Property> referencedColumns, boolean into) {
        return getSqlSelectStatement(referencedColumns, into, false);
    }

    /**
     * @param referencedColumns
     * @param into
     * @param where
     * @return the SQL statement
     */
    public String getSqlSelectStatement(List<Property> referencedColumns, boolean into, boolean where) {
        return getSqlSelectStatement(referencedColumns, into, where, true);
    }

    /**
     * @param referencedColumns
     * @param into
     * @param where
     * @param indent
     * @return the SQL statement
     */
    public String getSqlSelectStatement(List<Property> referencedColumns, boolean into, boolean where, boolean indent) {
        return _sqlProgrammer.getSqlSelectStatement(this, referencedColumns, into, where, indent);
    }

}
