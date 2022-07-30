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
package meta.proyecto.comun;

import adalid.core.*;
import meta.entidad.comun.operacion.compleja.PasoRutina;
import meta.entidad.comun.operacion.compleja.RutinaUsuario;
import meta.entidad.comun.operacion.compleja.VariableRutina;
import meta.paquete.comun.PaqueteConsultaControlRutinas;
import meta.paquete.comun.PaqueteProcesamientoControlRutinas;
import meta.paquete.comun.PaqueteRegistroControlRutinas;

/**
 * @author Jorge Campins
 */
public class ControlRutinas extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of ControlRutinas's attributes">
        setLocalizedLabel(ENGLISH, "Routine Control");
        setLocalizedLabel(SPANISH, "Control de Rutinas");
//      setLocalizedShortDescription(ENGLISH, "Routine Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Rutinas");
        setLocalizedDescription(ENGLISH, "Routine Control Module");
        setLocalizedDescription(SPANISH, "Módulo de Control de Rutinas");
        // </editor-fold>
    }

    @Override
    public boolean isMenuModule() {
        return super.isMenuModule() && !TLC.getProject().getUserFlows().isEmpty();
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(x010);
        System.out.println(x01010);
        System.out.println(x01020);
        System.out.println(consulta);
        System.out.println(procesamiento);
        System.out.println(registro);
    }
    // </editor-fold>

    RutinaUsuario x010;

    VariableRutina x01010;

    PasoRutina x01020;

    PaqueteConsultaControlRutinas consulta;

    PaqueteProcesamientoControlRutinas procesamiento;

    PaqueteRegistroControlRutinas registro;

}
