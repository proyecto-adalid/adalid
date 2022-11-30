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
import meta.entidad.comun.control.acceso.TipoRol;
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.control.acceso.UsuarioFuncion;
import meta.entidad.comun.control.acceso.UsuarioModulo;
import meta.entidad.comun.control.acceso.UsuarioSegmento;
import meta.paquete.base.PaqueteConsultaBase;

/**
 * @author Jorge Campins
 */
public class PaqueteConsultaControlAcceso extends PaqueteConsultaBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosControlAcceso");
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteConsultaControlAcceso's attributes">
        setLocalizedLabel(ENGLISH, "Access Control Resources Inquiry");
        setLocalizedLabel(SPANISH, "Consulta de Recursos de Control de Acceso");
        setLocalizedDescription(ENGLISH, "Access Control Resources Inquiry");
        setLocalizedDescription(SPANISH, "Consulta de Recursos de Control de Acceso");
        setLocalizedShortLabel(ENGLISH, "Access Control");
        setLocalizedShortLabel(SPANISH, "Control de Acceso");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(ConjuntoSegmento);
        System.out.println(ElementoSegmento);
        System.out.println(GrupoUsuario);
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
        System.out.println(TipoRol);
        System.out.println(Usuario);
        System.out.println(UsuarioFuncion);
        System.out.println(UsuarioModulo);
        System.out.println(UsuarioSegmento);
    }
    // </editor-fold>

    ConjuntoSegmento ConjuntoSegmento;

    ElementoSegmento ElementoSegmento;

    GrupoUsuario GrupoUsuario;

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

    TipoRol TipoRol;

    Usuario Usuario;

    UsuarioFuncion UsuarioFuncion;

    UsuarioModulo UsuarioModulo;

    UsuarioSegmento UsuarioSegmento;

}
