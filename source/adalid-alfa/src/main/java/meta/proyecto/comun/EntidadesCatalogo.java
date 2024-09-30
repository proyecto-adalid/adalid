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
import meta.entidad.comun.configuracion.basica.Aplicacion;
import meta.entidad.comun.configuracion.basica.AtributoAplicacion;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.Dominio;
import meta.entidad.comun.configuracion.basica.DominioParametro;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.ModuloAplicacion;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.Parametro;

/**
 * @author Jorge Campins
 */
@ProjectModuleDocGen(classDiagram = Kleenean.FALSE)
public class EntidadesCatalogo extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setImmutableModule(true);
        // <editor-fold defaultstate="collapsed" desc="localization of EntidadesCatalogo's attributes">
        setLocalizedLabel(ENGLISH, "Catalog Entities");
        setLocalizedLabel(SPANISH, "Entidades Del Catálogo");
//      setLocalizedShortDescription(ENGLISH, "Catalog Entities Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Entidades Del Catálogo");
        setLocalizedDescription(ENGLISH, "Catalog Entities Module");
        setLocalizedDescription(SPANISH, "Módulo de Entidades Del Catálogo");
        // </editor-fold>
    }

    protected Aplicacion Aplicacion;

    protected AtributoAplicacion AtributoAplicacion;

    protected ClaseRecurso ClaseRecurso;

    protected Dominio Dominio;

    protected DominioParametro DominioParametro;

    protected Funcion Funcion;

    protected FuncionParametro FuncionParametro;

    protected GrupoProceso GrupoProceso;

    protected ModuloAplicacion ModuloAplicacion;

//  protected OpcionMenu OpcionMenu;
//
    protected Pagina Pagina;

    protected Parametro Parametro;

}
