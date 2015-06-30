/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.paquete.comun;

import meta.entidad.comun.auditoria.ArchivoAdjunto;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.auditoria.RastroFuncionPar;
import meta.entidad.comun.auditoria.RastroInforme;
import meta.entidad.comun.auditoria.RastroProceso;
import meta.paquete.base.PaqueteConsultaBase;

/**
 * @author Jorge Campins
 */
public class PaqueteConsultaAuditoria extends PaqueteConsultaBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosAuditoria");
        setDefaultLabel("Rastros de Auditoría");
        setDefaultDescription("Rastros de Auditoría");
        setFragmentoCabezaBotonAbrirTareasEnabled(false);
    }

    ArchivoAdjunto ArchivoAdjunto;

    RastroFuncion RastroFuncion;

    RastroFuncionPar RastroFuncionPar;

    RastroInforme RastroInforme;

    RastroProceso RastroProceso;

}
