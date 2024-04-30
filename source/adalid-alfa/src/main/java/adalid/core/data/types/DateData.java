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
package adalid.core.data.types;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.primitives.*;
import java.sql.Date;

/**
 * @author Jorge Campins
 */
public class DateData extends TemporalPrimitive {

    public static final Date EPOCH = new Date(getEpochInMillis());

    {
        XS2.setDataClass(this, DateData.class);
        XS2.setDataType(this, Date.class);
        setMinDate(TimeUtils.jdbcObject("0001-01-01"));
        setMaxDate(TimeUtils.jdbcObject("9999-12-31"));
    }

    private boolean _disabledWeekends;

    private boolean _disabledWeekdays;

    private boolean _disabledHolidays;

    private int _yearRange = Constants.DEFAULT_YEAR_RANGE;

    /**
     * @return the disabled weekends indicator
     */
    public boolean isDisabledWeekends() {
        return _disabledWeekends;
    }

    /**
     * @param disabled the disabled weekends indicator to set
     */
    public void setDisabledWeekends(boolean disabled) {
        checkScope();
        _disabledWeekends = disabled;
    }

    /**
     * @return the disabled weekdays indicator
     */
    public boolean isDisabledWeekdays() {
        return _disabledWeekdays;
    }

    /**
     * @param disabled the disabled weekdays indicator to set
     */
    public void setDisabledWeekdays(boolean disabled) {
        checkScope();
        _disabledWeekdays = disabled;
    }

    /**
     * @return the disabled holidays indicator
     */
    public boolean isDisabledHolidays() {
        return _disabledHolidays;
    }

    /**
     * @param disabled the disabled holidays indicator to set
     */
    public void setDisabledHolidays(boolean disabled) {
        checkScope();
        _disabledHolidays = disabled;
    }

    /**
     * @return the year range
     */
    public int getYearRange() {
        return _yearRange;
    }

    /**
     * @param range the year range to set
     */
    public void setYearRange(int range) {
        checkScope();
        _yearRange = range;
    }

}
