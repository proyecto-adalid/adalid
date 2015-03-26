/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.ThrowableUtils;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Property;
import adalid.core.primitives.BooleanPrimitive;
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
public class Tab extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(Tab.class);

    private boolean _finalised;

    private List<TabField> _tabFieldsList = new ArrayList<>();

    private Map<String, TabField> _tabFields = new LinkedHashMap<>();

    private TabField[] _tabField;

    private BooleanExpression _renderingFilter;

    /**
     * @return the finalised indicator
     */
    public boolean isFinalised() {
        return _finalised;
    }

    /**
     * @return the tab fields
     */
    public List<TabField> getTabFieldsList() {
        List<TabField> list = new ArrayList<>();
        for (TabField value : _tabFields.values()) {
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * @return the tab fields
     */
    public Map<String, TabField> getTabFieldsMap() {
        return _tabFields;
    }

    /**
     * @return the rendering filter
     */
    public BooleanExpression getRenderingFilter() {
        return _renderingFilter;
    }

    /**
     * @param renderingFilter the rendering filter to set
     */
    public void setRenderingFilter(BooleanExpression renderingFilter) {
        _renderingFilter = renderingFilter instanceof BooleanPrimitive ? renderingFilter.isTrue() : renderingFilter;
    }

    private Field getTabFieldField() {
        String errmsg = "failed to get field \"_tabField\" declared at " + getClass();
        try {
            return getClass().getDeclaredField("_tabField");
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
        finaliseTabFieldArray();
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
        for (Field field : XS1.getFields(getClass(), Tab.class)) { // getClass().getDeclaredFields()
            field.setAccessible(true);
            logger.trace(field);
            name = field.getName();
            type = field.getType();
            if (!TabField.class.isAssignableFrom(type)) {
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
                } else if (o instanceof TabField) {
                    finaliseTabField(field, (TabField) o);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error(errmsg, ThrowableUtils.getCause(ex));
                TLC.getProject().getParser().increaseErrorCount();
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="finaliseTabFieldArray">
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
        String key = tabField.getProperty().getName();
        if (key == null || _tabFields.containsKey(key)) {
            return;
        }
        if (tabField.isNotDeclared()) {
            tabField.setDeclared(key, this, field, index);
        }
        _tabFields.put(key, tabField);
    }
    // </editor-fold>

    public void newTabField(Property property) {
        TabField tabField = new TabField(property);
        _tabFieldsList.add(tabField);
    }

    public void newTabField(Property... property) {
        Property[] properties = property;
        TabField tabField;
        if (properties != null) {
            for (int i = 0; i < properties.length; i++) {
                tabField = new TabField(properties[i]);
                _tabFieldsList.add(tabField);
            }
        }
    }

    /**
     * @return the tab sequence number
     */
    public int getSequenceNumber() {
        Entity declaringEntityRoot = getDeclaringEntityRoot();
        List<Tab> tabs = declaringEntityRoot == null ? null : declaringEntityRoot.getTabsList();
        return tabs == null || tabs.isEmpty() ? 0 : tabs.indexOf(this) + 1;
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isValidTabFor">
//  public boolean isValidTabFor(PersistentEntity entity) {
//      List<Property> columns = entity.getColumnsList();
//      List<TabField> fields = getTabFieldsList();
//      boolean valid, found;
//      valid = false;
//      for (TabField field : fields) {
//          found = false;
//          for (Property column : columns) {
//              if (field.getProperty().equals(column)) {
//                  found = true;
//                  break;
//              }
//          }
//          if (found) {
//              valid = true;
//          } else {
//              valid = false;
//              break;
//          }
//      }
//      return valid;
//  }
    // </editor-fold>
//
    private String message(Class<?> type, String name, Object value, int depth, int round) {
        String s1 = StringUtils.repeat(" ", 0 + 4 * depth);
        String s2 = this + "," + depth + "," + round;
        String s3 = type.getSimpleName() + " " + name + "=" + value;
        String s4 = s1 + s2 + " " + s3;
        return s4;
    }

}
