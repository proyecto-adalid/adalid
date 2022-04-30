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
package adalid.core.programmers;

import adalid.commons.util.*;
import adalid.core.interfaces.*;
import org.apache.commons.lang.StringUtils;

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
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String strong = StrUtils.getStringJava(string);
        String strung = StringUtils.replace(strong, "\\b", "\\u0008");
        return strung;
    }

}
