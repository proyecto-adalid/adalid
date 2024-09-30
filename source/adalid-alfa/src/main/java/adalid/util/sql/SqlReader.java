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
import java.io.File;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

/**
 * @author Jorge Campins
 */
public class SqlReader extends SqlUtil {

    private static final Logger logger = Logger.getLogger(SqlReader.class);

    private static final boolean INFO = true;

    private static final boolean DETAIL = false;

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final int ROW_LIMIT = 30000;

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final Map<String, SqlTable> _tables = new LinkedHashMap<>();

    private Set<String> _tablesExcludeSet = new LinkedHashSet<>();

    private Map<String, String> _tablesInheritMap = new LinkedHashMap<>();

    private Map<String, String> _tablesLoadMap = new LinkedHashMap<>();

    private Map<String, String> _catalogTablesMap = new LinkedHashMap<>();

    private List<String> _updatableColumns;

    private boolean _checkUpdatableColumns;
//
//  private Project _masterProject;

    private boolean _loadConfigurationTables;

    private boolean _loadOperationTables;

    private boolean _coverConstraintIndexes = true;

    private boolean _coverIndexes, _coverTabs, _coverRoutines;

    private String _selectTemplatesPath = "templates/meta/sql";
    // </editor-fold>

    public SqlReader() {
        super();
    }

    public SqlReader(String[] args) {
        super(args);
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    private SqlAid _sqlAid;

    protected SqlAid getSqlAid() {
        if (_sqlAid == null) {
            _sqlAid = switch (_dbms) {
                case "oracle" ->
                    new OracleAid();
                case "postgresql" ->
                    new PostgreSqlAid();
                default ->
                    null;
            };
        }
        return _sqlAid;
    }

    /**
     * @return the tables map
     */
    public Map<String, SqlTable> getTablesMap() {
        return _tables;
    }

    /**
     * @return the tables collection
     */
    public Collection<SqlTable> getTables() {
        return _tables.values();
    }

    /**
     * @return the tables exclude set
     */
    public Set<String> getTablesExcludeSet() {
        return _tablesExcludeSet;
    }

    /**
     * @param set the tables exclude set to set
     */
    public void setTablesExcludeSet(Set<String> set) {
        _tablesExcludeSet = set;
    }

    /**
     * @param set the tables exclude set to set
     */
    public void setTablesExcludeSet(String[] set) {
        _tablesExcludeSet = set == null ? null : new LinkedHashSet<>(Arrays.asList(set));
    }

    /**
     * @return the tables inherit set
     */
    public Set<String> getTablesInheritSet() {
        return _tablesInheritMap.keySet();
    }

    /**
     * @return the tables inherit map
     */
    public Map<String, String> getTablesInheritMap() {
        return _tablesInheritMap;
    }

    /**
     * @param map the tables inherit map to set
     */
    public void setTablesInheritMap(Map<String, String> map) {
        _tablesInheritMap = map;
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
     * @return the load configuration tables indicator
     */
    public boolean isLoadConfigurationTables() {
        return _loadConfigurationTables;
    }

    /**
     * @param loadConfigurationTables the load configuration tables indicator to set
     */
    public void setLoadConfigurationTables(boolean loadConfigurationTables) {
        _loadConfigurationTables = loadConfigurationTables;
    }

    /**
     * @return the load operation tables indicator
     */
    public boolean isLoadOperationTables() {
        return _loadOperationTables;
    }

    /**
     * @param loadOperationTables the load operation tables indicator to set
     */
    public void setLoadOperationTables(boolean loadOperationTables) {
        _loadOperationTables = loadOperationTables;
    }

    /**
     * @return the cover constraint indexes indicator
     */
    public boolean isCoverConstraintIndexes() {
        return _coverConstraintIndexes;
    }

    /**
     * @param b the cover constraint indexes indicator to set
     */
    public void setCoverConstraintIndexes(boolean b) {
        _coverConstraintIndexes = b;
    }

    /**
     * @return the cover indexes indicator
     */
    public boolean isCoverIndexes() {
        return _coverIndexes;
    }

    /**
     * @param b the cover indexes indicator to set
     */
    public void setCoverIndexes(boolean b) {
        _coverIndexes = b;
    }

    /**
     * @return the cover tabs indicator
     */
    public boolean isCoverTabs() {
        return _coverTabs;
    }

    /**
     * @param b the cover tabs indicator to set
     */
    public void setCoverTabs(boolean b) {
        _coverTabs = b;
    }

    /**
     * @return the cover routines indicator
     */
    public boolean isCoverRoutines() {
        return _coverRoutines;
    }

    /**
     * @param b the cover routines indicator to set
     */
    public void setCoverRoutines(boolean b) {
        _coverRoutines = b;
    }

    /**
     * @return the select templates path
     */
    public String getSelectTemplatesPath() {
        return _selectTemplatesPath;
    }

    /**
     * @param selectTemplatesPath the select templates path to set
     */
    public void setSelectTemplatesPath(String selectTemplatesPath) {
        _selectTemplatesPath = selectTemplatesPath;
    }
    // </editor-fold>

    public boolean read(boolean close) {
        boolean read = _initialised;
        read = read && connect();
        read = read && beforeReadBuild();
        read = read && build();
        read = read && beforeReadClose();
        if (close) {
            close();
        }
        return read;
    }

    protected boolean beforeReadBuild() {
        return true;
    }

    protected boolean beforeReadClose() {
        return true;
    }

    private boolean build() {
        logger.info("build");
        SqlAid aid = getSqlAid();
        if (aid == null) {
            return false;
        }
        PreparedStatementWrapper select = null;
        PreparedStatementWrapper selectTables = null;
        PreparedStatementWrapper selectColumns = null;
        /*
        PreparedStatementWrapper selectIndexes = null;
        PreparedStatementWrapper selectTabs = null;
        PreparedStatementWrapper selectRoutines = null;
        /**/
        PreparedStatementWrapper selectCKs;
        PreparedStatementWrapper selectPKs = null;
        PreparedStatementWrapper selectUKs = null;
        PreparedStatementWrapper selectFKs = null;
        PreparedStatementWrapper selectIXs = null;
        PreparedStatementWrapper selectRows = null;
        String tableName;
        /*
        Object[] executeQueryArgs;
        ResultSet tablesResultSet, columnsResultSet, indexesResultSet, tabsResultSet, rowsResultSet, routinesResultSet;
        /**/
        ResultSet tablesResultSet, columnsResultSet, constraintResultSet, rowsResultSet;
//      Level tableNameLogLevel = isSlowConnection() ? Level.INFO : Level.TRACE;
        int tables = 0, columns = 0, indexes = 0, tabs = 0, rows = 0, routines = 0;
        int cks = 0, pks = 0, uks = 0, fks = 0, ixs = 0;
//      int type1 = 0, type2 = 0, enum1 = 0, enum2 = 0, enum3 = 0, load1 = 0, load2 = 0;
        int enum2 = 0, enum3 = 0, load2 = 0;
        try {
            selectTables = aid.getSelectTablesStatement();
            if (selectTables == null) {
                logger.error("select tables statement is invalid");
                close(selectTables);
                return false;
            }
            select = selectTables;
            logger.trace(select);
            tablesResultSet = selectTables.executeQuery();
            boolean next = tablesResultSet.next();
            if (!next) {
                logger.error("tables=0");
                close(selectTables);
                return false;
            }
            selectColumns = aid.getSelectColumnsStatement();
            if (selectColumns == null) {
                logger.error("select columns statement is invalid");
                close(selectTables);
                close(selectColumns);
                return false;
            }
            setUpdatableColumns(aid);
            /*
            if (_coverIndexes) {
                selectIndexes = aid.getSelectIndexesStatement();
            }
            if (_coverTabs) {
                selectTabs = aid.getSelectTabsStatement();
            }
            if (_coverRoutines) {
                selectRoutines = aid.getSelectRoutinesStatement();
            }
            /**/
            selectCKs = aid.getSelectCKsStatement();
            if (_coverConstraintIndexes) {
                selectPKs = aid.getSelectPKsStatement();
                selectUKs = aid.getSelectUKsStatement();
                selectFKs = aid.getSelectFKsStatement();
                selectIXs = aid.getSelectIXsStatement();
            }
            logger.info("excluded-tables=" + _tablesExcludeSet);
            do {
                SqlTable sqlTable = aid.getSqlTable(tablesResultSet);
                if (foul(sqlTable)) {
                    continue;
                }
                if (add(sqlTable)) {
                    tables++;
                } else {
                    continue;
                }
                tableName = sqlTable.getName();
                /*
                logger.log(tableNameLogLevel, tableName);
                executeQueryArgs = new Object[]{tableName};
                /**/
                // <editor-fold defaultstate="collapsed" desc="read columns">
                /*
                select = selectColumns;
                logger.trace(select);
                columnsResultSet = selectColumns.executeQuery(executeQueryArgs);
                if (columnsResultSet.next()) {
                    SqlColumn sqlColumn;
                    do {
                        sqlColumn = aid.getSqlColumn(columnsResultSet, sqlTable);
                        if (sqlColumn != null) {
                            columns++;
                            sqlTable.add(sqlColumn);
                        }
                    } while (columnsResultSet.next());
                }
                /**/
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read indexes">
                /*
                if (selectIndexes != null) {
                    select = selectIndexes;
                    logger.trace(select);
                    indexesResultSet = selectIndexes.executeQuery(executeQueryArgs);
                    if (indexesResultSet.next()) {
                        SqlIndex sqlIndex = null;
                        String name;
                        do {
                            name = aid.getSqlIndexName(indexesResultSet);
                            if (name == null) {
                            } else if (sqlIndex != null && name.equals(sqlIndex.getName())) {
                                aid.getSqlIndex(indexesResultSet, sqlIndex);
                            } else {
                                sqlIndex = aid.getSqlIndex(indexesResultSet, sqlTable);
                                if (sqlIndex != null) {
                                    indexes++;
                                    sqlTable.add(sqlIndex);
                                }
                            }
                        } while (indexesResultSet.next());
                    }
                }
                /**/
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read tabs">
                /*
                if (selectTabs != null) {
                    select = selectTabs;
                    logger.trace(select);
                    tabsResultSet = selectTabs.executeQuery(executeQueryArgs);
                    if (tabsResultSet.next()) {
                        SqlTab sqlTab = null;
                        String name;
                        do {
                            name = aid.getSqlTabName(tabsResultSet);
                            if (name == null) {
                            } else if (sqlTab != null && name.equals(sqlTab.getName())) {
                                aid.getSqlTab(tabsResultSet, sqlTab);
                            } else {
                                sqlTab = aid.getSqlTab(tabsResultSet, sqlTable);
                                if (sqlTab != null) {
                                    tabs++;
                                    sqlTable.add(sqlTab);
                                }
                            }
                        } while (tabsResultSet.next());
                    }
                }
                /**/
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read routines">
                /*
                if (selectRoutines != null) {
                    select = selectRoutines;
                    logger.trace(select);
                    routinesResultSet = selectRoutines.executeQuery(executeQueryArgs);
                    if (routinesResultSet.next()) {
                        SqlRoutine sqlRoutine = null;
                        String name;
                        do {
                            name = aid.getSqlRoutineName(routinesResultSet);
                            if (name == null) {
                            } else if (sqlRoutine != null && name.equals(sqlRoutine.getName())) {
                                aid.getSqlRoutine(routinesResultSet, sqlRoutine);
                            } else {
                                sqlRoutine = aid.getSqlRoutine(routinesResultSet, sqlTable);
                                if (sqlRoutine != null) {
                                    routines++;
                                    sqlTable.add(sqlRoutine);
                                }
                            }
                        } while (routinesResultSet.next());
                    }
                }
                /**/
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read rows">
                /*
                if (sqlTable.isEnumeration() || sqlTable.isLoadable()) {
                    selectRows = aid.getSelectRowsStatement(sqlTable);
                    if (selectRows != null) {
                        select = selectRows;
                        logger.trace(select);
                        try {
                            int n = 0;
                            rowsResultSet = selectRows.executeQuery();
                            if (rowsResultSet.next()) {
                                SqlRow sqlRow;
                                do {
                                    sqlRow = aid.getSqlRow(rowsResultSet, sqlTable);
                                    if (sqlRow != null) {
                                        n++;
                                        rows++;
                                        sqlTable.add(sqlRow);
                                    }
                                } while (rowsResultSet.next());
                                if (n >= ROW_LIMIT) {
                                    warnRowLimit(sqlTable);
                                }
                            }
                            sqlTable.setLoaded(true);
                            if (INFO && DETAIL) {
                                logger.info(tableName + " rowcount = " + n + " " + sqlTable.getFeatures());
                            }
                        } catch (SQLException ex) {
                            logger.info(select + " / " + ThrowableUtils.getString(ex));
                        } finally {
                            close(selectRows);
                            selectRows = null;
                        }
                    }
                }
                /**/
                // </editor-fold>
                /**/
                select = selectTables;
                /**/
                // <editor-fold defaultstate="collapsed" desc="increment feature counters">
                /*
//              if (sqlTable.isConfigurationTable()) {
//                  type1++;
//              }
//              if (sqlTable.isOperationTable()) {
//                  type2++;
//              }
//              if (sqlTable.isEnumerable()) {
//                  enum1++;
//              }
                if (sqlTable.isEnumeration()) {
                    enum2++;
                }
                if (sqlTable.isUpdatableEnumeration()) {
                    enum3++;
                }
//              if (sqlTable.isLoadable()) {
//                  load1++;
//              }
                if (sqlTable.isLoaded()) {
                    load2++;
                }
                /**/
                // </editor-fold>
                /**/
            } while (tablesResultSet.next());
            if (_tables.isEmpty()) {
            } else {
                SqlTable sqlTable;
                /**/
                // <editor-fold defaultstate="collapsed" desc="read columns">
                select = selectColumns;
                logger.info("read columns");
                columnsResultSet = selectColumns.executeQuery();
                if (columnsResultSet.next()) {
                    SqlColumn sqlColumn;
                    do {
                        String key = aid.getSqlColumnTableName(columnsResultSet);
                        sqlTable = _tables.get(key);
                        if (sqlTable != null) {
                            columns++;
                            sqlColumn = aid.getSqlColumn(columnsResultSet, sqlTable);
                            sqlTable.add(sqlColumn);
                        }
                    } while (columnsResultSet.next());
                }
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read check constraints">
                if (selectCKs != null) {
                    select = selectCKs;
                    logger.info("read check constraints");
                    constraintResultSet = selectCKs.executeQuery();
                    if (constraintResultSet.next()) {
                        SqlCheckConstraint sqlCheckConstraint;
                        do {
                            String key = aid.getSqlCheckConstraintTableName(constraintResultSet);
                            sqlTable = _tables.get(key);
                            if (sqlTable != null) {
                                cks++;
                                sqlCheckConstraint = aid.getSqlCheckConstraint(constraintResultSet, sqlTable);
                                sqlTable.addCK(sqlCheckConstraint);
                            }
                        } while (constraintResultSet.next());
                    }
                }
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read primary keys">
                if (selectPKs != null) {
                    select = selectPKs;
                    logger.info("read primary keys");
                    constraintResultSet = selectPKs.executeQuery();
                    if (constraintResultSet.next()) {
                        SqlConstraintIndex sqlConstraintIndex;
                        do {
                            String key = aid.getSqlConstraintIndexTableName(constraintResultSet);
                            sqlTable = _tables.get(key);
                            if (sqlTable != null) {
                                pks++;
                                sqlConstraintIndex = aid.getSqlConstraintIndex(constraintResultSet, sqlTable);
                                sqlTable.addPK(sqlConstraintIndex);
                            }
                        } while (constraintResultSet.next());
                    }
                }
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read unique keys">
                if (selectUKs != null) {
                    select = selectUKs;
                    logger.info("read unique keys");
                    constraintResultSet = selectUKs.executeQuery();
                    if (constraintResultSet.next()) {
                        SqlConstraintIndex sqlConstraintIndex;
                        do {
                            String key = aid.getSqlConstraintIndexTableName(constraintResultSet);
                            sqlTable = _tables.get(key);
                            if (sqlTable != null) {
                                uks++;
                                sqlConstraintIndex = aid.getSqlConstraintIndex(constraintResultSet, sqlTable);
                                sqlTable.addUK(sqlConstraintIndex);
                            }
                        } while (constraintResultSet.next());
                    }
                }
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read foreign keys">
                if (selectFKs != null) {
                    select = selectFKs;
                    logger.info("read foreign keys");
                    constraintResultSet = selectFKs.executeQuery();
                    if (constraintResultSet.next()) {
                        SqlConstraintIndex sqlConstraintIndex;
                        do {
                            String key = aid.getSqlConstraintIndexTableName(constraintResultSet);
                            sqlTable = _tables.get(key);
                            if (sqlTable != null) {
                                fks++;
                                sqlConstraintIndex = aid.getSqlConstraintIndex(constraintResultSet, sqlTable);
                                sqlTable.addFK(sqlConstraintIndex);
                            }
                        } while (constraintResultSet.next());
                    }
                }
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read non-unique indexes">
                if (selectIXs != null) {
                    select = selectIXs;
                    logger.info("read non-unique indexes");
                    constraintResultSet = selectIXs.executeQuery();
                    if (constraintResultSet.next()) {
                        SqlConstraintIndex sqlConstraintIndex;
                        do {
                            String key = aid.getSqlConstraintIndexTableName(constraintResultSet);
                            sqlTable = _tables.get(key);
                            if (sqlTable != null) {
                                ixs++;
                                sqlConstraintIndex = aid.getSqlConstraintIndex(constraintResultSet, sqlTable);
                                sqlTable.addIX(sqlConstraintIndex);
                            }
                        } while (constraintResultSet.next());
                    }
                }
                // </editor-fold>
                /**/
                // <editor-fold defaultstate="collapsed" desc="read rows">
                logger.info("read rows");
//              int i = 0;
                for (String key : _tables.keySet()) {
                    sqlTable = _tables.get(key);
                    /**/
                    // <editor-fold defaultstate="collapsed" desc="read rows">
                    if (sqlTable.isEnumeration() || sqlTable.isLoadable()) {
                        selectRows = aid.getSelectRowsStatement(sqlTable);
                        if (selectRows != null) {
                            select = selectRows;
//                          logger.warn("\t" + String.format("%04d", ++i) + " " + sqlTable.getName());
                            try {
                                int n = 0;
                                rowsResultSet = selectRows.executeQuery();
                                if (rowsResultSet.next()) {
                                    SqlRow sqlRow;
                                    do {
                                        sqlRow = aid.getSqlRow(rowsResultSet, sqlTable);
                                        if (sqlRow != null) {
                                            n++;
                                            rows++;
                                            sqlTable.add(sqlRow);
                                        }
                                    } while (rowsResultSet.next());
                                    if (n >= ROW_LIMIT) {
                                        warnRowLimit(sqlTable);
                                    }
                                }
                                sqlTable.setLoaded(true);
                                if (INFO && DETAIL) {
                                    logger.info(tableName + " rowcount = " + n + " " + sqlTable.getFeatures());
                                }
                            } catch (SQLException ex) {
                                logger.info(select + " / " + ThrowableUtils.getString(ex));
                            } finally {
                                close(selectRows);
                                selectRows = null;
                            }
                        }
                    }
                    // </editor-fold>
                    /**/
                    // <editor-fold defaultstate="collapsed" desc="increment feature counters">
                    if (sqlTable.isEnumeration()) {
                        enum2++;
                    }
                    if (sqlTable.isUpdatableEnumeration()) {
                        enum3++;
                    }
                    if (sqlTable.isLoaded()) {
                        load2++;
                    }
                    // </editor-fold>
                    /**/
                }
                // </editor-fold>
                /**/
            }
            /**/
            logger.info("tables=" + tables);
//          logger.info("configuration-tables=" + type1);
//          logger.info("operation-tables=" + type2);
//          logger.info("enumerable-tables=" + enum1);
            logger.info("enumeration-tables=" + enum2);
            logger.info("updatable-enumeration-tables=" + enum3);
//          logger.info("loadable-tables=" + load1);
            logger.info("loaded-tables=" + load2);
            logger.info("columns=" + columns);
            if (_coverIndexes) {
                logger.info("indexes=" + indexes);
            }
            if (_coverTabs) {
                logger.info("tabs=" + tabs);
            }
            if (_coverRoutines) {
                logger.info("routines=" + routines);
            }
            logger.info("check-constraints=" + cks);
            if (_coverConstraintIndexes) {
                logger.info("primary-keys=" + pks);
                logger.info("unique-keys=" + uks);
                logger.info("foreign-keys=" + fks);
                logger.info("non-unique-indexes=" + ixs);
            }
            logger.info("rows=" + rows);
        } catch (SQLException ex) {
            logger.fatal(select == null ? _url : select.toString(), ex);
            return false;
        } finally {
            close(selectTables);
            close(selectColumns);
            /*
            close(selectIndexes);
            close(selectTabs);
            close(selectRoutines);
            /**/
            close(selectPKs);
            close(selectUKs);
            close(selectFKs);
            close(selectIXs);
            close(selectRows);
        }
        for (SqlTable sqlTable : _tables.values()) {
            aid.finalize(sqlTable);
        }
        for (SqlTable sqlTable : _tables.values()) {
            sqlTable.settleColumns();
        }
        for (SqlTable sqlTable : _tables.values()) {
            for (SqlTable extension : _tables.values()) {
                if (sqlTable.equals(extension.getRootTable())) {
                    sqlTable.add(extension);
                }
            }
        }
        return columns > 0;
    }

    private boolean add(SqlTable sqlTable) {
        String name = sqlTable.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name table will not be added ";
            logger.error(SqlUtil.highlight(text));
            return false;
        } else if (_tables.containsKey(name)) {
            text = "table " + name + " already added ";
            logger.error(SqlUtil.highlight(text));
            return false;
        } else {
            _tables.put(name, sqlTable);
            return true;
        }
    }

    private void warnRowLimit(SqlTable sqlTable) {
        String name = sqlTable.getName();
        String text = name + " reached " + ROW_LIMIT + " rows limit";
        logger.warn(SqlUtil.highlight(text));
    }

    /*
    private boolean fair(SqlTable sqlTable) {
        return !foul(sqlTable);
    }

    /**/
    private boolean foul(SqlTable sqlTable) {
        if (sqlTable == null) {
            return true;
        }
        if (_tablesExcludeSet == null) {
            return false;
        }
        String name = sqlTable.getName();
        return _tablesExcludeSet.contains(name)
            || _tablesExcludeSet.contains(name.toLowerCase())
            || _tablesExcludeSet.contains(name.toUpperCase());
    }

    private String merge(String template, VelocityContext context) {
        String message = "failed to merge \"" + template + "\"";
        try {
            return VelocityEngineer.merge(context, template).toString();
        } catch (Exception ex) {
            throw new RuntimeException(message, ex);
        }
    }

    private void setUpdatableColumns(SqlAid aid) {
        _updatableColumns = null; // by default, all columns must be considered updatable
        _checkUpdatableColumns = false;
        PreparedStatementWrapper selectUpdatableColumns = aid.getSelectUpdatableColumnsStatement();
        if (selectUpdatableColumns != null) {
            try {
                String table_name, column_name;
                ResultSet resultSet = selectUpdatableColumns.executeQuery();
                boolean next = resultSet.next();
                if (next) {
                    _updatableColumns = new ArrayList<>();
                    _checkUpdatableColumns = true; // not empty
                    do {
                        table_name = resultSet.getString(1);
                        column_name = resultSet.getString(2);
                        _updatableColumns.add(updatableColumnName(table_name, column_name));
                    } while (resultSet.next());
                }
            } catch (SQLException ex) {
                _updatableColumns = null; // if an exception is caught, all columns must be considered updatable
                _checkUpdatableColumns = false;
                logger.warn(ThrowableUtils.getString(ex) + "\n" + selectUpdatableColumns);
                logger.warn("all columns will be considered updatable");
            }
        }
    }

    private boolean isUpdatableColumn(String table, String column) {
        return _updatableColumns == null || (_checkUpdatableColumns && _updatableColumns.contains(updatableColumnName(table, column)));
    }

    private String updatableColumnName(String table, String column) {
        return table + "." + column;
    }

    // <editor-fold defaultstate="collapsed" desc="SqlAid">
    protected abstract class SqlAid {

        protected abstract boolean createDefaults() throws SQLException;

        protected abstract boolean dropDefaults() throws SQLException;

        protected PreparedStatementWrapper getSelectTablesStatement() {
            String template = getTemplateName("tables");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectColumnsStatement() {
            String template = getTemplateName("columns-v5r3");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectCKsStatement() {
            String template = getTemplateName("checks");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectPKsStatement() {
            String template = getTemplateName("PK-constraints");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectUKsStatement() {
            String template = getTemplateName("UK-constraints");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectFKsStatement() {
            String template = getTemplateName("FK-constraints");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectIXsStatement() {
            String template = getTemplateName("IX-constraints");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectIndexesStatement() {
            String template = getTemplateName("indexes");
            return getSelectStatement(template);
        }

        /*
        protected PreparedStatementWrapper getSelectTabsStatement() {
            String template = getTemplateName("tabs");
            return getSelectStatement(template);
        }

        /**/
        protected PreparedStatementWrapper getSelectRoutinesStatement() {
            String template = getTemplateName("routines");
            return getSelectStatement(template);
        }

        protected abstract PreparedStatementWrapper getSelectRowsStatement(SqlTable sqlTable);

        protected PreparedStatementWrapper getSelectUpdatableColumnsStatement() {
            String template = getTemplateName("updatable-columns");
            return getSelectStatement(template);
        }

        protected PreparedStatementWrapper getSelectStatement(String template) {
            VelocityContext context = new VelocityContext();
            return getSelectStatement(template, context);
        }

        protected PreparedStatementWrapper getSelectStatement(String template, SqlTable sqlTable) {
            VelocityContext context = new VelocityContext();
            String name = sqlTable.getName();
            context.put("table", name);
            return getSelectStatement(template, context);
        }

        protected PreparedStatementWrapper getSelectStatement(String template, VelocityContext context) {
            context.put("database", _database);
            context.put("schema", _schema);
            String templatePath;
            File templateFile;
            String[] fileResourceLoaderPathArray = VelocityEngineer.getFileResourceLoaderPathArray();
            for (String path : fileResourceLoaderPathArray) {
                templatePath = path + FILE_SEPARATOR + template.replaceAll("/", "\\" + FILE_SEPARATOR);
                templateFile = new File(templatePath);
                if (templateFile.exists()) {
                    logger.info("select-template=" + templatePath);
                    String string = merge(template, context);
                    if (StringUtils.startsWithIgnoreCase(string, "select")) {
                        String sql = string.replace("\r\n", " ");
                        return prepareSelectStatement(sql);
                    }
                    logger.info("template file \"" + template + "\" does not contain a SQL select statement");
                    return null;
                }
            }
            logger.warn("template file \"" + template + "\" is missing");
            return null;
        }

        protected PreparedStatementWrapper prepareSelectStatement(String statement) {
            return new PreparedStatementWrapper(statement);
        }

        protected String getTemplateName(String type) {
            return _selectTemplatesPath + "/" + _dbms + "/select-" + type + ".vm";
        }

        protected String getSqlTableName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }

        protected SqlTable getSqlTable(ResultSet resultSet) throws SQLException {
            SqlTable sqlTable = new SqlTable(SqlReader.this);
            sqlTable.setName(resultSet.getString(1));
            return sqlTable;
        }

        protected String getSqlColumnTableName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(15);
        }

        protected String getSqlColumnName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }

        protected SqlColumn getSqlColumn(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            SqlColumn sqlColumn = new SqlColumn(sqlTable);
            sqlColumn.setName(resultSet.getString(1));
            return sqlColumn;
        }

        protected String getSqlIndexName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }

        protected SqlIndex getSqlIndex(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            SqlIndex sqlIndex = new SqlIndex(sqlTable);
            sqlIndex.setName(resultSet.getString(1));
            return sqlIndex;
        }

        protected SqlIndex getSqlIndex(ResultSet resultSet, SqlIndex sqlIndex) throws SQLException {
            return sqlIndex;
        }

        protected String getSqlTabName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }

        protected SqlTab getSqlTab(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            SqlTab sqlTab = new SqlTab(sqlTable);
            sqlTab.setName(resultSet.getString(1));
            return sqlTab;
        }

        protected SqlTab getSqlTab(ResultSet resultSet, SqlTab sqlTab) throws SQLException {
            return sqlTab;
        }

        protected String getSqlRowName(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            int position = sqlTable.getBusinessKey().getPosition();
            return resultSet.getString(position);
        }

        protected SqlRow getSqlRow(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            int position = sqlTable.getBusinessKey().getPosition();
            String name = resultSet.getString(position);
            SqlRow sqlRow = new SqlRow(sqlTable);
            sqlRow.setName(name);
            String column;
            String type;
            Object object;
            String string;
            SqlRowValue sqlRowValue;
            for (SqlColumn sqlColumn : sqlTable.getColumns()) {
                column = sqlColumn.getName();
                type = sqlColumn.getType();
                object = switch (type) {
                    case "boolean", "char", "string", "byte", "short", "integer", "long", "decimal", "float", "double" ->
                        resultSet.getObject(sqlColumn.getPosition());
                    case "clob" ->
                        resultSet.getClob(sqlColumn.getPosition());
                    case "date" ->
                        resultSet.getDate(sqlColumn.getPosition());
                    case "time" ->
                        resultSet.getTime(sqlColumn.getPosition());
                    case "timestamp" ->
                        resultSet.getTimestamp(sqlColumn.getPosition());
                    default ->
                        null;
                };
                if (object != null) {
                    string = StrUtils.getString(object);
                    sqlRowValue = new SqlRowValue(sqlRow, sqlColumn);
                    sqlRowValue.setName(column);
                    sqlRowValue.setObject(object);
                    sqlRowValue.setValue(string);
                    sqlRow.add(sqlRowValue);
                }
            }
            return sqlRow;
        }

        protected String getSqlRoutineName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }

        protected SqlRoutine getSqlRoutine(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            SqlRoutine sqlRoutine = new SqlRoutine(sqlTable);
            sqlRoutine.setName(resultSet.getString(1));
            return sqlRoutine;
        }

        protected SqlRoutine getSqlRoutine(ResultSet resultSet, SqlRoutine sqlRoutine) throws SQLException {
            return sqlRoutine;
        }

        protected String getSqlCheckConstraintTableName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }

        protected SqlCheckConstraint getSqlCheckConstraint(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            SqlCheckConstraint sqlCheckConstraint = new SqlCheckConstraint(sqlTable);
            sqlCheckConstraint.setTableName(resultSet.getString(1));
            sqlCheckConstraint.setName(resultSet.getString(2));
            sqlCheckConstraint.setType(resultSet.getString(3));
            sqlCheckConstraint.setConstraintDefinition(resultSet.getString(4));
            return sqlCheckConstraint;
        }

        protected String getSqlConstraintIndexTableName(ResultSet resultSet) throws SQLException {
            return resultSet.getString(1);
        }

        protected SqlConstraintIndex getSqlConstraintIndex(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            SqlConstraintIndex sqlConstraintIndex = new SqlConstraintIndex(sqlTable);
            sqlConstraintIndex.setTableName(resultSet.getString(1));
            sqlConstraintIndex.setName(resultSet.getString(2));
            sqlConstraintIndex.setType(resultSet.getString(3));
            sqlConstraintIndex.setCreateStatement(resultSet.getString(4));
            sqlConstraintIndex.setDropStatement(resultSet.getString(5));
            return sqlConstraintIndex;
        }

        protected void finalize(SqlTable sqlTable) {
            for (SqlColumn sqlColumn : sqlTable.getColumns()) {
                finalizeColumn(sqlColumn);
            }
            for (SqlRow sqlRow : sqlTable.getRows()) {
                for (SqlRowValue sqlRowValue : sqlRow.getValues()) {
                    finalizeRowValue(sqlRowValue);
                }
            }
            for (SqlRoutine sqlRoutine : sqlTable.getRoutines()) {
                for (SqlRoutineParameter sqlRoutineParameter : sqlRoutine.getParameters()) {
                    finalizeParameter(sqlRoutineParameter);
                }
            }
        }

        protected void finalizeColumn(SqlColumn sqlColumn) {
            String foreignTableName = sqlColumn.getForeignTableName();
            if (foreignTableName != null && _tables.containsKey(foreignTableName)) {
                sqlColumn.setForeignTable(_tables.get(foreignTableName));
            }
            // foreign table must be set before executing stringValueOf
            sqlColumn.setDefault(stringValueOf(sqlColumn, sqlColumn.getDefault()));
        }

        protected void finalizeRowValue(SqlRowValue sqlRowValue) {
            sqlRowValue.setValue(stringValueOf(sqlRowValue.getColumn(), sqlRowValue.getValue()));
            sqlRowValue.setLiteral(literalOf(sqlRowValue.getObject()));
        }

        protected void finalizeParameter(SqlRoutineParameter sqlRoutineParameter) {
            sqlRoutineParameter.setDefault(stringValueOf(sqlRoutineParameter, sqlRoutineParameter.getDefault()));
        }

        protected int intValueOf(Object object) {
//          return object instanceof Integer ? (Integer) object : 0;
            return IntUtils.valueOf(NumUtils.newInteger(object));
        }

        protected boolean booleanValueOf(Object object) {
//          return object instanceof Boolean ? (Boolean) object : false;
            return BitUtils.valueOf(object);
        }

        protected abstract String literalOf(Object obj);

        protected abstract String literalOf(SqlColumn sqlColumn, String string);

        protected abstract String defaultOf(SqlColumn sqlColumn);

        protected String stringValueOf(SqlColumn sqlColumn, String string) {
            String ctype = sqlColumn.getType();
            String value = StringUtils.trimToNull(string);
            return value == null ? null
                : sqlColumn.getForeignTable() != null ? instanceValueOf(sqlColumn, string)
                : sqlColumn.isForeign() ? null
                : ctype.equals("date") ? null
                : ctype.equals("time") ? null
                : ctype.equals("timestamp") ? null
                : value;
        }

        protected String stringValueOf(SqlRoutineParameter sqlRoutineParameter, String string) {
            String ctype = sqlRoutineParameter.getType();
            SqlColumn namesake = sqlRoutineParameter.getNamesakeColumn();
            String value = StringUtils.trimToNull(string);
            return value == null ? null
                : sqlRoutineParameter.getForeignTable() != null ? instanceValueOf(namesake, string)
                : sqlRoutineParameter.isForeign() ? null
                : ctype.equals("date") ? null
                : ctype.equals("time") ? null
                : ctype.equals("timestamp") ? null
                : value;
        }

        protected String instanceValueOf(SqlColumn sqlColumn, String string) {
            String value = StringUtils.trimToNull(string);
            if (value != null) {
                SqlTable foreignTable = sqlColumn.getForeignTable();
                if (foreignTable != null) {
                    SqlColumn primaryKey = foreignTable.getPrimaryKey();
                    if (primaryKey != null) {
                        for (SqlRow sqlRow : foreignTable.getRows()) {
                            for (SqlRowValue sqlRowValue : sqlRow.getValues()) {
                                if (primaryKey.equals(sqlRowValue.getColumn())) {
                                    if (value.equals(sqlRowValue.getValue())) {
                                        return sqlColumn.getDecapitalizedJavaName() + "." + sqlRow.getJavaConstantName();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }

    }

    class OracleAid extends SqlAid {

        @Override
        protected boolean createDefaults() throws SQLException {
            String statement = "call " + _schema + "." + "create_defaults()";
            return executeStatement(statement, false);
        }

        @Override
        protected boolean dropDefaults() throws SQLException {
            String statement = "call " + _schema + "." + "drop_defaults()";
            return executeStatement(statement, false);
        }

        @Override
        protected PreparedStatementWrapper getSelectRowsStatement(SqlTable sqlTable) {
            String name = sqlTable.getName();
            String pk = sqlTable.getPrimaryKey().getName();
            String sql = "select * from " + _schema + "." + name + " where ROWNUM<=" + ROW_LIMIT + " order by " + pk;
            logger.debug(sql);
            return prepareSelectStatement(sql);
        }

        @Override
        protected SqlTable getSqlTable(ResultSet resultSet) throws SQLException {
            String table_name = resultSet.getString(1);
            String default_label = resultSet.getString(2);
            String default_collection_label = resultSet.getString(3);
            String resource_type = resultSet.getString(4);
            boolean is_enumerable = booleanValueOf(resultSet.getObject(5));
            boolean is_insertable = booleanValueOf(resultSet.getObject(6));
            boolean is_updatable = booleanValueOf(resultSet.getObject(7));
            boolean is_deletable = booleanValueOf(resultSet.getObject(8));
            boolean is_independent = booleanValueOf(resultSet.getObject(9));
            /**/
            SqlTable sqlTable = new SqlTable(SqlReader.this);
            sqlTable.setName(table_name);
            sqlTable.setDefaultLabel(default_label);
            sqlTable.setDefaultCollectionLabel(default_collection_label);
            sqlTable.setResourceType(resource_type);
            sqlTable.setEnumerable(is_enumerable);
            sqlTable.setInsertable(is_insertable);
            sqlTable.setUpdatable(is_updatable);
            sqlTable.setDeletable(is_deletable);
            sqlTable.setIndependent(is_independent);
            return sqlTable;
        }

        @Override
        protected SqlColumn getSqlColumn(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String table_name = sqlTable.getName();
            String column_name = resultSet.getString(1);
            int ordinal_position = intValueOf(resultSet.getObject(2));
            String data_type = StringUtils.trimToEmpty(resultSet.getString(3));
            int character_maximum_length = intValueOf(resultSet.getObject(4));
            int numeric_precision = intValueOf(resultSet.getObject(5));
            int numeric_scale = intValueOf(resultSet.getObject(6));
            int datetime_precision = intValueOf(resultSet.getObject(7));
            String column_default = StrUtils.disclose(StringUtils.trimToNull(resultSet.getString(8)), '(', ')');
            String string_default = stringValueOf(column_default);
            final boolean is_updatable = isUpdatableColumn(table_name, column_name);
            boolean is_nullable = booleanValueOf(resultSet.getObject(9));
            boolean is_primary_key = booleanValueOf(resultSet.getObject(10));
            boolean is_unique_key = booleanValueOf(resultSet.getObject(11));
            boolean is_booleanish = booleanValueOf(resultSet.getObject(12));
            String foreign_table_name = StringUtils.trimToNull(resultSet.getString(13));
            String default_label = resultSet.getString(14);
            /**/
            SqlColumn sqlColumn = new SqlColumn(sqlTable);
            sqlColumn.setName(column_name);
            sqlColumn.setPosition(ordinal_position);
            sqlColumn.setDefaultLabel(default_label);
            // <editor-fold defaultstate="collapsed" desc="switch(data_type)...">
            data_type = data_type.replaceAll("\\(.*\\)", "");
            sqlColumn.setSqlDataType(data_type);
            sqlColumn.setSqlType(data_type);
            sqlColumn.setType(data_type);
            switch (data_type) {
                case "number" -> {
                    if (numeric_precision > 0) {
                        if (numeric_scale == 0) {
                            if (numeric_precision <= 3) {
                                sqlColumn.setType("byte");
                                sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                            } else if (numeric_precision <= 5) {
                                sqlColumn.setType("short");
                                sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                            } else if (numeric_precision <= 10) {
                                sqlColumn.setType("integer");
                                sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                            } else if (numeric_precision <= 19) {
                                sqlColumn.setType("long");
                                sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                            } else {
                                sqlColumn.setType("decimal");
                                sqlColumn.setPrecision(numeric_precision);
                                sqlColumn.setScale(numeric_scale);
                                if (numeric_precision > 0) {
                                    if (numeric_scale > 0) {
                                        sqlColumn.setSqlType(data_type + "(" + numeric_precision + ", " + numeric_scale + ")");
                                    } else {
                                        sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                                    }
                                }
                            }
                        } else {
                            sqlColumn.setType("decimal");
                            sqlColumn.setPrecision(numeric_precision);
                            sqlColumn.setScale(numeric_scale);
                            if (numeric_scale > 0) {
                                sqlColumn.setSqlType(data_type + "(" + numeric_precision + ", " + numeric_scale + ")");
                            } else {
                                sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                            }
                        }
                    }
                }
                case "blob", "bytea" ->
                    sqlColumn.setType("blob");
                case "\"char\"" -> {
                    sqlColumn.setType("char");
                    sqlColumn.setSqlType("char(1)");
                }
                case "char", "character", "character varying", "nchar", "nvarchar", "nvarchar2", "varchar", "varchar2", "text" -> {
                    sqlColumn.setType("string");
                    sqlColumn.setLength(character_maximum_length);
                    if (character_maximum_length > 0) {
                        sqlColumn.setSqlType(data_type + "(" + character_maximum_length + ")");
                    }
                }
                case "smallint", "smallserial" ->
                    sqlColumn.setType("short");
                case "integer", "serial" ->
                    sqlColumn.setType("integer");
                case "bigint", "bigserial" ->
                    sqlColumn.setType("long");
                case "decimal", "numeric", "money" -> {
                    sqlColumn.setType("decimal");
                    sqlColumn.setPrecision(numeric_precision);
                    sqlColumn.setScale(numeric_scale);
                    if (numeric_precision > 0) {
                        if (numeric_scale > 0) {
                            sqlColumn.setSqlType(data_type + "(" + numeric_precision + ", " + numeric_scale + ")");
                        } else {
                            sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                        }
                    }
                }
                case "real" ->
                    sqlColumn.setType("float");
                case "double precision" ->
                    sqlColumn.setType("double");
                case "date" ->
                    sqlColumn.setType("date");
                case "timestamp", "timestamp with time zone", "timestamp without time zone" -> {
                    sqlColumn.setType("timestamp");
                    sqlColumn.setPrecision(datetime_precision);
                    if (datetime_precision > 0) {
                        int i = data_type.indexOf(" ");
                        if (i < 0) {
                            sqlColumn.setSqlType(data_type + "(" + datetime_precision + ")");
                        } else {
                            sqlColumn.setSqlType(data_type.substring(0, i) + "(" + datetime_precision + ")" + data_type.substring(i));
                        }
                    }
                }
            }
            // </editor-fold>
            sqlColumn.setDefault(string_default);
            sqlColumn.setSqlDefaultValue(column_default);
            sqlColumn.setSqlDefaultValueLiteral(literalOf(sqlColumn, string_default));
            sqlColumn.setSqlPrimalDefaultValue(defaultOf(sqlColumn));
            sqlColumn.setUpdatable(is_updatable);
            sqlColumn.setNullable(is_nullable);
            sqlColumn.setPrimary(is_primary_key);
            sqlColumn.setUnique(is_unique_key);
            sqlColumn.setBooleanish(is_booleanish);
            if (foreign_table_name != null) {
                sqlColumn.setForeign(true);
                sqlColumn.setForeignTableName(foreign_table_name);
            }
            return sqlColumn;
        }

        @Override
        protected String literalOf(Object obj) {
            String string = unquotedLiteralOf(obj);
            if (string == null) {
                return "null";
            } else if (obj instanceof Number) {
                return string;
            } else if (obj instanceof Date) {
                return "date" + StrUtils.enclose(string, '\'');
            } else if (obj instanceof Time) {
                return "timestamp" + StrUtils.enclose(string, '\'');
            } else if (obj instanceof java.util.Date) {
                return "timestamp" + StrUtils.enclose(string, '\'');
            } else {
                return StrUtils.enclose(string, '\'');
            }
        }

        @Override
        protected String literalOf(SqlColumn sqlColumn, String string) {
            String type = sqlColumn.getType();
            if (type == null || string == null) {
                return "null";
            }
            return switch (type) {
                case "byte", "short", "integer", "long", "decimal", "float", "double" ->
                    string;
                case "boolean", "char", "string" ->
                    StrUtils.enclose(string, '\'');
                case "date" ->
                    "date" + StrUtils.enclose(string, '\'');
                case "time" ->
                    "timestamp" + StrUtils.enclose(string, '\'');
                case "timestamp" ->
                    "timestamp" + StrUtils.enclose(string, '\'');
                default ->
                    "null";
            };
        }

        @Override
        protected String defaultOf(SqlColumn sqlColumn) {
            String type = sqlColumn.getType();
            if (type == null) {
                return "null";
            }
            return switch (type) {
                case "byte", "short", "integer", "long", "decimal", "float", "double" ->
                    "0";
                case "boolean", "char", "string" ->
                    "'?'";
                case "date" ->
                    "current_date";
                case "time" ->
                    "localtimestamp";
                case "timestamp" ->
                    "localtimestamp";
                default ->
                    "null";
            };
        }

        protected String unquotedLiteralOf(Object obj) {
            if (obj == null) {
                return null;
            } else if (obj instanceof Clob clob) {
                return StrUtils.getSubString(clob);
            } else if (obj instanceof Date) {
                return TimeUtils.jdbcDateString(obj);
            } else if (obj instanceof Time) {
                return TimeUtils.jdbcTimestampString(obj);
            } else if (obj instanceof java.util.Date) {
                return TimeUtils.jdbcTimestampString(obj);
            } else {
                return obj.toString();
            }
        }

        protected String stringValueOf(String string) {
            String trimmed = StringUtils.trimToNull(string);
            if (trimmed == null) {
                return null;
            }
            trimmed = StrUtils.disclose(trimmed, '(', ')');
            if (StringUtils.startsWithIgnoreCase(trimmed, "null")) {
                return null;
            }
            if (StringUtils.startsWithIgnoreCase(trimmed, "nextval")) {
                return null;
            }
            if (StringUtils.containsIgnoreCase(trimmed, "'now'")) {
                return "now()";
            }
            int i = trimmed.indexOf("::");
            if (i > 0) {
                trimmed = trimmed.substring(0, i);
            }
            return StrUtils.disclose(trimmed, '\'');
        }

        @Override
        protected String stringValueOf(SqlColumn sqlColumn, String string) {
            String trimmed = sqlColumn.isForeign() ? null : StringUtils.trimToNull(string);
            String value = trimmed == null ? null : stringValueOf(trimmed.toLowerCase(), sqlColumn.getTrueType());
            return value == null ? super.stringValueOf(sqlColumn, string) : value;
        }

        @Override
        protected SqlIndex getSqlIndex(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String index_name = resultSet.getString(1);
            boolean is_unique = booleanValueOf(resultSet.getObject(2));
            /**/
            SqlIndex sqlIndex = new SqlIndex(sqlTable);
            sqlIndex.setName(index_name);
            sqlIndex.setUnique(is_unique);
            return getSqlIndex(resultSet, sqlIndex);
        }

        @Override
        protected SqlIndex getSqlIndex(ResultSet resultSet, SqlIndex sqlIndex) throws SQLException {
            int ordinal_position = intValueOf(resultSet.getObject(3));
            String column_name = resultSet.getString(4);
            String column_option = resultSet.getString(5);
            /**/
            SqlIndexColumn sqlIndexColumn = new SqlIndexColumn(sqlIndex, sqlIndex.getTable().getSqlColumn(column_name));
            sqlIndexColumn.setName(column_name);
            sqlIndexColumn.setPosition(ordinal_position);
            sqlIndexColumn.setOption(column_option);
            sqlIndex.add(sqlIndexColumn);
            return sqlIndex;
        }

        @Override
        protected SqlTab getSqlTab(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String tab_name = resultSet.getString(1);
            String default_label = resultSet.getString(2);
            /**/
            SqlTab sqlTab = new SqlTab(sqlTable);
            sqlTab.setName(tab_name);
            sqlTab.setDefaultLabel(default_label);
            return getSqlTab(resultSet, sqlTab);
        }

        @Override
        protected SqlTab getSqlTab(ResultSet resultSet, SqlTab sqlTab) throws SQLException {
            String column_name = resultSet.getString(3);
            /**/
            SqlTabColumn sqlTabColumn = new SqlTabColumn(sqlTab, sqlTab.getTable().getSqlColumn(column_name));
            sqlTabColumn.setName(column_name);
            sqlTab.add(sqlTabColumn);
            return sqlTab;
        }

        @Override
        protected SqlRoutine getSqlRoutine(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String routine_name = resultSet.getString(1);
            String routine_type = resultSet.getString(2);
            /**/
            SqlRoutine sqlRoutine = new SqlRoutine(sqlTable);
            sqlRoutine.setName(routine_name);
            sqlRoutine.setOperationType(routine_type);
            return getSqlRoutine(resultSet, sqlRoutine);
        }

        @Override
        protected SqlRoutine getSqlRoutine(ResultSet resultSet, SqlRoutine sqlRoutine) throws SQLException {
            String parameter_name = resultSet.getString(3);
            if (StringUtils.isBlank(parameter_name)) {
                return sqlRoutine;
            }
            String data_type = StringUtils.trimToEmpty(resultSet.getString(4));
            boolean is_required = booleanValueOf(resultSet.getObject(5));
            boolean is_hidden = booleanValueOf(resultSet.getObject(6));
            String parameter_default = StringUtils.trimToNull(resultSet.getString(7));
            /**/
            SqlRoutineParameter sqlRoutineParameter = new SqlRoutineParameter(sqlRoutine);
            sqlRoutineParameter.setName(parameter_name);
            // <editor-fold defaultstate="collapsed" desc="switch(data_type)...">
            switch (data_type) {
                case "boolean" ->
                    sqlRoutineParameter.setType("boolean");
                case "\"char\"" ->
                    sqlRoutineParameter.setType("char");
                case "char", "character", "character varying", "nchar", "nvarchar", "nvarchar2", "varchar", "varchar2", "text" ->
                    sqlRoutineParameter.setType("string");
                case "smallint", "smallserial" ->
                    sqlRoutineParameter.setType("short");
                case "integer" -> {
                    if (StringUtils.startsWithIgnoreCase(parameter_name, "es_")) {
                        sqlRoutineParameter.setType("boolean");
                        parameter_default = "" + booleanValueOf(parameter_default);
                    } else {
                        sqlRoutineParameter.setType("integer");
                    }
                }
                case "serial" ->
                    sqlRoutineParameter.setType("integer");
                case "bigint", "bigserial" ->
                    sqlRoutineParameter.setType("long");
                case "decimal", "numeric", "money" ->
                    sqlRoutineParameter.setType("decimal");
                case "real" ->
                    sqlRoutineParameter.setType("float");
                case "double precision" ->
                    sqlRoutineParameter.setType("double");
                case "date" ->
                    sqlRoutineParameter.setType("date");
                case "time", "time with time zone", "time without time zone" ->
                    sqlRoutineParameter.setType("time");
                case "timestamp", "timestamp with time zone", "timestamp without time zone" ->
                    sqlRoutineParameter.setType("timestamp");
                default ->
                    sqlRoutineParameter.setType("string");
            }
            // </editor-fold>
            sqlRoutineParameter.setSqlDataType(data_type);
            sqlRoutineParameter.setRequired(is_required);
            sqlRoutineParameter.setHidden(is_hidden);
            sqlRoutineParameter.setDefault(parameter_default);
            sqlRoutineParameter.setSqlDefaultValue(parameter_default);
            sqlRoutine.add(sqlRoutineParameter);
            return sqlRoutine;
        }

        @Override
        protected String stringValueOf(SqlRoutineParameter sqlRoutineParameter, String string) {
            String trimmed = sqlRoutineParameter.isForeign() ? null : StringUtils.trimToNull(string);
            String value = trimmed == null ? null : stringValueOf(trimmed.toLowerCase(), sqlRoutineParameter.getTrueType());
            return value == null ? super.stringValueOf(sqlRoutineParameter, string) : value;
        }

        protected String stringValueOf(String string, String type) {
            return string == null || type == null ? null
                : string.equals("current_date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("current_time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("current_timestamp") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("current_timestamp") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("current_timestamp") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("localtime") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("localtimestamp") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("localtimestamp") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("localtimestamp") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("now()") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("now()") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("now()") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("getdate()") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("getdate()") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("getdate()") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("getuser()") && type.equals("long") ? "SpecialNumericValue.CURRENT_USER_ID"
                : string.equals("getuser()") && type.equals("string") ? "SpecialCharacterValue.CURRENT_USER"
                : null;
        }

    }

    class PostgreSqlAid extends SqlAid {

        @Override
        protected boolean createDefaults() throws SQLException {
            String statement = "select " + _schema + "." + "create_defaults()" + ";";
            return executeStatement(statement, false);
        }

        @Override
        protected boolean dropDefaults() throws SQLException {
            String statement = "select " + _schema + "." + "drop_defaults()" + ";";
            return executeStatement(statement, false);
        }

        @Override
        protected PreparedStatementWrapper getSelectRowsStatement(SqlTable sqlTable) {
            String name = sqlTable.getName();
            String pk = sqlTable.getPrimaryKey().getName();
            String sql = "select * from " + _schema + "." + name + " order by " + pk + " limit " + ROW_LIMIT;
            logger.debug(sql);
            return prepareSelectStatement(sql);
        }

        @Override
        protected SqlTable getSqlTable(ResultSet resultSet) throws SQLException {
            String table_name = resultSet.getString(1);
            String default_label = resultSet.getString(2);
            String default_collection_label = resultSet.getString(3);
            String resource_type = resultSet.getString(4);
            boolean is_enumerable = booleanValueOf(resultSet.getObject(5));
            boolean is_insertable = booleanValueOf(resultSet.getObject(6));
            boolean is_updatable = booleanValueOf(resultSet.getObject(7));
            boolean is_deletable = booleanValueOf(resultSet.getObject(8));
            boolean is_independent = booleanValueOf(resultSet.getObject(9));
            /**/
            SqlTable sqlTable = new SqlTable(SqlReader.this);
            sqlTable.setName(table_name);
            sqlTable.setDefaultLabel(default_label);
            sqlTable.setDefaultCollectionLabel(default_collection_label);
            sqlTable.setResourceType(resource_type);
            sqlTable.setEnumerable(is_enumerable);
            sqlTable.setInsertable(is_insertable);
            sqlTable.setUpdatable(is_updatable);
            sqlTable.setDeletable(is_deletable);
            sqlTable.setIndependent(is_independent);
            return sqlTable;
        }

        @Override
        protected SqlColumn getSqlColumn(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String table_name = sqlTable.getName();
            String column_name = resultSet.getString(1);
            int ordinal_position = intValueOf(resultSet.getObject(2));
            String data_type = StringUtils.trimToEmpty(resultSet.getString(3));
            int character_maximum_length = intValueOf(resultSet.getObject(4));
            int numeric_precision = intValueOf(resultSet.getObject(5));
            int numeric_scale = intValueOf(resultSet.getObject(6));
            int datetime_precision = intValueOf(resultSet.getObject(7));
            String column_default = StringUtils.trimToNull(resultSet.getString(8));
            String string_default = stringValueOf(column_default);
            final boolean is_updatable = isUpdatableColumn(table_name, column_name);
            boolean is_nullable = booleanValueOf(resultSet.getObject(9));
            boolean is_primary_key = booleanValueOf(resultSet.getObject(10));
            boolean is_unique_key = booleanValueOf(resultSet.getObject(11));
            boolean is_booleanish = booleanValueOf(resultSet.getObject(12));
            String foreign_table_name = StringUtils.trimToNull(resultSet.getString(13));
            String default_label = resultSet.getString(14);
            /**/
            SqlColumn sqlColumn = new SqlColumn(sqlTable);
            sqlColumn.setName(column_name);
            sqlColumn.setPosition(ordinal_position);
            sqlColumn.setDefaultLabel(default_label);
            // <editor-fold defaultstate="collapsed" desc="switch(data_type)...">
            data_type = data_type.replaceAll("\\(.*\\)", "");
            sqlColumn.setSqlDataType(data_type);
            sqlColumn.setSqlType(data_type);
            sqlColumn.setType(data_type);
            switch (data_type) {
                case "boolean" ->
                    sqlColumn.setType("boolean");
                case "blob", "bytea" ->
                    sqlColumn.setType("blob");
                case "\"char\"" -> {
                    sqlColumn.setType("char");
                    sqlColumn.setSqlType("char(1)");
                }
                case "char", "character", "character varying", "varchar", "text" -> {
                    sqlColumn.setType("string");
                    sqlColumn.setLength(character_maximum_length);
                    if (character_maximum_length > 0) {
                        sqlColumn.setSqlType(data_type + "(" + character_maximum_length + ")");
                    }
                }
                case "smallint", "smallserial" ->
                    sqlColumn.setType("short");
                case "integer", "serial" ->
                    sqlColumn.setType("integer");
                case "bigint", "bigserial" ->
                    sqlColumn.setType("long");
                case "decimal", "numeric", "money" -> {
                    sqlColumn.setType("decimal");
                    sqlColumn.setPrecision(numeric_precision);
                    sqlColumn.setScale(numeric_scale);
                    if (numeric_precision > 0) {
                        if (numeric_scale > 0) {
                            sqlColumn.setSqlType(data_type + "(" + numeric_precision + ", " + numeric_scale + ")");
                        } else {
                            sqlColumn.setSqlType(data_type + "(" + numeric_precision + ")");
                        }
                    }
                }
                case "real" ->
                    sqlColumn.setType("float");
                case "double precision" ->
                    sqlColumn.setType("double");
                case "date" ->
                    sqlColumn.setType("date");
                case "time", "time with time zone", "time without time zone" -> {
                    sqlColumn.setType("time");
                    sqlColumn.setPrecision(datetime_precision);
                    if (datetime_precision > 0) {
                        int i = data_type.indexOf(" ");
                        if (i < 0) {
                            sqlColumn.setSqlType(data_type + "(" + datetime_precision + ")");
                        } else {
                            sqlColumn.setSqlType(data_type.substring(0, i) + "(" + datetime_precision + ")" + data_type.substring(i));
                        }
                    }
                }
                case "timestamp", "timestamp with time zone", "timestamp without time zone" -> {
                    sqlColumn.setType("timestamp");
                    sqlColumn.setPrecision(datetime_precision);
                    if (datetime_precision > 0) {
                        int i = data_type.indexOf(" ");
                        if (i < 0) {
                            sqlColumn.setSqlType(data_type + "(" + datetime_precision + ")");
                        } else {
                            sqlColumn.setSqlType(data_type.substring(0, i) + "(" + datetime_precision + ")" + data_type.substring(i));
                        }
                    }
                }
            }
            // </editor-fold>
            sqlColumn.setDefault(string_default);
            sqlColumn.setSqlDefaultValue(column_default);
            sqlColumn.setSqlDefaultValueLiteral(literalOf(sqlColumn, string_default));
            sqlColumn.setSqlPrimalDefaultValue(defaultOf(sqlColumn));
            sqlColumn.setUpdatable(is_updatable);
            sqlColumn.setNullable(is_nullable);
            sqlColumn.setPrimary(is_primary_key);
            sqlColumn.setUnique(is_unique_key);
            sqlColumn.setBooleanish(is_booleanish);
            if (foreign_table_name != null) {
                sqlColumn.setForeign(true);
                sqlColumn.setForeignTableName(foreign_table_name);
            }
            return sqlColumn;
        }

        @Override
        protected String literalOf(Object obj) {
            String string = unquotedLiteralOf(obj);
            if (string == null) {
                return "null";
            } else if (obj instanceof Boolean) {
                return string;
            } else if (obj instanceof Number) {
                return string;
            } else if (obj instanceof Date) {
                return "date" + StrUtils.enclose(string, '\'');
            } else if (obj instanceof Time) {
                return "time" + StrUtils.enclose(string, '\'');
            } else if (obj instanceof java.util.Date) {
                return "timestamp" + StrUtils.enclose(string, '\'');
            } else {
                return StrUtils.enclose(string, '\'');
            }
        }

        @Override
        protected String literalOf(SqlColumn sqlColumn, String string) {
            String type = sqlColumn.getType();
            if (type == null || string == null) {
                return "null";
            }
            return switch (type) {
                case "boolean", "byte", "short", "integer", "long", "decimal", "float", "double" ->
                    string;
                case "char", "string" ->
                    StrUtils.enclose(string, '\'');
                case "date" ->
                    "date" + StrUtils.enclose(string, '\'');
                case "time" ->
                    "time" + StrUtils.enclose(string, '\'');
                case "timestamp" ->
                    "timestamp" + StrUtils.enclose(string, '\'');
                default ->
                    "null";
            };
        }

        @Override
        protected String defaultOf(SqlColumn sqlColumn) {
            String type = sqlColumn.getType();
            if (type == null) {
                return "null";
            }
            return switch (type) {
                case "boolean" ->
                    "false";
                case "byte", "short", "integer", "long", "decimal", "float", "double" ->
                    "0";
                case "char", "string" ->
                    "'?'";
                case "date" ->
                    "current_date";
                case "time" ->
                    "localtime";
                case "timestamp" ->
                    "localtimestamp";
                default ->
                    "null";
            };
        }

        protected String unquotedLiteralOf(Object obj) {
            if (obj == null) {
                return null;
            } else if (obj instanceof Clob clob) {
                return StrUtils.getSubString(clob);
            } else if (obj instanceof Date) {
                return TimeUtils.jdbcDateString(obj);
            } else if (obj instanceof Time) {
                return TimeUtils.jdbcTimeString(obj);
            } else if (obj instanceof java.util.Date) {
                return TimeUtils.jdbcTimestampString(obj);
            } else {
                return obj.toString();
            }
        }

        protected String stringValueOf(String string) {
            String trimmed = StringUtils.trimToNull(string);
            if (trimmed == null) {
                return null;
            }
            if (StringUtils.startsWithIgnoreCase(trimmed, "null")) {
                return null;
            }
            if (StringUtils.startsWithIgnoreCase(trimmed, "nextval")) {
                return null;
            }
            if (StringUtils.containsIgnoreCase(trimmed, "'now'")) {
                return "now()";
            }
            int i = trimmed.indexOf("::");
            if (i > 0) {
                trimmed = trimmed.substring(0, i);
            }
            return StrUtils.disclose(trimmed, '\'');
        }

        @Override
        protected String stringValueOf(SqlColumn sqlColumn, String string) {
            String trimmed = sqlColumn.isForeign() ? null : StringUtils.trimToNull(string);
            String value = trimmed == null ? null : stringValueOf(trimmed.toLowerCase(), sqlColumn.getTrueType());
            return value == null ? super.stringValueOf(sqlColumn, string) : value;
        }

        @Override
        protected SqlIndex getSqlIndex(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String index_name = resultSet.getString(1);
            boolean is_unique = booleanValueOf(resultSet.getObject(2));
            /**/
            SqlIndex sqlIndex = new SqlIndex(sqlTable);
            sqlIndex.setName(index_name);
            sqlIndex.setUnique(is_unique);
            return getSqlIndex(resultSet, sqlIndex);
        }

        @Override
        protected SqlIndex getSqlIndex(ResultSet resultSet, SqlIndex sqlIndex) throws SQLException {
            int ordinal_position = intValueOf(resultSet.getObject(3));
            String column_name = resultSet.getString(4);
            String column_option = resultSet.getString(5);
            /**/
            SqlIndexColumn sqlIndexColumn = new SqlIndexColumn(sqlIndex, sqlIndex.getTable().getSqlColumn(column_name));
            sqlIndexColumn.setName(column_name);
            sqlIndexColumn.setPosition(ordinal_position);
            sqlIndexColumn.setOption(column_option);
            sqlIndex.add(sqlIndexColumn);
            return sqlIndex;
        }

        @Override
        protected SqlTab getSqlTab(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String tab_name = resultSet.getString(1);
            String default_label = resultSet.getString(2);
            /**/
            SqlTab sqlTab = new SqlTab(sqlTable);
            sqlTab.setName(tab_name);
            sqlTab.setDefaultLabel(default_label);
            return getSqlTab(resultSet, sqlTab);
        }

        @Override
        protected SqlTab getSqlTab(ResultSet resultSet, SqlTab sqlTab) throws SQLException {
            String column_name = resultSet.getString(3);
            /**/
            SqlTabColumn sqlTabColumn = new SqlTabColumn(sqlTab, sqlTab.getTable().getSqlColumn(column_name));
            sqlTabColumn.setName(column_name);
            sqlTab.add(sqlTabColumn);
            return sqlTab;
        }

        @Override
        protected SqlRoutine getSqlRoutine(ResultSet resultSet, SqlTable sqlTable) throws SQLException {
            String routine_name = resultSet.getString(1);
            String routine_type = resultSet.getString(2);
            /**/
            SqlRoutine sqlRoutine = new SqlRoutine(sqlTable);
            sqlRoutine.setName(routine_name);
            sqlRoutine.setOperationType(routine_type);
            return getSqlRoutine(resultSet, sqlRoutine);
        }

        @Override
        protected SqlRoutine getSqlRoutine(ResultSet resultSet, SqlRoutine sqlRoutine) throws SQLException {
            String parameter_name = resultSet.getString(3);
            if (StringUtils.isBlank(parameter_name)) {
                return sqlRoutine;
            }
            String data_type = StringUtils.trimToEmpty(resultSet.getString(4));
            boolean is_required = booleanValueOf(resultSet.getObject(5));
            boolean is_hidden = booleanValueOf(resultSet.getObject(6));
            String parameter_default = StringUtils.trimToNull(resultSet.getString(7));
            /**/
            SqlRoutineParameter sqlRoutineParameter = new SqlRoutineParameter(sqlRoutine);
            sqlRoutineParameter.setName(parameter_name);
            // <editor-fold defaultstate="collapsed" desc="switch(data_type)...">
            switch (data_type) {
                case "boolean" ->
                    sqlRoutineParameter.setType("boolean");
                case "\"char\"" ->
                    sqlRoutineParameter.setType("char");
                case "char", "character", "character varying", "varchar", "text" ->
                    sqlRoutineParameter.setType("string");
                case "smallint", "smallserial" ->
                    sqlRoutineParameter.setType("short");
                case "integer", "serial" ->
                    sqlRoutineParameter.setType("integer");
                case "bigint", "bigserial" ->
                    sqlRoutineParameter.setType("long");
                case "decimal", "numeric", "money" ->
                    sqlRoutineParameter.setType("decimal");
                case "real" ->
                    sqlRoutineParameter.setType("float");
                case "double precision" ->
                    sqlRoutineParameter.setType("double");
                case "date" ->
                    sqlRoutineParameter.setType("date");
                case "time", "time with time zone", "time without time zone" ->
                    sqlRoutineParameter.setType("time");
                case "timestamp", "timestamp with time zone", "timestamp without time zone" ->
                    sqlRoutineParameter.setType("timestamp");
                default ->
                    sqlRoutineParameter.setType("string");
            }
            // </editor-fold>
            sqlRoutineParameter.setSqlDataType(data_type);
            sqlRoutineParameter.setRequired(is_required);
            sqlRoutineParameter.setHidden(is_hidden);
            sqlRoutineParameter.setDefault(parameter_default);
            sqlRoutineParameter.setSqlDefaultValue(parameter_default);
            sqlRoutine.add(sqlRoutineParameter);
            return sqlRoutine;
        }

        @Override
        protected String stringValueOf(SqlRoutineParameter sqlRoutineParameter, String string) {
            String trimmed = sqlRoutineParameter.isForeign() ? null : StringUtils.trimToNull(string);
            String value = trimmed == null ? null : stringValueOf(trimmed.toLowerCase(), sqlRoutineParameter.getTrueType());
            return value == null ? super.stringValueOf(sqlRoutineParameter, string) : value;
        }

        protected String stringValueOf(String string, String type) {
            return string == null || type == null ? null
                : string.equals("current_date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("current_time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("current_timestamp") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("current_timestamp") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("current_timestamp") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("localtime") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("localtimestamp") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("localtimestamp") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("localtimestamp") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("now()") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("now()") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("now()") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("getdate()") && type.equals("date") ? "SpecialTemporalValue.CURRENT_DATE"
                : string.equals("getdate()") && type.equals("time") ? "SpecialTemporalValue.CURRENT_TIME"
                : string.equals("getdate()") && type.equals("timestamp") ? "SpecialTemporalValue.CURRENT_TIMESTAMP"
                : string.equals("getuser()") && type.equals("long") ? "SpecialNumericValue.CURRENT_USER_ID"
                : string.equals("getuser()") && type.equals("string") ? "SpecialCharacterValue.CURRENT_USER"
                : null;
        }

    }
    // </editor-fold>

}
