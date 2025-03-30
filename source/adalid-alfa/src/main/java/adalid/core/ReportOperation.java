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
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.awt.Color;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ReportOperation extends Operation {

    private static final Logger logger = Logger.getLogger(Operation.class);

    private boolean _annotatedWithReportOperationClass;

    private String _reportName;

    private String _viewName = "";

    private String _viewFieldName = "";

    private Field _viewField;

    private View _view;

    private ReportQueryType _queryType = ReportQueryType.DYNAMIC;

    private ReportFileType[] _fileTypes = new ReportFileType[]{ReportFileType.PDF};

    private int _rowsLimit = Constants.DEFAULT_REPORT_ROWS_LIMIT;

    private int _detailRowsLimit = Constants.DEFAULT_REPORT_ROWS_LIMIT;

    private int _summaryRowsLimit = Constants.DEFAULT_REPORT_ROWS_LIMIT;

    private int _chartRowsLimit = Constants.DEFAULT_REPORT_ROWS_LIMIT;

    private SortOption _sortOption = SortOption.ASC;

    private ReportChartType[] _chartTypes = new ReportChartType[]{ReportChartType.BAR};

    private final List<Color> _chartColorList = new ArrayList<>(Arrays.asList(ColorUtils.chartColorPalette()));

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            checkChartTypes();
        }
        return ok;
    }

    private void checkChartTypes() {
        if (_view != null && _view.getPenultimateControlField() == null) {
            for (ReportChartType chartType : _chartTypes) {
                if (Constants.SERIES_BY_GROUP.equals(chartType.getJasperChartSubtype())) {
                    logger.error("view of operation " + getFullName() + " does not have enough groups to generate series-by-group charts like " + chartType);
                    Project.increaseParserErrorCount();
                    break;
                }
            }
        }
    }

    /**
     * @return true if annotated with ReportOperationClass; false otherwise
     */
    public boolean isAnnotatedWithReportOperationClass() {
        return _annotatedWithReportOperationClass;
    }

    /**
     * @return the proper name
     */
    @Override
    public String getProperName() {
        return _reportName;
    }

    /**
     * @return the report name
     */
    public String getReportName() {
        return _reportName;
    }

    /**
     * @return the view name
     */
    public String getViewName() {
        return _viewName;
    }

    /**
     * @return the view field name
     */
    public String getViewFieldName() {
        return _viewFieldName;
    }

    /**
     * @return the view field
     */
    public Field getViewField() {
        return _viewField;
    }

    /**
     * @return the view
     */
    public View getView() {
        return _view;
    }

    /**
     * @return the query type
     */
    public ReportQueryType getQueryType() {
        return _queryType;
    }

    /**
     * @return the file types
     */
    public ReportFileType[] getFileTypes() {
        return _fileTypes;
    }

    /**
     * @return the file types list
     */
    public List<ReportFileType> getFileTypesList() {
        return Arrays.asList(_fileTypes);
    }

    /**
     * @return the rows limit
     */
    public int getRowsLimit() {
        return _rowsLimit;
    }

    /**
     * @return the detail rows limit
     */
    public int getDetailRowsLimit() {
        return _detailRowsLimit;
    }

    /**
     * @return the summary rows limit
     */
    public int getSummaryRowsLimit() {
        return _summaryRowsLimit;
    }

    /**
     * @return the chart rows limit
     */
    public int getChartRowsLimit() {
        return _chartRowsLimit;
    }

    /**
     * @return the sort option
     */
    public SortOption getSortOption() {
        return _sortOption;
    }

    /**
     * @return the chart types
     */
    public ReportChartType[] getChartTypes() {
        return _chartTypes;
    }

    /**
     * @return the chart types list
     */
    public List<ReportChartType> getChartTypesList() {
        return Arrays.asList(_chartTypes);
    }

    /**
     * @return the chart color list
     */
    public List<Color> getChartColorList() {
        return _chartColorList;
    }

    /**
     * El método clearChartColorList se utiliza para remover todos los colores almacenados en la lista de colores utilizada para generar informes
     * gráficos.
     */
    public void clearChartColorList() {
        _chartColorList.clear();
    }

    /**
     * El método addChartColor se utiliza para agregar colores a la lista de colores utilizada para generar informes gráficos. Los colores se agregan
     * al final de la lista. Para especificar todos los colores, primero se deben remover los colores almacenados inicialmente en la lista, ejecutando
     * el método clearChartColorList.
     *
     * @param colors uno o más objetos de la clase java.awt.Color
     */
    public void addChartColor(Color... colors) {
        _chartColorList.addAll(Arrays.asList(colors));
    }

    /**
     * entity list report
     */
    private Report _report;

    /**
     * @return the entity list report
     */
    public Report getOperationViewReport() {
        if (_report == null && _view != null) {
            _report = new Report(_view);
        }
        return _report;
    }

    /**
     * @return the asynchronous execution indicator
     */
    @Override
    public boolean isAsynchronous() {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _reportName = StringUtils.defaultIfBlank(_reportName, getName());
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateReportOperationClass(type);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(ReportOperationClass.class);
        return valid;
    }

    private void annotateReportOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ReportOperationClass.class);
        if (annotatedClass != null) {
            ReportOperationClass annotation = annotatedClass.getAnnotation(ReportOperationClass.class);
            if (annotation != null) {
                String name = annotation.name();
                if (StringUtils.isNotBlank(name)) {
                    _reportName = name;
                }
                _viewName = annotation.view();
                _viewFieldName = annotation.viewField();
                _queryType = annotation.type();
                _fileTypes = annotation.fileTypes();
                _rowsLimit = Math.max(0, annotation.rowsLimit());
                _detailRowsLimit = Math.max(0, annotation.detailRowsLimit());
                _summaryRowsLimit = Math.max(0, annotation.summaryRowsLimit());
                _chartRowsLimit = Math.max(0, annotation.chartRowsLimit());
                _sortOption = annotation.sortOption();
                _chartTypes = annotation.chartTypes();
                _annotatedWithReportOperationClass = true;
                if (StringUtils.isNotBlank(_viewFieldName)) {
                    Operation declaringOperation = this;
                    Entity declaringEntity = declaringOperation.getDeclaringEntity();
                    Class<?>[] validTypes = new Class<?>[]{View.class};
                    String[] strings = {declaringOperation.getName(), getName(), "viewField"};
                    String role = StringUtils.join(strings, ".");
                    _viewField = XS1.getField(true, role, _viewFieldName, declaringEntity.getClass(), Entity.class, validTypes);
                    if (_viewField != null) {
                        _view = XS1.getView(_viewField, declaringEntity);
                    }
                }
            }
        }
    }
    // </editor-fold>

}
