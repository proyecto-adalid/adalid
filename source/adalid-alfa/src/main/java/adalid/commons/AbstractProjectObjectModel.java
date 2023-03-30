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
package adalid.commons;

import adalid.commons.properties.*;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

/**
 * @author Jorge Campins
 */
public abstract class AbstractProjectObjectModel implements ProjectObjectModelReader {

    private static final Logger logger = Logger.getLogger(ProjectObjectModel.class);

    private static final String EMPTY = "";

    private static final String V10SS = "1.0-SNAPSHOT";

    private final Properties pom;

    private final boolean empty;

    protected AbstractProjectObjectModel(String resource) {
        pom = StringUtils.isBlank(resource) ? null : PropertiesHandler.getResourceAsProperties(resource, Level.ERROR, Level.TRACE);
        empty = pom == null || pom.isEmpty();
    }

    @Override
    public String getProjectGroupId() {
        return empty ? EMPTY : StringUtils.trimToEmpty(pom.getProperty("project.groupId"));
    }

    @Override
    public String getProjectArtifactId() {
        return empty ? EMPTY : StringUtils.trimToEmpty(pom.getProperty("project.artifactId"));
    }

    @Override
    public String getProjectVersion() {
        return empty ? V10SS : StringUtils.defaultIfBlank(pom.getProperty("project.version"), V10SS);
    }

    @Override
    public String getProjectName() {
        return empty ? EMPTY : StringUtils.trimToEmpty(pom.getProperty("project.name"));
    }

    @Override
    public String getProjectBuildTimestamp() {
        return empty ? EMPTY : StringUtils.trimToEmpty(pom.getProperty("project.build.timestamp"));
    }

    @Override
    public String getProjectVersionNumber() {
        String version = getProjectVersion();
        if (version.contains("SNAPSHOT")) {
            String timestamp = getProjectBuildTimestamp(); // maven.build.timestamp.format=yyyyMMdd-HHmm
            return timestamp.isEmpty() ? version : StringUtils.replace(version, "SNAPSHOT", StringUtils.substringBefore(timestamp, "-"));
        }
        return version;
    }

    @Override
    public String getProjectVersionString() {
        ArtifactVersion artifactVersion = getArtifactVersion();
        int majorVersion = artifactVersion.getMajorVersion();
        int minorVersion = artifactVersion.getMinorVersion();
        int incrementalVersion = artifactVersion.getIncrementalVersion();
        int buildNumber = artifactVersion.getBuildNumber();
//      String qualifier = artifactVersion.getQualifier();
        String[] strings = {
            String.format("%03d", majorVersion),
            String.format("%03d", minorVersion),
            String.format("%03d", incrementalVersion),
            String.format("%03d", buildNumber)
        };
        return StringUtils.join(strings, ".");
    }

    @Override
    public ArtifactVersion getArtifactVersion() {
        String projectVersion = getProjectVersion();
        return new DefaultArtifactVersion(projectVersion);
    }

    @Override
    public void logProjectVersion() {
        String name = StringUtils.defaultIfBlank(getProjectName(), "?");
        String version = getProjectVersionNumber();
        logger.info(name + " " + version);
    }

}
