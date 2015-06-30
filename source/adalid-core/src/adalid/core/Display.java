/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.DisplayFormat;
import adalid.core.enums.DisplayMode;
import adalid.core.enums.DisplayType;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityReference;
import adalid.core.interfaces.Property;
import adalid.core.predicates.IsDisplayJoinField;
import adalid.core.predicates.IsDisplayRootField;
import adalid.core.primitives.BooleanPrimitive;
import adalid.core.sql.QueryTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class Display extends AbstractArtifact implements Comparable<Display> {

    public Display(String name) {
        _displayType = DisplayType.UNSPECIFIED;
        _displayMode = DisplayMode.UNSPECIFIED;
        _displayFormat = DisplayFormat.UNSPECIFIED;
        setDeclared(name);
    }

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    private Project _module;

    private DisplaySet _set;

    private Entity _entity;

    private EntityReference _reference;

    private Entity _master;

    private QueryTable _queryTable;

    private QueryTable _masterQueryTable;

    private DisplayType _displayType;

    private DisplayMode _displayMode;

    private DisplayFormat _displayFormat;

    private BooleanExpression _renderingFilter;

    private List<Display> _siblings;

    private List<Display> _children;
//
//  private List<Display> _ancestors;

    List<? extends DisplayField> _rootFields;

    List<? extends DisplayField> _rootMasterHeadingFields;

    List<? extends DisplayField> _joinFields;
//
//  private List<UIComponent> _components;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return the help file name
     */
    public String getHelpFileName() {
        String helpFileName = null;
        if (_entity != null) {
            helpFileName = _entity.getHelpFileName();
        }
        if (StringUtils.isBlank(helpFileName) && _module != null) {
            helpFileName = _module.getHelpFileName();
        }
        if (StringUtils.isBlank(helpFileName)) {
            helpFileName = TLC.getProject().getHelpFileName();
        }
        return helpFileName;
    }

    /**
     * @return the module
     */
    public Project getModule() {
        return _module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(Project module) {
        _module = module;
    }

    /**
     * @return the display set
     */
    public DisplaySet getDisplaySet() {
        return _set;
    }

    /**
     * @param set the display set to set
     */
    public void setDisplaySet(DisplaySet set) {
        _set = set;
    }

    /**
     * @return the entity
     */
    public Entity getEntity() {
        return _entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Entity entity) {
        _entity = entity;
    }

    /**
     * @return the entity reference
     */
    public EntityReference getReference() {
        return _reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(EntityReference reference) {
        _reference = reference;
    }

    /**
     * @return the master
     */
    public Entity getMaster() {
        return _master;
    }

    /**
     * @param master the master to set
     */
    public void setMaster(Entity master) {
        _master = master;
    }

    /**
     * @return the query table
     */
    public QueryTable getQueryTable() {
        return _queryTable;
    }

    /**
     * @param queryTable the query table to set
     */
    public void setQueryTable(QueryTable queryTable) {
        _queryTable = queryTable;
    }

    /**
     * @return the master query table
     */
    public QueryTable getMasterQueryTable() {
        return _masterQueryTable;
    }

    /**
     * @param queryTable the master query table to set
     */
    public void setMasterQueryTable(QueryTable queryTable) {
        _masterQueryTable = queryTable;
    }

    /**
     * @return the display type
     */
    public DisplayType getDisplayType() {
        return _displayType;
    }

    /**
     * @param displayType the display type to set
     */
    void setDisplayType(DisplayType displayType) {
        _displayType = displayType;
    }

    /**
     * @return the display mode
     */
    public DisplayMode getDisplayMode() {
        return _displayMode;
    }

    /**
     * @param displayMode the display mode to set
     */
    public void setDisplayMode(DisplayMode displayMode) {
        _displayMode = displayMode;
    }

    /**
     * @return the display format
     */
    public DisplayFormat getDisplayFormat() {
        return _displayFormat;
    }

    /**
     * @param displayFormat the display format to set
     */
    public void setDisplayFormat(DisplayFormat displayFormat) {
        _displayFormat = displayFormat;
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

    /**
     * @return the siblings list
     */
    public List<Display> getSiblings() {
        if (_siblings == null) {
            _siblings = new ArrayList<>();
            if (_module != null) {
                List<? extends Display> displaysList = _module.getDisplaysList();
                Collections.sort(displaysList);
                for (Display d : displaysList) {
                    if (!d.equals(this) && _entity != null && _entity.equals(d.getEntity())) {
                        if (_reference == null && d.getReference() == null) {
                            _siblings.add(d);
                        } else if (_reference != null && _reference.equals(d.getReference())) {
                            _siblings.add(d);
                        }
                    }
                }
            }
        }
        return _siblings;
    }

    /**
     * @return the children list
     */
    public List<Display> getChildren() {
        if (_children == null) {
            _children = new ArrayList<>();
            if (_module != null && _entity != null) {
                List<? extends Display> displaysList = _module.getDisplaysList();
                Collections.sort(displaysList);
                for (Display display : displaysList) {
                    if (isMyChild(display)) {
                        _children.add(display);
                    }
                }
            }
        }
        return _children;
    }

    private boolean isMyChild(Display display) {
        if (display.equals(this)) {
            return false;
        }
        Entity master = display.getMaster();
        if (master == null) {
            return false;
        }
        return master.equals(_entity) || master.getClass().isAssignableFrom(_entity.getClass());
    }
//
//  /**
//   * @return the ancestors list
//   */
//  public List<Display> getAncestors() {
//      if (_ancestors == null) {
//          _ancestors = new ArrayList<>();
//          if (_module != null && _entity != null && _reference != null && _master != null
//              && _displayMode != null && _displayMode != DisplayMode.PROCESSING && _displayMode != DisplayMode.UNSPECIFIED) {
//              List<? extends Display> displays = _module.getDisplaysList();
//              for (Display display : displays) {
//                  if (_displayMode == display.getDisplayMode() && _master.equals(display.getEntity())) {
//                      _ancestors.add(display);
//                  }
//              }
//          }
//      }
//      return _ancestors;
//  }

    /**
     * @return the fields list
     */
    public abstract List<? extends DisplayField> getFields();

    /**
     * @return the master heading fields list
     */
    public abstract List<? extends DisplayField> getMasterHeadingFields();

    private final ByPropertySequenceNumber byPropertySequenceNumber = new ByPropertySequenceNumber();

    class ByPropertySequenceNumber implements Comparator<DisplayField> {

        @Override
        public int compare(DisplayField o1, DisplayField o2) {
            if (o1 != null && o2 != null) {
                Property p1 = o1.getProperty();
                Property p2 = o2.getProperty();
                if (p1 != null && p2 != null) {
                    return Integer.compare(p1.getSequenceNumber(), p2.getSequenceNumber());
                }
            }
            return 0;
        }

    }

    public List<String> getFieldNames() {
        List<String> names = new ArrayList<>();
        List<? extends DisplayField> fields = getFields();
        for (DisplayField field : fields) {
            names.add(field.getName());
        }
        return names;
    }

    public List<? extends DisplayField> getRootFields() {
        List<? extends DisplayField> fields = new ArrayList<>(getFields());
        CollectionUtils.filter(fields, new IsDisplayRootField());
        Collections.sort(fields, byPropertySequenceNumber);
        return fields;
    }

    public List<? extends DisplayField> getRootMasterHeadingFields() {
        List<? extends DisplayField> fields = new ArrayList<>(getMasterHeadingFields());
        CollectionUtils.filter(fields, new IsDisplayRootField());
        Collections.sort(fields, byPropertySequenceNumber);
        return fields;
    }

    public List<String> getRootFieldNames() {
        List<String> names = new ArrayList<>();
        List<? extends DisplayField> fields = getRootFields();
        for (DisplayField field : fields) {
            names.add(field.getName());
        }
        return names;
    }

    public List<? extends DisplayField> getJoinFields() {
        List<? extends DisplayField> fields = new ArrayList<>(getFields());
        CollectionUtils.filter(fields, new IsDisplayJoinField());
        return fields;
    }

    public List<String> getJoinFieldNames() {
        List<String> names = new ArrayList<>();
        List<? extends DisplayField> fields = getJoinFields();
        for (DisplayField field : fields) {
            names.add(field.getName());
        }
        return names;
    }
//
    // <editor-fold defaultstate="collapsed" desc="components getters and setters">
//  /**
//   * @return the ui components list
//   */
//  public List<UIComponent> getComponents() {
//      if (_components == null) {
//          _components = new ArrayList<UIComponent>();
//          List<? extends DisplayField> fields = getFields();
//          if (fields == null || fields.isEmpty()) {
//              return _components;
//          }
//          UIComponent component;
//          for (DisplayField field : fields) {
//              component = field.getComponent();
//              if (component != null) {
//                  _components.add(component);
//              }
//          }
//      }
//      return _components;
//  }
//
//  public List<UIComponent> getRootFieldComponents() {
//      List<UIComponent> fields = new ArrayList<UIComponent>(getComponents());
//      CollectionUtils.filter(fields, new IsDisplayRootFieldComponent());
//      return fields;
//  }
//
//  public List<UIComponent> getJoinFieldComponents() {
//      List<UIComponent> fields = new ArrayList<UIComponent>(getComponents());
//      CollectionUtils.filter(fields, new IsDisplayJoinFieldComponent());
//      return fields;
//  }
    // </editor-fold>
//
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(Display o) {
        Display that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                if (_entity != null) {
                    string += fee + tab + "entity" + faa + _entity + foo;
                }
                if (_reference != null) {
                    string += fee + tab + "_reference" + faa + _reference + foo;
                }
                if (_master != null) {
                    string += fee + tab + "_master" + faa + _master + foo;
                }
                string += fee + tab + "displayType" + faa + _displayType + foo;
                string += fee + tab + "displayMode" + faa + _displayMode + foo;
                string += fee + tab + "displayFormat" + faa + _displayFormat + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
