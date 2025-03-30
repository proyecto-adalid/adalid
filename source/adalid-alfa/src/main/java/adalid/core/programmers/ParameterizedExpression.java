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
package adalid.core.programmers;

import adalid.core.interfaces.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public class ParameterizedExpression {

    private String _expression;

    private Map<String, Artifact> _parametersMap = new LinkedHashMap<>();

    private Map<String, NamedValue> _namedValuesMap = new LinkedHashMap<>();

    private Map<SpecialValue, String> _specialValuesMap = new LinkedHashMap<>();

    /**
     * @return the expression
     */
    public String getExpression() {
        return _expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(String expression) {
        _expression = expression;
    }

    /**
     * @return the parameters map
     */
    public Map<String, Artifact> getParametersMap() {
        return _parametersMap;
    }

    /**
     * @param parameters the parameters map to set
     */
    public void setParametersMap(Map<String, Artifact> parameters) {
        _parametersMap = parameters;
    }

    /**
     * @return the named values map
     */
    public Map<String, NamedValue> getNamedValuesMap() {
        return _namedValuesMap;
    }

    /**
     * @param namedValues the named values map to set
     */
    public void setNamedValuesMap(Map<String, NamedValue> namedValues) {
        _namedValuesMap = namedValues;
    }

    /**
     * @return the special values map
     */
    public Map<SpecialValue, String> getSpecialValuesMap() {
        return _specialValuesMap;
    }

    /**
     * @param specialValues the special values map to set
     */
    public void setSpecialValuesMap(Map<SpecialValue, String> specialValues) {
        _specialValuesMap = specialValues;
    }

}
