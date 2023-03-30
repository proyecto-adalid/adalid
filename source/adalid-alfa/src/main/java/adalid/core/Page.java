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
package adalid.core;

import adalid.commons.util.StrUtils;
import adalid.core.enums.*;
import adalid.core.interfaces.Entity;
import adalid.core.jee.JavaWebProject;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class Page extends Display {

    public static final String HREF = "href";

    public Page(String name) {
        super(name);
        init();
    }

    private void init() {
        setDisplayType(DisplayType.PAGE);
    }

    public String getLocation() {
        Project project = TLC.getProject();
        JavaWebProject jwp = jwp(project);
        String name = getName();
        String href = project.getStringKeyValuePairAttribute(Page.class, name, HREF);
        if (StringUtils.isNotBlank(href)) {
            return href;
        }
        if (jwp != null) {
            Project module = getModule();
            Entity entity = getEntity();
            DisplayMode mode = getDisplayMode();
            if (module != null && entity != null && mode != null) {
                String host = StringUtils.trimToEmpty(entity.getApplicationOrigin());
                String root = StringUtils.defaultIfBlank(entity.getApplicationContextRoot(), jwp.getWebProjectName());
                String fdir = jwp.getWebPageFileDirectory();
                String fext = jwp.getWebPageFileExtension();
                String path = pagePath(module, entity, mode);
                return host + "/" + root + "/" + fdir + "/" + path + "/" + name + "." + fext;
            }
        }
        return null;
    }

    public String getLocationWithinApplicationContextRoot() {
        Project project = TLC.getProject();
        JavaWebProject jwp = jwp(project);
        String name = getName();
        String href = project.getStringKeyValuePairAttribute(Page.class, name, HREF);
        if (StringUtils.isNotBlank(href)) {
            String wrsp = "/" + jwp.getWebRequestServletPath() + "/";
            return href.contains(wrsp) ? wrsp + StringUtils.substringAfter(href, wrsp) : href;
        }
        if (jwp != null) {
            Project module = getModule();
            Entity entity = getEntity();
            DisplayMode mode = getDisplayMode();
            if (module != null && entity != null && mode != null) {
                String fdir = jwp.getWebPageFileDirectory();
                String fext = jwp.getWebPageFileExtension();
                String path = pagePath(module, entity, mode);
                return "/" + fdir + "/" + path + "/" + name + "." + fext;
            }
        }
        return null;
    }

    private JavaWebProject jwp(Project project) {
        return project instanceof JavaWebProject ? (JavaWebProject) project : null;
    }

    private String pagePath(Project module, Entity entity, DisplayMode mode) {
        if (mode != null) {
            String path = null;
            switch (mode) {
                case READING:
                    path = entity.getApplicationReadingPath();
                    break;
                case WRITING:
                    path = entity.getApplicationWritingPath();
                    break;
                case PROCESSING:
                    path = entity.getApplicationConsolePath();
                    break;
            }
            if (StringUtils.isNotBlank(path)) {
                return path;
            }
        }
        return pagePath(module);
    }

    private String pagePath(Project module) {
        String path = StrUtils.getLowerCaseIdentifier(StringUtils.defaultIfBlank(module.getAlias(), module.getName()), "/");
        return path;
    }

    /**
     * @return the fields list
     */
    @Override
    public List<PageField> getFields() {
        return null;
    }

    /**
     * @return the master heading fields list
     */
    @Override
    public List<PageField> getMasterHeadingFields() {
        return null;
    }

    /**
     * @return the list of entities referenced by fields
     */
    @Override
    public Set<Entity> getEntitiesReferencedByFields() {
        return null;
    }

}
