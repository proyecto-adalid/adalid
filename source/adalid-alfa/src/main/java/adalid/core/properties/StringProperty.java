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
package adalid.core.properties;

import adalid.core.annotations.*;
import adalid.core.data.types.*;
import adalid.core.interfaces.*;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class StringProperty extends StringData implements AlphanumericProperty {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(BaseField.class);
        valid.add(BusinessKey.class);
        valid.add(CastingField.class);
        valid.add(CharacterDataGen.class);
        valid.add(ColumnField.class);
        valid.add(DescriptionProperty.class);
//      valid.add(DiscriminatorColumn.class); // 2020/05/03
        valid.add(EmbeddedDocument.class);
        valid.add(FileReference.class);
        valid.add(NameProperty.class);
        valid.add(PropertyField.class);
        valid.add(PropertyAggregation.class);
        valid.add(StringField.class);
        valid.add(UniqueKey.class);
        valid.add(UrlProperty.class);
        valid.add(UniformResourceLocator.class);
        valid.add(VariantString.class);
        return valid;
    }

}
