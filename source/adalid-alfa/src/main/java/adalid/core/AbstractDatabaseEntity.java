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

import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
public abstract class AbstractDatabaseEntity extends AbstractEntity implements DatabaseEntity {

    private DatabaseEntityType _databaseEntityType;

    private String _schema;

    /**
     * @return the database entity type
     */
    @Override
    public DatabaseEntityType getDatabaseEntityType() {
        return _databaseEntityType;
    }

    /**
     * @param databaseEntityType the database entity type to set
     */
    void setDatabaseEntityType(DatabaseEntityType databaseEntityType) {
        _databaseEntityType = databaseEntityType;
    }

    /**
     * @return the schema
     */
    @Override
    public String getSchema() {
        return _schema;
    }

    /**
     * El método setSchema se utiliza para establecer el nombre del esquema de la base de datos en el que se define la tabla correspondiente a la
     * entidad.
     *
     * @param schema nombre del esquema
     */
    @Override
    public void setSchema(String schema) {
        _schema = schema;
    }

    public AbstractDatabaseEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

}
