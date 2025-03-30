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
package adalid.core.wrappers;

import adalid.core.interfaces.*;
import adalid.core.programmers.*;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class PersistentEntityWrapper extends EntityWrapper {

    private final PersistentEntity _entity;

    public PersistentEntityWrapper(PersistentEntity entity) {
        super(entity);
        _entity = entity;
    }

    @Override
    public PersistentEntity getWrapped() {
        return _entity;
    }

    /**
     * @return the discriminator value
     */
    public String getSqlDiscriminatorValue() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlDiscriminatorValue(_entity);
    }

    /**
     * @return the discriminator values
     */
    public List<String> getSqlDiscriminatorValues() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlDiscriminatorValues(_entity);
    }

    /**
     * @return the schema name
     */
    public String getSqlSchemaName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaName(_entity);
    }

    /**
     * @return the table name
     */
    public String getSqlTableName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlTableName(_entity);
    }
//
//  /**
//   * @return the base table name
//   */
//  public String getSqlBaseTableName() {
//      SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
//      return sqlProgrammer == null ? null : sqlProgrammer.getSqlBaseTableName(_entity);
//  }

    public String getSqlSchemaQualifier() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifier(_entity);
    }

    public String getSqlSchemaQualifiedBundleKey() {
        BundleProgrammer bundleProgrammer = ChiefProgrammer.getBundleProgrammer();
        return bundleProgrammer == null ? null : bundleProgrammer.getSqlSchemaQualifiedKey(_entity);
    }

    public String getSqlSchemaQualifiedShortBundleKey() {
        BundleProgrammer bundleProgrammer = ChiefProgrammer.getBundleProgrammer();
        return bundleProgrammer == null ? null : bundleProgrammer.getSqlSchemaQualifiedShortKey(_entity);
    }

    public String getSqlSchemaUnqualifiedShortBundleKey() {
        BundleProgrammer bundleProgrammer = ChiefProgrammer.getBundleProgrammer();
        return bundleProgrammer == null ? null : bundleProgrammer.getSqlSchemaUnqualifiedShortKey(_entity);
    }

    public String getSqlSchemaQualifiedName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedName(_entity);
    }

    public String getSqlSchemaQualifiedShortName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortName(_entity);
    }

    public String getSqlSchemaUnqualifiedShortName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaUnqualifiedShortName(_entity);
    }

    public String getSqlSchemaQualifiedTableName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedTableName(_entity);
    }

    public String getSqlSchemaQualifiedShortTableName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaQualifiedShortTableName(_entity);
    }

    public String getSqlSchemaUnqualifiedShortTableName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaUnqualifiedShortTableName(_entity);
    }

}
