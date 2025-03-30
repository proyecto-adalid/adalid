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

import adalid.core.interfaces.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
public class TLC { // Thread Local Context

    private static final ThreadLocal<AllocationSettings> _allocationSettings = new ThreadLocal<>();

    private static final ThreadLocal<Artifact> _declaringArtifact = new ThreadLocal<>();

    private static final ThreadLocal<Field> _declaringField = new ThreadLocal<>();

    private static final ThreadLocal<Project> _project = new ThreadLocal<>();

    static {
        init();
    }

    static void init() {
    }

    static void destroy() {
        removeAllocationSettings();
        removeDeclaringArtifact();
        removeProject();
    }

    static AllocationSettings getAllocationSettings() {
        return _allocationSettings.get();
    }

    static void setAllocationSettings(AllocationSettings settings) {
        if (settings == null) {
            removeAllocationSettings();
        } else {
            _allocationSettings.set(settings);
        }
    }

    static void removeAllocationSettings() {
        _allocationSettings.remove();
    }

    static Artifact getDeclaringArtifact() {
        return _declaringArtifact.get();
    }

    static void setDeclaringArtifact(Artifact artifact) {
        if (artifact == null) {
            removeDeclaringArtifact();
        } else {
            _declaringArtifact.set(artifact);
        }
    }

    static void removeDeclaringArtifact() {
        _declaringArtifact.remove();
    }

    static Field getDeclaringField() {
        return _declaringField.get();
    }

    static void setDeclaringField(Field field) {
        if (field == null) {
            removeDeclaringField();
        } else {
            _declaringField.set(field);
        }
    }

    static void removeDeclaringField() {
        _declaringField.remove();
    }

    public static Project getProject() {
        return _project.get();
    }

    static void setProject(Project project) {
        if (project == null) {
            removeProject();
        } else {
            _project.set(project);
        }
    }

    static void removeProject() {
        _project.remove();
    }

}
