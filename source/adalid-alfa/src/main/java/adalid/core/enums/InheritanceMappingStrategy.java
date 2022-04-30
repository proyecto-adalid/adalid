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
public enum InheritanceMappingStrategy {

    UNSPECIFIED,
    /**
     * SingleTable per Class Hierarchy Strategy.
     *
     * All classes in the hierarchy are mapped to a single table in the database. This table has a discriminator column, a column that contains a
     * value that identifies the subclass to which the instance represented by the row belongs.
     */
    SINGLE_TABLE,
    /**
     * Joined Subclass Strategy.
     *
     * The root of the class hierarchy is represented by a single table, and each subclass has a separate table that only contains those fields
     * specific to that subclass. That is, the subclass table does not contain columns for inherited fields or properties. The subclass table also has
     * a column or columns that represent its primary key, which is a foreign key to the primary key of the superclass table.
     */
    JOINED,
    /**
     * Table per Concrete Class Strategy.
     *
     * Each concrete class is mapped to a separate table in the database. All fields or properties in the class, including inherited fields or
     * properties, are mapped to columns in the class table in the database.
     */
    TABLE_PER_CLASS

};
