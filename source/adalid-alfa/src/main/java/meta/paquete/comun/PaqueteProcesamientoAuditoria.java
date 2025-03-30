/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package meta.paquete.comun;

import meta.entidad.comun.auditoria.ArchivoAdjunto;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.auditoria.RastroFuncionPar;
import meta.entidad.comun.auditoria.RastroInforme;
import meta.entidad.comun.auditoria.RastroProceso;
import meta.paquete.base.PaqueteProcesamientoBase;

/**
 * @author Jorge Campins
 */
public class PaqueteProcesamientoAuditoria extends PaqueteProcesamientoBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosAuditoria");
//      setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteProcesamientoAuditoria's attributes">
        setLocalizedLabel(ENGLISH, "Audit Trails Processing");
        setLocalizedLabel(SPANISH, "Procesamiento de Rastros de Auditoría");
        setLocalizedDescription(ENGLISH, "Audit Trails Processing");
        setLocalizedDescription(SPANISH, "Procesamiento de Rastros de Auditoría");
        setLocalizedShortLabel(ENGLISH, "Audit Trails");
        setLocalizedShortLabel(SPANISH, "Rastros de Auditoría");
        // </editor-fold>
    }

    protected ArchivoAdjunto ArchivoAdjunto;

    protected RastroFuncion RastroFuncion;

    protected RastroFuncionPar RastroFuncionPar;

    protected RastroInforme RastroInforme;

    protected RastroProceso RastroProceso;

}
