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

import adalid.commons.util.*;
import adalid.core.enums.*;
import java.util.Arrays;

/**
 * @author Jorge Campins
 */
public class Constants {

    public static final int DEFAULT_ROWS_PER_PAGE = 10;

    public static final int DEFAULT_ROWS_PER_PAGE_LIMIT = 100;

    public static final int MINIMUM_ROWS_PER_PAGE_LIMIT = DEFAULT_ROWS_PER_PAGE / 2;

    public static final int MAXIMUM_ROWS_PER_PAGE_LIMIT = 10 * DEFAULT_ROWS_PER_PAGE_LIMIT;

    public static final int DEFAULT_SELECT_ROWS_LIMIT = 100;

    public static final int MAXIMUM_SELECT_ROWS_LIMIT = 10000; // tree-views could select more rows than table-views

    public static final SortOption DEFAULT_SELECT_SORT_OPTION = SortOption.ASC;

    public static final int DEFAULT_REPORT_ROWS_LIMIT = 10000;

    public static final int MAXIMUM_REPORT_ROWS_LIMIT = 1000000;

    public static final SortOption DEFAULT_REPORT_SORT_OPTION = SortOption.ASC;

    public static final int DEFAULT_EXPORT_ROWS_LIMIT = 10000;

    public static final int MAXIMUM_EXPORT_ROWS_LIMIT = 1000000;

    public static final SortOption DEFAULT_EXPORT_SORT_OPTION = SortOption.ASC;

    public static final int DEFAULT_CHARACTER_KEY_MAX_LENGTH = 30;

    public static final int DEFAULT_NAME_PROPERTY_MAX_LENGTH = 100;

    public static final int DEFAULT_DESCRIPTION_PROPERTY_MAX_LENGTH = 2000;

    public static final int DEFAULT_URL_PROPERTY_MAX_LENGTH = 2000;

    public static final int DEFAULT_EMBEDDED_DOCUMENT_MAX_LENGTH = 2000;

    public static final int DEFAULT_FILE_REFERENCE_MAX_LENGTH = 2000;

    public static final int DEFAULT_STRING_FIELD_MAX_LENGTH = 2000;

    public static final int DEFAULT_STRING_INDEX_MAX_LENGTH = 1596; // AL32UTF8 max key length: Oracle ~1596(6398b); PostgreSQL ~680?(2730b)

    public static final int DEFAULT_MAX_INPUT_FILE_SIZE = 100000; // 1000000; until 23/05/2022

    public static final int DEFAULT_NUMERIC_DIVISOR = 100;

    public static final int DEFAULT_DECIMAL_PRECISION = 16;

    public static final int DEFAULT_DECIMAL_SCALE = 2;

    public static final int DEFAULT_TIME_PRECISION = 3;

    public static final int DEFAULT_YEAR_RANGE = 5;

    public static final int DEFAULT_LARGE_DOCUMENT_WIDTH = 640;

    public static final int DEFAULT_LARGE_DOCUMENT_HEIGHT = 360;

    public static final int DEFAULT_LARGE_IMAGE_WIDTH = 288;

    public static final int DEFAULT_LARGE_IMAGE_HEIGHT = 288;

    public static final int DEFAULT_MEDIUM_DOCUMENT_WIDTH = 480;

    public static final int DEFAULT_MEDIUM_DOCUMENT_HEIGHT = 270;

    public static final int DEFAULT_MEDIUM_IMAGE_WIDTH = 192;

    public static final int DEFAULT_MEDIUM_IMAGE_HEIGHT = 192;

    public static final int DEFAULT_SMALL_DOCUMENT_WIDTH = 320;

    public static final int DEFAULT_SMALL_DOCUMENT_HEIGHT = 180;

    public static final int DEFAULT_SMALL_IMAGE_WIDTH = 96;

    public static final int DEFAULT_SMALL_IMAGE_HEIGHT = 96;

    public static final int MAX_DECIMAL_PRECISION = 60; // Long scale: Decillion, Short scale: Novemdecillion (USA & Canada)

    public static final int MAX_BYTE_DIVISOR = 100;

    public static final int MAX_SHORT_DIVISOR = 10000;

    public static final int MAX_INTEGER_DIVISOR = 1000000;

    public static final int MAX_STRING_LENGTH = StrUtils.MAX_STRING_LENGTH;

    public static final int MAX_STRING_INDEX_LENGTH = 6384;

    public static final int MAX_EMAIL_ADDRESS_LENGTH = 254; // https://www.rfc-editor.org/errata/eid1690

    public static final int MAX_PHONE_NUMBER_LENGTH = 20;

    public static final int MAX_LOCAL_PHONE_NUMBER_LENGTH = 20;

    public static final int MAX_TIME_PRECISION = 6;

    public static final int MAX_YEAR_RANGE = 100;

    public static final int MAX_DISPLAY_WIDTH = 1920; // 3840;

    public static final int MAX_DISPLAY_HEIGHT = 1080; // 2160;

    public static final int MAX_UPLOAD_FILE_LIMIT = 100;

    public static final int MAX_UPLOAD_UNDO_LIMIT = 4;

    public static final int MAX_SERIES_START = 2000000000; // 1000000000

    public static final int MAX_SERIES_STOP = 2100000000; // 2000000000

    public static final int MAX_SERIES_STEP = 1000000; // 1000000000

    public static final int MAX_ENTITY_SERIES_START = 2000000000; // 1000000000

    public static final int MAX_ENTITY_SERIES_STOP = 2100000000; // 2000000000

    public static final int MAX_ENTITY_SERIES_STEP = 1000000; // 1000000000

    public static final String QUICK_FILTER_SNIPPET = "/resources/snippets/base/panel/quickFilterPanel";

    public static final String SERIES_BY_AGGREGATE = "seriesByAggregate";

    public static final String SERIES_BY_GROUP = "seriesByGroup";

    public static final String DEFAULT_HELP_FILE_TYPE = "html";

    public static final String[] VALID_HELP_FILE_TYPES = {"html", "xhtml", "pdf", "gif", "jpg", "jpeg", "png"};

    public static final String VALID_HELP_FILE_TYPES_CSV = StrUtils.disclose(Arrays.toString(VALID_HELP_FILE_TYPES), '[', ']');

    /**
     * Java class name
     */
    public static final String JAVA_CLASS_NAME_REGEX = "([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*";

    /**
     * Font Awesome class regex
     */
    public static final String FONT_AWESOME_CLASS_REGEX = "^.*\\bfa[a-z]?(\\sfa[a-z]?\\-[a-z\\-]*)+.*$";

    /**
     * Unicode symbol class regex
     */
    public static final String UNICODE_SYMBOL_CLASS_REGEX = "^.*\\bxs[a-z]?(\\sxs[a-z]?\\-[a-z\\-]*)+.*$";

    /**
     * Java email validation using regex. https://howtodoinjava.com/regex/java-regex-validate-email-address/
     */
    public static final String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /**
     * Java phone number validation using regex. https://howtodoinjava.com/java/regex/java-regex-validate-international-phone-numbers/
     * <p>
     * Regex : ^\+(?:[0-9]\\s?){6,14}[0-9]$
     */
    public static final String PHONE_REGEX = "^\\+(?:\\d{1,3})[-\\s](?:\\d{1,4})(?:[-\\s]?\\d{6,10})$";

    /**
     * Java phone number validation using regex.
     */
    public static final String LOCAL_PHONE_REGEX = "^(?:\\d{1,4})(?:[-\\s]?\\d{6,10})$";

    /**
     * The Perfect URL Regular Expression. http://urlregex.com/
     */
    private static final String url_regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    /**
     * The Perfect URL Regular Expression. http://urlregex.com/
     */
    public static final String URL_REGEX = "^" + url_regex + "$";

    /**
     * RegEx testing from Dan's Tools. https://www.regextester.com/99829/
     */
    private static final String iframe_ini_regex = "(?:<iframe\\b[^>]*)";

    /**
     * RegEx testing from Dan's Tools. https://www.regextester.com/99829/
     */
    private static final String iframe_src_regex = "\\bsrc=\"" + url_regex + "\"";

    /**
     * RegEx testing from Dan's Tools. https://www.regextester.com/99829/
     */
    private static final String iframe_end_regex = "(?:(?:\\/>)|(?:>.*?<\\/iframe>))";

    /**
     * RegEx testing from Dan's Tools. https://www.regextester.com/99829/
     */
    public static final String iframe_regex = iframe_ini_regex + (iframe_src_regex + ".*") + iframe_end_regex;

    /**
     * RegEx testing from Dan's Tools. https://www.regextester.com/99829/
     */
    public static final String IFRAME_REGEX = "^" + iframe_regex + "$";

    /**
     * A simple pattern to get the definition of an iframe using MessageFormat.format
     * <p>
     * The pattern includes the following parameters:
     * <ul>
     * <li><b>{0}</b> Attribute <b>src</b>: Specifies the URL of the document to embed in the iframe</li>
     * <li><b>{1}</b> Attribute <b>width</b>: Specifies the width of the iframe in pixels</li>
     * <li><b>{2}</b> Attribute <b>height</b>: Specifies the height of the iframe in pixels</li>
     * </ul>
     */
    public static final String IFRAME_SIMPLE_PATTERN = ""
        + "<iframe src=\"{0}\" width=\"{1}\" height=\"{2}\" "
        + "frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen>"
        + "</iframe>"
        + "";

    /**
     * RegEx testing from Dan's Tools. https://www.regextester.com/99829/
     */
    public static final String EMBEDDED_DOCUMENT_REGEX = "^(" + iframe_regex + "|" + url_regex + ")$";

    /**
     * Help file name regex
     */
    public static final String HELP_FILE_NAME_REGEX = "^/?([\\w-]+/)*[\\w-]+\\.[a-zA-Z]+$";

    /**
     * Snippet file name regex
     */
    public static final String SNIPPET_FILE_NAME_REGEX = "^/?([\\w-]+/)*[\\w-]+(\\.[a-zA-Z]+)?$";

    /**
     * non-whitespace-only regex
     */
    public static final String WHITESPACELESS_REGEX = "^[\\S]+$";

    /**
     * DEFAULT_ROWS_PER_PAGE getter (for velocity templates)
     *
     * @return DEFAULT_ROWS_PER_PAGE
     */
    public static int getDefaultRowsPerPage() {
        return DEFAULT_ROWS_PER_PAGE;
    }

    /**
     * DEFAULT_ROWS_PER_PAGE_LIMIT getter (for velocity templates)
     *
     * @return DEFAULT_ROWS_PER_PAGE_LIMIT
     */
    public static int getDefaultRowsPerPageLimit() {
        return DEFAULT_ROWS_PER_PAGE_LIMIT;
    }

    /**
     * MAXIMUM_ROWS_PER_PAGE_LIMIT getter (for velocity templates)
     *
     * @return MAXIMUM_ROWS_PER_PAGE_LIMIT
     */
    public static int getMaximumRowsPerPageLimit() {
        return MAXIMUM_ROWS_PER_PAGE_LIMIT;
    }

    /**
     * MINIMUM_ROWS_PER_PAGE_LIMIT getter (for velocity templates)
     *
     * @return MINIMUM_ROWS_PER_PAGE_LIMIT
     */
    public static int getMinimumRowsPerPageLimit() {
        return MINIMUM_ROWS_PER_PAGE_LIMIT;
    }

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
