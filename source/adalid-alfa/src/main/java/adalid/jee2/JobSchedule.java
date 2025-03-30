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
package adalid.jee2;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class JobSchedule {

    private final String name;

    public JobSchedule(String name) {
        this.name = name;
    }

    /**
     * @return the job schedule name
     */
    public String getJobScheduleName() {
        return name;
    }

    /**
     * Specifies one or more seconds within a minute.
     */
    public String second = "0";

    /**
     * Specifies one or more minutes within an hour.
     */
    public String minute = "0";

    /**
     * Specifies one or more hours within a day.
     */
    public String hour = "0";

    /**
     * Specifies one or more days within a week.
     */
    public String dayOfWeek = "*";

    /**
     * Specifies one or more days within a month.
     */
    public String dayOfMonth = "*";

    /**
     * Specifies one or more months within a year.
     */
    public String month = "*";

    /**
     * Specifies one or more years.
     */
    public String year = "*";

    /**
     * Specifies the time zone within which the schedule is evaluated. Time zones are specified as an ID public String. The set of required time zone
     * IDs is defined by the Zone Name(TZ) column of the public domain zoneinfo database. If a timezone is not specified, the schedule is evaluated in
     * the context of the default timezone associated with the contianer in which the application is executing.
     */
    public String timezone = "";

    /**
     * Specifies an information public String that is associated with the timer
     */
    public String info = "";

    /**
     * Specifies whether the timer that is created is persistent.
     */
    public boolean persistent = false;

    /**
     * Specifies whether the timer that is created is permanently disabled.
     */
    public boolean permanentlyDisabled = false;

    /**
     * @return the second
     */
    public String getSecond() {
        return StringUtils.defaultIfBlank(second, "0");
    }

    /**
     * @return the minute
     */
    public String getMinute() {
        return StringUtils.defaultIfBlank(minute, "0");
    }

    /**
     * @return the hour
     */
    public String getHour() {
        return StringUtils.defaultIfBlank(hour, "0");
    }

    /**
     * @return the day of week
     */
    public String getDayOfWeek() {
        return StringUtils.defaultIfBlank(dayOfWeek, "*");
    }

    /**
     * @return the day of month
     */
    public String getDayOfMonth() {
        return StringUtils.defaultIfBlank(dayOfMonth, "*");
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return StringUtils.defaultIfBlank(month, "*");
    }

    /**
     * @return the year
     */
    public String getYear() {
        return StringUtils.defaultIfBlank(year, "*");
    }

    /**
     * @return the timezone
     */
    public String getTimezone() {
        return StringUtils.defaultIfBlank(timezone, "");
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return StringUtils.defaultIfBlank(info, "");
    }

    /**
     * @return the persistent indicator
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * @return the disabled indicator
     */
    public boolean isPermanentlyDisabled() {
        return permanentlyDisabled;
    }

}
