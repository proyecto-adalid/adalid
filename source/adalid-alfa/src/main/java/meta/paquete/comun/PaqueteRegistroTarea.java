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

import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaUsuarioCorreo;
import meta.entidad.comun.operacion.basica.TareaVirtual;
import meta.entidad.comun.operacion.basica.TransicionTareaUsuario;
import meta.paquete.base.PaqueteRegistroBase;

/**
 * @author Jorge Campins
 */
public class PaqueteRegistroTarea extends PaqueteRegistroBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicosTarea");
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteRegistroTarea's attributes">
        setLocalizedLabel(ENGLISH, "Task Registration");
        setLocalizedLabel(SPANISH, "Registro de Tareas");
        setLocalizedDescription(ENGLISH, "Task Registration");
        setLocalizedDescription(SPANISH, "Registro de Tareas");
        setLocalizedShortLabel(ENGLISH, "Task Control");
        setLocalizedShortLabel(SPANISH, "Control de Tareas");
        // </editor-fold>
    }

    protected CondicionTarea condicionTarea;

    protected TareaUsuario tareaUsuario;

    protected TareaUsuarioCorreo tareaUsuarioCorreo;

    protected TareaVirtual tareaVirtual;

    protected TransicionTareaUsuario transicionTareaUsuario;

}
