/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.util.sql;

import adalid.commons.velocity.Writer;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlWriter extends SqlReader {

    private static final Logger logger = Logger.getLogger(SqlWriter.class);

    public SqlWriter(String[] args) {
        super(args);
        setSelectTemplatesPath("templates/meta/sql");
    }

    public boolean write() {
        logger.info("write");
        if (read(true)) {
            Writer writer = new Writer(this, "reader");
            writer.write("meta-java-sql");
            return true;
        }
        return false;
    }

}
