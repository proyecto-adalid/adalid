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
import java.sql.Timestamp;

/**
 * @author Jorge Campins
 */
public class TimestampData extends TemporalPrimitive {

    public static final Timestamp EPOCH = new Timestamp(getEpochInMillis());

    {
        XS2.setDataClass(this, TimestampData.class);
        XS2.setDataType(this, Timestamp.class);
        setMinDate(TimeUtils.jdbcObject("0001-01-01 00:00:00"));
        setMaxDate(TimeUtils.jdbcObject("9999-12-31 23:59:59"));
    }

    private int _precision = Constants.DEFAULT_TIME_PRECISION;

    private boolean _disabledWeekends;

    private boolean _disabledWeekdays;

    private boolean _disabledHolidays;

    private int _yearRange = Constants.DEFAULT_YEAR_RANGE;

    private int _minHour = 0;

    private int _maxHour = 23;

    private int _stepHour = 1;

    private int _minMinute = 0;

    private int _maxMinute = 59;

    private int _stepMinute = 1;

    private int _minSecond = 0;

    private int _maxSecond = 59;

    private int _stepSecond = 1;

    /**
     * @return the precision
     */
    public int getPrecision() {
        return _precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(int precision) {
        checkScope();
        _precision = precision;
    }

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

    /**
     * @return the minimum hour
     */
    public int getMinHour() {
        return _minHour;
    }

    /**
     * @param min the minimum hour to set
     */
    public void setMinHour(int min) {
        checkScope();
        _minHour = min;
    }

    /**
     * @return the maximum hour
     */
    public int getMaxHour() {
        return _maxHour;
    }

    /**
     * @param max the maximum hour to set
     */
    public void setMaxHour(int max) {
        checkScope();
        _maxHour = max;
    }

    /**
     * @return the step hour
     */
    public int getStepHour() {
        return _stepHour;
    }

    /**
     * @param step the step hour to set
     */
    public void setStepHour(int step) {
        checkScope();
        _stepHour = step;
    }

    /**
     * @return the min minute
     */
    public int getMinMinute() {
        return _minMinute;
    }

    /**
     * @param min the min minute to set
     */
    public void setMinMinute(int min) {
        checkScope();
        _minMinute = min;
    }

    /**
     * @return the max minute
     */
    public int getMaxMinute() {
        return _maxMinute;
    }

    /**
     * @param max the max minute to set
     */
    public void setMaxMinute(int max) {
        checkScope();
        _maxMinute = max;
    }

    /**
     * @return the step minute
     */
    public int getStepMinute() {
        return _stepMinute;
    }

    /**
     * @param step the step minute to set
     */
    public void setStepMinute(int step) {
        checkScope();
        _stepMinute = step;
    }

    /**
     * @return the min second
     */
    public int getMinSecond() {
        return _minSecond;
    }

    /**
     * @param min the min second to set
     */
    public void setMinSecond(int min) {
        checkScope();
        _minSecond = min;
    }

    /**
     * @return the max second
     */
    public int getMaxSecond() {
        return _maxSecond;
    }

    /**
     * @param max the max second to set
     */
    public void setMaxSecond(int max) {
        checkScope();
        _maxSecond = max;
    }

    /**
     * @return the step second
     */
    public int getStepSecond() {
        return _stepSecond;
    }

    /**
     * @param step the step second to set
     */
    public void setStepSecond(int step) {
        checkScope();
        _stepSecond = step;
    }

}
