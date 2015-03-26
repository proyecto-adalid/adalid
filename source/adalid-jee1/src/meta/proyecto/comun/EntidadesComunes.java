/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.auditoria.*;
import meta.entidad.comun.configuracion.basica.*;
import meta.entidad.comun.control.acceso.*;
import meta.entidad.comun.control.prueba.*;
import meta.entidad.comun.operacion.basica.*;

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

    Tarea Tarea;

    TareaUsuario TareaUsuario;

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

    TipoAgregacion TipoAgregacion;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionPar;

}
