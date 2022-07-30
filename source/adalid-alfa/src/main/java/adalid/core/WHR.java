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

import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
class WHR {

    private static final Logger logger = Logger.getLogger(WHR.class);

    static final int LARGE_SIZE = 0, MEDIUM_SIZE = 1, SMALL_SIZE = 2;

    static final int MIN_DOC_W = 144, MIN_DOC_H = 144, MIN_IMG_W = 24, MIN_IMG_H = 24;

    static final String[] COMPONENT_DISPLAY_SIZE = {"large", "medium", "small"};

    static final int[] DEF_DOC_W = {
        Constants.DEFAULT_LARGE_DOCUMENT_WIDTH,
        Constants.DEFAULT_MEDIUM_DOCUMENT_WIDTH,
        Constants.DEFAULT_SMALL_DOCUMENT_WIDTH
    };

    static final int[] DEF_DOC_H = {
        Constants.DEFAULT_LARGE_DOCUMENT_HEIGHT,
        Constants.DEFAULT_MEDIUM_DOCUMENT_HEIGHT,
        Constants.DEFAULT_SMALL_DOCUMENT_HEIGHT
    };

    static final int[] DEF_IMG_W = {
        Constants.DEFAULT_LARGE_IMAGE_WIDTH,
        Constants.DEFAULT_MEDIUM_IMAGE_WIDTH,
        Constants.DEFAULT_SMALL_IMAGE_WIDTH
    };

    static final int[] DEF_IMG_H = {
        Constants.DEFAULT_LARGE_IMAGE_HEIGHT,
        Constants.DEFAULT_MEDIUM_IMAGE_HEIGHT,
        Constants.DEFAULT_SMALL_IMAGE_HEIGHT
    };

    final int displayWidth;

    final int displayHeight;

    final boolean resizable;

    WHR(String fieldName, boolean log, int size, int displayWidth, int displayHeight, int minimumWidth, int minimumHeight, int defaultWidth, int defaultHeight) {
        if (log) {
            logger.trace(fieldName + "\tS0=" + size + "\tW0=" + displayWidth + "\tH0=" + displayHeight + "\tW1=" + defaultWidth + "\tH1=" + defaultHeight);
        }
        if (displayWidth < 1) {
            displayWidth = 0;
        } else if (displayWidth < minimumWidth || displayWidth > Constants.MAX_DISPLAY_WIDTH) {
            displayWidth = 0;
            if (log) {
                logger.error(fieldName + " has an invalid " + COMPONENT_DISPLAY_SIZE[size] + " display width");
                Project.increaseParserErrorCount();
            }
        }
        if (displayHeight < 1) {
            displayHeight = 0;
        } else if (displayHeight < minimumHeight || displayHeight > Constants.MAX_DISPLAY_HEIGHT) {
            displayHeight = 0;
            if (log) {
                logger.error(fieldName + " has an invalid " + COMPONENT_DISPLAY_SIZE[size] + " display height");
                Project.increaseParserErrorCount();
            }
        }
        boolean resized = false;
        if (displayWidth == 0 && displayHeight == 0) {
            displayWidth = defaultWidth;
            displayHeight = defaultHeight;
            resized = true;
        } else if (displayWidth == 0) {
            displayWidth = displayHeight;
            resized = true;
        } else if (displayHeight == 0) {
            displayHeight = displayWidth > Constants.MAX_DISPLAY_HEIGHT ? Constants.MAX_DISPLAY_HEIGHT : displayWidth;
            resized = true;
        }
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.resizable = resized;
        if (log) {
            logger.trace(fieldName + "\tS2=" + size + "\tW2=" + this.displayWidth + "\tH2=" + this.displayHeight + "\tR2=" + this.resizable);
        }
    }

}
