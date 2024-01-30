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
import adalid.core.interfaces.*;
import adalid.jee2.features.*;
import java.util.List;
import meta.entidad.comun.auditoria.ArchivoAdjunto;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.auditoria.RastroFuncionPar;
import meta.entidad.comun.auditoria.RastroInforme;
import meta.entidad.comun.auditoria.RastroProceso;
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
import meta.entidad.comun.control.prueba.AmbientePrueba;
import meta.entidad.comun.control.prueba.CasoPrueba;
import meta.entidad.comun.control.prueba.DocumentoPrueba;
import meta.entidad.comun.control.prueba.DocumentoPruebaX1;
import meta.entidad.comun.control.prueba.DocumentoPruebaX2;
import meta.entidad.comun.control.prueba.DocumentoPruebaX3;
import meta.entidad.comun.control.prueba.DocumentoPruebaX4;
import meta.entidad.comun.control.prueba.DocumentoPruebaX5;
import meta.entidad.comun.control.prueba.DocumentoPruebaX6;
import meta.entidad.comun.control.prueba.EjecucionLineaPrueba;
import meta.entidad.comun.control.prueba.EjecucionPrueba;
import meta.entidad.comun.control.prueba.EscenarioPrueba;
import meta.entidad.comun.control.prueba.LineaPrueba;
import meta.entidad.comun.control.prueba.PaquetePrueba;
import meta.entidad.comun.control.prueba.ParametroLineaPrueba;
import meta.entidad.comun.control.prueba.ParteAmbientePrueba;
import meta.entidad.comun.control.prueba.PiezaAmbientePrueba;
import meta.entidad.comun.control.prueba.ProgramaPrueba;
import meta.entidad.comun.control.prueba.TipoDocumentoPrueba;
import meta.entidad.comun.control.prueba.TipoPiezaPrueba;
import meta.entidad.comun.control.prueba.TipoResultadoPrueba;
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
@ProjectModuleDocGen(classDiagram = Kleenean.FALSE)
public class EntidadesComunes extends Project implements SpecialEntityPack {

    public EntidadesComunes() {
        super();
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of EntidadesComunes's attributes">
        setLocalizedLabel(ENGLISH, "Common Entities");
        setLocalizedLabel(SPANISH, "Entidades Comúnes");
//      setLocalizedShortDescription(ENGLISH, "Common Entities Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Entidades Comúnes");
        setLocalizedDescription(ENGLISH, "Common Entities Module");
        setLocalizedDescription(SPANISH, "Módulo de Entidades Comúnes");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="special entity pack">
    SpecialEntityPack specialEntityPack = new SpecialEntityPackDelegate();

    @Override
    public Class<? extends Entity> getApplicationMessageEntityClass() {
        return specialEntityPack.getApplicationMessageEntityClass();
    }

    @Override
    public void setApplicationMessageEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setApplicationMessageEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getSegmentSetFactoryEntityClass() {
        return specialEntityPack.getSegmentSetFactoryEntityClass();
    }

    @Override
    public void setSegmentSetFactoryEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setSegmentSetFactoryEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getTaskNotificationEntityClass() {
        return specialEntityPack.getTaskNotificationEntityClass();
    }

    @Override
    public void setTaskNotificationEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setTaskNotificationEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getUploadedFileEntityClass() {
        return specialEntityPack.getUploadedFileEntityClass();
    }

    @Override
    public void setUploadedFileEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setUploadedFileEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getUserEntityClass() {
        return specialEntityPack.getUserEntityClass();
    }

    @Override
    public void setUserEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setUserEntityClass(clazz);
    }

    @Override
    public Class<? extends Entity> getVersionEntityClass() {
        return specialEntityPack.getVersionEntityClass();
    }

    @Override
    public void setVersionEntityClass(Class<? extends Entity> clazz) {
        specialEntityPack.setVersionEntityClass(clazz);
    }

    @Override
    public List<Class<? extends Entity>> unsetSpecialEntityClasses() {
        return specialEntityPack.unsetSpecialEntityClasses();
    }
    // </editor-fold>

    protected AccionArchivoCargado AccionArchivoCargado;

    protected AmbientePrueba AmbientePrueba;

    protected Aplicacion Aplicacion;

    protected ArchivoAdjunto ArchivoAdjunto;

    protected AtributoAplicacion AtributoAplicacion;

    protected CampoValorTemporal CampoValorTemporal;

    protected CasoPrueba CasoPrueba;

    protected ClaseJava ClaseJava;

    protected ClaseFabricador ClaseFabricador;

    protected ClaseRecurso ClaseRecurso;

    protected ColumnasOcultas ColumnasOcultas;

    protected CondicionEjeFun CondicionEjeFun;

    protected CondicionTarea CondicionTarea;

    protected ConjuntoSegmento ConjuntoSegmento;

    protected DocumentoPrueba DocumentoPrueba;

    protected DocumentoPruebaX1 DocumentoPruebaX1;

    protected DocumentoPruebaX2 DocumentoPruebaX2;

    protected DocumentoPruebaX3 DocumentoPruebaX3;

    protected DocumentoPruebaX4 DocumentoPruebaX4;

    protected DocumentoPruebaX5 DocumentoPruebaX5;

    protected DocumentoPruebaX6 DocumentoPruebaX6;

    protected DialogoDinamicoRemoto DialogoDinamicoRemoto;

    protected Dominio Dominio;

    protected DominioParametro DominioParametro;

    protected EjecucionLineaPrueba EjecucionLineaPrueba;

    protected EjecucionPrueba EjecucionPrueba;

    protected ElementoSegmento ElementoSegmento;

    protected EscenarioPrueba EscenarioPrueba;

    protected FiltroFuncion FiltroFuncion;

    protected FiltroFuncionPar FiltroFuncionPar;

    protected FormatoPaginaInforme FormatoPaginaInforme;

    protected Funcion Funcion;

    protected FuncionParametro FuncionParametro;

    protected GrupoProceso GrupoProceso;

    protected GrupoUsuario GrupoUsuario;

    protected LineaPrueba LineaPrueba;

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

    protected PaquetePrueba PaquetePrueba;

    protected Parametro Parametro;

    protected ParametroLineaPrueba ParametroLineaPrueba;

    protected ParteAmbientePrueba ParteAmbientePrueba;

    protected PiezaAmbientePrueba PiezaAmbientePrueba;

    protected ProgramaPrueba ProgramaPrueba;

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

    protected TipoDocumentoPrueba TipoDocumentoPrueba;

    protected TipoDominio TipoDominio;

    protected TipoFuncion TipoFuncion;

    protected TipoGrafico TipoGrafico;

    protected TipoInforme TipoInforme;

    protected TipoNodo TipoNodo;

    protected TipoPagina TipoPagina;

    protected TipoParametro TipoParametro;

    protected TipoParametroDom TipoParametroDom;

    protected TipoPiezaPrueba TipoPiezaPrueba;

    protected TipoRastroFun TipoRastroFun;

    protected TipoRecurso TipoRecurso;

    protected TipoRestriccionFormatos TipoRestriccionFormatos;

    protected TipoResultadoPrueba TipoResultadoPrueba;

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

    protected VistaFuncionCol VistaFuncionCol;

}
