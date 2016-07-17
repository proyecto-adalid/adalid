/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.StrUtils;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoMaven extends ProyectoJava {

    protected static final String DEFAULT_TLD = "org";

    protected static final String DEFAULT_SLD = "xyz";

    protected static final String DEFAULT_VERSION = "1.0-SNAPSHOT";

    @Override
    protected void beforeWriting(String platform) {
        super.beforeWriting(platform);
        final String resource = "/pom.properties";
        _pom = PropertiesHandler.getResourceAsProperties(resource);
    }

    private Properties _pom;

    private String _groupId;

    private String _version;

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

    protected String getDefaultGroupId() {
        String groupId = _pom.getProperty("project.groupId");
        if (StringUtils.isNotBlank(groupId)) {
            return StrUtils.getMavenIdentifier(groupId) + "." + getAlias();
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
    protected String getDefaultRootPackageName() {
        String group = getGroupId();
        String alias = getAlias();
        return group.contains(alias) ? group : group + "." + alias;
    }

}
