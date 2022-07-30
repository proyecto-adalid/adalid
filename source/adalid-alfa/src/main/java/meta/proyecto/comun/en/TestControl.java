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
package meta.proyecto.comun.en;

import adalid.core.*;
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
import meta.paquete.comun.PaqueteConsultaControlPruebas;
import meta.paquete.comun.PaqueteProcesamientoControlPruebas;
import meta.paquete.comun.PaqueteRegistroControlPruebas;

/**
 * @author Jorge Campins
 */
public class TestControl extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of ControlPruebas's attributes">
        setLocalizedLabel(ENGLISH, "Test Control");
        setLocalizedLabel(SPANISH, "Control de Pruebas");
//      setLocalizedShortDescription(ENGLISH, "Test Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Pruebas");
        setLocalizedDescription(ENGLISH, "Test Control Module");
        setLocalizedDescription(SPANISH, "Módulo de Control de Pruebas");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(x010);
        System.out.println(x030);
        System.out.println(x080);
        System.out.println(x08030);
        System.out.println(x0803010);
        System.out.println(x08040);
        System.out.println(x0804010);
        System.out.println(x08050);
        System.out.println(x0805010);
        System.out.println(x05010);
        System.out.println(x050);
        System.out.println(x03010);
        System.out.println(x04010);
        System.out.println(x020);
        System.out.println(x0501010);
        System.out.println(x01020);
        System.out.println(x01010);
        System.out.println(x040);
        System.out.println(TipoDocumentoPrueba);
        System.out.println(TipoPiezaPrueba);
        System.out.println(TipoResultadoPrueba);
        System.out.println(consulta);
        System.out.println(procesamiento);
        System.out.println(registro);
    }
    // </editor-fold>

    AmbientePrueba x010;

    CasoPrueba x030;

    DocumentoPrueba x080;

    DocumentoPruebaX1 x08030;

    DocumentoPruebaX2 x0803010;

    DocumentoPruebaX3 x08040;

    DocumentoPruebaX4 x0804010;

    DocumentoPruebaX5 x08050;

    DocumentoPruebaX6 x0805010;

    EjecucionLineaPrueba x05010;

    EjecucionPrueba x050;

    EscenarioPrueba x03010;

    LineaPrueba x04010;

    PaquetePrueba x020;

    ParametroLineaPrueba x0501010;

    ParteAmbientePrueba x01020;

    PiezaAmbientePrueba x01010;

    ProgramaPrueba x040;

    TipoDocumentoPrueba TipoDocumentoPrueba;

    TipoPiezaPrueba TipoPiezaPrueba;

    TipoResultadoPrueba TipoResultadoPrueba;

    PaqueteConsultaControlPruebas consulta;

    PaqueteProcesamientoControlPruebas procesamiento;

    PaqueteRegistroControlPruebas registro;

}
