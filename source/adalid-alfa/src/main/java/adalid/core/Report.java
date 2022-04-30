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
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.page.format.*;
import adalid.core.programmers.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Report extends AbstractArtifact implements Comparable<Report> {

    private static final Logger logger = Logger.getLogger(Report.class);

    private static final String EOL = "\n";

    private static final PageFormat defaultPageFormat = new DefaultPageFormat();

    private PersistentEntity _entity;

    private View _view;

    private PageFormat _pageFormat = defaultPageFormat;

    private int _interFieldGapWidth = 6;

    private final Map<ViewField, ReportGroup> _groupsMap = new LinkedHashMap<>();

    private final List<ReportGroup> _groups = new ArrayList<>();

    private ReportGroup _detailGroup;

    private ReportGroup _lastControlGroup;

    private ReportGroup _penultimateControlGroup;

    private boolean _cntAtHand;

    private boolean _minAtHand;

    private boolean _maxAtHand;

    private boolean _sumAtHand;

    private boolean _avgAtHand;

    private boolean _devAtHand;

    private final Set<ViewFieldAggregation> _aggregations = new LinkedHashSet<>();

    /*
    private Report() {
        super();
    }

    /**/
    public Report(String name) {
        super();
        if (name != null) {
            declare(name);
        }
    }

    private void declare(String name) {
        setDeclared(name);
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
        _pageFormat = view.getPageFormat();
        _entity = view.getDeclaringPersistentEntity();
        /**/
        int seq = 0;
        for (ViewField field : _view.getViewControlFields()) {
            _penultimateControlGroup = _lastControlGroup;
            _lastControlGroup = ReportGroup.addReportGroup(this, field, ++seq);
        }
        if (_lastControlGroup != null) {
            _lastControlGroup.setLastControl(true);
        }
        if (_penultimateControlGroup != null) {
            _penultimateControlGroup.setPenultimateControl(true);
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
        /**/
        verifyViewFields();
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

    public PageFormat getPageFormat() {
        return _pageFormat;
    }

    public String getPaperSize() {
        return _pageFormat.getPaperSize();
    }

    public boolean isLandscapeOrientation() {
        return _pageFormat.isLandscapeOrientation();
    }

    public boolean isPortraitOrientation() {
        return _pageFormat.isPortraitOrientation();
    }

    public int getColumnWidth() {
        return _pageFormat.getColumnWidth();
    }

    public int getPageWidth() {
        return _pageFormat.getPageWidth();
    }

    public int getPageHeight() {
        return _pageFormat.getPageHeight();
    }

    public int getTopMargin() {
        return _pageFormat.getTopMargin();
    }

    public int getBottomMargin() {
        return _pageFormat.getBottomMargin();
    }

    public int getLeftMargin() {
        return _pageFormat.getLeftMargin();
    }

    public int getRightMargin() {
        return _pageFormat.getRightMargin();
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

    /**
     * @return the penultimate control group
     */
    public ReportGroup getPenultimateControlGroup() {
        return _penultimateControlGroup;
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
        int columnWidth = getColumnWidth();
        ReportField last = null;
        for (ReportField field : group.getFields()) {
            width = field.getPixels();
            if (width > 0 && used > 0) {
                width += _interFieldGapWidth;
            }
            if (field.getPixels() > 0 && width + used <= columnWidth) {
                used += width;
                if (field.isResizeable()) {
                    last = field;
                    size += field.getPixels();
                }
            }
        }
        int free = columnWidth - used;
        if (last != null && free > 0) {
            int extra;
            for (ReportField field : group.getFields()) {
                if (field.getPixels() > 0 && used < columnWidth) {
                    if (field == last) {
                        extra = columnWidth - used;
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

    private void verifyViewFields() {
        int w;
        int x = 0;
        int columnWidth = getColumnWidth();
        String viewName = _entity.getName() + "." + _view.getName();
        List<ReportField> fields = _detailGroup.getFields();
        if (fields != null && fields.size() > 0) {
            for (ReportField field : fields) {
                w = field.getPixels();
                if (w > 0) {
                    x += w;
                    if (x > columnWidth) {
                        logger.warn("View field " + viewName + "." + field.getName() + " does not fit in the report print area");
                    } else {
                        x += _interFieldGapWidth;
                    }
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
