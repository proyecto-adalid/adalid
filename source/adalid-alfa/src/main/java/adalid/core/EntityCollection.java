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
import adalid.core.primitives.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class EntityCollection extends AbstractArtifact implements AnnotatableArtifact {

    private static final Logger logger = Logger.getLogger(EntityCollection.class);

    private static final CascadeType[] default_cascade_type = {CascadeType.PERSIST, CascadeType.REFRESH};

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    /**
     * annotated with OneToMany
     */
    private boolean _annotatedWithOneToMany;

    /**
     * annotated with EntityCollectionField
     */
    private boolean _annotatedWithEntityCollectionField;

    /**
     *
     */
    private Entity _sourceEntity;

    /**
     *
     */
    private Class<? extends Entity> _targetEntityClass = Entity.class;

    /**
     *
     */
    private Entity _targetEntity;

    /**
     *
     */
    private String _mappedByFieldName;

    /**
     *
     */
    private Field _mappedByField;

    /**
     *
     */
    private Property _mappedByProperty;

    /**
     *
     */
    private String _quickAddingFieldName;

    /**
     *
     */
    private Field _quickAddingField;

    /**
     *
     */
    private Property _quickAddingProperty;

    /**
     *
     */
    private QuickAddingFilter _quickAddingFilter;

    /**
     *
     */
    private FetchType _fetchType = FetchType.UNSPECIFIED;

    /**
     *
     */
    private CascadeType[] _cascadeType = default_cascade_type;

    /**
     *
     */
    private List<CascadeType> _cascadeList = Arrays.asList(default_cascade_type);

    /**
     *
     */
    private Kleenean _orphanRemoval = Kleenean.UNSPECIFIED;

    /**
     *
     */
    private Kleenean _createField = Kleenean.UNSPECIFIED;

    /**
     *
     */
    private Kleenean _updateField = Kleenean.UNSPECIFIED;

    /**
     *
     */
    private Kleenean _removeField = Kleenean.UNSPECIFIED;

    /**
     *
     */
    private Kleenean _detailField = Kleenean.UNSPECIFIED;

    /**
     *
     */
    private boolean _renderingFilterReadOnly;

    /**
     *
     */
    private BooleanExpression _renderingFilter;

    /**
     *
     */
    private BooleanExpression _nullifyingFilter;

    /**
     *
     */
    private PropertyAccess _propertyAccess = PropertyAccess.UNSPECIFIED;

    /**
     *
     */
    private DataEntryFormat _dataEntryFormat = DataEntryFormat.UNSPECIFIED;

    /**
     *
     */
    private String _afterReadingSnippetFileName = "";

    /**
     *
     */
    private String _afterWritingSnippetFileName = "";

    /**
     *
     */
    private String _beforeReadingSnippetFileName = "";

    /**
     *
     */
    private String _beforeWritingSnippetFileName = "";

    /**
     *
     */
    private String _readingViewRowActionSnippetFileName = "";

    /**
     *
     */
    private String _readingViewRowStatusSnippetFileName = "";

    /**
     *
     */
    private String _readingViewRowNumberSnippetFileName = "";

    /**
     *
     */
    private String _writingViewRowActionSnippetFileName = "";

    /**
     *
     */
    private String _writingViewRowStatusSnippetFileName = "";

    /**
     *
     */
    private String _writingViewRowNumberSnippetFileName = "";

    /**
     *
     */
    private Step _step;

    /**
     *
     */
    private Tab _tab;

    /**
     *
     */
    private boolean _setDisplays = true;

    /**
     *
     */
    private Display _readingDisplay, _readingTableDisplay, _readingDetailDisplay;

    /**
     *
     */
    private Display _writingDisplay, _writingTableDisplay, _writingDetailDisplay, _insertDisplay, _updateDisplay, _deleteDisplay;

    /**
     *
     */
    private final Map<Entity, Display> _extensionReadingDisplays = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<Entity, Display> _extensionWritingDisplays = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<String, EntityCollectionAggregate> _aggregates = new LinkedHashMap<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return the source entity
     */
//  @Override
    public Entity getSourceEntity() {
        return _sourceEntity;
    }

    /**
     * @return the target entity class
     */
//  @Override
    public Class<? extends Entity> getTargetEntityClass() {
        return _targetEntityClass;
    }

    /**
     * @return the target entity
     */
//  @Override
    public Entity getTargetEntity() {
        return _targetEntity;
    }

    /**
     * @return the mapped by field name
     */
//  @Override
    public String getMappedByFieldName() {
        return _mappedByFieldName;
    }

    /**
     * @return the mapped by field
     */
//  @Override
    public Field getMappedByField() {
        return _mappedByField;
    }

    /**
     * @return the mapped by property
     */
//  @Override
    public Property getMappedByProperty() {
        return _mappedByProperty;
    }

    /**
     * @return the quick adding field name
     */
//  @Override
    public String getQuickAddingFieldName() {
        return _quickAddingFieldName;
    }

    /**
     * @return the quick adding field
     */
//  @Override
    public Field getQuickAddingField() {
        return _quickAddingField;
    }

    /**
     * @return the quick adding property
     */
//  @Override
    public Property getQuickAddingProperty() {
        return _quickAddingProperty;
    }

    /**
     * @return the quick adding filter
     */
    public QuickAddingFilter getQuickAddingFilter() {
        return _quickAddingFilter;
    }

    /**
     * @return true if the entity defines a one-to-many relationship
     */
//  @Override
    public boolean isOneToMany() {
        return _annotatedWithOneToMany;
    }

    /**
     * @return the fetch type
     */
//  @Override
    public FetchType getFetchType() {
        return FetchType.UNSPECIFIED.equals(_fetchType) ? isUpdateField() ? FetchType.EAGER : FetchType.LAZY : _fetchType;
    }

    /**
     * El método setFetchType se utiliza para establecer la estrategia para obtener datos de la base de datos. Con la estrategia EAGER los datos de la
     * colección deben obtenerse simultáneamente con la entidad. Con la estrategia LAZY los datos de la colección pueden obtenerse posteriormente,
     * cuando se accede a ella por primera vez. El valor predeterminado del atributo es EAGER si los elementos de la colección son actualizables; de
     * lo contrario, LAZY.
     *
     * @param fetchType estrategia para obtener datos de la base de datos.
     */
    public void setFetchType(FetchType fetchType) {
        _fetchType = fetchType == null ? FetchType.UNSPECIFIED : fetchType;
    }

    /**
     * @return the cascade type
     */
//  @Override
    public CascadeType[] getCascadeType() {
        return _cascadeType;
    }

    /**
     * El método setCascadeType se utiliza para establecer el conjunto de operaciones que se propagan a la entidad relacionada. Especifique ALL para
     * propagar todas las operaciones. Incluya PERSIST, MERGE, REMOVE, REFRESH y/o DETACH para propagar la operación persist, merge, remove, refresh
     * y/o detach, respectivamente. El valor predeterminado del atributo es {CascadeType.PERSIST, CascadeType.REFRESH}
     *
     * @param cascadeType conjunto de operaciones que se propagan a la entidad relacionada.
     */
    public void setCascadeType(CascadeType... cascadeType) {
        _cascadeType = cascadeTypeOf(cascadeType);
        _cascadeList = Arrays.asList(_cascadeType);
    }

    private CascadeType[] cascadeTypeOf(CascadeType... cascadeType) {
        return cascadeType == null || cascadeType.length == 0 ? _cascadeType == null ? default_cascade_type : _cascadeType : cascadeType;
    }

    /**
     * @return the cascade type
     */
//  @Override
    public String getCascadeTypeString() {
        if (ArrayUtils.contains(_cascadeType, CascadeType.UNSPECIFIED)) {
            return null;
        }
        if (ArrayUtils.contains(_cascadeType, CascadeType.ALL)) {
            return "CascadeType.ALL";
        }
        Set<CascadeType> set = EnumSet.noneOf(CascadeType.class);
        if (cascadeListContainsAny(CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE)) {
            set.add(CascadeType.REFRESH);
        }
        set.addAll(_cascadeList); // duplicates are ignored
        List<String> list = new ArrayList<>();
        for (CascadeType ct : set) {
            list.add("CascadeType." + ct.name());
        }
        return list.size() == 1 ? list.get(0) : "{" + StringUtils.join(list, ", ") + "}";
    }

    /**
     * @return the cascade refresh indicator
     */
//  @Override
    public boolean isCascadeRefresh() {
        return cascadeListContainsAny(CascadeType.ALL, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE);
    }

    /**
     * @return the cascade persist indicator
     */
//  @Override
    public boolean isCascadePersist() {
        return cascadeListContainsAny(CascadeType.ALL, CascadeType.PERSIST);
    }

    /**
     * @return the cascade merge indicator
     */
//  @Override
    public boolean isCascadeMerge() {
        return cascadeListContainsAny(CascadeType.ALL, CascadeType.MERGE);
    }

    /**
     * @return the cascade remove indicator
     */
//  @Override
    public boolean isCascadeRemove() {
        return cascadeListContainsAny(CascadeType.ALL, CascadeType.REMOVE);
    }

    private boolean cascadeListContainsAny(CascadeType... types) {
        return !Collections.disjoint(_cascadeList, Arrays.asList(types));
    }

    /**
     * @return the orphan removal indicator
     */
//  @Override
    public boolean isOrphanRemoval() {
        return _orphanRemoval.toBoolean(isUpdateField());
    }

    /**
     * El método setOrphanRemoval se utiliza para establecer si se aplica la operación de eliminación a las entidades que se han eliminado de la
     * relación y si se conecta en cascada la operación de eliminación a esas entidades. El valor predeterminado del atributo es TRUE si los elementos
     * de la colección son actualizables; de lo contrario, FALSE.
     *
     * @param remove true si se aplica la operación de eliminación; de lo contrario, false.
     */
    public void setOrphanRemoval(boolean remove) {
        _orphanRemoval = Kleenean.valueOf(remove);
    }

    /**
     * @return the create field indicator
     */
//  @Override
    public boolean isCreateField() {
        boolean cascade = isCascadePersist();
        return (cascade && _createField.toBoolean(cascade));
    }

    /**
     * El método setCreateField se utiliza para establecer si la colección es, o no, requerida por la operación insert de las vistas (páginas) de
     * registro. Este atributo es relevante solo si el valor especificado, o determinado, para el atributo cascade de la colección es ALL o incluye
     * PERSIST. El valor predeterminado del atributo es TRUE si el atributo cascade de la colección incluye PERSIST; en caso contrario es FALSE.
     *
     * @param create true si la colección es requerida por la operación insert de las vistas (páginas) de registro; de lo contrario, false.
     */
    public void setCreateField(boolean create) {
        _createField = Kleenean.valueOf(create);
    }

    /**
     * @return the update field indicator
     */
//  @Override
    public boolean isUpdateField() {
        boolean cascade = isCascadeMerge();
        return (cascade && _updateField.toBoolean(cascade));
    }

    /**
     * El método setUpdateField se utiliza para establecer si la colección es, o no, requerida por la operación update de las vistas (páginas) de
     * registro. Este atributo es relevante solo si el valor especificado, o determinado, para el atributo cascade de la colección es ALL o incluye
     * MERGE. El valor predeterminado del atributo es TRUE si el atributo cascade de la colección incluye MERGE; en caso contrario es FALSE.
     *
     * @param update true si la colección es requerida por la operación update de las vistas (páginas) de registro; de lo contrario, false.
     */
    public void setUpdateField(boolean update) {
        _updateField = Kleenean.valueOf(update);
    }

    /**
     * @return the remove field indicator
     */
//  @Override
    protected boolean isRemoveField() {
        boolean cascade = isCascadeRemove();
        return (cascade && _removeField.toBoolean(cascade));
    }

    /**
     * El método setRemoveField se utiliza para establecer si la colección es, o no, requerida por la operación delete de las vistas (páginas) de
     * registro. Este atributo es relevante solo si el valor especificado, o determinado, para el atributo cascade de la colección es ALL o incluye
     * MERGE. El valor predeterminado del atributo es TRUE si el atributo cascade de la colección incluye MERGE; en caso contrario es FALSE.
     *
     * @param remove true si la colección es requerida por la operación delete de las vistas (páginas) de registro; de lo contrario, false.
     */
    protected void setRemoveField(boolean remove) {
        _removeField = Kleenean.valueOf(remove);
    }

    /**
     * @return the detail field indicator
     */
//  @Override
    public boolean isDetailField() {
        boolean cascade = isCascadeRefresh();
        return (cascade && _detailField.toBoolean(cascade));
    }

    /**
     * El método setDetailField se utiliza para establecer si la colección es, o no, visible en las vistas (páginas) de consulta y registro detallado.
     * Este atributo es relevante solo si el valor especificado, o determinado, para el atributo cascade de la colección es ALL o incluye REFRESH. El
     * valor predeterminado del atributo es TRUE si el atributo cascade de la colección incluye REFRESH; en caso contrario es FALSE.
     *
     * @param detail true si la colección es visible en las vistas (páginas) de consulta y registro detallado; de lo contrario, false.
     */
    public void setDetailField(boolean detail) {
        _detailField = Kleenean.valueOf(detail);
    }

    /**
     * @return the auditable indicator
     */
//  @Override
    public boolean isAuditable() {
        return true;
    }

    /**
     * @return the nullable indicator
     */
//  @Override
    public boolean isNullable() {
        return true;
    }

    /**
     * @return the rendering filter's read-only indicator
     */
//  @Override
    public boolean isRenderingFilterReadOnly() {
        return _renderingFilterReadOnly;
    }

    /**
     * @return the rendering filter
     */
//  @Override
    public BooleanExpression getRenderingFilter() {
        return _renderingFilter;
    }

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de la colección en las vistas (páginas) de consulta y/o
     * registro de la entidad. En las instancias de la entidad que no cumplen con los criterios del filtro, la colección será invisible.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro para lectura y escritura.
     */
//  @Override
    public void setRenderingFilter(BooleanExpression renderingFilter) {
        setRenderingFilter(renderingFilter, false);
    }

    /**
     * El método setRenderingFilter se utiliza para establecer el filtro de presentación de la colección en las vistas (páginas) de consulta y/o
     * registro de la entidad. En las instancias de la entidad que no cumplen con los criterios del filtro, la colección será invisible.
     *
     * @param renderingFilter expresión booleana que se utiliza como filtro
     * @param readOnly true, si el filtro solo aplica para lectura; false, si también aplica para escritura (al agregar o editar).
     */
//  @Override
    public void setRenderingFilter(BooleanExpression renderingFilter, boolean readOnly) {
        boolean log = depth() == 0;
        String message = "failed to set rendering filter of " + getFullName();
        if (renderingFilter == null) {
            if (log) {
                message += "; supplied expression is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (renderingFilter instanceof BooleanPrimitive) {
            _renderingFilter = renderingFilter.isTrue();
        } else {
            _renderingFilter = renderingFilter;
        }
        _renderingFilterReadOnly = readOnly;
    }

    /**
     * @return the nullifying filter
     */
//  @Override
    public BooleanExpression getNullifyingFilter() {
        return _nullifyingFilter;
    }

    /**
     * El método setNullifyingFilter se utiliza para establecer el filtro de anulación de la colección en las vistas (páginas) de registro de la
     * entidad. En las instancias de la entidad que cumplen con los criterios del filtro, la colección será anulada.
     *
     * @param nullifyingFilter expresión booleana que se utiliza como filtro.
     */
//  @Override
    public void setNullifyingFilter(BooleanExpression nullifyingFilter) {
        boolean log = depth() == 0;
        String message = "failed to set nullifying filter of " + getFullName();
        if (nullifyingFilter == null) {
            if (log) {
                message += "; supplied expression is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        } else if (nullifyingFilter instanceof BooleanPrimitive) {
            _nullifyingFilter = nullifyingFilter.isTrue();
        } else {
            _nullifyingFilter = nullifyingFilter;
        }
    }

    /**
     * @return the property access
     */
    public PropertyAccess getPropertyAccess() {
        return _propertyAccess;
    }

    /**
     * El método setPropertyAccess se utiliza para establecer el tipo de control de acceso de la colección. Su valor es uno de los elementos de la
     * enumeración PropertyAccess. Especifique RESTRICTED_WRITING o RESTRICTED_READING para acceso restringido de escritura o lectura,
     * respectivamente. Alternativamente, omita el elemento o especifique UNSPECIFIED para acceso no restringido.
     *
     * @param access tipo de control de acceso de la colección.
     */
    public void setPropertyAccess(PropertyAccess access) {
        _propertyAccess = access == null ? PropertyAccess.UNSPECIFIED : access;
    }

    /**
     * @return the data-entry display format preference
     */
    public DataEntryFormat getDataEntryFormat() {
        return _dataEntryFormat;
    }

    /**
     * El método setDataEntryFormat se utiliza para establecer el orden de búsqueda de la vista (página) para entrada de datos de la colección. Su
     * valor es uno de los elementos de la enumeración DataEntryFormat. Seleccione DETAIL_OR_TABLE o TABLE_OR_DETAIL para buscar primero la vista de
     * formato detallado o tabular, respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es DETAIL_OR_TABLE.
     *
     * @param format orden de búsqueda de la vista (página) para entrada de datos de la colección.
     */
    public void setDataEntryFormat(DataEntryFormat format) {
        _dataEntryFormat = format == null ? DataEntryFormat.UNSPECIFIED : format;
    }

    /**
     * @return the after reading collection snippet file name
     */
    public String getAfterReadingSnippetFileName() {
        return _afterReadingSnippetFileName;
    }

    protected void setAfterReadingSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _afterReadingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _afterReadingSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " after reading collection snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the after writing collection snippet file name
     */
    public String getAfterWritingSnippetFileName() {
        return _afterWritingSnippetFileName;
    }

    protected void setAfterWritingSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _afterWritingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _afterWritingSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " after writing collection snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the before reading collection snippet file name
     */
    public String getBeforeReadingSnippetFileName() {
        return _beforeReadingSnippetFileName;
    }

    protected void setBeforeReadingSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _beforeReadingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _beforeReadingSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " before reading collection snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the before writing collection snippet file name
     */
    public String getBeforeWritingSnippetFileName() {
        return _beforeWritingSnippetFileName;
    }

    protected void setBeforeWritingSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _beforeWritingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _beforeWritingSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " before writing collection snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view collection row action snippet file name
     */
//  @Override
    public String getReadingViewRowActionSnippetFileName() {
        return _readingViewRowActionSnippetFileName;
    }

    protected void setReadingViewRowActionSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingViewRowActionSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingViewRowActionSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view collection row action snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view collection row status snippet file name
     */
//  @Override
    public String getReadingViewRowStatusSnippetFileName() {
        return _readingViewRowStatusSnippetFileName;
    }

    protected void setReadingViewRowStatusSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingViewRowStatusSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingViewRowStatusSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view collection row status snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view collection row number snippet file name
     */
//  @Override
    public String getReadingViewRowNumberSnippetFileName() {
        return _readingViewRowNumberSnippetFileName;
    }

    protected void setReadingViewRowNumberSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingViewRowNumberSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingViewRowNumberSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view collection row number snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view collection row action snippet file name
     */
//  @Override
    public String getWritingViewRowActionSnippetFileName() {
        return _writingViewRowActionSnippetFileName;
    }

    protected void setWritingViewRowActionSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingViewRowActionSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingViewRowActionSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view collection row action snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view collection row status snippet file name
     */
//  @Override
    public String getWritingViewRowStatusSnippetFileName() {
        return _writingViewRowStatusSnippetFileName;
    }

    protected void setWritingViewRowStatusSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingViewRowStatusSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingViewRowStatusSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view collection row status snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view collection row number snippet file name
     */
//  @Override
    public String getWritingViewRowNumberSnippetFileName() {
        return _writingViewRowNumberSnippetFileName;
    }

    protected void setWritingViewRowNumberSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingViewRowNumberSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingViewRowNumberSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view collection row number snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the step containing this collection
     */
//  @Override
    public Step getStep() {
        return _step;
    }

    void setStep(Step step) {
        _step = step;
    }

    /**
     * @return the tab containing this collection
     */
//  @Override
    public Tab getTab() {
        return _tab;
    }

    void setTab(Tab tab) {
        _tab = tab;
    }

    /**
     * @param pageDisplayMode page display mode (READING or WRITING)
     * @return the entity collection display
     */
//  @Override
    public Display getDisplay(String pageDisplayMode) {
        return StringUtils.equalsIgnoreCase(pageDisplayMode, "READING")
            ? getReadingDisplay()
            : getWritingDisplay();
    }

    /**
     * @param pageDisplayMode page display mode (READING or WRITING)
     * @return the entity collection table display
     */
//  @Override
    public Display getTableDisplay(String pageDisplayMode) {
        return StringUtils.equalsIgnoreCase(pageDisplayMode, "READING")
            ? getReadingTableDisplay()
            : getWritingTableDisplay();
    }

    /**
     * @param pageDisplayMode page display mode (READING or WRITING)
     * @return the entity collection detail display
     */
//  @Override
    public Display getDetailDisplay(String pageDisplayMode) {
        return StringUtils.equalsIgnoreCase(pageDisplayMode, "READING")
            ? getReadingDetailDisplay()
            : getWritingDetailDisplay();
    }

    /**
     * @return the entity collection reading display
     */
//  @Override
    private Display getReadingDisplay() {
        setDisplays();
        return _readingDisplay;
    }

    /**
     * @return the entity collection reading table display
     */
//  @Override
    private Display getReadingTableDisplay() {
        setDisplays();
        return _readingTableDisplay;
    }

    /**
     * @return the entity collection reading detail display
     */
//  @Override
    private Display getReadingDetailDisplay() {
        setDisplays();
        return _readingDetailDisplay;
    }

    /**
     * @return the entity collection writing display
     */
//  @Override
    private Display getWritingDisplay() {
        setDisplays();
        return _writingDisplay;
    }

    /**
     * @return the entity collection writing table display
     */
//  @Override
    private Display getWritingTableDisplay() {
        setDisplays();
        return _writingTableDisplay;
    }

    /**
     * @return the entity collection writing detail display
     */
//  @Override
    private Display getWritingDetailDisplay() {
        setDisplays();
        return _writingDetailDisplay;
    }

    /**
     * @return the entity collection insert display
     */
//  @Override
    public Display getInsertDisplay() {
        setDisplays();
        return _insertDisplay;
    }

    /**
     * @return the entity collection update display
     */
//  @Override
    public Display getUpdateDisplay() {
        setDisplays();
        return _updateDisplay;
    }

    /**
     * @return the entity collection delete display
     */
//  @Override
    public Display getDeleteDisplay() {
        setDisplays();
        return _deleteDisplay;
    }

    private void setDisplays() {
        if (_setDisplays) {
            if (_sourceEntity != null && _targetEntity != null && _mappedByProperty instanceof EntityReference) {
                EntityReference reference = (EntityReference) _mappedByProperty;
                Project project = TLC.getProject();
                if (project != null) {
                    _readingTableDisplay = applicationDisplay(project.getReadingTableDisplayOf(_targetEntity, _sourceEntity, reference));
                    _readingDetailDisplay = applicationDisplay(project.getReadingDetailDisplayOf(_targetEntity, _sourceEntity, reference));
                    if (DataEntryFormat.TABLE_OR_DETAIL.equals(_dataEntryFormat)) {
                        _readingDisplay = _readingTableDisplay == null ? _readingDetailDisplay : _readingTableDisplay;
                    } else {
                        _readingDisplay = _readingDetailDisplay == null ? _readingTableDisplay : _readingDetailDisplay;
                    }
                    _writingTableDisplay = applicationDisplay(project.getWritingTableDisplayOf(_targetEntity, _sourceEntity, reference));
                    _writingDetailDisplay = applicationDisplay(project.getWritingDetailDisplayOf(_targetEntity, _sourceEntity, reference));
                    if (DataEntryFormat.TABLE_OR_DETAIL.equals(_dataEntryFormat)) {
                        _writingDisplay = _writingTableDisplay == null ? _writingDetailDisplay : _writingTableDisplay;
                    } else {
                        _writingDisplay = _writingDetailDisplay == null ? _writingTableDisplay : _writingDetailDisplay;
                    }
                    _insertDisplay = _writingDetailDisplay;
                    _updateDisplay = _writingDetailDisplay;
                    _deleteDisplay = _writingDetailDisplay;
                    if (_writingDisplay == _writingTableDisplay) {
                        if (_targetEntity.isTableViewWithInsertEnabled()) {
                            _insertDisplay = _writingTableDisplay;
                        }
                        if (_targetEntity.isTableViewWithUpdateEnabled()) {
                            _updateDisplay = _writingTableDisplay;
                        }
                        if (_targetEntity.isTableViewWithDeleteEnabled()) {
                            _deleteDisplay = _writingTableDisplay;
                        }
                    }
                    _setDisplays = false;
                }
            }
        }
    }

    /**
     * Retorna el objeto Display de la extensión de la entidad que corresponde a la página que se debe abrir en un diálogo.
     *
     * Se usa en la macro inicializarCollectionDataTableControllers de jee2/web/java/pages/blocks/archetype/pagina-consulta-con-coleccion.vm
     *
     * @param extension extension
     * @param pageDisplayMode page display mode (READING or WRITING)
     * @return the entity collection display
     */
//  @Override
    public Display getExtensionDisplay(Entity extension, String pageDisplayMode) {
        Entity root = extension == null ? null : extension.getRoot();
        return root == null ? null : extensionDisplay(root, pageDisplayMode);
    }

    private Display extensionDisplay(Entity extension, String pageDisplayMode) {
        Map<Entity, Display> map = StringUtils.equalsIgnoreCase(pageDisplayMode, "READING") ? _extensionReadingDisplays : _extensionWritingDisplays;
        if (map.containsKey(extension)) {
            return map.get(extension);
        }
        Display readingDisplay = null;
        Display writingDisplay = null;
        if (_sourceEntity != null && _targetEntity != null && _mappedByProperty instanceof EntityReference) {
            if (extension != null && _targetEntity.getClass().isAssignableFrom(extension.getClass())) {
                EntityReference reference = extensionReference(extension);
                Project project = TLC.getProject();
                if (project != null) {
                    if (DataEntryFormat.TABLE_OR_DETAIL.equals(_dataEntryFormat)) {
                        readingDisplay = project.getReadingTableDisplayOf(extension, _sourceEntity, reference);
                        if (readingDisplay == null) {
                            readingDisplay = project.getReadingDetailDisplayOf(extension, _sourceEntity, reference);
                        }
                        writingDisplay = project.getWritingTableDisplayOf(extension, _sourceEntity, reference);
                        if (writingDisplay == null) {
                            writingDisplay = project.getWritingDetailDisplayOf(extension, _sourceEntity, reference);
                        }
                    } else {
                        readingDisplay = project.getReadingDetailDisplayOf(extension, _sourceEntity, reference);
                        if (readingDisplay == null) {
                            readingDisplay = project.getReadingTableDisplayOf(extension, _sourceEntity, reference);
                        }
                        writingDisplay = project.getWritingDetailDisplayOf(extension, _sourceEntity, reference);
                        if (writingDisplay == null) {
                            writingDisplay = project.getWritingTableDisplayOf(extension, _sourceEntity, reference);
                        }
                    }
//                  if (readingDisplay != null) {
                    _extensionReadingDisplays.put(extension, readingDisplay);
//                  }
//                  if (writingDisplay != null) {
                    _extensionWritingDisplays.put(extension, writingDisplay);
//                  }
                }
            }
        }
        return StringUtils.equalsIgnoreCase(pageDisplayMode, "READING") ? readingDisplay : writingDisplay;
    }

    private EntityReference extensionReference(Entity extension) {
        String mappedByPropertyName = _mappedByProperty.getName();
        for (Property property : extension.getPropertiesList()) {
            if (property instanceof EntityReference && property.getName().equals(mappedByPropertyName)) {
                return (EntityReference) property;
            }
        }
        return null;
    }

    private Display applicationDisplay(Display display) {
        return display != null && display.isApplicationDefaultLocation() ? display : null;
    }

    /**
     * @return the aggregates list
     */
    public List<EntityCollectionAggregate> getAggregatesList() {
        return ColUtils.toList(_aggregates.values());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="add aggregates 1/4">
    public EntityCollectionAggregate addCount(int minimum) {
        return newAggregate(AggregateFunction.COUNT, "*", minimum, null);
    }

    public EntityCollectionAggregate addCount(int minimum, int maximum) {
        return newAggregate(AggregateFunction.COUNT, "*", minimum, maximum);
    }

    public EntityCollectionAggregate addCount(String fieldName, int minimum) {
        return newAggregate(AggregateFunction.COUNT, fieldName, minimum, null);
    }

    public EntityCollectionAggregate addCount(String fieldName, int minimum, int maximum) {
        return newAggregate(AggregateFunction.COUNT, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addSum(String fieldName, Number minimum) {
        return newAggregate(AggregateFunction.SUM, fieldName, minimum, null);
    }

    public EntityCollectionAggregate addSum(String fieldName, Number minimum, Number maximum) {
        return newAggregate(AggregateFunction.SUM, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addAverage(String fieldName, Number minimum) {
        return newAggregate(AggregateFunction.AVERAGE, fieldName, minimum, null);
    }

    public EntityCollectionAggregate addAverage(String fieldName, Number minimum, Number maximum) {
        return newAggregate(AggregateFunction.AVERAGE, fieldName, minimum, maximum);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="add aggregates 2/4">
    public EntityCollectionAggregate addCount(NumericExpression minimum) {
        return newAggregate(AggregateFunction.COUNT, "*", minimum, null);
    }

    public EntityCollectionAggregate addCount(NumericExpression minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.COUNT, "*", minimum, maximum);
    }

    public EntityCollectionAggregate addCount(String fieldName, NumericExpression minimum) {
        return newAggregate(AggregateFunction.COUNT, fieldName, minimum, null);
    }

    public EntityCollectionAggregate addCount(String fieldName, NumericExpression minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.COUNT, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addSum(String fieldName, NumericExpression minimum) {
        return newAggregate(AggregateFunction.SUM, fieldName, minimum, null);
    }

    public EntityCollectionAggregate addSum(String fieldName, NumericExpression minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.SUM, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addAverage(String fieldName, NumericExpression minimum) {
        return newAggregate(AggregateFunction.AVERAGE, fieldName, minimum, null);
    }

    public EntityCollectionAggregate addAverage(String fieldName, NumericExpression minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.AVERAGE, fieldName, minimum, maximum);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="add aggregates 3/4">
    public EntityCollectionAggregate addCount(int minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.COUNT, "*", minimum, maximum);
    }

    public EntityCollectionAggregate addCount(NumericExpression minimum, int maximum) {
        return newAggregate(AggregateFunction.COUNT, "*", minimum, maximum);
    }

    public EntityCollectionAggregate addCount(String fieldName, int minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.COUNT, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addCount(String fieldName, NumericExpression minimum, int maximum) {
        return newAggregate(AggregateFunction.COUNT, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addSum(String fieldName, Number minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.SUM, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addSum(String fieldName, NumericExpression minimum, Number maximum) {
        return newAggregate(AggregateFunction.SUM, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addAverage(String fieldName, Number minimum, NumericExpression maximum) {
        return newAggregate(AggregateFunction.AVERAGE, fieldName, minimum, maximum);
    }

    public EntityCollectionAggregate addAverage(String fieldName, NumericExpression minimum, Number maximum) {
        return newAggregate(AggregateFunction.AVERAGE, fieldName, minimum, maximum);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="add aggregates 4/4">
    private EntityCollectionAggregate newAggregate(AggregateFunction function, String fieldName, Object minimum, Object maximum) {
        String key = EntityCollectionAggregate.key(function, fieldName);
        EntityCollectionAggregate aggregate = new EntityCollectionAggregate(this, function, fieldName, minimum, maximum);
        _aggregates.put(key, aggregate);
        return aggregate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="filter tag fields, getters and setters">
    /**
     * @return the rendering filter tag
     */
//  @Override
    public String getRenderingFilterTag() {
        return getLocalizedRenderingFilterTag(null);
    }

    /**
     * El método setRenderingFilterTag se utiliza para establecer la descripción del filtro de presentación de la colección que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de presentación de la colección
     */
//  @Override
    public void setRenderingFilterTag(String tag) {
        setLocalizedRenderingFilterTag(null, tag);
    }

    /**
     * @return the nullifying filter tag
     */
//  @Override
    public String getNullifyingFilterTag() {
        return getLocalizedNullifyingFilterTag(null);
    }

    /**
     * El método setNullifyingFilterTag se utiliza para establecer la descripción del filtro de anulación de la colección que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de anulación de la colección
     */
//  @Override
    public void setNullifyingFilterTag(String tag) {
        setLocalizedNullifyingFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedRenderingFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the rendering filter tag
     */
//  @Override
    public String getLocalizedRenderingFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedRenderingFilterTag.get(l);
    }

    /**
     * El método setLocalizedRenderingFilterTag se utiliza para establecer la descripción del filtro de presentación de la colección que se almacena
     * en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de presentación de la colección
     */
//  @Override
    public void setLocalizedRenderingFilterTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedRenderingFilterTag.remove(l);
        } else {
            _localizedRenderingFilterTag.put(l, tag);
        }
    }

    /**
     * A descriptive word or phrase applied to the nullifying filter as a label or identifier.
     */
    private final Map<Locale, String> _localizedNullifyingFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the nullifying filter tag
     */
//  @Override
    public String getLocalizedNullifyingFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedNullifyingFilterTag.get(l);
    }

    /**
     * El método setLocalizedNullifyingFilterTag se utiliza para establecer la descripción del filtro de anulación de la colección que se almacena en
     * el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de anulación de la colección
     */
//  @Override
    public void setLocalizedNullifyingFilterTag(Locale locale, String tag) {
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedNullifyingFilterTag.remove(l);
        } else {
            _localizedNullifyingFilterTag.put(l, tag);
        }
    }
    // </editor-fold>

    /**
     * @return the entity collection field indicator
     */
//  @Override
    public boolean isEntityCollectionField() {
        return isDetailField() || isCreateField() || isUpdateField();
    }

    /**
     * @param pageDisplayMode page display mode (READING or WRITING)
     * @return the entity collection field indicator
     */
//  @Override
    public boolean isEntityCollectionField(String pageDisplayMode) {
        return StringUtils.equalsIgnoreCase(pageDisplayMode, "READING")
            ? isDetailField()
            : isDetailField() || isCreateField() || isUpdateField();
    }

    /**
     * @return the entity collection with display indicator
     */
//  @Override
    public boolean isEntityCollectionWithDisplay() {
        return isEntityCollectionWithReadingDisplay() || isEntityCollectionWithWritingDisplay();
    }

    /**
     * @param pageDisplayMode page display mode (READING or WRITING)
     * @return the entity collection with writing display indicator
     */
//  @Override
    public boolean isEntityCollectionWithDisplay(String pageDisplayMode) {
        return StringUtils.equalsIgnoreCase(pageDisplayMode, "READING")
            ? isEntityCollectionWithReadingDisplay()
            : isEntityCollectionWithWritingDisplay();
    }

    /**
     * @return the entity collection with reading display indicator
     */
//  @Override
    public boolean isEntityCollectionWithReadingDisplay() {
        return getReadingDisplay() != null;
    }

    /**
     * @return the entity collection with writing display indicator
     */
//  @Override
    public boolean isEntityCollectionWithWritingDisplay() {
        return getWritingDisplay() != null;
    }

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(OneToMany.class);
        valid.add(EntityCollectionField.class);
        return valid;
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            annotateOneToMany(field);
            annotateEntityCollectionField(field);
        }
    }

    private void annotateOneToMany(Field field) {
        _annotatedWithOneToMany = field.isAnnotationPresent(OneToMany.class);
        if (_annotatedWithOneToMany) {
            OneToMany annotation = field.getAnnotation(OneToMany.class);
            _targetEntityClass = annotation.targetEntity();
            _mappedByFieldName = annotation.mappedBy();
            _fetchType = specified(annotation.fetch(), _fetchType);
            _cascadeType = cascadeTypeOf(annotation.cascade());
            _cascadeList = Arrays.asList(_cascadeType);
            _orphanRemoval = specified(annotation.orphanRemoval(), _orphanRemoval);
        }
    }

    private void annotateEntityCollectionField(Field field) {
        _annotatedWithEntityCollectionField = field.isAnnotationPresent(EntityCollectionField.class);
        if (_annotatedWithEntityCollectionField) {
            EntityCollectionField annotation = field.getAnnotation(EntityCollectionField.class);
            _createField = specified(annotation.create(), _createField);
            _updateField = specified(annotation.update(), _updateField);
            _detailField = specified(annotation.detail(), _detailField);
            _propertyAccess = specified(annotation.access(), _propertyAccess);
            _dataEntryFormat = specified(annotation.format(), _dataEntryFormat);
            /**/
            String fileName;
            /**/
            fileName = annotation.afterReadingSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setAfterReadingSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.afterWritingSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setAfterWritingSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.beforeReadingSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setBeforeReadingSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.beforeWritingSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setBeforeWritingSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.readingViewRowActionSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setReadingViewRowActionSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.readingViewRowStatusSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setReadingViewRowStatusSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.readingViewRowNumberSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setReadingViewRowNumberSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.writingViewRowActionSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setWritingViewRowActionSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.writingViewRowStatusSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setWritingViewRowStatusSnippetFileName(fileName);
            }
            /**/
            fileName = annotation.writingViewRowNumberSnippet();
            if (StringUtils.isNotBlank(fileName)) {
                setWritingViewRowNumberSnippetFileName(fileName);
            }
            /**/
            setInlineHelpType(annotation.inlineHelp());
        }
    }

    /*
    private CascadeType[] trim(CascadeType[] cascade) {
        if (ArrayUtils.contains(cascade, CascadeType.UNSPECIFIED)) {
            return new CascadeType[]{CascadeType.UNSPECIFIED};
        }
        if (ArrayUtils.contains(cascade, CascadeType.ALL)) {
            return new CascadeType[]{CascadeType.ALL};
        }
        Set<CascadeType> set = EnumSet.noneOf(CascadeType.class);
        set.addAll(Arrays.asList(cascade)); // duplicates are ignored
        return set.toArray(ArrUtils.arrayOf(CascadeType.class));
    }

    /**/
    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            finaliseOneToMany();
        }
        return ok;
    }

    private boolean finaliseOneToMany() {
        if (depth() > 0) {
            return true; // TODO: make sure only depth 0 collections need to be finalised
        }
        boolean log = depth() == 0;
        String fullName = getFullName();
        Entity declaringEntity = getDeclaringEntity();
        // since 04/07/2022
        Field declaringField = getDeclaringField();
        if (declaringField == null) {
            String message = "no declaring field defined for " + fullName;
            logger.fatal(message);
            Project.increaseParserErrorCount();
            return false;
        }
        Class<?> declaringFieldClass = declaringField.getDeclaringClass();
        Entity declaringFieldDeclaringEntity = TLC.getProject().getEntity(declaringFieldClass);
        if (declaringFieldDeclaringEntity == null) {
            String message = "no declaring entity defined for " + declaringField;
            logger.fatal(message);
            Project.increaseParserErrorCount();
            return false;
        }
        if (!declaringFieldDeclaringEntity.equals(declaringEntity)) {
            declaringEntity = declaringFieldDeclaringEntity;
        }
        /**/
        _sourceEntity = declaringEntity;
        /* until 04/07/2000
        if (_sourceEntity == null) {
//          if (log) {
            String message = "no source entity defined for " + fullName;
            logger.fatal(message);
            Project.increaseParserErrorCount();
//          }
            return false;
        }
        /**/
        _targetEntity = _targetEntityClass == null || _targetEntityClass == Entity.class ? null : TLC.getProject().getEntity(_targetEntityClass);
        if (_targetEntity == null) {
//          if (log) {
            String message = "no target entity defined for " + fullName + "; it has an invalid target entity class.";
            logger.error(message);
            Project.increaseParserErrorCount();
//          }
            return false;
        }
        Class<? extends Entity> declaringEntityClass = declaringEntity.getClass();
        String decName = declaringEntityClass.getCanonicalName();
        String message = "no mapping defined for " + fullName;
        if (StringUtils.isBlank(_mappedByFieldName)) {
            List<Field> fields = XS1.getEntityFields(_targetEntityClass, Entity.class, declaringEntityClass);
            int size = fields.size();
            if (size == 0) {
                message += "; it has no mapped-by field name and there is no suitable " + decName + " field in " + _targetEntityClass;
            } else if (size > 1) {
                message += "; it has no mapped-by field name and there is more than one suitable field in the target entity class.";
            } else {
                _mappedByField = fields.get(0);
                _mappedByFieldName = _mappedByField.getName();
            }
        }
        if (StringUtils.isBlank(_mappedByFieldName)) {
            logger.error(message);
            Project.increaseParserErrorCount();
            return false;
        }
        if (_mappedByField == null) {
            String[] strings = {declaringEntity.getName(), getName(), "mappedBy"};
            String role = StringUtils.join(strings, ".");
            Class<?>[] validTypes = {declaringEntityClass};
            _mappedByField = XS1.getField(log, role, _mappedByFieldName, _targetEntityClass, Entity.class, validTypes);
        }
        if (_mappedByField == null) {
            message = "no mapping defined for " + fullName + "; it has an invalid mapped-by field name.";
            logger.error(message);
            Project.increaseParserErrorCount();
            return false;
        }
        _mappedByProperty = XS1.getProperty(_mappedByField, _targetEntity);
        if (_mappedByProperty == null) {
            message = "no mapping defined for " + fullName + "; it has an invalid mapped-by property name.";
            logger.error(message);
            Project.increaseParserErrorCount();
            return false;
        }
        if (_mappedByProperty.isCalculable()) {
            message = "no mapping defined for " + fullName + "; it has an invalid (calculable) mapped-by property.";
            logger.error(message);
            Project.increaseParserErrorCount();
            return false;
        }
        if (_mappedByProperty instanceof EntityReference reference) {
            // now it's too early to check the navigability...
            // so set the reference's mapped collection to check later
            reference.setMappedCollection(this);
        }
        /**/
        if (isUpdateField()) {
            if (FetchType.LAZY.equals(getFetchType())) {
                message = fullName + " is an updatable collection and therefore requires its fetch type set to EAGER.";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
            if (!isOrphanRemoval()) {
                message = fullName + " is an updatable collection but it will not allow deletions because its orphan removal option set to false.";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
        }
        /**/
        // now it's too early to set the display
        /**/
        List<EntityCollectionAggregate> aggregates = getAggregatesList();
        for (EntityCollectionAggregate aggregate : aggregates) {
            aggregate.check();
        }
        /**/
        List<Field> references = XS1.getEntityFields(_targetEntityClass, Entity.class, Entity.class);
        for (Field field : references) {
            String fieldName = field.getName();
            if (_mappedByFieldName.equals(fieldName)) {
                continue;
            }
            int modifiers = field.getModifiers();
            if ((Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers))) {
                continue;
            }
            ManyToOne annotation = field.getAnnotation(ManyToOne.class);
            QuickAddingFilter quickAdding = annotation == null ? null : annotation.quickAdding();
            if (quickAdding == null || quickAdding.equals(QuickAddingFilter.NONE)) {
                continue;
            }
            _quickAddingFieldName = fieldName;
            _quickAddingField = field;
            _quickAddingProperty = XS1.getProperty(_quickAddingField, _targetEntity);
            _quickAddingFilter = quickAdding;
            logger.trace("\t\t" + "QuickAddingFilter=" + quickAdding + "\n\t\t" + _quickAddingField + "\n\t\t" + _quickAddingProperty);
            break;
        }
        /**/
        return true;
    }

}
