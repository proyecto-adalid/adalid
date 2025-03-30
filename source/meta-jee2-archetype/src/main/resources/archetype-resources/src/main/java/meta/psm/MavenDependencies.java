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
 * @author ADALID meta-jee2-archetype, version 6.1.0
 */
public class MavenDependencies {

    /*
    public static Apache Apache;

    /**/
    public static PrimeFaces PrimeFaces;

    static {
        new MavenDependencies();
    }

    private MavenDependencies() {
        /*
        Apache = new Apache();
        /**/
        PrimeFaces = new PrimeFaces();
    }

    /* Apache HttpClient fue reemplazado por el HttpClient integrado de Java en la versión 6.1 de Adalid/JEE2
    public class Apache {

        public ProjectDependency httpclient() {
            return ProjectDependency.of("org.apache.httpcomponents", "httpclient", "4.5.13").
                setScope(ProjectDependencyScope.PROVIDED);
        }

    }

    /**/
    public class PrimeFaces {

        private static final String groupId = "org.primefaces.themes";

        public ProjectDependency freya() {
            return freya("7.0.0", "jakarta");
        }

        public ProjectDependency freya(String version, String classifier) {
            return ProjectDependency.of(groupId, "freya", version).
                setClassifier(classifier).
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency serenity() {
            return serenity("8.0.0", "jakarta");
        }

        public ProjectDependency serenity(String version, String classifier) {
            return ProjectDependency.of(groupId, "serenity", version).
                setClassifier(classifier).
                setScope(ProjectDependencyScope.RUNTIME);
        }

    }

}
