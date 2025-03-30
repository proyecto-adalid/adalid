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
import adalid.core.primitives.*;
import adalid.core.sql.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class ViewField extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(ViewField.class);

    private static final String EOL = "\n";

    private final View _view;

    private final Property _column;

    private final Property _group;

    private final boolean _control;

    private final boolean _chartable;

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
        _chartable = false;
        _aggregation = null;
        _sortOption = sort == null ? SortOption.ASC : sort;
        init(view);
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
        _chartable = false;
        _aggregation = null;
        _sortOption = null;
        init(view);
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
        if (aggregation == null) {
            _chartable = false;
            _aggregation = null;
        } else if (_column.isEntity() || _column.isBooleanPrimitive() || _column.isCharacterPrimitive()) {
            _chartable = true;
            _aggregation = ViewFieldAggregation.COUNT;
            logNotCountAggregation(aggregation);
        } else if (_column.isNumericPrimitive()) {
            _chartable = true;
            _aggregation = aggregation;
        } else if (_column.isTemporalPrimitive()) {
            switch (aggregation) {
                case MINIMUM, MAXIMUM, COUNT_MINIMUM_MAXIMUM, MINIMUM_MAXIMUM -> {
                    _chartable = false;
                    _aggregation = aggregation;
                }
                default -> {
                    _chartable = true;
                    _aggregation = ViewFieldAggregation.COUNT;
                    logNotCountAggregation(aggregation);
                }
            }
        } else {
            _chartable = false;
            _aggregation = null;
            logNotValidAggregation();
        }
        _sortOption = sort;
        init(view);
    }

    private void init(View view) {
        initDeclaringArtifact(view);
    }

    private void logNotCountAggregation(ViewFieldAggregation aggregation) {
        if (!ViewFieldAggregation.COUNT.equals(aggregation)) {
            String message = aggregation + " cannot be applied to " + _column.getFullName() + "; using COUNT instead";
            logger.warn(message);
            Project.increaseParserWarningCount();
        }
    }

    private void logNotValidAggregation() {
        String message = "no aggregation cannot be applied to " + _column.getFullName();
        logger.error(message);
        Project.increaseParserErrorCount();
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
     * @return the chartable indicator
     */
    public boolean isChartable() {
        return _chartable;
    }

    /**
     * @return the size in pixels
     */
    public Integer getPixels() {
        return _pixels;
    }

    /**
     * El método setPixels se utiliza para establecer el tamaño del campo en los informes, expresado en pixeles. Para calcular el tamaño del campo al
     * imprimir el informe, un pixel equivale a un punto tipográfico y 72 puntos tipográficos equivalen a una pulgada. Si no se especifica el tamaño
     * de algún campo, o si se especifica un valor fuera del rango permitido por el método setPixels, el tamaño del campo en el informe se determina
     * en función la clase de la correspondiente propiedad.
     *
     * @param pixels número entero entre 0 y 1260. Si el valor es 0 entonces el campo no se muestra en los informes. Si el valor es nulo, menor que 0
     * o mayor que 1260, entonces se utiliza el tamaño predeterminado.
     */
    public void setPixels(Integer pixels) {
        _pixels = pixels == null || pixels < 0 || pixels > 1260 ? null : pixels; // 18" * 72 pixels/inch - 36 pixels (left+right margin) = 1260 pixels
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
