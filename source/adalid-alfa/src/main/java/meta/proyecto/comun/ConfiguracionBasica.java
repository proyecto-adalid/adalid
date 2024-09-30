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
        setImmutableModule(true);
        // <editor-fold defaultstate="collapsed" desc="localization of ConfiguracionBasica's attributes">
        setLocalizedLabel(ENGLISH, "Basic Configuration");
        setLocalizedLabel(SPANISH, "Configuración Básica");
//      setLocalizedShortDescription(ENGLISH, "Basic Configuration Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Configuración Básica");
        setLocalizedDescription(ENGLISH, "Basic Configuration Module");
        setLocalizedDescription(SPANISH, "Módulo de Configuración Básica");
        // </editor-fold>
    }

    protected AccionArchivoCargado AccionArchivoCargado;

    protected Aplicacion Aplicacion;

    protected AtributoAplicacion AtributoAplicacion;

    protected ClaseJava ClaseJava;

    protected ClaseRecurso ClaseRecurso;

    protected CondicionEjeFun CondicionEjeFun;

    protected Dominio Dominio;

    protected DominioParametro DominioParametro;

    protected Funcion Funcion;

    protected FuncionParametro FuncionParametro;

    protected GrupoProceso GrupoProceso;

    protected MensajeAplicacion MensajeAplicacion;

    protected ModuloAplicacion ModuloAplicacion;

//  protected NivelOpcionMenu NivelOpcionMenu;
//
//  protected OpcionMenu OpcionMenu;
//
    protected Pagina Pagina;

    protected PaginaInicio PaginaInicio;

    protected Parametro Parametro;

    protected RangoAgregacion RangoAgregacion;

    protected RangoComparacion RangoComparacion;

    protected SeveridadMensaje SeveridadMensaje;

    protected SubtipoGrafico SubtipoGrafico;

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

    protected TipoValor TipoValor;

    protected VersionAdalid VersionAdalid;

}
