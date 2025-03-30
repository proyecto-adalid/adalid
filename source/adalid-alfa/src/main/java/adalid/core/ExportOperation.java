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

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class ExportOperation extends Operation {

    private boolean _annotatedWithExportOperationClass;

    private String _exportName;

    private String _viewName = "";

    private String _viewFieldName = "";

    private Field _viewField;

    private View _view;

    private ExportQueryType _queryType = ExportQueryType.DYNAMIC;

    private ExportFileType[] _fileTypes = new ExportFileType[]{ExportFileType.CSV};

    private int _rowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    private int _detailRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    private int _summaryRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    private int _chartRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    private SortOption _sortOption = SortOption.ASC;

    /**
     * @return true if annotated with ExportOperationClass; false otherwise
     */
    public boolean isAnnotatedWithExportOperationClass() {
        return _annotatedWithExportOperationClass;
    }

    /**
     * @return the proper name
     */
    @Override
    public String getProperName() {
        return _exportName;
    }

    /**
     * @return the export name
     */
    public String getExportName() {
        return _exportName;
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
    public ExportQueryType getQueryType() {
        return _queryType;
    }

    /**
     * @return the file types
     */
    public ExportFileType[] getFileTypes() {
        return _fileTypes;
    }

    /**
     * @return the file types list
     */
    public List<ExportFileType> getFileTypesList() {
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
        _exportName = StringUtils.defaultIfBlank(_exportName, getName());
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateExportOperationClass(type);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(ExportOperationClass.class);
        return valid;
    }

    private void annotateExportOperationClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ExportOperationClass.class);
        if (annotatedClass != null) {
            ExportOperationClass annotation = annotatedClass.getAnnotation(ExportOperationClass.class);
            if (annotation != null) {
                String name = annotation.name();
                if (StringUtils.isNotBlank(name)) {
                    _exportName = name;
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
                _annotatedWithExportOperationClass = true;
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
