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
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaUsuarioCorreo;
import meta.entidad.comun.operacion.basica.TransicionTareaUsuario;

/**
 * @author Jorge Campins
 */
public class TaskControl extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of ControlTareas's attributes">
        setLocalizedLabel(ENGLISH, "Task Control");
        setLocalizedLabel(SPANISH, "Control de Tareas");
//      setLocalizedShortDescription(ENGLISH, "Task Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Tareas");
        setLocalizedDescription(ENGLISH, meta.proyecto.comun.help.en.ControlTareas.getHelpText());
        setLocalizedDescription(SPANISH, meta.proyecto.comun.help.es.ControlTareas.getHelpText());
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(x010);
        System.out.println(x01010);
        System.out.println(x01020);
    }
    // </editor-fold>

    TareaUsuario x010;

    TareaUsuarioCorreo x01010;

    TransicionTareaUsuario x01020;

}
