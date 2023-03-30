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
import meta.entidad.comun.control.acceso.ConjuntoSegmento;
import meta.entidad.comun.control.acceso.ElementoSegmento;
import meta.entidad.comun.control.acceso.GrupoUsuario;
import meta.entidad.comun.control.acceso.PaginaEspecial;
import meta.entidad.comun.control.acceso.Rol;
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
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.control.acceso.UsuarioFuncion;
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
        // <editor-fold defaultstate="collapsed" desc="localization of ControlAcceso's attributes">
        setLocalizedLabel(ENGLISH, "Access Control");
        setLocalizedLabel(SPANISH, "Control de Acceso");
//      setLocalizedShortDescription(ENGLISH, "Access Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Acceso");
        setLocalizedDescription(ENGLISH, meta.proyecto.comun.help.en.ControlAcceso.getHelpText());
        setLocalizedDescription(SPANISH, meta.proyecto.comun.help.es.ControlAcceso.getHelpText());
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(ClaseFabricador);
        System.out.println(ConjuntoSegmento);
        System.out.println(ElementoSegmento);
        System.out.println(PaginaEspecial);
        System.out.println(Rol);
        System.out.println(RolFiltroFuncion);
        System.out.println(RolFuncion);
        System.out.println(RolFuncionPar);
        System.out.println(RolPagina);
        System.out.println(RolPaginaEspecial);
        System.out.println(RolUsuario);
        System.out.println(RolVistaFuncion);
        System.out.println(Segmento);
        System.out.println(TipoRestriccionFormatos);
        System.out.println(TipoRol);
        System.out.println(Usuario);
        System.out.println(UsuarioFuncion);
        System.out.println(UsuarioModulo);
        System.out.println(UsuarioSegmento);
        System.out.println(UsuarioX1);
        System.out.println(consulta);
        System.out.println(procesamiento);
        System.out.println(registro);
    }
    // </editor-fold>

    ClaseFabricador ClaseFabricador;

    ConjuntoSegmento ConjuntoSegmento;

    ElementoSegmento ElementoSegmento;

    GrupoUsuario UsuarioX1;

    PaginaEspecial PaginaEspecial;

    Rol Rol;

    RolFiltroFuncion RolFiltroFuncion;

    RolFuncion RolFuncion;

    RolFuncionPar RolFuncionPar;

    RolPagina RolPagina;

    RolPaginaEspecial RolPaginaEspecial;

    RolUsuario RolUsuario;

    RolVistaFuncion RolVistaFuncion;

    Segmento Segmento;

    TipoRestriccionFormatos TipoRestriccionFormatos;

    TipoRol TipoRol;

    Usuario Usuario;

    UsuarioFuncion UsuarioFuncion;

    UsuarioModulo UsuarioModulo;

    UsuarioSegmento UsuarioSegmento;

    PaqueteConsultaControlAcceso consulta;

    PaqueteProcesamientoControlAcceso procesamiento;

    PaqueteRegistroControlAcceso registro;

}
