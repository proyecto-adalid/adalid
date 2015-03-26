/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.programmers;

import adalid.commons.util.StrUtils;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.BundleProgrammer;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.SqlProgrammer;

/**
 * @author Jorge Campins
 */
public abstract class AbstractBundleProgrammer extends AbstractProgrammer implements BundleProgrammer {

    @Override
    public String getSqlSchemaQualifiedKey(PersistentEntity entity) {
        String qualifier = getSqlSchemaName(entity);
        String name = getKeyString(entity);
        return StrUtils.getQualifiedName(name, qualifier);
    }

    @Override
    public String getSqlSchemaQualifiedShortKey(PersistentEntity entity) {
        String qualifier = getSqlSchemaName(entity);
        String name = getKeyString(entity);
        return StrUtils.getQualifiedShortName(name, qualifier);
    }

    @Override
    public String getSqlSchemaUnqualifiedShortKey(PersistentEntity entity) {
        String qualifier = getSqlSchemaName(entity);
        String name = getKeyString(entity);
        return StrUtils.getUnqualifiedShortName(name, qualifier);
    }

    private String getSqlSchemaName(PersistentEntity entity) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSchemaName(entity);
    }

    @Override
    public String getKeyString(Artifact artifact) {
        String string = artifact == null ? null : artifact.getName();
        return string == null ? null : getKeyString(string);
    }

    @Override
    public String getKeyString(String string) {
        return StrUtils.getLowerHumplessCase(string);
    }

    @Override
    public String getValueString(String string) {
        return StrUtils.getStringJava(string);
    }

}
