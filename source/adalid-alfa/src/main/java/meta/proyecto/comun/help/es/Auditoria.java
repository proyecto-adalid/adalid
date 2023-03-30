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
package meta.proyecto.comun.help.es;

import static adalid.commons.util.MarkupUtils.*;

/**
 * @author Jorge Campins
 */
public class Auditoria {

    public static String getHelpText() {
        String str = ""
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
            + "";
        /**/
        return str;
    }

}
