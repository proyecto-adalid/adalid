/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.SortOption;

/**
 * @author Jorge Campins
 */
public class Constants {

    public static final int DEFAULT_SELECT_ROWS_LIMIT = 500;

    public static final SortOption DEFAULT_SELECT_SORT_OPTION = SortOption.ASC;

    public static final int DEFAULT_REPORT_ROWS_LIMIT = 10000;

    public static final SortOption DEFAULT_REPORT_SORT_OPTION = SortOption.ASC;

    public static final int DEFAULT_EXPORT_ROWS_LIMIT = 10000;

    public static final SortOption DEFAULT_EXPORT_SORT_OPTION = SortOption.ASC;

    public static final int DEFAULT_CHARACTER_KEY_MAX_LENGTH = 30;

    public static final int DEFAULT_NAME_PROPERTY_MAX_LENGTH = 100;

    public static final int MAX_DECIMAL_PRECISION = 1000;

    public static final int MAX_BYTE_DIVISOR = 100;

    public static final int MAX_SHORT_DIVISOR = 10000;

    public static final int MAX_INTEGER_DIVISOR = 1000000;

    public static final int MAX_STRING_LENGTH = 8000;

    public static final int MAX_TIME_PRECISION = 6;

    /**
     * DEFAULT_SELECT_ROWS_LIMIT getter (for velocity templates)
     *
     * @return DEFAULT_SELECT_ROWS_LIMIT
     */
    public static int getDefaultSelectRowsLimit() {
        return DEFAULT_SELECT_ROWS_LIMIT;
    }

    /**
     * DEFAULT_SELECT_SORT_OPTION getter (for velocity templates)
     *
     * @return DEFAULT_SELECT_SORT_OPTION
     */
    public static SortOption getDefaultSelectSortOption() {
        return DEFAULT_SELECT_SORT_OPTION;
    }

    /**
     * DEFAULT_REPORT_ROWS_LIMIT getter (for velocity templates)
     *
     * @return DEFAULT_REPORT_ROWS_LIMIT
     */
    public static int getDefaultReportRowsLimit() {
        return DEFAULT_REPORT_ROWS_LIMIT;
    }

    /**
     * DEFAULT_REPORT_SORT_OPTION getter (for velocity templates)
     *
     * @return DEFAULT_REPORT_SORT_OPTION
     */
    public static SortOption getDefaultReportSortOption() {
        return DEFAULT_REPORT_SORT_OPTION;
    }

    /**
     * DEFAULT_EXPORT_ROWS_LIMIT getter (for velocity templates)
     *
     * @return DEFAULT_EXPORT_ROWS_LIMIT
     */
    public static int getDefaultExportRowsLimit() {
        return DEFAULT_EXPORT_ROWS_LIMIT;
    }

    /**
     * DEFAULT_EXPORT_SORT_OPTION getter (for velocity templates)
     *
     * @return DEFAULT_EXPORT_SORT_OPTION
     */
    public static SortOption getDefaultExportSortOption() {
        return DEFAULT_EXPORT_SORT_OPTION;
    }

}
