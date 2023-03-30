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
public class BarraBotonesAccion2 extends AbstractHelpWriter {

    public BarraBotonesAccion2(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String str = ""
            + h3("Row toolbar")
            + "Each row of the table includes a toolbar, which can contain the following buttons:"
            + ul(
                ic("fa fa-list") + " to open the context menu. "
                + "That menu includes a list of related pages and a list of the entity's business functions. "
                + "In the list of pages you can select a page to open it and "
                + "in the list of functions you can select a function to execute it.",
                ic("fa fa-edit") + " to edit the row. This button is only shown on registration pages.",
                ic("fa fa-trash") + " to delete the row. This button is only shown on registration pages.",
                ic("fa fa-reply") + " discard the changes made to the row. This button is only shown on registration pages, when adding or editing the row.",
                ic("fa fa-arrow-left") + " to return the row. This button is only shown in search dialogs."
            )
            + h3("Table headers bar")
            + "The table headers bar includes components that allow you to sort and filter the visible rows. "
            + "The " + ic("fa fa-exchange fa-rotate-90") + " button to the right of each column name allows you to sort the visible rows. "
            + "When the button is clicked, the rows are sorted ascending by the value of that column. "
            + "By clicking on the button again, the rows are sorted descending. "
            + "To sort by more than one column, press the SHIFT key when clicking on the additional columns button."
            + BR + BR
            + "The component that is below the name of each column allows you to further filter the result "
            + "obtained by querying the database with the criteria specified in the search criteria dialog; "
            + "this way the user can hide some rows without needing to do a new query (to the database). "
            + "The bar shows a text box for the columns that correspond to references to other entities, "
            + "alphanumeric, numeric, and temporal fields, that is, dates, times, and " + i("timestamps") + " (fields that have both date and time). "
            + "The bar displays a drop-down list for the columns that correspond to logical fields. "
            + h4("References to other entities and alphanumeric fields")
            + "The default comparison operator for references to other entities and alphanumeric fields is " + b("Starts with") + ". "
            + "It is possible to filter using other comparison operators by using special characters:"
            + ul(
                iux + star + " to see the rows that in the column have a value starting with " + iux + "; "
                + "this is the default operator for references to other entities and alphanumeric fields, and therefore "
                + "just write " + iux + ", without the asterisk.",
                star + iux + " to see the rows that in the column have a value ending in " + iux,
                star + iux + star + " to see the rows that in the column have a value that contains " + iux,
                not + iux + star + " to see the rows that in the column have a value that does not start with " + iux,
                not + star + iux + " to see the rows that in the column have a value that does not end in " + iux,
                not + star + iux + star + " to see the rows that in the column have a value that does not contain " + iux,
                b("=") + iux + " to see the rows that in the column have a value equal to " + iux,
                b(FWGTS) + iux + " to see the rows that in the column have a value greater than " + iux,
                b(">=") + iux + " to see the rows that in the column have a value greater than or equal to " + iux,
                b(FWLTS) + iux + " to see the rows that in the column have a value less than " + iux,
                b("<=") + iux + " to see the rows that in the column have a value less than or equal to " + iux,
                not + iux + " to see the rows that in the column have a value not equal to " + iux,
                not + b("=") + iux + " to see the rows that in the column have a value not equal to " + iux,
                b("<>") + iux + " to see the rows that in the column have a value not equal to " + iux
            )
            + "The result will be the same whether the string " + iux + " is written in upper or lower case. "
            + BR + BR
            + "If " + iux + " contains asterisks or exclamation marks, they must be written between forward slashes "
            + "to prevent them from being used to determine the comparison operator; "
            + "for example, to display rows with an alphanumeric field beginning with an asterisk, "
            + "in the corresponding text box write " + b("/*/*") + " instead of " + b("**") + ". "
            + h4("Numerical and temporal fields")
            + "The default comparison operator for numeric and temporal fields is " + b("Is equal to") + ". "
            + "It is possible to filter using other comparison operators by using special characters:"
            + ul(
                b("=") + iux + " to see the rows that in the column have a value equal to " + iux + "; "
                + "this is the default operator for numeric and temporal fields, and therefore "
                + "just write " + iux + ", without the equals sign.",
                b(FWGTS) + iux + " to see the rows that in the column have a value greater than " + iux,
                b(">=") + iux + " to see the rows that in the column have a value greater than or equal to " + iux,
                b(FWLTS) + iux + " to see the rows that in the column have a value less than " + iux,
                b("<=") + iux + " to see the rows that in the column have a value less than or equal to " + iux,
                not + iux + " to see the rows that in the column have a value not equal to " + iux,
                not + b("=") + iux + " to see the rows that in the column have a value not equal to " + iux,
                b("<>") + iux + " to see the rows that in the column have a value not equal to " + iux
            )
            + "In the case of temporal fields the value of " + iux + " does not necessarily have to be a date, a time or a " + i("timestamp") + "; "
            + "it can also be a " + b("temporal add or subtrahend") + ", that is, "
            + "a time interval to add or subtract from the current date or timestamp. "
            + "Intervals are represented by a positive integer (without sign or preceded by a plus sign) "
            + "or negative integer (preceded by the minus sign), followed by a letter that identifies the unit of time of the interval. Valid letters are: "
            + b("D") + " (days), " + b("M") + " (months), " + b("Y") + " (years), "
            + b("h") + " (hours), " + b("m") + " (minutes), and " + b("s") + " (seconds). "
            + "Intervals defined with capital letters (D, M, A) are added to or subtracted (depending on the sign of the number) from the current date. "
            + "Intervals defined with lowercase letters (h, m, s) are added or subtracted (depending on the sign of the number) to the current timestamp. "
            + "For example, to see rows with a date in the last 3 months, the value of " + iux + " would be " + b(">-3M") + "."
            + h4("Logical fields")
            + "The comparison operator for logical fields is always " + b("Is equal to") + ". "
            + "The drop-down list allows you to select " + b("Yes") + " or " + b("No") + ", "
            + "equivalent to " + b("Is true") + " or " + b("Is false") + ", respectively. "
            + "";
        /**/
        return str;
    }

}
