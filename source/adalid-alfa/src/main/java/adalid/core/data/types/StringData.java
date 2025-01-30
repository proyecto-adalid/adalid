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
package adalid.core.data.types;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.primitives.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class StringData extends CharacterPrimitive {

    private static final String EOL = "\n";

    private static final String MIDDOT = " \u00b7 "; // an interpunct, also known as an interpoint, middle dot, middot, centered dot and centred dot

    public static final String EMPTY = "";

    {
        XS2.setDataClass(this, StringData.class);
        XS2.setDataType(this, String.class);
    }

    private int _minLength = 0;

    private Integer _maxLength;

    private Integer _displayLength;

    private String _inputMask;

    private char _slotChar = '_';

    private Pattern _pattern;

    private final Map<Locale, String> _localizedRegexErrorMessage = new LinkedHashMap<>();

    private AutoComplete _autoComplete = AutoComplete.UNSPECIFIED;

    private LetterCase _letterCase = LetterCase.UNSPECIFIED;

    private boolean _allowDiacritics = true;

    private boolean _richTextFormat = false;

    private boolean _translatable = true;

    private String _specialConverterName;

    private String _specialValidatorName;

    private CharacterExpression _typeNameExpression;

    private UrlType _urlType = UrlType.UNSPECIFIED;

    private DisplayMode _urlDisplayMode = DisplayMode.UNSPECIFIED;

    private UrlDisplayType _urlDisplayType = UrlDisplayType.UNSPECIFIED;

    private String[] _sourceURLs;

    private String _searchURL;

    private String _fileDownloadStartFunction, _fileDownloadStopFunction;

    private String[] _fileViewerDialogReturnUpdate;

    private Boolean _fileUploadAutoStart;

    private Boolean _fileUploadVirusScan;

    private int _fileUploadFileLimit = 1;

    private int _fileUploadUndoLimit = 2;

    private int _maxInputFileSize = -1; // Constants.DEFAULT_MAX_INPUT_FILE_SIZE;

    private MimeType[] _validInputFileTypes = new MimeType[]{};

    private Pattern _validInputFilePattern;

    private UploadStorageOption _uploadStorageOption = UploadStorageOption.UNSPECIFIED;

    private String _pathTemplate;

    private String _blobFieldName;

    private String _joinFieldName;

    private String _loadFieldName;

    private String _textFieldName;

    private Field _blobField;

    private Field _joinField;

    private Field _loadField;

    private Field _textField;

    private Entity _blobEntity;

    private Entity _joinEntity;

    private Entity _loadEntity;

    private Entity _textEntity;

    private Property _blobProperty;

    private Property _joinProperty;

    private Property _loadProperty;

    private Property _textProperty;

    private Kleenean _updateableFileReference = Kleenean.UNSPECIFIED; // unused variable

    private EmbeddedDocumentType _embeddedDocumentType = EmbeddedDocumentType.UNSPECIFIED;

    private EmbeddedDocumentStyle _embeddedDocumentStyle = EmbeddedDocumentStyle.UNSPECIFIED;

    // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
    /*
    private int _displayWidth = -1; // Constants.DEFAULT_DOCUMENT_WIDTH;

    private int _displayHeight = -1; // Constants.DEFAULT_DOCUMENT_HEIGHT;
    /**/
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="since 06/06/2022">
    private int _largeDisplayWidth = Constants.DEFAULT_LARGE_DOCUMENT_WIDTH;

    private int _largeDisplayHeight = Constants.DEFAULT_LARGE_DOCUMENT_HEIGHT;

    private int _mediumDisplayWidth = Constants.DEFAULT_MEDIUM_DOCUMENT_WIDTH;

    private int _mediumDisplayHeight = Constants.DEFAULT_MEDIUM_DOCUMENT_HEIGHT;

    private int _smallDisplayWidth = Constants.DEFAULT_SMALL_DOCUMENT_WIDTH;

    private int _smallDisplayHeight = Constants.DEFAULT_SMALL_DOCUMENT_HEIGHT;
    // </editor-fold>

    private boolean _resizable = true;

    private Boolean _frameBorder;

    private Boolean _encryptedMedia;

    private Boolean _accelerometer;

    private Boolean _autoplay;

    private Boolean _gyroscope;

    private Boolean _pictureInPicture;

    private Boolean _fullScreen;

    private EmbeddedDocumentLoading _loading = EmbeddedDocumentLoading.UNSPECIFIED;

    private EmbeddedDocumentPolicy _referrerPolicy = EmbeddedDocumentPolicy.UNSPECIFIED;

    private EmbeddedDocumentSandbox _sandbox = EmbeddedDocumentSandbox.UNSPECIFIED;

    private boolean _encodingEnabled = false;

    private EncodingType _encodingType = EncodingType.UNSPECIFIED;

    @Override
    public String getDataGenFunction() {
        String dataGenFunction = super.getDataGenFunction();
        return dataGenFunction == null ? implicitDataGenFunction() : dataGenFunction;
    }

    private String implicitDataGenFunction() {
        Object defaultValue = getDefaultValue();
        return defaultValue == null ? null : implicitDataGenFunction(defaultValue);
    }

    private String implicitDataGenFunction(Object defaultValue) {
        final String string_codigo_usuario = "util.string_codigo_usuario";
        if (SpecialCharacterValue.CURRENT_USER_CODE.equals(defaultValue)) {
            return string_codigo_usuario;
        }
        if (defaultValue instanceof ScalarX scalarX) {
            if (scalarX.getOperator() == null && SpecialCharacterValue.CURRENT_USER_CODE.equals(scalarX.getOperand())) {
                return string_codigo_usuario;
            }
        }
        return null;
    }

    @Override
    public boolean isSearchField() {
        return !isEncodingEnabled() && super.isSearchField();
    }

    @Override
    public boolean isFilterField() {
        return !isEncodingEnabled() && super.isFilterField();
    }

    @Override
    public boolean isSortField() {
        return !isEncodingEnabled() && super.isSortField();
    }

    @Override
    public boolean isReportField() {
        return !isEncodingEnabled() && super.isReportField();
    }

    @Override
    public boolean isExportField() {
        return !isEncodingEnabled() && super.isExportField();
    }

    @Override
    public Boolean isLoremIpsum() {
        Boolean loremIpsum = super.isLoremIpsum();
        return loremIpsum == null ? implicitLoremIpsum() : loremIpsum;
    }

    private boolean implicitLoremIpsum() {
        return !(isDataGenTypeSpecified()
            || IntUtils.valueOf(getMaxLength(), 30) < 30
            || getDefaultValue() != null
            || isUnique()
            || isEmbeddedDocumentField()
            || isFileReferenceField()
            || isNameProperty()
            || isPassword()
            || isUniformResourceLocatorField()
            || isUrlProperty()
            || isVariantStringField());
    }

    public boolean isResizableString() {
        return !(isUnique()
            || isEmbeddedDocumentField()
            || isFileReferenceField()
            || isPassword()
            || isUniformResourceLocatorField()
            || isUrlProperty()
            || isVariantStringField());
    }

    /**
     * @return the minLength
     */
    public int getMinLength() {
        return _minLength;
    }

    /**
     * Sets the string minimum length. WARNING: max length should be set BEFORE min length.
     *
     * @param minLength the minLength to set
     */
    public void setMinLength(int minLength) {
        checkScope();
        _minLength = minLength;
    }

    /**
     * @return the maxLength
     */
    public Integer getMaxLength() {
        return _richTextFormat ? null : _maxLength;
    }

    /**
     * Sets the string maximum length. WARNING: max length should be set BEFORE min length.
     *
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(Integer maxLength) {
        checkScope();
        _maxLength = maxLength;
    }

    public boolean isLargeObject() {
        return getMaxLength() == null;
    }

    // <editor-fold defaultstate="collapsed" desc="since 25/06/2024">
    private FetchType _fetchType = FetchType.EAGER;

    public FetchType getFetchType() {
        return _fetchType;
    }

    /**
     * El método setFetchType se utiliza para establecer la estrategia para obtener datos de la base de datos. Este método solo aplica si la meta
     * propiedad tiene longitud ilimitada, es decir, su maxLength es 0. Con la estrategia EAGER, el valor de la propiedad se obtiene simultáneamente
     * con el resto de las propiedades de la entidad. Con la estrategia LAZY, el valor se obtiene posteriormente, por demanda, cuando se accede a la
     * propiedad por primera vez. El valor predeterminado del atributo es EAGER.
     *
     * @param fetchType especifica si la operación de consulta obtiene el valor de la propiedad simultáneamente con el resto de las propiedades de la
     * entidad o posteriormente, por demanda. Su valor es uno de los elementos de la enumeración FetchType. Seleccione EAGER para obtener el valor de
     * la propiedad simultáneamente con el resto de las propiedades de la entidad; seleccione LAZY para obtenerlo posteriormente, por demanda;
     * alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es EAGER.
     */
    public void setFetchType(FetchType fetchType) {
        _fetchType = fetchType;
    }
    // </editor-fold>

    /**
     * @return the displayLength
     */
    public Integer getDisplayLength() {
        return _richTextFormat ? null : _displayLength;
    }

    /**
     * @return the displayLength
     */
    public Integer getMaxDisplayLength() {
        return _richTextFormat ? null : _displayLength == null ? _maxLength : _displayLength;
    }

    /**
     * Sets the string display length.
     *
     * @param displayLength the displayLength to set
     */
    public void setDisplayLength(Integer displayLength) {
        checkScope();
        _displayLength = displayLength;
    }

    /**
     * @return the input mask
     */
    public String getInputMask() {
        return _inputMask;
    }

    /**
     * @param inputMask the input mask to set
     */
    public void setInputMask(String inputMask) {
        checkScope();
        _inputMask = inputMask;
    }

    /**
     * @return the slot char
     */
    public char getSlotChar() {
        return _slotChar;
    }

    /**
     * @param slotChar the slot char to set
     */
    public void setSlotChar(char slotChar) {
        checkScope();
        _slotChar = slotChar;
    }

    /**
     * @return the pattern
     */
    public Pattern getPattern() {
        return _pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(Pattern pattern) {
        checkScope();
        _pattern = pattern;
    }

    public String getPatternRegex() {
        return _pattern == null ? null : _pattern.pattern();
    }

    /**
     * @return the default regex error message
     */
    public String getDefaultRegexErrorMessage() {
        return getLocalizedRegexErrorMessage(null);
    }

    /**
     * El método setDefaultRegexErrorMessage se utiliza para establecer el mensaje de error asociado a la expresión regular definida mediante el
     * elemento regex de la anotación StringField, que se almacena en el archivo de recursos por defecto. En caso de que el archivo de recursos para
     * el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener
     * el valor del mensaje.
     *
     * @param message mensaje de error asociado a la expresión regular
     */
    public void setDefaultRegexErrorMessage(String message) {
        setLocalizedRegexErrorMessage(null, message);
    }

    /**
     * @param locale the locale for the regex error message
     * @return the localized regex error message
     */
    public String getLocalizedRegexErrorMessage(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedRegexErrorMessage.get(l);
    }

    /**
     * El método setLocalizedRegexErrorMessage se utiliza para establecer el mensaje de error asociado a la expresión regular definida mediante el
     * elemento regex de la anotación StringField, que se almacena en el archivo de recursos de configuración regional. En caso de que el archivo de
     * recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto
     * para obtener el valor del mensaje.
     *
     * @param locale configuración regional
     * @param message mensaje de error asociado a la expresión regular
     */
    public void setLocalizedRegexErrorMessage(Locale locale, String message) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (message == null) {
            _localizedRegexErrorMessage.remove(l);
        } else {
            _localizedRegexErrorMessage.put(l, message);
        }
    }

    /**
     * @return the auto complete
     */
    public AutoComplete getAutoComplete() {
        return _autoComplete;
    }

    /**
     * @param autoComplete the auto complete to set
     */
    public void setAutoComplete(AutoComplete autoComplete) {
        checkScope();
        _autoComplete = autoComplete == null ? AutoComplete.UNSPECIFIED : autoComplete;
    }

    /**
     * @return the letter case
     */
    public LetterCase getLetterCase() {
        return _letterCase;
    }

    /**
     * @param letterCase the letter case to set
     */
    public void setLetterCase(LetterCase letterCase) {
        checkScope();
        _letterCase = letterCase == null ? LetterCase.UNSPECIFIED : letterCase;
    }

    /**
     * @return the allow diacritics indicator
     */
    public boolean isAllowDiacritics() {
        return _allowDiacritics;
    }

    /**
     * @param allowDiacritics the allow diacritics indicator to set
     */
    public void setAllowDiacritics(boolean allowDiacritics) {
        checkScope();
        _allowDiacritics = allowDiacritics;
    }

    /**
     * @return the rich text format indicator
     */
    public boolean isRichTextFormat() {
        return _richTextFormat;
    }

    /**
     * @param richTextFormat the rich text format indicator to set
     */
    public void setRichTextFormat(boolean richTextFormat) {
        checkScope();
        _richTextFormat = richTextFormat;
    }

    /**
     * @return the allow translatation indicator
     */
    public boolean isTranslatable() {
        return _translatable && isTranslationAllowed();
    }

    public boolean isTranslationAllowed() {
        return !(isLargeObject()
            || isEmbeddedDocumentField()
            || isFileReferenceField()
            || isPassword()
            || isUniformResourceLocatorField()
            || isUrlProperty()
            || isVariantStringField());
    }

    /**
     * @param translatable the allow translatation indicator to set
     */
    public void setTranslatable(boolean translatable) {
        checkScope();
        _translatable = translatable;
    }

    /**
     * @return the special converter name
     */
    public String getSpecialConverterName() {
        return _specialConverterName;
    }

    public void setSpecialConverterName(String converter) {
        checkScope();
        _specialConverterName = converter;
    }

    /**
     * @return the special validator name
     */
    public String getSpecialValidatorName() {
        return _specialValidatorName;
    }

    public void setSpecialValidatorName(String validator) {
        checkScope();
        _specialValidatorName = validator;
    }

    /**
     * @return the type name expression
     */
    public CharacterExpression getTypeNameExpression() {
        return _typeNameExpression;
    }

    /**
     * @param expression the type name expression to set
     */
    public void setTypeNameExpression(CharacterExpression expression) {
        checkScope();
        _typeNameExpression = expression;
    }

    /**
     * @return the url type
     */
//  @Override -- Implements method from: URL (StringProperty)
    public UrlType getUrlType() {
        return _urlType;
    }

    /**
     * @param type the url type to set
     */
    public void setUrlType(UrlType type) {
        checkScope();
        _urlType = type == null ? UrlType.UNSPECIFIED : type;
    }

    /**
     * @return the url display mode
     */
//  @Override -- Implements method from: URL (StringProperty)
    public DisplayMode getUrlDisplayMode() {
        return _urlDisplayMode;
    }

    /**
     * @param mode the url display mode to set
     */
    public void setUrlDisplayMode(DisplayMode mode) {
        checkScope();
        _urlDisplayMode = mode == null ? DisplayMode.UNSPECIFIED : mode;
    }

    /**
     * @return the url display type
     */
//  @Override -- Implements method from: URL (StringProperty)
    public UrlDisplayType getUrlDisplayType() {
        return _urlDisplayType;
    }

    /**
     * @param type the url display type to set
     */
    public void setUrlDisplayType(UrlDisplayType type) {
        checkScope();
        _urlDisplayType = type == null ? UrlDisplayType.UNSPECIFIED : type;
    }

    public String getSourceURLsJoint() {
        return getEmbeddedDocumentURLsJoint();
    }

    /**
     * @return the list of valid URLs
     */
    public String[] getSourceURLs() {
        return _sourceURLs;
    }

    /**
     * @param urls the list of valid URLs to set
     */
    public void setSourceURLs(String... urls) {
        checkScope();
        _sourceURLs = urls;
    }

    private boolean sourceURLsEquals(String string) {
        return _sourceURLs != null && _sourceURLs.length == 1 && _sourceURLs[0].equals(string);
    }

    /**
     * @return the URL of the website to search for the value
     */
    public String getSearchURL() {
        return _searchURL;
    }

    /**
     * @param searchURL the URL to set
     */
    public void setSearchURL(String searchURL) {
        checkScope();
        _searchURL = searchURL;
    }

    /**
     * @return the inline frame indicator of embedded maps
     */
    public boolean isGoogleMapsEmbedInlineFrame() {
        return EmbeddedDocumentType.IFRAME.equals(_embeddedDocumentType)
            // TrustedSites.GOOGLE_MAPS.equals(_searchURL)
            && sourceURLsEquals(TrustedSites.EMBED_MAPS);
    }

    /**
     * @return the encoding-enabled indicator
     */
    public boolean isEncodingEnabled() {
        return _encodingEnabled;
    }

    /**
     * El método setEncodingEnabled se utiliza para habilitar la codificación del valor de la propiedad para almacenarlo en la base de datos.
     * <p>
     * <b>Advertencias</b>
     * </p>
     * <ul>
     * <li>La codificación limita significativamente el uso de la propiedad en consultas e informes.</li>
     * <li>El algoritmo de codificación utilizado es Base64, el cual no es un algoritmo de cifrado, se decodifica fácilmente y, por lo tanto, no debe
     * utilizarse como un método de cifrado seguro.</li>
     * <li>La longitud del valor codificado es un tercio mayor que la longitud del valor original; si, por ejemplo, la longitud máxima de la propiedad
     * se establece en 2000, entonces la propiedad solo puede contener valores de hasta 1500 caracteres.</li>
     * </ul>
     *
     * @param encoding true para habilitar la codificación
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html">Class Base64</a>
     */
    public void setEncodingEnabled(boolean encoding) {
        checkScope();
        _encodingEnabled = encoding;
    }

    /**
     * @return the encoding type
     */
    public EncodingType getEncodingType() {
        return !_encodingEnabled ? EncodingType.UNSPECIFIED
            : isEmbeddedDocumentField() ? EncodingType.BASIC
                : isFileReferenceField() ? EncodingType.FILENAME
                    : isUniformResourceLocatorField() || isUrlProperty() ? EncodingType.URL
                        : _encodingType;
    }

    protected EncodingType encodingType() {
        return _encodingType;
    }

    /**
     * El método setEncodingType se utiliza para establecer el tipo de codificación del valor de la propiedad para almacenarlo en la base de datos. El
     * algoritmo de codificación utilizado es Base64. Su valor es uno de los elementos de la enumeración EncodingType.
     * <p>
     * Seleccione:
     * </p>
     * <ul>
     * <li>BASIC, para utilizar el "Alfabeto Base64" como se especifica en la Tabla 1 de RFC 4648 y RFC 2045 para la operación de codificación y
     * decodificación. El codificador no agrega ningún carácter de avance de línea (separador de línea). El decodificador rechaza los datos que
     * contienen caracteres fuera del alfabeto base64.</li>
     * <li>FILENAME o URL, para utilizar el "Alfabeto Base64 seguro para URL y nombre de archivo" como se especifica en la Tabla 2 de RFC 4648 para la
     * codificación y decodificación. El codificador no agrega ningún carácter de avance de línea (separador de línea). El decodificador rechaza los
     * datos que contienen caracteres fuera del alfabeto base64.</li>
     * <li>MIME, para utilizar el "Alfabeto Base64" como se especifica en la Tabla 1 de RFC 2045 para la operación de codificación y decodificación.
     * La salida codificada debe representarse en líneas de no más de 76 caracteres cada una y utiliza un retorno de carro '\r' seguido inmediatamente
     * por un salto de línea '\n' como separador de línea. No se agrega ningún separador de línea al final de la salida codificada. Todos los
     * separadores de línea u otros caracteres que no se encuentran en la tabla alfabética base64 se ignoran en la operación de decodificación.</li>
     * </ul><p>
     * Alternativamente, seleccione UNSPECIFIED para utilizar el valor predeterminado. El valor predeteminado es:
     * <ul>
     * <li>BASIC, para propiedades que contienen una definición de documento incrustado (anotadas con @EmbeddedDocument)</li>
     * <li>FILENAME, para propiedades que contienen una referencias a un archivo cargado en el servidor (anotadas con @FileReference)</li>
     * <li>URL, para propiedades que contienen una URL (anotadas con @UniformResourceLocator o @UrlProperty)</li>
     * <li>UNSPECIFIED, para las demás propiedades</li>
     * </ul>
     *
     * @param encoding tipo de codificación
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html">Class Base64</a>
     */
    public void setEncodingType(EncodingType encoding) {
        checkScope();
        _encodingType = encoding == null ? EncodingType.UNSPECIFIED : encoding;
    }

    public String getFileDownloadStartFunction() {
        return _fileDownloadStartFunction;
    }

    public void setFileDownloadStartFunction(String function) {
        checkScope();
        _fileDownloadStartFunction = function;
    }

    public String getFileDownloadStopFunction() {
        return _fileDownloadStopFunction;
    }

    public void setFileDownloadStopFunction(String function) {
        checkScope();
        _fileDownloadStopFunction = function;
    }

    public String getFileViewerDialogReturnUpdate() {
        return _fileViewerDialogReturnUpdate == null ? null : StringUtils.join(_fileViewerDialogReturnUpdate, ' ');
    }

    public void setFileViewerDialogReturnUpdate(String... update) {
        checkScope();
        _fileViewerDialogReturnUpdate = update == null || update.length == 0 ? null : update;
    }

    /**
     * @return the file-upload auto-start indicator
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public boolean isFileUploadAutoStart() {
        return _fileUploadAutoStart != null && _fileUploadAutoStart;
    }

    public Boolean fileUploadAutoStart() {
        return _fileUploadAutoStart;
    }

    /**
     * @param autoStart the file-upload auto-start indicator to set
     */
    public void setFileUploadAutoStart(boolean autoStart) {
        checkScope();
        _fileUploadAutoStart = autoStart;
    }

    /**
     * @return the file-upload virus-scan indicator
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public boolean isFileUploadVirusScan() {
        return _fileUploadVirusScan != null && _fileUploadVirusScan;
    }

    public Boolean fileUploadVirusScan() {
        return _fileUploadVirusScan;
    }

    /**
     * @param virusScan the file-upload virus-scan indicator to set
     */
    public void setFileUploadVirusScan(boolean virusScan) {
        checkScope();
        _fileUploadVirusScan = virusScan;
    }

    /**
     * @return the file-upload file limit
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public int getFileUploadFileLimit() {
        return _fileUploadFileLimit < 1 || _fileUploadFileLimit > Constants.MAX_UPLOAD_FILE_LIMIT ? 1 : _fileUploadFileLimit;
    }

    protected int fileUploadFileLimit() {
        return _fileUploadFileLimit;
    }

    /**
     * @param fileLimit the file-upload file limit indicator to set
     */
    public void setFileUploadFileLimit(int fileLimit) {
        checkScope();
        _fileUploadFileLimit = fileLimit;
    }

    /**
     * @return the file-upload file limit
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public int getFileUploadUndoLimit() {
        return _fileUploadUndoLimit < 0 || _fileUploadUndoLimit > Constants.MAX_UPLOAD_UNDO_LIMIT ? 2 : _fileUploadUndoLimit;
    }

    protected int fileUploadUndoLimit() {
        return _fileUploadUndoLimit;
    }

    /**
     * @param undoLimit the file-upload retry limit indicator to set
     */
    public void setFileUploadUndoLimit(int undoLimit) {
        checkScope();
        _fileUploadUndoLimit = undoLimit;
    }

    /**
     * @return the max input file size
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public int getMaxInputFileSize() {
        return _maxInputFileSize < 0 ? Constants.DEFAULT_MAX_INPUT_FILE_SIZE : _maxInputFileSize;
    }

    protected int maxInputFileSize() {
        return _maxInputFileSize;
    }

    /**
     * @param size the max input file size to set
     */
    public void setMaxInputFileSize(int size) {
        checkScope();
        _maxInputFileSize = size;
    }

    /**
     * @return the valid input file types
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public MimeType[] getValidInputFileTypes() {
        return _validInputFileTypes;
    }

    /**
     * @param types the valid input file types to set
     */
    public void setValidInputFileTypes(MimeType[] types) {
        checkScope();
        _validInputFileTypes = types == null ? new MimeType[]{} : types;
    }

    private final static String MIME_TYPES_REGEX_PREFIX = "^(.*)(\\.)";

    private final static String MIME_TYPES_REGEX_SUFFIX = "$";

    /**
     * @return the valid input file regex
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public String getValidInputFileTypesRegex() {
        MimeType[] validInputFileTypes = getValidInputFileTypes();
        if (validInputFileTypes == null || validInputFileTypes.length == 0) {
            return null;
        }
        String[] strings = new String[validInputFileTypes.length];
        for (int i = 0; i < validInputFileTypes.length; i++) {
            strings[i] = validInputFileTypes[i].getRegexExtensions();
        }
        String join = StringUtils.join(strings, "|");
        return MIME_TYPES_REGEX_PREFIX + "(" + join + ")" + MIME_TYPES_REGEX_SUFFIX;
    }

    /**
     * @return the valid input file pattern
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Pattern getValidInputFilePattern() {
        return _validInputFilePattern;
    }

    /**
     * @param pattern the valid input file pattern to set
     */
    public void setValidInputFilePattern(Pattern pattern) {
        checkScope();
        _validInputFilePattern = pattern;
    }

    public String getValidInputFilePatternRegex() {
        return _validInputFilePattern == null ? null : _validInputFilePattern.pattern();
    }

    /**
     * @return the upload storage option
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public UploadStorageOption getUploadStorageOption() {
        return _uploadStorageOption;
    }

    /**
     * @param option the upload storage option to set
     */
    public void setUploadStorageOption(UploadStorageOption option) {
        checkScope();
        _uploadStorageOption = option == null ? UploadStorageOption.UNSPECIFIED : option;
    }

    /**
     * @return the path template
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public String getPathTemplate() {
        return _pathTemplate;
    }

    /**
     * @param fieldName the path template to set
     */
    public void setPathTemplate(String fieldName) {
        checkScope();
        _pathTemplate = fieldName;
    }

    /**
     * @return the blob field name
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public String getBlobFieldName() {
        return _blobFieldName;
    }

    /**
     * @param fieldName the blob field name to set
     */
    public void setBlobFieldName(String fieldName) {
        checkScope();
        _blobFieldName = fieldName;
    }

    /**
     * @return the blob field
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Field getBlobField() {
        return _blobField;
    }

    /**
     * @param field the blob field to set
     *
     * @return the previously set blob field
     */
    public Field setBlobField(Field field) {
        checkScope();
        Field previous = _blobField;
        _blobField = field;
        return previous;
    }

    /**
     * @param entity the blob entity to set
     */
    public void setBlobEntity(Entity entity) {
        checkScope();
        _blobEntity = entity;
    }

    /**
     * @return the blob property
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Property getBlobProperty() {
        if (_blobProperty == null && _blobField != null && _blobEntity != null) {
            _blobProperty = XS2.getProperty(_blobField, _blobEntity, true);
        }
        return _blobProperty;
    }

    /**
     * @param property the blob property to set
     */
    public void setBlobProperty(Property property) {
        checkScope();
        _blobProperty = property;
    }

    /**
     * @return the join field name
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public String getJoinFieldName() {
        return _joinFieldName;
    }

    /**
     * @param fieldName the join field name to set
     */
    public void setJoinFieldName(String fieldName) {
        checkScope();
        _joinFieldName = fieldName;
    }

    /**
     * @return the join field
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Field getJoinField() {
        return _joinField;
    }

    /**
     * @param field the join field to set
     *
     * @return the previously set join field
     */
    public Field setJoinField(Field field) {
        checkScope();
        Field previous = _joinField;
        _joinField = field;
        return previous;
    }

    /**
     * @param entity the join entity to set
     */
    public void setJoinEntity(Entity entity) {
        checkScope();
        _joinEntity = entity;
    }

    /**
     * @return the join property
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Property getJoinProperty() {
        if (_joinProperty == null && _joinField != null && _joinEntity != null) {
            _joinProperty = XS2.getProperty(_joinField, _joinEntity, true);
        }
        return _joinProperty;
    }

    /**
     * @param property the join property to set
     */
    public void setJoinProperty(Property property) {
        checkScope();
        _joinProperty = property;
    }

    /**
     * @return the load field name
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public String getLoadFieldName() {
        return _loadFieldName;
    }

    /**
     * @param fieldName the load field name to set
     */
    public void setLoadFieldName(String fieldName) {
        checkScope();
        _loadFieldName = fieldName;
    }

    /**
     * @return the load field
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Field getLoadField() {
        return _loadField;
    }

    /**
     * @param field the load field to set
     *
     * @return the previously set load field
     */
    public Field setLoadField(Field field) {
        checkScope();
        Field previous = _loadField;
        _loadField = field;
        return previous;
    }

    /**
     * @param entity the load entity to set
     */
    public void setLoadEntity(Entity entity) {
        checkScope();
        _loadEntity = entity;
    }

    /**
     * @return the load property
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Property getLoadProperty() {
        if (_loadProperty == null && _loadField != null && _loadEntity != null) {
            _loadProperty = XS2.getProperty(_loadField, _loadEntity, true);
        }
        return _loadProperty;
    }

    /**
     * @param property the load property to set
     */
    public void setLoadProperty(Property property) {
        checkScope();
        _loadProperty = property;
    }

    /**
     * @return the text field name
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public String getTextFieldName() {
        return _textFieldName;
    }

    /**
     * @param fieldName the text field name to set
     */
    public void setTextFieldName(String fieldName) {
        checkScope();
        _textFieldName = fieldName;
    }

    /**
     * @return the text field
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Field getTextField() {
        return _textField;
    }

    /**
     * @param field the text field to set
     *
     * @return the previously set text field
     */
    public Field setTextField(Field field) {
        checkScope();
        Field previous = _textField;
        _textField = field;
        return previous;
    }

    /**
     * @param entity the text entity to set
     */
    public void setTextEntity(Entity entity) {
        checkScope();
        _textEntity = entity;
    }

    /**
     * @return the text property
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Property getTextProperty() {
        if (_textProperty == null && _textField != null && _textEntity != null) {
            _textProperty = XS2.getProperty(_textField, _textEntity, true);
        }
        return _textProperty;
    }

    /**
     * @param property the text property to set
     */
    public void setTextProperty(Property property) {
        checkScope();
        _textProperty = property;
    }

    /**
     * @return the updateable file reference indicator
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public Kleenean getUpdateableFileReference() {
        return _updateableFileReference;
    }

    /**
     * @param updateable the updateable file reference indicator to set
     */
    public void setUpdateableFileReference(Kleenean updateable) {
        checkScope();
        _updateableFileReference = updateable == null ? Kleenean.UNSPECIFIED : updateable;
    }

    /**
     * @return the updateable file reference indicator as boolean
     */
//  @Override -- Implements method from: FileReference (StringProperty/StringParameter)
    public boolean isUpdateableFileReference() {
        Kleenean updateableFileReference = getUpdateableFileReference();
        return updateableFileReference != null && updateableFileReference.toBoolean(false);
    }

    public String getEmbeddedDocumentURLsJoint() {
        return _sourceURLs == null ? "" : StringUtils.join(_sourceURLs, MIDDOT);
    }

    public String[] getEmbeddedDocumentURLs() {
        return _sourceURLs;
    }

    public void setEmbeddedDocumentURLs(String... urls) {
        checkScope();
        _sourceURLs = urls;
        initializeSearchURL();
    }

    private void initializeSearchURL() {
        if (StringUtils.isBlank(_searchURL)) {
            if (_sourceURLs != null && _sourceURLs.length > 0) {
                String sourceURL0 = _sourceURLs[0];
                URL url = XS2.getURL(sourceURL0);
                if (url != null) {
                    String path = url.getPath();
                    if (path.isEmpty()) {
                        _searchURL = sourceURL0;
                    } else if (path.endsWith("/embed")) {
                        _searchURL = StringUtils.removeEnd(sourceURL0, "/embed");
                    } else {
                        _searchURL = StringUtils.substringBefore(sourceURL0, path);
                    }
                }
            }
        }
        if (StringUtils.isBlank(_searchURL)) {
            _searchURL = TrustedSites.GOOGLE;
        }
    }

    public EmbeddedDocumentType getEmbeddedDocumentType() {
        return _embeddedDocumentType;
    }

    public void setEmbeddedDocumentType(EmbeddedDocumentType type) {
        checkScope();
        String regex;
        if (type == null) {
            type = EmbeddedDocumentType.UNSPECIFIED;
        }
        switch (type) {
            case IFRAME -> {
                _embeddedDocumentType = type;
                regex = Constants.IFRAME_REGEX;
            }
            case URL -> {
                _embeddedDocumentType = type;
                regex = Constants.URL_REGEX;
            }
            default -> {
                _embeddedDocumentType = EmbeddedDocumentType.BOTH;
                regex = Constants.EMBEDDED_DOCUMENT_REGEX;
            }
        }
        _pattern = Pattern.compile(regex);
    }

    public EmbeddedDocumentStyle getEmbeddedDocumentStyle() {
        return _embeddedDocumentStyle;
    }

    public void setEmbeddedDocumentStyle(EmbeddedDocumentStyle style) {
        checkScope();
        _embeddedDocumentStyle = style == null ? EmbeddedDocumentStyle.UNSPECIFIED : style;
    }

    // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
    /*
    public int getDisplayWidth() {
        return _displayWidth;
    }

    public void setDisplayWidth(int width) {
        checkScope();
        _displayWidth = width;
    }

    public int getDisplayHeight() {
        return _displayHeight;
    }

    public void setDisplayHeight(int height) {
        checkScope();
        _displayHeight = height;
    }
    /**/
    // </editor-fold>
//
    // <editor-fold defaultstate="collapsed" desc="since 06/06/2022">
    public int getLargeDisplayWidth() {
        return _largeDisplayWidth;
    }

    public void setLargeDisplayWidth(int width) {
        checkScope();
        _largeDisplayWidth = width;
    }

    public int getLargeDisplayHeight() {
        return _largeDisplayHeight;
    }

    public void setLargeDisplayHeight(int height) {
        checkScope();
        _largeDisplayHeight = height;
    }

    public int getMediumDisplayWidth() {
        return _mediumDisplayWidth;
    }

    public void setMediumDisplayWidth(int width) {
        checkScope();
        _mediumDisplayWidth = width;
    }

    public int getMediumDisplayHeight() {
        return _mediumDisplayHeight;
    }

    public void setMediumDisplayHeight(int height) {
        checkScope();
        _mediumDisplayHeight = height;
    }

    public int getSmallDisplayWidth() {
        return _smallDisplayWidth;
    }

    public void setSmallDisplayWidth(int width) {
        checkScope();
        _smallDisplayWidth = width;
    }

    public int getSmallDisplayHeight() {
        return _smallDisplayHeight;
    }

    public void setSmallDisplayHeight(int height) {
        checkScope();
        _smallDisplayHeight = height;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="since 23/01/2024">
    public int[] getDisplayWidth() {
        return new int[]{_largeDisplayWidth, _mediumDisplayWidth, _smallDisplayWidth};
    }

    public int[] getDisplayHeight() {
        return new int[]{_largeDisplayHeight, _mediumDisplayHeight, _smallDisplayHeight};
    }
    // </editor-fold>

    public boolean isResizable() {
        return _resizable;
    }

    public void setResizable(boolean resizable) {
        checkScope();
        _resizable = resizable;
    }

    public Boolean getFrameBorder() {
        return _frameBorder;
    }

    public void setFrameBorder(Boolean frameBorder) {
        checkScope();
        _frameBorder = frameBorder;
    }

    public Boolean getEncryptedMedia() {
        return _encryptedMedia;
    }

    public void setEncryptedMedia(Boolean allow) {
        checkScope();
        _encryptedMedia = allow;
    }

    public Boolean getAccelerometer() {
        return _accelerometer;
    }

    public void setAccelerometer(Boolean allow) {
        checkScope();
        _accelerometer = allow;
    }

    public Boolean getAutoplay() {
        return _autoplay;
    }

    public void setAutoplay(Boolean allow) {
        checkScope();
        _autoplay = allow;
    }

    public Boolean getGyroscope() {
        return _gyroscope;
    }

    public void setGyroscope(Boolean allow) {
        checkScope();
        _gyroscope = allow;
    }

    public Boolean getPictureInPicture() {
        return _pictureInPicture;
    }

    public void setPictureInPicture(Boolean allow) {
        checkScope();
        _pictureInPicture = allow;
    }

    public Boolean getFullScreen() {
        return _fullScreen;
    }

    public void setFullScreen(Boolean allow) {
        checkScope();
        _fullScreen = allow;
    }

    public EmbeddedDocumentLoading getLoading() {
        return _loading;
    }

    public void setLoading(EmbeddedDocumentLoading loading) {
        checkScope();
        _loading = loading == null ? EmbeddedDocumentLoading.UNSPECIFIED : loading;
    }

    public EmbeddedDocumentPolicy getReferrerPolicy() {
        return _referrerPolicy;
    }

    public void setReferrerPolicy(EmbeddedDocumentPolicy referrerPolicy) {
        checkScope();
        _referrerPolicy = referrerPolicy == null ? EmbeddedDocumentPolicy.UNSPECIFIED : referrerPolicy;
    }

    public EmbeddedDocumentSandbox getSandbox() {
        return _sandbox;
    }

    public void setSandbox(EmbeddedDocumentSandbox sandbox) {
        checkScope();
        _sandbox = sandbox == null ? EmbeddedDocumentSandbox.UNSPECIFIED : sandbox;
    }

    public CharacterOrderedPairX toZeroPaddedString() {
        int l = IntUtils.valueOf(getMaxLength(), 0);
        return toZeroPaddedString(l);
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
                string += fee + tab + "minLength" + faa + _minLength + foo;
                if (getMaxLength() != null) {
                    string += fee + tab + "maxLength" + faa + _maxLength + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
