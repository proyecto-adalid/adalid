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

    private MavenDependencies() {
        PrimeFaces = new PrimeFaces();
    }

    public class PrimeFaces {

        public static final String ORG_PRIMEFACES_THEMES = "org.primefaces.themes";

        public ProjectDependency serenity() {
            return ProjectDependency.of(ORG_PRIMEFACES_THEMES, "serenity", "[1.0.2,)").
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency spark() {
            return ProjectDependency.of(ORG_PRIMEFACES_THEMES, "spark", "[1.2,)").
                setScope(ProjectDependencyScope.RUNTIME);
        }

        public ProjectDependency ultima() {
            return ProjectDependency.of(ORG_PRIMEFACES_THEMES, "ultima", "[1.0.4,)").
                setScope(ProjectDependencyScope.RUNTIME);
        }

    }

}
