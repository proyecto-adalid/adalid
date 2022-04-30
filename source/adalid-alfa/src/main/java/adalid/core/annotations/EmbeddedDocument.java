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
 * La anotación EmbeddedDocument se utiliza para especificar atributos de documentos incrustados cuya definición es almacenada en propiedades
 * StringProperty.
 * <p>
 * <b>NOTA IMPORTANTE</b>
 * <p>
 * El componente Web de las aplicaciones generadas con la plataforma jee2 incluye el jar de OWASP HTML Sanitizer entre sus dependencias. OWASP HTML
 * Sanitizer es un Sanitizer HTML de fácil configuración que permite prevenir ataques del tipo Cross-Site Scripting (XSS), que podrían ocurrir por
 * incluir HTML creado por terceros en su aplicación web. Como parte de la refinación de la aplicación, se podrían definir e implementar políticas de
 * saneamiento de los documentos incrustados utilizando OWASP HTML Sanitizer.
 * <p>
 * @author Jorge Campins
 * @see <a href="https://github.com/OWASP/java-html-sanitizer/blob/master/docs/getting_started.md">OWASP HTML Sanitizer</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EmbeddedDocument {

    /**
     * sourceType indica el tipo de definición de documento incrustado que puede almacenar la propiedad. Su valor es uno de los elementos de la
     * enumeración EmbeddedDocumentType. Seleccione IFRAME para permitir la definición completa del componente <b>iframe</b>. Seleccione URL para
     * permitir solo la URL del documento (atributo <b>src</b> del componente <b>iframe</b>); en este caso, los demás atributos del componente
     * <b>iframe</b> generado se especifican mediante otros elementos de esta anotación. Seleccione BOTH para permitir cualquiera de las opciones
     * anteriores. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es BOTH.
     *
     * @return sourceType
     */
    EmbeddedDocumentType sourceType() default EmbeddedDocumentType.UNSPECIFIED;

    /**
     * style indica el tipo de presentación del documento incrustado. Su valor es uno de los elementos de la enumeración EmbeddedDocumentStyle.
     * Seleccione FIELD para presentar el documento en el campo de la vista (página) correspondiente a la propiedad. Seleccione POPUP para presentar
     * el documento en una ventana emergente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es FIELD.
     *
     * @return style
     */
    EmbeddedDocumentStyle style() default EmbeddedDocumentStyle.UNSPECIFIED;

    /**
     * displayWidth específica el número de pixeles de ancho del documento en la vista. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Especifique un
     * número entero entre 0 y 3.840. Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado
     * del atributo es 480. Si el valor especificado es mayor que 3.840, su valor será 3.840; si es menor o igual a 0, será el valor especificado para
     * el elemento displayHeight; y si el valor de displayHeight también es menor o igual a 0, entonces las vistas no muestran el documento.
     *
     * @return displayWidth
     */
    int displayWidth() default -1; // Constants.DEFAULT_DOCUMENT_WIDTH;

    /**
     * displayHeight específica el número de pixeles de alto del documento en la vista. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Especifique un
     * número entero entre 0 y 2.160. Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado
     * del atributo es 270. Si el valor especificado es mayor que 2.160, su valor será 2.160; si es menor o igual a 0, será el valor especificado para
     * el elemento displayWidth; si el valor de displayWidth es mayor que 2.160, será 2.160; y si el valor de displayWidth también es menor o igual a
     * 0, entonces las vistas no muestran el documento.
     *
     * @return displayHeight
     */
    int displayHeight() default -1; // Constants.DEFAULT_DOCUMENT_HEIGHT;

    /**
     * frameBorder indica si se debe mostrar o no un borde alrededor del documento en la vista. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la propiedad sea una
     * URL. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para mostrar el borde; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo depende de la plataforma.
     *
     * @return frameBorder
     */
    Kleenean frameBorder() default Kleenean.UNSPECIFIED;

    /**
     * encryptedMedia indica si se permite mostrar, o no, documentos encriptados. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es
     * uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir mostrar documentos encriptados; en caso contrario, seleccione
     * FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado
     * del atributo depende de la plataforma.
     *
     * @return encryptedMedia
     */
    Kleenean encryptedMedia() default Kleenean.UNSPECIFIED;

    /**
     * accelerometer indica si se permite utilizar, o no, el acelerómetro del dispositivo al mostrar los documentos. Este elemento es relevante solo
     * si el valor especificado, o determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la propiedad
     * sea una URL. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir el uso del acelerómetro; en caso
     * contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo.
     * El valor predeterminado del atributo depende de la plataforma.
     *
     * @return accelerometer
     */
    Kleenean accelerometer() default Kleenean.UNSPECIFIED;

    /**
     * autoplay indica si se permite utilizar, o no, la función de reproducción automática para mostrar los documentos. Este elemento es relevante
     * solo si el valor especificado, o determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la
     * propiedad sea una URL. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir el uso de la función; en caso
     * contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo.
     * El valor predeterminado del atributo depende de la plataforma.
     *
     * @return autoplay
     */
    Kleenean autoplay() default Kleenean.UNSPECIFIED;

    /**
     * gyroscope indica si se permite utilizar, o no, el giroscopio del dispositivo al mostrar los documentos. Este elemento es relevante solo si el
     * valor especificado, o determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la propiedad sea
     * una URL. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir el uso del giroscopio; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo depende de la plataforma.
     *
     * @return gyroscope
     */
    Kleenean gyroscope() default Kleenean.UNSPECIFIED;

    /**
     * pictureInPicture indica si se permite utilizar, o no, la función de imagen-en-imagen (picture-in-picture o PiP) para mostrar los documentos.
     * Este elemento es relevante solo si el valor especificado, o determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza
     * cuando el valor de la propiedad sea una URL. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir el uso
     * de la función; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo depende de la plataforma.
     *
     * @return pictureInPicture
     */
    Kleenean pictureInPicture() default Kleenean.UNSPECIFIED;

    /**
     * fullScreen indica si se permite mostrar, o no, los documentos en pantalla completa. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento sourceType es URL o BOTH; si es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es
     * uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir mostrar los documentos en pantalla completa; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo depende de la plataforma.
     *
     * @return fullScreen
     */
    Kleenean fullScreen() default Kleenean.UNSPECIFIED;

    /**
     * sourceURLs especifica una o más URL de sitios válidos para definir el valor de la propiedad. Para cada URL debe especificar al menos el
     * protocolo y el servidor o host; opcionalmente puede especificar el puerto de red y la ruta. Si este elemento no se especifica, entonces la
     * propiedad podrá contener una definición de documentos incrustados de cualquiera de los sitios en la lista general definida para la aplicación.
     * El valor de este elemento también se puede especificar mediante el método setEmbeddedDocumentURLs.
     *
     * @return sourceURLs
     */
    String[] sourceURLs() default "";

    /**
     * searchURL especifica la URL de un sitio Web donde buscar el valor de la propiedad. Por ejemplo: https://calendar.google.com/calendar,
     * https://www.google.com/maps, https://www.youtube.com. Si se especifica el elemento sourceURLs, el valor predeterminado de searchURL se deriva
     * de la primera de las URL de sourceURLs; y si no se especifica, el valor predeterminado es https://www.google.com. El valor de este elemento
     * también se puede especificar mediante el método setSearchURL.
     *
     * @return searchURL
     */
    String searchURL() default "";

}
