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
import meta.entidad.comun.control.acceso.PaginaEspecial;
import meta.entidad.comun.control.acceso.Rol;
import meta.entidad.comun.control.acceso.RolFiltroFuncion;
import meta.entidad.comun.control.acceso.RolFuncion;
import meta.entidad.comun.control.acceso.RolFuncionPar;
import meta.entidad.comun.control.acceso.RolPagina;
import meta.entidad.comun.control.acceso.RolUsuario;
import meta.entidad.comun.control.acceso.RolVistaFuncion;
import meta.entidad.comun.control.acceso.Segmento;
import meta.entidad.comun.control.acceso.TipoRol;
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.control.acceso.UsuarioFuncion;
import meta.entidad.comun.control.acceso.UsuarioModulo;
import meta.entidad.comun.control.acceso.UsuarioSegmento;
import meta.paquete.base.PaqueteRegistroBase;

/**
 * @author Jorge Campins
 */
public class PaqueteRegistroControlAcceso extends PaqueteRegistroBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosControlAcceso");
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteRegistroControlAcceso's attributes">
        setLocalizedLabel(ENGLISH, "Access Control Resources Registration");
        setLocalizedLabel(SPANISH, "Registro de Recursos de Control de Acceso");
        setLocalizedDescription(ENGLISH, "Access Control Resources Registration");
        setLocalizedDescription(SPANISH, "Registro de Recursos de Control de Acceso");
        setLocalizedShortLabel(ENGLISH, "Access Control");
        setLocalizedShortLabel(SPANISH, "Control de Acceso");
        // </editor-fold>
    }

    ConjuntoSegmento ConjuntoSegmento;

    ElementoSegmento ElementoSegmento;

    PaginaEspecial PaginaEspecial;

    Rol Rol;

    RolFiltroFuncion RolFiltroFuncion;

    RolFuncion RolFuncion;

    RolFuncionPar RolFuncionPar;

    RolPagina RolPagina;

    RolUsuario RolUsuario;

    RolVistaFuncion RolVistaFuncion;

    Segmento Segmento;

    TipoRol TipoRol;

    Usuario Usuario;

    UsuarioFuncion UsuarioFuncion;

    UsuarioModulo UsuarioModulo;

    UsuarioSegmento UsuarioSegmento;

}
