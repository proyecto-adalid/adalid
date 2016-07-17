/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.comun;

import meta.entidad.comun.operacion.basica.TipoAgregacion;
import meta.entidad.comun.operacion.basica.VistaFuncion;
import meta.entidad.comun.operacion.basica.VistaFuncionCol;
import meta.paquete.base.PaqueteRegistroBase;

/**
 * @author Jorge Campins
 */
public class PaqueteRegistroVista extends PaqueteRegistroBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosBasicosVista");
        setDefaultLabel("Registro de Vistas");
        setDefaultDescription("Registro de Vistas");
        setFragmentoVisorEnabled(false);
        setFragmentoCabezaBotonAbrirFavoritosEnabled(false);
        setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
        setFragmentoCabezaBotonCambiarPasswordEnabled(false);
    }

    TipoAgregacion tipoAgregacion;

    VistaFuncion vistaFuncion;

    VistaFuncionCol vistaFuncionCol;

}
