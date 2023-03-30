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
import adalid.core.interfaces.Entity;
import java.util.List;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public class Form extends Display {

    public Form(String name) {
        super(name);
        init();
    }

    private void init() {
        setDisplayType(DisplayType.FORM);
    }

    /**
     * @return the fields list
     */
    @Override
    public List<FormField> getFields() {
        return null;
    }

    /**
     * @return the master heading fields list
     */
    @Override
    public List<FormField> getMasterHeadingFields() {
        return null;
    }

    /**
     * @return the list of entities referenced by fields
     */
    @Override
    public Set<Entity> getEntitiesReferencedByFields() {
        return null;
    }

}
