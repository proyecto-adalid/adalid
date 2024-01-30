#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.beta;

import adalid.util.*;

/**
 * MetaDataUml genera los diagramas UML de actividad, clase y estado de su aplicación utilizando la plataforma meta-data-uml.
 * <p>
 * Los diagramas de actividad se generan para operaciones de insert, update y de negocio; los diagramas de clase se generan para módulos y entidades;
 * los diagramas de estado se generan para entidades que tengan definida una máquina de estados. Para cada diagrama se genera un archivo gráfico
 * (.png) y uno de texto (.txt) que contiene las instrucciones para la generación del diagrama utilizando PlantUML. Los archivos se almacenan en el
 * subdirectorio /source/development/resources/documents/uml del proyecto generado.
 * <p>
 * La plataforma meta-data-uml aún se encuentra en versión beta. Una beta representa la primera versión completa de un programa informático, que es
 * posible que sea inestable pero útil para que sea considerada como una versión preliminar. Esta etapa comienza cuando los desarrolladores anuncian
 * la congelación de las características del producto, indicando que no serán agregadas más características a esta versión y que solamente se harán
 * pequeñas ediciones y se corregirán errores. Las versiones beta están en un paso intermedio en el ciclo de desarrollo. Los desarrolladores las
 * entregan a un grupo de probadores de betas o al público en general, para una prueba de usuario. Los probadores informan cualquier error que
 * encuentran y características que quisieran ver en la versión final. Cuando una versión beta llega a estar disponible para el público en general, a
 * menudo es extensamente probada por personas tecnológicamente expertas y/o familiarizadas con versiones anteriores, como si el producto estuviera
 * acabado. Generalmente los desarrolladores de las versiones betas de software gratuito o de código abierto las entregan al público en general,
 * mientras que las versiones beta de software propietario van a un grupo relativamente pequeño de probadores.
 *
 * @author ADALID meta-jee2-archetype
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
