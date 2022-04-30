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
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class NativeQuery {

    public static NativeQuery of(QualifiedQuery... qualifiedQueries) {
        return new NativeQuery(qualifiedQueries);
    }

    public static NativeQuery of(String unqualifiedStatement) {
        return new NativeQuery(unqualifiedStatement);
    }

    NativeQuery(QualifiedQuery... qualifiedQueries) {
        if (qualifiedQueries != null && qualifiedQueries.length > 0) {
            for (QualifiedQuery qq : qualifiedQueries) {
                String statement = qq.getStatement();
                if (StringUtils.isNotBlank(statement)) {
                    SqlQualifierType type = qq.getType();
                    if (type != null) {
                        _qualifiedQueries.put(type, statement);
                    } else {
                        _unqualifiedStatement = statement;
                    }
                }
            }
        }
    }

    NativeQuery(String unqualifiedStatement) {
        _unqualifiedStatement = unqualifiedStatement;
    }

    private final Map<SqlQualifierType, String> _qualifiedQueries = new LinkedHashMap<>();

    public String getString(SqlQualifierType qualifier) {
        return StringUtils.defaultIfBlank(_qualifiedQueries.get(qualifier), _unqualifiedStatement);
    }

    private String _unqualifiedStatement;

    public String getString() {
        return _unqualifiedStatement;
    }

}
