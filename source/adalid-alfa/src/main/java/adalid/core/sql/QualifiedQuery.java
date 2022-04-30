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
package adalid.core.sql;

import adalid.core.enums.*;

/**
 * @author Jorge Campins
 */
public class QualifiedQuery {

    public static QualifiedQuery of(SqlQualifierType qualifier, String statement) {
        return new QualifiedQuery(qualifier, statement);
    }

    private QualifiedQuery(SqlQualifierType qualifier, String statement) {
        _type = qualifier;
        _statement = statement;
    }

    private final SqlQualifierType _type;

    public SqlQualifierType getType() {
        return _type;
    }

    private final String _statement;

    public String getStatement() {
        return _statement;
    }

}
