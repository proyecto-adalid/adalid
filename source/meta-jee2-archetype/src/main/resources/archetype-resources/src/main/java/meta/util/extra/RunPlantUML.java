#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.extra;

import adalid.util.RunnableClass;
import adalid.util.Utility;

/**
 * RunPlantUML genera archivos de imagen a partir de archivos de texto utilizando PlantUML.
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
@RunnableClass
public class RunPlantUML extends Utility {

    /**
     * DIR es la ruta del directorio a partir del cual se comienza a buscar el archivo .txt o el directorio que se procesará. Si se selecciona un
     * archivo, solo se procesará ese archivo. Si se selecciona un directorio, se procesarán todos los archivos .txt válidos dentro de ese directorio
     * y de sus subdirectorios, de manera recursiva. Los archivos no válidos serán ignorados. DIR puede ser una ruta absoluta o relativa al directorio
     * del proyecto actual. Si DIR es nulo, se comienza a buscar a partir del directorio que contiene al proyecto actual.
     */
    private static final String DIR = null;

    public static void main(String[] args) {
        adalid.util.uml.RunPlantUML.run(DIR);
    }

}
