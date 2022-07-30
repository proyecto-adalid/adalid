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
import adalid.core.annotations.*;
import adalid.core.enums.*;
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
import meta.entidad.comun.control.acceso.ConjuntoSegmento;
import meta.entidad.comun.control.acceso.ElementoSegmento;
import meta.entidad.comun.control.acceso.GrupoUsuario;
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
import meta.entidad.comun.control.acceso.UsuarioFuncion;
import meta.entidad.comun.control.acceso.UsuarioModulo;
import meta.entidad.comun.control.acceso.UsuarioSegmento;
import meta.entidad.comun.control.acceso.ext.Usuario;
import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.OperadorCom;
import meta.entidad.comun.operacion.basica.PaginaUsuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaUsuarioCorreo;
import meta.entidad.comun.operacion.basica.TareaVirtual;
import meta.entidad.comun.operacion.basica.TipoAgregacion;
import meta.entidad.comun.operacion.basica.TransicionTareaUsuario;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;

/**
 * @author Jorge Campins
 */
@ProjectModuleDocGen(classDiagram = Kleenean.FALSE)
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

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(AccionArchivoCargado);
        System.out.println(Aplicacion);
        System.out.println(ArchivoAdjunto);
        System.out.println(AtributoAplicacion);
        System.out.println(ClaseJava);
        System.out.println(ClaseRecurso);
        System.out.println(ClaseFabricador);
        System.out.println(CondicionEjeFun);
        System.out.println(CondicionTarea);
        System.out.println(ConjuntoSegmento);
        System.out.println(Dominio);
        System.out.println(DominioParametro);
        System.out.println(ElementoSegmento);
        System.out.println(FiltroFuncion);
        System.out.println(FiltroFuncionPar);
        System.out.println(Funcion);
        System.out.println(FuncionParametro);
        System.out.println(GrupoProceso);
        System.out.println(GrupoUsuario);
        System.out.println(MensajeAplicacion);
        System.out.println(ModuloAplicacion);
//      System.out.println(NivelOpcionMenu);
//      System.out.println(OpcionMenu);
        System.out.println(OperadorCom);
        System.out.println(Pagina);
        System.out.println(PaginaEspecial);
        System.out.println(PaginaInicio);
        System.out.println(PaginaUsuario);
        System.out.println(Parametro);
        System.out.println(RangoAgregacion);
        System.out.println(RangoComparacion);
        System.out.println(RastroFuncion);
        System.out.println(RastroFuncionPar);
        System.out.println(RastroInforme);
        System.out.println(RastroProceso);
        System.out.println(RecursoValor);
        System.out.println(Rol);
        System.out.println(RolFiltroFuncion);
        System.out.println(RolFuncion);
        System.out.println(RolFuncionPar);
        System.out.println(RolPagina);
        System.out.println(RolUsuario);
        System.out.println(RolVistaFuncion);
        System.out.println(Segmento);
        System.out.println(SeveridadMensaje);
        System.out.println(SubtipoGrafico);
        System.out.println(TareaUsuario);
        System.out.println(TareaUsuarioCorreo);
        System.out.println(TareaVirtual);
        System.out.println(TipoAgregacion);
        System.out.println(TipoClaseRecurso);
        System.out.println(TipoComparacion);
        System.out.println(TipoDatoPar);
        System.out.println(TipoDominio);
        System.out.println(TipoFuncion);
        System.out.println(TipoGrafico);
        System.out.println(TipoInforme);
        System.out.println(TipoNodo);
        System.out.println(TipoPagina);
        System.out.println(TipoParametro);
        System.out.println(TipoParametroDom);
        System.out.println(TipoRastroFun);
        System.out.println(TipoRecurso);
        System.out.println(TipoRol);
        System.out.println(TipoValor);
        System.out.println(TransicionTareaUsuario);
        System.out.println(Usuario);
        System.out.println(UsuarioFuncion);
        System.out.println(UsuarioModulo);
        System.out.println(UsuarioSegmento);
        System.out.println(VersionAdalid);
        System.out.println(VistaFuncion);
        System.out.println(VistaFuncionPar);
    }
    // </editor-fold>

    AccionArchivoCargado AccionArchivoCargado;

    Aplicacion Aplicacion;

    ArchivoAdjunto ArchivoAdjunto;

    AtributoAplicacion AtributoAplicacion;

    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

    ClaseFabricador ClaseFabricador;

    CondicionEjeFun CondicionEjeFun;

    CondicionTarea CondicionTarea;

    ConjuntoSegmento ConjuntoSegmento;

    Dominio Dominio;

    DominioParametro DominioParametro;

    ElementoSegmento ElementoSegmento;

    FiltroFuncion FiltroFuncion;

    FiltroFuncionPar FiltroFuncionPar;

    Funcion Funcion;

    FuncionParametro FuncionParametro;

    GrupoProceso GrupoProceso;

    GrupoUsuario GrupoUsuario;

    MensajeAplicacion MensajeAplicacion;

    ModuloAplicacion ModuloAplicacion;

//  NivelOpcionMenu NivelOpcionMenu;
//
//  OpcionMenu OpcionMenu;
//
    OperadorCom OperadorCom;

    Pagina Pagina;

    PaginaEspecial PaginaEspecial;

    PaginaInicio PaginaInicio;

    PaginaUsuario PaginaUsuario;

    Parametro Parametro;

    RangoAgregacion RangoAgregacion;

    RangoComparacion RangoComparacion;

    RastroFuncion RastroFuncion;

    RastroFuncionPar RastroFuncionPar;

    RastroInforme RastroInforme;

    RastroProceso RastroProceso;

    RecursoValor RecursoValor;

    Rol Rol;

    RolFiltroFuncion RolFiltroFuncion;

    RolFuncion RolFuncion;

    RolFuncionPar RolFuncionPar;

    RolPagina RolPagina;

    RolUsuario RolUsuario;

    RolVistaFuncion RolVistaFuncion;

    Segmento Segmento;

    SeveridadMensaje SeveridadMensaje;

    SubtipoGrafico SubtipoGrafico;

    TareaUsuario TareaUsuario;

    TareaUsuarioCorreo TareaUsuarioCorreo;

    TareaVirtual TareaVirtual;

    TipoAgregacion TipoAgregacion;

    TipoClaseRecurso TipoClaseRecurso;

    TipoComparacion TipoComparacion;

    TipoDatoPar TipoDatoPar;

    TipoDominio TipoDominio;

    TipoFuncion TipoFuncion;

    TipoGrafico TipoGrafico;

    TipoInforme TipoInforme;

    TipoNodo TipoNodo;

    TipoPagina TipoPagina;

    TipoParametro TipoParametro;

    TipoParametroDom TipoParametroDom;

    TipoRastroFun TipoRastroFun;

    TipoRecurso TipoRecurso;

    TipoRol TipoRol;

    TipoValor TipoValor;

    TransicionTareaUsuario TransicionTareaUsuario;

    Usuario Usuario;

    UsuarioFuncion UsuarioFuncion;

    UsuarioModulo UsuarioModulo;

    UsuarioSegmento UsuarioSegmento;

    VersionAdalid VersionAdalid;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionPar;

}
