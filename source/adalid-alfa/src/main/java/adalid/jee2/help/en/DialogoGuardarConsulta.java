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
public class DialogoGuardarConsulta extends AbstractHelpWriter {

    public DialogoGuardarConsulta(Project project) {
        super(project);
    }

    @Override
    public String getHelpPageText() {
        String str = ""
            + "This dialog allows you to save the results of your queries in several formats, using views previously defined by you. "
            + "Each of the dialog fields is described below:"
            + ul(
                b("view") + ": drop-down list that allows you to select a view previously defined by you. "
                + "Following the list are these buttons:"
                + ul(
                    ic("fa fa-plus") + " to define a new view.",
                    ic("fa fa-copy") + " to duplicate the selected view.",
                    ic("fa fa-table") + " to open the view registration page.",
                    ic("fa fa-th") + " to view and edit the criteria for the selected view."
                ),
                b("save as") + ": drop-down list that allows you to select the way you want to save the query result. "
                + "Select " + b("Report") + " or " + b("File") + " to save the query result as a report or data file, respectively.",
                b("format") + ": drop-down list that allows you to select the report or file format in which you want to save the query result. "
                + "Reports can be saved in the following formats: PDF, RTF, ODT, ODS, HTML, DOCX, PPTX, and XLSX; "
                + "select PDF (Portable Document Format) "
                + "to generate files that can be opened with Adobe Acrobat or another similar application; "
                + "select RTF (Rich Text Format) "
                + "to generate standard text files containing \"rich text\"; "
                + "select ODT to generate files that can be opened with OpenOffice Writer or another similar program; "
                + "select ODS to generate files that can be opened with OpenOffice Calc or another similar program; "
                + "select HTML to generate text files written in Hypertext Markup Language (HTML), typically web pages; "
                + "select DOCX to generate files that can be opened with Microsoft Word or another similar program; "
                + "select PPTX to generate files that can be opened with Microsoft PowerPoint or another similar program; "
                + "select XLSX to generate files that can be opened with Microsoft Excel or another similar program. "
                + "Files can be saved in the following formats: CSV and TSV; "
                + "select CSV to generate comma or semicolon separated values files, depending on locale; "
                + "select TSV to generate tab-separated value files.",
                b("type") + ": drop-down list that allows you to select the type of report or file in which you want to save the query result. "
                + "Select " + b("Detail") + " or " + b("Summary") + ", to save the query result as a detailed or summary report or file, respectively. "
                + "If the result is saved as a report, you can additionally specify the type " + b("Chart")
                + ", and specify the value of the chart type and subtype in the following drop-down lists.",
                b("chart type") + ": drop-down list that allows you to select the type of chart in which you want to save the query result. "
                + "Reports can be saved in the following chart types: "
                + b("Bar") + ", " + b("Stacked Bar") + ", " + b("Area") + ", " + b("Stacked Area") + ", " + b(" Line") + " y " + b("Pie") + ".",
                b("chart subtype") + ": drop-down list that allows you to select the chart subtype in which you want to save the query result. "
                + "Except for pie charts, reports can be saved to the following chart subtypes: "
                + b("Series by Aggregation") + " y " + b("Series by Group"),
                b("limit") + ": maximum number of records included in reports or files; this limit is usually much larger than the page limit, "
                + "since the used memory is freed automatically at the end of the generation. Usually this limit is 10,000 records. "
                + "This limit can be different for each user. If desired, users can specify a lower limit than the one assigned to them. "
                + "This way they can prevent the generation of a file or report larger than expected. "
                + "The specified limit must be in the range that appears to the right of the text box. The default limit is 1,000 records."
            )
            + "At the bottom of the dialog are the buttons that correspond to the other available actions. These are: "
            + ul(
                b("Save") + ": save the query result with the specified format, type, and view.",
                b("Show file") + ": open the page to view the report or file generated by saving the query result. "
                + "This button is only enabled for PDF, HTML, CSV and TSV formats.",
                b("Report Log") + ": open the page " + b("Reports Audit Trails") + ".",
                b("Hide") + ": hide the dialog.",
                b("Help") + ": display this help."
            )
            + "";
        /**/
        return str;
    }

}
