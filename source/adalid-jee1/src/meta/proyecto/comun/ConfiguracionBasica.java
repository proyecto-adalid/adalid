/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.configuracion.basica.*;

/**
 * @author Jorge Campins
 */
public class ConfiguracionBasica extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Configuración Básica");
        setDefaultDescription("Configuración Básica");
    }

    Aplicacion Aplicacion;

    ClaseJava ClaseJava;

    ClaseRecurso ClaseRecurso;

    Dominio Dominio;

    DominioParametro DominioParametro;

    Funcion Funcion;

    FuncionParametro FuncionParametro;

    GrupoProceso GrupoProceso;

    OpcionMenu OpcionMenu;

    Pagina Pagina;

    Parametro Parametro;
//
//  CondicionEjeFun CondicionEjeFun;
//
//  NivelOpcionMenu NivelOpcionMenu;
//
//  TipoClaseRecurso TipoClaseRecurso;
//
//  TipoComparacion TipoComparacion;
//
//  TipoDatoPar TipoDatoPar;
//
//  TipoDominio TipoDominio;
//
//  TipoFuncion TipoFuncion;
//
//  TipoNodo TipoNodo;
//
//  TipoPagina TipoPagina;
//
//  TipoParametro TipoParametro;
//
//  TipoParametroDom TipoParametroDom;
//
//  TipoRastroFun TipoRastroFun;
//
//  TipoRecurso TipoRecurso;
//
//  TipoValor TipoValor;

}
