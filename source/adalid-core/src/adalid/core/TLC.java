/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.core;

import adalid.core.interfaces.Artifact;

/**
 * @author Jorge Campins
 */
public class TLC { // Thread Local Context

    private static final ThreadLocal<AllocationSettings> _allocationSettings = new ThreadLocal<>();

    private static final ThreadLocal<Artifact> _declaringArtifact = new ThreadLocal<>();

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
