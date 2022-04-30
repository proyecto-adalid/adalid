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
package adalid.core.annotations;

import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación GraphicImage se utiliza para especificar atributos de imágenes almacenadas en propiedades BinaryProperty.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GraphicImage {

    /**
     * displayWidth específica el número de pixeles de ancho de la imagen en la vista. Especifique un número entero entre 0 y 3.840. Alternativamente,
     * omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es 96. Si el valor especificado es
     * mayor que 3.840, su valor será 3.840; si es menor o igual a 0, será el valor especificado para el elemento displayHeight; y si el valor de
     * displayHeight también es menor o igual a 0, entonces las vistas no muestran la imagen.
     *
     * @return displayWidth
     */
    int displayWidth() default 0; // Constants.DEFAULT_IMAGE_WIDTH;

    /**
     * displayHeight específica el número de pixeles de alto de la imagen en la vista. Especifique un número entero entre 0 y 2.160. Alternativamente,
     * omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es 96. Si el valor especificado es
     * mayor que 2.160, su valor será 2.160; si es menor o igual a 0, será el valor especificado para el elemento displayWidth; si el valor de
     * displayWidth es mayor que 2.160, será 2.160; y si el valor de displayWidth también es menor o igual a 0, entonces las vistas no muestran la
     * imagen.
     *
     * @return displayHeight
     */
    int displayHeight() default 0; // Constants.DEFAULT_IMAGE_HEIGHT;

    /**
     * resizable indica si el ancho de la imagen se debe ajustar de manera dinámica y proporcional en función de las dimensiones reales de la imagen y
     * de los valores especificados para los elementos displayWidth y displayHeight. Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE para ajustar el ancho; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return resizable
     */
    Kleenean resizable() default Kleenean.UNSPECIFIED; // Kleenean.TRUE;

}
