/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import meta.enumeracion.base.TipoModuloBase;
import meta.predicado.base.IsModuloConsultaResidualDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloConsulta extends ModuloBase {

    public ModuloConsulta() {
        super();
        setAlias("Consulta");
        setDefaultLabel("Consulta de Recursos");
        setDefaultDescription("Consulta de Recursos");
        IsModuloConsultaResidualDisplay pagePredicate = new IsModuloConsultaResidualDisplay(this);
        setPagePredicate(pagePredicate);
    }

    @Override
    public final TipoModuloBase getTipo() {
        return TipoModuloBase.CONSULTA;
    }

}
