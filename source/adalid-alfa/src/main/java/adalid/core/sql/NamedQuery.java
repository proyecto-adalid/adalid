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

/**
 * @author Jorge Campins
 */
public class NamedQuery extends NativeQuery {

    static final String NAMED_QUERY_FUNCTION = "NamedQuery";

    public static String getJavaExpressionOfSqlExpression(String sqlExpression) {
        return sqlExpression == null ? null : sqlExpression.replaceAll(NAMED_QUERY_FUNCTION + "\\('(\\w+)'\\)", "(\" + $1() + \")");
    }

    public static NamedQuery of(String queryName) {
        return new NamedQuery(queryName);
    }

    public static NamedQuery of(String queryName, String unqualifiedStatement) {
        return new NamedQuery(queryName, unqualifiedStatement);
    }

    public static NamedQuery of(String queryName, QualifiedQuery... qualifiedQueries) {
        return new NamedQuery(queryName, qualifiedQueries);
    }

    NamedQuery(String queryName) {
        super(NAMED_QUERY_FUNCTION + "('" + queryName + "')");
        this.queryName = queryName;
    }

    NamedQuery(String queryName, String unqualifiedStatement) {
        super(unqualifiedStatement);
        this.queryName = queryName;
    }

    NamedQuery(String queryName, QualifiedQuery... qualifiedQueries) {
        super(qualifiedQueries);
        this.queryName = queryName;
    }

    private final String queryName;

    public String getQueryName() {
        return queryName;
    }

}
