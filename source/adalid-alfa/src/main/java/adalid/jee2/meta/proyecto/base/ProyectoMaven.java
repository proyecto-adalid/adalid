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
package adalid.jee2.meta.proyecto.base;

import adalid.commons.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.jee2.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoMaven extends ProyectoJava2 {

    protected static final String POM_PROPERTIES = "/pom.properties";

    protected static final String DEFAULT_TLD = "org";

    protected static final String DEFAULT_SLD = "xyz";

    protected static final String DEFAULT_VERSION = "1.0-SNAPSHOT";

    private final ProjectModuleType[] _projectModuleTypes = ProjectModuleType.values();

    private final Map<ProjectModuleType, Map<String, ProjectDependency>> _projectDependencies;

    private Properties _pom;

    private String _groupId;

    private String _version;

    private final ProjectObjectModelReader pom = new AbstractProjectObjectModel("/pom.properties") {
    };

    public ProyectoMaven() {
        super();
        _projectDependencies = new LinkedHashMap<>();
        for (ProjectModuleType moduleType : _projectModuleTypes) {
            _projectDependencies.put(moduleType, new LinkedHashMap<>());
        }
    }

    public ProjectObjectModelReader getMetaProjectObjectModel() {
        return pom;
    }

    /**
     * El método addProjectDependency agrega una dependencia a la lista de dependencias de todos los módulos y librerias del proyecto. Solo se agregan
     * dependencias válidas, es decir, dependencias cuyos identificadores de grupo, artefacto y versión sean válidos.
     *
     * @param dependency la dependencia a agregar.
     * @return true, si agrega la dependencia; en caso contrario, false.
     */
    public boolean addProjectDependency(ProjectDependency dependency) {
        if (dependency != null && dependency.isValid()) {
            boolean added = false;
            for (ProjectModuleType moduleType : _projectModuleTypes) {
                added |= addProjectDependency(dependency, moduleType);
            }
            return added;
        }
        return false;
    }

    /**
     * El método addProjectDependency agrega una dependencia a la lista de dependencias del módulo (o librería) del proyecto identificado por el
     * parámetro moduleType. Solo se agregan dependencias válidas, es decir, dependencias cuyos identificadores de grupo, artefacto y versión sean
     * válidos.
     *
     * @param dependency la dependencia a agregar.
     * @param moduleType el módulo (o librería) al que se debe agregar la dependencia.
     * @return true, si agrega la dependencia; en caso contrario, false.
     */
    public boolean addProjectDependency(ProjectDependency dependency, ProjectModuleType moduleType) {
        if (dependency != null && dependency.isValid() && moduleType != null) {
            putProjectDependency(dependency, moduleType);
            if (ProjectModuleType.LIB.equals(moduleType)) {
                putProjectDependency(ProjectDependency.of(dependency).setScope(ProjectDependencyScope.RUNTIME), ProjectModuleType.LIB_DIR);
            }
            return true;
        }
        return false;
    }

    private void putProjectDependency(ProjectDependency dependency, ProjectModuleType moduleType) {
        Map<String, ProjectDependency> map = _projectDependencies.get(moduleType);
        String key = dependency.getKey();
        map.put(key, dependency.merge(map.get(key)));
    }

    /**
     * El método removeProjectDependency elimina una dependencia de la lista de dependencias de todos los módulos y librerias del proyecto.
     *
     * @param dependency la dependencia a eliminar.
     * @return true, si elimina la dependencia; en caso contrario, false.
     */
    public boolean removeProjectDependency(ProjectDependency dependency) {
        if (dependency != null) {
            boolean removed = false;
            for (ProjectModuleType moduleType : _projectModuleTypes) {
                removed |= removeProjectDependency(dependency, moduleType);
            }
            return removed;
        }
        return false;
    }

    /**
     * El método removeProjectDependency elimina una dependencia de la lista de dependencias del módulo (o librería) del proyecto identificado por el
     * parámetro moduleType.
     *
     * @param dependency la dependencia a eliminar.
     * @param moduleType el módulo (o librería) del que se debe eliminar la dependencia.
     * @return true, si elimina la dependencia; en caso contrario, false.
     */
    public boolean removeProjectDependency(ProjectDependency dependency, ProjectModuleType moduleType) {
        return dependency != null && moduleType != null && _projectDependencies.get(moduleType).remove(dependency.getKey()) != null;
    }

    public void clearProjectDependencies() {
        for (ProjectModuleType moduleType : _projectModuleTypes) {
            clearProjectDependencies(moduleType);
        }
    }

    public void clearProjectDependencies(ProjectModuleType moduleType) {
        if (moduleType != null) {
            _projectDependencies.get(moduleType).clear();
        }
    }

    public Map<ProjectModuleType, Map<String, ProjectDependency>> getProjectDependencies() {
        return _projectDependencies;
    }

    public List<ProjectDependency> getProjectDependencies(String moduleType) {
        return getProjectDependencies(ProjectModuleType.of(moduleType));
    }

    public List<ProjectDependency> getProjectDependencies(ProjectModuleType moduleType) {
        return moduleType == null ? null : new ArrayList<>(_projectDependencies.get(moduleType).values());
    }

    @Override
    protected boolean readyToWrite(String platform) {
        boolean ready = super.readyToWrite(platform);
        _pom = PropertiesHandler.getResourceAsProperties(POM_PROPERTIES);
        return ready && _pom != null && !_pom.isEmpty();
    }

    /**
     * @return the POM properties
     */
    public Properties getPomProperties() {
        return _pom;
    }

    /**
     * @return the group id
     */
    public String getGroupId() {
        return StringUtils.defaultIfBlank(_groupId, getDefaultGroupId());
    }

    /**
     * @param groupId the group id to set
     */
    public void setGroupId(String groupId) {
        _groupId = StrUtils.getMavenIdentifier(groupId);
    }

    /**
     * @return the artifact id
     */
    public String getArtifactId() {
        return getAlias();
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return StringUtils.defaultIfBlank(_version, getDefaultVersion());
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        _version = StrUtils.getMavenVersion(version);
    }

    public String getVersionNumber() {
        String version = getVersion();
        return version.contains("SNAPSHOT") ? StringUtils.replace(version, "SNAPSHOT", getBuildDate()) : version;
    }

    protected String getDefaultGroupId() {
        if (_pom != null) {
            String groupId = _pom.getProperty("project.groupId");
            if (StringUtils.isNotBlank(groupId)) {
                return StrUtils.getMavenIdentifier(groupId) + "." + getAlias();
            }
        }
        return DEFAULT_TLD + "." + DEFAULT_SLD + "." + getAlias();
    }

    protected String getDefaultVersion() {
        return DEFAULT_VERSION;
    }

    @Override
    protected String getDefaultEarProjectName() {
        return getAlias() + "-ear";
    }

    @Override
    protected String getDefaultWebProjectName() {
        return getAlias() + "-web";
    }

    @Override
    protected String getDefaultWebApiProjectName() {
        return getAlias() + "-web-api";
    }

    @Override
    protected String getDefaultRootPackageName() {
        String group = StrUtils.getLowerCaseIdentifier(getGroupId(), '.');
        String alias = getAlias();
        return group.contains(alias) ? group : group + "." + alias;
    }

    @Override
    protected String getDefaultPersistenceRootPackageName() {
        /*
        String group1 = _pom == null ? null : _pom.getProperty("project.groupId");
        String group2 = StringUtils.defaultIfBlank(group1, DEFAULT_TLD + "." + DEFAULT_SLD);
        String group3 = StrUtils.getLowerCaseIdentifier(group2, '.');
        String dbname = StrUtils.getLowerCaseIdentifier(getDatabaseName(), '.');
        return group3 + "." + dbname;
        /**/
        return super.getDefaultPersistenceRootPackageName();
    }

}
