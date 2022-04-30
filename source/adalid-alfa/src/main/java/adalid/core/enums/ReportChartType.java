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
package adalid.core.enums;

import adalid.core.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public enum ReportChartType {

    BAR("bar3DChart", Constants.SERIES_BY_AGGREGATE),
    BAR_BY_GROUP("bar3DChart", Constants.SERIES_BY_GROUP),
    STACKED_BAR("stackedBar3DChart", Constants.SERIES_BY_AGGREGATE),
    STACKED_BAR_BY_GROUP("stackedBar3DChart", Constants.SERIES_BY_GROUP),
    AREA("areaChart", Constants.SERIES_BY_AGGREGATE),
    AREA_BY_GROUP("areaChart", Constants.SERIES_BY_GROUP),
    STACKED_AREA("stackedAreaChart", Constants.SERIES_BY_AGGREGATE),
    STACKED_AREA_BY_GROUP("stackedAreaChart", Constants.SERIES_BY_GROUP),
    LINE("lineChart", Constants.SERIES_BY_AGGREGATE),
    LINE_BY_GROUP("lineChart", Constants.SERIES_BY_GROUP),
    PIE("pie3DChart", "");

    private final String _jasperChartType, _jasperChartSubtype, _jasperChartName;

    ReportChartType(String jasperChartType, String jasperChartSubtype) {
        _jasperChartType = jasperChartType;
        _jasperChartSubtype = jasperChartSubtype;
        _jasperChartName = jasperChartSubtype.isEmpty() ? jasperChartType : jasperChartType + StringUtils.capitalize(jasperChartSubtype);
    }

    public String getJasperChartName() {
        return _jasperChartName;
    }

    public String getJasperChartType() {
        return _jasperChartType;
    }

    public String getJasperChartSubtype() {
        return _jasperChartSubtype;
    }

}
