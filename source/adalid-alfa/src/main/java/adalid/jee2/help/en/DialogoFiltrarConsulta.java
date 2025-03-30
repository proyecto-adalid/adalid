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
package adalid.jee2.help.en;

import adalid.core.Project;
import adalid.jee2.help.AbstractHelpWriter;

import static adalid.commons.util.MarkupUtils.*;

/**
 * @author Jorge Campins
 */
public class DialogoFiltrarConsulta extends AbstractHelpWriter {

    public DialogoFiltrarConsulta(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String str = ""
            + "This dialog allows you to perform queries through predefined search fields or filters previously defined by you. "
            + BR + BR
            + "The first field of the dialog is " + b("filter") + ", "
            + "a drop-down list that allows you to select a search filter previously defined by you. "
            + "Following the list are these buttons: "
            + ul(
                ic("fa fa-plus") + " to define a new filter.",
                ic("fa fa-copy") + " to duplicate the selected filter.",
                ic("fa fa-table") + " to open the search filter registration page.",
                ic("fa fa-th") + " to view and edit the criteria for the selected filter."
            )
            + "On some pages, the dialog includes the drop-down list " + b("segment") + " after " + b("filter") + ". "
            + "Segments are filters defined by the application designer. "
            + BR + BR
            + "After " + b("filter") + " and " + b("segment") + " are the predefined search fields. "
            + "For each field you can select a comparison operator from the corresponding drop-down list. "
            + "Except in the case of logical fields, the comparison operator requires a value. "
            + "The component used to specify that value is located after the drop-down list of the comparison operator; "
            + "that component can be another drop-down list, or a text box. "
            + "In the case of text boxes for entity references, the value must be the entity code; "
            + "you can type or search for it by clicking the button immediately to the right of the text box. "
            + "In the case of temporal fields, that is, dates, times and timestamps (fields that have both date and time), "
            + "the value does not necessarily have to be a date, a time or a timestamp; "
            + "it can also be a " + b("temporal add or subtrahend") + ", that is, "
            + "a time interval to add or subtract from the current date or timestamp. "
            + "Intervals are represented by a positive integer (without sign or preceded by a plus sign) "
            + "or negative integer (preceded by the minus sign), followed by a letter that identifies the unit of time of the interval. Valid letters are: "
            + b("D") + " (days), " + b("M") + " (months), " + b("Y") + " (years), "
            + b("h") + " (hours), " + b("m") + " (minutes), and " + b("s") + " (seconds). "
            + "Intervals defined with capital letters (D, M, A) are added to or subtracted (depending on the sign of the number) from the current date. "
            + "Intervals defined with lowercase letters (h, m, s) are added or subtracted (depending on the sign of the number) to the current timestamp. "
            + "For example, to get records with a date in the last 3 months, you would specify the comparison operator "
            + b("Is greater than") + " and the value " + b("-3M") + " (with negative sign). "
            + BR + BR
            + "The result of the query will be the set of records that meet all the specified criteria. "
            + BR + BR
            + "At the bottom of the dialog are the buttons that correspond to the other available actions. These are: "
            + ul(
                b("Apply") + ": run a query with the specified search criteria.",
                b("Clear") + ": discard all specified search criteria. "
                + "The " + ic("fa fa-trash") + " button of each criteria allows you to discard only that criteria.",
                b("Save") + ": save a new filter with the specified criteria plus the specified search filter.",
                b("Hide") + ": hide the dialog.",
                b("Help") + ": display this help."
            )
            + "On pages that allow you to add new records, if there are no records that meet the specified search criteria, "
            + "one or more " + b("Add") + " buttons automatically appear, just below the other action buttons. "
            + BR + BR
            + "The query result is temporarily stored in a memory cache, therefore, "
            + "data transfer between application server and database server is reduced. "
            + "To avoid the indiscriminate use of the memory cache, the pages usually have a maximum limit of records that can be displayed; "
            + "usually that limit is 100 records. Search fields and filters serve to narrow down the result set. "
            + BR + BR
            + "The query result can be permanently stored as a file that can be viewed or downloaded "
            + "to be processed with an application installed on your computer. "
            + "The " + ic("fa fa-file-text") + " button on the action button bar displays the options menu for saving the query result. "
            + "";
        /**/
        return str;
    }

}
