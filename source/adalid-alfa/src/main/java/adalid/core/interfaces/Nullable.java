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
package adalid.core.interfaces;

import adalid.core.expressions.*;

/**
 * @author Jorge Campins
 */
public interface Nullable {

    /**
     * El método <b>isNull</b> contruye una expresión lógica que genera la comparación de este objeto con el valor nulo. La comparación resulta en
     * verdadero si el valor del objeto es nulo.
     *
     * @return expresión lógica que genera la comparación con el valor nulo.
     */
    BooleanComparisonX isNull();

    /**
     * El método <b>isNotNull</b> contruye una expresión lógica que genera la comparación de este objeto con el valor nulo. La comparación resulta en
     * verdadero si el valor del objeto no es nulo.
     *
     * @return expresión lógica que genera la comparación con el valor nulo.
     */
    BooleanComparisonX isNotNull();

}
