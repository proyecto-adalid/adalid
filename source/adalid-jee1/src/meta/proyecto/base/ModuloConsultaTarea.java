/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.core.predicates.IsEntityNameIncluded;
import meta.entidad.comun.operacion.basica.CondicionTarea;
import meta.entidad.comun.operacion.basica.Tarea;
import meta.entidad.comun.operacion.basica.TareaUsuario;
import meta.predicado.base.IsModuloConsultaDisplay;

/**
 * @author Jorge Campins
 */
public class ModuloConsultaTarea extends ModuloBase {

    static final String[] INCLUDED_ENTITY_NAMES = new String[]{
        CondicionTarea.class.getSimpleName(),
        Tarea.class.getSimpleName(),
        TareaUsuario.class.getSimpleName()
    };

    public ModuloConsultaTarea() {
        super();
        setAlias("Tarea");
        setDefaultLabel("Consulta de Tareas");
        setDefaultDescription("Consulta de Tareas");
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        IsEntityNameIncluded entityPredicate = new IsEntityNameIncluded();
        entityPredicate.setIncludedNames(INCLUDED_ENTITY_NAMES);
        IsModuloConsultaDisplay pagePredicate = new IsModuloConsultaDisplay(entityPredicate);
        setPagePredicate(pagePredicate);
    }

}
