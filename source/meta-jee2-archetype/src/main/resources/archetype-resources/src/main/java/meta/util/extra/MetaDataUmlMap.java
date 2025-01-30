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
 * MetaDataUmlMap genera los mapas mentales de su aplicación utilizando la plataforma meta-data-uml-map.
 * <p>
 * Para el proyecto y para cada módulo del proyecto se genera un archivo gráfico (.svg) y uno de texto (.txt) que contiene las instrucciones para la
 * generación del mapa utilizando PlantUML. Los archivos se almacenan en el subdirectorio /source/development/resources/documents/uml/map del proyecto
 * generado.
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
@RunnableClass
public class MetaDataUmlMap extends adalid.util.Utility {

    /**
     * MASTER_CLASS almacena la clase del proyecto maestro ejecutado para generar su aplicación.
     * <p>
     * El campo PROJECT_CLASS contiene inicialmente la clase del proyecto Maestro101; posteriormente la clase del último proyecto maestro ejecutado.
     */
    private static final Class<? extends adalid.commons.ProjectBuilder> MASTER_CLASS = ${package}.meta.util.Aid.PROJECT_CLASS;

    /**
     * PLATFORM almacena el nombre de la plataforma que genera los mapas mentales de su aplicación.
     */
    private static final String PLATFORM = adalid.util.Platform.META_DATA_UML_MAP;

    public static void main(String[] args) throws Exception {
        newInstance(MASTER_CLASS).build(PLATFORM);
    }

}
