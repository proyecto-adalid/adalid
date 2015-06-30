/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.comun;

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
import meta.entidad.comun.operacion.basica.PaginaUsuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.paquete.base.PaqueteConsultaBase;

/**
 * @author Jorge Campins
 */
public class PaqueteConsultaRecursosBasicos extends PaqueteConsultaBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicos");
        setDefaultLabel("Consulta de Recursos Básicos");
        setDefaultDescription("Consulta de Recursos Básicos");
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
    }

    Aplicacion Aplicacion;

    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

    CondicionEjeFun CondicionEjeFun;

//  CondicionTarea CondicionTarea;
//
    Dominio Dominio;

    DominioParametro DominioParametro;

//  FiltroFuncion FiltroFuncion;
//
//  FiltroFuncionPar FiltroFuncionPar;
//
    Funcion Funcion;

    FuncionParametro FuncionParametro;

    GrupoProceso GrupoProceso;

    NivelOpcionMenu NivelOpcionMenu;

    OpcionMenu OpcionMenu;

//  OperadorCom OperadorCom;
//
    Pagina Pagina;

    PaginaUsuario Favoritos;

    Parametro Parametro;

    RecursoValor RecursoValor;

//  Tarea Tarea;
//
//  TareaUsuario TareaUsuario;
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

//  VistaFuncion VistaFuncion;
//
//  VistaFuncionCol VistaFuncionPar;
//
    PaqueteConsultaFiltro consultaFiltro;

    PaqueteConsultaTarea consultaTarea;

}
