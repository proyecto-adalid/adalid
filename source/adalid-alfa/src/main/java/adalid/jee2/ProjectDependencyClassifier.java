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
public enum ProjectDependencyClassifier {

    TESTS, CLIENT, SOURCES, JAVADOC;

    private static final List<String> names = new ArrayList<>();

    static {
        for (ProjectDependencyClassifier value : ProjectDependencyClassifier.values()) {
            names.add(value.name());
        }
    }

    public static ProjectDependencyClassifier of(String mavenClassifier) {
        if (StringUtils.isBlank(mavenClassifier)) {
            return null;
        }
        String name = mavenClassifier.trim().toUpperCase();
        return names.contains(name) ? valueOf(name) : null;
    }

    public String getMavenClassifier() {
        return name().toLowerCase();
    }

}
