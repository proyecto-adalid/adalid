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
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
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
    private static final String PLATFORM = adalid.util.Platform.WEBAPP_HELP;

    public static void main(String[] args) throws Exception {
        newInstance(MASTER_CLASS).build(PLATFORM);
    }

}
