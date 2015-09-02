/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.StrUtils;
import adalid.core.data.types.StringData;
import adalid.core.enums.ViewFieldAggregation;
import adalid.core.interfaces.DataArtifact;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.JavaProgrammer;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.Property;
import adalid.core.programmers.ChiefProgrammer;
import adalid.core.sql.QueryTable;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ReportField extends AbstractArtifact implements Comparable<ReportField> {

    private ReportGroup _group;

    private Property _property;

    private Property _parentProperty;

    private ViewField _viewField;

    private int _pixels;

    private boolean _resizeable;

    public static ReportField addReportField(ReportGroup group, String name) {
        return new ReportField(group, name);
    }

    public static ReportField addReportField(ReportGroup group, Property property) {
        return new ReportField(group, property);
    }

    public static ReportField addReportField(ReportGroup group, ViewField field) {
        return new ReportField(group, field);
    }

    private ReportField(ReportGroup group, String name) {
        super();
        if (group != null && name != null) {
            _group = group;
            setDeclared(name);
            add();
        }
    }

    private ReportField(ReportGroup group, Property property) {
        super();
        if (group != null && property != null) {
            _group = group;
            _property = property;
            _pixels = property.isReportField() ? property.getPixels() : 0;
            _resizeable = _pixels > 120;
            Report report = group.getReport();
            PersistentEntity entity = report == null ? null : report.getEntity();
            QueryTable queryTable = entity == null ? null : entity.getQueryTable();
            String sqlAlias = queryTable == null ? null : queryTable.getSqlAlias(property);
            if (sqlAlias != null) {
                setDeclared(sqlAlias);
                add();
            }
        }
    }

    private ReportField(ReportGroup group, ViewField field) {
        super();
        if (group != null && field != null) {
            _group = group;
            _viewField = field;
            _property = field.getColumn();
            _pixels = field.getPixels() == null ? _property == null ? 0 : _property.getPixels() : field.getPixels();
            _resizeable = _pixels > 120;
            Report report = group.getReport();
            PersistentEntity entity = report == null ? null : report.getEntity();
            QueryTable queryTable = entity == null ? null : entity.getQueryTable();
            String sqlAlias = queryTable == null ? null : queryTable.getSqlAlias(_property);
            if (sqlAlias != null) {
                setDeclared(sqlAlias);
                add();
            }
        }
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
     * @param field the view field to set
     */
    void setViewField(ViewField field) {
        _viewField = field;
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
    void setResizeable(boolean resizeable) {
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
        switch (aggregation) {
            case COUNT:
                return isCountAtHand();
            case MINIMUM:
                return isMinAtHand();
            case MAXIMUM:
                return isMaxAtHand();
            case SUM:
                return isSumAtHand();
            case AVERAGE:
                return isAverageAtHand();
            case DEVIATION:
                return isDeviationAtHand();
            default:
                return false;
        }
    }

    public boolean isCountAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        switch (aggregation) {
            case COUNT:
            case COUNT_MINIMUM_MAXIMUM:
            case SUM_COUNT_AVERAGE:
            case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                return true;
            default:
                return false;
        }
    }

    public boolean isMinAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        switch (aggregation) {
            case MINIMUM:
            case COUNT_MINIMUM_MAXIMUM:
            case MINIMUM_MAXIMUM:
            case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
            case AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                return true;
            default:
                return false;
        }
    }

    public boolean isMaxAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        switch (aggregation) {
            case MAXIMUM:
            case COUNT_MINIMUM_MAXIMUM:
            case MINIMUM_MAXIMUM:
            case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
            case AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                return true;
            default:
                return false;
        }
    }

    public boolean isSumAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        switch (aggregation) {
            case SUM:
            case SUM_COUNT_AVERAGE:
            case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                return true;
            default:
                return false;
        }
    }

    public boolean isAverageAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        switch (aggregation) {
            case AVERAGE:
            case SUM_COUNT_AVERAGE:
            case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
            case AVERAGE_DEVIATION:
            case AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                return true;
            default:
                return false;
        }
    }

    public boolean isDeviationAtHand() {
        ViewFieldAggregation aggregation = getAggregation();
        if (aggregation == null) {
            return false;
        }
        switch (aggregation) {
            case DEVIATION:
            case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
            case AVERAGE_DEVIATION:
            case AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                return true;
            default:
                return false;
        }
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
