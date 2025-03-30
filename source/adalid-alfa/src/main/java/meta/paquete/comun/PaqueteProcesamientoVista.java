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

import meta.entidad.comun.operacion.basica.FormatoPaginaInforme;
import meta.entidad.comun.operacion.basica.TipoAgregacion;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;
import meta.paquete.base.PaqueteProcesamientoBase;

/**
 * @author Jorge Campins
 */
public class PaqueteProcesamientoVista extends PaqueteProcesamientoBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicosVista");
        setFragmentoVisorEnabled(false);
        setFragmentoCabezaBotonAbrirFavoritosEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteProcesamientoVista's attributes">
        setLocalizedLabel(ENGLISH, "Views Processing");
        setLocalizedLabel(SPANISH, "Procesamiento de Vistas");
        setLocalizedDescription(ENGLISH, "Views Processing");
        setLocalizedDescription(SPANISH, "Procesamiento de Vistas");
        setLocalizedShortLabel(ENGLISH, "Views Management");
        setLocalizedShortLabel(SPANISH, "Gestión de Vistas");
        // </editor-fold>
    }

    protected FormatoPaginaInforme formatoPaginaInforme;

    protected TipoAgregacion tipoAgregacion;

    protected VistaFuncion vistaFuncion;

    protected VistaFuncionCol vistaFuncionCol;

}
