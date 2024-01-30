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
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Jorge Campins
 */
public class ProjectEntityReference {

    private Entity _entity;

    private final Class<?> _entityClass;

    private boolean _explicit;

    private boolean _implicit;

    private final Map<String, Class<?>> _declaredTypes = new TreeMap<>();

    private final Map<String, Class<?>> _declaringTypes = new TreeMap<>();

    private final Project _outer;

    ProjectEntityReference(Class<?> entityClass, final Project outer) {
        _outer = outer;
        _entity = null;
        _entityClass = entityClass;
    }

    /**
     * @return the entity
     */
    public Entity getEntity() {
        return _entity;
    }

    /**
     * @param entity the entity to set
     */
    void setEntity(Entity entity) {
        _entity = entity;
    }

    /**
     * @return the entity class
     */
    public Class<?> getEntityClass() {
        return _entityClass;
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
            if (Entity.class.isAssignableFrom(declaringType)) {
                String key = declaringType.getSimpleName();
                if (_outer.getEntityReferences().containsKey(key)) {
                    ProjectEntityReference reference = _outer.getEntityReferences().get(key);
                    reference.putDeclaredType(_entityClass);
                }
            } else if (Project.class.isAssignableFrom(declaringType)) {
                String key = declaringType.getName();
                ProjectReference reference = _outer.getProjectReferences().get(key);
                reference.putDeclaredType(_entityClass);
            }
        }
    }

    public Map<String, Class<?>> getDeclaringTypes() {
        return _declaringTypes;
    }

    /**
     * @return the explicit reference indicator
     */
    public boolean isExplicit() {
        return _explicit;
    }

    /**
     * @param explicit the explicit reference indicator to set
     */
    void setExplicit(boolean explicit) {
        _explicit = explicit;
    }

    /**
     * @param explicit the explicit reference indicator to set
     */
    void setExplicit(Class<?> declaringType) {
        _explicit |= Project.class.isAssignableFrom(declaringType);
    }

    /**
     * @return the implicit reference indicator
     */
    public boolean isImplicit() {
        return _implicit;
    }

    /**
     * @param implicit the implicit reference indicator to set
     */
    void setImplicit(boolean implicit) {
        _implicit = implicit;
    }

    /**
     * @param implicit the implicit reference indicator to set
     */
    void setImplicit(Class<?> declaringType) {
        _implicit |= Entity.class.isAssignableFrom(declaringType);
    }

}
