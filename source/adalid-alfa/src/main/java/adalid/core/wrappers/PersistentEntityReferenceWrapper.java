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

/**
 * @author Jorge Campins
 */
public class PersistentEntityReferenceWrapper extends PersistentEntityWrapper {

    private final PersistentEntityReference _reference;

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
