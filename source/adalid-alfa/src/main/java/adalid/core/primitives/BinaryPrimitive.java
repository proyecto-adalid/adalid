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
package adalid.core.primitives;

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;

/**
 * @author Jorge Campins
 */
public abstract class BinaryPrimitive extends Primitive {

    private Object _calculableValue;

    private Object _currentValue;

    /**
     * @return the calculable value
     */
    @Override
    public Object getCalculableValue() {
        return _calculableValue;
    }

    /**
     * El método setCalculableValueExpression se utiliza para establecer la expresión para determinar el valor de propiedades binarias (BLOBs)
     * definidas como columnas calculables (mediante el elemento calculable de la anotación ColumnField).
     *
     * @param expression expresión para determinar el valor
     */
    public void setCalculableValueExpression(BinaryPrimitive expression) {
        _calculableValue = validCalculableValue(expression) ? expression : null;
    }

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return null;
    }

    /**
     * @return the default value
     */
    @Override
    public Object getDefaultValue() {
        return null;
    }

    /**
     * @return the current value
     */
    @Override
    public Object getCurrentValue() {
        return _currentValue;
    }

    /**
     * @return true if the current value is SpecialBinaryValue; false otherwise
     */
    public boolean isSpecialCurrentValue() {
        return _currentValue instanceof SpecialBinaryValue;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(SpecialBinaryValue currentValue) {
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    @Override
    public BooleanComparisonX isNull() {
        return XB.AnyType.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.AnyType.Comparison.isNotNull(this);
    }

}
