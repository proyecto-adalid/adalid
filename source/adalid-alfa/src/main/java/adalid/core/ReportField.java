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
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.programmers.*;
import adalid.core.sql.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ReportField extends AbstractArtifact implements Comparable<ReportField> {

    private static final String EOL = "\n";

    private final ReportGroup _group;

    private final Property _property;

    private Property _parentProperty;

    private final ViewField _viewField;

    private int _pixels;

    private boolean _resizeable;

    protected static ReportField addReportField(ReportGroup group, String name) { // protected avoids method never unused warning
        return new ReportField(group, name);
    }

    static ReportField addReportField(ReportGroup group, Property property) {
        return new ReportField(group, property);
    }

    static ReportField addReportField(ReportGroup group, ViewField field) {
        return new ReportField(group, field);
    }

    private ReportField(ReportGroup group, String name) {
        super();
        _group = group;
        _property = null;
        _viewField = null;
        if (group != null && name != null) {
            declare(name, group);
            add();
        }
    }

    private ReportField(ReportGroup group, Property property) {
        super();
        _group = group;
        _property = property;
        _viewField = null;
        if (group != null && property != null) {
            Report report = group.getReport();
            PersistentEntity entity = report == null ? null : report.getEntity();
            QueryTable queryTable = entity == null ? null : entity.getQueryTable();
            String sqlAlias = queryTable == null ? null : queryTable.getSqlAlias(property);
            initPixels(property.isReportField() ? property.getPixels() : 0);
            if (sqlAlias != null) {
                declare(sqlAlias, group);
                add();
            }
        }
    }

    private ReportField(ReportGroup group, ViewField field) {
        super();
        _group = group;
        _property = field == null ? null : field.getColumn();
        _viewField = field;
        if (group != null && field != null) {
            Report report = group.getReport();
            PersistentEntity entity = report == null ? null : report.getEntity();
            QueryTable queryTable = entity == null ? null : entity.getQueryTable();
            String sqlAlias = queryTable == null ? null : queryTable.getSqlAlias(_property);
            Integer pixels = field.getPixels();
            initPixels(pixels == null ? _property == null ? 0 : _property.getPixels() : pixels);
            if (sqlAlias != null) {
                declare(sqlAlias, group);
                add();
            }
        }
    }

    private void initPixels(int pixels) {
        _pixels = pixels;
        _resizeable = pixels > 120;
    }

    private void declare(String name, ReportGroup group) {
        setDeclared(name, group);
    }

    private void add() {
        _group.getFields().add(this);
        if (_pixels > 0) {
            _group.increaseVisibleFieldsCount();
        }
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return _group == null ? 0 : _group.getFields().indexOf(this) + 1;
    }

    /**
     * @return the composite index
     */
    public int getCompositeIndex() {
        int groupIndex = _group.getIndex();
        if (groupIndex < 0) {
            return -1;
        }
        int fieldIndex = getIndex();
        if (fieldIndex < 0) {
            return -1;
        }
        return 1000 * groupIndex + fieldIndex;
    }

    public String getLabel() {
        String etiqueta = _property == null ? getName() : _property.getDefaultShortLabel();
        return etiqueta == null ? null : StrUtils.getStringJava(etiqueta);
    }

    /**
     * @return the sequence
     */
    public Integer getSequence() {
        return getIndex();
    }

    /**
     * @return the report group
     */
    public ReportGroup getReportGroup() {
        return _group;
    }

    /**
     * @return the property
     */
    public Property getProperty() {
        return _property;
    }

    /**
     * @return the parent property
     */
    public Property getParentProperty() {
        return _parentProperty;
    }

    void setParentProperty(Property parent) {
        _parentProperty = parent;
    }

    /**
     * @return the view field
     */
    public ViewField getViewField() {
        return _viewField;
    }

    /**
     * @return the aggregation
     */
    public ViewFieldAggregation getAggregation() {
        return _viewField == null ? null : _viewField.getAggregation();
    }

    /**
     * @return the pixels
     */
    public int getPixels() {
        return _pixels;
    }

    /**
     * @param pixels the pixels to set
     */
    void setPixels(int pixels) {
        _pixels = pixels;
    }

    /**
     * @return the resizeable
     */
    public boolean isResizeable() {
        return _resizeable;
    }

    /**
     * @param resizeable the resizeable to set
     */
    protected void setResizeable(boolean resizeable) { // protected avoids method never unused warning
        _resizeable = resizeable;
    }

    public String getDataClassName() {
        Entity entity = _property instanceof Entity ? (Entity) _property : null;
        DataArtifact dataArtifact = entity != null ? entity.getPrimaryKeyProperty() : _property;
        Class<?> dataClass = dataArtifact == null ? null : dataArtifact.getDataClass();
        return dataClass == null ? StringData.class.getSimpleName() : dataClass.getSimpleName();
    }

    public String getJavaClassName() {
        Entity entity = _property instanceof Entity ? (Entity) _property : null;
        DataArtifact dataArtifact = entity != null ? entity.getPrimaryKeyProperty() : _property;
        JavaProgrammer javaProgrammer = dataArtifact == null ? null : ChiefProgrammer.getJavaProgrammer();
        return javaProgrammer == null ? String.class.getName() : javaProgrammer.getJavaTypeName(dataArtifact);
    }

    public boolean aggregationAtHand(ViewFieldAggregation aggregation) {
        if (aggregation == null) {
            return false;
        }
        return switch (aggregation) {
            case COUNT ->
                isCountAtHand();
            case MINIMUM ->
                isMinAtHand();
            case MAXIMUM ->
                isMaxAtHand();
            case SUM ->
                isSumAtHand();
            case AVERAGE ->
                isAverageAtHand();
            case DEVIATION ->
                isDeviationAtHand();
            default ->
                false;
        };
    }

    public boolean isCountAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        return switch (aggregation) {
            case COUNT, COUNT_MINIMUM_MAXIMUM, SUM_COUNT_AVERAGE, SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM ->
                true;
            default ->
                false;
        };
    }

    public boolean isMinAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        return switch (aggregation) {
            case MINIMUM, COUNT_MINIMUM_MAXIMUM, MINIMUM_MAXIMUM, SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM, AVERAGE_DEVIATION_MINIMUM_MAXIMUM ->
                true;
            default ->
                false;
        };
    }

    public boolean isMaxAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        return switch (aggregation) {
            case MAXIMUM, COUNT_MINIMUM_MAXIMUM, MINIMUM_MAXIMUM, SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM, AVERAGE_DEVIATION_MINIMUM_MAXIMUM ->
                true;
            default ->
                false;
        };
    }

    public boolean isSumAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        return switch (aggregation) {
            case SUM, SUM_COUNT_AVERAGE, SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM ->
                true;
            default ->
                false;
        };
    }

    public boolean isAverageAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        return switch (aggregation) {
            case AVERAGE, SUM_COUNT_AVERAGE, SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM, AVERAGE_DEVIATION, AVERAGE_DEVIATION_MINIMUM_MAXIMUM ->
                true;
            default ->
                false;
        };
    }

    public boolean isDeviationAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        return switch (aggregation) {
            case DEVIATION, SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM, AVERAGE_DEVIATION, AVERAGE_DEVIATION_MINIMUM_MAXIMUM ->
                true;
            default ->
                false;
        };
    }

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(ReportField that) {
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
                string += fee + tab + "pixels" + faa + _pixels + foo;
                string += fee + tab + "resizeable" + faa + _resizeable + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
