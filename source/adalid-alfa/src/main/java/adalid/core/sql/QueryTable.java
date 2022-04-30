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
package adalid.core.sql;

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.programmers.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class QueryTable {

    private PersistentEntity _entity;

    private String _name;

    private String _alias;

    private String _trace;

    private String _prefix;

    private String _suffix;

    private String _sufijo;

    private VirtualEntityType _virtualEntityType;

    private QueryTable _leftTable;

    private PersistentEntity _joinedEntity;

    private Entity _joiningEntity;

    private boolean _line;

    private boolean _star;

    private int _maxDepth;

    private int _depth;

    private int _index;

    private int _subqueryIndex;

    private List<Property> _columns;

    private List<QueryJoin> _joins;

    private SqlProgrammer _sqlProgrammer;

    /**
     * @return the _entity
     */
    public PersistentEntity getEntity() {
        return _entity;
    }

    /**
     * @return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return _alias;
    }

    /**
     * @return the trace message
     */
    public String getTrace() {
        return _trace == null ? "" : _trace.trim();
    }

    /**
     * @param trace message
     */
    public void setTrace(String trace) {
        _trace = trace;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return _prefix;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return _suffix;
    }

    /**
     * @return the sufijo
     */
    private String getSufijo() {
        return _sufijo;
    }

    /**
     * @return the virtual entity type
     */
    public VirtualEntityType getVirtualEntityType() {
        return _virtualEntityType;
    }

    /**
     * @return the max depth
     */
    public int getMaxDepth() {
        return _maxDepth;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return _depth;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return _index;
    }

    /**
     * @return the subquery index
     */
    public int getSubqueryIndex() {
        return _subqueryIndex;
    }

    /**
     * @param index the subquery index to set
     */
    private void setSubqueryIndex(int index) {
        _subqueryIndex = index;
        for (QueryJoin j : _joins) {
            j.getRightTable().setSubqueryIndex(index);
        }
    }

    /**
     * @return the columns
     */
    public List<Property> getColumns() {
        return _columns;
    }

    /**
     * @return the joins
     */
    public List<QueryJoin> getJoins() {
        return _joins;
    }

    /**
     * @return the sql programmer
     */
    public SqlProgrammer getSqlProgrammer() {
        return _sqlProgrammer;
    }

    public QueryTable(PersistentEntity entity, int maxDepth, VirtualEntityType virtualEntityType) {
        this(entity, maxDepth, virtualEntityType, 0, null, null, null);
        int index = 0;
        for (QueryJoin j : _joins) {
            index++;
            j.getRightTable().setSubqueryIndex(index);
        }
    }

    private QueryTable(PersistentEntity entity, int maxDepth, VirtualEntityType virtualEntityType, int depth,
        QueryTable leftTable, PersistentEntity joinedEntity, Entity joiningEntity) {
        if (entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        _sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        if (_sqlProgrammer == null) {
            throw new RuntimeException("null sql programmer");
        }
        _entity = entity;
        _maxDepth = maxDepth;
//      _virtualEntityType = virtualEntityType;
        _depth = depth;
        _leftTable = leftTable;
        _joinedEntity = joinedEntity;
        _joiningEntity = joiningEntity;
        _index = getReferenceIndex(entity);
//      _name = _sqlProgrammer.getSqlTableName(entity);
        _name = _sqlProgrammer.getSqlSchemaQualifiedShortTableName(entity);
//      _alias = _name;
        _alias = _sqlProgrammer.getSqlSchemaUnqualifiedShortTableName(entity);
        if (leftTable == null) {
            _prefix = "";
            _suffix = "";
            _sufijo = "";
            _virtualEntityType = virtualEntityType;
        } else {
            _prefix = "";
            _sufijo = leftTable.getSufijo() + "_" + _index;
            if (_sufijo.equals("_0")) {
                _sufijo = "";
            }
            /* until 08/10/2021
            _suffix = joinedEntity == null ? _sufijo : leftTable.getSuffix();
            /* since 08/10/2021 */
            if (joinedEntity == null) {
                _suffix = _sufijo;
            } else {
                _suffix = leftTable.getSuffix();
                _sufijo = StringUtils.removeEnd(_sufijo, "_0");
                if (_index == 0) {
                    _index = leftTable.getIndex();
                }
//              System.out.println(this);
            }
            /**/
//          _alias += _suffix;
            _virtualEntityType = leftTable.getVirtualEntityType();
            // get short alias
            int l1 = _alias.length();
            int l2 = _suffix.length();
            int l3 = _sqlProgrammer.getMaxIdentifierLength();
            String a = l1 + l2 > l3 ? _alias.substring(0, l3 - l2) : _alias;
            _alias = a + _suffix;
        }
        _columns = new ArrayList<>();
        _joins = new ArrayList<>();
        Property leftColumn;
        PersistentEntity leftEntity;
        Entity baseEntity;
        PersistentEntity rightEntity;
        List<Property> properties;
        initJoinFlags();
        if (entity.isJoinedTable()) {
            leftColumn = entity.getPrimaryKeyProperty();
            leftEntity = entity;
            baseEntity = leftEntity.getBaseRoot();
            rightEntity = leftEntity.getBaseTableRoot();
            if (joinedEntity == null) {
                properties = entity.getJoinedTablePropertiesList();
                if (_line) {
                    addJoin(leftColumn, rightEntity, maxDepth, depth, entity, baseEntity);
                }
            } else {
                properties = joinedEntity.getJoinedTableMatchingPropertiesList(entity.getJoinedTablePropertiesMap());
                if (_line) {
                    addJoin(leftColumn, rightEntity, maxDepth, depth, joinedEntity, baseEntity);
                }
            }
        } else if (joinedEntity != null) {
            properties = joinedEntity.getJoinedTableMatchingPropertiesList(joiningEntity.getPropertiesMap());
        } else if (entity.isTable()) {
            properties = entity.getPropertiesList();
        } else {
            leftColumn = entity.getPrimaryKeyProperty();
            leftEntity = entity.getBaseTableRoot();
            if (leftEntity == null) {
                properties = null;
            } else {
                baseEntity = leftEntity.getBaseRoot();
                rightEntity = leftEntity.getBaseTableRoot();
                if (rightEntity == null) {
                    properties = entity.getPropertiesList();
                } else {
                    properties = entity.getSingleJoinedTablePropertiesList(rightEntity.getPropertiesMap());
                    if (_line) {
                        addJoin(leftColumn, rightEntity, maxDepth, depth, entity, baseEntity);
                    }
                }
            }
        }
        if (properties != null) {
            for (Property property : properties) {
                if (property instanceof Primitive) {
                    _columns.add(property);
                } else if (property instanceof PersistentEntity) {
                    _columns.add(property);
                    leftColumn = property;
                    rightEntity = (PersistentEntity) property;
                    if (_star) {
                        addJoin(leftColumn, rightEntity, maxDepth, depth);
                    }
                }
            }
        }
        addQueryTable();
    }

    private void addQueryTable() {
        Project.addQueryTable(this);
    }

    private int getReferenceIndex(Entity entity) {
        String name = entity.getName();
        Entity declaringEntity = entity.getDeclaringEntity();
        Entity root = declaringEntity == null ? null : declaringEntity.getRoot();
        Property property = root == null ? null : root.getPropertiesMap().get(name);
        Entity reference = property == null ? null : (Entity) property;
        int referenceIndex = reference == null ? 0 : reference.getReferenceIndex();
        return referenceIndex;
    }

    private void initJoinFlags() {
        if (_virtualEntityType == null) {
            _line = true;
            _star = true;
        } else {
            switch (_virtualEntityType) {
                case SELECTION:
                    _line = false;
                    _star = false;
                    break;
                case LINE:
                    _line = true;
                    _star = false;
                    break;
                case STAR:
                    _line = false;
                    _star = true;
                    break;
                default:
                    _line = true;
                    _star = true;
                    break;
            }
        }
    }

    private void addJoin(Property leftColumn, PersistentEntity rightEntity, int maxDepth, int depth) {
        addJoin(leftColumn, rightEntity, maxDepth, depth, null, null);
    }

    private void addJoin(Property leftColumn, PersistentEntity rightEntity, int maxDepth, int depth,
        PersistentEntity joinedEntity, Entity joiningEntity) {
        if (depth >= maxDepth && maxDepth >= 0) {
            return;
        }
        if (leftColumn == null || rightEntity == null) {
            return;
        }
        Property rightColumn = rightEntity.getPrimaryKeyProperty();
        if (rightColumn == null) {
            return;
        }
//      if (rightEntity.isAbstractClass()) {
//          return;
//      }
        QueryJoinOp operator = queryJoinOp(leftColumn);
        QueryTable rightTable = new QueryTable(rightEntity, maxDepth, null, depth + 1,
            this, joinedEntity, joiningEntity);
        /**/
        QueryJoin join = new QueryJoin(_sqlProgrammer);
        join.setOperator(operator);
        join.setLeftTable(this);
        join.setLeftColumn(leftColumn);
        join.setRightTable(rightTable);
        join.setRightColumn(rightColumn);
        _joins.add(join);
    }

    private QueryJoinOp queryJoinOp(Property leftColumn) {
        boolean nullable;
        boolean foreignKey;
        if (leftColumn instanceof PersistentEntityReference) {
            PersistentEntityReference reference = (PersistentEntityReference) leftColumn;
            nullable = reference.isNullable();
            foreignKey = reference.isForeignKey();
        } else {
            nullable = leftColumn == null || leftColumn.isNullable();
            foreignKey = !nullable;
        }
        QueryJoinOp operator = nullable || !foreignKey ? QueryJoinOp.LEFT : QueryJoinOp.INNER;
        return operator;
    }

    @Override
    public String toString() {
        return super.toString()
            + "\n§thisTable........: " + toString(this)
            + "\n§leftTable........: " + toString(_leftTable)
            + "\n§joinedEntity.....: " + _joinedEntity
            + "\n§joiningEntity....: " + _joiningEntity
            + "\n";
    }

    private String toString(QueryTable qt) {
        return qt == null ? null : qt._entity + ", " + qt._name + ", " + qt._alias + ", " + qt._suffix + ", " + qt._index;
    }

    /**
     * @return the joins map
     */
    public Map<String, QueryJoin> getJoinsMap() {
        Map<String, QueryJoin> map = new LinkedHashMap<>();
        for (QueryJoin j : _joins) {
            String qualifier = getSqlJoinQualifier(j.getRightTable());
            map.put(qualifier, j);
        }
        return map;
    }

    /**
     * @param primitive primitive
     * @return the referenced joins map
     */
    public Map<String, QueryJoin> getReferencedJoinsMap(Primitive primitive) {
        ArrayList<Property> referencedColumns = new ArrayList<>();
        referencedColumns.add(primitive);
        return getReferencedJoinsMap(referencedColumns);
    }

    /**
     * @param property property
     * @return the referenced joins map
     */
    public Map<String, QueryJoin> getReferencedJoinsMap(Property property) {
        ArrayList<Property> referencedColumns = new ArrayList<>();
        referencedColumns.add(property);
        return getReferencedJoinsMap(referencedColumns);
    }

    /**
     * @param referencedColumns referenced columns
     * @return the referenced joins map
     */
    public Map<String, QueryJoin> getReferencedJoinsMap(List<Property> referencedColumns) {
        Map<String, QueryJoin> map = new LinkedHashMap<>();
        for (Property property : referencedColumns) {
            putPropertyJoin(property, map);
        }
        return map;
    }

    private void putPropertyJoin(Property property, Map<String, QueryJoin> map) {
        for (Property p : _columns) {
            if (p == property) {
                return;
            }
        }
        for (QueryJoin j : _joins) {
            if (j.getRightTable().isPropertyAtJoin(property)) {
                String qualifier = getSqlJoinQualifier(j.getRightTable());
                map.put(qualifier, j);
                return;
            }
        }
    }

    private boolean isPropertyAtJoin(Property property) {
        for (Property p : _columns) {
            if (p == property) {
                return true;
            }
        }
        for (QueryJoin j : _joins) {
            if (j.getRightTable().isPropertyAtJoin(property)) {
                return true;
            }
        }
        return false;
    }

    public QueryTable containingQueryTableOf(Property property) {
        for (Property p : _columns) {
            if (p == property) {
                return this;
            }
        }
        for (QueryJoin j : _joins) {
            QueryTable queryTable = j.getRightTable().containingQueryTableOf(property);
            if (queryTable != null) {
                return queryTable;
            }
        }
        return null;
    }

    public boolean contains(Property property) {
        return property != null && isPropertyAtJoin(property);
    }

    public boolean containsAny(List<Property> properties) {
        if (properties == null || properties.isEmpty()) {
            return false;
        }
        for (Property property : properties) {
            if (contains(property)) {
                return true;
            }
        }
        return false;
    }

    public QueryTable getContainingQueryTableOf(Property property) {
        return property == null ? null : containingQueryTableOf(property);
    }

    public void merge(QueryTable anotherQueryTable) {
        QueryJoinOp operator, anotherOperator;
        QueryTable leftTable, anotherLeftTable;
        Property leftColumn, anotherLeftColumn;
        QueryTable rightTable, anotherRightTable;
        Property rightColumn, anotherRightColumn;
        boolean found;
        String anotherColumnName;
        String anotherLeftTableName;
        String anotherLeftColumnName;
        String anotherRightTableName;
        String anotherRightColumnName;
        if (anotherQueryTable != null && anotherQueryTable.getName().equals(_name)) {
            for (Property anotherColumn : anotherQueryTable.getColumns()) {
                found = false;
                anotherColumnName = anotherColumn.getName();
                if (anotherColumnName != null) {
                    for (Property column : _columns) {
                        if (anotherColumnName.equals(column.getName())) { // field name comparison
                            found = true;
                            break;
                        }
                    }
                }
                if (found) {
                } else {
                    _columns.add(anotherColumn);
                }
            }
            for (QueryJoin anotherJoin : anotherQueryTable.getJoins()) {
                anotherOperator = anotherJoin.getOperator();
                anotherLeftTable = anotherJoin.getLeftTable();
                anotherLeftColumn = anotherJoin.getLeftColumn();
                anotherRightTable = anotherJoin.getRightTable();
                anotherRightColumn = anotherJoin.getRightColumn();
                anotherLeftTableName = anotherLeftTable.getName();
                anotherLeftColumnName = anotherLeftColumn.getName();
                anotherRightTableName = anotherRightTable.getName();
                anotherRightColumnName = anotherRightColumn.getName();
                found = false;
                rightTable = null;
                if (anotherOperator != null
                    && anotherLeftTableName != null
                    && anotherLeftColumnName != null
                    && anotherRightTableName != null
                    && anotherRightColumnName != null) {
                    for (QueryJoin join : _joins) {
                        operator = join.getOperator();
                        leftTable = join.getLeftTable();
                        leftColumn = join.getLeftColumn();
                        rightTable = join.getRightTable();
                        rightColumn = join.getRightColumn();
                        if (anotherOperator.equals(operator)
                            && anotherLeftTableName.equals(leftTable.getName())
                            && anotherLeftColumnName.equals(leftColumn.getName()) // field name comparison
                            && anotherRightTableName.equals(rightTable.getName())
                            && anotherRightColumnName.equals(rightColumn.getName())) { // field name comparison
                            found = true;
                            break;
                        }
                    }
                }
                if (found && rightTable != null) {
                    rightTable.merge(anotherRightTable);
                } else {
                    _joins.add(anotherJoin);
                }
            }
        }
    }

    public String getSqlAlias(Property property) {
        return _sqlProgrammer.getSqlAlias(property, this);
    }

    public Property getProperty(String sqlAlias) {
        return _sqlProgrammer.getProperty(sqlAlias, this);
    }

    public String getSqlQualifiedName(Property property) {
        return _sqlProgrammer.getSqlQualifiedName(property, this);
    }

    public String getSqlJoinQualifier() {
        return getSqlJoinQualifier(this);
    }

    private String getSqlJoinQualifier(QueryTable queryTable) {
        return _sqlProgrammer.getSqlJoinQualifier(queryTable);
    }

    public int getSelectColumnCount() {
        int count = _columns.size();
        for (QueryJoin j : getJoins()) {
            count += j.getRightTable().getSelectColumnCount();
        }
        return count;
    }

    public List<Property> getSelectColumnsList() {
        List<Property> list = new ArrayList<>();
        list.addAll(_columns);
        for (QueryJoin j : getJoins()) {
            list.addAll(j.getRightTable().getSelectColumnsList());
        }
        return list;
    }

    /**
     * @return the select columns map
     */
    public Map<String, Property> getSelectColumnsMap() {
        return _sqlProgrammer.getSelectColumnsMap(this);
    }

    /**
     * @return the SQL statement
     */
    public String getSqlSelectStatement() {
        return getSqlSelectStatement(null);
    }

    /**
     * @param referencedColumns referenced columns
     * @return the SQL statement
     */
    public String getSqlSelectStatement(List<Property> referencedColumns) {
        return getSqlSelectStatement(referencedColumns, false);
    }

    /**
     * @param referencedColumns referenced columns
     * @param into into
     * @return the SQL statement
     */
    public String getSqlSelectStatement(List<Property> referencedColumns, boolean into) {
        return getSqlSelectStatement(referencedColumns, into, true);
    }

    /**
     * @param referencedColumns referenced columns
     * @param into into
     * @param indent indent
     * @return the SQL statement
     */
    public String getSqlSelectStatement(List<Property> referencedColumns, boolean into, boolean indent) {
        return _sqlProgrammer.getSqlSelectStatement(this, referencedColumns, into, indent);
    }

}
