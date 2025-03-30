/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package adalid.core.jee;

/**
 * @author Jorge Campins
 */
public interface JavaWebProject {

    /**
     * @return the multi-application project indicator
     */
    boolean isMultiApplication();

    /**
     * @return the multi-web-application project indicator
     */
    boolean isRoleWebAppDissociationAllowed();

    /**
     * @return the ear project name
     */
    String getEarProjectName();

    /**
     * @return the ejb project name
     */
    String getEjbProjectName();

    /**
     * @return the web project name
     */
    String getWebProjectName();

    /**
     * @return the web-api project name
     */
    String getWebApiProjectName();

    /**
     * @return the web project pages directory
     */
    default String getWebPageFileDirectory() {
        return "faces/views/base/crop";
    }

    /**
     * @return the web project pages extension
     */
    default String getWebPageFileExtension() {
        return "xhtml";
    }

    /**
     * @return the web project request servlet path
     */
    default String getWebRequestServletPath() {
        return "faces";
    }

    /**
     * @return the project filter snippet path
     */
    default String getProjectFilterSnippetPath() {
        return null;
    }

}
