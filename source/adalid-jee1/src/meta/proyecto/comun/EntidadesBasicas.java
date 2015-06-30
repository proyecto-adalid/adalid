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
public class EntidadesBasicas extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Entidades Básicas");
        setDefaultDescription("Entidades Básicas");
    }

    Aplicacion Aplicacion;

    ArchivoAdjunto ArchivoAdjunto;

    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

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

    NivelOpcionMenu NivelOpcionMenu;

    OpcionMenu OpcionMenu;

    OperadorCom OperadorCom;

    Pagina Pagina;

    PaginaUsuario PaginaUsuario;

    Parametro Parametro;

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

    TipoDominio TipoDominio;

    TipoFuncion TipoFuncion;

    TipoNodo TipoNodo;

    TipoPagina TipoPagina;

    TipoParametro TipoParametro;

    TipoParametroDom TipoParametroDom;

    TipoRastroFun TipoRastroFun;

    TipoRecurso TipoRecurso;

    TipoRol TipoRol;

    TipoValor TipoValor;

    Usuario Usuario;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionPar;

}
