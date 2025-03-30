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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ReferencePath implements Comparable<ReferencePath> {

    private final EntityVertex origin;

    private final List<EntityVertex> vertices = new ArrayList<>();

    private boolean circular, complete, original;

    public ReferencePath(ReferenceArc arc) {
        origin = arc.getSource();
        vertices.add(origin);
        add(arc.getTarget());
    }

    public ReferencePath(ReferencePath referencePath, EntityVertex vertex) {
        origin = referencePath.origin;
        vertices.addAll(referencePath.vertices);
        add(vertex);
    }

    private void add(EntityVertex vertex) {
        if (complete) {
            throw new IllegalArgumentException(this + " is complete so vertex " + vertex + " cannot be added");
        } else {
            if (vertices.contains(vertex)) {
                finalise();
            }
            vertices.add(vertex);
        }
    }

    public EntityVertex getOrigin() {
        return origin;
    }

    public String getApplication() {
        return origin.getApplication();
    }

    public List<EntityVertex> getVertices() {
        return vertices;
    }

    public boolean isCircular() {
        return circular;
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isOriginal() {
        return original;
    }

    public void finalise() {
        circular = false;
        complete = true;
        original = true;
        String application = origin.getApplication();
        for (EntityVertex vertex : vertices) {
            if (application.equals(vertex.getApplication())) {
                if (original) {
                    continue;
                }
                circular = true;
                break;
            } else {
                original = false;
            }
        }
    }

    @Override
    public int compareTo(ReferencePath that) {
        return this.toString().compareTo(that.toString());
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof ReferencePath && this.toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.origin);
        hash = 29 * hash + Objects.hashCode(this.vertices);
        hash = 29 * hash + (this.complete ? 1 : 0);
        hash = 29 * hash + (this.circular ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        List<String> strings = new ArrayList<>();
        for (EntityVertex vertice : vertices) {
            strings.add(vertice.toString());
        }
        return StringUtils.join(strings, " > ");
    }

}
