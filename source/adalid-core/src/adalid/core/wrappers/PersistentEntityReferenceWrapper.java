/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.wrappers;

import adalid.core.interfaces.PersistentEntityReference;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;

/**
 * @author Jorge Campins
 */
public class PersistentEntityReferenceWrapper extends PersistentEntityWrapper {

    private PersistentEntityReference _reference;

    private PersistentEntityReferenceWrapper() {
        this(null);
    }

    public PersistentEntityReferenceWrapper(PersistentEntityReference entity) {
        super(entity);
        _reference = entity;
    }

    @Override
    public PersistentEntityReference getWrapped() {
        return _reference;
    }

    /**
     * @return the onDeleteAction
     */
    public String getSqlOnDeleteAction() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlOnDeleteAction(_reference);
    }

    /**
     * @return the onUpdateAction
     */
    public String getSqlOnUpdateAction() {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        return sqlProgrammer == null ? null : sqlProgrammer.getSqlOnUpdateAction(_reference);
    }

}
