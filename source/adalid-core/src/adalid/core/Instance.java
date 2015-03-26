/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.StrUtils;
import adalid.commons.util.ThrowableUtils;
import adalid.commons.util.TimeUtils;
import adalid.core.annotations.InstanceDataGen;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.PersistentEntityReference;
import adalid.core.interfaces.PersistentEnumerationEntity;
import adalid.core.interfaces.Property;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Instance extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(Instance.class);

    private boolean _finalised;

    private int _index;

    private List<InstanceField> _instanceFieldsList = new ArrayList<>();

    private Map<String, InstanceField> _instanceFields = new LinkedHashMap<>();

    private InstanceField[] _instanceField;

    /**
     * @return the finalised indicator
     */
    public boolean isFinalised() {
        return _finalised;
    }

    /**
     * @return the instance fields
     */
    public List<InstanceField> getInstanceFieldsList() {
        List<InstanceField> list = new ArrayList<>();
        for (InstanceField value : _instanceFields.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the instance fields
     */
    public Map<String, InstanceField> getInstanceFieldsMap() {
        return _instanceFields;
    }

    public Class<?> getInstanceKeyType() {
        Instance instanceAtRoot = getInstanceAtRoot();
        if (instanceAtRoot != null) {
            Map<String, Class<?>> map = getKeysMap();
            Property p;
            Class<?> c;
            for (String s : map.keySet()) {
                c = map.get(s);
                for (InstanceField instanceField : instanceAtRoot.getInstanceFieldsList()) {
                    p = instanceField.getProperty();
                    if (s.equals(p.getName())) {
                        if (c.equals(p.getDataType())) {
                            return p.getDataType();
                        }
                    }
                }
            }
        }
        return null;
    }

    public Object getInstanceKeyValue() {
        Instance instanceAtRoot = getInstanceAtRoot();
        if (instanceAtRoot != null) {
            Map<String, Class<?>> map = getKeysMap();
            Property p;
            Class<?> c;
            for (String s : map.keySet()) {
                c = map.get(s);
                for (InstanceField instanceField : instanceAtRoot.getInstanceFieldsList()) {
                    p = instanceField.getProperty();
                    if (s.equals(p.getName())) {
                        if (c.equals(p.getDataType())) {
                            return instanceField.getValue();
                        }
                    }
                }
            }
        }
        return null;
    }

    public Object getInstanceKeyLabel() {
        Instance instanceAtRoot = getInstanceAtRoot();
        if (instanceAtRoot != null) {
            Entity declaringEntity = getDeclaringEntity();
            Property k = declaringEntity.getCharacterKeyProperty();
            if (k != null) {
                Property p;
                for (InstanceField instanceField : instanceAtRoot.getInstanceFieldsList()) {
                    p = instanceField.getProperty();
                    if (k.equals(p)) {
                        return instanceField.getValue();
                    }
                }
            }
        }
        return null;
    }

    private Map<String, Class<?>> getKeysMap() {
        Map<String, Class<?>> map = new LinkedHashMap<>();
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity != null) {
            putKeyProperty(map, declaringEntity.getPrimaryKeyProperty());
            putKeyProperty(map, declaringEntity.getNumericKeyProperty());
            putKeyProperty(map, declaringEntity.getCharacterKeyProperty());
        }
        return map;
    }

    private void putKeyProperty(Map<String, Class<?>> map, Property property) {
        if (property != null) {
            String name = property.getName();
            Class<?> clazz = property.getDataType();
            if (name != null && clazz != null) {
                map.put(name, clazz);
            }
        }
    }

    private Field getInstanceFieldField() {
        String errmsg = "failed to get field \"_instanceField\" declared at " + getClass();
        try {
            return getClass().getDeclaredField("_instanceField");
        } catch (NoSuchFieldException | SecurityException ex) {
            logger.error(errmsg, ThrowableUtils.getCause(ex));
            TLC.getProject().getParser().increaseErrorCount();
        }
        return null;
    }

    private Instance getInstanceAtRoot() {
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity != null) {
            if (declaringEntity.isRootInstance()) {
                return this;
            }
            String name = getName();
            if (name != null) {
                Entity root = declaringEntity.getRoot();
                for (Instance instance : root.getInstancesList()) {
                    if (name.equals(instance.getName())) {
                        return instance;
                    }
                }
            }
        }
        return null;
    }

    void finalise(int index) {
        if (_finalised) {
            return;
        }
        _finalised = true;
        _index = index;
        finaliseFields();
        finaliseInstanceFieldArray();
        finaliseInstanceMissingFields();
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
        for (Field field : XS1.getFields(getClass(), Instance.class)) { // getClass().getDeclaredFields()
            field.setAccessible(true);
            logger.trace(field);
            name = field.getName();
            type = field.getType();
            if (!InstanceField.class.isAssignableFrom(type)) {
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
                } else if (o instanceof InstanceField) {
                    finaliseInstanceField(field, (InstanceField) o);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                TLC.getProject().getParser().increaseErrorCount();
            }
        }
    }

    private void finaliseInstanceField(Field field, InstanceField instanceField) {
        if (field == null || instanceField == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _instanceFields.containsKey(key)) {
            return;
        }
        if (instanceField.isNotDeclared()) {
            instanceField.setDeclared(key, this, field);
        }
        _instanceFields.put(key, instanceField);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseInstanceFieldArray">
    private void finaliseInstanceFieldArray() {
        _instanceField = new InstanceField[_instanceFieldsList.size()];
        Field field = getInstanceFieldField();
        int i = 0;
        for (InstanceField rf : _instanceFieldsList) {
            _instanceField[i] = rf;
            finaliseInstanceField(field, _instanceField[i], i);
            i++;
        }
    }

    private void finaliseInstanceField(Field field, InstanceField instanceField, int index) {
        if (field == null || instanceField == null) {
            return;
        }
        String key = instanceField.getProperty().getName();
        if (key == null || _instanceFields.containsKey(key)) {
            return;
        }
        if (instanceField.isNotDeclared()) {
            instanceField.setDeclared(key, this, field, index);
        }
        _instanceFields.put(key, instanceField);
    }
    // </editor-fold>

    private void finaliseInstanceMissingFields() {
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity instanceof PersistentEntity) {
            if (depth() == 0 && round() == 0) {
                Class<?> dataType;
                boolean keyField;
                Integer number;
                String string;
                Date date;
                Time time;
                Timestamp timestamp;
                PersistentEntity pent = (PersistentEntity) declaringEntity;
                String name = WordUtils.capitalize(StrUtils.getWordyString(getName()));
                String code = pent instanceof PersistentEnumerationEntity ? name : getName();
                Date currentDate = TimeUtils.currentDate();
                Time currentTime = TimeUtils.currentTime();
                Timestamp currentTimestamp = TimeUtils.currentTimestamp();
                List<Property> columnsList = pent.getColumnsList();
                for (Property property : columnsList) {
                    if (property.isNullable() || property.getDefaultValue() != null || propertyAlreadyAdded(property)) {
                        continue;
                    }
                    dataType = property.getDataType();
                    keyField = property.isKeyField();
                    if (Boolean.class.isAssignableFrom(dataType)) {
                        InstanceField instanceField = new InstanceField(property, false);
                        _instanceFieldsList.add(instanceField);
                    } else if (Number.class.isAssignableFrom(dataType)) {
                        number = keyField ? _index : 0;
                        InstanceField instanceField = new InstanceField(property, number);
                        _instanceFieldsList.add(instanceField);
                    } else if (String.class.isAssignableFrom(dataType)) {
                        string = keyField ? code : name;
                        InstanceField instanceField = new InstanceField(property, string);
                        _instanceFieldsList.add(instanceField);
                    } else if (Date.class.isAssignableFrom(dataType)) {
                        date = keyField ? TimeUtils.currentDate() : currentDate;
                        InstanceField instanceField = new InstanceField(property, date);
                        _instanceFieldsList.add(instanceField);
                    } else if (Time.class.isAssignableFrom(dataType)) {
                        time = keyField ? TimeUtils.currentTime() : currentTime;
                        InstanceField instanceField = new InstanceField(property, time);
                        _instanceFieldsList.add(instanceField);
                    } else if (Timestamp.class.isAssignableFrom(dataType)) {
                        timestamp = keyField ? TimeUtils.currentTimestamp() : currentTimestamp;
                        InstanceField instanceField = new InstanceField(property, timestamp);
                        _instanceFieldsList.add(instanceField);
                    }
                }
                if (!_instanceFieldsList.isEmpty()) {
                    finaliseInstanceFieldArray();
                }
            }
        }
    }

    private boolean propertyAlreadyAdded(Property property) {
        for (InstanceField f : _instanceFieldsList) {
            if (f.getProperty().equals(property)) {
                return true;
            }
        }
        return false;
    }

    public void newInstanceField(PersistentEntityReference property, Instance value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, BigDecimal value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, BigInteger value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Boolean value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Byte value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Character value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Date value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Double value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Float value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Integer value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Long value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Short value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, String value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Time value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    public void newInstanceField(Property property, Timestamp value) {
        InstanceField instanceField = new InstanceField(property, value);
        _instanceFieldsList.add(instanceField);
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        String claves = "";
        for (String clave : _instanceFields.keySet()) {
            InstanceField field = _instanceFields.get(clave);
            claves += clave + '=' + field.getValue() + ", ";
        }
        return super.toString() + "{" + StringUtils.removeEnd(claves, ", ") + "}";
    }

    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            string += fee + tab + "fields" + faa + _instanceFields.size() + foo;
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            for (String clave : _instanceFields.keySet()) {
                InstanceField valor = _instanceFields.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
        }
        return string;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="annotate">
    /**
     * annotated with InstanceDataGen
     */
    private boolean _annotatedWithInstanceDataGen;

    /**
     * data generation weight
     */
    private int _dataGenWeight;

    /**
     * @return the InstanceDataGen annotation indicator
     */
    public boolean isAnnotatedWithInstanceDataGen() {
        return _annotatedWithInstanceDataGen;
    }

    /**
     * @return the data generation weight
     */
    public int getDataGenWeight() {
        return _dataGenWeight;
    }

    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithInstanceDataGen = false;
        _dataGenWeight = 1;
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            annotateInstanceDataGen(field);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(InstanceDataGen.class);
        return valid;
    }

    private void annotateInstanceDataGen(Field field) {
        Class<? extends Annotation> annotationClass = InstanceDataGen.class;
        _annotatedWithInstanceDataGen = field.isAnnotationPresent(annotationClass);
        if (_annotatedWithInstanceDataGen) {
            InstanceDataGen annotation = field.getAnnotation(InstanceDataGen.class);
            _dataGenWeight = Math.min(100, Math.max(0, annotation.weight()));
        }
    }
    // </editor-fold>

    private String message(Class<?> type, String name, Object value, int depth, int round) {
        String s1 = StringUtils.repeat(" ", 0 + 4 * depth);
        String s2 = this + "," + depth + "," + round;
        String s3 = type.getSimpleName() + " " + name + "=" + value;
        String s4 = s1 + s2 + " " + s3;
        return s4;
    }

}
