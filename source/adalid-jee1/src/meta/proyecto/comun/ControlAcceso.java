/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.control.acceso.ConjuntoSegmento;
import meta.entidad.comun.control.acceso.ElementoSegmento;
import meta.entidad.comun.control.acceso.Rol;
import meta.entidad.comun.control.acceso.RolFiltroFuncion;
import meta.entidad.comun.control.acceso.RolFuncion;
import meta.entidad.comun.control.acceso.RolFuncionPar;
import meta.entidad.comun.control.acceso.RolPagina;
import meta.entidad.comun.control.acceso.RolUsuario;
import meta.entidad.comun.control.acceso.Segmento;
import meta.entidad.comun.control.acceso.TipoRol;
import meta.entidad.comun.control.acceso.Usuario;
import meta.paquete.comun.PaqueteConsultaControlAcceso;
import meta.paquete.comun.PaqueteProcesamientoControlAcceso;
import meta.paquete.comun.PaqueteRegistroControlAcceso;

/**
 * @author Jorge Campins
 */
public class ControlAcceso extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Control de Acceso");
        setDefaultDescription("Control de Acceso");
    }

    ConjuntoSegmento ConjuntoSegmento;

    ElementoSegmento ElementoSegmento;

    Rol Rol;

    RolFuncion RolFuncion;

    RolFuncionPar RolFuncionPar;

    RolFiltroFuncion RolFiltroFuncion;

    RolPagina RolPagina;

    RolUsuario RolUsuario;

    Segmento Segmento;

    TipoRol TipoRol;

    Usuario Usuario;

    PaqueteConsultaControlAcceso consulta;

    PaqueteProcesamientoControlAcceso procesamiento;

    PaqueteRegistroControlAcceso registro;

}
