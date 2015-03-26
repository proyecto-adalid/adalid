/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.enums;

/**
 * @author Jorge Campins
 */
public enum VirtualEntityType {

    SELECTION, LINE, STAR, STAR_LINE, STARRY_LINE, SNOWFLAKE, QUERY

}

/*
 * SELECTION: a single table view.
 *
 * LINE: a table hierarquical line, up to its root.
 *
 * STAR: a table joined with all its directly linked tables.
 *
 * STAR_LINE: a table joined with all its directly linked tables plus the tables in its hierarquical line.
 *
 * STARRY_LINE: a table hierarquical line joined with all tables directly linked to any table of the line.
 *
 * SNOWFLAKE: a table joined with all its directly or indirectly linked tables.
 *
 * QUERY: any other kind of view, defined by a sql query.
 */
