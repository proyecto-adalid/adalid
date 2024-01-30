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

import adalid.commons.util.StrUtils;
import adalid.core.interfaces.Property;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class MetaProperty {

    final MetaEntity entity;

    final String step, tab;

    final Class<? extends Property> type;

    final String name;

    final MetaPropertyKind kind;

    public String wordyName, description, label, anchorLabel;

    public MetaProperty(MetaEntity entity, Class<? extends Property> type, String name, MetaPropertyKind kind) {
        this.entity = entity;
        if (entity.step > 0) {
            this.step = "step" + entity.step;
            this.entity.steps.add(this.step);
            this.tab = "tab" + entity.step;
            this.entity.tabs.add(this.tab);
        } else {
            this.step = null;
            this.tab = null;
        }
        this.type = type;
        this.name = name;
        this.kind = kind;
        this.wordyName = fixString(StrUtils.getWordyString(name));
    }

    public MetaEntity getEntity() {
        return entity;
    }

    public String getStep() {
        return step;
    }

    public String getTab() {
        return tab;
    }

    public Class<? extends Property> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public MetaPropertyKind getKind() {
        return kind;
    }

    public String getWordyName() {
        return wordyName;
    }

    public String getDescription() {
        return StringUtils.isNotBlank(description) ? fixString(description)
            : MetaPropertyKind.ANCHOR.equals(kind) ? "descripción del grupo de propiedades ancladas a " + wordyName
            : MetaPropertyKind.ANCHORED.equals(kind) ? "descripción de la propiedad anclada " + wordyName : null;
    }

    public String getLabel() {
        return StringUtils.isNotBlank(label) ? fixString(label) : wordyName;
    }

    public String getAnchorLabel() {
        return StringUtils.isNotBlank(anchorLabel) ? fixString(anchorLabel) : wordyName;
    }

    private String fixString(String string) {
        return StringUtils.isBlank(string) ? null
            : string.replaceAll("\\\"", "\\\\\"").replaceAll("\\bano\\b", "año").replaceAll("\\barea\\b", "área").replaceAll("(\\w+)ion\\b", "$1ión");
    }

}
