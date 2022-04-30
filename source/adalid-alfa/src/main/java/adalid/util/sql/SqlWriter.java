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

import adalid.commons.velocity.*;
import java.sql.SQLException;
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

    private static String PROJECT_ALIAS_PATTERN = "^[a-z][a-z0-9]*$";

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
        return getDefaultPackage() + "." + getProjectAlias();
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

    public boolean write() {
        logger.info("write");
        boolean ok = checkProjectAlias();
        if (ok) {
            if (read(true)) {
                Writer writer = new Writer(this, "reader");
                writer.write("meta-java-sql");
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
