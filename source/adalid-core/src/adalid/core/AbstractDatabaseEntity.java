/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.DatabaseEntityType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.DatabaseEntity;
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
     * @param schema the schema to set
     */
    @Override
    public void setSchema(String schema) {
        _schema = schema;
    }

    public AbstractDatabaseEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

}
