/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.core.predicates.IsEntityNameIncluded;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.OperadorCom;
import meta.predicado.base.IsModuloRegistroDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloRegistroFiltro extends ModuloBase {

    static final String[] INCLUDED_ENTITY_NAMES = new String[]{
        FiltroFuncion.class.getSimpleName(),
        FiltroFuncionPar.class.getSimpleName(),
        OperadorCom.class.getSimpleName()
    };

    public ModuloRegistroFiltro() {
        super();
        setAlias("Filtro");
        setDefaultLabel("Registro de Filtros");
        setDefaultDescription("Registro de Filtros");
        setFragmentoVisorEnabled(false);
        setFragmentoFiltroListaFiltroEnabled(false);
        setFragmentoFiltroBotonFiltroEnabled(false);
        setFragmentoCabezaBotonAbrirFavoritosEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
        IsEntityNameIncluded entityPredicate = new IsEntityNameIncluded();
        entityPredicate.setIncludedNames(INCLUDED_ENTITY_NAMES);
        IsModuloRegistroDisplay pagePredicate = new IsModuloRegistroDisplay(entityPredicate);
        setPagePredicate(pagePredicate);
    }

}
