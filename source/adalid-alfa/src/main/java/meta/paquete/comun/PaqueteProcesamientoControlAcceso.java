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

import meta.entidad.comun.control.acceso.ClaseFabricador;
import meta.entidad.comun.control.acceso.ColumnasOcultas;
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
import meta.entidad.comun.control.acceso.UsuarioFuncionPar;
import meta.entidad.comun.control.acceso.UsuarioModulo;
import meta.entidad.comun.control.acceso.UsuarioSegmento;
import meta.paquete.base.PaqueteProcesamientoBase;

/**
 * @author Jorge Campins
 */
public class PaqueteProcesamientoControlAcceso extends PaqueteProcesamientoBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosControlAcceso");
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteProcesamientoControlAcceso's attributes">
        setLocalizedLabel(ENGLISH, "Access Control Resources Processing");
        setLocalizedLabel(SPANISH, "Procesamiento de Recursos de Control de Acceso");
        setLocalizedDescription(ENGLISH, "Access Control Resources Processing");
        setLocalizedDescription(SPANISH, "Procesamiento de Recursos de Control de Acceso");
        setLocalizedShortLabel(ENGLISH, "Access Control");
        setLocalizedShortLabel(SPANISH, "Control de Acceso");
        // </editor-fold>
    }

    protected ClaseFabricador ClaseFabricador;

    protected ColumnasOcultas ColumnasOcultas;

    protected ConjuntoSegmento ConjuntoSegmento;

    protected ElementoSegmento ElementoSegmento;

    protected GrupoUsuario GrupoUsuario;

    protected PaginaEspecial PaginaEspecial;

    protected Rol Rol;

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

    protected Usuario Usuario;

    protected UsuarioFuncion UsuarioFuncion;

    protected UsuarioFuncionPar UsuarioFuncionPar;

    protected UsuarioModulo UsuarioModulo;

    protected UsuarioSegmento UsuarioSegmento;

}
