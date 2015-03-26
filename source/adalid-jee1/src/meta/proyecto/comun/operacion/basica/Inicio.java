/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun.operacion.basica;

import adalid.core.Project;
import meta.entidad.comun.configuracion.basica.OpcionMenu;
import meta.entidad.comun.operacion.basica.PaginaUsuario;

/**
 * @author Jorge Campins
 */
public class Inicio extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Gestión de Recursos");
        setDefaultDescription("Gestión de Recursos");
    }

    OpcionMenu Menu;

    PaginaUsuario Favoritos;

}
