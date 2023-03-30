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
package adalid.jee2.help.es;

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
            + "Este diálogo le permite guardar los resultados de sus consultas en varios formatos, utilizando vistas definidas por usted. "
            + "A continuación se describe cada uno de los campos del diálogo:"
            + ul(
                b("vista") + ": lista desplegable que permite seleccionar una vista previamente definida por usted. "
                + "A continuación de la lista se encuentran los siguientes botones: "
                + ul(
                    ic("fa fa-plus") + " para definir una nueva vista.",
                    ic("fa fa-copy") + " para duplicar la vista seleccionada.",
                    ic("fa fa-table") + " para abrir la página de registro de vistas.",
                    ic("fa fa-th") + " para ver y editar las columnas de la vista seleccionada."
                ),
                b("guardar como") + ": lista desplegable que permite seleccionar la forma en la que desea guardar el resultado de la consulta. "
                + "Seleccione " + b("Informe") + " o " + b("Archivo") + " para guardar el resultado de la consulta como un informe o un archivo de datos, respectivamente.",
                b("formato") + ": lista desplegable que permite seleccionar el formato de informe o archivo en el que desea guardar el resultado de la consulta. "
                + "Los informes se pueden guardar en los siguientes formatos: PDF, RTF, ODT, ODS, HTML, DOCX, PPTX y XLSX; "
                + "seleccione PDF (siglas en inglés de Portable Document Format, «formato de documento portátil») "
                + "para generar archivos que puedan ser abiertos con Adobe Acrobat u otra aplicación similar; "
                + "seleccione RTF (siglas en inglés de Rich Text Format, «formato de texto enriquecido») "
                + "para generar archivos de texto estándar que contengan \"texto enriquecido\"; "
                + "seleccione ODT para generar archivos que puedan ser abiertos con OpenOffice Writer u otro programa similar; "
                + "seleccione ODS para generar archivos que puedan ser abiertos con OpenOffice Calc u otro programa similar; "
                + "seleccione HTML para generar archivos de texto escritos con lenguaje de marcado de hipertexto (HTML), típicamente páginas web; "
                + "seleccione DOCX para generar archivos que puedan ser abiertos con Microsoft Word u otro programa similar; "
                + "seleccione PPTX para generar archivos que puedan ser abiertos con Microsoft PowerPoint u otro programa similar; "
                + "seleccione XLSX para generar archivos que puedan ser abiertos con Microsoft Excel u otro programa similar. "
                + "Los archivos se pueden guardar en los siguientes formatos: CSV y TSV; "
                + "seleccione CSV para generar archivos de valores separados por coma o punto y coma, dependiendo de la configuración regional; "
                + "seleccione TSV para generar archivos de valores separados por tabulaciones.",
                b("tipo") + ": lista desplegable que permite seleccionar el tipo de informe o archivo en el que desea guardar el resultado de la consulta. "
                + "Seleccione " + b("Detalle") + " o " + b("Resumen") + ", para guardar el resultado de la consulta como un informe o archivo detallado o resumido, respectivamente. "
                + "Si el resultado se guarda como informe, adicionalmente puede especificar el tipo " + b("Gráfico")
                + ", y especificar el valor del tipo y subtipo de gráfico en las siguientes listas desplegables.",
                b("tipo de gráfico") + ": lista desplegable que permite seleccionar el tipo de gráfico en el que desea guardar el resultado de la consulta. "
                + "Los informes gráficos se pueden guardar en los siguientes tipos de gráfico: "
                + b("Barra") + ", " + b("Barra Apilada") + ", " + b("Área") + ", " + b("Área Apilada") + ", " + b("Línea") + " y " + b("Torta") + ".",
                b("subtipo de gráfico") + ": lista desplegable que permite seleccionar el subtipo de gráfico en el que desea guardar el resultado de la consulta. "
                + "Salvo los gráficos de torta, los informes gráficos se pueden guardar en los siguientes subtipos de gráfico: "
                + b("Series por Agregación") + " y " + b("Series por Grupo"),
                b("límite") + ": número máximo de registros incluidos en los informes o archivos; este límite suele ser mucho mayor que el límite de la página, "
                + "ya que la memoria utilizada es liberada automáticamente al terminar la generación. Por lo general este límite es de 10.000 registros. "
                + "Este límite puede ser diferente para cada usuario. Si lo desea, el usuario puede especificar un límite menor al que tiene asignado. "
                + "De esta manera puede prevenir la generación de un archivo o informe de mayor tamaño que el esperado. "
                + "El límite especificado debe estar en el rango que aparece a la derecha del cuadro de texto. El límite predefinido es 1.000 registros."
            )
            + "En la parte inferior del diálogo se encuentran los botones que corresponden a las demás acciones disponibles. Estas son: "
            + ul(
                b("Guardar") + ": guardar el resultado de la consulta con el formato, tipo y vista especificados.",
                b("Ver el archivo") + ": abrir la página para visualizar el informe o archivo generado al guardar el resultado de la consulta. "
                + "Este botón solo está habilitado para formatos PDF, HTML, CSV y TSV.",
                b("Bitácora de Informes") + ": abrir la página " + b("Bitácora de Informes") + ".",
                b("Ocultar") + ": ocultar el diálogo.",
                b("Ayuda") + ": mostrar esta ayuda."
            )
            + "";
        /**/
        return str;
    }

}
