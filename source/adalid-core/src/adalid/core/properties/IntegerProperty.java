/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.properties;

import adalid.core.annotations.BaseField;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DiscriminatorColumn;
import adalid.core.annotations.NumericDataGen;
import adalid.core.annotations.NumericField;
import adalid.core.annotations.NumericKey;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.UniqueKey;
import adalid.core.data.types.IntegerData;
import adalid.core.interfaces.Property;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class IntegerProperty extends IntegerData implements Property {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(BaseField.class);
        valid.add(BusinessKey.class);
        valid.add(ColumnField.class);
        valid.add(DiscriminatorColumn.class);
        valid.add(NumericDataGen.class);
        valid.add(NumericField.class);
        valid.add(NumericKey.class);
        valid.add(PrimaryKey.class);
        valid.add(PropertyField.class);
        valid.add(UniqueKey.class);
        return valid;
    }

}
