/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun.control.acceso;

import adalid.core.Project;
import meta.entidad.comun.control.acceso.*;

/**
 * @author Jorge Campins
 */
public class Usuarios extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Gestión de Usuarios");
        setDefaultDescription("Gestión de Usuarios");
    }

    Usuario Usuario;

    RolUsuario RolUsuario;

}
