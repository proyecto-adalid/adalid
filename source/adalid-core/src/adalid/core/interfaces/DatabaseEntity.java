/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.enums.DatabaseEntityType;

/**
 * @author Jorge Campins
 */
public interface DatabaseEntity extends Entity {

    /**
     * @return the database entity type
     */
    DatabaseEntityType getDatabaseEntityType();

    /**
     * @return the schema
     */
    String getSchema();

    /**
     * @param schema the schema to set
     */
    void setSchema(String schema);

}
