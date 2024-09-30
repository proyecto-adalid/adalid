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

import adalid.commons.connections.*;
import adalid.commons.interfaces.*;
import adalid.commons.util.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class AbstractDatabaseEntity extends AbstractEntity implements DatabaseEntity {

    private DatabaseEntityType _databaseEntityType;

    private DatabaseConnection _databaseConnection;

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
     * @return the database connection
     */
    @Override
    public DatabaseConnection getDatabaseConnection() {
        return _databaseConnection;
    }

    /**
     * El método setDatabaseConnection permite establecer el objeto para conexión a la base de datos en la que se encuentra la tabla correspondiente a
     * la entidad.
     *
     * @param connection conexión a la base de datos
     */
    @Override
    public void setDatabaseConnection(DatabaseConnection connection) {
        checkScope();
        if (connection != null) {
            String name = StrUtils.getLowerCaseIdentifier(connection.getName(), '-');
            if (StringUtils.isBlank(name)) {
                _databaseConnection = null;
            } else {
                Project project = TLC.getProject();
                if (project instanceof DatabaseProject databaseProject) {
                    if (name.equalsIgnoreCase(databaseProject.getDatabaseName())) {
                        _databaseConnection = null;
                    } else {
                        _databaseConnection = connection;
                        databaseProject.addExtraDatabaseConnection(connection);
                    }
                }
            }
        }
    }

    /**
     * @return the schema
     */
    @Override
    public String getSchema() {
        return _schema != null ? _schema : _databaseConnection != null ? _databaseConnection.getSchema() : null;
    }

    /**
     * El método setSchema se utiliza para establecer el nombre del esquema de la base de datos en el que se define la tabla correspondiente a la
     * entidad.
     *
     * @param schema nombre del esquema
     */
    @Override
    public void setSchema(String schema) {
        checkScope();
        _schema = schema;
    }

    @Override
    public boolean isSqlCodeGenEnabled() {
        return super.isSqlCodeGenEnabled() && _databaseConnection == null;
    }

    public AbstractDatabaseEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

}
