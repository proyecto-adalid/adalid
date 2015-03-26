/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.core.predicates.IsEntityNameNotExcluded;
import meta.predicado.base.IsModuloConsultaDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloConsulta extends ModuloBase {

    static final String[] EXCLUDED_ENTITY_NAMES = ModuloConsultaTarea.INCLUDED_ENTITY_NAMES;

    public ModuloConsulta() {
        super();
        setAlias("Consulta");
        setDefaultLabel("Consulta de Recursos");
        setDefaultDescription("Consulta de Recursos");
        IsEntityNameNotExcluded entityPredicate = new IsEntityNameNotExcluded();
        entityPredicate.setExcludedNames(EXCLUDED_ENTITY_NAMES);
        IsModuloConsultaDisplay pagePredicate = new IsModuloConsultaDisplay(entityPredicate);
        setPagePredicate(pagePredicate);
    }

}
