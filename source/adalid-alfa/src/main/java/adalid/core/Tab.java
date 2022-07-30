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
import adalid.core.primitives.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Tab extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(Tab.class);

    /* commented on 21/03/2021
    private static final String EOL = "\n";

    /**/
    private final List<TabField> _tabFieldsList = new ArrayList<>();

    /* commented on 21/03/2021
    private final Map<String, TabField> _tabFields = new LinkedHashMap<>();

    private TabField[] _tabField;

    /**/
    private int _sequenceNumber;

    private BooleanExpression _renderingFilter;

    /**
     * @return the tab fields
     */
    public List<TabField> getTabFieldsList() {
        /* commented on 21/03/2021
        List<TabField> list = new ArrayList<>();
        for (TabField value : _tabFields.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
        /**/
        return _tabFieldsList;
    }

    /**
     * @return the tab fields
     */
    /* commented on 21/03/2021
    public Map<String, TabField> getTabFieldsMap() {
        return _tabFields;
    }

    /**/
    /**
     * @return the rendering filter
     */
    public BooleanExpression getRenderingFilter() {
        return _renderingFilter;
    }

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de grupos de propiedades en pestañas (tabs), en vistas
     * (páginas) de consulta y registro de la entidad. Solo para las instancias de la entidad que cumplen con los criterios del filtro, la pestaña
     * será visible.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro
     */
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

    /* commented on 21/03/2021
    private Field getTabFieldField() {
        String errmsg = "failed to get field \"_tabField\" declared at " + getClass();
        try {
            return getClass().getDeclaredField("_tabField");
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
            finaliseTabFieldArray();
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
        for (Field field : XS1.getFields(getClass(), Tab.class, TabField.class)) { // getClass().getDeclaredFields()
            field.setAccessible(true);
            logger.trace(field);
            name = field.getName();
            type = field.getType();
//          if (!TabField.class.isAssignableFrom(type)) {
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
                } else if (o instanceof TabField) {
                    finaliseTabField(field, (TabField) o);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                Project.increaseParserErrorCount();
            }
        }
    }

    private void finaliseTabField(Field field, TabField tabField) {
        if (field == null || tabField == null) {
            return;
        }
        String key = field.getName();
        if (key == null || _tabFields.containsKey(key)) {
            return;
        }
        if (tabField.isNotDeclared()) {
            tabField.setDeclared(key, this, field);
        }
        _tabFields.put(key, tabField);
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
    // <editor-fold defaultstate="collapsed" desc="finaliseTabFieldArray">
    /* commented on 21/03/2021
    private void finaliseTabFieldArray() {
        _tabField = new TabField[_tabFieldsList.size()];
        Field field = getTabFieldField();
        int i = 0;
        for (TabField rf : _tabFieldsList) {
            _tabField[i] = rf;
            finaliseTabField(field, _tabField[i], i);
            i++;
        }
    }

    private void finaliseTabField(Field field, TabField tabField, int index) {
        if (field == null || tabField == null) {
            return;
        }
        Property tfp = tabField.getProperty();
        EntityCollection tfc = tabField.getEntityCollection();
        String key = tfp != null ? tfp.getName() : tfc != null ? tfc.getName() : null;
        if (key == null || _tabFields.containsKey(key)) {
            return;
        }
        if (tabField.isNotDeclared()) {
            tabField.setDeclared(key, this, field, index);
        }
        _tabFields.put(key, tabField);
    }
    /**/
    // </editor-fold>
/**/
    /**
     * El método newTabField se utiliza para agregar colecciones a un grupo. Los grupos generan pestañas (tabs) en las vistas (páginas) de consulta y
     * registro detallado de la entidad. Los grupos pueden estar integrados por una o más colecciones. Una misma colección puede estar en más de un
     * grupo.
     *
     * @param collection colección de la entidad
     */
    public void newTabField(EntityCollection collection) {
        String message = "failed to add collection " + collection.getFullName() + " to tab " + getName();
        Tab tab = collection.getTab();
        if (tab == null) {
            collection.setTab(this);
            TabField tabField = new TabField(this, collection);
            _tabFieldsList.add(tabField);
        } else {
            message += "; collection previously added to tab " + tab.getName();
            logger.error(message);
            Project.increaseParserErrorCount();
        }
    }

    /**
     * El método newTabField se utiliza para agregar propiedades a un grupo. Los grupos generan pestañas (tabs) en las vistas (páginas) de consulta y
     * registro detallado de la entidad. Los grupos pueden estar integrados por una o más propiedades. Una misma propiedad puede estar en más de un
     * grupo.
     *
     * @param property propiedad de la entidad
     */
    public void newTabField(Property property) {
        TabField tabField = new TabField(this, property);
        _tabFieldsList.add(tabField);
    }

    /**
     * El método newTabField se utiliza para agregar propiedades a un grupo. Los grupos generan pestañas (tabs) en las vistas (páginas) de consulta y
     * registro detallado de la entidad. Los grupos pueden estar integrados por una o más propiedades. Una misma propiedad puede estar en más de un
     * grupo.
     *
     * @param property una o más propiedades de la entidad
     */
    public void newTabField(Property... property) {
        Property[] properties = property;
        TabField tabField;
        if (properties != null) {
            for (Property p : properties) {
                tabField = new TabField(this, p);
                _tabFieldsList.add(tabField);
            }
        }
    }

    /**
     * @return the tab sequence number
     */
    public int getSequenceNumber() {
        return _sequenceNumber * 1000 + getFieldIndex();
    }

    /**
     * El método setSequenceNumber se utiliza para establecer el número de secuencia o posición relativa en la que se muestra la pestaña (tab)
     * correspondiente al grupo de propiedades, en las vistas (páginas) de consulta y registro detallado de la entidad. El valor predeterminado del
     * atributo es 0. Si todos los grupos tienen el mismo número de secuencia (0 o cualquier otro), entonces las vistas muestran las pestañas en el
     * orden en el que los grupos están definidos en la entidad.
     *
     * @param sequenceNumber número de secuencia o posición relativa en la que se muestra la pestaña. Su valor debe ser un número entero entre 0 y
     * 1.000.000. Especificar un valor menor que 0 equivale a especificar 0. Especificar un valor mayor que 1.000.000 equivale a especificar
     * 1.000.000.
     */
    public void setSequenceNumber(int sequenceNumber) {
        _sequenceNumber = sequenceNumber < 0 ? 0 : sequenceNumber > 1000000 ? 1000000 : sequenceNumber;
    }

    public int getFieldIndex() {
        Entity declaringEntityRoot = getDeclaringEntityRoot();
        List<Tab> tabs = declaringEntityRoot == null ? null : declaringEntityRoot.getTabsList();
        return tabs == null || tabs.isEmpty() ? 0 : tabs.indexOf(this) + 1;
    }

    /**
     * El método copy produce una copia profunda de un paso. Las copias profundas duplican tanto como sea posible, incluidos los campos del paso.
     *
     * @param step el paso a copiar
     */
    public void copy(Step step) {
        copy(step, true);
    }

    /**
     * El método copy copia los atributos de un paso. La copia puede ser profunda o superficial, según el valor del parámetro deep.
     *
     * @param step el paso a copiar
     * @param deep true para una copia profunda, false para una copia superficial. Las copias profundas duplican tanto como sea posible, incluidos los
     * campos del paso. Las copias superficiales duplican lo menos posible, excluyendo los campos del paso.
     */
    public void copy(Step step, boolean deep) {
        copyLocalizedStrings(step);
        if (deep) {
            List<StepField> fields = step.stepFieldsList(); // must not use the public method here
            for (StepField field : fields) {
                Property sfp = field.getProperty();
                EntityCollection sfc = field.getEntityCollection();
                if (sfp != null) {
                    newTabField(sfp);
                } else if (sfc != null) {
                    newTabField(sfc);
                }
            }
        }
        _sequenceNumber = step.sequenceNumber(); // must not use the public method here
//      _renderingFilter = step.getRenderingFilter();
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
            string += fee + tab + "fields" + faa + _tabFields.size() + foo;
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
            for (String clave : _tabFields.keySet()) {
                TabField valor = _tabFields.get(clave);
                string += valor.toString(n + 1, clave, verbose, fields, maps);
            }
        }
        return string;
    }
    /**/
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="isValidTabFor">
    /*
    public boolean isValidTabFor(PersistentEntity entity) {
        List<Property> columns = entity.getColumnsList();
        List<TabField> fields = getTabFieldsList();
        boolean valid, found;
        valid = false;
        for (TabField field : fields) {
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
