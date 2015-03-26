/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.interfaces.Property;
import adalid.core.sql.QueryTable;

/**
 * @author Jorge Campins
 */
public class FormField extends DisplayField {

    public FormField(Display display, Property property) {
        super(display, property);
    }

    public FormField(Display display, QueryTable queryTable, Property property, DisplayField parentField) {
        super(display, queryTable, property, parentField);
    }

}
