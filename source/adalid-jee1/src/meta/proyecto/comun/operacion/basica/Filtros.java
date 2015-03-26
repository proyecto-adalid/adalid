/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun.operacion.basica;

import adalid.core.Project;
import meta.entidad.comun.operacion.basica.*;

/**
 * @author Jorge Campins
 */
public class Filtros extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Gestión de Filtros de Búsqueda");
        setDefaultDescription("Gestión de Filtros de Búsqueda");
    }

    FiltroFuncion FiltroFuncion;

    FiltroFuncionPar FiltroFuncionPar;

}
