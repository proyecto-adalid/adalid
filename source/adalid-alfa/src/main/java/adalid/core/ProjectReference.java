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
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Jorge Campins
 */
public class ProjectReference {

    private Project _project;

    private final Class<?> _projectClass;

    private Artifact _declaringArtifact;

    private Field _declaringField;

    private final Map<String, Class<?>> _declaredTypes = new TreeMap<>();

    private final Map<String, Class<?>> _declaringTypes = new TreeMap<>();

    private final Project _outer;

    ProjectReference(Class<?> projectClass, final Project outer) {
        _outer = outer;
        _project = null;
        _projectClass = projectClass;
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return _project;
    }

    /**
     * @param project the project to set
     */
    void setProject(Project project) {
        _project = project;
    }

    /**
     * @return the project class
     */
    public Class<?> getProjectClass() {
        return _projectClass;
    }

    void putDeclaredType(Class<?> declaredType) {
        _declaredTypes.put(declaredType.getName(), declaredType);
    }

    public Map<String, Class<?>> getDeclaredTypes() {
        return _declaredTypes;
    }

    void putDeclaringType(Class<?> declaringType) {
        if (declaringType == null) {
        } else {
            _declaringTypes.put(declaringType.getName(), declaringType);
            if (Project.class.isAssignableFrom(declaringType)) {
                String key = declaringType.getName();
                ProjectReference reference = _outer.getProjectReferences().get(key);
                reference.putDeclaredType(_projectClass);
            }
        }
    }

    public Map<String, Class<?>> getDeclaringTypes() {
        return _declaringTypes;
    }

    /**
     * @return the declaring artifact
     */
    public Artifact getDeclaringArtifact() {
        return _declaringArtifact;
    }

    /**
     * @param declaringArtifact the declaring artifact to set
     */
    void setDeclaringArtifact(Artifact declaringArtifact) {
        _declaringArtifact = declaringArtifact;
    }

    /**
     * @return the declaring field
     */
    public Field getDeclaringField() {
        return _declaringField;
    }

    /**
     * @param declaringField the declaring field to set
     */
    void setDeclaringField(Field declaringField) {
        _declaringField = declaringField;
    }

}
