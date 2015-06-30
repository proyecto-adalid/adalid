/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
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
        setDefaultLabel("Procesamiento de Recursos de Control de Pruebas");
        setDefaultDescription("Procesamiento de Recursos de Control de Pruebas");
    }

    AmbientePrueba ambientePrueba;

    CasoPrueba casoPrueba;

    DocumentoPrueba documentoPrueba;

    DocumentoPruebaX1 documentoPruebaX1;

    DocumentoPruebaX2 documentoPruebaX2;

    DocumentoPruebaX3 documentoPruebaX3;

    DocumentoPruebaX4 documentoPruebaX4;

    DocumentoPruebaX5 documentoPruebaX5;

    DocumentoPruebaX6 documentoPruebaX6;

    EjecucionLineaPrueba ejecucionLineaPrueba;

    EjecucionPrueba ejecucionPrueba;

    EscenarioPrueba escenarioPrueba;

    LineaPrueba lineaPrueba;

    PaquetePrueba paquetePrueba;

    ParametroLineaPrueba parametroLineaPrueba;

    ParteAmbientePrueba parteAmbientePrueba;

    PiezaAmbientePrueba piezaAmbientePrueba;

    ProgramaPrueba programaPrueba;

    TipoDocumentoPrueba tipoDocumentoPrueba;

    TipoPiezaPrueba tipoPiezaPrueba;

    TipoResultadoPrueba tipoResultadoPrueba;

}
