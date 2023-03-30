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
import meta.entidad.comun.configuracion.basica.FormatoPaginaInforme;
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
public class OperacionBasica extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of OperacionBasica's attributes">
        setLocalizedLabel(ENGLISH, "Basic Operation");
        setLocalizedLabel(SPANISH, "Operación Básica");
//      setLocalizedShortDescription(ENGLISH, "Basic Operation Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Operación Básica");
        setLocalizedDescription(ENGLISH, "Basic Operation Module");
        setLocalizedDescription(SPANISH, "Módulo de Operación Básica");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(CampoValorTemporal);
        System.out.println(CondicionTarea);
        System.out.println(DialogoDinamicoRemoto);
        System.out.println(FiltroFuncion);
        System.out.println(FiltroFuncionPar);
        System.out.println(FormatoPaginaInforme);
        System.out.println(OperadorCom);
        System.out.println(Favoritos);
        System.out.println(RecursoValor);
        System.out.println(TareaUsuario);
        System.out.println(TareaUsuarioCorreo);
        System.out.println(TareaVirtual);
        System.out.println(TipoAgregacion);
        System.out.println(TipoValorCriterio);
        System.out.println(TransicionTareaUsuario);
        System.out.println(VistaFuncion);
        System.out.println(VistaFuncionPar);
    }
    // </editor-fold>

    CampoValorTemporal CampoValorTemporal;

    CondicionTarea CondicionTarea;

    DialogoDinamicoRemoto DialogoDinamicoRemoto;

    FiltroFuncion FiltroFuncion;

    FiltroFuncionPar FiltroFuncionPar;

    FormatoPaginaInforme FormatoPaginaInforme;

    OperadorCom OperadorCom;

    PaginaUsuario Favoritos;

    RecursoValor RecursoValor;

    TareaUsuario TareaUsuario;

    TareaUsuarioCorreo TareaUsuarioCorreo;

    TareaVirtual TareaVirtual;

    TipoAgregacion TipoAgregacion;

    TipoValorCriterio TipoValorCriterio;

    TransicionTareaUsuario TransicionTareaUsuario;

    VistaFuncion VistaFuncion;

    VistaFuncionCol VistaFuncionPar;

}
