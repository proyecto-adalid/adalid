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

import adalid.util.*;

/**
 * MetaDataUml genera los diagramas UML de actividad, clase, estado y mapas mentales de su aplicación utilizando la plataforma meta-data-uml.
 * <p>
 * Los diagramas de actividad se generan para operaciones de insert, update y de negocio; los diagramas de clase se generan para módulos y entidades;
 * los diagramas de estado se generan para entidades que tengan definida una máquina de estados; los mapas mentales se generan para el proyecto y para
 * cada módulo. Para cada diagrama se genera un archivo gráfico (.png) y uno de texto (.txt) que contiene las instrucciones para la generación del
 * diagrama utilizando PlantUML. Los archivos se almacenan en el subdirectorio /source/development/resources/documents/uml del proyecto generado.
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
@RunnableClass
public class MetaDataUml extends adalid.util.Utility {

    /**
     * MASTER_CLASS almacena la clase del proyecto maestro ejecutado para generar su aplicación.
     * <p>
     * El campo PROJECT_CLASS contiene inicialmente la clase del proyecto Maestro101; posteriormente la clase del último proyecto maestro ejecutado.
     */
    private static final Class<? extends adalid.commons.ProjectBuilder> MASTER_CLASS = ${package}.meta.util.Aid.PROJECT_CLASS;

    /**
     * PLATFORM almacena el nombre de la plataforma que genera los diagramas UML de actividad, clase y estado de su aplicación.
     */
    private static final String PLATFORM = adalid.util.Platform.META_DATA_UML;

    public static void main(String[] args) throws Exception {
        newInstance(MASTER_CLASS).build(PLATFORM);
    }

}
