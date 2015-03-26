/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.control.prueba.*;

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

    AmbientePrueba AmbientePrueba;

    PaquetePrueba PaquetePrueba;

    CasoPrueba CasoPrueba;

    EscenarioPrueba EscenarioPrueba;

    ProgramaPrueba ProgramaPrueba;

    LineaPrueba LineaPrueba;

    EjecucionPrueba EjecucionPrueba;

    EjecucionLineaPrueba EjecucionLineaPrueba;

    ParametroLineaPrueba ParametroLineaPrueba;

    ParteAmbientePrueba ParteAmbientePrueba;

    PiezaAmbientePrueba PiezaAmbientePrueba;

    DocumentoPrueba DocumentoPrueba;

    DocumentoPruebaX1 DocumentoPruebaX1;

    DocumentoPruebaX2 DocumentoPruebaX2;

    DocumentoPruebaX3 DocumentoPruebaX3;

    DocumentoPruebaX4 DocumentoPruebaX4;

    DocumentoPruebaX5 DocumentoPruebaX5;

    DocumentoPruebaX6 DocumentoPruebaX6;
//
//  TipoDocumentoPrueba TipoDocumentoPrueba;
//
//  TipoPiezaPrueba TipoPiezaPrueba;
//
//  TipoResultadoPrueba TipoResultadoPrueba;

}
