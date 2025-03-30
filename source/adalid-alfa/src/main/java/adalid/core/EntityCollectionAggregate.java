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
package adalid.core;

import adalid.commons.util.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class EntityCollectionAggregate extends AbstractArtifact implements BoundedArtifact {

    private static final Logger logger = Logger.getLogger(EntityCollection.class);

    private final EntityCollection _collection;

    private final AggregateFunction _function;

    private final String _fieldName;

    private Field _field;

    private Property _property;

    private final Object _minValue, _maxValue;

    static String key(AggregateFunction function, String fieldName) {
        return function + (StringUtils.isBlank(fieldName) ? "" : "(" + fieldName + ")");
    }

    EntityCollectionAggregate(EntityCollection collection, AggregateFunction function, String fieldName, Object minimum, Object maximum) {
        super();
        _collection = collection;
        _function = function;
        _fieldName = ObjUtils.nullif(fieldName, "*");
        _minValue = minimum;
        _maxValue = maximum;
        init(collection);
    }

    private void init(EntityCollection collection) {
        initDeclaringArtifact(collection);
    }

    /**
     * @return the aggregate function
     */
    public AggregateFunction getFunction() {
        return _function;
    }

    /**
     * @return the field name
     */
    public String getFieldName() {
        return _fieldName;
    }

    /**
     * @return the field
     */
    public Field getField() {
        if (_field == null && StringUtils.isNotBlank(_fieldName)) {
            _field = aggregateFunctionField();
        }
        return _field;
    }

    /**
     * @return the property
     */
    public Property getProperty() {
        if (_property == null && StringUtils.isNotBlank(_fieldName)) {
            _property = aggregateFunctionProperty();
        }
        return _property;
    }

    /**
     * @return the minimum value
     */
    @Override
    public Object getMinValue() {
        return _minValue;
    }

    /**
     * @return true if the minimum value is not null; false otherwise
     */
    public boolean isDefinedMinValue() {
        return _minValue != null;
    }

    /**
     * @return the numeric minimum value indicator
     */
    public boolean isNumericMinValue() {
        return _minValue instanceof Number;
    }

    /**
     * @return the maximum value
     */
    @Override
    public Object getMaxValue() {
        return _maxValue;
    }

    /**
     * @return true if the maximum value is not null; false otherwise
     */
    public boolean isDefinedMaxValue() {
        return _maxValue != null;
    }

    /**
     * @return the numeric maximum value indicator
     */
    public boolean isNumericMaxValue() {
        return _maxValue instanceof Number;
    }

    // <editor-fold defaultstate="collapsed" desc="value tag fields, getters and setters">
    /**
     * @return the maximum value tag
     */
    @Override
    public String getMaximumValueTag() {
        return getLocalizedMaximumValueTag(null);
    }

    /**
     * El método setMaximumValueTag se utiliza para establecer la descripción del valor máximo de la agregación que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor máximo de la agregación
     */
    @Override
    public void setMaximumValueTag(String tag) {
        setLocalizedMaximumValueTag(null, tag);
    }

    /**
     * @return the minimum value tag
     */
    @Override
    public String getMinimumValueTag() {
        return getLocalizedMinimumValueTag(null);
    }

    /**
     * El método setMinimumValueTag se utiliza para establecer la descripción del valor mínimo de la agregación que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor mínimo de la agregación
     */
    @Override
    public void setMinimumValueTag(String tag) {
        setLocalizedMinimumValueTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the maximum value as a label or identifier.
     */
    private final Map<Locale, String> _localizedMaximumValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the maximum value tag
     */
//  @Override
    public String getLocalizedMaximumValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedMaximumValueTag.get(l);
    }

    /**
     * El método setMaximumValueTag se utiliza para establecer la descripción del valor máximo de la agregación que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor máximo de la agregación
     */
//  @Override
    public void setLocalizedMaximumValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedMaximumValueTag.remove(l);
        } else {
            _localizedMaximumValueTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the minimum value as a label or identifier.
     */
    private final Map<Locale, String> _localizedMinimumValueTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the minimum value tag
     */
//  @Override
    public String getLocalizedMinimumValueTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedMinimumValueTag.get(l);
    }

    /**
     * El método setMinimumValueTag se utiliza para establecer la descripción del valor mínimo de la agregación que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el valor mínimo de la agregación
     */
//  @Override
    public void setLocalizedMinimumValueTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedMinimumValueTag.remove(l);
        } else {
            _localizedMinimumValueTag.put(l, tag);
        }
    }
    // </editor-fold>

    boolean check() {
        String message = "failed to add aggregate " + _collection.getFullName() + "." + key(_function, _fieldName);
        boolean error;
        if (AggregateFunction.COUNT.equals(_function)) {
            error = _minValue == null && _maxValue == null;
            if (error) {
                error(message, "no minimum nor maximum value has been specified");
            } else {
                boolean a = isNumericMinValue() && ObjUtils.lt(_minValue, 0);
                if (a) {
                    error(message, "minimum value is less than zero");
                }
                boolean b = isNumericMaxValue() && ObjUtils.lt(_maxValue, 0);
                if (b) {
                    error(message, "maximum value is less than zero");
                }
                boolean c = isNumericMinValue() && isNumericMaxValue() && ObjUtils.gt(_minValue, _maxValue);
                if (c) {
                    error(message, "minimum value is greater than maximum value");
                }
                error = a || b || c;
            }
        } else {
            boolean a = getProperty() == null;
            if (a) {
                error(message, "property argument is missing or invalid");
            }
            boolean b = _minValue == null && _maxValue == null;
            if (b) {
                error(message, "no minimum nor maximum value has been specified");
            } else {
                b = isNumericMinValue() && isNumericMaxValue() && ObjUtils.gt(_minValue, _maxValue);
                if (b) {
                    error(message, "minimum value is greater than maximum value");
                }
            }
            error = a || b;
        }
        return error;
    }

    private Field aggregateFunctionField() {
        Class targetEntityClass = _collection.getTargetEntityClass();
        if (targetEntityClass == null || targetEntityClass == void.class) {
            return null;
        }
        String role = "aggregable property";
        Class<?>[] validTypes = validTypes();
        return XS1.getField(true, role, _fieldName, targetEntityClass, Entity.class, validTypes);
    }

    private Property aggregateFunctionProperty() {
        Field field = getField();
        Entity targetEntity = _collection.getTargetEntity();
        return field == null || targetEntity == null ? null : XS1.getProperty(field, targetEntity);
    }

    private Class<?>[] validTypes() {
        return AggregateFunction.COUNT.equals(_function)
            ? new Class<?>[]{Property.class}
            : new Class<?>[]{NumericProperty.class};
    }

    private void error(String message, String reasons) {
        logger.error(message + "; " + reasons);
        Project.increaseParserErrorCount();
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    private static final String EOL = "\n";

    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                string += fee + tab + "function" + faa + _function + foo;
                string += fee + tab + "property" + faa + _property + foo;
                string += fee + tab + "minValue" + faa + _minValue + foo;
                string += fee + tab + "maxValue" + faa + _maxValue + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
