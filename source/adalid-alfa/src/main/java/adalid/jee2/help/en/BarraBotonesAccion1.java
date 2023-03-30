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
public class BarraBotonesAccion1 extends AbstractHelpWriter {

    public BarraBotonesAccion1(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String str = ""
            + h3("Pages for entity management")
            + "Each entity in the application can have multiple types of inquiry and registration pages. "
            + "These can be any combination of the modes, formats and layouts available. "
            + "The inquiry pages are pages that work in " + b("Read Only") + " mode; "
            + "the registration pages, in " + b("Read and Write") + " mode. "
            + "The latter, in addition to querying, also allow adding, editing and deleting. "
            + BR + BR
            + "There are two formats available, for both inquiry and registration pages. The available formats are: "
            + ul(
                b("Independent") + ": pages displaying information for all records.",
                b("Master/Detail") + ": pages that display information only for the records (details) related to another record (master) previously selected."
            )
            + "There are three layouts available, for both inquiry and registration pages. The available layouts are: "
            + ul(
                b("Tabular") + ": pages that display the information of several records at the same time, using a table. "
                + "Can be pages of format " + b("Independent") + " or " + b("Master/Detail") + ".",
                b("Tree") + ": pages that show the records in a hierarchical way. "
                + "These are pages of format " + b("Independent") + ".",
                b("Form") + ": pages that display the information of a single record at a time, using a columnar form. "
                + "They can be pages of format " + b("Independent") + " or " + b("Master/Detail") + ", with or without tabs, with or without wizard."
            )
            + "Additionally, each entity can also have a page for the execution of business and other kind of processes, "
            + "such as report generation, file upload and download, etc. "
            + "These pages are known as " + b("Processing Consoles") + "."
            + h3("Toolbar")
            + "The buttons that appear on the toolbar vary depending on the type of the current page; "
            + "those buttons that cannot be used at a given time will be disabled. "
            + "The following list includes all the buttons that can appear on the toolbar:"
            + ul(
                ic("fa fa-home") + " Open the home page of the application. "
                + "Each user can choose their home page, using the function " + b("Change settings") + " of the page " + b("Settings") + ". "
                + "Typically, if the user has pending task notifications, the default home page would be " + b("Tasks") + "; "
                + "otherwise it would be " + b("Start Menu") + ".",
                ic("fa fa-star") + " Open page " + b("Favorites") + "."
                + "On that page the list of hyperlinks of the user's favorite places is displayed; "
                + "that is, the pages of the application that the user has added to his list of favorite pages.",
                ic("fa fa-star-o") + " Add the current page to the list of favorite pages.",
                ic("fa fa-tasks") + " Open page " + b("Tasks") + ", "
                + "to query and perform operations on task notifications.",
                ic("xs xs-function") + " Open page " + b("Function Audit Trails") + ", "
                + "to query the audit trails of the execution of registry operations and business processes.",
                ic("fa fa-print") + " Open page " + b("Reports Audit Trails") + ", "
                + "to query the audit trails of file and report generation.",
                ic("fa fa-gears") + " Open page " + b("Process Audit Trails") + ", "
                + "to query the audit trails of business processes.",
                ic("fa fa-folder-open") + " " + b("Open") + " Show the list of related pages."
                + "In the list you can select a page to open it. It only appears on inquiry and registration pages.",
                ic("fa fa-gear") + " " + b("Execute") + " Display the list of business functions for the page."
                + "In the list you can select a function to execute it on the selected record(s). "
                + "Only appears on inquiry and registration pages. "
                + "The list includes only entity instance functions."
                + "Instance functions are those that operate on a single record and are typically "
                + "have as their first mandatory parameter the reference to the registry with which they operate. "
                + "The content of the list depends on the page layout. "
                + "On Tabular layout pages it includes only functions whose only mandatory parameter with no default value is "
                + "the reference to the record, so that it can be executed with each of the selected records (table rows), "
                + "using default values for the other function parameters (if any)."
                + "On Form layout pages, the list includes all entity instance functions; "
                + "those functions whose only mandatory parameter with no default value is the reference to the record "
                + "are executed using default values for the other function parameters (if any); "
                + "to execute the other functions, the Processing Console is opened. "
                + "Note that, in the list, the label of those functions ends with an ellipsis; "
                + "in addition, its tooltip (which appears when you point it with the mouse), indicates that the Processing Console will be opened to execute the function.",
                ic("fa fa-gear") + " " + b("Process") + " Run the selected process."
                + "Only appears in Processing Consoles.",
                ic("fa fa-filter") + " Display the dialog to specify the search criteria for the query. "
                + "Only appears on Tabular layout pages.",
                ic("fa fa-file-text") + " Display the dialog to save the query result. "
                + "Only appears on Tabular layout pages.",
                ic("fa fa-repeat") + " Requery, using the search criteria from the last query. "
                + "Only appears on inquiry and registration pages.",
                ic("fa fa-step-backward") + " Display the first record of the query result."
                + "Only appears on Form layout pages.",
                ic("fa fa-backward") + " Show the previous record."
                + "Only appears on Form layout pages.",
                ic("fa fa-forward") + " Display the next record."
                + "Only appears on Form layout pages.",
                ic("fa fa-step-forward") + " Display the last record of the query result."
                + "Only appears on Form layout pages.",
                ic("fa fa-plus") + " Add a new record."
                + "Only appears on registration pages.",
                ic("fa fa-plus-square") + " Add a new record."
                + "Only appears on registration pages of entities that are extended by at least one other entity.",
                ic("fa fa-plus-square-o") + " Open the page that allows you to select one or more records to add. "
                + "Only appears on registration pages that have the Quick Adding feature enabled.",
                ic("fa fa-plus-circle") + " Open the page that allows you to add a new record using a business transaction. "
                + "Only appears on registration pages of entities that have at least one business operation to add new records.",
                ic("fa fa-edit") + " Edit the selected record(s). Appears only on registration pages.",
                ic("fa fa-trash") + " Delete the selected record(s). Appears only on registration pages.",
                ic("fa fa-reply-all") + " Discard changes made to added or edited records. Appears only on registration pages.",
                ic("fa fa-check") + " Validate the changes made to the page fields.",
                ic("fa fa-check-square") + " Validate the changes made to the fields of the page and return to the page that contains the collection. "
                + "Only appears on Collection registration pages.",
                ic("fa fa-save") + " Commit (save to the database) the changes made to the added or edited records."
                + "Only appears on registration pages.",
                ic("fa fa-arrow-circle-up") + " Go to the top of the page.",
                ic("fa fa-arrow-circle-down") + " Go to the bottom of the page.",
                ic("fa fa-arrow-left") + " Return the selected record(s). Only appears in search dialogs.",
                ic("fa fa-close") + " Close dialog without returning any record. Appears only in dialogs.",
                ic("fa fa-question") + " Show help for current page.",
                ic("fa fa-youtube") + " Show tutorial for current page.",
                ic("fa fa-book") + " Open application's documentation page.",
                ic("fa fa-question-circle") + " Show this help.",
                ic("fa fa-sign-out") + " End the work session."
            )
            + "The button to open the page " + b("Tasks") + " "
            + "can be decorated with the number of tasks assigned to the user. "
            + "The buttons to open the pages " + b("Report Audit Trails") + " and " + b("Process Audit Trails") + " "
            + "may be decorated with the number of unread and undownloaded reports and processes the user has. "
            + BR + BR
            + "The bar also shows the " + b("Main Message Icon") + ". "
            + "After generating a file or report, this image shows the icon corresponding to the type of object generated; "
            + "in other cases, displays the severity of the message appearing on the " + b("Main Message Line") + ". "
            + "Clicking on the " + b("Main Message Icon") + " page content scrolls vertically to the bottom, "
            + "so that the " + b("Message Box") + " is visible. "
            + h3("Settings")
            + "The functions of the page " + b("Settings") + " allow you to customize the appearance and behavior of the application. "
            + "In the page header there is a button to open the page " + b("Settings") + ". "
            + "The original image of that button is " + img("user.jpe", 18, 18) + " and can be replaced in " + b("Settings") + " by executing "
            + "the functions " + b("Upload portrait") + " or " + b("Take portrait") + "."
            + h3("Add, Search, View Detail and Peek buttons")
            + "On the inquiry and registration pages, "
            + "entity references to other entities can be implemented via a dropdown list, "
            + "or by a text box to write the code of the referenced entity and the following action buttons: "
            + ul(
                ic("fa fa-plus") + " Start an insert dialog, opening the detailed registration page for the referenced entity. "
                + "You can return the code of the inserted instance to the referring page "
                + "by clicking the " + ic("fa fa-arrow-left") + " button on the action button bar. "
                + "This button is only available when adding or editing.",
                ic("fa fa-search") + " Start a search dialog, opening the inquiry or registration page of the referenced entity. "
                + "You can return the code of the selected instance to the referring page, "
                + "by clicking the " + ic("fa fa-arrow-left") + " button on the row toolbar, "
                + "in Tabular layout pages, "
                + "o from the page toolbar, in Form layout pages. "
                + "This button is only available when adding or editing.",
                ic("fa fa-th-list") + " Open the detailed inquiry page for the referenced entity.",
                ic("fa fa-eye") + " Show the peek box for the referenced entity."
            )
            + "";
        /**/
        return str;
    }

}
