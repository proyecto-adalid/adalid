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
 * La anotación ImageProperty se utiliza para designar una propiedad como propiedad imagen de la entidad. Cada entidad puede tener una sola propiedad
 * imagen. Solo se puede designar como propiedad descripción a propiedades de la clase BinaryProperty.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImageProperty {

    /**
     * avatarShape especifica la forma de la imagen cuando se utiliza como avatar. Su valor es uno de los elementos de la enumeración AvatarShape.
     * Seleccione CIRCLE o RECTANGLE para mostrar una imagen circular o rectangular, respectivamente. Alternativamente, omita el elemento o seleccione
     * NONE para no utilizar la imagen como avatar. El valor predeterminado del atributo es NONE.
     *
     * @return avatarShape
     */
    AvatarShape avatarShape() default AvatarShape.UNSPECIFIED;

    /**
     * avatarDefault especifica el avatar de las instancias que no tienen imagen. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento avatarShape es distinto de NONE. Su valor es uno de los elementos de la enumeración AvatarDefault. Seleccione
     * COMPANY, PERSON, USER o UNKNOWN para mostrar el avatar predeterminado de compañía, persona, usuario o desconocido, respectivamente. Seleccione
     * UNSEEN para mostrar una imagen invisible, que ocupe el mismo espacio que ocuparía un avatar. Alternativamente, omita el elemento o seleccione
     * NONE para no mostrar el avatar en las instancias que no tienen imagen. El valor predeterminado del atributo es NONE.
     *
     * @return avatarShape
     */
    AvatarDefault avatarDefault() default AvatarDefault.UNSPECIFIED;

    /**
     * avatarWidth específica el número de pixeles de ancho de la imagen en la vista, cuando se utiliza como avatar. Este elemento es relevante solo
     * si el valor especificado, o determinado, para el elemento avatarShape es distinto de NONE. Especifique un número entero entre 24 y 96.
     * Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es 36.
     *
     * @return avatarWidth
     */
    int avatarWidth() default 0;

    /**
     * avatarHeight específica el número de pixeles de alto de la imagen en la vista, cuando se utiliza como avatar. Este elemento es relevante solo
     * si el valor especificado, o determinado, para el elemento avatarShape es distinto de NONE. Especifique un número entero entre 24 y 96.
     * Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es 36.
     *
     * @return avatarHeight
     */
    int avatarHeight() default 0;

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
     * resizable indica si el tamaño de la imagen se debe ajustar de manera dinámica y proporcional en función de las dimensiones reales de la imagen
     * y de los valores especificados para los elementos displayWidth y displayHeight. Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE para ajustar el tamaño; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return resizable
     */
    Kleenean resizable() default Kleenean.UNSPECIFIED; // Kleenean.TRUE;

}
