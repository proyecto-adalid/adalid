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
package adalid.core;

import adalid.commons.bundles.Bundle;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class TemporalAddend implements Comparable {

    static final String CANONICAL_NAME = TemporalAddend.class.getCanonicalName();

    // <editor-fold defaultstate="collapsed" desc="static fields">
    static final char OTHER_YEARS_1 = 'y';

    static final char OTHER_YEARS_2 = 'A';

    static final char OTHER_YEARS_3 = 'a';

    static final char OTHER_DAYS_1 = 'd';

    static final char OTHER_HOURS_1 = 'H';

    static final char OTHER_SECONDS_1 = 'S';

    public static final char YEARS = 'Y';

    public static final char MONTHS = 'M';

    public static final char DAYS = 'D';

    public static final char HOURS = 'h';

    public static final char MINUTES = 'm';

    public static final char SECONDS = 's';

    public static final char DEFAULT_UNIT = DAYS;

    public static final char[] DATE_UNITS = {YEARS, MONTHS, DAYS};

    public static final char[] TIME_UNITS = {HOURS, MINUTES, SECONDS};

    public static final char[] DATE_TIME_UNITS = {YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS};

    public static final TemporalAddend MIN_VALUE = new TemporalAddend(-1000, YEARS);

    public static final TemporalAddend MAX_VALUE = new TemporalAddend(+1000, YEARS);

    public static final TemporalAddend MIN_INT_VALUE = new TemporalAddend(-60, YEARS);

    public static final TemporalAddend MAX_INT_VALUE = new TemporalAddend(+60, YEARS);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static methods">
    public static TemporalAddend temporalAddendValueOf(String string) {
        return temporalAddendValueOf(string, DATE_TIME_UNITS, DEFAULT_UNIT, MIN_VALUE, MAX_VALUE);
    }

    public static TemporalAddend temporalAddendValueOf(String string, char[] validUnits, char defaultUnit) {
        return temporalAddendValueOf(string, validUnits, defaultUnit, MIN_VALUE, MAX_VALUE);
    }

    public static TemporalAddend temporalAddendValueOf(String string, char[] validUnits, char defaultUnit, String min, String max) {
        TemporalAddend minimum = temporalAddendValueOf(min, validUnits, defaultUnit, MIN_VALUE, MAX_VALUE);
        TemporalAddend maximum = temporalAddendValueOf(max, validUnits, defaultUnit, MIN_VALUE, MAX_VALUE);
        return temporalAddendValueOf(string, validUnits, defaultUnit, minimum, maximum);
    }

    public static TemporalAddend temporalAddendValueOf(String string, char[] validUnits, char defaultUnit, TemporalAddend min, TemporalAddend max) {
        String trimmed = StringUtils.trimToNull(string);
        if (trimmed == null) {
            return null;
        }
        if (validUnits == null) {
            validUnits = DATE_TIME_UNITS;
        } else {
            validUnits = fix(validUnits);
            if (!valid(validUnits)) {
                validUnits = DATE_TIME_UNITS;
            }
        }
        defaultUnit = fix(defaultUnit);
        if (!valid(defaultUnit)) {
            defaultUnit = DEFAULT_UNIT;
        }
        if (min == null) {
            min = MIN_VALUE;
        }
        if (max == null) {
            max = MAX_VALUE;
        }
        if (min.compareTo(max) > 0) {
            min = MIN_VALUE;
            max = MAX_VALUE;
        }
        String chopped;
        int end = trimmed.length() - 1;
        char unit = fix(trimmed.charAt(end));
        if (Character.isDigit(unit)) {
            chopped = trimmed;
            unit = defaultUnit;
        } else if (end > 0 && ArrayUtils.contains(validUnits, unit)) {
            chopped = trimmed.substring(0, end);
        } else {
            return null;
        }
        try {
            Integer number = Integer.valueOf(chopped);
            return new TemporalAddend(number, unit, min, max);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static char[] fix(char[] units) {
        if (units == null || units.length == 0) {
            return null;
        }
        for (int i = 0; i < units.length; i++) {
            units[i] = fix(units[i]);
        }
        return units;
    }

    private static char fix(char unit) {
        return switch (unit) {
            case OTHER_SECONDS_1 ->
                SECONDS;
            case OTHER_HOURS_1 ->
                HOURS;
            case OTHER_DAYS_1 ->
                DAYS;
            case OTHER_YEARS_1, OTHER_YEARS_2, OTHER_YEARS_3 ->
                YEARS;
            default ->
                unit;
        };
    }

    private static boolean valid(char[] units) {
        if (units == null || units.length == 0) {
            return false;
        }
        for (int i = 0; i < units.length; i++) {
            if (!valid(units[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean valid(char unit) {
        return ArrayUtils.contains(DATE_TIME_UNITS, unit);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final int quantity;

    private final char unitCode;

    private final TemporalAddend minValue;

    private final TemporalAddend maxValue;

    private final boolean badValue;
    // </editor-fold>

    private TemporalAddend(int quantity, char unitCode) {
        this(quantity, unitCode, null, null);
    }

    private TemporalAddend(int quantity, char unitCode, TemporalAddend minValue, TemporalAddend maxValue) {
        this.quantity = quantity;
        this.unitCode = unitCode;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.badValue = minValue != null && maxValue != null && (this.compareTo(minValue) < 0 || this.compareTo(maxValue) > 0);
    }

    /**
     * @return the number
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return the unit code
     */
    public char getUnitCode() {
        return unitCode;
    }

    /**
     * @return the unit name
     */
    public String getUnitName() {
        switch (unitCode) {
            case SECONDS -> {
                return "seconds";
            }
            case MINUTES -> {
                return "minutes";
            }
            case HOURS -> {
                return "hours";
            }
            case DAYS -> {
                return "days";
            }
            case MONTHS -> {
                return "months";
            }
            case YEARS -> {
                return "years";
            }
        }
        return null;
    }

    /**
     * @return the localized unit name
     */
    public String getLocalizedUnitName() {
        String key = CANONICAL_NAME + "." + getUnitName();
        return Bundle.getString(key);
    }

    /**
     * @return the minimum value
     */
    public TemporalAddend getMinValue() {
        return minValue;
    }

    /**
     * @return the maximum value
     */
    public TemporalAddend getMaxValue() {
        return maxValue;
    }

    /**
     * @return the bad value (out of range) indicator
     */
    public boolean isBadValue() {
        return badValue;
    }

    // <editor-fold defaultstate="collapsed" desc="conversion methods">
    public long toSeconds() {
        return (long) seconds();
    }

    private double seconds() {
        switch (unitCode) {
            case SECONDS -> {
                return quantity;
            }
            case MINUTES -> {
                return quantity * 60;
            }
            case HOURS -> {
                return quantity * 60 * 60;
            }
            case DAYS -> {
                return quantity * 60 * 60 * 24;
            }
            case MONTHS -> {
                return quantity * 60 * 60 * 24 * 30.4375D;
            }
            case YEARS -> {
                return quantity * 60 * 60 * 24 * 365.25D;
            }
        }
        return 0D;
    }

    public long toMinutes() {
        return (long) minutes();
    }

    private double minutes() {
        switch (unitCode) {
            case SECONDS -> {
                return quantity / 60.0D;
            }
            case MINUTES -> {
                return quantity;
            }
            case HOURS -> {
                return quantity * 60;
            }
            case DAYS -> {
                return quantity * 60 * 24;
            }
            case MONTHS -> {
                return quantity * 60 * 24 * 30.4375D;
            }
            case YEARS -> {
                return quantity * 60 * 24 * 365.25D;
            }
        }
        return 0D;
    }

    public long toHours() {
        return (long) hours();
    }

    private double hours() {
        switch (unitCode) {
            case SECONDS -> {
                return quantity / 60.0D / 60.0D;
            }
            case MINUTES -> {
                return quantity / 60.0D;
            }
            case HOURS -> {
                return quantity;
            }
            case DAYS -> {
                return quantity * 24;
            }
            case MONTHS -> {
                return quantity * 24 * 30.4375D;
            }
            case YEARS -> {
                return quantity * 24 * 365.25D;
            }
        }
        return 0D;
    }

    public long toDays() {
        return (long) days();
    }

    private double days() {
        switch (unitCode) {
            case SECONDS -> {
                return quantity / 24.0D / 60.0D / 60.0D;
            }
            case MINUTES -> {
                return quantity / 24.0D / 60.0D;
            }
            case HOURS -> {
                return quantity / 24.0D;
            }
            case DAYS -> {
                return quantity;
            }
            case MONTHS -> {
                return quantity * 30.4375D;
            }
            case YEARS -> {
                return quantity * 365.25D;
            }
        }
        return 0D;
    }

    public long toMonths() {
        return (long) months();
    }

    private double months() {
        switch (unitCode) {
            case SECONDS -> {
                return quantity / 30.4375D / 24.0D / 60.0D / 60.0D;
            }
            case MINUTES -> {
                return quantity / 30.4375D / 24.0D / 60.0D;
            }
            case HOURS -> {
                return quantity / 30.4375D / 24.0D;
            }
            case DAYS -> {
                return quantity / 30.4375D;
            }
            case MONTHS -> {
                return quantity;
            }
            case YEARS -> {
                return quantity * 12;
            }
        }
        return 0D;
    }

    public long toYears() {
        return (long) years();
    }

    private double years() {
        switch (unitCode) {
            case SECONDS -> {
                return quantity / 365.25D / 24.0D / 60.0D / 60.0D;
            }
            case MINUTES -> {
                return quantity / 365.25D / 24.0D / 60.0D;
            }
            case HOURS -> {
                return quantity / 365.25D / 24.0D;
            }
            case DAYS -> {
                return quantity / 365.25D;
            }
            case MONTHS -> {
                return quantity / 12.0D;
            }
            case YEARS -> {
                return quantity;
            }
        }
        return 0D;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comparable methods">
    @Override
    public int compareTo(Object object) {
        if (object instanceof TemporalAddend that) {
            return compareTo(that);
        }
        throw new IllegalArgumentException();
    }

    private int compareTo(TemporalAddend that) {
        Double seconds = this.seconds();
        return seconds.compareTo(that.seconds());
    }
    // </editor-fold>

    @Override
    public String toString() {
        return quantity + "" + unitCode;
    }

}
