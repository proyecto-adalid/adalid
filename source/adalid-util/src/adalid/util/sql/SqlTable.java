/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlTable extends SqlArtifact {

    private static final Logger logger = Logger.getLogger(SqlTable.class);

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlReader _reader;

    private String _defaultLabel;

    private String _defaultCollectionLabel;

    private boolean _enumerable;

    private boolean _independent;

    private boolean _loaded;

    private String _resourceType;

    private SqlTable _baseTable;

    private SqlColumn _primaryKey;

    private SqlColumn _businessKey;

    private final Map<String, SqlColumn> _columns = new LinkedHashMap<>();

    private final Map<String, SqlIndex> _indexes = new LinkedHashMap<>();

    private final Map<String, SqlTab> _tabs = new LinkedHashMap<>();

    private final Map<String, SqlRow> _rows = new LinkedHashMap<>();

    private final Map<String, SqlRoutine> _routines = new LinkedHashMap<>();

    private final Map<SqlColumn, SqlTable> _references = new LinkedHashMap<>();

    private final Map<String, SqlTable> _extensions = new LinkedHashMap<>();

    private int _discriminatorValue;
    // </editor-fold>

    public SqlTable(SqlReader reader) {
        _reader = reader;
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    /**
     * @return the table reader
     */
    public SqlReader getReader() {
        return _reader;
    }

    /**
     * @return the default label
     */
    public String getDefaultLabel() {
        return _defaultLabel;
    }

    /**
     * @param defaultLabel the default label to set
     */
    void setDefaultLabel(String defaultLabel) {
        _defaultLabel = defaultLabel;
    }

    /**
     * @return the default collection label
     */
    public String getDefaultCollectionLabel() {
        return _defaultCollectionLabel;
    }

    /**
     * @param defaultCollectionLabel the default collection label to set
     */
    void setDefaultCollectionLabel(String defaultCollectionLabel) {
        _defaultCollectionLabel = defaultCollectionLabel;
    }

    /**
     * @return the enumerable indicator
     */
    public boolean isEnumerable() {
        return _enumerable;
    }

    /**
     * @param enumerable the enumerable indicator to set
     */
    void setEnumerable(boolean enumerable) {
        _enumerable = enumerable;
    }

    /**
     * @return the enumerable indicator
     */
    public boolean isEnumeration() {
        return _enumerable && _businessKey != null && _primaryKey != null && _primaryKey.getType().equals("integer");
    }

    /**
     * @return the enumerable indicator
     */
    public boolean isUpdatableEnumeration() {
        return isEnumeration() && hasOrdinaryColumns();
    }

    private boolean hasOrdinaryColumns() {
        for (SqlColumn column : getColumns()) {
            if (column.isOrdinary()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the independent indicator
     */
    public boolean isIndependent() {
        return _independent;
    }

    /**
     * @param independent the independent indicator to set
     */
    void setIndependent(boolean independent) {
        _independent = independent;
    }

    /**
     * @return the loaded indicator
     */
    public boolean isLoaded() {
        return _loaded;
    }

    /**
     * @param loaded the loaded indicator to set
     */
    void setLoaded(boolean loaded) {
        _loaded = loaded;
    }

    /**
     * @return the resource type
     */
    public String getResourceType() {
        return _resourceType;
    }

    /**
     * @param resourceType the resource type to set
     */
    void setResourceType(String resourceType) {
        _resourceType = resourceType;
    }

    public boolean isConfigurationTable() {
        return _resourceType != null && _resourceType.equalsIgnoreCase("configuration");
    }

    public boolean isOperationTable() {
        return _resourceType != null && _resourceType.equalsIgnoreCase("operation");
    }

    public boolean isLoadable() {
        if (_businessKey != null && _primaryKey != null) {
            return _reader.getTablesLoadMap().containsKey(getName())
                || (isConfigurationTable() && _reader.isLoadConfigurationTables())
                || (isOperationTable() && _reader.isLoadOperationTables());
        }
        return false;
    }

    /**
     * @return the base table
     */
    public SqlTable getBaseTable() {
        return _baseTable;
    }

    /**
     * @param baseTable the base table to set
     */
    void setBaseTable(SqlTable baseTable) {
        _baseTable = baseTable;
    }

    /**
     * @return the primary key column
     */
    public SqlColumn getPrimaryKey() {
        return _primaryKey;
    }

    /**
     * @param primaryKey the primary key column to set
     */
    void setPrimaryKey(SqlColumn primaryKey) {
        _primaryKey = primaryKey;
    }

    /**
     * @return the business key column
     */
    public SqlColumn getBusinessKey() {
        return _businessKey;
    }

    /**
     * @param businessKey the business key column to set
     */
    void setBusinessKey(SqlColumn businessKey) {
        _businessKey = businessKey;
    }

    /**
     * @return the version column
     */
    public SqlColumn getVersion() {
        for (SqlColumn column : _columns.values()) {
            if (column.isVersion()) {
                return column;
            }
        }
        return null;
    }

    private Map<String, SqlColumn> _settledColumns;

    /**
     * @return the columns map
     */
    public Map<String, SqlColumn> getColumnsMap() {
        return _settledColumns == null ? _columns : _settledColumns;
    }

    /**
     * @return the columns list
     */
    public Collection<SqlColumn> getColumns() {
        return _settledColumns == null ? _columns.values() : _settledColumns.values();
    }

    /**
     * @return the ordinary columns list
     */
    public Collection<SqlColumn> getOrdinaryColumns() {
        List<SqlColumn> list = new ArrayList<>();
        for (SqlColumn column : getColumns()) {
            if (column.isOrdinary()) {
                list.add(column);
            }
        }
        return list;
    }

    void settleColumns() {
        List<String> names = getSuperColumnsNamesList();
        String text;
        if (names == null || names.isEmpty()) {
            _settledColumns = _columns;
        } else {
            _settledColumns = new LinkedHashMap<>();
            for (SqlColumn column : _columns.values()) {
                String name = column.getName();
                if (!names.contains(name)) {
                    if (_settledColumns.containsKey(name)) {
                        text = "column " + name + " already settled in table " + getQualifiedName();
                        logger.error(SqlUtil.highlight(text));
                    } else {
                        _settledColumns.put(name, column);
                    }
                }
            }
        }
    }

    private List<String> getSuperColumnsNamesList() {
        List<String> list = new ArrayList<>();
        SqlTable superTable = getSuperTable();
        if (superTable != null) {
            list.addAll(superTable.getSuperColumnsNamesList());
            Collection<SqlColumn> columns = superTable.getColumns();
            for (SqlColumn col : columns) {
                list.add(col.getName());
            }
        }
        return list;
    }

    public SqlColumn getSqlColumn(String name) {
        for (SqlColumn sqlColumn : getColumns()) {
            if (sqlColumn.getName().equals(name)) {
                return sqlColumn;
            }
        }
        return null;
    }

    /**
     * @return the indexes map
     */
    public Map<String, SqlIndex> getIndexesMap() {
        return _indexes;
    }

    /**
     * @return the indexes list
     */
    public Collection<SqlIndex> getIndexes() {
        return _indexes.values();
    }

    /**
     * @return the rows map
     */
    public Map<String, SqlRow> getRowsMap() {
        return _rows;
    }

    /**
     * @return the rows list
     */
    public Collection<SqlRow> getRows() {
        return _rows.values();
    }

    /**
     * @return the tabs map
     */
    public Map<String, SqlTab> getTabsMap() {
        return _tabs;
    }

    /**
     * @return the tabs list
     */
    public Collection<SqlTab> getTabs() {
        return _tabs.values();
    }

    /**
     * @return the routines map
     */
    public Map<String, SqlRoutine> getRoutinesMap() {
        return _routines;
    }

    /**
     * @return the routines list
     */
    public Collection<SqlRoutine> getRoutines() {
        return _routines.values();
    }

    /**
     * @return the references map
     */
    public Map<SqlColumn, SqlTable> getReferences() {
        return _references;
    }

    /**
     * @return the extensions map
     */
    public Map<String, SqlTable> getExtensions() {
        return _extensions;
    }

    public boolean isRoot() {
        return !_extensions.isEmpty();
    }

    /**
     * @return the discriminator value
     */
    public int getDiscriminatorValue() {
        return _discriminatorValue;
    }

    /**
     * @param discriminatorValue the discriminator value to set
     */
    private void setDiscriminatorValue(int discriminatorValue) {
        _discriminatorValue = discriminatorValue;
    }

    public SqlTable getRootTable() {
        SqlTable rootTable = rootTable();
        return rootTable.equals(this) ? null : rootTable;
    }

    private SqlTable rootTable() {
        SqlTable superTable = getSuperTable();
        return superTable == null ? this : superTable.rootTable();
    }

    public SqlTable getSuperTable() {
        return _primaryKey == null ? null : _primaryKey.getForeignTable();
    }

    public String getQualifiedName() {
        return getName();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="add">
//  private int columns;
//
    void add(SqlColumn column) {
//      column.setPosition(++columns);
        String name = column.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name column will not added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_columns.containsKey(name)) {
            text = "column " + name + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _columns.put(name, column);
        }
    }

    private int indexes;

    void add(SqlIndex index) {
        index.setPosition(++indexes);
        String name = index.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name index will not added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_indexes.containsKey(name)) {
            text = "index " + name + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _indexes.put(name, index);
        }
    }

    private int rows;

    void add(SqlRow row) {
        row.setPosition(++rows);
        String name = row.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name row will not added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_rows.containsKey(name)) {
            text = "row " + name + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _rows.put(name, row);
        }
    }

    private int tabs;

    void add(SqlTab tab) {
        tab.setPosition(++tabs);
        String name = tab.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name tab will not added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_tabs.containsKey(name)) {
            text = "tab " + name + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _tabs.put(name, tab);
        }
    }

    private int routines;

    void add(SqlRoutine routine) {
        routine.setPosition(++routines);
        String name = routine.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name routine will not added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_routines.containsKey(name)) {
            text = "routine " + name + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _routines.put(name, routine);
        }
    }

    private int extensions;

    void add(SqlTable extension) {
//      extension.setDiscriminatorValue(++extensions);
        int discriminatorValue = ++extensions;
        extension.setDiscriminatorValue(discriminatorValue);
        String name = extension.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name table extension will not added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_extensions.containsKey(name)) {
            text = "table extension " + name + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _extensions.put(name, extension);
        }
    }
    // </editor-fold>

}
