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
        setLocalizedDescription(ENGLISH, ""
            + "The Audit module keeps a record of the execution of all the functions, processes and reports of the application and of all "
            + "files uploaded to the server."
            + "");
        setLocalizedDescription(SPANISH, ""
            + "El módulo de Auditoría mantiene un registro de la ejecución de todas las funciones, procesos e informes de la aplicación y de todos "
            + "los archivos cargados al servidor. "
            + BR + BR
            + "El módulo de Auditoría incluye consolas de procesamiento y páginas de consulta para las siguientes clases de entidades: "
            + ul(
                "Archivos Adjuntos",
                "Rastros de Funciones",
                "Rastros de Parámetros de Funciones",
                "Rastros de Informes",
                "Rastros de Procesos"
            )
            + "El acceso a los rastros de auditoría es personalizado y, a la vez, segmentado por ejecutante. "
            + "Esto significa que, a menos que reciba otra autorización, solo podrá ver los rastros de sus ejecuciones y de las de sus supervisados. "
            + "");
        // </editor-fold>
    }

    ArchivoAdjunto ArchivoAdjunto;

    RastroFuncion RastroFuncion;

    RastroFuncionPar RastroFuncionPar;

    RastroInforme RastroInforme;

    RastroProceso RastroProceso;

    GrupoProceso GrupoProceso;

    PaqueteConsultaAuditoria consulta;

    PaqueteProcesamientoAuditoria procesamiento;

}
