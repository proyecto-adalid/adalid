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
package adalid.core.interfaces;

import adalid.commons.connections.*;
import adalid.core.enums.*;

/**
 * @author Jorge Campins
 */
public interface DatabaseEntity extends Entity {

    /**
     * @return the database entity type
     */
    DatabaseEntityType getDatabaseEntityType();

    /**
     * @return the database connection
     */
    DatabaseConnection getDatabaseConnection();

    /**
     * El método setDatabaseConnection permite establecer el objeto para conexión a la base de datos en la que se encuentra la tabla correspondiente a
     * la entidad.
     *
     * @param connection conexión a la base de datos
     */
    void setDatabaseConnection(DatabaseConnection connection);

    /**
     * @return the schema
     */
    String getSchema();

    /**
     * El método setSchema se utiliza para establecer el nombre del esquema de la base de datos en el que se define la tabla correspondiente a la
     * entidad.
     *
     * @param schema nombre del esquema
     */
    void setSchema(String schema);

}
