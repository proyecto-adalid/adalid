#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.psm;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.interfaces.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
public class DescriptiveAttributes {

    @AddAttributesMethod
    public static void addAttributes(Project project) {
        setDescriptiveAttributes(project);
    }

    @AddAttributesMethod
    public static void addAttributes(Entity entity) {
        setDescriptiveAttributes(entity);
    }

    @AddAttributesMethod
    public static void addAttributes(Operation operation) {
        setDescriptiveAttributes(operation);
    }

    private static final String description = LoremIpsum.getString();

    private static final String short_description = LoremIpsum.getString(500);

    private static void setDescriptiveAttributes(Artifact artifact) {
        if (artifact.getClass().getCanonicalName().startsWith("meta")) {
            artifact.setDefaultDescription(StringUtils.defaultIfBlank(artifact.getDefaultDescription(), description));
            artifact.setDefaultShortDescription(StringUtils.defaultIfBlank(artifact.getDefaultShortDescription(), short_description));
        }
    }

}
