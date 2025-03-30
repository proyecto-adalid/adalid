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
package adalid.commons.properties;

import adalid.commons.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jorge Campins
 */
public class ProjectBuilderDictionary extends Dictionary {

    public static ProjectBuilderDictionary load() {
        return new ProjectBuilderDictionary();
    }

    ProjectBuilderDictionary() {
        super(ProjectBuilder.class, null, null, null, false);
    }

    private static final String lastExecutedProjectAlias = "projectAlias";

    public String getLastExecutedProjectAlias() {
        return getProperty(lastExecutedProjectAlias);
    }

    public void setLastExecutedProjectAlias(String value) {
        setProperty(lastExecutedProjectAlias, value);
    }

    private static final String lastExecutedProjectBaseFolderName = "projectBaseFolder";

    public String getLastExecutedProjectBaseFolderName() {
        return getProperty(lastExecutedProjectBaseFolderName);
    }

    public void setLastExecutedProjectBaseFolderName(String value) {
        setProperty(lastExecutedProjectBaseFolderName, value);
    }

    private static final String lastExecutedProjectClassName = "projectClass";

    public String getLastExecutedProjectClassName() {
        return getProperty(lastExecutedProjectClassName);
    }

    public void setLastExecutedProjectClassName(String value) {
        setProperty(lastExecutedProjectClassName, value);
    }

    private static final String lastExecutedUtilityClassName = "utilityClass";

    public String getLastExecutedUtilityClassName() {
        return getProperty(lastExecutedUtilityClassName);
    }

    public void setLastExecutedUtilityClassName(String value) {
        setProperty(lastExecutedUtilityClassName, value);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends ProjectBuilder> getLastExecutedProjectClass() {
        String name = getLastExecutedProjectClassName();
        if (name == null || name.isEmpty()) {
            return null;
        }
        try {
            return (Class<? extends ProjectBuilder>) Class.forName(name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public ProjectBuilder getLastExecutedProject() {
        Class<? extends ProjectBuilder> clazz = getLastExecutedProjectClass();
        if (clazz == null) {
            return null;
        }
        Constructor<? extends ProjectBuilder> constructor;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException | SecurityException ex) {
            return null;
        }
        try {
            return constructor.newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            return null;
        }
    }

}
