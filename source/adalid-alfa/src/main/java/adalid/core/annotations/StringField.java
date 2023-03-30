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
 * La anotación StringField se utiliza para establecer atributos de propiedades y parámetros String.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringField {

    /**
     * maxLength específica la cantidad máxima de caracteres que deben tener los valores de la propiedad o parámetro. Su valor debe ser un número
     * entero entre 0 y 32.767. El valor predeterminado es 2.000. Especifique 0 para utilizar el máximo permitido por la plataforma.
     *
     * @return maxLength
     */
    int maxLength() default -1;

    /**
     * minLength específica la cantidad mínima de caracteres que pueden tener los valores de la propiedad o parámetro. Su valor debe ser un número
     * entero entre 0 y 32.000. El valor predeterminado es 0.
     *
     * @return minLength
     */
    int minLength() default -1;

    /**
     * mask especifica la plantilla de la máscara para capturar los valores de la propiedad o parámetro. La plantilla puede tener los siguientes
     * caracteres, que tienen un significado especial:
     * <ul>
     * <li><b>9</b>: Representa un solo dígito, de <b>0</b> a <b>9</b>.</li>
     * <li><b>a</b>: Representa un solo carácter alfabético, de <b>A</b> a <b>Z</b> o de <b>a</b> a <b>z</b>.</li>
     * <li><b>*</b>: Representa un solo carácter alfanumérico, de <b>0</b> a <b>9</b>, de <b>A</b> a <b>Z</b> o de <b>a</b> a <b>z</b>.</li>
     * </ul>
     * La plantilla puede tener una parte opcional; esa parte se debe colocar entre corchetes. Por ejemplo, la plantilla "(999) 999-9999[ x9999]"
     * serviría para capturar números telefónicos, con un número opcional de extensión.
     *
     * @return mask
     */
    String mask() default "";

    /**
     * slotChar especifica el caracter que se muestra al capturar los valores de la propiedad o parámetro con una máscara, para resaltar donde el
     * usuario debe escribir una letra o número. Este elemento solo es relevante si se especifica un valor para el elemento mask. No puede ser un
     * caracter de control. Su valor predeterminado es el guion bajo (underscore).
     *
     * @return slotChar
     */
    char slotChar() default '_';

    /**
     * regex especifica la expresión regular que deben satisfacer los valores de la propiedad o parámetro. Para más información sobre expresiones
     * regulares consulte la documentación de Java (la página Regular Expressions es un buen punto de partida).
     *
     * @return regex
     */
    String regex() default "";

    /**
     * autoComplete especifica si los campos de entrada de la propiedad o parámetro deben tener habilitado, o no, el autocompletado. Cuando el usuario
     * comienza a escribir en un campo que tiene habilitado el autocompletado, el navegador debe mostrar y permitir seleccionar alguno de los valores
     * escritos anteriormente. El valor de autoComplete es uno de los elementos de la enumeración AutoComplete. Seleccione ON u OFF para habilitar o
     * inhabilitar el autocompletado, respectivamente. Si la propiedad o parámetro es un password, puede utilizar NEW_PASSWORD en lugar de ON para
     * que, además de valores escritos anteriormente, el navegador ofrezca a los usuarios contraseñas generadas automáticamente. Alternativamente,
     * omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del navegador.
     *
     * @return autoComplete
     */
    AutoComplete autoComplete() default AutoComplete.UNSPECIFIED;

    /**
     * letterCase especifica la conversión que se debe realizar al almacenar valores de la propiedad o parámetro en la base de datos. Su valor es uno
     * de los elementos de la enumeración LetterCase. Seleccione LOWER, UPPER o CAPITALIZED para convertir todos los caracteres a minúsculas, todos a
     * mayúsculas, o para capitalizar (convertir el primer carácter de cada palabra a mayúscula y el resto a minúsculas), respectivamente.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para no ejecutar conversión alguna.
     *
     * @return letterCase
     */
    LetterCase letterCase() default LetterCase.UNSPECIFIED;

    /**
     * allowDiacritics indica si se permiten, o no, signos diacríticos al almacenar valores de la propiedad o parámetro en la base de datos. Su valor
     * es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir signos diacríticos; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return allowDiacritics
     */
    Kleenean allowDiacritics() default Kleenean.UNSPECIFIED;

    /**
     * richTextFormat indica si la propiedad permite, o no, el uso del formato de texto enriquecido (RTF). Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para permitir el uso de RTF; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return richTextFormat
     */
    Kleenean richTextFormat() default Kleenean.UNSPECIFIED;

    /**
     * converter específica el nombre de un componente personalizado que permite convertir el valor de la propiedad o parámetro.
     *
     * @return converter
     */
    String converter() default "";

    /**
     * validator específica el nombre de un componente personalizado que permite validar el valor de la propiedad o parámetro.
     *
     * @return validator
     */
    String validator() default "";

}
