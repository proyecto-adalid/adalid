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
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 * @see <a href="https://en.wikipedia.org/wiki/Paper_size">Paper size</a>
 * @see <a href="https://es.wikipedia.org/wiki/Formato_de_papel">Formato de papel</a>
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE, startWith = 0)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class FormatoPaginaInforme extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public FormatoPaginaInforme(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of FormatoPaginaInforme's attributes">
        setLocalizedLabel(ENGLISH, "page format");
        setLocalizedLabel(SPANISH, "formato de página");
        setLocalizedCollectionLabel(ENGLISH, "Report Page Formats");
        setLocalizedCollectionLabel(SPANISH, "Formatos de Página de Informe");
        // </editor-fold>
    }

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty formatoPapel;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public IntegerProperty anchoPagina;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public IntegerProperty largoPagina;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty orientacionHorizontal;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty orientacionVertical;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public IntegerProperty margenSuperior;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public IntegerProperty margenInferior;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public IntegerProperty margenIzquierdo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public IntegerProperty margenDerecho;

    @ColumnField(calculable = Kleenean.TRUE)
    public IntegerProperty anchoColumna;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty largoColumna;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        anchoPagina.setDefaultValue(1008);
        largoPagina.setDefaultValue(774);
        /**/
        orientacionHorizontal.setCalculableValueExpression(anchoPagina.isGreaterThan(largoPagina));
        orientacionVertical.setCalculableValueExpression(anchoPagina.isLessThan(largoPagina));
        /**/
        margenSuperior.setDefaultValue(24);
        margenInferior.setDefaultValue(24);
        margenIzquierdo.setDefaultValue(24);
        margenDerecho.setDefaultValue(24);
        /**/
        anchoColumna.setCalculableValueExpression(anchoPagina.minus(margenIzquierdo.plus(margenDerecho)));
        largoColumna.setCalculableValueExpression(largoPagina.minus(margenSuperior.plus(margenInferior)));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FormatoPaginaInforme's properties">
        /**/
        String appendix, apendice;
        /**/
        numero.setLocalizedLabel(ENGLISH, "page format number");
        numero.setLocalizedLabel(SPANISH, "número del formato de página");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "page format code");
        codigo.setLocalizedLabel(SPANISH, "código del formato de página");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        appendix = "; one pixel equals one typographic dot and 72 typographic dots equals one inch.";
        apendice = "; un pixel equivale a un punto tipográfico y 72 puntos tipográficos equivalen a una pulgada.";
        /**/
        anchoPagina.setLocalizedLabel(ENGLISH, "page width");
        anchoPagina.setLocalizedLabel(SPANISH, "ancho de página");
        anchoPagina.setLocalizedDescription(ENGLISH, "Width of the sheet of paper, in pixels" + appendix);
        anchoPagina.setLocalizedDescription(SPANISH, "Ancho de la hoja de papel, expresado en pixeles" + apendice);
        /**/
        largoPagina.setLocalizedLabel(ENGLISH, "page height");
        largoPagina.setLocalizedLabel(SPANISH, "altura de página");
        largoPagina.setLocalizedDescription(ENGLISH, "Height of the sheet of paper, in pixels" + appendix);
        largoPagina.setLocalizedDescription(SPANISH, "Altura de la hoja de papel, expresado en pixeles" + apendice);
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
        margenInferior.setLocalizedLabel(ENGLISH, "bottom margin");
        margenInferior.setLocalizedLabel(SPANISH, "margen inferior");
        margenInferior.setLocalizedDescription(ENGLISH, "Bottom margin, in pixels" + appendix);
        margenInferior.setLocalizedDescription(SPANISH, "Margen inferior, expresado en pixeles" + apendice);
        /**/
        margenIzquierdo.setLocalizedLabel(ENGLISH, "left margin");
        margenIzquierdo.setLocalizedLabel(SPANISH, "margen izquierdo");
        margenIzquierdo.setLocalizedDescription(ENGLISH, "Left margin, in pixels" + appendix);
        margenIzquierdo.setLocalizedDescription(SPANISH, "Margen izquierdo, expresado en pixeles" + apendice);
        /**/
        margenDerecho.setLocalizedLabel(ENGLISH, "right margin");
        margenDerecho.setLocalizedLabel(SPANISH, "margen derecho");
        margenDerecho.setLocalizedDescription(ENGLISH, "Right margin, in pixels" + appendix);
        margenDerecho.setLocalizedDescription(SPANISH, "Margen derecho, expresado en pixeles" + apendice);
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
        // </editor-fold>
    }

    public Instance PERSONALIZADO;

    public Instance A4_HORIZONTAL, A4_VERTICAL;

    public Instance B5_HORIZONTAL, B5_VERTICAL;

    public Instance LEGAL_HORIZONTAL, LEGAL_VERTICAL;

    public Instance CARTA_HORIZONTAL, CARTA_VERTICAL;

    public Instance DOBLE_CARTA_HORIZONTAL, DOBLE_CARTA_VERTICAL;

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        PERSONALIZADO.newInstanceField(anchoPagina, 1008);
        PERSONALIZADO.newInstanceField(largoPagina, 774);
        /**/
        A4_HORIZONTAL.newInstanceField(anchoPagina, 842);
        A4_HORIZONTAL.newInstanceField(largoPagina, 594);
        /**/
        A4_VERTICAL.newInstanceField(anchoPagina, 594);
        A4_VERTICAL.newInstanceField(largoPagina, 842);
        /**/
        B5_HORIZONTAL.newInstanceField(anchoPagina, 708);
        B5_HORIZONTAL.newInstanceField(largoPagina, 498);
        /**/
        B5_VERTICAL.newInstanceField(anchoPagina, 498);
        B5_VERTICAL.newInstanceField(largoPagina, 708);
        /**/
        LEGAL_HORIZONTAL.newInstanceField(anchoPagina, 1008);
        LEGAL_HORIZONTAL.newInstanceField(largoPagina, 612);
        /**/
        LEGAL_VERTICAL.newInstanceField(anchoPagina, 612);
        LEGAL_VERTICAL.newInstanceField(largoPagina, 1008);
        /**/
        CARTA_HORIZONTAL.newInstanceField(anchoPagina, 792);
        CARTA_HORIZONTAL.newInstanceField(largoPagina, 612);
        /**/
        CARTA_VERTICAL.newInstanceField(anchoPagina, 612);
        CARTA_VERTICAL.newInstanceField(largoPagina, 792);
        /**/
        DOBLE_CARTA_HORIZONTAL.newInstanceField(anchoPagina, 1224);
        DOBLE_CARTA_HORIZONTAL.newInstanceField(largoPagina, 792);
        /**/
        DOBLE_CARTA_VERTICAL.newInstanceField(anchoPagina, 792);
        DOBLE_CARTA_VERTICAL.newInstanceField(largoPagina, 1224);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FormatoPaginaInforme's instances">
        /**/
        PERSONALIZADO.newInstanceField(codigo, "Custom format", ENGLISH);
        PERSONALIZADO.newInstanceField(codigo, "Personalizado", SPANISH);
        PERSONALIZADO.newInstanceField(formatoPapel, "Custom paper and margins", ENGLISH); // 14\" × 11\" · 356mm × 279mm
        PERSONALIZADO.newInstanceField(formatoPapel, "Papel y margenes personalizados", SPANISH); // 14\" × 11\" · 356mm × 279mm
        /**/
        A4_HORIZONTAL.newInstanceField(codigo, "A4/Landscape", ENGLISH);
        A4_HORIZONTAL.newInstanceField(codigo, "A4/Horizontal", SPANISH);
        A4_HORIZONTAL.newInstanceField(formatoPapel, "A4-size paper, landscape orientation · 297mm × 210mm · 11.7\" × 8.25\"", ENGLISH);
        A4_HORIZONTAL.newInstanceField(formatoPapel, "Papel tamaño A4, orientación horizontal · 297mm × 210mm · 11.7\" × 8.25\"", SPANISH);
        /**/
        A4_VERTICAL.newInstanceField(codigo, "A4/Portrait", ENGLISH);
        A4_VERTICAL.newInstanceField(codigo, "A4/Vertical", SPANISH);
        A4_VERTICAL.newInstanceField(formatoPapel, "A4-size paper, portrait orientation · 210mm × 297mm · 8.25\" × 11.7\"", ENGLISH);
        A4_VERTICAL.newInstanceField(formatoPapel, "Papel tamaño A4, orientación vertical · 210mm × 297mm · 8.25\" × 11.7\"", SPANISH);
        /**/
        B5_HORIZONTAL.newInstanceField(codigo, "B5/Landscape", ENGLISH);
        B5_HORIZONTAL.newInstanceField(codigo, "B5/Horizontal", SPANISH);
        B5_HORIZONTAL.newInstanceField(formatoPapel, "B5-size paper, landscape orientation · 250mm × 176mm · 9.8\" × 6.9\"", ENGLISH);
        B5_HORIZONTAL.newInstanceField(formatoPapel, "Papel tamaño B5, orientación horizontal · 250mm × 176mm · 9.8\" × 6.9\"", SPANISH);
        /**/
        B5_VERTICAL.newInstanceField(codigo, "B5/Portrait", ENGLISH);
        B5_VERTICAL.newInstanceField(codigo, "B5/Vertical", SPANISH);
        B5_VERTICAL.newInstanceField(formatoPapel, "B5-size paper, portrait orientation · 176mm × 250mm · 6.9\" × 9.8\"", ENGLISH);
        B5_VERTICAL.newInstanceField(formatoPapel, "Papel tamaño B5, orientación vertical · 176mm × 250mm · 6.9\" × 9.8\"", SPANISH);
        /**/
        LEGAL_HORIZONTAL.newInstanceField(codigo, "Legal/Landscape", ENGLISH);
        LEGAL_HORIZONTAL.newInstanceField(codigo, "Legal/Horizontal", SPANISH);
        LEGAL_HORIZONTAL.newInstanceField(formatoPapel, "Legal-size paper, landscape orientation · 14\" × 8.5\" · 356mm × 216mm", ENGLISH);
        LEGAL_HORIZONTAL.newInstanceField(formatoPapel, "Papel tamaño Legal, orientación horizontal · 14\" × 8.5\" · 356mm × 216mm", SPANISH);
        /**/
        LEGAL_VERTICAL.newInstanceField(codigo, "Legal/Portrait", ENGLISH);
        LEGAL_VERTICAL.newInstanceField(codigo, "Legal/Vertical", SPANISH);
        LEGAL_VERTICAL.newInstanceField(formatoPapel, "Legal-size paper, portrait orientation · 8.5\" × 14\" · 216mm × 356mm", ENGLISH);
        LEGAL_VERTICAL.newInstanceField(formatoPapel, "Papel tamaño Legal, orientación vertical · 8.5\" × 14\" · 216mm × 356mm", SPANISH);
        /**/
        CARTA_HORIZONTAL.newInstanceField(codigo, "Letter/Landscape", ENGLISH);
        CARTA_HORIZONTAL.newInstanceField(codigo, "Carta/Horizontal", SPANISH);
        CARTA_HORIZONTAL.newInstanceField(formatoPapel, "Letter-size paper, landscape orientation · 11\" × 8.5\" · 279mm × 216mm", ENGLISH);
        CARTA_HORIZONTAL.newInstanceField(formatoPapel, "Papel tamaño Carta, orientación horizontal · 11\" × 8.5\" · 279mm × 216mm", SPANISH);
        /**/
        CARTA_VERTICAL.newInstanceField(codigo, "Letter/Portrait", ENGLISH);
        CARTA_VERTICAL.newInstanceField(codigo, "Carta/Vertical", SPANISH);
        CARTA_VERTICAL.newInstanceField(formatoPapel, "Letter-size paper, portrait orientation · 8.5\" × 11\" · 216mm × 279mm", ENGLISH);
        CARTA_VERTICAL.newInstanceField(formatoPapel, "Papel tamaño Carta, orientación vertical · 8.5\" × 11\" · 216mm × 279mm", SPANISH);
        /**/
        DOBLE_CARTA_HORIZONTAL.newInstanceField(codigo, "Ledger", ENGLISH);
        DOBLE_CARTA_HORIZONTAL.newInstanceField(codigo, "Doble Carta/Horizontal", SPANISH);
        DOBLE_CARTA_HORIZONTAL.newInstanceField(formatoPapel, "Ledger-size paper, landscape orientation · 17\" × 11\" · 432mm × 279mm", ENGLISH);
        DOBLE_CARTA_HORIZONTAL.newInstanceField(formatoPapel, "Papel tamaño Doble Carta, orientación horizontal · 17\" × 11\" · 432mm × 279mm", SPANISH);
        /**/
        DOBLE_CARTA_VERTICAL.newInstanceField(codigo, "Tabloid", ENGLISH);
        DOBLE_CARTA_VERTICAL.newInstanceField(codigo, "Doble Carta/Vertical", SPANISH);
        DOBLE_CARTA_VERTICAL.newInstanceField(formatoPapel, "Tabloid-size paper, portrait orientation · 11\" × 17\" · 279mm × 432mm", ENGLISH);
        DOBLE_CARTA_VERTICAL.newInstanceField(formatoPapel, "Papel tamaño Doble Carta, orientación vertical · 11\" × 17\" · 279mm × 432mm", SPANISH);
        /**/
        // </editor-fold>
    }

}
