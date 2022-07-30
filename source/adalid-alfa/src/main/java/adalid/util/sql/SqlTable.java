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

import adalid.commons.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private boolean _insertable;

    private boolean _updatable;

    private boolean _deletable;

    private boolean _independent;

    private boolean _loaded;

    private boolean _primaryKeyCollision, _businessKeyCollision, _versionPropertyCollision;

    private String _resourceType;

    private SqlTable _baseTable;

    private SqlColumn _primaryKey;

    private SqlColumn _businessKey;

    private Map<String, SqlColumn> _settledColumns;

    private final Map<String, SqlColumn> _columns = new LinkedHashMap<>();

    private final Map<String, SqlIndex> _indexes = new LinkedHashMap<>();

    private final Map<String, SqlTab> _tabs = new LinkedHashMap<>();

    private final Map<String, SqlRow> _rows = new LinkedHashMap<>();

    private final Map<String, SqlRow> _rowsByPK = new LinkedHashMap<>();

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
        return isEnumeration() && isUpdatable() && hasOrdinaryColumns();
    }

    /*
    private boolean isUpdatable() {
        Project master = _reader.getMasterProject();
        if (master != null) {
            String key = StrUtils.getCamelCase('_' + getName());
            Entity entity = master.getEntitiesMap().get(key);
            return entity == null || entity.isUpdateEnabled();
        }
        return true;
    }
     */
    /**
     * @return the insertable indicator
     */
    public boolean isInsertable() {
        return _insertable;
    }

    /**
     * @param insertable the insertable indicator to set
     */
    void setInsertable(boolean insertable) {
        _insertable = insertable;
    }

    /**
     * @return the updatable indicator
     */
    public boolean isUpdatable() {
        return _updatable;
    }

    /**
     * @param updatable the updatable indicator to set
     */
    void setUpdatable(boolean updatable) {
        _updatable = updatable;
    }

    /**
     * @return the deletable indicator
     */
    public boolean isDeletable() {
        return _deletable;
    }

    /**
     * @param deletable the deletable indicator to set
     */
    void setDeletable(boolean deletable) {
        _deletable = deletable;
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

    public boolean isCatalogTable() {
        return _reader.getCatalogTablesMap().containsKey(getName());
    }

    public boolean isLoadable() {
        /*
        if (_businessKey != null && _primaryKey != null) {
            return _reader.getTablesLoadMap().containsKey(getName())
                || (isConfigurationTable() && _reader.isLoadConfigurationTables())
                || (isOperationTable() && _reader.isLoadOperationTables());
        }
        return false;
         */
        return _businessKey != null && _primaryKey != null && _reader.getTablesLoadMap().containsKey(getName());
    }

    public String getFeatures() {
        List<String> features = new ArrayList<>();
        if (isConfigurationTable()) {
            features.add("configuration-table");
        } else if (isOperationTable()) {
            features.add("operation-table");
        } else if (_resourceType != null) {
            features.add(_resourceType);
        } else {
            features.add("typeless");
        }
        if (isUpdatableEnumeration()) {
            features.add("updatable-enumeration");
        } else if (isEnumeration()) {
            features.add("enumeration");
        } else if (isEnumerable()) {
            features.add("enumerable");
        }
        if (isIndependent()) {
            features.add("independent");
        }
        if (isLoaded()) {
            features.add("loaded");
        } else if (isLoadable()) {
            features.add("loadable");
        }
        return "(" + StringUtils.join(features, ", ") + ")";
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
        return getVersionProperty();
    }

    /**
     * @return the version property column
     */
    public SqlColumn getVersionProperty() {
        for (SqlColumn column : _columns.values()) {
            if (column.isVersion()) {
                return column;
            }
        }
        return null;
    }

    /**
     * @return the discriminator property column
     */
    public SqlColumn getDiscriminatorProperty() {
        for (SqlColumn column : _columns.values()) {
            if (column.isDiscriminator()) {
                return column;
            }
        }
        return null;
    }

    /**
     * @return the name property column
     */
    public SqlColumn getNameProperty() {
        for (SqlColumn column : _columns.values()) {
            if (column.isName()) {
                return column;
            }
        }
        return null;
    }

    /**
     * @return the description property column
     */
    public SqlColumn getDescriptionProperty() {
        for (SqlColumn column : _columns.values()) {
            if (column.isDescription()) {
                return column;
            }
        }
        return null;
    }

    /**
     * @return the columns map
     */
    public Map<String, SqlColumn> getColumnsMap() {
        return getColumnsMap(true);
    }

    /**
     * @param settled
     * @return the columns map
     */
    private Map<String, SqlColumn> getColumnsMap(boolean settled) {
        return settled ? getSettledColumnsMap() : getUnsettledColumnsMap();
    }

    /**
     * @return the columns list
     */
    public Collection<SqlColumn> getColumns() {
        return getColumns(true);
    }

    /**
     * @param settled
     * @return the columns list
     */
    private Collection<SqlColumn> getColumns(boolean settled) {
        return settled ? getSettledColumns() : getUnsettledColumns();
    }

    /**
     * @return the settled columns map
     */
    private Map<String, SqlColumn> getSettledColumnsMap() {
        return _settledColumns == null ? _columns : _settledColumns;
    }

    /**
     * @return the settled columns list
     */
    private Collection<SqlColumn> getSettledColumns() {
        return _settledColumns == null ? _columns.values() : _settledColumns.values();
    }

    /**
     * @return the unsettled columns map
     */
    private Map<String, SqlColumn> getUnsettledColumnsMap() {
        return _columns;
    }

    /**
     * @return the unsettled columns list
     */
    private Collection<SqlColumn> getUnsettledColumns() {
        return _columns.values();
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
                if (_settledColumns.containsKey(name)) {
                    text = "column " + name + " already settled in table " + getQualifiedName();
                    logger.error(SqlUtil.highlight(text));
                } else {
                    _settledColumns.put(name, column);
                    if (names.contains(name) && warnSameName(column)) {
                        text = "column " + name + " is also present in a super-table of " + getQualifiedName();
                        logger.warn(SqlUtil.highlight(text));
                    }
                }
            }
        }
        checkColumnNameCollisions();
    }

    private void checkColumnNameCollisions() {
        final String pattern = "column {0}.{1} has the same name as the default {2} of entity {3}";
        final String tabname = getQualifiedName();
        final String nttname = getCapitalizedJavaName();
        String colname, text;
        if (_primaryKey == null) {
            // a table w/o primary key would be later rejected by IsFairTable predicate
            text = "table " + getQualifiedName() + " does not have a primary key";
            logger.warn(SqlUtil.highlight(text));
            if (isEnumeration()) {
                // Toda enumeración debe tener una clave primaria de tipo IntegerProperty.
                // Si no está definida en la base de datos, se define automáticamente con el nombre "numero".
                colname = "numero";
            } else {
                // Toda entidad estándar debe tener una clave primaria de tipo LongProperty.
                // Si no está definida en la base de datos, se define automáticamente con el nombre "id".
                colname = "id";
            }
            text = StrUtils.getStringParametrizado(pattern, tabname, colname, "primary key", nttname);
            _primaryKeyCollision = columnNameCollision(colname, text);
        }
        /**/
        if (_businessKey == null) {
            if (isEnumeration()) {
                // Toda enumeración debe tener una clave de negocio de tipo String.
                // Si no está definida en la base de datos, se define automáticamente con el nombre "codigo".
                colname = "codigo";
                text = StrUtils.getStringParametrizado(pattern, tabname, colname, "business key", nttname);
                _businessKeyCollision = columnNameCollision(colname, text);
            }
        }
        SqlColumn versionProperty = getVersionProperty();
        if (versionProperty == null) {
            if (isUpdatableEnumeration() || !isEnumeration()) {
                // Toda entidad estándar debe tener una propiedad versión de tipo LongProperty.
                // Si no está definida en la base de datos, se define automáticamente con el nombre "version".
                colname = "version";
                text = StrUtils.getStringParametrizado(pattern, tabname, colname, "version property", nttname);
                _versionPropertyCollision = columnNameCollision(colname, text);
            }
        }
    }

    private boolean columnNameCollision(String name, String text) {
        SqlColumn column = _columns.get(name);
        if (column != null) {
            column.setCollision(true);
            logger.error(SqlUtil.highlight(text));
            return true;
        }
        return false;
    }

    public boolean isPrimaryKeyCollision() {
        return _primaryKeyCollision;
    }

    public boolean isBusinessKeyCollision() {
        return _businessKeyCollision;
    }

    public boolean isVersionPropertyCollision() {
        return _versionPropertyCollision;
    }

    private boolean warnSameName(SqlColumn column) {
        return !column.isPrimary();
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
     * @return the rows by primary key map
     */
    public Map<String, SqlRow> getRowsByPrimaryKeyMap() {
        return _rowsByPK;
    }

    /**
     * @return the rows by primary key list
     */
    public Set<String> getPrimaryKeys() {
        return _rowsByPK.keySet();
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
        SqlTable superTable = _primaryKey == null ? null : _primaryKey.getForeignTable();
        return superTable == this ? null : superTable;
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
            text = "a null name column will not be added to table " + getQualifiedName();
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
            text = "a null name index will not be added to table " + getQualifiedName();
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
            text = "a null name row will not be added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_rows.containsKey(name)) {
            text = "row " + name + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _rows.put(name, row);
        }
//      String pkey = row.getPrimaryKey().getValue();
        String pkey = StringUtils.leftPad(row.getPrimaryKey().getValue(), 19, '0');
        if (pkey == null) {
            text = "a null primary-key row will not be added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_rowsByPK.containsKey(pkey)) {
            text = "row " + pkey + " already added to table " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _rowsByPK.put(pkey, row);
        }
    }

    private int tabs;

    void add(SqlTab tab) {
        tab.setPosition(++tabs);
        String name = tab.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name tab will not be added to table " + getQualifiedName();
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
            text = "a null name routine will not be added to table " + getQualifiedName();
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
            text = "a null name table extension will not be added to table " + getQualifiedName();
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
