/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.util.sql;

import adalid.commons.velocity.Writer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlWriter extends SqlReader {

    private static final Logger logger = Logger.getLogger(SqlWriter.class);

    public SqlWriter(String[] args) {
        super(args);
        init();
    }

    private void init() {
        setSelectTemplatesPath("templates/meta/sql");
    }

    private String _projectAlias;

    public String getProjectAlias() {
        return _projectAlias == null ? defaultProjectAlias() : _projectAlias;
    }

    public void setProjectAlias(String alias) {
        if (StringUtils.isBlank(alias)) {
            logger.warn("null value for alias parameter; project alias remains " + getProjectAlias());
        } else if (!alias.matches("^[a-z][a-z0-9]*$")) {
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
        } else if (!alias.matches("^[a-z][a-z0-9]*$")) {
            logger.error(alias + " is an invalid project alias; generation aborted");
            return false;
        } else if (alias.equalsIgnoreCase("meta") || alias.equalsIgnoreCase("workspace")) {
            logger.error(alias + " is a restricted project alias; generation aborted");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getTargetMetajavaPackage() {
        return getDefaultPackage() + "." + getProjectAlias();
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

}
