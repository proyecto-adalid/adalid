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
package meta.proyecto.comun.en;

import adalid.core.*;
import meta.entidad.comun.configuracion.basica.Aplicacion;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;

/**
 * @author Jorge Campins
 */
public class ServicesControl extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setImmutableModule(true);
        // <editor-fold defaultstate="collapsed" desc="localization of ControlServicios's attributes">
        setLocalizedLabel(ENGLISH, "Supplementary Services Control");
        setLocalizedLabel(SPANISH, "Control de Servicios Complementarios");
        setLocalizedShortDescription(ENGLISH, "Supplementary Services Control Module");
        setLocalizedShortDescription(SPANISH, "Módulo de Control de Servicios Complementarios");
        setLocalizedDescription(ENGLISH, "Supplementary Services Control Module");
        setLocalizedDescription(SPANISH, "Módulo de Control de Servicios Complementarios");
        // </editor-fold>
    }

    protected Aplicacion Aplicacion;

    protected FiltroFuncion FiltroFuncion;

    protected FiltroFuncionPar FiltroFuncionPar;

    protected VistaFuncion VistaFuncion;

    protected VistaFuncionCol VistaFuncionCol;

}
