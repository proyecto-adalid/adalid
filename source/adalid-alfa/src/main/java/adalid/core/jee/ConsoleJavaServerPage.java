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
package adalid.core.jee;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.interfaces.*;
import adalid.core.predicates.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jorge Campins
 */
public class ConsoleJavaServerPage extends JavaServerPage {

    public ConsoleJavaServerPage(String name) {
        super(name);
    }

    private List<PageField> _fields;

    private List<PageField> _masterFields;

    private Set<Entity> _entitiesReferencedByFields;

    /**
     * @param hidden whether hidden fields should be included in the list or not
     * @return the fields list
     */
    @Override
    public List<PageField> getFields(boolean hidden) {
        if (_fields == null) {
            _fields = new ArrayList<>();
        }
        return _fields;
    }

    /**
     * @param hidden whether hidden fields should be included in the list or not
     * @return the fields list
     */
    @Override
    public List<PageField> getMasterHeadingFields(boolean hidden) {
        if (_masterFields == null) {
            _masterFields = new ArrayList<>();
        }
        return _masterFields;
    }

    /**
     * @param hidden whether hidden fields should be included in the set or not
     * @return the list of entities referenced by fields
     */
    @Override
    public Set<Entity> getEntitiesReferencedByFields(boolean hidden) {
        if (_entitiesReferencedByFields == null) {
            _entitiesReferencedByFields = new LinkedHashSet<>();
            Entity entity = getEntity();
            if (entity != null) {
                Collection<Operation> operations = ColUtils.filter(entity.getBusinessOperationsList(), new IsAccesibleOperation());
                if (operations != null && !operations.isEmpty()) {
                    for (Operation operation : operations) {
                        for (Parameter parameter : operation.getParametersList()) {
                            if (parameter instanceof Entity reference) {
                                _entitiesReferencedByFields.add(reference);
                            }
                        }

                    }
                }
            }
        }
        return _entitiesReferencedByFields;
    }

}
