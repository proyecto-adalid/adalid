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
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.Property;
import adalid.core.jee.JavaWebProject;
import adalid.core.properties.StringProperty;
import java.util.List;
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
                case READING ->
                    path = entity.getApplicationReadingPath();
                case WRITING ->
                    path = entity.getApplicationWritingPath();
                case PROCESSING ->
                    path = entity.getApplicationConsolePath();
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

    public String getMainFormEncodingType() {
        DisplayMode mode = getDisplayMode();
        DisplayFormat format = getDisplayFormat();
        if (DisplayMode.PROCESSING.equals(mode) && DisplayFormat.CONSOLE.equals(format)) {
            Entity entity = getEntity();
            if (entity != null) {
                List<Operation> operations = entity.getBusinessOperationsList();
                if (operations != null) {
                    for (Operation operation : operations) {
                        if (OperationType.PROCESS.equals(operation.getOperationType())) {
                            OperationAccess access = operation.getOperationAccess();
                            if (OperationAccess.PUBLIC.equals(access) || OperationAccess.PROTECTED.equals(access) || OperationAccess.RESTRICTED.equals(access)) {
                                List<Parameter> parameters = operation.getParametersList();
                                for (Parameter parameter : parameters) {
                                    if (!parameter.isHiddenField() && parameter.isFileReferenceField()) {
                                        return "multipart/form-data";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (DisplayMode.WRITING.equals(mode) && DisplayFormat.DETAIL.equals(format)) {
            Property property;
            for (PageField field : getFields()) {
                property = field.getProperty();
                if (property instanceof StringProperty sp) {
                    if (!sp.isHiddenField() && sp.isFileReferenceField() && (sp.isCreateField() || sp.isUpdateField())) {
                        return "multipart/form-data";
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return the fields list
     */
    @Override
    public List<PageField> getFields() {
        return getFields(false);
    }

    /**
     * @param hidden whether hidden fields should be included in the list or not
     * @return the fields list
     */
    @Override
    public List<PageField> getFields(boolean hidden) {
        return null;
    }

    /**
     * @return the master heading fields list
     */
    @Override
    public List<PageField> getMasterHeadingFields() {
        return getMasterHeadingFields(false);
    }

    /**
     * @param hidden whether hidden fields should be included in the list or not
     * @return the master heading fields list
     */
    @Override
    public List<PageField> getMasterHeadingFields(boolean hidden) {
        return null;
    }

}
