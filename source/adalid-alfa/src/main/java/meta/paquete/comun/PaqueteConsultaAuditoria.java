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
import meta.paquete.base.PaqueteConsultaBase;

/**
 * @author Jorge Campins
 */
public class PaqueteConsultaAuditoria extends PaqueteConsultaBase {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setAlias("RecursosAuditoria");
//      setFragmentoCabezaBotonAgregarFavoritosEnabled(false);
        // <editor-fold defaultstate="collapsed" desc="localization of PaqueteConsultaAuditoria's attributes">
        setLocalizedLabel(ENGLISH, "Audit Trails Inquiry");
        setLocalizedLabel(SPANISH, "Consulta de Rastros de Auditoría");
        setLocalizedDescription(ENGLISH, "Audit Trails Inquiry");
        setLocalizedDescription(SPANISH, "Consulta de Rastros de Auditoría");
        setLocalizedShortLabel(ENGLISH, "Audit Trails");
        setLocalizedShortLabel(SPANISH, "Rastros de Auditoría");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(ArchivoAdjunto);
        System.out.println(RastroFuncion);
        System.out.println(RastroFuncionPar);
        System.out.println(RastroInforme);
        System.out.println(RastroProceso);
    }
    // </editor-fold>

    ArchivoAdjunto ArchivoAdjunto;

    RastroFuncion RastroFuncion;

    RastroFuncionPar RastroFuncionPar;

    RastroInforme RastroInforme;

    RastroProceso RastroProceso;

}
