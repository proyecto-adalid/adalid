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
package adalid.core.page.format;

import adalid.core.interfaces.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class AbstractPageFormat implements PageFormat {

    private final String paperSize;

    private final int pageWidth, pageHeight, topMargin, bottomMargin, leftMargin, rightMargin;

    /**
     * Construye una instancia de la clase, dando valor a todos los campos requeridos del formato.
     * <p>
     * Todas las dimensiones se expresan en puntos tipográficos. Una pulgada equivale a 72 puntos tipográficos. Si el valor de <code>pageWidth</code>
     * es mayor que el valor de <code>pageHeight</code>, entonces el reporte tendrá orientación horizontal; de lo contrario, orientación vertical. En
     * cualquier caso, el ancho del área de impresión es <code>pageWidth</code> - <code>leftMargin</code> - <code>rightMargin</code>.
     *
     * @param paperSize tamaño de la hoja de papel; por ejemplo: A4, B5, Legal, Letter. Especifique Custom para tamaños personalizados.
     * @param pageWidth ancho de la hoja de papel. Valor mínimo 360 puntos (5"). Valor máximo 1296 puntos (18").
     * @param pageHeight largo de la hoja de papel. Valor mínimo 360 puntos (5"). Valor máximo 1296 puntos (18").
     * @param topMargin margen superior. Valor mínimo 18 puntos (0.25"). Valor máximo 144 puntos (2").
     * @param bottomMargin margen inferior. Valor mínimo 18 puntos (0.25"). Valor máximo 144 puntos (2").
     * @param leftMargin margen izquierdo. Valor mínimo 18 puntos (0.25"). Valor máximo 144 puntos (2").
     * @param rightMargin margen derecho. Valor mínimo 18 puntos (0.25"). Valor máximo 144 puntos (2").
     *
     * @see <a href="https://en.wikipedia.org/wiki/Paper_size">Paper size</a>
     * @see <a href="https://es.wikipedia.org/wiki/Formato_de_papel">Formato de papel</a>
     */
    protected AbstractPageFormat(String paperSize, int pageWidth, int pageHeight, int topMargin, int bottomMargin, int leftMargin, int rightMargin) {
        this.paperSize = StringUtils.defaultIfBlank(paperSize, "Custom");
        // desde Junior Legal (5x8) hasta Tabloid Extra (12x18)
        this.pageWidth = pageWidth < 360 ? 360 : pageWidth > 1296 ? 1296 : pageWidth; // mínimo 5", máximo 18"
        this.pageHeight = pageHeight < 360 ? 360 : pageHeight > 1296 ? 1296 : pageHeight; // mínimo 5", máximo 18"
        // What are the minimum margins most printers can handle? Every printer is different but 0.25" (6.35 mm) is a safe bet.
        this.topMargin = topMargin < 18 ? 18 : topMargin > 144 ? 144 : topMargin; // mínimo 0.25", máximo 2"
        this.bottomMargin = bottomMargin < 18 ? 18 : bottomMargin > 144 ? 144 : bottomMargin; // mínimo 0.25", máximo 2"
        this.leftMargin = leftMargin < 18 ? 18 : leftMargin > 144 ? 144 : leftMargin; // mínimo 0.25", máximo 2"
        this.rightMargin = rightMargin < 18 ? 18 : rightMargin > 144 ? 144 : rightMargin; // mínimo 0.25", máximo 2"
    }

    @Override
    public String getPaperSize() {
        return paperSize;
    }

    @Override
    public boolean isLandscapeOrientation() {
        return pageWidth > pageHeight;
    }

    @Override
    public boolean isPortraitOrientation() {
        return pageHeight > pageWidth;
    }

    @Override
    public int getColumnWidth() {
        return pageWidth - leftMargin - rightMargin;
    }

    @Override
    public int getPageWidth() {
        return pageWidth;
    }

    @Override
    public int getPageHeight() {
        return pageHeight;
    }

    @Override
    public int getTopMargin() {
        return topMargin;
    }

    @Override
    public int getBottomMargin() {
        return bottomMargin;
    }

    @Override
    public int getLeftMargin() {
        return leftMargin;
    }

    @Override
    public int getRightMargin() {
        return rightMargin;
    }

}
