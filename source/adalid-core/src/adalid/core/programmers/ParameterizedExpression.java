/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.programmers;

import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.NamedValue;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jorge Campins
 */
public class ParameterizedExpression {

    private String _expression;

    private Map<String, Artifact> _parametersMap = new LinkedHashMap<>();

    private Map<String, NamedValue> _namedValuesMap = new LinkedHashMap<>();

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

}
