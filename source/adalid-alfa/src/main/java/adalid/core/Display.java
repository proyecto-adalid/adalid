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
import adalid.core.predicates.*;
import adalid.core.primitives.*;
import adalid.core.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class Display extends AbstractArtifact implements Comparable<Display> {

    private static final Logger logger = Logger.getLogger(Display.class);

    private static final String EOL = "\n";

    public Display(String name) {
        super();
        _displayType = DisplayType.UNSPECIFIED;
        _displayMode = DisplayMode.UNSPECIFIED;
        _displayFormat = DisplayFormat.UNSPECIFIED;
        init(name);
    }

    private void init(String name) {
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

    private List<Display> _collaterals;

    /*
    private List<Display> _ancestors;

    private List<UIComponent> _components;

    /**/
    private String _helpDocument;

    private String _helpFileName;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    public boolean isMenuOptionEnabled() {
        return _reference == null || _reference.isMasterDetailViewMenuOptionEnabled(this);
    }

    /**
     * @return the help file name
     */
    public String getHelpDocument() {
        if (StringUtils.isNotBlank(_helpDocument)) {
            return _helpDocument;
        }
        if (_entity != null) {
            String helpDocument = _entity.getHelpDocument();
            switch (_displayFormat) {
                case TABLE:
                    helpDocument = StringUtils.defaultIfBlank(_entity.getTableViewHelpDocument(), helpDocument);
                    break;
                case DETAIL:
                    helpDocument = StringUtils.defaultIfBlank(_entity.getDetailViewHelpDocument(), helpDocument);
                    break;
                case TREE:
                    helpDocument = StringUtils.defaultIfBlank(_entity.getTreeViewHelpDocument(), helpDocument);
                    break;
                case CONSOLE:
                    helpDocument = StringUtils.defaultIfBlank(_entity.getConsoleViewHelpDocument(), helpDocument);
                    break;
            }
            if (StringUtils.isNotBlank(helpDocument)) {
                return helpDocument;
            }
        }
        if (_module != null) {
            String helpDocument = _module.getHelpDocument();
            if (StringUtils.isNotBlank(helpDocument)) {
                return helpDocument;
            }
        }
        return TLC.getProject().getHelpDocument();
    }

    public void setHelpDocument(String document) {
        if (StringUtils.isBlank(document)) {
            _helpDocument = "";
        } else if (isValidEmbeddedDocument(document)) {
            _helpDocument = document;
        } else {
            logger.error(getName() + " help document is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the help file name
     */
    public String getHelpFileName() {
        if (StringUtils.isNotBlank(_helpFileName)) {
            return _helpFileName;
        }
        HelpFileAutoName helpFileAutoName = helpFileAutoName();
        String helpFileAutoType = helpFileAutoType();
        String helpFileAutoBase = helpFileAutoName.name().toLowerCase() + "/";
        if (HelpFileAutoName.DISPLAY.equals(helpFileAutoName)) {
            return helpFileAutoBase + getName() + "." + helpFileAutoType;
        }
        if (_entity != null) {
            String helpFileName = _entity.getHelpFileName();
            switch (_displayFormat) {
                case TABLE:
                    helpFileName = StringUtils.defaultIfBlank(_entity.getTableViewHelpFileName(), helpFileName);
                    break;
                case DETAIL:
                    helpFileName = StringUtils.defaultIfBlank(_entity.getDetailViewHelpFileName(), helpFileName);
                    break;
                case TREE:
                    helpFileName = StringUtils.defaultIfBlank(_entity.getTreeViewHelpFileName(), helpFileName);
                    break;
                case CONSOLE:
                    helpFileName = StringUtils.defaultIfBlank(_entity.getConsoleViewHelpFileName(), helpFileName);
                    break;
            }
            if (StringUtils.isNotBlank(helpFileName)) {
                return helpFileName;
            }
            if (HelpFileAutoName.ENTITY.equals(helpFileAutoName)) {
                return helpFileAutoBase + _entity.getName() + "." + helpFileAutoType;
            }
        }
        if (_module != null) {
            String helpFileName = _module.getHelpFileName();
            if (StringUtils.isNotBlank(helpFileName)) {
                return helpFileName;
            }
        }
        return TLC.getProject().getHelpFileName();
    }

    public void setHelpFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            _helpFileName = "";
        } else if (isValidHelpFileName(fileName)) {
            if (isValidHelpFileType(fileName)) {
                _helpFileName = fileName;
            } else {
                logger.error(getName() + " help file type is missing or invalid; valid types are: " + Project.getHelpFileTypesCSV());
                Project.increaseParserErrorCount();
            }
        } else {
            logger.error(getName() + " help file name is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    private HelpFileAutoName helpFileAutoName() {
        if (_entity != null) {
            HelpFileAutoName helpFileAutoName = _entity.getHelpFileAutoName();
            if (helpFileAutoName != null && !helpFileAutoName.equals(HelpFileAutoName.NONE)) {
                return helpFileAutoName;
            }
        }
        if (_module != null) {
            HelpFileAutoName helpFileAutoName = _module.getHelpFileAutoName();
            if (helpFileAutoName != null && !helpFileAutoName.equals(HelpFileAutoName.NONE)) {
                return helpFileAutoName;
            }
        }
        return TLC.getProject().getHelpFileAutoName();
    }

    private String helpFileAutoType() {
        if (_entity != null) {
            HelpFileAutoName helpFileAutoName = _entity.getHelpFileAutoName();
            if (helpFileAutoName != null && !helpFileAutoName.equals(HelpFileAutoName.NONE)) {
                return _entity.getHelpFileAutoType();
            }
        }
        if (_module != null) {
            HelpFileAutoName helpFileAutoName = _module.getHelpFileAutoName();
            if (helpFileAutoName != null && !helpFileAutoName.equals(HelpFileAutoName.NONE)) {
                return _module.getHelpFileAutoType();
            }
        }
        return TLC.getProject().getHelpFileAutoType();
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
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de vistas (páginas) de registro y consulta. Solo si se
     * cumplen los criterios del filtro, la vista (página) será presentada.
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

    /**
     * @return the siblings list
     */
    public List<Display> getSiblings() {
        Project module = linkOuterSiblings() ? TLC.getProject() : _module;
        if (_siblings == null) {
            _siblings = new ArrayList<>();
            if (module != null) {
                List<? extends Display> displaysList = module.getDisplaysList();
                Collections.sort(displaysList);
                for (Display display : displaysList) {
                    if (equals(display) || unequals(_displayMode, display.getDisplayMode())) {
                        continue;
                    }
                    if (equals(_entity, display.getEntity())) {
                        if (_reference == display.getReference() || equals(_reference, display.getReference())) { // nulls are equals
                            _siblings.add(display);
                        }
                    }
                }
            }
        }
        return _siblings;
    }

    private boolean linkOuterSiblings() {
        return _entity != null && _entity.isLinkOuterSiblings();
    }

    /**
     * @return the children list
     */
    public List<Display> getChildren() {
        Project module = linkOuterChildren() ? TLC.getProject() : _module;
        if (_children == null) {
            _children = new ArrayList<>();
            if (module != null && _entity != null) {
                List<? extends Display> displaysList = module.getDisplaysList();
                Collections.sort(displaysList);
                for (Display display : displaysList) {
                    if (equals(display) || unequals(_displayMode, display.getDisplayMode())) {
                        continue;
                    }
                    if (isMyChild(display)) {
                        _children.add(display);
                    }
                }
            }
        }
        return _children;
    }

    private boolean linkOuterChildren() {
        return _entity != null && _entity.isLinkOuterChildren();
    }

    private boolean isMyChild(Display display) {
        Entity master = display.getMaster();
        if (master == null) {
            return false;
        }
        return master.equals(_entity) || master.getClass().isAssignableFrom(_entity.getClass());
    }

    /**
     * @return the collaterals list
     */
    public List<Display> getCollaterals() {
        Project module = linkOuterCollaterals() ? TLC.getProject() : _module;
        if (_collaterals == null) {
            _collaterals = new ArrayList<>();
            if (module != null && _entity != null && _master != null && _reference != null) {
                List<? extends Display> displaysList = module.getDisplaysList();
                Collections.sort(displaysList);
                for (Display display : displaysList) {
                    if (equals(display) || unequals(_displayMode, display.getDisplayMode())) {
                        continue;
                    }
                    if (isMyCollateral(display)) {
                        _collaterals.add(display);
                    }
                }
            }
        }
        return _collaterals;
    }

    private boolean linkOuterCollaterals() {
        return _entity != null && _entity.isLinkOuterCollaterals();
    }

    private boolean isMyCollateral(Display display) {
        Entity entity = display.getEntity();
        Entity master = display.getMaster();
        EntityReference reference = display.getReference();
        if (entity == null || master == null || reference == null) {
            return false;
        }
        if (master.equals(_master)) {
            return !reference.equals(_reference);
        }
        Class<? extends Entity> masterClass = _master.getClass();
        Class<? extends Entity> claseMaestro = master.getClass();
        return masterClass.isAssignableFrom(claseMaestro) || claseMaestro.isAssignableFrom(masterClass);
    }

    /**
     * @return the fields list
     */
    public abstract List<? extends DisplayField> getFields();

    /**
     * @return the master heading fields list
     */
    public abstract List<? extends DisplayField> getMasterHeadingFields();

    private final ByPropertyDisplaySortKey byPropertyDisplaySortKey = new ByPropertyDisplaySortKey();

    class ByPropertyDisplaySortKey implements Comparator<DisplayField> {

        @Override
        public int compare(DisplayField o1, DisplayField o2) {
            if (o1 != null && o2 != null) {
                Property p1 = o1.getProperty();
                Property p2 = o2.getProperty();
                if (p1 != null && p2 != null) {
                    return p1.getDisplaySortKey().compareTo(p2.getDisplaySortKey());
                }
            }
            return 0;
        }

    }

    private final ByStepFieldIndex byStepFieldIndex = new ByStepFieldIndex();

    class ByStepFieldIndex implements Comparator<DisplayField> {

        @Override
        public int compare(DisplayField o1, DisplayField o2) {
            if (o1 != null && o2 != null) {
                return o1.getStepFieldIndex() - o2.getStepFieldIndex();
            }
            return 0;
        }

    }

    class ByTabFieldIndex implements Comparator<DisplayField> {

        private final Tab _tab;

        public ByTabFieldIndex(Tab tab) {
            _tab = tab;
        }

        @Override
        public int compare(DisplayField o1, DisplayField o2) {
            if (o1 != null && o2 != null) {
                return o1.getTabFieldIndex(_tab) - o2.getTabFieldIndex(_tab);
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
        Collections.sort(fields, byPropertyDisplaySortKey);
        return fields;
    }

    public List<? extends DisplayField> getRootFields(Step step) {
        List<DisplayField> fields = new ArrayList<>();
        for (DisplayField rootField : getRootFields()) {
            if (step.equals(rootField.getStep())) {
                fields.add(rootField);
            }
        }
        Collections.sort(fields, byStepFieldIndex);
        return fields;
    }

    public List<? extends DisplayField> getRootFields(Tab tab) {
        List<DisplayField> fields = new ArrayList<>();
        for (DisplayField rootField : getRootFields()) {
            if (rootField.isInTab(tab)) {
                fields.add(rootField);
            }
        }
        Collections.sort(fields, new ByTabFieldIndex(tab));
        return fields;
    }

    public List<? extends DisplayField> getRootMasterHeadingFields() {
        List<? extends DisplayField> fields = new ArrayList<>(getMasterHeadingFields());
        CollectionUtils.filter(fields, new IsDisplayRootField());
        Collections.sort(fields, byPropertyDisplaySortKey);
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

    public List<Entity> getOverlayEntitiesList() {
        if (_entity != null) {
            if (DisplayFormat.TREE.equals(_displayFormat)) {
                if (_entity.isOverlayableEntity()) {
                    List<Entity> list = new ArrayList<>();
                    list.add(_entity);
                    return list;
                }
            } else {
                Map<String, Entity> map = _entity.getOverlayEntitiesMap();
                List<? extends DisplayField> rootMasterHeadingFields = getRootMasterHeadingFields();
                if (_master != null && rootMasterHeadingFields != null) {
                    Property property;
                    Entity entity;
                    for (DisplayField field : rootMasterHeadingFields) {
                        property = field.getProperty();
                        if (property != null && property.isHeadingField() && property.isOverlayableEntityReference()) {
                            entity = (Entity) property;
                            map.put(entity.getRoot().getName(), entity);
                        }
                    }
                }
                return new ArrayList<>(map.values());
            }
        }
        return new ArrayList<>();
    }
    // </editor-fold>

    private boolean unequals(Object a, Object b) {
        return !equals(a, b);
    }

    private boolean equals(Object a, Object b) {
        return a != null && a.equals(b);
    }

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
