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
     * displayWidth específica un trío de números de pixeles de ancho de la imagen en la vista, correspondientes a tamaño grande, mediano y pequeño,
     * respectivamente. Especifique números enteros entre 24 y 1.920. Alternativamente, omita el elemento para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es el trío (288, 192, 96}. Si alguno de los valores especificados es mayor que 1.920, su valor
     * será 1.920; si es menor o igual a 0, será el valor del correspondiente tamaño en el trío displayHeight; pero si ese valor es menor o igual a 0,
     * será el valor del correspondiente tamaño en el trío predeterminado.
     *
     * @return displayWidth
     */
    int[] displayWidth() default {0, 0, 0};

    /**
     * displayHeight específica un trío de números de pixeles de alto de la imagen en la vista, correspondientes a tamaño grande, mediano y pequeño,
     * respectivamente. Especifique un número entero entre 24 y 1.080. Alternativamente, omita el elemento para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es el trío (288, 192, 96}. Si alguno de los valores especificados es mayor que 1.080, su valor
     * será 1.080; si es menor o igual a 0, será el valor del correspondiente tamaño en el trío displayWidth; pero si ese valor es mayor que 1.080,
     * será 1.080, y si es menor o igual a 0, será el valor del correspondiente tamaño en el trío predeterminado.
     *
     * @return displayHeight
     */
    int[] displayHeight() default {0, 0, 0};

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
