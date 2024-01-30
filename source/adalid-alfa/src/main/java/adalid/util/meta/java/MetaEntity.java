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

import adalid.core.interfaces.Property;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jorge Campins
 */
public class MetaEntity {

    final String name;

    final List<MetaProperty> properties = new ArrayList<>();

    final Set<String> steps = new TreeSet<>();

    final Set<String> tabs = new TreeSet<>();

    public int step = 0;

    public MetaEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<MetaProperty> getProperties() {
        return properties;
    }

    public List<String> getSteps() {
        List<String> list = new ArrayList<>();
        list.addAll(steps);
        return list;
    }

    public List<String> getTabs() {
        List<String> list = new ArrayList<>();
        list.addAll(tabs);
        return list;
    }

    public MetaProperty add(Class<? extends Property> type, String name) {
        return add(build(type, name, MetaPropertyKind.UNLINKED));
    }

    public MetaProperty add(Class<? extends Property> type, String name, MetaPropertyKind kind) {
        return add(build(type, name, kind));
    }

    private MetaProperty add(MetaProperty property) {
        properties.add(property);
        return property;
    }

    private MetaProperty build(Class<? extends Property> type, String name, MetaPropertyKind kind) {
        return new MetaProperty(this, type, name, kind);
    }

}
