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

import adalid.core.interfaces.*;
import adalid.core.sql.*;

/**
 * @author Jorge Campins
 */
public class FormField extends DisplayField {

    public FormField(Display display, EntityCollection collection) {
        super(display, collection);
    }

    public FormField(Display display, Property property) {
        super(display, property);
    }

    public FormField(Display display, QueryTable queryTable, Property property, DisplayField parentField) {
        super(display, queryTable, property, parentField);
    }

}
