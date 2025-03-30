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

import adalid.core.interfaces.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Campins
 */
public class Step extends AbstractArtifact {

    /* commented on 21/03/2021
    private static final Logger logger = Logger.getLogger(Step.class);

    private static final String EOL = "\n";

    /**/
    private final List<StepField> _stepFieldsList = new ArrayList<>();

    private void stepFieldsList_add(StepField field) { // since 14/01/2025
        appendField(_stepFieldsList, field);
    }

    /* commented on 21/03/2021
    private final Map<String, StepField> _stepFields = new LinkedHashMap<>();

    private StepField[] _stepField;

    /**/
    private int _sequenceNumber;
//
//  private BooleanExpression _renderingFilter;

    /**
     * @return the step fields
     */
    List<StepField> stepFieldsList() {
        return _stepFieldsList;
    }

    /**
     * @return the step fields
     */
    public List<StepField> getStepFieldsList() {
        /* commented on 21/03/2021
        List<StepField> list = new ArrayList<>();
        for (StepField value : _stepFields.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
        /**/
        return _stepFieldsList;
    }

    /**
     * @return the step fields
     */
    /* commented on 21/03/2021
    public Map<String, StepField> getStepFieldsMap() {
        return _stepFields;
    }

    /**/
    // <editor-fold defaultstate="collapsed" desc="renderingFilter">
    /**
     * @return the rendering filter
     */
    /*
    public BooleanExpression getRenderingFilter() {
        return _renderingFilter;
    }

    /**/
    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de grupos de propiedades en pasos (steps), en vistas
     * (páginas) de registro de la entidad. Solo para las instancias de la entidad que cumplen con los criterios del filtro, el paso será visible.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro
     */
    /*
    public void setRenderingFilter(BooleanExpression renderingFilter) {
        String message = "failed to set rendering filter of " + getFullName();
        if (renderingFilter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (renderingFilter instanceof BooleanPrimitive) {
            _renderingFilter = renderingFilter.isTrue();
        } else {
            _renderingFilter = renderingFilter;
        }
    }
    /**/
    // </editor-fold>
/**/
 /* commented on 21/03/2021
    private Field getStepFieldField() {
        String errmsg = "failed to get field \"_stepField\" declared at " + getClass();
        try {
            return getClass().getDeclaredField("_stepField");
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
            finaliseStepFieldArray();
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
        for (Field field : XS1.getFields(getClass(), Step.class, StepField.class)) { // getClass().getDeclaredFields()
            field.setAccessible(true);
            logger.trace(field);
            name = field.getName();
            type = field.getType();
//          if (!StepField.class.isAssignableFrom(type)) {
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
                } else if (o instanceof StepField) {
                    finaliseStepField(field, (StepField) o);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                Project.increaseParserErrorCount();
            }
        }
    }

    private void finaliseStepField(Field field, StepField stepField) {
        if (field == null || stepField == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _stepFields.containsKey(key)) {
            return;
        }
        if (stepField.isNotDeclared()) {
            stepField.setDeclared(key, this, field);
        }
        _stepFields.put(key, stepField);
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
    // <editor-fold defaultstate="collapsed" desc="finaliseStepFieldArray">
    /* commented on 21/03/2021
    private void finaliseStepFieldArray() {
        _stepField = new StepField[_stepFieldsList.size()];
        Field field = getStepFieldField();
        int i = 0;
        for (StepField rf : _stepFieldsList) {
            _stepField[i] = rf;
            finaliseStepField(field, _stepField[i], i);
            i++;
        }
    }

    private void finaliseStepField(Field field, StepField stepField, int index) {
        if (field == null || stepField == null) {
            return;
        }
        Property sfp = stepField.getProperty();
        EntityCollection sfc = stepField.getEntityCollection();
        String key = sfp != null ? sfp.getName() : sfc != null ? sfc.getName() : null;
        if (key == null || _stepFields.containsKey(key)) {
            return;
        }
        if (stepField.isNotDeclared()) {
            stepField.setDeclared(key, this, field, index);
        }
        _stepFields.put(key, stepField);
    }
    /**/
    // </editor-fold>
/**/
    /**
     * El método newStepField se utiliza para agregar colecciones a un grupo. Los grupos generan pasos (steps) en las vistas (páginas) de registro
     * detallado de la entidad. Los grupos pueden estar integrados por una o más colecciones. Una misma colección no puede estar en más de un grupo.
     *
     * @param collection colección de la entidad
     */
    public void newStepField(EntityCollection collection) {
        collection.setStep(this);
        StepField stepField = new StepField(this, collection);
        stepFieldsList_add(stepField);
    }

    /**
     * El método newStepField se utiliza para agregar propiedades a un grupo. Los grupos generan pasos (steps) en las vistas (páginas) de registro
     * detallado de la entidad. Los grupos pueden estar integrados por una o más propiedades. Una misma propiedad no puede estar en más de un grupo.
     *
     * @param property propiedad de la entidad
     */
    public void newStepField(Property property) {
        StepField stepField = new StepField(this, property);
        stepFieldsList_add(stepField);
    }

    /**
     * El método newStepField se utiliza para agregar propiedades a un grupo. Los grupos generan pasos (steps) en las vistas (páginas) de registro
     * detallado de la entidad. Los grupos pueden estar integrados por una o más propiedades. Una misma propiedad no puede estar en más de un grupo.
     *
     * @param property una o más propiedades de la entidad
     */
    public void newStepField(Property... property) {
        Property[] properties = property;
        StepField stepField;
        if (properties != null) {
            for (Property p : properties) {
                stepField = new StepField(this, p);
                stepFieldsList_add(stepField);
            }
        }
    }

    /**
     * @return the step sequence number
     */
    public int getSequenceNumber() {
        return _sequenceNumber * 1000 + getFieldIndex();
    }

    int sequenceNumber() {
        return _sequenceNumber;
    }

    /**
     * El método setSequenceNumber se utiliza para establecer el número de secuencia o posición relativa en la que se muestra el paso (step)
     * correspondiente al grupo de propiedades, en las vistas (páginas) de registro detallado de la entidad. El valor predeterminado del atributo es
     * 0. Si todos los grupos tienen el mismo número de secuencia (0 o cualquier otro), entonces las vistas muestran los pasos en el orden en el que
     * los grupos están definidos en la entidad.
     *
     * @param sequenceNumber número de secuencia o posición relativa en la que se muestra el paso. Su valor debe ser un número entero entre 0 y
     * 1.000.000. Especificar un valor menor que 0 equivale a especificar 0. Especificar un valor mayor que 1.000.000 equivale a especificar
     * 1.000.000.
     */
    public void setSequenceNumber(int sequenceNumber) {
        _sequenceNumber = sequenceNumber < 0 ? 0 : sequenceNumber > 1000000 ? 1000000 : sequenceNumber;
    }

    public int getFieldIndex() {
        Entity declaringEntityRoot = getDeclaringEntityRoot();
        List<Step> steps = declaringEntityRoot == null ? null : declaringEntityRoot.getStepsList();
        return steps == null || steps.isEmpty() ? 0 : steps.indexOf(this) + 1;
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
            string += fee + tab + "fields" + faa + _stepFields.size() + foo;
            if (verbose) {
                if (_renderingFilter != null) {
                    string += _renderingFilter.toString(n + 1, "renderingFilter", verbose, fields, maps) + foo;
                }
            }
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            for (String clave : _stepFields.keySet()) {
                StepField valor = _stepFields.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
        }
        return string;
    }
    /**/
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="isValidStepFor">
    /*
    public boolean isValidStepFor(PersistentEntity entity) {
        List<Property> columns = entity.getColumnsList();
        List<StepField> fields = getStepFieldsList();
        boolean valid, found;
        valid = false;
        for (StepField field : fields) {
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

    /**/
    // </editor-fold>
/**/
}
