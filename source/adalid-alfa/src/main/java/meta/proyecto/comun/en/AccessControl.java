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
import meta.entidad.comun.control.acceso.ClaseFabricador;
import meta.entidad.comun.control.acceso.ColumnasOcultas;
import meta.entidad.comun.control.acceso.ConjuntoSegmento;
import meta.entidad.comun.control.acceso.ElementoSegmento;
import meta.entidad.comun.control.acceso.GrupoUsuario;
import meta.entidad.comun.control.acceso.PaginaEspecial;
import meta.entidad.comun.control.acceso.Rol;
import meta.entidad.comun.control.acceso.RolAplicacion;
import meta.entidad.comun.control.acceso.RolFiltroFuncion;
import meta.entidad.comun.control.acceso.RolFuncion;
import meta.entidad.comun.control.acceso.RolFuncionPar;
import meta.entidad.comun.control.acceso.RolPagina;
import meta.entidad.comun.control.acceso.RolPaginaEspecial;
import meta.entidad.comun.control.acceso.RolUsuario;
import meta.entidad.comun.control.acceso.RolVistaFuncion;
import meta.entidad.comun.control.acceso.Segmento;
import meta.entidad.comun.control.acceso.TipoRestriccionFormatos;
import meta.entidad.comun.control.acceso.TipoRol;
import meta.entidad.comun.control.acceso.TipoUsuario;
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.control.acceso.UsuarioFuncion;
import meta.entidad.comun.control.acceso.UsuarioFuncionPar;
import meta.entidad.comun.control.acceso.UsuarioModulo;
import meta.entidad.comun.control.acceso.UsuarioSegmento;
import meta.paquete.comun.PaqueteConsultaControlAcceso;
import meta.paquete.comun.PaqueteProcesamientoControlAcceso;
import meta.paquete.comun.PaqueteRegistroControlAcceso;

/**
 * @author Jorge Campins
 */
public class AccessControl extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setImmutableModule(true);
        // <editor-fold defaultstate="collapsed" desc="localization of ControlAcceso's attributes">
        setLocalizedLabel(ENGLISH, "Access Control");
        setLocalizedLabel(SPANISH, "Control de Acceso");
//      setLocalizedShortDescription(ENGLISH, "Access Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Acceso");
        setLocalizedDescription(ENGLISH, meta.proyecto.comun.help.en.ControlAcceso.getHelpText());
        setLocalizedDescription(SPANISH, meta.proyecto.comun.help.es.ControlAcceso.getHelpText());
        // </editor-fold>
    }

    protected ClaseFabricador ClaseFabricador;

    protected ColumnasOcultas ColumnasOcultas;

    protected ConjuntoSegmento ConjuntoSegmento;

    protected ElementoSegmento ElementoSegmento;

    protected GrupoUsuario UsuarioX1;

    protected PaginaEspecial PaginaEspecial;

    protected Rol Rol;

    protected RolAplicacion RolAplicacion;

    protected RolFiltroFuncion RolFiltroFuncion;

    protected RolFuncion RolFuncion;

    protected RolFuncionPar RolFuncionPar;

    protected RolPagina RolPagina;

    protected RolPaginaEspecial RolPaginaEspecial;

    protected RolUsuario RolUsuario;

    protected RolVistaFuncion RolVistaFuncion;

    protected Segmento Segmento;

    protected TipoRestriccionFormatos TipoRestriccionFormatos;

    protected TipoRol TipoRol;

    protected TipoUsuario TipoUsuario;

    protected Usuario Usuario;

    protected UsuarioFuncion UsuarioFuncion;

    protected UsuarioFuncionPar UsuarioFuncionPar;

    protected UsuarioModulo UsuarioModulo;

    protected UsuarioSegmento UsuarioSegmento;

    protected PaqueteConsultaControlAcceso consulta;

    protected PaqueteProcesamientoControlAcceso procesamiento;

    protected PaqueteRegistroControlAcceso registro;

}
