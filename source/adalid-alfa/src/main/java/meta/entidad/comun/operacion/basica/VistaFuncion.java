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
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import adalid.jee2.constants.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.FormatoPaginaInforme;
import meta.entidad.comun.configuracion.basica.ext.Funcion;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, menu = ViewMenuOption.NONE, inserts = Kleenean.FALSE, updates = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE, menu = Kleenean.FALSE)
public class VistaFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public VistaFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY_ON_INSERT, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcion;

//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    public Funcion funcion;

    @OwnerProperty
    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, defaultCondition = DefaultCondition.IF_NULL_ON_INSERT, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public Usuario propietario;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.FALSE)
    public Usuario remitente;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty publica;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty especial;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, heading = Kleenean.TRUE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public FormatoPaginaInforme formato;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public IntegerProperty anchoPagina;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public IntegerProperty largoPagina;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty orientacionHorizontal;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty orientacionVertical;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public IntegerProperty margenSuperior;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public IntegerProperty margenInferior;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public IntegerProperty margenIzquierdo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public IntegerProperty margenDerecho;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(prominent = Kleenean.TRUE)
    public IntegerProperty anchoColumna;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty largoColumna;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, prominent = Kleenean.TRUE)
    public IntegerProperty anchoDetalle;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(prominent = Kleenean.TRUE, table = Kleenean.TRUE)
    public IntegerProperty anchoDisponible;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty valida;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty secuencia;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idVistaFuncionOriginal;

    /**/
    @EntityCollectionField() // set element values with setters so they can be easily overriden
    @OneToMany(targetEntity = VistaFuncionCol.class, mappedBy = "vista") // set other element values with setters so they can be easily overriden
    EntityCollection columnas;

    /**/
    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "funcion.dominio"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        setOrderBy(funcion, publica, especial, nombre);
        /**/
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncion's attributes">
        setLocalizedLabel(ENGLISH, "view");
        setLocalizedLabel(SPANISH, "vista");
        setLocalizedCollectionLabel(ENGLISH, "Views");
        setLocalizedCollectionLabel(SPANISH, "Vistas");
        setLocalizedDescription(ENGLISH, "view to generate files and reports defined by the end user");
        setLocalizedDescription(SPANISH, "vista para generar archivos e informes definida por el usuario final");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        codigo.setDefaultValue(funcion.dominio.codigoDominio.concat("-").concat(id));
        /**/
        publica.setInitialValue(false);
        publica.setDefaultValue(false);
        /**/
        especial.setInitialValue(false);
        especial.setDefaultValue(false);
        /**/
        formato.setInitialValue(formato.PERSONALIZADO);
        formato.setDefaultValue(formato.PERSONALIZADO);
        /**/
        BooleanExpression personalizado = formato.isEqualTo(formato.PERSONALIZADO);
        /**/
        anchoPagina.setInitialValue(formato.anchoPagina);
        anchoPagina.setDefaultValue(formato.anchoPagina);
        anchoPagina.setMinValue(360); // desde Junior Legal (5x8)
        anchoPagina.setMaxValue(1296); // hasta Tabloid Extra (12x18)
        anchoPagina.setPrimalInitialValue(1008);
        anchoPagina.setPrimalDefaultValue(1008);
        /**/
        largoPagina.setInitialValue(formato.largoPagina);
        largoPagina.setDefaultValue(formato.largoPagina);
        largoPagina.setMinValue(360); // desde Junior Legal (5x8)
        largoPagina.setMaxValue(1296); // hasta Tabloid Extra (12x18)
        largoPagina.setPrimalInitialValue(774);
        largoPagina.setPrimalDefaultValue(774);
        /**/
        orientacionHorizontal.setCalculableValueExpression(anchoPagina.isGreaterThan(largoPagina));
        orientacionHorizontal.setInitialValue(personalizado.
            then(anchoPagina.isGreaterThan(largoPagina)).
            otherwise(formato.anchoPagina.isGreaterThan(formato.largoPagina))
        );
        /**/
        orientacionVertical.setCalculableValueExpression(anchoPagina.isLessThan(largoPagina));
        orientacionVertical.setInitialValue(personalizado.
            then(anchoPagina.isLessThan(largoPagina)).
            otherwise(formato.anchoPagina.isLessThan(formato.largoPagina))
        );
        /**/
        margenSuperior.setInitialValue(formato.margenSuperior);
        margenSuperior.setDefaultValue(formato.margenSuperior);
        margenSuperior.setMinValue(18); // desde 0.25"
        margenSuperior.setMaxValue(144); // hasta 2"
        margenSuperior.setPrimalInitialValue(24);
        margenSuperior.setPrimalDefaultValue(24);
        /**/
        margenInferior.setInitialValue(formato.margenInferior);
        margenInferior.setDefaultValue(formato.margenInferior);
        margenInferior.setMinValue(18); // desde 0.25"
        margenInferior.setMaxValue(144); // hasta 2"
        margenInferior.setPrimalInitialValue(24);
        margenInferior.setPrimalDefaultValue(24);
        /**/
        margenIzquierdo.setInitialValue(formato.margenIzquierdo);
        margenIzquierdo.setDefaultValue(formato.margenIzquierdo);
        margenIzquierdo.setMinValue(18); // desde 0.25"
        margenIzquierdo.setMaxValue(144); // hasta 2"
        margenIzquierdo.setPrimalInitialValue(24);
        margenIzquierdo.setPrimalDefaultValue(24);
        /**/
        margenDerecho.setInitialValue(formato.margenDerecho);
        margenDerecho.setDefaultValue(formato.margenDerecho);
        margenDerecho.setMinValue(18); // desde 0.25"
        margenDerecho.setMaxValue(144); // hasta 2"
        margenDerecho.setPrimalInitialValue(24);
        margenDerecho.setPrimalDefaultValue(24);
        /**/
        anchoColumna.setMinValue(324); // 360 - 18 - 18
        anchoColumna.setMaxValue(1260); // 1296 - 18 - 18
        anchoColumna.setCalculableValueExpression(anchoPagina.minus(margenIzquierdo.plus(margenDerecho)));
        anchoColumna.setInitialValue(personalizado.
            then(anchoPagina.minus(margenIzquierdo.plus(margenDerecho))).
            otherwise(formato.anchoPagina.minus(formato.margenIzquierdo.plus(formato.margenDerecho)))
        );
        /**/
        largoColumna.setMinValue(324); // 360 - 18 - 18
        largoColumna.setMaxValue(1260); // 1296 - 18 - 18
        largoColumna.setCalculableValueExpression(largoPagina.minus(margenSuperior.plus(margenInferior)));
        largoColumna.setInitialValue(personalizado.
            then(largoPagina.minus(margenSuperior.plus(margenInferior))).
            otherwise(formato.largoPagina.minus(formato.margenSuperior.plus(formato.margenInferior)))
        );
        /**/
        anchoDetalle.setInitialValue(0);
        anchoDetalle.setDefaultValue(0);
        anchoDetalle.setMinValue(0);
        anchoDetalle.setMaxValue(999999999);
        /**/
        final String imagenAnchoDisponible1 = fa(FA.NULL_VALUE + FA.WITH_FIXED_WIDTH + CSS.STATUS_NULL_VALUE_IMAGE);
        final String imagenAnchoDisponible2 = fa(W3.TEXT_GREEN + FA.CHECK_CIRCLE + FA.WITH_SIZE_LG);
        final String imagenAnchoDisponible3 = fa(W3.TEXT_DEEP_ORANGE + FA.EXCLAMATION_CIRCLE + FA.WITH_SIZE_LG);
        final CharacterExpression imagenAnchoDisponibleX = anchoDisponible.isNull().then(imagenAnchoDisponible1).
            otherwise(anchoDisponible.isGreaterOrEqualTo(0).then(imagenAnchoDisponible2).otherwise(imagenAnchoDisponible3));
        /**/
        anchoDisponible.setMinValue(-999999999); // 1296 - 18 - 18 - anchoDetalle
        anchoDisponible.setMaxValue(1260); // 1296 - 18 - 18 - 0
        anchoDisponible.setGraphicImageFontAwesomeClassNameExpression(imagenAnchoDisponibleX);
        anchoDisponible.setCalculableValueExpression(anchoPagina.minus(margenIzquierdo.plus(margenDerecho).plus(anchoDetalle)));
        anchoDisponible.setInitialValue(personalizado.
            then(anchoPagina.minus(margenIzquierdo.plus(margenDerecho).plus(anchoDetalle))).
            otherwise(formato.anchoPagina.minus(formato.margenIzquierdo.plus(formato.margenDerecho).plus(anchoDetalle)))
        );
        /**/
        valida.setInitialValue(false);
        valida.setDefaultValue(false);
        /**/
        secuencia.setInitialValue(0);
        secuencia.setDefaultValue(0);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncion's properties">
        /**/
        String english, spanish, appendix, apendice;
        /**/
        codigo.setLocalizedLabel(ENGLISH, "view code");
        codigo.setLocalizedLabel(SPANISH, "código de la vista");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        codigo.setLocalizedDefaultValueTag(ENGLISH, b("code") + _of_ + b("domain") + _of_ + b("function") + " + " + b("id"));
        codigo.setLocalizedDefaultValueTag(SPANISH, b("código") + _de_ + b("dominio") + _de_ + b("función") + " + " + b("id"));
        /**/
        nombre.setLocalizedLabel(ENGLISH, "view name");
        nombre.setLocalizedLabel(SPANISH, "nombre de la vista");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "view description");
        descripcion.setLocalizedLabel(SPANISH, "descripción de la vista");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        propietario.setLocalizedLabel(ENGLISH, "view owner");
        propietario.setLocalizedLabel(SPANISH, "propietario de la vista");
        propietario.setLocalizedShortLabel(ENGLISH, "owner");
        propietario.setLocalizedShortLabel(SPANISH, "propietario");
        /**/
        remitente.setLocalizedLabel(ENGLISH, "sender");
        remitente.setLocalizedLabel(SPANISH, "remitente");
        /**/
        publica.setLocalizedLabel(ENGLISH, "public");
        publica.setLocalizedLabel(SPANISH, "pública");
        publica.setLocalizedDescription(ENGLISH, "Public view indicator; "
            + "public views can be used by all users authorized to query the entity");
        publica.setLocalizedDescription(SPANISH, "indicador de vista pública; "
            + "las vistas públicas pueden ser utilizadas por todos los usuarios autorizados a hacer consultas sobre la entidad");
        /**/
        especial.setLocalizedLabel(ENGLISH, "special");
        especial.setLocalizedLabel(SPANISH, "especial");
        especial.setLocalizedDescription(ENGLISH, "Special view indicator; "
            + "special views are predefined views of the application; they are initially public, but can be privatized");
        especial.setLocalizedDescription(SPANISH, "indicador de vista especial; "
            + "las vistas especiales son vistas predefinidas de la aplicación; inicialmente son públicas, pero pueden ser privatizadas");
        /**/
        formato.setLocalizedLabel(ENGLISH, "preconfigured page format");
        formato.setLocalizedLabel(SPANISH, "formato de página preconfigurado");
        formato.setLocalizedShortLabel(ENGLISH, "format");
        formato.setLocalizedShortLabel(SPANISH, "formato");
        formato.setLocalizedDescription(ENGLISH, "Preconfigured page format; "
            + "the preconfigured page formats define paper size, orientation (landscape or portrait) and margins of the report");
        formato.setLocalizedDescription(SPANISH, "Formato de página preconfigurado; "
            + "los formatos de página preconfigurados definen el tamaño del papel, la orientación (horizontal o vertical) y los márgenes del informe");
        /**/
        appendix = "; one pixel equals one typographic dot and 72 typographic dots equals one inch.";
        apendice = "; un pixel equivale a un punto tipográfico y 72 puntos tipográficos equivalen a una pulgada.";
        /**/
        anchoPagina.setLocalizedLabel(ENGLISH, "page width");
        anchoPagina.setLocalizedLabel(SPANISH, "ancho de página");
        anchoPagina.setLocalizedDescription(ENGLISH, "Width of the sheet of paper, in pixels" + appendix);
        anchoPagina.setLocalizedDescription(SPANISH, "Ancho de la hoja de papel, expresado en pixeles" + apendice);
        /**/
        english = b("page width") + _of_ + b("format");
        spanish = b("ancho de página") + _de_ + b("formato");
        anchoPagina.setLocalizedInitialValueTag(ENGLISH, english);
        anchoPagina.setLocalizedInitialValueTag(SPANISH, spanish);
        anchoPagina.setLocalizedDefaultValueTag(ENGLISH, english);
        anchoPagina.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        largoPagina.setLocalizedLabel(ENGLISH, "page height");
        largoPagina.setLocalizedLabel(SPANISH, "altura de página");
        largoPagina.setLocalizedDescription(ENGLISH, "Height of the sheet of paper, in pixels" + appendix);
        largoPagina.setLocalizedDescription(SPANISH, "Altura de la hoja de papel, expresado en pixeles" + apendice);
        /**/
        english = b("page height") + _of_ + b("format");
        spanish = b("altura de página") + _de_ + b("formato");
        largoPagina.setLocalizedInitialValueTag(ENGLISH, english);
        largoPagina.setLocalizedInitialValueTag(SPANISH, spanish);
        largoPagina.setLocalizedDefaultValueTag(ENGLISH, english);
        largoPagina.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        orientacionHorizontal.setLocalizedLabel(ENGLISH, "landscape orientation");
        orientacionHorizontal.setLocalizedLabel(SPANISH, "orientación horizontal");
        /**/
        orientacionVertical.setLocalizedLabel(ENGLISH, "portrait orientation");
        orientacionVertical.setLocalizedLabel(SPANISH, "orientación vertical");
        /**/
        margenSuperior.setLocalizedLabel(ENGLISH, "top margin");
        margenSuperior.setLocalizedLabel(SPANISH, "margen superior");
        margenSuperior.setLocalizedDescription(ENGLISH, "Top margin, in pixels" + appendix);
        margenSuperior.setLocalizedDescription(SPANISH, "Margen superior, expresado en pixeles" + apendice);
        /**/
        english = b("top margin") + _of_ + b("format");
        spanish = b("margen superior") + _de_ + b("formato");
        margenSuperior.setLocalizedInitialValueTag(ENGLISH, english);
        margenSuperior.setLocalizedInitialValueTag(SPANISH, spanish);
        margenSuperior.setLocalizedDefaultValueTag(ENGLISH, english);
        margenSuperior.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        margenInferior.setLocalizedLabel(ENGLISH, "bottom margin");
        margenInferior.setLocalizedLabel(SPANISH, "margen inferior");
        margenInferior.setLocalizedDescription(ENGLISH, "Bottom margin, in pixels" + appendix);
        margenInferior.setLocalizedDescription(SPANISH, "Margen inferior, expresado en pixeles" + apendice);
        /**/
        english = b("bottom margin") + _of_ + b("format");
        spanish = b("margen inferior") + _de_ + b("formato");
        margenInferior.setLocalizedInitialValueTag(ENGLISH, english);
        margenInferior.setLocalizedInitialValueTag(SPANISH, spanish);
        margenInferior.setLocalizedDefaultValueTag(ENGLISH, english);
        margenInferior.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        margenIzquierdo.setLocalizedLabel(ENGLISH, "left margin");
        margenIzquierdo.setLocalizedLabel(SPANISH, "margen izquierdo");
        margenIzquierdo.setLocalizedDescription(ENGLISH, "Left margin, in pixels" + appendix);
        margenIzquierdo.setLocalizedDescription(SPANISH, "Margen izquierdo, expresado en pixeles" + apendice);
        /**/
        english = b("left margin") + _of_ + b("format");
        spanish = b("margen izquierdo") + _de_ + b("formato");
        margenIzquierdo.setLocalizedInitialValueTag(ENGLISH, english);
        margenIzquierdo.setLocalizedInitialValueTag(SPANISH, spanish);
        margenIzquierdo.setLocalizedDefaultValueTag(ENGLISH, english);
        margenIzquierdo.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        margenDerecho.setLocalizedLabel(ENGLISH, "right margin");
        margenDerecho.setLocalizedLabel(SPANISH, "margen derecho");
        margenDerecho.setLocalizedDescription(ENGLISH, "Right margin, in pixels" + appendix);
        margenDerecho.setLocalizedDescription(SPANISH, "Margen derecho, expresado en pixeles" + apendice);
        /**/
        english = b("right margin") + _of_ + b("format");
        spanish = b("margen derecho") + _de_ + b("formato");
        margenDerecho.setLocalizedInitialValueTag(ENGLISH, english);
        margenDerecho.setLocalizedInitialValueTag(SPANISH, spanish);
        margenDerecho.setLocalizedDefaultValueTag(ENGLISH, english);
        margenDerecho.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        anchoColumna.setLocalizedLabel(ENGLISH, "print area width");
        anchoColumna.setLocalizedLabel(SPANISH, "ancho del área de impresión");
        anchoColumna.setLocalizedShortLabel(SPANISH, "ancho de impresión");
        anchoColumna.setLocalizedDescription(ENGLISH, "Width of the print area, in pixels; it is equal to: page width - left margin - right margin");
        anchoColumna.setLocalizedDescription(SPANISH, "Ancho del área de impresión, expresado en pixeles; es igual a: ancho de página - margen izquierdo - margen derecho");
        /**/
        largoColumna.setLocalizedLabel(ENGLISH, "print area height");
        largoColumna.setLocalizedLabel(SPANISH, "altura del área de impresión");
        largoColumna.setLocalizedShortLabel(SPANISH, "altura de impresión");
        largoColumna.setLocalizedDescription(ENGLISH, "Height of the print area, in pixels; it is equal to: page height - top margin - bottom margin");
        largoColumna.setLocalizedDescription(SPANISH, "Altura del área de impresión, expresado en pixeles; es igual a: altura de página - margen superior - margen inferior");
        /**/
        anchoDetalle.setLocalizedLabel(ENGLISH, "detail width");
        anchoDetalle.setLocalizedLabel(SPANISH, "ancho del detalle");
        anchoDetalle.setLocalizedDescription(ENGLISH, "Width of the detail group, in pixels; it is the sum of the witdh of all fields in the group");
        anchoDetalle.setLocalizedDescription(SPANISH, "Ancho del grupo de detalle, en píxeles; es la suma del ancho de todos los campos del grupo");
        /**/
        anchoDisponible.setLocalizedLabel(ENGLISH, "available in detail");
        anchoDisponible.setLocalizedLabel(SPANISH, "disponible en detalle");
        anchoDisponible.setLocalizedDescription(ENGLISH, "Available width for fields in detail group, in pixels; it is equal to: print area width - detail width");
        anchoDisponible.setLocalizedDescription(SPANISH, "Ancho disponible para campos en el grupo de detalle, en píxeles; es igual a: ancho de impresión - ancho del detalle");
        /**/
        anchoDisponible.setLocalizedGraphicImageTooltip(ENGLISH, imagenAnchoDisponible1, "unspecified value");
        anchoDisponible.setLocalizedGraphicImageTooltip(ENGLISH, imagenAnchoDisponible2, "the detail is wide enough");
        anchoDisponible.setLocalizedGraphicImageTooltip(ENGLISH, imagenAnchoDisponible3, "the detail is not wide enough to fit all the columns in the view");
        anchoDisponible.setLocalizedGraphicImageTooltip(SPANISH, imagenAnchoDisponible1, "valor no especificado");
        anchoDisponible.setLocalizedGraphicImageTooltip(SPANISH, imagenAnchoDisponible2, "el detalle es lo suficientemente ancho");
        anchoDisponible.setLocalizedGraphicImageTooltip(SPANISH, imagenAnchoDisponible3, "el detalle no es lo suficientemente ancho para poder incluir todas las columnas de la vista");
        /**/
        valida.setLocalizedLabel(ENGLISH, "valid");
        valida.setLocalizedLabel(SPANISH, "válida");
        /**/
        secuencia.setLocalizedLabel(ENGLISH, "sequence");
        secuencia.setLocalizedLabel(SPANISH, "secuencia");
        /**/
        idVistaFuncionOriginal.setLocalizedLabel(ENGLISH, "original view");
        idVistaFuncionOriginal.setLocalizedLabel(SPANISH, "vista original");
        idVistaFuncionOriginal.setLocalizedShortLabel(ENGLISH, "original");
        idVistaFuncionOriginal.setLocalizedShortLabel(SPANISH, "original");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleCollections() {
        super.settleCollections();
        /**/
        // Necesita OrphanRemoval, PERSIST y REMOVE; no debe incluir EAGER/MERGE porque da problemas, quizá por la referencia circular (grupo)
        /**/
        columnas.setCreateField(false);
        columnas.setDetailField(false);
        columnas.setFetchType(FetchType.LAZY);
        columnas.setOrphanRemoval(true);
        columnas.setUpdateField(true);
        columnas.setCascadeType(CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncion's collections">
        /**/
        columnas.setLocalizedLabel(ENGLISH, "view columns");
        columnas.setLocalizedLabel(SPANISH, "columnas de la vista");
        columnas.setLocalizedShortLabel(ENGLISH, "columns");
        columnas.setLocalizedShortLabel(SPANISH, "columnas");
        columnas.setLocalizedDescription(ENGLISH, "view column collection");
        columnas.setLocalizedDescription(SPANISH, "colección de columnas de la vista");
        /**/
        // </editor-fold>
    }

    protected Segment privadas, ordinarias, personales;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /*
        predeterminadas = publica.or(propietario.id.isEqualTo(CURRENT_USER_ID));
        /**/
        privadas = publica.isFalse();
        ordinarias = especial.isFalse();
        personales = privadas.and(ordinarias);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncion's expressions">
        /*
        predeterminadas.setLocalizedCollectionLabel(ENGLISH, "all user's own views and all public views");
        predeterminadas.setLocalizedCollectionLabel(SPANISH, "todas las vistas propias del usuario y todas las vistas públicas");
        predeterminadas.setLocalizedCollectionShortLabel(ENGLISH, "My views and public views");
        predeterminadas.setLocalizedCollectionShortLabel(SPANISH, "Mis vistas y las vistas públicas");
        predeterminadas.setLocalizedDescription(ENGLISH, "the view is own or public");
        predeterminadas.setLocalizedDescription(SPANISH, "el vista es propia o pública");
        predeterminadas.setLocalizedErrorMessage(ENGLISH, "the view is private and of another user");
        predeterminadas.setLocalizedErrorMessage(SPANISH, "el vista es privado y de otro usuario");
        /**/
        privadas.setLocalizedErrorMessage(ENGLISH, "the view is public");
        privadas.setLocalizedErrorMessage(SPANISH, "la vista es pública");
        /**/
        ordinarias.setLocalizedErrorMessage(ENGLISH, "the view is special");
        ordinarias.setLocalizedErrorMessage(SPANISH, "la vista es especial");
        /**/
        personales.setLocalizedErrorMessage(ENGLISH, "the view is public or special");
        personales.setLocalizedErrorMessage(SPANISH, "la vista es pública o especial");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setUpdateFilter(personales);
        setDeleteFilter(personales);
        /*
        addSelectSegment(predeterminadas, true);
        /**/
        BooleanExpression formatoPersonalizado = formato.isEqualTo(formato.PERSONALIZADO);
        /**/
        anchoPagina.setModifyingFilter(formatoPersonalizado);
//      anchoPagina.setNullifyingFilter(not(formatoPersonalizado));
//      anchoPagina.setRenderingFilter(formatoPersonalizado, true);
        anchoPagina.setRequiringFilter(formatoPersonalizado);
        /**/
        largoPagina.setModifyingFilter(formatoPersonalizado);
//      largoPagina.setNullifyingFilter(not(formatoPersonalizado));
//      largoPagina.setRenderingFilter(formatoPersonalizado, true);
        largoPagina.setRequiringFilter(formatoPersonalizado);
        /**/
        margenSuperior.setModifyingFilter(formatoPersonalizado);
//      margenSuperior.setNullifyingFilter(not(formatoPersonalizado));
//      margenSuperior.setRenderingFilter(formatoPersonalizado, true);
        margenSuperior.setRequiringFilter(formatoPersonalizado);
        /**/
        margenInferior.setModifyingFilter(formatoPersonalizado);
//      margenInferior.setNullifyingFilter(not(formatoPersonalizado));
//      margenInferior.setRenderingFilter(formatoPersonalizado, true);
        margenInferior.setRequiringFilter(formatoPersonalizado);
        /**/
        margenIzquierdo.setModifyingFilter(formatoPersonalizado);
//      margenIzquierdo.setNullifyingFilter(not(formatoPersonalizado));
//      margenIzquierdo.setRenderingFilter(formatoPersonalizado, true);
        margenIzquierdo.setRequiringFilter(formatoPersonalizado);
        /**/
        margenDerecho.setModifyingFilter(formatoPersonalizado);
//      margenDerecho.setNullifyingFilter(not(formatoPersonalizado));
//      margenDerecho.setRenderingFilter(formatoPersonalizado, true);
        margenDerecho.setRequiringFilter(formatoPersonalizado);
        /**/
    }

    protected EnviarCopia enviarCopia;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class EnviarCopia extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "send a copy");
            setLocalizedLabel(SPANISH, "enviar copia");
            /**/
            setLocalizedDescription(ENGLISH, "send a copy of the view to the specified recipient");
            setLocalizedDescription(SPANISH, "enviar una copia de la vista al destinatario especificado");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "a copy of the view was sent to the specified recipient");
            setLocalizedSuccessMessage(SPANISH, "se envió una copia de la vista al destinatario especificado");
            /**/
            // </editor-fold>
            /**/
        }

        @InstanceReference
        protected VistaFuncion vista;

        @ParameterField(required = Kleenean.TRUE)
        @EntityReferenceConversionValidation(restrictedAccess = Kleenean.FALSE)
        protected Usuario destinatario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's parameters">
            /**/
            vista.setLocalizedLabel(ENGLISH, "view");
            vista.setLocalizedLabel(SPANISH, "vista");
            /**/
            destinatario.setLocalizedLabel(ENGLISH, "recipient user");
            destinatario.setLocalizedLabel(SPANISH, "usuario destinatario");
            destinatario.setLocalizedShortLabel(ENGLISH, "recipient");
            destinatario.setLocalizedShortLabel(SPANISH, "destinatario");
            destinatario.setLocalizedShortDescription(ENGLISH, "view recipient");
            destinatario.setLocalizedShortDescription(SPANISH, "destinatario de la vista");
            destinatario.setLocalizedTooltip(ENGLISH, "user code of the view recipient");
            destinatario.setLocalizedTooltip(SPANISH, "código de usuario del destinatario de la vista");
            /**/
            destinatario.codigoUsuario.setLocalizedShortLabel(ENGLISH, "recipient code");
            destinatario.codigoUsuario.setLocalizedShortLabel(SPANISH, "destinatario");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102, check201, check202;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = vista.publica.isFalse();
            check102 = vista.propietario.id.isEqualTo(CURRENT_USER_ID);
            /**/
            check201 = destinatario.esUsuarioEspecial.isFalse();
            check202 = destinatario.id.isNotEqualTo(CURRENT_USER_ID);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the view is not a public view");
            check101.setLocalizedDescription(SPANISH, "la vista no es una vista pública");
            check101.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of public views");
            check101.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de vistas públicas");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the view belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "la vista le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of views that do not belong to you");
            check102.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de vistas que no le pertenecen");
            /**/
            check201.setLocalizedDescription(ENGLISH, "the recipient is not a special user");
            check201.setLocalizedDescription(SPANISH, "el destinatario no es un usuario especial");
            check201.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of views to special users");
            check201.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de vistas a usuarios especiales");
            /**/
            check202.setLocalizedDescription(ENGLISH, "the recipient is not the current user");
            check202.setLocalizedDescription(SPANISH, "el destinatario no es el usuario actual");
            check202.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of views to yourself");
            check202.setLocalizedErrorMessage(SPANISH, "no está permitido enviarse copias de vistas a uno mismo");
            /**/
            // </editor-fold>
            /**/
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            /**/
            destinatario.setSearchQueryFilter(and(check201, check202));
            /**/
        }

    }

    protected Publicar publicar;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class Publicar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Publicar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "publish");
            setLocalizedLabel(SPANISH, "publicar");
            /**/
            setLocalizedDescription(ENGLISH, "make a private view public");
            setLocalizedDescription(SPANISH, "convertir una vista privada en pública");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the view was successfully published");
            setLocalizedSuccessMessage(SPANISH, "la vista se publicó con éxito");
            /**/
            // </editor-fold>
            /**/
        }

        @InstanceReference
        protected VistaFuncion vista;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            vista.publica.setCurrentValue(true);
            vista.propietario.setCurrentValue(SpecialEntityValue.NULL);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Publicar's parameters">
            /**/
            vista.setLocalizedLabel(ENGLISH, "view");
            vista.setLocalizedLabel(SPANISH, "vista");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = vista.publica.isFalse();
            check102 = vista.propietario.id.isEqualTo(CURRENT_USER_ID);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Publicar's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the view is not a public view");
            check101.setLocalizedDescription(SPANISH, "la vista no es una vista pública");
            check101.setLocalizedErrorMessage(ENGLISH, "the view is already public");
            check101.setLocalizedErrorMessage(SPANISH, "la vista ya es pública");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the view belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "la vista le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "it is not allowed to publish views that do not belong to you");
            check102.setLocalizedErrorMessage(SPANISH, "no está permitido publicar vistas que no le pertenecen");
            /**/
            // </editor-fold>
            /**/
        }

    }

    protected Privatizar privatizar;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class Privatizar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Privatizar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "privatize");
            setLocalizedLabel(SPANISH, "privatizar");
            /**/
            setLocalizedDescription(ENGLISH, "make a public view private");
            setLocalizedDescription(SPANISH, "convertir una vista pública en privada");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the view was successfully privatized");
            setLocalizedSuccessMessage(SPANISH, "la vista se privatizó con éxito");
            /**/
            // </editor-fold>
            /**/
        }

        @InstanceReference
        protected VistaFuncion vista;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            vista.publica.setCurrentValue(false);
            vista.propietario.setCurrentValue(SpecialEntityValue.CURRENT_USER);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Privatizar's parameters">
            /**/
            vista.setLocalizedLabel(ENGLISH, "view");
            vista.setLocalizedLabel(SPANISH, "vista");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = vista.propietario.isNull().and(vista.publica.isTrue());
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Privatizar's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the view is not a private view");
            check101.setLocalizedDescription(SPANISH, "la vista no es una vista privada");
            check101.setLocalizedErrorMessage(ENGLISH, "the view is already private");
            check101.setLocalizedErrorMessage(SPANISH, "la vista ya es privada");
            /**/
            // </editor-fold>
            /**/
        }

    }

}
