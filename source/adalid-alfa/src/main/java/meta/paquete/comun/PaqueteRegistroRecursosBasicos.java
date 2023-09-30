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
import meta.paquete.base.PaqueteRegistroBase;

/**
 * @author Jorge Campins
 */
public class PaqueteRegistroRecursosBasicos extends PaqueteRegistroBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicos");
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteRegistroRecursosBasicos's attributes">
        setLocalizedLabel(ENGLISH, "Basic Resources Registration");
        setLocalizedLabel(SPANISH, "Registro de Recursos Básicos");
        setLocalizedDescription(ENGLISH, "Basic Resources Registration");
        setLocalizedDescription(SPANISH, "Registro de Recursos Básicos");
        setLocalizedShortLabel(ENGLISH, "Basic Resources Management");
        setLocalizedShortLabel(SPANISH, "Gestión de Recursos Básicos");
        // </editor-fold>
    }

    protected Aplicacion Aplicacion;

    protected AtributoAplicacion AtributoAplicacion;

//  protected CampoValorTemporal CampoValorTemporal;
//
    protected ClaseJava ClaseJava;

    protected ClaseRecurso ClaseRecurso;

    protected CondicionEjeFun CondicionEjeFun;

//  protected CondicionTarea CondicionTarea;
//
    protected DialogoDinamicoRemoto DialogoDinamicoRemoto;

    protected Dominio Dominio;

    protected DominioParametro DominioParametro;

//  protected FiltroFuncion FiltroFuncion;
//
//  protected FiltroFuncionPar FiltroFuncionPar;
//
//  protected FormatoPaginaInforme FormatoPaginaInforme;
//
    protected Funcion Funcion;

    protected FuncionParametro FuncionParametro;

    protected GrupoProceso GrupoProceso;

    protected MensajeAplicacion MensajeAplicacion;

    protected ModuloAplicacion ModuloAplicacion;

//  protected NivelOpcionMenu NivelOpcionMenu;
//
//  protected OpcionMenu OpcionMenu;
//
//  protected OperadorCom OperadorCom;
//
    protected Pagina Pagina;

    protected PaginaInicio PaginaInicio;

    protected PaginaUsuario Favoritos;

    protected Parametro Parametro;

    protected RecursoValor RecursoValor;

//  protected Tarea Tarea;
//
//  protected TareaUsuario TareaUsuario;
//
//  protected TareaUsuarioCorreo TareaUsuarioCorreo;
//
//  protected TipoAgregacion TipoAgregacion;
//
    protected TipoClaseRecurso TipoClaseRecurso;

    protected TipoComparacion TipoComparacion;

    protected TipoDatoPar TipoDatoPar;

    protected TipoDominio TipoDominio;

    protected TipoFuncion TipoFuncion;

    protected TipoNodo TipoNodo;

    protected TipoPagina TipoPagina;

    protected TipoParametro TipoParametro;

    protected TipoParametroDom TipoParametroDom;

    protected TipoRastroFun TipoRastroFun;

    protected TipoRecurso TipoRecurso;

    protected TipoValor TipoValor;
//
//  protected TipoValorCriterio TipoValorCriterio;

    protected VersionAdalid VersionAdalid;

//  protected VistaFuncion VistaFuncion;
//
//  protected VistaFuncionCol VistaFuncionPar;
//
    protected PaqueteRegistroFiltro registroFiltro;

//  protected PaqueteRegistroTarea registroTarea;
//
    protected PaqueteRegistroVista registroVista;

}
