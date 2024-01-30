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
package meta.proyecto.comun;

import adalid.core.*;
import meta.entidad.comun.auditoria.ArchivoAdjunto;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.auditoria.RastroFuncionPar;
import meta.entidad.comun.auditoria.RastroInforme;
import meta.entidad.comun.auditoria.RastroProceso;
import meta.entidad.comun.configuracion.basica.AccionArchivoCargado;
import meta.entidad.comun.configuracion.basica.Aplicacion;
import meta.entidad.comun.configuracion.basica.AtributoAplicacion;
import meta.entidad.comun.configuracion.basica.ClaseJava;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.configuracion.basica.DominioParametro;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.MensajeAplicacion;
import meta.entidad.comun.configuracion.basica.ModuloAplicacion;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.PaginaInicio;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.entidad.comun.configuracion.basica.RangoAgregacion;
import meta.entidad.comun.configuracion.basica.RangoComparacion;
import meta.entidad.comun.configuracion.basica.SeveridadMensaje;
import meta.entidad.comun.configuracion.basica.SubtipoGrafico;
import meta.entidad.comun.configuracion.basica.TipoClaseRecurso;
import meta.entidad.comun.configuracion.basica.TipoComparacion;
import meta.entidad.comun.configuracion.basica.TipoDatoPar;
import meta.entidad.comun.configuracion.basica.TipoDominio;
import meta.entidad.comun.configuracion.basica.TipoFuncion;
import meta.entidad.comun.configuracion.basica.TipoGrafico;
import meta.entidad.comun.configuracion.basica.TipoInforme;
import meta.entidad.comun.configuracion.basica.TipoNodo;
import meta.entidad.comun.configuracion.basica.TipoPagina;
import meta.entidad.comun.configuracion.basica.TipoParametro;
import meta.entidad.comun.configuracion.basica.TipoParametroDom;
import meta.entidad.comun.configuracion.basica.TipoRastroFun;
import meta.entidad.comun.configuracion.basica.TipoRecurso;
import meta.entidad.comun.configuracion.basica.TipoValor;
import meta.entidad.comun.configuracion.basica.VersionAdalid;
import meta.entidad.comun.configuracion.basica.ext.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.ext.Dominio;
import meta.entidad.comun.configuracion.basica.ext.Funcion;
import meta.entidad.comun.configuracion.basica.ext.FuncionParametro;
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
import meta.entidad.comun.control.acceso.UsuarioFuncion;
import meta.entidad.comun.control.acceso.UsuarioFuncionPar;
import meta.entidad.comun.control.acceso.UsuarioModulo;
import meta.entidad.comun.control.acceso.UsuarioSegmento;
import meta.entidad.comun.control.acceso.ext.Usuario;
import meta.entidad.comun.operacion.basica.CampoValorTemporal;
import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.entidad.comun.operacion.basica.DialogoDinamicoRemoto;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.FormatoPaginaInforme;
import meta.entidad.comun.operacion.basica.OperadorCom;
import meta.entidad.comun.operacion.basica.PaginaUsuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaUsuarioCorreo;
import meta.entidad.comun.operacion.basica.TareaVirtual;
import meta.entidad.comun.operacion.basica.TipoAgregacion;
import meta.entidad.comun.operacion.basica.TipoValorCriterio;
import meta.entidad.comun.operacion.basica.TransicionTareaUsuario;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;

/**
 * @author Jorge Campins
 */
public class EntidadesBasicas extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of EntidadesBasicas's attributes">
        setLocalizedLabel(ENGLISH, "Basic Entities");
        setLocalizedLabel(SPANISH, "Entidades Básicas");
//      setLocalizedShortDescription(ENGLISH, "Basic Entities Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Entidades Básicas");
        setLocalizedDescription(ENGLISH, "Basic Entities Module");
        setLocalizedDescription(SPANISH, "Módulo de Entidades Básicas");
        // </editor-fold>
    }

    protected AccionArchivoCargado AccionArchivoCargado;

    protected Aplicacion Aplicacion;

    protected ArchivoAdjunto ArchivoAdjunto;

    protected AtributoAplicacion AtributoAplicacion;

    protected CampoValorTemporal CampoValorTemporal;

    protected ClaseFabricador ClaseFabricador;

    protected ClaseJava ClaseJava;

    protected ClaseRecurso ClaseRecurso;

    protected ColumnasOcultas ColumnasOcultas;

    protected CondicionEjeFun CondicionEjeFun;

    protected CondicionTarea CondicionTarea;

    protected ConjuntoSegmento ConjuntoSegmento;

    protected DialogoDinamicoRemoto DialogoDinamicoRemoto;

    protected Dominio Dominio;

    protected DominioParametro DominioParametro;

    protected ElementoSegmento ElementoSegmento;

    protected FiltroFuncion FiltroFuncion;

    protected FiltroFuncionPar FiltroFuncionPar;

    protected FormatoPaginaInforme FormatoPaginaInforme;

    protected Funcion Funcion;

    protected FuncionParametro FuncionParametro;

    protected GrupoProceso GrupoProceso;

    protected GrupoUsuario GrupoUsuario;

    protected MensajeAplicacion MensajeAplicacion;

    protected ModuloAplicacion ModuloAplicacion;

//  protected NivelOpcionMenu NivelOpcionMenu;
//
//  protected OpcionMenu OpcionMenu;
//
    protected OperadorCom OperadorCom;

    protected Pagina Pagina;

    protected PaginaEspecial PaginaEspecial;

    protected PaginaInicio PaginaInicio;

    protected PaginaUsuario PaginaUsuario;

    protected Parametro Parametro;

    protected RangoAgregacion RangoAgregacion;

    protected RangoComparacion RangoComparacion;

    protected RastroFuncion RastroFuncion;

    protected RastroFuncionPar RastroFuncionPar;

    protected RastroInforme RastroInforme;

    protected RastroProceso RastroProceso;

    protected RecursoValor RecursoValor;

    protected Rol Rol;

    protected RolFiltroFuncion RolFiltroFuncion;

    protected RolFuncion RolFuncion;

    protected RolFuncionPar RolFuncionPar;

    protected RolPagina RolPagina;

    protected RolPaginaEspecial RolPaginaEspecial;

    protected RolUsuario RolUsuario;

    protected RolVistaFuncion RolVistaFuncion;

    protected Segmento Segmento;

    protected SeveridadMensaje SeveridadMensaje;

    protected SubtipoGrafico SubtipoGrafico;

    protected TareaUsuario TareaUsuario;

    protected TareaUsuarioCorreo TareaUsuarioCorreo;

    protected TareaVirtual TareaVirtual;

    protected TipoAgregacion TipoAgregacion;

    protected TipoClaseRecurso TipoClaseRecurso;

    protected TipoComparacion TipoComparacion;

    protected TipoDatoPar TipoDatoPar;

    protected TipoDominio TipoDominio;

    protected TipoFuncion TipoFuncion;

    protected TipoGrafico TipoGrafico;

    protected TipoInforme TipoInforme;

    protected TipoNodo TipoNodo;

    protected TipoPagina TipoPagina;

    protected TipoParametro TipoParametro;

    protected TipoParametroDom TipoParametroDom;

    protected TipoRastroFun TipoRastroFun;

    protected TipoRecurso TipoRecurso;

    protected TipoRestriccionFormatos TipoRestriccionFormatos;

    protected TipoRol TipoRol;

    protected TipoValor TipoValor;

    protected TipoValorCriterio TipoValorCriterio;

    protected TransicionTareaUsuario TransicionTareaUsuario;

    protected Usuario Usuario;

    protected UsuarioFuncion UsuarioFuncion;

    protected UsuarioFuncionPar UsuarioFuncionPar;

    protected UsuarioModulo UsuarioModulo;

    protected UsuarioSegmento UsuarioSegmento;

    protected VersionAdalid VersionAdalid;

    protected VistaFuncion VistaFuncion;

    protected VistaFuncionCol VistaFuncionPar;

}
