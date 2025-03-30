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
package adalid.jee2;

/**
 * @author Jorge Campins
 */
public class ImageFile {

    private final String name;

    public ImageFile(String name) {
        this.name = name;
    }

    /**
     * @return the image file name
     */
    public String getImageFileName() {
        return name;
    }

    public String path;

    public String getPath() {
        return path;
    }

    public int height;

    public int getHeight() {
        return height < 0 ? 0 : height;
    }

    public int width;

    public int getWidth() {
        return width < 0 ? 0 : width;
    }

}
