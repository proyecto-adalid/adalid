/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.properties;

import adalid.core.annotations.BaseField;
import adalid.core.annotations.CastingField;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.NumericDataGen;
import adalid.core.annotations.NumericField;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.UniqueKey;
import adalid.core.data.types.ByteData;
import adalid.core.interfaces.Property;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class ByteProperty extends ByteData implements Property {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(BaseField.class);
        valid.add(CastingField.class);
        valid.add(ColumnField.class);
        valid.add(NumericDataGen.class);
        valid.add(NumericField.class);
        valid.add(PropertyField.class);
        valid.add(UniqueKey.class);
        return valid;
    }

}
