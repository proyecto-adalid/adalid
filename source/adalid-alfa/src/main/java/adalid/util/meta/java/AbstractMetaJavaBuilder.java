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
package adalid.util.meta.java;

import adalid.commons.velocity.Writer;
import adalid.util.Platform;
import adalid.util.Utility;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class AbstractMetaJavaBuilder extends Utility {

    protected void build() {
        addEntities();
        writeMetaJava();
    }

    protected abstract void addEntities();

    protected void writeMetaJava() {
        Writer writer = new Writer(this, "builder");
        writer.write(Platform.META_JAVA_BUILDER);
    }

    protected final List<MetaEntity> entities = new ArrayList<>();

    public List<MetaEntity> getEntities() {
        return entities;
    }

    protected MetaEntity add(String name) {
        return add(new MetaEntity(name));
    }

    protected MetaEntity add(MetaEntity entity) {
        entities.add(entity);
        return entity;
    }

    protected String hyperlink(String url, String text) {
        final String br = "<br>";
        final String href = "href=\"" + url + "\"";
        final String style = "style=\"display: inline-block; margin: 0.25rem 0;\"";
        final String target = "target=\"_blank\"";
        return br + "<a " + href + " " + style + " " + target + ">" + StringUtils.defaultIfBlank(text, url) + "</a>" + br;
    }

}
