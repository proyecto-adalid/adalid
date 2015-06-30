/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import static adalid.core.AbstractArtifact.EOL;
import adalid.core.enums.SelectOption;
import adalid.core.enums.SortOption;
import adalid.core.enums.ViewFieldAggregation;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.SqlProgrammer;
import adalid.core.programmers.ChiefProgrammer;
import adalid.core.sql.QueryTable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Jorge Campins
 */
public class View extends AbstractArtifact {

    private static final Logger logger = Logger.getLogger(View.class);

    private static void log(Level level, String message) {
        if (level != null && !level.equals(Level.OFF)) {
            logger.log(level, message);
        }
    }

    private boolean _finalised, _selectable, _summarizable, _chartable;

    private PersistentEntity _entity;

    private ViewField _lastControlField;

    private final List<ViewField> _viewFields = new ArrayList<>();

    private final List<ViewField> _viewControlFields = new ArrayList<>();

    private final List<ViewField> _viewAggregationFields = new ArrayList<>();

    private final List<String> _viewNonNumericAggregationFieldNames = new ArrayList<>();

    /**
     * @return the finalised indicator
     */
    public boolean isFinalised() {
        return _finalised;
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
     * @return the fields map
     */
    public List<ViewField> getViewFields() {
        return _viewFields;
    }

    /**
     * @return the control fields map
     */
    public List<ViewField> getViewControlFields() {
        return _viewControlFields;
    }

    /**
     * @return the aggregation fields map
     */
    public List<ViewField> getViewAggregationFields() {
        return _viewAggregationFields;
    }

    void finalise() {
        if (_finalised) {
            return;
        }
        _finalised = true;
        String message = "view " + getFullName();
        _entity = getDeclaringPersistentEntity();
        if (_entity == null) {
            message += " is not selectable; its declaring entity is not persistent";
            logger.warn(message);
            TLC.getProject().getParser().increaseWarningCount();
        } else {
            _selectable = true;
        }
        if (_viewFields.isEmpty()) {
            message += " does not have any fields";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else {
            for (ViewField field : _viewFields) {
                if (field.isControlField()) {
                    _lastControlField = field;
                }
            }
            summaryCheck();
            chartCheck();
        }
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

    public ViewField newControlField(Property column) {
        return newControlField(column, null);
    }

    public ViewField newControlField(Property column, SortOption sort) {
        String message = "failed to add a control field to view " + getFullName();
        if (column == null) {
            message += "; column property is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return null;
        }
        ViewField field = new ViewField(this, column, sort);
        _viewFields.add(field);
        _viewControlFields.add(field);
        return field;
    }

    public ViewField newHeadingField(Property column, Property group) {
        String message = "failed to add a heading field to view " + getFullName();
        if (column == null) {
            message += "; column property is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return null;
        }
        if (group == null) {
            message += "; group property is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return null;
        }
        ViewField field = new ViewField(this, column, group);
        _viewFields.add(field);
        return field;
    }

    public ViewField newDetailField(Property column) {
        return newDetailField(column, null, null);
    }

    public ViewField newDetailField(Property column, ViewFieldAggregation aggregation) {
        return newDetailField(column, aggregation, null);
    }

    public ViewField newDetailField(Property column, SortOption sort) {
        return newDetailField(column, null, sort);
    }

    public ViewField newDetailField(Property column, ViewFieldAggregation aggregation, SortOption sort) {
        String message = "failed to add a detail field to view " + getFullName();
        if (column == null) {
            message += "; column property is null";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
            return null;
        }
        ViewField field = new ViewField(this, column, aggregation, sort);
        _viewFields.add(field);
        if (field.isAggregationField()) {
            _viewAggregationFields.add(field);
            if (field.isNonNumericAggregationField()) {
                _viewNonNumericAggregationFieldNames.add(column.getName());
            }
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
            addGrafico(grafico, alias, field, group, true);
            addGroupBy(groupBy, alias, field, group);
            switch (option) {
                case DETAIL:
                    addOrderBy(orderBy, alias, order);
                    break;
                case SUMMARY:
                    if (field.isControlField()) {
                        addOrderBy(orderBy, alias, order);
                    }
                    break;
                case CHART:
                    if (field.isControlField()) {
                        filters.add(alias + " IS NOT NULL");
                        if (group != _lastControlField) {
                            addOrderBy(orderBy, alias, order);
                        }
                    }
                    break;
            }
        }
        boolean distinct = false;
        switch (option) {
            case DETAIL:
                return select(distinct, detalle, from, null, null, orderBy);
            case SUMMARY:
                return select(distinct, resumen, from, null, groupBy, orderBy);
            case CHART:
                distinct = true;
                if (grafico.isEmpty()) {
                    grafico.add("1");
                    return select(distinct, grafico, from, filters, null, grafico);
                } else {
                    return select(distinct, grafico, from, filters, null, orderBy);
                }
            default:
                return null;
        }
    }

    private boolean validSelectOption(SelectOption option) {
        if (option == null) {
            return false;
        }
        switch (option) {
            case DETAIL:
                return _selectable;
            case SUMMARY:
                return _summarizable;
            case CHART:
                return _chartable;
            default:
                return false;
        }
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
                case COUNT:
                    resumen.add(cnt(alias, visible));
                    break;
                case MINIMUM:
                    resumen.add(min(alias, visible));
                    break;
                case MAXIMUM:
                    resumen.add(max(alias, visible));
                    break;
                case SUM:
                    resumen.add(sum(alias, visible));
                    break;
                case AVERAGE:
                    resumen.add(avg(alias, visible));
                    break;
                case DEVIATION:
                    resumen.add(dev(alias, visible));
                    break;
                case COUNT_MINIMUM_MAXIMUM:
                    resumen.add(cnt(alias, visible));
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                    break;
                case MINIMUM_MAXIMUM:
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                    break;
                case SUM_COUNT_AVERAGE:
                    resumen.add(sum(alias, visible));
                    resumen.add(cnt(alias, visible));
                    resumen.add(avg(alias, visible));
                    break;
                case SUM_COUNT_AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                    resumen.add(sum(alias, visible));
                    resumen.add(cnt(alias, visible));
                    resumen.add(avg(alias, visible));
                    resumen.add(dev(alias, visible));
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                    break;
                case AVERAGE_DEVIATION:
                    resumen.add(avg(alias, visible));
                    resumen.add(dev(alias, visible));
                    break;
                case AVERAGE_DEVIATION_MINIMUM_MAXIMUM:
                    resumen.add(avg(alias, visible));
                    resumen.add(dev(alias, visible));
                    resumen.add(min(alias, visible));
                    resumen.add(max(alias, visible));
                    break;
                default:
                    resumen.add(cnt(alias, visible));
                    break;
            }
        }
    }

    private void addGrafico(List<String> grafico, String alias, ViewField field, ViewField grupo, boolean visible) {
        ViewFieldAggregation tipo = field.getAggregation();
        if (grupo != null && grupo != _lastControlField) {
            if (tipo == null || field.isControlField()) {
                grafico.add(pdq(alias, visible));
            }
        }
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
//      String prefijo = tipo.getNameTag();
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
