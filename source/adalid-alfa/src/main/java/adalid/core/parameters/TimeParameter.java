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
package adalid.core.parameters;

import adalid.core.annotations.*;
import adalid.core.data.types.*;
import adalid.core.interfaces.*;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class TimeParameter extends TimeData implements TemporalParameter {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(ParameterField.class);
        valid.add(TimeField.class);
        return valid;
    }

}
