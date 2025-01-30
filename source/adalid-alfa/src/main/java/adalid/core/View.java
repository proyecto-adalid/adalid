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
import adalid.core.sql.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Jorge Campins
 */
public class View extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(View.class);

    private static final String EOL = "\n";

    private static final PageFormat defaultPageFormat = new DefaultPageFormat();

    private static final Map<Class<? extends PageFormat>, PageFormat> pageFormats = new LinkedHashMap<>();

    private static PageFormat getPageFormat(Class<? extends PageFormat> pageFormatClass) {
        PageFormat pageFormat;
        if (pageFormatClass == null) {
            pageFormat = defaultPageFormat;
        } else if (pageFormats.containsKey(pageFormatClass)) {
            pageFormat = pageFormats.get(pageFormatClass);
        } else {
            try {
                pageFormat = pageFormatClass.getDeclaredConstructor().newInstance();
                logger.trace("An instance of " + pageFormatClass + " was successfully constructed");
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                pageFormat = defaultPageFormat;
                logger.warn("Failed to construct an instance of " + pageFormatClass, ex);
            }
            pageFormats.put(pageFormatClass, pageFormat);
        }
        return pageFormat;
    }

    private static void log(Level level, String message) {
        if (LogUtils.fair(level)) {
            logger.log(level, message);
        }
    }

    private boolean _shareable = true;

    private boolean _selectable, _summarizable, _chartable;

    private PageFormat _pageFormat = defaultPageFormat;

    private Class<? extends PageFormat> _pageFormatClass = DefaultPageFormat.class;

    private PersistentEntity _entity;

    private ViewField _lastControlField;

    private ViewField _penultimateControlField;

    private final List<ViewField> _viewFields = new ArrayList<>();

    private final List<ViewField> _viewDetailFields = new ArrayList<>();

    private final List<ViewField> _viewControlFields = new ArrayList<>();

    private final List<ViewField> _viewAggregationFields = new ArrayList<>();

    private final List<ViewField> _viewOrderByFields = new ArrayList<>();

    private final List<String> _viewNonNumericAggregationFieldNames = new ArrayList<>();

    /**
     * @return the shareable indicator
     */
    public boolean isShareable() {
        return _shareable;
    }

    /**
     * El método setShareable se utiliza para especificar si la vista se puede compartir, o no. Las vistas compartibles se definen como vistas
     * públicas en la aplicación generada, de modo que todos los usuarios autorizados a hacer consultas sobre la entidad puedan utilizarla. De manera
     * predeterminada, todas las vistas del modelo son compartibles. Este método se puede utilizar para dejar de compartir algunas de ellas.
     *
     * @param shareable true, si la vista se puede compartir; de lo contrario, false.
     */
    public void setShareable(boolean shareable) {
        _shareable = shareable;
    }

    /**
     * @return the selectable indicator
     */
    public boolean isSelectable() {
        return _selectable;
    }

    /**
     * @return the summarizable indicator
     */
    public boolean isSummarizable() {
        return _summarizable;
    }

    /**
     * @return the chartable indicator
     */
    public boolean isChartable() {
        return _chartable;
    }

    /**
     * @return the page format
     */
    public PageFormat getPageFormat() {
        return _pageFormat;
    }

    /**
     * @return the page format class
     */
    public Class<? extends PageFormat> getPageFormatClass() {
        return _pageFormatClass;
    }

    /**
     * El método setPageFormatClass establece la clase que define el formato de página de los informes generados para la vista. El formato de página
     * define el tamaño del papel, la orientación (horizontal o vertical) y los márgenes del informe. La clase debe ser una extensión de
     * <code>adalid.core.page.format.AbstractPageFormat</code>, no puede ser una clase abstracta, y su constructor predeterminado (sin argumentos)
     * debe ejecutar el constructor protegido de <code>AbstractPageFormat</code> que da valor a todos los campos requeridos del formato. El valor
     * predeterminado del atributo es <code>adalid.core.page.format.DefaultPageFormat.class</code>. Este es un formato de página personalizado, para
     * papel tamaño EDP americano (Eastern Daily Press), con orientación horizontal. Además de la clase DefaultPageFormat, el paquete
     * adalid.core.page.format también contiene:
     * <ul>
     * <li>LandscapeA4: papel tamaño A4, orientación horizontal.</li>
     * <li>LandscapeB5: papel tamaño B5, orientación horizontal.</li>
     * <li>LandscapeLegal: papel tamaño Legal, orientación horizontal.</li>
     * <li>LandscapeLetter: papel tamaño Carta, orientación horizontal.</li>
     * <li>Ledger: papel tamaño Doble Carta, orientación horizontal.</li>
     * <li>PortraitA4: papel tamaño A4, orientación vertical.</li>
     * <li>PortraitB5: papel tamaño B5, orientación vertical.</li>
     * <li>PortraitLegal: papel tamaño Legal, orientación vertical.</li>
     * <li>PortraitLetter: papel tamaño Carta, orientación vertical.</li>
     * <li>Tabloid: papel tamaño Doble Carta, orientación vertical.</li>
     * </ul>
     *
     * @param pageFormatClass clase que define el formato de página
     *
     * @see <a href="https://en.wikipedia.org/wiki/Paper_size">Paper size</a>
     * @see <a href="https://es.wikipedia.org/wiki/Formato_de_papel">Formato de papel</a>
     */
    public void setPageFormatClass(Class<? extends PageFormat> pageFormatClass) {
        _pageFormat = getPageFormat(pageFormatClass);
        _pageFormatClass = pageFormatClass;
    }

    /**
     * @return the entity
     */
    public PersistentEntity getEntity() {
        return _entity;
    }

    /**
     * @return the last control field
     */
    public ViewField getLastControlField() {
        return _lastControlField;
    }

    /**
     * @return the penultimate control field
     */
    public ViewField getPenultimateControlField() {
        return _penultimateControlField;
    }

    /**
     * @return the fields list
     */
    public List<ViewField> getViewFields() {
        return _viewFields;
    }

    /**
     * @param column the field's column
     * @return the field
     */
    public ViewField getField(Property column) {
        if (column != null) {
            for (ViewField field : _viewFields) {
                if (column.equals(field.getColumn())) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * @return the detail fields list
     */
    public List<ViewField> getViewDetailFields() {
        return _viewDetailFields;
    }

    /**
     * @return the control fields list
     */
    public List<ViewField> getViewControlFields() {
        return _viewControlFields;
    }

    /**
     * @return the aggregation fields list
     */
    public List<ViewField> getViewAggregationFields() {
        return _viewAggregationFields;
    }

    /**
     * @return the order-by fields list
     */
    public List<ViewField> getViewOrderByFields() {
        return _viewOrderByFields;
    }

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            String message = "view " + getFullName();
            _entity = getDeclaringPersistentEntity();
            if (_entity == null) {
                message += " is not selectable; its declaring entity is not persistent";
                logger.warn(message);
                Project.increaseParserWarningCount();
            } else {
                _selectable = true;
            }
            if (_viewFields.isEmpty()) {
                message += " does not have any fields";
                logger.error(message);
                Project.increaseParserErrorCount();
            } else if (_viewDetailFields.isEmpty()) {
                message += " does not have any detail fields";
                logger.error(message);
                Project.increaseParserErrorCount();
            } else {
                if (_viewOrderByFields.isEmpty()) {
                    message += " does not have any detail field with sort option";
                    logger.warn(message);
                    Project.increaseParserWarningCount();
                }
                for (ViewField field : _viewFields) {
                    if (field.isControlField()) {
                        _penultimateControlField = _lastControlField;
                        _lastControlField = field;
                    }
                }
                summaryCheck();
                chartCheck();
            }
        }
        return ok;
    }

    private void summaryCheck() {
        String message = "view " + getFullName() + " is not summarizable";
        String reasons = "";
        if (_viewAggregationFields.isEmpty()) {
            reasons += "; it does not have any aggregations";
        }
        if (reasons.isEmpty()) {
            _summarizable = _selectable;
        } else {
//          logger.info(message + reasons);
            log(Project.getDetailLevel(), message + reasons);
        }
    }

    private void chartCheck() {
        String message = "view " + getFullName() + " is not chartable";
        String reasons = "";
        if (_viewControlFields.isEmpty()) {
            reasons += "; it does not have any control fields";
        }
        if (_viewAggregationFields.isEmpty()) {
            reasons += "; it does not have any aggregations";
        }
        int size = _viewNonNumericAggregationFieldNames.size();
        if (size > 0) {
            reasons += "; " + StringUtils.join(_viewNonNumericAggregationFieldNames, " and ") + " ";
            reasons += size > 1 ? "are non-numeric aggregations" : "is a non-numeric aggregation";
        }
        if (reasons.isEmpty()) {
            _chartable = _selectable;
        } else {
//          logger.info(message + reasons);
            log(Project.getDetailLevel(), message + reasons);
        }
    }

    /**
     * El método newControlField se utiliza para agregar propiedades de control a la vista. Las propiedades de control definen los grupos de
     * agregación de la vista. Las vistas pueden o no tener propiedades de control.
     *
     * @param column una de las propiedades de la entidad; el orden de la propiedad en la vista será ascendente.
     * @return campo de la vista correspondiente a la propiedad agregada.
     */
    public ViewField newControlField(Property column) {
        return newControlField(column, null);
    }

    /**
     * El método newControlField se utiliza para agregar propiedades de control a la vista. Las propiedades de control definen los grupos de
     * agregación de la vista. Las vistas pueden o no tener propiedades de control.
     *
     * @param column una de las propiedades de la entidad; el orden será ascendente.
     * @param sort especifica el orden de la propiedad en la vista. Su valor es uno de los elementos de la enumeración SortOption. Seleccione ASC o
     * DESC para establecer el orden como ascendente o descendente, respectivamente.
     * @return campo de la vista correspondiente a la propiedad agregada.
     */
    public ViewField newControlField(Property column, SortOption sort) {
        String message = "failed to add a control field to view " + getFullName();
        if (column == null) {
            message += "; column property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return null;
        }
        ViewField field = new ViewField(this, column, sort);
        _viewFields.add(field);
        _viewControlFields.add(field);
        return field;
    }

    /**
     * El método newHeadingField se utiliza para agregar propiedades a los encabezados de los grupos de agregación. Inicialmente, los encabezados de
     * los grupos de agregación de los informes generados a partir de la vista tienen una sola propiedad (la propiedad de control que se utilizó para
     * definir el grupo).
     *
     * @param column una de las propiedades de la entidad.
     * @param group propiedad de control que se utilizó para definir el grupo al que se desea agregar la propiedad.
     * @return campo de la vista correspondiente a la propiedad agregada.
     */
    public ViewField newHeadingField(Property column, Property group) {
        String message = "failed to add a heading field to view " + getFullName();
        if (column == null) {
            message += "; column property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return null;
        }
        if (group == null) {
            message += "; group property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return null;
        }
        ViewField field = new ViewField(this, column, group);
        _viewFields.add(field);
        return field;
    }

    /**
     * El método newDetailField se utiliza para agregar propiedades de detalle a la vista. Las vistas pueden o no tener propiedades de detalle.
     *
     * @param column una de las propiedades de la entidad.
     * @return campo de la vista correspondiente a la propiedad agregada.
     */
    public ViewField newDetailField(Property column) {
        return newDetailField(column, null, null);
    }

    /**
     * El método newDetailField se utiliza para agregar propiedades de detalle a la vista. Las vistas pueden o no tener propiedades de detalle.
     *
     * @param column una de las propiedades de la entidad.
     * @param aggregation funciones de agregación de la propiedad en la vista.
     * @return campo de la vista correspondiente a la propiedad agregada.
     */
    public ViewField newDetailField(Property column, ViewFieldAggregation aggregation) {
        return newDetailField(column, aggregation, null);
    }

    /**
     * El método newDetailField se utiliza para agregar propiedades de detalle a la vista. Las vistas pueden o no tener propiedades de detalle.
     *
     * @param column una de las propiedades de la entidad.
     * @param sort especifica el orden de la propiedad en la vista. Su valor es uno de los elementos de la enumeración SortOption. Seleccione ASC o
     * @return campo de la vista correspondiente a la propiedad agregada.
     */
    public ViewField newDetailField(Property column, SortOption sort) {
        return newDetailField(column, null, sort);
    }

    /**
     * El método newDetailField se utiliza para agregar propiedades de detalle a la vista. Las vistas pueden o no tener propiedades de detalle.
     *
     * @param column una de las propiedades de la entidad.
     * @param aggregation funciones de agregación de la propiedad en la vista.
     * @param sort especifica el orden de la propiedad en la vista. Su valor es uno de los elementos de la enumeración SortOption. Seleccione ASC o
     * @return campo de la vista correspondiente a la propiedad agregada.
     */
    public ViewField newDetailField(Property column, ViewFieldAggregation aggregation, SortOption sort) {
        String message = "failed to add a detail field to view " + getFullName();
        if (column == null) {
            message += "; column property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return null;
        }
        ViewField field = new ViewField(this, column, aggregation, sort);
        _viewFields.add(field);
        _viewDetailFields.add(field);
        if (field.isAggregationField()) {
            _viewAggregationFields.add(field);
            if (field.isNonNumericAggregationField()) {
                _viewNonNumericAggregationFieldNames.add(column.getName());
            }
        }
        if (field.getSortOption() != null) {
            _viewOrderByFields.add(field);
        }
        return field;
    }

    public ViewSelect getViewSelect(String view, SelectOption option) {
        return select(view, option);
    }

    public String getSelectStatement(String view, SelectOption option) {
        ViewSelect select = select(view, option);
        return select == null ? null : select.getStatement();
    }

    private ViewSelect select(String view, SelectOption option) {
        if (StringUtils.isBlank(view) || !validSelectOption(option)) {
            return null;
        }
        QueryTable queryTable = _entity.getQueryTable();
//      String from = " FROM " + view;
        String from = view;
        String alias;
        SortOption order;
        ViewField group;
        ArrayList<String> detalle = new ArrayList<>();
        ArrayList<String> resumen = new ArrayList<>();
        ArrayList<String> grafico = new ArrayList<>();
        ArrayList<String> filters = new ArrayList<>();
        ArrayList<String> groupBy = new ArrayList<>();
        ArrayList<String> orderBy = new ArrayList<>();
        for (ViewField field : _viewFields) {
            alias = queryTable.getSqlAlias(field.getColumn());
            order = field.getSortOption();
            group = field.getGroupField();
            addDetalle(detalle, alias, true);
            addResumen(resumen, alias, field, group, true);
            addGrafico(grafico, alias, field, group, true, option);
            addGroupBy(groupBy, alias, field, group);
            switch (option) {
                case DETAIL ->
                    addOrderBy(orderBy, alias, order);
                case SUMMARY -> {
                    if (field.isControlField()) {
                        addOrderBy(orderBy, alias, order);
                    }
                }
                case CHART, CHART_BY_GROUP -> {
                    if (field.isControlField()) {
                        filters.add(alias + " IS NOT NULL");
                        if (grupoGrafico(group, option)) {
                            addOrderBy(orderBy, alias, order);
                        }
                    }
                }
            }
        }
        boolean distinct = false;
        switch (option) {
            case DETAIL -> {
                return select(distinct, detalle, from, null, null, orderBy);
            }
            case SUMMARY -> {
                return select(distinct, resumen, from, null, groupBy, orderBy);
            }
            case CHART, CHART_BY_GROUP -> {
                distinct = true;
                if (grafico.isEmpty()) {
                    grafico.add("1");
                    return select(distinct, grafico, from, filters, null, grafico);
                } else {
                    return select(distinct, grafico, from, filters, null, orderBy);
                }
            }
            default -> {
                return null;
            }
        }
    }

    private boolean validSelectOption(SelectOption option) {
        if (option == null) {
            return false;
        }
        return switch (option) {
            case DETAIL ->
                _selectable;
            case SUMMARY ->
                _summarizable;
            case CHART, CHART_BY_GROUP ->
                _chartable;
            default ->
                false;
        };
    }

    private void addDetalle(List<String> detalle, String alias, boolean visible) {
        detalle.add(pdq(alias, visible));
    }

    private void addResumen(List<String> resumen, String alias, ViewField field, ViewField grupo, boolean visible) {
        ViewFieldAggregation tipo = field.getAggregation();
        if (tipo == null) {
            if (grupo != null) {
                resumen.add(pdq(alias, visible));
            }
        } else {
            switch (tipo) {
                case COUNT ->
                    resumen.add(cnt(alias, visible));
                case MINIMUM ->
                    resumen.add(min(alias, visible));
                case MAXIMUM ->
                    resumen.add(max(alias, visible));
                case SUM ->
                    resumen.add(sum(alias, visible));
                case AVERAGE ->
                    resumen.add(avg(alias, visible));
                case DEVIATION ->
                    resumen.add(dev(alias, visible));
                case COUNT_MINIMUM_MAXIMUM -> {
                    resumen.add(cnt(alias, visible));
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                }
                case MINIMUM_MAXIMUM -> {
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                }
                case SUM_COUNT_AVERAGE -> {
                    resumen.add(sum(alias, visible));
                    resumen.add(cnt(alias, visible));
                    resumen.add(avg(alias, visible));
                }
                case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM -> {
                    resumen.add(sum(alias, visible));
                    resumen.add(cnt(alias, visible));
                    resumen.add(avg(alias, visible));
                    resumen.add(dev(alias, visible));
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                }
                case AVERAGE_DEVIATION -> {
                    resumen.add(avg(alias, visible));
                    resumen.add(dev(alias, visible));
                }
                case AVERAGE_DEVIATION_MINIMUM_MAXIMUM -> {
                    resumen.add(avg(alias, visible));
                    resumen.add(dev(alias, visible));
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                }
                default ->
                    resumen.add(cnt(alias, visible));
            }
        }
    }

    private void addGrafico(List<String> grafico, String alias, ViewField field, ViewField grupo, boolean visible, SelectOption opcion) {
        ViewFieldAggregation tipo = field.getAggregation();
        if (grupoGrafico(grupo, opcion)) {
            if (tipo == null || field.isControlField()) {
                grafico.add(pdq(alias, visible));
            }
        }
    }

    private boolean grupoGrafico(ViewField grupo, SelectOption option) {
        return grupo != null && grupo != _lastControlField && (!SelectOption.CHART_BY_GROUP.equals(option) || grupo != _penultimateControlField);
    }

    private void addGroupBy(List<String> groupBy, String alias, ViewField field, ViewField grupo) {
        ViewFieldAggregation tipo = field.getAggregation();
        if (field.isControlField() || (tipo == null && grupo != null)) {
            groupBy.add(alias);
        }
    }

    private void addOrderBy(List<String> orderBy, String alias, SortOption order) {
        if (order != null) {
            orderBy.add(alias + " " + order);
        }
    }

    private String pdq(String alias, boolean visible) {
        return visible ? alias : "NULL" + " AS " + alias;
    }

    private String cnt(String alias, boolean visible) {
        return agg(alias, visible, ViewFieldAggregation.COUNT);
    }

    private String min(String alias, boolean visible) {
        return agg(alias, visible, ViewFieldAggregation.MINIMUM);
    }

    private String max(String alias, boolean visible) {
        return agg(alias, visible, ViewFieldAggregation.MAXIMUM);
    }

    private String sum(String alias, boolean visible) {
        return agg(alias, visible, ViewFieldAggregation.SUM);
    }

    private String avg(String alias, boolean visible) {
        return agg(alias, visible, ViewFieldAggregation.AVERAGE);
    }

    private String dev(String alias, boolean visible) {
        return agg(alias, visible, ViewFieldAggregation.DEVIATION);
    }

    private String agg(String alias, boolean visible, ViewFieldAggregation tipo) {
        SqlProgrammer sqlProgrammer = ChiefProgrammer.getSqlProgrammer();
        String funcion = sqlProgrammer.getSqlFunctionName(tipo);
        String prefijo = funcion.toLowerCase();
        String value = visible ? funcion + "(" + alias + ")" : "NULL";
        return value + " AS " + prefijo + "__" + alias;
    }

    private ViewSelect select(boolean distinct, List<String> columns, String from, List<String> filters, List<String> groupBy, List<String> orderBy) {
        return new ViewSelect(distinct, columns, from, filters, groupBy, orderBy);
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
            string += fee + tab + "fields" + faa + _viewFields.size() + foo;
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            for (ViewField valor : _viewFields) {
                string += valor.toString(n + 1, valor.getName(), verbose, fields, maps);
            }
        }
        return string;
    }
    // </editor-fold>

}
