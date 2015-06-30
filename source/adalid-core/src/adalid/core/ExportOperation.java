/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.annotations.ExportOperationClass;
import adalid.core.enums.ExportQueryType;
import adalid.core.enums.SortOption;
import adalid.core.interfaces.Entity;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class ExportOperation extends Operation {

    private boolean _annotatedWithExportOperationClass;

    private String _exportName;

    private String _viewName;

    private String _viewFieldName;

    private Field _viewField;

    private View _view;

    private ExportQueryType _queryType;

    private int _rowsLimit;

    private int _detailRowsLimit;

    private int _summaryRowsLimit;

    private int _chartRowsLimit;

    private SortOption _sortOption;

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

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithExportOperationClass = false;
        _exportName = getName();
        _viewName = "";
        _viewFieldName = "";
        _viewField = null;
        _view = null;
        _queryType = ExportQueryType.DYNAMIC;
        _rowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;
        _detailRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;
        _summaryRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;
        _chartRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;
        _sortOption = SortOption.ASC;
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
                    String[] strings = new String[]{declaringOperation.getName(), getName(), "viewField"};
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
