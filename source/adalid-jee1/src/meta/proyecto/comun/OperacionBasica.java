/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.operacion.basica.*;

/**
 * @author Jorge Campins
 */
public class OperacionBasica extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Operación Básica");
        setDefaultDescription("Operación Básica");
    }

    FiltroFuncion FiltroFuncion;

    FiltroFuncionPar FiltroFuncionPar;

    PaginaUsuario Favoritos;

    RecursoValor RecursoValor;

    Tarea Tarea;

    TareaUsuario TareaUsuario;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionPar;
//
//  CondicionTarea CondicionTarea;
//
//  OperadorCom OperadorCom;
//
//  TipoAgregacion TipoAgregacion;

}
