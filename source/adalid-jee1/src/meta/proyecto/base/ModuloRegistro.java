/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.commons.util.ArrUtils;
import adalid.core.predicates.IsEntityNameNotExcluded;
import meta.predicado.base.IsModuloRegistroDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloRegistro extends ModuloBase {

    static final String[] EXCLUDED_ENTITY_NAMES = ArrUtils.join(String.class,
        ModuloRegistroFiltro.INCLUDED_ENTITY_NAMES,
        ModuloRegistroPrueba.INCLUDED_ENTITY_NAMES,
        ModuloRegistroVista.INCLUDED_ENTITY_NAMES);

    public ModuloRegistro() {
        super();
        setAlias("Registro");
        setDefaultLabel("Registro de Recursos");
        setDefaultDescription("Registro de Recursos");
        IsEntityNameNotExcluded entityPredicate = new IsEntityNameNotExcluded();
        entityPredicate.setExcludedNames(EXCLUDED_ENTITY_NAMES);
        IsModuloRegistroDisplay pagePredicate = new IsModuloRegistroDisplay(entityPredicate);
        setPagePredicate(pagePredicate);
    }

}
