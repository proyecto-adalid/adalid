/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.properties;

import adalid.core.annotations.BaseField;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.CastingField;
import adalid.core.annotations.CharacterDataGen;
import adalid.core.annotations.CharacterKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.DiscriminatorColumn;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.annotations.UniqueKey;
import adalid.core.annotations.UrlProperty;
import adalid.core.data.types.StringData;
import adalid.core.interfaces.Property;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class StringProperty extends StringData implements Property {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(BaseField.class);
        valid.add(BusinessKey.class);
        valid.add(CastingField.class);
        valid.add(CharacterKey.class);
        valid.add(CharacterDataGen.class);
        valid.add(ColumnField.class);
        valid.add(DescriptionProperty.class);
        valid.add(DiscriminatorColumn.class);
        valid.add(FileReference.class);
        valid.add(NameProperty.class);
        valid.add(PropertyField.class);
        valid.add(StringField.class);
        valid.add(UniqueKey.class);
        valid.add(UrlProperty.class);
        return valid;
    }

}
