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
package meta.paquete.comun;

import meta.entidad.comun.operacion.basica.CampoValorTemporal;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.OperadorCom;
import meta.entidad.comun.operacion.basica.TipoValorCriterio;
import meta.paquete.base.PaqueteConsultaBase;

/**
 * @author Jorge Campins
 */
public class PaqueteConsultaFiltro extends PaqueteConsultaBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicosFiltro");
        setFragmentoVisorEnabled(false);
        setFragmentoFiltroListaFiltroEnabled(false);
        setFragmentoFiltroBotonFiltroEnabled(false);
        setFragmentoCabezaBotonAbrirFavoritosEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteConsultaFiltro's attributes">
        setLocalizedLabel(ENGLISH, "Filters Inquiry");
        setLocalizedLabel(SPANISH, "Consulta de Filtros");
        setLocalizedDescription(ENGLISH, "Filters Inquiry");
        setLocalizedDescription(SPANISH, "Consulta de Filtros");
        setLocalizedShortLabel(ENGLISH, "Filters Management");
        setLocalizedShortLabel(SPANISH, "Gestión de Filtros");
        // </editor-fold>
    }

    protected CampoValorTemporal campoValorTemporal;

    protected FiltroFuncion filtroFuncion;

    protected FiltroFuncionPar filtroFuncionPar;

    protected OperadorCom operadorCom;

    protected TipoValorCriterio tipoValorCriterio;

}
