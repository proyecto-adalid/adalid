/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
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
public class ControlPruebas extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Control de Pruebas");
        setDefaultDescription("Control de Pruebas");
    }

    AmbientePrueba _10;

    CasoPrueba _30;

    DocumentoPrueba _80;

    DocumentoPruebaX1 _8030;

    DocumentoPruebaX2 _803010;

    DocumentoPruebaX3 _8040;

    DocumentoPruebaX4 _804010;

    DocumentoPruebaX5 _8050;

    DocumentoPruebaX6 _805010;

    EjecucionLineaPrueba _5010;

    EjecucionPrueba _50;

    EscenarioPrueba _3010;

    LineaPrueba _4010;

    PaquetePrueba _20;

    ParametroLineaPrueba _501010;

    ParteAmbientePrueba _1020;

    PiezaAmbientePrueba _1010;

    ProgramaPrueba _40;

    TipoDocumentoPrueba TipoDocumentoPrueba;

    TipoPiezaPrueba TipoPiezaPrueba;

    TipoResultadoPrueba TipoResultadoPrueba;

    PaqueteConsultaControlPruebas consulta;

    PaqueteProcesamientoControlPruebas procesamiento;

    PaqueteRegistroControlPruebas registro;

}
