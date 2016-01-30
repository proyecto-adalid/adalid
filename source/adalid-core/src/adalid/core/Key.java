/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.ThrowableUtils;
import adalid.core.enums.SortOption;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.Property;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Key extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(Key.class);

    private static final String EOL = "\n";

    private boolean _finalised;

    private final List<KeyField> _keyFieldsList = new ArrayList<>();

    private final Map<String, KeyField> _keyFields = new LinkedHashMap<>();

    private String _keyFieldsLastKey;

    private KeyField[] _keyField;

    private boolean _unique;

    /**
     * @return the finalised indicator
     */
    public boolean isFinalised() {
        return _finalised;
    }

    /**
     * @return the key fields
     */
    public List<KeyField> getKeyFieldsList() {
        List<KeyField> list = new ArrayList<>();
        for (KeyField value : _keyFields.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the key fields
     */
    public Map<String, KeyField> getKeyFieldsMap() {
        return _keyFields;
    }

    /**
     * @return the unique indicator
     */
    public boolean isUnique() {
        return _unique;
    }

    /**
     * @param unique the unique indicator to set
     */
    public void setUnique(boolean unique) {
        _unique = unique;
    }

    private Field getKeyFieldField() {
        String errmsg = "failed to get field \"_keyField\" declared at " + getClass();
        try {
            return getClass().getDeclaredField("_keyField");
        } catch (NoSuchFieldException | SecurityException ex) {
            logger.error(errmsg, ThrowableUtils.getCause(ex));
            TLC.getProject().getParser().increaseErrorCount();
        }
        return null;
    }

    void finalise() {
        if (_finalised) {
            return;
        }
        _finalised = true;
        finaliseFields();
        finaliseKeyFieldArray();
    }

    // <editor-fold defaultstate="collapsed" desc="finaliseFields">
    private void finaliseFields() {
        String name;
        Class<?> type;
        int modifiers;
        boolean restricted;
        Object o;
        int depth = depth();
        int round = round();
        for (Field field : XS1.getFields(getClass(), Key.class)) { // getClass().getDeclaredFields()
            field.setAccessible(true);
            logger.trace(field);
            name = field.getName();
            type = field.getType();
            if (!KeyField.class.isAssignableFrom(type)) {
                continue;
            }
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
                TLC.getProject().getParser().increaseErrorCount();
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseKeyFieldArray">
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
        String key = keyField.getProperty().getName();
        if (key == null || _keyFields.containsKey(key)) {
            return;
        }
        if (keyField.isNotDeclared()) {
            keyField.setDeclared(key, this, field, index);
        }
        _keyFields.put(key, keyField);
        _keyFieldsLastKey = key;
    }
    // </editor-fold>

    public void newKeyField(Property property) {
        KeyField keyField = new KeyField(property);
        _keyFieldsList.add(keyField);
    }

    public void newKeyField(Property property, SortOption sortOption) {
        KeyField keyField = new KeyField(property, sortOption);
        _keyFieldsList.add(keyField);
    }

    public void newKeyField(Property... property) {
        Property[] properties = property;
        KeyField keyField;
        if (properties != null) {
            for (Property p : properties) {
                keyField = new KeyField(p);
                _keyFieldsList.add(keyField);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
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
    // </editor-fold>

    public boolean isValidKeyFor(PersistentEntity entity) {
        List<Property> columns = entity.getColumnsList();
        List<KeyField> fields = getKeyFieldsList();
        boolean valid, found;
        valid = false;
        for (KeyField field : fields) {
            found = false;
            for (Property column : columns) {
                if (field.getProperty().equals(column)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                valid = true;
            } else {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public boolean isSingleProperty() {
        return _keyFieldsLastKey != null && _keyFields.size() == 1;
    }

    public Property getTheProperty() {
        return isSingleProperty() ? _keyFields.get(_keyFieldsLastKey).getProperty() : null;
    }

    private String message(Class<?> type, String name, Object value, int depth, int round) {
        String s1 = StringUtils.repeat(" ", 0 + 4 * depth);
        String s2 = this + "," + depth + "," + round;
        String s3 = type.getSimpleName() + " " + name + "=" + value;
        String s4 = s1 + s2 + " " + s3;
        return s4;
    }

}
