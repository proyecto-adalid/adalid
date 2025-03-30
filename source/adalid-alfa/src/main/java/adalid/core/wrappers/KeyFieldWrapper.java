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

import adalid.core.*;
import adalid.core.interfaces.*;
import adalid.core.programmers.*;

/**
 * @author Jorge Campins
 */
public class KeyFieldWrapper extends ArtifactWrapper {

    private final KeyField _keyField;

    public KeyFieldWrapper(KeyField keyField) {
        super(keyField);
        _keyField = keyField;
    }

    @Override
    public KeyField getWrapped() {
        return _keyField;
    }

    @Override
    public String getSqlName() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlName(_keyField.getProperty());
    }

    /**
     * @return the SQL sort option
     */
    public String getSqlSortOption() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSortOption(_keyField.getSortOption());
    }

}
