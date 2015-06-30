/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun;

import adalid.core.Project;
import meta.entidad.comun.configuracion.basica.GrupoProceso;

/**
 * @author Jorge Campins
 */
public class ControlProcesos extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Control de Procesos");
        setDefaultDescription("Control de Procesos");
    }

    GrupoProceso grupoProceso;

}
