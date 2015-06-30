/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.auditoria.ArchivoAdjunto;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.auditoria.RastroFuncionPar;
import meta.entidad.comun.auditoria.RastroInforme;
import meta.entidad.comun.auditoria.RastroProceso;
import meta.entidad.comun.configuracion.basica.Aplicacion;
import meta.entidad.comun.configuracion.basica.ClaseJava;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.configuracion.basica.Dominio;
import meta.entidad.comun.configuracion.basica.DominioParametro;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.NivelOpcionMenu;
import meta.entidad.comun.configuracion.basica.OpcionMenu;
import meta.entidad.comun.configuracion.basica.Pagina;
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
import meta.entidad.comun.control.acceso.ConjuntoSegmento;
import meta.entidad.comun.control.acceso.ElementoSegmento;
import meta.entidad.comun.control.acceso.Rol;
import meta.entidad.comun.control.acceso.RolFiltroFuncion;
import meta.entidad.comun.control.acceso.RolFuncion;
import meta.entidad.comun.control.acceso.RolFuncionPar;
import meta.entidad.comun.control.acceso.RolPagina;
import meta.entidad.comun.control.acceso.RolUsuario;
import meta.entidad.comun.control.acceso.Segmento;
import meta.entidad.comun.control.acceso.TipoRol;
import meta.entidad.comun.control.acceso.Usuario;
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
import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.entidad.comun.operacion.basica.FiltroFuncionPar;
import meta.entidad.comun.operacion.basica.OperadorCom;
import meta.entidad.comun.operacion.basica.PaginaUsuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.entidad.comun.operacion.basica.TareaVirtual;
import meta.entidad.comun.operacion.basica.TipoAgregacion;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;

/**
 * @author Jorge Campins
 */
public class EntidadesComunes extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Entidades Comúnes");
        setDefaultDescription("Entidades Comúnes");
    }

    AmbientePrueba AmbientePrueba;

    Aplicacion Aplicacion;

    ArchivoAdjunto ArchivoAdjunto;

    CasoPrueba CasoPrueba;

    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

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

    Dominio Dominio;

    DominioParametro DominioParametro;

    EjecucionLineaPrueba EjecucionLineaPrueba;

    EjecucionPrueba EjecucionPrueba;

    ElementoSegmento ElementoSegmento;

    EscenarioPrueba EscenarioPrueba;

    FiltroFuncion FiltroFuncion;

    FiltroFuncionPar FiltroFuncionPar;

    Funcion Funcion;

    FuncionParametro FuncionParametro;

    GrupoProceso GrupoProceso;

    LineaPrueba LineaPrueba;

    NivelOpcionMenu NivelOpcionMenu;

    OpcionMenu OpcionMenu;

    OperadorCom OperadorCom;

    Pagina Pagina;

    PaginaUsuario PaginaUsuario;

    PaquetePrueba PaquetePrueba;

    Parametro Parametro;

    ParametroLineaPrueba ParametroLineaPrueba;

    ParteAmbientePrueba ParteAmbientePrueba;

    PiezaAmbientePrueba PiezaAmbientePrueba;

    ProgramaPrueba ProgramaPrueba;

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

    Segmento Segmento;

    TareaUsuario TareaUsuario;

    TareaVirtual TareaVirtual;

    TipoAgregacion TipoAgregacion;

    TipoClaseRecurso TipoClaseRecurso;

    TipoComparacion TipoComparacion;

    TipoDatoPar TipoDatoPar;

    TipoDocumentoPrueba TipoDocumentoPrueba;

    TipoDominio TipoDominio;

    TipoFuncion TipoFuncion;

    TipoNodo TipoNodo;

    TipoPagina TipoPagina;

    TipoParametro TipoParametro;

    TipoParametroDom TipoParametroDom;

    TipoPiezaPrueba TipoPiezaPrueba;

    TipoRastroFun TipoRastroFun;

    TipoRecurso TipoRecurso;

    TipoResultadoPrueba TipoResultadoPrueba;

    TipoRol TipoRol;

    TipoValor TipoValor;

    Usuario Usuario;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionPar;

}
