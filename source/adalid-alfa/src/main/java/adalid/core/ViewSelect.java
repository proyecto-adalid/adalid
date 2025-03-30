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
package adalid.core;

import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ViewSelect {

    private final boolean _distinct;

    private final List<String> _columns;

    private final String _from;

    private final List<String> _filters;

    private final List<String> _groupBy;

    private final List<String> _orderBy;

    ViewSelect(boolean distinct, List<String> columns, String from, List<String> filters, List<String> groupBy, List<String> orderBy) {
        _distinct = distinct;
        _columns = columns;
        _from = from;
        _filters = filters;
        _groupBy = groupBy;
        _orderBy = orderBy;
    }

    /**
     * @return the distinct indicator
     */
    public boolean isDistinct() {
        return _distinct;
    }

    /**
     * @return the columns
     */
    public List<String> getColumns() {
        return _columns;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return _from;
    }

    /**
     * @return the filters
     */
    public List<String> getFilters() {
        return _filters;
    }

    /**
     * @return the groupBy
     */
    public List<String> getGroupBy() {
        return _groupBy;
    }

    /**
     * @return the orderBy
     */
    public List<String> getOrderBy() {
        return _orderBy;
    }

    /**
     * @return the statement
     */
    public String getStatement() {
        return "SELECT " + (_distinct ? "DISTINCT " : "")
            + columns(_columns) + " FROM " + _from
            + filters(_filters)
            + groupBy(_groupBy)
            + orderBy(_orderBy);
    }

    private String columns(List<String> list) {
        return list == null || list.isEmpty() ? "*" : StringUtils.join(list, ", ");
    }

    private String filters(List<String> list) {
        return list == null || list.isEmpty() ? "" : " WHERE (" + StringUtils.join(list, " AND ") + ") ";
    }

    private String groupBy(List<String> list) {
        return list == null || list.isEmpty() ? "" : " GROUP BY " + StringUtils.join(list, ", ");
    }

    private String orderBy(List<String> list) {
        return list == null || list.isEmpty() ? "" : " ORDER BY " + StringUtils.join(list, ", ");
    }

}
