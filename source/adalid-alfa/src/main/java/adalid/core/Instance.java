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
import adalid.core.annotations.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Instance extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(Instance.class);

    // <editor-fold defaultstate="collapsed" desc="until 21/03/2021">
    /* commented on 21/03/2021
    private static final String EOL = "\n";

    /**/
    // </editor-fold>
/**/
    private int _index;

    private InstanceTag _customTag;

    /**
     * @return la etiqueta personalizada de la instancia
     */
    public InstanceTag getCustomTag() {
        return _customTag;
    }

    /**
     * El método setCustomTag se utiliza para establecer la etiqueta personalizada de la instancia. La etiqueta determina el color del fondo, el color
     * del texto y el tipo de conversión del texto que se utiliza al mostrar el valor de la instancia en las vistas (páginas) de consulta y registro.
     *
     * @param tag un objeto de la clase InstanceTag. Utilice cualquiera de las etiquetas predefinidas como campos estáticos de InstanceTag o construya
     * su propia etiqueta con alguno de los métodos estáticos <code>InstanceTag.of</code>.
     */
    public void setCustomTag(InstanceTag tag) {
        _customTag = tag;
    }

    private final List<InstanceField> _instanceFieldsList = new ArrayList<>();

    // <editor-fold defaultstate="collapsed" desc="until 21/03/2021">
    /* commented on 21/03/2021
    private final Map<String, InstanceField> _instanceFields = new LinkedHashMap<>();

    private InstanceField[] _instanceField;

    /**/
    // </editor-fold>
/**/
    /**
     * @return the instance fields
     */
    public List<InstanceField> getInstanceFieldsList() {
        /**/
        // <editor-fold defaultstate="collapsed" desc="until 21/03/2021">
        /* commented on 21/03/2021
        List<InstanceField> list = new ArrayList<>();
        for (InstanceField value : _instanceFields.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
        // </editor-fold>
        /**/
        return _instanceFieldsList;
    }

    // <editor-fold defaultstate="collapsed" desc="until 21/03/2021">
    /**
     * @return the instance fields
     */
    /* commented on 21/03/2021
    public Map<String, InstanceField> getInstanceFieldsMap() {
        return _instanceFields;
    }

    /**/
    // </editor-fold>
//
    public Class<?> getInstanceKeyType() {
        /**/
        // <editor-fold defaultstate="collapsed" desc="until 01/04/2022">
        /* commented on 01/04/2022
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
        // </editor-fold>
        /**/
        InstanceField field = getInstanceKeyField();
        return field == null ? null : field.getProperty().getDataType();
    }

    public Object getInstanceKeyValue() {
        /**/
        // <editor-fold defaultstate="collapsed" desc="until 01/04/2022">
        /* commented on 01/04/2022
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
        // </editor-fold>
        /**/
        InstanceField field = getInstanceKeyField();
        return field == null ? null : field.getValue();
    }

    public Object getInstanceKeyLabel() {
        /**/
        // <editor-fold defaultstate="collapsed" desc="until 01/04/2022">
        /* commented on 01/04/2022
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
        // </editor-fold>
        /**/
        Entity declaringEntity = getDeclaringEntity();
        Property property = declaringEntity == null ? null : declaringEntity.getCharacterKeyProperty();
        return property == null ? null : getInstanceFieldValue(property.getName());
    }

    public Object getInstanceKeyDescription() {
        /**/
        // <editor-fold defaultstate="collapsed" desc="until 01/04/2022">
        /* commented on 01/04/2022
        Instance instanceAtRoot = getInstanceAtRoot();
        if (instanceAtRoot != null) {
            Entity declaringEntity = getDeclaringEntity();
            Property n = declaringEntity.getNameProperty();
            if (n != null) {
                Property p;
                for (InstanceField instanceField : instanceAtRoot.getInstanceFieldsList()) {
                    p = instanceField.getProperty();
                    if (n.equals(p)) {
                        return instanceField.getValue();
                    }
                }
            }
        }
        return null;
        // </editor-fold>
        /**/
        Entity declaringEntity = getDeclaringEntity();
        Property property = declaringEntity == null ? null : declaringEntity.getNameProperty();
        return declaringEntity == null ? null : property == null ? descriptionValueOf(declaringEntity) : getInstanceFieldValue(property.getName());
    }

    private Object descriptionValueOf(Entity declaringEntity) {
        Property property = declaringEntity.getDescriptionProperty();
        return property == null ? null : getInstanceFieldValue(property.getName());
    }

    public Object getInstanceFieldValue(String name) {
        return name == null ? null : getInstanceFieldValue(name, null);
    }

    public Object getInstanceFieldValue(String name, Locale locale) {
        Instance instanceAtRoot = name == null ? null : getInstanceAtRoot();
        if (instanceAtRoot != null) {
            Property property = instanceAtRoot.getDeclaringEntity().getProperty(name);
            if (property != null) {
                for (InstanceField field : instanceAtRoot.getInstanceFieldsList()) {
                    if (property.equals(field.getProperty())) {
                        return property instanceof StringProperty && locale != null ? field.getLocalizedValue(locale) : field.getValue();
                    }
                }
            }
        }
        return null;
    }

    private InstanceField getInstanceKeyField() {
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
                            return instanceField;
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean _usualArgumentInExpressions = false;

    public boolean isUsualArgumentInExpressions() {
        Instance instanceAtRoot = getInstanceAtRoot();
        return instanceAtRoot == null || instanceAtRoot == this ? _usualArgumentInExpressions : instanceAtRoot.isUsualArgumentInExpressions();
    }

    public void setUsualArgumentInExpressions(boolean usual) {
        _usualArgumentInExpressions = usual;
    }

    private Map<String, Class<?>> getKeysMap() {
        Map<String, Class<?>> map = new LinkedHashMap<>();
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity != null) {
            putKeyProperty(map, declaringEntity.getPrimaryKeyProperty());
            putKeyProperty(map, declaringEntity.getSequenceProperty());
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

    // <editor-fold defaultstate="collapsed" desc="until 21/03/2021">
    /* commented on 21/03/2021
    private Field getInstanceFieldField() {
        String errmsg = "failed to get field \"_instanceField\" declared at " + getClass();
        try {
            return getClass().getDeclaredField("_instanceField");
        } catch (NoSuchFieldException | SecurityException ex) {
            logger.error(errmsg, ThrowableUtils.getCause(ex));
            Project.increaseParserErrorCount();
        }
        return null;
    }

    /**/
    // </editor-fold>
/**/
    private Instance getInstanceAtRoot() {
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity != null) {
            if (declaringEntity.isRootInstance()) {
                return this;
            }
            String name = getName();
            if (name != null) {
                Entity root = declaringEntity.getRoot();
                List<Instance> list = root.getInstancesList();
                if (list.isEmpty()) {
                    return instanceAt(root);
                } else {
                    for (Instance instance : list) {
                        if (name.equals(instance.getName())) {
                            return instance;
                        }
                    }
                }
            }
        }
        return null;
    }

    private Instance instanceAt(Entity root) {
        Field field = XS1.getField(false, getName(), getName(), root.getClass(), Entity.class, new Class<?>[]{Instance.class});
        if (field == null) {
            return null;
        }
        try {
            Object obj = field.get(root);
            return obj instanceof Instance ? (Instance) obj : null;
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            return null;
        }
    }

    boolean finalise(int index) {
        boolean ok = super.finalise();
        if (ok) {
            _index = index;
            /**/
            // <editor-fold defaultstate="collapsed" desc="until 21/03/2021">
            /* commented on 21/03/2021
            finaliseFields();
            finaliseInstanceFieldArray();
            /**/
            // </editor-fold>
            /**/
            finaliseInstanceMissingFields();
        }
        return ok;
    }

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
        for (Field field : XS1.getFields(getClass(), Instance.class, InstanceField.class)) { // getClass().getDeclaredFields()
            field.setAccessible(true);
            logger.trace(field);
            name = field.getName();
            type = field.getType();
//          if (!InstanceField.class.isAssignableFrom(type)) {
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
                } else if (o instanceof InstanceField) {
                    finaliseInstanceField(field, (InstanceField) o);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                Project.increaseParserErrorCount();
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
    // <editor-fold defaultstate="collapsed" desc="finaliseInstanceFieldArray">
    /* commented on 21/03/2021
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
    /**/
    // </editor-fold>
/**/
    private void finaliseInstanceMissingFields() {
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity instanceof PersistentEntity) {
            if (depth() == 0 && round() == 0) {
                Class<?> dataType;
                boolean keyField;
                Object dpv;
                Boolean logico;
                Integer number;
                String string;
                Date date;
                Time time;
                Timestamp timestamp;
                PersistentEntity pent = (PersistentEntity) declaringEntity;
//              String name = WordUtils.capitalize(StrUtils.getWordyString(getName()));
                String name = StringUtils.capitalize(StrUtils.getWordyString(getName()));
//              String code = pent instanceof PersistentEnumerationEntity ? name : getName();
                String code = pent.getBusinessKeyValueOf(this);
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
                    dpv = pent.getDefaultPropertyValueOf(this, property);
                    if (Boolean.class.isAssignableFrom(dataType)) {
                        logico = dpv instanceof Boolean ? (Boolean) dpv : false;
                        InstanceField instanceField = new InstanceField(this, property, logico);
                        _instanceFieldsList.add(instanceField);
                    } else if (Number.class.isAssignableFrom(dataType)) {
                        number = keyField ? _index : dpv instanceof Integer ? (Integer) dpv : 0;
                        InstanceField instanceField = new InstanceField(this, property, number);
                        _instanceFieldsList.add(instanceField);
                    } else if (String.class.isAssignableFrom(dataType)) {
                        string = keyField ? code : dpv instanceof String ? (String) dpv : name;
                        InstanceField instanceField = new InstanceField(this, property, string);
                        _instanceFieldsList.add(instanceField);
                    } else if (Date.class.isAssignableFrom(dataType)) {
                        date = keyField ? TimeUtils.currentDate() : dpv instanceof Date ? (Date) dpv : currentDate;
                        InstanceField instanceField = new InstanceField(this, property, date);
                        _instanceFieldsList.add(instanceField);
                    } else if (Time.class.isAssignableFrom(dataType)) {
                        time = keyField ? TimeUtils.currentTime() : dpv instanceof Time ? (Time) dpv : currentTime;
                        InstanceField instanceField = new InstanceField(this, property, time);
                        _instanceFieldsList.add(instanceField);
                    } else if (Timestamp.class.isAssignableFrom(dataType)) {
                        timestamp = keyField ? TimeUtils.currentTimestamp() : dpv instanceof Timestamp ? (Timestamp) dpv : currentTimestamp;
                        InstanceField instanceField = new InstanceField(this, property, timestamp);
                        _instanceFieldsList.add(instanceField);
                    }
                }
                /**/
                // <editor-fold defaultstate="collapsed" desc="until 21/03/2021">
                /* commented on 21/03/2021
                if (!_instanceFieldsList.isEmpty()) {
                    finaliseInstanceFieldArray();
                }
                /**/
                // </editor-fold>
                /**/
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

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(PersistentEntityReference property, Instance value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, BigDecimal value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, BigInteger value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Boolean value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Byte value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Character value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Date value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Double value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Float value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Integer value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Long value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Short value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, String value) {
        Class<?> dataType = property.getDataType();
        if (java.util.Date.class.isAssignableFrom(dataType)) {
            java.util.Date valor = TimeUtils.jdbcObject(value, (Class<? extends java.util.Date>) dataType);
            if (valor instanceof Date) {
                newInstanceField(property, (Date) valor);
            } else if (valor instanceof Time) {
                newInstanceField(property, (Time) valor);
            } else if (valor instanceof Timestamp) {
                newInstanceField(property, (Timestamp) valor);
            } else {
                logger.error("invalid date/time value for field " + getFullName() + "." + property.getName());
                Project.increaseParserErrorCount();
            }
        } else {
            InstanceField instanceField = new InstanceField(this, property, value);
            _instanceFieldsList.add(instanceField);
        }
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(StringProperty property, String value) {
        newInstanceField(property, value, (Locale) null);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     * @param locale configuración regional
     */
    public void newInstanceField(StringProperty property, String value, Locale locale) {
        for (InstanceField field : _instanceFieldsList) {
            if (property.equals(field.getProperty())) {
                field.addLocalizedValue(locale, value);
                return;
            }
        }
        InstanceField instanceField = new InstanceField(this, property, value, locale);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Time value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    /**
     * El método newInstanceField se utiliza para especificar el valor de propiedades de la instancia.
     *
     * @param property propiedad de la entidad
     * @param value valor de la propiedad
     */
    public void newInstanceField(Property property, Timestamp value) {
        InstanceField instanceField = new InstanceField(this, property, value);
        _instanceFieldsList.add(instanceField);
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    /* commented on 21/03/2021
    @Override
    public String toString() {
        if (_instanceFields.isEmpty()) {
            return super.toString();
        }
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
    /**/
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="annotate">
    /**
     * annotated with InstanceDataGen
     */
    private boolean _annotatedWithInstanceDataGen;

    /**
     * data generation weight
     */
    private int _dataGenWeight = 1;

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

    /**
     * @return an entity expression representing this instance
     */
    public EntityScalarX toEntityExpression() {
        return XB.toEntity(this);
    }

}
