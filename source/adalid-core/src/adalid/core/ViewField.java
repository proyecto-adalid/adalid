/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.SortOption;
import adalid.core.enums.ViewFieldAggregation;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.Property;
import adalid.core.primitives.NumericPrimitive;
import adalid.core.sql.QueryTable;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ViewField extends AbstractArtifact {

    private final View _view;

    private final Property _column;

    private final Property _group;

    private final boolean _control;

    private final ViewFieldAggregation _aggregation;

    private final SortOption _sortOption;

    private Integer _pixels;

    /**
     * Constructs a control field.
     *
     * @param column control-break column
     * @param sort sort type
     */
    ViewField(View view, Property column, SortOption sort) {
        super();
        _view = view;
        _column = column;
        _group = column;
        _control = true;
        _aggregation = null;
        _sortOption = sort == null ? SortOption.ASC : sort;
    }

    /**
     * Constructs a heading field.
     *
     * @param column heading column
     * @param group control-break column
     */
    ViewField(View view, Property column, Property group) {
        super();
        _view = view;
        _column = column;
        _group = group;
        _control = false;
        _aggregation = null;
        _sortOption = null;
    }

    /**
     * Constructs a detail field.
     *
     * @param column detail column
     * @param aggregation aggregation type
     * @param sort sort type
     */
    ViewField(View view, Property column, ViewFieldAggregation aggregation, SortOption sort) {
        super();
        _view = view;
        _column = column;
        _group = null;
        _control = false;
        _aggregation = aggregation;
        _sortOption = sort;
    }

    @Override
    public String getName() {
        return _column == null ? super.getName() : _column.getName();
    }

    @Override
    public String getAlias() {
        PersistentEntity entity = _view.getEntity();
        QueryTable queryTable = entity == null ? null : entity.getQueryTable();
        return queryTable == null ? super.getAlias() : queryTable.getSqlAlias(_column);
    }

    /**
     * @return the column
     */
    public Property getColumn() {
        return _column;
    }

    /**
     * @return the group
     */
    public Property getGroup() {
        return _group;
    }

    /**
     * @return the group field
     */
    public ViewField getGroupField() {
        if (_group != null) {
            for (ViewField field : _view.getViewControlFields()) {
                if (field.getColumn().equals(_group)) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * @return the group field indicator
     */
    public boolean isControlField() {
        return _control;
    }

    /**
     * @return the view column aggregation
     */
    public ViewFieldAggregation getAggregation() {
        return _aggregation;
    }

    /**
     * @return the aggregation indicator
     */
    public boolean isAggregationField() {
        return _aggregation != null;
    }

    /**
     * @return the numeric aggregation indicator
     */
    public boolean isNumericAggregationField() {
        return _aggregation != null && (_aggregation.equals(ViewFieldAggregation.COUNT) || _column instanceof NumericPrimitive);
    }

    /**
     * @return the non-numeric aggregation indicator
     */
    public boolean isNonNumericAggregationField() {
        return _aggregation != null && !isNumericAggregationField();
    }

    /**
     * @return the sort option
     */
    public SortOption getSortOption() {
        return _sortOption;
    }

    /**
     * @return the size in pixels
     */
    public Integer getPixels() {
        return _pixels;
    }

    /**
     * @param pixels the size in pixels to set
     */
    public void setPixels(Integer pixels) {
        _pixels = pixels == null || pixels < 0 || pixels > 960 ? null : pixels;
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
            if (verbose) {
                String column = _column == null ? "" : _column.getName();
                string += fee + tab + "column" + faa + column + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
