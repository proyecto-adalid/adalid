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
 * WebAppHelp genera los archivos de ayuda en-línea de su aplicación utilizando la plataforma webapp-help.
 * <p>
 * Para cada entidad del proyecto se genera un archivo de ayuda; adicionalmente se genera un archivo de ayuda para el proyecto. Los archivos se
 * almacenan en el subdirectorio src/main/webapp/resources/help/custom-made del módulo Web de la aplicación generada.
 * <p>
 * Para que las páginas de la aplicación generada utilicen los archivos de ayuda generados, es necesario incluir los elementos helpFile,
 * helpFileAutoName y helpFileAutoType en la anotación {@code @MasterProject} del proyecto maestro, de la siguiente manera:
 * <p>
 * helpFile = "<i>alias</i>.xhtml", helpFileAutoName = HelpFileAutoName.ENTITY, helpFileAutoType = "xhtml"
 * <p>
 * donde <i>alias</i> es el valor del elemento alias de {@code @MasterProject}; por ejemplo:
 * <p>
 * {@code @MasterProject(alias = "${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101", helpFile = "${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101.xhtml", helpFileAutoName = HelpFileAutoName.ENTITY, helpFileAutoType = "xhtml")}
 * <p>
 * La plataforma webapp-help aún se encuentra en versión beta. Una beta representa la primera versión completa de un programa informático, que es
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
public class WebAppHelp extends adalid.util.Utility {

    /**
     * MASTER_CLASS almacena la clase del proyecto maestro ejecutado para generar su aplicación.
     * <p>
     * El campo PROJECT_CLASS contiene inicialmente la clase del proyecto Maestro101; posteriormente la clase del último proyecto maestro ejecutado.
     */
    private static final Class<? extends adalid.commons.ProjectBuilder> MASTER_CLASS = ${package}.meta.util.Aid.PROJECT_CLASS;

    /**
     * PLATFORM almacena el nombre de la plataforma que genera los archivos de ayuda en-línea de su aplicación.
     */
    private static final String PLATFORM = "webapp-help";

    public static void main(String[] args) throws Exception {
        MASTER_CLASS.getDeclaredConstructor().newInstance().build(PLATFORM);
    }

}
