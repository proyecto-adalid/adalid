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

import meta.entidad.comun.configuracion.basica.Aplicacion;
import meta.entidad.comun.configuracion.basica.AtributoAplicacion;
import meta.entidad.comun.configuracion.basica.ClaseJava;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.configuracion.basica.Dominio;
import meta.entidad.comun.configuracion.basica.DominioParametro;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.MensajeAplicacion;
import meta.entidad.comun.configuracion.basica.ModuloAplicacion;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.PaginaInicio;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.entidad.comun.configuracion.basica.TipoClaseRecurso;
import meta.entidad.comun.configuracion.basica.TipoComparacion;
import meta.entidad.comun.configuracion.basica.TipoDatoPar;
import meta.entidad.comun.configuracion.basica.TipoDominio;
import meta.entidad.comun.configuracion.basica.TipoFuncion;
import meta.entidad.comun.configuracion.basica.TipoNodo;
import meta.entidad.comun.configuracion.basica.TipoPagina;
import meta.entidad.comun.configuracion.basica.TipoParametro;
import meta.entidad.comun.configuracion.basica.TipoParametroDom;
import meta.entidad.comun.configuracion.basica.TipoRastroFun;
import meta.entidad.comun.configuracion.basica.TipoRecurso;
import meta.entidad.comun.configuracion.basica.TipoValor;
import meta.entidad.comun.configuracion.basica.VersionAdalid;
import meta.entidad.comun.operacion.basica.DialogoDinamicoRemoto;
import meta.entidad.comun.operacion.basica.PaginaUsuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.paquete.base.PaqueteProcesamientoBase;

/**
 * @author Jorge Campins
 */
public class PaqueteProcesamientoRecursosBasicos extends PaqueteProcesamientoBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicos");
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteProcesamientoRecursosBasicos's attributes">
        setLocalizedLabel(ENGLISH, "Basic Resources Processing");
        setLocalizedLabel(SPANISH, "Procesamiento de Recursos Básicos");
        setLocalizedDescription(ENGLISH, "Basic Resources Processing");
        setLocalizedDescription(SPANISH, "Procesamiento de Recursos Básicos");
        setLocalizedShortLabel(ENGLISH, "Basic Resources Management");
        setLocalizedShortLabel(SPANISH, "Gestión de Recursos Básicos");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(Aplicacion);
        System.out.println(AtributoAplicacion);
//      System.out.println(CampoValorTemporal);
        System.out.println(ClaseJava);
        System.out.println(ClaseRecurso);
        System.out.println(CondicionEjeFun);
//      System.out.println(CondicionTarea);
        System.out.println(DialogoDinamicoRemoto);
        System.out.println(Dominio);
        System.out.println(DominioParametro);
//      System.out.println(FiltroFuncion);
//      System.out.println(FiltroFuncionPar);
//      System.out.println(FormatoPaginaInforme);
        System.out.println(Funcion);
        System.out.println(FuncionParametro);
        System.out.println(GrupoProceso);
        System.out.println(MensajeAplicacion);
        System.out.println(ModuloAplicacion);
//      System.out.println(NivelOpcionMenu);
//      System.out.println(OpcionMenu);
//      System.out.println(OperadorCom);
        System.out.println(Pagina);
        System.out.println(PaginaInicio);
        System.out.println(Favoritos);
        System.out.println(Parametro);
        System.out.println(RecursoValor);
//      System.out.println(Tarea);
//      System.out.println(TareaUsuario);
//      System.out.println(TareaUsuarioCorreo);
//      System.out.println(TipoAgregacion);
        System.out.println(TipoClaseRecurso);
        System.out.println(TipoComparacion);
        System.out.println(TipoDatoPar);
        System.out.println(TipoDominio);
        System.out.println(TipoFuncion);
        System.out.println(TipoNodo);
        System.out.println(TipoPagina);
        System.out.println(TipoParametro);
        System.out.println(TipoParametroDom);
        System.out.println(TipoRastroFun);
        System.out.println(TipoRecurso);
        System.out.println(TipoValor);
//      System.out.println(TipoValorCriterio);
        System.out.println(VersionAdalid);
//      System.out.println(VistaFuncion);
//      System.out.println(VistaFuncionPar);
        System.out.println(procesamientoFiltro);
//      System.out.println(procesamientoTarea);
        System.out.println(procesamientoVista);
    }
    // </editor-fold>

    Aplicacion Aplicacion;

    AtributoAplicacion AtributoAplicacion;

//  CampoValorTemporal CampoValorTemporal;
//
    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

    CondicionEjeFun CondicionEjeFun;

//  CondicionTarea CondicionTarea;
//
    DialogoDinamicoRemoto DialogoDinamicoRemoto;

    Dominio Dominio;

    DominioParametro DominioParametro;

//  FiltroFuncion FiltroFuncion;
//
//  FiltroFuncionPar FiltroFuncionPar;
//
//  FormatoPaginaInforme FormatoPaginaInforme;
//
    Funcion Funcion;

    FuncionParametro FuncionParametro;

    GrupoProceso GrupoProceso;

    MensajeAplicacion MensajeAplicacion;

    ModuloAplicacion ModuloAplicacion;

//  NivelOpcionMenu NivelOpcionMenu;
//
//  OpcionMenu OpcionMenu;
//
//  OperadorCom OperadorCom;
//
    Pagina Pagina;

    PaginaInicio PaginaInicio;

    PaginaUsuario Favoritos;

    Parametro Parametro;

    RecursoValor RecursoValor;

//  Tarea Tarea;
//
//  TareaUsuario TareaUsuario;
//
//  TareaUsuarioCorreo TareaUsuarioCorreo;
//
//  TipoAgregacion TipoAgregacion;
//
    TipoClaseRecurso TipoClaseRecurso;

    TipoComparacion TipoComparacion;

    TipoDatoPar TipoDatoPar;

    TipoDominio TipoDominio;

    TipoFuncion TipoFuncion;

    TipoNodo TipoNodo;

    TipoPagina TipoPagina;

    TipoParametro TipoParametro;

    TipoParametroDom TipoParametroDom;

    TipoRastroFun TipoRastroFun;

    TipoRecurso TipoRecurso;

    TipoValor TipoValor;
//
//  TipoValorCriterio TipoValorCriterio;

    VersionAdalid VersionAdalid;

//  VistaFuncion VistaFuncion;
//
//  VistaFuncionCol VistaFuncionPar;
//
    PaqueteProcesamientoFiltro procesamientoFiltro;

//  PaqueteProcesamientoTarea procesamientoTarea;
//
    PaqueteProcesamientoVista procesamientoVista;

}
