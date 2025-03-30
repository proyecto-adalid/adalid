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
 * MetaDataUmlClass genera los diagramas de clase UML de su aplicación utilizando la plataforma meta-data-uml-class.
 * <p>
 * Para cada módulo y cada entidad del proyecto se genera un archivo gráfico (.png) y uno de texto (.txt) que contiene las instrucciones para la
 * generación del diagrama utilizando PlantUML. Los archivos se almacenan en el subdirectorio /source/development/resources/documents/uml/clazz del
 * proyecto generado.
 *
 * @author ADALID meta-jee2-archetype, version 6.1.0
 */
@RunnableClass
public class MetaDataUmlClass extends adalid.util.Utility {

    /**
     * MASTER_CLASS almacena la clase del proyecto maestro ejecutado para generar su aplicación.
     * <p>
     * El campo PROJECT_CLASS contiene inicialmente la clase del proyecto Maestro101; posteriormente la clase del último proyecto maestro ejecutado.
     */
    private static final Class<? extends adalid.commons.ProjectBuilder> MASTER_CLASS = ${package}.meta.util.Aid.PROJECT_CLASS;

    /**
     * PLATFORM almacena el nombre de la plataforma que genera los diagramas de clase UML de su aplicación.
     */
    private static final String PLATFORM = adalid.util.Platform.META_DATA_UML_CLASS;

    public static void main(String[] args) throws Exception {
        newInstance(MASTER_CLASS).build(PLATFORM);
    }

}
