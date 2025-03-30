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
import adalid.commons.velocity.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlMerger extends SqlUtil {

    private static final Logger logger = Logger.getLogger(SqlMerger.class);

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private boolean _infoLogging = true;

    private boolean _detailLogging = _infoLogging;

    private boolean _testingPhase;

    private String _oldHost;

    private String _oldPort;

    private String _oldUser;

    private String _oldPassword;

    private String _oldDatabase;

    private String _oldSchema;

    private String _projectAlias;

    private String _oldDataFolder;

    private String _selectTemplatesPath;

    private SqlReader _reader1, _reader2;
//
//  private Project _masterProject;

    private Map<String, String> _tablesLoadMap = new LinkedHashMap<>();

    private Map<String, String> _catalogTablesMap = new LinkedHashMap<>();

    private final Set<String> _tableNames = new TreeSet<>();

    private final Set<String> _excludableTableNames = new TreeSet<>();

    private final Set<String> _currentKeyTableNames = new TreeSet<>();

    private final Set<String> _mutableKeyTableNames = new TreeSet<>();

    private final Set<String> _addedKeyTableNames = new TreeSet<>();

    private final Set<String> _updatedKeyTableNames = new TreeSet<>();

    private final Set<String> _updatedRowTableNames = new TreeSet<>();

    private final Set<String> _deletedRowTableNames = new TreeSet<>();

    private final List<SqlTable> _addedTables = new ArrayList<>();

    private final List<SqlTable> _droppedTables = new ArrayList<>();

    private final Map<String, SqlColumn> _newColumns = new LinkedHashMap<>();

    private final Map<String, SqlColumn> _oldColumns = new LinkedHashMap<>();

    private final List<SqlColumnPair> _oddColumns = new ArrayList<>();

    private final List<SqlColumn> _addedColumns = new ArrayList<>();

    private final List<SqlColumn> _droppedColumns = new ArrayList<>();

    private final List<SqlConstraintIndex> _addedConstraintIndexes = new ArrayList<>();

    private final List<SqlConstraintIndex> _droppedConstraintIndexes = new ArrayList<>();

    private final List<SqlConstraintIndex> _addedPrimaryKeyConstraints = new ArrayList<>();

    private final List<SqlConstraintIndex> _droppedPrimaryKeyConstraints = new ArrayList<>();

    private final List<SqlRow> _addedRows = new ArrayList<>();

    private final List<SqlRow> _deletedRows = new ArrayList<>();

    private final List<SqlRowValue> _addedValues = new ArrayList<>();

    private final Map<String, SqlRowPair> _updatedRows = new LinkedHashMap<>();

    private final List<SqlTableWrapper> _sharedTables = new ArrayList<>();

    private final Map<String, Set<String>> _messages = new LinkedHashMap<>();

    private final Map<String, Set<String>> _warnings = new LinkedHashMap<>();
    // </editor-fold>

    public SqlMerger() {
        super();
        init();
    }

    public SqlMerger(String[] args) {
        super(args);
        init();
    }

    private void init() {
        _initialised = _initialised && oldSchema(_argIndex++, _args);
        _initialised = _initialised && oldHost(_argIndex++, _args);
        _initialised = _initialised && oldPort(_argIndex++, _args);
        _initialised = _initialised && oldUser(_argIndex++, _args);
        _initialised = _initialised && oldPassword(_argIndex++, _args);
        _initialised = _initialised && oldDatabase(_argIndex++, _args);
        _initialised = _initialised && projectAlias(_argIndex++, _args);
    }

    // <editor-fold defaultstate="collapsed" desc="args">
    private boolean oldHost(int i, String[] args) {
        _oldHost = StringUtils.defaultIfBlank(arg(i, args), _host);
        if (StringUtils.isNotBlank(_oldHost)) {
            logValidArgument(old() + " host", _oldHost);
            return true;
        }
        logInvalidArgument(old() + " host", _oldHost);
        logSyntaxError();
        return false;
    }

    private boolean oldPort(int i, String[] args) {
        _oldPort = StringUtils.defaultIfBlank(arg(i, args), _port);
        if (StringUtils.isNotBlank(_oldPort)) {
            logValidArgument(old() + " port", _oldPort);
            return true;
        }
        logInvalidArgument(old() + " port", _oldPort);
        logSyntaxError();
        return false;
    }

    private boolean oldUser(int i, String[] args) {
        _oldUser = StringUtils.defaultIfBlank(arg(i, args), _user);
        if (StringUtils.isNotBlank(_oldUser)) {
            logValidArgument(old() + " user", _oldUser);
            return true;
        }
        logInvalidArgument(old() + " user", _oldUser);
        logSyntaxError();
        return false;
    }

    private boolean oldPassword(int i, String[] args) {
        _oldPassword = StringUtils.defaultIfBlank(arg(i, args), _password);
        if (StringUtils.isNotBlank(_oldPassword)) {
//          logValidArgument(old() + " password", _oldPassword);
            return true;
        }
        logInvalidArgument(old() + " password", _oldPassword);
        logSyntaxError();
        return false;
    }

    private boolean oldDatabase(int i, String[] args) {
        _oldDatabase = StringUtils.defaultIfBlank(arg(i, args), _database);
        if (StringUtils.isNotBlank(_oldDatabase)) {
            logValidArgument(old() + " database", _oldDatabase);
            return true;
        }
        logInvalidArgument(old() + " database", _oldDatabase);
        logSyntaxError();
        return false;
    }

    private boolean oldSchema(int i, String[] args) {
        _oldSchema = arg(i, args);
        if (StringUtils.isNotBlank(_oldSchema)) {
            logValidArgument(old() + " schema", _oldSchema);
            return true;
        }
        logInvalidArgument(old() + " schema", _oldSchema);
        logSyntaxError();
        return false;
    }

    private boolean projectAlias(int i, String[] args) {
        String alias = arg(i, args);
        if (StringUtils.isBlank(alias)) {
            return true; // optional argument
        }
        setProjectAlias(alias);
        if (alias.equals(_projectAlias)) {
            logValidArgument("project alias", alias);
            return true;
        }
        logInvalidArgument("project alias", alias);
        logSyntaxError();
        return false;
    }

    protected String old() {
        return "old";
    }

    @Override
    protected String getSyntax() {
        return getSqlUtilSyntax() + ", old schema, [old host], [old port], [old user], [old password], [old database]";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    public boolean isInfoLoggingEnabled() {
        return _infoLogging;
    }

    public void enableInfoLogging() {
        _infoLogging = true;
    }

    public void disableInfoLogging() {
        _infoLogging = false;
    }

    public boolean isDetailLoggingEnabled() {
        return _infoLogging && _detailLogging;
    }

    public void enableDetailLogging() {
        _detailLogging = true;
    }

    public void disableDetailLogging() {
        _detailLogging = false;
    }

    public boolean isThereChanceOfRenamedTables() {
        return !_addedTables.isEmpty() && !_droppedTables.isEmpty();
    }

    public boolean isThereChanceOfRenamedColumns() {
        for (SqlTableWrapper sharedTable : _sharedTables) {
            if (sharedTable.hasAddedColumns() && sharedTable.hasDroppedColumns()) {
                return true;
            }
        }
        return false;
    }

    /**
     * incrementally upgradeable indicator.
     *
     * A database can be safely incrementally upgraded if there are no:
     * <ul>
     * <li>dropped tables</li>
     * <li>dropped columns</li>
     * <li>tables with new not-nullable columns with no default value</li>
     * <li>tables with matching columns now not-nullable with no default value</li>
     * <li>tables with matching columns with incompatible data types</li>
     * <li>tables with matching columns with shorter length</li>
     * <li>tables with matching columns with fewer integer digits</li>
     * <li>tables with matching columns with new foreign-key constraint</li>
     * <li>tables with matching columns with different foreign-key constraint</li>
     * <li>rows in referenced enumeration tables with matching primary-key but different business-key</li>
     * <li>rows deleted in referenced enumeration tables</li>
     * </ul>
     *
     * @return true, if the database can be safely incrementally upgraded; false otherwise
     */
    public boolean isIncrementallyUpgradeable() {
        return _droppedTables.isEmpty()
            && _droppedColumns.isEmpty()
            && _oddColumns.isEmpty()
            && _updatedKeyTableNames.isEmpty()
            && _deletedRows.isEmpty();
    }

    public boolean isTestingPhase() {
        return _testingPhase;
    }

    public String getNewSchema() {
        return _schema;
    }

    public String getOldSchema() {
        return _oldSchema;
    }

    public String getOldHost() {
        return _oldHost;
    }

    public String getOldPort() {
        return _oldPort;
    }

    public String getOldUser() {
        return _oldUser;
    }

    public String getOldPassword() {
        return _oldPassword;
    }

    public String getOldDatabase() {
        return _oldDatabase;
    }

    private static String PROJECT_ALIAS_PATTERN = "^[a-z][a-z0-9]*$"; // "^[a-z]\\w*$";

    public String getProjectAlias() {
        return _projectAlias == null ? defaultProjectAlias() : _projectAlias;
    }

    public void setProjectAlias(String alias) {
        if (StringUtils.isBlank(alias)) {
            logger.warn("null value for alias parameter; project alias remains " + getProjectAlias());
        } else if (!alias.matches(PROJECT_ALIAS_PATTERN)) {
            logger.warn(alias + " is an invalid project alias; project alias remains " + getProjectAlias());
        } else if (alias.equalsIgnoreCase("meta") || alias.equalsIgnoreCase("workspace")) {
            logger.warn(alias + " is a restricted project alias; project alias remains " + getProjectAlias());
        } else {
            _projectAlias = alias;
        }
    }

    private String defaultProjectAlias() {
        boolean oracle = StringUtils.equalsIgnoreCase(_dbms, "oracle");
        return (oracle ? _schema : _database).toLowerCase();
    }

    private boolean checkProjectAlias() {
        String alias = getProjectAlias();
        if (StringUtils.isBlank(alias)) {
            logger.error("invalid project alias; generation aborted");
            return false;
        } else if (!alias.matches(PROJECT_ALIAS_PATTERN)) {
            logger.error(alias + " is an invalid project alias; generation aborted");
            return false;
        } else if (alias.equalsIgnoreCase("meta") || alias.equalsIgnoreCase("workspace")) {
            logger.error(alias + " is a restricted project alias; generation aborted");
            return false;
        } else {
            return true;
        }
    }

    public String getOldDataFolder() {
        return _oldDataFolder == null ? defaultOldDataFolder() : _oldDataFolder;
    }

    public void setOldDataFolder(String folder) {
        if (StringUtils.isBlank(folder)) {
            logger.warn("null value for folder parameter; old data folder remains " + getOldDataFolder());
        } else {
            folder = folder.replace("\\", "/");
            if (folder.endsWith("/")) {
            } else {
                folder += "/";
            }
            _oldDataFolder = folder;
        }
    }

    private String defaultOldDataFolder() {
        String folder = FilUtils.getWorkspaceFolderPath();
        folder += FILE_SEPARATOR + getProjectAlias();
        folder += FILE_SEPARATOR + "source";
        folder += FILE_SEPARATOR + "management";
        folder += FILE_SEPARATOR + "backup";
        folder += FILE_SEPARATOR + _dbms;
        folder += FILE_SEPARATOR + _oldSchema;
        folder += FILE_SEPARATOR;
        folder = folder.replace("\\", "/");
        return folder;
    }

    public String getSelectTemplatesPath() {
        return _selectTemplatesPath;
    }

    public void setSelectTemplatesPath(String selectTemplatesPath) {
        _selectTemplatesPath = selectTemplatesPath;
    }

    public Set<String> getTableNames() {
        return _tableNames;
    }

    public Set<String> getExcludableTableNames() {
        return _excludableTableNames;
    }

    public Set<String> getCurrentKeyTableNames() {
        return _currentKeyTableNames;
    }

    public Set<String> getMutableKeyTableNames() {
        return _mutableKeyTableNames;
    }

    private boolean mutableKey(String tableName) {
        return _mutableKeyTableNames.contains(tableName);
    }

    public Set<String> getAddedKeyTableNames() {
        return _addedKeyTableNames;
    }

    public Set<String> getUpdatedKeyTableNames() {
        return _updatedKeyTableNames;
    }

    public Set<String> getUpdatedRowTableNames() {
        return _updatedRowTableNames;
    }

    public Set<String> getDeletedRowTableNames() {
        return _deletedRowTableNames;
    }

    public List<SqlTable> getAddedTables() {
        return _addedTables;
    }

    public List<SqlTable> getDroppedTables() {
        return _droppedTables;
    }

    public Map<String, SqlColumn> getNewColumns() {
        return _newColumns;
    }

    public Map<String, SqlColumn> getOldColumns() {
        return _oldColumns;
    }

    public List<SqlColumnPair> getOddColumns() {
        return _oddColumns;
    }

    public Set<String> getModifiedColumnNames() {
        return _newColumns.keySet();
    }

    public List<SqlColumn> getAddedColumns() {
        return _addedColumns;
    }

    public List<SqlColumn> getDroppedColumns() {
        return _droppedColumns;
    }

    public List<SqlConstraintIndex> getAddedConstraintIndexes() {
        return _addedConstraintIndexes;
    }

    public List<SqlConstraintIndex> getDroppedConstraintIndexes() {
        return _droppedConstraintIndexes;
    }

    public List<SqlConstraintIndex> getAddedPrimaryKeyConstraints() {
        return _addedPrimaryKeyConstraints;
    }

    public List<SqlConstraintIndex> getDroppedPrimaryKeyConstraints() {
        return _droppedPrimaryKeyConstraints;
    }

    public List<SqlRow> getAddedRows() {
        return _addedRows;
    }

    public List<SqlRow> getDeletedRows() {
        return _deletedRows;
    }

    public List<SqlRowValue> getAddedValues() {
        return _addedValues;
    }

    public Map<String, SqlRowPair> getUpdatedRows() {
        return _updatedRows;
    }

    public List<SqlTableWrapper> getSharedTables() {
        return _sharedTables;
    }

    public Map<String, Set<String>> getMessages() {
        return _messages;
    }

    public Map<String, Set<String>> getWarnings() {
        return _warnings;
    }
    // </editor-fold>

    public synchronized void clear() {
        logger.info("clear");
        _tableNames.clear();
        _excludableTableNames.clear();
        _currentKeyTableNames.clear();
        _mutableKeyTableNames.clear();
        _addedKeyTableNames.clear();
        _updatedKeyTableNames.clear();
        _updatedRowTableNames.clear();
        _deletedRowTableNames.clear();
        _addedTables.clear();
        _droppedTables.clear();
        _newColumns.clear();
        _oldColumns.clear();
        _oddColumns.clear();
        _addedColumns.clear();
        _droppedColumns.clear();
        _addedConstraintIndexes.clear();
        _droppedConstraintIndexes.clear();
        _addedPrimaryKeyConstraints.clear();
        _droppedPrimaryKeyConstraints.clear();
        _addedRows.clear();
        _deletedRows.clear();
        _addedValues.clear();
        _updatedRows.clear();
    }

    public synchronized boolean merge() {
        return merge(false);
    }

    public synchronized boolean merge(boolean testing) {
        logger.info("merge");
        boolean ok = checkProjectAlias();
        if (ok) {
            beforeMerging();
            _testingPhase = testing;
            boolean merge = _initialised;
            merge = merge && read1();
            merge = merge && read2();
            merge = merge && mergeTables();
            merge = merge && write();
            stats(merge);
            return merge;
        } else {
            return false;
        }
    }

    private void beforeMerging() {
        /*
        if (_masterProject != null) {
            if (_catalogTablesMap == null || _catalogTablesMap.isEmpty()) {
                _catalogTablesMap = _masterProject.getCatalogTablesMap();
            }
        }
        /**/
    }

    protected boolean read1() {
        return read1(false);
    }

    protected boolean read1(boolean simple) {
        boolean read = initReader1();
        if (read) {
            if (simple) {
                return _reader1.read(true);
            }
            SqlReader.SqlAid aid = _reader1.getSqlAid();
            if (aid != null) {
                if (_reader1.connect()) {
                    try {
                        aid.createDefaults();
                        read = _reader1.read(false);
                        aid.dropDefaults();
                        return read;
                    } catch (SQLException ex) {
                    } finally {
                        _reader1.close();
                    }
                }
            }
        }
        return false;
    }

    private boolean initReader1() {
        String[] args = reader1_args();
        _reader1 = new SqlReader(args);
        _reader1.setSelectTemplatesPath(StringUtils.defaultIfBlank(_selectTemplatesPath, "templates/meta/sql"));
//      _reader1.setMasterProject(_masterProject);
        _reader1.getTablesExcludeSet().addAll(_excludableTableNames);
        _reader1.setTablesLoadMap(_tablesLoadMap);
        _reader1.setCatalogTablesMap(_catalogTablesMap);
        return _reader1.isInitialised();
    }

    protected String[] reader1_args() {
        return new String[]{_dbms, _host, _port, _user, _password, _database, _schema};
    }

    private boolean read2() {
        boolean read = initReader2();
        return read && _reader2.read(true);
    }

    private boolean initReader2() {
        String[] args = reader2_args();
        _reader2 = new SqlReader(args);
        _reader2.setSelectTemplatesPath(StringUtils.defaultIfBlank(_selectTemplatesPath, "templates/meta/sql"));
//      _reader2.setMasterProject(_masterProject);
        _reader2.getTablesExcludeSet().addAll(_excludableTableNames);
        _reader2.setTablesLoadMap(_tablesLoadMap);
        _reader2.setCatalogTablesMap(_catalogTablesMap);
        return _reader2.isInitialised();
    }

    protected String[] reader2_args() {
        return new String[]{_dbms, _oldHost, _oldPort, _oldUser, _oldPassword, _oldDatabase, _oldSchema};
    }
//
//  /**
//   * @return the master project
//   */
//  public Project getMasterProject() {
//      return _masterProject;
//  }
//
//  /**
//   * @param project the master project to set
//   */
//  public void setMasterProject(Project project) {
//      _masterProject = project;
//  }

    /**
     * @return the tables load set
     */
    public Set<String> getTablesLoadSet() {
        return _tablesLoadMap.keySet();
    }

    /**
     * @return the tables load map
     */
    public Map<String, String> getTablesLoadMap() {
        return _tablesLoadMap;
    }

    /**
     * @param map the tables load map to set
     */
    public void setTablesLoadMap(Map<String, String> map) {
        _tablesLoadMap = map;
    }

    /**
     * @return the catalog tables set
     */
    public Set<String> getCatalogTablesSet() {
        return _catalogTablesMap.keySet();
    }

    /**
     * @return the catalog tables map
     */
    public Map<String, String> getCatalogTablesMap() {
        return _catalogTablesMap;
    }

    /**
     * @param map the catalog tables map to set
     */
    public void setCatalogTablesMap(Map<String, String> map) {
        _catalogTablesMap = map;
    }

    /*
    private Map<String, String> tablesLoadMap() {
        return _tablesLoadMap
            .entrySet()
            .stream()
            .filter(map -> !_catalogTablesMap.containsKey(map.getKey()))
            .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

    }

    /**/
    private boolean mergeTables() {
        Map<String, SqlTable> map1 = _reader1.getTablesMap();
        Map<String, SqlTable> map2 = _reader2.getTablesMap();
        if (map1.isEmpty()) {
            logger.warn("schema " + _reader1.getSchema() + " contains no tables");
        } else {
            logger.info("schema " + _reader1.getSchema() + " contains " + map1.size() + " tables");
        }
        if (map2.isEmpty()) {
            logger.warn("schema " + _reader2.getSchema() + " contains no tables");
        } else {
            logger.info("schema " + _reader2.getSchema() + " contains " + map2.size() + " tables");
        }
        if (map1.isEmpty() && map2.isEmpty()) {
            logger.error("none of the schemas contains tables");
            return false;
        } else if (map1.isEmpty() || map2.isEmpty()) {
            logger.error("one of the schemas contains no tables");
            return false;
        }
        _tableNames.addAll(map1.keySet());
        _tableNames.addAll(map2.keySet());
        SqlTable table1, table2;
        Set<String> catalog = getCatalogTablesSet();
        for (String key : _tableNames) {
            if (catalog.contains(key)) {
                logDetails(key, key + " is a catalog table and will be fully regenerated");
            } else if (map1.containsKey(key) && map2.containsKey(key)) {
                table1 = map1.get(key);
                table2 = map2.get(key);
                table1.setOtherTable(table2);
                table2.setOtherTable(table1);
                mergeColumns(table1, table2);
                mergePrimaryKeys(table1, table2);
                mergeConstraintIndexes(table1, table2);
            } else if (map1.containsKey(key)) {
                table1 = map1.get(key);
                createTable(table1);
            } else {
                table2 = map2.get(key);
                dropTable(table2);
            }
        }
        _addedConstraintIndexes.addAll(0, _addedPrimaryKeyConstraints); // primary keys must be added before the other constraints
        _droppedConstraintIndexes.addAll(_droppedPrimaryKeyConstraints); // primary keys must be dropped after the other constraints
        if (isThereChanceOfRenamedTables()) {
            logWarning("*", "there may be renamed tables; check CREATE and DROP TABLE statements");
        }
        if (isThereChanceOfRenamedColumns()) {
            logWarning("*", "there may be renamed columns; check ADD and DROP COLUMN statements");
        }
        return true;
    }

    private static final String DATA_CONV_REQD = "; data conversion might be required";

    private boolean mergeColumns(SqlTable table1, SqlTable table2) {
        SqlTableWrapper sharedTable = newSharedTable(table1);
        Map<String, SqlColumn> map1 = table1.getColumnsMap();
        Map<String, SqlColumn> map2 = table2.getColumnsMap();
        Set<String> keySet = new TreeSet<>();
        keySet.addAll(map1.keySet());
        keySet.addAll(map2.keySet());
        SqlColumn column1, column2;
        SqlColumnPair columnPair;
        String tableName = table1.getName();
        boolean upgradeable = true;
        boolean compareRows = !mutableKey(tableName) && table1.isLoaded() && table2.isLoaded();
        String text;
        String name1;
        String type1, type2;
        String sqlx1, sqlx2;
        String xref1, xref2, xref3;
        String default1;
        for (String key : keySet) {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                column1 = map1.get(key);
                column2 = map2.get(key);
                columnPair = sharedTable.addColumnPair(column1, column2);
                if (columnPair.equals()) {
                    continue;
                }
                modifyColumn(column1, column2);
                sharedTable.setSlightlyModifiedColumns(true);
                if (columnPair.equates()) {
                    continue;
                }
                sharedTable.setHeavilyModifiedColumns(true);
                name1 = tableName + "." + column1.getName();
                type1 = column1.getType();
                type2 = column2.getType();
                sqlx1 = column1.getSqlType();
                sqlx2 = column2.getSqlType();
                xref1 = column1.getForeignTableName();
                xref2 = column2.getForeignTableName();
                xref3 = xref2 == null ? "nothing" : xref2;
                if (upgradeable) {
                    _oddColumns.add(columnPair);
                    if (columnPair.casts()) {
                        text = name1 + " is now a non-nullable " + sqlx1 + " column with no default value" + DATA_CONV_REQD;
                    } else if (type1.equals(type2) && columnPair.refers()) {
                        text = name1 + " is now " + sqlx1 + "; it previously was " + sqlx2 + DATA_CONV_REQD;
                    } else if (type1.equals(type2)) {
                        text = name1 + " now references " + xref1 + "; it previously referenced " + xref3 + DATA_CONV_REQD;
                    } else {
                        text = name1 + " is now " + sqlx1 + "; it previously was " + sqlx2 + DATA_CONV_REQD;
                    }
                    logWarning(tableName, text);
                }
            } else if (map1.containsKey(key)) {
                column1 = map1.get(key);
                addColumn(column1);
                sharedTable.setAddedColumns(true);
                name1 = tableName + "." + column1.getName();
                sqlx1 = column1.getSqlType();
                boolean nullable1 = column1.isNullable();
                default1 = column1.getSqlDefaultValue();
                if (!nullable1 && default1 == null) {
                    columnPair = sharedTable.addColumnPair(column1, null);
                    if (upgradeable) {
                        _oddColumns.add(columnPair);
                        text = name1 + " is a new non-nullable " + sqlx1 + " column with no default value";
                        logWarning(tableName, text);
                    }
                }
            } else {
                column2 = map2.get(key);
                dropColumn(column2);
                sharedTable.setDroppedColumns(true);
            }
        }
        if (sharedTable.hasAddedColumns() && sharedTable.hasDroppedColumns()) {
            text = tableName + " may have renamed columns; check its ADD and DROP COLUMN statements";
//          logMessage(tableName, text, true);
            logWarning(tableName, text);
        }
        if (compareRows) {
            compareRows(table1, table2, keySet);
        }
        return true;
    }

    private void mergePrimaryKeys(SqlTable table1, SqlTable table2) {
        SqlColumn pk1 = table1.getPrimaryKey();
        SqlColumn pk2 = table2.getPrimaryKey();
        if (pk1 == null && pk2 == null) {
            logger.warn("pk1=" + pk1 + ", pk2=" + pk2);
        } else if (pk1 == null) {
            logger.warn("pk1=" + pk1 + ", pk2=" + pk2);
            _droppedPrimaryKeyConstraints.addAll(table2.getPKConstraintsMap().values());
        } else if (pk2 == null) {
            logger.warn("pk1=" + pk1 + ", pk2=" + pk2);
            _addedPrimaryKeyConstraints.addAll(table1.getPKConstraintsMap().values());
        } else if (StringUtils.equals(pk1.getName(), pk2.getName()) && StringUtils.equals(pk1.getSqlDataType(), pk2.getSqlDataType())) {
        } else {
            _addedPrimaryKeyConstraints.addAll(table1.getPKConstraintsMap().values());
            _droppedPrimaryKeyConstraints.addAll(table2.getPKConstraintsMap().values());
        }
    }

    private void mergeConstraintIndexes(SqlTable table1, SqlTable table2) {
        mergeConstraintIndexes(table1.getFKConstraintsMap(), table2.getFKConstraintsMap());
        mergeConstraintIndexes(table1.getUKConstraintsMap(), table2.getUKConstraintsMap());
        mergeConstraintIndexes(table1.getIXConstraintsMap(), table2.getIXConstraintsMap());
    }

    private boolean mergeConstraintIndexes(Map<String, SqlConstraintIndex> map1, Map<String, SqlConstraintIndex> map2) {
        for (String key1 : map1.keySet()) {
            if (!map2.containsKey(key1)) {
                _addedConstraintIndexes.add(map1.get(key1));
            }
        }
        for (String key2 : map2.keySet()) {
            if (!map1.containsKey(key2)) {
                _droppedConstraintIndexes.add(map2.get(key2));
            }
        }
        return true;
    }

    private static final String v3r5i0n = "v3r5i0n";

    private void compareRows(SqlTable table1, SqlTable table2, Set<String> colnames) {
        Map<String, SqlRow> map1 = table1.getRowsByPrimaryKeyMap();
        Map<String, SqlRow> map2 = table2.getRowsByPrimaryKeyMap();
        Map<String, SqlRow> mbk1 = table1.getRowsMap();
        Map<String, SqlRow> mbk2 = table2.getRowsMap();
        Map<String, SqlColumn> colmap1 = table1.getColumnsMap();
        Map<String, SqlColumn> colmap2 = table2.getColumnsMap();
        Set<String> keySet = new TreeSet<>();
        keySet.addAll(map1.keySet());
        keySet.addAll(map2.keySet());
        String tableName = table1.getName();
//      String pk1 = table1.getPrimaryKey().getName();
//      String pk2 = table2.getPrimaryKey().getName();
        String bk1 = table1.getBusinessKey().getName();
        String bk2 = table2.getBusinessKey().getName();
        String vn1 = table1.getVersion() == null ? null : table1.getVersion().getName();
        String vn2 = table2.getVersion() == null ? null : table2.getVersion().getName();
        boolean bk;
        SqlRow row1, row2;
        Map<String, SqlRowValue> valuesMap1, valuesMap2;
        SqlRowPair rowPair;
        List<SqlRowValuePair> rowValuePairs;
        SqlRowValue rowValue1, rowValue2;
        SqlRowValuePair rowValuePair;
        SqlColumn column1, column2;
        String strKey, strValue1, strValue2, subject, predicate, text;
        for (String key : keySet) {
            strKey = "#" + key;
            if (map1.containsKey(key) && map2.containsKey(key)) {
                row1 = map1.get(key);
                row2 = map2.get(key);
                rowPair = new SqlRowPair(row1, row2);
                rowValuePairs = rowPair.getUpdatedRowValues();
                valuesMap1 = row1.getValuesMap();
                valuesMap2 = row2.getValuesMap();
                for (String colname : colnames) {
                    if (colname.equals(vn1) && colname.equals(vn2)) {
                        continue;
                    }
                    if (colname.equals(v3r5i0n) && colname.equals(v3r5i0n)) {
                        continue;
                    }
                    column1 = colmap1.get(colname);
                    column2 = colmap2.get(colname);
                    if (column1 == null) {
                        continue; // dropped column
                    }
                    rowValue1 = valuesMap1.get(colname);
                    rowValue2 = valuesMap2.get(colname);
                    strValue1 = rowValue1 == null ? null : rowValue1.getValue();
                    strValue2 = rowValue2 == null ? null : rowValue2.getValue();
                    if (StringUtils.equals(strValue1, strValue2)) {
                        continue;
                    }
                    boolean oldColumn = column2 != null;
                    boolean newColumn = column2 == null;
                    boolean newColumnWithValue = newColumn && rowValue1 != null && strValue1 != null;
                    boolean newColumnWithDefaultValue = newColumnWithValue && isDefaultValue(rowValue1);
                    boolean newValueIsTheDefaultValue = rowValue1 != null && strValue1 != null && isDefaultValue(rowValue1);
                    rowValuePair = new SqlRowValuePair(rowValue1, rowValue2);
                    if (oldColumn) {
                        rowValuePairs.add(rowValuePair);
                    }
                    _updatedRows.put(tableName + "/" + key, rowPair);
                    _updatedRowTableNames.add(tableName);
                    bk = colname.equals(bk1) || colname.equals(bk2);
                    if (bk) {
                        subject = strKey;
                        _currentKeyTableNames.add(tableName);
                        if (oldColumn) {
                            predicate = "business key modified";
                            _updatedKeyTableNames.add(tableName);
                        } else {
                            predicate = "business key added";
                            _addedKeyTableNames.add(tableName);
                        }
                    } else {
                        subject = isDetailLoggingEnabled() ? strKey : "rows";
                        predicate = "modified";
                    }
                    text = join(tableName, subject, predicate);
                    if (bk || isDetailLoggingEnabled()) {
                        if (oldColumn) {
                            text += " (" + colname + ": new value " + StrUtils.enclose(strValue1, '"') + ", current value " + StrUtils.enclose(strValue2, '"') + ")";
                        } else {
                            text += " (" + colname + ": new value " + StrUtils.enclose(strValue1, '"') + ")";
                        }
                    }
                    if (bk && oldColumn) {
                        logWarning(tableName, text + DATA_CONV_REQD);
                    } else if (newColumnWithDefaultValue) {
                        text = join(tableName, strKey, colname, "has a new value but it is the default value so an update is not required");
                        logger.trace(text);
                    } else {
                        logDetails(tableName, text);
                        if (newColumnWithValue) {
                            text = join(tableName, strKey, colname, "has a new value and it is not the default value so an update is required");
                            logger.trace(text);
                            logger.trace(getUpdateStatement(rowValue1));
                            _addedValues.add(rowValue1);
                        } else if (rowValuePair.isUpdatableColumn()) {
                            text = join(tableName, strKey, colname, "has a new value; it may also have been updated by the end user");
                            if (newValueIsTheDefaultValue) {
                                text += " and the new value is the default value, so an update might not be required";
                            }
                            logWarning(tableName, text);
                        }
                    }
                }
            } else if (map1.containsKey(key)) {
                row1 = map1.get(key);
                _addedRows.add(row1);
                String bkv = row1.getBusinessKeyValue();
                subject = isDetailLoggingEnabled() ? "[" + key + ", " + StrUtils.enclose(bkv, '"') + "]" : "rows";
                text = join(tableName, subject, "added");
                if (mbk2.containsKey(bkv)) {
                    _updatedKeyTableNames.add(tableName);
                    _currentKeyTableNames.add(tableName);
                    logWarning(tableName, text + DATA_CONV_REQD);
                } else {
                    logMessage(tableName, text);
                }
            } else {
                row2 = map2.get(key);
                _deletedRows.add(row2);
                _deletedRowTableNames.add(tableName);
                _currentKeyTableNames.add(tableName);
                String bkv = row2.getBusinessKeyValue();
                subject = isDetailLoggingEnabled() ? "[" + key + ", " + StrUtils.enclose(bkv, '"') + "]" : "rows";
                text = join(tableName, subject, "deleted");
                if (mbk1.containsKey(bkv)) {
                    _updatedKeyTableNames.add(tableName);
                }
                logWarning(tableName, text + DATA_CONV_REQD);
            }
        }
    }

    private boolean isDefaultValue(SqlRowValue rowValue) {
        return rowValue.isDefaultValue();
    }

    private String getUpdateStatement(SqlRowValue rowValue) {
        return rowValue.getUpdateStatement();
    }

    private String join(String... texts) {
        String join = "";
        for (String text : texts) {
            if (StringUtils.isNotBlank(text)) {
                join += text + " ";
            }
        }
        return join.trim();
    }

    private SqlTableWrapper newSharedTable(SqlTable table) {
        SqlTableWrapper sharedTable = new SqlTableWrapper(table, this);
        _sharedTables.add(sharedTable);
        return sharedTable;
    }

    private void createTable(SqlTable table) {
        //<editor-fold defaultstate="collapsed" desc="testing">
        /*
        String tableName = table.getName();
        String statement = "create table " + tableName;
        boolean log = logMessage(tableName, statement);
        if (_testingPhase) {
            String name, type, defaultValue;
            int length, precision, scale;
            boolean nullable;
            Collection<SqlColumn> columns = table.getColumns();
            for (SqlColumn column : columns) {
                name = column.getName();
                type = column.getSqlDataType();
                length = column.getLength();
                precision = column.getPrecision();
                scale = column.getScale();
                nullable = column.isNullable();
                defaultValue = column.getSqlDefaultValue();
                statement = name + "(" + type + "," + length + "," + precision + "," + scale + "," + nullable + "," + defaultValue + ")";
                if (log && isDetailLoggingEnabled()) {
                    logger.info(statement); // logDetails must not be used here
                }
            }
        }
        /**/
        //</editor-fold>
        _addedTables.add(table);
        addConstraintIndexes(table);
        insertRows(table);
    }

    private void addConstraintIndexes(SqlTable table) {
//      for (SqlConstraintIndex pk : table.getPKConstraints()) {
//          _addedConstraintIndexes.add(pk);
//      }
        for (SqlConstraintIndex fk : table.getFKConstraints()) {
            _addedConstraintIndexes.add(fk);
        }
        for (SqlConstraintIndex uk : table.getUKConstraints()) {
            _addedConstraintIndexes.add(uk);
        }
        for (SqlConstraintIndex ix : table.getIXConstraints()) {
            _addedConstraintIndexes.add(ix);
        }
    }

    private void insertRows(SqlTable table) {
        Map<String, SqlRow> map = table.getRowsMap();
        String tableName = table.getName();
        if (map.isEmpty()) {
            logDetails(tableName, join("no rows added to table", tableName));
        } else {
            SqlRow row;
            String strKey, subject, text;
            for (String key : map.keySet()) {
                row = map.get(key);
                _addedRows.add(row);
                strKey = row.getPrimaryKeyValue();
                subject = isDetailLoggingEnabled() ? "[" + strKey + ", " + StrUtils.enclose(key, '"') + "]" : "rows";
                text = join(tableName, subject, "added");
                logMessage(tableName, text);
            }
        }
    }

    private void dropTable(SqlTable table) {
        String tableName = table.getName();
        String statement = "drop table " + tableName + ";";
        logWarning(tableName, statement);
        _droppedTables.add(table);
    }

    private void modifyColumn(SqlColumn column1, SqlColumn column2) {
        String tableName = column1.getTable().getName();
        String columnName = column1.getName();
        String name = tableName + "." + columnName;
        /**/
        //<editor-fold defaultstate="collapsed" desc="logging">
        /*
        String type1 = column1.getSqlDataType();
        int length1 = column1.getLength();
        int precision1 = column1.getPrecision();
        int scale1 = column1.getScale();
        boolean nullable1 = column1.isNullable();
        String default1 = column1.getSqlDefaultValue();
        boolean foreign1 = column1.isForeign();
        String foreignTableName1 = foreign1 ? "->" + column1.getForeignTableName() : "";
        String type2 = column2.getSqlDataType();
        int length2 = column2.getLength();
        int precision2 = column2.getPrecision();
        int scale2 = column2.getScale();
        boolean nullable2 = column2.isNullable();
        String default2 = column2.getSqlDefaultValue();
        boolean foreign2 = column2.isForeign();
        String foreignTableName2 = foreign2 ? "->" + column2.getForeignTableName() : "";
        String statement = "alter table " + tableName + " modify column " + columnName
            + " [" + type2 + "(" + length2 + "," + precision2 + "," + scale2 + "," + nullable2 + "," + default2 + ")" + foreignTableName2
            + ", " + type1 + "(" + length1 + "," + precision1 + "," + scale1 + "," + nullable1 + "," + default1 + ")" + foreignTableName1
            + "];";
        //
        logMessage(tableName, statement);
//      */
        //</editor-fold>
        /**/
        _newColumns.put(name, column1);
        _oldColumns.put(name, column2);
    }

    private void addColumn(SqlColumn column) {
        String tableName = column.getTable().getName();
        String columnName = column.getName();
        String type = column.getSqlDataType();
        int length = column.getLength();
        int precision = column.getPrecision();
        int scale = column.getScale();
        boolean nullable = column.isNullable();
        String defaultValue = column.getSqlDefaultValue();
        String statement = "alter table " + tableName + " add column " + columnName
            + " [" + type + "(" + length + "," + precision + "," + scale + "," + nullable + "," + defaultValue + ")"
            + "];";
        logMessage(tableName, statement);
        _addedColumns.add(column);
    }

    private void dropColumn(SqlColumn column) {
        String tableName = column.getTable().getName();
        String columnName = column.getName();
        String statement = "alter table " + tableName + " drop column " + columnName + ";";
//      logMessage(tableName, statement, true);
        logWarning(tableName, statement);
        _droppedColumns.add(column);
    }

    static boolean equals(SqlColumn column1, SqlColumn column2) {
        String type1 = column1.getType();
        int length1 = column1.getLength();
        int precision1 = column1.getPrecision();
        int scale1 = column1.getScale();
        boolean nullable1 = column1.isNullable();
        /**/
        String type2 = column2.getType();
        int length2 = column2.getLength();
        int precision2 = column2.getPrecision();
        int scale2 = column2.getScale();
        boolean nullable2 = column2.isNullable();
        /**/
        return type1.equals(type2)
            && length1 == length2
            && precision1 == precision2
            && scale1 == scale2
            && nullable1 == nullable2
            && refers(column1, column2);
    }

    static boolean equates(SqlColumn column1, SqlColumn column2) {
        boolean casts = casts(column1, column2);
        if (!casts) {
            return false;
        }
        boolean nullable1 = column1.isNullable();
        String default1 = column1.getSqlDefaultValue();
        /**/
        boolean nullable2 = column2.isNullable();
//      String default2 = column2.getSqlDefaultValue();
        /**/
        return nullable1 || !nullable2 || default1 != null;
    }

    static boolean casts(SqlColumn column1, SqlColumn column2) {
        boolean refers = refers(column1, column2);
        if (!refers) {
            return false;
        }
        String type1 = column1.getType();
        int length1 = column1.getLength();
        int precision1 = column1.getPrecision();
        int scale1 = column1.getScale();
        int characters1 = characters(type1, length1);
        int digits1 = digits(type1, precision1, scale1);
        /**/
        String type2 = column2.getType();
        int length2 = column2.getLength();
        int precision2 = column2.getPrecision();
        int scale2 = column2.getScale();
        int characters2 = characters(type2, length2);
        int digits2 = digits(type2, precision2, scale2);
        /*
        switch (type1) {
//          case "blob":
//          case "boolean":
//              return type2.equals(type1);
            case "char":
            case "string":
                return characters1 >= characters2 && characters2 > 0;
            case "byte":
            case "short":
            case "integer":
            case "long":
            case "decimal":
            case "float":
            case "double":
                return digits1 >= digits2 && digits2 > 0;
//          case "date":
//          case "time":
//              return type2.equals(type1);
            case "timestamp":
                return type2.equals(type1) || type2.equals("date") || type2.equals("time");
            default:
                return type2.equals(type1);
        }
        /**/
        return switch (type1) {
            case "char", "string" ->
                characters1 >= characters2 && characters2 > 0;
            case "byte", "short", "integer", "long", "decimal", "float", "double" ->
                digits1 >= digits2 && digits2 > 0;
            case "timestamp" ->
                type2.equals(type1) || type2.equals("date") || type2.equals("time");
            default ->
                type2.equals(type1);
        };
    }

    static boolean refers(SqlColumn column1, SqlColumn column2) {
        boolean foreign1 = column1.isForeign();
        boolean foreign2 = column2.isForeign();
        if (foreign1) {
            String foreignTableName1 = column1.getForeignTableName();
            String foreignTableName2 = column2.getForeignTableName();
            return foreign2 && foreignTableName1.equals(foreignTableName2);
        }
        return true;
    }

    private static int characters(String type, int length) {
        /*
        switch (type) {
            case "char":
            case "string":
                return length == 0 ? Integer.MAX_VALUE : length;
            default:
                return 0;
        }
        /**/
        return switch (type) {
            case "char", "string" ->
                length == 0 ? Integer.MAX_VALUE : length;
            default ->
                0;
        };
        /**/
    }

    private static int digits(String type, int precision, int scale) {
        /*
        switch (type) {
            case "byte":
                return 3;
            case "short":
                return 5;
            case "integer":
                return 10;
            case "long":
                return 19;
            case "decimal":
            case "float":
            case "double":
                return precision == 0 ? Integer.MAX_VALUE : precision - scale;
            default:
                return 0;
        }
        /**/
        return switch (type) {
            case "byte" ->
                3;
            case "short" ->
                5;
            case "integer" ->
                10;
            case "long" ->
                19;
            case "decimal", "float", "double" ->
                precision == 0 ? Integer.MAX_VALUE : precision - scale;
            default ->
                0;
        };
        /**/
    }

    private boolean logDetails(String name, String text) {
        return isDetailLoggingEnabled() && logMessage(name, text);
    }

    private boolean logMessage(String name, String text) {
        return logMessage(name, text, false);
    }

    private boolean logMessage(String name, String text, boolean notable) {
        if (isInfoLoggingEnabled()) {
            String prefix = notable ? "<!>" : "";
            if (addMessage(name, prefix + text)) {
                logger.info(text);
                return true;
            }
        }
        return false;
    }

    private boolean logWarning(String name, String text) {
        if (addWarning(name, text)) {
            logger.warn(SqlUtil.highlight(text));
            return true;
        }
        return false;
    }

    private boolean addMessage(String name, String text) {
        Set<String> set;
        if (_messages.containsKey(name)) {
            set = _messages.get(name);
        } else {
            set = new LinkedHashSet<>();
            _messages.put(name, set);
        }
        return set.add(text);
    }

    private boolean addWarning(String name, String text) {
        Set<String> set;
        if (_warnings.containsKey(name)) {
            set = _warnings.get(name);
        } else {
            set = new LinkedHashSet<>();
            _warnings.put(name, set);
        }
        return set.add(text);
    }

    private void stats(boolean merge) {
        if (merge) {
            logger.info("added tables=" + _addedTables.size()); // + " " + _addedTables
            logger.info("dropped tables=" + _droppedTables.size()); // + " " + _droppedTables
            if (!_addedKeyTableNames.isEmpty()) {
                logger.warn("added-key tables=" + _addedKeyTableNames.size() + " " + _addedKeyTableNames);
            }
            if (!_updatedKeyTableNames.isEmpty()) {
                logger.warn("updated-key tables=" + _updatedKeyTableNames.size() + " " + _updatedKeyTableNames);
            }
            if (!_updatedRowTableNames.isEmpty()) {
                logger.info("updated-row tables=" + _updatedRowTableNames.size() + " " + _updatedRowTableNames);
            }
            if (!_deletedRowTableNames.isEmpty()) {
                logger.warn("deleted-row tables=" + _deletedRowTableNames.size() + " " + _deletedRowTableNames);
            }
            logger.info("added columns=" + _addedColumns.size());
            logger.info("dropped columns=" + _droppedColumns.size());
            logger.info("altered columns=" + _newColumns.size());
            if (!_oddColumns.isEmpty()) {
                logger.warn("odd columns=" + _oddColumns.size());
            }
            logger.info("added rows=" + _addedRows.size());
            logger.info("deleted rows=" + _deletedRows.size());
            logger.info("updated rows=" + _updatedRows.size());
            if (isIncrementallyUpgradeable()) {
                logger.info("an incremental upgrade should work!");
            } else {
                logger.warn("AN INCREMENTAL UPGRADE COULD FAIL!");
            }
        } else {
            logger.error("MERGE OPERATION FAILED!");
        }
    }

    private boolean write() {
        logger.info("write");
        Writer writer = new Writer(this, "merger");
        writer.write(platform());
        return true;
    }

    protected String platform() {
        return "meta-sql-" + _dbms + "-1st";
    }

}
