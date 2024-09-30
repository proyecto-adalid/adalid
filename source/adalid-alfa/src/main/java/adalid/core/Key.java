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

import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class Key extends AbstractArtifact {

    /* commented on 21/03/2021
    private static final Logger logger = Logger.getLogger(Key.class);

    private static final String EOL = "\n";

    /**/
    private final List<KeyField> _keyFieldsList = new ArrayList<>();

    /* commented on 21/03/2021
    private final Map<String, KeyField> _keyFields = new LinkedHashMap<>();

    private String _keyFieldsLastKey;

    private KeyField[] _keyField;

    /**/
    private boolean _unique;

    private boolean _nullsNotDistinct;

    private boolean _containsNullProperties;

    @Override
    public String getKeyFeatures(String prefix, String suffix) {
        String unique = isUnique() ? ",UNIQUE" : "";
        String fields = keyFieldsFeatures(prefix, suffix);
        return "(Key" + fields + unique + ")";
    }

    private String keyFieldsFeatures(String prefix, String suffix) {
        List<String> list = new ArrayList<>();
        for (KeyField keyField : getKeyFieldsList()) {
            list.add(keyField.getKeyFeatures(prefix, suffix));
        }
        return "[" + StringUtils.join(list, ",") + "]";
    }

    /**
     * @return the key fields
     */
    public List<KeyField> getKeyFieldsList() {
        /* commented on 21/03/2021
        List<KeyField> list = new ArrayList<>();
        for (KeyField value : _keyFields.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
        /**/
        return _keyFieldsList;
    }

    /**
     * @return the key fields
     */
    /* commented on 21/03/2021
    public Map<String, KeyField> getKeyFieldsMap() {
        return _keyFields;
    }

    /**/
    /**
     * @return the unique indicator
     */
    public boolean isUnique() {
        return _unique;
    }

    /**
     * El método setUnique se utiliza para establecer la unicidad de la clave. Las claves únicas no permiten valores duplicados en las propiedades que
     * las integran.
     *
     * @param unique true, si la clave es única; de lo contrario false
     */
    public void setUnique(boolean unique) {
        _unique = unique;
    }

    public boolean isNullsNotDistinct() {
        return _nullsNotDistinct;
    }

    /**
     * El método setNullsNotDistinct se utiliza para definir la forma en la que se deben manejar los valores nulos en restricciones e índices únicos.
     * Si el manejador de base de datos de su aplicación es PostgreSQL, de manera predeterminada los valores nulos en una columna única no se
     * consideran iguales, lo que permite múltiples valores nulos en la columna. A partir de la versión 15 de PostgreSQL, setNullsNotDistinct(true)
     * permite modificar esto, haciendo que el índice trate los nulos como iguales. Un índice único de varias columnas solo rechazará los casos en los
     * que todas las columnas indexadas sean iguales en varias filas.
     *
     * @param nullsNotDistinct true, si los valores nulos se deben tratar como iguales; de lo contrario false
     */
    public void setNullsNotDistinct(boolean nullsNotDistinct) {
        _nullsNotDistinct = nullsNotDistinct;
    }

    /* commented on 21/03/2021
    private Field getKeyFieldField() {
        String errmsg = "failed to get field \"_keyField\" declared at " + getClass();
        try {
            return getClass().getDeclaredField("_keyField");
        } catch (NoSuchFieldException | SecurityException ex) {
            logger.error(errmsg, ThrowableUtils.getCause(ex));
            Project.increaseParserErrorCount();
        }
        return null;
    }

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            finaliseFields();
            finaliseKeyFieldArray();
        }
        return ok;
    }

    /**/
    // <editor-fold defaultstate="collapsed" desc="finaliseFields">
    /* commented on 21/03/2021
    private void finaliseFields() {
        String name;
        Class<?> type;
        int modifiers;
        boolean restricted;
        Object o;
        int depth = depth();
        int round = round();
        for (Field field : XS1.getFields(getClass(), Key.class, KeyField.class)) { // getClass().getDeclaredFields()
            field.setAccessible(true);
            logger.trace(field);
            name = field.getName();
            type = field.getType();
//          if (!KeyField.class.isAssignableFrom(type)) {
//              continue;
//          }
            modifiers = field.getModifiers();
            restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
            if (restricted) {
                continue;
            }
            String errmsg = "failed to initialize field \"" + field + "\" at " + this;
            try {
                o = field.get(this);
                if (o == null) {
                    logger.debug(message(type, name, o, depth, round));
                } else if (o instanceof KeyField) {
                    finaliseKeyField(field, (KeyField) o);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                Project.increaseParserErrorCount();
            }
        }
    }

    private void finaliseKeyField(Field field, KeyField keyField) {
        if (field == null || keyField == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _keyFields.containsKey(key)) {
            return;
        }
        if (keyField.isNotDeclared()) {
            keyField.setDeclared(key, this, field);
        }
        _keyFields.put(key, keyField);
        _keyFieldsLastKey = key;
    }

    private String message(Class<?> type, String name, Object value, int depth, int round) {
        String s1 = StringUtils.repeat(" ", 0 + 4 * depth);
        String s2 = this + "," + depth + "," + round;
        String s3 = type.getSimpleName() + " " + name + "=" + value;
        String s4 = s1 + s2 + " " + s3;
        return s4;
    }
    /**/
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="finaliseKeyFieldArray">
    /* commented on 21/03/2021
    private void finaliseKeyFieldArray() {
        _keyField = new KeyField[_keyFieldsList.size()];
        Field field = getKeyFieldField();
        int i = 0;
        for (KeyField rf : _keyFieldsList) {
            _keyField[i] = rf;
            finaliseKeyField(field, _keyField[i], i);
            i++;
        }
    }

    private void finaliseKeyField(Field field, KeyField keyField, int index) {
        if (field == null || keyField == null) {
            return;
        }
        Property property = keyField.getProperty();
        if (property == null) {
            _containsNullProperties = true;
            return;
        }
        String key = property.getName();
        if (key == null) {
            _containsNullProperties = true;
            return;
        }
        if (_keyFields.containsKey(key)) {
            return;
        }
        if (keyField.isNotDeclared()) {
            keyField.setDeclared(key, this, field, index);
        }
        _keyFields.put(key, keyField);
        _keyFieldsLastKey = key;
    }
    /**/
    // </editor-fold>
/**/
    /**
     * El método newKeyField se utiliza para agregar propiedades a la clave. Las claves pueden estar integradas por una o más propiedades, y para cada
     * propiedad se puede establecer el orden en el índice correspondiente. La posición relativa de cada propiedad en el índice está determinado por
     * la secuencia en la que se agregan a la clave.
     *
     * @param property propiedad de la entidad; el orden en el índice será ascendente.
     */
    public void newKeyField(Property property) {
        KeyField keyField = new KeyField(this, property);
        _keyFieldsList.add(keyField);
    }

    /**
     * El método newKeyField se utiliza para agregar propiedades a la clave. Las claves pueden estar integradas por una o más propiedades, y para cada
     * propiedad se puede establecer el orden en el índice correspondiente. La posición relativa de cada propiedad en el índice está determinado por
     * la secuencia en la que se agregan a la clave.
     *
     * @param property propiedad de la entidad.
     * @param sortOption especifica el orden de la propiedad en el índice. Su valor es uno de los elementos de la enumeración SortOption. Seleccione
     * ASC o DESC para establecer el orden como ascendente o descendente, respectivamente.
     */
    public void newKeyField(Property property, SortOption sortOption) {
        KeyField keyField = new KeyField(this, property, sortOption);
        _keyFieldsList.add(keyField);
    }

    /**
     * El método newKeyField se utiliza para agregar propiedades a la clave. Las claves pueden estar integradas por una o más propiedades, y para cada
     * propiedad se puede establecer el orden en el índice correspondiente. La posición relativa de cada propiedad en el índice está determinado por
     * la secuencia en la que se agregan a la clave.
     *
     * @param property una o más propiedades de la entidad; el orden en el índice será ascendente.
     */
    public void newKeyField(Property... property) {
        Property[] properties = property;
        KeyField keyField;
        if (properties != null) {
            for (Property p : properties) {
                keyField = new KeyField(this, p);
                _keyFieldsList.add(keyField);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    /* commented on 21/03/2021
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            string += fee + tab + "fields" + faa + _keyFields.size() + foo;
            if (verbose) {
                string += fee + tab + "unique" + faa + _unique + foo;
            }
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            for (String clave : _keyFields.keySet()) {
                KeyField valor = _keyFields.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
        }
        return string;
    }
    /**/
    // </editor-fold>
//
    public boolean isValidKeyFor(PersistentEntity entity) {
        if (_containsNullProperties) {
            return false;
        }
        List<Property> columns = entity.getColumnsList();
        List<KeyField> fields = getKeyFieldsList();
        Property property;
        for (KeyField field : fields) {
            property = field.getProperty();
            if (property != null && !property.isCalculable() && columns.contains(property)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public List<String> invalidPropertyNames(PersistentEntity entity) {
        String prefix = entity.getName() + ".";
        List<String> invalidPropertyNames = new ArrayList<>();
        List<Property> invalidProperties = invalidProperties(entity);
        for (Property property : invalidProperties) {
            if (property == null) {
                invalidPropertyNames.add("null");
            } else {
                invalidPropertyNames.add(StringUtils.removeStart(property.getFullName(), prefix));
            }
        }
        return invalidPropertyNames;
    }

    public List<Property> invalidProperties(PersistentEntity entity) {
        List<Property> columns = entity.getColumnsList();
        List<KeyField> fields = getKeyFieldsList();
        List<Property> properties = new ArrayList<>();
        Property property;
        for (KeyField field : fields) {
            property = field.getProperty();
            if (property != null && !property.isCalculable() && columns.contains(property)) {
                continue;
            }
            properties.add(property);
        }
        return properties;
    }

    public List<String> dubiousPropertyNames(PersistentEntity entity) {
        String prefix = entity.getName() + ".";
        List<String> dubiousPropertyNames = new ArrayList<>();
        List<Property> dubiousProperties = dubiousProperties(entity);
        for (Property property : dubiousProperties) {
            if (property == null) {
                dubiousPropertyNames.add("null");
            } else {
                dubiousPropertyNames.add(StringUtils.removeStart(property.getFullName(), prefix));
            }
        }
        return dubiousPropertyNames;
    }

    public List<Property> dubiousProperties(PersistentEntity entity) {
        /*
        Oracle 18c Unique Constraints
        https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/constraint.html#GUID-1055EA97-BA6F-4764-A15F-1024FD5B6DFE
        Any row that contains nulls in all key columns satisfies the constraint; hence, any unique key made up of a single column can contain nulls.
        Two rows that contain nulls for one or more key columns and the same combination of values for the other key columns violate the constraint.
        /**/
        List<Property> columns = entity.getColumnsList();
        List<KeyField> fields = getKeyFieldsList();
        List<Property> properties = new ArrayList<>();
        Property property;
        for (KeyField field : fields) {
            property = field.getProperty();
            if (property != null && !property.isCalculable() && columns.contains(property)) {
                if (_unique && property.isNullable()) {
                    properties.add(property);
                }
            }
        }
        return properties;
    }

    public boolean isUniqueKeyWithNullProperties() {
        if (_unique) {
            for (KeyField field : getKeyFieldsList()) {
                if (field.getProperty() != null && field.getProperty().isNullable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSingleProperty() {
        /* commented on 21/03/2021
        return _keyFieldsLastKey != null && _keyFields.size() == 1;
        /**/
        return _keyFieldsList.size() == 1;
    }

    public Property getTheProperty() {
        /* commented on 21/03/2021
        return isSingleProperty() ? _keyFields.get(_keyFieldsLastKey).getProperty() : null;
        /**/
        return _keyFieldsList.size() == 1 ? _keyFieldsList.get(0).getProperty() : null;
    }

}
