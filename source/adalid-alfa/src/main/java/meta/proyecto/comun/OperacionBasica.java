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
public class OperacionBasica extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setImmutableModule(true);
        // <editor-fold defaultstate="collapsed" desc="localization of OperacionBasica's attributes">
        setLocalizedLabel(ENGLISH, "Basic Operation");
        setLocalizedLabel(SPANISH, "Operación Básica");
//      setLocalizedShortDescription(ENGLISH, "Basic Operation Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Operación Básica");
        setLocalizedDescription(ENGLISH, "Basic Operation Module");
        setLocalizedDescription(SPANISH, "Módulo de Operación Básica");
        // </editor-fold>
    }

    protected CampoValorTemporal CampoValorTemporal;

    protected CondicionTarea CondicionTarea;

    protected DialogoDinamicoRemoto DialogoDinamicoRemoto;

    protected FiltroFuncion FiltroFuncion;

    protected FiltroFuncionPar FiltroFuncionPar;

    protected FormatoPaginaInforme FormatoPaginaInforme;

    protected OperadorCom OperadorCom;

    protected PaginaUsuario Favoritos;

    protected RecursoValor RecursoValor;

    protected TareaUsuario TareaUsuario;

    protected TareaUsuarioCorreo TareaUsuarioCorreo;

    protected TareaVirtual TareaVirtual;

    protected TipoAgregacion TipoAgregacion;

    protected TipoValorCriterio TipoValorCriterio;

    protected TransicionTareaUsuario TransicionTareaUsuario;

    protected VistaFuncion VistaFuncion;

    protected VistaFuncionCol VistaFuncionPar;

}
