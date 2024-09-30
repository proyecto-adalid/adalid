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
package adalid.jee2.help;

import adalid.core.Project;
import adalid.core.jee.JavaWebProject;

import static adalid.commons.util.MarkupUtils.*;

/**
 * @author Jorge Campins
 */
public abstract class AbstractHelpWriter implements JavaWebProject {

    protected static final String directory = "/faces/resources/help/base/";

    protected static final String iux = b(i("x")), not = b("!"), star = b("*");

    private final Project currentProject;

    private final JavaWebProject javaWebProject;

    public AbstractHelpWriter(Project project) {
        currentProject = project;
        javaWebProject = project instanceof JavaWebProject ? (JavaWebProject) project : null;
    }

    public abstract String getHelpPageText();

    protected String getAlias() {
        return currentProject.getAlias();
    }

    // <editor-fold defaultstate="collapsed" desc="JavaWebProject">
    @Override
    public boolean isMultiApplication() {
        return javaWebProject != null && javaWebProject.isMultiApplication();
    }

    @Override
    public boolean isRoleWebAppDissociationAllowed() {
        return javaWebProject != null && javaWebProject.isRoleWebAppDissociationAllowed();
    }

    @Override
    public String getEarProjectName() {
        return javaWebProject == null ? "?" : javaWebProject.getEarProjectName();
    }

    @Override
    public String getEjbProjectName() {
        return javaWebProject == null ? "?" : javaWebProject.getEjbProjectName();
    }

    @Override
    public String getWebProjectName() {
        return javaWebProject == null ? "?" : javaWebProject.getWebProjectName();
    }

    @Override
    public String getWebApiProjectName() {
        return javaWebProject == null ? "?" : javaWebProject.getWebApiProjectName();
    }

    @Override
    public String getWebPageFileDirectory() {
        return javaWebProject == null ? "?" : javaWebProject.getWebPageFileDirectory();
    }

    @Override
    public String getWebPageFileExtension() {
        return javaWebProject == null ? "?" : javaWebProject.getWebPageFileExtension();
    }

    @Override
    public String getWebRequestServletPath() {
        return javaWebProject == null ? "?" : javaWebProject.getWebRequestServletPath();
    }
    // </editor-fold>

}
