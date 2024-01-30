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

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class IntegerProperty extends IntegerData implements NumericProperty {

    private static final boolean experimenting = false;

    private String _masterSequenceMasterFieldName;

    private Field _masterSequenceMasterField;

    private Entity _masterSequenceMasterEntity;

    private Property _masterSequenceMasterProperty;

    private int _masterSequenceStart = 1;

    private int _masterSequenceStep = 1;

    private NextValueRule _masterSequenceNextValueRule = NextValueRule.UNSPECIFIED;

    /**
     * @return the MasterSequence master field name
     */
//  @Override -- Implements method from: MasterSequence
    public String getMasterSequenceMasterFieldName() {
        return _masterSequenceMasterFieldName;
    }

    /**
     * @param fieldName the MasterSequence master field name to set
     */
    public void setMasterSequenceMasterFieldName(String fieldName) {
        XS2.checkAccess();
        _masterSequenceMasterFieldName = fieldName;
    }

    /**
     * @return the MasterSequence master field
     */
//  @Override -- Implements method from: MasterSequence
    public Field getMasterSequenceMasterField() {
        return _masterSequenceMasterField;
    }

    /**
     * @param field the MasterSequence master field to set
     */
    public void setMasterSequenceMasterField(Field field) {
        XS2.checkAccess();
        _masterSequenceMasterField = field;
    }

    /**
     * @param entity the MasterSequence master entity to set
     */
    public void setMasterSequenceMasterEntity(Entity entity) {
        XS2.checkAccess();
        _masterSequenceMasterEntity = entity;
    }

    /**
     * @return the MasterSequence master property
     */
//  @Override -- Implements method from: MasterSequence
    public Property getMasterSequenceMasterProperty() {
        if (experimenting) {
            if (_masterSequenceMasterProperty == null && _masterSequenceMasterField != null && _masterSequenceMasterEntity != null) {
                _masterSequenceMasterProperty = XS2.getProperty(_masterSequenceMasterField, _masterSequenceMasterEntity, true);
            }
        }
        return _masterSequenceMasterProperty;
    }

    /**
     * @param property the MasterSequence master property to set
     */
    public void setMasterSequenceMasterProperty(Property property) {
        XS2.checkAccess();
        _masterSequenceMasterProperty = property;
    }

    /**
     * @return the MasterSequence start value
     */
    public int getMasterSequenceStart() {
        return _masterSequenceStart;
    }

    /**
     * @param start the MasterSequence start value to set
     */
    public void setMasterSequenceStart(int start) {
        _masterSequenceStart = start;
    }

    /**
     * @return the MasterSequence step value
     */
    public int getMasterSequenceStep() {
        return _masterSequenceStep;
    }

    /**
     * @param step the MasterSequence step value to set
     */
    public void setMasterSequenceStep(int step) {
        _masterSequenceStep = step;
    }

    /**
     * @return the MasterSequence next value rule
     */
    public NextValueRule getMasterSequenceNextValueRule() {
        return _masterSequenceNextValueRule;
    }

    /**
     * @param rule the MasterSequence next value rule to set
     */
    public void setMasterSequenceNextValueRule(NextValueRule rule) {
        XS2.checkAccess();
        _masterSequenceNextValueRule = rule;
    }

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(BaseField.class);
        valid.add(BusinessKey.class);
        valid.add(CastingField.class);
        valid.add(ColumnField.class);
        valid.add(DiscriminatorColumn.class);
        valid.add(MasterSequence.class);
        valid.add(NumericDataGen.class);
        valid.add(NumericField.class);
        valid.add(PrimaryKey.class);
        valid.add(PropertyAggregation.class);
        valid.add(PropertyField.class);
        valid.add(UniqueKey.class);
        return valid;
    }

}
