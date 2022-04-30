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
package adalid.core.enums;

/**
 * @author Jorge Campins
 */
public enum ExpressionUsage {

    INDETERMINATE,
    SELECT_FILTER, INSERT_FILTER, UPDATE_FILTER, DELETE_FILTER,
    RENDERING_FILTER, REQUIRING_FILTER, MODIFYING_FILTER, NULLIFYING_FILTER, SEARCH_QUERY_FILTER,
    CALCULABLE_FIELD_VALUE, CALCULABLE_QUERY_PROPERTY_VALUE,
    CURRENT_VALUE, DEFAULT_VALUE, INITIAL_VALUE,
    CHECK, SEGMENT, STATE

}
