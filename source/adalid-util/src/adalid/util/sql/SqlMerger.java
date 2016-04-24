/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.sql;

import adalid.commons.velocity.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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

    private static final boolean info = false;

    private static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String TEST_DIR = USER_DIR + FILE_SEPARATOR + "test";

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private String _oldSchema;

    private String _oldDataFolder;

    private boolean _testingPhase;

    private SqlReader _reader1, _reader2;

    private Map<String, String> _tablesLoadMap = new LinkedHashMap<>();

    private final Set<String> _tableNames = new TreeSet<>();

    private final Set<String> _currentKeyTableNames = new TreeSet<>();

    private final List<SqlTable> _addedTables = new ArrayList<>();

    private final List<SqlTable> _droppedTables = new ArrayList<>();

    private final Map<String, SqlColumn> _newColumns = new LinkedHashMap<>();

    private final Map<String, SqlColumn> _oldColumns = new LinkedHashMap<>();

    private final List<SqlColumn> _addedColumns = new ArrayList<>();

    private final List<SqlColumn> _droppedColumns = new ArrayList<>();

    private final List<SqlTableWrapper> _sharedTables = new ArrayList<>();

    private final Map<String, Set<String>> _warnings = new LinkedHashMap<>();
    // </editor-fold>

    public SqlMerger(String[] args) {
        super(args);
        _initialised = _initialised && oldSchema(args);
    }

    // <editor-fold defaultstate="collapsed" desc="args">
    private boolean oldSchema(String[] args) {
        _oldSchema = arg(7, args);
        if (StringUtils.isNotBlank(_oldSchema)) {
            logValidArgument("old schema", _oldSchema);
            return true;
        }
        logInvalidArgument("old schema", _oldSchema);
        logSyntaxError();
        return false;
    }

    @Override
    protected void logSyntaxError() {
        logger.error("Syntax: " + getClass().getSimpleName() + " dbms, host, port, user, password, database, schema, oldSchema");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    public boolean isTestingPhase() {
        return _testingPhase;
    }

    public String getNewSchema() {
        return _schema;
    }

    public String getOldSchema() {
        return _oldSchema;
    }

    public String getOldDataFolder() {
        if (_oldDataFolder == null) {
            _oldDataFolder = defaultOldDataFolder();
        }
        return _oldDataFolder;
    }

    public void setOldDataFolder(String folder) {
        if (folder != null) {
            folder = folder.replace("\\", "/");
            if (folder.endsWith("/")) {
            } else {
                folder += "/";
            }
        }
        _oldDataFolder = folder;
    }

    private String defaultOldDataFolder() {
        String defaultOldDataFolder = TEST_DIR;
        defaultOldDataFolder += FILE_SEPARATOR + _database;
        defaultOldDataFolder += FILE_SEPARATOR + _dbms;
        defaultOldDataFolder += FILE_SEPARATOR + _oldSchema;
        defaultOldDataFolder += FILE_SEPARATOR;
        defaultOldDataFolder = defaultOldDataFolder.replace("\\", "/");
        return defaultOldDataFolder;
    }

    public Set<String> getTableNames() {
        return _tableNames;
    }

    public Set<String> getCurrentKeyTableNames() {
        return _currentKeyTableNames;
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

    public Set<String> getModifiedColumnNames() {
        return _newColumns.keySet();
    }

    public List<SqlColumn> getAddedColumns() {
        return _addedColumns;
    }

    public List<SqlColumn> getDroppedColumns() {
        return _droppedColumns;
    }

    public List<SqlTableWrapper> getSharedTables() {
        return _sharedTables;
    }

    public Map<String, Set<String>> getWarnings() {
        return _warnings;
    }
    // </editor-fold>

    public synchronized boolean merge() {
        return merge(false);
    }

    public synchronized boolean merge(boolean testing) {
        logger.info("merge");
        _testingPhase = testing;
        _tableNames.clear();
        _currentKeyTableNames.clear();
        _currentKeyTableNames.add("rol");
        _addedTables.clear();
        _droppedTables.clear();
        _newColumns.clear();
        _oldColumns.clear();
        _addedColumns.clear();
        _droppedColumns.clear();
        boolean merge = _initialised;
        merge = merge && read1();
        merge = merge && read2();
        merge = merge && mergeTables();
        merge = merge && write();
        return merge;
    }

    private boolean read1() {
        boolean read = initReader1();
        if (read) {
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
        String[] args = new String[]{_dbms, _host, _port, _user, _password, _database, _schema};
        _reader1 = new SqlReader(args);
        _reader1.setSelectTemplatesPath("templates/meta/sql");
        _reader1.setTablesLoadMap(_tablesLoadMap);
        return _reader1.isInitialised();
    }

    private boolean read2() {
        boolean read = initReader2();
        return read && _reader2.read(true);
    }

    private boolean initReader2() {
        String[] args = new String[]{_dbms, _host, _port, _user, _password, _database, _oldSchema};
        _reader2 = new SqlReader(args);
        _reader2.setSelectTemplatesPath("templates/meta/sql");
        _reader2.setTablesLoadMap(_tablesLoadMap);
        return _reader2.isInitialised();
    }

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

    private boolean mergeTables() {
        Map<String, SqlTable> map1 = _reader1.getTablesMap();
        Map<String, SqlTable> map2 = _reader2.getTablesMap();
        _tableNames.addAll(map1.keySet());
        _tableNames.addAll(map2.keySet());
        SqlTable table1, table2;
        for (String key : _tableNames) {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                table1 = map1.get(key);
                table2 = map2.get(key);
                mergeColumns(table1, table2);
            } else if (map1.containsKey(key)) {
                table1 = map1.get(key);
                createTable(table1);
            } else {
                table2 = map2.get(key);
                dropTable(table2);
            }
        }
        return true;
    }

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
        boolean migrateable = !table1.isLoaded();
        String text;
        String name1;
        String type1, type2;
        String sqlx1, sqlx2;
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
                if (migrateable) {
                    if (columnPair.casts()) {
                        text = name1 + " is now a not nullable " + sqlx1 + " column with no default value";
                        if (add(tableName, text)) {
                            logger.warn(SqlUtil.highlight(text));
                        }
                    } else if (type1.equals(type2) || type1.equals("string")) {
                        text = name1 + " is now " + sqlx1 + "; previously it was " + sqlx2 + "; proper conversion might be required";
                        if (add(tableName, text)) {
                            logger.warn(SqlUtil.highlight(text));
                        }
                    } else {
                        text = name1 + " is now " + sqlx1 + "; previously it was " + sqlx2 + "; proper conversion is required";
                        if (add(tableName, text)) {
                            logger.warn(SqlUtil.highlight(text));
                        }
                    }
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
                    sharedTable.addColumnPair(column1, null);
                    if (migrateable) {
                        text = name1 + " is a new not nullable " + sqlx1 + " column with no default value";
                        if (add(tableName, text)) {
                            logger.warn(SqlUtil.highlight(text));
                        }
                    }
                }
            } else {
                column2 = map2.get(key);
                dropColumn(column2);
                sharedTable.setDroppedColumns(true);
            }
        }
        if (table1.isLoaded() && table2.isLoaded()) {
            compareRows(table1, table2, keySet);
        }
        return true;
    }

    private void compareRows(SqlTable table1, SqlTable table2, Set<String> colnames) {
        Map<String, SqlRow> map1 = table1.getRowsMap();
        Map<String, SqlRow> map2 = table2.getRowsMap();
        Set<String> keySet = new TreeSet<>();
        keySet.addAll(map1.keySet());
        keySet.addAll(map2.keySet());
        String tableName = table1.getName();
        String pk1 = table1.getPrimaryKey().getName();
        String pk2 = table2.getPrimaryKey().getName();
        SqlRow row1, row2;
        Map<String, SqlRowValue> valuesMap1, valuesMap2;
        SqlRowValue rowValue1, rowValue2;
        String value1, value2;
        String text;
        for (String key : keySet) {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                row1 = map1.get(key);
                row2 = map2.get(key);
                valuesMap1 = row1.getValuesMap();
                valuesMap2 = row2.getValuesMap();
                for (String colname : colnames) {
                    rowValue1 = valuesMap1.get(colname);
                    rowValue2 = valuesMap2.get(colname);
                    value1 = rowValue1 == null ? null : rowValue1.getValue();
                    value2 = rowValue2 == null ? null : rowValue2.getValue();
                    if (StringUtils.equals(value1, value2)) {
                        continue;
                    }
                    if (colname.equals(pk1) || colname.equals(pk2)) {
                        _currentKeyTableNames.add(tableName);
//                      text = warning(tableName, key, "modified") + ": " + colname + " \"" + value2 + "\" -> \"" + value1 + "\"";
                        text = warning(tableName, "key values", "modified");
                        if (add(tableName, text)) {
                            logger.warn(SqlUtil.highlight(text));
                        }
                    }
                }
            } else if (map1.containsKey(key)) {
                if (info) {
//                  text = warning(tableName, key, "added");
                    text = warning(tableName, "rows", "added");
                    logger.info(text);
                }
            } else {
                _currentKeyTableNames.add(tableName);
//              text = warning(tableName, key, "deleted");
                text = warning(tableName, "rows", "deleted");
                if (add(tableName, text)) {
                    logger.warn(SqlUtil.highlight(text));
                }
            }
        }
    }

    private SqlTableWrapper newSharedTable(SqlTable table) {
        SqlTableWrapper sharedTable = new SqlTableWrapper(table, this);
        _sharedTables.add(sharedTable);
        return sharedTable;
    }

    private boolean add(String name, String text) {
        Set<String> set;
        if (_warnings.containsKey(name)) {
            set = _warnings.get(name);
        } else {
            set = new LinkedHashSet<>();
            _warnings.put(name, set);
        }
        return set.add(text);
    }

    private void createTable(SqlTable table) {
        String tableName = table.getName();
        String statement = "create table " + _oldSchema + "." + tableName;
        if (info) {
            logger.info(statement);
        }
        _addedTables.add(table);
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
                statement = "\t" + name
                    + " [" + type + "(" + length + "," + precision + "," + scale + "," + nullable + "," + defaultValue + ")"
                    + "]";
                if (info) {
                    logger.info(statement);
                }
            }
        }
    }

    private void dropTable(SqlTable table) {
        String tableName = table.getName();
        String statement = "drop table " + _oldSchema + "." + tableName + ";";
        logger.warn(SqlUtil.highlight(statement));
        _droppedTables.add(table);
    }

    private void modifyColumn(SqlColumn column1, SqlColumn column2) {
        String tableName = column1.getTable().getName();
        String columnName = column1.getName();
        String name = tableName + "." + columnName;
        /**/
        String type1 = column1.getSqlDataType();
        int length1 = column1.getLength();
        int precision1 = column1.getPrecision();
        int scale1 = column1.getScale();
        boolean nullable1 = column1.isNullable();
        String default1 = column1.getSqlDefaultValue();
        /**/
        String type2 = column2.getSqlDataType();
        int length2 = column2.getLength();
        int precision2 = column2.getPrecision();
        int scale2 = column2.getScale();
        boolean nullable2 = column2.isNullable();
        String default2 = column2.getSqlDefaultValue();
        /**/
        String statement = "alter table " + _oldSchema + "." + tableName + " modify column " + columnName
            + " [" + type2 + "(" + length2 + "," + precision2 + "," + scale2 + "," + nullable2 + "," + default2 + ")"
            + ", " + type1 + "(" + length1 + "," + precision1 + "," + scale1 + "," + nullable1 + "," + default1 + ")"
            + "];";
        if (equates(column1, column2)) {
            if (info) {
                logger.info(statement);
            }
        } else {
            logger.warn(SqlUtil.highlight(statement));
        }
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
        String statement = "alter table " + _oldSchema + "." + tableName + " add column " + columnName
            + " [" + type + "(" + length + "," + precision + "," + scale + "," + nullable + "," + defaultValue + ")"
            + "];";
        if (info) {
            logger.info(statement);
        }
        _addedColumns.add(column);
    }

    private void dropColumn(SqlColumn column) {
        String tableName = column.getTable().getName();
        String columnName = column.getName();
        String statement = "alter table " + _oldSchema + "." + tableName + " drop column " + columnName + ";";
        logger.warn(SqlUtil.highlight(statement));
        _droppedColumns.add(column);
    }

    private String warning(String tableName, String key, String adjective) {
        return tableName + " " + key + " " + adjective;
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
            && nullable1 == nullable2;
    }

    static boolean equates(SqlColumn column1, SqlColumn column2) {
        boolean nullable1 = column1.isNullable();
        String default1 = column1.getSqlDefaultValue();
        /**/
        boolean nullable2 = column2.isNullable();
//      String default2 = column2.getSqlDefaultValue();
        /**/
        return casts(column1, column2) && (nullable1 || !nullable2 || default1 != null);
    }

    static boolean casts(SqlColumn column1, SqlColumn column2) {
        String type1 = column1.getType();
        int length1 = column1.getLength();
        int precision1 = column1.getPrecision();
        int characters1 = characters(type1, length1);
        int digits1 = digits(type1, precision1);
        /**/
        String type2 = column2.getType();
        int length2 = column2.getLength();
        int precision2 = column2.getPrecision();
        int characters2 = characters(type2, length2);
        int digits2 = digits(type2, precision2);
        /**/
        return (type1.equals("boolean") && type2.equals(type1))
            || (type1.equals("date") && type2.equals(type1))
            || (type1.equals("time") && type2.equals(type1))
            || (type1.equals("timestamp") && (type2.equals("timestamp") || type2.equals("date") || type2.equals("time")))
            || (characters1 >= characters2 && characters2 > 0)
            || (digits1 >= digits2 && digits2 > 0);
    }

    private static int characters(String type, int length) {
        return type.equals("char") || type.equals("string") ? length == 0 ? Integer.MAX_VALUE : length : 0;
    }

    private static int digits(String type, int precision) {
        switch (type) {
            case "short":
                return 4;
            case "integer":
                return 9;
            case "long":
                return 18;
            case "decimal":
            case "float":
            case "double":
                return precision == 0 ? 131072 : precision;
            default:
                return 0;
        }
    }

    private boolean write() {
        logger.info("write");
        Writer writer = new Writer(this, "merger");
        writer.write("meta-sql-" + _dbms);
        return true;
    }

}
