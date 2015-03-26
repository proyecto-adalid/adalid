/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.core.predicates.IsEntityNameIncluded;
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
import meta.predicado.base.IsModuloRegistroDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloRegistroPrueba extends ModuloBase {

    static final String[] INCLUDED_ENTITY_NAMES = new String[]{
        AmbientePrueba.class.getSimpleName(),
        CasoPrueba.class.getSimpleName(),
        DocumentoPrueba.class.getSimpleName(),
        DocumentoPruebaX1.class.getSimpleName(),
        DocumentoPruebaX2.class.getSimpleName(),
        DocumentoPruebaX3.class.getSimpleName(),
        DocumentoPruebaX4.class.getSimpleName(),
        DocumentoPruebaX5.class.getSimpleName(),
        DocumentoPruebaX6.class.getSimpleName(),
        EjecucionLineaPrueba.class.getSimpleName(),
        EjecucionPrueba.class.getSimpleName(),
        EscenarioPrueba.class.getSimpleName(),
        LineaPrueba.class.getSimpleName(),
        PaquetePrueba.class.getSimpleName(),
        ParametroLineaPrueba.class.getSimpleName(),
        ParteAmbientePrueba.class.getSimpleName(),
        PiezaAmbientePrueba.class.getSimpleName(),
        ProgramaPrueba.class.getSimpleName(),
        TipoDocumentoPrueba.class.getSimpleName(),
        TipoPiezaPrueba.class.getSimpleName(),
        TipoResultadoPrueba.class.getSimpleName()
    };

    public ModuloRegistroPrueba() {
        super();
        setAlias("Prueba");
        setDefaultLabel("Registro de Pruebas");
        setDefaultDescription("Registro de Casos, Programas y Ejecución de Pruebas");
        IsEntityNameIncluded entityPredicate = new IsEntityNameIncluded();
        entityPredicate.setIncludedNames(INCLUDED_ENTITY_NAMES);
        IsModuloRegistroDisplay pagePredicate = new IsModuloRegistroDisplay(entityPredicate);
        setPagePredicate(pagePredicate);
    }

}
