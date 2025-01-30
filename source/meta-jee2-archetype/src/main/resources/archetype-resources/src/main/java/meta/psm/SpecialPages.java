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
 * @author ADALID meta-jee2-archetype, version 6.0.0
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

    public static SpecialPage dashboardMirage() {
        SpecialPage dashboard = SpecialPage.of("DashboardMirage", SpecialPage.view("dashboards/DashboardMirage"));
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedLabel(Bundle.ENGLISH, "Mirage Dashboard");
        dashboard.setLocalizedLabel(Bundle.SPANISH, "Tablero «Mirage»");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Mirage Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero «Mirage» de indicadores clave de rendimiento (KPIs) de la empresa");
        return dashboard;
    }

    public static SpecialPage dashboardOmega() {
        SpecialPage dashboard = SpecialPage.of("DashboardOmega", SpecialPage.view("dashboards/DashboardOmega"));
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedLabel(Bundle.ENGLISH, "Omega Dashboard");
        dashboard.setLocalizedLabel(Bundle.SPANISH, "Tablero «Omega»");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Omega Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero «Omega» de indicadores clave de rendimiento (KPIs) de la empresa");
        return dashboard;
    }

    public static SpecialPage dashboardPrestige() {
        SpecialPage dashboard = SpecialPage.of("DashboardPrestige", SpecialPage.view("dashboards/DashboardPrestige"));
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedLabel(Bundle.ENGLISH, "Prestige Dashboard");
        dashboard.setLocalizedLabel(Bundle.SPANISH, "Tablero «Prestige»");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Prestige Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero «Prestige» de indicadores clave de rendimiento (KPIs) de la empresa");
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

    public static SpecialPage dashboardUltima() {
        SpecialPage dashboard = SpecialPage.of("DashboardUltima", SpecialPage.view("dashboards/DashboardUltima"));
        dashboard.setIconClass("fa fa-dashboard");
        dashboard.setLocalizedLabel(Bundle.ENGLISH, "Ultima Dashboard");
        dashboard.setLocalizedLabel(Bundle.SPANISH, "Tablero «Ultima»");
        dashboard.setLocalizedShortDescription(Bundle.ENGLISH, "Ultima Dashboard of the company key performance indicators (KPIs)");
        dashboard.setLocalizedShortDescription(Bundle.SPANISH, "Tablero «Ultima» de indicadores clave de rendimiento (KPIs) de la empresa");
        return dashboard;
    }

}
