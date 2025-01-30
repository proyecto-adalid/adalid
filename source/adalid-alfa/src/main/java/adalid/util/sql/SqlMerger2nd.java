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

/**
 * @author Jorge Campins
 */
public class SqlMerger2nd extends SqlMerger {

    public SqlMerger2nd() {
        super();
    }

    public SqlMerger2nd(String[] args) {
        super(args);
    }

    @Override
    protected String old() {
        return "new";
    }

    @Override
    protected String getSyntax() {
        return getSqlUtilSyntax() + ", new schema, [new host], [new port], [new user], [new password], [new database], [project alias]";
    }

    @Override
    protected String[] reader1_args() {
        return super.reader2_args();
    }

    @Override
    protected String[] reader2_args() {
        return super.reader1_args();
    }

    @Override
    protected String platform() {
        return "meta-sql-" + _dbms + "-2nd";
    }

}
