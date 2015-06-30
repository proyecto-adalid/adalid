/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.OperadorCom;
import meta.entidad.comun.operacion.basica.PaginaUsuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaVirtual;
import meta.entidad.comun.operacion.basica.TipoAgregacion;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;

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

    CondicionTarea CondicionTarea;

    FiltroFuncion FiltroFuncion;

    FiltroFuncionPar FiltroFuncionPar;

    OperadorCom OperadorCom;

    PaginaUsuario Favoritos;

    RecursoValor RecursoValor;

    TareaUsuario TareaUsuario;

    TareaVirtual TareaVirtual;

    TipoAgregacion TipoAgregacion;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionPar;

}
