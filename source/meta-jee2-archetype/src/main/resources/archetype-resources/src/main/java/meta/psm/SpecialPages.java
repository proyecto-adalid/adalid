#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.psm;

import adalid.commons.bundles.Bundle;
import adalid.jee2.SpecialPage;

/**
 * @author ADALID meta-jee2-archetype
 */
public class SpecialPages {

    public static SpecialPage dashboard() {
        SpecialPage dashboard = SpecialPage.of("Dashboard");
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero (Dashboard) de indicadores clave de rendimiento (KPIs) de la empresa");
        return dashboard;
    }

}
