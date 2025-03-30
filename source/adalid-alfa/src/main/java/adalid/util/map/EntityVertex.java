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

import adalid.core.interfaces.Entity;
import java.util.Objects;

/**
 * @author Jorge Campins
 */
public class EntityVertex implements Comparable<EntityVertex> {

    private final String application, entityClass;

    private final boolean defaultLocation, displayAvailable;

    public EntityVertex(String application, Entity entity) {
        this.application = application;
        this.entityClass = entity.getClass().getSimpleName();
        this.defaultLocation = entity.isApplicationDefaultLocation();
        this.displayAvailable = entity.isDisplayAvailable();
    }

    public String getApplication() {
        return application;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public boolean isDefaultLocation() {
        return defaultLocation;
    }

    public boolean isDisplayAvailable() {
        return displayAvailable;
    }

    @Override
    public int compareTo(EntityVertex that) {
        return this.toString().compareTo(that.toString());
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof EntityVertex && this.toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.application);
        hash = 97 * hash + Objects.hashCode(this.entityClass);
        return hash;
    }

    @Override
    public String toString() {
        return application + "/" + entityClass;
    }

}
