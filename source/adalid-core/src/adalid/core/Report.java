/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.StrUtils;
import adalid.core.enums.ViewFieldAggregation;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class Report extends AbstractArtifact implements Comparable<Report> {

    private static final int COLUMN_WIDTH = 960;

    private static final String EOL = "\n";

    private PersistentEntity _entity;

    private View _view;

    private int _interFieldGapWidth = 6;

    private final Map<ViewField, ReportGroup> _groupsMap = new LinkedHashMap<>();

    private final List<ReportGroup> _groups = new ArrayList<>();

    private ReportGroup _detailGroup;

    private ReportGroup _lastControlGroup;

    private boolean _cntAtHand;

    private boolean _minAtHand;

    private boolean _maxAtHand;

    private boolean _sumAtHand;

    private boolean _avgAtHand;

    private boolean _devAtHand;

    private final Set<ViewFieldAggregation> _aggregations = new LinkedHashSet<>();

    private Report() {
        super();
    }

    public Report(String name) {
        super();
        if (name != null) {
            setDeclared(name);
        }
    }

    public Report(PersistentEntity entity) {
        super();
        if (entity != null) {
            init(entity);
        }
    }

    private void init(PersistentEntity entity) {
        _entity = entity;
        _detailGroup = ReportGroup.addReportGroup(this);
        List<ReportField> fields = _detailGroup.getFields();
        if (fields != null && fields.size() > 0) {
            resize(_detailGroup);
        }
        Collections.sort(_groups);
        setDeclared(entity.getName());
    }

    public Report(View view) {
        super();
        if (view != null) {
            init(view);
        }
    }

    private void init(View view) {
        _view = view;
        _entity = view.getDeclaringPersistentEntity();
        /**/
        int seq = 0;
        for (ViewField field : _view.getViewControlFields()) {
            _lastControlGroup = ReportGroup.addReportGroup(this, field, ++seq);
        }
        if (_lastControlGroup != null) {
            _lastControlGroup.setLastControl(true);
        }
        _detailGroup = ReportGroup.addReportGroup(this, view);
        ViewField groupField;
        ReportGroup reportGroup;
        ReportField reportField;
        for (ViewField viewField : _view.getViewFields()) {
            groupField = viewField.getGroupField();
            if (groupField == null) {
                reportField = ReportField.addReportField(_detailGroup, viewField);
                setReportProperties(reportField);
            } else {
                reportGroup = _groupsMap.get(groupField);
                if (reportGroup != null) {
                    reportField = ReportField.addReportField(reportGroup, viewField);
                    if (viewField.isControlField()) {
                        reportGroup.setField(reportField);
                    }
                }
            }
        }
        addAggregates();
        /**/
        Collections.sort(_groups);
        setDeclared(_entity.getName() + "_" + view.getName());
    }

    /**
     * @return the entity
     */
    public PersistentEntity getEntity() {
        return _entity;
    }

    /**
     * @return the view
     */
    public View getView() {
        return _view;
    }

    public String getTitle() {
        return StrUtils.getStringJava(_view.getName().toUpperCase());
    }

    /**
     * @return the inter field gap width
     */
    public int getInterFieldGapWidth() {
        return _interFieldGapWidth;
    }

    /**
     * @param interFieldGapWidth the inter field gap width to set
     */
    void setInterFieldGapWidth(int interFieldGapWidth) {
        _interFieldGapWidth = interFieldGapWidth;
    }

    /**
     * @return the groups map
     */
    public Map<ViewField, ReportGroup> getGroupsMap() {
        return _groupsMap;
    }

    /**
     * @return the groups list
     */
    @SuppressWarnings("unchecked")
    public List<ReportGroup> getGroups() {
        return _groups;
    }

    /**
     * @return the detail group
     */
    public ReportGroup getDetailGroup() {
        return _detailGroup;
    }

    /**
     * @return the last control group
     */
    public ReportGroup getLastControlGroup() {
        return _lastControlGroup;
    }

    public boolean isCountAtHand() {
        return _cntAtHand;
    }

    public boolean isMinAtHand() {
        return _minAtHand;
    }

    public boolean isMaxAtHand() {
        return _maxAtHand;
    }

    public boolean isSumAtHand() {
        return _sumAtHand;
    }

    public boolean isAverageAtHand() {
        return _avgAtHand;
    }

    public boolean isDeviationAtHand() {
        return _devAtHand;
    }

    public void addAggregates() {
        if (_sumAtHand) {
            _aggregations.add(ViewFieldAggregation.SUM);
        }
        if (_cntAtHand) {
            _aggregations.add(ViewFieldAggregation.COUNT);
        }
        if (_avgAtHand) {
            _aggregations.add(ViewFieldAggregation.AVERAGE);
        }
        if (_devAtHand) {
            _aggregations.add(ViewFieldAggregation.DEVIATION);
        }
        if (_minAtHand) {
            _aggregations.add(ViewFieldAggregation.MINIMUM);
        }
        if (_maxAtHand) {
            _aggregations.add(ViewFieldAggregation.MAXIMUM);
        }
    }

    private void setReportProperties(ReportField field) {
        _cntAtHand |= field.isCountAtHand();
        _minAtHand |= field.isMinAtHand();
        _maxAtHand |= field.isMaxAtHand();
        _sumAtHand |= field.isSumAtHand();
        _avgAtHand |= field.isAverageAtHand();
        _devAtHand |= field.isDeviationAtHand();
    }

    public Set<ViewFieldAggregation> getAggregations() {
        return _aggregations;
    }

    public String getAggregateFunctionName(String aggregation) {
        ViewFieldAggregation tipo = ViewFieldAggregation.valueOf(aggregation);
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        String funcion = sqlProgrammer.getSqlFunctionName(tipo);
        return funcion;
    }

    private void resize(ReportGroup group) {
        int used = 0;
        int size = 0;
        int width;
        ReportField last = null;
        for (ReportField field : group.getFields()) {
            width = field.getPixels();
            if (width > 0 && used > 0) {
                width += _interFieldGapWidth;
            }
            if (field.getPixels() > 0 && width + used <= COLUMN_WIDTH) {
                used += width;
                if (field.isResizeable()) {
                    last = field;
                    size += field.getPixels();
                }
            }
        }
        int free = COLUMN_WIDTH - used;
        if (last != null && free > 0) {
            int extra;
            for (ReportField field : group.getFields()) {
                if (field.getPixels() > 0 && used < COLUMN_WIDTH) {
                    if (field == last) {
                        extra = COLUMN_WIDTH - used;
                    } else if (field.isResizeable()) {
                        extra = field.getPixels() * free / size;
                    } else {
                        extra = 0;
                    }
                    if (extra > field.getPixels()) {
                        extra = field.getPixels();
                    }
                    used += extra;
                    field.setPixels(field.getPixels() + extra);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(Report that) {
        if (that != null) {
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
                if (_view != null) {
                    string += fee + tab + "view" + faa + _view + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
