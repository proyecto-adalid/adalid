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
import adalid.util.Platform;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlWriter extends SqlReader {

    private static final Logger logger = Logger.getLogger(SqlWriter.class);

    public SqlWriter() {
        super();
        init();
    }

    public SqlWriter(String[] args) {
        super(args);
        init();
    }

    private void init() {
//      setSelectTemplatesPath("templates/meta/sql");
        setSelectTemplatesPath("templates/meta/java/sql/select/010");
    }

    private String _projectAlias;

    public String getProjectAlias() {
        return _projectAlias == null ? defaultProjectAlias() : _projectAlias;
    }

    public String getProjectShortAlias() {
        return _projectAlias == null ? defaultProjectShortAlias() : _projectAlias;
    }

    private static final String PROJECT_ALIAS_PATTERN = "^[a-z][a-z0-9]*$";

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

    private String defaultProjectShortAlias() {
        boolean oracle = StringUtils.equalsIgnoreCase(_dbms, "oracle");
        return (oracle ? _schema : _database).toLowerCase();
    }

    private String defaultProjectAlias() {
        final String MAVEN = "1", POSTGRESQL = "0", ORACLE = "1", GLASSFISH = "1", WILDFLY = "2";
        boolean oracle = StringUtils.equalsIgnoreCase(_dbms, "oracle");
        return defaultProjectShortAlias() + MAVEN + (oracle ? ORACLE : POSTGRESQL)
            + (StringUtils.containsIgnoreCase(getProjectPlatform(), "glassfish") ? GLASSFISH : WILDFLY);
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

    private String _projectPlatform;

    public String getProjectPlatform() {
        return _projectPlatform == null ? defaultProjectPlatform() : _projectPlatform;
    }

    /* TODO: enable this setter
    public void setProjectPlatform(String platform) {
        _projectPlatform = platform;
    }

    /**/
    private String defaultProjectPlatform() {
        final String[] array = {"PLATAFORMA", "MAVEN", _dbms.toUpperCase(), "WILDFLY"};
        return StringUtils.join(array, '_');
    }

    private boolean _createAndDropDefaults;

    /**
     * @return the create and drop defaults indicator
     */
    public boolean isCreateAndDropDefaults() {
        return _createAndDropDefaults;
    }

    /**
     * @param b the create and drop defaults indicator to set
     */
    public void setCreateAndDropDefaults(boolean b) {
        _createAndDropDefaults = b;
    }

    @Override
    public String getTargetMetajavaPackage() {
        return getDefaultPackage() + "." + getProjectShortAlias();
    }

    private String _targetMetaProjectsPackage;

    public String getTargetMetaProjectsPackage() {
        return StringUtils.isNotBlank(_targetMetaProjectsPackage) ? _targetMetaProjectsPackage : getTargetMetajavaPackage();
    }

    public void setTargetMetaProjectsPackage(String packageName) {
        _targetMetaProjectsPackage = packageName;
    }

    private String _targetMetaEntitiesPackage;

    public String getTargetMetaEntitiesPackage() {
        return StringUtils.isNotBlank(_targetMetaEntitiesPackage) ? _targetMetaEntitiesPackage : getTargetMetajavaPackage() + ".entities";
    }

    public void setTargetMetaEntitiesPackage(String packageName) {
        _targetMetaEntitiesPackage = packageName;
    }

    private static final String COLLISION_SUFFIX = "1d3c5";

    public String getCollisionSuffix() {
        return COLLISION_SUFFIX;
    }

    public Set<String> getJavaKeywords() {
        return JavaUtils.getJavaKeywordSet();
    }

    public int getTableNameError(String tabname) {
        if (StringUtils.isBlank(tabname)) {
            logger.error("missing table name");
            return -1;
        } else if (tabname.length() < 2) {
            logger.error(tabname + " must be renamed; " + tabname + " is too short an artifact name");
            return 1;
        } else if (!tabname.matches("^[A-Z][a-z]\\w*$")) {
            logger.error(tabname + " must be renamed; " + tabname + " is an invalid artifact name (it does not begin with [A-Z][a-z])");
            return 2;
        }
        return 0;
    }

    public int getColumnNameError(String tabname, String colname) {
        if (StringUtils.isBlank(tabname)) {
            logger.error("missing table name");
            return -1;
        } else if (StringUtils.isBlank(colname)) {
            logger.error("missing column name");
            return -1;
        } else if (colname.length() < 2) {
            logger.error(tabname + "." + colname + " must be renamed; " + colname + " is too short an artifact name");
            return 1;
        } else if (!colname.matches("^[a-zA-Z]\\w*$")) {
            logger.error(tabname + "." + colname + " must be renamed; " + colname + " is an invalid artifact name (it does not begin with [a-zA-Z])");
            return 2;
        } else if (colname.matches("^[a-z][A-Z].*$")) {
            logger.error(tabname + "." + colname + " must be renamed; " + colname + " is an invalid artifact name (it begins with [a-z][A-Z])");
            return 3;
        } else if (colname.matches("^[A-Z][A-Z]+[a-z].*$")) {
            logger.error(tabname + "." + colname + " must be renamed; " + colname + " is an invalid artifact name (it begins with [A-Z][A-Z]+[a-z])");
            return 4;
        }
        return 0;
    }

    private int _maxTablePrefixLength;

    public int isMaxTablePrefixLength() {
        return _maxTablePrefixLength;
    }

    public void setMaxTablePrefixLength(int prefixLength) {
        _maxTablePrefixLength = prefixLength;
    }

    private final Map<String, Set<SqlTable>> _modules = new TreeMap<>();

    public Set<String> getModules() {
        if (_modules.isEmpty()) {
            fillModules();
        }
        return _modules.keySet();
    }

    private static final String PREFIJO_MODULO = "Modulo";

    private static final String MODULO_UNO = "Modulo1";

    private void fillModules() {
        Set<SqlTable> set;
        String prefix;
        String module = MODULO_UNO;
        Map<String, SqlTable> tables = getTablesMap();
        Set<String> tableNames = tables.keySet();
        for (String name : tableNames) {
            if (_maxTablePrefixLength > 0) {
                prefix = StringUtils.substringBefore(name, "_").toUpperCase();
                module = prefix.length() > _maxTablePrefixLength ? MODULO_UNO : PREFIJO_MODULO + prefix;
            }
            if (_modules.containsKey(module)) {
                set = _modules.get(module);
            } else {
                set = new LinkedHashSet<>();
                _modules.put(module, set);
            }
            SqlTable table = tables.get(name);
            set.add(table);
        }
    }

    public Collection<SqlTable> getTables(String module) {
        return _modules.get(module);
    }

    public String getModuleID(String module) {
        return StringUtils.substringAfter(module, PREFIJO_MODULO);
    }

    public boolean write() {
        logger.info("write");
        boolean ok = checkProjectAlias();
        if (ok) {
            if (read(true)) {
                Writer writer = new Writer(this, "reader");
                writer.write(Platform.META_JAVA_SQL);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean beforeReadBuild() {
        if (_createAndDropDefaults) {
            try {
                getSqlAid().createDefaults();
            } catch (SQLException ex) {
            }
        }
        return true;
    }

    @Override
    protected boolean beforeReadClose() {
        if (_createAndDropDefaults) {
            try {
                getSqlAid().dropDefaults();
            } catch (SQLException ex) {
            }
        }
        return true;
    }

}
