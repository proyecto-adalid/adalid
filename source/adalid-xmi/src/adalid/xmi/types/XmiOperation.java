/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.types;

import adalid.xmi.util.XmiTags;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Carlos
 */
public class XmiOperation {

    private static Logger logger = Logger.getLogger(XmiOperation.class);

    private String baseOperationName = "ProcessOperation";

    private String operationName;

    private String operationNameString;

    private String scopeString;

    private boolean instanceScope;

    private boolean classScope;

    private String alias;

    private boolean customizedParameters = false;

    private Map<String, XmiParameter> parametersMap = new LinkedHashMap<>();

    private List<XmiParameter> parametersList = new ArrayList<>();

    public XmiOperation() {
    }

    /**
     * @return the baseOperationName
     */
    public String getBaseOperationName() {
        return baseOperationName;
    }

    /**
     * @param baseOperationName the baseOperationName to set
     */
    public void setBaseOperationName(String baseOperationName) {
        this.baseOperationName = baseOperationName;
    }

    public void setOperationName(String name) {
        this.operationName = name;

    }

    public String getName() {
        return operationName;
    }

    public Map<String, XmiParameter> getParametersMap() {
        return parametersMap;
    }

    public void setParametersMap(Map<String, XmiParameter> parametersMap) {
        this.parametersMap = parametersMap;
    }

    /**
     * @return the parametersList
     */
    public List<XmiParameter> getParametersList() {
        if (parametersList.isEmpty()) {
            for (XmiParameter parameter : this.getParametersMap().values()) {
                if (parameter != null && !parameter.isIgnored()) {
                    parametersList.add(parameter);
                }
            }
        }
        return parametersList;
    }

    /**
     * @param parametersList the parametersList to set
     */
    public void setParametersList(List<XmiParameter> parametersList) {
        this.parametersList = parametersList;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public String getOperationNameString() {
        operationNameString = StringUtils.capitalize(operationName);
        return operationNameString;
    }

    public boolean getCustomizedParameters() {
        for (XmiParameter parameter : parametersList) {
            if (parameter.isCustomizedParameter()) {
                customizedParameters = true;
                break;
            }
        }
        return customizedParameters;
    }

    /**
     * @param scopeString the scopeString to set
     */
    public void setScopeString(String scopeString) {
        this.scopeString = scopeString;
    }

    /**
     * @return the scopeString
     */
    public String getScopeString() {
        return scopeString;
    }

    /**
     * @return the instanceScope
     */
    public boolean isInstanceScope() {
        instanceScope = this.scopeString.equals(XmiTags.INSTANCE_SCOPE_TAG);
        return instanceScope;
    }

    /**
     * @return the classScope
     */
    public boolean isClassScope() {
        classScope = this.scopeString.equals(XmiTags.CLASSIFIER_SCOPE_TAG);
        return classScope;
    }

}
