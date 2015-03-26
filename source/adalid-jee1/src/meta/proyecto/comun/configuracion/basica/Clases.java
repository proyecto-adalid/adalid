/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.comun.configuracion.basica;

import adalid.core.Project;
import meta.entidad.comun.configuracion.basica.*;

/**
 * @author Jorge Campins
 */
public class Clases extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setDefaultLabel("Gestión de Clases");
        setDefaultDescription("Gestión de Clases");
    }

    ClaseRecurso ClaseRecurso;

    Dominio Dominio;

    DominioParametro DominioParametro;

    Funcion Funcion;

    FuncionParametro FuncionParametro;

    Parametro Parametro;

}
