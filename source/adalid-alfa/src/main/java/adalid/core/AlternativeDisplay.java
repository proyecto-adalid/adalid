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
package adalid.core;

import adalid.core.enums.*;
import adalid.core.interfaces.*;

/**
 * @author Jorge Campins
 */
public class AlternativeDisplay {

    AlternativeDisplay(Display alternative, Display display, Entity entity, DisplayMode mode, DisplayFormat format, String qualifier, String kinship, Entity someEntity) {
        this.alternative = alternative;
        this.display = display;
        this.entity = entity;
        this.mode = mode;
        this.format = format;
        this.qualifier = qualifier;
        this.kinship = kinship;
        this.someEntity = someEntity;
    }

    Display alternative;

    Display display;

    Entity entity;

    DisplayMode mode;

    DisplayFormat format;

    String qualifier;

    String kinship;

    Entity someEntity;

    public Display getAlternative() {
        return alternative;
    }

    public Display getDisplay() {
        return display;
    }

    public Entity getEntity() {
        return entity;
    }

    public DisplayMode getMode() {
        return mode;
    }

    public DisplayFormat getFormat() {
        return format;
    }

    public String getQualifier() {
        return qualifier;
    }

    public String getKinship() {
        return kinship;
    }

    public Entity getSomeEntity() {
        return someEntity;
    }

}
