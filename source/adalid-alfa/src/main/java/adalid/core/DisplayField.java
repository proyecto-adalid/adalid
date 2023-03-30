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

import adalid.commons.bundles.*;
import adalid.core.interfaces.*;
import adalid.core.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class DisplayField extends AbstractArtifact implements Comparable<DisplayField> {

    private static final String EOL = "\n";

    public DisplayField(Display display, EntityCollection collection) {
        _display = display;
        _dataArtifact = null;
        _entityCollection = collection;
        _parent = null;
        String name = collection.getName() + "EntityCollection";
        init(name);
    }

    public DisplayField(Display display, Property property) {
        this(display, display.getQueryTable(), property, null);
    }

    public DisplayField(Display display, QueryTable queryTable, Property property, DisplayField parentField) {
        super();
        _display = display;
        _dataArtifact = property;
        _entityCollection = null;
        _parent = parentField;
        String name = name(queryTable, property);
        init(name);
    }

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    private final Display _display;

    private final DataArtifact _dataArtifact;

    private final EntityCollection _entityCollection;

    private final DisplayField _parent;

    private Step _step;

    private int _stepFieldIndex;

    private final Map<Tab, Integer> _tabs = new LinkedHashMap<>();

    private boolean _foreignCode;

    private boolean _foreignName;

    private List<DisplayField> _children;
//
//  private UIComponent _component;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return the display
     */
    public Display getDisplay() {
        return _display;
    }

    /**
     * @return the linked artifact
     */
    public Artifact getArtifact() {
        return _dataArtifact == null ? _entityCollection : _dataArtifact;
    }

    /**
     * @return the linked data artifact
     */
    public DataArtifact getDataArtifact() {
        return _dataArtifact;
    }

    /**
     * @return true if data artifact is not null
     */
    public boolean isDataArtifact() {
        return _dataArtifact != null;
    }

    /**
     * @return the linked parameter
     */
    public Parameter getParameter() {
        return _dataArtifact instanceof Parameter ? (Parameter) _dataArtifact : null;
    }

    /**
     * @return true if data artifact is a parameter
     */
    public boolean isParameter() {
        return getParameter() != null;
    }

    /**
     * @return the linked property
     */
    public Property getProperty() {
        return _dataArtifact instanceof Property ? (Property) _dataArtifact : null;
    }

    /**
     * @return true if data artifact is a property
     */
    public boolean isProperty() {
        return getProperty() != null;
    }

    /**
     * @return the entity collection
     */
    public EntityCollection getEntityCollection() {
        return _entityCollection;
    }

    /**
     * @return true if entity collection is not null
     */
    public boolean isEntityCollection() {
        return _entityCollection != null;
    }

    /**
     * @return the parent field
     */
    public DisplayField getParent() {
        return _parent;
    }

    /**
     * @return the step
     */
    public Step getStep() {
        return _step;
    }

    /**
     * @return the step field index
     */
    public int getStepFieldIndex() {
        return _stepFieldIndex;
    }

    /**
     * @return the tab
     */
    public Map<Tab, Integer> getTabs() {
        return _tabs;
    }

    /**
     * @param tab the tab to search
     * @return true if the tab contains this field
     */
    public boolean isInTab(Tab tab) {
        return tab != null && _tabs.containsKey(tab);
    }

    /**
     * @param tab the tab to search
     * @return the tab field index
     */
    public int getTabFieldIndex(Tab tab) {
        Integer index = tab == null ? null : _tabs.get(tab);
        return index == null ? 0 : index;
    }

    /**
     * @return the foreign code indicator
     */
    public boolean isForeignCode() {
        return _foreignCode;
    }

    /**
     * @param indicator the foreign code indicator to set
     */
    public void setForeignCode(boolean indicator) {
        _foreignCode = indicator;
    }

    public DisplayField getForeignCodeField() {
        if (_children != null && !_children.isEmpty()) {
            for (DisplayField child : _children) {
                if (child.isForeignCode()) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * @return the foreign name indicator
     */
    public boolean isForeignName() {
        return _foreignName;
    }

    /**
     * @param indicator the foreign name indicator to set
     */
    public void setForeignName(boolean indicator) {
        _foreignName = indicator;
    }

    public DisplayField getForeignNameField() {
        if (_children != null && !_children.isEmpty()) {
            for (DisplayField child : _children) {
                if (child.isForeignName()) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * @return the children
     */
    public List<DisplayField> getChildren() {
        return _children;
    }
//
//  /**
//   * @return the UI component
//   */
//  public UIComponent getComponent() {
//      return _component;
//  }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="initialization">
    private String name(QueryTable queryTable, Property property) {
        String name = property.getName();
        if (queryTable != null) {
            String sqlAlias = queryTable.getSqlAlias(property);
            return sqlAlias == null ? name : sqlAlias;
        }
        return name;
    }

    private String alias(String name) {
        String prefix = string("prefix");
        String suffix = string("suffix");
        return prefix + name + suffix;
    }

    private String string(String keyword) {
        String uic = getClass().getSimpleName();
        String key = uic + ".name." + keyword;
        return Bundle.getTrimmedToEmptyString(key);
    }

    private void init(String name) {
        setDeclared(name, _display);
        String alias = alias(name);
        setAlias(alias);
        _foreignCode = false;
        _foreignName = false;
        _children = new ArrayList<>();
        if (_parent != null) {
            _parent.getChildren().add(this);
        }
        Entity entity = _display.getEntity();
        List<Step> stepsList = entity.getStepsList();
        if (!stepsList.isEmpty()) {
            DataArtifact dataArtifact = _parent == null ? _dataArtifact : _parent.getDataArtifact();
            if (_entityCollection != null || (dataArtifact != null && dataArtifact.isProperty())) {
                outerloop:
                    for (Step step : stepsList) {
                        int index = 0;
                        for (StepField stepField : step.getStepFieldsList()) {
                            Property sfp = stepField.getProperty();
                            EntityCollection sfc = stepField.getEntityCollection();
                            if (sfp != null) {
                                if (sfp == dataArtifact) {
                                    _step = step;
                                    _stepFieldIndex = index;
                                    break outerloop;
                                }
                            } else if (sfc != null) {
                                if (sfc == _entityCollection) {
                                    _step = step;
                                    _stepFieldIndex = index;
                                    break outerloop;
                                }
                            }
                            index++;
                        }
                    }
            }
        }
        List<Tab> tabsList = entity.getTabsList();
        if (!tabsList.isEmpty()) {
            DataArtifact dataArtifact = _parent == null ? _dataArtifact : _parent.getDataArtifact();
            if (_entityCollection != null || (dataArtifact != null && dataArtifact.isProperty())) {
                for (Tab tab : tabsList) {
                    int index = 0;
                    for (TabField tabField : tab.getTabFieldsList()) {
                        Property tfp = tabField.getProperty();
                        EntityCollection tfc = tabField.getEntityCollection();
                        if (tfp != null) {
                            if (tfp == dataArtifact) {
                                _tabs.put(tab, index);
                            }
                        } else if (tfc != null) {
                            if (tfc == _entityCollection) {
                                _tabs.put(tab, index);
                            }
                        }
                        index++;
                    }
                }
            }
        }
//      initUIComponent();
    }
//
    // <editor-fold defaultstate="collapsed" desc="initUIComponent">
//  private void initUIComponent() {
//      if (_display != null && _dataArtifact != null) {
//          if (_dataArtifact instanceof EntityReference) {
//              EntityReference reference = (EntityReference) _dataArtifact;
//              SearchType searchType = reference.getSearchType();
//              if (SearchType.LIST.equals(searchType)) {
//                  _component = new DropDown(this);
//              } else {
//                  _component = new TextField(this);
//              }
//          } else if (_dataArtifact instanceof BooleanPrimitive) {
//              _component = new DropDown(this);
//          } else if (_dataArtifact instanceof CharacterPrimitive) {
//              if (_dataArtifact instanceof StringData) {
//                  StringData data = (StringData) _dataArtifact;
//                  Integer maxLength = data.getMaxLength();
//                  if (maxLength == null || maxLength > 100) {
//                      _component = new TextArea(this);
//                  } else {
//                      _component = new TextField(this);
//                  }
//              } else {
//                  _component = new TextField(this);
//              }
//          } else if (_dataArtifact instanceof NumericPrimitive) {
//              _component = new TextField(this);
//          } else if (_dataArtifact instanceof TemporalPrimitive) {
//              if (_dataArtifact instanceof DateData) {
//                  DisplayFormat format = _display.getDisplayFormat();
//                  if (DisplayFormat.CONSOLE.equals(format) || DisplayFormat.DETAIL.equals(format)) {
//                      _component = new Calendar(this);
//                  } else {
//                      _component = new TextField(this);
//                  }
//              } else {
//                  _component = new TextField(this);
//              }
//          }
//      }
//  }
//
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(DisplayField o) {
        DisplayField that;
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
                string += fee + tab + "display" + faa + _display + foo;
                string += fee + tab + "dataArtifact" + faa + _dataArtifact + foo;
                if (_parent != null) {
                    string += fee + tab + "parent" + faa + _parent + foo;
                    string += fee + tab + "foreignCode" + faa + _foreignCode + foo;
                    string += fee + tab + "foreignName" + faa + _foreignName + foo;
                }
            }

        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
//      String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            if (_children != null && !_children.isEmpty()) {
                string += fee + tab + DisplayField.class.getSimpleName() + "[] children {" + foo;
                for (DisplayField field : _children) {
                    string += field.toString(n + 2, null, verbose, fields, maps);
                }
                string += fee + tab + "}";
            }
        }
        return string;
    }
    // </editor-fold>

}
