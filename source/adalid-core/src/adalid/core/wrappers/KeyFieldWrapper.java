/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.wrappers;

import adalid.core.KeyField;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;

/**
 * @author Jorge Campins
 */
public class KeyFieldWrapper extends ArtifactWrapper {

    private KeyField _keyField;

    private KeyFieldWrapper() {
        this(null);
    }

    public KeyFieldWrapper(KeyField keyField) {
        super(keyField);
        _keyField = keyField;
    }

    @Override
    public KeyField getWrapped() {
        return _keyField;
    }

    /**
     * @return the SQL sort option
     */
    public String getSqlSortOption() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlSortOption(_keyField.getSortOption());
    }

}
