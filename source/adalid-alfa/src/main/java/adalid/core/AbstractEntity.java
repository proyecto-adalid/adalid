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

import adalid.commons.bundles.*;
import adalid.commons.i18n.*;
import adalid.commons.util.*;
import adalid.core.annotations.*;
import adalid.core.comparators.*;
import adalid.core.constants.*;
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.exceptions.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.jee.JavaWebProject;
import adalid.core.operations.*;
import adalid.core.predicates.*;
import adalid.core.primitives.*;
import adalid.core.properties.*;
import adalid.core.sql.*;
import adalid.core.wrappers.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractEntity extends AbstractDataArtifact implements EntityReference {

    // <editor-fold defaultstate="collapsed" desc="expression building protected static methods">
    /**
     * La conjunción (AND) es un operador lógico que resulta en verdadero si ambos operandos son verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si ambos operandos son verdadero; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX and(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.and(operand1, operand2);
    }

    /**
     * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si uno de los operandos es falso. Es equivalente a la negación
     * (NOT) de la conjunción (AND).
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si uno de los operandos es falso; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX nand(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.nand(operand1, operand2);
    }

    /**
     * La disyunción (OR) es un operador lógico que resulta en verdadero si uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX or(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.or(operand1, operand2);
    }

    /**
     * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si ambos operandos son falso. Es equivalente a la negación (NOT) de
     * la disyunción (OR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si ambos operandos son falso; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX nor(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.nor(operand1, operand2);
    }

    /**
     * La disyunción exclusiva (XOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si uno y solo uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX xor(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.xor(operand1, operand2);
    }

    /**
     * La equivalencia (XNOR) es un operador lógico que resulta en verdadero si ambos operandos son verdadero ó si ambos son falso. Es equivalente a
     * la negación (NOT) de la disyunción exclusiva (XOR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @return verdadero si ambos operandos son verdadero ó si ambos son falso; de lo contrario, falso.
     */
    protected static BooleanOrderedPairX xnor(BooleanExpression operand1, BooleanExpression operand2) {
        return XB.Boolean.OrderedPair.xnor(operand1, operand2);
    }

    /**
     * La condicional material (X_IMPLIES_Y) es un operador lógico que resulta en falso solo si X es verdadero y Y es falso. Es equivalente a la
     * disyunción (OR) de la negación (NOT) de X con Y (NOT X OR Y).
     *
     * @param operandX X
     * @param operandY Y
     * @return falso solo si X es verdadero y Y es falso; de lo contrario, verdadero.
     */
    protected static BooleanOrderedPairX xImpliesY(BooleanExpression operandX, BooleanExpression operandY) {
        return XB.Boolean.OrderedPair.xImpliesY(operandX, operandY);
    }

    /**
     * La conjunción (AND) es un operador lógico que resulta en verdadero si todos los operandos son verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si todos los operandos son verdadero; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX and(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.and(operand1, operand2, extraOperands);
    }

    /**
     * La negación alternativa (NAND) es un operador lógico que resulta en verdadero si al menos uno de los operandos es falso. Es equivalente a la
     * negación (NOT) de la conjunción (AND).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si al menos uno de los operandos es falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX nand(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.nand(operand1, operand2, extraOperands);
    }

    /**
     * La disyunción (OR) es un operador lógico que resulta en verdadero si al menos uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si al menos uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX or(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.or(operand1, operand2, extraOperands);
    }

    /**
     * La negación conjunta (NOR) es un operador lógico que resulta en verdadero si todos los operandos son falso. Es equivalente a la negación (NOT)
     * de la disyunción (OR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si todos los operandos son falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX nor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.nor(operand1, operand2, extraOperands);
    }

    /**
     * La disyunción exclusiva no-asociativa (NAXOR) es un operador lógico que resulta en verdadero si uno y solo uno de los operandos es verdadero.
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si uno y solo uno de los operandos es verdadero; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX naxor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.naxor(operand1, operand2, extraOperands);
    }

    /**
     * La equivalencia no-asociativa (NAXNOR) es un operador lógico que resulta en verdadero si todos los operandos son verdadero ó si todos son
     * falso. Es equivalente a la negación (NOT) de la disyunción exclusiva no-asociativa (NAXOR).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si todos los operandos son verdadero ó si todos son falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX naxnor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.naxnor(operand1, operand2, extraOperands);
    }

    /**
     * NOR_OR_NAXOR es un operador lógico compuesto que resulta en verdadero si uno y solo uno de los operandos es verdadero ó si todos son falso. Es
     * equivalente a la disyunción (OR) de la negación conjunta (NOR) y la disyunción exclusiva no-asociativa (NAXOR). Con solo dos operandos también
     * es equivalente a la negación alternativa (NAND).
     *
     * @param operand1 X
     * @param operand2 Y
     * @param extraOperands operandos adicionales
     * @return verdadero si uno y solo uno de los operandos es verdadero ó si todos son falso; de lo contrario, falso.
     */
    protected static BooleanDataAggregateX norOrNaxor(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.norOrNaxor(operand1, operand2, extraOperands);
    }

    /**
     * La negación (NOT), también llamada complemento lógico, es un operador lógico que resulta en verdadero si el operando es falso, y viceversa.
     *
     * @param x operando X
     * @return verdadero si el operando es falso, y viceversa.
     */
    protected static BooleanScalarX not(BooleanExpression x) {
        return x.not();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special expression building protected static methods">
    /**
     * El método <b>exists</b> contruye una expresión lógica para la evaluación del resultado del query que recibe como argumento (operando X). La
     * evaluación resulta en verdadero si el resultado de X es verdadero.
     *
     * @param x operando X
     * @return segmento de selección para la evaluación del resultado del query nativo que recibe como argumento (operando X).
     */
    protected static BooleanComparisonX exists(String x) {
        return XB.AnyType.Comparison.exists(x);
    }

    /**
     * El método <b>exists</b> contruye un segmento de selección para la evaluación del resultado del query nativo que recibe como argumento (operando
     * X). La evaluación resulta en verdadero si el resultado de X es verdadero.
     *
     * @param x operando X
     * @return segmento de selección para la evaluación del resultado del query nativo que recibe como argumento (operando X).
     */
    protected static NativeQuerySegment exists(NativeQuery x) {
        return NativeQuerySegment.of(XB.AnyType.Comparison.exists(x));
    }

    /**
     * El método <b>exists</b> contruye una expresión lógica para la evaluación del resultado del query que recibe como argumento (operando X). La
     * evaluación resulta en verdadero si el resultado de X es falso.
     *
     * @param x operando X
     * @return segmento de selección para la evaluación del resultado del query nativo que recibe como argumento (operando X).
     */
    protected static BooleanComparisonX notExists(String x) {
        return XB.AnyType.Comparison.notExists(x);
    }

    /**
     * El método <b>exists</b> contruye un segmento de selección para la evaluación del resultado del query nativo que recibe como argumento (operando
     * X). La evaluación resulta en verdadero si el resultado de X es falso.
     *
     * @param x operando X
     * @return segmento de selección para la evaluación del resultado del query nativo que recibe como argumento (operando X).
     */
    protected static NativeQuerySegment notExists(NativeQuery x) {
        return NativeQuerySegment.of(XB.AnyType.Comparison.notExists(x));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Project's private static methods">
    private static boolean getDatabaseOperationConfirmationRequired(boolean unspecified) {
        Project project = TLC.getProject();
        return project != null && project.getDatabaseOperationConfirmationRequired().toBoolean(unspecified);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(Entity.class);

    private static final String EOL = "\n";

    /**
     *
     */
    private boolean _initialised;
//
//  /**
//   *
//   */
//  private boolean _prepared;

    /**
     *
     */
    private boolean _settled;

    /**
     *
     */
    private char _settler = '?';

    /**
     *
     */
    private Project _project;

    /**
     *
     */
    private boolean _rootInstance;

    /**
     *
     */
    private boolean _explicitlyDeclared;

    /**
     *
     */
    private boolean _implicitlyDeclared;

    /**
     *
     */
    private int _referenceIndex;

    /**
     *
     */
    private final Map<LocaleEntityReference, String> _localizedLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<LocaleEntityReference, String> _localizedShortLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<LocaleEntityReference, String> _localizedCollectionLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<LocaleEntityReference, String> _localizedCollectionShortLabelByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<LocaleDisplayFormat, String> _localizedMenuOptionLabelByDisplayFormatMap = new LinkedHashMap<>();

    /**
     *
     */
    private final EntityAtlas _atlas = new EntityAtlas(this);

    /**
     * baseClass: concrete entity superclass, if any (null otherwise)
     */
    private Class<?> _baseClass;

    /**
     * subclasses: direct known subclasses
     */
    private final Map<String, Class<?>> _subclasses = new LinkedHashMap<>();

    /**
     * subentities: known extensions
     */
    private final Map<String, Entity> _subentities = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<String, AllocationOverride> _allocationOverrides = new LinkedHashMap<>();

    /**
     *
     */
    private String _superViewName, _supraViewName;

    /**
     *
     */
    private String _primaryKeyFieldName;

    /**
     *
     */
    private Field _primaryKeyField;

    /**
     * primaryKeyProperty: reference to the entity primary key property, if any (null otherwise)
     */
    private Property _primaryKeyProperty;

    /**
     *
     */
    private String _sequenceFieldName;

    /**
     *
     */
    private Field _sequenceField;

    /**
     * sequenceProperty: reference to the entity sequence property, if any (null otherwise)
     */
    private LongProperty _sequenceProperty;

    /**
     *
     */
    private String _versionFieldName;

    /**
     *
     */
    private Field _versionField;

    /**
     * versionProperty: reference to the entity version property, if any (null otherwise)
     */
    private LongProperty _versionProperty;

    /**
     *
     */
    private String _numericKeyFieldName;

    /**
     *
     */
    private Field _numericKeyField;

    /**
     * numericKeyProperty: reference to the entity numeric key property, if any (null otherwise)
     */
    private IntegerProperty _numericKeyProperty;

    /**
     *
     */
    private String _characterKeyFieldName;

    /**
     *
     */
    private Field _characterKeyField;

    /**
     * characterKeyProperty: reference to the entity character key property, if any (null otherwise)
     */
    private StringProperty _characterKeyProperty;

    /**
     *
     */
    private String _nameFieldName;

    /**
     *
     */
    private Field _nameField;

    /**
     * nameProperty: reference to the entity name (short description) property, if any (null otherwise)
     */
    private StringProperty _nameProperty;

    /**
     *
     */
    private String _descriptionFieldName;

    /**
     *
     */
    private Field _descriptionField;

    /**
     * descriptionProperty: reference to the entity description property, if any (null otherwise)
     */
    private StringProperty _descriptionProperty;

    /**
     *
     */
    private String _imageFieldName;

    /**
     *
     */
    private Field _imageField;

    /**
     * imageProperty: reference to the entity image property, if any (null otherwise)
     */
    private BinaryProperty _imageProperty;

    /**
     *
     */
    private String _inactiveIndicatorFieldName;

    /**
     *
     */
    private Field _inactiveIndicatorField;

    /**
     * inactiveIndicatorProperty: reference to the entity inactive indicator property, if any (null otherwise)
     */
    private BooleanProperty _inactiveIndicatorProperty;

    /**
     *
     */
    private boolean _foreignInactiveIndicatorProperty;

    /**
     *
     */
    private String _urlFieldName;

    /**
     *
     */
    private Field _urlField;

    /**
     * urlProperty: reference to the entity URL property, if any (null otherwise)
     */
    private StringProperty _urlProperty;

    /**
     *
     */
    private String _parentFieldName;

    /**
     *
     */
    private Field _parentField;

    /**
     * parentProperty: parent entity reference, if any (null otherwise)
     */
    private EntityReference _parentProperty;

    /**
     *
     */
    private String _ownerFieldName;

    /**
     *
     */
    private Field _ownerField;

    /**
     * ownerProperty: owner (user) entity reference, if any (null otherwise)
     */
    private EntityReference _ownerProperty;

    /**
     *
     */
    private boolean _foreignOwnerProperty;

    /**
     *
     */
    private String _userFieldName;

    /**
     *
     */
    private Field _userField;

    /**
     * userProperty: user entity reference, if any (null otherwise)
     */
    private EntityReference _userProperty;

    /**
     *
     */
    private String _segmentFieldName;

    /**
     *
     */
    private Field _segmentField;

    /**
     * segmentProperty: reference to the entity segment property, if any (null otherwise)
     */
    private DataArtifact _segmentProperty; // since 20210218

    /**
     *
     */
    private boolean _foreignSegmentProperty;

    /**
     *
     */
    private String _businessKeyFieldName;

    /**
     *
     */
    private Field _businessKeyField;

    /**
     * businessKeyProperty: reference to the entity business key property, if any (null otherwise)
     */
    private Property _businessKeyProperty;

    /**
     * businessKeyType: character key, numeric key or unspecified
     */
    private final BusinessKeyType _businessKeyType = BusinessKeyType.UNSPECIFIED;

    /**
     *
     */
    private String _stateFieldName;

    /**
     *
     */
    private Field _stateField;

    /**
     *
     */
    private EntityReference _stateProperty;

    /**
     * basicOperationEntity: basic operation entity indicator
     */
    private boolean _basicOperationEntity;

    /**
     * catalogEntity: catalog entity indicator
     */
    private boolean _catalogEntity;

    /**
     * existentiallyIndependent: existentially independent entity indicator
     */
    private boolean _existentiallyIndependent = true;

    /**
     * entityViewType: independent, master/detail, both or unspecified
     */
    private EntityViewType _entityViewType = EntityViewType.UNSPECIFIED;

    /**
     * variantEntity: variant entity indicator
     */
    private boolean _variantEntity;

    /**
     * resourceType: configuration, operation or unspecified
     */
    private ResourceType _resourceType = ResourceType.UNSPECIFIED;

    /**
     * resourceGender: masculine, feminine, common, neuter or unspecified
     */
    private ResourceGender _resourceGender = ResourceGender.UNSPECIFIED;

    /**
     *
     */
    private final String _propertiesPrefix = "";

    /**
     *
     */
    private final String _propertiesSuffix = "";

    /**
     *
     */
    private String _collectionName = "";

    /**
     *
     */
    private String _helpDocument = "";

    /**
     *
     */
    private String _helpFileName = "";

    /**
     *
     */
    private HelpFileAutoName _helpFileAutoName = HelpFileAutoName.NONE;

    /**
     *
     */
    private String _helpFileAutoType = ""; // Constants.DEFAULT_HELP_FILE_TYPE; until 10/09/2023

    /**
     *
     */
    private int _startWith = 1;

    /**
     *
     */
    private int _seriesStart = 1;

    /**
     *
     */
    private int _seriesStop = 100;

    /**
     *
     */
    private int _seriesStep = 1;

    /**
     *
     */
    private boolean _selectEnabled = true;

    /**
     *
     */
    private OperationAccess _selectOperationAccess = OperationAccess.UNSPECIFIED;

    /**
     *
     */
    private SelectOnloadOption _selectOnloadOption = SelectOnloadOption.DEFAULT;

    /**
     *
     */
    private int _selectRowsLimit = Constants.DEFAULT_SELECT_ROWS_LIMIT;

    /**
     *
     */
    SortOption _selectSortOption = SortOption.ASC;

    /**
     *
     */
    private boolean _insertEnabled = !isEnumerationEntity();

    /**
     *
     */
    private boolean _insertConfirmationRequired = getDatabaseOperationConfirmationRequired(false);

    /**
     *
     */
    private OperationAccess _insertOperationAccess = OperationAccess.UNSPECIFIED;

    /**
     *
     */
    private OperationLogging _insertLogging = OperationLogging.UNSPECIFIED;

    /**
     *
     */
    private boolean _updateEnabled = true;

    /**
     *
     */
    private boolean _updateConfirmationRequired = getDatabaseOperationConfirmationRequired(false);

    /**
     *
     */
    private OperationAccess _updateOperationAccess = OperationAccess.UNSPECIFIED;

    /**
     *
     */
    private OperationLogging _updateLogging = OperationLogging.UNSPECIFIED;

    /**
     *
     */
    private boolean _deleteEnabled = !isEnumerationEntity();

    /**
     *
     */
    private boolean _deleteConfirmationRequired = getDatabaseOperationConfirmationRequired(true);

    /**
     *
     */
    private OperationAccess _deleteOperationAccess = OperationAccess.UNSPECIFIED;

    /**
     *
     */
    private OperationLogging _deleteLogging = OperationLogging.UNSPECIFIED;

    /**
     *
     */
    private boolean _reportEnabled = !isEnumerationEntity();

    /**
     *
     */
    private int _reportRowsLimit = Constants.DEFAULT_REPORT_ROWS_LIMIT;

    /**
     *
     */
    SortOption _reportSortOption = SortOption.ASC;

    /**
     *
     */
    private boolean _exportEnabled = !isEnumerationEntity();

    /**
     *
     */
    private int _exportRowsLimit = Constants.DEFAULT_EXPORT_ROWS_LIMIT;

    /**
     *
     */
    SortOption _exportSortOption = SortOption.ASC;

    /**
     *
     */
    private Boolean _foreignEntityClass;

    /**
     *
     */
    private Boolean _privateEntityClass;

    /**
     *
     */
    private Boolean _displayAvailable;

    /**
     *
     */
    private boolean _linkOuterChildren = false;

    /**
     *
     */
    private boolean _linkOuterCollaterals = false;

    /**
     *
     */
    private boolean _linkOuterSiblings = false;

    /**
     *
     */
    private Boolean _tableViewEnabled;

    /**
     *
     */
    private boolean _tableViewWithInsertEnabled = true;

    /**
     *
     */
    private boolean _tableViewWithUpdateEnabled = true;

    /**
     *
     */
    private boolean _tableViewWithDeleteEnabled = true;

    /**
     *
     */
    private boolean _tableViewWithMasterHeading = true;

    /**
     *
     */
    private boolean _tableViewWithQuickFilterSnippet = false;

    /**
     *
     */
    private boolean _tableViewWithStickyHeader = false;

    /**
     *
     */
    private int _tableViewRowsLimit = Constants.DEFAULT_ROWS_PER_PAGE_LIMIT;

    /**
     *
     */
    private int _tableViewRows = Constants.DEFAULT_ROWS_PER_PAGE;

    /**
     *
     */
    private TableResponsiveMode _tableResponsiveMode = TableResponsiveMode.UNSPECIFIED;

    /**
     *
     */
    private ViewMenuOption _tableViewMenuOption = ViewMenuOption.ALL;

    /**
     *
     */
    private String _tableViewHelpDocument = "";

    /**
     *
     */
    private String _tableViewHelpFileName = "";

    /**
     *
     */
    private String _readingTableViewHeadSnippetFileName = "";

    /**
     *
     */
    private String _readingTableViewEasternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _readingTableViewWesternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _readingTableViewAboveTableSnippetFileName = "";

    /**
     *
     */
    private String _readingTableViewBelowTableSnippetFileName = "";

    /**
     *
     */
    private String _readingTableViewRowActionSnippetFileName = "";

    /**
     *
     */
    private String _readingTableViewRowStatusSnippetFileName = "";

    /**
     *
     */
    private String _readingTableViewRowNumberSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewHeadSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewEasternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewWesternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewAboveTableSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewBelowTableSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewRowActionSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewRowStatusSnippetFileName = "";

    /**
     *
     */
    private String _writingTableViewRowNumberSnippetFileName = "";

    /**
     *
     */
    private boolean _detailViewEnabled = !isEnumerationEntity();

    /**
     *
     */
    private boolean _detailViewWithMasterHeading = true;

    /**
     *
     */
    private ViewMenuOption _detailViewMenuOption = ViewMenuOption.NONE;

    /**
     *
     */
    private ViewMenuOptionOpenAction _detailViewMenuOptionOpenAction = ViewMenuOptionOpenAction.DEFAULT;

    /**
     *
     */
    private String _detailViewHelpDocument = "";

    /**
     *
     */
    private String _detailViewHelpFileName = "";

    /**
     *
     */
    private String _readingDetailViewHeadSnippetFileName = "";

    /**
     *
     */
    private String _readingDetailViewEasternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _readingDetailViewWesternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _readingDetailViewAboveDetailSnippetFileName = "";

    /**
     *
     */
    private String _readingDetailViewBelowDetailSnippetFileName = "";

    /**
     *
     */
    private String _writingDetailViewHeadSnippetFileName = "";

    /**
     *
     */
    private String _writingDetailViewEasternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _writingDetailViewWesternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _writingDetailViewAboveDetailSnippetFileName = "";

    /**
     *
     */
    private String _writingDetailViewBelowDetailSnippetFileName = "";

    /**
     *
     */
    private boolean _treeViewEnabled;

    /**
     *
     */
    private ViewMenuOption _treeViewMenuOption = ViewMenuOption.NONE;

    /**
     *
     */
    private int _treeViewRootLimit = Constants.DEFAULT_ROWS_PER_PAGE_LIMIT;

    /**
     *
     */
    private int _treeViewBranchLimit = Constants.DEFAULT_ROWS_PER_PAGE_LIMIT;

    /**
     *
     */
    private String _treeViewHelpDocument = "";

    /**
     *
     */
    private String _treeViewHelpFileName = "";

    /**
     *
     */
    private String _readingTreeViewHeadSnippetFileName = "";

    /**
     *
     */
    private String _readingTreeViewEasternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _readingTreeViewWesternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _readingTreeViewAboveTreeSnippetFileName = "";

    /**
     *
     */
    private String _readingTreeViewBelowTreeSnippetFileName = "";

    /**
     *
     */
    private String _writingTreeViewHeadSnippetFileName = "";

    /**
     *
     */
    private String _writingTreeViewEasternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _writingTreeViewWesternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _writingTreeViewAboveTreeSnippetFileName = "";

    /**
     *
     */
    private String _writingTreeViewBelowTreeSnippetFileName = "";

    /**
     *
     */
    private boolean _consoleViewEnabled = !isEnumerationEntity();

    /**
     *
     */
    private boolean _consoleViewMenuOption = !isEnumerationEntity();

    /**
     *
     */
    private String _consoleViewHelpDocument = "";

    /**
     *
     */
    private String _consoleViewHelpFileName = "";

    /**
     *
     */
    private String _consoleViewHeadSnippetFileName = "";

    /**
     *
     */
    private String _consoleViewEasternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _consoleViewWesternToolbarSnippetFileName = "";

    /**
     *
     */
    private String _consoleViewAboveProcessingPanelSnippetFileName = "";

    /**
     *
     */
    private String _consoleViewBelowProcessingPanelSnippetFileName = "";

    /**
     *
     */
    private String _aboveMasterHeadingSnippetFileName = "";

    /**
     *
     */
    private String _belowMasterHeadingSnippetFileName = "";

    /**
     *
     */
    private String _insideMasterHeadingSnippetFileName = "";

    /**
     *
     */
    private boolean _warningsEnabled = true;

    /**
     *
     */
    private boolean _businessKeyWarningsEnabled = true;

    /**
     *
     */
    boolean _discriminatorPropertyWarningsEnabled = true;

    /**
     *
     */
    private boolean _propertiesWithoutStepWarningsEnabled = true;

    /**
     *
     */
    private boolean _treeViewWarningsEnabled = true;

    /**
     *
     */
    private boolean _triggersWarningsEnabled = true;

    /**
     *
     */
    boolean _versionPropertyWarningsEnabled = true;

    /**
     *
     */
    private boolean _visibleFieldsWarningsEnabled = true;

    /**
     *
     */
    private boolean _stepCreatableFieldsWarningsEnabled = true;

    /**
     *
     */
    private boolean _stepUpdatableFieldsWarningsEnabled = true;

    /**
     *
     */
    private boolean _specialExpressionsWarningsEnabled = true;

    /**
     *
     */
    private boolean _unusualExpressionsWarningsEnabled = true;

    /**
     *
     */
    private boolean _bplCodeGenEnabled = Project.getDefaultEntityCodeGenBPL();

    /**
     *
     */
    private boolean _bwsCodeGenEnabled = Project.getDefaultEntityCodeGenBWS(); // && !isEnumerationEntity();

    /**
     *
     */
    private boolean _daoCodeGenEnabled = Project.getDefaultEntityCodeGenDAO();

    /**
     *
     */
    private boolean _dafCodeGenEnabled = Project.getDefaultEntityCodeGenDAF();

    /**
     *
     */
    private boolean _fwsCodeGenEnabled = Project.getDefaultEntityCodeGenFWS(); // && !isEnumerationEntity();

    /**
     *
     */
    private boolean _guiCodeGenEnabled = Project.getDefaultEntityCodeGenGUI();

    /**
     *
     */
    private boolean _sqlCodeGenEnabled = Project.getDefaultEntityCodeGenSQL();

    /**
     *
     */
    private boolean _smcCodeGenEnabled = Project.getDefaultEntityCodeGenSMC();

    /**
     *
     */
    private boolean _entityClassDiagramGenEnabled = true;

    /**
     *
     */
    private boolean _entityStateDiagramGenEnabled = true;

    /**
     *
     */
    private boolean _entityInsertActivityDiagramGenEnabled = true;

    /**
     *
     */
    private boolean _entityUpdateActivityDiagramGenEnabled = true;

    /**
     *
     */
    private String _jsonSerializerClassName = "";

    /**
     *
     */
    private String _jsonDeserializerClassName = "";

    /**
     * annotated with EntityReferenceDisplay
     */
    private boolean _referenceAvatar = true;

    /**
     * annotated with EntityReferenceConversionValidation
     */
    private boolean _customConverterEntityReference = false;

    /**
     * annotated with EntityReferenceConversionValidation
     */
    private boolean _restrictedAccessEntityReference = true;

    /**
     * annotated with EntityReferenceDisplay
     */
    private EntityReferenceStyle _referenceStyle = EntityReferenceStyle.UNSPECIFIED;

    /**
     * annotated with EntityReferenceDisplay
     */
    private EntityReferenceProperty _referenceFilterBy = EntityReferenceProperty.UNSPECIFIED;

    /**
     * annotated with EntityReferenceDisplay
     */
    private EntityReferenceFilterType _referenceFilterType = EntityReferenceFilterType.UNSPECIFIED;

    /**
     * annotated with EntityReferenceDisplay
     */
    private EntityReferenceProperty _referenceSortBy = EntityReferenceProperty.UNSPECIFIED;

    /**
     * annotated with EntityReferenceSearch
     */
    private SearchType _searchType = SearchType.UNSPECIFIED;

    /**
     * annotated with EntityReferenceSearch
     */
    private ListStyle _listStyle = ListStyle.UNSPECIFIED;

    /**
     * annotated with EntityReferenceSearch
     */
    private int _radioColumns;

    /**
     * annotated with EntityReferenceSearch
     */
    private boolean _descendingOrder;

    /**
     * annotated with EntityReferenceSearch
     */
    private SearchDisplayFormat _searchDisplayFormat = SearchDisplayFormat.UNSPECIFIED;

    /**
     * annotated with EntityReferenceSearch
     */
    private DisplayMode _searchDisplayMode = DisplayMode.UNSPECIFIED;

    /**
     * annotated with EntityViewLocation
     */
    private String _applicationOrigin, _applicationContextRoot, _applicationConsolePath, _applicationReadingPath, _applicationWritingPath;

    /**
     * search query filter
     */
    private BooleanExpression _searchQueryFilter;

    /**
     * search value filter
     */
    private final Map<Property, Object> _searchValueFilter = new LinkedHashMap<>();

    private boolean _searchValueFilterFromSearchQueryFilter = true;

    private boolean _searchValueFilterFromSearchQueryFilterExplicitlySet;

    /**
     * select filter
     */
    private BooleanExpression _selectFilter;

    /**
     * insert filter
     */
    private final Map<EntityReference, BooleanExpression> _insertFilterByReferenceMap = new LinkedHashMap<>();

    /**
     * update filter
     */
    private BooleanExpression _updateFilter;

    /**
     * delete filter
     */
    private BooleanExpression _deleteFilter;

    private boolean _updateFilterWhereverPossible, _deleteFilterWhereverPossible;

    /**
     * master/detail filter
     */
    private final Map<EntityReference, BooleanExpression> _masterDetailFilterByReferenceMap = new LinkedHashMap<>();

    /**
     *
     */
    private boolean _filterInactiveIndicatorProperty;

    /**
     *
     */
    private boolean _filterOwnerProperty;

    /**
     *
     */
    private boolean _filterSegmentProperty;

    /**
     *
     */
    private boolean _mainRelationship;

    /**
     *
     */
    private FetchType _fetchType = FetchType.UNSPECIFIED;

    /**
     *
     */
    private CascadeType[] _cascadeType = {CascadeType.UNSPECIFIED};

    /**
     *
     */
    private Navigability _navigability = Navigability.UNSPECIFIED;

    /**
     *
     */
    private EntityCollection _mappedCollection;

    /**
     *
     */
    private boolean _oneToOneDetailView;

    /**
     *
     */
    private boolean _masterSequenceMasterField;

    /**
     *
     */
    private MasterDetailView _masterDetailView = MasterDetailView.UNSPECIFIED;

    /**
     *
     */
    private int _masterDetailViewSequence = 0;

    /**
     *
     */
    private Kleenean _masterDetailViewMenuOptionEnabled = Kleenean.UNSPECIFIED;

    /**
     *
     */
    private QuickAddingFilter _quickAddingFilter = QuickAddingFilter.NONE;

    /**
     *
     */
    private boolean _keyPropertiesQueryMappingEnabled = true;

    /**
     *
     */
    private Object _calculableValue;

    /**
     *
     */
    private Object _initialValue;

    /**
     *
     */
    private Object _defaultValue;

    /**
     *
     */
    private Object _currentValue;

    /**
     *
     */
    private Object _orderBy;

    /**
     *
     */
    private Tab _defaultTab;

    /**
     * annotated with AbstractClass
     */
    private boolean _annotatedWithAbstractClass;

    /**
     * annotated with AllocationOverride
     */
    private boolean _annotatedWithAllocationOverride;

    /**
     * annotated with AllocationOverrides
     */
    private boolean _annotatedWithAllocationOverrides;

    /**
     * annotated with EntityClass
     */
    private boolean _annotatedWithEntityClass;

    /**
     * annotated with EntityDataGen
     */
    private boolean _annotatedWithEntityDataGen;

    /**
     * annotated with EntitySelectOperation
     */
    private boolean _annotatedWithEntitySelectOperation;

    /**
     * annotated with EntityInsertOperation
     */
    private boolean _annotatedWithEntityInsertOperation;

    /**
     * annotated with EntityUpdateOperation
     */
    private boolean _annotatedWithEntityUpdateOperation;

    /**
     * annotated with EntityDeleteOperation
     */
    private boolean _annotatedWithEntityDeleteOperation;

    /**
     * annotated with EntityReportOperation
     */
    private boolean _annotatedWithEntityReportOperation;

    /**
     * annotated with EntityExportOperation
     */
    private boolean _annotatedWithEntityExportOperation;

    /**
     * annotated with EntityTableView
     */
    private boolean _annotatedWithEntityTableView;

    /**
     * annotated with EntityDetailView
     */
    private boolean _annotatedWithEntityDetailView;

    /**
     * annotated with EntityTreeView
     */
    private boolean _annotatedWithEntityTreeView;

    /**
     * annotated with EntityConsoleView
     */
    private boolean _annotatedWithEntityConsoleView;

    /**
     * annotated with EntityMasterView
     */
    private boolean _annotatedWithEntityMasterView;

    /**
     * annotated with EntityWarnings
     */
    private boolean _annotatedWithEntityWarnings;

    /**
     * annotated with EntityJsonCustomization
     */
    private boolean _annotatedWithEntityJsonCustomization;

    /**
     * annotated with EntityCodeGen
     */
    private boolean _annotatedWithEntityCodeGen;

    /**
     * annotated with EntityDocGen
     */
    private boolean _annotatedWithEntityDocGen;

    /**
     * annotated with EntityReferenceConversionValidation
     */
    private boolean _annotatedWithEntityReferenceConversionValidation;

    /**
     * annotated with EntityReferenceDisplay
     */
    private boolean _annotatedWithEntityReferenceDisplay;

    /**
     * annotated with EntityReferenceSearch
     */
    private boolean _annotatedWithEntityReferenceSearch;

    /**
     * annotated with EntityViewLocation
     */
    private boolean _annotatedWithEntityViewLocation;

    /**
     * annotated with Filter
     */
    private boolean _annotatedWithFilter;

    /**
     * annotated with OneToOne
     */
    private boolean _annotatedWithOneToOne;

    /**
     * annotated with ManyToOne
     */
    private boolean _annotatedWithManyToOne;

    /**
     * annotated with QueryMapping
     */
    private boolean _annotatedWithQueryMapping;

    /**
     * annotated with EntityReferenceDataGen
     */
    private boolean _annotatedWithEntityReferenceDataGen;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return the initialised indicator
     */
    @Override
    public boolean isInitialised() {
        return _initialised;
    }
//
//  /**
//   * @return the prepared indicator
//   */
//  @Override
//  public boolean isPrepared() {
//      return _prepared;
//  }

    /**
     * @return the settled indicator
     */
    @Override
    public boolean isSettled() {
        return _settled;
    }

    /**
     * @return the settler
     */
    @Override
    protected char settler() {
        return _settler;
    }

    /**
     * @return the root-instance indicator
     */
    @Override
    public boolean isRootInstance() {
        return _rootInstance;
    }

    /**
     * @return the explicitly declared indicator
     */
    @Override
    public boolean isExplicitlyDeclared() {
        return _explicitlyDeclared;
    }

    /**
     * @return the implicitly declared indicator
     */
    @Override
    public boolean isImplicitlyDeclared() {
        return _implicitlyDeclared;
    }

    /**
     * @return the reference index
     */
    @Override
    public int getReferenceIndex() {
        return _referenceIndex;
    }

    /**
     * @param referenceIndex the reference index to set
     */
    void setReferenceIndex(int referenceIndex) {
        _referenceIndex = referenceIndex;
    }

    /**
     * @param reference the referenced entity
     * @return the default label
     */
    @Override
    public String getDefaultLabel(EntityReference reference) {
        return getLocalizedLabel(null, reference);
    }

    /**
     * El método setDefaultLabel se utiliza para establecer la etiqueta de la entidad en las vistas (páginas) Maestro/Detalle, con el maestro
     * identificado por el parámetro reference, que se almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el
     * idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el
     * valor de la etiqueta.
     *
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param defaultLabel sustantivo singular que se usa como etiqueta de la entidad
     */
//  @Override
    public void setDefaultLabel(EntityReference reference, String defaultLabel) {
        setLocalizedLabel(null, reference, defaultLabel);
    }

    /**
     * @param reference the referenced entity
     * @return the default short label
     */
    @Override
    public String getDefaultShortLabel(EntityReference reference) {
        return getLocalizedShortLabel(null, reference);
    }

    /**
     * El método setDefaultShortLabel se utiliza para establecer la etiqueta corta de la entidad en las vistas (páginas) Maestro/Detalle, con el
     * maestro identificado por el parámetro reference, que se almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos
     * para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para
     * obtener el valor de la etiqueta.
     *
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param defaultShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta de la entidad
     */
//  @Override
    public void setDefaultShortLabel(EntityReference reference, String defaultShortLabel) {
        setLocalizedShortLabel(null, reference, defaultShortLabel);
    }

    /**
     * @param reference the referenced entity
     * @return the default collection label
     */
    @Override
    public String getDefaultCollectionLabel(EntityReference reference) {
        return getLocalizedCollectionLabel(null, reference);
    }

    /**
     * El método setDefaultCollectionLabel se utiliza para establecer la etiqueta de colección de la entidad en las vistas (páginas) Maestro/Detalle,
     * con el maestro identificado por el parámetro reference, que se almacena en el archivo de recursos por defecto. En caso de que el archivo de
     * recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto
     * para obtener el valor de la etiqueta.
     *
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param defaultCollectionLabel sustantivo plural que se usa como etiqueta de colección de la entidad
     */
//  @Override
    public void setDefaultCollectionLabel(EntityReference reference, String defaultCollectionLabel) {
        setLocalizedCollectionLabel(null, reference, defaultCollectionLabel);
    }

    /**
     * @param reference the referenced entity
     * @return the default collection short label
     */
    @Override
    public String getDefaultCollectionShortLabel(EntityReference reference) {
        return getLocalizedCollectionShortLabel(null, reference);
    }

    /**
     * El método setDefaultCollectionShortLabel se utiliza para establecer la etiqueta corta de colección de la entidad en las vistas (páginas)
     * Maestro/Detalle, con el maestro identificado por el parámetro reference, que se almacena en el archivo de recursos por defecto. En caso de que
     * el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de
     * recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param defaultCollectionShortLabel sustantivo plural, preferiblemente sin complementos, que se usa como etiqueta corta de colección de la
     * entidad
     */
//  @Override
    public void setDefaultCollectionShortLabel(EntityReference reference, String defaultCollectionShortLabel) {
        setLocalizedCollectionShortLabel(null, reference, defaultCollectionShortLabel);
    }

    /**
     * @param format the display format
     * @return the localized menu option label
     */
    @Override
    public String getDefaultMenuOptionLabel(DisplayFormat format) {
        return getLocalizedMenuOptionLabel(null, format);
    }

    /**
     * El método setDefaultMenuOptionLabel se utiliza para establecer la etiqueta de la opción de menú de las vistas (páginas) de la entidad con el
     * formato identificado por el parámetro format, que se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de
     * recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto
     * para obtener el valor de la etiqueta.
     *
     * @param format formato de la vista (página)
     * @param defaultMenuOptionLabel texto que se usa como etiqueta de la opción de menú
     */
//  @Override
    public void setDefaultMenuOptionLabel(DisplayFormat format, String defaultMenuOptionLabel) {
        setLocalizedMenuOptionLabel(null, format, defaultMenuOptionLabel);
    }

    /**
     * @param locale the locale for the label
     * @param reference the referenced entity
     * @return the localized label
     */
    @Override
    public String getLocalizedLabel(Locale locale, EntityReference reference) {
        if (reference == null) {
            return null;
        }
        LocaleEntityReference ler = localeEntityReferenceReadingKey(locale, reference);
        return _localizedLabelByReferenceMap.get(ler);
    }

    /**
     * El método setLocalizedLabel se utiliza para establecer la etiqueta de la entidad en las vistas (páginas) Maestro/Detalle, con el maestro
     * identificado por el parámetro reference, que se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de
     * recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto
     * para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param localizedLabel sustantivo singular que se usa como etiqueta de la entidad
     */
//  @Override
    public void setLocalizedLabel(Locale locale, EntityReference reference, String localizedLabel) {
        checkScope();
        if (reference != null) {
            LocaleEntityReference ler = localeEntityReferenceWritingKey(locale, reference);
            if (localizedLabel == null) {
                _localizedLabelByReferenceMap.remove(ler);
            } else {
                _localizedLabelByReferenceMap.put(ler, localizedLabel);
            }
        }
    }

    /**
     * @param locale the locale for the label
     * @param reference the referenced entity
     * @return the localized short label
     */
    @Override
    public String getLocalizedShortLabel(Locale locale, EntityReference reference) {
        if (reference == null) {
            return null;
        }
        LocaleEntityReference ler = localeEntityReferenceReadingKey(locale, reference);
        return _localizedShortLabelByReferenceMap.get(ler);
    }

    /**
     * El método setLocalizedShortLabel se utiliza para establecer la etiqueta corta de la entidad en las vistas (páginas) Maestro/Detalle, con el
     * maestro identificado por el parámetro reference, que se almacena en el archivo de recursos de configuración regional. En caso de que el archivo
     * de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por
     * defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param localizedShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta de la entidad
     */
//  @Override
    public void setLocalizedShortLabel(Locale locale, EntityReference reference, String localizedShortLabel) {
        checkScope();
        if (reference != null) {
            LocaleEntityReference ler = localeEntityReferenceWritingKey(locale, reference);
            if (localizedShortLabel == null) {
                _localizedShortLabelByReferenceMap.remove(ler);
            } else {
                _localizedShortLabelByReferenceMap.put(ler, localizedShortLabel);
            }
        }
    }

    /**
     * @param locale the locale for the label
     * @param reference the referenced entity
     * @return the localized collection label
     */
    @Override
    public String getLocalizedCollectionLabel(Locale locale, EntityReference reference) {
        if (reference == null) {
            return null;
        }
        LocaleEntityReference ler = localeEntityReferenceReadingKey(locale, reference);
        return _localizedCollectionLabelByReferenceMap.get(ler);
    }

    /**
     * El método setLocalizedCollectionLabel se utiliza para establecer la etiqueta de colección de la entidad en las vistas (páginas)
     * Maestro/Detalle, con el maestro identificado por el parámetro reference, que se almacena en el archivo de recursos de configuración regional.
     * En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el
     * archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param localizedCollectionLabel sustantivo plural que se usa como etiqueta de colección de la entidad
     */
//  @Override
    public void setLocalizedCollectionLabel(Locale locale, EntityReference reference, String localizedCollectionLabel) {
        checkScope();
        if (reference != null) {
            LocaleEntityReference ler = localeEntityReferenceWritingKey(locale, reference);
            if (localizedCollectionLabel == null) {
                _localizedCollectionLabelByReferenceMap.remove(ler);
            } else {
                _localizedCollectionLabelByReferenceMap.put(ler, localizedCollectionLabel);
            }
        }
    }

    /**
     * @param locale the locale for the label
     * @param reference the referenced entity
     * @return the localized collection short label
     */
    @Override
    public String getLocalizedCollectionShortLabel(Locale locale, EntityReference reference) {
        if (reference == null) {
            return null;
        }
        LocaleEntityReference ler = localeEntityReferenceReadingKey(locale, reference);
        return _localizedCollectionShortLabelByReferenceMap.get(ler);
    }

    /**
     * El método setLocalizedCollectionShortLabel se utiliza para establecer la etiqueta corta de colección de la entidad en las vistas (páginas)
     * Maestro/Detalle, con el maestro identificado por el parámetro reference, que se almacena en el archivo de recursos de configuración regional.
     * En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el
     * archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param reference entidad que hace la función de maestro en la vista Maestro/Detalle
     * @param localizedCollectionShortLabel sustantivo plural, preferiblemente sin complementos, que se usa como etiqueta corta de colección de la
     * entidad
     */
//  @Override
    public void setLocalizedCollectionShortLabel(Locale locale, EntityReference reference, String localizedCollectionShortLabel) {
        checkScope();
        if (reference != null) {
            LocaleEntityReference ler = localeEntityReferenceWritingKey(locale, reference);
            if (localizedCollectionShortLabel == null) {
                _localizedCollectionShortLabelByReferenceMap.remove(ler);
            } else {
                _localizedCollectionShortLabelByReferenceMap.put(ler, localizedCollectionShortLabel);
            }
        }
    }

    /**
     * @param locale the locale for the label
     * @param format the display format
     * @return the localized menu option label
     */
    @Override
    public String getLocalizedMenuOptionLabel(Locale locale, DisplayFormat format) {
        if (format == null || format.equals(DisplayFormat.UNSPECIFIED)) {
            return null;
        }
        LocaleDisplayFormat ldf = localeDisplayFormatReadingKey(locale, format);
        return _localizedMenuOptionLabelByDisplayFormatMap.get(ldf);
    }

    /**
     * El método setLocalizedMenuOptionLabel se utiliza para establecer la etiqueta de la opción de menú de las vistas (páginas) de la entidad con el
     * formato identificado por el parámetro format, que se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de
     * recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto
     * para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param format formato de la vista (página)
     * @param localizedMenuOptionLabel texto que se usa como etiqueta de la opción de menú
     */
//  @Override
    public void setLocalizedMenuOptionLabel(Locale locale, DisplayFormat format, String localizedMenuOptionLabel) {
        checkScope();
        if (format != null && !format.equals(DisplayFormat.UNSPECIFIED)) {
            LocaleDisplayFormat ldf = localeDisplayFormatWritingKey(locale, format);
            if (localizedMenuOptionLabel == null) {
                _localizedMenuOptionLabelByDisplayFormatMap.remove(ldf);
            } else {
                _localizedMenuOptionLabelByDisplayFormatMap.put(ldf, localizedMenuOptionLabel);
            }
        }
    }

    @Override
    public int getReferencePropertiesCount() {
        return _atlas.referencePropertiesCount();
    }

    @Override
    public List<Property> getPropertiesList() {
        return _atlas.getPropertiesList();
    }

    @Override
    public List<EntityCollection> getEntityCollectionsList() {
        return _atlas.getEntityCollectionsList();
    }

    public List<EntityCollection> getThisEntityCollectionsList() {
        List<EntityCollection> list = new ArrayList<>();
        for (EntityCollection entityCollection : _atlas.getEntityCollectionsList()) {
            if (!entityCollection.isInherited()) {
                list.add(entityCollection);
            }
        }
        return list;
    }

    @Override
    public List<Property> getReferencesList() {
        return _atlas.getReferencesList();
    }

    @Override
    public List<Parameter> getParameterReferencesList() {
        return _atlas.getParameterReferencesList();
    }

    @Override
    public List<Key> getKeysList() {
        return _atlas.getKeysList();
    }

    @Override
    public List<Step> getStepsList() {
        return _atlas.getStepsList();
    }

    @Override
    public List<Tab> getTabsList() {
        return _atlas.getTabsList();
    }

    @Override
    public List<View> getViewsList() {
        return _atlas.getViewsList();
    }

    @Override
    public List<Instance> getInstancesList() {
        return _atlas.getInstancesList();
    }

    @Override
    public List<NamedValue> getNamedValuesList() {
        return _atlas.getNamedValuesList();
    }

    @Override
    public List<Expression> getExpressionsList() {
        return _atlas.getExpressionsList();
    }

    @Override
    public List<Transition> getTransitionsList() {
        return _atlas.getTransitionsList();
    }

    @Override
    public List<Operation> getOperationsList() {
        return _atlas.getOperationsList();
    }

    @Override
    public List<Class<?>> getOperationClassesList() {
        return new ArrayList<>(getOperationClassesMap().values());
    }

    @Override
    public List<Trigger> getTriggersList() {
        return _atlas.getTriggersList();
    }

    @Override
    public List<Property> getCalculablePropertiesList() {
        return new ArrayList<>(getCalculablePropertiesMap().values());
    }

    @Override
    public List<Property> getOverlayPropertiesList() {
        return new ArrayList<>(getOverlayPropertiesMap().values());
    }

    @Override
    public List<Property> getQueryPropertiesList() {
        return new ArrayList<>(getQueryPropertiesMap().values());
    }

    @Override
    public Map<String, Property> getPropertiesMap() {
        return _atlas.getPropertiesMap();
    }

    @Override
    public Map<String, EntityCollection> getEntityCollectionsMap() {
        return _atlas.getEntityCollectionsMap();
    }

    @Override
    public Map<String, Property> getReferencesMap() {
        return _atlas.getReferencesMap();
    }

    @Override
    public Map<String, Parameter> getParameterReferencesMap() {
        return _atlas.getParameterReferencesMap();
    }

    @Override
    public Map<String, Key> getKeysMap() {
        return _atlas.getKeysMap();
    }

    @Override
    public Map<String, Step> getStepsMap() {
        return _atlas.getStepsMap();
    }

    @Override
    public Map<String, Tab> getTabsMap() {
        return _atlas.getTabsMap();
    }

    @Override
    public Map<String, View> getViewsMap() {
        return _atlas.getViewsMap();
    }

    @Override
    public Map<String, Instance> getInstancesMap() {
        return _atlas.getInstancesMap();
    }

    @Override
    public Map<String, NamedValue> getNamedValuesMap() {
        return _atlas.getNamedValuesMap();
    }

    @Override
    public Map<String, Expression> getExpressionsMap() {
        return _atlas.getExpressionsMap();
    }

    @Override
    public Map<String, Transition> getTransitionsMap() {
        return _atlas.getTransitionsMap();
    }

    @Override
    public Map<String, Operation> getOperationsMap() {
        return _atlas.getOperationsMap();
    }

    @Override
    public Map<String, Class<?>> getOperationClassesMap() {
        return _atlas.getOperationClassesMap();
    }

    @Override
    public Map<String, Trigger> getTriggersMap() {
        return _atlas.getTriggersMap();
    }

    @Override
    public Map<String, Property> getCalculablePropertiesMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        List<Property> list = getPropertiesList();
        if (list.isEmpty()) {
            return map;
        }
        for (Property property : list) {
            if (property.isCalculable()) {
                map.put(property.getPathString(), property);
            }
        }
        return map;
    }

    @Override
    public Map<String, Property> getOverlayPropertiesMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        List<Property> list = getPropertiesList();
        if (list.isEmpty()) {
            return map;
        }
        Collection<Property> collection = new ArrayList<>();
        for (Property property : list) {
            if (property.isOverlayField()) {
                collection.add(property);
            }
        }
        if (collection.isEmpty()) {
            return map;
        }
        for (Property property : ColUtils.sort(collection, new ByPropertyDisplaySortKey())) {
            map.put(property.getPathString(), property);
        }
        return map;
    }

    @Override
    public Map<String, Property> getQueryPropertiesMap() {
        Map<String, Property> map = new LinkedHashMap<>();
        List<Property> list = getPropertiesList();
        if (list.isEmpty()) {
            return map;
        }
        for (Property property : list) {
            mapQueryProperty(map, property);
        }
        for (Property property : list) {
            if (property.isCalculable()) {
                continue;
            }
            if (property instanceof EntityReference entityReference) {
                mapReferenceQueryProperties(map, entityReference);
            }
        }
        if (_inactiveIndicatorProperty != null) {
            mapQueryProperty(map, _inactiveIndicatorProperty);
        }
        if (_ownerProperty != null) {
            mapQueryProperty(map, _ownerProperty);
            mapReferenceQueryProperties(map, _ownerProperty);
        }
        if (_userProperty != null) {
            mapQueryProperty(map, _userProperty);
            mapReferenceQueryProperties(map, _userProperty);
        }
        if (_segmentProperty instanceof EntityReference entityReference) { // since 20210218
            mapQueryProperty(map, entityReference);
            mapReferenceQueryProperties(map, entityReference);
        }
        if (_orderBy != null) {
            Property orderByProperty = getOrderByProperty();
            if (orderByProperty != null) {
                mapQueryProperty(map, orderByProperty);
            } else {
                Property[] orderByProperties = getOrderByProperties();
                if (orderByProperties != null) {
                    for (Property property : orderByProperties) {
                        if (property != null) {
                            mapQueryProperty(map, property);
                        }
                    }
                }
            }
        }
        List<View> views = getViewsList();
        if (views != null && !views.isEmpty()) {
            Property column;
            List<ViewField> fields;
            for (View view : views) {
                fields = view.getViewFields();
                if (fields != null && !fields.isEmpty()) {
                    for (ViewField field : fields) {
                        column = field.getColumn();
                        if (column != null) {
                            mapQueryProperty(map, column);
                        }
                    }
                }
            }
        }
        for (Segment segment : _selectSegments) {
            List<Property> referencedColumns = segment.getReferencedColumnsList();
            for (Property property : referencedColumns) {
                mapQueryProperty(map, property);
            }
        }
        Property foreignQueryProperty;
        for (String key : _foreignQueryProperties.keySet()) {
            foreignQueryProperty = _foreignQueryProperties.get(key);
            mapQueryProperty(map, foreignQueryProperty);
        }
        return map;
    }

    private void mapQueryProperty(Map<String, Property> map, Property property) {
        String key = property.getPathString();
        if (map.containsKey(key)) {
        } else if (property.isCalculable()) {
            if (property instanceof CalculableProperty calculableProperty) {
                mapCalculableQueryProperty(map, calculableProperty);
            }
        } else {
            map.put(key, property);
        }
    }

    private void mapCalculableQueryProperty(Map<String, Property> map, CalculableProperty calculableProperty) {
        Object calculableValue = calculableProperty.getCalculableValue();
        if (calculableValue instanceof Property property) {
            boolean ok = true;
            List<Artifact> pathList = property.getPathList();
            for (Artifact artifact : pathList) {
                if (artifact instanceof Property && ((Property) artifact).isCalculable()) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                String key = calculableProperty.getPathString();
                map.put(key, calculableProperty);
                mapQueryProperty(map, property);
            }
        } else if (calculableValue instanceof Expression calculableValueExpression) {
            boolean ok = verifyExpression(calculableValueExpression, calculableProperty, ExpressionUsage.CALCULABLE_QUERY_PROPERTY_VALUE);
            if (ok) {
                String key = calculableProperty.getPathString();
                map.put(key, calculableProperty);
                List<Property> referencedColumnsList = calculableValueExpression.getReferencedColumnsList();
                for (Property property : referencedColumnsList) {
                    mapQueryProperty(map, property);
                }
            }
        }
    }

    private void mapReferenceQueryProperties(Map<String, Property> map, EntityReference reference) {
        if (reference.isVariantEntity()) {
        } else if (reference.isKeyPropertiesQueryMappingEnabled()) {
//          boolean enumeration = reference.isEnumerationEntity(); // until 25/03/2014; PK can be a linked foreign segment, etc.
            List<Property> list = reference.getPropertiesList();
            for (Property property : list) {
//              if ((property.isPrimaryKeyProperty() && enumeration) || property.isBusinessKeyProperty() || property.isNameProperty()) { // until 25/03/2014
                if (property.isPrimaryKeyProperty() || property.isBusinessKeyProperty() || property.isNameProperty()) { // since 25/03/2024
                    mapQueryProperty(map, property);
                }
            }
        }
    }

    /**
     * @param type an entity class
     * @return the main entity reference from another entity class
     */
    @Override
    public EntityReference getMainEntityReferenceFrom(Class<?> type) {
        if (type == null || !Entity.class.isAssignableFrom(type)) {
            return null;
        }
        Project project = TLC.getProject();
        Entity entity = project == null ? null : project.getEntity(type);
        if (entity == null) { // || entity.isAbstractClass()
            return null;
        }
        Class<?> dataClass = getDataClass();
        EntityReference entityReference = null;
        List<Property> properties = entity.getPropertiesList();
        for (Property property : properties) {
            if (property.isCalculable()) {
                continue;
            }
            if (dataClass.equals(property.getDataClass())) {
                entityReference = (EntityReference) property;
                if (entityReference.isMainRelationship()) {
                    break;
                }
            }
        }
        return entityReference;
    }

    /**
     * @return true if the entity is an abstract entity class
     */
    @Override
    public boolean isAbstractClass() {
        return _annotatedWithAbstractClass;
    }

    /**
     * @return the baseClass
     */
    @Override
    public Class<?> getBaseClass() {
        return _baseClass;
    }

    /**
     * @return the direct known subclasses list
     */
    @Override
    public List<Class<?>> getSubclassesList() {
        return new ArrayList<>(getSubclassesMap().values());
    }

    /**
     * @return the direct known subclasses map
     */
    @Override
    public Map<String, Class<?>> getSubclassesMap() {
        return _subclasses;
    }

    /**
     * @return the direct known subentities map
     */
    @Override
    public Map<String, Entity> getSubentitiesMap() {
        return _subentities;
    }

    /**
     * @return the allocation overrides list
     */
    @Override
    public List<AllocationOverride> getAllocationOverridesList() {
        return new ArrayList<>(getAllocationOverridesMap().values());
    }

    /**
     * @return the allocation overrides map
     */
    @Override
    public Map<String, AllocationOverride> getAllocationOverridesMap() {
        return _allocationOverrides;
    }

    /**
     * @return the superViewName
     */
    @Override
    public String getSuperViewName() {
        return _superViewName;
    }

    /**
     * El método setSuperViewName se utiliza para establecer el nombre de la super-vista de la entidad. Si este método no es ejecutado, el nombre de
     * la super-vista se determina a partir del nombre SQL de la entidad.
     *
     * @param viewName nombre de la vista
     */
    @Override
    public void setSuperViewName(String viewName) {
        _superViewName = viewName;
    }

    /**
     * @return the supraViewName
     */
    @Override
    public String getSupraViewName() {
        return _supraViewName;
    }

    /**
     * El método setSupraViewName se utiliza para establecer el nombre de la supra-vista de la entidad. Si este método no es ejecutado, el nombre de
     * la supra-vista se determina a partir del nombre SQL de la entidad.
     *
     * @param viewName nombre de la vista
     */
    @Override
    public void setSupraViewName(String viewName) {
        _supraViewName = viewName;
    }

    /**
     * @return the primaryKeyFieldName
     */
    @Override
    public String getPrimaryKeyFieldName() {
        return _primaryKeyFieldName;
    }

    /**
     * @return the primaryKeyField
     */
    @Override
    public Field getPrimaryKeyField() {
        return _primaryKeyField;
    }

    /**
     * @return the primaryKeyProperty
     */
    @Override
    public Property getPrimaryKeyProperty() {
        return _primaryKeyProperty;
    }

    /**
     * @return the sequenceFieldName
     */
    @Override
    public String getSequenceFieldName() {
        return _sequenceFieldName;
    }

    /**
     * @return the sequenceField
     */
    @Override
    public Field getSequenceField() {
        return _sequenceField;
    }

    /**
     * @return the sequenceProperty
     */
    @Override
    public LongProperty getSequenceProperty() {
        return _sequenceProperty;
    }

    /**
     * @return the versionFieldName
     */
    @Override
    public String getVersionFieldName() {
        return _versionFieldName;
    }

    /**
     * @return the versionField
     */
    @Override
    public Field getVersionField() {
        return _versionField;
    }

    /**
     * @return the versionProperty
     */
    @Override
    public LongProperty getVersionProperty() {
        return _versionProperty;
    }

    /**
     * @return the numericKeyFieldName
     */
    @Override
    public String getNumericKeyFieldName() {
        return _numericKeyFieldName;
    }

    /**
     * @return the numericKeyField
     */
    @Override
    public Field getNumericKeyField() {
        return _numericKeyField;
    }

    /**
     * @return the numericKeyProperty
     */
    @Override
    public IntegerProperty getNumericKeyProperty() {
        return _numericKeyProperty;
    }

    /**
     * @return the characterKeyFieldName
     */
    @Override
    public String getCharacterKeyFieldName() {
        return _characterKeyFieldName;
    }

    /**
     * @return the characterKeyField
     */
    @Override
    public Field getCharacterKeyField() {
        return _characterKeyField;
    }

    /**
     * @return the characterKeyProperty
     */
    @Override
    public StringProperty getCharacterKeyProperty() {
        return _characterKeyProperty;
    }

    /**
     * @return the nameFieldName
     */
    @Override
    public String getNameFieldName() {
        return _nameFieldName;
    }

    /**
     * @return the nameField
     */
    @Override
    public Field getNameField() {
        return _nameField;
    }

    /**
     * @return the nameProperty
     */
    @Override
    public StringProperty getNameProperty() {
        return _nameProperty;
    }

    /**
     * @return the descriptionFieldName
     */
    @Override
    public String getDescriptionFieldName() {
        return _descriptionFieldName;
    }

    /**
     * @return the descriptionField
     */
    @Override
    public Field getDescriptionField() {
        return _descriptionField;
    }

    /**
     * @return the descriptionProperty
     */
    @Override
    public StringProperty getDescriptionProperty() {
        return _descriptionProperty;
    }

    /**
     * @return the imageFieldName
     */
    @Override
    public String getImageFieldName() {
        return _imageFieldName;
    }

    /**
     * @return the imageField
     */
    @Override
    public Field getImageField() {
        return _imageField;
    }

    /**
     * @return the imageProperty
     */
    @Override
    public BinaryProperty getImageProperty() {
        return _imageProperty;
    }

    /**
     * @return the inactiveIndicatorFieldName
     */
    @Override
    public String getInactiveIndicatorFieldName() {
        return _inactiveIndicatorFieldName;
    }

    /**
     * @return the inactiveIndicatorField
     */
    @Override
    public Field getInactiveIndicatorField() {
        return _inactiveIndicatorField;
    }

    /**
     * @return the inactive indicator property
     */
    @Override
    public BooleanProperty getInactiveIndicatorProperty() {
        return _inactiveIndicatorProperty;
    }

    /**
     * @return the urlFieldName
     */
    @Override
    public String getUrlFieldName() {
        return _urlFieldName;
    }

    /**
     * @return the urlField
     */
    @Override
    public Field getUrlField() {
        return _urlField;
    }

    /**
     * @return the url property
     */
    @Override
    public StringProperty getUrlProperty() {
        return _urlProperty;
    }

    /**
     * @return the parentFieldName
     */
    @Override
    public String getParentFieldName() {
        return _parentFieldName;
    }

    /**
     * @return the parentField
     */
    @Override
    public Field getParentField() {
        return _parentField;
    }

    /**
     * @return the parentProperty
     */
    @Override
    public Entity getParentProperty() {
        return _parentProperty;
    }

    /**
     * @return the ownerFieldName
     */
    @Override
    public String getOwnerFieldName() {
        return _ownerFieldName;
    }

    /**
     * @return the ownerField
     */
    @Override
    public Field getOwnerField() {
        return _ownerField;
    }

    /**
     * @return the ownerProperty
     */
    @Override
    public Entity getOwnerProperty() {
        return _ownerProperty;
    }

    /**
     * @return the userFieldName
     */
    @Override
    public String getUserFieldName() {
        return _userFieldName;
    }

    /**
     * @return the userField
     */
    @Override
    public Field getUserField() {
        return _userField;
    }

    /**
     * @return the userProperty
     */
    @Override
    public Entity getUserProperty() {
        return _userProperty;
    }

    /**
     * @return the segmentFieldName
     */
    @Override
    public String getSegmentFieldName() {
        return _segmentFieldName;
    }

    /**
     * @return the segmentField
     */
    @Override
    public Field getSegmentField() {
        return _segmentField;
    }

    /**
     * @return the segmentProperty
     */
    @Override
    public DataArtifact getSegmentProperty() { // since 20210218
        return _segmentProperty;
    }

    /**
     * @return the segment entity class
     */
    @Override
    public Class<?> getSegmentEntityClass() { // since 20210218
        return _segmentProperty == null ? null : _segmentProperty instanceof Entity ? _segmentProperty.getDataType() : _segmentProperty.getSegmentEntityClass();
    }

    @Override
    public boolean isSegmentEqualToOwner() { // since 24/07/2022
        return _segmentProperty != null && _segmentProperty.equals(_ownerProperty);
    }

    @Override
    public boolean isSegmentEqualToPrimaryKey() { // since 24/07/2022
        return _segmentProperty != null && _segmentProperty.equals(_primaryKeyProperty);
    }

    /**
     * @return the businessKeyFieldName
     */
    @Override
    public String getBusinessKeyFieldName() {
        return _businessKeyFieldName;
    }

    /**
     * @return the businessKeyField
     */
    @Override
    public Field getBusinessKeyField() {
        return _businessKeyField;
    }

    /**
     * @return the businessKeyProperty
     */
    @Override
    public Property getBusinessKeyProperty() {
        return _businessKeyProperty;
    }

    /**
     * @return the stateFieldName
     */
    @Override
    public String getStateFieldName() {
        return _stateFieldName;
    }

    /**
     * @return the stateField
     */
    @Override
    public Field getStateField() {
        return _stateField;
    }

    /**
     * @return the stateProperty
     */
    @Override
    public Entity getStateProperty() {
        return _stateProperty;
    }

    /**
     * @return true if the inactive indicator is a foreign property
     */
    public boolean isForeignInactiveIndicatorProperty() {
        return _foreignInactiveIndicatorProperty;
    }

    /**
     * @return true if the owner is a foreign property
     */
    public boolean isForeignOwnerProperty() {
        return _foreignOwnerProperty;
    }

    /**
     * @return true if the segment is a foreign property
     */
    public boolean isForeignSegmentProperty() {
        return _foreignSegmentProperty;
    }

    /**
     * @return the businessKeyType
     */
    @Override
    public BusinessKeyType getBusinessKeyType() {
        return _businessKeyType;
    }

    /**
     * @return the basic operation entity indicator
     */
    @Override
    public boolean isBasicOperationEntity() {
        return _basicOperationEntity;
    }

    /**
     * @return the catalog entity indicator
     */
    @Override
    public boolean isCatalogEntity() {
        return _catalogEntity;
    }

    /**
     * @return the existentially independent indicator
     */
    @Override
    public boolean isExistentiallyIndependent() {
        return _existentiallyIndependent;
    }

    /**
     * @return the entity view type
     */
    @Override
    public EntityViewType getEntityViewType() {
        return _entityViewType != null && !_entityViewType.equals(EntityViewType.UNSPECIFIED) ? _entityViewType
            : isEnumerationEntity() ? EntityViewType.INDEPENDENT : EntityViewType.BOTH;
    }

    /**
     * @return the variant entity indicator
     */
    @Override
    public boolean isVariantEntity() {
        return _variantEntity;
    }

    /**
     * @return the invariant entity indicator
     */
    @Override
    public boolean isInvariantEntity() {
        return !isVariantEntity();
    }

    /**
     * @return the resource type
     */
    @Override
    public ResourceType getResourceType() {
        return _resourceType != null && !_resourceType.equals(ResourceType.UNSPECIFIED) ? _resourceType
            : isEnumerationEntity() ? ResourceType.CONFIGURATION : ResourceType.OPERATION;
    }

    /**
     * @return the resource gender
     */
    @Override
    public ResourceGender getResourceGender() {
        return _resourceGender;
    }

    /**
     * @return the properties prefix
     */
    @Override
    public String getPropertiesPrefix() {
        return _propertiesPrefix;
    }

    /**
     * @return the properties suffix
     */
    @Override
    public String getPropertiesSuffix() {
        return _propertiesSuffix;
    }

    /**
     * @return the collection name
     */
    @Override
    public String getCollectionName() {
        return StringUtils.isBlank(_collectionName) ? defaultCollectionName() : _collectionName;
    }

    private String defaultCollectionName() {
        Linguist linguist = Bundle.getLinguist();
        return linguist == null ? getName() + "s" : StrUtils.getCamelCase(linguist.pluralOfMultiwordExpression(StrUtils.getWordyString(getName())));
    }

    protected void setCollectionName(String collectionName) {
        _collectionName = StringUtils.trimToEmpty(collectionName);
    }

    /**
     * @return the help document
     */
    @Override
    public String getHelpDocument() {
        return _helpDocument;
    }

    /**
     * El método setHelpDocument se utiliza para establecer el documento incrustado de ayuda de la entidad.
     *
     * También se puede especificar un documento incrustado diferente para cada formato de vista de la entidad, mediante las anotaciones
     * EntityTableView, EntityDetailView, EntityTreeView y EntityConsoleView.
     *
     * Las vistas utilizarán el documento incrustado definido para el correspondiente formato de vista de la entidad, la entidad de la vista, el
     * módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, no habrá un
     * documento incrustado disponible para la vista.
     *
     * @param document definición del documento incrustado de ayuda del proyecto; si utiliza la plataforma jee2, puede ser una URL o un
     * <b>iframe</b> que incluya la URL del documento.
     */
    @Override
    public void setHelpDocument(String document) {
        checkScope();
        boolean log = depth() == 0;
        if (StringUtils.isBlank(document)) {
            _helpDocument = "";
        } else if (isValidEmbeddedDocument(document)) {
            _helpDocument = document;
        } else if (log) {
            logger.error(getName() + " help document is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the help file name
     */
    @Override
    public String getHelpFileName() {
        return StringUtils.isBlank(_helpFileName) && Project.isMetaHelpEnabled() ? getJavaFileName() : _helpFileName;
    }

    /**
     * El método setHelpFileName se utiliza para establecer la ruta y el nombre del archivo de ayuda de la entidad.
     *
     * Cada vista (página) de la entidad podría tener su propio archivo de ayuda, según lo establecido mediante el elemento helpFileAutoName. También
     * se puede especificar un archivo diferente para cada formato de vista de la entidad, mediante las anotaciones EntityTableView, EntityDetailView,
     * EntityTreeView y EntityConsoleView.
     *
     * La vista que no tenga su propio archivo de ayuda utilizará el definido para el correspondiente formato de vista de la entidad, la entidad de la
     * vista, el módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, la
     * página de ayuda no estará disponible para la vista.
     *
     * @param fileName ruta y nombre del archivo de ayuda de la entidad; si utiliza la plataforma jee2, la ruta del archivo debe ser relativa al
     * subdirectorio especificado mediante el atributo extraordinario HELP_RESOURCES_FOLDER del proyecto maestro, y cuyo valor por omisión es el
     * subdirectorio resources/help/custom-made del directorio src/main/webapp del módulo Web de la aplicación.
     */
    @Override
    public void setHelpFileName(String fileName) {
        checkScope();
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _helpFileName = "";
        } else if (isValidHelpFileName(fileName)) {
            if (isValidHelpFileType(fileName)) {
                _helpFileName = fileName;
            } else if (log) {
                logger.error(getName() + " help file type is missing or invalid; valid types are: " + Project.getHelpFileTypesCSV());
                Project.increaseParserErrorCount();
            }
        } else if (log) {
            logger.error(getName() + " help file name is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the help file auto name
     */
    @Override
    public HelpFileAutoName getHelpFileAutoName() {
        return _helpFileAutoName;
    }

    protected void setHelpFileAutoName(HelpFileAutoName helpFileAutoName) {
        _helpFileAutoName = coalesce(helpFileAutoName, HelpFileAutoName.NONE);
    }

    private void checkHelpFileAutoName() {
        if (HelpFileAutoName.META.equals(_helpFileAutoName)) {
            logger.error(getName() + " META help file auto-type can only be specified in a MasterProject annotation");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the help file auto type
     */
    @Override
    public String getHelpFileAutoType() {
        return _helpFileAutoType;
    }

    protected void setHelpFileAutoType(String helpFileAutoType) {
        /* until 10/09/2023
        _helpFileAutoType = StringUtils.defaultIfBlank(helpFileAutoType, Constants.DEFAULT_HELP_FILE_TYPE);
        /**/
        _helpFileAutoType = StringUtils.trimToEmpty(helpFileAutoType);
    }

    private void checkHelpFileAutoType() {
        if (HelpFileAutoName.NONE.equals(_helpFileAutoName) || HelpFileAutoName.META.equals(_helpFileAutoName)) {
            _helpFileAutoType = "";
        } else if (StringUtils.isBlank(_helpFileAutoType)) {
            _helpFileAutoType = ""; // Constants.DEFAULT_HELP_FILE_TYPE; until 10/09/2023
        } else if (!ArrayUtils.contains(Project.getHelpFileTypes(), _helpFileAutoType)) {
            logger.error(getName() + " help file auto-type is invalid; valid types are: " + Project.getHelpFileTypesCSV());
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the start-with
     */
    @Override
    public int getStartWith() {
        return _startWith;
    }

    /**
     * @return the series start
     */
//  @Override
    public int getSeriesStart() {
        return _seriesStart;
    }

    /**
     * @return the series stop
     */
//  @Override
    public int getSeriesStop() {
        return _seriesStop;
    }

    /**
     * @return the series step
     */
//  @Override
    public int getSeriesStep() {
        return _seriesStep;
    }

    /**
     * @return the entity data generation enabled indicator
     */
    public boolean isDataGenEnabled() {
        return _annotatedWithEntityDataGen && validStartStopStep(_seriesStart, _seriesStop, _seriesStep);
    }

    /**
     * @return the select enabled indicator
     */
    @Override
    public boolean isSelectEnabled() {
        return _selectEnabled;
    }

    /**
     * @param enabled the select enabled indicator to set
     */
    @Override
    public void setSelectEnabled(boolean enabled) {
        checkScope();
        _selectEnabled = enabled;
    }

    /**
     * @return the select operation access mode
     */
    @Override
    public OperationAccess isSelectOperationAccess() {
        return _selectOperationAccess;
    }

    /**
     * @return the select onload option
     */
    @Override
    public SelectOnloadOption getSelectOnloadOption() {
        return _selectOnloadOption != null && !_selectOnloadOption.equals(SelectOnloadOption.DEFAULT) ? _selectOnloadOption
            : isEnumerationEntity() ? SelectOnloadOption.EXECUTE : getTableViewQuickFilterSnippetPath() == null ? SelectOnloadOption.PROMPT : SelectOnloadOption.NO_ACTION;
    }

    /**
     * @return the select rows limit
     */
    @Override
    public int getSelectRowsLimit() {
        return _selectRowsLimit;
    }

    /**
     * @return the select sort option
     */
    @Override
    public SortOption getSelectSortOption() {
        return _selectSortOption;
    }

    /**
     * @return the insert enabled indicator
     */
    @Override
    public boolean isInsertEnabled() {
        return _insertEnabled && !OperationAccess.PRIVATE.equals(_insertOperationAccess) && !_annotatedWithAbstractClass;
    }

    /**
     * @param enabled the insert enabled indicator to set
     */
    @Override
    public void setInsertEnabled(boolean enabled) {
        checkScope();
        _insertEnabled = enabled;
    }

    /**
     * @return the insert confirmation required indicator
     */
    @Override
    public boolean isInsertConfirmationRequired() {
        return _insertConfirmationRequired;
    }

    /**
     * @return the insert operation access mode
     */
    @Override
    public OperationAccess getInsertOperationAccess() {
        return _insertOperationAccess;
    }

    /**
     * @return the insert logging mode
     */
    @Override
    public OperationLogging getInsertLogging() {
        return _insertLogging;
    }

    /**
     * @return the update enabled indicator
     */
    @Override
    public boolean isUpdateEnabled() {
        return _updateEnabled && !OperationAccess.PRIVATE.equals(_updateOperationAccess);
    }

    /**
     * @param enabled the update enabled indicator to set
     */
    @Override
    public void setUpdateEnabled(boolean enabled) {
        checkScope();
        _updateEnabled = enabled;
    }

    /**
     * @return the update confirmation required indicator
     */
    @Override
    public boolean isUpdateConfirmationRequired() {
        return _updateConfirmationRequired;
    }

    /**
     * @return the update operation access mode
     */
    @Override
    public OperationAccess getUpdateOperationAccess() {
        return _updateOperationAccess;
    }

    /**
     * @return the update logging mode
     */
    @Override
    public OperationLogging getUpdateLogging() {
        return _updateLogging;
    }

    /**
     * @return the delete enabled indicator
     */
    @Override
    public boolean isDeleteEnabled() {
        return _deleteEnabled && !OperationAccess.PRIVATE.equals(_deleteOperationAccess);
    }

    /**
     * @param enabled the delete enabled indicator to set
     */
    @Override
    public void setDeleteEnabled(boolean enabled) {
        checkScope();
        _deleteEnabled = enabled;
    }

    /**
     * @return the delete confirmation required indicator
     */
    @Override
    public boolean isDeleteConfirmationRequired() {
        return _deleteConfirmationRequired;
    }

    /**
     * @return the delete operation access mode
     */
    @Override
    public OperationAccess getDeleteOperationAccess() {
        return _deleteOperationAccess;
    }

    /**
     * @return the delete logging mode
     */
    @Override
    public OperationLogging getDeleteLogging() {
        return _deleteLogging;
    }

    /**
     * @return the report enabled indicator
     */
    @Override
    public boolean isReportEnabled() {
        return _reportEnabled;
    }

    /**
     * @param enabled the report enabled indicator to set
     */
    @Override
    public void setReportEnabled(boolean enabled) {
        checkScope();
        _reportEnabled = enabled;
    }

    /**
     * @return the report rows limit
     */
    @Override
    public int getReportRowsLimit() {
        return _reportRowsLimit;
    }

    /**
     * @return the report sort option
     */
    @Override
    public SortOption getReportSortOption() {
        return _reportSortOption;
    }

    /**
     * @return the export enabled indicator
     */
    @Override
    public boolean isExportEnabled() {
        return _exportEnabled;
    }

    /**
     * @param enabled the export enabled indicator to set
     */
    @Override
    public void setExportEnabled(boolean enabled) {
        checkScope();
        _exportEnabled = enabled;
    }

    /**
     * @return the export rows limit
     */
    @Override
    public int getExportRowsLimit() {
        return _exportRowsLimit;
    }

    /**
     * @return the export sort option
     */
    @Override
    public SortOption getExportSortOption() {
        return _exportSortOption;
    }

    /**
     * @return the foreign entity class indicator
     */
    @Override
    public boolean isForeignEntityClass() {
        if (_foreignEntityClass != null) {
            return _foreignEntityClass;
        }
        Project project = TLC.getProject();
        Set<Class<?>> foreignEntityClasses = project == null ? null : project.getForeignEntityClasses();
        return foreignEntityClasses != null && foreignEntityClasses.contains(getNamedClass());
    }

    /**
     * El método setForeignEntityClass se utiliza para especificar si la entidad se debe agregar, o no, al conjunto de entidades foráneas de la
     * aplicación. Las entidades foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos de la aplicación, sino en
     * otra que tipicamente reside en un servidor diferente.
     *
     * El método setForeignEntityClass debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param foreignEntityClass true o false para agregar o no la entidad, respectivamente; null, para agregar la entidad solo si pertenece a un
     * módulo de entidades foráneas (vea el elemento foreign de la Anotación ProjectModule)
     */
    @Override
    public void setForeignEntityClass(Boolean foreignEntityClass) {
        checkScope();
        _foreignEntityClass = foreignEntityClass;
    }

    /**
     * @return the private entity class indicator
     */
    @Override
    public boolean isPrivateEntityClass() {
        if (_privateEntityClass != null) {
            return _privateEntityClass;
        }
        Project project = TLC.getProject();
        Set<Class<?>> privateEntityClasses = project == null ? null : project.getPrivateEntityClasses();
        return privateEntityClasses != null && privateEntityClasses.contains(getNamedClass());
    }

    /**
     * El método setPrivateEntityClass se utiliza para especificar si la entidad se debe agregar, o no, al conjunto de entidades privadas de la
     * aplicación. Las entidades privadas son entidades para las que no se deben generar vistas.
     *
     * El método setPrivateEntityClass debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param privateEntityClass true o false para agregar o no la entidad, respectivamente; null, para agregar la entidad solo si pertenece a un
     * módulo de entidades privadas (vea el elemento privacy de la Anotación ProjectModule)
     */
    @Override
    public void setPrivateEntityClass(Boolean privateEntityClass) {
        checkScope();
        _privateEntityClass = privateEntityClass;
    }

    /**
     * @return the link-outer-children indicator
     */
    @Override
    public boolean isLinkOuterChildren() {
        return _linkOuterChildren;
    }

    /**
     * El método setLinkOuterChildren se utiliza para permitir enlazar las vistas de la entidad con sus "hijas" que están en otros paquetes. Las
     * vistas (páginas) "hijas" de una vista de Consulta o Registro son otras vistas del mismo tipo (Consulta o Registro) y de formato
     * Maestro/Detalle, en las que el Maestro de la vista "hija" es la entidad Detalle de la vista. De manera predeterminada, solo se generan enlaces
     * para navegar a "hijas" que se encuentran en el mismo paquete.
     *
     * @param b true para generar enlaces para navegar a "hijas" que se encuentran en otros paquetes
     */
    protected void setLinkOuterChildren(boolean b) {
        _linkOuterChildren = b;
    }

    /**
     * @return the link-outer-collaterals indicator
     */
    @Override
    public boolean isLinkOuterCollaterals() {
        return _linkOuterCollaterals;
    }

    /**
     * El método setLinkOuterCollaterals se utiliza para permitir enlazar las vistas de la entidad con sus vistas "colaterales" que están en otros
     * paquetes. Las vistas (páginas) "colaterales" de una vista Maestro/Detalle de Consulta o Registro son otras vistas Maestro/Detalle del mismo
     * tipo (Consulta o Registro), en las que el Maestro es la misma entidad. De manera predeterminada, solo se generan enlaces para navegar a
     * "colaterales" que se encuentran en el mismo paquete.
     *
     * @param b true para generar enlaces para navegar a vistas "colaterales" que se encuentren en otros paquetes
     */
    protected void setLinkOuterCollaterals(boolean b) {
        _linkOuterCollaterals = b;
    }

    /**
     * @return the link-outer-siblings indicator
     */
    @Override
    public boolean isLinkOuterSiblings() {
        return _linkOuterSiblings;
    }

    /**
     * El método setLinkOuterSiblings se utiliza para permitir enlazar las vistas de la entidad con sus vistas "hermanas" que están en otros paquetes.
     * Las vistas (páginas) "hermanas" de una vista de Consulta o Registro son otras vistas del mismo tipo (Consulta o Registro) del mismo formato
     * (Independiente o Maestro/Detalle) y diferente presentación (Tabular, Forma o Árbol), en las que la entidad Detalle y la entidad Maestro (si
     * aplica) son las mismas entidades. De manera predeterminada, solo se generan enlaces para navegar entre "hermanas" que se encuentren en un mismo
     * paquete.
     *
     * @param b true para generar enlaces para navegar entre "hermanas" que se encuentren en otros paquetes
     */
    protected void setLinkOuterSiblings(boolean b) {
        _linkOuterSiblings = b;
    }

    /**
     * @return the table-view enabled indicator
     */
    @Override
    public boolean isTableViewEnabled() {
        return BitUtils.valueOf(_tableViewEnabled) && isGuiCodeGenEnabled();
    }

    /**
     * @param enabled the table-view enabled indicator to set
     */
    @Override
    public void setTableViewEnabled(boolean enabled) {
        checkScope();
        _tableViewEnabled = enabled;
    }

    /**
     * @return the table-view-with-insert-enabled indicator
     */
    @Override
    public boolean isTableViewWithInsertEnabled() {
        return _tableViewWithInsertEnabled && isInsertEnabled() && tableViewHasAtLeastOneCreateField();
    }

    private boolean tableViewHasAtLeastOneCreateField() {
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (property.isTableField() && property.isCreateField()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the table-view-with-update-enabled indicator
     */
    @Override
    public boolean isTableViewWithUpdateEnabled() {
        return _tableViewWithUpdateEnabled && isUpdateEnabled() && tableViewHasAtLeastOneUpdateField();
    }

    private boolean tableViewHasAtLeastOneUpdateField() {
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (property.isTableField() && property.isUpdateField()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the table-view-with-delete-enabled indicator
     */
    @Override
    public boolean isTableViewWithDeleteEnabled() {
        return _tableViewWithDeleteEnabled && isDeleteEnabled();
    }

    /**
     * @return the table-view-with-master-heading indicator
     */
    @Override
    public boolean isTableViewWithMasterHeading() {
        return _tableViewWithMasterHeading;
    }

    /**
     * @return the table-view-with-quick-filter-snippet indicator
     */
    @Override
    public boolean isTableViewWithQuickFilterSnippet() {
        return _tableViewWithQuickFilterSnippet;
    }

    /**
     * @return the table-view-with-sticky-header indicator
     */
    @Override
    public boolean isTableViewWithStickyHeader() {
        return _tableViewWithStickyHeader;
    }

    /**
     * @return the quick-filter-snippet path
     */
    @Override
    public String getTableViewQuickFilterSnippetPath() {
        if (isTableViewWithQuickFilterSnippet()) {
            JavaWebProject jwp = getJavaWebProject();
            String path = jwp == null ? null : jwp.getProjectFilterSnippetPath();
            return path != null ? path : getBusinessKeyProperty() != null || getNameProperty() != null ? Constants.QUICK_FILTER_SNIPPET : null;
        }
        return null;
    }

    /**
     * @return the table view rows limit
     */
//  @Override
    public int getTableViewRowsLimit() {
        return _tableViewRowsLimit;
    }

    /**
     * @return the table view rows
     */
//  @Override
    public int getTableViewRows() {
        return _tableViewRows;
    }

    /**
     * @return the table responsive mode
     */
    @Override
    public TableResponsiveMode getTableResponsiveMode() {
        return TableResponsiveMode.UNSPECIFIED.equals(_tableResponsiveMode) || Project.isReplaceEntityTableViewResponsiveMode()
            ? Project.getDefaultEntityTableViewResponsiveMode() : _tableResponsiveMode;
    }

    /**
     * El método setTableResponsiveMode se utiliza para establecer el modo "responsive" de las vistas (páginas) de consulta y/o registro tabular de la
     * entidad. Su valor es uno de los elementos de la enumeración TableResponsiveMode. Seleccione AUTO o PRIORITY para mostrar las columnas de la
     * tabla dependiendo de su prioridad, según el tamaño de la pantalla; la diferencia entre estas opciones es que, especificando AUTO, la prioridad
     * de las columnas se determina de manera automática. Seleccione REFLOW para mostrar todas las columnas, apiladas o no, según el tamaño de la
     * pantalla. Seleccione NONE para que la tabla no sea "responsive". Seleccione UNSPECIFIED para que se utilice el valor establecido mediante el
     * método setDefaultEntityTableViewResponsiveMode del meta-proyecto, cuyo valor inicial es NONE.
     *
     * @param responsiveMode modo "responsive" de la tabla
     */
    protected void setTableResponsiveMode(TableResponsiveMode responsiveMode) {
        _tableResponsiveMode = responsiveMode == null ? TableResponsiveMode.UNSPECIFIED : responsiveMode;
    }

    /**
     * @return the table view menu option
     */
//  @Override
    public ViewMenuOption getTableViewMenuOption() {
        return _tableViewMenuOption;
    }

    /**
     * @return the table view help document
     */
    @Override
    public String getTableViewHelpDocument() {
        return _tableViewHelpDocument;
    }

    protected void setTableViewHelpDocument(String document) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(document)) {
            _tableViewHelpDocument = "";
        } else if (isValidEmbeddedDocument(document)) {
            _tableViewHelpDocument = document;
        } else if (log) {
            logger.error(getName() + " table view help document is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the table view help file name
     */
    @Override
    public String getTableViewHelpFileName() {
        return _tableViewHelpFileName;
    }

    protected void setTableViewHelpFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _tableViewHelpFileName = "";
        } else if (isValidHelpFileName(fileName)) {
            if (isValidHelpFileType(fileName)) {
                _tableViewHelpFileName = fileName;
            } else if (log) {
                logger.error(getName() + " table view help file type is missing or invalid; valid types are: " + Project.getHelpFileTypesCSV());
                Project.increaseParserErrorCount();
            }
        } else if (log) {
            logger.error(getName() + " table view help file name is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view head snippet file name
     */
//  @Override
    public String getReadingTableViewHeadSnippetFileName() {
        return _readingTableViewHeadSnippetFileName;
    }

    protected void setReadingTableViewHeadSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewHeadSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewHeadSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view head snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view eastern toolbar snippet file name
     */
//  @Override
    public String getReadingTableViewEasternToolbarSnippetFileName() {
        return _readingTableViewEasternToolbarSnippetFileName;
    }

    protected void setReadingTableViewEasternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewEasternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewEasternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view eastern toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view western toolbar snippet file name
     */
//  @Override
    public String getReadingTableViewWesternToolbarSnippetFileName() {
        return _readingTableViewWesternToolbarSnippetFileName;
    }

    protected void setReadingTableViewWesternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewWesternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewWesternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view western toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view above table snippet file name
     */
//  @Override
    public String getReadingTableViewAboveTableSnippetFileName() {
        return _readingTableViewAboveTableSnippetFileName;
    }

    protected void setReadingTableViewAboveTableSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewAboveTableSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewAboveTableSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view above table snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view below table snippet file name
     */
//  @Override
    public String getReadingTableViewBelowTableSnippetFileName() {
        return _readingTableViewBelowTableSnippetFileName;
    }

    protected void setReadingTableViewBelowTableSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewBelowTableSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewBelowTableSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view below table snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view row action snippet file name
     */
//  @Override
    public String getReadingTableViewRowActionSnippetFileName() {
        return _readingTableViewRowActionSnippetFileName;
    }

    protected void setReadingTableViewRowActionSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewRowActionSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewRowActionSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view row action snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view row status snippet file name
     */
//  @Override
    public String getReadingTableViewRowStatusSnippetFileName() {
        return _readingTableViewRowStatusSnippetFileName;
    }

    protected void setReadingTableViewRowStatusSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewRowStatusSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewRowStatusSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view row status snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading table view row number snippet file name
     */
//  @Override
    public String getReadingTableViewRowNumberSnippetFileName() {
        return _readingTableViewRowNumberSnippetFileName;
    }

    protected void setReadingTableViewRowNumberSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTableViewRowNumberSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTableViewRowNumberSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading table view row number snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view head snippet file name
     */
//  @Override
    public String getWritingTableViewHeadSnippetFileName() {
        return _writingTableViewHeadSnippetFileName;
    }

    protected void setWritingTableViewHeadSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewHeadSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewHeadSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view head snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view eastern toolbar snippet file name
     */
//  @Override
    public String getWritingTableViewEasternToolbarSnippetFileName() {
        return _writingTableViewEasternToolbarSnippetFileName;
    }

    protected void setWritingTableViewEasternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewEasternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewEasternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view eastern toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view western toolbar snippet file name
     */
//  @Override
    public String getWritingTableViewWesternToolbarSnippetFileName() {
        return _writingTableViewWesternToolbarSnippetFileName;
    }

    protected void setWritingTableViewWesternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewWesternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewWesternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view western toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view above table snippet file name
     */
//  @Override
    public String getWritingTableViewAboveTableSnippetFileName() {
        return _writingTableViewAboveTableSnippetFileName;
    }

    protected void setWritingTableViewAboveTableSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewAboveTableSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewAboveTableSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view above table snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view below table snippet file name
     */
//  @Override
    public String getWritingTableViewBelowTableSnippetFileName() {
        return _writingTableViewBelowTableSnippetFileName;
    }

    protected void setWritingTableViewBelowTableSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewBelowTableSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewBelowTableSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view below table snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view row action snippet file name
     */
//  @Override
    public String getWritingTableViewRowActionSnippetFileName() {
        return _writingTableViewRowActionSnippetFileName;
    }

    protected void setWritingTableViewRowActionSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewRowActionSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewRowActionSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view row action snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view row status snippet file name
     */
//  @Override
    public String getWritingTableViewRowStatusSnippetFileName() {
        return _writingTableViewRowStatusSnippetFileName;
    }

    protected void setWritingTableViewRowStatusSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewRowStatusSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewRowStatusSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view row status snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing table view row number snippet file name
     */
//  @Override
    public String getWritingTableViewRowNumberSnippetFileName() {
        return _writingTableViewRowNumberSnippetFileName;
    }

    protected void setWritingTableViewRowNumberSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTableViewRowNumberSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTableViewRowNumberSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing table view row number snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the detail-view enabled indicator
     */
    @Override
    public boolean isDetailViewEnabled() {
        return _detailViewEnabled && isGuiCodeGenEnabled();
    }

    /**
     * @param enabled the detail-view enabled indicator to set
     */
    @Override
    public void setDetailViewEnabled(boolean enabled) {
        checkScope();
        _detailViewEnabled = enabled;
    }

    /**
     * @return the detail-view-with-master-heading indicator
     */
//  @Override
    public boolean isDetailViewWithMasterHeading() {
        return _detailViewWithMasterHeading;
    }

    /**
     * @return the detail view menu option
     */
//  @Override
    public ViewMenuOption getDetailViewMenuOption() {
        return _detailViewMenuOption;
    }

    /**
     * @return the detail view menu option on-open action
     */
//  @Override
    public ViewMenuOptionOpenAction getDetailViewMenuOptionOpenAction() {
        return _detailViewMenuOptionOpenAction;
    }

    /**
     * @return the detail view help document
     */
    @Override
    public String getDetailViewHelpDocument() {
        return _detailViewHelpDocument;
    }

    protected void setDetailViewHelpDocument(String document) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(document)) {
            _detailViewHelpDocument = "";
        } else if (isValidEmbeddedDocument(document)) {
            _detailViewHelpDocument = document;
        } else if (log) {
            logger.error(getName() + " detail view help document is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the detail view help file name
     */
    @Override
    public String getDetailViewHelpFileName() {
        return _detailViewHelpFileName;
    }

    protected void setDetailViewHelpFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _detailViewHelpFileName = "";
        } else if (isValidHelpFileName(fileName)) {
            if (isValidHelpFileType(fileName)) {
                _detailViewHelpFileName = fileName;
            } else if (log) {
                logger.error(getName() + " detail view help file type is missing or invalid; valid types are: " + Project.getHelpFileTypesCSV());
                Project.increaseParserErrorCount();
            }
        } else if (log) {
            logger.error(getName() + " detail view help file name is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading detail view head snippet file name
     */
//  @Override
    public String getReadingDetailViewHeadSnippetFileName() {
        return _readingDetailViewHeadSnippetFileName;
    }

    protected void setReadingDetailViewHeadSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingDetailViewHeadSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingDetailViewHeadSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading detail view head snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading detail view head eastern toolbar snippet file name
     */
//  @Override
    public String getReadingDetailViewEasternToolbarSnippetFileName() {
        return _readingDetailViewEasternToolbarSnippetFileName;
    }

    protected void setReadingDetailViewEasternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingDetailViewEasternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingDetailViewEasternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading detail view head eastern toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading detail view western toolbar snippet file name
     */
//  @Override
    public String getReadingDetailViewWesternToolbarSnippetFileName() {
        return _readingDetailViewWesternToolbarSnippetFileName;
    }

    protected void setReadingDetailViewWesternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingDetailViewWesternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingDetailViewWesternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading detail view western toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading detail view above detail snippet file name
     */
//  @Override
    public String getReadingDetailViewAboveDetailSnippetFileName() {
        return _readingDetailViewAboveDetailSnippetFileName;
    }

    protected void setReadingDetailViewAboveDetailSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingDetailViewAboveDetailSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingDetailViewAboveDetailSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading detail view above detail snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading detail view below detail snippet file name
     */
//  @Override
    public String getReadingDetailViewBelowDetailSnippetFileName() {
        return _readingDetailViewBelowDetailSnippetFileName;
    }

    protected void setReadingDetailViewBelowDetailSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingDetailViewBelowDetailSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingDetailViewBelowDetailSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading detail view below detail snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing detail view head snippet file name
     */
//  @Override
    public String getWritingDetailViewHeadSnippetFileName() {
        return _writingDetailViewHeadSnippetFileName;
    }

    protected void setWritingDetailViewHeadSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingDetailViewHeadSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingDetailViewHeadSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing detail view head snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing detail view eastern toolbar snippet file name
     */
//  @Override
    public String getWritingDetailViewEasternToolbarSnippetFileName() {
        return _writingDetailViewEasternToolbarSnippetFileName;
    }

    protected void setWritingDetailViewEasternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingDetailViewEasternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingDetailViewEasternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing detail view eastern toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing detail view western toolbar snippet file name
     */
//  @Override
    public String getWritingDetailViewWesternToolbarSnippetFileName() {
        return _writingDetailViewWesternToolbarSnippetFileName;
    }

    protected void setWritingDetailViewWesternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingDetailViewWesternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingDetailViewWesternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing detail view western toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing detail view above detail snippet file name
     */
//  @Override
    public String getWritingDetailViewAboveDetailSnippetFileName() {
        return _writingDetailViewAboveDetailSnippetFileName;
    }

    protected void setWritingDetailViewAboveDetailSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingDetailViewAboveDetailSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingDetailViewAboveDetailSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing detail view above detail snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing detail view below detail snippet file name
     */
//  @Override
    public String getWritingDetailViewBelowDetailSnippetFileName() {
        return _writingDetailViewBelowDetailSnippetFileName;
    }

    protected void setWritingDetailViewBelowDetailSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingDetailViewBelowDetailSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingDetailViewBelowDetailSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing detail view below detail snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the tree-view enabled indicator
     */
    @Override
    public boolean isTreeViewEnabled() {
        /* until 13/07/2022
        return _treeViewEnabled && _parentProperty != null && _ownerProperty == null && _segmentProperty == null && isGuiCodeGenEnabled();
        /**/
        return _treeViewEnabled && _parentProperty != null && _baseClass == null && isGuiCodeGenEnabled(); // 16/01/2025 add && _baseClass == null
    }

    /**
     * @param enabled the tree-view enabled indicator to set
     */
    @Override
    public void setTreeViewEnabled(boolean enabled) {
        checkScope();
        _treeViewEnabled = enabled;
    }

    /**
     * @return the tree view menu option
     */
//  @Override
    public ViewMenuOption getTreeViewMenuOption() {
        return _treeViewMenuOption;
    }

    /**
     * @return the tree view root limit
     */
//  @Override
    public int getTreeViewRootLimit() {
        return _treeViewRootLimit;
    }

    /**
     * @return the tree view branch limit
     */
//  @Override
    public int getTreeViewBranchLimit() {
        return _treeViewBranchLimit;
    }

    /**
     * @return the tree view help document
     */
    @Override
    public String getTreeViewHelpDocument() {
        return _treeViewHelpDocument;
    }

    protected void setTreeViewHelpDocument(String document) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(document)) {
            _treeViewHelpDocument = "";
        } else if (isValidEmbeddedDocument(document)) {
            _treeViewHelpDocument = document;
        } else if (log) {
            logger.error(getName() + " tree view help document is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the tree view help file name
     */
    @Override
    public String getTreeViewHelpFileName() {
        return _treeViewHelpFileName;
    }

    protected void setTreeViewHelpFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _treeViewHelpFileName = "";
        } else if (isValidHelpFileName(fileName)) {
            if (isValidHelpFileType(fileName)) {
                _treeViewHelpFileName = fileName;
            } else if (log) {
                logger.error(getName() + " tree view help file type is missing or invalid; valid types are: " + Project.getHelpFileTypesCSV());
                Project.increaseParserErrorCount();
            }
        } else if (log) {
            logger.error(getName() + " tree view help file name is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading tree view head snippet file name
     */
//  @Override
    public String getReadingTreeViewHeadSnippetFileName() {
        return _readingTreeViewHeadSnippetFileName;
    }

    protected void setReadingTreeViewHeadSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTreeViewHeadSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTreeViewHeadSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading tree view head snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading tree view eastern toolbar snippet file name
     */
//  @Override
    public String getReadingTreeViewEasternToolbarSnippetFileName() {
        return _readingTreeViewEasternToolbarSnippetFileName;
    }

    protected void setReadingTreeViewEasternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTreeViewEasternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTreeViewEasternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading tree view eastern toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading tree view western toolbar snippet file name
     */
//  @Override
    public String getReadingTreeViewWesternToolbarSnippetFileName() {
        return _readingTreeViewWesternToolbarSnippetFileName;
    }

    protected void setReadingTreeViewWesternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTreeViewWesternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTreeViewWesternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading tree view western toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading tree view above tree snippet file name
     */
//  @Override
    public String getReadingTreeViewAboveTreeSnippetFileName() {
        return _readingTreeViewAboveTreeSnippetFileName;
    }

    protected void setReadingTreeViewAboveTreeSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTreeViewAboveTreeSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTreeViewAboveTreeSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading tree view above tree snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the reading tree view below tree snippet file name
     */
//  @Override
    public String getReadingTreeViewBelowTreeSnippetFileName() {
        return _readingTreeViewBelowTreeSnippetFileName;
    }

    protected void setReadingTreeViewBelowTreeSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _readingTreeViewBelowTreeSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _readingTreeViewBelowTreeSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " reading tree view below tree snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing tree view head snippet file name
     */
//  @Override
    public String getWritingTreeViewHeadSnippetFileName() {
        return _writingTreeViewHeadSnippetFileName;
    }

    protected void setWritingTreeViewHeadSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTreeViewHeadSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTreeViewHeadSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing tree view head snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing tree view eastern toolbar snippet file name
     */
//  @Override
    public String getWritingTreeViewEasternToolbarSnippetFileName() {
        return _writingTreeViewEasternToolbarSnippetFileName;
    }

    protected void setWritingTreeViewEasternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTreeViewEasternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTreeViewEasternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing tree view eastern toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing tree view western toolbar snippet file name
     */
//  @Override
    public String getWritingTreeViewWesternToolbarSnippetFileName() {
        return _writingTreeViewWesternToolbarSnippetFileName;
    }

    protected void setWritingTreeViewWesternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTreeViewWesternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTreeViewWesternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing tree view western toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing tree view above tree snippet file name
     */
//  @Override
    public String getWritingTreeViewAboveTreeSnippetFileName() {
        return _writingTreeViewAboveTreeSnippetFileName;
    }

    protected void setWritingTreeViewAboveTreeSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTreeViewAboveTreeSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTreeViewAboveTreeSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing tree view above tree snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the writing tree view below tree snippet file name
     */
//  @Override
    public String getWritingTreeViewBelowTreeSnippetFileName() {
        return _writingTreeViewBelowTreeSnippetFileName;
    }

    protected void setWritingTreeViewBelowTreeSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _writingTreeViewBelowTreeSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _writingTreeViewBelowTreeSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " writing tree view below tree snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the console-view enabled indicator
     */
    @Override
    public boolean isConsoleViewEnabled() {
        return _consoleViewEnabled && isGuiCodeGenEnabled();
    }

    /**
     * @param enabled the console-view enabled indicator to set
     */
    @Override
    public void setConsoleViewEnabled(boolean enabled) {
        checkScope();
        _consoleViewEnabled = enabled;
    }

    /**
     * @return the console view menu option
     */
//  @Override
    public boolean getConsoleViewMenuOption() {
        return _consoleViewMenuOption;
    }

    /**
     * @return the console view help document
     */
    @Override
    public String getConsoleViewHelpDocument() {
        return _consoleViewHelpDocument;
    }

    protected void setConsoleViewHelpDocument(String document) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(document)) {
            _consoleViewHelpDocument = "";
        } else if (isValidEmbeddedDocument(document)) {
            _consoleViewHelpDocument = document;
        } else if (log) {
            logger.error(getName() + " console view help document is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the console view help file name
     */
    @Override
    public String getConsoleViewHelpFileName() {
        return _consoleViewHelpFileName;
    }

    protected void setConsoleViewHelpFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _consoleViewHelpFileName = "";
        } else if (isValidHelpFileName(fileName)) {
            if (isValidHelpFileType(fileName)) {
                _consoleViewHelpFileName = fileName;
            } else if (log) {
                logger.error(getName() + " console view help file type is missing or invalid; valid types are: " + Project.getHelpFileTypesCSV());
                Project.increaseParserErrorCount();
            }
        } else if (log) {
            logger.error(getName() + " console view help file name is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the console view head snippet file name
     */
//  @Override
    public String getConsoleViewHeadSnippetFileName() {
        return _consoleViewHeadSnippetFileName;
    }

    protected void setConsoleViewHeadSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _consoleViewHeadSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _consoleViewHeadSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " console view head snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the console view eastern toolbar snippet file name
     */
//  @Override
    public String getConsoleViewEasternToolbarSnippetFileName() {
        return _consoleViewEasternToolbarSnippetFileName;
    }

    protected void setConsoleViewEasternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _consoleViewEasternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _consoleViewEasternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " console view eastern toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the console view western toolbar snippet file name
     */
//  @Override
    public String getConsoleViewWesternToolbarSnippetFileName() {
        return _consoleViewWesternToolbarSnippetFileName;
    }

    protected void setConsoleViewWesternToolbarSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _consoleViewWesternToolbarSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _consoleViewWesternToolbarSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " console view western toolbar snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the above processing panel snippet file name
     */
//  @Override
    public String getConsoleViewAboveProcessingPanelSnippetFileName() {
        return _consoleViewAboveProcessingPanelSnippetFileName;
    }

    protected void setConsoleViewAboveProcessingPanelSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _consoleViewAboveProcessingPanelSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _consoleViewAboveProcessingPanelSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " above processing panel snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the below processing panel snippet file name
     */
//  @Override
    public String getConsoleViewBelowProcessingPanelSnippetFileName() {
        return _consoleViewBelowProcessingPanelSnippetFileName;
    }

    protected void setConsoleViewBelowProcessingPanelSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _consoleViewBelowProcessingPanelSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _consoleViewBelowProcessingPanelSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " below processing panel snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the above master heading snippet file name
     */
//  @Override
    public String getAboveMasterHeadingSnippetFileName() {
        return _aboveMasterHeadingSnippetFileName;
    }

    protected void setAboveMasterHeadingSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _aboveMasterHeadingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _aboveMasterHeadingSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " above master heading snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the below master heading snippet file name
     */
//  @Override
    public String getBelowMasterHeadingSnippetFileName() {
        return _belowMasterHeadingSnippetFileName;
    }

    protected void setBelowMasterHeadingSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _belowMasterHeadingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _belowMasterHeadingSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " below master heading snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the inside master heading snippet file name
     */
//  @Override
    public String getInsideMasterHeadingSnippetFileName() {
        return _insideMasterHeadingSnippetFileName;
    }

    protected void setInsideMasterHeadingSnippetFileName(String fileName) {
        boolean log = depth() == 0;
        if (StringUtils.isBlank(fileName)) {
            _insideMasterHeadingSnippetFileName = "";
        } else if (isValidSnippetFileName(fileName)) {
            _insideMasterHeadingSnippetFileName = fileName;
        } else if (log) {
            logger.error(getName() + " inside master heading snippet is invalid ");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the custom JSON serializer class name
     */
    public String getJsonSerializerClassName() {
        return _jsonSerializerClassName;
    }

    public void setJsonSerializerClassName(String className) {
        checkScope();
        boolean log = depth() == 0;
        if (StringUtils.isBlank(className)) {
            _jsonSerializerClassName = "";
        } else if (isValidJavaClassName(className)) {
            _jsonSerializerClassName = className;
        } else if (log) {
            logger.error(getName() + " has an invalid JSON serializer class name");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the custom JSON deserializer class name
     */
    public String getJsonDeserializerClassName() {
        return _jsonDeserializerClassName;
    }

    public void setJsonDeserializerClassName(String className) {
        checkScope();
        boolean log = depth() == 0;
        if (StringUtils.isBlank(className)) {
            _jsonDeserializerClassName = "";
        } else if (isValidJavaClassName(className)) {
            _jsonDeserializerClassName = className;
        } else if (log) {
            logger.error(getName() + " has an invalid JSON deserializer class name");
            Project.increaseParserErrorCount();
        }
    }

    /**
     * @return the warnings enabled indicator
     */
    @Override
    public boolean isWarningsEnabled() {
        return _warningsEnabled;
    }

    /**
     * @return the special expressions warnings enabled indicator
     */
    @Override
    public boolean isSpecialExpressionsWarningsEnabled() {
        return _specialExpressionsWarningsEnabled;
    }

    /**
     * @return the unusual expressions warnings enabled indicator
     */
    @Override
    public boolean isUnusualExpressionsWarningsEnabled() {
        return _unusualExpressionsWarningsEnabled;
    }

    /**
     * @return the business process logic code generation enabled indicator
     */
    @Override
    public boolean isBplCodeGenEnabled() {
        return _bplCodeGenEnabled;
    }

    /**
     * El método setBplCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BPL (Business Process Logic) para la entidad.
     *
     * El método setBplCodeGenEnabled debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param enabled true o false para generar, o no, código BPL para la entidad
     */
    @Override
    public void setBplCodeGenEnabled(boolean enabled) {
        checkScope();
        _bplCodeGenEnabled = enabled;
    }

    /**
     * @return the business web service code generation enabled indicator
     */
    @Override
    public boolean isBwsCodeGenEnabled() {
        return _bwsCodeGenEnabled;
    }

    /**
     * El método setBwsCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BWS (Business Web Service) para la entidad.
     *
     * El método setBwsCodeGenEnabled debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param enabled true o false para generar, o no, código BWS para la entidad
     */
    @Override
    public void setBwsCodeGenEnabled(boolean enabled) {
        checkScope();
        _bwsCodeGenEnabled = enabled;
    }

    /**
     * @return the data access object code generation enabled indicator
     */
    @Override
    public boolean isDaoCodeGenEnabled() {
        return _daoCodeGenEnabled;
    }

    /**
     * El método setDaoCodeGenEnabled se utiliza para especificar si se debe, o no, generar un objeto de acceso a datos (DAO, por las siglas en inglés
     * de Data Access Object) para la entidad.
     *
     * El método setDaoCodeGenEnabled debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param enabled true o false para generar, o no, código DAO para la entidad
     */
    @Override
    public void setDaoCodeGenEnabled(boolean enabled) {
        checkScope();
        _daoCodeGenEnabled = enabled;
    }

    /**
     * @return the data access facade code generation enabled indicator
     */
    @Override
    public boolean isDafCodeGenEnabled() {
        return _dafCodeGenEnabled;
    }

    /**
     * El método setDafCodeGenEnabled se utiliza para especificar si se debe, o no, generar una fachada de acceso a datos (código DAF, por las siglas
     * en inglés de Data Access Façade) para la entidad.
     *
     * El método setDafCodeGenEnabled debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param enabled true o false para generar, o no, código DAF para la entidad
     */
    @Override
    public void setDafCodeGenEnabled(boolean enabled) {
        checkScope();
        _dafCodeGenEnabled = enabled;
    }

    /**
     * @return the facade web service code generation enabled indicator
     */
    @Override
    public boolean isFwsCodeGenEnabled() {
        return _fwsCodeGenEnabled;
    }

    /**
     * El método setFwsCodeGenEnabled se utiliza para especificar si se debe, o no, generar código FWS (Façade Web Service) para la entidad.
     *
     * El método setFwsCodeGenEnabled debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param enabled true o false para generar, o no, código FWS para la entidad
     */
    @Override
    public void setFwsCodeGenEnabled(boolean enabled) {
        checkScope();
        _fwsCodeGenEnabled = enabled;
    }

    /**
     * @return the GUI code generation enabled indicator
     */
    @Override
    public boolean isGuiCodeGenEnabled() {
        return _guiCodeGenEnabled && !isPrivateEntityClass();
    }

    /**
     * El método setGuiCodeGenEnabled se utiliza para especificar si se debe, o no, generar código GUI (Graphical User Interface) para la entidad.
     *
     * El método setGuiCodeGenEnabled debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param enabled true o false para generar, o no, código GUI para la entidad
     */
    @Override
    public void setGuiCodeGenEnabled(boolean enabled) {
        checkScope();
        _guiCodeGenEnabled = enabled;
    }

    /**
     * @return the SQL code generation enabled indicator
     */
    @Override
    public boolean isSqlCodeGenEnabled() {
        return _sqlCodeGenEnabled && !isForeignEntityClass();
    }

    /**
     * El método setSqlCodeGenEnabled se utiliza para especificar si se debe, o no, generar código SQL para la entidad.
     *
     * El método setSqlCodeGenEnabled debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param enabled true o false para generar, o no, código SQL para la entidad
     */
    @Override
    public void setSqlCodeGenEnabled(boolean enabled) {
        checkScope();
        _sqlCodeGenEnabled = enabled;
    }

    /**
     * @return the entity state machine code generation enabled indicator
     */
    @Override
    public boolean isEntityStateCodeGenEnabled() {
        return _smcCodeGenEnabled && _stateProperty != null && _stateProperty.isNotInheritedFromConcrete();
    }

    /**
     * @return the entity class diagram generation indicator
     */
    @Override
    public boolean isEntityClassDiagramGenEnabled() {
        return _entityClassDiagramGenEnabled;
    }

    /**
     * @return the entity state diagram generation indicator
     */
    @Override
    public boolean isEntityStateDiagramGenEnabled() {
        return _entityStateDiagramGenEnabled && !getTransitionsMap().isEmpty();
    }

    /**
     * @return the entity insert activity diagram generation indicator
     */
    @Override
    public boolean isEntityInsertActivityDiagramGenEnabled() {
        return _entityInsertActivityDiagramGenEnabled;
    }

    /**
     * @return the entity update activity diagram generation indicator
     */
    @Override
    public boolean isEntityUpdateActivityDiagramGenEnabled() {
        return _entityUpdateActivityDiagramGenEnabled;
    }

    /**
     * @param name property's name
     * @return the property with the specified name
     */
    @Override
    public Property getProperty(String name) {
        return name == null ? null : getPropertiesMap().get(name);
    }

    /**
     * @return the reference avatar indicator
     */
    public boolean isReferenceAvatar() {
        return _referenceAvatar;
    }

    public void setReferenceAvatar(boolean avatar) {
        checkScope();
        _referenceAvatar = avatar;
    }

    /**
     * @return the reference avatar availability
     */
    public boolean isReferenceWithAvatar() {
        return _referenceAvatar && isEntityWithAvatar();
    }

    /**
     * @return the entity avatar availability
     */
    public boolean isEntityWithAvatar() {
        BinaryProperty imageProperty = getImageProperty();
        AvatarShape avatarShape = imageProperty == null ? null : imageProperty.getAvatarShape();
        return !(avatarShape == null || avatarShape.equals(AvatarShape.NONE));
    }

    /**
     * @return the custom converter entity reference indicator
     */
    public boolean isCustomConverterEntityReference() {
        return _customConverterEntityReference;
    }

    /**
     * El método setCustomConverterEntityReference se utiliza para establecer si existe un componente personalizado que permite convertir el valor de
     * la referencia (propiedad o parámetro que hace referencia a otra entidad). El valor predeterminado del atributo es false, es decir, no existe un
     * componente personalizado y, por lo tanto, se utiliza el componente generado para convertir el valor de la referencia.
     *
     * @param custom true si existe un componente personalizado que permite convertir el valor de la referencia; de lo contrario, false, para utilizar
     * el componente generado.
     */
    public void setCustomConverterEntityReference(boolean custom) {
        checkScope();
        _customConverterEntityReference = custom;
    }

    /**
     * @return the restricted access entity reference indicator
     */
    public boolean isRestrictedAccessEntityReference() {
        return _restrictedAccessEntityReference;
    }

    /**
     * @return the restricted access entity reference search list indicator
     */
    public boolean isRestrictedAccessEntityReferenceSearchList() { // since 24/07/2022
        SearchType searchType = getSearchType(); // displays (pages) should always add the necessary access control criteria
        return _restrictedAccessEntityReference && (SearchType.LIST.equals(searchType) || SearchType.RADIO.equals(searchType));
    }

    /**
     * El método setRestrictedAccessEntityReference se utiliza para establecer si la referencia (propiedad o parámetro que hace referencia a otra
     * entidad) es una referencia con o sin acceso restringido por las reglas de control de acceso. El valor predeterminado del atributo es true, es
     * decir, con acceso restringido.
     *
     * @param restricted true para que sea una referencia con acceso restringido; o false, para que sea una referencia sin acceso restringido.
     */
    public void setRestrictedAccessEntityReference(boolean restricted) {
        checkScope();
        _restrictedAccessEntityReference = restricted;
    }

    /**
     * @return the specified reference style
     */
    public EntityReferenceStyle getSpecifiedReferenceStyle() {
        return _referenceStyle;
    }

    /**
     * @return the reference style
     */
    @Override
    public EntityReferenceStyle getReferenceStyle() {
        Property name = getNameProperty();
        Property code = getBusinessKeyProperty();
        EntityReferenceStyle defaultStyle = defaultReferenceStyle(code, name, EntityReferenceStyle.UNSPECIFIED);
        return switch (_referenceStyle) {
            case NAME ->
                nameOrCodeStyle(code, name, defaultStyle);
            case CHARACTER_KEY ->
                codeOrNameStyle(code, name, defaultStyle);
            case NAME_AND_CHARACTER_KEY ->
                nameAndOrCodeStyle(code, name, defaultStyle);
            case CHARACTER_KEY_AND_NAME ->
                codeAndOrNameStyle(code, name, defaultStyle);
            default ->
                defaultStyle;
        };
    }

    public void setReferenceStyle(EntityReferenceStyle referenceStyle) {
        checkScope();
        _referenceStyle = referenceStyle != null ? referenceStyle : EntityReferenceStyle.UNSPECIFIED;
    }

    private EntityReferenceStyle defaultReferenceStyle(Property code, Property name, EntityReferenceStyle defaultStyle) {
        SearchType searchType = getSearchType();
        if (SearchType.LIST.equals(searchType) || SearchType.RADIO.equals(searchType)) {
            switch (_listStyle) {
                case NAME, PRIMARY_KEY_AND_NAME -> {
                    return nameOrCodeStyle(code, name, defaultStyle);
                }
                case CHARACTER_KEY, PRIMARY_KEY_AND_CHARACTER_KEY -> {
                    return codeOrNameStyle(code, name, defaultStyle);
                }
                case NAME_AND_CHARACTER_KEY -> {
                    return nameAndOrCodeStyle(code, name, defaultStyle);
                }
                case CHARACTER_KEY_AND_NAME -> {
                    return codeAndOrNameStyle(code, name, defaultStyle);
                }
            }
        }
        return isEnumerationEntity() ? codeAndOrNameStyle(code, name, defaultStyle) : nameOrCodeStyle(code, name, defaultStyle);
    }

    private EntityReferenceStyle nameOrCodeStyle(Property code, Property name, EntityReferenceStyle defaultStyle) {
        return name != null ? EntityReferenceStyle.NAME : code != null ? EntityReferenceStyle.CHARACTER_KEY : defaultStyle;
    }

    private EntityReferenceStyle codeOrNameStyle(Property code, Property name, EntityReferenceStyle defaultStyle) {
        return code != null ? EntityReferenceStyle.CHARACTER_KEY : name != null ? EntityReferenceStyle.NAME : defaultStyle;
    }

    private EntityReferenceStyle nameAndOrCodeStyle(Property code, Property name, EntityReferenceStyle defaultStyle) {
        return code != null && name != null ? EntityReferenceStyle.NAME_AND_CHARACTER_KEY : nameOrCodeStyle(code, name, defaultStyle);
    }

    private EntityReferenceStyle codeAndOrNameStyle(Property code, Property name, EntityReferenceStyle defaultStyle) {
        return code != null && name != null ? EntityReferenceStyle.CHARACTER_KEY_AND_NAME : codeOrNameStyle(code, name, defaultStyle);
    }

    /**
     * @return the reference filter-by
     */
    @Override
    public EntityReferenceProperty getReferenceFilterBy() {
        Property name = getNameProperty();
        Property code = getBusinessKeyProperty();
        EntityReferenceProperty defaultProperty = defaultReferenceProperty(code, name, EntityReferenceProperty.UNSPECIFIED);
        return switch (_referenceFilterBy) {
            case NAME ->
                nameOrCodeProperty(code, name, defaultProperty);
            case CHARACTER_KEY ->
                codeOrNameProperty(code, name, defaultProperty);
            default ->
                defaultProperty;
        };
    }

    public void setReferenceFilterBy(EntityReferenceProperty referenceFilterBy) {
        checkScope();
        _referenceFilterBy = referenceFilterBy != null ? referenceFilterBy : EntityReferenceProperty.UNSPECIFIED;
    }

    /**
     * @return the reference filter-by property
     */
    @Override
    public Property getReferenceFilterByProperty() {
        EntityReferenceProperty referenceFilterBy = getReferenceFilterBy();
        return switch (referenceFilterBy) {
            case NAME ->
                getNameProperty();
            case CHARACTER_KEY ->
                getBusinessKeyProperty();
            default ->
                null;
        };
    }

    /**
     * @return the reference filter-type
     */
    public EntityReferenceFilterType getReferenceFilterType() {
        return _referenceFilterType != null && !_referenceFilterType.equals(EntityReferenceFilterType.UNSPECIFIED) ? _referenceFilterType
            : /*isEnumerationEntity() ? EntityReferenceFilterType.LIST :*/ EntityReferenceFilterType.TEXTBOX;
    }

    public void setReferenceFilterType(EntityReferenceFilterType referenceFilterType) {
        checkScope();
        _referenceFilterType = referenceFilterType != null ? referenceFilterType : EntityReferenceFilterType.UNSPECIFIED;
    }

    /**
     * @return the reference sort-by
     */
    @Override
    public EntityReferenceProperty getReferenceSortBy() {
        Property name = getNameProperty();
        Property code = getBusinessKeyProperty();
        EntityReferenceProperty defaultProperty = defaultReferenceProperty(code, name, EntityReferenceProperty.UNSPECIFIED);
        return switch (_referenceSortBy) {
            case NAME ->
                nameOrCodeProperty(code, name, defaultProperty);
            case CHARACTER_KEY ->
                codeOrNameProperty(code, name, defaultProperty);
            default ->
                defaultProperty;
        };
    }

    public void setReferenceSortBy(EntityReferenceProperty referenceSortBy) {
        checkScope();
        _referenceSortBy = referenceSortBy != null ? referenceSortBy : EntityReferenceProperty.UNSPECIFIED;
    }

    /**
     * @return the reference sort-by proerty
     */
    @Override
    public Property getReferenceSortByProperty() {
        EntityReferenceProperty referenceSortBy = getReferenceSortBy();
        return switch (referenceSortBy) {
            case NAME ->
                getNameProperty();
            case CHARACTER_KEY ->
                getBusinessKeyProperty();
            default ->
                null;
        };
    }

    private EntityReferenceProperty defaultReferenceProperty(Property code, Property name, EntityReferenceProperty defaultProperty) {
        EntityReferenceStyle referenceStyle = getReferenceStyle();
        return switch (referenceStyle) {
            case NAME, NAME_AND_CHARACTER_KEY ->
                nameOrCodeProperty(code, name, defaultProperty);
            case CHARACTER_KEY, CHARACTER_KEY_AND_NAME ->
                codeOrNameProperty(code, name, defaultProperty);
            default ->
                EntityReferenceProperty.UNSPECIFIED;
        };
    }

    private EntityReferenceProperty nameOrCodeProperty(Property code, Property name, EntityReferenceProperty defaultProperty) {
        return name != null ? EntityReferenceProperty.NAME : code != null ? EntityReferenceProperty.CHARACTER_KEY : defaultProperty;
    }

    private EntityReferenceProperty codeOrNameProperty(Property code, Property name, EntityReferenceProperty defaultProperty) {
        return code != null ? EntityReferenceProperty.CHARACTER_KEY : name != null ? EntityReferenceProperty.NAME : defaultProperty;
    }

    /**
     * @return the search type
     */
    @Override
    public SearchType getSearchType() {
        return _searchType != null && !_searchType.equals(SearchType.UNSPECIFIED) ? _searchType
            : isEnumerationEntity() ? SearchType.LIST : SearchType.DISPLAY;
    }

    public void setSearchType(SearchType searchType) {
        checkScope();
        _searchType = searchType != null ? searchType : SearchType.UNSPECIFIED;
    }

    /**
     * @return the specified list style
     */
    public ListStyle getSpecifiedListStyle() {
        return _listStyle;
    }

    /**
     * @return the list style
     */
    @Override
    public ListStyle getListStyle() {
        SearchType searchType = getSearchType();
        if (SearchType.LIST.equals(searchType) || SearchType.RADIO.equals(searchType)) {
            Property name = getNameProperty();
            Property code = getBusinessKeyProperty();
            ListStyle defaultStyle = defaultListStyle(code, name, ListStyle.UNSPECIFIED);
            return switch (_listStyle) {
                case NAME ->
                    nameOrCodeListStyle(code, name, defaultStyle);
                case CHARACTER_KEY ->
                    codeOrNameListStyle(code, name, defaultStyle);
                case NAME_AND_CHARACTER_KEY ->
                    nameAndOrCodeListStyle(code, name, defaultStyle);
                case CHARACTER_KEY_AND_NAME ->
                    codeAndOrNameListStyle(code, name, defaultStyle);
                case PRIMARY_KEY_AND_NAME ->
                    name == null ? defaultStyle : ListStyle.PRIMARY_KEY_AND_NAME;
                case PRIMARY_KEY_AND_CHARACTER_KEY ->
                    code == null ? defaultStyle : ListStyle.PRIMARY_KEY_AND_CHARACTER_KEY;
                default ->
                    defaultStyle;
            };
        }
        return ListStyle.UNSPECIFIED;
    }

    public void setListStyle(ListStyle listStyle) {
        checkScope();
        _listStyle = listStyle != null ? listStyle : ListStyle.UNSPECIFIED;
    }

    private ListStyle defaultListStyle(Property code, Property name, ListStyle defaultStyle) {
        return switch (_referenceStyle) {
            case NAME ->
                nameOrCodeListStyle(code, name, defaultStyle);
            case CHARACTER_KEY ->
                codeOrNameListStyle(code, name, defaultStyle);
            case NAME_AND_CHARACTER_KEY ->
                nameAndOrCodeListStyle(code, name, defaultStyle);
            case CHARACTER_KEY_AND_NAME ->
                codeAndOrNameListStyle(code, name, defaultStyle);
            default ->
                defaultStyle;
        };
    }

    private ListStyle nameOrCodeListStyle(Property code, Property name, ListStyle defaultStyle) {
        return name != null ? ListStyle.NAME : code != null ? ListStyle.CHARACTER_KEY : defaultStyle;
    }

    private ListStyle codeOrNameListStyle(Property code, Property name, ListStyle defaultStyle) {
        return code != null ? ListStyle.CHARACTER_KEY : name != null ? ListStyle.NAME : defaultStyle;
    }

    private ListStyle nameAndOrCodeListStyle(Property code, Property name, ListStyle defaultStyle) {
        return code != null && name != null ? ListStyle.NAME_AND_CHARACTER_KEY : nameOrCodeListStyle(code, name, defaultStyle);
    }

    private ListStyle codeAndOrNameListStyle(Property code, Property name, ListStyle defaultStyle) {
        return code != null && name != null ? ListStyle.CHARACTER_KEY_AND_NAME : codeOrNameListStyle(code, name, defaultStyle);
    }

    /**
     * @return the radio columns
     */
    public int getRadioColumns() {
        if (SearchType.RADIO.equals(_searchType)) {
            if (_radioColumns == 0) {
                int size = unfilteredInstances() ? getInstancesList().size() : 0;
                return size > 0 && 12 % size == 0 ? size : _radioColumns;
            }
        }
        return _radioColumns;
    }

    public void setRadioColumns(int radioColumns) {
        checkScope();
        _radioColumns = Math.min(12, Math.max(0, radioColumns));
    }

    boolean unfilteredInstances() {
        return isEnumerationEntity() && getSearchQueryFilter() == null;
    }

    /**
     * @return the descending order indicator
     */
    public boolean isDescendingOrder() {
        return _descendingOrder;
    }

    public void setDescendingOrder(boolean descendingOrder) {
        checkScope();
        _descendingOrder = descendingOrder;
    }

    /**
     * @return the search display format
     */
    @Override
    public SearchDisplayFormat getSearchDisplayFormat() {
        return _searchDisplayFormat;
    }

    public void setSearchDisplayFormat(SearchDisplayFormat searchDisplayFormat) {
        checkScope();
        _searchDisplayFormat = searchDisplayFormat != null ? searchDisplayFormat : SearchDisplayFormat.UNSPECIFIED;
    }

    /**
     * @return the search display mode
     */
    @Override
    public DisplayMode getSearchDisplayMode() {
        return _searchDisplayMode;
    }

    public void setSearchDisplayMode(DisplayMode searchDisplayMode) {
        checkScope();
        _searchDisplayMode = searchDisplayMode != null ? searchDisplayMode : DisplayMode.UNSPECIFIED;
    }

    @Override
    public String getApplicationOrigin() {
        return isSingleApplicationProject() ? null : _applicationOrigin;
    }

    /**
     * El método setApplicationOrigin se utiliza para establecer el origen de la aplicación empresarial que contiene las vistas (páginas) de la
     * entidad.
     *
     * Este atributo solo tiene efecto si el proyecto generado puede utilizar vistas (páginas) de otros proyectos. Utilice el método
     * setMultiApplication del proyecto maestro para permitir el uso de vistas de otros proyectos.
     *
     * El método setApplicationOrigin debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param origin consta del protocolo, el nombre o dirección IP del servidor y el número de puerto. Por ejemplo, http://86.48.31.84:8080
     */
    @Override
    public void setApplicationOrigin(String origin) {
        checkScope();
        _applicationOrigin = StringUtils.trimToNull(origin);
    }

    @Override
    public String getApplicationContextRoot() {
        return isSingleApplicationProject() ? null : _applicationContextRoot;
    }

    /**
     * El método setApplicationContextRoot se utiliza para establecer la raíz de contexto del módulo Web de la aplicación empresarial que contiene las
     * vistas (páginas) de la entidad.
     *
     * Este atributo solo tiene efecto si el proyecto generado puede utilizar vistas (páginas) de otros proyectos. Utilice el método
     * setMultiApplication del proyecto maestro para permitir el uso de vistas de otros proyectos.
     *
     * El método setApplicationContextRoot debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param contextRoot raíz de contexto del módulo Web de la aplicación que contiene las vistas. Por ejemplo, <b>showcase102-web</b>
     */
    @Override
    public void setApplicationContextRoot(String contextRoot) {
        checkScope();
        _applicationContextRoot = StringUtils.trimToNull(contextRoot);
    }

    @Override
    public String getApplicationConsolePath() {
        return _applicationConsolePath;
    }

    /**
     * El método setApplicationConsolePath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de procesamiento de la
     * entidad.
     *
     * El método setApplicationConsolePath debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param consolePath parte intermedia del path de las vistas (páginas) de procesamiento de la entidad. Por ejemplo,
     * <b>faces/views/base/crop/procesamiento</b>
     */
    @Override
    public void setApplicationConsolePath(String consolePath) {
        checkScope();
        _applicationConsolePath = StringUtils.trimToNull(consolePath);
    }

    @Override
    public String getApplicationReadingPath() {
        return _applicationReadingPath;
    }

    /**
     * El método setApplicationReadingPath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de consulta de la entidad.
     *
     * El método setApplicationReadingPath debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param readingPath parte intermedia del path de las vistas (páginas) de consulta de la entidad. Por ejemplo,
     * <b>faces/views/base/crop/consulta</b>
     */
    @Override
    public void setApplicationReadingPath(String readingPath) {
        checkScope();
        _applicationReadingPath = StringUtils.trimToNull(readingPath);
    }

    @Override
    public String getApplicationWritingPath() {
        return _applicationWritingPath;
    }

    /**
     * El método setApplicationWritingPath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de registro de la entidad.
     *
     * El método setApplicationWritingPath debe ejecutarse en el método settleAttributes de la entidad.
     *
     * @param writingPath parte intermedia del path de las vistas (páginas) de registro de la entidad. Por ejemplo,
     * <b>faces/views/base/crop/registro</b>
     */
    @Override
    public void setApplicationWritingPath(String writingPath) {
        checkScope();
        _applicationWritingPath = StringUtils.trimToNull(writingPath);
    }

    @Override
    public boolean isApplicationDefaultLocation() {
        if (isGuiCodeGenEnabled()) {
            if (isSingleApplicationProject()) {
                return true;
            }
            String host = getApplicationOrigin();
            String root = getApplicationContextRoot();
            return StringUtils.isBlank(host) && (StringUtils.isBlank(root) || root.equals(getWebProjectName()));
        }
        return false;
    }

    private boolean isSingleApplicationProject() {
        return !isMultiApplicationProject();
    }

    private boolean isMultiApplicationProject() {
        JavaWebProject jwp = getJavaWebProject();
        return jwp != null && jwp.isMultiApplication();
    }

    private String getWebProjectName() {
        JavaWebProject jwp = getJavaWebProject();
        return jwp == null ? null : jwp.getWebProjectName();
    }

    private JavaWebProject getJavaWebProject() {
        Project project = TLC.getProject();
        return project instanceof JavaWebProject ? (JavaWebProject) project : null;
    }

    protected boolean isRoleWebAppDissociationAllowed() {
        JavaWebProject jwp = getJavaWebProject();
        return jwp != null && jwp.isRoleWebAppDissociationAllowed();
    }

    /**
     * @return the search query filter
     */
    @Override
    public BooleanExpression getSearchQueryFilter() {
        BooleanExpression exp = isInstanceReferenceField() ? defaultInstanceParameterSearchQueryFilter() : null;
        if (exp != null) {
            return _searchQueryFilter == null ? exp : exp.and(_searchQueryFilter);
        }
        // required by query-builder.vm
        if (_searchQueryFilter == null && !isHiddenField()) {
            BooleanExpression isNotNull = isNotNull();
            isNotNull.setLogicalTautology(true);
            /* until 30/06/2022
            if (isFilterInactiveIndicatorProperty() || isFilterOwnerProperty() || isFilterSegmentProperty()) {
                return isNotNull;
            }
            /**/
            if (QuickAddingFilter.MISSING.equals(_quickAddingFilter)) {
                return isNotNull;
            }
            if (isSegmentProperty() && !isReadOnly()) {
                return isNotNull;
            }
            // since 24/07/2022
            if (isRestrictedAccessEntityReferenceSearchList()) {
                Entity root = getRoot();
                if (root.getOwnerProperty() != null || root.getSegmentProperty() != null) {
                    return isNotNull;
                }
            }
            /**/
        }
        return _searchQueryFilter;
    }

    private BooleanExpression defaultInstanceParameterSearchQueryFilter() {
        BooleanExpression states = operationInitialStatesDisjunction();
        BooleanExpression checks = _searchQueryFilter == null ? instanceParameterChecksConjunction() : null;
        return states == null && checks == null ? null : states == null ? checks : checks == null ? states : and(states, checks);
    }

    private BooleanExpression operationInitialStatesDisjunction() {
        Operation operation = getDeclaringOperation();
        List<State> list = operation.getInitialStatesList();
        int size = list.size();
        if (size == 0) {
            return null;
        }
        Expression exp;
        Map<String, Expression> map = getExpressionsMap();
        State[] array = new State[size];
        int i = 0;
        for (State state : list) {
            exp = map.get(state.getName());
            if (exp instanceof State s) {
                array[i++] = s;
            }
        }
        return switch (i) {
            case 0 ->
                null;
            case 1 ->
                array[0];
            case 2 ->
                or(array[0], array[1]);
            default ->
                or(array[0], array[1], (State[]) ArrayUtils.subarray(array, 2, i));
        };
    }

    private BooleanExpression instanceParameterChecksConjunction() {
        Operation operation = getDeclaringOperation();
        List<Check> list = operation.getInstanceParameterChecksList();
        int size = list.size();
        if (size == 0) {
            return null;
        }
        Expression exp;
        Map<String, Expression> map = operation.getExpressionsMap();
        Check[] array = new Check[size];
        int i = 0;
        for (Check check : list) {
            exp = map.get(check.getName());
            if (exp instanceof Check c) {
                array[i++] = c;
            }
        }
        return switch (i) {
            case 0 ->
                null;
            case 1 ->
                array[0];
            case 2 ->
                and(array[0], array[1]);
            default ->
                and(array[0], array[1], (Check[]) ArrayUtils.subarray(array, 2, i));
        };
    }

    private final Map<String, Artifact> _searchQueryFilterParametersMap = new LinkedHashMap<>();

    public void addSearchQueryFilterParameter(String key, DataArtifact value) {
        if (key != null && value != null) {
            _searchQueryFilterParametersMap.put(key, value);
        }
    }

    public Map<String, Artifact> addSearchQueryFilterParametersMap(Map<String, Artifact> map) {
        if (map != null && !map.isEmpty()) {
            _searchQueryFilterParametersMap.putAll(map);
        }
        return _searchQueryFilterParametersMap;
    }

    public Map<String, Artifact> getSearchQueryFilterParametersMap() {
        return _searchQueryFilterParametersMap;
    }

    /**
     * El método setSearchQueryFilter se utiliza para establecer el filtro de búsqueda del valor de la referencia (propiedad o parámetro que hace
     * referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la entidad.
     *
     * @param filter expresión booleana que se utiliza como filtro; solo las instancias que satisfacen los criterios del filtro se pueden utilizar
     * como valor de la propiedad o parámetro
     */
    public void setSearchQueryFilter(BooleanExpression filter) {
        checkScope();
        String message = "failed to set search query filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _searchQueryFilter = filter.isTrue();
        } else {
            _searchQueryFilter = filter;
        }
    }

    /**
     * @return the search value filter
     */
    public Map<Property, Object> getSearchValueFilter() {
        return _searchValueFilter;
    }

    /**
     * @return the search value filter property references
     */
    public Set<Entity> getSearchValueFilterPropertyReferences() {
        Object object;
        Artifact artifact;
        List<Artifact> pathList;
        Set<Entity> set = new LinkedHashSet<>();
        for (Property key : _searchValueFilter.keySet()) {
            object = _searchValueFilter.get(key);
            if (object instanceof Property property) {
                pathList = property.getPropertyPathList();
                artifact = pathList.get(0);
                if (artifact instanceof Entity entity) {
                    set.add(entity);
                }
            }
        }
        return set;
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BigDecimalProperty property, BigDecimalProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BigDecimalProperty property, Number value) {
        addSearchValueFilterBigDecimalProperty(property, value);
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BigDecimalProperty property, String value) {
        addSearchValueFilterBigDecimalProperty(property, value);
    }

    private void addSearchValueFilterBigDecimalProperty(BigDecimalProperty property, Object value) {
        BigDecimal big = ObjUtils.toBigDecimal(value);
        if (validSearchValueFilter(property, big, value)) {
            _searchValueFilter.put(property, big);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BigIntegerProperty property, BigIntegerProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BigIntegerProperty property, Number value) {
        addSearchValueFilterBigIntegerProperty(property, value);
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BigIntegerProperty property, String value) {
        addSearchValueFilterBigIntegerProperty(property, value);
    }

    private void addSearchValueFilterBigIntegerProperty(BigIntegerProperty property, Object value) {
        BigInteger big = ObjUtils.toBigInteger(value);
        if (validSearchValueFilter(property, big, value)) {
            _searchValueFilter.put(property, big);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BooleanProperty property, BooleanProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(BooleanProperty property, boolean value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(ByteProperty property, ByteProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(ByteProperty property, byte value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(CharacterProperty property, CharacterProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(CharacterProperty property, char value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(DateProperty property, DateProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(DateProperty property, java.util.Date value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(DateProperty property, SpecialTemporalValue value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(DoubleProperty property, DoubleProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(DoubleProperty property, double value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(FloatProperty property, FloatProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(FloatProperty property, float value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(IntegerProperty property, IntegerProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(IntegerProperty property, int value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(LongProperty property, LongProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(LongProperty property, long value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(ShortProperty property, ShortProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(ShortProperty property, short value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(StringProperty property, StringProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(StringProperty property, String value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(TimeProperty property, TimeProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(TimeProperty property, java.util.Date value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(TimeProperty property, SpecialTemporalValue value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(TimestampProperty property, TimestampProperty value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(TimestampProperty property, java.util.Date value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(TimestampProperty property, SpecialTemporalValue value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(EntityReference property, EntityReference value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(EntityReference property, Instance value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    /**
     * El método addSearchValueFilterProperty se utiliza para agregar una propiedad al filtro de valores para la búsqueda del valor de la referencia
     * (propiedad o parámetro que hace referencia a otra entidad) en vistas (páginas) de registro o de ejecución de operaciones de negocio de la
     * entidad.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param property la propiedad de la referencia a la que se agrega el filtro
     * @param value el valor que tomará la propiedad al agregar nuevas instancias durante la búsqueda
     */
    public void addSearchValueFilterProperty(EntityReference property, SpecialEntityValue value) {
        if (validSearchValueFilter(property, value)) {
            _searchValueFilter.put(property, value);
        }
    }

    private void setSearchValueFilter() {
        if (isSearchValueFilterFromSearchQueryFilter() && _searchQueryFilter != null) {
            if (_searchValueFilterFromSearchQueryFilterExplicitlySet) {
                _searchValueFilter.clear();
            }
            if (_searchValueFilter.isEmpty()) {
                boolean ok = setSearchValueFilter(_searchQueryFilter);
                if (!ok && _searchValueFilterFromSearchQueryFilterExplicitlySet) {
                    String message = "failed to build the search value filter from the search query filter of " + getFullName();
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private boolean setSearchValueFilter(Expression expression) {
        Operator operator = expression.getOperator();
        Object[] operands = expression.getOperands();
        if (operator != null && operands != null) {
            boolean ok = false;
            if (ComparisonOp.EQ.equals(operator)) {
                ok = operands.length == 2 && setSearchValueFilter(operands[0], operands[1]);
            } else if (ComparisonOp.IS_TRUE.equals(operator) || ComparisonOp.IS_FALSE.equals(operator)) {
                ok = operands.length == 1 && setSearchValueFilter(operands[0], ComparisonOp.IS_TRUE.equals(operator));
            } else if (DataAggregateOp.AND.equals(operator) || OrderedPairOp.AND.equals(operator)) {
                ok = operands.length >= 2 && setSearchValueFilter(operands);
            } else if (_searchValueFilterFromSearchQueryFilterExplicitlySet) {
                String message = "operator " + operator + " in search query filter prevents building search value filter of " + getFullName();
                logger.error(message);
                Project.increaseParserErrorCount();
            }
            if (ok) {
                return true;
            }
        }
        _searchValueFilter.clear();
        return false;
    }

    private boolean setSearchValueFilter(Object[] operands) {
        for (Object operand : operands) {
            if (operand instanceof BooleanExpression && setSearchValueFilter((Expression) operand)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private boolean setSearchValueFilter(Object operand, Object originalValue) {
        if (operand instanceof Property property) {
            Object value
                = operand instanceof BigDecimalProperty ? ObjUtils.toBigDecimal(originalValue)
                    : operand instanceof BigIntegerProperty ? ObjUtils.toBigInteger(originalValue)
                        : originalValue;
            /**/
            if (validSearchValueFilter(property, value, originalValue, _searchValueFilterFromSearchQueryFilterExplicitlySet)) {
                _searchValueFilter.put(property, value);
                return true;
            }
        } else if (_searchValueFilterFromSearchQueryFilterExplicitlySet) {
            String message = "operand " + objectFullName(operand) + " in search query filter prevents building search value filter of " + getFullName();
            logger.error(message);
            Project.increaseParserErrorCount();
        }
        return false;
    }

    private boolean validSearchValueFilter(Property property, Object value) {
        return validSearchValueFilter(property, value, value);
    }

    private boolean validSearchValueFilter(Property property, Object value, Object originalValue) {
        return validSearchValueFilter(property, value, originalValue, true);
    }

    private boolean validSearchValueFilter(Property property, Object value, Object originalValue, boolean log) {
        boolean b1 = validSearchValueFilterProperty(property) && this.equals(property.getDeclaringEntity());
        boolean b2 = validSearchValueFilterValue(value) && value != this && value != property;
        boolean b3 = value instanceof Instance ? b1 && b2 && property.equals(((Instance) value).getDeclaringEntity()) : true;
        if (b1 && b2 && b3) {
            return true;
        }
        String message = "failed to add property to search value filter of " + getFullName();
        if (b1) {
            message += "; property: " + property.getName();
        } else {
            message += "; invalid property: " + objectFullName(property);
        }
        if (b2 && b3) {
            message += "; value: " + value;
        } else {
            message += "; invalid value: " + originalValue;
        }
        if (log) {
            logger.error(message);
            Project.increaseParserErrorCount();
        }
        return false;
    }

    private boolean validSearchValueFilterProperty(Object value) {
        return value instanceof BigDecimalProperty
            || value instanceof BigIntegerProperty
            || value instanceof BooleanProperty
            || value instanceof ByteProperty
            || value instanceof CharacterProperty
            || value instanceof DateProperty
            || value instanceof DoubleProperty
            || value instanceof FloatProperty
            || value instanceof IntegerProperty
            || value instanceof LongProperty
            || value instanceof ShortProperty
            || value instanceof StringProperty
            || value instanceof TimeProperty
            || value instanceof TimestampProperty
            || value instanceof EntityReference;
    }

    private boolean validSearchValueFilterValue(Object value) {
        return value instanceof Property
            || value instanceof Boolean
            || value instanceof Character
            || value instanceof Number
            || value instanceof String
            || value instanceof java.util.Date
            || value instanceof Instance
            || value instanceof SpecialEntityValue
            || value instanceof SpecialTemporalValue;
    }

    private String objectFullName(Object object) {
        return object instanceof Artifact ? ((Artifact) object).getFullName() : object + "";
    }

    /**
     * @return the build the search value filter from the search query filter indicator
     */
    public boolean isSearchValueFilterFromSearchQueryFilter() {
        return _searchValueFilterFromSearchQueryFilter && DisplayMode.WRITING.equals(_searchDisplayMode);
    }

    /**
     * El método setSearchValueFilterFromSearchQueryFilter se utiliza para especificar si se debe construir, o no, el filtro de valores para la
     * búsqueda del valor de la referencia a partir del filtro de búsqueda del valor de la referencia.
     *
     * Para poder construir el filtro, los únicos operadores de comparación que el filtro de búsqueda del valor de la referencia puede tener son: EQ
     * (método isEqualTo), IS_TRUE (método isTrue), IS_FALSE (método isFalse); y él único operador de agregación que puede tener es AND (método and).
     *
     * Si no se utiliza el método setSearchValueFilterFromSearchQueryFilter, pero se estableció un filtro de búsqueda del valor de la referencia
     * (utilizando el método setSearchQueryFilter) y no se estableció el filtro de valores para la búsqueda (no se agregó ninguna propiedad utilizando
     * el método addSearchValueFilterProperty), entonces se intenta construir el filtro, pero no se emiten mensajes en caso de que ocurra algún error;
     * utilice setSearchValueFilterFromSearchQueryFilter(false) si no desea el filtro; utilice setSearchValueFilterFromSearchQueryFilter(true) si
     * desea construir el filtro, ignorando las propiedades agregadas con el método addSearchValueFilterProperty, y emitiendo mensajes de error.
     *
     * El filtro de valores para la búsqueda del valor de la referencia establece valores de propiedades de instancias agregadas durante la búsqueda
     * de la referencia, cuando las vistas (páginas) de búsqueda sean vistas de registro. Los campos de las vistas que correspondan a propiedades del
     * filtro serán protegidos, de modo que el usuario no pueda darles un valor diferente al especificado en el filtro.
     *
     * @param build true, si se debe construir el filtro de valores para la búsqueda del valor de la referencia a partir del filtro de búsqueda del
     * valor de la referencia; de lo contrario, false.
     */
    public void setSearchValueFilterFromSearchQueryFilter(boolean build) {
        checkScope();
        _searchValueFilterFromSearchQueryFilter = build;
        _searchValueFilterFromSearchQueryFilterExplicitlySet = build;
    }

    /**
     * the initial select segment
     */
    private Segment _initialSelectSegment;

    public Segment getInitialSelectSegment() {
        return _initialSelectSegment;
    }

    /**
     * select segments are designer-defined select filters
     */
    private final Set<Segment> _selectSegments = new LinkedHashSet<>();

    public Set<Segment> getSelectSegments() {
        return _selectSegments;
    }

    private void addSpecialNativeQuerySegments() {
        Project project = TLC.getProject();
        List<NativeQuerySegment> specialNativeQuerySegments = project == null ? null : project.getSpecialNativeQuerySegments(this);
        if (specialNativeQuerySegments != null && !specialNativeQuerySegments.isEmpty()) {
            for (NativeQuerySegment specialNativeQuerySegment : specialNativeQuerySegments) {
                addSelectSegment(specialNativeQuerySegment);
            }
        }
    }

    /**
     * El método addSelectSegment de la meta entidad se utiliza para agregar segmentos de selección para las operaciones <b>select</b>, <b>export</b>
     * y <b>report</b> de las vistas (páginas) de consulta y/o registro de la entidad. Solo las instancias de la entidad que cumplen con los criterios
     * del segmento son incluidas en el resultado de estas operaciones.
     *
     * @param segments una o más expresiones booleanas que definen segmentos de la entidad
     */
    public void addSelectSegment(Segment... segments) {
        if (depth() == 0 && segments != null && segments.length != 0) {
            for (Segment segment : segments) {
                addSelectSegment(segment);
            }
        }
    }

    /**
     * El método addSelectSegment de la meta entidad se utiliza para agregar segmentos de selección para las operaciones <b>select</b>, <b>export</b>
     * y <b>report</b> de las vistas (páginas) de consulta y/o registro de la entidad. Solo las instancias de la entidad que cumplen con los criterios
     * del segmento son incluidas en el resultado de estas operaciones.
     *
     * @param segment expresión booleana que define un segmento de la entidad
     */
    public void addSelectSegment(Segment segment) {
        addSelectSegment(segment, false);
    }

    /**
     * El método addSelectSegment de la meta entidad se utiliza para agregar segmentos de selección para las operaciones <b>select</b>, <b>export</b>
     * y <b>report</b> de las vistas (páginas) de consulta y/o registro de la entidad.Solo las instancias de la entidad que cumplen con los criterios
     * del segmento son incluidas en el resultado de estas operaciones.
     *
     * @param segment expresión booleana que define un segmento de la entidad
     * @param initial parámetro que define si el segmento será seleccionado al abrir las vistas (páginas) de consulta y/o registro de la entidad
     */
    public void addSelectSegment(Segment segment, boolean initial) {
        addToSelectSegments(segment, initial);
    }

    /**
     * El método addSelectSegment de la meta entidad se utiliza para agregar segmentos de selección para las operaciones <b>select</b>, <b>export</b>
     * y <b>report</b> de las vistas (páginas) de consulta y/o registro de la entidad. Solo las instancias de la entidad que cumplen con los criterios
     * del segmento son incluidas en el resultado de estas operaciones.
     *
     * @param segments una o más expresiones booleanas que definen segmentos de la entidad
     */
    public void addSelectSegment(NativeQuerySegment... segments) {
        if (depth() == 0 && segments != null && segments.length != 0) {
            for (NativeQuerySegment segment : segments) {
                addSelectSegment(segment);
            }
        }
    }

    /**
     * El método addSelectSegment de la meta entidad se utiliza para agregar segmentos de selección para las operaciones <b>select</b>, <b>export</b>
     * y <b>report</b> de las vistas (páginas) de consulta y/o registro de la entidad. Solo las instancias de la entidad que cumplen con los criterios
     * del segmento son incluidas en el resultado de estas operaciones.
     *
     * @param segment expresión booleana que define un segmento de la entidad
     */
    public void addSelectSegment(NativeQuerySegment segment) {
        addSelectSegment(segment, false);
    }

    /**
     * El método addSelectSegment de la meta entidad se utiliza para agregar segmentos de selección para las operaciones <b>select</b>, <b>export</b>
     * y <b>report</b> de las vistas (páginas) de consulta y/o registro de la entidad.Solo las instancias de la entidad que cumplen con los criterios
     * del segmento son incluidas en el resultado de estas operaciones.
     *
     * @param segment expresión booleana que define un segmento de la entidad
     * @param initial parámetro que define si el segmento será seleccionado al abrir las vistas (páginas) de consulta y/o registro de la entidad
     */
    public void addSelectSegment(NativeQuerySegment segment, boolean initial) {
        if (segment != null) {
            addToSelectSegments(segment.getSegment(), initial);
        }
    }

    private void addToSelectSegments(Segment segment, boolean initial) {
        if (depth() == 0 && segment != null) {
            _selectSegments.add(segment);
            if (initial) {
                _initialSelectSegment = segment;
            }
        }
    }

    /**
     * @return the select filter
     */
    @Override
    public BooleanExpression getSelectFilter() {
        return _selectFilter;
    }

    /**
     * El método setSelectFilter se utiliza para establecer el filtro de selección de las operaciones select, export y report de las vistas (páginas)
     * de consulta y/o registro de la entidad. Solo las instancias de la entidad que cumplen con los criterios del filtro son incluidas en el
     * resultado de estas operaciones.
     *
     * @param filter expresión booleana que se utiliza como filtro
     */
    public void setSelectFilter(BooleanExpression filter) {
        checkScope();
        String message = "failed to set select filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _selectFilter = filter.isTrue();
        } else {
            _selectFilter = filter;
        }
    }

    /**
     * @param reference entity reference
     * @return the insert filter
     */
    @Override
    public BooleanExpression getInsertFilter(EntityReference reference) {
        return _insertFilterByReferenceMap.get(reference);
    }

    /**
     * El método setInsertFilter se utiliza para establecer el filtro de selección de la operación insert de las vistas (páginas) de registro,
     * Maestro/Detalle, de la entidad. Solo las instancias de la entidad referenciada que cumplen con los criterios del filtro podrán ser utilizadas
     * como maestro por la operación insert.
     *
     * @param filter expresión booleana, definida en una entidad referenciada (por una relación con cardinalidad varios-a-uno), que se utiliza como
     * filtro
     */
    public void setInsertFilter(BooleanExpression filter) {
        checkScope();
        String message = "failed to set insert filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        if (filter instanceof Primitive) {
            message += "; supplied expression is a boolean primitive";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        Entity master = filter.getDeclaringEntity();
        if (master == null) {
            message += "; supplied expression declaring entity is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        EntityReference reference;
        if (master instanceof EntityReference entityReference) {
            reference = entityReference;
        } else {
            message += "; supplied expression declaring entity is not an entity reference";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        Entity detail = master.getDeclaringEntity();
        if (detail == null || !detail.equals(this)) {
            message += "; supplied expression declaring entity is not a valid entity reference";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        if (filter instanceof BooleanPrimitive) {
            _insertFilterByReferenceMap.put(reference, filter.isTrue());
        } else {
            _insertFilterByReferenceMap.put(reference, filter);
        }
    }

    /**
     * @return the insert filter by reference map
     */
    public Map<EntityReference, BooleanExpression> getInsertFilterByReferenceMap() {
        return _insertFilterByReferenceMap;
    }

    /**
     * @return the update filter
     */
    @Override
    public BooleanExpression getUpdateFilter() {
        return _updateFilter;
    }

    public Checkpoint getUpdateFilterCheckpoint() {
        /*
        if (_updateFilter instanceof Check check) {
            Checkpoint checkpoint = check.getCheckpoint();
            return checkpoint == null ? Checkpoint.UNSPECIFIED : checkpoint;
        }
        return null;
        /**/
        return _updateFilterWhereverPossible ? Checkpoint.WHEREVER_POSSIBLE : Checkpoint.USER_INTERFACE;
    }

    /**
     * El método setUpdateFilter se utiliza para establecer el filtro de selección de la operación update que se implementa en las vistas (páginas) de
     * registro de la entidad. Solo las instancias de la entidad que cumplen con los criterios del filtro podrán ser modificadas.
     *
     * @param filter expresión booleana que se utiliza como filtro
     */
    public void setUpdateFilter(BooleanExpression filter) {
        setUpdateFilter(filter, false);
    }

    /**
     * El método setUpdateFilter se utiliza para establecer el filtro de selección de la operación update que se implementa en las vistas (páginas) de
     * registro de la entidad y, opcionalmente, en el trigger before update de la tabla de la base de datos que corresponde a la entidad. Solo las
     * instancias de la entidad que cumplen con los criterios del filtro podrán ser modificadas.
     *
     * @param filter expresión booleana que se utiliza como filtro
     * @param whereverPossible true para implementar el filtro también en el trigger before update
     */
    public void setUpdateFilter(BooleanExpression filter, boolean whereverPossible) {
        checkScope();
        String message = "failed to set update filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _updateFilter = filter.isTrue();
            _updateFilterWhereverPossible = whereverPossible;
        } else {
            _updateFilter = filter;
            _updateFilterWhereverPossible = whereverPossible;
        }
    }

    /**
     * @return the delete filter
     */
    @Override
    public BooleanExpression getDeleteFilter() {
        return _deleteFilter;
    }

    public Checkpoint getDeleteFilterCheckpoint() {
        /*
        if (_deleteFilter instanceof Check check) {
            Checkpoint checkpoint = check.getCheckpoint();
            return checkpoint == null ? Checkpoint.UNSPECIFIED : checkpoint;
        }
        return null;
        /**/
        return _deleteFilterWhereverPossible ? Checkpoint.WHEREVER_POSSIBLE : Checkpoint.USER_INTERFACE;
    }

    /**
     * El método setDeleteFilter se utiliza para establecer el filtro de selección de la operación delete que se implementa en las vistas (páginas) de
     * registro de la entidad. Solo las instancias de la entidad que cumplen con los criterios del filtro podrán ser eliminadas.
     *
     * @param filter expresión booleana que se utiliza como filtro
     */
    public void setDeleteFilter(BooleanExpression filter) {
        setDeleteFilter(filter, false);
    }

    /**
     * El método setDeleteFilter se utiliza para establecer el filtro de selección de la operación delete que se implementa en las vistas (páginas) de
     * registro de la entidad y, opcionalmente, en el trigger before delete de la tabla de la base de datos que corresponde a la entidad. Solo las
     * instancias de la entidad que cumplen con los criterios del filtro podrán ser eliminadas.
     *
     * @param filter expresión booleana que se utiliza como filtro
     * @param whereverPossible true para implementar el filtro también en el trigger before delete
     */
    public void setDeleteFilter(BooleanExpression filter, boolean whereverPossible) {
        checkScope();
        String message = "failed to set delete filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (filter instanceof BooleanPrimitive) {
            _deleteFilter = filter.isTrue();
            _deleteFilterWhereverPossible = whereverPossible;
        } else {
            _deleteFilter = filter;
            _deleteFilterWhereverPossible = whereverPossible;
        }
    }

    /**
     * @param reference entity reference
     * @return the master/detail filter
     */
    @Override
    public BooleanExpression getMasterDetailFilter(EntityReference reference) {
        return _masterDetailFilterByReferenceMap.get(reference);
    }

    /**
     * El método setMasterDetailFilter se utiliza para establecer el filtro de selección de la operación select de las vistas (páginas) de consulta y
     * registro, Maestro/Detalle, de la entidad. Solo las instancias de la entidad referenciada que cumplen con los criterios del filtro podrán ser
     * utilizadas como maestro por la operación select.
     *
     * @param filter expresión booleana, definida en una entidad referenciada (por una relación con cardinalidad varios-a-uno), que se utiliza como
     * filtro
     */
    public void setMasterDetailFilter(BooleanExpression filter) {
        checkScope();
        String message = "failed to set master/detail filter of " + getFullName();
        if (filter == null) {
            message += "; supplied expression is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        if (filter instanceof Primitive) {
            message += "; supplied expression is a boolean primitive";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        Entity master = filter.getDeclaringEntity();
        if (master == null) {
            message += "; supplied expression declaring entity is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        EntityReference reference;
        if (master instanceof EntityReference entityReference) {
            reference = entityReference;
        } else {
            message += "; supplied expression declaring entity is not an entity reference";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        Entity detail = master.getDeclaringEntity();
        if (detail == null || !detail.equals(this)) {
            message += "; supplied expression declaring entity is not a valid entity reference";
            logger.error(message);
            Project.increaseParserErrorCount();
            return;
        }
        if (filter instanceof BooleanPrimitive) {
            _masterDetailFilterByReferenceMap.put(reference, filter.isTrue());
        } else {
            _masterDetailFilterByReferenceMap.put(reference, filter);
        }
    }

    /**
     * @return the master/detail filter by reference map
     */
    public Map<EntityReference, BooleanExpression> getMasterDetailFilterByReferenceMap() {
        return _masterDetailFilterByReferenceMap;
    }

    /**
     * @return the filter inactive indicator property
     */
    public boolean isFilterInactiveIndicatorProperty() {
        /* until 29/06/2022
        return _filterInactiveIndicatorProperty && getInactiveIndicatorProperty() != null && filterable();
        /**/
        return _filterInactiveIndicatorProperty;
    }

    /**
     * @return the filter owner property
     */
    public boolean isFilterOwnerProperty() {
        /* until 29/06/2022
        return _filterOwnerProperty && getOwnerProperty() != null && filterable();
        /**/
        return _filterOwnerProperty;
    }

    /**
     * @return the filter segment property
     */
    public boolean isFilterSegmentProperty() {
        /* until 29/06/2022
        return _filterSegmentProperty && getSegmentProperty() != null && filterable();
        /**/
        return _filterSegmentProperty;
    }

    /* until 29/06/2022
    private boolean filterable() {
        if (isHiddenField()) {
            return false;
        }
        if (isProperty()) {
            if (isReadOnly()) {
                return false;
            }
            Entity root = getDeclaringEntityRoot();
            return root.isInsertEnabled() || root.isUpdateEnabled(); // Why?
        }
        return isParameter();
    }

    /**
     * @return true if the entity defines a one-to-one relationship
     */
    @Override
    public boolean isOneToOne() {
        return _annotatedWithOneToOne;
    }

    /**
     * @return true if the entity defines a manty-to-one relationship
     */
    @Override
    public boolean isManyToOne() {
        return _annotatedWithManyToOne;
    }

    /**
     * @return true if the entity defines the main relationship
     */
    @Override
    public boolean isMainRelationship() {
        return _mainRelationship;
    }

    /**
     * @return the fetch type
     */
    @Override
    public FetchType getFetchType() {
        return FetchType.UNSPECIFIED.equals(_fetchType)
            ? _mappedCollection == null ? FetchType.LAZY : FetchType.EAGER
            : _fetchType;
    }

    /**
     * @return the cascade type
     */
    @Override
    public CascadeType[] getCascadeType() {
        return _cascadeType;
    }

    /**
     * @return the cascade type
     */
    @Override
    public String getCascadeTypeString() {
        if (ArrayUtils.contains(_cascadeType, CascadeType.UNSPECIFIED)) {
            return null;
        }
        if (ArrayUtils.contains(_cascadeType, CascadeType.ALL)) {
            return "CascadeType.ALL";
        }
        Set<CascadeType> set = EnumSet.noneOf(CascadeType.class);
        set.addAll(Arrays.asList(_cascadeType)); // duplicates are ignored
        List<String> list = new ArrayList<>();
        for (CascadeType ct : set) {
            list.add("CascadeType." + ct.name());
        }
        return list.size() == 1 ? list.get(0) : "{" + StringUtils.join(list, ", ") + "}";
    }

    /**
     * @return the navigability
     */
    @Override
    public Navigability getNavigability() {
        return Navigability.UNSPECIFIED.equals(_navigability)
            ? _mappedCollection == null ? Navigability.UNIDIRECTIONAL : Navigability.BIDIRECTIONAL
            : _navigability;
    }

    @Override
    public EntityCollection getMappedCollection() {
        return _mappedCollection;
    }

    @Override
    public void setMappedCollection(EntityCollection collection) {
        checkScope();
        _mappedCollection = collection;
    }

    /**
     * @return the one-to-one detail view indicator
     */
    @Override
    public boolean isOneToOneDetailView() {
        return _oneToOneDetailView;
    }

    /**
     * @return the master/detail view
     */
    @Override
    public MasterDetailView getMasterDetailView() {
        MasterDetailView masterDetailView = _masterDetailView;
        if (MasterDetailView.UNSPECIFIED.equals(masterDetailView)) {
            masterDetailView = MasterDetailView.NONE;
            if (isManyToOne()) {
                ResourceType rt1 = getResourceType();
                if (rt1 != null && !rt1.equals(ResourceType.UNSPECIFIED)) {
                    Entity declaringEntity = getDeclaringEntity();
                    ResourceType rt2 = declaringEntity == null ? null : declaringEntity.getResourceType();
                    if (declaringEntity != null && rt1.equals(rt2)) {
                        if (declaringEntity.isExistentiallyIndependent()) {
                            masterDetailView = MasterDetailView.TABLE;
                        } else {
                            masterDetailView = MasterDetailView.TABLE_AND_DETAIL;
                        }
                    }
                }
            }
        }
        return masterDetailView;
    }

    /**
     * @return the master/detail view sequence
     */
    @Override
    public int getMasterDetailViewSequence() {
        return _masterDetailViewSequence;
    }

    public void setMasterDetailViewSequence(int sequence) {
        checkScope();
        _masterDetailViewSequence = sequence;
    }

    /**
     * @return the master/detail view menu option enabled indicator
     */
    @Override
    public boolean isMasterDetailViewMenuOptionEnabled() {
        return isMasterDetailViewMenuOptionEnabled(null);
    }

    /**
     * @param display the display that is opened by the menu option
     * @return the master/detail view menu option enabled indicator
     */
    @Override
    public boolean isMasterDetailViewMenuOptionEnabled(Display display) {
        boolean unspecified = _annotatedWithOneToOne || (display == null ? aggregateless() : !DisplayMode.WRITING.equals(display.getDisplayMode()) || aggregateless());
        return _masterDetailViewMenuOptionEnabled.toBoolean(unspecified);
    }

    private boolean aggregateless() {
        return _mappedCollection == null || _mappedCollection.getAggregatesList().isEmpty();
    }

    /**
     * @return the master sequence master field indicator
     */
    @Override
    public boolean isMasterSequenceMasterField() {
        return _masterSequenceMasterField;
    }

    /**
     * @param b the master sequence master field indicator to set
     */
    @Override
    public void setMasterSequenceMasterField(boolean b) {
        checkScope();
        _masterSequenceMasterField = b;
    }

    /**
     *
     */
    private final List<Property> _masterDependentProperties = new ArrayList<>();

    /**
     * @return the master-dependent property list
     */
    @Override
    public List<Property> getMasterDependentProperties() {
        MasterDetailView masterDetailView = getMasterDetailView();
        return MasterDetailView.UNSPECIFIED.equals(masterDetailView) || MasterDetailView.NONE.equals(masterDetailView) ? null : _masterDependentProperties;
    }

    /**
     * El método setMasterDependentProperties se utiliza para establecer la lista de propiedades dependientes de referencias que identifican un
     * maestro de vistas (páginas) maestro/detalle. Este método es irrelevante para referencias que no tengan vistas Maestro/Detalle disponibles; la
     * disponibilidad de vistas Maestro/Detalle la determina el elemento view de la anotación ManyToOne de la referencia. Las propiedades dependientes
     * del maestro se omiten en el detalle de las correspondientes vistas Maestro/Detalle.
     *
     * @param properties lista de propiedades dependientes del maestro
     */
    @Override
    public void setMasterDependentProperties(Property... properties) {
        checkScope();
        _masterDependentProperties.clear();
        if (properties != null && properties.length > 0) {
            _masterDependentProperties.addAll(Arrays.asList(properties));
        }
    }

    private EntityReference _missingInstanceQuickAddingMasterOverride;

    /**
     * @return the missing-instance-quick-adding-master override
     */
    @Override
    public EntityReference getMissingInstanceQuickAddingMasterOverride() {
        return _missingInstanceQuickAddingMasterOverride;
    }

    /**
     * El método setMissingInstanceQuickAddingMasterOverride se utiliza para establecer el maestro que debe utilizar, en lugar de esta referencia,
     * para construir el filtro de la función Adición Rápida en las vistas (páginas) maestro/detalle. Este método es irrelevante para referencias que
     * no tengan vistas Maestro/Detalle disponibles; la disponibilidad de vistas Maestro/Detalle la determina el elemento view de la anotación
     * ManyToOne de la referencia.
     *
     * @param reference maestro de adición rápida de instancias faltantes
     */
    @Override
    public void setMissingInstanceQuickAddingMasterOverride(EntityReference reference) {
        checkScope();
        _missingInstanceQuickAddingMasterOverride = reference;
    }

    /**
     * @return the quick-adding filter
     */
    @Override
    public QuickAddingFilter getQuickAddingFilter() {
        return _quickAddingFilter;
    }

    /**
     * @return the key-properties-query-mapping indicator
     */
    @Override
    public boolean isKeyPropertiesQueryMappingEnabled() {
        return _keyPropertiesQueryMappingEnabled;
    }

    /**
     * @return the calculable value
     */
    @Override
    public Object getCalculableValue() {
        return _calculableValue;
    }

    /**
     * El método linkCalculableValueEntityReference enlaza el valor la propiedad a la entidad referenciada. El valor de una referencia (propiedad que
     * hace referencia a una entidad) definida como columna calculable (mediante el elemento calculable de la anotación ColumnField) se puede enlazar
     * a otra referencia (no calculable) mediante el método linkCalculableValueEntityReference de la propiedad.
     *
     * @param entity referencia (no calculable) a una entidad
     */
    public void linkCalculableValueEntityReference(Entity entity) {
        setCalculableValueEntityReference(entity);
    }

    /**
     * El método linkCalculableValueExpression se utiliza para establecer la expresión para el cálculo del valor de propiedades definidas como
     * columnas calculables (mediante el elemento calculable de la anotación ColumnField).
     *
     * @param expression expresión para el cálculo del valor
     */
    public void linkCalculableValueExpression(EntityExpression expression) {
        setCalculableValueExpression(expression);
    }

    /**
     * El método setCalculableValueEntityReference enlaza el valor la propiedad a la entidad referenciada. El valor de una referencia (propiedad que
     * hace referencia a una entidad) definida como columna calculable (mediante el elemento calculable de la anotación ColumnField) se puede enlazar
     * a otra referencia (no calculable) mediante el método setCalculableValueEntityReference de la propiedad.
     *
     * @param entity referencia (no calculable) a una entidad
     */
    public void setCalculableValueEntityReference(Entity entity) {
        checkScope();
        _calculableValue = entity;
    }

    /**
     * El método setCalculableValueExpression se utiliza para establecer la expresión para el cálculo del valor de propiedades definidas como columnas
     * calculables (mediante el elemento calculable de la anotación ColumnField).
     *
     * @param expression expresión para el cálculo del valor
     */
    public void setCalculableValueExpression(EntityExpression expression) {
        checkScope();
        _calculableValue = validCalculableValue(expression) ? expression : null;
    }

    /**
     * @return the initial value
     */
    @Override
    public Object getInitialValue() {
        return getInitialValue(_initialValue);
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    @Override
    public void setInitialValue(Entity initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    @Override
    public void setInitialValue(Instance initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    @Override
    public void setInitialValue(EntityExpression initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) ? initialValue : null;
    }

    /**
     * El método setInitialValue se utiliza para establecer el valor inicial de propiedades y parámetros. El valor inicial de las propiedades se
     * utiliza para inicializar el valor de la propiedad en la operación insert de las vistas (páginas) de registro. El valor inicial de los
     * parámetros se utiliza para inicializar el valor del parámetro al ejecutar la operación.
     *
     * @param initialValue valor inicial de la propiedad o el parámetro
     */
    @Override
    public void setInitialValue(SpecialEntityValue initialValue) {
        checkScope();
        _initialValue = validInitialValue(initialValue) && validSpecialEntityValue(initialValue) ? initialValue : null;
    }

    /**
     * @return the default value
     */
    @Override
    public Object getDefaultValue() {
        return _defaultValue;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    @Override
    public void setDefaultValue(Entity defaultValue) {
        checkScope();
//      _defaultValue = validInitialValue(defaultValue) ? defaultValue.self() : null;
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    @Override
    public void setDefaultValue(Instance defaultValue) {
        checkScope();
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    @Override
    public void setDefaultValue(EntityExpression defaultValue) {
        checkScope();
        _defaultValue = validDefaultValue(defaultValue) ? defaultValue : null;
    }

    /**
     * El método setDefaultValue se utiliza para establecer el valor por omisión de propiedades y parámetros. El valor por omisión de las propiedades
     * se utiliza al almacenar el valor de la propiedad en la base de datos, dependiendo de la opción seleccionada para el elemento defaultCondition
     * de la anotación PropertyField. El valor por omisión de los parámetros se utiliza al ejecutar la operación.
     *
     * @param defaultValue valor por omisión de la propiedad o el parámetro
     */
    @Override
    public void setDefaultValue(SpecialEntityValue defaultValue) {
        checkScope();
        _defaultValue = validDefaultValue(defaultValue) && validSpecialEntityValue(defaultValue) ? defaultValue : null;
        if (SpecialEntityValue.CURRENT_USER.equals(_defaultValue)) {
            if (Checkpoint.UNSPECIFIED.equals(defaultCheckpoint())) {
                setDefaultCheckpoint(Checkpoint.USER_INTERFACE);
            }
        }
    }

    /**
     * @return the current value
     */
    @Override
    public Object getCurrentValue() {
        return _currentValue;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(Entity currentValue) {
        checkScope();
//      _currentValue = validInitialValue(currentValue) ? currentValue.self() : null;
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(Instance currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(EntityExpression currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) ? currentValue : null;
    }

    /**
     * El método setCurrentValue se utiliza para establecer el valor actual del parámetro.
     *
     * @param currentValue valor actual
     */
    public void setCurrentValue(SpecialEntityValue currentValue) {
        checkScope();
        _currentValue = validCurrentValue(currentValue) && validSpecialEntityValue(currentValue) ? currentValue : null;
    }

    private boolean validSpecialEntityValue(SpecialEntityValue value) {
        Class<? extends Entity> userEntityClass;
        if (value != null) {
            switch (value) {
                case CURRENT_USER -> {
                    Project project = TLC.getProject();
                    userEntityClass = project == null ? null : project.getUserEntityClass();
                    if (userEntityClass != null && userEntityClass.isAssignableFrom(getClass())) {
                        return true;
                    }
                }
                default -> {
                    return true;
                }
            }
        }
        String message = value + " is not a valid value for " + getFullName();
        logger.error(message);
        Project.increaseParserErrorCount();
        return false;
    }

    /**
     * @return the order by object
     */
    public Object getOrderBy() {
        return _orderBy;
    }

    /**
     * @return the order by property
     */
    public Property getOrderByProperty() {
        return _orderBy instanceof Property ? (Property) _orderBy : null;
    }

    /**
     * @return the order by properties
     */
    public Property[] getOrderByProperties() {
        return _orderBy instanceof Property[] ? (Property[]) _orderBy : null;
    }

    /**
     * @return the order by key
     */
    public Key getOrderByKey() {
        return _orderBy instanceof Key ? (Key) _orderBy : null;
    }

    /**
     * El método setOrderBy se utiliza para establecer el criterio de ordenamiento de las operaciones select, export y report de las vistas (páginas)
     * de consulta y/o registro de la entidad.
     *
     * @param orderBy propiedad que se utiliza como criterio de ordenamiento
     */
    public void setOrderBy(Property orderBy) {
        checkScope();
        _orderBy = orderBy;
    }

    /**
     * El método setOrderBy se utiliza para establecer el criterio de ordenamiento de las operaciones select, export y report de las vistas (páginas)
     * de consulta y/o registro de la entidad.
     *
     * @param orderBy una o más propiedades que se utilizan como criterio de ordenamiento, en la misma secuencia en la que son escritas
     */
    public void setOrderBy(Property... orderBy) {
        checkScope();
        _orderBy = orderBy;
    }

    /**
     * El método setOrderBy se utiliza para establecer el criterio de ordenamiento de las operaciones select, export y report de las vistas (páginas)
     * de consulta y/o registro de la entidad.
     *
     * @param orderBy clave de acceso cuyas propiedades se utilizan como criterio de ordenamiento, en la misma secuencia en la que aparecen en la
     * clave
     */
    public void setOrderBy(Key orderBy) {
        checkScope();
        _orderBy = orderBy;
    }

    /**
     * @return the default tab
     */
    public Tab getDefaultTab() {
        if (_defaultTab == null) {
            List<Tab> tabs = getTabsList();
            if (tabs != null && !tabs.isEmpty()) {
                _defaultTab = tabs.get(0);
            }
        }
        return _defaultTab;
    }

    /**
     * El método setDefaultTab de la entidad se utiliza para establecer la pestaña (tab) por defecto, es decir, la pestaña que recibe el enfoque al
     * abrir una vista (página) con pestañas de la entidad.
     *
     * @param tab pestaña que recibe el enfoque al abrir una vista con pestañas de la entidad
     */
    public void setDefaultTab(Tab tab) {
        checkScope();
        _defaultTab = tab;
    }

    /**
     * @return the default tab sequence number
     */
    public int getDefaultTabSequenceNumber() {
        Tab tab = getDefaultTab();
        return tab == null ? 0 : tab.getSequenceNumber();
    }

    /**
     * @return true if the entity has at least one display
     */
    @Override
    public boolean isDisplayAvailable() {
        if (_displayAvailable != null) {
            return _displayAvailable;
        }
        if (isGuiCodeGenEnabled()) {
            Project project = TLC.getProject();
            if (project != null) {
                List<? extends Display> displays = project.getDisplaysList();
                if (displays != null) {
                    for (Display display : displays) {
                        if (equals(display.getEntity())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void setDisplayAvailable(Boolean displayAvailable) {
        checkScope();
        _displayAvailable = displayAvailable;
    }

    /**
     * @return true if the entity is master of at least one detail
     */
    @Override
    public boolean isWritingPageMaster() {
        EntityReference reference;
        MasterDetailView masterDetailView;
        Class<? extends Entity> declaring;
        Entity detail;
        List<Property> properties = getReferencesList();
        for (Property property : properties) {
            reference = (EntityReference) property;
            if (reference.isOneToOne() && reference.isOneToOneDetailView()) {
                declaring = reference.getDeclaringEntity().getClass();
                if (declaring != null) {
                    detail = _project.getEntity(declaring);
                    if (detail != null && detail.isGuiCodeGenEnabled()) { // && detail.isSelectEnabled()
                        if (detail.isInsertEnabled() || detail.isUpdateEnabled() || detail.isDeleteEnabled()) {
                            return true;
                        }
                    }
                }
            }
            if (reference.isManyToOne()) {
                masterDetailView = reference.getMasterDetailView();
                switch (masterDetailView) {
                    case TABLE, TABLE_AND_DETAIL -> {
                        declaring = reference.getDeclaringEntity().getClass();
                        if (declaring != null) {
                            detail = _project.getEntity(declaring);
                            if (detail != null && detail.isGuiCodeGenEnabled()) { // && detail.isSelectEnabled()
                                if (detail.isInsertEnabled() || detail.isUpdateEnabled() || detail.isDeleteEnabled()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return the AbstractClass annotation indicator
     */
    public boolean isAnnotatedWithAbstractClass() {
        return _annotatedWithAbstractClass;
    }

    /**
     * @return the AllocationOverride annotation indicator
     */
    public boolean isAnnotatedWithAllocationOverride() {
        return _annotatedWithAllocationOverride;
    }

    /**
     * @return the AllocationOverrides annotation indicator
     */
    public boolean isAnnotatedWithAllocationOverrides() {
        return _annotatedWithAllocationOverrides;
    }

    /**
     * @return the EntityClass annotation indicator
     */
    public boolean isAnnotatedWithEntityClass() {
        return _annotatedWithEntityClass;
    }

    /**
     * @return the EntityDataGen annotation indicator
     */
    public boolean isAnnotatedWithEntityDataGen() {
        return _annotatedWithEntityDataGen;
    }

    /**
     * @return the EntitySelectOperation annotation indicator
     */
    public boolean isAnnotatedWithEntitySelectOperation() {
        return _annotatedWithEntitySelectOperation;
    }

    /**
     * @return the EntityInsertOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityInsertOperation() {
        return _annotatedWithEntityInsertOperation;
    }

    /**
     * @return the EntityUpdateOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityUpdateOperation() {
        return _annotatedWithEntityUpdateOperation;
    }

    /**
     * @return the EntityDeleteOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityDeleteOperation() {
        return _annotatedWithEntityDeleteOperation;
    }

    /**
     * @return the EntityReportOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityReportOperation() {
        return _annotatedWithEntityReportOperation;
    }

    /**
     * @return the EntityExportOperation annotation indicator
     */
    public boolean isAnnotatedWithEntityExportOperation() {
        return _annotatedWithEntityExportOperation;
    }

    /**
     * @return the EntityTableView annotation indicator
     */
    public boolean isAnnotatedWithEntityTableView() {
        return _annotatedWithEntityTableView;
    }

    /**
     * @return the EntityDetailView annotation indicator
     */
    public boolean isAnnotatedWithEntityDetailView() {
        return _annotatedWithEntityDetailView;
    }

    /**
     * @return the EntityTreeView annotation indicator
     */
    public boolean isAnnotatedWithEntityTreeView() {
        return _annotatedWithEntityTreeView;
    }

    /**
     * @return the EntityConsoleView annotation indicator
     */
    public boolean isAnnotatedWithEntityConsoleView() {
        return _annotatedWithEntityConsoleView;
    }

    /**
     * @return the EntityMasterView annotation indicator
     */
    public boolean isAnnotatedWithEntityMasterView() {
        return _annotatedWithEntityMasterView;
    }

    /**
     * @return the EntityWarnings annotation indicator
     */
    public boolean isAnnotatedWithEntityWarnings() {
        return _annotatedWithEntityWarnings;
    }

    /**
     * @return the EntityJsonCustomization annotation indicator
     */
    public boolean isAnnotatedWithEntityJsonCustomization() {
        return _annotatedWithEntityJsonCustomization;
    }

    /**
     * @return the EntityCodeGen annotation indicator
     */
    public boolean isAnnotatedWithEntityCodeGen() {
        return _annotatedWithEntityCodeGen;
    }

    /**
     * @return the EntityDocGen annotation indicator
     */
    public boolean isAnnotatedWithEntityDocGen() {
        return _annotatedWithEntityDocGen;
    }

    /**
     * @return the EntityReferenceConversionValidation annotation indicator
     */
    public boolean isAnnotatedWithEntityReferenceConversionValidation() {
        return _annotatedWithEntityReferenceConversionValidation;
    }

    /**
     * @return the EntityReferenceDisplay annotation indicator
     */
    public boolean isAnnotatedWithEntityReferenceDisplay() {
        return _annotatedWithEntityReferenceDisplay;
    }

    /**
     * @return the EntityReferenceSearch annotation indicator
     */
    public boolean isAnnotatedWithEntityReferenceSearch() {
        return _annotatedWithEntityReferenceSearch;
    }

    /**
     * @return the EntityViewLocation annotation indicator
     */
    public boolean isAnnotatedWithEntityViewLocation() {
        return _annotatedWithEntityViewLocation;
    }

    /**
     * @return the Filter annotation indicator
     */
    public boolean isAnnotatedWithFilter() {
        return _annotatedWithFilter;
    }

    /**
     * @return the OneToOne annotation indicator
     */
    public boolean isAnnotatedWithOneToOne() {
        return _annotatedWithOneToOne;
    }

    /**
     * @return the ManyToOne annotation indicator
     */
    public boolean isAnnotatedWithManyToOne() {
        return _annotatedWithManyToOne;
    }

    /**
     * @return the QueryMapping annotation indicator
     */
    public boolean isAnnotatedWithQueryMapping() {
        return _annotatedWithQueryMapping;
    }

    /**
     * @return the EntityReferenceDataGen annotation indicator
     */
    public boolean isAnnotatedWithEntityReferenceDataGen() {
        return _annotatedWithEntityReferenceDataGen;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CRUD operations">
    protected SelectOperation select;

    protected InsertOperation insert;

    protected UpdateOperation update;

    protected DeleteOperation delete;

    /**
     * @return the select operation
     */
    public SelectOperation getSelectOperation() {
        return select;
    }

    /**
     * @return the insert operation
     */
    public InsertOperation getInsertOperation() {
        return insert;
    }

    /**
     * @return the update operation
     */
    public UpdateOperation getUpdateOperation() {
        return update;
    }

    /**
     * @return the delete operation
     */
    public DeleteOperation getDeleteOperation() {
        return delete;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="CRUD operations filter tag fields, getters and setters">
    /**
     * @return the select filter tag
     */
//  @Override
    public String getSelectFilterTag() {
        return getLocalizedSelectFilterTag(null);
    }

    /**
     * El método setSelectFilterTag se utiliza para establecer la descripción del filtro de selección de las operaciones select, export y report que
     * se almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setSelectFilterTag(String tag) {
        setLocalizedSelectFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedSelectFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the select filter tag
     */
//  @Override
    public String getLocalizedSelectFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedSelectFilterTag.get(l);
    }

    /**
     * El método setLocalizedSelectFilterTag se utiliza para establecer la descripción del filtro de selección de las operaciones select, export y
     * report que se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado
     * por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la
     * descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setLocalizedSelectFilterTag(Locale locale, String tag) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedSelectFilterTag.remove(l);
        } else {
            _localizedSelectFilterTag.put(l, tag);
        }
    }

    /**
     * @return the insert filter tag
     */
//  @Override
    public String getInsertFilterTag() {
        return getLocalizedInsertFilterTag(null);
    }

    /**
     * El método setInsertFilterTag se utiliza para establecer la descripción del filtro de selección de la operación insert de las vistas (páginas)
     * de registro Maestro/Detalle de la entidad, en las que la propiedad es el Maestro, que se almacena en el archivo de recursos por defecto. En
     * caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el
     * archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setInsertFilterTag(String tag) {
        setLocalizedInsertFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedInsertFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the insert filter tag
     */
//  @Override
    public String getLocalizedInsertFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedInsertFilterTag.get(l);
    }

    /**
     * El método setLocalizedInsertFilterTag se utiliza para establecer la descripción del filtro de selección de la operación insert de las vistas
     * (páginas) de registro Maestro/Detalle de la entidad, en las que la propiedad es el Maestro, que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setLocalizedInsertFilterTag(Locale locale, String tag) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedInsertFilterTag.remove(l);
        } else {
            _localizedInsertFilterTag.put(l, tag);
        }
    }

    /**
     * @return the update filter tag
     */
//  @Override
    public String getUpdateFilterTag() {
        return getLocalizedUpdateFilterTag(null);
    }

    /**
     * El método setUpdateFilterTag se utiliza para establecer la descripción del filtro de selección de la operación update que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setUpdateFilterTag(String tag) {
        setLocalizedUpdateFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedUpdateFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the update filter tag
     */
//  @Override
    public String getLocalizedUpdateFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedUpdateFilterTag.get(l);
    }

    /**
     * El método setLocalizedUpdateFilterTag se utiliza para establecer la descripción del filtro de selección de la operación update que se almacena
     * en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setLocalizedUpdateFilterTag(Locale locale, String tag) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedUpdateFilterTag.remove(l);
        } else {
            _localizedUpdateFilterTag.put(l, tag);
        }
    }

    /**
     * @return the delete filter tag
     */
//  @Override
    public String getDeleteFilterTag() {
        return getLocalizedDeleteFilterTag(null);
    }

    /**
     * El método setDeleteFilterTag se utiliza para establecer la descripción del filtro de selección de la operación delete que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setDeleteFilterTag(String tag) {
        setLocalizedDeleteFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedDeleteFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the delete filter tag
     */
//  @Override
    public String getLocalizedDeleteFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedDeleteFilterTag.get(l);
    }

    /**
     * El método setLocalizedDeleteFilterTag se utiliza para establecer la descripción del filtro de selección de la operación delete que se almacena
     * en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setLocalizedDeleteFilterTag(Locale locale, String tag) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedDeleteFilterTag.remove(l);
        } else {
            _localizedDeleteFilterTag.put(l, tag);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="more filter tag fields, getters and setters">
    /**
     * @return the search query filter tag
     */
//  @Override
    public String getSearchQueryFilterTag() {
        return getLocalizedSearchQueryFilterTag(null);
    }

    /**
     * El método setSearchQueryFilterTag se utiliza para establecer la descripción del filtro de búsqueda del valor de la referencia que se almacena
     * en el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de búsqueda
     */
//  @Override
    public void setSearchQueryFilterTag(String tag) {
        setLocalizedSearchQueryFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedSearchQueryFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the search query filter tag
     */
//  @Override
    public String getLocalizedSearchQueryFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedSearchQueryFilterTag.get(l);
    }

    /**
     * El método setLocalizedSearchQueryFilterTag se utiliza para establecer la descripción del filtro de búsqueda del valor de la referencia que se
     * almacena en el archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario
     * no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de búsqueda
     */
//  @Override
    public void setLocalizedSearchQueryFilterTag(Locale locale, String tag) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedSearchQueryFilterTag.remove(l);
        } else {
            _localizedSearchQueryFilterTag.put(l, tag);
        }
    }

    /**
     * @return the master detail filter tag
     */
//  @Override
    public String getMasterDetailFilterTag() {
        return getLocalizedMasterDetailFilterTag(null);
    }

    /**
     * El método setMasterDetailFilterTag se utiliza para establecer la descripción del filtro de selección de la operación select de las vistas
     * (páginas) de consulta y registro Maestro/Detalle de la entidad, en las que la propiedad es el Maestro, que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setMasterDetailFilterTag(String tag) {
        setLocalizedMasterDetailFilterTag(null, tag);
    }

    /**
     * A descriptive word or phrase applied to the filter expression as a label or identifier.
     */
    private final Map<Locale, String> _localizedMasterDetailFilterTag = new LinkedHashMap<>();

    /**
     * @param locale the locale for the tag
     * @return the master detail filter tag
     */
//  @Override
    public String getLocalizedMasterDetailFilterTag(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedMasterDetailFilterTag.get(l);
    }

    /**
     * El método setLocalizedMasterDetailFilterTag se utiliza para establecer la descripción del filtro de selección de la operación select de las
     * vistas (páginas) de consulta y registro Maestro/Detalle de la entidad, en las que la propiedad es el Maestro, que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param tag una o más oraciones que describen muy brevemente el filtro de selección
     */
//  @Override
    public void setLocalizedMasterDetailFilterTag(Locale locale, String tag) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (tag == null) {
            _localizedMasterDetailFilterTag.remove(l);
        } else {
            _localizedMasterDetailFilterTag.put(l, tag);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="LocaleEntityReferenceKey">
    protected class LocaleEntityReference {

        private final Locale loc;

        private final EntityReference ref;

        private LocaleEntityReference(Locale locale, EntityReference reference) {
            loc = locale;
            ref = reference;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof LocaleEntityReference ler) {
                return loc.equals(ler.loc) && ref.equals(ler.ref);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(loc, ref);
        }

    }

    protected LocaleEntityReference localeEntityReferenceReadingKey(Locale locale, EntityReference reference) {
        if (reference == null) {
            throw new IllegalArgumentException("null entity reference");
        }
        Locale l = locale == null ? Bundle.getLocale() : locale;
        return new LocaleEntityReference(l, reference);
    }

    protected LocaleEntityReference localeEntityReferenceWritingKey(Locale locale, EntityReference reference) {
        if (reference == null) {
            throw new IllegalArgumentException("null entity reference");
        }
        Locale l = locale == null ? Bundle.getLocale() : Bundle.isSupportedLocale(locale) ? locale : null;
        if (l == null) {
            throw new IllegalArgumentException("Locale " + locale + " not supported yet.");
        }
        return new LocaleEntityReference(l, reference);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="LocaleDisplayFormatKey">
    protected class LocaleDisplayFormat {

        private final Locale loc;

        private final DisplayFormat fmt;

        private LocaleDisplayFormat(Locale locale, DisplayFormat format) {
            loc = locale;
            fmt = format;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof LocaleDisplayFormat ldf) {
                return loc.equals(ldf.loc) && fmt.equals(ldf.fmt);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(loc, fmt);
        }

    }

    protected LocaleDisplayFormat localeDisplayFormatReadingKey(Locale locale, DisplayFormat format) {
        if (format == null) {
            throw new IllegalArgumentException("null display format");
        }
        Locale l = locale == null ? Bundle.getLocale() : locale;
        return new LocaleDisplayFormat(l, format);
    }

    protected LocaleDisplayFormat localeDisplayFormatWritingKey(Locale locale, DisplayFormat format) {
        if (format == null) {
            throw new IllegalArgumentException("null display format");
        }
        Locale l = locale == null ? Bundle.getLocale() : Bundle.isSupportedLocale(locale) ? locale : null;
        if (l == null) {
            throw new IllegalArgumentException("Locale " + locale + " not supported yet.");
        }
        return new LocaleDisplayFormat(l, format);
    }
    // </editor-fold>

    public AbstractEntity(Artifact declaringArtifact, Field declaringField) {
        super();
        init(declaringArtifact, declaringField);
    }

    private void init(Artifact declaringArtifact, Field declaringField) {
        if (declaringArtifact == null) {
            throw new IllegalArgumentException("null declaring artifact");
        }
        Class<?> namedClass = getNamedClass();
        String className = namedClass.getSimpleName();
        String fieldName = declaringField == null ? className : declaringField.getName();
        setDataClass(namedClass);
        setDataType(namedClass);
        if (declaringArtifact instanceof Project project) {
            _project = project;
            _rootInstance = true;
            setDeclaredIndicators();
            /*
             * data type, project and the root indicator must be set before calling setDeclared
             * they are all used at initializeInheritanceFields, which is called by setDeclared
             */
            setDeclared(className);
        } else {
            Project currentProject = TLC.getProject();
            if (currentProject == null) {
                throw new UnexpectedRuntimeException("null current project");
            }
            _project = currentProject;
            _rootInstance = false;
            setDeclaredIndicators();
            setName(fieldName);
            setDeclaringArtifact(declaringArtifact);
            setDeclaringField(declaringField);
            initialise();
            settle();
        }
        init();
    }

    private void init() {
        Project project = TLC.getProject();
        _tableViewWithStickyHeader = project != null && project.isEntityTableViewWithStickyHeaderDefaultValue();
    }

    private boolean _alwaysAddSnippets = false;

    public boolean addHeadAndToolbarSnippetsEvenWhenNoneAreDefined() {
        return _alwaysAddSnippets;
    }

    protected void addHeadAndToolbarSnippetsEvenWhenNoneAreDefined(boolean b) {
        _alwaysAddSnippets = b;
    }

    private boolean _reuseWhenContains = true;

    public boolean reuseDefaultEntityLabelsWhenClassNameContainsPropertyFieldName() {
        return _reuseWhenContains;
    }

    protected void reuseDefaultEntityLabelsWhenClassNameContainsPropertyFieldName(boolean b) {
        _reuseWhenContains = b;
    }

    @Override
    protected void copyBootstrappingAttributes() {
        super.copyBootstrappingAttributes();
        _alwaysAddSnippets = Bootstrapping.addHeadAndToolbarSnippetsEvenWhenNoneAreDefined;
        _reuseWhenContains = Bootstrapping.reuseDefaultEntityLabelsWhenClassNameContainsPropertyFieldName;
    }

    private void setDeclaredIndicators() {
        ProjectEntityReference reference = _project.getEntityReference(getDataType());
        _explicitlyDeclared = reference.isExplicit();
        _implicitlyDeclared = reference.isImplicit();
    }

    // <editor-fold defaultstate="collapsed" desc="check">
//  private void check(Artifact declaringArtifact) {
//      if (declaringArtifact == null) {
//          Class<?> namedClass = getNamedClass();
//          String methodName = namedClass.getName() + '.' + XS.GET_INSTANCE;
//          int lineNumber = XS.getLineNumber(methodName, true);
//          if (lineNumber == 0) {
//              methodName = namedClass.getName() + '.' + "<init>";
//              lineNumber = XS.getLineNumber(methodName, false);
//              String msg = "null declaring artifact";
//              if (lineNumber > 0) {
//                  msg += " at " + methodName + "(" + namedClass.getSimpleName() + ".java:" + lineNumber + ")";
//              }
//              throw new IllegalArgumentException(msg);
//          }
//      }
//      String message = "";
//      message += getAbstractSuperclass().getSimpleName() + " ";
//      message += getNamedClass().getSimpleName() + " " + hashCode();
//      if (declaringArtifact != null) {
//          message += " @ ";
//          message += XS.getNamedClass(declaringArtifact).getSimpleName() + " " + declaringArtifact.hashCode();
//      }
//      logger.trace(message);
//  }
    // </editor-fold>
//
    @Override
    public final void initialise() {
        XS1.checkAccess();
        if (_initialised) {
            logger.warn(getFullName() + " already initialised! ");
            Project.increaseParserWarningCount();
            return;
        }
        _initialised = true;
        Artifact declaringArtifact = getDeclaringArtifact();
        if (declaringArtifact == null) {
            TLC.removeAllocationSettings();
            addAllocationStrings();
            logAllocationStrings(Project.getDetailLevel());
            _atlas.initialiseFields(Property.class);
            _atlas.initialiseFields(EntityCollection.class);
            _atlas.initialiseFields(Key.class);
            _atlas.initialiseFields(Step.class);
            _atlas.initialiseFields(Tab.class);
            _atlas.initialiseFields(View.class);
            _atlas.initialiseFields(Instance.class);
            _atlas.initialiseFields(NamedValue.class);
            _atlas.initialiseFields(Expression.class);
            _atlas.initialiseFields(Transition.class);
            _atlas.checkOperationClasses();
            _atlas.initialiseFields(Operation.class);
            _atlas.checkOperationFields();
            _atlas.initialiseFields(Trigger.class);
        } else {
            _atlas.initialiseFields(Property.class);
            _atlas.initialiseFields(EntityCollection.class);
            _atlas.initialiseFields(Instance.class);
            _atlas.initialiseFields(NamedValue.class);
            _atlas.initialiseFields(Expression.class);
        }
    }

    private final Set<String> _allocationStrings = new LinkedHashSet<>();

    @Override
    public Set<String> getAllocationStrings() {
        return _allocationStrings;
    }

    protected void addAllocationStrings() {
        track("addAllocationStrings");
    }

    /**
     * El método addAllocationStrings de la meta-entidad se utiliza para establecer sus cadenas de asignación (allocation strings). Las cadenas de
     * asignación permiten especificar meta-entidades referenciadas por expresiones de la meta-entidad, y que, por lo tanto, deben ser construidas
     * (instanciadas) al generar la aplicación.
     *
     * @param strings una o más cadenas de asignación (allocation strings). Cada cadena de asignación es una lista de referencias (meta-propiedades
     * que hacen referencia a otras meta-entidades) separadas por puntos (siguiendo la notación de puntos de Java), comenzando por una de las meta
     * propiedades definidas en la meta-entidad.
     */
    protected void addAllocationStrings(String... strings) {
        String name = getFullName();
        if (strings != null && strings.length > 0) {
            for (String string : strings) {
                if (StringUtils.isNotBlank(string)) {
                    _allocationStrings.add(name + "." + string);
                }
            }
        }
    }

    private void logAllocationStrings(Level level) {
        if (Level.OFF.equals(level) || Level.ALL.equals(level)) {
        } else if (_allocationStrings != null && !_allocationStrings.isEmpty()) {
            logger.log(level, getFullName() + ".logAllocationStrings(" + depth() + "/" + round() + ")");
            for (String string : _allocationStrings) {
                logger.log(level, string);
            }
        }
    }

    @Override
    public final void prepare() {
        /*
        XS1.checkAccess();
        if (_prepared) {
            logger.warn(getFullName() + " already prepared! ");
            Project.increaseParserWarningCount();
            return;
        }
        _prepared = true;
        _atlas.prepare();
        /**/
    }

    @Override
    public final void settle() {
        XS1.checkAccess();
        if (_settled) {
            logger.warn(getFullName() + " already settled! ");
            Project.increaseParserWarningCount();
            return;
        }
        _settled = true;
        Artifact declaringArtifact = getDeclaringArtifact();
        settleAttributes();
        if (declaringArtifact == null) {
            settleProperties();
            settleCollections();
            settleLinks();
            settleKeys();
            settleSteps();
            settleTabs();
            settleViews();
            settleInstances();
//          settleNamedValues();
            settleExpressions();
            verifyNames(Entity.class, Expression.class);
            settleFilters();
            settleTransitions();
            clinchOperations();
            settleOperations();
            settleTriggers();
//          verifyNames(Entity.class);
            verifyProperties();
        } else {
            if (declaringArtifact instanceof Operation) {
                settleExpressions();
            }
            setSqlName(null); // since 23/08/2024 - Nullify the SQL name eventually set for the root entity
            verifyLabels();
            verifyDescriptions();
        }
        _settler = '?';
        localize();
    }

    private void verifyProperties() {
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (!property.isAnnotated()) {
                logger.warn("property " + getFullName() + " not annotated yet");
                Project.increaseParserWarningCount();
            }
        }
    }

    private void verifyLabels() {
        String className = StrUtils.getHumplessCase(getDataClass().getSimpleName());
        String fieldName = StrUtils.getHumplessCase(getName());
        String shortName = StrUtils.removeWords(fieldName, EntityReference.class, "_");
        boolean preserve = className.equals(shortName) || (_reuseWhenContains && className.contains(shortName));
        if (!preserve) {
            // erase labels set by settleAttributes
            for (Locale locale : Bundle.getSupportedLocales()) {
                setLocalizedLabel(locale, null);
//              setLocalizedShortLabel(locale, (String) null); setLocalizedLabel also sets the short label
            }
        }
    }

    private void verifyDescriptions() {
        String className = StrUtils.getHumplessCase(getDataClass().getSimpleName());
        String fieldName = StrUtils.getHumplessCase(getName());
        String shortName = StrUtils.removeWords(fieldName, EntityReference.class, "_");
        boolean preserve = className.equals(shortName);
        if (!preserve) {
            // erase descriptions set by settleAttributes
            for (Locale locale : Bundle.getSupportedLocales()) {
                setLocalizedDescription(locale, null);
                setLocalizedShortDescription(locale, null); // setLocalizedDescription does not set short description to prevent excessive inline help
            }
        } else { // since 16/03/2023
            boolean linkedParameter = isLinkedParameter();
            for (Locale locale : Bundle.getSupportedLocales()) {
                // erase linked parameter descriptions set by settleAttributes
                if (linkedParameter) { // since 03/11/2024
                    setLocalizedDescription(locale, null);
                    setLocalizedShortDescription(locale, null);
                    continue;
                }
                // replace descriptions set by settleAttributes
                String lsd = getLocalizedShortDescription(locale);
                if (lsd != null) {
                    setLocalizedDescription(locale, lsd);
                    setLocalizedShortDescription(locale, null); // since 03/11/2024
                }
            }
        }
    }

    private boolean isLinkedParameter() {
        Field field = getDeclaringField();
        if (field != null) {
            ParameterField annotation = field.getAnnotation(ParameterField.class);
            if (annotation != null) {
                return StringUtils.isNotBlank(annotation.linkedField());
            }
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="settle">
    protected void settleAttributes() {
        track("settleAttributes");
        _settler = 'A';
    }

    protected void settleProperties() {
        track("settleProperties");
        _settler = 'P';
    }

    protected void settleCollections() {
        track("settleCollections");
        _settler = 'C';
    }

    protected void settleLinks() {
        track("settleLinks");
        _settler = 'L';
    }

    protected void settleKeys() {
        track("settleKeys");
        _settler = 'K';
    }

    protected void settleSteps() {
        track("settleSteps");
        _settler = 'S';
    }

    protected void settleTabs() {
        track("settleTabs");
        _settler = 'T';
    }

    protected void settleViews() {
        track("settleViews");
        _settler = 'V';
    }

    protected void settleInstances() {
        track("settleInstances");
        _settler = 'I';
    }

    protected void settleExpressions() {
        track("settleExpressions");
        _settler = 'X';
    }

    protected void settleFilters() {
        track("settleFilters");
        _settler = 'F';
    }

    protected void settleTransitions() {
        track("settleTransitions");
        _settler = 'R';
    }

    protected void settleOperations() {
        track("settleOperations");
        _settler = 'O';
    }

    protected void clinchOperations() {
        track("clinchOperations");
//      Class<?> type;
        Object o;
        Class<?> clazz = getClass();
        Class<?> top = Entity.class;
        for (Field field : XS1.getFields(clazz, top, Operation.class)) {
            field.setAccessible(true);
//          type = field.getType();
            if (isNotRestricted(field)) { // && Operation.class.isAssignableFrom(type)
                String errmsg = "failed to get field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o instanceof Operation operation) {
                        operation.settle();
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    protected void settleTriggers() {
        track("settleTriggers");
        _settler = 'G';
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="localize">
    protected void localize() {
        Artifact declaringArtifact = getDeclaringArtifact();
        localizeAttributes();
        if (declaringArtifact == null) {
            localizeProperties();
            localizeCollections();
            localizeLinks();
            localizeKeys();
            localizeSteps();
            localizeTabs();
            localizeViews();
            localizeInstances();
            localizeExpressions();
            localizeFilters();
            localizeTransitions();
            localizeOperations();
            localizeTriggers();
        } else {
            if (declaringArtifact instanceof Operation) {
                localizeExpressions();
            }
        }
    }

    protected void localizeAttributes() {
        track("localizeAttributes");
    }

    protected void localizeProperties() {
        track("localizeProperties");
    }

    protected void localizeCollections() {
        track("localizeCollections");
    }

    protected void localizeLinks() {
        track("localizeLinks");
    }

    protected void localizeKeys() {
        track("localizeKeys");
    }

    protected void localizeSteps() {
        track("localizeSteps");
    }

    protected void localizeTabs() {
        track("localizeTabs");
    }

    protected void localizeViews() {
        track("localizeViews");
    }

    protected void localizeInstances() {
        track("localizeInstances");
    }

    protected void localizeExpressions() {
        track("localizeExpressions");
    }

    protected void localizeFilters() {
        track("localizeFilters");
    }

    protected void localizeTransitions() {
        track("localizeTransitions");
    }

    protected void localizeOperations() {
        track("localizeOperations");
    }

    protected void localizeTriggers() {
        track("localizeTriggers");
    }
    // </editor-fold>

    private final Map<String, Property> _foreignQueryProperties = new LinkedHashMap<>();

    public List<Property> getForeignQueryPropertiesList() {
        return new ArrayList<>(_foreignQueryProperties.values());
    }

    public Map<String, Property> getForeignQueryPropertiesMap() {
        return _foreignQueryProperties;
    }

    /**
     * El método linkForeignQueryProperty agrega una propiedad no calculable a la vista de la entidad. La vista predeterminada de una entidad incluye,
     * además de todas sus propiedades, propiedades de entidades referenciadas que sean necesarias para la interfaz de usuario, el control de acceso y
     * otras funciones. Es posible agregar propiedades a la vista predeterminada de manera explícita, mediante los métodos linkForeignQueryProperty y
     * linkForeignQueryEntityReference.
     *
     * @param property propiedad no calculable de alguna entidad referenciada (directa o indirectamente)
     */
    public void linkForeignQueryProperty(Property property) {
        String message = "failed to link foreign query property to " + getFullName();
        if (property == null) {
            message += "; specified foreign property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (checkCalculablePropertyPath(property, message)) {
            String key = property.getPathString();
            if (_foreignQueryProperties.containsKey(key)) {
            } else {
                _foreignQueryProperties.put(key, property);
            }
        }
    }

    /**
     * El método linkForeignQueryProperty agrega una propiedad no calculable a la vista de la entidad. La vista predeterminada de una entidad incluye,
     * además de todas sus propiedades, propiedades de entidades referenciadas que sean necesarias para la interfaz de usuario, el control de acceso y
     * otras funciones. Es posible agregar propiedades a la vista predeterminada de manera explícita, mediante los métodos linkForeignQueryProperty y
     * linkForeignQueryEntityReference.
     *
     * @param properties una o más propiedades no calculables de alguna entidad referenciada (directa o indirectamente)
     */
    public void linkForeignQueryProperty(Property... properties) {
        for (Property property : properties) {
            linkForeignQueryProperty(property);
        }
    }

    /**
     * El método linkForeignQueryProperty agrega una propiedad no calculable a la vista de la entidad. La vista predeterminada de una entidad incluye,
     * además de todas sus propiedades, propiedades de entidades referenciadas que sean necesarias para la interfaz de usuario, el control de acceso y
     * otras funciones. Es posible agregar propiedades a la vista predeterminada de manera explícita, mediante los métodos linkForeignQueryProperty y
     * linkForeignQueryEntityReference.
     *
     * @param properties lista de propiedades no calculables de alguna entidad referenciada (directa o indirectamente)
     */
    public void linkForeignQueryProperty(List<Property> properties) {
        for (Property property : properties) {
            linkForeignQueryProperty(property);
        }
    }

    /**
     * El método linkForeignQueryEntityReference agrega las propiedades no calculables de una o más referencias (no calculables) a la vista de la
     * entidad. La vista predeterminada de una entidad incluye, además de todas sus propiedades, propiedades de entidades referenciadas que sean
     * necesarias para la interfaz de usuario, el control de acceso y otras funciones. Es posible agregar propiedades a la vista predeterminada de
     * manera explícita, mediante los métodos linkForeignQueryProperty y linkForeignQueryEntityReference.
     *
     * @param references una o más referencias (no calculables) a entidades referenciadas directa o indirectamente
     */
    public void linkForeignQueryEntityReference(EntityReference... references) {
        List<Property> propertiesList;
        for (EntityReference reference : references) {
            linkForeignQueryProperty(reference);
            propertiesList = reference.getPropertiesList();
            linkForeignQueryProperty(propertiesList);
        }
    }

    /**
     * El método linkForeignInactiveIndicatorProperty enlaza el indicador de inactividad al indicador de inactividad de una entidad referenciada. El
     * indicador de inactividad (eliminación lógica) de la entidad puede ser el indicador de inactividad de una de sus entidades referenciadas. Cuando
     * éste sea el caso, en lugar de utilizar la anotación InactiveIndicator se utiliza el método linkForeignInactiveIndicatorProperty.
     *
     * @param foreignInactiveIndicatorProperty indicador de inactividad de una entidad referenciada (vea Anotación InactiveIndicator)
     */
    public void linkForeignInactiveIndicatorProperty(BooleanProperty foreignInactiveIndicatorProperty) {
        String message = "failed to link foreign inactive indicator property to " + getFullName();
        if (foreignInactiveIndicatorProperty == null) {
            message += "; specified foreign property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (checkCalculablePropertyPath(foreignInactiveIndicatorProperty, message)) {
            _inactiveIndicatorProperty = foreignInactiveIndicatorProperty;
        }
    }

    /**
     * El método linkForeignOwnerProperty enlaza la propiedad propietario a la propiedad propietario de una entidad referenciada. La propiedad
     * propietario de la entidad puede ser la propiedad propietario de una de sus entidades referenciadas. Cuando éste sea el caso, en lugar de
     * utilizar la anotación OwnerProperty se utiliza el método linkForeignOwnerProperty.
     *
     * @param foreignOwnerProperty propiedad propietario de una entidad referenciada (vea Anotación OwnerProperty)
     */
    public void linkForeignOwnerProperty(EntityReference foreignOwnerProperty) {
        String message = "failed to link foreign owner property to " + getFullName();
        if (foreignOwnerProperty == null) {
            message += "; specified foreign property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (checkCalculablePropertyPath(foreignOwnerProperty, message)) {
            Class<?> foreignOwnerPropertyClass = foreignOwnerProperty.getClass();
            Project project = TLC.getProject();
            Class<? extends Entity> userEntityClass = project == null ? null : project.getUserEntityClass();
            if (userEntityClass != null && userEntityClass.isAssignableFrom(foreignOwnerPropertyClass)) {
                Field field = foreignOwnerProperty.getDeclaringField();
                boolean aye = field.isAnnotationPresent(OwnerProperty.class);
                if (aye) {
                    _ownerProperty = foreignOwnerProperty;
                } else {
                    message += "; " + field.getDeclaringClass().getSimpleName() + "." + field.getName() + " is not an owner property";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            } else {
                message += "; " + userEntityClass + " is not assignable from " + foreignOwnerPropertyClass;
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    /**
     * El método linkForeignSegmentProperty enlaza la propiedad segmento a la propiedad segmento de una entidad referenciada. La propiedad segmento de
     * la entidad puede ser la propiedad segmento de una de sus entidades referenciadas. Cuando éste sea el caso, en lugar de utilizar la anotación
     * SegmentProperty se utiliza el método linkForeignSegmentProperty.
     *
     * @param foreignSegmentProperty propiedad segmento de una entidad referenciada (vea Anotación SegmentProperty)
     */
    public void linkForeignSegmentProperty(EntityReference foreignSegmentProperty) {
        linkForeignSegmentProperty((Property) foreignSegmentProperty); // since 20210218
    }

    /**
     * El método linkForeignSegmentProperty enlaza la propiedad segmento a la propiedad segmento de una entidad referenciada. La propiedad segmento de
     * la entidad puede ser la propiedad segmento de una de sus entidades referenciadas. Cuando éste sea el caso, en lugar de utilizar la anotación
     * SegmentProperty se utiliza el método linkForeignSegmentProperty.
     *
     * @param foreignSegmentProperty propiedad segmento de una entidad referenciada (vea Anotación SegmentProperty)
     */
    public void linkForeignSegmentProperty(LongProperty foreignSegmentProperty) {
        linkForeignSegmentProperty((Property) foreignSegmentProperty); // since 20210218
    }

    private void linkForeignSegmentProperty(Property foreignSegmentProperty) { // since 20210218
        String message = "failed to link foreign segment property to " + getFullName();
        if (foreignSegmentProperty == null) {
            message += "; specified foreign property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
        } else if (checkCalculablePropertyPath(foreignSegmentProperty, message)) {
            Field field = foreignSegmentProperty.getDeclaringField();
            boolean aye = field.isAnnotationPresent(SegmentProperty.class);
            if (aye) {
                _segmentProperty = foreignSegmentProperty;
            } else {
                message += "; " + field.getDeclaringClass().getSimpleName() + "." + field.getName() + " is not a segment property";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private boolean isNotRestricted(Field field) {
        int modifiers = field.getModifiers();
        return !(Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers));
    }

    @Override
    public boolean finalise() {
        boolean ok = super.finalise();
        if (ok) {
            _atlas.finalise();
            Artifact declaringArtifact = getDeclaringArtifact();
            if (declaringArtifact == null) {
                finaliseProperties();
                finaliseCollections();
            }
            setKeyFields();
            setBusinessKey();
            setKeyProperties();
            setForeignKeyFields();
            setSearchValueFilter();
            setTableViewEnabledIndicator();
            setNullValueGraphicImageExpressions();
            setBasicDatabaseOperationsAttributes();
            setPropertiesDisplaySortKey();
        }
        return ok;
    }

    protected void finaliseProperties() {
        // field annotations can be overriden here
    }

    protected void finaliseCollections() {
        // field annotations can be overriden here
    }

    @Override
    public boolean finish() {
        boolean ok = super.finish();
        if (ok) {
            addSpecialNativeQuerySegments();
            finishProperties();
            check();
        }
        return ok;
    }

    private void finishProperties() {
        setResponsivePriorities();
        for (Property property : getPropertiesList()) {
            property.finish();
        }
    }

    private void setResponsivePriorities() {
        if (isTableViewEnabled() && TableResponsiveMode.AUTO.equals(getTableResponsiveMode())) {
            boolean b = false; // la tabla tiene al menos una columna con prioridad 0
            Property p = getBusinessKeyProperty();
            if (prioritizable(p)) {
                p.setResponsivePriority(0);
                b = true;
            }
            p = getNameProperty();
            if (prioritizable(p)) {
                p.setResponsivePriority(0);
                b = true;
            }
            if (!b) {
                for (Property q : getPropertiesList()) {
                    if (prioritizable(q) && q.isUnique()) {
                        q.setResponsivePriority(0);
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    for (Key k : getKeysList()) {
                        if (k.isUnique()) {
                            int n = 0;
                            List<KeyField> keyFields = k.getKeyFieldsList();
                            for (KeyField kf : keyFields) {
                                if (prioritizable(kf.getProperty())) {
                                    n++;
                                }
                            }
                            b = n > 0 && n == keyFields.size();
                            if (b) {
                                for (KeyField kf : keyFields) {
                                    kf.getProperty().setResponsivePriority(0);
                                }
                                break;
                            }
                        }
                    }
                }
            }
            if (b) {
                if (this instanceof PersistentEntity pe) {
                    p = pe.getDiscriminatorProperty();
                    if (prioritizable(p)) {
                        p.setResponsivePriority(1);
                    }
                }
                p = getInactiveIndicatorProperty();
                if (prioritizable(p)) {
                    p.setResponsivePriority(4);
                }
                Entity e = getStateProperty();
                if (e instanceof Property q && prioritizable(q)) {
                    q.setResponsivePriority(4);
                }
                e = getParentProperty();
                if (e instanceof Property q && prioritizable(q)) {
                    q.setResponsivePriority(5);
                }
                e = getOwnerProperty();
                if (e instanceof Property q && prioritizable(q)) {
                    q.setResponsivePriority(5);
                }
                DataArtifact a = getSegmentProperty();
                if (a instanceof Property q && prioritizable(q)) {
                    q.setResponsivePriority(5);
                }
                for (Property q : getPropertiesList()) {
                    if (prioritizable(q)) {
                        q.setResponsivePriority(6);
                    }
                }
            }
        }
    }

    private boolean prioritizable(Property p) {
        return p != null && p.getResponsivePriority() < 0 && p.isTableField();
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios de la entidad. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a una entidad, el valor de tal atributo será el último valor
     * agregado.
     *
     * @param property meta-propiedad (de la entidad) a la que corresponde el atributo.
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(Property property, String name, Object value) {
        return property == null ? null : property.addAttribute(name, value);
    }

    void setKeyFields() {
        setPrimaryKeyField();
        setSequenceField();
        setVersionField();
        setNameField();
        setDescriptionField();
        setImageField();
        setInactiveIndicatorField();
        setUrlField();
        setParentField();
        setOwnerField();
        setUserField();
        setSegmentField();
        setBusinessKeyField();
        setUniqueKeyFields();
        setStateField();
    }

    void setPrimaryKeyField() {
        Field field = getAnnotations().get(PrimaryKey.class);
        if (field != null) {
            Class<?> type = getDataType();
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            if (field.equals(getPrimaryKeyField(fieldName, type))) {
                _primaryKeyFieldName = fieldName;
                _primaryKeyField = field;
                if (IntegerProperty.class.isAssignableFrom(fieldType) && _numericKeyField == null) {
                    _numericKeyFieldName = fieldName;
                    _numericKeyField = field;
                }
            }
        }
    }

    void setSequenceField() {
        Field field = getAnnotations().get(SequenceProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getSequenceField(fieldName, type))) {
                _sequenceFieldName = fieldName;
                _sequenceField = field;
            }
        }
    }

    void setVersionField() {
        Field field = getAnnotations().get(VersionProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getVersionField(fieldName, type))) {
                _versionFieldName = fieldName;
                _versionField = field;
            }
        }
    }

    void setNameField() {
        Field field = getAnnotations().get(NameProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getNameField(fieldName, type))) {
                _nameFieldName = fieldName;
                _nameField = field;
            }
        }
    }

    void setDescriptionField() {
        Field field = getAnnotations().get(DescriptionProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getDescriptionField(fieldName, type))) {
                _descriptionFieldName = fieldName;
                _descriptionField = field;
            }
        }
    }

    void setImageField() {
        Field field = getAnnotations().get(ImageProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getImageField(fieldName, type))) {
                _imageFieldName = fieldName;
                _imageField = field;
            }
        }
    }

    void setInactiveIndicatorField() {
        Field field = getAnnotations().get(InactiveIndicator.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getInactiveIndicatorField(fieldName, type))) {
                _inactiveIndicatorFieldName = fieldName;
                _inactiveIndicatorField = field;
            }
        }
    }

    void setUrlField() {
        Field field = getAnnotations().get(UrlProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getUrlField(fieldName, type))) {
                _urlFieldName = fieldName;
                _urlField = field;
            }
        }
    }

    void setParentField() {
        Field field = getAnnotations().get(ParentProperty.class);
        if (field != null) {
            String fieldName = field.getName();
            if (field.equals(getParentField(fieldName, field.getDeclaringClass()))) {
                _parentFieldName = fieldName;
                _parentField = field;
            }
        }
    }

    void setOwnerField() {
        Field field = getAnnotations().get(OwnerProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getOwnerField(fieldName, type))) {
                _ownerFieldName = fieldName;
                _ownerField = field;
            }
        }
    }

    void setUserField() {
        Field field = getAnnotations().get(UserProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getUserField(fieldName, type))) {
                _userFieldName = fieldName;
                _userField = field;
            }
        }
    }

    void setSegmentField() {
        Field field = getAnnotations().get(SegmentProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getSegmentField(fieldName, type))) {
                _segmentFieldName = fieldName;
                _segmentField = field;
            }
        }
    }

    void setBusinessKeyField() {
        Field field = getAnnotations().get(BusinessKey.class);
        if (field != null) {
            Class<?> type = getDataType();
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            if (field.equals(getBusinessKeyField(fieldName, type))) {
                _businessKeyFieldName = fieldName;
                _businessKeyField = field;
                if (IntegerProperty.class.isAssignableFrom(fieldType)) {
                    _numericKeyFieldName = fieldName;
                    _numericKeyField = field;
                }
                if (StringProperty.class.isAssignableFrom(fieldType)) {
                    _characterKeyFieldName = fieldName;
                    _characterKeyField = field;
                }
            }
        }
    }

    void setUniqueKeyFields() {
//      Class<?> type;
        Object o;
        Class<?> clazz = getClass();
        Class<?> top = Entity.class;
        for (Field field : XS1.getFields(clazz, top, Property.class)) {
            field.setAccessible(true);
//          type = field.getType();
            if (isNotRestricted(field)) { // && Property.class.isAssignableFrom(type)
                String errmsg = "failed to get field \"" + field + "\" at " + this;
                try {
                    o = field.get(this);
                    if (o instanceof Property) {
                        setKeyField(field);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.error(errmsg, ThrowableUtils.getCause(ex));
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    void setStateField() {
        Field field = getAnnotations().get(StateProperty.class);
        if (field != null) {
            Class<?> type = getDataType();
            String fieldName = field.getName();
            if (field.equals(getStateField(fieldName, type))) {
                StateProperty annotation = field.getAnnotation(StateProperty.class);
                if (annotation != null) {
                    _stateFieldName = fieldName;
                    _stateField = field;
                }
            }
        }
    }

    void setKeyField(Field field) {
        Class<?> type = getDataType();
        Class<?> fieldType = field.getType();
        String fieldName = field.getName();
        if (field.isAnnotationPresent(UniqueKey.class)) {
            if (field.equals(getUniqueKeyField(fieldName, type))) {
                if (IntegerProperty.class.isAssignableFrom(fieldType) && _numericKeyField == null) {
                    _numericKeyFieldName = fieldName;
                    _numericKeyField = field;
                }
                if (StringProperty.class.isAssignableFrom(fieldType) && _characterKeyField == null) {
                    _characterKeyFieldName = fieldName;
                    _characterKeyField = field;
                }
            }
        }
    }

    void setKeyProperties() {
        Object keyProperty;
        keyProperty = getKeyProperty(_primaryKeyField);
        if (keyProperty instanceof Property property) {
            _primaryKeyProperty = property;
        }
        keyProperty = getKeyProperty(_sequenceField);
        if (keyProperty instanceof LongProperty longProperty) {
            _sequenceProperty = longProperty;
        }
        keyProperty = getKeyProperty(_versionField);
        if (keyProperty instanceof LongProperty longProperty) {
            _versionProperty = longProperty;
        }
        keyProperty = getKeyProperty(_numericKeyField);
        if (keyProperty instanceof IntegerProperty integerProperty) {
            _numericKeyProperty = integerProperty;
        }
        keyProperty = getKeyProperty(_characterKeyField);
        if (keyProperty instanceof StringProperty stringProperty) {
            _characterKeyProperty = stringProperty;
        }
        keyProperty = getKeyProperty(_nameField);
        if (keyProperty instanceof StringProperty stringProperty) {
            _nameProperty = stringProperty;
        }
        keyProperty = getKeyProperty(_descriptionField);
        if (keyProperty instanceof StringProperty stringProperty) {
            _descriptionProperty = stringProperty;
        }
        keyProperty = getKeyProperty(_imageField);
        if (keyProperty instanceof BinaryProperty binaryProperty) {
            _imageProperty = binaryProperty;
        }
        keyProperty = getKeyProperty(_inactiveIndicatorField);
        if (keyProperty instanceof BooleanProperty booleanProperty) {
            _inactiveIndicatorProperty = booleanProperty;
        }
        keyProperty = getKeyProperty(_urlField);
        if (keyProperty instanceof StringProperty stringProperty) {
            _urlProperty = stringProperty;
        }
        keyProperty = getKeyProperty(_parentField);
        if (keyProperty instanceof Entity) {
            _parentProperty = (EntityReference) keyProperty;
        }
        keyProperty = getKeyProperty(_ownerField);
        if (keyProperty instanceof Entity) {
            _ownerProperty = (EntityReference) keyProperty;
        }
        keyProperty = getKeyProperty(_userField);
        if (keyProperty instanceof Entity) {
            _userProperty = (EntityReference) keyProperty;
        }
        keyProperty = getKeyProperty(_segmentField);
        if (keyProperty instanceof Entity) {
            _segmentProperty = (EntityReference) keyProperty;
        } else if (keyProperty instanceof LongProperty longProperty) { // since20210218
            _segmentProperty = longProperty;
        }
        keyProperty = getKeyProperty(_businessKeyField);
        if (keyProperty instanceof Property property) {
            _businessKeyProperty = property;
        }
        keyProperty = getKeyProperty(_stateField);
        if (keyProperty instanceof Entity) {
            _stateProperty = (EntityReference) keyProperty;
        }
    }

    Object getKeyProperty(Field field) {
        if (field != null) {
            try {
                return field.get(this);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Throwable cause = ThrowableUtils.getCause(ex);
                String message = ex.equals(cause) ? null : ex.getMessage();
                logger.error(message, cause);
                Project.increaseParserErrorCount();
            }
        }
        return null;
    }

    private void setForeignKeyFields() {
        setForeignInactiveIndicatorProperty();
        setForeignOwnerProperty();
        setForeignSegmentProperty();
    }

    private void setForeignInactiveIndicatorProperty() {
        if (_inactiveIndicatorField == null && _inactiveIndicatorProperty != null) {
            String name = _inactiveIndicatorProperty.getName();
            String role = "inactive indicator property";
            String rootName = getRoot().getName();
            String message = "failed to link foreign " + role + " at entity " + rootName;
            Entity declaringEntity = _inactiveIndicatorProperty.getDeclaringEntity();
            if (declaringEntity == null) {
                message += "; declaring entity of \"" + name + "\" is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            } else {
                String declaringEntityName = declaringEntity.getRoot().getName();
                Field declaringField = _inactiveIndicatorProperty.getDeclaringField();
                if (declaringField == null) {
                    message += "; declaring field of \"" + name + "\" is null";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                } else {
                    String declaringFieldName = declaringField.getName();
                    BooleanProperty inactiveIndicatorProperty = declaringEntity.getInactiveIndicatorProperty();
                    if (_inactiveIndicatorProperty.equals(inactiveIndicatorProperty)) {
                        _inactiveIndicatorField = declaringField;
                        _inactiveIndicatorFieldName = declaringFieldName;
                        _foreignInactiveIndicatorProperty = true;
                    } else {
                        message += "; " + declaringFieldName + " is not the " + role + " of " + declaringEntityName;
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private void setForeignOwnerProperty() {
        if (_ownerField == null && _ownerProperty != null) {
            String name = _ownerProperty.getName();
            String role = "owner property";
            String rootName = getRoot().getName();
            String message = "failed to link foreign " + role + " at entity " + rootName;
            Entity declaringEntity = _ownerProperty.getDeclaringEntity();
            if (declaringEntity == null) {
                message += "; declaring entity of \"" + name + "\" is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            } else {
                String declaringEntityName = declaringEntity.getRoot().getName();
                Field declaringField = _ownerProperty.getDeclaringField();
                if (declaringField == null) {
                    message += "; declaring field of \"" + name + "\" is null";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                } else {
                    String declaringFieldName = declaringField.getName();
                    Entity ownerProperty = declaringEntity.getOwnerProperty();
                    if (_ownerProperty.equals(ownerProperty)) {
                        _ownerField = declaringField;
                        _ownerFieldName = declaringFieldName;
                        _foreignOwnerProperty = true;
                    } else {
                        message += "; " + declaringFieldName + " is not the " + role + " of " + declaringEntityName;
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private void setForeignSegmentProperty() {
        if (_segmentField == null && _segmentProperty != null) {
            String name = _segmentProperty.getName();
            String role = "segment property";
            String rootName = getRoot().getName();
            String message = "failed to link foreign " + role + " at entity " + rootName;
            Entity declaringEntity = _segmentProperty.getDeclaringEntity();
            if (declaringEntity == null) {
                message += "; declaring entity of \"" + name + "\" is null";
                logger.error(message);
                Project.increaseParserErrorCount();
            } else {
                String declaringEntityName = declaringEntity.getRoot().getName();
                Field declaringField = _segmentProperty.getDeclaringField();
                if (declaringField == null) {
                    message += "; declaring field of \"" + name + "\" is null";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                } else {
                    String declaringFieldName = declaringField.getName();
                    DataArtifact segmentProperty = declaringEntity.getSegmentProperty(); // since 20210218
                    if (_segmentProperty.equals(segmentProperty)) {
                        _segmentField = declaringField;
                        _segmentFieldName = declaringFieldName;
                        _foreignSegmentProperty = true;
                    } else {
                        message += "; " + declaringFieldName + " is not the " + role + " of " + declaringEntityName;
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private void setTableViewEnabledIndicator() {
        if (_tableViewEnabled == null) {
            _tableViewEnabled = !isEnumerationEntity() || isUpdateEnabled();
        }
    }

    private void setNullValueGraphicImageExpressions() {
        if (depth() == 0) {
            List<Property> properties = getPropertiesList();
            for (Property property : properties) {
                property.setNullValueGraphicImageExpression();
            }
        }
    }

    private void setBasicDatabaseOperationsAttributes() {
        Operation operation;
        /**/
        if (select != null) {
            operation = select;
            operation.setOperationAccess(_selectOperationAccess);
        }
        /**/
        if (insert != null) {
            operation = insert;
            operation.setOperationAccess(_insertOperationAccess);
            operation.setOperationLogging(_insertLogging);
        }
        /**/
        if (update != null) {
            operation = update;
            operation.setOperationAccess(_updateOperationAccess);
            operation.setOperationLogging(_updateLogging);
        }
        /**/
        if (delete != null) {
            operation = delete;
            operation.setOperationAccess(_deleteOperationAccess);
            operation.setOperationLogging(_deleteLogging);
        }
        /**/
    }

    /**
     * anchor-linked detail fields indicator
     */
    private boolean _containsAnchorLinkedDetailFields;

    /**
     * @return the contains anchor-linked detail fields indicator
     */
    public boolean containsAnchorLinkedDetailFields() {
        return _containsAnchorLinkedDetailFields;
    }

    private void setPropertiesDisplaySortKey() {
        if (_rootInstance) {
            List<Property> list = getPropertiesList();
            Comparator<Property> comparator = new ByPropertySequence();
            list = (List<Property>) ColUtils.sort(list, comparator);
            int i = 0;
            for (Property property : list) {
                property.setDisplaySortKey(String.format("%04d", i++));
            }
            for (Property property : list) {
                if (property.getAnchorProperty() == null) {
                    setChildrenDisplaySortKey(property, list);
                } else if (property.isDetailField()) {
                    boolean unlinked = AnchorType.UNLINKED.equals(property.getAnchorType());
                    if (!unlinked) {
                        _containsAnchorLinkedDetailFields = true;
                    }
                }
            }
        }
    }

    private void setChildrenDisplaySortKey(Property parent, List<Property> list) {
        int i = 0;
        boolean first = true;
        for (Property child : list) {
            if (child.getAnchorProperty() == parent) {
                boolean unlinked = AnchorType.UNLINKED.equals(child.getAnchorType());
                if (!unlinked && child.isDetailField()) {
                    parent.setAnchoringLinkedDetailFields(true);
                    if (first) {
                        first = false;
                        parent.setFirstAnchoredFieldAnchorType(child.getAnchorType());
                    }
                }
                child.setDisplaySortKey(parent.getDisplaySortKey() + (unlinked ? '/' : '-') + String.format("%02d", i++));
                setChildrenDisplaySortKey(child, list); // como '/' es mayor que '-' las UNLINKED quedan después de las enlazadas
            }
        }
    }

    @Override
    protected boolean checkName() {
        if (super.checkName()) {
            String name = getName();
            if (depth() == 0 && !name.matches("^[A-Z][a-z]\\w*$")) {
                logger.error(getFullName() + " must be renamed; " + name + " is an invalid entity name");
                Project.increaseParserErrorCount();
            } else if (Character.isUpperCase(name.charAt(0)) && ((isProperty() && depth() == 1) || isParameter())) {
                String tag = isParameter() ? "parameter" : "property";
                logger.error(tag + " " + getFullName() + " must be renamed; " + name + " must begin with a lowercase letter; "
                    + "therefore it should be " + StringUtils.uncapitalize((name)));
                Project.increaseParserErrorCount();
            } else {
                return true;
            }
        }
        return false;
    }

    private void check() {
        checkExpressions();
        checkMissingInstanceQuickAddingMasterOverride();
        if (_rootInstance) {
            checkParentField();
            checkSequenceField();
            checkReferenceFields();
            checkBinaryFields();
            checkNumericFields();
            checkStringFields();
            checkCollectionFields();
            checkCalculableFields();
            checkInstances();
            checkKeys();
            checkSteps();
            checkTabs();
            checkViews();
            checkAggregates();
            checkImageProperty();
            if (_warningsEnabled) {
                if (_triggersWarningsEnabled) {
                    checkTriggers();
                }
                if (_treeViewWarningsEnabled) {
                    checkTreeView();
                }
                if (_visibleFieldsWarningsEnabled) {
                    checkVisibleFields();
                }
                if (_businessKeyWarningsEnabled) {
                    if (_businessKeyProperty == null) {
                        checkBusinessKeyProperty();
                    } else {
                        checkBusinessKeyPropertyDefaultValue();
                    }
                }
            }
        }
    }

    private void checkExpressions() {
        Object o;
        Expression e;
        e = getSelectFilter();
        if (e != null) {
            verifyExpression(e, ExpressionUsage.SELECT_FILTER);
        }
        e = getUpdateFilter();
        if (e != null) {
            verifyExpression(e, ExpressionUsage.UPDATE_FILTER);
        }
        e = getDeleteFilter();
        if (e != null) {
            verifyExpression(e, ExpressionUsage.DELETE_FILTER);
        }
        for (Property property : getPropertiesList()) {
            e = property.getRenderingFilter();
            if (e != null) {
                verifyExpression(e, property, ExpressionUsage.RENDERING_FILTER, false);
            }
            e = property.getRequiringFilter();
            if (e != null) {
                verifyExpression(e, property, ExpressionUsage.REQUIRING_FILTER, false);
            }
            e = property.getModifyingFilter();
            if (e != null) {
                verifyExpression(e, property, ExpressionUsage.MODIFYING_FILTER, false);
            }
            e = property.getNullifyingFilter();
            if (e != null) {
                verifyExpression(e, property, ExpressionUsage.NULLIFYING_FILTER, false);
            }
            if (property instanceof Entity entity) {
                e = entity.getSearchQueryFilter();
                if (e != null) {
                    verifyExpression(e, property, ExpressionUsage.SEARCH_QUERY_FILTER, false);
                }
            }
            o = property.getInitialValue();
            if (o instanceof Expression expression) {
                e = expression;
                verifyExpression(e, property, ExpressionUsage.INITIAL_VALUE, false);
            }
            o = property.getDefaultValue();
            if (o instanceof Expression expression) {
                e = expression;
                verifyExpression(e, property, ExpressionUsage.DEFAULT_VALUE);
            }
            o = property.getCurrentValue();
            if (o instanceof Expression expression) {
                e = expression;
                verifyExpression(e, property, ExpressionUsage.CURRENT_VALUE);
            }
            if (property instanceof IntervalizedArtifact ia) {
                o = ia.getMaxValue();
                if (o instanceof Expression expression) {
                    e = expression;
                    verifyExpression(e, property, ExpressionUsage.MAX_VALUE);
                }
                o = ia.getMinValue();
                if (o instanceof Expression expression) {
                    e = expression;
                    verifyExpression(e, property, ExpressionUsage.MIN_VALUE);
                }
            }
        }
        /*
        for (Step step : _atlas.getStepsList()) {
            e = step.getRenderingFilter();
            if (e != null) {
                verifyExpression(e, step, ExpressionUsage.RENDERING_FILTER, false);
            }
        }
        /**/
        for (Tab tab : _atlas.getTabsList()) {
            e = tab.getRenderingFilter();
            if (e != null) {
                verifyExpression(e, tab, ExpressionUsage.RENDERING_FILTER, false);
            }
        }
        for (Expression expression : _atlas.getExpressionsList()) {
            if (expression != null) {
                verifyExpression(expression);
            }
        }
    }

    private void checkParentField() {
        String name = getName();
        if (_parentProperty != null && !_parentProperty.isInherited()) {
            HierarchyNodeType hierarchyNodeType = getHierarchyNodeType();
            if (hierarchyNodeType != null && !hierarchyNodeType.equals(HierarchyNodeType.ROOT)) {
                String message = name + " has a parent property but it is not the hierarchy's root; "
                    + "only the hierarchy's root can have a parent property";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkSequenceField() {
        String name = getName();
        if (_sequenceProperty != null) {
            if (isDataGenEnabled() && _sequenceProperty.isSequencePropertyDataGenDisabled()) {
                String message = name + " sequence property data generation is disabled; "
                    + "check elements start/stop/step of its SequenceProperty and NumericDataGen annotations ";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkReferenceFields() {
        String name = getName();
        String msg1 = name + " has the following mutually exclusive annotations: ";
        Set<String> annotations = new TreeSet<>();
        Field field;
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (property.isEntity()) {
                field = property.getDeclaringField();
                if (field != null) {
                    annotations.clear();
                    if (field.isAnnotationPresent(OneToOne.class)) {
                        annotations.add("OneToOne");
                    }
                    if (field.isAnnotationPresent(ManyToOne.class)) {
                        annotations.add("ManyToOne");
                    }
                    if (annotations.size() > 1) {
                        logger.error(msg1 + StringUtils.join(annotations, ", "));
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private static final boolean anyMasterDetailView = true;

    private void checkMissingInstanceQuickAddingMasterOverride() {
        EntityReference master = this;
        EntityReference miqamo = _missingInstanceQuickAddingMasterOverride;
        String message = getFullName() + " has an invalid missing-instance-quick-adding-master override";
        if (miqamo != null) {
            MasterDetailView mdv = miqamo.getMasterDetailView();
            if (anyMasterDetailView || MasterDetailView.TABLE.equals(mdv) || MasterDetailView.TABLE_AND_DETAIL.equals(mdv)) {
                Object iv = miqamo.getInitialValue();
                if (iv instanceof EntityReference iver && master.getPropertiesList().contains(iver)) {
                    Entity decent = miqamo.getDeclaringEntity();
                    if (decent != null && decent.equals(getDeclaringEntity())) {
                        for (Property property : _masterDependentProperties) {
                            if (property instanceof EntityReference reference) {
                                if (miqamo.equals(reference)) {
                                    return;
                                }
                            }
                        }
                        message += "; " + miqamo.getName() + " is not a master dependent property of " + master.getName();
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    } else {
                        message += "; the declaring entity of " + miqamo.getName() + " is " + decent;
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                } else {
                    message += "; the initial value of " + miqamo.getName() + " is " + iv;
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            } else {
                message += "; the master detail view of " + miqamo.getName() + " is " + mdv;
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkBinaryFields() {
        String name = getName();
        String msg1 = name + " has the following mutually exclusive annotations: ";
        Set<String> annotations = new TreeSet<>();
        Field field;
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (property.isBinaryData()) {
                field = property.getDeclaringField();
                if (field != null) {
                    annotations.clear();
                    if (field.isAnnotationPresent(GraphicImage.class)) {
                        annotations.add("GraphicImage");
                    }
                    if (field.isAnnotationPresent(ImageProperty.class)) {
                        annotations.add("ImageProperty");
                    }
                    if (annotations.size() > 1) {
                        logger.error(msg1 + StringUtils.join(annotations, ", "));
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private void checkNumericFields() {
        String name = getName();
        String msg1 = name + " has the following mutually exclusive annotations: ";
        String msg2 = name + " is a unique key property and therefore cannot be: ";
        Set<String> annotations = new TreeSet<>();
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (property.isNumericPrimitive()) {
                annotations.clear();
                if (property.isPrimaryKeyProperty()) {
                    annotations.add("PrimaryKey");
                }
                if (property.isSequenceProperty()) {
                    annotations.add("SequenceProperty");
                }
                if (annotations.size() > 1) {
                    logger.error(msg1 + StringUtils.join(annotations, ", "));
                    Project.increaseParserErrorCount();
                }
                if (property.isUniqueKeyProperty()) {
                    annotations.clear();
                    if (property.isDiscriminatorProperty()) {
                        annotations.add("DiscriminatorColumn");
                    }
                    if (property.isVersionProperty()) {
                        annotations.add("VersionProperty");
                    }
                    if (!annotations.isEmpty()) {
                        logger.error(msg2 + StringUtils.join(annotations, ", "));
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private void checkStringFields() {
        String name = getName();
        String msg1 = name + " has the following mutually exclusive annotations: ";
        String msg2 = name + " is a unique key property and therefore cannot be: ";
        Set<String> annotations = new TreeSet<>();
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (property.isStringData()) {
                annotations.clear();
                if (property.isDiscriminatorProperty()) {
                    annotations.add("DiscriminatorColumn");
                }
                if (property.isEmbeddedDocumentField()) {
                    annotations.add("EmbeddedDocument");
                }
                if (property.isFileReferenceField()) {
                    annotations.add("FileReference");
                }
                if (property.isUniformResourceLocatorField()) {
                    annotations.add("UniformResourceLocator");
                }
                if (property.isUrlProperty()) {
                    annotations.add("UrlProperty");
                }
                if (property.isVariantStringField()) {
                    annotations.add("VariantString");
                }
                if (annotations.size() > 1) {
                    logger.error(msg1 + StringUtils.join(annotations, ", "));
                    Project.increaseParserErrorCount();
                }
                if (property.isUniqueKeyProperty()) {
                    annotations.clear();
                    if (property.isDescriptionProperty()) {
                        annotations.add("DescriptionProperty");
                    }
                    if (property.isDiscriminatorProperty()) {
                        annotations.add("DiscriminatorColumn");
                    }
                    if (property.isNameProperty()) {
                        annotations.add("NameProperty");
                    }
                    if (property.isVariantStringField()) {
                        annotations.add("VariantString");
                    }
                    if (!annotations.isEmpty()) {
                        logger.error(msg2 + StringUtils.join(annotations, ", "));
                        Project.increaseParserErrorCount();
                    }
                }
                StringData data = (StringData) property;
                String fullName = data.getFullName();
                String searchURL = data.getSearchURL();
                if (StringUtils.isNotBlank(searchURL) && XS2.getURL(searchURL) == null) {
                    logger.error(fullName + " has the following invalid search URL: " + searchURL);
                    Project.increaseParserErrorCount();
                }
                String[] sourceURLs = data.getSourceURLs();
                if (sourceURLs != null && sourceURLs.length > 0) {
                    List<String> invalidSourceURLs = XS2.invalidURLs(sourceURLs);
                    for (String sourceURL : invalidSourceURLs) {
                        String cause = StringUtils.isBlank(searchURL) ? "has a null source URL" : "has the following invalid source URL: " + sourceURL;
                        logger.error(fullName + " " + cause);
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private void checkCollectionFields() {
        String collectionName;
        Property mappedByProperty;
        EntityReference reference;
        String referenceName;
        FetchType fetchType;
        Navigability navigability;
        for (EntityCollection collection : getEntityCollectionsList()) {
            collectionName = collection.getFullName();
            mappedByProperty = collection.getMappedByProperty();
            if (mappedByProperty instanceof EntityReference) {
                reference = (EntityReference) collection.getMappedByProperty();
                referenceName = reference.getFullName();
                fetchType = reference.getFetchType();
                navigability = reference.getNavigability();
                if (collection.isUpdateField()) {
                    if (!FetchType.UNSPECIFIED.equals(fetchType) && !FetchType.EAGER.equals(fetchType)) {
                        String message = "fetch type of " + referenceName + " must be EAGER because it maps updatable collection " + collectionName;
                        logger.error(message);
                        Project.increaseParserErrorCount();
                    }
                }
                if (!Navigability.UNSPECIFIED.equals(navigability) && !Navigability.BIDIRECTIONAL.equals(navigability)) {
                    String message = "navigability of " + referenceName + " must be BIDIRECTIONAL because it maps collection " + collectionName;
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private void checkCalculableFields() {
        final String msg1 = " is calculable and therefore does not support the following annotations: ";
        final String msg2 = " is calculable and has no calculable-value expression or reference ";
        final String msg3 = " is calculable and its calculable-value references calculable property ";
        final String msg4 = " is calculable but not assignable from ";
        Set<String> annotations = new TreeSet<>();
//      EntityReference reference;
        Object calculableValue;
        List<Artifact> pathList;
        boolean b1, b2;
        Class<?> class1, class2;
        String simple1, simple2;
        List<Property> properties = getPropertiesList();
        for (Property property : properties) {
            if (property.isCalculable()) {
                annotations.clear();
                if (property.isPrimaryKeyProperty()) {
                    annotations.add("PrimaryKey");
                }
                if (property.isSequenceProperty()) {
                    annotations.add("SequenceProperty");
                }
                if (property.isVersionProperty()) {
                    annotations.add("VersionProperty");
                }
                /* until 30/07/2021
                if (property.isNameProperty()) {
                    annotations.add("NameProperty");
                }
                if (property.isDescriptionProperty()) {
                    annotations.add("DescriptionProperty");
                }
                if (property.isImageProperty()) {
                    annotations.add("ImageProperty");
                }
                /**/
                if (property.isInactiveIndicatorProperty()) {
                    annotations.add("InactiveIndicator");
                }
                /* until 30/07/2021
                if (property.isUrlProperty()) {
                    annotations.add("UrlProperty");
                }
                /**/
                if (property.isParentProperty()) {
                    annotations.add("ParentProperty");
                }
                if (property.isOwnerProperty()) {
                    annotations.add("OwnerProperty");
                }
                if (property.isUserProperty()) { // 18/11/2020 - mapQueryProperty
                    annotations.add("UserProperty");
                }
                if (property.isSegmentProperty()) {
                    annotations.add("SegmentProperty");
                }
                if (property.isUniqueKeyProperty()) { // 18/11/2020
                    annotations.add("UniqueKey");
                }
                if (property.isBusinessKeyProperty()) {
                    annotations.add("BusinessKey");
                }
                if (property.isDiscriminatorProperty()) {
                    annotations.add("DiscriminatorColumn");
                }
                if (property.isStateProperty()) {
                    annotations.add("StateProperty");
                }
                /* until 30/07/2021
                if (property instanceof EntityReference) {
                    reference = (EntityReference) property;
                    if (reference.isManyToOne()) {
                        annotations.add("ManyToOne");
                    }
                    if (reference.isOneToOne()) {
                        annotations.add("OneToOne");
                    }
                }
                /**/
                if (property instanceof PersistentEntityReference persistentEntityReference) {
                    if (persistentEntityReference.isForeignKey()) {
                        annotations.add("ForeignKey");
                    }
                }
                if (!annotations.isEmpty()) { // 0 since 30/07/2021
                    logger.error(property.getFullName() + msg1 + StringUtils.join(annotations, ", "));
                    Project.increaseParserErrorCount();
                }
                if (property instanceof CalculableProperty calculableProperty) {
                    calculableValue = calculableProperty.getCalculableValue();
                    if (calculableValue == null) {
                        logger.error(property.getFullName() + msg2);
                        Project.increaseParserErrorCount();
                    } else if (calculableValue instanceof DataArtifact dataArtifact) {
                        pathList = dataArtifact.getPathList();
                        for (Artifact artifact : pathList) {
                            if (artifact instanceof Property && ((Property) artifact).isCalculable()) {
                                logger.error(property.getFullName() + msg3 + artifact.getFullName());
                                Project.increaseParserErrorCount();
                            }
                        }
                        b1 = property instanceof Entity;
                        b2 = dataArtifact instanceof Entity;
                        class1 = property.getDataClass();
                        class2 = dataArtifact.getDataClass();
                        simple1 = property.getDataClass().getSimpleName();
                        simple2 = dataArtifact.getDataClass().getSimpleName();
                        if (class1.isAssignableFrom(class2) || (b1 && b2 && simple1.equals(simple2))) {
                        } else {
                            logger.error(property.getFullName() + msg4 + dataArtifact.getFullName());
                            Project.increaseParserErrorCount();
                        }
                    } else if (calculableValue instanceof Expression expression) {
                        verifyExpression(expression, property, ExpressionUsage.CALCULABLE_FIELD_VALUE);
                    }
                }
            }
        }
        checkCalculableKeyFields();
        checkCalculableViewFields();
        checkCalculableOrderByProperties();
        checkCalculableForeignQueryProperties();
        checkCalculableQueryProperties();
    }

    private void checkCalculableKeyFields() {
        List<Key> keys = getKeysList();
        if (keys != null && !keys.isEmpty()) {
            String message;
            Property property;
            List<KeyField> fields;
            for (Key key : keys) {
                message = "failed to add a key field to " + key.getFullName();
                fields = key.getKeyFieldsList();
                if (fields != null && !fields.isEmpty()) {
                    for (KeyField field : fields) {
                        property = field.getProperty();
                        checkCalculablePropertyPath(property, message);
                    }
                }
            }
        }
    }

    private void checkCalculableViewFields() {
        List<View> views = getViewsList();
        if (views != null && !views.isEmpty()) {
            String message;
            Property property;
            List<ViewField> fields;
            for (View view : views) {
                message = "failed to add a view field to " + view.getFullName();
                fields = view.getViewFields();
                if (fields != null && !fields.isEmpty()) {
                    for (ViewField field : fields) {
                        property = field.getColumn();
                        checkCalculablePropertyPath(property, message);
                    }
                }
            }
        }
    }

    private void checkCalculableOrderByProperties() {
        String message = "failed to add a sort field to " + getFullName();
        Property orderByProperty = getOrderByProperty();
        if (orderByProperty != null) {
            checkCalculablePropertyPath(orderByProperty, message);
        } else {
            Property[] orderByProperties = getOrderByProperties();
            if (orderByProperties != null) {
                for (Property property : orderByProperties) {
                    checkCalculablePropertyPath(property, message);
                }
            }
        }
    }

    private void checkCalculableForeignQueryProperties() {
        String message = "failed to link a foreign query property to " + getFullName();
        Property property;
        for (String key : _foreignQueryProperties.keySet()) {
            property = _foreignQueryProperties.get(key);
            checkCalculablePropertyPath(property, message);
        }
    }

    private void checkCalculableQueryProperties() {
        String message = "failed to add a query property to " + getFullName();
        Property property;
        Object calculableValue;
        Map<String, Property> queryPropertiesMap = getQueryPropertiesMap();
        for (String key : queryPropertiesMap.keySet()) {
            property = queryPropertiesMap.get(key);
            if (property.isCalculable()) {
                if (property instanceof CalculableProperty calculableProperty) {
                    calculableValue = calculableProperty.getCalculableValue();
                    if (calculableValue == null) {
                        logger.error(message + "; " + property.getFullName() + " is a calculable property with no calculable value expression");
                        Project.increaseParserErrorCount();
                    }
                }
            }
        }
    }

    private boolean checkCalculablePropertyPath(Property property, String message) {
        if (property == null) {
            message += "; specified property is null";
            logger.error(message);
            Project.increaseParserErrorCount();
            return false;
        } else if (property.isCalculable()) {
            logger.error(message + "; " + property.getFullName() + " is a calculable property");
            Project.increaseParserErrorCount();
            return false;
        } else {
            List<Artifact> pathList = property.getPathList();
            for (Artifact artifact : pathList) {
                if (artifact instanceof Property && ((Property) artifact).isCalculable()) {
                    logger.error(message + "; " + artifact.getName() + " in " + property.getFullName() + " is a calculable property");
                    Project.increaseParserErrorCount();
                    return false;
                }
            }
        }
        return true;
    }

    private void checkInstances() {
        Property property;
        Object object;
        String string;
        for (Instance instance : getInstancesList()) {
            List<InstanceField> fields = instance.getInstanceFieldsList();
            if (fields.isEmpty()) {
                logger.error("instance " + instance.getFullName() + " has no fields");
                Project.increaseParserErrorCount();
            } else {
                for (InstanceField field : fields) {
                    property = field.getProperty();
                    if (property instanceof StringProperty stringProperty) {
                        object = field.getValue();
                        string = object instanceof String ? (String) object : null;
                        if (string == null) {
                            logger.error("null string value for field " + instance.getFullName() + "." + property.getName());
                            Project.increaseParserErrorCount();
                        } else if (string.length() < IntUtils.valueOf(stringProperty.getMinLength(), 0)) {
                            logger.error("string value too small for field " + instance.getFullName() + "." + property.getName());
                            Project.increaseParserErrorCount();
                        } else if (string.length() > IntUtils.valueOf(stringProperty.getMaxLength(), Integer.MAX_VALUE)) {
                            logger.error("string value too large for field " + instance.getFullName() + "." + property.getName());
                            Project.increaseParserErrorCount();
                        }
                    }
                }
            }
        }
    }

    private void checkKeys() {
        for (Key key : getKeysList()) {
            List<KeyField> fields = key.getKeyFieldsList();
            if (fields.isEmpty()) {
                logger.error("key " + key.getFullName() + " has no fields");
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkSteps() {
        List<Step> stepsList = getStepsList();
        if (stepsList.isEmpty()) {
            return;
        }
        boolean insertEnabled = isInsertEnabled();
        boolean updateEnabled = isUpdateEnabled();
        for (Step step : stepsList) {
            List<StepField> fields = step.getStepFieldsList();
            if (fields.isEmpty()) {
                logger.error("step " + step.getFullName() + " has no fields");
                Project.increaseParserErrorCount();
            } else if (insertEnabled || updateEnabled) {
                boolean ec = false;
                boolean cf = !insertEnabled;
                boolean uf = !updateEnabled;
                for (StepField field : fields) {
                    EntityCollection sfc = field.getEntityCollection();
                    if (sfc != null) {
                        ec = true;
                        break;
                    }
                    Property sfp = field.getProperty();
                    if (sfp != null) {
                        cf |= sfp.isCreateField();
                        uf |= sfp.isUpdateField();
                    }
                    if (cf && uf) {
                        break;
                    }
                }
                if (!ec) {
                    if (!cf && _stepCreatableFieldsWarningsEnabled) {
                        logger.warn("step " + step.getFullName() + " has no creatable fields");
                        Project.increaseParserWarningCount();
                    }
                    if (!uf && _stepCreatableFieldsWarningsEnabled) {
                        logger.warn("step " + step.getFullName() + " has no updatable fields");
                        Project.increaseParserWarningCount();
                    }
                }
            }
        }
        boolean warningsEnabled = _warningsEnabled && _propertiesWithoutStepWarningsEnabled;
        List<String> missing = new ArrayList<>();
        List<Property> properties = getPropertiesList();
        for (Property property : properties) {
            List<String> enclosingSteps = enclosingSteps(property, stepsList);
            int size = enclosingSteps.size();
            if (size > 1) {
                logger.error(property.getFullName() + " is included in more than one step: " + StringUtils.join(enclosingSteps, ", "));
                Project.increaseParserErrorCount();
            } else if (size < 1) {
                if (warningsEnabled) {
                    if (property.isCreateField() || property.isUpdateField()) {
                        missing.add(property.getName());
                    }
                }
            }
        }
        if (warningsEnabled) {
            int size = missing.size();
            if (size > 1) {
                logger.warn(getFullName() + " properties not included in any step: " + StringUtils.join(missing, ", "));
                Project.increaseParserWarningCount();
            } else if (size > 0) {
                logger.warn(missing.get(0) + " is not included in any step of " + getFullName());
                Project.increaseParserWarningCount();
            }
        }
    }

    private List<String> enclosingSteps(Property property, List<Step> stepsList) {
        List<String> list = new ArrayList<>();
        for (Step step : stepsList) {
            for (StepField stepField : step.getStepFieldsList()) {
                Property sfp = stepField.getProperty();
                if (sfp != null) {
                    if (sfp.equals(property)) {
                        list.add(step.getName());
                        break;
                    }
                }
            }
        }
        return list;
    }

    private void checkTabs() {
        for (Tab tab : getTabsList()) {
            List<TabField> fields = tab.getTabFieldsList();
            if (fields.isEmpty()) {
                logger.error("tab " + tab.getFullName() + " has no fields");
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkViews() {
        for (View view : getViewsList()) {
            List<ViewField> fields = view.getViewFields();
            if (fields.isEmpty()) {
                logger.error("view " + view.getFullName() + " has no fields");
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkTriggers() {
        String tag;
        String message;
        State state;
        ProcessOperation operation;
        List<State> initialStatesList;
        List<Trigger> triggers = getTriggersList();
        for (Trigger trigger : triggers) {
            tag = " trigger " + trigger.getName() + " at entity " + getName();
            state = trigger.getState();
            operation = trigger.getOperation();
            if (state == null) {
                message = "invalid" + tag;
                message += "; trigger state is null";
                logger.warn(message);
                Project.increaseParserWarningCount();
            } else if (operation == null) {
                message = "invalid" + tag;
                message += "; trigger operation is null";
                logger.warn(message);
                Project.increaseParserWarningCount();
            } else {
                initialStatesList = operation.getInitialStatesList();
                if (initialStatesList == null || !initialStatesList.contains(state)) {
                    message = "suspicious" + tag;
                    message += "; " + state.getName() + " is not an initial state of " + operation.getName();
                    logger.warn(message);
                    Project.increaseParserWarningCount();
                }
            }
        }
    }

    private void checkTreeView() {
        if (_treeViewEnabled) {
            if (_parentProperty == null) {
                String message = getName() + " tree view will not be enabled because this entity does not have a parent property";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
            /* until 13/07/2022
            if (_ownerProperty != null) {
                String message = getName() + " tree view will not be enabled because this entity has an owner property";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
            if (_segmentProperty != null) {
                String message = getName() + " tree view will not be enabled because this entity has a segment property";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
            /**/
        }
        /* until 06/07/2022
        if (isTreeViewEnabled()) {
            if (_selectRowsLimit != 0) {
                String message = getName() + " tree view will ignore the select rows limit set for this entity";
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
        }
        /**/
    }

    private void checkVisibleFields() {
        String msg1;
        String name = getName();
        int visibleTableField = 0;
        int visibleReportField = 0;
        int visibleRequired = 0;
        List<Property> list = getPropertiesList();
        for (Property property : list) {
            if (property.isHiddenField()) {
                continue;
            }
            if (property.isTableField()) {
                visibleTableField++;
            }
            if (property.isReportField()) {
                visibleReportField++;
            }
            if (property.isRequiredProperty()) {
                visibleRequired++;
            }
        }
        if (visibleTableField == 0 && isTableViewEnabled()) {
            msg1 = name + " does not have any visible table fields";
            logger.warn(msg1);
            Project.increaseParserWarningCount();
        }
        if (visibleReportField == 0 && isReportEnabled()) {
            msg1 = name + " does not have any visible report fields";
            logger.warn(msg1);
            Project.increaseParserWarningCount();
        }
        if (visibleRequired == 0 && isInsertEnabled()) {
            msg1 = name + " does not have any visible required properties";
            logger.warn(msg1);
            Project.increaseParserWarningCount();
        }
    }

    private void checkImageProperty() {
        if (_imageProperty != null) {
            if (PropertyAccess.RESTRICTED_READING.equals(_imageProperty.getPropertyAccess())) {
                String message = _imageProperty.getFullName() + " cannot be the image property of the entity due to its restricted read access";
                logger.error(message);
                Project.increaseParserErrorCount();
            }
        }
    }

    private void checkBusinessKeyProperty() {
        String warn = getName() + " does not have a business key property and is referenced by ";
        String referenceFullName = null;
        EntityReference entityReference;
        List<Property> references = _atlas.getReferencesList();
        int size = references.size();
        if (size > 0) {
            size = 0;
            Entity declaringEntity;
            List<Property> declaringEntityProperties;
            for (Property reference : references) {
                if (reference.isHiddenField() || reference.isInherited()) {
                    continue;
                }
                if (!(reference.isCreateField() || reference.isUpdateField())) {
                    continue; // since 22/01/2025
                }
                entityReference = (EntityReference) reference;
                EntityReferenceStyle referenceStyle = entityReference.getReferenceStyle();
                SearchType searchType = entityReference.getSearchType();
                ListStyle listStyle = entityReference.getListStyle();
                if (EntityReferenceStyle.NAME.equals(referenceStyle)
                    && (SearchType.LIST.equals(searchType) || SearchType.RADIO.equals(searchType))
                    && (ListStyle.NAME.equals(listStyle) || ListStyle.PRIMARY_KEY_AND_NAME.equals(listStyle))) {
                    continue;
                }
                declaringEntity = reference.getDeclaringEntity();
                if (declaringEntity.isExistentiallyIndependent()) {
                    size++;
                    referenceFullName = reference.getFullName();
                    continue;
                } else {
                    entityReference = (EntityReference) reference;
                    if (MasterDetailView.NONE.equals(entityReference.getMasterDetailView())) {
                        size++;
                        referenceFullName = reference.getFullName();
                        continue;
                    }
                }
                declaringEntityProperties = declaringEntity.getPropertiesList();
                for (Property property : declaringEntityProperties) {
                    if (property.equals(reference)) {
                        continue;
                    }
                    if (property instanceof EntityReference er) {
                        if (MasterDetailView.NONE.equals(er.getMasterDetailView())) {
                            continue;
                        }
                        size++;
                        referenceFullName = reference.getFullName();
                        break;
                    }
                }
            }
            if (size > 0) {
                String by = size == 1 ? "property " + referenceFullName : size + " non-exclusively-existentially-dependent creatable-and/or-updatable properties";
                logger.warn(warn + by);
                Project.increaseParserWarningCount();
            }
        }
        List<Parameter> parameterReferencesList = _atlas.getParameterReferencesList();
        size = parameterReferencesList.size();
        if (size > 0) {
            size = 0;
            for (Parameter reference : parameterReferencesList) {
                if (reference.isInstanceReferenceField()) {
                    entityReference = (EntityReference) reference;
                    if (!entityReference.isConsoleViewEnabled()) {
                        continue;
                    }
                }
                size++;
                referenceFullName = reference.getFullName();
            }
            if (size > 0) {
                String by = size == 1 ? "parameter " + referenceFullName : size + " parameters";
                logger.warn(warn + by);
                Project.increaseParserWarningCount();
            }
        }
    }

    private void checkBusinessKeyPropertyDefaultValue() {
        if (_insertEnabled) {
            if (_businessKeyProperty != null && !_businessKeyProperty.isCreateField() && _businessKeyProperty.getDefaultValue() == null) {
                String bkfn = _businessKeyProperty.getFullName();
                String pkfn = _primaryKeyProperty == null ? null : _primaryKeyProperty.getFullName();
                String message;
                if (_primaryKeyProperty == null) {
                    message = "property " + bkfn + " seems to need a default value";
                } else if (_businessKeyProperty.isStringData()) {
                    StringData bksd = (StringData) _businessKeyProperty;
                    bksd.setDefaultValue(XB.toCharString(_primaryKeyProperty));
                    if (_businessKeyProperty.getDefaultValueTag() == null) {
                        _businessKeyProperty.setDefaultValueTag("ID");
                    }
                    message = "property " + bkfn + " seemed to need a default value so it was set to \"" + pkfn + "\"";
                } else if (_businessKeyProperty.isIntegerData() && _primaryKeyProperty.isIntegerData() && _businessKeyProperty != _primaryKeyProperty) {
                    IntegerData bkid = (IntegerData) _businessKeyProperty;
                    IntegerData pkid = (IntegerData) _primaryKeyProperty;
                    bkid.setDefaultValue(pkid);
                    if (_businessKeyProperty.getDefaultValueTag() == null) {
                        _businessKeyProperty.setDefaultValueTag("ID");
                    }
                    message = "property " + bkfn + " seemed to need a default value so it was set to \"" + pkfn + "\"";
                } else {
                    message = "property " + bkfn + " seems to need a default value";
                }
                logger.warn(message);
                Project.increaseParserWarningCount();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    protected void initializeAnnotations() {
        super.initializeAnnotations();
        /**/
        _serializableField = coalesce(_serializableField, Project.getDefaultPropertyFieldSerializable());
        _serializableIUID = coalesce(_serializableIUID, Project.getDefaultPropertyFieldSerializableIUID());
        /**/
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateAbstractClass(type);
            annotateAllocationOverride(type);
            annotateAllocationOverrides(type);
            annotateEntityClass(type);
            annotateEntityDataGen(type);
            annotateEntitySelectOperation(type);
            annotateEntityInsertOperation(type);
            annotateEntityUpdateOperation(type);
            annotateEntityDeleteOperation(type);
            annotateEntityReportOperation(type);
            annotateEntityExportOperation(type);
            annotateEntityTableView(type);
            annotateEntityDetailView(type);
            annotateEntityTreeView(type);
            annotateEntityConsoleView(type);
            annotateEntityMasterView(type);
            annotateEntityWarnings(type);
            annotateEntityCodeGen(type);
            annotateEntityDocGen(type);
            annotateEntityJsonCustomization(type);
            annotateEntityReferenceConversionValidation(type);
            annotateEntityReferenceDisplay(type);
            annotateEntityReferenceSearch(type);
            annotateEntityViewLocation(type);
        }
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            if (isEntityReference()) {
                annotateEntityReferenceConversionValidation(field);
                annotateEntityReferenceDataGen(field);
                annotateEntityReferenceDisplay(field);
                annotateEntityReferenceSearch(field);
                annotateFilter(field);
                annotateOneToOne(field);
                if (!isOneToOne()) {
                    annotateManyToOne(field);
                }
                annotateQueryMapping(field);
            }
        }
    }

    @Override
//  @SuppressWarnings("deprecation")
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(AbstractClass.class);
        valid.add(AllocationOverride.class);
        valid.add(AllocationOverrides.class);
        valid.add(EntityClass.class);
        valid.add(EntityCodeGen.class);
        valid.add(EntityConsoleView.class);
        valid.add(EntityDeleteOperation.class);
        valid.add(EntityDetailView.class);
        valid.add(EntityDocGen.class);
        valid.add(EntityExportOperation.class);
        valid.add(EntityInsertOperation.class);
        valid.add(EntityJsonCustomization.class);
        valid.add(EntityMasterView.class);
        valid.add(EntityReferenceConversionValidation.class);
        valid.add(EntityReferenceDisplay.class);
        valid.add(EntityReferenceSearch.class);
        valid.add(EntityReportOperation.class);
        valid.add(EntitySelectOperation.class);
        valid.add(EntityTableView.class);
        valid.add(EntityTreeView.class);
        valid.add(EntityUpdateOperation.class);
        valid.add(EntityViewLocation.class);
        valid.add(EntityWarnings.class);
        if (isEnumerationEntity()) {
        } else {
            valid.add(EntityDataGen.class);
        }
        return valid;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(Allocation.class);
        if (isParameter()) {
            valid.add(EntityReferenceConversionValidation.class);
            valid.add(EntityReferenceDisplay.class);
            valid.add(EntityReferenceSearch.class);
//          valid.add(Filter.class);
            valid.add(InstanceReference.class);
            valid.add(ParameterField.class);
        }
        if (isProperty()) {
            valid.add(BaseField.class);
            valid.add(CastingField.class);
            valid.add(ColumnField.class);
            valid.add(EntityReferenceConversionValidation.class);
            valid.add(EntityReferenceDataGen.class);
            valid.add(EntityReferenceDisplay.class);
            valid.add(EntityReferenceSearch.class);
//          valid.add(Filter.class);
            valid.add(ManyToOne.class);
            valid.add(OneToOne.class);
            valid.add(OwnerProperty.class);
            valid.add(UserProperty.class);
            valid.add(ParentProperty.class);
            valid.add(PropertyField.class);
            valid.add(PropertyAggregation.class);
            valid.add(QueryMapping.class);
            valid.add(SegmentProperty.class);
            valid.add(UniqueKey.class);
        }
        return valid;
    }

    private void annotateAbstractClass(Class<?> type) {
        /*
         * AbstractClass annotation cannot be "inherited"
         * unless the annotated class has the same simple name as the type parameter (since 09/02/2023)
         */
        //
        /* until 09/02/2023
        boolean annotationPresent = type.isAnnotationPresent(AbstractClass.class);
        if (annotationPresent) {
            AbstractClass annotation = type.getAnnotation(AbstractClass.class);
            _annotatedWithAbstractClass = annotation.value();
        }
        /**/
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, AbstractClass.class);
        if (annotatedClass != null && annotatedClass.getSimpleName().equals(type.getSimpleName())) {
            AbstractClass annotation = annotatedClass.getAnnotation(AbstractClass.class);
            _annotatedWithAbstractClass = annotation != null && annotation.value();
        }
    }

    private void annotateAllocationOverride(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, AllocationOverride.class);
        if (annotatedClass != null) {
            AllocationOverride annotation = annotatedClass.getAnnotation(AllocationOverride.class);
            if (annotation != null) {
                _annotatedWithAllocationOverride = putAllocationOverride(annotation, type);
            }
        }
    }

    private void annotateAllocationOverrides(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, AllocationOverrides.class);
        if (annotatedClass != null) {
            AllocationOverrides annotation = annotatedClass.getAnnotation(AllocationOverrides.class);
            if (annotation != null) {
                for (AllocationOverride value : annotation.value()) {
                    _annotatedWithAllocationOverrides |= putAllocationOverride(value, type);
                }
            }
        }
    }

    private boolean putAllocationOverride(AllocationOverride annotation, Class<?> type) {
        if (annotation != null) {
            String name = annotation.field();
            if (StringUtils.isNotBlank(name)) {
                Field field = XS1.getEntityField(name, type);
                if (field != null) {
                    _allocationOverrides.put(name, annotation);
                    return true;
                }
            }
        }
        return false;
    }

    private void setBusinessKey() {
        if (_businessKeyFieldName == null || _businessKeyField == null) {
            switch (_businessKeyType) {
                case CHARACTER_KEY_PROPERTY -> {
                    if (_characterKeyField != null) {
                        _businessKeyFieldName = _characterKeyFieldName;
                        _businessKeyField = _characterKeyField;
                    }
                }
                case NUMERIC_KEY_PROPERTY -> {
                    if (_numericKeyField != null) {
                        _businessKeyFieldName = _numericKeyFieldName;
                        _businessKeyField = _numericKeyField;
                    }
                }
                case UNSPECIFIED -> {
                    if (_characterKeyField != null) {
                        _businessKeyFieldName = _characterKeyFieldName;
                        _businessKeyField = _characterKeyField;
                    } else if (_numericKeyField != null) {
                        _businessKeyFieldName = _numericKeyFieldName;
                        _businessKeyField = _numericKeyField;
                    }
                }
            }
        }
    }

    private void annotateEntityClass(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityClass.class);
        if (annotatedClass != null) {
            EntityClass annotation = annotatedClass.getAnnotation(EntityClass.class);
            if (annotation != null) {
                _basicOperationEntity = annotation.base().toBoolean(_basicOperationEntity);
                _catalogEntity = annotation.catalog().toBoolean(_catalogEntity);
                _existentiallyIndependent = annotation.independent().toBoolean(_existentiallyIndependent);
                _entityViewType = annotation.viewType();
                _variantEntity = annotation.variant().toBoolean(_variantEntity);
                _resourceType = annotation.resourceType();
                _resourceGender = annotation.resourceGender();
//              _propertiesPrefix = annotation.propertiesPrefix();
//              _propertiesSuffix = annotation.propertiesSuffix();
                _collectionName = specified(annotation.collectionName(), _collectionName);
                _startWith = Math.max(0, annotation.startWith());
                _annotatedWithEntityClass = true;
                String document = annotation.helpDocument();
                if (StringUtils.isNotBlank(document)) {
                    setHelpDocument(document);
                }
                String fileName = annotation.helpFile();
                if (StringUtils.isNotBlank(fileName)) {
                    setHelpFileName(fileName);
                }
                _helpFileAutoName = specified(annotation.helpFileAutoName(), _helpFileAutoName);
                _helpFileAutoType = specified(annotation.helpFileAutoType(), _helpFileAutoType);
            }
        }
        checkHelpFileAutoName();
        checkHelpFileAutoType();
    }

    private void annotateEntityDataGen(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDataGen.class);
        if (annotatedClass != null) {
            EntityDataGen annotation = annotatedClass.getAnnotation(EntityDataGen.class);
            if (annotation != null) {
                _annotatedWithEntityDataGen = true;
                _seriesStart = Math.min(Constants.MAX_ENTITY_SERIES_START, Math.max(1, annotation.start()));
                _seriesStop = Math.min(Constants.MAX_ENTITY_SERIES_STOP, Math.max(1, annotation.stop()));
                _seriesStep = Math.min(Constants.MAX_ENTITY_SERIES_STEP, Math.max(1, annotation.step()));
                if (!isDataGenEnabled()) {
                    String message = getName() + " has invalid values for elements start/stop/step of its EntityDataGen annotation: "
                        + _seriesStart + "/" + _seriesStop + "/" + _seriesStep;
                    logger.error(message);
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private void annotateEntitySelectOperation(Class<?> type) {
        final int MAX = Constants.MAXIMUM_SELECT_ROWS_LIMIT;
        final int MIN = 0;
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntitySelectOperation.class);
        if (annotatedClass != null) {
            EntitySelectOperation annotation = annotatedClass.getAnnotation(EntitySelectOperation.class);
            if (annotation != null) {
                _selectEnabled = annotation.enabled().toBoolean(_selectEnabled);
                _selectOperationAccess = annotation.access();
                _selectOnloadOption = annotation.onload();
                _selectRowsLimit = Math.min(MAX, Math.max(MIN, annotation.rowsLimit()));
                _selectSortOption = annotation.sortOption();
                _annotatedWithEntitySelectOperation = true;
            }
        }
    }

    private void annotateEntityInsertOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityInsertOperation.class);
        if (annotatedClass != null) {
            EntityInsertOperation annotation = annotatedClass.getAnnotation(EntityInsertOperation.class);
            if (annotation != null) {
                _insertEnabled = annotation.enabled().toBoolean(_insertEnabled);
                _insertConfirmationRequired = annotation.confirmation().toBoolean(_insertConfirmationRequired);
                _insertOperationAccess = annotation.access();
                _insertLogging = annotation.logging();
                _annotatedWithEntityInsertOperation = true;
            }
        }
    }

    private void annotateEntityUpdateOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityUpdateOperation.class);
        if (annotatedClass != null) {
            EntityUpdateOperation annotation = annotatedClass.getAnnotation(EntityUpdateOperation.class);
            if (annotation != null) {
                _updateEnabled = annotation.enabled().toBoolean(_updateEnabled);
                _updateConfirmationRequired = annotation.confirmation().toBoolean(_updateConfirmationRequired);
                _updateOperationAccess = annotation.access();
                _updateLogging = annotation.logging();
                _annotatedWithEntityUpdateOperation = true;
            }
        }
    }

    private void annotateEntityDeleteOperation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDeleteOperation.class);
        if (annotatedClass != null) {
            EntityDeleteOperation annotation = annotatedClass.getAnnotation(EntityDeleteOperation.class);
            if (annotation != null) {
                _deleteEnabled = annotation.enabled().toBoolean(_deleteEnabled);
                _deleteConfirmationRequired = annotation.confirmation().toBoolean(_deleteConfirmationRequired);
                _deleteOperationAccess = annotation.access();
                _deleteLogging = annotation.logging();
                _annotatedWithEntityDeleteOperation = true;
            }
        }
    }

    private void annotateEntityReportOperation(Class<?> type) {
        final int MAX = Constants.MAXIMUM_REPORT_ROWS_LIMIT;
        final int MIN = 0;
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityReportOperation.class);
        if (annotatedClass != null) {
            EntityReportOperation annotation = annotatedClass.getAnnotation(EntityReportOperation.class);
            if (annotation != null) {
                _reportEnabled = annotation.enabled().toBoolean(_reportEnabled);
                _reportRowsLimit = Math.min(MAX, Math.max(MIN, annotation.rowsLimit()));
                _reportSortOption = annotation.sortOption();
                _annotatedWithEntityReportOperation = true;
            }
        }
    }

    private void annotateEntityExportOperation(Class<?> type) {
        final int MAX = Constants.MAXIMUM_EXPORT_ROWS_LIMIT;
        final int MIN = 0;
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityExportOperation.class);
        if (annotatedClass != null) {
            EntityExportOperation annotation = annotatedClass.getAnnotation(EntityExportOperation.class);
            if (annotation != null) {
                _exportEnabled = annotation.enabled().toBoolean(_exportEnabled);
                _exportRowsLimit = Math.min(MAX, Math.max(MIN, annotation.rowsLimit()));
                _exportSortOption = annotation.sortOption();
                _annotatedWithEntityExportOperation = true;
            }
        }
    }

    private void annotateEntityTableView(Class<?> type) {
        final int MAX = Constants.MAXIMUM_ROWS_PER_PAGE_LIMIT;
        final int MIN = Constants.MINIMUM_ROWS_PER_PAGE_LIMIT;
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityTableView.class);
        if (annotatedClass != null) {
            EntityTableView annotation = annotatedClass.getAnnotation(EntityTableView.class);
            if (annotation != null) {
                _tableViewEnabled = annotation.enabled().toBoolean(_tableViewEnabled);
                _tableViewWithInsertEnabled = annotation.inserts().toBoolean(_tableViewWithInsertEnabled);
                _tableViewWithUpdateEnabled = annotation.updates().toBoolean(_tableViewWithUpdateEnabled);
                _tableViewWithDeleteEnabled = annotation.deletes().toBoolean(_tableViewWithDeleteEnabled);
                _tableViewWithMasterHeading = annotation.heading().toBoolean(_tableViewWithMasterHeading);
                _tableViewWithQuickFilterSnippet = annotation.quickFilter().toBoolean(_tableViewWithQuickFilterSnippet);
                _tableViewWithStickyHeader = annotation.stickyHeader().toBoolean(_tableViewWithStickyHeader);
                _tableViewRowsLimit = Math.min(MAX, Math.max(MIN, annotation.rowsLimit()));
                _tableViewRowsLimit = (_tableViewRowsLimit + 9) / 10 * 10;
                _tableViewRows = Math.min(_tableViewRowsLimit, Math.max(1, annotation.rows()));
                _tableViewRows = (_tableViewRows + 4) / 5 * 5;
                _tableResponsiveMode = specified(annotation.responsiveMode(), _tableResponsiveMode);
                _tableViewMenuOption = annotation.menu();
                _annotatedWithEntityTableView = true;
                /**/
                String document = annotation.helpDocument();
                if (StringUtils.isNotBlank(document)) {
                    setTableViewHelpDocument(document);
                }
                /**/
                String fileName = annotation.helpFile();
                if (StringUtils.isNotBlank(fileName)) {
                    setTableViewHelpFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewHeadSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewHeadSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewEasternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewEasternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewWesternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewWesternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewAboveTableSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewAboveTableSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewBelowTableSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewBelowTableSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewRowActionSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewRowActionSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewRowStatusSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewRowStatusSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewRowNumberSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTableViewRowNumberSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewHeadSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewHeadSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewEasternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewEasternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewWesternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewWesternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewAboveTableSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewAboveTableSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewBelowTableSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewBelowTableSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewRowActionSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewRowActionSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewRowStatusSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewRowStatusSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewRowNumberSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTableViewRowNumberSnippetFileName(fileName);
                }
                /**/
            }
        }
    }

    private void annotateEntityDetailView(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDetailView.class);
        if (annotatedClass != null) {
            EntityDetailView annotation = annotatedClass.getAnnotation(EntityDetailView.class);
            if (annotation != null) {
                _detailViewEnabled = annotation.enabled().toBoolean(_detailViewEnabled);
                _detailViewWithMasterHeading = annotation.heading().toBoolean(_detailViewWithMasterHeading);
                _detailViewMenuOption = annotation.menu();
                _detailViewMenuOptionOpenAction = Kleenean.TRUE.equals(annotation.inserting()) ? ViewMenuOptionOpenAction.INSERT : ViewMenuOptionOpenAction.DEFAULT;
                _annotatedWithEntityDetailView = true;
                /**/
                String document = annotation.helpDocument();
                if (StringUtils.isNotBlank(document)) {
                    setDetailViewHelpDocument(document);
                }
                /**/
                String fileName = annotation.helpFile();
                if (StringUtils.isNotBlank(fileName)) {
                    setDetailViewHelpFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewHeadSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingDetailViewHeadSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewEasternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingDetailViewEasternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewWesternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingDetailViewWesternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewAboveDetailSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingDetailViewAboveDetailSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewBelowDetailSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingDetailViewBelowDetailSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewHeadSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingDetailViewHeadSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewEasternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingDetailViewEasternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewWesternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingDetailViewWesternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewAboveDetailSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingDetailViewAboveDetailSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewBelowDetailSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingDetailViewBelowDetailSnippetFileName(fileName);
                }
                /**/
            }
        }
    }

    private void annotateEntityTreeView(Class<?> type) {
        final int MAX = Constants.MAXIMUM_ROWS_PER_PAGE_LIMIT;
        final int MIN = Constants.MINIMUM_ROWS_PER_PAGE_LIMIT;
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityTreeView.class);
        if (annotatedClass != null) {
            EntityTreeView annotation = annotatedClass.getAnnotation(EntityTreeView.class);
            if (annotation != null) {
                _treeViewEnabled = annotation.enabled().toBoolean(_treeViewEnabled);
                _treeViewMenuOption = annotation.menu();
                _treeViewRootLimit = Math.min(MAX, Math.max(MIN, annotation.rootLimit()));
                _treeViewBranchLimit = Math.min(MAX, Math.max(MIN, annotation.branchLimit()));
                _annotatedWithEntityTreeView = true;
                /**/
                String document = annotation.helpDocument();
                if (StringUtils.isNotBlank(document)) {
                    setTreeViewHelpDocument(document);
                }
                /**/
                String fileName = annotation.helpFile();
                if (StringUtils.isNotBlank(fileName)) {
                    setTreeViewHelpFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewHeadSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTreeViewHeadSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewEasternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTreeViewEasternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewWesternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTreeViewWesternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewAboveTreeSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTreeViewAboveTreeSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.readingViewBelowTreeSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setReadingTreeViewBelowTreeSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewHeadSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTreeViewHeadSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewEasternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTreeViewEasternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewWesternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTreeViewWesternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewAboveTreeSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTreeViewAboveTreeSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.writingViewBelowTreeSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setWritingTreeViewBelowTreeSnippetFileName(fileName);
                }
                /**/
            }
        }
    }

    private void annotateEntityConsoleView(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityConsoleView.class);
        if (annotatedClass != null) {
            EntityConsoleView annotation = annotatedClass.getAnnotation(EntityConsoleView.class);
            if (annotation != null) {
                _consoleViewEnabled = annotation.enabled().toBoolean(_consoleViewEnabled);
                _consoleViewMenuOption = annotation.menu().toBoolean(_consoleViewMenuOption);
                _annotatedWithEntityConsoleView = true;
                /**/
                String document = annotation.helpDocument();
                if (StringUtils.isNotBlank(document)) {
                    setConsoleViewHelpDocument(document);
                }
                /**/
                String fileName = annotation.helpFile();
                if (StringUtils.isNotBlank(fileName)) {
                    setConsoleViewHelpFileName(fileName);
                }
                /**/
                fileName = annotation.headSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setConsoleViewHeadSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.easternToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setConsoleViewEasternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.westernToolbarSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setConsoleViewWesternToolbarSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.aboveProcessingPanelSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setConsoleViewAboveProcessingPanelSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.belowProcessingPanelSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setConsoleViewBelowProcessingPanelSnippetFileName(fileName);
                }
                /**/
            }
        }
    }

    private void annotateEntityMasterView(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityMasterView.class);
        if (annotatedClass != null) {
            EntityMasterView annotation = annotatedClass.getAnnotation(EntityMasterView.class);
            if (annotation != null) {
                _annotatedWithEntityMasterView = true;
                /**/
                String fileName;
                /**/
                fileName = annotation.aboveHeadingSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setAboveMasterHeadingSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.belowHeadingSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setBelowMasterHeadingSnippetFileName(fileName);
                }
                /**/
                fileName = annotation.insideHeadingSnippet();
                if (StringUtils.isNotBlank(fileName)) {
                    setInsideMasterHeadingSnippetFileName(fileName);
                }
                /**/
            }
        }
    }

    private void annotateEntityWarnings(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityWarnings.class);
        if (annotatedClass != null) {
            EntityWarnings annotation = annotatedClass.getAnnotation(EntityWarnings.class);
            if (annotation != null) {
                if (Project.isWarnose()) {
                    if (depth() == 0) {
                        logger.warn(getFullName() + " @EntityWarnings annotation ignored ");
                        Project.increaseParserWarningCount();
                    }
                } else {
                    _warningsEnabled = annotation.enabled().toBoolean(_warningsEnabled);
                    if (_warningsEnabled) {
                        _businessKeyWarningsEnabled = annotation.aboutBusinessKey().toBoolean(_businessKeyWarningsEnabled);
                        _discriminatorPropertyWarningsEnabled = annotation.aboutDiscriminatorProperty().toBoolean(_discriminatorPropertyWarningsEnabled);
                        _propertiesWithoutStepWarningsEnabled = annotation.aboutPropertiesWithoutStep().toBoolean(_propertiesWithoutStepWarningsEnabled);
                        _treeViewWarningsEnabled = annotation.aboutTreeView().toBoolean(_treeViewWarningsEnabled);
                        _triggersWarningsEnabled = annotation.aboutTriggers().toBoolean(_triggersWarningsEnabled);
                        _versionPropertyWarningsEnabled = annotation.aboutVersionProperty().toBoolean(_versionPropertyWarningsEnabled);
                        _visibleFieldsWarningsEnabled = annotation.aboutVisibleFields().toBoolean(_visibleFieldsWarningsEnabled);
                        _stepCreatableFieldsWarningsEnabled = annotation.aboutStepCreatableFields().toBoolean(_stepCreatableFieldsWarningsEnabled);
                        _stepUpdatableFieldsWarningsEnabled = annotation.aboutStepUpdatableFields().toBoolean(_stepUpdatableFieldsWarningsEnabled);
                        _specialExpressionsWarningsEnabled = annotation.aboutSpecialExpressions().toBoolean(_specialExpressionsWarningsEnabled);
                        _unusualExpressionsWarningsEnabled = annotation.aboutUnusualExpressions().toBoolean(_unusualExpressionsWarningsEnabled);
                    } else {
                        _businessKeyWarningsEnabled = false;
                        _discriminatorPropertyWarningsEnabled = false;
                        _propertiesWithoutStepWarningsEnabled = false;
                        _treeViewWarningsEnabled = false;
                        _triggersWarningsEnabled = false;
                        _versionPropertyWarningsEnabled = false;
                        _visibleFieldsWarningsEnabled = false;
                        _stepCreatableFieldsWarningsEnabled = false;
                        _stepUpdatableFieldsWarningsEnabled = false;
                        _specialExpressionsWarningsEnabled = false;
                        _unusualExpressionsWarningsEnabled = false;
                    }
                    _annotatedWithEntityWarnings = true;
                }
            }
        }
    }

    private void annotateEntityCodeGen(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityCodeGen.class);
        if (annotatedClass != null) {
            EntityCodeGen annotation = annotatedClass.getAnnotation(EntityCodeGen.class);
            if (annotation != null) {
                _bplCodeGenEnabled = annotation.bpl().toBoolean(_bplCodeGenEnabled);
                _bwsCodeGenEnabled = annotation.bws().toBoolean(_bwsCodeGenEnabled);
                _daoCodeGenEnabled = annotation.dao().toBoolean(_daoCodeGenEnabled);
                _dafCodeGenEnabled = annotation.daf().toBoolean(_dafCodeGenEnabled);
                _fwsCodeGenEnabled = annotation.fws().toBoolean(_fwsCodeGenEnabled);
                _guiCodeGenEnabled = annotation.gui().toBoolean(_guiCodeGenEnabled);
                _sqlCodeGenEnabled = annotation.sql().toBoolean(_sqlCodeGenEnabled);
                _smcCodeGenEnabled = annotation.state().toBoolean(_smcCodeGenEnabled);
                _annotatedWithEntityCodeGen = true;
            }
        }
    }

    private void annotateEntityDocGen(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityDocGen.class);
        if (annotatedClass != null) {
            EntityDocGen annotation = annotatedClass.getAnnotation(EntityDocGen.class);
            if (annotation != null) {
                _entityClassDiagramGenEnabled = annotation.classDiagram().toBoolean(_entityClassDiagramGenEnabled);
                _entityStateDiagramGenEnabled = annotation.stateDiagram().toBoolean(_entityStateDiagramGenEnabled);
                _entityInsertActivityDiagramGenEnabled = annotation.insertActivityDiagram().toBoolean(_entityInsertActivityDiagramGenEnabled);
                _entityUpdateActivityDiagramGenEnabled = annotation.updateActivityDiagram().toBoolean(_entityUpdateActivityDiagramGenEnabled);
                _annotatedWithEntityDocGen = true;
            }
        }
    }

    private void annotateEntityJsonCustomization(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityJsonCustomization.class);
        if (annotatedClass == type) { // esta anotación no es heredable
            EntityJsonCustomization annotation = annotatedClass.getAnnotation(EntityJsonCustomization.class);
            if (annotation != null) {
                _annotatedWithEntityJsonCustomization = true;
                /**/
                String serializer = annotation.serializer();
                if (StringUtils.isNotBlank(serializer)) {
                    setJsonSerializerClassName(serializer);
                }
                /**/
                String deserializer = annotation.deserializer();
                if (StringUtils.isNotBlank(deserializer)) {
                    setJsonDeserializerClassName(deserializer);
                }
                /**/
            }
        }
    }

    private void annotateEntityReferenceConversionValidation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityReferenceConversionValidation.class);
        if (annotatedClass != null) {
            EntityReferenceConversionValidation annotation = annotatedClass.getAnnotation(EntityReferenceConversionValidation.class);
            if (annotation != null) {
                _customConverterEntityReference = annotation.customConverter().toBoolean(_customConverterEntityReference);
                _restrictedAccessEntityReference = annotation.restrictedAccess().toBoolean(_restrictedAccessEntityReference);
                _annotatedWithEntityReferenceConversionValidation = true;
            }
        }
    }

    private void annotateEntityReferenceConversionValidation(Field field) {
        _annotatedWithEntityReferenceConversionValidation = field.isAnnotationPresent(EntityReferenceConversionValidation.class);
        if (_annotatedWithEntityReferenceConversionValidation) {
            EntityReferenceConversionValidation annotation = field.getAnnotation(EntityReferenceConversionValidation.class);
            _customConverterEntityReference = annotation.customConverter().toBoolean(_customConverterEntityReference);
            _restrictedAccessEntityReference = annotation.restrictedAccess().toBoolean(_restrictedAccessEntityReference);
        }
    }

    private void annotateEntityReferenceDisplay(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityReferenceDisplay.class);
        if (annotatedClass != null) {
            EntityReferenceDisplay annotation = annotatedClass.getAnnotation(EntityReferenceDisplay.class);
            if (annotation != null) {
                _referenceAvatar = annotation.avatar().toBoolean(_referenceAvatar);
                _referenceStyle = specified(annotation.style(), _referenceStyle);
                _referenceFilterBy = specified(annotation.filter(), _referenceFilterBy);
                _referenceFilterType = specified(annotation.filterType(), _referenceFilterType);
                _referenceSortBy = specified(annotation.sort(), _referenceSortBy);
                _annotatedWithEntityReferenceDisplay = true;
            }
        }
    }

    private void annotateEntityReferenceDisplay(Field field) {
        _annotatedWithEntityReferenceDisplay = field.isAnnotationPresent(EntityReferenceDisplay.class);
        if (_annotatedWithEntityReferenceDisplay) {
            EntityReferenceDisplay annotation = field.getAnnotation(EntityReferenceDisplay.class);
            _referenceAvatar = annotation.avatar().toBoolean(_referenceAvatar);
            _referenceStyle = specified(annotation.style(), _referenceStyle);
            _referenceFilterBy = specified(annotation.filter(), _referenceFilterBy);
            _referenceFilterType = specified(annotation.filterType(), _referenceFilterType);
            _referenceSortBy = specified(annotation.sort(), _referenceSortBy);
        }
    }

    private void annotateEntityReferenceSearch(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityReferenceSearch.class);
        if (annotatedClass != null) {
            EntityReferenceSearch annotation = annotatedClass.getAnnotation(EntityReferenceSearch.class);
            if (annotation != null) {
                _searchType = specified(annotation.searchType(), _searchType);
                _listStyle = specified(annotation.listStyle(), _listStyle);
                _radioColumns = Math.min(12, Math.max(0, annotation.radioColumns()));
                _descendingOrder = annotation.descending();
                _searchDisplayFormat = specified(annotation.displayFormat(), _searchDisplayFormat);
                _searchDisplayMode = specified(annotation.displayMode(), _searchDisplayMode);
                _annotatedWithEntityReferenceSearch = true;
            }
        }
    }

    private void annotateEntityReferenceSearch(Field field) {
        _annotatedWithEntityReferenceSearch = field.isAnnotationPresent(EntityReferenceSearch.class);
        if (_annotatedWithEntityReferenceSearch) {
            EntityReferenceSearch annotation = field.getAnnotation(EntityReferenceSearch.class);
            _searchType = specified(annotation.searchType(), _searchType);
            _listStyle = specified(annotation.listStyle(), _listStyle);
            _radioColumns = Math.min(12, Math.max(0, annotation.radioColumns()));
            _descendingOrder = annotation.descending();
            _searchDisplayFormat = specified(annotation.displayFormat(), _searchDisplayFormat);
            _searchDisplayMode = specified(annotation.displayMode(), _searchDisplayMode);
        }
    }

    private void annotateEntityViewLocation(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, EntityViewLocation.class);
        if (annotatedClass != null) {
            EntityViewLocation annotation = annotatedClass.getAnnotation(EntityViewLocation.class);
            if (annotation != null) {
                setApplicationOrigin(specified(annotation.origin(), _applicationOrigin));
                setApplicationContextRoot(specified(annotation.application(), _applicationContextRoot));
                setApplicationConsolePath(specified(annotation.consolePath(), _applicationConsolePath));
                setApplicationReadingPath(specified(annotation.readingPath(), _applicationReadingPath));
                setApplicationWritingPath(specified(annotation.writingPath(), _applicationWritingPath));
                _annotatedWithEntityViewLocation = true;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void annotateFilter(Field field) {
        /*
        _annotatedWithFilter = field.isAnnotationPresent(Filter.class);
        if (_annotatedWithFilter) {
            Filter annotation = field.getAnnotation(Filter.class);
            _filterInactiveIndicatorProperty = annotation.inactive().toBoolean(_filterInactiveIndicatorProperty);
            _filterOwnerProperty = annotation.owner().toBoolean(_filterOwnerProperty);
            _filterSegmentProperty = annotation.segment().toBoolean(_filterSegmentProperty);
        }
        /**/
    }

    private void annotateOneToOne(Field field) {
        _annotatedWithOneToOne = field.isAnnotationPresent(OneToOne.class);
        if (_annotatedWithOneToOne) {
            OneToOne annotation = field.getAnnotation(OneToOne.class);
            _mainRelationship = annotation.main().toBoolean(false);
            _fetchType = annotation.fetch();
            _cascadeType = annotation.cascade();
            _navigability = annotation.navigability();
            _oneToOneDetailView = annotation.detailView().toBoolean(false);
            _masterDetailViewSequence = Math.max(_masterDetailViewSequence, annotation.viewSequence());
            _masterDetailViewMenuOptionEnabled = annotation.menu();
        }
    }

    private void annotateManyToOne(Field field) {
        _annotatedWithManyToOne = field.isAnnotationPresent(ManyToOne.class);
        if (_annotatedWithManyToOne) {
            ManyToOne annotation = field.getAnnotation(ManyToOne.class);
            _mainRelationship = annotation.main().toBoolean(false);
            _fetchType = annotation.fetch();
            _cascadeType = annotation.cascade();
            _navigability = annotation.navigability();
            _masterDetailView = annotation.view();
            _masterDetailViewSequence = Math.max(_masterDetailViewSequence, annotation.viewSequence());
            _masterDetailViewMenuOptionEnabled = annotation.menu();
            _quickAddingFilter = annotation.quickAdding();
            /**/
            _aboveMasterHeadingSnippetFileName = annotation.aboveMasterHeadingSnippet();
            _belowMasterHeadingSnippetFileName = annotation.belowMasterHeadingSnippet();
            _insideMasterHeadingSnippetFileName = annotation.insideMasterHeadingSnippet();
            /**/
        }
    }

    private void annotateQueryMapping(Field field) {
        _annotatedWithQueryMapping = field.isAnnotationPresent(QueryMapping.class);
        if (_annotatedWithQueryMapping) {
            QueryMapping annotation = field.getAnnotation(QueryMapping.class);
            _keyPropertiesQueryMappingEnabled = annotation.mapKeyProperties().toBoolean(_keyPropertiesQueryMappingEnabled);
        }
    }

    private void annotateEntityReferenceDataGen(Field field) {
        _annotatedWithEntityReferenceDataGen = field.isAnnotationPresent(EntityReferenceDataGen.class);
        if (_annotatedWithEntityReferenceDataGen) {
            EntityReferenceDataGen annotation = field.getAnnotation(EntityReferenceDataGen.class);
            _dataGenType = annotation.type();
            _dataGenNullable = Math.min(100, Math.max(0, annotation.nullable()));
        }
    }
    // </editor-fold>

    void initializeInheritanceFields() {
        Class<?> type = getDataType();
        if (_rootInstance) {
            _baseClass = XS1.getConcreteSuperclass(getClass());
            if (_baseClass != null) {
                Entity baseEntity = _project.getEntity(_baseClass);
                if (baseEntity != null) {
                    baseEntity.getSubclassesMap().put(type.getName(), type);
                }
            }
        } else {
            Entity rootEntity = _project.getEntity(type);
            _baseClass = rootEntity.getBaseClass();
        }
    }

    @Override
    public Project getDeclaringProject() {
        return _project;
    }

    /**
     * @return the root entity instance of the same class
     */
    @Override
    public Entity getRoot() {
        return getClassMainInstance();
    }

    /**
     * @return the root entity instance of the base class
     */
    @Override
    public Entity getBaseRoot() {
        return getBaseClassMainInstance();
    }

    public Entity getSqlOperationBaseRoot() {
        return sqlOperationBaseRoot(this);
    }

    private Entity sqlOperationBaseRoot(Entity entity) {
        Entity br = entity.getBaseRoot();
        return br == null ? null : br.isSqlCodeGenEnabled() && br.isPersistentEntity() ? br : sqlOperationBaseRoot(br);
    }

    /**
     * @return the root entity instance of the same class
     */
    @Override
    public Entity getHierarchyRoot() {
        return _baseClass == null ? getRoot() : getBaseClassMainInstance().getHierarchyRoot();
    }

    /**
     * @return the class hierarchy node type; null if the entity is not part of a hierarchy
     */
    @Override
    public HierarchyNodeType getHierarchyNodeType() {
        return _baseClass == null
            ? _subclasses.isEmpty() ? null : HierarchyNodeType.ROOT
            : _subclasses.isEmpty() ? HierarchyNodeType.LEAF : HierarchyNodeType.BRANCH;
    }

    /**
     * @return the ancestors list
     */
    @Override
    public List<Entity> getAncestorsList() {
        List<Entity> ancestors = new ArrayList<>();
        if (_baseClass != null) {
            Entity base = getBaseClassMainInstance();
            ancestors.addAll(base.getAncestorsList());
            ancestors.add(base);
        }
        return ancestors;
    }

    /**
     * @return the known extensions list
     */
    @Override
    public List<Entity> getExtensionsList() {
        return new ArrayList<>(getExtensionsMap().values());
    }

    /**
     * @return the direct known extensions map
     */
    @Override
    public Map<String, Entity> getExtensionsMap() {
        Map<String, Entity> entities = new LinkedHashMap<>();
        Entity ext;
        List<Class<?>> subclasses = getMainInstanceSubclassesList();
        if (subclasses != null) {
            for (Class<?> subclass : subclasses) {
                ext = _project.getEntity(subclass);
                entities.put(subclass.getName(), ext);
                entities.putAll(ext.getExtensionsMap());
            }
        }
        return entities;
    }

    public List<State> getStatesList() {
        List<State> list = new ArrayList<>();
        list.addAll(getStatesMap().values());
        return list;
    }

    public List<State> getInitialStatesList() {
        List<State> list = new ArrayList<>();
        List<State> states = getStatesList();
        List<Operation> operations = getOperationsList();
        List<Transition> transitions;
        boolean add;
        for (State state : states) {
            add = true;
            operationsLoop:
                for (Operation operation : operations) {
                    transitions = operation.getTransitionsList();
                    for (Transition transition : transitions) {
                        if (state.equals(transition.getY())) {
                            if (state.equals(transition.getX())) {
                                continue;
                            }
                            add = transition.getX() == null;
                            if (add) {
                                break operationsLoop;
                            }
                        }
                    }
                }
            if (add) {
                list.add(state);
            }
        }
        return list;
    }

    public List<State> getFinalStatesList() {
        List<State> list = new ArrayList<>();
        List<State> states = getStatesList();
        List<Operation> operations = getOperationsList();
        List<Transition> transitions;
        boolean add;
        for (State state : states) {
            add = true;
            operationsLoop:
                for (Operation operation : operations) {
                    transitions = operation.getTransitionsList();
                    for (Transition transition : transitions) {
                        if (state.equals(transition.getX())) {
                            if (state.equals(transition.getY())) {
                                continue;
                            }
                            add = transition.getY() == null;
                            if (add) {
                                break operationsLoop;
                            }
                        }
                    }
                }
            if (add) {
                list.add(state);
            }
        }
        return list;
    }

    public List<State> getImplicitInitialStatesList() {
        List<State> list = new ArrayList<>();
        List<State> states = getStatesList();
        List<Operation> operations = getOperationsList();
        List<Transition> transitions;
        boolean add;
        for (State state : states) {
            add = true;
            operationsLoop:
                for (Operation operation : operations) {
                    transitions = operation.getTransitionsList();
                    for (Transition transition : transitions) {
                        if (state.equals(transition.getY())) {
                            if (state.equals(transition.getX())) {
                                continue;
                            }
                            add = false;
                            break operationsLoop;
                        }
                    }
                }
            if (add) {
                list.add(state);
            }
        }
        return list;
    }

    public List<State> getImplicitFinalStatesList() {
        List<State> list = new ArrayList<>();
        List<State> states = getStatesList();
        List<Operation> operations = getOperationsList();
        List<Transition> transitions;
        boolean add;
        for (State state : states) {
            add = true;
            operationsLoop:
                for (Operation operation : operations) {
                    transitions = operation.getTransitionsList();
                    for (Transition transition : transitions) {
                        if (state.equals(transition.getX())) {
                            if (state.equals(transition.getY())) {
                                continue;
                            }
                            add = false;
                            break operationsLoop;
                        }
                    }
                }
            if (add) {
                list.add(state);
            }
        }
        return list;
    }

    public List<State> getEnumStates() {
        List<State> list = new ArrayList<>();
        Entity stateProperty = getStateProperty();
        if (stateProperty instanceof EnumerationEntity) {
            for (State state : getStatesList()) {
                if (state instanceof BooleanComparisonX bcx) {
                    if (ComparisonOp.EQ.equals(bcx.getOperator()) && bcx.getX() == stateProperty && bcx.getY() instanceof Instance instance) {
                        if (sameDataTypeSimpleName(stateProperty, instance.getDeclaringEntity())) {
                            list.add(state);
                        }
                    }
                }
            }
        }
        return list;
    }

    public Map<String, State> getStatesMap() {
        Map<String, State> map = new LinkedHashMap<>();
        Map<String, Expression> expressions = _atlas.getExpressionsMap();
        Expression value;
        Class<?> fieldType;
        for (String key : expressions.keySet()) {
            value = expressions.get(key);
            if (value instanceof State state) {
                fieldType = value.getDeclaringField().getType();
                if (State.class.isAssignableFrom(fieldType)) {
                    map.put(value.getName(), state);
                }
            }
        }
        return map;
    }

    private final List<State> _happyPath = new ArrayList<>();

    public List<State> getHappyPath() {
        return _happyPath;
    }

    /**
     * El método setHappyPath se utiliza para establecer la secuencia de estados que están en el camino feliz de la entidad. En el contexto del
     * modelado de información, el camino feliz (a veces llamado flujo feliz) es un escenario predeterminado que no presenta condiciones excepcionales
     * o de error. La interfaz de usuario muestra el camino feliz mediante una línea de tiempo en el campo que corresponde a la propiedad estado de la
     * entidad, en las vistas (páginas) de consulta detallada y en el encabezado de vistas Maestro/Detalle, en las que la entidad es el Maestro,
     * dependiendo de lo establecido con el método setHappyPathDisplaySpots. La línea de tiempo podría incluir la fecha, o fecha y hora, de la
     * transición para llegar a cada estado; para incluirla se utilizan los métodos setTransitionDate y setTransitionTimestamp de la expresión que
     * define el estado.
     *
     * El método setHappyPath debe ejecutarse en el método settleExpressions, después de haber definido las expresiones correspondientes a los
     * estados.
     *
     * @param states dos o más estados previamente definidos. dos o más estados previamente definidos. Solo se pueden incluir estados definidos con el
     * método isEqualTo de igualdad de la propiedad estado de la entidad.
     */
    protected void setHappyPath(State... states) {
        if (depth() == 0) {
            _happyPath.clear();
            if (states == null || states.length == 0) {
                logger.trace(getName() + "'s list of happy states has been deleted");
            } else if (states.length < 2) {
                logger.error(getName() + "'s list of happy states has only one element");
                Project.increaseParserErrorCount();
            } else {
                List<Field> fields = XS1.getFields(getClass(), Entity.class, EnumerationEntity.class, StateProperty.class);
                Field field = fields.size() == 1 ? fields.get(0) : null;
                Object keyProperty = field == null ? null : getKeyProperty(field);
                if (keyProperty instanceof Entity stateProperty) {
                    boolean error = false;
                    for (State state : states) {
                        if (state instanceof BooleanComparisonX bcx) {
                            if (ComparisonOp.EQ.equals(bcx.getOperator()) && bcx.getX() == stateProperty && bcx.getY() instanceof Instance instance) {
                                if (sameDataTypeSimpleName(stateProperty, instance.getDeclaringEntity())) {
                                    _happyPath.add(state);
                                } else {
                                    error = true;
                                }
                            } else {
                                error = true;
                            }
                        } else {
                            error = true;
                        }
                    }
                    if (error) {
                        logger.error(getName() + "'s list of happy states has one or more elements that cannot be used as a happy state");
                        Project.increaseParserErrorCount();
                    } else {
                        logger.trace(getName() + "'s list of happy states has been set");
                    }
                } else {
                    logger.error(getName() + " does not have a state property; therefore a list of happy states can't be set");
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    private HappyPathDisplaySpots _happyPathDisplaySpots = HappyPathDisplaySpots.DETAIL_VIEW;

    public HappyPathDisplaySpots getHappyPathDisplaySpots() {
        return _happyPathDisplaySpots;
    }

    /**
     * El método setHappyPathDisplaySpots se utiliza para establecer los puntos donde la interfaz de usuario muestra el camino feliz de la entidad. De
     * forma predeterminada, el camino feliz se muestra mediante una línea de tiempo en el campo que corresponde a la propiedad estado de la entidad,
     * en vistas (páginas) de consulta detallada.
     *
     * El método setHappyPathDisplaySpots debe ejecutarse en el método settleExpressions, conjuntamente con el método setHappyPath.
     *
     * @param spots puntos donde la interfaz de usuario muestra el camino feliz de la entidad. Su valor es uno de los elementos de la enumeración
     * HappyPathDisplaySpots. Seleccione DETAIL_VIEW o DETAIL_VIEW_AND_MASTER_HEADING para mostrarlo solo en vistas (páginas) de consulta detallada de
     * la entidad (este es su valor predeterminado), o en vistas de consulta detallada y en el encabezado de vistas Maestro/Detalle, en las que la
     * entidad es el Maestro, respectivamente. Seleccione NONE para no mostrar el camino feliz.
     */
    public void setHappyPathDisplaySpots(HappyPathDisplaySpots spots) {
        checkScope();
        _happyPathDisplaySpots = spots == null ? HappyPathDisplaySpots.UNSPECIFIED : spots;
    }

    private Entity getClassMainInstance() {
        if (_rootInstance) {
            return this;
        }
        return _project.getEntity(getDataType());
    }

    private Entity getBaseClassMainInstance() {
        return _baseClass == null ? null : _project.getEntity(_baseClass);
    }

    private List<Class<?>> getMainInstanceSubclassesList() {
        Entity main = getClassMainInstance();
        return main == null ? getSubclassesList() : main.getSubclassesList();
    }

    boolean isEntityReference() {
        Field field = getDeclaringField();
        if (field != null) {
            Class<?> type = field.getType();
            if (type != null) {
                return EntityReference.class.isAssignableFrom(type);
            }
        }
        return false;
    }

    @Override
    public boolean isEntityCollector() {
        List<EntityCollection> list = getEntityCollectionsList();
        return !list.isEmpty() && !extendsEntityCollector();
    }

    private boolean extendsEntityCollector() {
        Entity base = getBaseRoot();
        return base != null && base.isEntityCollector();
    }

    @Override
    public boolean isOverlayableEntity() {
        List<Property> list = getRoot().getOverlayPropertiesList();
        return list != null && !list.isEmpty();
    }

    @Override
    public boolean isOverlayableEntityReference() {
        if (isEntityReference() && !isHiddenField()) { // umtil 04/05/2023:  && !isEnumerationEntity()
            List<Property> list = getRoot().getOverlayPropertiesList();
            return list != null && !list.isEmpty();
        }
        return false;
    }

    /**
     * @return the overlay entities list
     */
    @Override
    public List<Entity> getOverlayEntitiesList() {
        return new ArrayList<>(getOverlayEntitiesMap().values());
    }

    /**
     * @return the overlay entities map
     */
    @Override
    public Map<String, Entity> getOverlayEntitiesMap() {
        Map<String, Entity> map = new LinkedHashMap<>();
        Entity entt;
        Entity root;
        String name;
        List<Property> properties = getPropertiesList();
        for (Property property : properties) {
            if (property.isOverlayableEntityReference()) {
                entt = (Entity) property;
                root = entt.getRoot();
                name = root.getName();
                if (!map.containsKey(name)) {
                    map.put(name, root);
                }
            }
        }
        return map;
    }

    /**
     * @return the accesible operations overlay entities list
     */
    @Override
    public List<Entity> getAccesibleOperationsOverlayEntitiesList() {
        return new ArrayList<>(getAccesibleOperationsOverlayEntitiesMap().values());
    }

    /**
     * @return the accesible operations overlay entities map
     */
    @Override
    public Map<String, Entity> getAccesibleOperationsOverlayEntitiesMap() {
        Map<String, Entity> map = new LinkedHashMap<>();
        Entity entt;
        Entity root;
        String name;
        for (Operation operation : getAccesibleBusinessOperationsList()) {
            for (Parameter parameter : operation.getParametersList()) {
                if (parameter.isOverlayableEntityReference()) {
                    entt = (Entity) parameter;
                    root = entt.getRoot();
                    name = root.getName();
                    if (!map.containsKey(name)) {
                        map.put(name, root);
                    }
                }
            }
        }
        return map;
    }

    /**
     * @return the operation keys
     */
    public Set<String> getOperationKeys() {
        Set<String> set = new TreeSet<>();
        set.addAll(getDefaultCrudOperationKeys());
        set.addAll(getUserDefinedOperationKeys());
        return set;
    }

    /**
     * @return the CRUD operation keys
     */
    public Set<String> getDefaultCrudOperationKeys() {
        Set<String> set = new TreeSet<>();
        String[] operations = Operation.getCrudOperationKeys();
        set.addAll(Arrays.asList(operations));
        return set;
    }

    /**
     * @return the user-defined operation keys
     */
    public Set<String> getUserDefinedOperationKeys() {
        Set<String> set = new TreeSet<>();
        for (Operation operation : getOperationsList()) {
            set.add(operation.getName());
        }
        return set;
    }

    /**
     * @param <T> generic operation type
     * @param type operation type
     * @return the operation of the specified class
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Operation> T getOperation(Class<T> type) {
        for (Operation operation : getOperationsList()) {
            if (operation.getClass().isAssignableFrom(type)) {
                return (T) operation;
            }
        }
        return null;
    }

    /**
     * @return the CRUD operation list
     */
    @Override
    public List<Operation> getCrudOperationsList() {
        ArrayList<Operation> list = new ArrayList<>();
        if (select != null) {
            list.add(select);
        }
        if (insert != null) {
            list.add(insert);
        }
        if (update != null) {
            list.add(update);
        }
        if (delete != null) {
            list.add(delete);
        }
        return list;
    }

    /**
     * @return the user-defined business operation list
     */
    @Override
    public List<Operation> getBusinessOperationsList() {
        ArrayList<Operation> list = new ArrayList<>();
        OperationType operationType;
        for (Operation operation : getOperationsList()) {
            operationType = operation.getOperationType();
            switch (operationType) {
                case EXPORT, REPORT, PROCEDURE, PROCESS ->
                    list.add(operation);
            }
        }
        return list;
    }

    /**
     * @return the user-defined accesible business operation list
     */
    @Override
    public List<Operation> getAccesibleBusinessOperationsList() {
        List<Operation> list = getBusinessOperationsList();
        if (list != null && !list.isEmpty()) {
            Collection<Operation> collection = ColUtils.filter(list, new IsAccesibleOperation());
            if (collection != null && !collection.isEmpty()) {
                return new ArrayList<>(collection);
            }
        }
        return new ArrayList<>();
    }

    // <editor-fold defaultstate="collapsed" desc="getAccesibleConstructionOperationsList">
    /*
    // * @return the user-defined accesible construction operation list of this entity class
    //
    public List<Operation> getAccesibleConstructionOperationsList() {
        return getAccesibleConstructionOperationsList(null);
    }

    //
    // * @param master a second entity class to search for construction operations of this entity class.
    // *
    // * @return the user-defined accesible construction operation list of this entity class; if parameter master is not null, construction operations
    // * defined in the master entity class are also included.
    //
    public List<Operation> getAccesibleConstructionOperationsList(Entity master) {
        List<Operation> constructors = accesibleConstructionOperationsList(this);
        if (master != null) {
            constructors.addAll(accesibleConstructionOperationsList(master));
        }
        return constructors;
    }

    private List<Operation> accesibleConstructionOperationsList(Entity entity) {
        Class<?> thisClass = getNamedClass();
        List<Operation> constructors = new ArrayList<>();
        List<Operation> operations = entity.getOperationsList();
        if (operations != null && !operations.isEmpty()) {
            Class<? extends Entity> constructionType;
            for (Operation operation : operations) {
                if (operation instanceof ProcessOperation) {
                    constructionType = ((ProcessOperation) operation).getConstructionType();
                    if (constructionType != null && constructionType.isAssignableFrom(thisClass)) {
                        constructors.add(operation);
                        logger.trace(operation.signature());
                    }
                }
            }
        }
        return constructors;
    }
    /**/
    // </editor-fold>
//
    public List<ProcessOperation> getConstructionOperationsList() {
        Class<?> thisClass = getNamedClass();
        String thisClassSimpleName = thisClass.getSimpleName();
        List<ProcessOperation> constructors = new ArrayList<>();
        Project project = TLC.getProject();
        Map<Class<? extends Entity>, List<ProcessOperation>> map = project == null ? new LinkedHashMap<>() : project.getConstructorsMap();
        Set<Class<? extends Entity>> keySet = map.keySet();
        if (keySet != null && !keySet.isEmpty()) {
            List<ProcessOperation> list;
            for (Class<? extends Entity> entityClass : keySet) {
                if (thisClassSimpleName.equals(entityClass.getSimpleName()) || thisClass.isAssignableFrom(entityClass)) {
                    list = map.get(entityClass);
                    for (ProcessOperation processOperation : list) {
                        if (processOperation.isNotInheritedFromConcrete()) {
                            constructors.add(processOperation);
                        }
                    }
                }
            }
        }
        return constructors;
    }

    /**
     * @return the parameter keys
     */
    public Set<String> getParameterKeys() {
        Set<String> set = new TreeSet<>();
        for (Property property : getPropertiesList()) {
            set.add(property.getName());
        }
        for (Operation operation : getOperationsList()) {
            for (Parameter parameter : operation.getParametersList()) {
                set.add(parameter.getName());
            }
        }
        return set;
    }

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends EntityWrapper> getDefaultWrapperClass() {
        return EntityWrapper.class;
    }

    private boolean _removePropertiesInRemoveHeadingPropertyArray;

    /**
     * @return true if the array contains the properties that should be excluded from the heading of Master/Detail views (pages); false, if the array
     * contains the properties that should not be excluded from the heading.
     */
    @Override
    public boolean isRemovePropertiesInRemoveHeadingPropertyArray() {
        return _removePropertiesInRemoveHeadingPropertyArray;
    }

    private Property[] _removeHeadingPropertyArray;

    /**
     * @return the array of properties that should be excluded from the heading of Master/Detail views (pages)
     */
    @Override
    public Property[] getRemoveHeadingPropertyArray() {
        return _removeHeadingPropertyArray;
    }

    /**
     * El método setRemoveHeadingPropertyArray se utiliza para establecer el conjunto de propiedades que se deben excluir del encabezado del Maestro
     * en las vistas (páginas) Maestro/Detalle en las que esta entidad es el Detalle.
     *
     * @param properties una o más propiedades de cualquiera de las referencias (propiedades que hacen referencia a otra entidad) que generan vistas
     * (páginas) Maestro/Detalle.
     */
    public void setRemoveHeadingPropertyArray(Property... properties) {
        checkScope();
        _removeHeadingPropertyArray = properties;
        _removePropertiesInRemoveHeadingPropertyArray = true;
    }

    /**
     * El método setRemoveHeadingPropertyNotInArray se utiliza para establecer el conjunto de propiedades que no se deben excluir del encabezado del
     * Maestro en las vistas (páginas) Maestro/Detalle en las que esta entidad es el Detalle.
     *
     * @param properties una o más propiedades de cualquiera de las referencias (propiedades que hacen referencia a otra entidad) que generan vistas
     * (páginas) Maestro/Detalle.
     */
    public void setRemoveHeadingPropertyNotInArray(Property... properties) {
        checkScope();
        _removeHeadingPropertyArray = properties;
        _removePropertiesInRemoveHeadingPropertyArray = false;
    }

    // <editor-fold defaultstate="collapsed" desc="EntityExpression">
    @Override
    public BooleanComparisonX isNull() {
        return XB.Entity.Comparison.isNull(this);
    }

    @Override
    public BooleanComparisonX isNotNull() {
        return XB.Entity.Comparison.isNotNull(this);
    }

    @Override
    public BooleanComparisonX isEqualTo(Entity y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(Instance y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    /*
    @Override
    public BooleanComparisonX isEqualTo(SpecialEntityValue y) {
        return XB.Entity.Comparison.isEqualTo(this, y);
    }

    /**/
    @Override
    public BooleanComparisonX isNotEqualTo(Entity y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(Instance y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNotEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    /*
    @Override
    public BooleanComparisonX isNotEqualTo(SpecialEntityValue y) {
        return XB.Entity.Comparison.isNotEqualTo(this, y);
    }

    /**/
    @Override
    public BooleanComparisonX isNullOrEqualTo(Entity y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(Instance y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    /*
    @Override
    public BooleanComparisonX isNullOrEqualTo(SpecialEntityValue y) {
        return XB.Entity.Comparison.isNullOrEqualTo(this, y);
    }

    /**/
    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Entity y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(Instance y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotEqualTo(EntityExpression y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    /*
    @Override
    public BooleanComparisonX isNullOrNotEqualTo(SpecialEntityValue y) {
        return XB.Entity.Comparison.isNullOrNotEqualTo(this, y);
    }

    /**/
    @Override
    public EntityScalarX self() {
        return XB.Entity.Scalar.self(this);
    }

    @Override
    public EntityOrderedPairX coalesce(Entity y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(Instance y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }

    @Override
    public EntityOrderedPairX coalesce(EntityExpression y) {
        return XB.Entity.OrderedPair.coalesce(this, y);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="EntityReference Expressions">
    @Override
    public BooleanComparisonX isIn(EntityReference... y) {
        return XB.Entity.Comparison.isIn(this, y);
    }

    @Override
    public BooleanComparisonX isIn(Instance... y) {
        return XB.Entity.Comparison.isIn(this, y);
    }

    @Override
    public BooleanComparisonX isIn(String y) {
        return XB.Entity.Comparison.isIn(this, y);
    }

    @Override
    public NativeQuerySegment isIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Entity.Comparison.isIn(this, y));
    }

    @Override
    public BooleanComparisonX isNotIn(EntityReference... y) {
        return XB.Entity.Comparison.isNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNotIn(Instance... y) {
        return XB.Entity.Comparison.isNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNotIn(String y) {
        return XB.Entity.Comparison.isNotIn(this, y);
    }

    @Override
    public NativeQuerySegment isNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Entity.Comparison.isNotIn(this, y));
    }

    @Override
    public BooleanComparisonX isNullOrIn(EntityReference... y) {
        return XB.Entity.Comparison.isNullOrIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrIn(Instance... y) {
        return XB.Entity.Comparison.isNullOrIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrIn(String y) {
        return XB.Entity.Comparison.isNullOrIn(this, y);
    }

    @Override
    public NativeQuerySegment isNullOrIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Entity.Comparison.isNullOrIn(this, y));
    }

    @Override
    public BooleanComparisonX isNullOrNotIn(EntityReference... y) {
        return XB.Entity.Comparison.isNullOrNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotIn(Instance... y) {
        return XB.Entity.Comparison.isNullOrNotIn(this, y);
    }

    @Override
    public BooleanComparisonX isNullOrNotIn(String y) {
        return XB.Entity.Comparison.isNullOrNotIn(this, y);
    }

    @Override
    public NativeQuerySegment isNullOrNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Entity.Comparison.isNullOrNotIn(this, y));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Converters and Validators">
    protected static final String GOOGLE_MAPS_EMBED_CONVERTER = GoogleMapsConstants.GOOGLE_MAPS_EMBED_CONVERTER;

    protected static final String PHONE_NUMBER_VALIDATOR = PhoneConstants.PHONE_NUMBER_VALIDATOR;

    protected static final String LOCAL_PHONE_NUMBER_VALIDATOR = PhoneConstants.LOCAL_PHONE_NUMBER_VALIDATOR;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Converters and Validators">
    protected static final String GOOGLE_MAPS_EMBED_ENGLISH_SHORT_DESCRIPTION = GoogleMapsConstants.GOOGLE_MAPS_EMBED_ENGLISH_SHORT_DESCRIPTION;

    protected static final String GOOGLE_MAPS_EMBED_SPANISH_SHORT_DESCRIPTION = GoogleMapsConstants.GOOGLE_MAPS_EMBED_SPANISH_SHORT_DESCRIPTION;

    protected static final String GOOGLE_MAPS_EMBED_ENGLISH_TOOLTIP = GoogleMapsConstants.GOOGLE_MAPS_EMBED_ENGLISH_TOOLTIP;

    protected static final String GOOGLE_MAPS_EMBED_SPANISH_TOOLTIP = GoogleMapsConstants.GOOGLE_MAPS_EMBED_SPANISH_TOOLTIP;

    protected static final String GOOGLE_MAPS_EMBED_ENGLISH_DESCRIPTION = GoogleMapsConstants.GOOGLE_MAPS_EMBED_ENGLISH_DESCRIPTION;

    protected static final String GOOGLE_MAPS_EMBED_SPANISH_DESCRIPTION = GoogleMapsConstants.GOOGLE_MAPS_EMBED_SPANISH_DESCRIPTION;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Fields">
    protected static final int MAX_EMAIL_ADDRESS_LENGTH = Constants.MAX_EMAIL_ADDRESS_LENGTH;

    protected static final String EMAIL_REGEX = Constants.EMAIL_REGEX;

    protected static final String PHONE_REGEX = Constants.PHONE_REGEX;

    protected static final String LOCAL_PHONE_REGEX = Constants.LOCAL_PHONE_REGEX;

    protected static final String URL_REGEX = Constants.URL_REGEX;

    protected static final String WHITESPACELESS_REGEX = Constants.WHITESPACELESS_REGEX;

    protected static final SpecialCharacterValue NO_IMAGE = SpecialCharacterValue.NULL;

    protected static final SpecialEntityValue CURRENT_USER = SpecialEntityValue.CURRENT_USER;

    protected static final BooleanScalarX NULL_BOOLEAN = XB.NULL_BOOLEAN;

    protected static final BooleanScalarX TRUTH = XB.TRUTH;

    protected static final BooleanScalarX UNTRUTH = XB.UNTRUTH;

    protected static final CharacterScalarX NULL_STRING = XB.NULL_STRING;

    protected static final CharacterScalarX EMPTY_STRING = XB.EMPTY_STRING;

    protected static final CharacterScalarX EMPTY = XB.EMPTY;

    protected static final CharacterScalarX RGUID = XB.RGUID;

    protected static final CharacterScalarX SPACE = XB.SPACE;

    protected static final CharacterScalarX COMMA = XB.COMMA;

    protected static final CharacterScalarX HYPHEN = XB.HYPHEN;

    protected static final CharacterScalarX PERIOD = XB.PERIOD;

    protected static final CharacterScalarX SLASH = XB.SLASH;

    protected static final CharacterScalarX COLON = XB.COLON;

    protected static final CharacterScalarX SEMICOLON = XB.SEMICOLON;

    protected static final CharacterScalarX UNDERSCORE = XB.UNDERSCORE;

    protected static final CharacterScalarX VBAR = XB.VBAR;

    protected static final CharacterScalarX CONTENT_ROOT_DIR = XB.CONTENT_ROOT_DIR;

    protected static final CharacterScalarX CURRENT_USER_CODE = XB.CURRENT_USER_CODE;

    protected static final NumericScalarX NULL_NUMBER = XB.NULL_NUMBER;

    protected static final NumericScalarX CURRENT_USER_ID = XB.CURRENT_USER_ID;

    protected static final TemporalScalarX NULL_TEMPORAL = XB.NULL_TEMPORAL;

    protected static final TemporalScalarX CURRENT_DATE = XB.CURRENT_DATE;

    protected static final TemporalScalarX CURRENT_TIME = XB.CURRENT_TIME;

    protected static final TemporalScalarX CURRENT_TIMESTAMP = XB.CURRENT_TIMESTAMP;

    protected static final java.sql.Date EPOCH_DATE = DateData.EPOCH;

    protected static final java.sql.Time EPOCH_TIME = TimeData.EPOCH;

    protected static final java.sql.Timestamp EPOCH_TIMESTAMP = TimestampData.EPOCH;

    protected static final EntityScalarX NULL_ENTITY = XB.NULL_ENTITY;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Fields">
    protected static final String EMAIL_REGEX_ENGLISH_DESCRIPTION = EmailConstants.EMAIL_REGEX_ENGLISH_DESCRIPTION;

    protected static final String EMAIL_REGEX_SPANISH_DESCRIPTION = EmailConstants.EMAIL_REGEX_SPANISH_DESCRIPTION;

    protected static final String EMAIL_REGEX_ENGLISH_ERROR_MESSAGE = EmailConstants.EMAIL_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String EMAIL_REGEX_SPANISH_ERROR_MESSAGE = EmailConstants.EMAIL_REGEX_SPANISH_ERROR_MESSAGE;

    protected static final String PHONE_REGEX_ENGLISH_DESCRIPTION = PhoneConstants.PHONE_REGEX_ENGLISH_DESCRIPTION;

    protected static final String PHONE_REGEX_SPANISH_DESCRIPTION = PhoneConstants.PHONE_REGEX_SPANISH_DESCRIPTION;

    protected static final String PHONE_REGEX_ENGLISH_ERROR_MESSAGE = PhoneConstants.PHONE_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String PHONE_REGEX_SPANISH_ERROR_MESSAGE = PhoneConstants.PHONE_REGEX_SPANISH_ERROR_MESSAGE;

    protected static final String LOCAL_PHONE_REGEX_ENGLISH_DESCRIPTION = PhoneConstants.LOCAL_PHONE_REGEX_ENGLISH_DESCRIPTION;

    protected static final String LOCAL_PHONE_REGEX_SPANISH_DESCRIPTION = PhoneConstants.LOCAL_PHONE_REGEX_SPANISH_DESCRIPTION;

    protected static final String LOCAL_PHONE_REGEX_ENGLISH_ERROR_MESSAGE = PhoneConstants.LOCAL_PHONE_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String LOCAL_PHONE_REGEX_SPANISH_ERROR_MESSAGE = PhoneConstants.LOCAL_PHONE_REGEX_SPANISH_ERROR_MESSAGE;

    protected static final String URL_REGEX_ENGLISH_DESCRIPTION = URLConstants.URL_REGEX_ENGLISH_DESCRIPTION;

    protected static final String URL_REGEX_SPANISH_DESCRIPTION = URLConstants.URL_REGEX_SPANISH_DESCRIPTION;

    protected static final String URL_REGEX_ENGLISH_ERROR_MESSAGE = URLConstants.URL_REGEX_ENGLISH_ERROR_MESSAGE;

    protected static final String URL_REGEX_SPANISH_ERROR_MESSAGE = URLConstants.URL_REGEX_SPANISH_ERROR_MESSAGE;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Expressions">
    protected static BooleanScalarX nullBoolean() {
        return NULL_BOOLEAN;
    }

    protected static BooleanScalarX truth() {
        return TRUTH;
    }

    protected static BooleanScalarX untruth() {
        return UNTRUTH;
    }

    protected static CharacterScalarX nullString() {
        return NULL_STRING;
    }

    protected static CharacterScalarX emptyString() {
        return EMPTY_STRING;
    }

    protected static CharacterScalarX empty() {
        return EMPTY;
    }

    protected static CharacterScalarX contentRootDir() {
        return CONTENT_ROOT_DIR;
    }

    protected static CharacterScalarX currentUserCode() {
        return CURRENT_USER_CODE;
    }

    protected static NumericScalarX nullNumber() {
        return NULL_NUMBER;
    }

    protected static NumericScalarX currentUserId() {
        return CURRENT_USER_ID;
    }

    protected static TemporalScalarX nullTemporal() {
        return NULL_TEMPORAL;
    }

    protected static TemporalScalarX currentDate() {
        return CURRENT_DATE;
    }

    protected static TemporalScalarX currentTime() {
        return CURRENT_TIME;
    }

    protected static TemporalScalarX currentTimestamp() {
        return CURRENT_TIMESTAMP;
    }

    protected static EntityScalarX nullEntity() {
        return NULL_ENTITY;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Special Select Segments">
    protected static NativeQuerySegment isCurrentUserCodeIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Character.Comparison.isIn(CURRENT_USER_CODE, y));
    }

    protected static NativeQuerySegment isCurrentUserCodeNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Character.Comparison.isNotIn(CURRENT_USER_CODE, y));
    }

    protected static NativeQuerySegment isCurrentUserIdIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Numeric.Comparison.isIn(CURRENT_USER_ID, y));
    }

    protected static NativeQuerySegment isCurrentUserIdNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Numeric.Comparison.isNotIn(CURRENT_USER_ID, y));
    }

    protected static NativeQuerySegment isCurrentDateIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Temporal.Comparison.isIn(CURRENT_DATE, y));
    }

    protected static NativeQuerySegment isCurrentDateNotIn(NativeQuery y) {
        return NativeQuerySegment.of(XB.Temporal.Comparison.isNotIn(CURRENT_DATE, y));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Trusted Sites">
    protected static final String EMBED_CALENDAR = TrustedSites.EMBED_CALENDAR;

    protected static final String EMBED_MAPS = TrustedSites.EMBED_MAPS;

    protected static final String EMBED_YOUTUBE = TrustedSites.EMBED_YOUTUBE;

    protected static final String GOOGLE = TrustedSites.GOOGLE;

    protected static final String GOOGLE_CALENDAR = TrustedSites.GOOGLE_CALENDAR;

    protected static final String GOOGLE_MAPS = TrustedSites.GOOGLE_MAPS;

    protected static final String GOOGLE_MAPS_LINK = TrustedSites.GOOGLE_MAPS_LINK;

    protected static final String YOUTUBE = TrustedSites.YOUTUBE;

    protected static final String YOUTUBE_LINK = TrustedSites.YOUTUBE_LINK;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Supplementary Expressions">
    protected static EntityDataAggregateX coalesce(Entity operand1, Entity operand2, Entity... extraOperands) {
        return XB.Entity.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static BooleanDataAggregateX coalesce(BooleanExpression operand1, BooleanExpression operand2, BooleanExpression... extraOperands) {
        return XB.Boolean.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static CharacterDataAggregateX coalesce(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
        return XB.Character.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static NumericDataAggregateX coalesce(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
        return XB.Numeric.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static TemporalDataAggregateX coalesce(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
        return XB.Temporal.DataAggregate.coalesce(operand1, operand2, extraOperands);
    }

    protected static CharacterDataAggregateX max(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
        return XB.Character.DataAggregate.max(operand1, operand2, extraOperands);
    }

    protected static NumericDataAggregateX max(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
        return XB.Numeric.DataAggregate.max(operand1, operand2, extraOperands);
    }

    protected static TemporalDataAggregateX max(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
        return XB.Temporal.DataAggregate.max(operand1, operand2, extraOperands);
    }

    protected static CharacterDataAggregateX min(CharacterExpression operand1, CharacterExpression operand2, CharacterExpression... extraOperands) {
        return XB.Character.DataAggregate.min(operand1, operand2, extraOperands);
    }

    protected static NumericDataAggregateX min(NumericExpression operand1, NumericExpression operand2, NumericExpression... extraOperands) {
        return XB.Numeric.DataAggregate.min(operand1, operand2, extraOperands);
    }

    protected static TemporalDataAggregateX min(TemporalExpression operand1, TemporalExpression operand2, TemporalExpression... extraOperands) {
        return XB.Temporal.DataAggregate.min(operand1, operand2, extraOperands);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Supplementary Expressions">
    protected static CharacterExpression concat(String x, Expression y) {
        return XB.Character.OrderedPair.concat(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concat(SpecialCharacterValue x, Expression y) {
        return XB.Character.OrderedPair.concat(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concat(Expression x, Expression y) {
        return XB.Character.OrderedPair.concat(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concat(Expression x, Expression y, Expression... extraOperands) {
        CharacterExpression cx = charStringOf(x);
        CharacterExpression cy = charStringOf(y);
        if (extraOperands != null && extraOperands.length > 0) {
            List<CharacterExpression> cz = new ArrayList<>();
            for (Expression extraOperand : extraOperands) {
                if (extraOperand != null) {
                    cz.add(charStringOf(extraOperand));
                }
            }
            if (!cz.isEmpty()) {
                return XB.Character.DataAggregate.concat(cx, cy, cz.toArray(CharacterExpression[]::new));
            }
        }
        return XB.Character.OrderedPair.concat(cx, cy);
    }

    protected static CharacterExpression concatenate(String x, Expression y) {
        return XB.Character.OrderedPair.concatenate(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concatenate(SpecialCharacterValue x, Expression y) {
        return XB.Character.OrderedPair.concatenate(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concatenate(Expression x, Expression y) {
        return XB.Character.OrderedPair.concatenate(charStringOf(x), charStringOf(y));
    }

    protected static CharacterExpression concatenate(Expression x, Expression y, Expression... extraOperands) {
        CharacterExpression cx = charStringOf(x);
        CharacterExpression cy = charStringOf(y);
        if (extraOperands != null && extraOperands.length > 0) {
            List<CharacterExpression> cz = new ArrayList<>();
            for (Expression extraOperand : extraOperands) {
                if (extraOperand != null) {
                    cz.add(charStringOf(extraOperand));
                }
            }
            if (!cz.isEmpty()) {
                return XB.Character.DataAggregate.concatenate(cx, cy, cz.toArray(CharacterExpression[]::new));
            }
        }
        return XB.Character.OrderedPair.concatenate(cx, cy);
    }

    protected static CharacterExpression charStringOf(Object x) {
        return x instanceof CharacterExpression ? (CharacterExpression) x : XB.toCharString(x);
    }

    protected static TemporalExpression dateOf(Object x) {
        return x instanceof TemporalExpression ? (TemporalExpression) x : XB.toDate(x);
    }

    protected static TemporalExpression timeOf(Object x) {
        return x instanceof TemporalExpression ? (TemporalExpression) x : XB.toTime(x);
    }

    protected static TemporalExpression timestampOf(Object x) {
        return x instanceof TemporalExpression ? (TemporalExpression) x : XB.toTimestamp(x);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="iframe">
    /**
     * Crea la definición de un iframe usando MessageFormat.format
     *
     * @param src URL del documento a incrustar en el iframe
     * @return la definición de un iframe de 300 x 150 píxeles
     */
    protected static String iframe(String src) {
        return iframe(src, 0, 0);
    }

    /**
     * Crea la definición de un iframe usando MessageFormat.format
     *
     * @param src URL del documento a incrustar en el iframe
     * @param width ancho del iframe en píxeles. Un número menor o igual a 0 es equivalente a 300; un número mayor que 0 y menor que 100 es
     * equivalente a 100.
     * @param height alto del iframe en píxeles. Un número menor o igual a 0 es equivalente a 150; un número mayor que 0 y menor que 50 es equivalente
     * a 50.
     * @return la definición de un iframe
     */
    protected static String iframe(String src, int width, int height) {
        return XS2.iframe(src, width, height);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(Entity o) {
        Entity that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
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
            string += _atlas.fieldsToString(n, key, verbose, fields, maps);
            if (verbose) {
//              string += fee + tab + "rootInstance" + faa + _rootInstance + foo;
                string += fee + tab + "explicitlyDeclared" + faa + _explicitlyDeclared + foo;
                string += fee + tab + "implicitlyDeclared" + faa + _implicitlyDeclared + foo;
                if (isEntityReference()) {
                    string += fee + tab + "oneToOne" + faa + isOneToOne() + foo;
                    string += fee + tab + "manyToOne" + faa + isManyToOne() + foo;
                    string += fee + tab + "fetchType" + faa + _fetchType + foo;
//                  string += fee + tab + "cascadeType" + faa + _cascadeType + foo;
                    string += fee + tab + "navigability" + faa + _navigability + foo;
                    string += fee + tab + "masterDetailView" + faa + _masterDetailView + foo;
                    string += fee + tab + "masterDetailViewMenuOptionEnabled" + faa + _masterDetailViewMenuOptionEnabled + foo;
                } else {
                    string += fee + tab + "abstract" + faa + _annotatedWithAbstractClass + foo;
                    string += fee + tab + "baseClass" + faa + _baseClass + foo;
                    string += fee + tab + "subclassesList" + faa + getMainInstanceSubclassesList() + foo;
                    string += fee + tab + "primaryKeyProperty" + faa + _primaryKeyProperty + foo;
                    string += fee + tab + "sequenceProperty" + faa + _sequenceProperty + foo;
                    string += fee + tab + "versionProperty" + faa + _versionProperty + foo;
                    string += fee + tab + "numericKeyProperty" + faa + _numericKeyProperty + foo;
                    string += fee + tab + "characterKeyProperty" + faa + _characterKeyProperty + foo;
                    string += fee + tab + "nameProperty" + faa + _nameProperty + foo;
                    string += fee + tab + "descriptionProperty" + faa + _descriptionProperty + foo;
                    string += fee + tab + "imageProperty" + faa + _imageProperty + foo;
                    string += fee + tab + "inactiveIndicatorProperty" + faa + _inactiveIndicatorProperty + foo;
                    string += fee + tab + "urlProperty" + faa + _urlProperty + foo;
                    string += fee + tab + "parentProperty" + faa + _parentProperty + foo;
                    string += fee + tab + "ownerProperty" + faa + _ownerProperty + foo;
                    string += fee + tab + "userProperty" + faa + _userProperty + foo;
                    string += fee + tab + "segmentProperty" + faa + _segmentProperty + foo;
                    string += fee + tab + "businessKeyProperty" + faa + _businessKeyProperty + foo;
                    string += fee + tab + "businessKeyType" + faa + _businessKeyType + foo;
                    string += fee + tab + "existentiallyIndependent" + faa + _existentiallyIndependent + foo;
                    string += fee + tab + "viewType" + faa + _entityViewType + foo;
                    string += fee + tab + "resourceType" + faa + _resourceType + foo;
                    string += fee + tab + "resourceGender" + faa + _resourceGender + foo;
//                  string += fee + tab + "propertiesPrefix" + faa + _propertiesPrefix + foo;
//                  string += fee + tab + "propertiesSuffix" + faa + _propertiesSuffix + foo;
                    string += fee + tab + "selectEnabled" + faa + _selectEnabled + foo;
                    string += fee + tab + "selectRowsLimit" + faa + _selectRowsLimit + foo;
                    string += fee + tab + "selectSortOption" + faa + _selectSortOption + foo;
                    string += fee + tab + "insertEnabled" + faa + _insertEnabled + foo;
                    string += fee + tab + "updateEnabled" + faa + _updateEnabled + foo;
                    string += fee + tab + "deleteEnabled" + faa + _deleteEnabled + foo;
                    string += fee + tab + "reportEnabled" + faa + _reportEnabled + foo;
                    string += fee + tab + "reportRowsLimit" + faa + _reportRowsLimit + foo;
                    string += fee + tab + "reportSortOption" + faa + _reportSortOption + foo;
                    string += fee + tab + "exportEnabled" + faa + _exportEnabled + foo;
                    string += fee + tab + "exportRowsLimit" + faa + _exportRowsLimit + foo;
                    string += fee + tab + "exportSortOption" + faa + _exportSortOption + foo;
                    string += fee + tab + "tableViewEnabled" + faa + _tableViewEnabled + foo;
                    string += fee + tab + "detailViewEnabled" + faa + _detailViewEnabled + foo;
                    string += fee + tab + "treeViewEnabled" + faa + _treeViewEnabled + foo;
                    string += fee + tab + "consoleViewEnabled" + faa + _consoleViewEnabled + foo;
                    string += fee + tab + "warningsEnabled" + faa + _warningsEnabled + foo;
                }
                string += fee + tab + "searchType" + faa + _searchType + foo;
                string += fee + tab + "listStyle" + faa + _listStyle + foo;
                string += fee + tab + "radioColumns" + faa + _radioColumns + foo;
                string += fee + tab + "descendingOrder" + faa + _descendingOrder + foo;
                string += fee + tab + "searchDisplayFormat" + faa + _searchDisplayFormat + foo;
                string += fee + tab + "searchDisplayMode" + faa + _searchDisplayMode + foo;
            }
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        string += _atlas.mapsToString(n, key, verbose, fields, maps, depth() == 0);
        return string;
    }

    @Override
    protected String getValueString(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Instance instance) {
            return getValueString(instance.getDeclaringArtifact(), instance.getName());
        } else {
            return super.getValueString(value);
        }
    }
    // </editor-fold>

    private Field getPrimaryKeyField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            LongProperty.class,
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.PRIMARY_KEY, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.PRIMARY_KEY, name, type); // since 20201214
    }

    private Field getSequenceField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            LongProperty.class,
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.SEQUENCE, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.SEQUENCE, name, type); // since 20201214
    }

    private Field getVersionField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            LongProperty.class
        };
        return getKeyPropertyField(KeyProperty.VERSION, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.VERSION, name, type); // since 20201214
    }

    /*
    private Field getNumericKeyField(String name, Class<?> type) {
        //
        Class<?>[] validTypes = new Class<?>[]{
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.NUMERIC_KEY, name, type, validTypes);
        //
        return getKeyPropertyField(KeyProperty.NUMERIC_KEY, name, type); // since 20201214
    }

    /*
    private Field getCharacterKeyField(String name, Class<?> type) {
        //
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.CHARACTER_KEY, name, type, validTypes);
        //
        return getKeyPropertyField(KeyProperty.CHARACTER_KEY, name, type); // since 20201214
    }

    /**/
    private Field getNameField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.NAME, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.NAME, name, type); // since 20201214
    }

    private Field getDescriptionField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.DESCRIPTION, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.DESCRIPTION, name, type); // since 20201214
    }

    private Field getImageField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            BinaryProperty.class
        };
        return getKeyPropertyField(KeyProperty.IMAGE, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.IMAGE, name, type); // since 20201214
    }

    private Field getInactiveIndicatorField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            BooleanProperty.class,
            IntegerProperty.class
        };
        return getKeyPropertyField(KeyProperty.INACTIVE_INDICATOR, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.INACTIVE_INDICATOR, name, type); // since 20201214
    }

    private Field getUrlField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.URL, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.URL, name, type); // since 20201214
    }

    private Field getParentField(String name, Class<?> type) {
        /*
        keyPropertyValidTypes.put(KeyProperty.PARENT,
            new Class<?>[]{EntityReference.class});
        /**/
        Class<?>[] validTypes = new Class<?>[]{
            type // 20201214 - es preferible type que EntityReference.class porque es mas específico
        };
        return getKeyPropertyField(KeyProperty.PARENT, name, type, validTypes);
    }

    private Field getOwnerField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.OWNER, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.OWNER, name, type); // since 20201214
    }

    private Field getUserField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.USER, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.USER, name, type); // since 20201214
    }

    private Field getSegmentField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.SEGMENT, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.SEGMENT, name, type); // since 20201214
    }

    private Field getUniqueKeyField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            BooleanPrimitive.class,
            CharacterPrimitive.class,
            NumericPrimitive.class,
            TemporalPrimitive.class,
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.UNIQUE_KEY, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.UNIQUE_KEY, name, type); // since 20201214
    }

    private Field getBusinessKeyField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            IntegerProperty.class,
            StringProperty.class
        };
        return getKeyPropertyField(KeyProperty.BUSINESS_KEY, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.BUSINESS_KEY, name, type); // since 20201214
    }

    private Field getStateField(String name, Class<?> type) {
        /*
        Class<?>[] validTypes = new Class<?>[]{
            EntityReference.class
        };
        return getKeyPropertyField(KeyProperty.STATE, name, type, validTypes);
        /**/
        return getKeyPropertyField(KeyProperty.STATE, name, type); // since 20201214
    }

    Field getKeyPropertyField(KeyProperty keyProperty, String name, Class<?> type) {
        Class<?>[] validTypes = XS1.keyPropertyValidTypes.get(keyProperty);
        return getKeyPropertyField(keyProperty, name, type, validTypes);
    }

    Field getKeyPropertyField(KeyProperty keyProperty, String name, Class<?> type, Class<?>... validTypes) {
        String role = keyProperty.name().replace('_', ' ');
        return XS1.getField(depth() == 0, role, name, type, Entity.class, validTypes);
    }

    private void track(String method) {
        track(method, this);
    }

    private void track(String method, Object... parameters) {
        Project project = TLC.getProject();
        if (project != null) {
            project.getParser().track(depth(), round(), getClassPath(), method, parameters);
        }
    }

}
