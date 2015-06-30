/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.comun;

import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaVirtual;
import meta.paquete.base.PaqueteConsultaBase;

/**
 * @author Jorge Campins
 */
public class PaqueteConsultaTarea extends PaqueteConsultaBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicosTarea");
        setDefaultLabel("Control de Tareas");
        setDefaultDescription("Control de Tareas");
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
    }

    CondicionTarea condicionTarea;

    TareaUsuario tareaUsuario;

    TareaVirtual tareaVirtual;

}
