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

import meta.entidad.comun.operacion.compleja.PasoRutina;
import meta.entidad.comun.operacion.compleja.RutinaUsuario;
import meta.entidad.comun.operacion.compleja.VariableRutina;
import meta.paquete.base.PaqueteProcesamientoBase;

/**
 * @author Jorge Campins
 */
public class PaqueteProcesamientoControlRutinas extends PaqueteProcesamientoBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosControlRutinas");
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteProcesamientoControlRutinas's attributes">
        setLocalizedLabel(ENGLISH, "Routine Control Resources Processing");
        setLocalizedLabel(SPANISH, "Procesamiento de Recursos de Control de Rutinas");
        setLocalizedDescription(ENGLISH, "Routine Control Resources Processing");
        setLocalizedDescription(SPANISH, "Procesamiento de Recursos de Control de Rutinas");
        setLocalizedShortLabel(ENGLISH, "Routine Control");
        setLocalizedShortLabel(SPANISH, "Control de Rutinas");
        // </editor-fold>
    }

    PasoRutina PasoRutina;

    RutinaUsuario RutinaUsuario;

    VariableRutina VariableRutina;

}
