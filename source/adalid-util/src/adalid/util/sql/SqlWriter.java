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
        _alias = StringUtils.lowerCase(getDatabase());
        setSelectTemplatesPath("templates/meta/sql");
    }

    private String _alias;

    public String getAlias() {
        return _alias;
    }

    public void setAlias(String alias) {
        _alias = alias;
    }

    @Override
    public String getTargetMetajavaPackage() {
        return getDefaultPackage() + "." + getAlias();
    }

    public boolean write() {
        logger.info("write");
        String alias = getAlias();
        if (StringUtils.isBlank(alias)) {
            logger.error("invalid project alias; generation aborted");
            return false;
        } else if (!alias.matches("^[a-z][a-z0-9]*$")) {
            logger.error(alias + " is an invalid project alias; generation aborted");
            return false;
        } else if (alias.equalsIgnoreCase("meta") || alias.equalsIgnoreCase("workspace")) {
            logger.error(alias + " is a restricted project alias; generation aborted");
            return false;
        } else if (alias.equalsIgnoreCase("jee1ap101")) {
            logger.error("project alias matches platform name; generation aborted");
            return false;
        }
        if (read(true)) {
            Writer writer = new Writer(this, "reader");
            writer.write("meta-java-sql");
            return true;
        }
        return false;
    }

}
