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
     * displayWidth específica un trío de números de pixeles de ancho del documento en la vista, correspondientes a tamaño grande, mediano y pequeño,
     * respectivamente. Especifique números enteros entre 144 y 1.920. Alternativamente, omita el elemento para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es el trío (640, 480, 320}. Si alguno de los valores especificados es mayor que 1.920, su valor
     * será 1.920; si es menor o igual a 0, será el valor del correspondiente tamaño en el trío displayHeight; pero si ese valor es menor o igual a 0,
     * será el valor del correspondiente tamaño en el trío predeterminado.
     *
     * @return displayWidth
     */
    int[] displayWidth() default {0, 0, 0};

    /**
     * displayHeight específica un trío de números de pixeles de alto del documento en la vista, correspondientes a tamaño grande, mediano y pequeño,
     * respectivamente. Especifique números enteros entre 144 y 1.080. Alternativamente, omita el elemento para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es el trío (360, 270, 180}. Si alguno de los valores especificados es mayor que 1.080, su valor
     * será 1.080; si es menor o igual a 0, será el valor del correspondiente tamaño en el trío displayWidth; pero si ese valor es mayor que 1.080,
     * será 1.080, y si es menor o igual a 0, será el valor del correspondiente tamaño en el trío predeterminado.
     *
     * @return displayHeight
     */
    int[] displayHeight() default {0, 0, 0};

    /**
     * resizable indica si el ancho del documento se debe ajustar de manera dinámica y proporcional en función de las dimensiones reales del documento
     * y de los valores especificados para los elementos displayWidth y displayHeight. Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE para ajustar el ancho; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return resizable
     */
    Kleenean resizable() default Kleenean.UNSPECIFIED; // Kleenean.TRUE;

    /**
     * frameBorder indica si se debe mostrar o no un borde alrededor del documento en la vista. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento sourceType es URL o
     * BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para mostrar el borde; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo depende de la plataforma.
     *
     * @return frameBorder
     */
    Kleenean frameBorder() default Kleenean.UNSPECIFIED;

    /**
     * encryptedMedia indica si se permite mostrar, o no, documentos encriptados. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento sourceType es URL o BOTH; si sourceType
     * es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione
     * TRUE para permitir mostrar documentos encriptados; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo depende de la plataforma.
     *
     * @return encryptedMedia
     */
    Kleenean encryptedMedia() default Kleenean.UNSPECIFIED;

    /**
     * accelerometer indica si se permite utilizar, o no, el acelerómetro del dispositivo al mostrar los documentos. Este elemento es relevante solo
     * si el valor especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento sourceType
     * es URL o BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para permitir el uso del acelerómetro; en caso contrario, seleccione FALSE. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo depende de la
     * plataforma.
     *
     * @return accelerometer
     */
    Kleenean accelerometer() default Kleenean.UNSPECIFIED;

    /**
     * autoplay indica si se permite utilizar, o no, la función de reproducción automática para mostrar los documentos. Este elemento es relevante
     * solo si el valor especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento
     * sourceType es URL o BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es uno de los elementos
     * de la enumeración Kleenean. Seleccione TRUE para permitir el uso de la función; en caso contrario, seleccione FALSE. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo depende de la
     * plataforma.
     *
     * @return autoplay
     */
    Kleenean autoplay() default Kleenean.UNSPECIFIED;

    /**
     * gyroscope indica si se permite utilizar, o no, el giroscopio del dispositivo al mostrar los documentos. Este elemento es relevante solo si el
     * valor especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento sourceType es URL
     * o BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para permitir el uso del giroscopio; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo depende de la plataforma.
     *
     * @return gyroscope
     */
    Kleenean gyroscope() default Kleenean.UNSPECIFIED;

    /**
     * pictureInPicture indica si se permite utilizar, o no, la función de imagen-en-imagen (picture-in-picture o PiP) para mostrar los documentos.
     * Este elemento es relevante solo si el valor especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o
     * determinado, para el elemento sourceType es URL o BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su
     * valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para permitir el uso de la función; en caso contrario, seleccione
     * FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado
     * del atributo depende de la plataforma.
     *
     * @return pictureInPicture
     */
    Kleenean pictureInPicture() default Kleenean.UNSPECIFIED;

    /**
     * fullScreen indica si se permite mostrar, o no, los documentos en pantalla completa. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento sourceType es URL o BOTH; si sourceType
     * es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione
     * TRUE para permitir mostrar los documentos en pantalla completa; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo depende de la plataforma.
     *
     * @return fullScreen
     */
    Kleenean fullScreen() default Kleenean.UNSPECIFIED;

    /**
     * loading especifica si el navegador debe cargar el documento inmediatamente, o posponer la carga hasta que se cumplan ciertas condiciones. Este
     * elemento es relevante solo si el valor especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o determinado,
     * para el elemento sourceType es URL o BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL.<p>
     * Su valor es uno de los elementos de la enumeración EmbeddedDocumentLoading. Seleccione:
     * <ul>
     * <li>EAGER para cargar el documento inmediatamente.</li>
     * <li>LAZY para posponer la carga hasta que se cumplan ciertas condiciones.</li>
     * </ul><p>
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para excluir el atributo de la definición del <b>iframe</b>.
     *
     * @return loading
     */
    EmbeddedDocumentLoading loading() default EmbeddedDocumentLoading.UNSPECIFIED;

    /**
     * referrerPolicy especifica qué información de referencia enviar al buscar el documento. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento sourceType es URL o
     * BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL.<p>
     * Su valor es uno de los elementos de la enumeración EmbeddedDocumentPolicy. Seleccione:
     * <ul>
     * <li>NO_REFERRER para no enviar información de referencia junto con la solicitud.</li>
     * <li>NO_REFERRER_WHEN_DOWNGRADE para para no enviar información de referencia a orígenes sin HTTPS.</li>
     * <li>ORIGIN para enviar solo esquema, host y puerto.</li>
     * <li>ORIGIN_WHEN_CROSS_ORIGIN para enviar solo esquema, host y puerto en solicitudes de origen cruzado; y para incluir también la ruta en
     * solicitudes del mismo origen.</li>
     * <li>SAME_ORIGIN para enviar información de referencia solo en solicitudes del mismo origen.</li>
     * <li>STRICT_ORIGIN para no enviar información de referencia a un destino menos seguro; por ejemplo, de HTTPS a HTTP.</li>
     * <li>STRICT_ORIGIN_WHEN_CROSS_ORIGIN para enviar la ruta completa al realizar una solicitud del mismo origen; para enviar solo esquema, host y
     * puerto cuando el nivel de seguridad sea el mismo, por ejemplo, de HTTPS a HTTPS; y para no enviar ningún encabezado a un destino menos seguro,
     * por ejemplo, de HTTPS a HTTP.</li>
     * <li>UNSAFE_URL para enviar la ruta completa y la cadena de consulta, pero no el fragmento, la contraseña o el nombre de usuario.</li>
     * </ul><p>
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para excluir el atributo de la definición del <b>iframe</b>.
     *
     * @return referrerPolicy
     */
    EmbeddedDocumentPolicy referrerPolicy() default EmbeddedDocumentPolicy.UNSPECIFIED;

    /**
     * sandbox habilita un conjunto adicional de restricciones para el contenido en el <b>iframe</b>. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento style es FIELD; y el valor especificado, o determinado, para el elemento sourceType es URL o
     * BOTH; si sourceType es BOTH, solo se utiliza cuando el valor de la propiedad sea una URL.<p>
     * Su valor es uno de los elementos de la enumeración EmbeddedDocumentSandbox. Seleccione:
     * <ul>
     * <li>ALLOW_FORMS para permitir el envío de formularios.</li>
     * <li>ALLOW_MODALS para permitir abrir ventanas modales.</li>
     * <li>ALLOW_ORIENTATION_LOCK para permitir bloquear la orientación de la pantalla.</li>
     * <li>ALLOW_POINTER_LOCK para permitir usar el API de bloqueo de puntero.</li>
     * <li>ALLOW_POPUPS para permitir ventanas emergentes.</li>
     * <li>ALLOW_POPUPS_TO_ESCAPE_SANDBOX para permitir ventanas emergentes para abrir nuevas ventanas sin heredar el sandboxing.</li>
     * <li>ALLOW_PRESENTATION para permitir iniciar una sesión de presentación.</li>
     * <li>ALLOW_SAME_ORIGIN para permitir que el contenido del <b>iframe</b> sea tratado como si fuera del mismo origen.</li>
     * <li>ALLOW_SCRIPTS para permitir ejecutar scripts.</li>
     * <li>ALLOW_TOP_NAVIGATION para permitir que el contenido del <b>iframe</b> navegue por su contexto de navegación de nivel superior.</li>
     * <li>ALLOW_TOP_NAVIGATION_BY_USER_ACTIVATION para permitir que el contenido del <b>iframe</b> navegue por su contexto de navegación de nivel
     * superior, pero solo si lo inicia el usuario.</li>
     * </ul><p>
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para excluir el atributo de la definición del <b>iframe</b>.
     *
     * @return sandbox
     */
    EmbeddedDocumentSandbox sandbox() default EmbeddedDocumentSandbox.UNSPECIFIED;

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

    /**
     * encoding indica si se debe codificar, o no, el valor de la propiedad para almacenarlo en la base de datos. Su valor es uno de los elementos de
     * la enumeración Kleenean. Seleccione TRUE para codificar el valor; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     * <p>
     * <b>Advertencias</b>
     * <ul>
     * <li>La codificación limita significativamente el uso de la propiedad en consultas e informes.</li>
     * <li>El algoritmo de codificación utilizado es Base64, el cual no es un algoritmo de cifrado, se decodifica fácilmente y, por lo tanto, no debe
     * utilizarse como un método de cifrado seguro.</li>
     * <li>La longitud del valor codificado es un tercio mayor que la longitud del valor original; si, por ejemplo, la longitud máxima de la propiedad
     * se establece en 2000, entonces la propiedad solo puede contener valores de hasta 1500 caracteres.</li>
     * </ul><p>
     *
     * @return encoding
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html">class Base64</a>
     */
    Kleenean encoding() default Kleenean.UNSPECIFIED;

}
