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
import adalid.core.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ReportGroup extends AbstractArtifact implements Comparable<ReportGroup> {

    private static final String EOL = "\n";

    private Report _report;

    private ViewField _viewField;

    private ReportField _reportField;

    private int _sequence;

    private boolean _detail;

    private boolean _lastControl;

    private boolean _penultimateControl;

    private final List<ReportField> _fields = new ArrayList<>();

    private int _visibleFieldsCount;

    private int _minHeightToStartNewPage;

    public static ReportGroup addReportGroup(Report report) {
        return new ReportGroup(report);
    }

    public static ReportGroup addReportGroup(Report report, int sequence) {
        return new ReportGroup(report, sequence);
    }

    public static ReportGroup addReportGroup(Report report, View view) {
        return new ReportGroup(report, view);
    }

    public static ReportGroup addReportGroup(Report report, ViewField field, int sequence) {
        return new ReportGroup(report, field, sequence);
    }

    private ReportGroup(Report report) {
        super();
        ReportGroup group = this;
        if (report != null) {
            _report = report;
            _sequence = Integer.MAX_VALUE;
            _detail = true;
            PersistentEntity entity = report.getEntity();
            if (entity != null) {
                QueryTable queryTable = entity.getQueryTable();
                if (queryTable != null) {
                    Property p;
                    ReportField field;
                    List<Property> columns = entity.getDataProviderColumnsList();
                    if (columns != null && !columns.isEmpty()) {
                        Map<String, Property> qpm = entity.getQueryPropertiesMap();
                        if (qpm != null && !qpm.isEmpty()) {
                            for (Property column : columns) {
                                if (column.isHiddenField() || !column.isReportField() || !qpm.containsKey(column.getPathString())) {
                                    continue;
                                }
                                if (column instanceof Entity e) {
                                    p = e.getBusinessKeyProperty();
                                    if (p != null && p.isReportField() && queryTable.contains(p) && qpm.containsKey(p.getPathString())) {
                                        field = ReportField.addReportField(group, p);
                                        field.setParentProperty(column);
                                    }
                                    if (column instanceof EnumerationEntity) {
                                    } else {
                                        p = e.getNameProperty();
                                        if (p != null && p.isReportField() && queryTable.contains(p) && qpm.containsKey(p.getPathString())) {
                                            field = ReportField.addReportField(group, p);
                                            field.setParentProperty(column);
                                        }
                                    }
                                } else if (column instanceof BinaryPrimitive) {
                                } else if (column instanceof Primitive) {
                                    field = ReportField.addReportField(group, column);
                                    field.setParentProperty(null);
                                }
                            }
                        }
                    }
                }
            }
            declare("detailGroup", report);
            add();
        }
    }

    private ReportGroup(Report report, int sequence) {
        super();
        _report = report;
        _sequence = sequence;
        _detail = false;
        declare("reportGroup" + sequence, report);
        add();
    }

    private ReportGroup(Report report, View view) {
        super();
        assert true : "view=" + view; // this assert avoids unused parameter warning
        _report = report;
        _sequence = Integer.MAX_VALUE;
        _detail = true;
        declare("detailGroup", report);
        add();
    }

    private ReportGroup(Report report, ViewField field, int sequence) {
        super();
        _report = report;
        _viewField = field;
        _sequence = sequence;
        _detail = false;
        PersistentEntity entity = report == null ? null : report.getEntity();
        QueryTable queryTable = entity == null ? null : entity.getQueryTable();
        String sqlAlias = queryTable == null ? null : queryTable.getSqlAlias(field.getColumn());
        if (sqlAlias == null) {
            declare("reportGroup" + sequence, report);
        } else {
            declare(sqlAlias, report);
        }
        add();
    }

    private void declare(String name, Report report) {
        setDeclared(name, report);
    }

    private void add() {
        if (_viewField != null) {
            _report.getGroupsMap().put(_viewField, this);
        }
        _report.getGroups().add(this);
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return _report == null ? 0 : _report.getGroups().indexOf(this) + 1;
    }

    /**
     * @return the report
     */
    public Report getReport() {
        return _report;
    }

    /**
     * @return the view field
     */
    public ViewField getViewField() {
        return _viewField;
    }

    /**
     * @param field the view field to set
     */
    protected void setViewField(ViewField field) { // protected avoids method never unused warning
        _viewField = field;
    }

    /**
     * @return the report control field
     */
    public ReportField getField() {
        return _reportField;
    }

    /**
     * @param field the report control field to set
     */
    void setField(ReportField field) {
        _reportField = field;
    }

    /**
     * @return the sequence
     */
    public Integer getSequence() {
        return _sequence;
    }

    /**
     * @return the detail
     */
    public boolean isDetail() {
        return _detail;
    }

    /**
     * @param detail the detail to set
     */
    protected void setDetail(boolean detail) { // protected avoids method never unused warning
        _detail = detail;
    }

    /**
     * @return the last control
     */
    public boolean isLastControl() {
        return _lastControl;
    }

    /**
     * @param b the value to set
     */
    void setLastControl(boolean b) {
        _lastControl = b;
    }

    /**
     * @return the penultimate control
     */
    public boolean isPenultimateControl() {
        return _penultimateControl;
    }

    /**
     * @param b the value to set
     */
    void setPenultimateControl(boolean b) {
        _penultimateControl = b;
    }

    /**
     * @return the fields list
     */
    public List<ReportField> getFields() {
        return _fields;
    }

    public int getVisibleFieldsCount() {
        return _visibleFieldsCount;
    }

    void increaseVisibleFieldsCount() {
        _visibleFieldsCount++;
    }

    public int getMinHeightToStartNewPage() {
        return _minHeightToStartNewPage;
    }

    public int increaseMinHeightToStartNewPage(int height) {
        _minHeightToStartNewPage += height;
        return _minHeightToStartNewPage;
    }

    public int resetMinHeightToStartNewPage() {
        _minHeightToStartNewPage = 0;
        return _minHeightToStartNewPage;
    }

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(ReportGroup that) {
        return that == null ? 0 : this.getSequence().compareTo(that.getSequence());
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
                string += fee + tab + "sequence" + faa + _sequence + foo;
                string += fee + tab + "detail" + faa + _detail + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
