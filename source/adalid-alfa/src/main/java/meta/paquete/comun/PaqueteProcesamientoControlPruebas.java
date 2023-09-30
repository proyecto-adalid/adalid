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
package meta.paquete.comun;

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
import meta.paquete.base.PaqueteProcesamientoBase;

/**
 * @author Jorge Campins
 */
public class PaqueteProcesamientoControlPruebas extends PaqueteProcesamientoBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosControlPruebas");
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteProcesamientoControlPruebas's attributes">
        setLocalizedLabel(ENGLISH, "Test Control Resources Processing");
        setLocalizedLabel(SPANISH, "Procesamiento de Recursos de Control de Pruebas");
        setLocalizedDescription(ENGLISH, "Test Control Resources Processing");
        setLocalizedDescription(SPANISH, "Procesamiento de Recursos de Control de Pruebas");
        setLocalizedShortLabel(ENGLISH, "Test Control");
        setLocalizedShortLabel(SPANISH, "Control de Pruebas");
        // </editor-fold>
    }

    protected AmbientePrueba ambientePrueba;

    protected CasoPrueba casoPrueba;

    protected DocumentoPrueba documentoPrueba;

    protected DocumentoPruebaX1 documentoPruebaX1;

    protected DocumentoPruebaX2 documentoPruebaX2;

    protected DocumentoPruebaX3 documentoPruebaX3;

    protected DocumentoPruebaX4 documentoPruebaX4;

    protected DocumentoPruebaX5 documentoPruebaX5;

    protected DocumentoPruebaX6 documentoPruebaX6;

    protected EjecucionLineaPrueba ejecucionLineaPrueba;

    protected EjecucionPrueba ejecucionPrueba;

    protected EscenarioPrueba escenarioPrueba;

    protected LineaPrueba lineaPrueba;

    protected PaquetePrueba paquetePrueba;

    protected ParametroLineaPrueba parametroLineaPrueba;

    protected ParteAmbientePrueba parteAmbientePrueba;

    protected PiezaAmbientePrueba piezaAmbientePrueba;

    protected ProgramaPrueba programaPrueba;

    protected TipoDocumentoPrueba tipoDocumentoPrueba;

    protected TipoPiezaPrueba tipoPiezaPrueba;

    protected TipoResultadoPrueba tipoResultadoPrueba;

}
