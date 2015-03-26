/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.primitives;

import adalid.core.Primitive;

/**
 * @author Jorge Campins
 */
public abstract class BinaryPrimitive extends Primitive {

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
        return null;
    }

}
