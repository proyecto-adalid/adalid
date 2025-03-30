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
package adalid.jee2;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public enum ProjectDependencyType {

    POM, JAR, TEST_JAR, MAVEN_PLUGIN, EJB, EJB_CLIENT, WAR, EAR, RAR, JAVA_SOURCE, JAVADOC;

    private static final List<String> names = new ArrayList<>();

    static {
        for (ProjectDependencyType value : ProjectDependencyType.values()) {
            names.add(value.name());
        }
    }

    public static ProjectDependencyType of(String mavenType) {
        if (StringUtils.isBlank(mavenType)) {
            return null;
        }
        String name = mavenType.trim().toUpperCase().replace('-', '_');
        return names.contains(name) ? valueOf(name) : null;
    }

    public String getMavenType() {
        return name().toLowerCase().replace('_', '-');
    }

}
