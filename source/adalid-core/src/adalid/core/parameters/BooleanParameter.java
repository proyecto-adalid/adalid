/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.parameters;

import adalid.core.annotations.BooleanField;
import adalid.core.annotations.ParameterField;
import adalid.core.data.types.BooleanData;
import adalid.core.interfaces.Parameter;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class BooleanParameter extends BooleanData implements Parameter {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(BooleanField.class);
        valid.add(ParameterField.class);
        return valid;
    }

}
