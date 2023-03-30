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
import meta.entidad.comun.configuracion.basica.FormatoPaginaInforme;
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

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(AccionArchivoCargado);
        System.out.println(AmbientePrueba);
        System.out.println(Aplicacion);
        System.out.println(ArchivoAdjunto);
        System.out.println(AtributoAplicacion);
        System.out.println(CampoValorTemporal);
        System.out.println(CasoPrueba);
        System.out.println(ClaseJava);
        System.out.println(ClaseRecurso);
        System.out.println(ClaseFabricador);
        System.out.println(CondicionEjeFun);
        System.out.println(CondicionTarea);
        System.out.println(ConjuntoSegmento);
        System.out.println(DocumentoPrueba);
        System.out.println(DocumentoPruebaX1);
        System.out.println(DocumentoPruebaX2);
        System.out.println(DocumentoPruebaX3);
        System.out.println(DocumentoPruebaX4);
        System.out.println(DocumentoPruebaX5);
        System.out.println(DocumentoPruebaX6);
        System.out.println(DialogoDinamicoRemoto);
        System.out.println(Dominio);
        System.out.println(DominioParametro);
        System.out.println(EjecucionLineaPrueba);
        System.out.println(EjecucionPrueba);
        System.out.println(ElementoSegmento);
        System.out.println(EscenarioPrueba);
        System.out.println(FiltroFuncion);
        System.out.println(FiltroFuncionPar);
        System.out.println(FormatoPaginaInforme);
        System.out.println(Funcion);
        System.out.println(FuncionParametro);
        System.out.println(GrupoProceso);
        System.out.println(GrupoUsuario);
        System.out.println(LineaPrueba);
        System.out.println(MensajeAplicacion);
        System.out.println(ModuloAplicacion);
//      System.out.println(NivelOpcionMenu);
//      System.out.println(OpcionMenu);
        System.out.println(OperadorCom);
        System.out.println(Pagina);
        System.out.println(PaginaEspecial);
        System.out.println(PaginaInicio);
        System.out.println(PaginaUsuario);
        System.out.println(PaquetePrueba);
        System.out.println(Parametro);
        System.out.println(ParametroLineaPrueba);
        System.out.println(ParteAmbientePrueba);
        System.out.println(PiezaAmbientePrueba);
        System.out.println(ProgramaPrueba);
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
        System.out.println(RolPaginaEspecial);
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
        System.out.println(TipoDocumentoPrueba);
        System.out.println(TipoDominio);
        System.out.println(TipoFuncion);
        System.out.println(TipoGrafico);
        System.out.println(TipoInforme);
        System.out.println(TipoNodo);
        System.out.println(TipoPagina);
        System.out.println(TipoParametro);
        System.out.println(TipoParametroDom);
        System.out.println(TipoPiezaPrueba);
        System.out.println(TipoRastroFun);
        System.out.println(TipoRecurso);
        System.out.println(TipoRestriccionFormatos);
        System.out.println(TipoResultadoPrueba);
        System.out.println(TipoRol);
        System.out.println(TipoValor);
        System.out.println(TipoValorCriterio);
        System.out.println(TransicionTareaUsuario);
        System.out.println(Usuario);
        System.out.println(UsuarioFuncion);
        System.out.println(UsuarioModulo);
        System.out.println(UsuarioSegmento);
        System.out.println(VersionAdalid);
        System.out.println(VistaFuncion);
        System.out.println(VistaFuncionCol);
    }
    // </editor-fold>

    AccionArchivoCargado AccionArchivoCargado;

    AmbientePrueba AmbientePrueba;

    Aplicacion Aplicacion;

    ArchivoAdjunto ArchivoAdjunto;

    AtributoAplicacion AtributoAplicacion;

    CampoValorTemporal CampoValorTemporal;

    CasoPrueba CasoPrueba;

    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

    ClaseFabricador ClaseFabricador;

    CondicionEjeFun CondicionEjeFun;

    CondicionTarea CondicionTarea;

    ConjuntoSegmento ConjuntoSegmento;

    DocumentoPrueba DocumentoPrueba;

    DocumentoPruebaX1 DocumentoPruebaX1;

    DocumentoPruebaX2 DocumentoPruebaX2;

    DocumentoPruebaX3 DocumentoPruebaX3;

    DocumentoPruebaX4 DocumentoPruebaX4;

    DocumentoPruebaX5 DocumentoPruebaX5;

    DocumentoPruebaX6 DocumentoPruebaX6;

    DialogoDinamicoRemoto DialogoDinamicoRemoto;

    Dominio Dominio;

    DominioParametro DominioParametro;

    EjecucionLineaPrueba EjecucionLineaPrueba;

    EjecucionPrueba EjecucionPrueba;

    ElementoSegmento ElementoSegmento;

    EscenarioPrueba EscenarioPrueba;

    FiltroFuncion FiltroFuncion;

    FiltroFuncionPar FiltroFuncionPar;

    FormatoPaginaInforme FormatoPaginaInforme;

    Funcion Funcion;

    FuncionParametro FuncionParametro;

    GrupoProceso GrupoProceso;

    GrupoUsuario GrupoUsuario;

    LineaPrueba LineaPrueba;

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

    PaquetePrueba PaquetePrueba;

    Parametro Parametro;

    ParametroLineaPrueba ParametroLineaPrueba;

    ParteAmbientePrueba ParteAmbientePrueba;

    PiezaAmbientePrueba PiezaAmbientePrueba;

    ProgramaPrueba ProgramaPrueba;

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

    RolPaginaEspecial RolPaginaEspecial;

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

    TipoDocumentoPrueba TipoDocumentoPrueba;

    TipoDominio TipoDominio;

    TipoFuncion TipoFuncion;

    TipoGrafico TipoGrafico;

    TipoInforme TipoInforme;

    TipoNodo TipoNodo;

    TipoPagina TipoPagina;

    TipoParametro TipoParametro;

    TipoParametroDom TipoParametroDom;

    TipoPiezaPrueba TipoPiezaPrueba;

    TipoRastroFun TipoRastroFun;

    TipoRecurso TipoRecurso;

    TipoRestriccionFormatos TipoRestriccionFormatos;

    TipoResultadoPrueba TipoResultadoPrueba;

    TipoRol TipoRol;

    TipoValor TipoValor;

    TipoValorCriterio TipoValorCriterio;

    TransicionTareaUsuario TransicionTareaUsuario;

    Usuario Usuario;

    UsuarioFuncion UsuarioFuncion;

    UsuarioModulo UsuarioModulo;

    UsuarioSegmento UsuarioSegmento;

    VersionAdalid VersionAdalid;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionCol;

}
