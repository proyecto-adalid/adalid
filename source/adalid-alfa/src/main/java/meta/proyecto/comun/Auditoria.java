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
package meta.proyecto.comun;

import adalid.core.*;
import meta.entidad.comun.auditoria.ArchivoAdjunto;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.auditoria.RastroFuncionPar;
import meta.entidad.comun.auditoria.RastroInforme;
import meta.entidad.comun.auditoria.RastroProceso;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.paquete.comun.PaqueteConsultaAuditoria;
import meta.paquete.comun.PaqueteProcesamientoAuditoria;

/**
 * @author Jorge Campins
 */
public class Auditoria extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of Auditoria's attributes">
        setLocalizedLabel(ENGLISH, "Audit");
        setLocalizedLabel(SPANISH, "Auditoría");
//      setLocalizedShortDescription(ENGLISH, "Audit Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Auditoría");
        setLocalizedDescription(ENGLISH, meta.proyecto.comun.help.en.Auditoria.getHelpText());
        setLocalizedDescription(SPANISH, meta.proyecto.comun.help.es.Auditoria.getHelpText());
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
        System.out.println(GrupoProceso);
        System.out.println(consulta);
        System.out.println(procesamiento);
    }
    // </editor-fold>

    ArchivoAdjunto ArchivoAdjunto;

    RastroFuncion RastroFuncion;

    RastroFuncionPar RastroFuncionPar;

    RastroInforme RastroInforme;

    RastroProceso RastroProceso;

    GrupoProceso GrupoProceso;

    PaqueteConsultaAuditoria consulta;

    PaqueteProcesamientoAuditoria procesamiento;

}
