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
 * @author ADALID meta-jee2-archetype, version 6.1.0
 */
public class SpecialPages {

    public static SpecialPage dashboard() {
        SpecialPage dashboard = SpecialPage.of("Dashboard");
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero (Dashboard) de indicadores clave de rendimiento (KPIs) de la empresa");
        return dashboard;
    }

    public static SpecialPage dashboardFreya() {
        SpecialPage dashboard = SpecialPage.of("DashboardFreya", SpecialPage.view("dashboards/DashboardFreya"));
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedLabel(Bundle.ENGLISH, "Freya Dashboard");
        dashboard.setLocalizedLabel(Bundle.SPANISH, "Tablero «Freya»");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Freya Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero «Freya» de indicadores clave de rendimiento (KPIs) de la empresa");
        return dashboard;
    }

    public static SpecialPage dashboardSerenity() {
        SpecialPage dashboard = SpecialPage.of("DashboardSerenity", SpecialPage.view("dashboards/DashboardSerenity"));
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedLabel(Bundle.ENGLISH, "Serenity Dashboard");
        dashboard.setLocalizedLabel(Bundle.SPANISH, "Tablero «Serenity»");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Serenity Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero «Serenity» de indicadores clave de rendimiento (KPIs) de la empresa");
        return dashboard;
    }

}
