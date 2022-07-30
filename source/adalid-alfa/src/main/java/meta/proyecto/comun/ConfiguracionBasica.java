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
import meta.entidad.comun.configuracion.basica.AccionArchivoCargado;
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

/**
 * @author Jorge Campins
 */
public class ConfiguracionBasica extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of ConfiguracionBasica's attributes">
        setLocalizedLabel(ENGLISH, "Basic Configuration");
        setLocalizedLabel(SPANISH, "Configuración Básica");
//      setLocalizedShortDescription(ENGLISH, "Basic Configuration Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Configuración Básica");
        setLocalizedDescription(ENGLISH, "Basic Configuration Module");
        setLocalizedDescription(SPANISH, "Módulo de Configuración Básica");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(AccionArchivoCargado);
        System.out.println(Aplicacion);
        System.out.println(AtributoAplicacion);
        System.out.println(ClaseJava);
        System.out.println(ClaseRecurso);
        System.out.println(CondicionEjeFun);
        System.out.println(Dominio);
        System.out.println(DominioParametro);
        System.out.println(Funcion);
        System.out.println(FuncionParametro);
        System.out.println(GrupoProceso);
        System.out.println(MensajeAplicacion);
        System.out.println(ModuloAplicacion);
//      System.out.println(NivelOpcionMenu);
//      System.out.println(OpcionMenu);
        System.out.println(Pagina);
        System.out.println(PaginaInicio);
        System.out.println(Parametro);
        System.out.println(RangoAgregacion);
        System.out.println(RangoComparacion);
        System.out.println(SeveridadMensaje);
        System.out.println(SubtipoGrafico);
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
        System.out.println(TipoValor);
        System.out.println(VersionAdalid);
    }
    // </editor-fold>

    AccionArchivoCargado AccionArchivoCargado;

    Aplicacion Aplicacion;

    AtributoAplicacion AtributoAplicacion;

    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

    CondicionEjeFun CondicionEjeFun;

    Dominio Dominio;

    DominioParametro DominioParametro;

    Funcion Funcion;

    FuncionParametro FuncionParametro;

    GrupoProceso GrupoProceso;

    MensajeAplicacion MensajeAplicacion;

    ModuloAplicacion ModuloAplicacion;

//  NivelOpcionMenu NivelOpcionMenu;
//
//  OpcionMenu OpcionMenu;
//
    Pagina Pagina;

    PaginaInicio PaginaInicio;

    Parametro Parametro;

    RangoAgregacion RangoAgregacion;

    RangoComparacion RangoComparacion;

    SeveridadMensaje SeveridadMensaje;

    SubtipoGrafico SubtipoGrafico;

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

    TipoValor TipoValor;

    VersionAdalid VersionAdalid;

}
