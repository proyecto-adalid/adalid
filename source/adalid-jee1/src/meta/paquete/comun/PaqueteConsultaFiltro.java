/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.comun;

import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.OperadorCom;
import meta.paquete.base.PaqueteConsultaBase;

/**
 * @author Jorge Campins
 */
public class PaqueteConsultaFiltro extends PaqueteConsultaBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicosFiltro");
        setDefaultLabel("Consulta de Filtros");
        setDefaultDescription("Consulta de Filtros");
        setFragmentoVisorEnabled(false);
        setFragmentoFiltroListaFiltroEnabled(false);
        setFragmentoFiltroBotonFiltroEnabled(false);
        setFragmentoCabezaBotonAbrirFavoritosEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
    }

    FiltroFuncion filtroFuncion;

    FiltroFuncionPar filtroFuncionPar;

    OperadorCom operadorCom;

}
