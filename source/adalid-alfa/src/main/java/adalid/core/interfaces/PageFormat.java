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
package adalid.core.interfaces;

/**
 * @author Jorge Campins
 */
public interface PageFormat {

    default int ordinal() {
        return 0;
    }

    String getPaperSize();

    boolean isLandscapeOrientation();

    boolean isPortraitOrientation();

    int getColumnWidth();

    int getPageWidth();

    int getPageHeight();

    int getTopMargin();

    int getBottomMargin();

    int getLeftMargin();

    int getRightMargin();

}
