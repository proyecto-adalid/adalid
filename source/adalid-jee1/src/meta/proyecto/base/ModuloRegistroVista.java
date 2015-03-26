/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.core.predicates.IsEntityNameIncluded;
import meta.entidad.comun.operacion.basica.TipoAgregacion;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;
import meta.predicado.base.IsModuloRegistroDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloRegistroVista extends ModuloBase {

    static final String[] INCLUDED_ENTITY_NAMES = new String[]{
        TipoAgregacion.class.getSimpleName(),
        VistaFuncion.class.getSimpleName(),
        VistaFuncionCol.class.getSimpleName()
    };

    public ModuloRegistroVista() {
        super();
        setAlias("Vista");
        setDefaultLabel("Registro de Vistas");
        setDefaultDescription("Registro de Vistas");
        setFragmentoVisorEnabled(false);
        setFragmentoCabezaBotonAbrirFavoritosEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
        IsEntityNameIncluded entityPredicate = new IsEntityNameIncluded();
        entityPredicate.setIncludedNames(INCLUDED_ENTITY_NAMES);
        IsModuloRegistroDisplay pagePredicate = new IsModuloRegistroDisplay(entityPredicate);
        setPagePredicate(pagePredicate);
    }

}
