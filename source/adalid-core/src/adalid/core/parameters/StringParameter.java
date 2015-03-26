/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.parameters;

import adalid.core.annotations.FileReference;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.StringField;
import adalid.core.data.types.StringData;
import adalid.core.interfaces.Parameter;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class StringParameter extends StringData implements Parameter {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(FileReference.class);
        valid.add(ParameterField.class);
        valid.add(StringField.class);
        return valid;
    }

}
