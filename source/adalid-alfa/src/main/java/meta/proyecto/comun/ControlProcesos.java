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
import meta.entidad.comun.configuracion.basica.GrupoProceso;

/**
 * @author Jorge Campins
 */
public class ControlProcesos extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of ControlProcesos's attributes">
        setLocalizedLabel(ENGLISH, "Process Control");
        setLocalizedLabel(SPANISH, "Control de Procesos");
//      setLocalizedShortDescription(ENGLISH, "Process Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Procesos");
        setLocalizedDescription(ENGLISH, "Process Control Module");
        setLocalizedDescription(SPANISH, "Módulo de Control de Procesos");
        // </editor-fold>
    }

    @Override
    public boolean isMenuModule() {
        return super.isMenuModule() && !TLC.getProject().getProcessingGroups().isEmpty();
    }

    protected GrupoProceso GrupoProceso;

}
