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
package adalid.util.sql;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SqlRoutine extends SqlArtifact {

    private static final Logger logger = Logger.getLogger(SqlRoutine.class);

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final SqlTable _table;

    private int _position;

    private String _operationType;

    private final Map<String, SqlRoutineParameter> _parameters = new LinkedHashMap<>();
    // </editor-fold>

    public SqlRoutine(SqlTable table) {
        _table = table;
    }

    // <editor-fold defaultstate="collapsed" desc="instance getters and setters">
    /**
     * @return the table
     */
    public SqlTable getTable() {
        return _table;
    }

    /**
     * @return the ordinal position
     */
    public int getPosition() {
        return _position;
    }

    /**
     * @param position the ordinal position to set
     */
    void setPosition(int position) {
        _position = position;
    }

    /**
     * @return the operation type
     */
    public String getOperationType() {
        return _operationType;
    }

    /**
     * @param operationType the operation type to set
     */
    public void setOperationType(String operationType) {
        _operationType = operationType;
    }

    /**
     * @return the parameters map
     */
    public Map<String, SqlRoutineParameter> getParametersMap() {
        return _parameters;
    }

    /**
     * @return the parameters list
     */
    public Collection<SqlRoutineParameter> getParameters() {
        return _parameters.values();
    }

    public String getQualifiedName() {
        return getTable().getName() + "." + getName();
    }
    // </editor-fold>

    private int parameters;

    void add(SqlRoutineParameter parameter) {
        parameter.setPosition(++parameters);
        String name = parameter.getName();
        String text;
        if (StringUtils.isBlank(name)) {
            text = "a null name parameter will not be added to routine " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else if (_parameters.containsKey(name)) {
            text = "parameter " + name + " already added to routine " + getQualifiedName();
            logger.error(SqlUtil.highlight(text));
        } else {
            _parameters.put(name, parameter);
        }
    }

}
