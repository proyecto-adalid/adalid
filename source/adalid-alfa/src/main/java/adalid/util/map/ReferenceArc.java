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
package adalid.util.map;

import java.util.Objects;

/**
 * @author Jorge Campins
 */
public class ReferenceArc implements Comparable<ReferenceArc> {

    private final EntityVertex source, target;

    private final boolean across;

    public ReferenceArc(EntityVertex source, EntityVertex target) {
        this.source = source;
        this.target = target;
        this.across = !source.getApplication().equals(target.getApplication());
    }

    public EntityVertex getSource() {
        return source;
    }

    public EntityVertex getTarget() {
        return target;
    }

    public boolean isAcross() {
        return across;
    }

    @Override
    public int compareTo(ReferenceArc that) {
        return this.toString().compareTo(that.toString());
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof ReferenceArc && this.toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.source);
        hash = 29 * hash + Objects.hashCode(this.target);
        return hash;
    }

    @Override
    public String toString() {
        return source + " > " + target;
    }

}
