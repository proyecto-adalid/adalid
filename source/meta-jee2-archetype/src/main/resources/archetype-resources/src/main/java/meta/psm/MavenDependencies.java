#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.psm;

import adalid.jee2.*;

/**
 * @author ADALID meta-jee2-archetype
 */
public class MavenDependencies {

    public static PrimeFaces PrimeFaces;

    static {
        new MavenDependencies();
    }

    private MavenDependencies() {
        PrimeFaces = new PrimeFaces();
    }

    public class PrimeFaces {

        private static final String groupId = "org.primefaces.themes";

        public ProjectDependency freya() {
            return ProjectDependency.of(groupId, "freya", "4.0.0").
                setClassifier("jakarta").
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency mirage() {
            return ProjectDependency.of(groupId, "mirage", "4.0.0").
                setClassifier("jakarta").
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency omega() {
            return ProjectDependency.of(groupId, "omega", "5.0.0").
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency prestige() {
            return ProjectDependency.of(groupId, "prestige", "6.0.0").
                setClassifier("jakarta").
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency serenity() {
            return ProjectDependency.of(groupId, "serenity", "5.0.0").
                setClassifier("jakarta").
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency ultima() {
            return ProjectDependency.of(groupId, "ultima", "5.0.0").
                setClassifier("jakarta").
                setScope(ProjectDependencyScope.RUNTIME);
        }

    }

}
